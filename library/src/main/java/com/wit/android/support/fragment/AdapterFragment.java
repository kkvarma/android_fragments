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
package com.wit.android.support.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wit.android.support.fragment.annotation.AdapterViewOptions;
import com.wit.android.support.fragment.util.FragmentAnnotations;

import java.lang.reflect.InvocationTargetException;

/**
 * <h4>Class Overview</h4>
 * todo: description
 * <h6>Accepted annotations</h6>
 * <ul>
 * <li>{@link com.wit.android.support.fragment.annotation.AdapterViewOptions @AdapterViewOptions} [<b>class - inherited</b>]</li>
 * <p>
 * If this annotation is presented, all necessary stuffs around AdapterView like empty view, empty text
 * will be managed using this annotation. This annotation with {@link com.wit.android.support.fragment.annotation.ContentView @ContentView}
 * annotation allows to set up custom AdapterFragment layout with custom ids without implementing
 * any Java code.
 * </p>
 * <li>{@link com.wit.android.support.fragment.annotation.ActionModeOptions @ActionModeOptions} [<b>class - inherited</b>]</li>
 * <p>
 * If this annotation is presented, the {@link android.support.v7.view.ActionMode} will be started
 * with a new instance of {@link com.wit.android.support.fragment.AdapterFragment.ActionModeCallback}
 * from {@link #onItemLongClick(android.widget.AdapterView, android.view.View, int, long)} when invoked.
 * See {@link #startActionMode(android.support.v7.view.ActionMode.Callback, android.widget.AdapterView, android.view.View, int, long)}
 * for more information.
 * </p>
 * <li>{@link ActionBarFragment super annotations}</li>
 * </ul>
 *
 * @param <V> A type of the adapter view which presents data set provided by the adapter used within
 *            the context of an instance of this AdapterFragment implementation.
 * @param <A> A type of the adapter used within the context of an instance of this AdapterFragment implementation.
 * @author Martin Albedinsky
 * @see com.wit.android.support.fragment.ListFragment
 * @see com.wit.android.support.fragment.GridFragment
 */
public abstract class AdapterFragment<V extends AdapterView, A extends Adapter> extends ActionBarFragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

	/**
	 * Interface ===================================================================================
	 */

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * Log TAG.
	 */
	private static final String TAG = "AdapterFragment";

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
	 * Adapter view of this AdapterFragment. instance.
	 */
	V mAdapterView;

	/**
	 * Empty view of this AdapterFragment.'s adapter view.
	 */
	View mEmptyView;

	/**
	 * Current adapter.
	 */
	A mAdapter;

	/**
	 * Resource id of empty view layout to inflate.
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
	 * Constructors ================================================================================
	 */

	/**
	 * Creates a new instance of AdapterFragment. If {@link com.wit.android.support.fragment.annotation.AdapterViewOptions @AdapterViewOptions}
	 * annotation is presented above a subclass of this AdapterFragment, it will be processed here.
	 */
	public AdapterFragment() {
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// There can be @ContentView annotation presented, so the parent can create content view.
		final View view = super.onCreateView(inflater, container, savedInstanceState);
		if (view != null) {
			return view;
		}
		/**
		 * Build our custom adapter view layout.
		 */
		final ViewGroup layout = onCreateLayout(inflater, container, savedInstanceState);
		if (layout == null) {
			throw new NullPointerException("Layout created by " + ((Object) this).getClass().getSimpleName() + ".onCreateLayout(...) can't be null.");
		}
		final ViewGroup.LayoutParams layoutParams = createLayoutParams();
		if (layoutParams != null) {
			layout.setLayoutParams(layoutParams);
		}
		// Resolve empty view.
		final View emptyView = onCreateEmptyView(inflater, layout, savedInstanceState);
		if (emptyView != null) {
			emptyView.setId(mAdapterViewOptions != null ? mAdapterViewOptions.emptyViewId() : AdapterViewOptions.EMPTY_VIEW_DEFAULT_ID);
			final FrameLayout.LayoutParams emptyParams = createEmptyViewParams();
			if (emptyParams != null) {
				layout.addView(emptyView, emptyParams);
			} else {
				layout.addView(emptyView);
			}
		}
		// Resolve adapter view.
		final View adapterView = createAdapterViewInner(inflater, layout, savedInstanceState);
		if (adapterView != null) {
			// Ensure that adapter view will have correct id.
			adapterView.setId(mAdapterViewOptions != null ? mAdapterViewOptions.viewId() : AdapterViewOptions.VIEW_DEFAULT_ID);
			final FrameLayout.LayoutParams adapterParams = createAdapterViewParams();
			if (adapterParams != null) {
				layout.addView(adapterView, adapterParams);
			} else {
				layout.addView(adapterView);
			}
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
	 * Wrapped {@link android.widget.Adapter#isEmpty()} on the current adapter.
	 *
	 * @see #getItemsCount()
	 */
	public boolean isAdapterEmpty() {
		return mAdapter == null || mAdapter.isEmpty();
	}

	/**
	 * Returns flag indicating whether this adapter fragment has adapter attached or not.
	 *
	 * @return <code>True</code> if adapter is attached, <code>false</code> otherwise.
	 */
	public boolean hasAdapter() {
		return mAdapter != null;
	}

	/**
	 */
	@Override
	public void onItemClick(@NonNull AdapterView<?> parent, @NonNull View view, int position, long id) {
	}

	/**
	 * By default, this will start the {@link android.support.v7.view.ActionMode} if
	 * {@link com.wit.android.support.fragment.annotation.ActionModeOptions @ActionModeOptions}
	 * annotation is presented above a subclass of this AdapterFragment.
	 */
	@Override
	public boolean onItemLongClick(@NonNull AdapterView<?> parent, @NonNull View view, int position, long id) {
		return (mActionModeOptions != null) && startActionModeInner(parent, view, position, id);
	}

	/**
	 * Getters + Setters ---------------------------------------------------------------------------
	 */

	/**
	 * Wrapped {@link android.widget.Adapter#getCount()} on the current adapter.
	 *
	 * @see #isAdapterEmpty()
	 */
	public int getItemsCount() {
		return mAdapter != null ? mAdapter.getCount() : 0;
	}

	/**
	 * Same as {@link #setEmptyView(android.view.View)}.
	 *
	 * @param resource A resource id of the desired layout with an empty view.
	 */
	public void setEmptyView(int resource) {
		if (mEmptyViewRes != resource) {
			setEmptyView(inflateView(mEmptyViewRes = resource));
		}
	}

	/**
	 * Sets the given <var>view</var> as empty view for this fragment's adapter view.
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
	 * Returns the current empty view.
	 *
	 * @return The view which was set as empty view for this fragment's adapter view.
	 */
	@Nullable
	public View getEmptyView() {
		return mEmptyView;
	}

	/**
	 * Same as {@link #setEmptyText(CharSequence)}.
	 *
	 * @param resId A resource id of the desired empty text.
	 */
	public void setEmptyText(int resId) {
		setEmptyText(obtainString(resId));
	}

	/**
	 * Sets the given text for the empty view. <b>Note</b>, that the given text will be set
	 * only in case that the current empty view is instance of {@link android.widget.TextView}.
	 *
	 * @param text The desired text to set for the empty view.
	 * @see #setEmptyText(int)
	 */
	public void setEmptyText(CharSequence text) {
		this.mEmptyText = text;
		if (mEmptyView instanceof TextView) {
			((TextView) mEmptyView).setText(text);
		}
	}

	/**
	 * Returns the text which was set for the empty view.
	 *
	 * @return The text presented in the empty view.
	 */
	public CharSequence getEmptyText() {
		return mEmptyText;
	}

	/**
	 * Returns an instance of the adapter view presented within this AdapterFragment implementation.
	 *
	 * @return Instance of the adapter view presented in the root view hierarchy of this AdapterFragment.
	 */
	public V getAdapterView() {
		return mAdapterView;
	}

	/**
	 * Sets adapter for this AdapterFragment implementation.
	 *
	 * @param adapter The adapter which should be used to populate the adapter view of this AdapterFragment.
	 * @see #getAdapter()
	 */
	public void setAdapter(A adapter) {
		this.setAdapterInner(adapter);
	}

	/**
	 * Returns the current adapter of this AdapterFragment.
	 *
	 * @return An instance of the current adapter.
	 * @see #setAdapter(android.widget.Adapter)
	 */
	public A getAdapter() {
		return mAdapter;
	}

	/**
	 * Changes visibility of the adapter view either to {@link View#VISIBLE} or {@link View#GONE} depends
	 * on the given <var>visible</var> flag.
	 *
	 * @param visible <code>True</code> to set adapter view visible, <code>false</code> to hide it.
	 */
	public void setAdapterViewVisible(boolean visible) {
		if (mAdapterView != null) {
			if (visible && mAdapterView.getVisibility() != View.VISIBLE) {
				mAdapterView.setVisibility(View.VISIBLE);
			} else if (!visible && mAdapterView.getVisibility() == View.VISIBLE) {
				mAdapterView.setVisibility(View.GONE);
			}
		}
	}

	/**
	 * Returns visibility of the current adapter view.
	 * <p/>
	 * <b>Note</b>, that this will always return visibility of the AdapterView presented within this
	 * fragment's root view, because AdapterView is for this fragment essential and is never <code>null</code>
	 * while this fragment has its root view created.
	 *
	 * @return <code>True</code> if the adapter view is visible, <code>false</code> if not or if it
	 * is not presented.
	 */
	public boolean isAdapterViewVisible() {
		return mAdapterView != null && mAdapterView.getVisibility() == View.VISIBLE;
	}

	/**
	 * Changes visibility of the empty view either to {@link View#VISIBLE} or {@link View#GONE} depends
	 * on the given <var>visible</var> flag.
	 *
	 * @param visible <code>True</code> to set empty view visible, <code>false</code> to hide it.
	 */
	public void setEmptyViewVisible(boolean visible) {
		if (mEmptyView != null) {
			if (visible && mEmptyView.getVisibility() != View.VISIBLE) {
				mEmptyView.setVisibility(View.VISIBLE);
			} else if (!visible && mEmptyView.getVisibility() == View.VISIBLE) {
				mEmptyView.setVisibility(View.GONE);
			}
		}
	}

	/**
	 * Returns visibility of the current empty view.
	 *
	 * @return <code>True</code> if the empty view is visible, <code>false</code> if not or if it is
	 * not presented.
	 */
	public boolean isEmptyViewVisible() {
		return mEmptyView != null && mEmptyView.getVisibility() == View.VISIBLE;
	}

	/**
	 * Protected -----------------------------------------------------------------------------------
	 */

	/**
	 * Invoked from {@link #onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)}
	 * to create layout where an AdapterView and its empty view will be placed. <b>Note</b>, that this
	 * is invoked only in case that <code>super.onCreateView(...)</code> doesn't creates a view for
	 * this fragment from the {@link com.wit.android.support.fragment.annotation.ContentView @ContentView}
	 * annotation.
	 * <p/>
	 * <b>Also note</b>, that this method should always return valid layout, otherwise {@link #onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)}
	 * method will fail and exception will be thrown.
	 *
	 * @param inflater           Valid layout inflater.
	 * @param container          The fragment root view into which will be created layout placed.
	 * @param savedInstanceState Bundle with saved state or <code>null</code> if this fragment's view
	 *                           is just being first time created.
	 * @return This returns be default an instance of {@link android.widget.FrameLayout}.
	 * @see #createLayoutParams()
	 */
	protected ViewGroup onCreateLayout(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return new FrameLayout(inflater.getContext());
	}

	/**
	 * Invoked to create a new instance of layout parameters for the layout in which will be AdapterView
	 * and its empty view placed.
	 *
	 * @return An instance of layout params. By default this creates params to <b>MATCH_PARENT</b> size.
	 * @see #onCreateLayout(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	protected ViewGroup.LayoutParams createLayoutParams() {
		return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
	}

	/**
	 * Invoked to create a new instance of the adapter view specific for this instance of AdapterFragment.
	 *
	 * @param inflater           Valid layout inflater.
	 * @param container          The root view into which will be created adapter view placed.
	 * @param savedInstanceState Bundle with saved state or <code>null</code> if this fragment's view
	 *                           is just being first time created.
	 * @return This returns by default <code>null</code>.
	 */
	protected V onCreateAdapterView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return null;
	}

	/**
	 * Called to create a new instance of layout parameters for this fragment's adapter view.
	 * <p/>
	 * Return <code>null</code> if the default layout parameters should be used when adding this view
	 * into parent's view hierarchy.
	 *
	 * @return An instance of layout params, depends on the root view of this AdapterFragment.
	 * By default this creates params to <b>MATCH_PARENT</b> size.
	 */
	protected FrameLayout.LayoutParams createAdapterViewParams() {
		return new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
	}

	/**
	 * Invoked to create a new instance of the view which will be set as empty view for this fragment's
	 * adapter view.
	 *
	 * @param inflater           Valid layout inflater.
	 * @param container          The root view into which will be created empty view placed.
	 * @param savedInstanceState Bundle with saved state or <code>null</code> if this fragment's view
	 *                           is just being first time created.
	 * @return An instance of empty view.
	 */
	protected View onCreateEmptyView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return new TextView(inflater.getContext(), null, android.R.attr.textViewStyle);
	}

	/**
	 * Called to create a new instance of the layout parameters for this fragment's empty view.
	 * <p/>
	 * Return <code>null</code> if the default layout parameters should be used when adding this view
	 * into parent's view hierarchy.
	 *
	 * @return An instance of layout params, depends on the root view of this AdapterFragment.
	 * By default this creates params to <b>WRAP_CONTENT</b> size with {@link Gravity#CENTER} gravity.
	 */
	protected FrameLayout.LayoutParams createEmptyViewParams() {
		final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.CENTER;
		return params;
	}

	/**
	 * Invoked from {@link #onViewCreated(android.view.View, android.os.Bundle)} after the root <var>view</var>
	 * is checked for valid adapter view.
	 *
	 * @param view               The root view of this AdapterFragment.
	 * @param adapterView        The adapter view obtained from {@link #onCreateAdapterView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)}
	 *                           or instantiated from the class provided by {@link com.wit.android.support.fragment.annotation.AdapterViewOptions @AdapterViewOptions}
	 *                           annotation, if presented.
	 * @param savedInstanceState Bundle with saved state or <code>null</code> if this fragment's view
	 *                           is just being first time created.
	 */
	protected void onViewCreated(@NonNull View view, @NonNull V adapterView, @Nullable Bundle savedInstanceState) {
		// Base set up.
		this.setAdapterInner(mAdapter);
		if (mEmptyView == null && mEmptyViewRes > 0) {
			this.mEmptyView = inflateView(mEmptyViewRes);
		}
		if (mEmptyView != null) {
			if (mAdapterViewOptions == null || mAdapterViewOptions.attachEmptyView()) {
				adapterView.setEmptyView(mEmptyView);
			}
			if (mEmptyView instanceof TextView) {
				if (mAdapterViewOptions != null && mAdapterViewOptions.emptyText() > 0) {
					((TextView) mEmptyView).setText(mAdapterViewOptions.emptyText());
				} else {
					((TextView) mEmptyView).setText(mEmptyText);
				}
			}
		}

		// Set up listeners if requested.
		if (mAdapterViewOptions != null) {
			if (mAdapterViewOptions.clickable()) {
				adapterView.setOnItemClickListener(this);
			}
			if (mAdapterViewOptions.longClickable()) {
				adapterView.setOnItemLongClickListener(this);
			}
		} else {
			// As default set up only item click listener.
			adapterView.setOnItemClickListener(this);
		}
	}

	/**
	 * Starts a loader with the specified <var>id</var>. If there was already started loader with the
	 * same id before, such a loader will be <b>re-started</b>, otherwise new loader will be <b>initialized</b>.
	 * <p/>
	 * See {@link LoaderManager#restartLoader(int, Bundle, LoaderManager.LoaderCallbacks)} and
	 * {@link LoaderManager#initLoader(int, Bundle, LoaderManager.LoaderCallbacks)} for more info.
	 *
	 * @param id        An id of the desired loader to start.
	 * @param params    Params for loader.
	 * @param callbacks Callbacks for loader.
	 * @return <code>True</code> if loader with the specified id was <b>initialized</b> or <b>re-started</b>,
	 * <code>false</code> if the current activity is already invalid or {@link LoaderManager} is not
	 * available.
	 */
	protected boolean startLoader(int id, @Nullable Bundle params, @NonNull LoaderManager.LoaderCallbacks callbacks) {
		if (isActivityAvailable()) {
			final LoaderManager loaderManager = getActivity().getSupportLoaderManager();
			if (loaderManager != null) {
				if (loaderManager.getLoader(id) != null) {
					loaderManager.restartLoader(id, params, callbacks);
				} else {
					loaderManager.initLoader(id, params, callbacks);
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * Starts a new action mode for this fragment. <b>Note</b>, that by default is this called
	 * from {@link #onItemLongClick(android.widget.AdapterView, android.view.View, int, long)} if
	 * there is {@link com.wit.android.support.fragment.annotation.ActionModeOptions @ActionModeOptions}
	 * annotation presented above a sub-class of this AdapterFragment. The started ActionMode will be
	 * populated by a menu inflated form the resource id provided by this annotation.
	 *
	 * @param callback    The callback used to manage action mode.
	 * @param adapterView The current adapter view.
	 * @param view        A view of the item from the current adapter view, because of which is this
	 *                    callback invoked.
	 * @param position    The position of an item from the current adapter's data set for which is
	 *                    being ActionMode started.
	 * @param id          An id of the item from the current adapter's data set for which is being
	 *                    ActionMode started.
	 * @return <code>True</code> if action mode has been started, <code>false</code> if this fragment
	 * is already in the action mode or the parent activity of this fragment is not available.
	 * @see #isInActionMode()
	 * @see #isActivityAvailable()
	 */
	protected boolean startActionMode(@NonNull ActionMode.Callback callback, @NonNull V adapterView, @NonNull View view, int position, long id) {
		if (!isInActionMode() && mActivity != null) {
			onActionModeStarted(
					mActionMode = getActionBarActivity().startSupportActionMode(callback),
					adapterView, view, position, id
			);
			return true;
		}
		return false;
	}

	/**
	 * Invoked immediately after {@link #startActionMode(android.support.v7.view.ActionMode.Callback, V, android.view.View, int, long)}
	 * was called and this fragment was not in the action mode yet.
	 *
	 * @param actionMode  Currently started action mode.
	 * @param adapterView The current adapter view.
	 * @param view        A view of the item from the current adapter view, because of which is this
	 *                    callback invoked.
	 * @param position    The position of an item from the current adapter's data set for which is
	 *                    being ActionMode started.
	 * @param id          An id of the item from the current adapter's data set for which is being
	 *                    ActionMode started.
	 */
	protected void onActionModeStarted(@NonNull ActionMode actionMode, @NonNull V adapterView, @NonNull View view, int position, long id) {
	}

	/**
	 */
	@Override
	void processClassAnnotations(Class<?> classOfFragment) {
		super.processClassAnnotations(classOfFragment);
		// Obtain adapter view options.
		this.mAdapterViewOptions = FragmentAnnotations.obtainAnnotationFrom(
				classOfFragment, AdapterViewOptions.class, AdapterFragment.class
		);
	}

	/**
	 * Private -------------------------------------------------------------------------------------
	 */

	/**
	 */
	@SuppressWarnings("unchecked")
	private void setAdapterInner(A adapter) {
		this.mAdapter = adapter;
		if (mAdapterView != null) {
			mAdapterView.setAdapter(mAdapter);
		}
	}

	/**
	 * Starts action mode inner to hide implementation.
	 * See {@link #startActionMode(android.support.v7.view.ActionMode.Callback, android.widget.AdapterView, android.view.View, int, long)}
	 * for info.
	 */
	@SuppressWarnings("unchecked")
	private boolean startActionModeInner(AdapterView<?> parent, View view, int position, long id) {
		return startActionMode(new ActionModeCallback(this), (V) parent, view, position, id);
	}

	/**
	 * Creates adapter view internally to hide implementation. This will also check for class of
	 * adapter view to initialize within {@link com.wit.android.support.fragment.annotation.AdapterViewOptions @AdapterViewOptions}
	 * if it is presented.
	 */
	@SuppressWarnings("unchecked")
	private V createAdapterViewInner(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		V adapterView = null;
		if (mAdapterViewOptions != null) {
			// Try to create adapter view from class provided within annotation.
			final Class<? extends AdapterView> classOfAdapterView = mAdapterViewOptions.viewType();
			if (!AdapterView.class.equals(classOfAdapterView)) {
				try {
					adapterView = (V) classOfAdapterView.getConstructor(Context.class).newInstance(inflater.getContext());
				} catch (java.lang.InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
					Log.e(TAG, "Failed to instantiate an instance of(" + classOfAdapterView.getSimpleName() + "). ", e);
				}
			}
		}
		return adapterView == null ? onCreateAdapterView(inflater, container, savedInstanceState) : adapterView;
	}

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
	 * Inflates a new instance of the view hierarchy for the specified <var>resource</var>.
	 *
	 * @param resource The resource id of the view to inflate.
	 * @return Inflated view hierarchy or <code>null</code> if the parent activity is not available.
	 */
	private View inflateView(int resource) {
		if (isActivityAvailable()) {
			final LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			return inflater.inflate(resource, (ViewGroup) getView(), false);
		}
		return null;
	}

	/**
	 * Inner classes ===============================================================================
	 */

	/**
	 * <h4>Class Overview</h4>
	 * todo: description
	 *
	 * @author Martin Albedinsky
	 */
	public static class ActionModeCallback extends ActionBarFragment.ActionModeCallback {

		/**
		 * Creates a new instance of ActionModeCallback without fragment.
		 */
		public ActionModeCallback() {
			this(null);
		}

		/**
		 * Creates a new instance of ActionModeCallback for the context of the given <var>fragment</var>.
		 *
		 * @param fragment The instance of adapter fragment in which was this action mode started.
		 */
		public ActionModeCallback(AdapterFragment fragment) {
			super(fragment);
		}
	}
}
