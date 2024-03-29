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

import android.support.annotation.IntDef;
import android.support.annotation.MenuRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <h4>Annotation Overview</h4>
 * Defines an annotation for determining how an {@link android.view.Menu} should be set.
 * <h6>Usage</h6>
 * <ul>
 * <li>{@link com.wit.android.fragment.ActionBarFragment ActionBarFragment}</li>
 * </ul>
 *
 * @author Martin Albedinsky
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MenuOptions {

	/**
	 * Interface ===================================================================================
	 */

	/**
	 * <h4>Annotation Overview</h4>
	 * Defines an annotation for determining set of allowed flags for {@link #flags()} attribute.
	 */
	@Retention(RetentionPolicy.SOURCE)
	@IntDef({DEFAULT, IGNORE_SUPER, BEFORE_SUPER})
	public @interface Flags {}

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * The flag indicating the default menu set up.
	 */
	public static final int DEFAULT = 0x00;

	/**
	 * The flag indicating that the creation of the super's menu should be ignored.
	 */
	public static final int IGNORE_SUPER = 0x01;

	/**
	 * The flag indicating that a menu should be created before the super ones.
	 */
	public static final int BEFORE_SUPER = 0x04;

	/**
	 * Methods =====================================================================================
	 */

	/**
	 * A resource id of the desired xml menu.
	 * <p/>
	 * Default value: <b>0</b>
	 */
	@MenuRes
	int value() default 0;

	/**
	 * Flag indicating whether to clear the already created menu or not.
	 */
	boolean clear() default false;

	/**
	 * Flags for determining a menu set up.
	 */
	@Flags
	int flags() default DEFAULT;
}
