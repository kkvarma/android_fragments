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
package com.wit.android.support.fragment.manage;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.wit.android.support.fragment.FragmentsConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * <h4>Class Overview</h4>
 * todo: description
 *
 * @author Martin Albedinsky
 * @see com.wit.android.support.fragment.manage.FragmentController.FragmentFactory
 * @see com.wit.android.support.fragment.manage.FragmentController.TransactionOptions
 */
public class FragmentController {

	/**
	 * Interface ===================================================================================
	 */

	/**
	 * <h4>Interface Overview</h4>
	 * Required interface for fragment factory.
	 *
	 * @author Martin Albedinsky
	 * @see com.wit.android.support.fragment.manage.FragmentController
	 */
	public static interface FragmentFactory {

		/**
		 * Returns an instance of the fragment associated with the specified <var>fragmentId</var>
		 * within this fragment factory.
		 * <p/>
		 * This is in most cases invoked from an instance of {@link com.wit.android.support.fragment.manage.FragmentController},
		 * when that instance of FragmentController was requested to show fragment with the specified
		 * <var>fragmentId</var>.
		 *
		 * @param fragmentId An id of the requested fragment to create.
		 * @param params     Bundle with parameters for fragment.
		 * @return An instance of the fragment associated with the specified <var>fragmentId</var> or
		 * <code>null</code> if this fragment factory doesn't provides requested fragment.
		 * @see #isFragmentProvided(int)
		 */
		@Nullable
		public Fragment createFragmentInstance(int fragmentId, @Nullable Bundle params);

		/**
		 * Returns an options for the fragment associated with the specified <var>fragmentId</var>
		 * within this fragment factory to manage such a fragment showing by FragmentController.
		 * <p/>
		 * This is in most cases invoked form an instance of {@link com.wit.android.support.fragment.manage.FragmentController},
		 * when that instance of FragmentController was requested to show fragment with the specified
		 * <var>fragmentId</var>. In such a case, if this fragment factory will not provide valid
		 * TransactionOptions, FragmentController will use default ones.
		 *
		 * @param fragmentId An id of the fragment for which are options requested.
		 * @param params     Same params as for {@link #createFragmentInstance(int, android.os.Bundle)}.
		 * @return TransactionOptions object for fragment associated with the specified <var>fragmentId</var>
		 * or <code>null</code> if this fragment factory doesn't provides TransactionOptions for fragment
		 * with the specified <var>fragmentId</var>.
		 */
		@Nullable
		public TransactionOptions getFragmentTransactionOptions(int fragmentId, @Nullable Bundle params);

		/**
		 * Returns a tag for the fragment associated with the specified <var>fragmentId</var> within
		 * this fragment factory.
		 *
		 * @param fragmentId An id of the fragment for which is its TAG requested.
		 * @return Tag for fragment associated with the specified <var>fragmentId</var> or <code>null</code>
		 * if this fragment factory doesn't provides TAG for fragment with the specified <var>fragmentId</var>.
		 */
		@Nullable
		public String getFragmentTag(int fragmentId);

		/**
		 * Returns flag indicating whether there is provided a fragment for the specified <var>fragmentId</var>
		 * by this factory or not.
		 *
		 * @param fragmentId An id of the desired fragment to check.
		 * @return <code>True</code> if fragment is provided, so {@link #createFragmentInstance(int, android.os.Bundle)}
		 * will return an instance of such a fragment, <code>false</code> otherwise.
		 */
		public boolean isFragmentProvided(int fragmentId);
	}

	/**
	 * <h4>Class Overview</h4>
	 * todo: description
	 *
	 * @author Martin Albedinsky
	 */
	public static interface OnChangeListener {

		/**
		 * Invoked whenever an old fragment is replaced by a new one or simply a new fragment is first
		 * time showed by an instance of FragmentController.
		 *
		 * @param id          An id of the currently changed (showed) fragment.
		 * @param tag         A tag of the currently changed (showed) fragment.
		 * @param fromFactory <code>True</code> if the changed fragment was obtained from a factory,
		 *                    <code>false</code> otherwise.
		 */
		public void onFragmentChanged(int id, String tag, boolean fromFactory);
	}

	/**
	 * <h4>Class Overview</h4>
	 * todo: description
	 *
	 * @author Martin Albedinsky
	 */
	public static interface OnBackStackChangeListener {

		/**
		 * Invoked whenever fragments back stack change occur.
		 *
		 * @param added <code>True</code> if there was added new back stack entry, <code>false</code>
		 *              if old one was removed.
		 * @param id    An id of the back stack entry of which status was changed. This is actually
		 *              a position of the added/removed entry in the fragments back stack. This is
		 *              default behaviour of the fragments back stack managed by {@link android.support.v4.app.FragmentManager}.
		 * @param tag   A tag of the back stack entry of which status was changed.
		 */
		public void onFragmentsBackStackChanged(boolean added, int id, String tag);
	}

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * Log TAG.
	 */
	private static final String TAG = "FragmentController";

	/**
	 * Flag indicating whether the debug output trough log-cat is enabled or not.
	 */
	private static final boolean DEBUG_ENABLED = FragmentsConfig.LIBRARY_DEBUG_LOG_ENABLED;

	/**
	 * Flag indicating whether the output trough log-cat is enabled or not.
	 */
	private static final boolean LOG_ENABLED = FragmentsConfig.LIBRARY_LOG_ENABLED;

	/**
	 * Default tag used when showing fragments.
	 */
	public static final String FRAGMENT_TAG = "com.wit.android.support.fragment.manage.FragmentController.TAG.Fragment";

	/**
	 * Static members ==============================================================================
	 */

	/**
	 * Members =====================================================================================
	 */

	/**
	 * Fragment manager to handle showing, obtaining and hiding fragments.
	 */
	final FragmentManager mFragmentManager;

	/**
	 * Fragment factory which provides fragment instances to show (manage) by this controller.
	 */
	private FragmentFactory mFactory;

	/**
	 * Id of a layout container within the current window view hierarchy, into which will be view of
	 * all managed fragments placed.
	 */
	private int mFragmentContainerId = -1;

	/**
	 * The entry at the top of the fragments back stack.
	 */
	private FragmentManager.BackStackEntry mTopBackStackEntry;

	/**
	 * Listener callback for back stack changes.
	 */
	private OnBackStackChangeListener mBackStackListener;

	/**
	 * Listener callback for fragment changes.
	 */
	private OnChangeListener mFragmentListener;

	/**
	 * Tag of the currently showing fragment showed by this controller
	 */
	private String mCurrentFragmentTag;

	/**
	 * Constructors ================================================================================
	 */

	/**
	 * Creates a new instance of FragmentController within the given <var>parentFragment</var>'s context.
	 * <p/>
	 * Passed fragment will be used to obtain an instance of {@link android.support.v4.app.FragmentManager}
	 * by {@link android.support.v4.app.Fragment#getFragmentManager()} that will be used to mange
	 * fragments by this controller.
	 * <p/>
	 * If the given <var>parentFragment</var> implements {@link FragmentController.OnBackStackChangeListener}
	 * or {@link com.wit.android.support.fragment.manage.FragmentController.OnChangeListener} it will
	 * be automatically set as these callbacks to this controller.
	 *
	 * @param parentFragment A fragment in which will be this controller used.
	 * @see #FragmentController(android.support.v4.app.FragmentActivity)
	 */
	public FragmentController(@NonNull Fragment parentFragment) {
		this(parentFragment.getFragmentManager());
		if (parentFragment instanceof OnBackStackChangeListener) {
			setOnBackStackChangeListener((OnBackStackChangeListener) parentFragment);
		}
		if (parentFragment instanceof OnChangeListener) {
			setOnChangeListener((OnChangeListener) parentFragment);
		}
	}

	/**
	 * Creates a new instance of FragmentController within the given <var>parentActivity</var>'s context.
	 * <p/>
	 * Passed activity will be used to obtain an instance of {@link android.support.v4.app.FragmentManager}
	 * by {@link android.support.v4.app.FragmentActivity#getFragmentManager()} that will be used to
	 * mange fragments by this controller.
	 * <p/>
	 * If the given <var>parentActivity</var> implements {@link FragmentController.OnBackStackChangeListener}
	 * or {@link com.wit.android.support.fragment.manage.FragmentController.OnChangeListener} it will
	 * be automatically set as these callbacks to this controller.
	 *
	 * @param parentActivity An activity in which will be this controller used.
	 * @see #FragmentController(android.support.v4.app.Fragment)
	 */
	public FragmentController(@NonNull FragmentActivity parentActivity) {
		this(parentActivity.getSupportFragmentManager());
		if (parentActivity instanceof OnBackStackChangeListener) {
			setOnBackStackChangeListener((OnBackStackChangeListener) parentActivity);
		}
		if (parentActivity instanceof OnChangeListener) {
			setOnChangeListener((OnChangeListener) parentActivity);
		}
	}

	/**
	 * Creates a new instance of FragmentController with the given <var>fragmentManager</var>.
	 *
	 * @param fragmentManager Fragment manager to manage fragments.
	 * @see #FragmentController(android.support.v4.app.FragmentActivity)
	 * @see #FragmentController(android.support.v4.app.Fragment)
	 */
	public FragmentController(@NonNull FragmentManager fragmentManager) {
		this.mFragmentManager = fragmentManager;
		mFragmentManager.addOnBackStackChangedListener(new BackStackListener());
		// Check for back stacked fragments.
		final int n = mFragmentManager.getBackStackEntryCount();
		if (n > 0) {
			this.mTopBackStackEntry = mFragmentManager.getBackStackEntryAt(n - 1);
		}
	}

	/**
	 * Methods =====================================================================================
	 */

	/**
	 * Public --------------------------------------------------------------------------------------
	 */

	/**
	 * Same as {@link #showFragment(int, android.os.Bundle)} without params.
	 */
	public boolean showFragment(int fragmentId) {
		return showFragment(fragmentId, null);
	}

	/**
	 * Shows a fragment provided by the current factory associated with the specified <var>fragmentId</var>.
	 *
	 * @param fragmentId Id of the desired factory fragment to show.
	 * @param params     Parameters for fragment. These will be passed to the factory when calling
	 *                   {@link FragmentFactory#createFragmentInstance(int, android.os.Bundle)} upon it.
	 * @return <code>True</code> if the requested fragment has been successfully showed, <code>false</code>
	 * if the current factory does not provide fragment with the requested id or there is already
	 * fragment with the same TAG already showing and should not be replaced.
	 * @throws java.lang.IllegalStateException If this controller does not have factory attached.
	 */
	public boolean showFragment(int fragmentId, @Nullable Bundle params) {
		// Check if we have fragment factory and fragment is provided.
		if (!checkFragmentFactory(fragmentId)) {
			Log.e(TAG, "Current factory(" + mFactory.getClass().getSimpleName() + ") does not provide fragment for the requested id(" + fragmentId + ").");
			return false;
		}
		return this.performShowFragment(fragmentId, params);
	}

	/**
	 * Same as {@link #showFragment(android.support.v4.app.Fragment, FragmentController.TransactionOptions)}
	 * with default {@link FragmentController.TransactionOptions}.
	 */
	public boolean showFragment(@NonNull Fragment fragment) {
		return showFragment(fragment, new TransactionOptions());
	}

	/**
	 * Same as {@link #showFragment(android.support.v4.app.Fragment, FragmentController.TransactionOptions)}
	 * with default {@link FragmentController.TransactionOptions} with the given <var>fragmentTag</var>.
	 */
	public boolean showFragment(@Nullable Fragment fragment, @Nullable String fragmentTag) {
		return showFragment(fragment, new TransactionOptions().tag(fragmentTag));
	}

	/**
	 * Shows the given <var>fragment</var> using the given options.
	 *
	 * @param fragment The fragment instance to show.
	 * @param options  Options used when showing fragment.
	 * @return <code>True</code> if the fragment has been successfully showed, <code>false</code>
	 * if there is already fragment with the same TAG already showing and should not be replaced.
	 */
	public boolean showFragment(@Nullable Fragment fragment, @Nullable TransactionOptions options) {
		return this.performShowFragment(fragment, options);
	}

	/**
	 * Returns an instance of Fragment obtained form the FragmentManager attached to this controller
	 * by the specified <var>fragmentTag</var>.
	 *
	 * @param fragmentTag TAG of the desired fragment to find.
	 * @return Fragment instance or <code>null</code> if there is no such a fragment within the
	 * FragmentManager with the specified tag.
	 * @see #findFactoryFragmentById(int)
	 */
	@Nullable
	public Fragment findFragmentByTag(@Nullable String fragmentTag) {
		return mFragmentManager.findFragmentByTag(fragmentTag);
	}

	/**
	 * Same as {@link #findFragmentByTag(String)}, but with id.
	 */
	@Nullable
	public Fragment findFragmentById(int fragmentId) {
		return mFragmentManager.findFragmentById(fragmentId);
	}

	/**
	 * Same as {@link #findFragmentByTag(String)}, where fragment tag will be requested from the
	 * current factory.
	 *
	 * @param fragmentId Id of the desired factory fragment to find.
	 * @throws java.lang.IllegalStateException If this controller does not have factory attached.
	 */
	@Nullable
	public Fragment findFactoryFragmentById(int fragmentId) {
		return this.checkFragmentFactory(fragmentId) ? findFragmentByTag(mFactory.getFragmentTag(fragmentId)) : null;
	}

	/**
	 * Returns an instance of the fragment which is currently being shown on the screen. To determine
	 * visible fragment, these fragment's flags are checked:
	 * <ul>
	 * <li>{@link android.support.v4.app.Fragment#isVisible()}</li>
	 * <li>or {@link android.support.v4.app.Fragment#isAdded()}</li>
	 * </ul>
	 * This will be actually an instance of the fragment which was showed as last.
	 *
	 * @return Instance of the currently visible or added fragment, or <code>null</code> if there
	 * are no fragments (<code>n = 0</code>) within the current fragment manager or all checked fragments
	 * are in the undesirable state.
	 * @see #getVisibleSecondFragment()
	 */
	@Nullable
	public Fragment getVisibleFragment() {
		final List<Fragment> fragments = mFragmentManager.getFragments();
		final List<Fragment> visibleFragments = new ArrayList<>();

		if (fragments != null) {
			// Get only visible fragments.
			for (Fragment fragment : fragments) {
				// TODO: perform here more fragment flag checks ?
				if (fragment != null && (fragment.isVisible() || fragment.isAdded())) {
					visibleFragments.add(fragment);

					if (DEBUG_ENABLED) {
						Log.d(TAG, "visible/added fragment(" + fragment.getTag() + ")");
					}
				}
			}
		}

		int size;
		Fragment fragment = null;
		switch (size = visibleFragments.size()) {
			case 0:
				// No fragments available.
				break;
			case 1:
				// Only one fragment available, get it.
				fragment = visibleFragments.get(0);
				break;
			default:
				// More than one fragment available.
				fragment = visibleFragments.get(size - 1);
		}
		if (DEBUG_ENABLED) {
			Log.d(TAG, "Resolved visible fragment(" + fragment + ")");
		}
		return fragment;
	}

	/**
	 * Returns an instance of the second fragment which is currently being shown on the screen, where
	 * two fragments are visible at the same time. To determine visible second fragment, these fragment's
	 * flags are checked:
	 * <ul>
	 * <li>{@link android.support.v4.app.Fragment#isVisible()}</li>
	 * <li>or {@link android.support.v4.app.Fragment#isAdded()}</li>
	 * </ul>
	 * This will be actually an instance of the fragment which was showed before the last one.
	 *
	 * @return Instance of the currently visible or added second fragment, or <code>null</code> if there
	 * are no fragments (<code>n > 1</code>) within the current fragment manager or all checked fragments
	 * are in the undesirable state.
	 * @see #getVisibleSecondFragment()
	 */
	@Nullable
	public Fragment getVisibleSecondFragment() {
		final List<Fragment> fragments = mFragmentManager.getFragments();
		final List<Fragment> visibleFragments = new ArrayList<>();

		if (fragments != null) {
			// Get only visible fragments.
			for (Fragment fragment : fragments) {
				// TODO: perform here more fragment flag checks ?
				if (fragment != null && (fragment.isVisible() || fragment.isAdded())) {
					visibleFragments.add(fragment);

					if (DEBUG_ENABLED) {
						Log.d(TAG, "visible/added fragment(" + fragment.getTag() + ")");
					}
				}
			}
		}

		int size;
		Fragment secondFragment = null;
		switch (size = visibleFragments.size()) {
			case 0:
			case 1:
				// No or one fragments available.
				break;
			case 2:
				// Two fragments available, get the second one.
				secondFragment = visibleFragments.get(0);
				break;
			default:
				// More than two fragments available.
				secondFragment = visibleFragments.get(size - 2);
		}
		if (DEBUG_ENABLED) {
			Log.d(TAG, "Resolved second visible fragment(" + secondFragment + ")");
		}
		return secondFragment;
	}

	/**
	 * Wrapped {@link android.support.v4.app.FragmentManager#popBackStack()}.
	 */
	public void hideVisibleFragment() {
		mFragmentManager.popBackStack();
	}

	/**
	 * Wrapped {@link android.support.v4.app.FragmentManager#popBackStackImmediate()}.
	 */
	public boolean hideVisibleFragmentImmediate() {
		return mFragmentManager.popBackStackImmediate();
	}

	/**
	 * Same as {@link #setFragmentOptionsMenuVisible(String, boolean)}, where fragment tag will be
	 * requested form the current factory by the specified <var>fragmentId</var>.
	 *
	 * @param fragmentId Id of the desired fragment from the current factory, of which options menu
	 *                   to show/hide.
	 */
	public boolean setFactoryFragmentOptionsMenuVisible(int fragmentId, boolean visible) {
		return this.checkFragmentFactory(fragmentId) && setFragmentOptionsMenuVisible(mFactory.getFragmentTag(fragmentId), visible);
	}

	/**
	 * Same as {@link #setFragmentOptionsMenuVisible(String, boolean)} but with fragment id.
	 */
	public boolean setFragmentOptionsMenuVisible(int fragmentId, boolean visible) {
		final Fragment fragment = mFragmentManager.findFragmentById(fragmentId);
		if (fragment != null) {
			fragment.setHasOptionsMenu(visible);
			return true;
		}
		return false;
	}

	/**
	 * Shows/hides options menu of the requested fragment by calling {@link android.support.v4.app.Fragment#setHasOptionsMenu(boolean)}.
	 *
	 * @param fragmentTag A tag of the desired fragment of which options to show/hide.
	 * @param visible     <code>True</code> to show options menu, <code>false</code> to hide options menu.
	 * @return <code>True</code> if fragment was found and request to show/hide its options menu was
	 * performed, <code>false</code> otherwise.
	 * @see #setFragmentOptionsMenuVisible(int, boolean)
	 */
	public boolean setFragmentOptionsMenuVisible(@Nullable String fragmentTag, boolean visible) {
		final Fragment fragment = mFragmentManager.findFragmentByTag(fragmentTag);
		if (fragment != null) {
			fragment.setHasOptionsMenu(visible);
			return true;
		}
		return false;
	}

	/**
	 * Wrapped {@link android.support.v4.app.FragmentManager#beginTransaction()}.
	 * <p/>
	 * <b>Do not forget to commit here created transaction.</b>
	 */
	@NonNull
	@SuppressLint("CommitTransaction")
	public FragmentTransaction beginTransaction() {
		return mFragmentManager.beginTransaction();
	}

	/**
	 * Returns a flag indicating whether this controller has factory attached or not.
	 *
	 * @return <code>True</code> if factory is attached, <code>false</code> otherwise.
	 */
	public boolean hasFragmentFactory() {
		return mFactory != null;
	}

	/**
	 * Returns flag indicating whether there are some fragments within the fragment manager's back
	 * stack or not.
	 *
	 * @return <code>True</code> if fragment manager's back stack holds some entries, <code>false</code>
	 * otherwise.
	 * @see android.support.v4.app.FragmentManager#getBackStackEntryCount()
	 */
	public boolean hasBackStackEntries() {
		return mFragmentManager.getBackStackEntryCount() > 0;
	}

	/**
	 * Clears fragments back stack by calling {@link android.support.v4.app.FragmentManager#popBackStack()}
	 * in loop of size obtained by {@link android.support.v4.app.FragmentManager#getBackStackEntryCount()}.
	 * <p/>
	 * <b>Note</b>, that {@link android.support.v4.app.FragmentManager#popBackStack()} is an asynchronous
	 * call, so the fragments back stack can be cleared in the feature not immediately.
	 *
	 * @see #clearBackStackImmediate()
	 */
	public void clearBackStack() {
		final int n = mFragmentManager.getBackStackEntryCount();
		if (n > 0) {
			for (int i = 0; i < n; i++) {
				mFragmentManager.popBackStack();
			}
		}
	}

	/**
	 * Like {@link #clearBackStack()}, but this will call {@link android.support.v4.app.FragmentManager#popBackStackImmediate()}.
	 * <p/>
	 * <b>Note</b>, that {@link android.support.v4.app.FragmentManager#popBackStackImmediate()} is a
	 * synchronous call, so the fragments back stack will be popped immediately within this call. If
	 * there is too many fragments, this can take some time.
	 *
	 * @return <code>True</code> if there was at least one fragment popped, <code>false</code> otherwise.
	 */
	public boolean clearBackStackImmediate() {
		final int n = mFragmentManager.getBackStackEntryCount();
		if (n > 0) {
			boolean popped = false;
			for (int i = 0; i < n; i++) {
				if (mFragmentManager.popBackStackImmediate() && !popped) {
					popped = true;
				}
			}
			return popped;
		}
		return false;
	}

	/**
	 * Getters + Setters ---------------------------------------------------------------------------
	 */

	/**
	 * Registers a callback to be invoked when some change occur in the fragments back stack.
	 *
	 * @param listener Listener callback.
	 */
	public void setOnBackStackChangeListener(@NonNull OnBackStackChangeListener listener) {
		this.mBackStackListener = listener;
	}

	/**
	 * Removes the current OnBackStackChangeListener callback.
	 */
	public void removeOnBackStackChangeListener() {
		this.mFragmentListener = null;
	}

	/**
	 * Registers a callback to be invoked when fragments are being changed.
	 *
	 * @param listener Listener callback.
	 */
	public void setOnChangeListener(@NonNull OnChangeListener listener) {
		this.mFragmentListener = listener;
	}

	/**
	 * Removes the current OnChangeListener callback.
	 */
	public void removeOnChangeListener() {
		this.mBackStackListener = null;
	}

	/**
	 * Returns a tag of the currently showing fragment. <b>Note</b>, that this is only accurate, as
	 * it depends on how are fragments changing, and if all fragments are managed by this controller.
	 * <p/>
	 * The current value of this tag is changing when back stack change occur or there was showed a
	 * new fragment by some of methods provided by this controller.
	 *
	 * @return Tag of the currently showing fragment or <code>null</code> if no fragment was shown
	 * by this controller yet.
	 */
	@Nullable
	public String getCurrentFragmentTag() {
		return mCurrentFragmentTag;
	}

	/**
	 * Returns a tag of the currently visible fragment.
	 *
	 * @return Fragment tag or <code>null</code> if there is currently no fragments visible.
	 * @see #getVisibleFragment()
	 * @see #getVisibleSecondFragmentTag()
	 */
	@Nullable
	public String getVisibleFragmentTag() {
		final Fragment visibleFragment = getVisibleFragment();
		return (visibleFragment != null) ? visibleFragment.getTag() : null;
	}

	/**
	 * Returns a tag of the second currently visible fragment.
	 *
	 * @return Fragment tag or <code>null</code> if there is only one or none currently visible fragments.
	 * @see #getVisibleSecondFragment()
	 * @see #getVisibleFragmentTag()
	 */
	@Nullable
	public String getVisibleSecondFragmentTag() {
		final Fragment visibleFragment = getVisibleSecondFragment();
		return (visibleFragment != null) ? visibleFragment.getTag() : null;
	}

	/**
	 * Returns the fragment manager passed to this controller during initialization.
	 *
	 * @return Instance of FragmentManager.
	 */
	@NonNull
	public FragmentManager getFragmentManager() {
		return mFragmentManager;
	}

	/**
	 * Sets an id of the layout container used to host root views of all fragments manager by this
	 * controller.
	 *
	 * @param layoutId The desired id of layout container within the current window view hierarchy,
	 *                 into which should be views of all managed fragments placed.
	 * @see #getFragmentContainerId()
	 */
	public void setFragmentContainerId(int layoutId) {
		this.mFragmentContainerId = layoutId;
	}

	/**
	 * Returns an id of the layout container for fragment views.
	 *
	 * @return The desired id of layout container within the current window view hierarchy, into which
	 * should be views of all managed fragments placed or <code>-1</code> as default.
	 * @see #setFragmentContainerId(int)
	 */
	public int getFragmentContainerId() {
		return mFragmentContainerId;
	}

	/**
	 * Sets the factory for this dialog controller.
	 *
	 * @param factory The desired factory.
	 * @see #getFragmentFactory()
	 * @see #hasFragmentFactory()
	 */
	public void setFragmentFactory(@Nullable FragmentFactory factory) {
		this.mFactory = factory;
	}

	/**
	 * Returns the current fragment factory of this controller instance.
	 *
	 * @return Instance of fragment factory or <code>null</code> if there was no factory set.
	 * @see #setFragmentFactory(com.wit.android.support.fragment.manage.FragmentController.FragmentFactory)
	 * @see #hasFragmentFactory()
	 */
	@Nullable
	public FragmentFactory getFragmentFactory() {
		return mFactory;
	}

	/**
	 * Returns the top entry of the fragments back stack.
	 *
	 * @return The top back stack entry or <code>null</code> if there are no back stack entries.
	 */
	@Nullable
	public FragmentManager.BackStackEntry getTopBackStackEntry() {
		return mTopBackStackEntry;
	}

	/**
	 * Protected -----------------------------------------------------------------------------------
	 */

	/**
	 * Invoked to show the given fragment instance using the given options.
	 *
	 * @param fragment Fragment to show.
	 * @param options  Transaction options for the given fragment. If the given <var>options</var>
	 *                 are invalid, the default options will be used.
	 * @return <code>True</code> if showing was successful, <code>false</code> if there is already
	 * showing fragment with the same tag as specified within the options.
	 * @throws java.lang.IllegalStateException If the current id for layout container is invalid.
	 */
	protected boolean onShowFragment(@NonNull Fragment fragment, @Nullable TransactionOptions options) {
		if (options == null) {
			options = new TransactionOptions();
		}

		if (!options.replaceSame) {
			// Do not replace same fragment.
			Fragment currentFragment = mFragmentManager.findFragmentByTag(options.tag);
			if (currentFragment != null) {
				if (LOG_ENABLED) {
					Log.v(TAG, "Fragment with tag(" + options.tag + ") is already showing or within the back-stack.");
				}
				return false;
			}
		}

		// Check if we have place where the fragment should be placed.
		if (options.containerId <= 0) {
			if (mFragmentContainerId <= 0) {
				// No id provided for the layout where should be fragment
				// placed.
				throw new IllegalStateException("There is no id provided for the layout container into which should be requested fragment's view placed.");
			} else {
				options.containerId = mFragmentContainerId;
			}
		}

		final FragmentTransaction transaction = beginTransaction();

		// Apply animations to the transaction from the FragmentTransition parameter.
		if (options.transition != null && options.transition != FragmentTransition.NONE) {
			final FragmentTransition trans = options.transition;

			/**
			 * <pre>
			 * There are provided 4 animations:
			 * First two for currently incoming and outgoing fragment.
			 * Second two for incoming fragment from back stack and
			 * currently outgoing fragment.
			 * </pre>
			 */
			transaction.setCustomAnimations(
					trans.getInAnimResId(), trans.getOutAnimResId(),
					trans.getInAnimBackResId(), trans.getOutAnimBackResId()
			);
		}

		if (DEBUG_ENABLED) {
			Log.d(TAG, "onShowFragment() options = " + options.toString());
		}

		if (options.add) {
			transaction.add(options.containerId, fragment, options.tag);
		} else {
			transaction.replace(options.containerId, fragment, options.tag);
		}

		// Add fragment to back stack if requested.
		if (options.addToBackStack) {
			transaction.addToBackStack(fragment.getTag());
			if (DEBUG_ENABLED) {
				Log.d(TAG, "Fragment(" + fragment + ") added to back stack under the tag(" + fragment.getTag() + ").");
			}
		}
		return onCommitTransaction(transaction, options);
	}

	/**
	 * Invoked to finally commit created fragment transaction. Here passed transaction is already set
	 * upped according to the TransactionOptions.
	 * <p/>
	 * This implementation commits the passed <var>transaction</var> and in case that the <var>options</var>
	 * has set flag {@link com.wit.android.support.fragment.manage.FragmentController.TransactionOptions#showImmediate}
	 * to <code>true</code>, {@link android.support.v4.app.FragmentManager#executePendingTransactions()}
	 * will be invoked too on the attached FragmentManager.
	 *
	 * @param transaction Final fragment transaction to commit.
	 * @param options     Already processed transaction.
	 * @return Always returns <code>true</code>.
	 */
	protected boolean onCommitTransaction(@NonNull FragmentTransaction transaction, @NonNull TransactionOptions options) {
		// Commit transaction.
		transaction.commit();
		if (options.showImmediate) {
			mFragmentManager.executePendingTransactions();
		}
		return true;
	}

	/**
	 * Called to dispatch the back stack change.
	 *
	 * @param entriesCount The count of the fragment back stack entries.
	 * @param action       The back stack change action identifier.
	 */
	void dispatchBackStackChanged(int entriesCount, int action) {
		final boolean added = action == BackStackListener.ADDED;
		if (entriesCount > 0) {
			final FragmentManager.BackStackEntry entry = mFragmentManager.getBackStackEntryAt(entriesCount - 1);
			if (entry != null) {
				notifyBackStackEntryChange(mTopBackStackEntry = entry, added);
			}
		} else if (mTopBackStackEntry != null) {
			notifyBackStackEntryChange(mTopBackStackEntry, false);
			this.mTopBackStackEntry = null;
		}
	}

	/**
	 * Private -------------------------------------------------------------------------------------
	 */

	/**
	 * Performs showing of a fragment obtained from the current fragment factory.
	 *
	 * @param fragmentId The id of fragment from the current fragment factory to show.
	 * @param params     Parameters to be passed to the current factory by
	 *                   {@link com.wit.android.support.fragment.manage.FragmentController.FragmentFactory#createFragmentInstance(int, android.os.Bundle)}.
	 * @return <code>True</code> if showing was successful, <code>false</code> otherwise.
	 */
	private boolean performShowFragment(int fragmentId, Bundle params) {
		// First obtain fragment instance then fragment tag.
		Fragment fragment = mFactory.createFragmentInstance(fragmentId, params);
		if (fragment == null) {
			// Invalid fragment instance.
			Log.e(TAG, "No fragment instance provided by factory(" + mFactory.getClass().getSimpleName() + ") for the requested fragment id(" + fragmentId + ").");
			return false;
		}
		final boolean success = onShowFragment(fragment, mFactory.getFragmentTransactionOptions(fragmentId, params));
		return success && notifyFragmentChanged(fragmentId, fragment.getTag(), true);
	}

	/**
	 * Performs showing of the given fragment instance using the given options.
	 *
	 * @param fragment Fragment to show.
	 * @param options  Show options for the given fragment.
	 * @return <code>True</code> if showing was successful, <code>false</code> otherwise.
	 */
	private boolean performShowFragment(Fragment fragment, TransactionOptions options) {
		final boolean success = onShowFragment(fragment, options);
		return success && notifyFragmentChanged(fragment.getId(), fragment.getTag(), false);
	}

	/**
	 * Checks whether there is factory available and if so, if a fragment for the specified <var>fragmentId</var>
	 * is provided by that factory.
	 *
	 * @param fragmentId The id of requested fragment.
	 * @return <code>True</code> if the current fragment factory provides such a fragment, <code>false</code>
	 * otherwise.
	 * @throws java.lang.IllegalStateException If the current factory isn't available.
	 */
	private boolean checkFragmentFactory(int fragmentId) {
		if (mFactory == null) {
			throw new IllegalStateException("No fragment factory found.");
		}
		return mFactory.isFragmentProvided(fragmentId);
	}

	/**
	 * Called to notify, that the given <var>changedEntry</var> was added or removed from the back stack.
	 *
	 * @param changedEntry The back stack entry which was changed.
	 * @param added        <code>True</code> if the specified entry was added to the back stack,
	 *                     <code>false</code> if was removed.
	 */
	private void notifyBackStackEntryChange(FragmentManager.BackStackEntry changedEntry, boolean added) {
		this.mCurrentFragmentTag = changedEntry.getName();
		if (mBackStackListener != null) {
			// Dispatch to listener.
			mBackStackListener.onFragmentsBackStackChanged(added, changedEntry.getId(), mCurrentFragmentTag);
		}
	}

	/**
	 * Called to notify, that there was fragment with the given id and tag currently changed, so replaces
	 * the old one.
	 *
	 * @param id      The id of the currently changed (showed) fragment.
	 * @param tag     The tag of the currently changed (showed) fragment.
	 * @param factory <code>True</code> if the changed fragment was obtained from a factory,
	 *                <code>false</code> otherwise.
	 */
	private boolean notifyFragmentChanged(int id, String tag, boolean factory) {
		this.mCurrentFragmentTag = tag;
		if (mFragmentListener != null) {
			mFragmentListener.onFragmentChanged(id, mCurrentFragmentTag, factory);
		}
		return true;
	}

	/**
	 * Inner classes ===============================================================================
	 */

	/**
	 * <h4>Class Overview</h4>
	 * todo: description
	 * <h4>Default SetUp:</h4>
	 * <ul>
	 * <li>tag: {@link com.wit.android.support.fragment.manage.FragmentController#FRAGMENT_TAG}</li>
	 * <li>transition: {@link FragmentTransition#NONE}</li>
	 * <li>container id: <b>-1</b></li>
	 * <li>back-stacking: <b>false</b></li>
	 * <li>replacing same: <b>true</b></li>
	 * <li>showing immediately: <b>false</b></li>
	 * </ul>
	 *
	 * @author Martin Albedinsky
	 * @see com.wit.android.support.fragment.manage.FragmentController
	 * @see com.wit.android.support.fragment.manage.FragmentController.FragmentFactory
	 */
	public static class TransactionOptions implements Parcelable {
		/**
		 * Members =================================================================================
		 */

		/**
		 * Creator used to create an instance or array of instances of TransactionOptions from {@link android.os.Parcel}.
		 */
		public static final Creator<TransactionOptions> CREATOR = new Creator<TransactionOptions>() {
			/**
			 */
			@Override
			public TransactionOptions createFromParcel(Parcel source) {
				return new TransactionOptions(source);
			}

			/**
			 */
			@Override
			public TransactionOptions[] newArray(int size) {
				return new TransactionOptions[size];
			}
		};

		/**
		 * Tag for fragment.
		 * <p/>
		 * Default value: <b>{@link #FRAGMENT_TAG}</b>
		 */
		protected String tag = FRAGMENT_TAG;

		/**
		 * Show direction.
		 */
		protected FragmentTransition transition = FragmentTransition.NONE;

		/**
		 * Fragment container layout id.
		 */
		protected int containerId = -1;

		/**
		 * Flag indicating, whether fragment should be added to back stack or not.
		 */
		protected boolean addToBackStack = false;

		/**
		 * Flag indicating, whether a same fragment (currently showing) can be replaced by a new one
		 * with this options containing same tag or not.
		 */
		protected boolean replaceSame = true;

		/**
		 * Flag indicating, whether a new fragment should be showed immediately or not.
		 */
		protected boolean showImmediate = false;

		/**
		 * Flag indicating, whether to add a new fragment or replace old one.
		 */
		protected boolean add;

		/**
		 * Constructors ============================================================================
		 */

		/**
		 * Creates a new instance of default TransactionOptions.
		 */
		public TransactionOptions() {
		}

		/**
		 * Called form {@link #CREATOR} to create an instance of TransactionOptions form the given
		 * parcel <var>source</var>.
		 *
		 * @param source Parcel with data for a new instance.
		 */
		protected TransactionOptions(Parcel source) {
			this.containerId = source.readInt();
			this.tag = source.readString();
			this.addToBackStack = source.readInt() == 1;
			this.replaceSame = source.readInt() == 1;
			this.showImmediate = source.readInt() == 1;
			this.add = source.readInt() == 1;
			this.transition = source.readParcelable(FragmentTransition.class.getClassLoader());
		}

		/**
		 * Methods =================================================================================
		 */

		/**
		 */
		@Override
		public void writeToParcel(Parcel dest, int flags) {
			dest.writeInt(containerId);
			dest.writeString(tag);
			dest.writeInt(addToBackStack ? 1 : 0);
			dest.writeInt(replaceSame ? 1 : 0);
			dest.writeInt(showImmediate ? 1 : 0);
			dest.writeInt(add ? 1 : 0);
			transition.writeToParcel(dest, flags);
		}

		/**
		 */
		@Override
		public int describeContents() {
			return 0;
		}

		/**
		 */
		@Override
		public String toString() {
			final StringBuilder builder = new StringBuilder("");
			builder.append("[tag(");
			builder.append(tag);
			builder.append("), ");
			builder.append(" transition(");
			builder.append(transition != null ? transition.name() : "null");
			builder.append("), ");
			builder.append(" backStacked(");
			builder.append(addToBackStack);
			builder.append("), ");
			builder.append(" replace(");
			builder.append(replaceSame);
			builder.append("), ");
			builder.append(" container(");
			builder.append(containerId);
			builder.append("), ");
			builder.append(" add(");
			builder.append(add);
			builder.append(")]");
			return builder.toString();
		}

		/**
		 * Sets a flag indicating, whether to add or replace fragment to <code>true</code> so a new
		 * fragment will be showed using {@link android.support.v4.app.FragmentTransaction#add(int, android.support.v4.app.Fragment, String)}.
		 *
		 * @return This options instance.
		 */
		public TransactionOptions add() {
			this.add = true;
			return this;
		}

		/**
		 * Sets a flag indicating, whether to add or replace fragment to <code>false</code> so an old
		 * fragment will be replaced by a new one using {@link android.support.v4.app.FragmentTransaction#replace(int, android.support.v4.app.Fragment, String)}.
		 *
		 * @return This options instance.
		 */
		public TransactionOptions replace() {
			this.add = false;
			return this;
		}

		/**
		 * Sets a tag for the fragment to be showed.
		 *
		 * @param fragmentTag The desired fragment tag.
		 * @return This options instance.
		 */
		public TransactionOptions tag(String fragmentTag) {
			this.tag = fragmentTag;
			return this;
		}

		/**
		 * Sets a flag indicating, whether fragment should be added to the fragments back stack or not.
		 *
		 * @param add <code>True</code> to add fragment to the back stack, <code>false</code> otherwise.
		 * @return This options instance.
		 */
		public TransactionOptions addToBackStack(boolean add) {
			this.addToBackStack = add;
			return this;
		}

		/**
		 * Sets a transition used to animate fragment views change.
		 *
		 * @param transition Transition with animations.
		 * @return This options instance.
		 * @see com.wit.android.support.fragment.manage.FragmentTransition
		 */
		public TransactionOptions transition(FragmentTransition transition) {
			this.transition = transition;
			return this;
		}

		/**
		 * Sets an id of the layout container into which should be a new fragment's view placed.
		 * <p/>
		 * <b>Note</b>, that this id will be used only for this options.
		 *
		 * @param layoutId An id of the desired layout container to be used as container for fragment's
		 *                 view.
		 * @return This options instance.
		 * @see com.wit.android.support.fragment.manage.FragmentController#setFragmentContainerId(int)
		 */
		public TransactionOptions containerId(int layoutId) {
			this.containerId = layoutId;
			return this;
		}

		/**
		 * Sets a flag indicating, whether the currently showing fragment with the same TAG can be
		 * replaced by a new one (using this options) or not.
		 *
		 * @param replace <code>True</code> to replace a fragment with the same TAG as specified here,
		 *                with a new one, <code>false</code> otherwise.
		 * @return This options instance.
		 */
		public TransactionOptions replaceSame(boolean replace) {
			this.replaceSame = replace;
			return this;
		}

		/**
		 * Sets a flag indicating, whether a new fragment should be showed immediately or not.
		 *
		 * @param immediate <code>True</code> to show immediately, <code>false</code> otherwise.
		 * @return This options instance.
		 */
		public TransactionOptions showImmediate(boolean immediate) {
			this.showImmediate = immediate;
			return this;
		}
	}

	/**
	 * Fragments back stack inner implementation.
	 */
	private final class BackStackListener implements FragmentManager.OnBackStackChangedListener {

		/**
		 * Constants ===============================================================================
		 */

		/**
		 * Flag to indicate, that fragment was added to the back stack.
		 */
		static final int ADDED = 0x00;

		/**
		 * Flag to indicate, that fragment was removed from the back stack.
		 */
		static final int REMOVED = 0x01;

		/**
		 * Members =================================================================================
		 */

		/**
		 * Current size of the fragments back stack.
		 */
		int currentCount = 0;

		/**
		 * Methods =================================================================================
		 */

		/**
		 */
		@Override
		public void onBackStackChanged() {
			final int n = mFragmentManager.getBackStackEntryCount();
			if (n >= 0 && n != currentCount) {
				dispatchBackStackChanged(n, n > currentCount ? ADDED : REMOVED);
				this.currentCount = n;
			}
		}
	}
}
