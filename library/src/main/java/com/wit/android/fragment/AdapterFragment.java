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

import android.content.Context;
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

import com.wit.android.fragment.annotation.ActionModeOptions;
import com.wit.android.fragment.annotation.AdapterViewOptions;
import com.wit.android.fragment.util.FragmentAnnotations;

/**
 * <h4>Class Overview</h4>
 * <p>
 * todo: description
 * </p>
 * <h6>Used annotations</h6>
 * {@link com.wit.android.fragment.annotation.AdapterViewOptions @AdapterViewOptions} [<b>class</b>]
 * <p>
 * If this annotation is presented, all necessary stuffs around AdapterView like empty view, empty text
 * will be managed. This annotation with {@link com.wit.android.fragment.annotation.ContentView @ContentView}
 * annotation allows to set up custom AdapterFragment layout with custom ids without implementing
 * any Java code.
 * </p>
 * {@link com.wit.android.fragment.annotation.ActionModeOptions @ActionModeOptions} [<b>class</b>]
 * <p>
 * If this annotation is presented, the {@link android.view.ActionMode} will be started
 * with a new instance of {@link AdapterFragment.ActionModeCallback}
 * from {@link #onItemLongClick(android.widget.AdapterView, android.view.View, int, long)}. See
 * {@link #startActionMode(android.view.ActionMode.Callback, android.widget.AdapterView, android.view.View, int, long)}
 * for more information.
 * </p>
 *
 * @param <A> The type of an adapter used within the context of an instance of this AdapterFragment
 *            class implementation.
 * @author Martin Albedinsky
 */
public abstract class AdapterFragment<V extends AdapterView, A extends Adapter> extends ActionBarFragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * Log TAG.
	 */
	// private static final String TAG = AdapterFragment.class.getSimpleName();

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
	 * Adapter view of this AdapterFragment. instance.
	 */
	V mAdapterView;

	/**
	 * Empty view of this AdapterFragment.'s adapter view.
	 */
	View mEmptyView;

	/**
	 * Loading view presented behind this fragment's adapter view.
	 */
	View mLoadingView;

	/**
	 * Current adapter.
	 */
	A mAdapter;

	/**
	 * Current action mode.
	 */
	ActionMode mActionMode;

	/**
	 *
	 */
	private int mEmptyViewRes;

	/**
	 * Text presented within the empty view (if instance of TextView) of this AdapterFragment.'s adapter
	 * view.
	 */
	private CharSequence mEmptyText = "";

	/**
	 * Annotation holding an options for this fragment's adapter view.
	 */
	private AdapterViewOptions mAdapterViewOptions;

	/**
	 * Annotation holding an options for this fragment's action mode.
	 */
	private ActionModeOptions mActionModeOptions;

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
	 * Creates a new instance of AdapterFragment. If {@link com.wit.android.fragment.annotation.AdapterViewOptions}
	 * or {@link com.wit.android.fragment.annotation.ActionModeOptions} annotations are presented,
	 * they will be processed here.
	 * </p>
	 */
	public AdapterFragment() {
		final Class<?> classOfFragment = ((Object) this).getClass();
		/**
		 * Process class annotations.
		 */
		// Retrieve adapter view options.
		this.mAdapterViewOptions = FragmentAnnotations.obtainAnnotationFrom(
				classOfFragment, AdapterViewOptions.class, true, AdapterFragment.class
		);
		// Retrieve action mode options.
		this.mActionModeOptions = FragmentAnnotations.obtainAnnotationFrom(
				classOfFragment, ActionModeOptions.class, true, AdapterFragment.class
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
			emptyView.setId(mAdapterViewOptions != null ? mAdapterViewOptions.emptyViewId() : AdapterViewOptions.EMPTY_VIEW_DEFAULT_ID);
			layout.addView(emptyView, createEmptyViewParams());
		}
		// Resolve adapter view.
		final View adapterView = onCreateAdapterView(inflater, layout, savedInstanceState);
		if (adapterView != null) {
			// Ensure that adapter view will have correct id.
			adapterView.setId(mAdapterViewOptions != null ? mAdapterViewOptions.viewId() : AdapterViewOptions.VIEW_DEFAULT_ID);
			layout.addView(adapterView, createAdapterViewParams());
		}
		// Resolve loading view.
		final View loadingView = onCreateLoadingView(inflater, layout, savedInstanceState);
		if (loadingView != null) {
			layout.addView(mLoadingView = loadingView, createLoadingViewParams());
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
	 * Same as {@link android.widget.Adapter#isEmpty()}.
	 * </p>
	 *
	 * @see #getItemsCount()
	 */
	public boolean isAdapterEmpty() {
		return mAdapter == null || mAdapter.isEmpty();
	}

	/**
	 * <p>
	 * Same as {@link android.widget.Adapter#getCount()}.
	 * </p>
	 *
	 * @see #isAdapterEmpty()
	 */
	public int getItemsCount() {
		return mAdapter != null ? mAdapter.getCount() : 0;
	}

	/**
	 * <p>
	 * Shows/hides the loading view of this adapter fragment.
	 * </p>
	 *
	 * @param visible <code>True</code> to show loading view, <code>false</code> to hide it.
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
	 * Returns flag indicating whether the loading view of this AdapterFragment. is visible or not.
	 * </p>
	 *
	 * @return <code>True</code> if it is visible, <code>false</code> otherwise.
	 */
	public boolean isLoadingViewVisible() {
		return mLoadingView != null && mLoadingView.getVisibility() == View.VISIBLE;
	}

	/**
	 * <p>
	 * Returns flag indicating whether this adapter fragment holds some adapter or not.
	 * </p>
	 *
	 * @return <code>True</code> if adapter is presented, <code>false</code> otherwise.
	 */
	public boolean hasAdapter() {
		return mAdapter != null;
	}

	/**
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	}

	/**
	 */
	@Override
	@SuppressWarnings("unchecked")
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		return (mActionModeOptions != null) && startActionMode(new ActionModeCallback(this), (V) parent, view, position, id);
	}

	/**
	 * Getters + Setters ---------------------------------------------------------------------------
	 */

	/**
	 * <p>
	 * Same as {@link #setEmptyView(android.view.View)}.
	 * </p>
	 *
	 * @param resource The resource id of the layout with an empty view.
	 */
	public void setEmptyView(int resource) {
		if (mEmptyViewRes != resource) {
			setEmptyView(inflateView(mEmptyViewRes = resource));
		}
	}

	/**
	 * <p>
	 * Sets the given <var>view</var> as empty view for this fragment's adapter view.
	 * </p>
	 *
	 * @param view The view to set as empty view.
	 * @see #setEmptyView(int)
	 */
	public void setEmptyView(View view) {
		this.mEmptyView = view;
		if (mAdapterView != null) {
			mAdapterView.setEmptyView(mEmptyView);
		}
	}

	/**
	 * <p>
	 * Returns the current empty view.
	 * </p>
	 *
	 * @return The view which was set as empty view for this fragment's adapter view.
	 */
	public View getEmptyView() {
		return mEmptyView;
	}

	/**
	 * <p>
	 * Same as {@link #setEmptyText(CharSequence)}.
	 * </p>
	 *
	 * @param resId The desired resource id of empty text.
	 */
	public void setEmptyText(int resId) {
		setEmptyText(obtainString(resId));
	}

	/**
	 * <p>
	 * Sets the given text to the current empty view. <b>Note</b>, that the given text will be set
	 * only in case that the current empty view is instance of {@link android.widget.TextView}.
	 * </p>
	 *
	 * @param text The text to to the current empty view.
	 * @see #setEmptyText(int)
	 */
	public void setEmptyText(CharSequence text) {
		this.mEmptyText = text;
		if (mEmptyView instanceof TextView) {
			((TextView) mEmptyView).setText(text);
		}
	}

	/**
	 * <p>
	 * Returns the text which was set as text for the current empty view.
	 * </p>
	 *
	 * @return The text presented in the current empty view.
	 */
	public CharSequence getEmptyText() {
		return mEmptyText;
	}

	/**
	 * <p>
	 * Returns the instance of adapter view of this AdapterFragment.
	 * </p>
	 *
	 * @return Instance of the adapter view presented in the root view of this AdapterFragment.
	 */
	public V getAdapterView() {
		return mAdapterView;
	}

	/**
	 * <p>
	 * Sets the given <var>adapter</var> for this fragment.
	 * </p>
	 *
	 * @param adapter The adapter which should be used to populate the adapter view of this AdapterFragment.
	 * @see #getAdapter()
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
	 * Returns the current adapter of this AdapterFragment.
	 * </p>
	 *
	 * @return An instance of the current adapter.
	 * @see #setAdapter(android.widget.Adapter)
	 */
	public A getAdapter() {
		return mAdapter;
	}

	/**
	 * <p>
	 * Sets loading view for this AdapterFragment.
	 * </p>
	 *
	 * @param loadingView The loading view.
	 * @see #getLoadingView()
	 */
	public void setLoadingView(View loadingView) {
		this.mLoadingView = loadingView;
	}

	/**
	 * <p>
	 * Returns the current loading view of this AdapterFragment.
	 * </p>
	 *
	 * @return An instance of the current loading view.
	 * @see #setLoadingView(android.view.View)
	 */
	public View getLoadingView() {
		return mLoadingView;
	}

	/**
	 * Protected -----------------------------------------------------------------------------------
	 */

	/**
	 * <p>
	 * Called to create a new instance of layout parameters for this fragment's adapter view.
	 * </p>
	 *
	 * @return An instance of layout params, depends on the root view of this AdapterFragment.
	 */
	protected FrameLayout.LayoutParams createAdapterViewParams() {
		return new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
	}

	/**
	 * <p>
	 * Invoked to create a new instance of view which will be set as empty view to this fragment's
	 * adapter view
	 * </p>
	 *
	 * @param inflater           Valid layout inflater.
	 * @param container          The root view into which will be created empty view placed.
	 * @param savedInstanceState Bundle with saved state or <code>null</code> if this fragment's view
	 *                           is just first time created.
	 * @return An instance of empty view.
	 */
	protected View onCreateEmptyView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return new TextView(inflater.getContext(), null, android.R.attr.textViewStyle);
	}

	/**
	 * <p>
	 * Called to create a new instance of layout parameters for this fragment's empty view.
	 * </p>
	 *
	 * @return An instance of layout params, depends on the root view of this AdapterFragment.
	 */
	protected FrameLayout.LayoutParams createEmptyViewParams() {
		final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.CENTER;
		return params;
	}

	/**
	 * <p>
	 * Invoked to create a new instance of view which will be used as loading view of this adapter
	 * fragment.
	 * </p>
	 *
	 * @param inflater           Valid layout inflater.
	 * @param container          The root view into which will be created loading view placed.
	 * @param savedInstanceState Bundle with saved state or <code>null</code> if this fragment's view
	 *                           is just first time created.
	 * @return An instance of loading view.
	 */
	protected View onCreateLoadingView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View loadingView = new ProgressBar(inflater.getContext(), null, android.R.attr.progressBarStyleLarge);
		loadingView.setVisibility(View.GONE);
		return loadingView;
	}

	/**
	 * <p>
	 * Called to create a new instance of layout parameters for this fragment's loading view.
	 * </p>
	 *
	 * @return An instance of layout params, depends on the root view of this AdapterFragment.
	 */
	protected FrameLayout.LayoutParams createLoadingViewParams() {
		final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.CENTER;
		return params;
	}

	/**
	 * <p>
	 * Invoked from {@link #onViewCreated(android.view.View, android.os.Bundle)} after the root <var>view</var>
	 * is checked for valid adapter view.
	 * </p>
	 *
	 * @param view               The root view of this AdapterFragment.
	 * @param adapterView        The adapter view presented in the root view of this AdapterFragment.
	 * @param savedInstanceState Bundle with saved state or <code>null</code> if this fragment's view
	 *                           is just first time created.
	 */
	@SuppressWarnings("unchecked")
	protected void onViewCreated(View view, V adapterView, Bundle savedInstanceState) {
		// Base set up.
		if (mAdapter != null) {
			adapterView.setAdapter(mAdapter);
		}
		if (mEmptyView == null && mEmptyViewRes > 0) {
			setEmptyView(inflateView(mEmptyViewRes));
		}
		if (mEmptyView != null) {
			adapterView.setEmptyView(mEmptyView);
			if (mEmptyView instanceof TextView) {
				if (mAdapterViewOptions != null && mAdapterViewOptions.emptyText() > 0) {
					((TextView) mEmptyView).setText(mAdapterViewOptions.emptyText());
				} else {
					((TextView) mEmptyView).setText(mEmptyText);
				}
			}
		}
		adapterView.setOnItemClickListener(this);
		if (mAdapterViewOptions != null && mAdapterViewOptions.longClickable()) {
			adapterView.setOnItemLongClickListener(this);
		}
	}

	/**
	 * <p>
	 * Starts the action mode for this adapter fragment. <b>Note</b>, that by default is this called
	 * from {@link #onItemLongClick(android.widget.AdapterView, android.view.View, int, long)} if
	 * there is {@link com.wit.android.fragment.annotation.ActionModeOptions} annotation
	 * presented. The started ActionMode will be populated by a menu inflated form the resource id
	 * presented within this annotation.
	 * </p>
	 *
	 * @param callback    The callback used to manage action mode.
	 * @param adapterView The current adapter view.
	 * @param view        The view of an item for which is this action mode started.
	 * @param position    The position of an item from the current adapter's data set for which is this
	 *                    action mode started.
	 * @param id          The id of an item from the current adapter's data set for which is this
	 *                    action mode started.
	 * @return <code>True</code> if action mode started, <code>false</code> if this fragment is already
	 * in the action mode or the parent activity of this AdapterFragment. isn't available.
	 * @see #isInActionMode()
	 * @see #isActivityAvailable()
	 */
	protected boolean startActionMode(ActionMode.Callback callback, V adapterView, View view, int position, long id) {
		if (!isInActionMode()) {
			if (isActivityAvailable()) {
				onActionModeStarted(
						this.mActionMode = getActivity().startActionMode(callback),
						adapterView, view, position, id
				);
			}
			return true;
		}
		return false;
	}

	/**
	 * <p>
	 * Returns flag indicating whether this fragment is in the action mode or not.
	 * </p>
	 *
	 * @return <code>True</code> if this fragment is in the action mode, <code>false</code> otherwise.
	 * @see #getActionMode()
	 */
	protected boolean isInActionMode() {
		return mActionMode != null;
	}

	/**
	 * <p>
	 * Returns the current action mode.
	 * </p>
	 *
	 * @return The current action mode, or <code>null</code> if this fragment is not in action mode.
	 * @see #isInActionMode()
	 */
	protected ActionMode getActionMode() {
		return mActionMode;
	}

	/**
	 * <p>
	 * Invoked immediately after {@link #startActionMode(android.view.ActionMode.Callback, V, android.view.View, int, long)}
	 * was called an this fragment isn't in action mode yet.
	 * </p>
	 *
	 * @param actionMode  Currently started action mode.
	 * @param adapterView The current adapter view.
	 * @param view        The view of an item for which is this action mode started.
	 * @param position    The position of an item from the current adapter's data set for which is this
	 *                    action mode started.
	 * @param id          The id of an item from the current adapter's data set for which is this
	 *                    action mode started.
	 */
	protected void onActionModeStarted(ActionMode actionMode, V adapterView, View view, int position, long id) {
	}

	/**
	 * <p>
	 * Invoked whenever {@link AdapterFragment.ActionModeCallback#onDestroyActionMode(android.view.ActionMode)}
	 * is called on the current action mode callback (if instance of {@link AdapterFragment.ActionModeCallback}).
	 * </p>
	 */
	protected void onActionModeFinished() {
	}

	/**
	 * Private -------------------------------------------------------------------------------------
	 */

	/**
	 * Checks whether the given root <var>view</var> contains adapter view or not.
	 *
	 * @param view The root view of this AdapterFragment.
	 * @throws IllegalStateException If there is no adapter view presented with this fragment's layout.
	 */
	@SuppressWarnings("unchecked")
	private void ensureAdapterView(View view) {
		final int viewId = (mAdapterViewOptions != null) ? mAdapterViewOptions.viewId() : AdapterViewOptions.VIEW_DEFAULT_ID;
		if ((mAdapterView = (V) view.findViewById(viewId)) == null) {
			throw new IllegalStateException("Missing an adapter view with the id(" + viewId + ") within this adapter fragment's layout.");
		}
		// Get also empty view if is presented.
		this.mEmptyView = view.findViewById(
				mAdapterViewOptions != null ? mAdapterViewOptions.emptyViewId() : AdapterViewOptions.EMPTY_VIEW_DEFAULT_ID
		);
	}

	/**
	 * Inflates a new instance of view for the specified <var>resource</var>.
	 *
	 * @param resource The resource id of the view to inflate.
	 * @return A new instance of inflated view.
	 */
	private View inflateView(int resource) {
		if (isActivityAvailable()) {
			final LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			return inflater.inflate(resource, (ViewGroup) getView(), false);
		}
		return null;
	}

	/**
	 * Abstract methods ----------------------------------------------------------------------------
	 */

	/**
	 * <p>
	 * Invoked to create a new instance of adapter view specific for this instance of adapter fragment.
	 * </p>
	 *
	 * @param inflater           Valid layout inflater.
	 * @param container          The root view into which will be created adapter view placed.
	 * @param savedInstanceState Bundle with saved state or <code>null</code> if this fragment's view
	 *                           is just first time created.
	 * @return A new instance of adapter view.
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
		 * Instance of adapter fragment within which context was this action mode started.
		 */
		protected final AdapterFragment mAdapterFragment;

		/**
		 * Constructors ============================================================================
		 */

		/**
		 * <p>
		 * Creates a new instance of ActionModeCallback.
		 * </p>
		 */
		public ActionModeCallback() {
			this(null);
		}

		/**
		 * <p>
		 * Creates a new instance of ActionModeCallback for the context of the given <var>adapterFragment</var>.
		 * </p>
		 *
		 * @param adapterFragment The instance of adapter fragment in which was this action mode started.
		 */
		public ActionModeCallback(AdapterFragment adapterFragment) {
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
