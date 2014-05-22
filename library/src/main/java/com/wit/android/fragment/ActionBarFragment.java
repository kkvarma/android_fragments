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
package com.wit.android.fragment;

import android.app.ActionBar;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.wit.android.fragment.annotation.ActionBarOptions;
import com.wit.android.fragment.annotation.MenuOptions;
import com.wit.android.fragment.util.FragmentAnnotations;

/**
 * <h4>Class Overview</h4>
 * <p>
 * todo: description
 * </p>
 * <p>
 * <h6>Used annotations</h6>
 * {@link com.wit.android.fragment.annotation.ActionBarOptions @ActionBarOptions} [<b>class</b>]
 * <p>
 * If this annotation is presented, all options presented within this annotation will be applied to
 * the instance of ActionBar presented within the context of an instance of this ActionBarFragment class
 * implementation. Such a set up is accomplished in {@link #onViewCreated(android.view.View, android.os.Bundle)}.
 * </p>
 * {@link com.wit.android.fragment.annotation.MenuOptions @MenuOptions} [<b>class</b>]
 * <p>
 * If this annotation is presented, options menu will be requested in {@link #onCreate(android.os.Bundle)}
 * by {@link #setHasOptionsMenu(boolean)} and menu will be created in {@link #onCreateOptionsMenu(android.view.Menu, android.view.MenuInflater)}
 * according to the options presented within this annotation.
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
	 * Arrays --------------------------------------------------------------------------------------
	 */

	/**
	 * Booleans ------------------------------------------------------------------------------------
	 */

	/**
	 * Flag indicating whether this fragment is already created or not.
	 */
	private boolean bCreated = false;

	/**
	 * Constructors ================================================================================
	 */

	/**
	 * <p>
	 * Creates a new instance of ActionBarFragment. If {@link com.wit.android.fragment.annotation.ActionBarOptions}
	 * or {@link com.wit.android.fragment.annotation.MenuOptions} annotations are presented,
	 * they will be processed here.
	 * </p>
	 */
	public ActionBarFragment() {
		super();
		final Class<?> classOfFragment = ((Object) this).getClass();
		/**
		 * Process class annotations.
		 */
		// Retrieve action bar options.
		this.mActionBarOptions = FragmentAnnotations.obtainAnnotationFrom(
				classOfFragment, ActionBarOptions.class
		);
		// Retrieve options menu.
		this.mMenuOptions = FragmentAnnotations.obtainAnnotationFrom(
				classOfFragment, MenuOptions.class, true, ActionBarFragment.class
		);
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
		// Obtain action bar from the parent activity.
		this.mActionBar = getActivity().getActionBar();
		// Enable/disable options menu.
		setHasOptionsMenu(mMenuOptions != null);
		this.bCreated = true;
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
					throw new IllegalStateException("Unknown options menu flags(" + mMenuOptions.flags() + ").");
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
	 * @see #isActivityAvailable()
	 */
	public boolean requestWindowFeature(int featureId) {
		return isActivityAvailable() && getActivity().requestWindowFeature(featureId);
	}

	/**
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		this.bCreated = false;
	}

	/**
	 * Getters + Setters ---------------------------------------------------------------------------
	 */

	/**
	 * <p>
	 * Same as {@link android.app.ActionBar#setTitle(int)}.
	 * </p>
	 *
	 * @see #setActionBarTitle(CharSequence)
	 * @see #isActionBarAvailable()
	 */
	public void setActionBarTitle(int resId) {
		if (isActionBarAvailable()) {
			mActionBar.setTitle(resId);
		}
	}

	/**
	 * <p>
	 * Same as {@link android.app.ActionBar#setTitle(CharSequence)}.
	 * </p>
	 *
	 * @see #setActionBarTitle(int)
	 * @see #isActionBarAvailable()
	 */
	public void setActionBarTitle(CharSequence title) {
		if (isActionBarAvailable()) {
			mActionBar.setTitle(title);
		}
	}

	/**
	 * <p>
	 * Same as {@link android.app.ActionBar#setIcon(int)}.
	 * </p>
	 *
	 * @see #setActionBarIcon(android.graphics.drawable.Drawable)
	 * @see #isActionBarAvailable()
	 */
	public void setActionBarIcon(int resId) {
		if (isActionBarAvailable()) {
			mActionBar.setIcon(resId);
		}
	}

	/**
	 * <p>
	 * Same as {@link android.app.ActionBar#setIcon(Drawable)}.
	 * </p>
	 *
	 * @see #setActionBarIcon(int)
	 * @see #isActionBarAvailable()
	 */
	public void setActionBarIcon(Drawable icon) {
		if (isActionBarAvailable()) {
			mActionBar.setIcon(icon);
		}
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
	 * Protected -----------------------------------------------------------------------------------
	 */

	/**
	 * <p>
	 * Returns flag indicating whether the action bar is available or not.
	 * </p>
	 *
	 * @return <code>True</code> if action bar obtained from the parent activity is available,
	 * <code>false</code> otherwise.
	 */
	protected boolean isActionBarAvailable() {
		return mActionBar != null;
	}

	/**
	 * <p>
	 * Returns action bar which can be accessed by this fragment. <b>Note</b>, that action bar can be
	 * accessed only between {@link #onCreate(android.os.Bundle)} and {@link #onDestroy()}, otherwise
	 * exception will be thrown.
	 * </p>
	 *
	 * @return The instance of ActionBar obtained from the parent activity.
	 * @throws java.lang.IllegalStateException If this fragment isn't created yet or is already destroyed.
	 */
	protected ActionBar getActionBar() {
		if (!bCreated) {
			throw new IllegalStateException(
					"Action bar can be accessed only when fragment is created." +
							((Object) this).getClass().getSimpleName() + " isn't created yet or is already destroyed."
			);
		}
		return mActionBar;
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
