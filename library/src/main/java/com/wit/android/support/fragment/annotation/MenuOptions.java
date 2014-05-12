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
 * Description.
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
public @interface MenuOptions {

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * <p>
	 * </p>
	 */
	public static final int DEFAULT = 0x00;

	/**
	 * <p>
	 * </p>
	 */
	public static final int IGNORE_SUPER = 0x01;

	/**
	 * <p>
	 * </p>
	 */
	public static final int BEFORE_SUPER = 0x04;

	/**
	 * Methods =====================================================================================
	 */

	/**
	 *
	 *
	 * @return
	 */
	int value();

	/**
	 *
	 *
	 * @return
	 */
	boolean clear() default false;

	/**
	 *
	 *
	 * @return
	 */
	int flags() default DEFAULT;
}
