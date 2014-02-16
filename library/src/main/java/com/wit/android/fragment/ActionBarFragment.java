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

import android.app.ActionBar;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.wit.android.fragment.annotation.ActionBarIcon;
import com.wit.android.fragment.annotation.ActionBarTitle;
import com.wit.android.support.fragment.annotation.OptionsMenu;

/**
 * <h4>Class Overview</h4>
 * <p>
 * Description.
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
	 *
	 */
	private int mActionBarIcon = -1;

	/**
	 *
	 */
	private int mOptionsMenu = -1;

	/**
	 *
	 */
	private int mOptionsMenuFlags = OptionsMenu.DEFAULT;

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
	private boolean bClearOptionsMenu = true;

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
		if (classOfFragment.isAnnotationPresent(ActionBarTitle.class)) {
			this.mActionBarTitle = classOfFragment.getAnnotation(ActionBarTitle.class).value();
		}
		// Retrieve action bar icon.
		final ActionBarIcon actionBarIcon = obtainAnnotationFrom(ActionBarIcon.class, classOfFragment);
		if (actionBarIcon != null) {
			this.mActionBarIcon = actionBarIcon.value();
		}
		// Retrieve options menu.
		final OptionsMenu optionsMenu = obtainAnnotationFrom(OptionsMenu.class, classOfFragment);
		if (optionsMenu != null) {
			this.mOptionsMenu = optionsMenu.value();
			this.mOptionsMenuFlags = optionsMenu.flags();
			this.bClearOptionsMenu = optionsMenu.clear();
		}
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
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		if (mOptionsMenu > 0) {
			if (bClearOptionsMenu) {
				menu.clear();
			}
			switch (mOptionsMenuFlags) {
				case OptionsMenu.IGNORE_SUPER:
					inflater.inflate(mOptionsMenu, menu);
					break;
				case OptionsMenu.BEFORE_SUPER:
					inflater.inflate(mOptionsMenu, menu);
					super.onCreateOptionsMenu(menu, inflater);
					break;
				case OptionsMenu.DEFAULT:
					super.onCreateOptionsMenu(menu, inflater);
					inflater.inflate(mOptionsMenu, menu);
					break;
				default:
					throw new IllegalArgumentException("Unknown options menu flags(" + mOptionsMenuFlags + ").");
			}
			return;
		}
		super.onCreateOptionsMenu(menu, inflater);
	}

	/**
	 */
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		if (mActionBarTitle > 0) {
			setActionBarTitle(mActionBarTitle);
		}
		if (mActionBarIcon > 0) {
			setActionBarIcon(mActionBarIcon);
		}
	}

	/**
	 * <p>
	 * Same as {@link android.app.Activity#invalidateOptionsMenu()}.
	 * </p>
	 *
	 * @see #isActivityAvailable()
	 */
	public void invalidateOptionsMenu() {
		if (isActivityAvailable()) {
			getActivity().invalidateOptionsMenu();
		}
	}

	/**
	 * <p>
	 * Same as {@link android.app.Activity#requestWindowFeature(int)}.
	 * </p>
	 *
	 * @see #getActionBar()
	 * @see #isActivityAvailable()
	 */
	public boolean requestWindowFeature(int featureID) {
		return isActivityAvailable() && getActivity().requestWindowFeature(featureID);
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
	 * Same as {@link android.app.Activity#getActionBar()}
	 * </p>
	 *
	 * @see #requestWindowFeature(int)
	 * @see #isActivityAvailable()
	 */
	public ActionBar getActionBar() {
		return isActivityAvailable() ? getActivity().getActionBar() : null;
	}

	/**
	 * <p>
	 * Same as {@link android.app.Activity#setProgress(int)}.
	 * </p>
	 *
	 * @see #setProgressBarVisibility(boolean)
	 * @see #isActivityAvailable()
	 */
	public void setProgress(int progress) {
		if (isActivityAvailable()) {
			getActivity().setProgress(progress);
		}
	}

	/**
	 * <p>
	 * Same as {@link android.app.Activity#setProgressBarVisibility(boolean)}.
	 * </p>
	 *
	 * @see #setProgress(int)
	 * @see #setProgressBarIndeterminate(boolean)
	 * @see #isActivityAvailable()
	 */
	public void setProgressBarVisibility(boolean visible) {
		if (isActivityAvailable()) {
			getActivity().setProgressBarVisibility(visible);
		}
	}

	/**
	 * <p>
	 * Same as {@link android.app.Activity#setProgressBarIndeterminate(boolean)}.
	 * </p>
	 *
	 * @see #setProgressBarIndeterminateVisibility(boolean)
	 * @see #isActivityAvailable()
	 */
	public void setProgressBarIndeterminate(boolean indeterminate) {
		if (isActivityAvailable()) {
			getActivity().setProgressBarIndeterminate(indeterminate);
		}
	}

	/**
	 * <p>
	 * Same as {@link android.app.Activity#setProgressBarIndeterminateVisibility(boolean)}.
	 * </p>
	 *
	 * @see #setProgressBarIndeterminate(boolean)
	 * @see #isActivityAvailable()
	 */
	public void setProgressBarIndeterminateVisibility(boolean visible) {
		if (isActivityAvailable()) {
			getActivity().setProgressBarIndeterminateVisibility(visible);
		}
	}

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
