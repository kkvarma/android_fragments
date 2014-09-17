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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <h4>Annotation Overview</h4>
 * Defines an annotation for determining which layout should be inflated as the root view.
 * <h6>Usage</h6>
 * <ul>
 * <li>{@link com.wit.android.support.fragment.BaseFragment BaseFragment}</li>
 * </ul>
 *
 * @author Martin Albedinsky
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ContentView {

	/**
	 * A resource id of the desired layout to inflate as root view.
	 */
	int value();

	/**
	 * Flag indicating whether to attach inflated content view to its parent or not.
	 * <p/>
	 * Default value: <b>false</b>
	 */
	boolean attachToRoot() default false;

	/**
	 * A resource id for the background to be set to the inflated root view. May be a resource id to
	 * either color or drawable.
	 * <p/>
	 * Default value: <b>-1</b>
	 */
	int backgroundRes() default -1;
}
