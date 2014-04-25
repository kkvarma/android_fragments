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
package com.wit.android.support.fragment;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.AndroidRuntimeException;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.wit.android.support.fragment.annotation.ActionBarOptions;
import com.wit.android.support.fragment.annotation.MenuOptions;
import com.wit.android.support.fragment.util.FragmentAnnotations;

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
	 * Constants ===================================================================================
	 */

	/**
	 * Log TAG.
	 */
	// private static final String TAG = ActionBarFragment.class.getSimpleName();

	/**
	 * Flag indicating whether the debug output trough log-cat is enabled or not.
	 */
	// private static final boolean DEBUG_ENABLED = true;

	/**
	 * Flag indicating whether the output trough log-cat is enabled or not.
	 */
	// private static final boolean LOG_ENABLED = true;

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
	 *
	 */
	private ActionBarOptions mActionBarOptions;

	/**
	 *
	 */
	private MenuOptions mMenuOptions;

	/**
	 * Action bar obtained from the parent activity of this fragment. Can be accessed immediately from
	 * {@link #onCreate(android.os.Bundle)}.
	 */
	private ActionBar mActionBar;

	/**
	 * Listeners -----------------------------------------------------------------------------------
	 */

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
	 * </p>
	 */
	public ActionBarFragment() {
		super();
		final Class<?> classOfFragment = ((Object) this).getClass();
		/**
		 * Process class annotations.
		 */
		// Retrieve action bar options.
		if (classOfFragment.isAnnotationPresent(ActionBarOptions.class)) {
			this.mActionBarOptions = classOfFragment.getAnnotation(ActionBarOptions.class);
		}
		// Retrieve options menu.
		this.mMenuOptions = FragmentAnnotations.obtainAnnotationFrom(classOfFragment, MenuOptions.class);
	}

	/**
	 * Methods =====================================================================================
	 */

	/**
	 * Public --------------------------------------------------------------------------------------
	 */

	/**
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Check if this fragment instance is placed within ActionBarActivity context.
		final Activity activity = getActivity();
		if (!(activity instanceof ActionBarActivity)) {
			throw new AndroidRuntimeException(
					"ActionBarFragment implementation can be used only within the context of ActionBarActivity."
			);
		}
		// Obtain action bar from the parent activity.
		this.mActionBar = getActionBarActivity().getSupportActionBar();
		// Enable/disable options menu.
		setHasOptionsMenu(mMenuOptions != null);
	}

	/**
	 */
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		if (mMenuOptions != null) {
			if (mMenuOptions.clear()) {
				menu.clear();
			}
			switch (mMenuOptions.flags()) {
				case MenuOptions.IGNORE_SUPER:
					inflater.inflate(mMenuOptions.value(), menu);
					break;
				case MenuOptions.BEFORE_SUPER:
					inflater.inflate(mMenuOptions.value(), menu);
					super.onCreateOptionsMenu(menu, inflater);
					break;
				case MenuOptions.DEFAULT:
					super.onCreateOptionsMenu(menu, inflater);
					inflater.inflate(mMenuOptions.value(), menu);
					break;
				default:
					throw new IllegalArgumentException("Unknown options menu flags(" + mMenuOptions.flags() + ").");
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
		// Resolve action bar options.
		if (mActionBarOptions != null) {
			if (mActionBarOptions.title() >= 0) {
				setActionBarTitle(mActionBarOptions.title());
			}
			if (mActionBarOptions.icon() >= 0) {
				setActionBarIcon(mActionBarOptions.icon());
			}
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
	 * @see #isActivityAvailable()
	 */
	public boolean requestWindowFeature(int featureId) {
		return isActivityAvailable() && getActionBarActivity().supportRequestWindowFeature(featureId);
	}

	/**
	 * Getters + Setters ---------------------------------------------------------------------------
	 */

	/**
	 * <p>
	 * </p>
	 *
	 * @param resId
	 */
	public void setActionBarTitle(int resId) {
		if (isActionBarAvailable()) {
			mActionBar.setTitle(resId);
		}
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param title
	 */
	public void setActionBarTitle(CharSequence title) {
		if (isActionBarAvailable()) {
			mActionBar.setTitle(title);
		}
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param resId
	 */
	public void setActionBarIcon(int resId) {
		if (isActionBarAvailable()) {
			mActionBar.setIcon(resId);
		}
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param icon
	 */
	public void setActionBarIcon(Drawable icon) {
		if (isActionBarAvailable()) {
			mActionBar.setIcon(icon);
		}
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
	 * Protected -----------------------------------------------------------------------------------
	 */

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 */
	protected boolean isActionBarAvailable() {
		return mActionBar != null;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 */
	protected ActionBar getActionBar() {
		return mActionBar;
	}

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
	 * Private -------------------------------------------------------------------------------------
	 */

	/**
	 * Abstract methods ----------------------------------------------------------------------------
	 */

	/**
	 * Inner classes ===============================================================================
	 */

	/**
	 * Interface ===================================================================================
	 */
}
