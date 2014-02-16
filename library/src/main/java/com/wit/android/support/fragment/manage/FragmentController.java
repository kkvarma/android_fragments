/*
 * =================================================================================
 * Copyright (C) 2013 - 2014 Martin Albedinsky [Wolf-ITechnologies]
 * =================================================================================
 * Licensed under the Apache License, Version 2.0 or later (further "License" only);
 * ---------------------------------------------------------------------------------
 * You may use this file only in compliance with the License. More details and copy
 * of this License you may obtain at
 * 
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * 
 * You can redistribute, modify or publish any part of the code written in this
 * file but as it is described in the License, the software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES or CONDITIONS OF
 * ANY KIND.
 * 
 * See the License for the specific language governing permissions and limitations
 * under the License.
 * =================================================================================
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

import java.util.ArrayList;
import java.util.List;

/**
 * <h4>Class Overview</h4>
 * <p>
 * </p>
 *
 * @author Martin Albedinsky
 * @see com.wit.android.support.fragment.manage.FragmentController.IFragmentFactory
 * @see com.wit.android.support.fragment.manage.FragmentController.ShowOptions
 */
public class FragmentController {

	/**
	 * Constants =============================
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
	private static final boolean DEBUG = false;

	/**
	 * Flag indicating whether the output for user trough log-cat is enabled or not.
	 */
	private static final boolean USER_LOG = true;

	/**
	 * Enums =================================
	 */

	/**
	 * Static members ========================
	 */

	/**
	 * Members ===============================
	 */

	/**
	 * Fragment manager to handle showing, obtaining and hiding fragments.
	 */
	private FragmentManager mFragmentManager = null;

	/**
	 * Fragment factory which provides fragment instances to show (manage) by this controller.
	 */
	private IFragmentFactory mFragmentFactory = null;

	/**
	 * Id of a layout container within the current window view hierarchy, into which will be view of
	 * all managed fragments placed.
	 */
	private int mFragmentContainerID = -1;

	/**
	 *
	 */
	private String mLastFragmentTag = null;

	/**
	 * Listeners -----------------------------
	 */

	/**
	 *
	 */
	private OnFragmentChangeListener lChangeListener;

	/**
	 * Arrays --------------------------------
	 */

	/**
	 * Booleans ------------------------------
	 */

	/**
	 * Constructors ==========================
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
		if (parentFragment instanceof OnFragmentChangeListener) {
			this.lChangeListener = (OnFragmentChangeListener) parentFragment;
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
		if (parentActivity instanceof OnFragmentChangeListener) {
			this.lChangeListener = (OnFragmentChangeListener) parentActivity;
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
			throw new NullPointerException("Illegal fragment manager.");
		}
	}

	/**
	 * Methods ===============================
	 */

	/**
	 * Public --------------------------------
	 */

	/**
	 * <p>
	 * Same as {@link #showFragment(int, android.os.Bundle)} without params.
	 * </p>
	 */
	public final boolean showFragment(int fragmentID) {
		return this.showFragment(fragmentID, null);
	}

	/**
	 * <p>
	 * Shows a fragment instance provided by the current factory represented by the specified
	 * <var>fragmentID</var>.
	 * </p>
	 *
	 * @param fragmentID The id of fragment from the current fragment factory to show.
	 * @param params     Parameters to be passed to the current factory by
	 *                   {@link com.wit.android.support.fragment.manage.FragmentController.IFragmentFactory#createFragmentInstance(int, android.os.Bundle)}.
	 * @return <code>True</code> if transaction for the requested fragment was successfully created
	 * and committed or if fragment is currently being shown and should not be replaced by the new one,
	 * <code>false</code> otherwise.
	 * @throws IllegalStateException If there is no factory currently available.
	 * @see #hasFactory()
	 */
	public final boolean showFragment(int fragmentID, Bundle params) {
		// Check if we have fragment factory.
		return this.checkFragmentFactory() && this.performShowFactoryFragment(fragmentID, params);
	}

	/**
	 * <p>
	 * Same as {@link #showFragment(android.support.v4.app.Fragment, com.wit.android.support.fragment.manage.FragmentController.ShowOptions)}
	 * with default {@link com.wit.android.support.fragment.manage.FragmentController.ShowOptions}.
	 * </p>
	 */
	public final boolean showFragment(Fragment fragment) {
		return this.showFragment(fragment, new ShowOptions());
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
	public final boolean showFragment(Fragment fragment, ShowOptions options) {
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
	 */
	public Fragment findFragmentByID(int fragmentID) {
		return mFragmentManager.findFragmentById(fragmentID);
	}

	/**
	 * <p>
	 * Same as {@link #findFragmentByTag(String)}, where fragment tag will be requested from the
	 * current fragment factory by the specified <var>fragmentID</var>.
	 * </p>
	 *
	 * @param fragmentID The id of fragment from the current factory to obtain.
	 * @throws IllegalStateException If there is no factory currently available.
	 * @see #hasFactory()
	 */
	public Fragment getFragmentByID(int fragmentID) {
		// Check if we have fragment factory.
		return this.checkFragmentFactory() ? findFragmentByTag(mFragmentFactory.getFragmentTag(fragmentID)) : null;
	}

	/**
	 * <p>
	 * Same as {@link android.support.v4.app.FragmentManager#findFragmentByTag(String)}.
	 * </p>
	 *
	 * @return Found an instance of fragment or <code>null</code> if there is no fragment with the
	 * specified tag within the current fragment manager.
	 * @see #findFragmentByID(int)
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
		final List<Fragment> visibleFragments = new ArrayList<Fragment>();

		if (fragments != null) {
			// Get only visible fragments.
			for (Fragment fragment : fragments) {
				// TODO: perform here more fragment flag checks ?
				if (fragment != null && (fragment.isVisible() || fragment.isAdded())) {
					visibleFragments.add(fragment);

					if (DEBUG) {
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
		if (DEBUG) {
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
		final List<Fragment> visibleFragments = new ArrayList<Fragment>();

		if (fragments != null) {
			// Get only visible fragments.
			for (Fragment fragment : fragments) {
				// TODO: perform here more fragment flag checks ?
				if (fragment != null && (fragment.isVisible() || fragment.isAdded())) {
					visibleFragments.add(fragment);

					if (DEBUG) {
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
		if (DEBUG) {
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
	 * Same as {@link #removeFragmentFromBackStack(String)}, where fragment tag will be requested form
	 * the current fragment factory by the specified <var>fragmentID</var>.
	 * </p>
	 * <p>
	 * <b>This implementation does nothing.</b>
	 * </p>
	 *
	 * @param fragmentID The id of fragment from the current fragment factory to remove.
	 */
	public boolean removeFragmentFromBackStack(int fragmentID) {
		// Check if we have fragment factory.
		return this.checkFragmentFactory() && removeFragmentFromBackStack(mFragmentFactory.getFragmentTag(fragmentID));
	}

	/**
	 * <p>
	 * Removes a fragment associated with the specified <var>fragmentTag</var> from the back stack of
	 * the current fragment manager.
	 * </p>
	 * <p>
	 * <b>This implementation does nothing.</b>
	 * </p>
	 *
	 * @param fragmentTag The tag of fragment to remove.
	 * @return <code>True</code> if removing was successful, <code>false</code> otherwise.
	 */
	public boolean removeFragmentFromBackStack(String fragmentTag) {
		return performRemoveBackStackFragment(fragmentTag);
	}

	/**
	 * <p>
	 * Same as {@link #showFragmentOptionsMenu(String, boolean)}, where fragment tag will be requested
	 * form the current fragment factory by the specified <var>fragmentID</var>.
	 * </p>
	 *
	 * @param fragmentID The id of fragment from the current fragment factory, of which options to
	 *                   show/hide.
	 */
	public boolean showFragmentOptionsMenu(int fragmentID, boolean enable) {
		// Check if we have fragment factory.
		return this.checkFragmentFactory() && showFragmentOptionsMenu(mFragmentFactory.getFragmentTag(fragmentID), enable);
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
	 * Called to restore state of this fragment controller instance.
	 * </p>
	 *
	 * @param savedState Should be the bundle with saved state in {@link #dispatchSaveInstanceState(android.os.Bundle)}.
	 */
	public final void dispatchRestoreInstanceState(Bundle savedState) {
		if (savedState != null) {
			onRestoreInstanceState(savedState);
		}
	}

	/**
	 * <p>
	 * Called to save state of this fragment controller instance. If the given <var>outState</var>
	 * is invalid, a new bundle will be created.
	 * </p>
	 *
	 * @param outState Outgoing state in which should this fragment controller instance save its state.
	 * @see #dispatchRestoreInstanceState(android.os.Bundle)
	 */
	public final void dispatchSaveInstanceState(Bundle outState) {
		if (outState == null) {
			outState = new Bundle();
		}
		onSaveInstanceState(outState);
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
	public final FragmentTransaction beginTransaction() {
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
	 * Getters + Setters ---------------------
	 */

	/**
	 * <p>
	 * Returns the tag of the currently visible fragment.
	 * </p>
	 *
	 * @return Fragment tag, or <code>null</code> if there is not currently visible any fragment.
	 * @see #getVisibleFragment()
	 */
	public String getVisibleFragmentTag() {
		final Fragment visibleFragment = getVisibleFragment();
		return (visibleFragment != null) ? visibleFragment.getTag() : null;
	}

	/**
	 * <p>
	 * Returns an instance of the current support fragment manager.
	 * </p>
	 *
	 * @return Current fragment manager, or <code>null</code> if there is no fragment manager available.
	 */
	public final FragmentManager getFragmentManager() {
		return mFragmentManager;
	}

	/**
	 * <p>
	 * Sets an id of layout container for fragment views.
	 * </p>
	 *
	 * @param layoutID The id of a layout container within the current window view hierarchy, into
	 *                 which will be view of all managed fragments placed.
	 * @see #getFragmentContainerID()
	 */
	public final void setFragmentContainerID(int layoutID) {
		this.mFragmentContainerID = layoutID;
	}

	/**
	 * <p>
	 * Returns an id of layout container for fragment views.
	 * </p>
	 *
	 * @return The id of a layout container within the current window view hierarchy, into which will
	 * be view of all managed fragments placed or <code>-1</code> as default.
	 * @see #setFragmentContainerID(int)
	 */
	public final int getFragmentContainerID() {
		return mFragmentContainerID;
	}

	/**
	 * <p>
	 * Sets the fragment factory for this fragment controller instance, which will be used to obtain
	 * instances of fragments requested by this controller by specific <b>fragmentID</b>.
	 * </p>
	 *
	 * @param factory Fragment factory to provide fragment instances, show options and fragment tags.
	 * @see #getFragmentFactory()
	 * @see #hasFactory()
	 */
	public final void setFragmentFactory(IFragmentFactory factory) {
		this.mFragmentFactory = factory;
	}

	/**
	 * <p>
	 * Returns the current fragment factory.
	 * </p>
	 *
	 * @return Current fragment factory, or <code>null</code> if there is no factory available.
	 * @see #setFragmentFactory(com.wit.android.support.fragment.manage.FragmentController.IFragmentFactory)
	 * @see #hasFactory()
	 */
	public final IFragmentFactory getFragmentFactory() {
		return mFragmentFactory;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 */
	public final String getLastFragmentTag() {
		return mLastFragmentTag;
	}

	/**
	 * Protected -----------------------------
	 */

	/**
	 * <p>
	 * Invoked to save state of this fragment controller instance. This is invoked whenever
	 * {@link #dispatchSaveInstanceState(android.os.Bundle)} is called.
	 * </p>
	 * <p>
	 * <b>This implementation does nothing.</b>
	 * </p>
	 *
	 * @param outState Outgoing state. Always valid bundle.
	 * @see #onRestoreInstanceState(android.os.Bundle)
	 */
	protected void onSaveInstanceState(Bundle outState) {
	}

	/**
	 * <p>
	 * Invoked to restore state of this fragment controller instance. Note, that this is invoked
	 * only in case that the bundle passed to {@link #dispatchRestoreInstanceState(android.os.Bundle)}
	 * is valid.
	 * </p>
	 * <p>
	 * <b>This implementation does nothing.</b>
	 * </p>
	 *
	 * @param savedState Bundle with saved data populated in {@link #onSaveInstanceState(Bundle)}.
	 *                   Always valid bundle.
	 */
	protected void onRestoreInstanceState(Bundle savedState) {
	}

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
				if (USER_LOG) {
					Log.i(TAG, "Fragment with tag(" + options.tag + ") is already showing or in the back-stack.");
				}
				return true;
			}
		}

		// Check if we have place where the fragment should be placed.
		if (options.containerID == -1) {
			if (mFragmentContainerID == -1) {
				// No id provided for the layout where should be fragment
				// placed.
				throw new IllegalStateException("There is no id provided for the layout container into which should be requested fragment's view placed.");
			} else {
				options.containerID = mFragmentContainerID;
			}
		}

		final FragmentTransaction transaction = this.beginTransaction();

		// Apply animations to the transaction from the ShowDirection parameter.
		if (options.showDirection != ShowDirection.NONE) {
			ShowDirection dir = options.showDirection;

			/**
			 * <pre>
			 * There are provided 4 animations:
			 * First two for currently incoming and outgoing fragment.
			 * Second two for incoming fragment from back stack and
			 * currently outgoing fragment.
			 * </pre>
			 */
			transaction.setCustomAnimations(dir.getInAnimResID(), dir.getOutAnimResID(), dir.getInAnimBackResID(),
					dir.getOutAnimBackResID());
		}

		if (DEBUG) {
			Log.d(TAG, "onShowFragment() options = " + options.toString());
		}

		// Fragment's view replaces the view of the current fragment container layout.
		transaction.replace(options.containerID, fragment, options.tag);

		// Add fragment to back stack if requested.
		if (options.addToBackStack) {
			transaction.addToBackStack(fragment.getTag());
			if (USER_LOG) {
				Log.i(TAG, "Fragment(" + fragment + ") added to back stack under the tag(" + fragment.getTag() + ").");
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
	 * <p>
	 * Invoked to remove a fragment with the given tag from the back stack of the current fragment
	 * manager.
	 * </p>
	 * <p>
	 * <b>This implementation does nothing.</b>
	 * </p>
	 *
	 * @param fragmentTag The tag of fragment to remove.
	 * @return <code>True</code> if removing was successful, <code>false</code> otherwise.
	 */
	protected boolean onRemoveBackStackFragment(String fragmentTag) {
		return false; // TODO: implement this
	}

	/**
	 * Private -------------------------------
	 */

	/**
	 * Performs showing of the given fragment instance using the given options.
	 *
	 * @param fragment Fragment to show.
	 * @param options  Show options for the given fragment.
	 * @return <code>True</code> if showing was successful, <code>false</code> otherwise.
	 */
	private boolean performShowFragment(Fragment fragment, ShowOptions options) {
		final boolean shown = onShowFragment(fragment, options);
		return shown && notifyFragmentChanged(fragment.getId(), fragment.getTag(), false);
	}

	/**
	 * Performs showing of a fragment obtained from the current fragment factory.
	 *
	 * @param fragmentID The id of fragment from the current fragment factory to show.
	 * @param params     Parameters to be passed to the current factory by
	 *                   {@link com.wit.android.support.fragment.manage.FragmentController.IFragmentFactory#createFragmentInstance(int, android.os.Bundle)}.
	 * @return <code>True</code> if showing was successful, <code>false</code> otherwise.
	 */
	private boolean performShowFactoryFragment(int fragmentID, Bundle params) {
		// First obtain fragment instance then fragment tag.
		Fragment fragment = mFragmentFactory.createFragmentInstance(fragmentID, params);
		if (fragment == null) {
			// Invalid fragment instance.
			Log.e(TAG, "No such fragment instance for the requested fragment id(" + fragmentID + "). Please check your fragment factory.");
			return false;
		}
		final boolean shown = onShowFragment(fragment, mFragmentFactory.getFragmentShowOptions(fragmentID, params));
		return shown && notifyFragmentChanged(fragmentID, fragment.getTag(), true);
	}

	/**
	 * @param id
	 * @param tag
	 * @param factoryFragment
	 */
	private boolean notifyFragmentChanged(int id, String tag, boolean factoryFragment) {
		this.mLastFragmentTag = tag;
		if (lChangeListener != null) {
			lChangeListener.onFragmentChanged(id, tag, factoryFragment);
		}
		return true;
	}

	/**
	 * Performs removing of a fragment with the requested <var>fragmentTag</var> from the back stack.
	 *
	 * @param fragmentTag The tag of fragment to remove.
	 * @return <code>True</code> if removing was successful, <code>false</code> otherwise.
	 */
	private boolean performRemoveBackStackFragment(String fragmentTag) {
		return onRemoveBackStackFragment(fragmentTag);
	}

	/**
	 * Checks if the current fragment factory is available or not.
	 *
	 * @return Always <code>true</code> or throws exception.
	 * @throws java.lang.IllegalStateException If the current factory isn't available.
	 */
	private boolean checkFragmentFactory() {
		if (mFragmentFactory == null) {
			throw new IllegalStateException("No fragment factory found.");
		}
		return true;
	}

	/**
	 * Abstract methods ----------------------
	 */

	/**
	 * Inner classes =========================
	 */

	/**
	 * <h4>Class Overview</h4>
	 * <p>
	 * Description.
	 * </p>
	 * <h4>Default SetUp:</h4>
	 * <ul>
	 * <li>tag: {@link com.wit.android.support.fragment.manage.FragmentController#FRAGMENT_TAG}</li>
	 * <li>show direction: {@link com.wit.android.support.fragment.manage.ShowDirection#NONE}</li>
	 * <li>container id: <b>-1</b></li>
	 * <li>back-stacking: <b>false</b></li>
	 * <li>replacing same: <b>true</b></li>
	 * <li>showing immediately: <b>false</b></li>
	 * <li>showing options menu: <b>false</b></li>
	 * </ul>
	 *
	 * @author Martin Albedinsky
	 * @see com.wit.android.support.fragment.manage.FragmentController
	 * @see com.wit.android.support.fragment.manage.FragmentController.IFragmentFactory
	 */
	public static class ShowOptions implements Parcelable {
		/**
		 * Constants =============================
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
		 * Members ===============================
		 */

		/**
		 * Tag of fragment.
		 */
		protected String tag = FRAGMENT_TAG;

		/**
		 * Show direction.
		 */
		protected ShowDirection showDirection = ShowDirection.NONE;

		/**
		 * Container layout id.
		 */
		protected int containerID = -1;

		/**
		 * Booleans ------------------------------
		 */

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
		 * Constructors ==========================
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
			this.containerID = input.readInt();
			this.tag = input.readString();
			this.addToBackStack = input.readInt() == 1;
			this.replaceSame = input.readInt() == 1;
			this.showImmediately = input.readInt() == 1;
			this.showDirection = ShowDirection.CREATOR.createFromParcel(input);
		}

		/**
		 * Methods ===============================
		 */

		/**
		 * Public --------------------------------
		 */

		/**
		 */
		@Override
		public void writeToParcel(Parcel dest, int flags) {
			dest.writeInt(containerID);
			dest.writeString(tag);
			dest.writeInt(addToBackStack ? 1 : 0);
			dest.writeInt(replaceSame ? 1 : 0);
			dest.writeInt(showImmediately ? 1 : 0);
			showDirection.writeToParcel(dest, flags);
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
			builder.append(" showDirection(");
			builder.append(showDirection.name());
			builder.append("), ");
			builder.append(" backStacked(");
			builder.append(addToBackStack);
			builder.append("), ");
			builder.append(" replace(");
			builder.append(replaceSame);
			builder.append("), ");
			builder.append(" container(");
			builder.append(containerID);
			builder.append(")]");
			return builder.toString();
		}

		/**
		 * Getters + Setters ---------------------
		 */

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
		 * Sets the show direction for fragment.
		 * </p>
		 *
		 * @param direction Show direction.
		 * @return This options.
		 * @see com.wit.android.support.fragment.manage.ShowDirection
		 */
		public ShowOptions showDirection(ShowDirection direction) {
			this.showDirection = direction;
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
		 * @param layoutID The id of layout container.
		 * @return This options.
		 * @see com.wit.android.support.fragment.manage.FragmentController#setFragmentContainerID(int)
		 */
		public ShowOptions containerID(int layoutID) {
			this.containerID = layoutID;
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
	 * Interface =============================
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
	public static interface IFragmentFactory {

		/**
		 * Methods ===============================
		 */

		/**
		 * <p>
		 * Returns an instance of fragment associated with the specified <var>fragmentID</var> within
		 * this fragment factory.
		 * </p>
		 * <p>
		 * This is in most cases invoked from instance of {@link com.wit.android.support.fragment.manage.FragmentController},
		 * when that instance of FragmentController was requested to show fragment with the specified
		 * <var>fragmentID</var>.
		 * </p>
		 *
		 * @param fragmentID The id of fragment to create.
		 * @param params     Bundle with parameters for requested fragment.
		 * @return The instance of fragment associated with the specified <var>fragmentID</var> or
		 * <code>null</code> if this fragment factory doesn't provides requested fragment.
		 */
		public Fragment createFragmentInstance(int fragmentID, Bundle params);

		/**
		 * <p>
		 * Returns a ShowOptions object for fragment associated with the specified <var>fragmentID</var>
		 * within this fragment factory.
		 * </p>
		 * <p>
		 * This is in most cases invoked form instance of {@link com.wit.android.support.fragment.manage.FragmentController},
		 * when that instance of FragmentController was requested to show fragment with the specified
		 * <var>fragmentID</var>. In such a case, if this fragment factory will not provide valid
		 * ShowOptions, FragmentController will use default ones.
		 * </p>
		 *
		 * @param fragmentID The id of fragment for which are options requested.
		 * @param params     Same params as for {@link #createFragmentInstance(int, android.os.Bundle)}.
		 * @return ShowOptions object for fragment associated with the specified <var>fragmentID</var>
		 * or <code>null</code> if this fragment factory doesn't provides ShowOptions for requested
		 * fragment.
		 */
		public ShowOptions getFragmentShowOptions(int fragmentID, Bundle params);

		/**
		 * <p>
		 * Returns a tag for fragment associated with the specified <var>fragmentID</var> within
		 * this fragment factory.
		 * </p>
		 *
		 * @param fragmentID The id of fragment for which is tag requested.
		 * @return Tag for fragment associated with the specified <var>fragmentID</var> or <code>null</code>
		 * if this fragment factory doesn't provides tag for requested fragment.
		 */
		public String getFragmentTag(int fragmentID);
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
		 * </p>
		 *
		 * @param id
		 * @param tag
		 * @param factoryFragment
		 */
		public void onFragmentChanged(int id, String tag, boolean factoryFragment);
	}
}
