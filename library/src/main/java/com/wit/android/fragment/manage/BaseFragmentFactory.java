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
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;

import com.wit.android.fragment.annotation.FactoryFragment;
import com.wit.android.fragment.annotation.FactoryFragments;
import com.wit.android.fragment.annotation.FragmentFactories;
import com.wit.android.fragment.util.FragmentAnnotations;

import java.lang.reflect.Field;
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
 * Also, there will be automatically created default tags for all such ids, so they can be obtained
 * by calling {@link #getFragmentTag(int)} with the specific fragment id.
 * </p>
 * <li>{@link com.wit.android.fragment.annotation.FactoryFragment @FactoryFragment} [<b>member</b>]</li>
 * <p>
 * This annotation provides same results as {@link com.wit.android.fragment.annotation.FactoryFragments @FactoryFragments}
 * annotation, but this annotation is meant to be used to mark directly constant fields which specifies
 * fragment ids and also provides more configuration options like the type of fragment which should
 * be instantiated for the specified id.
 * <p/>
 * <b>Note</b>, that tag for fragment with the specified id will be automatically created.
 * </p>
 * <li>{@link com.wit.android.fragment.annotation.FragmentFactories @FragmentFactories} [<b>class - inherited</b>]</li>
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
	 * Static members ==============================================================================
	 */

	/**
	 * Members =====================================================================================
	 */

	/**
	 * <p>
	 * Default fragment transaction options with {@link FragmentTransition#FADE_IN} transition.
	 * </p>
	 */
	private final FragmentController.TransactionOptions DEFAULT_OPTIONS = new FragmentController.TransactionOptions()
			.transition(FragmentTransition.FADE_IN);

	/**
	 * Id of the fragment which was last checked by {@link #isFragmentProvided(int)}.
	 */
	private int mLastCheckedFragmentId = -1;

	/**
	 * Array with gathered
	 */
	private SparseArray<FragmentItem> mFragmentItems;

	/**
	 * List with joined factories. Instances and tags are first obtained from these factories then
	 * from this one.
	 */
	private List<FragmentController.FragmentFactory> mFactories;

	/**
	 * Flag indicating whether an instance of fragment for {@link #mLastCheckedFragmentId} can be
	 * provided by this factory or not.
	 */
	private boolean mFragmentProvided = false;

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
		final SparseArray<FragmentItem> items = new SparseArray<>();
		// Obtain fragment ids.
		if (classOfFactory.isAnnotationPresent(FactoryFragments.class)) {
			final FactoryFragments fragments = classOfFactory.getAnnotation(FactoryFragments.class);

			final int[] ids = fragments.value();
			if (ids.length > 0) {
				for (int id : ids) {
					items.put(id, new FragmentItem(
							id,
							getFragmentTag(id),
							null
					));
				}
			}
		}
		this.processAnnotatedFragments(classOfFactory, items);
		if (items.size() > 0) {
			this.mFragmentItems = items;
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
			return mFragmentProvided;
		}
		// Store last checked fragment id.
		this.mLastCheckedFragmentId = fragmentId;
		// Check joined factories.
		if (hasJoinedFactories()) {
			for (FragmentController.FragmentFactory factory : mFactories) {
				if (factory.isFragmentProvided(fragmentId)) {
					return mFragmentProvided = true;
				}
			}
		}
		return mFragmentProvided = providesFragment(fragmentId);
	}

	/**
	 */
	@Override
	public Fragment createFragmentInstance(int fragmentId, Bundle params) {
		if (hasJoinedFactories()) {
			// Try to obtain dialog fragment from the current joined factories.
			for (FragmentController.FragmentFactory factory : mFactories) {
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
			for (FragmentController.FragmentFactory factory : mFactories) {
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
			for (FragmentController.FragmentFactory factory : mFactories) {
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
		return (mFactories != null) && !mFactories.isEmpty();
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
		return mFactories;
	}

	/**
	 * Protected -----------------------------------------------------------------------------------
	 */

	/**
	 * <p>
	 * Invoked from {@link #isFragmentProvided(int)} if none of the current joined factories provides
	 * a fragment with the specified <var>fragmentId</var>.
	 * </p>
	 * <p>
	 * By default this will check for registered fragment parsed from annotated fields marked
	 * by {@link com.wit.android.fragment.annotation.FactoryFragment @FactoryFragment} annotation
	 * for the specified id. If there is no such a fragment item, <code>false</code> will be returned.
	 * </p>
	 */
	protected boolean providesFragment(int fragmentId) {
		return (mFragmentItems != null) && mFragmentItems.indexOfKey(fragmentId) >= 0;
	}

	/**
	 * <p>
	 * Invoked from {@link #getFragmentTag(int)} if none of the current joined factories provides a
	 * tag for the specified <var>fragmentId</var>.
	 * </p>
	 * <p>
	 * By default this will check for registered fragment parsed from annotated fields marked
	 * by {@link com.wit.android.fragment.annotation.FactoryFragment @FactoryFragment} annotation
	 * for the specified id. If there is no such a fragment item, {@link #createFragmentTag(Class, String)}
	 * will be called with appropriate parameters.
	 * </p>
	 */
	protected String onGetFragmentTag(int fragmentId) {
		return providesFragment(fragmentId) ? mFragmentItems.get(fragmentId).tag : createFragmentTag(getClass(), Integer.toString(fragmentId));
	}

	/**
	 * <p>
	 * Invoked whenever {@link #createFragmentInstance(int, android.os.Bundle)} is called and none of
	 * the current joined factories provides a fragment instance for the specified <var>fragmentId</var>.
	 * </p>
	 * <p>
	 * By default this will check for registered fragment parsed from annotated fields marked
	 * by {@link com.wit.android.fragment.annotation.FactoryFragment @FactoryFragment} annotation
	 * for the specified id and tries to create its instance from class provided by
	 * {@link com.wit.android.fragment.annotation.FactoryFragment#type() @FactoryFragment.type()}
	 * if not default <code>Fragment.class</code>. If there is no such a fragment item, <code>null</code>
	 * will be returned.
	 * </p>
	 */
	protected Fragment onCreateFragmentInstance(int fragmentId, Bundle params) {
		return providesFragment(fragmentId) ? mFragmentItems.get(fragmentId).newInstance(params) : null;
	}

	/**
	 * <p>
	 * Invoked form {@link #getFragmentTransactionOptions(int, android.os.Bundle)} if none of the
	 * current joined factories provides an options for fragment with the specified <var>fragmentId</var>.
	 * </p>
	 * <p>
	 * By default, this returns default transaction options with {@link FragmentTransition#FADE_IN}
	 * transition and tag for the specified <var>fragmentId</var> obtained by {@link #getFragmentTag(int)}.
	 * <b>Note</b>, that changing some attribute of these options will affect options when calling
	 * this method next time except the <b>tag</b> attribute.
	 * </p>
	 */
	protected FragmentController.TransactionOptions onGetFragmentTransactionOptions(int fragmentId, Bundle params) {
		return DEFAULT_OPTIONS.tag(getFragmentTag(fragmentId));
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
		if (mFactories == null) {
			mFactories = new ArrayList<>();
		}
		return mFactories;
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
	 * Processes all annotated fields marked with {@link com.wit.android.support.fragment.annotation.FactoryFragment @FactoryFragment}
	 * annotation and puts them into the given <var>items</var> array.
	 *
	 * @param classOfFactory Class of this fragment factory.
	 * @param items          Initial array of fragment items.
	 */
	@SuppressWarnings("unchecked")
	private void processAnnotatedFragments(final Class<?> classOfFactory, final SparseArray<FragmentItem> items) {
		FragmentAnnotations.iterateFields(classOfFactory, new FragmentAnnotations.FieldProcessor() {

			/**
			 */
			@Override
			public void onProcessField(Field field, String name) {
				if (field.isAnnotationPresent(FactoryFragment.class) && int.class.equals(field.getType())) {
					final FactoryFragment factoryFragment = field.getAnnotation(FactoryFragment.class);
					try {
						final int id = (int) field.get(BaseFragmentFactory.this);
						items.put(id, new FragmentItem(
								id,
								TextUtils.isEmpty(factoryFragment.taggedName()) ?
										getFragmentTag(id) :
										createFragmentTag((Class<? extends FragmentController.FragmentFactory>) classOfFactory, factoryFragment.taggedName()),
								factoryFragment.type()
						));
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	/**
	 * Abstract methods ----------------------------------------------------------------------------
	 */

	/**
	 * Inner classes ===============================================================================
	 */

	/**
	 * Holder for fragment item configuration.
	 */
	static class FragmentItem {

		/**
		 * Members =================================================================================
		 */

		/**
		 * Fragment id specified for this item.
		 */
		final int id;

		/**
		 * Fragment tag specified for this item.
		 */
		final String tag;

		/**
		 * Fragment type specified for this item.
		 */
		final Class<? extends Fragment> type;

		/**
		 * Constructors ============================================================================
		 */

		/**
		 * Creates a new instance of FragmentItem with the given parameters.
		 */
		FragmentItem(int id, String tag, Class<? extends Fragment> type) {
			this.id = id;
			this.tag = tag;
			this.type = type;
		}

		/**
		 * Methods =================================================================================
		 */

		/**
		 * Creates a new instance of Fragment specified for this item.
		 *
		 * @param params Parameters for fragment to be set as its arguments.
		 * @return New fragment instance or <code>null</code> if type of this item is {@link Fragment Fragment.class}
		 * which is default and can not be instantiated or instantiation error occur.
		 */
		public Fragment newInstance(Bundle params) {
			if (type == null || type.equals(Fragment.class)) {
				return null;
			}
			try {
				final Fragment fragment = type.newInstance();
				if (params != null) {
					fragment.setArguments(params);
				}
				return fragment;
			} catch (InstantiationException | IllegalAccessException e) {
				Log.e(TAG, "Failed to instantiate fragment class of(" + type + "). Make sure this fragment has public empty constructor.");
			}
			return null;
		}
	}

	/**
	 * Interface ===================================================================================
	 */
}
