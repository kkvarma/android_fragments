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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.wit.android.support.fragment.config.FragmentsConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * <h4>Class Overview</h4>
 * <p>
 * </p>
 *
 * @author Martin Albedinsky
 * @see com.wit.android.support.fragment.manage.FragmentController.FragmentFactory
 * @see com.wit.android.support.fragment.manage.FragmentController.ShowOptions
 */
public class FragmentController {

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * <p>
	 * Default tag for fragments.
	 * </p>
	 */
	public static final String FRAGMENT_TAG = "com.wit.android.support.fragment.manage.FragmentController.Fragment.TAG";

	/**
	 * Log TAG.
	 */
	private static final String TAG = FragmentController.class.getSimpleName();

	/**
	 * Flag indicating whether the debug output trough log-cat is enabled or not.
	 */
	private static final boolean DEBUG_ENABLED = FragmentsConfig.LIBRARY_DEBUG_LOG_ENABLED;

	/**
	 * Flag indicating whether the output trough log-cat is enabled or not.
	 */
	private static final boolean LOG_ENABLED = FragmentsConfig.LIBRARY_LOG_ENABLED;

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
	 * Fragment manager to handle showing, obtaining and hiding fragments.
	 */
	private FragmentManager mFragmentManager = null;

	/**
	 * Fragment factory which provides fragment instances to show (manage) by this controller.
	 */
	private FragmentFactory mFragmentFactory = null;

	/**
	 * Id of a layout container within the current window view hierarchy, into which will be view of
	 * all managed fragments placed.
	 */
	private int mFragmentContainerId = -1;

	/**
	 * The entry at the top of the fragments back stack.
	 */
	private FragmentManager.BackStackEntry mTopBackStackEntry = null;

	/**
	 * Listener callback for back stack changes.
	 */
	private OnBackStackChangeListener mBackStackListener;

	/**
	 * Listener callback for fragment changes.
	 */
	private OnFragmentChangeListener mFragmentListener;

	/**
	 * Arrays --------------------------------------------------------------------------------------
	 */

	/**
	 * Booleans ------------------------------------------------------------------------------------
	 */

	/**
	 * Constructors ================================================================================
	 */

	/**
	 * <p>
	 * Creates a new instance of FragmentController within the given <var>parentFragment</var>'s context.
	 * </p>
	 *
	 * @param parentFragment The fragment in which will be this manager used.
	 * @see #FragmentController(android.support.v4.app.FragmentActivity)
	 */
	public FragmentController(Fragment parentFragment) {
		this(parentFragment.getFragmentManager());
		if (parentFragment instanceof OnBackStackChangeListener) {
			setOnBackStackChangeListener((OnBackStackChangeListener) parentFragment);
		}
		if (parentFragment instanceof OnFragmentChangeListener) {
			setOnFragmentChangeListener((OnFragmentChangeListener) parentFragment);
		}
	}

	/**
	 * <p>
	 * Creates a new instance of FragmentController within the given <var>parentActivity</var>'s context.
	 * </p>
	 *
	 * @param parentActivity The activity in which will be this manager used.
	 * @see #FragmentController(android.support.v4.app.Fragment)
	 */
	public FragmentController(FragmentActivity parentActivity) {
		this(parentActivity.getSupportFragmentManager());
		if (parentActivity instanceof OnBackStackChangeListener) {
			setOnBackStackChangeListener((OnBackStackChangeListener) parentActivity);
		}
		if (parentActivity instanceof OnFragmentChangeListener) {
			setOnFragmentChangeListener((OnFragmentChangeListener) parentActivity);
		}
	}

	/**
	 * <p>
	 * Creates a new instance of FragmentController with the given <var>fragmentManager</var>.
	 * </p>
	 *
	 * @param fragmentManager Fragment manager to manage dialog fragments.
	 * @throws java.lang.NullPointerException If the given fragment manager is <code>null</code>.
	 * @see #FragmentController(android.support.v4.app.FragmentActivity)
	 * @see #FragmentController(android.support.v4.app.Fragment)
	 */
	public FragmentController(FragmentManager fragmentManager) {
		if ((mFragmentManager = fragmentManager) == null) {
			throw new NullPointerException("Invalid fragment manager.");
		}
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
	 * <p>
	 * Same as {@link #showFragment(int, android.os.Bundle)} without params.
	 * </p>
	 */
	public boolean showFragment(int fragmentId) {
		return showFragment(fragmentId, null);
	}

	/**
	 * <p>
	 * Shows a fragment instance provided by the current factory represented by the specified
	 * <var>fragmentId</var>.
	 * </p>
	 *
	 * @param fragmentId The id of fragment from the current fragment factory to show.
	 * @param params     Parameters to be passed to the current factory by
	 *                   {@link com.wit.android.support.fragment.manage.FragmentController.FragmentFactory#createFragmentInstance(int, android.os.Bundle)}.
	 * @return <code>True</code> if transaction for the requested fragment was successfully created
	 * and committed or if fragment is currently being visible and should not be replaced by the new
	 * one, <code>false</code> otherwise (like the current factory doesn't provides requested fragment
	 * or some error occurs).
	 * @throws IllegalStateException If there is no factory currently available.
	 * @see #hasFactory()
	 */
	public boolean showFragment(int fragmentId, Bundle params) {
		// Check if we have fragment factory and fragment is provided.
		if (!this.checkFragmentFactory(fragmentId)) {
			Log.e(TAG, "Current factory does not provides fragment for the requested id(" + fragmentId + ").");
			return false;
		}
		return this.performShowFragment(fragmentId, params);
	}

	/**
	 * <p>
	 * Same as {@link #showFragment(android.support.v4.app.Fragment, com.wit.android.support.fragment.manage.FragmentController.ShowOptions)}
	 * with default {@link com.wit.android.support.fragment.manage.FragmentController.ShowOptions}.
	 * </p>
	 */
	public boolean showFragment(Fragment fragment) {
		return showFragment(fragment, new ShowOptions());
	}

	/**
	 * <p>
	 * Shows the given fragment instance using the given options.
	 * </p>
	 *
	 * @param fragment The instance of fragment to show.
	 * @param options  Show options.
	 * @return <code>True</code> if transaction for the requested fragment was successfully created
	 * and committed or if fragment is currently being shown and should not be replaced by the new one,
	 * <code>false</code> otherwise.
	 */
	public boolean showFragment(Fragment fragment, ShowOptions options) {
		return this.performShowFragment(fragment, options);
	}

	/**
	 * <p>
	 * Same as {@link android.support.v4.app.FragmentManager#findFragmentById(int)}.
	 * </p>
	 *
	 * @return Found an instance of fragment or <code>null</code> if there is no fragment with the
	 * specified id within the current fragment manager.
	 * @see #findFragmentByTag(String)
	 * @see #findFactoryFragmentById(int)
	 */
	public Fragment findFragmentById(int fragmentId) {
		return mFragmentManager.findFragmentById(fragmentId);
	}

	/**
	 * <p>
	 * Same as {@link #findFragmentByTag(String)}, where fragment tag will be requested from the
	 * current fragment factory by the specified <var>fragmentId</var>.
	 * </p>
	 *
	 * @param fragmentId The id of fragment from the current factory to obtain.
	 * @throws IllegalStateException If there is no factory currently available.
	 * @see #hasFactory()
	 */
	public Fragment findFactoryFragmentById(int fragmentId) {
		// Check if we have fragment factory.
		return this.checkFragmentFactory(fragmentId) ? findFragmentByTag(mFragmentFactory.getFragmentTag(fragmentId)) : null;
	}

	/**
	 * <p>
	 * Same as {@link android.support.v4.app.FragmentManager#findFragmentByTag(String)}.
	 * </p>
	 *
	 * @return Found an instance of fragment or <code>null</code> if there is no fragment with the
	 * specified tag within the current fragment manager.
	 * @see #findFragmentById(int)
	 */
	public Fragment findFragmentByTag(String fragmentTag) {
		return mFragmentManager.findFragmentByTag(fragmentTag);
	}

	/**
	 * <p>
	 * Returns an instance of fragment which is currently being shown on the screen. To determine
	 * visible fragment, these fragment's flags are checked:
	 * <ul>
	 * <li>{@link android.support.v4.app.Fragment#isVisible()}</li>
	 * <li>or {@link android.support.v4.app.Fragment#isAdded()}</li>
	 * </ul>
	 * </p>
	 * <p>
	 * This will be actually the instance of fragment which was showed as last.
	 * </p>
	 *
	 * @return The instance of currently visible or added fragment, or <code>null</code> if there
	 * are no fragments (<code>n = 0</code>) within the current fragment manager or all checked fragments
	 * are in the undesirable state.
	 * @see #getVisibleSecondFragment()
	 */
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
	 * <p>
	 * Returns an instance of second fragment which is currently being shown on the screen, where two
	 * fragments are visible at the same time. To determine visible second fragment, these fragment's
	 * flags are checked:
	 * <ul>
	 * <li>{@link android.support.v4.app.Fragment#isVisible()}</li>
	 * <li>or {@link android.support.v4.app.Fragment#isAdded()}</li>
	 * </ul>
	 * </p>
	 * <p>
	 * This will be actually the instance of fragment which was showed before the last one.
	 * </p>
	 *
	 * @return The instance of currently visible or added second fragment, or <code>null</code> if there
	 * are no fragments (<code>n > 1</code>) within the current fragment manager or all checked fragments
	 * are in the undesirable state.
	 * @see #getVisibleSecondFragment()
	 */
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
	 * <p>
	 * Same as {@link android.support.v4.app.FragmentManager#popBackStack()}.
	 * </p>
	 */
	public void hideVisibleFragment() {
		mFragmentManager.popBackStack();
	}

	/**
	 * <p>
	 * Same as {@link android.support.v4.app.FragmentManager#popBackStackImmediate()}.
	 * </p>
	 */
	public boolean hideVisibleFragmentImmediate() {
		return mFragmentManager.popBackStackImmediate();
	}

	/**
	 * <p>
	 * Same as {@link #showFragmentOptionsMenu(String, boolean)}, where fragment tag will be requested
	 * form the current fragment factory by the specified <var>fragmentId</var>.
	 * </p>
	 *
	 * @param fragmentId The id of fragment from the current fragment factory, of which options to
	 *                   show/hide.
	 */
	public boolean showFragmentOptionsMenu(int fragmentId, boolean enable) {
		// Check if we have fragment factory.
		return this.checkFragmentFactory(fragmentId) && showFragmentOptionsMenu(mFragmentFactory.getFragmentTag(fragmentId), enable);
	}

	/**
	 * <p>
	 * Shows/hides options menu for fragment by calling {@link android.support.v4.app.Fragment#setHasOptionsMenu(boolean)}.
	 * </p>
	 *
	 * @param fragmentTag The tag of fragment, of which options to show/hide.
	 * @param show        <code>True</code> to show options menu, <code>false</code> to hide options menu.
	 * @return <code>True</code> if fragment was found and request to show/hide its options menu was
	 * provided, <code>false</code> otherwise.
	 * @see #showFragmentOptionsMenu(int, boolean)
	 */
	public boolean showFragmentOptionsMenu(String fragmentTag, boolean show) {
		final Fragment fragment = mFragmentManager.findFragmentByTag(fragmentTag);
		if (fragment != null) {
			fragment.setHasOptionsMenu(show);
			return true;
		}
		return false;
	}

	/**
	 * <p>
	 * Begins a new {@link android.support.v4.app.FragmentTransaction}. Same as
	 * {@link android.support.v4.app.FragmentManager#beginTransaction()}.
	 * </p>
	 *
	 * @return Fragment transaction.
	 */
	@SuppressLint("CommitTransaction")
	public FragmentTransaction beginTransaction() {
		return mFragmentManager.beginTransaction();
	}

	/**
	 * <p>
	 * Returns flag indicating whether this controller has some fragment factory or not
	 * </p>
	 *
	 * @return <code>True</code> if the current fragment factory is available, <code>false</code>
	 * otherwise.
	 */
	public boolean hasFactory() {
		return mFragmentFactory != null;
	}

	/**
	 * <p>
	 * Returns flag indicating whether there are some fragments within the fragment manager's back
	 * stack.
	 * </p>
	 *
	 * @return <code>True</code> if fragment manager's back stack holds some entries, <code>false</code>
	 * otherwise.
	 * @see android.support.v4.app.FragmentManager#getBackStackEntryCount()
	 */
	public boolean hasBackStackEntries() {
		return mFragmentManager.getBackStackEntryCount() > 0;
	}

	/**
	 * <p>
	 * Clears fragments back stack by calling {@link android.support.v4.app.FragmentManager#popBackStack()}
	 * in loop of size obtained by {@link android.support.v4.app.FragmentManager#getBackStackEntryCount()}.
	 * </p>
	 * <p>
	 * <b>Note</b>, that {@link android.support.v4.app.FragmentManager#popBackStack()} is an asynchronous
	 * call, so the fragments back stack can be cleared in the feature not immediately.
	 * </p>
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
	 * <p>
	 * Like {@link #clearBackStack()}, but this will call {@link android.support.v4.app.FragmentManager#popBackStackImmediate()}.
	 * </p>
	 * <p>
	 * <b>Note</b>, that {@link android.support.v4.app.FragmentManager#popBackStackImmediate()} is a
	 * synchronous call, so the fragments back stack will be popped immediately within this call. If
	 * there is too many fragments, this can take some time.
	 * </p>
	 *
	 * @return <code>True</code> if there was at least one fragment popped, <code>false</code> otherwise.
	 */
	public boolean clearBackStackImmediate() {
		final int n = mFragmentManager.getBackStackEntryCount();
		if (n > 0) {
			boolean popped = false;
			for (int i = 0; i < n; i++) {
				if (!popped) {
					popped = mFragmentManager.popBackStackImmediate();
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
	 * <p>
	 * Registers a callback to be invoked when some change occur in the fragments back stack.
	 * </p>
	 *
	 * @param listener Listener callback.
	 */
	public void setOnBackStackChangeListener(OnBackStackChangeListener listener) {
		this.mBackStackListener = listener;
	}

	/**
	 * <p>
	 * Registers a callback to be invoked when fragments are being changed.
	 * </p>
	 *
	 * @param listener Listener callback.
	 */
	public void setOnFragmentChangeListener(OnFragmentChangeListener listener) {
		this.mFragmentListener = listener;
	}

	/**
	 * <p>
	 * </p>
	 */
	public void removeOnFragmentChangeListener() {
		this.mBackStackListener = null;
	}

	/**
	 * <p>
	 * Returns the tag of the currently visible fragment.
	 * </p>
	 *
	 * @return Fragment tag or <code>null</code> if there is not currently visible any fragment.
	 * @see #getVisibleFragment()
	 * @see #getVisibleSecondFragmentTag()
	 */
	public String getVisibleFragmentTag() {
		final Fragment visibleFragment = getVisibleFragment();
		return (visibleFragment != null) ? visibleFragment.getTag() : null;
	}

	/**
	 * <p>
	 * Returns the tag of the second currently visible fragment.
	 * </p>
	 *
	 * @return Fragment tag or <code>null</code> if there is only one or none currently visible fragment.
	 * @see #getVisibleSecondFragment()
	 * @see #getVisibleFragmentTag()
	 */
	public String getVisibleSecondFragmentTag() {
		final Fragment visibleFragment = getVisibleSecondFragment();
		return (visibleFragment != null) ? visibleFragment.getTag() : null;
	}

	/**
	 * <p>
	 * Returns an instance of the current support fragment manager.
	 * </p>
	 *
	 * @return Current fragment manager, or <code>null</code> if there is no fragment manager available.
	 */
	public FragmentManager getFragmentManager() {
		return mFragmentManager;
	}

	/**
	 * <p>
	 * Sets an id of layout container for fragment views.
	 * </p>
	 *
	 * @param layoutId The id of a layout container within the current window view hierarchy, into
	 *                 which will be view of all managed fragments placed.
	 * @see #getFragmentContainerId()
	 */
	public void setFragmentContainerId(int layoutId) {
		this.mFragmentContainerId = layoutId;
	}

	/**
	 * <p>
	 * Returns an id of layout container for fragment views.
	 * </p>
	 *
	 * @return The id of a layout container within the current window view hierarchy, into which will
	 * be view of all managed fragments placed or <code>-1</code> as default.
	 * @see #setFragmentContainerId(int)
	 */
	public int getFragmentContainerId() {
		return mFragmentContainerId;
	}

	/**
	 * <p>
	 * Sets the fragment factory for this fragment controller instance, which will be used to obtain
	 * instances of fragments requested by this controller by specific <b>fragmentId</b>.
	 * </p>
	 *
	 * @param factory Fragment factory to provide fragment instances, show options and fragment tags.
	 * @see #getFragmentFactory()
	 * @see #hasFactory()
	 */
	public void setFragmentFactory(FragmentFactory factory) {
		this.mFragmentFactory = factory;
	}

	/**
	 * <p>
	 * Returns the current fragment factory.
	 * </p>
	 *
	 * @return Current fragment factory, or <code>null</code> if there is no factory available.
	 * @see #setFragmentFactory(com.wit.android.support.fragment.manage.FragmentController.FragmentFactory)
	 * @see #hasFactory()
	 */
	public FragmentFactory getFragmentFactory() {
		return mFragmentFactory;
	}

	/**
	 * <p>
	 * Returns the top entry of the fragments back stack.
	 * </p>
	 *
	 * @return The top back stack entry or <code>null</code> if there are no back stack entries.
	 */
	public FragmentManager.BackStackEntry getTopBackStackEntry() {
		return mTopBackStackEntry;
	}

	/**
	 * Protected -----------------------------------------------------------------------------------
	 */

	/**
	 * <p>
	 * Invoked to show the given fragment instance using the given options.
	 * </p>
	 *
	 * @param fragment Fragment to show.
	 * @param options  Show options for the given fragment. If the given <var>options</var> are invalid,
	 *                 a new default options will be created.
	 * @return <code>True</code> if showing was successful, <code>false</code> otherwise.
	 * This implementation always returns <code>true</code> or throws exception.
	 * @throws java.lang.IllegalStateException If the current id for fragment layout container is invalid.
	 */
	protected boolean onShowFragment(Fragment fragment, ShowOptions options) {
		if (options == null) {
			options = new ShowOptions();
		}

		if (!options.replaceSame) {
			// Do not replace same fragment.
			Fragment currentFragment = mFragmentManager.findFragmentByTag(options.tag);
			if (currentFragment != null) {
				if (LOG_ENABLED) {
					Log.v(TAG, "Fragment with tag(" + options.tag + ") is already showing or within the back-stack.");
				}
				return true;
			}
		}

		// Check if we have place where the fragment should be placed.
		if (options.containerId == -1) {
			if (mFragmentContainerId == -1) {
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

		// Fragment's view replaces the view of the current fragment container layout.
		transaction.replace(options.containerId, fragment, options.tag);

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
	 * <p>
	 * Invoked to finally commit created fragment transaction. Here passed transaction is already set
	 * upped according to the show options.
	 * </p>
	 * <p>
	 * This implementation commits the passed <var>transaction</var> and in case that the <var>options</var>
	 * has set flag {@link com.wit.android.support.fragment.manage.FragmentController.ShowOptions#showImmediately}
	 * to <code>true</code>, {@link android.support.v4.app.FragmentManager#executePendingTransactions()}
	 * will be invoked too.
	 * </p>
	 *
	 * @param transaction Final transaction to commit.
	 * @param options     Already processed show options.
	 * @return This implementation always returns <code>true</code>.
	 */
	protected boolean onCommitTransaction(FragmentTransaction transaction, ShowOptions options) {
		// Commit transaction.
		transaction.commit();
		if (options.showImmediately) {
			mFragmentManager.executePendingTransactions();
		}
		return true;
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
		Fragment fragment = mFragmentFactory.createFragmentInstance(fragmentId, params);
		if (fragment == null) {
			// Invalid fragment instance.
			Log.e(TAG, "No such fragment instance for the requested fragment id(" + fragmentId + "). Please check your fragment factory.");
			return false;
		}
		final boolean success = onShowFragment(fragment, mFragmentFactory.getFragmentShowOptions(fragmentId, params));
		return success && dispatchFragmentChanged(fragmentId, fragment.getTag(), true);
	}

	/**
	 * Performs showing of the given fragment instance using the given options.
	 *
	 * @param fragment Fragment to show.
	 * @param options  Show options for the given fragment.
	 * @return <code>True</code> if showing was successful, <code>false</code> otherwise.
	 */
	private boolean performShowFragment(Fragment fragment, ShowOptions options) {
		final boolean success = onShowFragment(fragment, options);
		return success && dispatchFragmentChanged(fragment.getId(), fragment.getTag(), false);
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
		if (mFragmentFactory == null) {
			throw new IllegalStateException("No fragment factory found.");
		}
		return mFragmentFactory.isFragmentProvided(fragmentId);
	}

	/**
	 * Called to dispatch back stack change.
	 *
	 * @param entriesCount The count of the fragment back stack entries.
	 * @param action       The back stack change action identifier.
	 */
	private void dispatchBackStackChanged(int entriesCount, int action) {
		final boolean added = action == BackStackListener.ADDED;
		if (entriesCount > 0) {
			final FragmentManager.BackStackEntry entry = mFragmentManager.getBackStackEntryAt(entriesCount - 1);
			if (entry != null) {
				dispatchBackStackEntryChange(mTopBackStackEntry = entry, added);
			}
		} else if (mTopBackStackEntry != null) {
			dispatchBackStackEntryChange(mTopBackStackEntry, false);
			this.mTopBackStackEntry = null;
		}
	}

	/**
	 * Called to dispatch back stack entry change.
	 *
	 * @param changedEntry The back stack entry which was changed.
	 * @param added        <code>True</code> if the specified entry was added to the back stack,
	 *                     <code>false</code> if was removed.
	 */
	private void dispatchBackStackEntryChange(FragmentManager.BackStackEntry changedEntry, boolean added) {
		if (mBackStackListener != null) {
			// Dispatch to listener.
			mBackStackListener.onBackStackChanged(added, changedEntry.getId(), changedEntry.getName());
		}
	}

	/**
	 * Called to dispatch fragment change.
	 *
	 * @param id      The id of the currently changed (showed) fragment.
	 * @param tag     The tag of the currently changed (showed) fragment.
	 * @param factory <code>True</code> if the changed fragment was obtained from a factory,
	 *                <code>false</code> otherwise.
	 */
	private boolean dispatchFragmentChanged(int id, String tag, boolean factory) {
		if (mFragmentListener != null) {
			mFragmentListener.onFragmentChanged(id, tag, factory);
		}
		return true;
	}

	/**
	 * Abstract methods ----------------------------------------------------------------------------
	 */

	/**
	 * Inner classes ===============================================================================
	 */

	/**
	 * <h4>Class Overview</h4>
	 * <p>
	 * Description.
	 * </p>
	 * <h4>Default SetUp:</h4>
	 * <ul>
	 * <li>tag: {@link com.wit.android.support.fragment.manage.FragmentController#FRAGMENT_TAG}</li>
	 * <li>show direction: {@link FragmentTransition#NONE}</li>
	 * <li>container id: <b>-1</b></li>
	 * <li>back-stacking: <b>false</b></li>
	 * <li>replacing same: <b>true</b></li>
	 * <li>showing immediately: <b>false</b></li>
	 * <li>showing options menu: <b>false</b></li>
	 * </ul>
	 *
	 * @author Martin Albedinsky
	 * @see com.wit.android.support.fragment.manage.FragmentController
	 * @see com.wit.android.support.fragment.manage.FragmentController.FragmentFactory
	 */
	public static class ShowOptions implements Parcelable {
		/**
		 * Members ================================================================================
		 */

		/**
		 * <p>
		 * Parcelable creator.
		 * </p>
		 */
		public static final Creator<ShowOptions> CREATOR = new Creator<ShowOptions>() {
			@Override
			public ShowOptions createFromParcel(Parcel source) {
				return new ShowOptions(source);
			}

			@Override
			public ShowOptions[] newArray(int size) {
				return new ShowOptions[size];
			}
		};

		/**
		 * Tag of fragment.
		 */
		protected String tag = FRAGMENT_TAG;

		/**
		 * Show direction.
		 */
		protected FragmentTransition transition = FragmentTransition.NONE;

		/**
		 * Container layout id.
		 */
		protected int containerId = -1;

		/**
		 * Flag indicating, whether fragment should be added to back stack or not.
		 */
		protected boolean addToBackStack = false;

		/**
		 * Flag indicating, whether same fragment (currently showing) can be replaced by the new one
		 * with this options containing same tag or not.
		 */
		protected boolean replaceSame = true;

		/**
		 * Flag indicating, whether fragment should be showed immediately or not.
		 */
		protected boolean showImmediately = false;

		/**
		 * Constructors ============================================================================
		 */

		/**
		 * <p>
		 * Creates a new instance of default ShowOptions.
		 * </p>
		 */
		public ShowOptions() {
		}

		/**
		 * <p>
		 * Called by {@link #CREATOR}.
		 * </p>
		 *
		 * @param input Parcelable source with saved data.
		 */
		protected ShowOptions(Parcel input) {
			this.containerId = input.readInt();
			this.tag = input.readString();
			this.addToBackStack = input.readInt() == 1;
			this.replaceSame = input.readInt() == 1;
			this.showImmediately = input.readInt() == 1;
			this.transition = FragmentTransition.CREATOR.createFromParcel(input);
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
			dest.writeInt(showImmediately ? 1 : 0);
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
			builder.append(transition.name());
			builder.append("), ");
			builder.append(" backStacked(");
			builder.append(addToBackStack);
			builder.append("), ");
			builder.append(" replace(");
			builder.append(replaceSame);
			builder.append("), ");
			builder.append(" container(");
			builder.append(containerId);
			builder.append(")]");
			return builder.toString();
		}

		/**
		 * <p>
		 * Sets the tag for fragment.
		 * </p>
		 *
		 * @param fragmentTag Fragment tag.
		 * @return This options.
		 */
		public ShowOptions tag(String fragmentTag) {
			this.tag = fragmentTag;
			return this;
		}

		/**
		 * <p>
		 * Sets flag indicating, whether fragment should be added to back stack or not.
		 * </p>
		 *
		 * @param add <code>True</code> to add fragment to back stack, <code>false</code> otherwise.
		 * @return This options.
		 */
		public ShowOptions addToBackStack(boolean add) {
			this.addToBackStack = add;
			return this;
		}

		/**
		 * <p>
		 * Sets the show transition for fragment.
		 * </p>
		 *
		 * @param transition Show transition.
		 * @return This options.
		 * @see com.wit.android.support.fragment.manage.FragmentTransition
		 */
		public ShowOptions transition(FragmentTransition transition) {
			this.transition = transition;
			return this;
		}

		/**
		 * <p>
		 * Sets the id of a layout container into which should be the fragment's view placed.
		 * </p>
		 * <p>
		 * <b>Note</b>, that this container id will be used only for this options.
		 * </p>
		 *
		 * @param layoutId The id of layout container.
		 * @return This options.
		 * @see com.wit.android.support.fragment.manage.FragmentController#setFragmentContainerId(int)
		 */
		public ShowOptions containerId(int layoutId) {
			this.containerId = layoutId;
			return this;
		}

		/**
		 * <p>
		 * Sets flag indicating, whether the currently showing fragment with same tag can be replaced
		 * by the new one (using this options) or not.
		 * </p>
		 *
		 * @param replace <code>True</code> to replace same fragment with the new one, <code>false</code> otherwise.
		 * @return This options.
		 */
		public ShowOptions replaceSame(boolean replace) {
			this.replaceSame = replace;
			return this;
		}

		/**
		 * <p>
		 * Sets flag indicating, whether fragment should be showed immediately or not.
		 * </p>
		 *
		 * @param immediately <code>True</code> to show immediately, <code>false</code> otherwise.
		 * @return This options.
		 */
		public ShowOptions showImmediately(boolean immediately) {
			this.showImmediately = immediately;
			return this;
		}
	}

	/**
	 *
	 */
	private final class BackStackListener implements FragmentManager.OnBackStackChangedListener {

		/**
		 * Constants =============================
		 */

		/**
		 *
		 */
		static final int ADDED = 0x00;

		/**
		 *
		 */
		static final int REMOVED = 0x01;

		/**
		 * Members ===============================
		 */

		int currentCount = 0;

		/**
		 * Methods ===============================
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

	/**
	 * Interface ===================================================================================
	 */

	/**
	 * <h4>Interface Overview</h4>
	 * <p>
	 * Base interface for fragment factory.
	 * </p>
	 *
	 * @author Martin Albedinsky
	 * @see com.wit.android.support.fragment.manage.FragmentController
	 */
	public static interface FragmentFactory {

		/**
		 * <p>
		 * Returns an instance of fragment associated with the specified <var>fragmentId</var> within
		 * this fragment factory.
		 * </p>
		 * <p>
		 * This is in most cases invoked from instance of {@link com.wit.android.support.fragment.manage.FragmentController},
		 * when that instance of FragmentController was requested to show fragment with the specified
		 * <var>fragmentId</var>.
		 * </p>
		 *
		 * @param fragmentId The id of fragment to create.
		 * @param params     Bundle with parameters for requested fragment.
		 * @return The instance of fragment associated with the specified <var>fragmentId</var> or
		 * <code>null</code> if this fragment factory doesn't provides requested fragment.
		 * @see #isFragmentProvided(int)
		 */
		public Fragment createFragmentInstance(int fragmentId, Bundle params);

		/**
		 * <p>
		 * Returns a ShowOptions object for fragment associated with the specified <var>fragmentId</var>
		 * within this fragment factory.
		 * </p>
		 * <p>
		 * This is in most cases invoked form instance of {@link com.wit.android.support.fragment.manage.FragmentController},
		 * when that instance of FragmentController was requested to show fragment with the specified
		 * <var>fragmentId</var>. In such a case, if this fragment factory will not provide valid
		 * ShowOptions, FragmentController will use default ones.
		 * </p>
		 *
		 * @param fragmentId The id of fragment for which are options requested.
		 * @param params     Same params as for {@link #createFragmentInstance(int, android.os.Bundle)}.
		 * @return ShowOptions object for fragment associated with the specified <var>fragmentId</var>
		 * or <code>null</code> if this fragment factory doesn't provides ShowOptions for requested
		 * fragment.
		 */
		public ShowOptions getFragmentShowOptions(int fragmentId, Bundle params);

		/**
		 * <p>
		 * Returns a tag for fragment associated with the specified <var>fragmentId</var> within
		 * this fragment factory.
		 * </p>
		 *
		 * @param fragmentId The id of fragment for which is tag requested.
		 * @return Tag for fragment associated with the specified <var>fragmentId</var> or <code>null</code>
		 * if this fragment factory doesn't provides tag for requested fragment.
		 */
		public String getFragmentTag(int fragmentId);

		/**
		 * <p>
		 * Returns flag indicating whether there is provided a fragment for the specified <var>fragmentId</var>
		 * by this factory or not.
		 * </p>
		 *
		 * @param fragmentId The id of desired fragment.
		 * @return <code>True</code> if the fragment is provided ({@link #createFragmentInstance(int, android.os.Bundle)}
		 * will return an instance of such a fragment), <code>false</code> otherwise.
		 */
		public boolean isFragmentProvided(int fragmentId);
	}

	/**
	 * <h4>Class Overview</h4>
	 * <p>
	 * </p>
	 *
	 * @author Martin Albedinsky
	 */
	public static interface OnFragmentChangeListener {

		/**
		 * <p>
		 * Invoked whenever an old fragment is replaced by a new one or simply a new fragment is first
		 * time showed by an instance of FragmentController.
		 * </p>
		 *
		 * @param id      The id of the currently changed (showed) fragment.
		 * @param tag     The tag of the currently changed (showed) fragment.
		 * @param factory <code>True</code> if the changed fragment was obtained from a factory,
		 *                <code>false</code> otherwise.
		 */
		public void onFragmentChanged(int id, String tag, boolean factory);
	}

	/**
	 * <h4>Class Overview</h4>
	 * <p>
	 * </p>
	 *
	 * @author Martin Albedinsky
	 */
	public static interface OnBackStackChangeListener {

		/**
		 * <p>
		 * Invoked whenever fragments back stack change occur.
		 * </p>
		 *
		 * @param added <code>True</code> if there was added new back stack entry, <code>false</code>
		 *              if old one was removed.
		 * @param id    The id of a back stack entry which status was changed.
		 * @param tag   The tag of a back stack entry which status was changed.
		 */
		public void onBackStackChanged(boolean added, int id, String tag);
	}
}
