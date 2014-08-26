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
 * Defines an annotation for determining how an {@link android.app.ActionBar} should be set.
 * </p>
 * <h6>Usage</h6>
 * <ul>
 * <li>{@link com.wit.android.fragment.ActionBarFragment ActionBarFragment}</li>
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
	 * <p>
	 * Flag indicating that an ActionBar's home as up icon should be enabled (visible).
	 * </p>
	 */
	public static final int HOME_AS_UP_ENABLED = 0x01;

	/**
	 * <p>
	 * Flag indicating that an ActionBar's home as up icon should be disabled (invisible).
	 * </p>
	 */
	public static final int HOME_AS_UP_DISABLED = 0x02;

	/**
	 * Methods =====================================================================================
	 */

	/**
	 * A resource id of the desired text which should be set as title for an ActionBar.
	 *
	 * @see android.app.ActionBar#setTitle(int)
	 */
	int title();

	/**
	 * A resource id of the desired image which should be set as icon for an ActionBar.
	 * <p/>
	 * Default value: <b>-1</b>
	 *
	 * @see android.app.ActionBar#setIcon(int)
	 */
	int icon() default -1;

	/**
	 * Flag indicating whether to display/hide an ActionBar's home as up icon. Can be one of
	 * {@link #HOME_AS_UP_ENABLED}, {@link #HOME_AS_UP_DISABLED} or 0 to not "touch" home as up icon.
	 * <p/>
	 * Default value: <b>0</b>
	 *
	 * @see android.app.ActionBar#setDisplayHomeAsUpEnabled(boolean)
	 */
	int homeAsUp() default 0;
}
