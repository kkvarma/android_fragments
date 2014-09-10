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
 * Defines an annotation for determining a content to be loaded into {@link android.webkit.WebView}.
 * </p>
 * <h6>Usage</h6>
 * <ul>
 * <li>{@link com.wit.android.fragment.WebFragment WebFragment}</li>
 * </ul>
 *
 * @author Martin Albedinsky
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface WebContent {

	/**
	 * The desired content to be loaded into {@link android.webkit.WebView}. Can be a raw <b>HTML</b>,
	 * web <b>URL</b> or path to a <b>FILE</b> with the desired HTML content.
	 * <p/>
	 * Default value: <b>""</b>
	 */
	String value() default "";

	/**
	 * Like {@link #value()}, but this specifies a resource id of the desired web content.
	 * <p/>
	 * Default value: <b>-1</b>
	 */
	int valueRes() default -1;
}
