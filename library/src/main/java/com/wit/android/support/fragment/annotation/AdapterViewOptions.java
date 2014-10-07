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
package com.wit.android.support.fragment.annotation;

import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.widget.AdapterView;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <h4>Annotation Overview</h4>
 * Defines an annotation for determining how an {@link android.widget.AdapterView} and all necessary
 * stuffs around it should be set.
 * <h6>Usage</h6>
 * <ul>
 * <li>{@link com.wit.android.support.fragment.AdapterFragment AdapterFragment}</li>
 * </ul>
 *
 * @author Martin Albedinsky
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AdapterViewOptions {

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * The default id for an AdapterView.
	 */
	public static final int VIEW_DEFAULT_ID = android.R.id.list;

	/**
	 * The default id for an empty view of AdapterView.
	 */
	public static final int EMPTY_VIEW_DEFAULT_ID = android.R.id.empty;

	/**
	 * Methods =====================================================================================
	 */

	/**
	 * A class of the desired AdapterView to be instantiated.
	 */
	Class<? extends AdapterView> viewType() default AdapterView.class;

	/**
	 * The desired id for an AdapterView.
	 * <p/>
	 * Default value: <b>{@link #VIEW_DEFAULT_ID}</b>
	 */
	@IdRes
	int viewId() default VIEW_DEFAULT_ID;

	/**
	 * Flag indicating whether an empty view should be attached to AdapterView by
	 * {@link android.widget.AdapterView#setEmptyView(android.view.View)} or not.
	 * <p/>
	 * Default value: <b>true</b>
	 */
	boolean attachEmptyView() default true;

	/**
	 * The desired id for an empty view.
	 * <p/>
	 * Default value: <b>{@link #EMPTY_VIEW_DEFAULT_ID}</b>
	 */
	@IdRes
	int emptyViewId() default EMPTY_VIEW_DEFAULT_ID;

	/**
	 * A resource id of the desired text for an empty view (if instance of {@link android.widget.TextView}).
	 * <p/>
	 * Default value: <b>-1</b>
	 */
	@StringRes
	int emptyText() default -1;

	/**
	 * Flag indicating whether items presented within an AdapterView are <b>clickable</b> or not.
	 * <p/>
	 * Default value: <b>true</b>
	 */
	boolean clickable() default true;

	/**
	 * Flag indicating whether items presented within an AdapterView are <b>long-clickable</b> or not.
	 * <p/>
	 * Default value: <b>false</b>
	 */
	boolean longClickable() default false;
}
