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

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wit.android.fragment.annotation.ClickableViews;
import com.wit.android.fragment.annotation.ContentView;
import com.wit.android.fragment.annotation.InjectView;
import com.wit.android.fragment.annotation.InjectViews;
import com.wit.android.fragment.util.FragmentAnnotations;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * <h4>Class Overview</h4>
 * todo: description
 * <h6>Accepted annotations</h6>
 * <ul>
 * <li>{@link com.wit.android.fragment.annotation.ContentView @ContentView} [<b>class - inherited</b>]</li>
 * <p>
 * If this annotation is presented, the layout id presented within this annotation will be used to
 * inflate the root view for an instance of annotated BaseFragment sub-class in
 * {@link #onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)}.
 * </p>
 * <li>{@link com.wit.android.fragment.annotation.ClickableViews @ClickableViews} [<b>class - inherited</b>]</li>
 * <p>
 * If this annotation is presented, an inner {@link android.view.View.OnClickListener} will be attached
 * to all views found by ids presented within this annotation. If any of these views is clicked,
 * {@link #onViewClick(android.view.View, int)} will be invoked with that particular view and its id.
 * <p/>
 * OnClickListener is attached to these views whenever {@link #onViewCreated(android.view.View, android.os.Bundle)}
 * is called.
 * </p>
 * <li>{@link com.wit.android.fragment.annotation.InjectView @InjectView} [<b>member - recursive</b>]</li>
 * <li>{@link com.wit.android.fragment.annotation.InjectView.Last @InjectView.Last} [<b>member - recursive</b>]</li>
 * <p>
 * All fields marked with this annotation will be automatically injected (by {@link android.view.View#findViewById(int)})
 * using the root view passed to {@link #onViewCreated(android.view.View, android.os.Bundle)}.
 * <b>Note that {@link com.wit.android.fragment.annotation.InjectViews @InjectViews}
 * annotation is required above each sub-class of BaseFragment to run injecting process, otherwise
 * all marked fields (views) of such a sub-class will be ignored.</b>
 * <p/>
 * All marked view fields are injected whenever {@link #onViewCreated(android.view.View, android.os.Bundle)}
 * is called.
 * </p>
 * </ul>
 *
 * @author Martin Albedinsky
 * @see com.wit.android.fragment.ActionBarFragment
 */
public abstract class BaseFragment extends Fragment implements BackPressWatcher, ViewClickWatcher {

	/**
	 * Interface ===================================================================================
	 */

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * Log TAG.
	 */
	// private static final String TAG = "BaseFragment";

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
	 * Static members ==============================================================================
	 */

	/**
	 * Members =====================================================================================
	 */

	/**
	 * Activity to which is this instance of fragment currently attached, <code>null</code> if this
	 * fragment is not attached to any activity.
	 */
	Activity mActivity;

	/**
	 * Stores all private flags for this object.
	 */
	int mPrivateFlags;

	/**
	 * Content view annotation holding configuration for the root view of this fragment.
	 */
	private ContentView mContentView;

	/**
	 * Array with ids of views to which should be attached instance of {@link android.view.View.OnClickListener}.
	 * When one of these views is clicked, {@link #onViewClick(android.view.View, int)} is invoked to
	 * handle click event.
	 */
	private List<Integer> mClickableViewIds;

	/**
	 * Array with gathered view fields which should be injected to this fragment whenever the new
	 * root view hierarchy is created for this fragment instance.
	 */
	private List<Field> mViewsToInject;

	/**
	 * Constructors ================================================================================
	 */

	/**
	 * Creates a new instance of BaseFragment. If {@link com.wit.android.fragment.annotation.ContentView @ContentView}
	 * or {@link com.wit.android.fragment.annotation.ClickableViews @ClickableViews} annotations
	 * are presented above a sub-class of BaseFragment, they will be processed here. Also all declared
	 * fields marked by annotation {@link com.wit.android.fragment.annotation.InjectView @InjectView}
	 * or {@link com.wit.android.fragment.annotation.InjectView.Last @InjectView.Last} will
	 * be recursively gathered and stored to be later injected.
	 */
	public BaseFragment() {
		processClassAnnotations(((Object) this).getClass());
	}

	/**
	 * Methods =====================================================================================
	 */

	/**
	 * Public --------------------------------------------------------------------------------------
	 */

	/**
	 * Creates a new instance of the given <var>classOfFragment</var> with the given <var>args</var>.
	 *
	 * @param classOfFragment Class of the desired fragment to instantiate.
	 * @param args            Arguments to set to new instance of fragment by {@link Fragment#setArguments(android.os.Bundle)}.
	 * @param <F>             Type of the desired fragment.
	 * @return New instance of fragment with the given arguments or <code>null</code> if some instantiation
	 * error occurs.
	 */
	@Nullable
	public static <F extends Fragment> F newInstanceWithArguments(@NonNull Class<F> classOfFragment, @Nullable Bundle args) {
		try {
			final F fragment = classOfFragment.newInstance();
			fragment.setArguments(args);
			return fragment;
		} catch (java.lang.InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mActivity = activity;
	}

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
		// Set up clickable views.
		final ClickListener clickListener = new ClickListener();
		if (mClickableViewIds != null) {
			for (int id : mClickableViewIds) {
				View child = view.findViewById(id);
				if (child == null) {
					throw new NullPointerException("Clickable view with id(" + id + ") not found.");
				}
				child.setOnClickListener(clickListener);
			}
		}

		if (mViewsToInject != null) {
			for (Field field : mViewsToInject) {
				FragmentAnnotations.injectView(field, this, view, clickListener);
			}
		}
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
	 */
	@Override
	public void onDetach() {
		super.onDetach();
		this.mActivity = null;
	}

	/**
	 */
	@Override
	public boolean dispatchBackPressed() {
		return onBackPressed();
	}

	/**
	 */
	@Override
	public boolean dispatchViewClick(View view) {
		return onViewClick(view, view.getId());
	}

	/**
	 * Returns flag indicating whether this fragment instance was restored or not.
	 *
	 * @return <code>True</code> if this fragment was restored (<i>like, after orientation change</i>),
	 * <code>false</code> otherwise.
	 */
	public boolean isRestored() {
		return hasPrivateFlag(PFLAG_RESTORED);
	}

	/**
	 * Returns flag indicating whether the view was restored or not.
	 *
	 * @return <code>True</code> if the view of this fragment was restored (<i>like, when the fragment
	 * was showed from the back stack</i>), <code>false</code> otherwise.
	 */
	public boolean isViewRestored() {
		return hasPrivateFlag(PFLAG_VIEW_RESTORED);
	}

	/**
	 * Returns flag indicating whether the view is already created or not.
	 *
	 * @return <code>True</code> if the view of this fragment is already created, <code>false</code>
	 * otherwise.
	 */
	public boolean isViewCreated() {
		return getView() != null;
	}

	/**
	 * Same as {@link #getString(int)}, but first is performed check if the parent activity of this
	 * fragment instance is available to prevent illegal state exceptions.
	 */
	@NonNull
	public String obtainString(@StringRes int resId) {
		return isActivityAvailable() ? getString(resId) : "";
	}

	/**
	 * Same as  {@link #getString(int, Object...)}, but first is performed check if the parent activity
	 * of this fragment instance is available to prevent illegal state exceptions.
	 */
	@NonNull
	public String obtainString(@StringRes int resId, @Nullable Object... args) {
		return isActivityAvailable() ? getString(resId, args) : "";
	}

	/**
	 * Same as {@link #getText(int)}, but first is performed check if the parent activity of this
	 * fragment instance is available to prevent illegal state exceptions.
	 */
	@NonNull
	public CharSequence obtainText(@StringRes int resId) {
		return isActivityAvailable() ? getText(resId) : "";
	}

	/**
	 * Wrapped {@link android.app.Activity#runOnUiThread(Runnable)} on this fragment's parent activity.
	 *
	 * @return <code>True</code> if parent activity is available and action was posted, <code>false</code>
	 * otherwise.
	 */
	public final boolean runOnUiThread(@NonNull Runnable action) {
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
	 * Invoked immediately after {@link #dispatchBackPressed()} was called to process back press event.
	 *
	 * @return <code>True</code> if this instance of fragment processes dispatched back press event,
	 * <code>false</code> otherwise.
	 */
	protected boolean onBackPressed() {
		return false;
	}

	/**
	 * Invoked immediately after {@link #dispatchViewClick(android.view.View)} was called to process
	 * click event on the given <var>view</var>.
	 *
	 * @param view The view which was clicked.
	 * @param id   The id of the clicked view.
	 * @return <code>True</code> if this fragment handles dispatched click event for the given
	 * <var>view</var>, <code>false</code> otherwise.
	 */
	protected boolean onViewClick(@NonNull View view, int id) {
		return false;
	}

	/**
	 * Returns flag indicating whether the parent Activity of this fragment instance is available or not.
	 * <p/>
	 * Parent activity is always available between {@link #onAttach(android.app.Activity)} and
	 * {@link #onDetach()} life cycle calls.
	 *
	 * @return <code>True</code> if activity is available, <code>false</code> otherwise.
	 */
	protected boolean isActivityAvailable() {
		return mActivity != null;
	}

	/**
	 * Called to process all annotations of the specified <var>classOfFragment</var>.
	 *
	 * @param classOfFragment The class of which annotations to process.
	 */
	void processClassAnnotations(Class<?> classOfFragment) {
		// Obtain content view.
		this.mContentView = FragmentAnnotations.obtainAnnotationFrom(classOfFragment, ContentView.class, BaseFragment.class);
		// Obtain clickable view ids.
		// Note, that we will gather ids from all annotated class to this parent.
		this.mClickableViewIds = this.gatherClickableViewIds(classOfFragment, new ArrayList<Integer>());
		if (mClickableViewIds.isEmpty()) {
			this.mClickableViewIds = null;
		}
		// Store all fields to inject as views.
		this.mViewsToInject = new ArrayList<>();
		this.iterateInjectableViewFields(classOfFragment, new FragmentAnnotations.FieldProcessor() {

			/**
			 */
			@Override
			public void onProcessField(@NonNull Field field, String name) {
				if (field.isAnnotationPresent(InjectView.class) || field.isAnnotationPresent(InjectView.Last.class)) {
					mViewsToInject.add(field);
				}
			}
		});
		if (mViewsToInject.isEmpty()) {
			this.mViewsToInject = null;
		}
	}

	/**
	 * Private -------------------------------------------------------------------------------------
	 */

	/**
	 * Gathers all ids presented within ClickableViews annotation. Note, that this is recursive method,
	 * which will gather all ids from {@link com.wit.android.fragment.annotation.ClickableViews}
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
	 * Iterates all declared fields of the given <var>classOfFragment</var> using
	 * {@link FragmentAnnotations#iterateFields(Class, FragmentAnnotations.FieldProcessor)} utility
	 * method passing it the given <var>fieldProcessor</var> and classOfFragment.
	 *
	 * @param classOfFragment Class of which fields to iterate.
	 * @param fieldProcessor  Field processor to handle fields iteration.
	 */
	private void iterateInjectableViewFields(Class<?> classOfFragment, FragmentAnnotations.FieldProcessor fieldProcessor) {
		if (classOfFragment.isAnnotationPresent(InjectViews.class)) {
			FragmentAnnotations.iterateFields(classOfFragment, fieldProcessor);
		}
		// Iterate also fields of super class, but only to this BaseFragment super.
		final Class<?> superOfFragment = classOfFragment.getSuperclass();
		if (superOfFragment != null && !superOfFragment.equals(BaseFragment.class)) {
			iterateInjectableViewFields(superOfFragment, fieldProcessor);
		}
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
	 * Updates the current private flags.
	 *
	 * @param flag Value of the desired flag to add/remove to/from the current private flags.
	 * @param add  Boolean flag indicating whether to add or remove the specified <var>flag</var>.
	 */
	void updatePrivateFlags(int flag, boolean add) {
		if (add) {
			this.mPrivateFlags |= flag;
		} else {
			this.mPrivateFlags &= ~flag;
		}
	}

	/**
	 * Returns a boolean flag indicating whether the specified <var>flag</var> is contained within
	 * the current private flags or not.
	 *
	 * @param flag Value of the flag to check.
	 * @return <code>True</code> if the requested flag is contained, <code>false</code> otherwise.
	 */
	boolean hasPrivateFlag(int flag) {
		return (mPrivateFlags & flag) != 0;
	}

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
}
