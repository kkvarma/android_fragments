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
 * Annotation type used to mark a field (instance of {@link android.view.View}) which should be injected
 * into the context associated with the root view.
 * </p>
 * <h6>Usage</h6>
 * <ul>
 * <li>{@link com.wit.android.support.fragment.BaseFragment BaseFragment}</li>
 * </ul>
 *
 * @author Martin Albedinsky
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectView {

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * Methods =====================================================================================
	 */

	/**
	 * An id of the desired view presented within the root view hierarchy to inject.
	 */
	int value();

	/**
	 * Flag indicating whether the desired view is clickable or not. If <code>true</code>, the injected
	 * view will be processed same way as if its id was presented within {@link com.wit.android.support.fragment.annotation.ClickableViews @ClickableViews}
	 * annotation.
	 * <p/>
	 * Default value: <b>false</b>
	 */
	boolean clickable() default false;

	/**
	 * Inner annotations ===========================================================================
	 */

	/**
	 * <h4>Annotation Overview</h4>
	 * <p>
	 * Same as {@link InjectView}. This special annotation type is used to improve views injecting,
	 * so when this annotation is found during injecting process, fields iteration ends, so no more
	 * fields will be iterated and no more views will be injected.
	 * </p>
	 * <p>
	 * <b>Note</b>, that all fields obtained from a specific class are always in the <b>alphabetical</b>
	 * order, so when you decide to used this annotation, make sure, you place it above the
	 * <b>alphabetically</b> last field which should be injected as View.
	 * </p>
	 * <h6>Annotations example</h6>
	 * <pre>
	 *  // ...
	 *
	 *  // Both of views below will be properly injected.
	 *
	 *  &#64;InjectView(R.id.contentTextView)
	 *  private TextView mContentTextView;
	 *
	 *  &#64;InjectView.Last(R.id.titleTextView)
	 *  private TextView mTitleTextView;
	 *
	 *  // =========================================================================================
	 *
	 *  // Only mContentTextView view will be injected from views below.
	 *
	 *  &#64;InjectView(R.id.titleTextView)
	 *  private TextView mTitleTextView;
	 *
	 *  &#64;InjectView.Last(R.id.contentTextView)
	 *  private TextView mContentTextView;
	 *
	 *  // ...
	 * </pre>
	 */
	@Target({ElementType.FIELD, ElementType.TYPE})
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface Last {

		/**
		 * An id of the desired view presented within the root view hierarchy to inject.
		 */
		int value();

		/**
		 * Flag indicating whether the desired view is clickable or not. If <code>true</code>, the injected
		 * view will be processed same way as if its id was presented within {@link com.wit.android.support.fragment.annotation.ClickableViews @ClickableViews}
		 * annotation.
		 * <p/>
		 * Default value: <b>false</b>
		 */
		boolean clickable() default false;
	}
}
