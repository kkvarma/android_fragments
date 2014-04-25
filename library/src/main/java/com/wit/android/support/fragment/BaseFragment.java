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
import com.wit.android.support.fragment.annotation.InjectView;
import com.wit.android.support.fragment.util.FragmentAnnotations;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * <h4>Class Overview</h4>
 * <p>
 * </p>
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
	 *
	 */
	private ClickableViews mClickableViews = null;

	/**
	 * Listeners -----------------------------------------------------------------------------------
	 */

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
	 * Flag indicating whether this instance of fragment is restored (like after orientation change)
	 * or not.
	 */
	private boolean bRestored = false;

	/**
	 * Flag indicating whether the view of this instance of fragment is restored or not.
	 */
	private boolean bViewRestored = false;

	/**
	 * Constructors ================================================================================
	 */

	/**
	 * <p>
	 * </p>
	 */
	public BaseFragment() {
		final Class<?> classOfFragment = ((Object) this).getClass();
		/**
		 * Process class annotations.
		 */
		// Retrieve content view.
		this.mContentView = FragmentAnnotations.obtainAnnotationFrom(classOfFragment, ContentView.class);
		// Retrieve clickable view ids.
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
		this.bViewRestored = false;
		this.bRestored = savedInstanceState != null;
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
		// Check views which should be injected.
		this.injectViews(((Object) this).getClass(), view);
	}

	/**
	 */
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		this.bViewRestored = true;
	}

	/**
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		this.bViewRestored = false;
	}

	/**
	 * <p>
	 * Called to dispatch back press action to this fragment instance.
	 * </p>
	 *
	 * @return <code>True</code> if this instance of fragment processes dispatched back press action,
	 * <code>false</code> otherwise.
	 */
	public boolean dispatchBackPressed() {
		return onBackPressed();
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
		return bRestored;
	}

	/**
	 * <p>
	 * Returns flag indicating whether the view of this fragment instance was restored or not.
	 * </p>
	 *
	 * @return <code>True</code> if the view of this fragment was restored (<i>like, when the fragment
	 * was showed from the back stack</i>), <code>false</code> otherwise.
	 */
	public boolean isViewRestored() {
		return bViewRestored;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 */
	public boolean isViewCreated() {
		return getView() != null;
	}

	/**
	 * <p>
	 * Same as  {@link #getString(int)}, but first is performed check if the parent activity of this
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
	 * </p>
	 *
	 * @param view
	 * @return
	 */
	public boolean dispatchViewClick(View view) {
		return onViewClick(view, view.getId());
	}

	/**
	 * Getters + Setters ---------------------------------------------------------------------------
	 */

	/**
	 * Protected -----------------------------------------------------------------------------------
	 */

	/**
	 * <p>
	 * Invoked to process back press action dispatched to this fragment instance.
	 * </p>
	 *
	 * @return <code>True</code> if this instance of fragment processes dispatched back press action,
	 * <code>false</code> otherwise.
	 * @see #dispatchBackPressed()
	 */
	protected boolean onBackPressed() {
		return false;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param view
	 * @param id
	 * @return
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
	 * @param classOfFragment
	 * @param view
	 */
	private void injectViews(Class<?> classOfFragment, View view) {
		// Process annotated fields.
		final Field[] fields = classOfFragment.getDeclaredFields();
		if (fields.length > 0) {
			for (Field field : fields) {
				if (field.isAnnotationPresent(InjectView.class)) {
					// Check correct type of the field.
					final Class<?> classOfField = field.getType();
					if (View.class.isAssignableFrom(classOfField)) {
						field.setAccessible(true);
						try {
							field.set(
									this,
									view.findViewById(field.getAnnotation(InjectView.class).value())
							);
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}

		// Inject also views of supper class, but only to this BaseFragment super.
		final Class<?> superOfFragment = classOfFragment.getSuperclass();
		if (superOfFragment != null && !superOfFragment.equals(BaseFragment.class)) {
			injectViews(superOfFragment, view);
		}
	}

	/**
	 *
	 * @param classOfFragment
	 * @param ids
	 * @return
	 */
	private List<Integer> gatherClickableViewIds(Class<?> classOfFragment, List<Integer> ids) {
		if (classOfFragment.isAnnotationPresent(ClickableViews.class)) {
			final ClickableViews clickableViews = classOfFragment.getAnnotation(ClickableViews.class);
			if (clickableViews.value().length > 0) {
				ids.addAll(idsToList(clickableViews.value()));
			}
		}

		// Retrieve also ids of super class, but only to this BaseFragment super.
		final Class<?> superOfFragment = classOfFragment.getSuperclass();
		if (superOfFragment != null && !superOfFragment.equals(BaseFragment.class)) {
			gatherClickableViewIds(superOfFragment, ids);
		}
		return ids;
	}

	/**
	 *
	 * @param ids
	 * @return
	 */
	private List<Integer> idsToList(int[] ids) {
		final List<Integer> idsList = new ArrayList<>(ids.length);
		for (int id : ids) {
			idsList.add(id);
		}
		return idsList;
	}

	/**
	 * Abstract methods ----------------------------------------------------------------------------
	 */

	/**
	 * Inner classes ===============================================================================
	 */

	/**
	 *
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
