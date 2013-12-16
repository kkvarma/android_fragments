/*
 * =================================================================================
 * Copyright (C) 2013 Martin Albedinsky [Wolf-ITechnologies]
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
package com.wit.android.fragment.manage;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.wit.android.fragment.R;

import java.util.ArrayList;
import java.util.List;

/**
 * <h4>Class Overview</h4>
 * <p>
 * </p>
 *
 * @author Martin Albedinsky
 *
 * @see com.wit.android.fragment.manage.FragmentController.IFragmentFactory
 * @see com.wit.android.fragment.manage.FragmentController.ShowOptions
 */
public class FragmentController {

    /**
     * Constants =============================
     */

    /**
     * <h5><i>public static final String FRAGMENT_TAG</i></h5>
     * <p>
     * Default tag for the showing on fragments.
     * </p>
     */
    public static final String FRAGMENT_TAG = "com.wit.and.app.fragment.manage.FragmentController.Fragment.TAG";

    /**
     * <br/>
     * <h5><i>public static final ShowOptions SHOW_OPTIONS</i></h5>
     * <p>
     * Default options to show fragment.
     * </p>
     */
    public static final ShowOptions SHOW_OPTIONS = new ShowOptions();

    /**
     * Log TAG.
     */
    private static final String TAG = FragmentController.class.getSimpleName();

    /**
     * Indicates if debug private output trough log-cat is enabled.
     */
    private static final boolean DEBUG = false;

    /**
     * Indicates if logging for user output trough log-cat is enabled.
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
     * Fragment manager to handle showing/searching and hiding fragments.
     */
    private FragmentManager mFragmentManager = null;

    /**
     * Fragment factory which provides fragment instance.
     */
    private IFragmentFactory mFragmentFactory = null;

    /**
     * Id of global layout in which will be layout of showing fragment placed.
     */
    private int mFragmentContainerID = -1;

    /**
     * Listeners -----------------------------
     */

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
     * Same as
     * {@link #FragmentController(android.support.v4.app.FragmentManager, com.wit.android.fragment.manage.FragmentController.IFragmentFactory)}, but with
     * not initialized factory.
     * </p>
     *
     * @param fragmentManager Support fragment manager.
     */
    public FragmentController(FragmentManager fragmentManager) {
        this(fragmentManager, null);
    }

    /**
     * <p>
     * Constructs the controller to handle fragments.
     * </p>
     * <p>
     * <b>Note: </b>always use {@link android.support.v4.app.FragmentManager} provided by
     * {@link android.support.v4.app.FragmentActivity}.
     * </p>
     * <p>
     * <b>DialogManager parent:</b>
     * <ul>
     * <li>FragmentActivity:
     * <code>yourActivity.getSupportFragmentManager()</code></li>
     * <li>Fragment: <code>yourFragment.getFragmentManager()</code></li>
     * </ul>
     * </p>
     *
     * @param fragmentManager Support fragment manager.
     * @param factory         Fragment factory to provide fragment instances.
     */
    public FragmentController(FragmentManager fragmentManager, IFragmentFactory factory) {
        this.mFragmentManager = fragmentManager;
        this.mFragmentFactory = factory;
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
     *
     * @param fragmentID Id of factory fragment to show.
     * @return <code>True</code> if transaction for fragment was successfully
     *         created and committed or if fragment is currently showing and should
     *         not be replaced by new one, <code>false</code> otherwise.
     * @throws IllegalStateException If current factory isn't valid.
     *
     * @see #hasFactory()
     */
    public final boolean showFragment(int fragmentID) {
        return this.showFragment(fragmentID, null);
    }

    /**
     * <p>
     * Shows the fragment provided from the current factory represented by
     * the requested <var>fragmentID</var>.
     * </p>
     *
     * @param fragmentID Id of factory fragment to show.
     * @param params Parameters for factory fragment. These parameters will
     *               be passed to the factory.
     * @return <code>True</code> if transaction for fragment was successfully
     *         created and committed or if fragment is currently showing and should
     *         not be replaced by new one, <code>false</code> otherwise.
     * @throws IllegalStateException If current factory isn't valid.
     *
     * @see #hasFactory()
     */
    public final boolean showFragment(int fragmentID, Bundle params) {
        // Check if we have fragment factory.
        return this.checkFragmentFactory() && this.performShowFactoryFragment(fragmentID, params);
    }

    /**
     * <p>
     * Same as {@link #showFragment(android.support.v4.app.Fragment, com.wit.android.fragment.manage.FragmentController.ShowOptions)}
     * with default {@link com.wit.android.fragment.manage.FragmentController.ShowOptions}.
     * </p>
     *
     * @param fragment Fragment to show.
     * @return <code>True</code> if transaction for fragment was successfully
     *         created and committed or if fragment is currently showing and should
     *         not be replaced by new one, <code>false</code> otherwise.
     */
    public final boolean showFragment(Fragment fragment) {
        return this.showFragment(fragment, SHOW_OPTIONS);
    }

    /**
     * <p>
     * Shows the given fragment using the given show options.
     * </p>
     *
     * @param fragment Fragment to show.
     * @param options Show options for the given fragment.
     * @return <code>True</code> if transaction for fragment was successfully
     *         created and committed or if fragment is currently showing and should
     *         not be replaced by new one, <code>false</code> otherwise.
     */
    public final boolean showFragment(Fragment fragment, ShowOptions options) {
        return this.performShowFragment(fragment, options);
    }

    /**
     * <p>
     * Same as {@link android.support.v4.app.FragmentManager#findFragmentById(int)}.
     * </p>
     *
     * @param fragmentID Id of fragment to find.
     * @return Found fragment or <code>null</code> if there is no fragment with the requested id.
     *
     * @see #findFragmentByTag(String)
     */
    public Fragment findFragmentByID(int fragmentID) {
        return mFragmentManager.findFragmentById(fragmentID);
    }

    /**
     * <p>
     * Same as {@link android.support.v4.app.FragmentManager#findFragmentByTag(String)}.
     * </p>
     *
     * @param fragmentTag Tag of fragment to find.
     * @return Found fragment or <code>null</code> if there is no fragment with the request tag.
     *
     * @see #findFragmentByID(int)
     */
    public Fragment findFragmentByTag(String fragmentTag) {
        return mFragmentManager.findFragmentByTag(fragmentTag);
    }

    /**
     * <p>
     * Same as {@link #findFragmentByTag(String)} where fragment tag
     * will be requested from the current fragment factory.
     * </p>
     *
     * @param fragmentID If of factory dialog.
     * @return Found fragment or <code>null</code> if there is no fragment with the tag
     *         provided from factory.
     * @throws IllegalStateException If current factory isn't valid.
     */
    public Fragment getFragmentByID(int fragmentID) {
        // Check if we have fragment factory.
        return this.checkFragmentFactory() ? findFragmentByTag(mFragmentFactory.getFragmentTag(fragmentID)) : null;
    }

    /**
     * <p>
     * Returns the fragment which is currently showing at the screen. To determine
     * visible fragment state are check these flags:
     * <ul>
     * <li>{@link android.support.v4.app.Fragment#isVisible()}</li>
     * <li>or {@link android.support.v4.app.Fragment#isAdded()}</li>
     * </ul>
     * </p>
     * <p>
     * This will be actually the fragment which was showed as last.
     * </p>
     *
     * @return Currently visible or added fragment, or <code>null</code> if there
     *         are no fragments or fragments are in undesirable states.
     *
     * @see #getVisibleSecondFragment()
     */
    public Fragment getVisibleFragment() {
        if (DEBUG)
            Log.d(TAG, "Resolving current visible fragment.");

        final List<Fragment> fragments = mFragmentManager.getFragments();
        final List<Fragment> visibleFragments = new ArrayList<Fragment>();

        if (fragments != null) {
            // Get only visible fragments.
            for (Fragment fragment : fragments) {
                // TODO: perform here more fragment checks ?
                if (fragment != null && (fragment.isVisible() || fragment.isAdded())) {
                    visibleFragments.add(fragment);

                    if (DEBUG)
                        Log.d(TAG, "visible/added fragment(" + fragment.getTag() + ")");
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
     * Returns the second fragment which is currently showing at the screen
     * where two fragments are visible at the same time. To determine
     * second visible fragment state are check these flags:
     * <ul>
     * <li>{@link android.support.v4.app.Fragment#isVisible()}</li>
     * <li>or {@link android.support.v4.app.Fragment#isAdded()}</li>
     * </ul>
     * </p>
     * <p>
     * This will be actually the fragment which was showed before the last one.
     * </p>
     *
     * @return Currently second visible or added fragment, or <code>null</code> if there
     *         are no fragments or fragments are in undesirable states.
     *
     * @see #getVisibleFragment()
     */
    public Fragment getVisibleSecondFragment() {
        if (DEBUG)
            Log.d(TAG, "Resolving current second visible fragment.");

        final List<Fragment> fragments = mFragmentManager.getFragments();
        final List<Fragment> visibleFragments = new ArrayList<Fragment>();

        if (fragments != null) {
            // Get only visible fragments.
            for (Fragment fragment : fragments) {
                // TODO: perform here more fragment checks ?
                if (fragment != null && (fragment.isVisible() || fragment.isAdded())) {
                    visibleFragments.add(fragment);

                    if (DEBUG)
                        Log.d(TAG, "visible/added fragment(" + fragment.getTag() + ")");
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
     *
     * @return <code>True</code> if fragment was hided (popped) immediate, <code>false</code> otherwise.
     */
    public boolean hideVisibleFragmentImmediate() {
        return mFragmentManager.popBackStackImmediate();
    }

    /**
     * <p>
     * Same as {@link #removeFragmentFromBackStack(String)} where fragment
     * tag will be requested form the current factory.
     * </p>
     * <p>
     * <b>This implementation does nothing.</b>
     * </p>
     *
     * @param fragmentID Id of factory fragment to remove.
     * @return <code>True</code> if removing was successful, <code>false</code> otherwise.
     *
     * @throws java.lang.IllegalStateException If current factory isn't valid.
     */
    public boolean removeFragmentFromBackStack(int fragmentID) {
        // Check if we have fragment factory.
        return this.checkFragmentFactory() && removeFragmentFromBackStack(mFragmentFactory.getFragmentTag(fragmentID));
    }

    /**
     * <p>
     * Removes fragment associated with the given <var>fragmentTag</var>
     * from the back stack.
     * </p>
     * <p>
     * <b>This implementation does nothing.</b>
     * </p>
     *
     * @param fragmentTag Tag of fragment to remove.
     * @return <code>True</code> if removing was successful, <code>false</code> otherwise.
     */
    public boolean removeFragmentFromBackStack(String fragmentTag) {
        return performRemoveBackStackFragment(fragmentTag);
    }

    /**
     * <p>
     * Returns flag indicating if this controller holds valid factory.
     * </p>
     *
     * @return <code>True</code> if current factory is valid, <code>false</code> otherwise.
     */
    public boolean hasFactory() {
        return mFragmentFactory != null;
    }

    /**
     * Getters + Setters ---------------------
     */

    /**
     * <p>
     * Returns tag of the currently visible fragment.
     * </p>
     *
     * @return Fragment tag, or <code>null</code> if there is currently no visible fragment.
     *
     * @see #getVisibleFragment()
     */
    public String getVisibleFragmentTag() {
        final Fragment visibleFragment = getVisibleFragment();
        return (visibleFragment != null) ? visibleFragment.getTag() : null;
    }

    /**
     * <p>
     * Sets the fragment manager to handle showing fragments.
     * </p>
     * <b>Note: </b>always use {@link android.support.v4.app.FragmentManager} provided by fragment
     * activity.</p>
     * <p>
     * <b>FragmentController parent:</b>
     * <ul>
     * <li>FragmentActivity:
     * <code>yourActivity.getSupportFragmentManager()</code></li>
     * <li>Fragment: <code>yourFragment.getFragmentManager()</code></li>
     * </ul>
     * </p>
     *
     * @param fragmentManager Support fragment manager.
     */
    public final void setFragmentManager(FragmentManager fragmentManager) {
        this.mFragmentManager = fragmentManager;
    }

    /**
     * <p>
     * Sets the id of container layout.
     * </p>
     *
     * @param layoutID Id of layout into which will be created
     *                 view of showing fragments placed.
     *
     * @see #getFragmentContainerID()
     */
    public final void setFragmentContainerID(int layoutID) {
        this.mFragmentContainerID = layoutID;
    }

    /**
     * <p>
     * Returns id of container layout.
     * </p>
     *
     * @return Same id as set or <code>-1</code> as default.
     *
     * @see #setFragmentContainerID(int)
     */
    public final int getFragmentContainerID() {
        return mFragmentContainerID;
    }

    /**
     * <p>
     * Sets the fragment factory.
     * </p>
     *
     * @param factory Fragment factory to provide fragment instances.
     *
     * @see #getFragmentFactory()
     * @see #hasFactory()
     */
    public final void setFragmentFactory(IFragmentFactory factory) {
        this.mFragmentFactory = factory;
    }

    /**
     * <p>
     * Returns current fragment factory.
     * </p>
     *
     * @return Current fragment factory, or <code>null</code> if there is no factory set.
     *
     * @see #setFragmentFactory(com.wit.android.fragment.manage.FragmentController.IFragmentFactory)
     * @see #hasFactory()
     */
    public final IFragmentFactory getFragmentFactory() {
        return mFragmentFactory;
    }

    /**
     * <p>
     * Dispatches restoring of the fragment controller saved state.
     * </p>
     *
     * @param savedInstanceState Bundle with controller state to restore.
     */
    public final void dispatchRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }
    }

    /**
     * <p>
     * Dispatches saving of the current fragment controller state.
     * </p>
     *
     * @param outState Bundle to save controller state.
     */
    public final void dispatchSaveInstanceState(Bundle outState) {
        if (outState == null) {
            outState = new Bundle();
        }
        onSaveInstanceState(outState);
    }

    /**
     * Protected -----------------------------
     */

    /**
     * <p>
     * Derived class should restored here its own saved state. This implementation does nothing.
     * </p>
     *
     * @param savedInstanceState Bundle with controller state to restore.
     */
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    }

    /**
     * <p>
     * Derived class should here save its own state. This implementation does nothing.
     * </p>
     *
     * @param outState Bundle to save controller state.
     */
    protected void onSaveInstanceState(Bundle outState) {
    }

    /**
     * <p>
     * Returns support fragment manager.
     * </p>
     *
     * @return Current fragment manager, or <code>null</code> if there is no
     *         manager set.
     */
    protected final FragmentManager getFragmentManager() {
        return mFragmentManager;
    }

    /**
     * <p>
     * Invoked to show the given fragment using the given options.
     * </p>
     *
     * @param fragment Fragment to show.
     * @param options Show options for the given fragment.
     * @return <code>True</code> if showing was successful, <code>false</code> otherwise.
     *         This implementation always returns <code>true</code> or throws exception.
     *
     * @throws java.lang.IllegalStateException If the id of layout container is not set.
     */
    protected boolean onShowFragment(Fragment fragment, ShowOptions options) {
        if (options == null) {
            // Use default options.
            options = SHOW_OPTIONS;
        }

        if (!options.replaceSame) {
            // Do not replace same fragment.
            Fragment currentFragment = mFragmentManager.findFragmentByTag(options.tag);
            if (currentFragment != null) {
                if (USER_LOG)
                    Log.i(TAG, "Fragment with tag(" + options.tag + ") is already showing or in the back-stack.");

                // Check if this fragment has options menu.
                if (options.showOptionsMenu) {
                    currentFragment.setHasOptionsMenu(true);
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

        FragmentTransaction transaction = this.beginTransaction();

        // Apply animations to the transaction from the ShowDirection parameter.
        if (options.showDirection != ShowDirection.NONE) {
            ShowDirection dir = options.showDirection;

            /**
             * <pre>
             * There are provided 4 animations:
             * First two for currently incoming and outgoing fragment.
             * Second two for incoming and outgoing fragment from the back stack.
             * </pre>
             */
            transaction.setCustomAnimations(dir.getInAnimResID(), dir.getOutAnimResID(), dir.getInAnimBackResID(),
                    dir.getOutAnimBackResID());
        }

        if (DEBUG)
            Log.d(TAG, "onShowFragment() options = " + options.toString());

        // Fragment's view replaces the view of the container layout.
        transaction.replace(options.containerID, fragment, options.tag);

        // Add fragment to back stack if requested.
        if (options.addToBackStack) {
            transaction.addToBackStack(fragment.getTag());
            if (USER_LOG)
                Log.i(TAG, "Fragment(" + fragment + ") added to back stack under tag(" + fragment.getTag() + ")");
        }
        return onCommitTransaction(transaction, options);
    }

    /**
     * <p>
     * Invoked to finally commit created fragment transaction. Here passed transaction
     * is already set upped according to the show options.
     * </p>
     * <p>
     * This implementation commits the <var>transaction</var> and in case that the <var>options</var>
     * has set flag {@link com.wit.android.fragment.manage.FragmentController.ShowOptions#showImmediately}
     * to <code>true</code> the {@link android.support.v4.app.FragmentManager#executePendingTransactions()}
     * will be invoked too.
     * </p>
     *
     * @param transaction Final transaction to commit.
     * @param options Already processed show options.
     * @return Always <code>true</code>.
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
     * Invoked to remove fragment with the given tag from the back stack.
     * </p>
     *
     * @param fragmentTag Tag of fragment to remove.
     * @return <code>True</code> if removing was successful, <code>false</code> otherwise.
     */
    protected boolean onRemoveBackStackFragment(String fragmentTag) {
        boolean removed = false;
        // TODO: implement this
        return removed;
    }

    /**
     * <p>
     * Begins new {@link android.support.v4.app.FragmentTransaction}.
     * </p>
     *
     * @return Prepared fragment transaction.
     */
    @SuppressLint("CommitTransaction")
    protected final FragmentTransaction beginTransaction() {
        return mFragmentManager.beginTransaction();
    }

    /**
     * Private -------------------------------
     */

    /**
     * Performs showing of the given fragment using the given options.
     *
     * @param fragment Fragment to show.
     * @param options Show options for the given fragment.
     * @return <code>True</code> if showing was successful, <code>false</code> otherwise.
     */
    private boolean performShowFragment(Fragment fragment, ShowOptions options) {
        if (fragment == null) {
            if (USER_LOG)
                Log.i(TAG, "Invalid fragment instance(null) to show.");
            return false;
        }
        return onShowFragment(fragment, options);
    }

    /**
     * Performs showing of the fragment obtained from the factory with the given
     * parameters.
     *
     * @param fragmentID Id of factory fragment to show..
     * @param params Parameters for factory fragment. These parameters will
     *               be passed to the factory.
     * @return <code>True</code> if showing was successful, <code>false</code> otherwise.
     * @throws IllegalStateException If current factory isn't valid.
     */
    private boolean performShowFactoryFragment(int fragmentID, Bundle params) {
        // First obtain fragment instance then fragment tag.
        Fragment fragment = mFragmentFactory.createFragmentInstance(fragmentID, params);
        if (fragment == null) {
            // Invalid fragment instance.
            Log.e(TAG, "No such fragment instance for the requested fragment id(" + fragmentID + "). Please check your fragment factory.");
            return false;
        }

        // Resolve options.
        final ShowOptions options = mFragmentFactory.getFragmentShowOptions(fragmentID);
        return onShowFragment(fragment, (options == null) ? SHOW_OPTIONS : options);
    }

    /**
     * Performs removing of fragment with the requested tag from the back stack.
     *
     * @param fragmentTag Tag of fragment to remove.
     * @return <code>True</code> if removing was successful, <code>false</code> otherwise.
     */
    private boolean performRemoveBackStackFragment(String fragmentTag) {
        return onRemoveBackStackFragment(fragmentTag);
    }

    /**
     * Check if current fragment factory is valid.
     *
     * @throws java.lang.IllegalStateException If current factory isn't valid.
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
     * Options for fragment showing.
     * </p>
     * <h4>Default SetUp:</h4>
     * <ul>
     * <li>tag: {@link com.wit.android.fragment.manage.FragmentController#FRAGMENT_TAG}</li>
     * <li>show direction: {@link com.wit.android.fragment.manage.FragmentController.ShowDirection#NONE}</li>
     * <li>container id: <b>-1</b></li>
     * <li>back-stacking: <b>false</b></li>
     * <li>replacing same: <b>true</b></li>
     * <li>showing immediately: <b>false</b></li>
     * <li>showing options menu: <b>false</b></li>
     * </ul>
     *
     * @author Martin Albedinsky
     * @see com.wit.android.fragment.manage.FragmentController
     * @see com.wit.android.fragment.manage.FragmentController.IFragmentFactory
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
         * Flag indicating, whether same fragment (currently showing) can
         * be replaced by the new one with this options containing same tag.
         */
        protected boolean replaceSame = true;

        /**
         * Flag indicating, whether fragment should be showed immediately.
         */
        protected boolean showImmediately = false;

        /**
         * Flag indicating, whether fragment's options menu should be enabled
         * if same fragment (withs same tag) is already showing and should not be replaced.
         */
        protected boolean showOptionsMenu = false;

        /**
         * Constructors ==========================
         */

        /**
         * <p>
         * Constructs default fragment show options.
         * </p>
         */
        public ShowOptions() {
        }

        /**
         * <p>
         * Called from the parcelable creator.
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
            this.showOptionsMenu = input.readInt() == 1;
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
            dest.writeInt(showOptionsMenu ? 1 : 0);
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
            StringBuilder builder = new StringBuilder("");

            builder.append("[tag(");
            builder.append(tag);
            builder.append("), ");
            builder.append(" showDirection(");
            builder.append(showDirection.toString());
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
         * Sets fragment tag.
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
         *
         * @see com.wit.android.fragment.manage.FragmentController.ShowDirection
         */
        public ShowOptions showDirection(ShowDirection direction) {
            this.showDirection = direction;
            return this;
        }

        /**
         * <p>
         * Sets id of layout container into which should be placed the view of fragment.
         * <b>Note</b>, that this container will be used only for this fragment.
         * </p>
         *
         * @param layoutID Id of layout container.
         * @return This options.
         *
         * @see com.wit.android.fragment.manage.FragmentController#setFragmentContainerID(int)
         */
        public ShowOptions containerID(int layoutID) {
            this.containerID = layoutID;
            return this;
        }

        /**
         * <p>
         * Sets flag indicating, whether currently showing fragment with same tag can
         * be replaced by the new one.
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
         * Sets flag indicating, whether fragment should be showed immediately.
         * </p>
         *
         * @param immediately <code>True</code> to show immediately, <code>false</code> otherwise.
         * @return This options.
         */
        public ShowOptions showImmediately(boolean immediately) {
            this.showImmediately = immediately;
            return this;
        }

        /**
         * <p>
         * Sets flag indicating, whether fragment's options menu should be enabled in case
         * that, there is already same fragment (with same tag) showing and can't be
         * replaced be the new one.
         * </p>
         * <p>
         * <b>Note</b>, that this flag will be checked only if the
         * {@link #replaceSame(boolean)} was called with <code>false</code>.
         * </p>
         *
         * @param show <code>True</code>
         * @return This options.
         */
        public ShowOptions showOptionsMenu(boolean show) {
            this.showOptionsMenu = show;
            return this;
        }
    }

    /**
     * <h4>Class Overview</h4>
     * <p>
     * Handles the directions of replacing fragments in the activity content
     * view. Each type contains animations for incoming and outgoing fragment
     * and also for incoming and outgoing fragment from the back stack (when the
     * back button is clicked and the old fragment is being showed from the back
     * stack).
     * </p>
     * <p>
     * Back stack animations are in the reverse order against their fragment
     * animation partners. For example in case of {@link ShowDirection#FROM_LEFT}
     * direction are used these animations:
     * <ul>
     * <li>incoming: <var>R.anim.and_slide_fragment_in_right</var></li>
     * <li>outgoing: <var>R.anim.and_slide_fragment_out_right</var></li>
     * <li>incoming(backStack): <var>R.anim.and_slide_fragment_in_left_back</var></li>
     * <li>outgoing(backStack): <var>R.anim.and_slide_fragment_out_left_back</var></li>
     * </ul>
     * </p>
     * <p>
     * Each animation has duration set to <code>500 ms</code> and in case of back stack
     * animation to <code>350 ms</code>.
     * </p>
     */
    public static class ShowDirection implements Parcelable {
        /**
         * Constants =============================
         */

        /**
         * <p>
         * Parcelable creator.
         * </p>
         */
        public static final Creator<ShowDirection> CREATOR = new Creator<ShowDirection>() {
            @Override
            public ShowDirection createFromParcel(Parcel source) {
                return new ShowDirection(source);
            }
            @Override
            public ShowDirection[] newArray(int size) {
                return new ShowDirection[size];
            }
        };

        /**
         * <p>
         * Use this to show new incoming fragment which actually replaces the
         * current fragment without any animation.
         * </p>
         */
        public static final ShowDirection NONE = new ShowDirection(0, 0, 0, 0, "NONE");

        /**
         * <p>
         * Use this to show new incoming fragment from the left and outgoing
         * will be hided to the right.
         * </p>
         */
        public static final ShowDirection FROM_LEFT = new ShowDirection(
                // Incoming animation.
                R.anim.and_slide_fragment_in_right,
                // Outgoing animation.
                R.anim.and_slide_fragment_out_right,
                // Incoming back-stack animation.
                R.anim.and_slide_fragment_in_left_back,
                // Outgoing back-stack animation.
                R.anim.and_slide_fragment_out_left_back,
                "FROM_LEFT");

        /**
         * <p>
         * Use this to show new incoming fragment from the right and outgoing
         * will be hided to the left.
         * </p>
         */
        public static final ShowDirection FROM_RIGHT = new ShowDirection(
                // Incoming animation.
                R.anim.and_slide_fragment_in_left,
                // Outgoing animation.
                R.anim.and_slide_fragment_out_left,
                // Incoming back-stack animation.
                R.anim.and_slide_fragment_in_right_back,
                // Outgoing back-stack animation.
                R.anim.and_slide_fragment_out_right_back,
                "FROM_RIGHT");

        /**
         * <p>
         * Use this to show new incoming fragment from the top and outgoing will
         * be hided to the bottom.
         * </p>
         */
        public static final ShowDirection FROM_TOP = new ShowDirection(
                // Incoming animation.
                R.anim.and_slide_fragment_in_bottom,
                // Outgoing animation.
                R.anim.and_slide_fragment_out_bottom,
                // Incoming back-stack animation.
                R.anim.and_slide_fragment_in_top_back,
                // Outgoing back-stack animation.
                R.anim.and_slide_fragment_out_top_back,
                "FROM_TOP");

        /**
         * <p>
         * Use this to show new incoming fragment from the bottom and outgoing
         * will be hided to the top.
         * </p>
         */
        public static final ShowDirection FROM_BOTTOM = new ShowDirection(
                // Incoming animation.
                R.anim.and_slide_fragment_in_top,
                // Outgoing animation.
                R.anim.and_slide_fragment_out_top,
                // Incoming back-stack animation.
                R.anim.and_slide_fragment_in_bottom_back,
                // Outgoing back-stack animation.
                R.anim.and_slide_fragment_out_bottom_back,
                "FROM_BOTTOM");

        /**
         * Members ===============================
         */

        /**
         * Animation resource id.
         */
        private int mInAnimResID, mOutAnimResID, mInAnimBackResID, mOutAnimBackResID;

        /**
         * Name of this show direction.
         */
        private String mName = "";

        /**
         * Constructors ==========================
         */

        /**
         * <p>
         * Creates the show showDirection type with the given
         * animations. The back-stack animation resource ids will be set to
         * <code>0</code>.
         * </p>
         *
         * @param inAnim  Animation resource id for incoming new fragment.
         * @param outAnim Animation resource id for outgoing current fragment.
         */
        public ShowDirection(int inAnim, int outAnim) {
            this(inAnim, outAnim, 0, 0);
        }

        /**
         * <p>
         * Same as {@link #ShowDirection(int, int, int, int, String)}
         * with empty name.
         * </p>
         *
         * @param inAnim      Animation resource id for incoming new fragment.
         * @param outAnim     Animation resource id for outgoing fragment to the back
         *                    stack.
         * @param inAnimBack  Animation resource id for incoming fragment from the back
         *                    stack.
         * @param outAnimBack Animation resource id for outgoing current fragment.
         */
        public ShowDirection(int inAnim, int outAnim, int inAnimBack, int outAnimBack) {
            this(inAnim, outAnim, inAnimBack, outAnimBack, "");
        }

        /**
         * <p>
         * Creates the show showDirection type with the given
         * animations and name.
         * </p>
         *
         * @param inAnim      Animation resource id for incoming new fragment.
         * @param outAnim     Animation resource id for outgoing fragment to the back
         *                    stack.
         * @param inAnimBack  Animation resource id for incoming fragment from the back
         *                    stack.
         * @param outAnimBack Animation resource id for outgoing current fragment.
         * @param name        Name of the showDirection used to identify it. Also used in {@link #toString()} method.
         */
        public ShowDirection(int inAnim, int outAnim, int inAnimBack, int outAnimBack, String name) {
            this.mInAnimResID = inAnim;
            this.mOutAnimResID = outAnim;
            this.mInAnimBackResID = inAnimBack;
            this.mOutAnimBackResID = outAnimBack;
            this.mName = name;
        }

        /**
         * <p>
         * Called from the parcelable creator.
         * </p>
         *
         * @param input Parcelable source with saved data.
         */
        protected ShowDirection(Parcel input) {
            this.mInAnimResID = input.readInt();
            this.mOutAnimResID = input.readInt();
            this.mInAnimBackResID = input.readInt();
            this.mOutAnimBackResID = input.readInt();
            this.mName = input.readString();
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
        public String toString() {
            return mName;
        }

        /**
         */
        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(mInAnimResID);
            dest.writeInt(mOutAnimResID);
            dest.writeInt(mInAnimBackResID);
            dest.writeInt(mOutAnimBackResID);
            dest.writeString(mName);
        }

        /**
         */
        @Override
        public int describeContents() {
            return 0;
        }

        /**
         * Getters + Setters ---------------------
         */

        /**
         * <p>
         * Returns the id of the animation for incoming fragment for this
         * showDirection.
         * </p>
         *
         * @return Animation resource id.
         */
        public int getInAnimResID() {
            return mInAnimResID;
        }

        /**
         * <p>
         * Returns the id of the animation for outgoing fragment for this
         * showDirection.
         * </p>
         *
         * @return Animation resource id.
         */
        public int getOutAnimResID() {
            return mOutAnimResID;
        }

        /**
         * <p>
         * Returns the id of the animation for outgoing fragment for this
         * showDirection when the fragment is being hided while new one is being
         * showed from the back stack.
         * </p>
         *
         * @return Animation resource id.
         */
        public int getOutAnimBackResID() {
            return mOutAnimBackResID;
        }

        /**
         * <p>
         * Returns the id of the animation for incoming fragment for this
         * showDirection when the fragment is being showed from the back stack.
         * </p>
         *
         * @return Animation resource id.
         */
        public int getInAnimBackResID() {
            return mInAnimBackResID;
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
     * @see com.wit.android.fragment.manage.FragmentController
     */
    public static interface IFragmentFactory {

        /**
         * Methods ===============================
         */

        /**
         * <p>
         * Returns the instance of the fragment. Instance of the fragment depends on
         * the given <var>fragmentID</var>. This is invoked after call of
         * <code>showFragment(int, ...)</code> from the {@link com.wit.android.fragment.manage.FragmentController}.
         * </p>
         *
         * @param fragmentID Factory id of fragment.
         * @param params     Bundle parameters with data or <code>null</code> if there was
         *                   invoked the method to show fragment without <var>params</var>.
         * @return Instance of fragment associated with the <var>fragmentID</var> or <code>null</code> if this
         *         factory has no mapping for requested fragment id.
         */
        public Fragment createFragmentInstance(int fragmentID, Bundle params);

        /**
         * <p>
         * Returns show options associated with the fragment represented by the given
         * <var>fragmentID</var>. If here provided options are <code>null</code>, default
         * show options from {@link com.wit.android.fragment.manage.FragmentController} will be
         * used to show that fragment.
         * </p>
         *
         * @param fragmentID Factory id of fragment.
         * @return Show options for fragment associated with the <var>fragmentID</var> or <code>null</code> if this
         *         factory has no mapping for requested fragment id.
         */
        public ShowOptions getFragmentShowOptions(int fragmentID);

        /**
         * <p>
         * Returns tag associated with the fragment represented by the given <var>fragmentID</var>.
         * </p>
         *
         * @param fragmentID Factory id of fragment.
         * @return Tag for fragment associated with the <var>fragmentID</var> or <code>null</code> if this
         *         factory has no mapping for requested fragment id.
         */
        public String getFragmentTag(int fragmentID);
    }
}
