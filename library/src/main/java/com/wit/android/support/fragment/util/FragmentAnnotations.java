/*
 * =================================================================================
 * Copyright (C) 2014 Martin Albedinsky [Wolf-ITechnologies]
 * =================================================================================
 * Licensed under the Apache License, Version 2.0 or later (further "License" only);
 * ---------------------------------------------------------------------------------
 * You may use this file only in compliance with the License. More details and copy
 * of this License you may obtain at
 * 
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * 
 * You can redistribute, modify or publish any part of the code written in this
 * file but as it is described in the License, the software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES or CONDITIONS OF
 * ANY KIND.
 * 
 * See the License for the specific language governing permissions and limitations
 * under the License.
 * =================================================================================
 */
package com.wit.android.support.fragment.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * <h4>Class Overview</h4>
 * <p>
 * Description.
 * </p>
 *
 * @author Martin Albedinsky
 */
public final class FragmentAnnotations {

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * Log TAG.
	 */
	// private static final String TAG = DatabaseAnnotations.class.getSimpleName();

	/**
	 * Flag indicating whether the debug output trough log-cat is enabled or not.
	 */
	// private static final boolean DEBUG = true;

	/**
	 * Flag indicating whether the output for user trough log-cat is enabled or not.
	 */
	// private static final boolean USER_LOG = true;

	/**
	 * Enums =======================================================================================
	 */

	/**
	 * Static members ==============================================================================
	 */

	/**
	 * Members =====================================================================================
	 */

	/**
	 * Listeners -----------------------------------------------------------------------------------
	 */

	/**
	 * Arrays --------------------------------------------------------------------------------------
	 */

	/**
	 * Booleans ------------------------------------------------------------------------------------
	 */

	/**
	 * Constructors ================================================================================
	 */

	/**
	 * Methods =====================================================================================
	 */

	/**
	 * Public --------------------------------------------------------------------------------------
	 */

	/**
	 * <p>
	 * </p>
	 *
	 * @param annotatedClass
	 * @param classOfAnnotation
	 * @param <A>
	 * @return
	 */
	public static <A extends Annotation> A retrieveAnnotationFrom(Class<?> annotatedClass, Class<A> classOfAnnotation) {
		return retrieveAnnotationFrom(annotatedClass, classOfAnnotation, true);
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param annotatedClass
	 * @param classOfAnnotation
	 * @param checkSuper
	 * @param <A>
	 * @return
	 * @see #retrieveAnnotationFrom(Class, Class)
	 */
	public static <A extends Annotation> A retrieveAnnotationFrom(Class<?> annotatedClass, Class<A> classOfAnnotation, boolean checkSuper) {
		if (!annotatedClass.isAnnotationPresent(classOfAnnotation) && checkSuper) {
			final Class<?> parent = annotatedClass.getSuperclass();
			if (parent != null) {
				return retrieveAnnotationFrom(parent, classOfAnnotation, true);
			}
		}
		return annotatedClass.getAnnotation(classOfAnnotation);
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param fieldsClass
	 * @param processor
	 */
	public static void iterateFields(Class<?> fieldsClass, FieldProcessor processor) {
		iterateFields(fieldsClass, processor, true);
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param fieldsClass
	 * @param processor
	 * @param checkSuper
	 * @see #iterateFields(Class, FragmentAnnotations.FieldProcessor)
	 */
	public static void iterateFields(Class<?> fieldsClass, FieldProcessor processor, boolean checkSuper) {
		final Field[] fields = fieldsClass.getDeclaredFields();
		if (fields.length > 0) {
			for (Field field : fields) {
				processor.onProcessField(field, field.getName());
			}
		}
		// If check also super iterate trough super fields too.
		if (checkSuper) {
			final Class<?> superOfFieldsClass = fieldsClass.getSuperclass();
			if (superOfFieldsClass != null) {
				iterateFields(superOfFieldsClass, processor, true);
			}
		}
	}

	/**
	 * Getters + Setters ---------------------------------------------------------------------------
	 */

	/**
	 * Protected -----------------------------------------------------------------------------------
	 */

	/**
	 * Private -------------------------------------------------------------------------------------
	 */

	/**
	 * Abstract methods ----------------------------------------------------------------------------
	 */

	/**
	 * Inner classes ===============================================================================
	 */

	/**
	 * Interface ===================================================================================
	 */

	/**
	 * <h4>Interface Overview</h4>
	 * <p>
	 * Description.
	 * </p>
	 *
	 * @author Martin Albedinsky
	 */
	public static interface FieldProcessor {

		/**
		 * <p>
		 * </p>
		 *
		 * @param field
		 * @param name
		 */
		public void onProcessField(Field field, String name);
	}
}
