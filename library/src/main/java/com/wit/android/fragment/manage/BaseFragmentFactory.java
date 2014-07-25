/*
 * =================================================================================================
 *                Copyright (C) 2013 - 2014 Martin Albedinsky [Wolf-ITechnologies]
 * =================================================================================================
 *         Licensed under the Apache License, Version 2.0 or later (further "License" only).
 * -------------------------------------------------------------------------------------------------
 * You may use this file only in compliance with the License. More details and copy of this License
 * you may obtain at
 *
 * 		http://www.apache.org/licenses/LICENSE-2.0
 *
 * You can redistribute, modify or publish any part of the code written within this file but as it
 * is described in the License, the software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES or CONDITIONS OF ANY KIND.
 *
 * See the License for the specific language governing permissions and limitations under the License.
 * =================================================================================================
 */
package com.wit.android.fragment.manage;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.util.SparseArray;

import com.wit.android.fragment.annotation.FactoryFragments;
import com.wit.android.fragment.annotation.FragmentFactories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <h4>Class Overview</h4>
 * <p>
 * Fragment factory which provides base simple implementation of required {@link com.wit.android.fragment.manage.FragmentController.FragmentFactory}
 * interface for {@link com.wit.android.fragment.manage.FragmentController}.
 * </p>
 * <h6>Accepted annotations</h6>
 * <ul>
 * <li>{@link com.wit.android.fragment.annotation.FactoryFragments @FactoryFragments} [<b>class</b>]</li>
 * <p>
 * If this annotation is presented, all ids of fragments presented within this annotation will be
 * attached to an instance of annotated BaseFragmentFactory sub-class, so {@link #isFragmentProvided(int)}
 * will returns always <code>true</code> for each of these ids.
 * <p/>
 * Also if {@link com.wit.android.fragment.annotation.FactoryFragments#createTags() @FactoryFragments#createTags()}
 * is set to <code>true</code>, there will be automatically created (cached) tags for all such ids,
 * so they can be obtained by calling {@link #getFragmentTag(int)} with the specific fragment id.
 * </p>
 * <li>{@link com.wit.android.fragment.annotation.FragmentFactories @FragmentFactories} [<b>class, recursively</b>]</li>
 * <p>
 * If this annotation is presented, all classes of FragmentFactory provided by this annotation will
 * be instantiated and joined to an instance of annotated BaseFragmentFactory sub-class.
 * </p>
 * </ul>
 *
 * @author Martin Albedinsky
 */
public abstract class BaseFragmentFactory implements FragmentController.FragmentFactory {

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * Log TAG.
	 */
	private static final String TAG = BaseFragmentFactory.class.getSimpleName();

	/**
	 * Flag indicating whether the debug output trough log-cat is enabled or not.
	 */
	// private static final boolean DEBUG_ENABLED = true;

	/**
	 * Flag indicating whether the output trough log-cat is enabled or not.
	 */
	// private static final boolean LOG_ENABLED = true;

	/**
	 * Enums =======================================================================================
	 */

	/**
	 * Static members ==============================================================================
	 */

	/**
	 * Members =====================================================================================
	 */

	/**
	 * Id of the fragment which was last checked by {@link #isFragmentProvided(int)}.
	 */
	private int mLastCheckedFragmentId = -1;

	/**
	 * Arrays --------------------------------------------------------------------------------------
	 */

	/**
	 * List of tags for all fragments provided by this factory.
	 */
	private List<Integer> aFragmentIds = null;

	/**
	 * Array of tags for all fragments provided by this factory.
	 */
	private SparseArray<String> aFragmentTags = null;

	/**
	 * List with joined factories. Instances and tags are first obtained from these factories then
	 * from this one.
	 */
	private List<FragmentController.FragmentFactory> aFactories;

	/**
	 * Booleans ------------------------------------------------------------------------------------
	 */

	/**
	 * Flag indicating whether an instance of fragment for {@link #mLastCheckedFragmentId} can be
	 * provided by this factory or not.
	 */
	private boolean bFragmentProvided = false;

	/**
	 * Constructors ================================================================================
	 */

	/**
	 * <p>
	 * Creates a new instance of BaseFragmentFactory. If {@link com.wit.android.fragment.annotation.FactoryFragments @FactoryFragments}
	 * or {@link com.wit.android.fragment.annotation.FragmentFactories @FragmentFactories}
	 * annotations are presented above a sub-class of BaseFragmentFactory, they will be processed here.
	 * </p>
	 */
	public BaseFragmentFactory() {
		final Class<?> classOfFactory = ((Object) this).getClass();
		/**
		 * Process class annotations.
		 */
		// Obtain fragment ids.
		if (classOfFactory.isAnnotationPresent(FactoryFragments.class)) {
			final FactoryFragments fragments = classOfFactory.getAnnotation(FactoryFragments.class);

			final int[] ids = fragments.value();
			if (ids.length > 0) {
				this.aFragmentIds = new ArrayList<>(ids.length);
				for (int id : ids) {
					aFragmentIds.add(id);
				}

				// Create also tags if requested.
				if (fragments.createTags()) {
					final SparseArray<String> tags = new SparseArray<>(ids.length);
					for (int id : ids) {
						tags.put(id, getFragmentTag(id));
					}
					this.aFragmentTags = tags;
				}
			}
		}
		// Obtain joined factories.
		final List<Class<? extends FragmentController.FragmentFactory>> factories = this.gatherJoinedFactories(
				classOfFactory, new ArrayList<Class<? extends FragmentController.FragmentFactory>>()
		);
		if (!factories.isEmpty()) {
			for (Class<? extends FragmentController.FragmentFactory> factory : factories) {
				FragmentController.FragmentFactory fragmentFactory = null;
				try {
					fragmentFactory = factory.newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					Log.e(
							TAG,
							"Failed to instantiate the fragment factory class of(" + factory.getSimpleName() + ")." +
									"Make sure this fragment factory has public empty constructor.",
							e
					);
				}
				if (fragmentFactory != null) {
					joinFactory(fragmentFactory);
				}
			}
		}
	}

	/**
	 * Methods =====================================================================================
	 */

	/**
	 * Public --------------------------------------------------------------------------------------
	 */

	/**
	 * <p>
	 * Creates a tag for fragment in the required format for the specified class of factory and <var>fragmentName</var>.
	 * </p>
	 * <p>
	 * Example format: <u>com.android.app.fragment.factories.ProfileActivityFactory.TAG.EditProfile</u><br/><br/>
	 * - where <b>com.android.app.fragment.factories</b> is a name of the package where the specified
	 * <var>classOfFactory</var> is situated, <b>ProfileActivityFactory</b> is a name of the <var>classOfFactory</var>
	 * class, <b>TAG</b> is tag identifier an <b>EditProfile</b> is <var>fragmentName</var>.
	 * </p>
	 *
	 * @param classOfFactory A class of the factory for which should be requested tag created.
	 * @param fragmentName   A fragment name (can be fragment's class name) for which should be requested
	 *                       tag created.
	 * @return Fragment tag in required format, or <code>null</code> if the <var>fragmentName</var>
	 * is <code>null</code> or empty.
	 */
	public static String createFragmentTag(Class<? extends FragmentController.FragmentFactory> classOfFactory, String fragmentName) {
		// Only valid fragment name is allowed.
		if (fragmentName == null || fragmentName.length() == 0) {
			return null;
		}
		return classOfFactory.getPackage().getName() + "." + classOfFactory.getSimpleName() + ".TAG." + fragmentName;
	}

	/**
	 */
	@Override
	public boolean isFragmentProvided(int fragmentId) {
		if (fragmentId == mLastCheckedFragmentId) {
			return bFragmentProvided;
		}
		// Store last checked fragment id.
		this.mLastCheckedFragmentId = fragmentId;
		// Check joined factories.
		if (hasJoinedFactories()) {
			for (FragmentController.FragmentFactory factory : aFactories) {
				if (factory.isFragmentProvided(fragmentId)) {
					return bFragmentProvided = true;
				}
			}
		}
		return bFragmentProvided = providesFragment(fragmentId);
	}

	/**
	 */
	@Override
	public Fragment createFragmentInstance(int fragmentId, Bundle params) {
		if (hasJoinedFactories()) {
			// Try to obtain dialog fragment from the current joined factories.
			for (FragmentController.FragmentFactory factory : aFactories) {
				if (factory.isFragmentProvided(fragmentId)) {
					return factory.createFragmentInstance(fragmentId, params);
				}
			}
		}
		// Create fragment within this factory.
		return onCreateFragmentInstance(fragmentId, params);
	}

	/**
	 */
	@Override
	public String getFragmentTag(int fragmentId) {
		if (hasJoinedFactories()) {
			// Try to obtain tag from the joined factories.
			for (FragmentController.FragmentFactory factory : aFactories) {
				if (factory.isFragmentProvided(fragmentId)) {
					return factory.getFragmentTag(fragmentId);
				}
			}
		}
		return onGetFragmentTag(fragmentId);
	}

	/**
	 */
	@Override
	public FragmentController.TransactionOptions getFragmentTransactionOptions(int fragmentId, Bundle params) {
		if (hasJoinedFactories()) {
			// Try to obtain TransactionOptions from the joined factories.
			for (FragmentController.FragmentFactory factory : aFactories) {
				if (factory.isFragmentProvided(fragmentId)) {
					return factory.getFragmentTransactionOptions(fragmentId, params);
				}
			}
		}
		return onGetFragmentTransactionOptions(fragmentId, params);
	}

	/**
	 * <p>
	 * Checks whether this factory instance has some joined factories or not.
	 * </p>
	 *
	 * @return <code>True</code> if there are some joined factories, <code>false</code> otherwise.
	 * @see #getJoinedFactories()
	 */
	public boolean hasJoinedFactories() {
		return (aFactories != null) && !aFactories.isEmpty();
	}

	/**
	 * Getters + Setters ---------------------------------------------------------------------------
	 */

	/**
	 * <p>
	 * Joins the given factory with this one. <b>Note</b>, that fragment instances (and their tags)
	 * are obtained from the current joined factories in order as they was joined.
	 * </p>
	 *
	 * @param factory Fragment factory to join with this one.
	 * @see #getJoinedFactories()
	 */
	public final void joinFactory(FragmentController.FragmentFactory factory) {
		final List<FragmentController.FragmentFactory> factories = this.accessFactories();
		if (!factories.contains(factory)) {
			factories.add(factory);
		}
	}

	/**
	 * <p>
	 * Returns all currently joined factories with this one.
	 * </p>
	 *
	 * @return Set of fragment factories.
	 * @see #hasJoinedFactories()
	 * @see #joinFactory(com.wit.android.fragment.manage.FragmentController.FragmentFactory)
	 */
	public final List<FragmentController.FragmentFactory> getJoinedFactories() {
		return aFactories;
	}

	/**
	 * Protected -----------------------------------------------------------------------------------
	 */

	/**
	 * <p>
	 * Invoked from {@link #isFragmentProvided(int)} if none of the current joined factories provide
	 * a fragment with the specified <var>fragmentId</var>.
	 * </p>
	 */
	protected boolean providesFragment(int fragmentId) {
		return (aFragmentIds != null) && aFragmentIds.contains(fragmentId);
	}

	/**
	 * <p>
	 * Invoked from {@link #getFragmentTag(int)} if none of the current joined factories provide a tag
	 * for the specified <var>fragmentId</var>.
	 * </p>
	 */
	protected String onGetFragmentTag(int fragmentId) {
		return (aFragmentTags != null) ? aFragmentTags.get(fragmentId) : createFragmentTag(getClass(), Integer.toString(fragmentId));
	}

	/**
	 * <p>
	 * Invoked form {@link #getFragmentTransactionOptions(int, android.os.Bundle)} if none of the current
	 * joined factories provide an options for fragment with the specified <var>fragmentId</var>.
	 * </p>
	 */
	protected FragmentController.TransactionOptions onGetFragmentTransactionOptions(int fragmentId, Bundle params) {
		return new FragmentController.TransactionOptions().tag(getFragmentTag(fragmentId));
	}

	/**
	 * Private -------------------------------------------------------------------------------------
	 */

	/**
	 * Returns the list of joined factories. If the list isn't initialized yet, a new instance will
	 * be created.
	 *
	 * @return List of the joined factories. Always <code>valid</code> list.
	 */
	private List<FragmentController.FragmentFactory> accessFactories() {
		if (aFactories == null) {
			aFactories = new ArrayList<>();
		}
		return aFactories;
	}

	/**
	 * Gathers all classes of fragment factories presented within FragmentFactories annotation. Note,
	 * that this is recursive method, which will gather all classes from
	 * {@link com.wit.android.fragment.annotation.FragmentFactories @FragmentFactories}
	 * annotation presented above the given <var>classOfFragment</var>.
	 *
	 * @param classOfFactory Class of fragment where to check FragmentFactories annotation.
	 * @param factories      List of already gathered factories.
	 * @return List of all gathered classes of fragment factories.
	 */
	private List<Class<? extends FragmentController.FragmentFactory>> gatherJoinedFactories(Class<?> classOfFactory, List<Class<? extends FragmentController.FragmentFactory>> factories) {
		if (classOfFactory.isAnnotationPresent(FragmentFactories.class)) {
			final FragmentFactories fragmentFactories = classOfFactory.getAnnotation(FragmentFactories.class);
			if (fragmentFactories.value().length > 0) {
				factories.addAll(Arrays.asList(fragmentFactories.value()));
			}
		}

		// Obtain also factories of super class, but only to this BaseFragmentFactory super.
		final Class<?> superOfFactory = classOfFactory.getSuperclass();
		if (superOfFactory != null && !classOfFactory.equals(BaseFragmentFactory.class)) {
			gatherJoinedFactories(superOfFactory, factories);
		}
		return factories;
	}

	/**
	 * Abstract methods ----------------------------------------------------------------------------
	 */

	/**
	 * <p>
	 * Invoked whenever {@link #createFragmentInstance(int, android.os.Bundle)} is called and any of
	 * the current joined factories does not provide fragment instance for the specified <var>fragmentId</var>.
	 * </p>
	 */
	protected abstract Fragment onCreateFragmentInstance(int fragmentId, Bundle params);

	/**
	 * Inner classes ===============================================================================
	 */

	/**
	 * Interface ===================================================================================
	 */
}
