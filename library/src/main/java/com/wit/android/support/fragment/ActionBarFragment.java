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
import android.support.v7.view.ActionMode;
import android.util.AndroidRuntimeException;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.wit.android.support.fragment.annotation.ActionBarOptions;
import com.wit.android.support.fragment.annotation.ActionModeOptions;
import com.wit.android.support.fragment.annotation.MenuOptions;
import com.wit.android.support.fragment.util.FragmentAnnotations;

/**
 * <h4>Class Overview</h4>
 * todo: description
 * <p/>
 * <b>Note, that implementation of this fragment can be used only within the context of
 * {@link android.support.v7.app.ActionBarActivity}</b>.
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
 * <li>{@link com.wit.android.support.fragment.annotation.ActionModeOptions @ActionModeOptions} [<b>class - inherited</b>]</li>
 * <p>
 * If this annotation is presented, the {@link android.support.v7.view.ActionMode} started by {@link }
 * will be started with a new instance of {@link com.wit.android.support.fragment.ActionBarFragment.ActionModeCallback}.
 * </p>
 * </ul>
 *
 * @author Martin Albedinsky
 */
public class ActionBarFragment extends BaseFragment {

	/**
	 * Interface ===================================================================================
	 */

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * Log TAG.
	 */
	// private static final String TAG = "ActionBarFragment";

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
	 * Current action mode.
	 */
	ActionMode mActionMode;

	/**
	 * Annotation holding an options for this fragment's action mode.
	 */
	ActionModeOptions mActionModeOptions;

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
	 * Creates a new instance of ActionBarFragment. If {@link com.wit.android.support.fragment.annotation.ActionBarOptions @ActionBarOptions},
	 * {@link com.wit.android.support.fragment.annotation.ActionModeOptions @ActionModeOptions}
	 * or {@link com.wit.android.support.fragment.annotation.MenuOptions @MenuOptions} annotations are
	 * presented above a sub-class of ActionBarFragment, they will be processed here.
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
		if (mMenuOptions != null) {
			setHasOptionsMenu(true);
		}
		this.mCreated = true;
	}

	/**
	 */
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		if (mMenuOptions != null && mMenuOptions.value() > 0) {
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
	 * Wrapped {@link android.support.v7.app.ActionBarActivity#supportInvalidateOptionsMenu()}.
	 *
	 * @see #isActivityAvailable()
	 */
	public void invalidateOptionsMenu() {
		if (mActivity != null) {
			((ActionBarActivity) mActivity).supportInvalidateOptionsMenu();
		}
	}

	/**
	 * Wrapped {@link android.support.v7.app.ActionBarActivity#supportRequestWindowFeature(int)}.
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
	 * Wrapped {@link android.app.ActionBar#setTitle(int)} on the ActionBar instance accessible from
	 * this fragment instance.
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
	 * Wrapped {@link android.app.ActionBar#setTitle(CharSequence)} on the ActionBar instance accessible
	 * from this fragment instance.
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
	 * Wrapped {@link android.app.ActionBar#setIcon(int)} on the ActionBar instance accessible from
	 * this fragment instance.
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
	 * Wrapped {@link android.app.ActionBar#setIcon(Drawable)} on the ActionBar instance accessible
	 * from this fragment instance.
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
	 * Wrapped {@link android.support.v7.app.ActionBarActivity#setSupportProgress(int)} on the current
	 * activity.
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
	 * Wrapped {@link android.support.v7.app.ActionBarActivity#setSupportProgressBarVisibility(boolean)}
	 * on the current activity.
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
	 * Wrapped {@link android.support.v7.app.ActionBarActivity#setSupportProgressBarIndeterminate(boolean)}.
	 * on the current activity.
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
	 * Wrapped {@link android.support.v7.app.ActionBarActivity#setSupportProgressBarIndeterminateVisibility(boolean)}.
	 * on the current activity.
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
	 * Same as {@link #startActionMode(android.support.v7.view.ActionMode.Callback)} with a new instance
	 * of {@link com.wit.android.support.fragment.ActionBarFragment.ActionModeCallback} for this
	 * fragment.
	 */
	protected boolean startActionMode() {
		return startActionMode(new ActionModeCallback(this));
	}

	/**
	 * Starts a new action mode for this fragment.
	 *
	 * @param callback The callback used to manage action mode.
	 * @return <code>True</code> if action mode has been started, <code>false</code> if this fragment
	 * is already in the action mode or the parent activity of this fragment is not available.
	 * @see #isInActionMode()
	 * @see #isActivityAvailable()
	 */
	protected boolean startActionMode(ActionMode.Callback callback) {
		if (!isInActionMode() && mActivity != null) {
			onActionModeStarted(getActionBarActivity().startSupportActionMode(callback));
			return true;
		}
		return false;
	}

	/**
	 * Returns flag indicating whether this fragment is in the action mode or not.
	 *
	 * @return <code>True</code> if this fragment is in the action mode, <code>false</code> otherwise.
	 * @see #getActionMode()
	 */
	protected boolean isInActionMode() {
		return mActionMode != null;
	}

	/**
	 * Returns the current action mode.
	 *
	 * @return The current action mode, or <code>null</code> if this fragment is not in action mode.
	 * @see #isInActionMode()
	 */
	protected ActionMode getActionMode() {
		return mActionMode;
	}

	/**
	 * Invoked immediately after {@link #startActionMode(android.support.v7.view.ActionMode.Callback)}
	 * was called and this fragment was not in the action mode yet.
	 *
	 * @param actionMode Currently started action mode.
	 */
	protected void onActionModeStarted(ActionMode actionMode) {
		this.mActionMode = actionMode;
	}

	/**
	 * Invoked whenever {@link com.wit.android.support.fragment.AdapterFragment.ActionModeCallback#onDestroyActionMode(android.support.v7.view.ActionMode)}
	 * is called on the current action mode callback (if instance of {@link com.wit.android.support.fragment.ActionBarFragment.ActionModeCallback}).
	 */
	protected void onActionModeFinished() {
		this.mActionMode = null;
	}

	/**
	 * Returns flag indicating whether the ActionBar is available or not.
	 *
	 * @return <code>True</code> if ActionBar obtained from the parent activity is available,
	 * <code>false</code> otherwise.
	 */
	protected boolean isActionBarAvailable() {
		return mActionBar != null;
	}

	/**
	 * Returns an instance of ActionBar which can be accessed by this fragment. <b>Note</b>, that
	 * ActionBar can be accessed only between {@link #onCreate(android.os.Bundle)} and {@link #onDestroy()},
	 * otherwise exception will be thrown.
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
	 * Returns an instance of {@link android.support.v7.app.ActionBarActivity}.
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
		// Obtain action mode options.
		this.mActionModeOptions = FragmentAnnotations.obtainAnnotationFrom(
				classOfFragment, ActionModeOptions.class, ActionBarFragment.class
		);
	}

	/**
	 * Private -------------------------------------------------------------------------------------
	 */

	/**
	 * Inner classes ===============================================================================
	 */


	/**
	 * <h4>Class Overview</h4>
	 * todo: description
	 *
	 * @author Martin Albedinsky
	 */
	public static class ActionModeCallback implements ActionMode.Callback {

		/**
		 * Members =================================================================================
		 */

		/**
		 * Instance of fragment within which context was this action mode started.
		 */
		protected final ActionBarFragment fragment;

		/**
		 * Constructors ============================================================================
		 */

		/**
		 * Creates a new instance of ActionModeCallback without fragment.
		 */
		public ActionModeCallback() {
			this(null);
		}

		/**
		 * Creates a new instance of ActionModeCallback for the context of the given <var>fragment</var>.
		 *
		 * @param fragment The instance of fragment in which is being action mode started.
		 */
		public ActionModeCallback(ActionBarFragment fragment) {
			this.fragment = fragment;
		}

		/**
		 * Methods =================================================================================
		 */

		/**
		 */
		@Override
		public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
			final ActionModeOptions options = fragment != null ? fragment.mActionModeOptions : null;
			if (options != null) {
				if (options.menu() > 0) {
					actionMode.getMenuInflater().inflate(options.menu(), menu);
				}
				return true;
			}
			return false;
		}

		/**
		 */
		@Override
		public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
			return false;
		}

		/**
		 */
		@Override
		public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
			if (fragment != null && fragment.onOptionsItemSelected(menuItem)) {
				actionMode.finish();
				return true;
			}
			return false;
		}

		/**
		 */
		@Override
		public void onDestroyActionMode(ActionMode actionMode) {
			if (fragment != null) {
				fragment.onActionModeFinished();
			}
		}
	}
}
