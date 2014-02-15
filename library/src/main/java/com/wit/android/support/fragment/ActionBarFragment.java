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
package com.wit.android.support.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.AndroidRuntimeException;

import com.wit.android.support.fragment.annotation.ActionBarTitle;

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
	 *
	 */
	private int mActionBarTitle = -1;

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
	 * </p>
	 */
	public ActionBarFragment() {
		super();
		final Class<? extends ActionBarFragment> classOfFragment = getClass();
		/**
		 * Process class annotations.
		 */
		// Retrieve action bar title.
		final ActionBarTitle actionBarTitle = this.obtainActionBarTitleFrom(classOfFragment);
		if (actionBarTitle != null) {
			this.mActionBarTitle = actionBarTitle.value();
		}
	}

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
	 * @see #isActivityAvailable()
	 */
	public void invalidateOptionsMenu() {
		if (isActivityAvailable()) {
			getActionBarActivity().supportInvalidateOptionsMenu();
		}
	}

	/**
	 * <p>
	 * Same as {@link android.support.v7.app.ActionBarActivity#supportRequestWindowFeature(int)}.
	 * </p>
	 *
	 * @see #getActionBar()
	 * @see #isActivityAvailable()
	 */
	public boolean requestWindowFeature(int featureID) {
		return isActivityAvailable() && getActionBarActivity().supportRequestWindowFeature(featureID);
	}

	/**
	 * Getters + Setters ---------------------
	 */

	/**
	 * <p>
	 * </p>
	 *
	 * @param resID
	 */
	public void setActionBarTitle(int resID) {
		if (isActivityAvailable()) {
			getActionBar().setTitle(resID);
		}
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param title
	 */
	public void setActionBarTitle(CharSequence title) {
		if (isActivityAvailable()) {
			getActionBar().setTitle(title);
		}
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param resID
	 */
	public void setActionBarIcon(int resID) {
		if (isActivityAvailable()) {
			getActionBar().setIcon(resID);
		}
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param icon
	 */
	public void setActionBarIcon(Drawable icon) {
		if (isActivityAvailable()) {
			getActionBar().setIcon(icon);
		}
	}

	/**
	 * <p>
	 * Same as {@link android.support.v7.app.ActionBarActivity#getSupportActionBar()}
	 * </p>
	 *
	 * @see #requestWindowFeature(int)
	 * @see #isActivityAvailable()
	 * @see #getActionBarActivity()
	 */
	public ActionBar getActionBar() {
		return isActivityAvailable() ? getActionBarActivity().getSupportActionBar() : null;
	}

	/**
	 * <p>
	 * Same as {@link android.support.v7.app.ActionBarActivity#setSupportProgress(int)}.
	 * </p>
	 *
	 * @see #setProgressBarVisibility(boolean)
	 * @see #isActivityAvailable()
	 */
	public void setProgress(int progress) {
		if (isActivityAvailable()) {
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
	 * @see #isActivityAvailable()
	 */
	public void setProgressBarVisibility(boolean visible) {
		if (isActivityAvailable()) {
			getActionBarActivity().setSupportProgressBarVisibility(visible);
		}
	}

	/**
	 * <p>
	 * Same as {@link android.support.v7.app.ActionBarActivity#setSupportProgressBarIndeterminate(boolean)}.
	 * </p>
	 *
	 * @see #setProgressBarIndeterminateVisibility(boolean)
	 * @see #isActivityAvailable()
	 */
	public void setProgressBarIndeterminate(boolean indeterminate) {
		if (isActivityAvailable()) {
			getActionBarActivity().setSupportProgressBarIndeterminate(indeterminate);
		}
	}

	/**
	 * <p>
	 * Same as {@link android.support.v7.app.ActionBarActivity#setSupportProgressBarIndeterminateVisibility(boolean)}.
	 * </p>
	 *
	 * @see #setProgressBarIndeterminate(boolean)
	 * @see #isActivityAvailable()
	 */
	public void setProgressBarIndeterminateVisibility(boolean visible) {
		if (isActivityAvailable()) {
			getActionBarActivity().setSupportProgressBarIndeterminateVisibility(visible);
		}
	}

	/**
	 * Protected -----------------------------
	 */

	/**
	 * <p>
	 * Returns an instance of {@link android.support.v7.app.ActionBarActivity}.
	 * </p>
	 *
	 * @return The current parent activity of this fragment instance casted to the ActionBarActivity.
	 * @see #isActivityAvailable()
	 */
	protected ActionBarActivity getActionBarActivity() {
		return ((ActionBarActivity) getActivity());
	}

	/**
	 * Private -------------------------------
	 */

	/**
	 *
	 * @param classOfFragment
	 * @return
	 */
	private ActionBarTitle obtainActionBarTitleFrom(Class<?> classOfFragment) {
		if (!classOfFragment.isAnnotationPresent(ActionBarTitle.class)) {
			final Class<?> parent = classOfFragment.getSuperclass();
			if (parent != null) {
				return obtainActionBarTitleFrom(parent);
			}
		}
		return classOfFragment.getAnnotation(ActionBarTitle.class);
	}

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
