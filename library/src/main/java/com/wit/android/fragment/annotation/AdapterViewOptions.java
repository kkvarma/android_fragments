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
package com.wit.android.fragment.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <h4>Annotation Overview</h4>
 * <p>
 * Defines an annotation for determining how an {@link android.widget.AdapterView} and all necessary
 * stuffs around it should be set.
 * </p>
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
	 * <p>
	 * The default id for an AdapterView.
	 * </p>
	 */
	public static final int VIEW_DEFAULT_ID = android.R.id.list;

	/**
	 * <p>
	 * The default id for an empty view of AdapterView.
	 * </p>
	 */
	public static final int EMPTY_VIEW_DEFAULT_ID = android.R.id.empty;

	/**
	 * Methods =====================================================================================
	 */

	/**
	 * The id for an AdapterView.
	 */
	int viewId() default VIEW_DEFAULT_ID;

	/**
	 * The id for an empty view.
	 */
	int emptyViewId() default EMPTY_VIEW_DEFAULT_ID;

	/**
	 * The resource id of the text for an empty view (if instance of {@link android.widget.TextView}).
	 */
	int emptyText() default -1;

	/**
	 * Flag indicating whether items presented within an AdapterView are long-clickable or not.
	 */
	boolean longClickable() default false;
}
