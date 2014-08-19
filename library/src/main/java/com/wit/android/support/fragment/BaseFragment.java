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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wit.android.support.fragment.annotation.ClickableViews;
import com.wit.android.support.fragment.annotation.ContentView;
import com.wit.android.support.fragment.util.FragmentAnnotations;

import java.util.ArrayList;
import java.util.List;

/**
 * <h4>Class Overview</h4>
 * <p>
 * todo: description
 * </p>
 * <h6>Accepted annotations</h6>
 * <ul>
 * <li>{@link com.wit.android.support.fragment.annotation.ContentView @ContentView} [<b>class, recursively</b>]</li>
 * <p>
 * If this annotation is presented, the layout id presented within this annotation will be used to
 * inflate the root view for an instance of annotated BaseFragment sub-class in
 * {@link #onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)}.
 * </p>
 * <li>{@link com.wit.android.support.fragment.annotation.ClickableViews @ClickableViews} [<b>class, recursively</b>]</li>
 * <p>
 * If this annotation is presented, an inner {@link android.view.View.OnClickListener} will be attached
 * to all views found by ids presented within this annotation. If any of these views is clicked,
 * {@link #onViewClick(android.view.View, int)} will be invoked with that particular view and its id.
 * </p>
 * <li>{@link com.wit.android.support.fragment.annotation.InjectView @InjectView} [<b>field</b>]</li>
 * <li>{@link com.wit.android.support.fragment.annotation.InjectView.Last @InjectView.Last} [<b>field</b>]</li>
 * <p>
 * All fields marked with this annotation will be automatically injected (by {@link android.view.View#findViewById(int)})
 * using the root view passed to {@link #onViewCreated(android.view.View, android.os.Bundle)}.
 * <b>Note that {@link com.wit.android.support.fragment.annotation.InjectViews @InjectViews} [class]
 * annotation is required above each sub-class of BaseFragment to run injecting process, otherwise
 * all marked fields (views) of such a sub-class will be ignored.</b>
 * </p>
 * </ul>
 *
 * @author Martin Albedinsky
 * @see com.wit.android.support.fragment.ActionBarFragment
 */
public abstract class BaseFragment extends Fragment {

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * Log TAG.
	 */
	// private static final String TAG = BaseFragment.class.getSimpleName();

	/**
	 * Flag indicating whether the debug output trough log-cat is enabled or not.
	 */
	// private static final boolean DEBUG_ENABLED = true;

	/**
	 * Flag indicating whether the output trough log-cat is enabled or not.
	 */
	// private static final boolean LOG_ENABLED = true;

	/**
	 * Flag indicating whether this instance of fragment is restored (like after orientation change)
	 * or not.
	 */
	private static final int PFLAG_RESTORED = 0x01;

	/**
	 * Flag indicating whether the view of this instance of fragment is restored or not.
	 */
	private static final int PFLAG_VIEW_RESTORED = 0x02;

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
	private ContentView mContentView = null;

	/**
	 * Stores all private flags for this object.
	 */
	private int mPrivateFlags;

	/**
	 * Arrays --------------------------------------------------------------------------------------
	 */

	/**
	 *
	 */
	private List<Integer> aClickableViewIds;

	/**
	 * Booleans ------------------------------------------------------------------------------------
	 */

	/**
	 * Constructors ================================================================================
	 */

	/**
	 * <p>
	 * Creates a new instance of BaseFragment. If {@link com.wit.android.support.fragment.annotation.ContentView @ContentView}
	 * or {@link com.wit.android.support.fragment.annotation.ClickableViews @ClickableViews} annotations
	 * are presented above a sub-class of BaseFragment, they will be processed here.
	 * </p>
	 */
	public BaseFragment() {
		final Class<?> classOfFragment = ((Object) this).getClass();
		/**
		 * Process class annotations.
		 */
		// Obtain content view.
		this.mContentView = FragmentAnnotations.obtainAnnotationFrom(classOfFragment, ContentView.class, BaseFragment.class);
		// Obtain clickable view ids.
		// Note, that we will gather ids from all annotated class to this parent.
		this.aClickableViewIds = this.gatherClickableViewIds(classOfFragment, new ArrayList<Integer>());
		if (aClickableViewIds.isEmpty()) {
			this.aClickableViewIds = null;
		}
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
		this.updatePrivateFlags(PFLAG_VIEW_RESTORED, false);
		this.updatePrivateFlags(PFLAG_RESTORED, savedInstanceState != null);
	}

	/**
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (mContentView != null) {
			if (mContentView.attachToRoot()) {
				inflater.inflate(mContentView.value(), container, true);
				return null;
			}
			return inflater.inflate(mContentView.value(), container, false);
		}
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	/**
	 */
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		// Resolve view background.
		if (mContentView != null) {
			if (mContentView.backgroundRes() >= 0) {
				view.setBackgroundResource(mContentView.backgroundRes());
			}
		}
		// Resolve clickable views.
		if (aClickableViewIds != null && !aClickableViewIds.isEmpty()) {
			// Set up clickable views.
			final ClickListener clickListener = new ClickListener();
			for (int id : aClickableViewIds) {
				View child = view.findViewById(id);
				if (child == null) {
					throw new NullPointerException("Clickable view with id(" + id + ") not found.");
				}
				child.setOnClickListener(clickListener);
			}
		}
		FragmentAnnotations.injectFragmentViews(this, BaseFragment.class);
	}

	/**
	 */
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		this.updatePrivateFlags(PFLAG_VIEW_RESTORED, true);
	}

	/**
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		this.updatePrivateFlags(PFLAG_VIEW_RESTORED, false);
	}

	/**
	 * <p>
	 * Called to dispatch back press event to this fragment instance.
	 * </p>
	 *
	 * @return <code>True</code> if this instance of fragment processes dispatched back press event,
	 * <code>false</code> otherwise.
	 */
	public boolean dispatchBackPressed() {
		return onBackPressed();
	}

	/**
	 * <p>
	 * Called to dispatch view click event to this fragment instance.
	 * </p>
	 *
	 * @param view The view which was clicked.
	 * @return <code>True</code> if this fragment handles dispatched click event for the given
	 * <var>view</var>, <code>false</code> otherwise.
	 */
	public boolean dispatchViewClick(View view) {
		return onViewClick(view, view.getId());
	}

	/**
	 * <p>
	 * Returns flag indicating whether this fragment instance was restored or not.
	 * </p>
	 *
	 * @return <code>True</code> if this fragment was restored (<i>like, after orientation change</i>),
	 * <code>false</code> otherwise.
	 */
	public boolean isRestored() {
		return hasPrivateFlag(PFLAG_RESTORED);
	}

	/**
	 * <p>
	 * Returns flag indicating whether the view was restored or not.
	 * </p>
	 *
	 * @return <code>True</code> if the view of this fragment was restored (<i>like, when the fragment
	 * was showed from the back stack</i>), <code>false</code> otherwise.
	 */
	public boolean isViewRestored() {
		return hasPrivateFlag(PFLAG_VIEW_RESTORED);
	}

	/**
	 * <p>
	 * Returns flag indicating whether the view is already created or not.
	 * </p>
	 *
	 * @return <code>True</code> if the view of this fragment is already created, <code>false</code>
	 * otherwise.
	 */
	public boolean isViewCreated() {
		return getView() != null;
	}

	/**
	 * <p>
	 * Same as {@link #getString(int)}, but first is performed check if the parent activity of this
	 * fragment instance is available to prevent illegal state exceptions.
	 * </p>
	 */
	public String obtainString(int resId) {
		return isActivityAvailable() ? getString(resId) : "";
	}

	/**
	 * <p>
	 * <p>
	 * Same as  {@link #getString(int, Object...)}, but first is performed check if the parent activity
	 * of this fragment instance is available to prevent illegal state exceptions.
	 * </p>
	 * </p>
	 */
	public String obtainString(int resId, Object... args) {
		return isActivityAvailable() ? getString(resId, args) : "";
	}

	/**
	 * <p>
	 * Same as {@link #getText(int)}, but first is performed check if the parent activity of this
	 * fragment instance is available to prevent illegal state exceptions.
	 * </p>
	 */
	public CharSequence obtainText(int resId) {
		return isActivityAvailable() ? getText(resId) : "";
	}

	/**
	 * <p>
	 * Wrapped {@link android.app.Activity#runOnUiThread(Runnable)} on this fragment's parent activity.
	 * </p>
	 *
	 * @return <code>True</code> if parent activity is available and action was posted, <code>false</code>
	 * otherwise.
	 */
	public final boolean runOnUiThread(Runnable action) {
		if (isActivityAvailable()) {
			getActivity().runOnUiThread(action);
			return true;
		}
		return false;
	}

	/**
	 * Getters + Setters ---------------------------------------------------------------------------
	 */

	/**
	 * Protected -----------------------------------------------------------------------------------
	 */

	/**
	 * <p>
	 * Invoked immediately after {@link #dispatchBackPressed()} was called to process back press event.
	 * </p>
	 *
	 * @return <code>True</code> if this instance of fragment processes dispatched back press event,
	 * <code>false</code> otherwise.
	 */
	protected boolean onBackPressed() {
		return false;
	}

	/**
	 * <p>
	 * Invoked immediately after {@link #dispatchViewClick(android.view.View)} was called to process
	 * click event on the given <var>view</var>.
	 * </p>
	 *
	 * @param view The view which was clicked.
	 * @param id   The id of the clicked view.
	 * @return <code>True</code> if this fragment handles dispatched click event for the given
	 * <var>view</var>, <code>false</code> otherwise.
	 */
	protected boolean onViewClick(View view, int id) {
		return false;
	}

	/**
	 * <p>
	 * Returns flag indicating whether the parent Activity of this fragment instance is available or not.
	 * </p>
	 *
	 * @return <code>True</code> if activity is available, <code>false</code> otherwise.
	 */
	protected boolean isActivityAvailable() {
		return getActivity() != null;
	}

	/**
	 * Private -------------------------------------------------------------------------------------
	 */

	/**
	 * Gathers all ids presented within ClickableViews annotation. Note, that this is recursive method,
	 * which will gather all ids from {@link com.wit.android.support.fragment.annotation.ClickableViews}
	 * annotation presented above the given <var>classOfFragment</var>.
	 *
	 * @param classOfFragment Class of fragment where to check ClickableViews annotation.
	 * @param ids             List of already gathered ids.
	 * @return List of all gathered clickable view's ids.
	 */
	private List<Integer> gatherClickableViewIds(Class<?> classOfFragment, List<Integer> ids) {
		if (classOfFragment.isAnnotationPresent(ClickableViews.class)) {
			final ClickableViews clickableViews = classOfFragment.getAnnotation(ClickableViews.class);
			if (clickableViews.value().length > 0) {
				ids.addAll(idsToList(clickableViews.value()));
			}
		}

		// Obtain also ids of super class, but only to this BaseFragment super.
		final Class<?> superOfFragment = classOfFragment.getSuperclass();
		if (superOfFragment != null && !superOfFragment.equals(BaseFragment.class)) {
			gatherClickableViewIds(superOfFragment, ids);
		}
		return ids;
	}

	/**
	 * Converts the given <var>ids</var> array to list.
	 *
	 * @param ids The array of ids to convert.
	 * @return Converted list of ids.
	 */
	private List<Integer> idsToList(int[] ids) {
		final List<Integer> idsList = new ArrayList<>(ids.length);
		for (int id : ids) {
			idsList.add(id);
		}
		return idsList;
	}

	/**
	 * Updates current private flags.
	 *
	 * @param flag The value of the flag to add/remove from current private flags.
	 * @param add  Boolean flag indicating whether to add or remove the specified <var>flag</var>.
	 */
	private void updatePrivateFlags(int flag, boolean add) {
		if (add) {
			this.mPrivateFlags |= flag;
		} else {
			this.mPrivateFlags &= ~flag;
		}
	}

	/**
	 * Returns boolean flag indicating whether the specified <var>flag</var> is contained within
	 * current private flags.
	 *
	 * @param flag The value of the flag to check.
	 * @return <code>True</code> if the requested flag is contained, <code>false</code> otherwise.
	 */
	private boolean hasPrivateFlag(int flag) {
		return (mPrivateFlags & flag) != 0;
	}

	/**
	 * Abstract methods ----------------------------------------------------------------------------
	 */

	/**
	 * Inner classes ===============================================================================
	 */

	/**
	 * Simple on view click listener to attach to clickable views of this fragment's class.
	 */
	private final class ClickListener implements View.OnClickListener {

		/**
		 */
		@Override
		public void onClick(View view) {
			dispatchViewClick(view);
		}
	}

	/**
	 * Interface ===================================================================================
	 */
}
