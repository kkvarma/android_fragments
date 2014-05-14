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
 * <p>
 * Defines an annotation for determining how an {@link android.support.v7.app.ActionBar} should be set.
 * </p>
 * <h6>Usage</h6>
 * <ul>
 * <li>{@link com.wit.android.support.fragment.ActionBarFragment ActionBarFragment}</li>
 * </ul>
 *
 * @author Martin Albedinsky
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ActionBarOptions {

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * Methods =====================================================================================
	 */

	/**
	 * The resource id of the text which should be set as title for an ActionBar.
	 *
	 * @see android.support.v7.app.ActionBar#setTitle(int)
	 */
	int title();

	/**
	 * The resource id of the image which should be set as icon for an ActionBar.
	 *
	 * @see android.support.v7.app.ActionBar#setIcon(int)
	 */
	int icon() default -1;

	/**
	 * Flag indicating whether to enable/disable an ActionBar's home as up icon.
	 *
	 * @see android.support.v7.app.ActionBar#setDisplayHomeAsUpEnabled(boolean)
	 */
	boolean homeAsUp() default false;
}
