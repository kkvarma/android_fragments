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
package com.wit.and.fragment.manage;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.wit.and.fragment.R;

import java.util.ArrayList;
import java.util.List;

/**
 * <h4>Class Overview</h4>
 * <p>
 * </p>
 *
 * @author Martin Albedinsky
 */
public class FragmentController {

    // TODO: implements parcelable for show options and showDirection

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
     * Fragment manager to handle showing fragments.
     */
    private FragmentManager mFragmentManager = null;

    private IFragmentFactory mFragmentFactory = null;

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
     * {@link #FragmentController(android.support.v4.app.FragmentManager, com.wit.and.fragment.manage.FragmentController.IFragmentFactory)}, but with
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
     * Constructs the manager to handle fragments.
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
     * </p>
     *
     * @param fragmentID
     * @return
     * @throws IllegalStateException If current factory isn't valid.
     */
    public final boolean showFragment(int fragmentID) {
        return this.showFragment(fragmentID, null);
    }

    /**
     * <p>
     * </p>
     *
     * @param fragmentID
     * @param params
     * @return
     * @throws IllegalStateException If current factory isn't valid.
     */
    public final boolean showFragment(int fragmentID, Bundle params) {
        // Check if we have fragment factory.
        return this.checkFragmentFactory() && this.performShowFactoryFragment(fragmentID, params);
    }

    /**
     * <p>
     * </p>
     *
     * @param fragment
     * @return
     */
    public final boolean showFragment(Fragment fragment) {
        return this.showFragment(fragment, SHOW_OPTIONS);
    }

    /**
     * <p>
     * </p>
     *
     * @param fragment
     * @param options
     * @return
     */
    public final boolean showFragment(Fragment fragment, ShowOptions options) {
        return this.performShowFragment(fragment, options);
    }

    /**
     * <p>
     * Same as {@link android.support.v4.app.FragmentManager#findFragmentById(int)}.
     * </p>
     *
     * @param fragmentID
     * @return
     */
    public Fragment findFragmentByID(int fragmentID) {
        return mFragmentManager.findFragmentById(fragmentID);
    }

    /**
     * <p>
     * Same as {@link android.support.v4.app.FragmentManager#findFragmentByTag(String)}.
     * </p>
     *
     * @param fragmentTag
     * @return
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
     * @param fragmentID
     * @return
     * @throws IllegalStateException If current factory isn't valid.
     */
    public Fragment getFragmentByID(int fragmentID) {
        // Check if we have fragment factory.
        return this.checkFragmentFactory() ? findFragmentByTag(mFragmentFactory.getFragmentTag(fragmentID)) : null;
    }

    /**
     * <p>
     * </p>
     *
     * @return
     */
    public Fragment getVisibleFragment() {
        if (DEBUG)
            Log.d(TAG, "Resolving current visible fragment.");

        // Get the last one or two fragments.
        // The penultimate one is required in case when the manager is currently popping
        // the back stack with fragments, in that case the last fragment will be still
        // there but invalid.
        final List<Fragment> fragments = mFragmentManager.getFragments();
        final List<Fragment> visibleFragments = new ArrayList<Fragment>();

        // Get only visible fragments.
        for (Fragment fragment : fragments) {
            // TODO: perform here more fragment checks ?
            if (fragment != null && fragment.isVisible()) {
                visibleFragments.add(fragment);

                if (DEBUG)
                    Log.d(TAG, "visible fragment(" + fragment.getTag() + ")");
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
                if (fragment == null) {
                    fragment = visibleFragments.get(size - 2);
                }
        }
        if (DEBUG) {
            Log.d(TAG, "Resolved visible fragment(" + fragment + ")");
        }
        return fragment;
    }

    /**
     * <p>
     * </p>
     *
     * @return
     */
    public Fragment getVisibleSecondFragment() {
        if (DEBUG)
            Log.d(TAG, "Resolving current second visible fragment.");

        // Get the last two or three fragments.
        // The before the penultimate one is required in case when the manager is currently popping
        // the back stack with fragments, in that case the last fragment will be still
        // there but invalid.
        final List<Fragment> fragments = mFragmentManager.getFragments();
        final List<Fragment> visibleFragments = new ArrayList<Fragment>();

        // Get only visible fragments.
        for (Fragment fragment : fragments) {
            // TODO: perform here more fragment checks ?
            if (fragment != null && fragment.isVisible()) {
                visibleFragments.add(fragment);

                if (DEBUG)
                    Log.d(TAG, "visible fragment(" + fragment.getTag() + ")");
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
                // Get the first visible one.
                final Fragment firstFragment = visibleFragments.get(size - 1);
                if (firstFragment == null) {
                    secondFragment = visibleFragments.get(size - 3);
                } else {
                    secondFragment = visibleFragments.get(size - 2);
                }
        }
        if (DEBUG) {
            Log.d(TAG, "Resolved second visible fragment(" + secondFragment + ")");
        }
        return secondFragment;
    }

    /**
     * <p>
     * </p>
     */
    public void hideVisibleFragment() {
        mFragmentManager.popBackStack();
    }

    /**
     * <p>
     * </p>
     *
     * @return
     */
    public boolean hideVisibleFragmentImmediate() {
        return mFragmentManager.popBackStackImmediate();
    }

    /**
     * <p>
     * </p>
     *
     * @param fragmentID
     * @return
     */
    public boolean removeFragmentFromBackStack(int fragmentID) {
        // Check if we have fragment factory.
        return this.checkFragmentFactory() && performRemoveBackStackFactoryFragment(fragmentID);
    }

    /**
     * <p>
     * </p>
     *
     * @param fragmentTag
     * @return
     */
    public boolean removeFragmentFromBackStack(String fragmentTag) {
        return performRemoveBackStackFragment(fragmentTag);
    }

    /**
     * Getters + Setters ---------------------
     */

    /**
     * <p>
     * </p>
     *
     * @return
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
     * </p>
     *
     * @param layoutID
     */
    public final void setFragmentContainerID(int layoutID) {
        this.mFragmentContainerID = layoutID;
    }

    /**
     * <p>
     * </p>
     *
     * @return
     */
    public final int getFragmentContainerID() {
        return mFragmentContainerID;
    }

    /**
     * <p>
     * </p>
     *
     * @param factory
     */
    public final void setFragmentFactory(IFragmentFactory factory) {
        this.mFragmentFactory = factory;
    }

    /**
     * <p>
     * </p>
     *
     * @return
     */
    public final IFragmentFactory getFragmentFactory() {
        return mFragmentFactory;
    }

    /**
     * <p>
     * Dispatches restoring of the fragment controller saved state.
     * </p>
     *
     * @param savedInstanceState
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
     * @param outState
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
     * @param savedInstanceState
     */
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    }

    /**
     * <p>
     * Derived class should here save its own state. This implementation does nothing.
     * </p>
     *
     * @param outState
     */
    protected void onSaveInstanceState(Bundle outState) {
    }

    /**
     * <p>
     * Returns support fragment manager.
     * </p>
     *
     * @return
     */
    protected final FragmentManager getFragmentManager() {
        return mFragmentManager;
    }

    /**
     * <p>
     * </p>
     *
     * @param fragment
     * @param options
     * @return
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

        // Commit transaction.
        transaction.commit();

        if (options.showImmediately) {
            mFragmentManager.executePendingTransactions();
        }
        return true;
    }

    /**
     * <p>
     * </p>
     *
     * @param fragmentTag
     * @return
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
     * @return
     */
    @SuppressLint("CommitTransaction")
    protected final FragmentTransaction beginTransaction() {
        return mFragmentManager.beginTransaction();
    }

    /**
     * Private -------------------------------
     */

    /**
     * Performs showing of the given fragment with the given parameters.
     *
     * @param fragment
     * @param options
     * @return
     */
    private boolean performShowFragment(Fragment fragment, ShowOptions options) {
        if (fragment == null) {
            if (USER_LOG)
                Log.i(TAG, "Invalid fragment instance(" + fragment + ") to show.");
            return false;
        }
        return onShowFragment(fragment, options);
    }

    /**
     * Performs showing of the fragment obtained from the factory with the given
     * parameters.
     *
     * @param fragmentID
     * @param params
     * @return
     * @throws IllegalStateException
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
     * @param fragmentID
     * @return
     */
    private boolean performRemoveBackStackFactoryFragment(int fragmentID) {
        // TODO: implement this
        return false;
    }

    /**
     * @param fragmentTag
     * @return
     */
    private boolean performRemoveBackStackFragment(String fragmentTag) {
        // TODO: implement this
        return onRemoveBackStackFragment(fragmentTag);
    }

    /**
     *
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
     * </p>
     *
     * @author Martin Albedinsky
     */
    public static class ShowOptions {

        /**
         * Members ===============================
         */

        /**
         *
         */
        String tag = FRAGMENT_TAG;

        /**
         *
         */
        ShowDirection showDirection = ShowDirection.NONE;

        /**
         *
         */
        int containerID = -1;

        /**
         * Booleans ------------------------------
         */

        /**
         *
         */
        boolean addToBackStack = false;

        /**
         *
         */
        boolean replaceSame = true;

        /**
         *
         */
        boolean showImmediately = false;

        /**
         *
         */
        boolean showOptionsMenu = false;

        /**
         * Constructors ==========================
         */

        /**
         * <br/>
         * <h5><i>public ShowOptions()</i></h5>
         * <p>
         * Constructs default show fragment options.
         * </p>
         */
        public ShowOptions() {
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
         * </p>
         *
         * @param fragmentTag
         * @return This options.
         */
        public ShowOptions tag(String fragmentTag) {
            this.tag = fragmentTag;
            return this;
        }

        /**
         * <p>
         * </p>
         *
         * @param add
         * @return This options.
         */
        public ShowOptions addToBackStack(boolean add) {
            this.addToBackStack = add;
            return this;
        }

        /**
         * <p>
         * </p>
         *
         * @param direction
         * @return This options.
         */
        public ShowOptions showDirection(ShowDirection direction) {
            this.showDirection = direction;
            return this;
        }

        /**
         * <p>
         * </p>
         *
         * @param layoutID
         * @return This options.
         */
        public ShowOptions containerID(int layoutID) {
            this.containerID = layoutID;
            return this;
        }

        /**
         * <p>
         * </p>
         *
         * @param replace
         * @return This options.
         */
        public ShowOptions replaceSame(boolean replace) {
            this.replaceSame = replace;
            return this;
        }

        /**
         * <p>
         * </p>
         *
         * @param immediately
         * @return This options.
         */
        public ShowOptions showImmediately(boolean immediately) {
            this.showImmediately = immediately;
            return this;
        }

        /**
         * <p>
         * </p>
         *
         * @param show
         * @return This options
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
     * also for incoming and outgoing fragment from the back stack (when the
     * back button is clicked and the old fragment is showed from the back
     * stack).
     * </p>
     * <p>
     * Back stack animations are in the reverse state against their fragment
     * animation partners. For example in case of <var>FROM_LEFT</var> direction
     * are used these animations:
     * <ul>
     * <li>incoming: <var>R.anim.and_slide_fragment_in_right</var></li>
     * <li>outgoing: <var>R.anim.and_slide_fragment_out_right</var></li>
     * <li>incoming(backStack): <var>R.anim.and_slide_fragment_in_left_back</var></li>
     * <li>outgoing(backStack): <var>R.anim.and_slide_fragment_out_left_back</var></li>
     * </ul>
     * </p>
     * <p>
     * Each animation has duration set to <code>500 ms</code>.
     * </p>
     */
    public static class ShowDirection {
        /**
         * <h5><i>public static final ShowDirection NONE</i></h5>
         * <p>
         * Use this to show new incoming fragment which actually replaces the
         * current fragment without any animation.
         * </p>
         */
        public static final ShowDirection NONE = new ShowDirection(0, 0, 0, 0, "NONE");

        /**
         * <h5><i>public static final ShowDirection FROM_LEFT</i></h5>
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
         * <h5><i>public static final ShowDirection FROM_RIGHT</i></h5>
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
         * <h5><i>public static final ShowDirection FROM_TOP</i></h5>
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
         * <h5><i>public static final ShowDirection FROM_BOTTOM</i></h5>
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
         * Animation resource ids.
         */

        /**
         */
        private int mInAnimResID = 0;

        private int mOutAnimResID = 0;

        private int mInAnimBackResID = 0;

        private int mOutAnimBackResID = 0;

        private String mName = "";

        /**
         * <h5><i>public ShowDirection(int inAnim, int outAnim)</i></h5>
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
         * <h5><i>public ShowDirection(int inAnim, int outAnim, int inAnimBack,
         * int outAnimBack)</i></h5>
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
         * <h5><i>public ShowDirection(int inAnim, int outAnim, int inAnimBack,
         * int outAnimBack, String name)</i></h5>
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
         * Getters + Setters ---------------------
         */

        /**
         * <h5><i>public int getInAnimResID()</i></h5>
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
         * <h5><i>public int getOutAnimResID()</i></h5>
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
         * <h5><i>public int getOutAnimBackResID()</i></h5>
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
         * <h5><i>public int getInAnimBackResID()</i></h5>
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
     * </p>
     *
     * @author Martin Albedinsky
     * @see com.wit.and.fragment.manage.FragmentController
     */
    public static interface IFragmentFactory {

        /**
         * Methods ===============================
         */

        /**
         * <p>
         * Returns the instance of the fragment. Instance of the fragment depends on
         * the given <var>fragmentID</var>. This is invoked after call of
         * <code>showFragment(int, ...)</code> from the {@link com.wit.and.fragment.manage.FragmentController}.
         * </p>
         *
         * @param fragmentID Factory id of fragment.
         * @param params     Bundle parameters with data or <code>null</code> if there was
         *                   invoked the method to show fragment without <var>params</var>.
         * @return
         */
        public Fragment createFragmentInstance(int fragmentID, Bundle params);

        /**
         * <p>
         * Returns show options associated with the dialog fragment with the given
         * <var>fragmentID</var>. If here provided options are <code>null</code>, default
         * show options from {@link com.wit.and.fragment.manage.FragmentController} will be
         * used to show that fragment.
         * </p>
         *
         * @param fragmentID Factory id of fragment.
         * @return
         */
        public ShowOptions getFragmentShowOptions(int fragmentID);

        /**
         * <p>
         * Returns tag associated with the fragment with the given <var>fragmentID</var>.
         * </p>
         *
         * @param fragmentID Factory id of fragment.
         */
        public String getFragmentTag(int fragmentID);
    }
}
