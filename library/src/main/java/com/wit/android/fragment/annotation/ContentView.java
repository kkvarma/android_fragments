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
 * Defines an annotation for determining which layout should be inflated as the root view.
 * </p>
 * <h6>Usage</h6>
 * <ul>
 * <li>{@link com.wit.android.fragment.BaseFragment BaseFragment}</li>
 * </ul>
 *
 * @author Martin Albedinsky
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ContentView {

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * Methods =====================================================================================
	 */

	/**
	 * The resource id of the desired layout to inflate as content view.
	 */
	int value();

	/**
	 * Flag indicating whether to attach inflated content view to its parent or not.
	 */
	boolean attachToRoot() default false;

	/**
	 * The resource id for the background of the inflated content view. May be a resource to either
	 * color or drawable.
	 */
	int backgroundRes() default -1;
}
