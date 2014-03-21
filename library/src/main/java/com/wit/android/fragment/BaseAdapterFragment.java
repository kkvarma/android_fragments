/*
 * =================================================================================================
 *                    Copyright (C) 2014 Martin Albedinsky [Wolf-ITechnologies]
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

import android.os.Bundle;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wit.android.fragment.annotation.AdapterViewOptions;
import com.wit.android.fragment.annotation.ActionModeOptions;

/**
 * <h4>Class Overview</h4>
 * <p>
 * Description.
 * </p>
 *
 * @author Martin Albedinsky
 */
public abstract class BaseAdapterFragment<V extends AdapterView, A extends Adapter> extends ActionBarFragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * Log TAG.
	 */
	// private static final String TAG = BaseAdapterFragment.class.getSimpleName();

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
	V mAdapterView;

	/**
	 *
	 */
	View mEmptyView;

	/**
	 *
	 */
	View mLoadingView;

	/**
	 *
	 */
	A mAdapter;

	/**
	 *
	 */
	ActionMode mActionMode;

	/**
	 *
	 */
	private CharSequence mEmptyText = "";

	/**
	 *
	 */
	private AdapterViewOptions mAdapterViewOptions;

	/**
	 *
	 */
	private ActionModeOptions mActionModeOptions;

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
	public BaseAdapterFragment() {
		final Class<?> classOfFragment = ((Object) this).getClass();
		/**
		 * Process class annotations.
		 */
		// Retrieve adapter view options.
		this.mAdapterViewOptions = obtainAnnotationFrom(AdapterViewOptions.class, classOfFragment);
		// Retrieve action mode options.
		this.mActionModeOptions = obtainAnnotationFrom(ActionModeOptions.class, classOfFragment);
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// There can be @ContentView annotation presented, so the parent can create content view.
		final View view = super.onCreateView(inflater, container, savedInstanceState);
		if (view != null) {
			return view;
		}
		/**
		 * Build our custom adapter view layout.
		 */
		final FrameLayout layout = new FrameLayout(inflater.getContext());
		// Resolve empty view.
		final View emptyView = onCreateEmptyView(inflater, layout, savedInstanceState);
		if (emptyView != null) {
			layout.addView(emptyView, createEmptyViewParams());
		}
		// Resolve loading view.
		final View loadingView = onCreateLoadingView(inflater, layout, savedInstanceState);
		if (loadingView != null) {
			layout.addView(loadingView, createLoadingViewParams());
		}
		// Resolve adapter view.
		final View adapterView = onCreateAdapterView(inflater, layout, savedInstanceState);
		if (adapterView != null) {
			// Ensure that adapter view will have correct id.
			adapterView.setId(mAdapterViewOptions != null ? mAdapterViewOptions.viewId() : AdapterViewOptions.VIEW_DEFAULT_ID);
			layout.addView(adapterView, createAdapterViewParams());
		}
		return layout;
	}

	/**
	 */
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		this.ensureAdapterView(view);
		onViewCreated(view, mAdapterView, savedInstanceState);
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param view
	 * @param adapterView
	 * @param savedInstanceState
	 */
	@SuppressWarnings("unchecked")
	protected void onViewCreated(View view, V adapterView, Bundle savedInstanceState) {
		// Base set up.
		if (mAdapter != null) {
			adapterView.setAdapter(mAdapter);
		}
		if (mEmptyView != null) {
			adapterView.setEmptyView(mEmptyView);
			if (mEmptyView instanceof TextView) {
				if (mAdapterViewOptions != null && mAdapterViewOptions.emptyText() >= 0) {
					((TextView) mEmptyView).setText(mAdapterViewOptions.emptyText());
				} else {
					((TextView) mEmptyView).setText(mEmptyText);
				}
			}
		}
		adapterView.setOnItemClickListener(this);
		adapterView.setOnItemLongClickListener(this);
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 */
	public boolean isEmpty() {
		return mAdapter == null || mAdapter.isEmpty();
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param visible
	 */
	public void setLoadingViewVisible(boolean visible) {
		// todo: animate
		if (mLoadingView != null) {
			if (visible && mLoadingView.getVisibility() != View.VISIBLE) {
				mLoadingView.setVisibility(View.VISIBLE);
			} else if (!visible && mLoadingView.getVisibility() == View.VISIBLE) {
				mLoadingView.setVisibility(View.GONE);
			}
		}
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 */
	public boolean isLoadingViewVisible() {
		return mLoadingView != null && mLoadingView.getVisibility() == View.VISIBLE;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 */
	public boolean hasAdapter() {
		return mAdapter != null;
	}

	/**
	 * Getters + Setters ---------------------------------------------------------------------------
	 */

	/**
	 * <p>
	 * </p>
	 *
	 * @param emptyView
	 */
	public void setEmptyView(View emptyView) {
		this.mEmptyView = emptyView;
		if (mAdapterView != null) {
			mAdapterView.setEmptyView(mEmptyView);
		}
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param resId
	 */
	public void setEmptyViewText(int resId) {
		setEmptyViewText(obtainString(resId));
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param text
	 */
	public void setEmptyViewText(CharSequence text) {
		this.mEmptyText = text;
		if (mEmptyView instanceof TextView) {
			((TextView) mEmptyView).setText(text);
		}
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 */
	public View getEmptyView() {
		return mEmptyView;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 */
	public V getAdapterView() {
		return mAdapterView;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param adapter
	 */
	@SuppressWarnings("unchecked")
	public void setAdapter(A adapter) {
		this.mAdapter = adapter;
		if (mAdapterView != null) {
			mAdapterView.setAdapter(mAdapter);
		}
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 */
	public A getAdapter() {
		return mAdapter;
	}

	/**
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	}

	/**
	 */
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		return startActionMode(new ActionModeCallback(this), parent, view, position, id);
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
	protected FrameLayout.LayoutParams createAdapterViewParams() {
		return new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param inflater
	 * @param container
	 * @param savedInstanceState
	 * @return
	 */
	protected View onCreateEmptyView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return new TextView(inflater.getContext(), null, android.R.attr.textViewStyle);
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 */
	protected FrameLayout.LayoutParams createEmptyViewParams() {
		final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.CENTER;
		return params;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param inflater
	 * @param container
	 * @param savedInstanceState
	 * @return
	 */
	protected View onCreateLoadingView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View loadingView = new ProgressBar(inflater.getContext(), null, android.R.attr.progressBarStyleLarge);
		loadingView.setVisibility(View.GONE);
		return loadingView;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 */
	protected FrameLayout.LayoutParams createLoadingViewParams() {
		final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.CENTER;
		return params;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param callback
	 * @param parent
	 * @param view
	 * @param position
	 * @param id
	 * @return
	 */
	protected boolean startActionMode(ActionMode.Callback callback, AdapterView<?> parent, View view, int position, long id) {
		if (!isInActionMode()) {
			if (isActivityAvailable()) {
				onActionModeStarted(mActionMode = getActivity().startActionMode(callback), position);
			}
			return true;
		}
		return false;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 */
	protected boolean isInActionMode() {
		return mActionMode != null;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 */
	protected ActionMode getActionMode() {
		return mActionMode;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param actionMode
	 * @param position
	 */
	protected void onActionModeStarted(ActionMode actionMode, int position) {
	}

	/**
	 * <p>
	 * </p>
	 */
	protected void onActionModeFinished() {
	}

	/**
	 * Private -------------------------------------------------------------------------------------
	 */

	/**
	 * @param view
	 */
	@SuppressWarnings("unchecked")
	private void ensureAdapterView(View view) {
		final int viewId = (mAdapterViewOptions != null) ? mAdapterViewOptions.viewId() : AdapterViewOptions.VIEW_DEFAULT_ID;
		if ((mAdapterView = (V) view.findViewById(viewId)) == null) {
			throw new IllegalArgumentException("Missing adapter view with id(" + viewId + ") within adapter fragment layout.");
		}
		// Get also empty view if is presented.
		this.mEmptyView = view.findViewById(
				mAdapterViewOptions != null ? mAdapterViewOptions.emptyViewId() : AdapterViewOptions.EMPTY_VIEW_DEFAULT_ID
		);
	}

	/**
	 * Abstract methods ----------------------------------------------------------------------------
	 */

	/**
	 * <p>
	 * </p>
	 *
	 * @param inflater
	 * @param container
	 * @param savedInstanceState
	 * @return
	 */
	protected abstract V onCreateAdapterView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

	/**
	 * Inner classes ===============================================================================
	 */

	/**
	 * <h4>Class Overview</h4>
	 * <p>
	 * </p>
	 *
	 * @author Martin Albedinsky
	 */
	public static class ActionModeCallback implements ActionMode.Callback {

		/**
		 * Members =================================================================================
		 */

		/**
		 *
		 */
		protected BaseAdapterFragment mAdapterFragment;

		/**
		 * Constructors ============================================================================
		 */

		/**
		 * <p>
		 * </p>
		 */
		public ActionModeCallback() {
			this(null);
		}

		/**
		 * <p>
		 * </p>
		 *
		 * @param adapterFragment
		 */
		public ActionModeCallback(BaseAdapterFragment adapterFragment) {
			this.mAdapterFragment = adapterFragment;
		}

		/**
		 * Methods =================================================================================
		 */

		/**
		 */
		@Override
		public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
			if (mAdapterFragment != null && mAdapterFragment.mActionModeOptions != null) {
				actionMode.getMenuInflater().inflate(mAdapterFragment.mActionModeOptions.menu(), menu);
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
			if (mAdapterFragment != null && mAdapterFragment.onOptionsItemSelected(menuItem)) {
				actionMode.finish();
				return true;
			}
			return false;
		}

		/**
		 */
		@Override
		public void onDestroyActionMode(ActionMode actionMode) {
			if (mAdapterFragment != null) {
				mAdapterFragment.mActionMode = null;
				mAdapterFragment.onActionModeFinished();
			}
		}
	}

	/**
	 * Interface ===================================================================================
	 */
}
