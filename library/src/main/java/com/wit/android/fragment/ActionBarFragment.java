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
package com.wit.android.fragment;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.AndroidRuntimeException;

/**
 * <h4>Class Overview</h4>
 * <p>
 * Description.
 * </p>
 * <p>
 * <b>Note, that implementation of this fragment can be used only within the context of
 * {@link android.support.v7.app.ActionBarActivity}</b>.
 * </p>
 *
 * @author Martin Albedinsky
 */
public class ActionBarFragment extends BaseFragment {

	/**
	 * Constants =============================
	 */

	/**
	 * Log TAG.
	 */
	// private static final String TAG = ActionBarFragment.class.getSimpleName();

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
	 * Constructors ==========================
	 */

	/**
	 * Methods ===============================
	 */

	/**
	 * Public --------------------------------
	 */

	/**
	 * @throws android.util.AndroidRuntimeException If the currently created parent activity isn't
	 * instance of {@link android.support.v7.app.ActionBarActivity}.
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// Check if this fragment instance is placed within ActionBarActivity context.
		if (!(getActivity() instanceof ActionBarActivity)) {
			throw new AndroidRuntimeException(
					"ActionBarFragment implementation can be used only within the context of ActionBarActivity."
			);
		}
	}

	/**
	 * <p>
	 * Same as {@link android.support.v7.app.ActionBarActivity#supportInvalidateOptionsMenu()}.
	 * </p>
	 *
	 * @see #isActionBarActivityAvailable()
	 */
	public void invalidateOptionsMenu() {
		if (isActionBarActivityAvailable()) {
			getActionBarActivity().supportInvalidateOptionsMenu();
		}
	}

	/**
	 * <p>
	 * Same as {@link android.support.v7.app.ActionBarActivity#supportRequestWindowFeature(int)}.
	 * </p>
	 *
	 * @see #getActionBar()
	 * @see #isActionBarActivityAvailable()
	 */
	public boolean requestWindowFeature(int featureID) {
		return isActionBarActivityAvailable() && getActionBarActivity().supportRequestWindowFeature(featureID);
	}

	/**
	 * Getters + Setters ---------------------
	 */

	/**
	 * <p>
	 * Same as {@link android.support.v7.app.ActionBarActivity#getSupportActionBar()}
	 * </p>
	 *
	 * @see #requestWindowFeature(int)
	 * @see #isActionBarActivityAvailable()
	 * @see #getActionBarActivity()
	 */
	public ActionBar getActionBar() {
		return isActionBarActivityAvailable() ? getActionBarActivity().getSupportActionBar() : null;
	}

	/**
	 * <p>
	 * Same as {@link android.support.v7.app.ActionBarActivity#setSupportProgress(int)}.
	 * </p>
	 *
	 * @see #setProgressBarVisibility(boolean)
	 * @see #isActionBarActivityAvailable()
	 */
	public void setProgress(int progress) {
		if (isActionBarActivityAvailable()) {
			getActionBarActivity().setSupportProgress(progress);
		}
	}

	/**
	 * <p>
	 * Same as {@link android.support.v7.app.ActionBarActivity#setSupportProgressBarVisibility(boolean)}.
	 * </p>
	 *
	 * @see #setProgress(int)
	 * @see #setProgressBarIndeterminate(boolean)
	 * @see #isActionBarActivityAvailable()
	 */
	public void setProgressBarVisibility(boolean visible) {
		if (isActionBarActivityAvailable()) {
			getActionBarActivity().setSupportProgressBarVisibility(visible);
		}
	}

	/**
	 * <p>
	 * Same as {@link android.support.v7.app.ActionBarActivity#setSupportProgressBarIndeterminate(boolean)}.
	 * </p>
	 *
	 * @see #setProgressBarIndeterminateVisibility(boolean)
	 * @see #isActionBarActivityAvailable()
	 */
	public void setProgressBarIndeterminate(boolean indeterminate) {
		if (isActionBarActivityAvailable()) {
			getActionBarActivity().setSupportProgressBarIndeterminate(indeterminate);
		}
	}

	/**
	 * <p>
	 * Same as {@link android.support.v7.app.ActionBarActivity#setSupportProgressBarIndeterminateVisibility(boolean)}.
	 * </p>
	 *
	 * @see #setProgressBarIndeterminate(boolean)
	 * @see #isActionBarActivityAvailable()
	 */
	public void setProgressBarIndeterminateVisibility(boolean visible) {
		if (isActionBarActivityAvailable()) {
			getActionBarActivity().setSupportProgressBarIndeterminateVisibility(visible);
		}
	}

	/**
	 * Protected -----------------------------
	 */

	/**
	 * <p>
	 * Returns flag indicating whether the parent ActionBarActivity of this fragment instance is
	 * available or not.
	 * </p>
	 *
	 * @return <code>True</code> if activity is available, <code>false</code> otherwise.
	 * @see #getActionBarActivity()
	 */
	protected boolean isActionBarActivityAvailable() {
		return getActionBarActivity() != null;
	}

	/**
	 * <p>
	 * Returns an instance of {@link android.support.v7.app.ActionBarActivity}.
	 * </p>
	 *
	 * @return The current parent activity of this fragment instance casted to the ActionBarActivity.
	 * @see #isActionBarActivityAvailable()
	 */
	protected ActionBarActivity getActionBarActivity() {
		return ((ActionBarActivity) getActivity());
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
