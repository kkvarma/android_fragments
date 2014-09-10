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

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
 * todo: description
 * </p>
 * <p>
 * <b>Note, that implementation of this fragment can be used only within the context of
 * {@link android.support.v7.app.ActionBarActivity}</b>.
 * </p>
 * <h6>Accepted annotations</h6>
 * <ul>
 * <li>{@link com.wit.android.support.fragment.annotation.ActionBarOptions @ActionBarOptions} [<b>class</b>]</li>
 * <p>
 * If this annotation is presented, all options presented within this annotation will be used to set
 * up an instance of ActionBar accessible from within context of a sub-class of ActionBarFragment.
 * Such a set up is accomplished in {@link #onViewCreated(android.view.View, android.os.Bundle)}.
 * </p>
 * <li>{@link com.wit.android.support.fragment.annotation.MenuOptions @MenuOptions} [<b>class - inherited</b>]</li>
 * <p>
 * If this annotation is presented, options menu will be requested in {@link #onCreate(android.os.Bundle)}
 * by {@link #setHasOptionsMenu(boolean)} and menu will be created in {@link #onCreateOptionsMenu(android.view.Menu, android.view.MenuInflater)}
 * according to the options presented within this annotation.
 * </p>
 * </ul>
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
	 * Static members ==============================================================================
	 */

	/**
	 * Members =====================================================================================
	 */

	/**
	 * Annotation holding configuration for the ActionBar of this fragment.
	 */
	private ActionBarOptions mActionBarOptions;

	/**
	 * Annotation holding configuration for the options menu of this fragment.
	 */
	private MenuOptions mMenuOptions;

	/**
	 * Action bar obtained from the parent activity of this fragment. Can be accessed immediately from
	 * {@link #onCreate(android.os.Bundle)}.
	 */
	private ActionBar mActionBar;

	/**
	 * Flag indicating whether this fragment is already created or not.
	 */
	private boolean mCreated;

	/**
	 * Constructors ================================================================================
	 */

	/**
	 * <p>
	 * Creates a new instance of ActionBarFragment. If {@link com.wit.android.support.fragment.annotation.ActionBarOptions @ActionBarOptions}
	 * or {@link com.wit.android.support.fragment.annotation.MenuOptions @MenuOptions} annotations are
	 * presented above a sub-class of ActionBarFragment, they will be processed here.
	 * </p>
	 */
	public ActionBarFragment() {
		super();
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
		if (!(mActivity instanceof ActionBarActivity)) {
			throw new AndroidRuntimeException(
					"ActionBarFragment implementation(" + ((Object) this).getClass().getSimpleName() + ") can be used only within the context of ActionBarActivity."
			);
		}
		// Obtain action bar from the parent activity.
		this.mActionBar = getActionBarActivity().getSupportActionBar();
		if (mActionBar != null && mActionBarOptions != null) {
			switch (mActionBarOptions.homeAsUp()) {
				case ActionBarOptions.HOME_AS_UP_DISABLED:
					mActionBar.setDisplayHomeAsUpEnabled(false);
					break;
				case ActionBarOptions.HOME_AS_UP_ENABLED:
					mActionBar.setDisplayHomeAsUpEnabled(true);
					break;
			}
		}
		// Enable/disable options menu.
		setHasOptionsMenu(mMenuOptions != null);
		this.mCreated = true;
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
				if (mActionBarOptions.title() != ActionBarOptions.NONE) {
					setActionBarTitle(mActionBarOptions.title());
				} else {
					setActionBarTitle("");
				}
			}
			if (mActionBarOptions.icon() >= 0) {
				if (mActionBarOptions.icon() != ActionBarOptions.NONE) {
					setActionBarIcon(mActionBarOptions.icon());
				} else {
					setActionBarIcon(new ColorDrawable(Color.TRANSPARENT));
				}
			}
		}
	}

	/**
	 * <p>
	 * Wrapped {@link android.support.v7.app.ActionBarActivity#supportInvalidateOptionsMenu()}.
	 * </p>
	 *
	 * @see #isActivityAvailable()
	 */
	public void invalidateOptionsMenu() {
		if (mActivity != null) {
			((ActionBarActivity) mActivity).supportInvalidateOptionsMenu();
		}
	}

	/**
	 * <p>
	 * Wrapped {@link android.support.v7.app.ActionBarActivity#supportRequestWindowFeature(int)}.
	 * </p>
	 *
	 * @see #isActivityAvailable()
	 */
	public boolean requestWindowFeature(int featureId) {
		return mActivity != null && ((ActionBarActivity) mActivity).supportRequestWindowFeature(featureId);
	}

	/**
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		this.mCreated = false;
	}

	/**
	 * Getters + Setters ---------------------------------------------------------------------------
	 */

	/**
	 * <p>
	 * Wrapped {@link android.app.ActionBar#setTitle(int)} on the ActionBar instance accessible from
	 * this fragment instance.
	 * </p>
	 *
	 * @see #setActionBarTitle(CharSequence)
	 * @see #isActionBarAvailable()
	 */
	public void setActionBarTitle(int resId) {
		if (mActionBar != null) {
			mActionBar.setTitle(resId);
		}
	}

	/**
	 * <p>
	 * Wrapped {@link android.app.ActionBar#setTitle(CharSequence)} on the ActionBar instance accessible
	 * from this fragment instance.
	 * </p>
	 *
	 * @see #setActionBarTitle(int)
	 * @see #isActionBarAvailable()
	 */
	public void setActionBarTitle(CharSequence title) {
		if (mActionBar != null) {
			mActionBar.setTitle(title);
		}
	}

	/**
	 * <p>
	 * Wrapped {@link android.app.ActionBar#setIcon(int)} on the ActionBar instance accessible from
	 * this fragment instance.
	 * </p>
	 *
	 * @see #setActionBarIcon(android.graphics.drawable.Drawable)
	 * @see #isActionBarAvailable()
	 */
	public void setActionBarIcon(int resId) {
		if (mActionBar != null) {
			mActionBar.setIcon(resId);
		}
	}

	/**
	 * <p>
	 * Wrapped {@link android.app.ActionBar#setIcon(Drawable)} on the ActionBar instance accessible
	 * from this fragment instance.
	 * </p>
	 *
	 * @see #setActionBarIcon(int)
	 * @see #isActionBarAvailable()
	 */
	public void setActionBarIcon(Drawable icon) {
		if (mActionBar != null) {
			mActionBar.setIcon(icon);
		}
	}

	/**
	 * <p>
	 * Wrapped {@link android.support.v7.app.ActionBarActivity#setSupportProgress(int)} on the current
	 * activity.
	 * </p>
	 *
	 * @see #setProgressBarVisibility(boolean)
	 * @see #isActivityAvailable()
	 */
	public void setProgress(int progress) {
		if (mActivity != null) {
			((ActionBarActivity) mActivity).setSupportProgress(progress);
		}
	}

	/**
	 * <p>
	 * Wrapped {@link android.support.v7.app.ActionBarActivity#setSupportProgressBarVisibility(boolean)}
	 * on the current activity.
	 * </p>
	 *
	 * @see #setProgress(int)
	 * @see #setProgressBarIndeterminate(boolean)
	 * @see #isActivityAvailable()
	 */
	public void setProgressBarVisibility(boolean visible) {
		if (mActivity != null) {
			((ActionBarActivity) mActivity).setSupportProgressBarVisibility(visible);
		}
	}

	/**
	 * <p>
	 * Wrapped {@link android.support.v7.app.ActionBarActivity#setSupportProgressBarIndeterminate(boolean)}.
	 * on the current activity.
	 * </p>
	 *
	 * @see #setProgressBarIndeterminateVisibility(boolean)
	 * @see #isActivityAvailable()
	 */
	public void setProgressBarIndeterminate(boolean indeterminate) {
		if (mActivity != null) {
			((ActionBarActivity) mActivity).setSupportProgressBarIndeterminate(indeterminate);
		}
	}

	/**
	 * <p>
	 * Wrapped {@link android.support.v7.app.ActionBarActivity#setSupportProgressBarIndeterminateVisibility(boolean)}.
	 * on the current activity.
	 * </p>
	 *
	 * @see #setProgressBarIndeterminate(boolean)
	 * @see #isActivityAvailable()
	 */
	public void setProgressBarIndeterminateVisibility(boolean visible) {
		if (mActivity != null) {
			((ActionBarActivity) mActivity).setSupportProgressBarIndeterminateVisibility(visible);
		}
	}

	/**
	 * Protected -----------------------------------------------------------------------------------
	 */

	/**
	 * <p>
	 * Returns flag indicating whether the ActionBar is available or not.
	 * </p>
	 *
	 * @return <code>True</code> if ActionBar obtained from the parent activity is available,
	 * <code>false</code> otherwise.
	 */
	protected boolean isActionBarAvailable() {
		return mActionBar != null;
	}

	/**
	 * <p>
	 * Returns an instance of ActionBar which can be accessed by this fragment. <b>Note</b>, that
	 * ActionBar can be accessed only between {@link #onCreate(android.os.Bundle)} and {@link #onDestroy()},
	 * otherwise exception will be thrown.
	 * </p>
	 *
	 * @return Instance of the ActionBar obtained from the parent activity.
	 * @throws java.lang.IllegalStateException If this fragment isn't created yet or is already destroyed.
	 */
	protected ActionBar getActionBar() {
		if (!mCreated) {
			throw new IllegalStateException(
					"Can not to access ActionBar." + ((Object) this).getClass().getSimpleName() + " is not created yet or is already destroyed."
			);
		}
		return mActionBar;
	}

	/**
	 * <p>
	 * Returns an instance of {@link android.support.v7.app.ActionBarActivity}.
	 * </p>
	 *
	 * @return The current parent activity of this fragment instance casted to ActionBarActivity.
	 * @see #isActivityAvailable()
	 */
	protected ActionBarActivity getActionBarActivity() {
		return (ActionBarActivity) mActivity;
	}

	/**
	 */
	@Override
	void processClassAnnotations(Class<?> classOfFragment) {
		super.processClassAnnotations(classOfFragment);
		// Obtain action bar options.
		this.mActionBarOptions = FragmentAnnotations.obtainAnnotationFrom(
				classOfFragment, ActionBarOptions.class
		);
		// Obtain options menu.
		this.mMenuOptions = FragmentAnnotations.obtainAnnotationFrom(
				classOfFragment, MenuOptions.class, ActionBarFragment.class
		);
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
