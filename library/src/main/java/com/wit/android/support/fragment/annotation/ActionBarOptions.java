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
 * Defines an annotation for determining how an {@link android.support.v7.app.ActionBar} should be set.
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
	 * Flag for {@link #title()} or {@link #icon()} options to identify that the current value should
	 * be hided/removed from either ActionBar title view or ActionBar icon view.
	 */
	public static final int NONE = 0x00;

	/**
	 * Flag indicating that an ActionBar's home as up icon should be enabled (visible).
	 */
	public static final int HOME_AS_UP_ENABLED = 0x01;

	/**
	 * Flag indicating that an ActionBar's home as up icon should be disabled (invisible).
	 */
	public static final int HOME_AS_UP_DISABLED = 0x02;

	/**
	 * Methods =====================================================================================
	 */

	/**
	 * A resource id of the desired text which should be set as title for an ActionBar.
	 * <p/>
	 * Use {@link #NONE} to remove the current title from ActionBar.
	 * <p/>
	 * Default value: <b>-1</b>
	 *
	 * @see android.app.ActionBar#setTitle(int)
	 */
	int title() default -1;

	/**
	 * A resource id of the desired image which should be set as icon for an ActionBar.
	 * <p/>
	 * Use {@link #NONE} to hide/remove the current icon from ActionBar.
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
