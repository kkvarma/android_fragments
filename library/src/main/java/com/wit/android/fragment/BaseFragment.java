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
package com.wit.android.fragment;

import android.os.Bundle;
import android.app.Fragment;

/**
 * <h4>Class Overview</h4>
 * <p>
 * </p>
 *
 * @author Martin Albedinsky
 * @see com.wit.android.fragment.ActionBarFragment
 */
public abstract class BaseFragment extends Fragment {

    /**
     * Constants =============================
     */

    /**
     * Log TAG.
     */
    // private static final String TAG = BaseFragment.class.getSimpleName();

    /**
     * Flag indicating whether the debug output trough log-cat is enabled or not.
     */
    // private static final boolean DEBUG = true;

    /**
     * Flag indicating whether the output for user trough log-cat is enabled or not.
     */
    // private static final boolean USER_LOG = true;

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
     * Listeners -----------------------------
     */

    /**
     * Arrays --------------------------------
     */

    /**
     * Booleans ------------------------------
     */

    /**
     * Flag indicating whether this instance of fragment is restored (like after orientation change)
     * or not.
     */
    private boolean bRestored = false;

    /**
     * Flag indicating whether the view of this instance of fragment is restored or not.
     */
    private boolean bViewRestored = false;

    /**
     * Constructors ==========================
     */

    /**
     * Methods ===============================
     */

    /**
     * Public --------------------------------
     */

    /**
     * <p>
     * Called to dispatch back press action to this fragment instance.
     * </p>
     *
     * @return <code>True</code> if this instance of fragment processes dispatched back press action,
     * <code>false</code> otherwise.
     */
    public boolean dispatchBackPress() {
        return onBackPress();
    }

    /**
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.bViewRestored = false;
        this.bRestored = savedInstanceState != null;
    }

    /**
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.bViewRestored = true;
    }

    /**
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.bViewRestored = false;
    }

	/**
	 * <p>
	 * Returns flag indicating whether this fragment instance was restored or not.
	 * </p>
	 *
	 * @return <code>True</code> if this fragment was restored (<i>like, after orientation change</i>),
	 * <code>false</code> otherwise.
	 */
	public boolean isRestored() {
		return bRestored;
	}

	/**
	 * <p>
	 * Returns flag indicating whether the view of this fragment instance was restored or not.
	 * </p>
	 *
	 * @return <code>True</code> if the view of this fragment was restored (<i>like, when the fragment
	 * was showed from the back stack</i>), <code>false</code> otherwise.
	 */
	public boolean isViewRestored() {
		return bViewRestored;
	}

	/**
	 * <p>
	 * Same as  {@link #getString(int)}, but first is performed check if the parent activity of this
	 * fragment instance is available to prevent illegal state exceptions.
	 * </p>
	 */
	public String obtainString(int resID) {
		return isActivityAvailable() ? getString(resID) : "";
	}

	/**
	 * <p>
	 * <p>
	 * Same as  {@link #getString(int, Object...)}, but first is performed check if the parent activity
	 * of this fragment instance is available to prevent illegal state exceptions.
	 * </p>
	 * </p>
	 */
	public String obtainString(int resID, Object... args) {
		return isActivityAvailable() ? getString(resID, args) : "";
	}

    /**
     * Getters + Setters ---------------------
     */

    /**
     * Protected -----------------------------
     */

	/**
	 * <p>
	 * Invoked to process back press action dispatched to this fragment instance.
	 * </p>
	 *
	 * @return <code>True</code> if this instance of fragment processes dispatched back press action,
	 * <code>false</code> otherwise.
	 * @see #dispatchBackPress()
	 */
	protected boolean onBackPress() {
		return false;
	}

	/**
	 * <p>
	 * Returns flag indicating whether the parent Activity of this fragment instance is available or not.
	 * </p>
	 *
	 * @return <code>True</code> if activity is available, <code>false</code> otherwise.
	 */
	protected boolean isActivityAvailable() {
		return getActivity() != null;
	}

    /**
     * Private -------------------------------
     */

    /**
     * Abstract methods ----------------------
     */

    /**
     * Inner classes =========================
     */

    /**
     * Interface =============================
     */
}
