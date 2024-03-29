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

import android.app.Fragment;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <h4>Annotation Overview</h4>
 * Annotation type used to mark an <b>int</b> field which specifies an id of fragment provided by
 * instance of {@link com.wit.android.fragment.manage.BaseFragmentFactory BaseFragmentFactory}.
 * <h6>Usage</h6>
 * <ul>
 * <li>{@link com.wit.android.fragment.manage.BaseFragmentFactory BaseFragmentFactory}</li>
 * </ul>
 *
 * @author Martin Albedinsky
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FactoryFragment {

	/**
	 * Type of the desired fragment of which instance should be instantiated when calling
	 * {@link com.wit.android.fragment.manage.BaseFragmentFactory#createFragmentInstance(int, android.os.Bundle) BaseFragmentFactory#createFragmentInstance(int, android.os.Bundle)}
	 * for this id .
	 */
	Class<? extends Fragment> type() default Fragment.class;

	/**
	 * Name of the desired fragment to be placed within TAG used when showing such a fragment.
	 */
	String taggedName() default "";
}
