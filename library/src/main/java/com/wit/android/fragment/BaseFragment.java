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
import android.support.v4.app.Fragment;

/**
 * <h4>Class Overview</h4>
 * <p>
 * </p>
 *
 * @author Martin Albedinsky
 */
public abstract class BaseFragment extends Fragment {

    /**
     * Constants =============================
     */

    /**
     * Log TAG.
     */
    private static final String TAG = BaseFragment.class.getSimpleName();

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
     *
     */
    private boolean bRestored = false;

    /**
     *
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
     * </p>
     *
     * @return
     */
    public boolean dispatchBackPress() {
        return false;
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
	 * </p>
	 *
	 * @return
	 */
	public boolean isRestored() {
		return bRestored;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 */
	public boolean isViewRestored() {
		return bViewRestored;
	}

    /**
     * Getters + Setters ---------------------
     */

    /**
     * Protected -----------------------------
     */

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
