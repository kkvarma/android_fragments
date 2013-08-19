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
package com.wit.and.app.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.wit.and.R;

/**
 * <p>
 * public class
 * </p>
 * <h5>FragmentController</h5>
 * <p>
 * </p>
 * <h4>Class Overview</h4>
 * <p>
 * </p>
 * 
 * @author Martin Albedinsky
 */
public class FragmentController {

    // TODO: save last show option to determine back stacked fragment
    // TODO: implements parcelable for show options and direction
    // TODO: dispatch back pressed to handle removing fragments -> notify listeners with fragment tag or id.

	/**
	 * Constants =============================
	 */

    /**
     * Log TAG.
     */
    private static final String TAG = FragmentController.class.getSimpleName();

    /**
     * Indicates if debug private output trough log-cat is enabled.
     */
    private static final boolean DEBUG = true;

    /**
     * Indicates if logging for user output trough log-cat is enabled.
     */
    private static final boolean USER_LOG = true;

	/**
	 * <h5><i>public static final String FRAGMENT_TAG</i></h5>
	 * <p>
	 * Default tag for the showing on fragments.
	 * </p>
	 */
	public static final String FRAGMENT_TAG = "com.wit.and.app.fragment.FragmentController.Fragment.TAG";

	/**
	 * <br/>
	 * <h5><i>public static final ShowOptions SHOW_OPTIONS</i></h5>
	 * <p>
	 * Default options to show fragment.
	 * </p>
	 */
	public static final ShowOptions SHOW_OPTIONS = new ShowOptions();

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
	 * <br/>
	 * <h5><i>public FragmentController(FragmentManager fragmentManager)</i></h5>
	 * <p>
	 * Same as
	 * {@link #FragmentController(FragmentManager, IFragmentFactory)}, but with
	 * not initialized factory.
	 * </p>
	 * 
	 * @param fragmentManager
	 *            Support fragment manager.
	 */
	public FragmentController(FragmentManager fragmentManager) {
		this(fragmentManager, null);
	}

	/**
	 * <br/>
	 * <h5><i>public FragmentController(FragmentManager fragmentManager,
	 * IFragmentFactory factory)</i></h5>
	 * <p>
	 * Constructs the manager to handle fragments.
	 * </p>
	 * <p>
	 * <b>Note: </b>always use {@link FragmentManager} provided by
	 * {@link FragmentActivity}.
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
	 * @param fragmentManager
	 *            Support fragment manager.
	 * @param factory
	 *            Fragment factory to provide fragment instances.
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
	 * <br/>
	 * <h5><i></i></h5>
	 * <p>
	 * </p>
	 * 
	 * @param fragmentID
	 * @return
	 * @throws IllegalStateException
	 */
	public final boolean showFragment(int fragmentID) {
		return this.showFragment(fragmentID, SHOW_OPTIONS, null);
	}

	/**
	 * <br/>
	 * <h5><i>public final boolean showFragment(int fragmentID, Bundle
	 * params)</i></h5>
	 * <p>
	 * </p>
	 * 
	 * @param fragmentID
	 * @param params
	 * @return
	 * @throws IllegalStateException
	 */
	public final boolean showFragment(int fragmentID, Bundle params) {
		return this.showFragment(fragmentID, SHOW_OPTIONS, params);
	}

	/**
	 * <br/>
	 * <h5><i>public final boolean showFragment(int fragmentID, ShowOptions
	 * options)</i></h5>
	 * <p>
	 * </p>
	 * 
	 * @param fragmentID
	 * @param options
	 * @return
	 * @throws IllegalStateException
	 */
	public final boolean showFragment(int fragmentID, ShowOptions options) {
		return this.showFragment(fragmentID, options, null);
	}

	/**
	 * <br/>
	 * <h5><i>public final boolean showFragment(int fragmentID, ShowOptions
	 * options, Bundle params)</i></h5>
	 * <p>
	 * </p>
	 * 
	 * @param fragmentID
	 * @param options
	 * @param params
	 * @return
	 * @throws IllegalStateException
	 */
	public final boolean showFragment(int fragmentID, ShowOptions options, Bundle params) {
		return this.performShowFactoryFragment(fragmentID, options, params);
	}

	/**
	 * <br/>
	 * <h5><i>public final boolean showFragment(Fragment fragment)</i></h5>
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
	 * <br/>
	 * <h5><i>public final boolean showFragment(Fragment fragment, ShowOptions
	 * options)</i></h5>
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
	 * <br/>
	 * <h5><i></i></h5>
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public Fragment getVisibleFragment() {
		return mFragmentManager.findFragmentByTag(getVisibleFragmentTag());
	}

	/**
	 * <br/>
	 * <h5><i></i></h5>
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public boolean hideVisibleFragment() {
		boolean hided = false;

		// TODO: implement this

		return hided;
	}

	/**
	 * 
	 * @param fragmentID
	 * @return
	 */
	public boolean removeFragmentFromBackStack(int fragmentID) {
		return performRemoveBackStackFactoryFragment(fragmentID);
	}

	/**
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
	 * <br/>
	 * <h5><i></i></h5>
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public String getVisibleFragmentTag() {
		// TODO: implement this
		return null;
	}

	/**
	 * <br/>
	 * <h5><i>public final void setFragmentManager(FragmentManager
	 * fragmentManager)</i></h5>
	 * <p>
	 * Sets the fragment manager to handle showing fragments.
	 * </p>
	 * <b>Note: </b>always use {@link FragmentManager} provided by fragment
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
	 * @param fragmentManager
	 *            Support fragment manager.
	 */
	public final void setFragmentManager(FragmentManager fragmentManager) {
		this.mFragmentManager = fragmentManager;
	}

	/**
	 * <br/>
	 * <h5><i></i></h5>
	 * <p>
	 * </p>
	 * 
	 * @param layoutID
	 */
	public final void setFragmentContainerID(int layoutID) {
		this.mFragmentContainerID = layoutID;
	}

	/**
	 * <br/>
	 * <h5><i></i></h5>
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public final int getFragmentContainerID() {
		return mFragmentContainerID;
	}

	/**
	 * <br/>
	 * <h5><i></i></h5>
	 * <p>
	 * </p>
	 * 
	 * @param factory
	 */
	public final void setFragmentFactory(IFragmentFactory factory) {
		this.mFragmentFactory = factory;
	}

	/**
	 * <br/>
	 * <h5><i></i></h5>
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public final IFragmentFactory getFragmentFactory() {
		return mFragmentFactory;
	}

    /**
     * <br/>
     * <h5><i>public final void dispatchRestoreInstanceState(Bundle savedInstanceState)</i></h5>
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
     * <br/>
     * <h5><i>public final void dispatchSaveInstanceState(Bundle outState)</i></h5>
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
     * <br/>
     * <h5><i>protected void onRestoreInstanceState(Bundle savedInstanceState)</i></h5>
     * <p>
     * Derived class should restored here its own saved state. This implementation does nothing.
     * </p>
     *
     * @param savedInstanceState
     */
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    }

    /**
     * <br/>
     * <h5><i>protected void onSaveInstanceState(Bundle outState)</i></h5>
     * <p>
     * Derived class should here save its own state. This implementation does nothing.
     * </p>
     *
     * @param outState
     */
    protected void onSaveInstanceState(Bundle outState) {
    }

	/**
	 * <br/>
	 * <h5><i>protected final FragmentManager getFragmentManager()</i></h5>
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
	 * <br/>
	 * <h5><i>protected boolean onShowFragment(Fragment fragment, ShowOptions
	 * options)</i></h5>
	 * <p>
	 * </p>
	 * 
	 * @param fragment
	 * @param options
	 * @return
	 */
	protected boolean onShowFragment(Fragment fragment, ShowOptions options) {
		boolean showed = false;

		if (options == null) {
			// Use default options.
			options = SHOW_OPTIONS;
		}

        if (!options.replaceSame) {
            // Do not replace same fragment.
            Fragment oldFragment = mFragmentManager.findFragmentByTag(options.tag);
            if (oldFragment != null) {
                if (USER_LOG)
                    Log.i(TAG, "Fragment with tag(" + options.tag + ") is already showing.");
                return showed;
            }
        }

		// Check if we have place where the fragment should be placed.
		if (options.containerID == -1) {
			if (mFragmentContainerID == -1) {
				// No id provided for the layout where should be fragment
				// placed.
				throw new IllegalStateException("There is no id provided for the layout container into which should be new fragment placed.");
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
		showed = true;

		return showed;
	}

	protected boolean onRemoveBackStackFragment(String fragmentTag) {
		boolean removed = false;
		// TODO: implement this
		return removed;
	}

	/**
	 * <br/>
	 * <h5><i>protected final FragmentTransaction beginTransaction()</i></h5>
	 * <p>
	 * Begins new {@link FragmentTransaction}.
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
	 * @param options
	 * @param params
	 * @return
	 * @throws IllegalStateException
	 */
	private boolean performShowFactoryFragment(int fragmentID, ShowOptions options, Bundle params) {
		// Check if we have dialog factory.
		if (mFragmentFactory == null) {
			throw new IllegalStateException("No fragment factory found. Please set factory before you use showFragment(int, ...) methods");
		}

		// First obtain fragment instance then fragment tag.
		Fragment fragment = mFragmentFactory.createFragmentInstance(fragmentID, params);
		if (fragment == null) {
			// Invalid fragment instance.
			Log.e(TAG, "No such fragment instance for the requested fragment id(" + fragmentID + "). Please check your fragment factory.");
			return false;
		}
		String tag = mFragmentFactory.getFragmentTag(fragmentID);
		if (tag == null && options == null) {
			options = SHOW_OPTIONS;
			options.tag = tag;
		}

		return onShowFragment(null, options);
	}

	private boolean performRemoveBackStackFactoryFragment(int fragmentID) {
		// TODO: implement this
		return false;
	}

	private boolean performRemoveBackStackFragment(String fragmentTag) {
		// TODO: implement this
		return onRemoveBackStackFragment(fragmentTag);
	}

	/**
	 * Abstract methods ----------------------
	 */

	/**
	 * Inner classes =========================
	 */

	/**
	 * <p>
	 * public static class
	 * </p>
	 * <h5>ShowOptions</h5>
	 * <p>
	 * </p>
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
		boolean addToBackStack = true;

        boolean replaceSame = true;

		/**
		 * 
		 */
		ShowDirection showDirection = ShowDirection.NONE;

		int containerID = -1;

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

            builder.append("[");
            builder.append(" tag(");
            builder.append(tag);
            builder.append("), ");
            builder.append(" direction(");
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
            builder.append(") ");
            builder.append("]");

            return builder.toString();
        }

        /**
		 * Getters + Setters ---------------------
		 */

		/**
		 * <br/>
		 * <h5><i>public ShowOptions tag(String fragmentTag)</i></h5>
		 * <p>
		 * </p>
		 * 
		 * @param fragmentTag
		 * @return
		 */
		public ShowOptions tag(String fragmentTag) {
			this.tag = fragmentTag;
			return this;
		}

		/**
		 * <br/>
		 * <h5><i>public ShowOptions addToBackStack(boolean add)</i></h5>
		 * <p>
		 * </p>
		 * 
		 * @param add
		 * @return
		 */
		public ShowOptions addToBackStack(boolean add) {
			this.addToBackStack = add;
			return this;
		}

		/**
		 * <br/>
		 * <h5><i>public ShowOptions direction(ShowDirection direction)</i></h5>
		 * <p>
		 * </p>
		 * 
		 * @param direction
		 * @return
		 */
		public ShowOptions direction(ShowDirection direction) {
			this.showDirection = direction;
			return this;
		}

		/**
		 * <br/>
		 * <h5><i>public ShowOptions containerID(int layoutID)</i></h5>
		 * <p>
		 * </p>
		 * 
		 * @param layoutID
		 * @return
		 */
		public ShowOptions containerID(int layoutID) {
			this.containerID = layoutID;
			return this;
		}

        /**
         * <br/>
         * <h5><i>public ShowOptions replaceSame(boolean replace)</i></h5>
         * <p>
         * </p>
         *
         * @param replace
         * @return
         */
        public ShowOptions replaceSame(boolean replace) {
            this.replaceSame = replace;
            return this;
        }
	}

	/**
	 * <p>
	 * public static class
	 * </p>
	 * <h5>ShowDirection</h5>
	 * <p>
	 * Handles the directions of replacing fragments in the activity content
	 * view. Each type contains animations for incoming and outgoing fragment
	 * also for incoming and outgoing fragment from the back stack (when the
	 * back button is clicked and the old fragment is showed from the back
	 * stack).
	 * </p>
	 * <p>
	 * Back stack animations are in the reverse state against their dialog
	 * animation partners. For example in case of <var>FROM_LEFT</var> direction
	 * are used these animations:
	 * <ul>
	 * <li>incoming: <var>R.anim.and_slide_fragment_in_right</var></li>
	 * <li>outgoing: <var>R.anim.and_slide_fragment_out_right</var></li>
	 * <li>incoming(backStack): <var>R.anim.and_slide_fragment_in_left</var></li>
	 * <li>outgoing(backStack): <var>R.anim.and_slide_fragment_out_left</var></li>
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
		public static final ShowDirection FROM_LEFT = new ShowDirection(R.anim.and_slide_fragment_in_right, R.anim.and_slide_fragment_out_right,
				R.anim.and_slide_fragment_in_left, R.anim.and_slide_fragment_out_left, "FROM_LEFT");

		/**
		 * <h5><i>public static final ShowDirection FROM_RIGHT</i></h5>
		 * <p>
		 * Use this to show new incoming fragment from the right and outgoing
		 * will be hided to the left.
		 * </p>
		 */
		public static final ShowDirection FROM_RIGHT = new ShowDirection(R.anim.and_slide_fragment_in_left, R.anim.and_slide_fragment_out_left,
				R.anim.and_slide_fragment_in_right, R.anim.and_slide_fragment_out_right, "FROM_RIGHT");

		/**
		 * <h5><i>public static final ShowDirection FROM_TOP</i></h5>
		 * <p>
		 * Use this to show new incoming fragment from the top and outgoing will
		 * be hided to the bottom.
		 * </p>
		 */
		public static final ShowDirection FROM_TOP = new ShowDirection(R.anim.and_slide_fragment_in_bottom, R.anim.and_slide_fragment_out_bottom,
				R.anim.and_slide_fragment_in_top, R.anim.and_slide_fragment_out_top, "FROM_TOP");

		/**
		 * <h5><i>public static final ShowDirection FROM_BOTTOM</i></h5>
		 * <p>
		 * Use this to show new incoming fragment from the bottom and outgoing
		 * will be hided to the top.
		 * </p>
		 */
		public static final ShowDirection FROM_BOTTOM = new ShowDirection(R.anim.and_slide_fragment_in_top, R.anim.and_slide_fragment_out_top,
				R.anim.and_slide_fragment_in_bottom, R.anim.and_slide_fragment_out_bottom, "FROM_BOTTOM");

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
		 * Creates the show direction type with the given
		 * animations. The back-stack animation resource ids will be set to
		 * <code>0</code>.
		 * </p>
		 * 
		 * @param inAnim
		 *            Animation resource id for incoming new fragment.
		 * @param outAnim
		 *            Animation resource id for outgoing current fragment.
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
		 * @param inAnim
		 *            Animation resource id for incoming new fragment.
		 * @param outAnim
		 *            Animation resource id for outgoing fragment to the back
		 *            stack.
		 * @param inAnimBack
		 *            Animation resource id for incoming fragment from the back
		 *            stack.
		 * @param outAnimBack
		 *            Animation resource id for outgoing current fragment.
		 */
		public ShowDirection(int inAnim, int outAnim, int inAnimBack, int outAnimBack) {
            this(inAnim, outAnim, inAnimBack, outAnimBack, "");
        }

        /**
         * <h5><i>public ShowDirection(int inAnim, int outAnim, int inAnimBack,
         * int outAnimBack, String name)</i></h5>
         * <p>
         * Creates the show direction type with the given
         * animations and name.
         * </p>
         *
         * @param inAnim
         *            Animation resource id for incoming new fragment.
         * @param outAnim
         *            Animation resource id for outgoing fragment to the back
         *            stack.
         * @param inAnimBack
         *            Animation resource id for incoming fragment from the back
         *            stack.
         * @param outAnimBack
         *            Animation resource id for outgoing current fragment.
         * @param name
         *            Name of the direction used to identify it. Also used in {@link #toString()} method.
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
		 * direction.
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
		 * direction.
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
		 * direction when the fragment is being hided while new one is being
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
		 * direction when the fragment is being showed from the back stack.
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
     * <p>
     * public static interface
     * </p>
     * <h5>IFragmentFactory</h5>
     * <p>
     * </p>
     * <h4>Interface Overview</h4>
     * <p>
     * </p>
     *
     * @author Martin Albedinsky
     * @see FragmentController
     */
    public static interface IFragmentFactory {

        /**
         * Methods ===============================
         */

        /**
         * <br/>
         * <h5><i>public abstract Fragment createFragmentInstance(int fragmentID,
         * Bundle params)</i></h5>
         * <p>
         * Returns the instance of the fragment. Instance of the fragment depends on
         * the given <var>fragmentID</var>. This is invoked after call of
         * <code>showFragment(int, ...)</code> from the {@link FragmentController}.
         * </p>
         *
         * @param fragmentID
         *          Id of fragment.
         * @param params
         *            Bundle parameters with data or <code>null</code> if there was
         *            invoked the method to show fragment without <var>params</var>.
         */
        public Fragment createFragmentInstance(int fragmentID, Bundle params);

        /**
         * <br/>
         * <h5><i>public abstract String getFragmentTag(int fragmentID)</i></h5>
         * <p>
         * Returns tag associated with the dialog fragment with the given
         * <var>fragmentID</var>. If here provided tag is <code>null</code>, default
         * tag from {@link FragmentController} will be used to show actual fragment.
         * </p>
         *
         * @param fragmentID
         */
        public String getFragmentTag(int fragmentID);
    }
}
