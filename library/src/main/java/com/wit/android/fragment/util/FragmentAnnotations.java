/*
 * =================================================================================================
 *                   Copyright (C) 2014 Martin Albedinsky [Wolf-ITechnologies]
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
package com.wit.android.fragment.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * <h4>Class Overview</h4>
 * <p>
 * Annotations utility.
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
	// private static final String TAG = FragmentAnnotations.class.getSimpleName();

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
	 * Same as {@link #obtainAnnotationFrom(Class, Class, boolean, Class)} with <var>checkSuper</var>
	 * set to <code>false</code>.
	 * </p>
	 */
	public static <A extends Annotation> A obtainAnnotationFrom(Class<?> fromClass, Class<A> classOfAnnotation) {
		return obtainAnnotationFrom(fromClass, classOfAnnotation, false, null);
	}

	/**
	 * <p>
	 * Obtains the specified type of an annotation from the given <var>fromClass</var> if it is
	 * presented.
	 * </p>
	 *
	 * @param fromClass         The class from which should be the requested annotation obtained.
	 * @param classOfAnnotation The class of the requested annotation.
	 * @param checkSuper        If set to <code>true</code>, this method will be called recursively
	 *                          for all super classes of the given annotated class until the requested
	 *                          annotation is presented and obtained, otherwise annotation will be
	 *                          obtained only from the given annotated class.
	 * @param maxSuperClass     The class to which should be recursion running if <var>checkSuper</var>
	 *                          is set to <code>true</code>.
	 * @param <A>               The type of the requested annotation.
	 * @return Obtained annotation or <code>null</code> if the requested annotation is not presented
	 * for the given class.
	 * @see #obtainAnnotationFrom(Class, Class)
	 */
	public static <A extends Annotation> A obtainAnnotationFrom(Class<?> fromClass, Class<A> classOfAnnotation, boolean checkSuper, Class<?> maxSuperClass) {
		if (!fromClass.isAnnotationPresent(classOfAnnotation) && checkSuper) {
			final Class<?> parent = fromClass.getSuperclass();
			if (parent != null && !parent.equals(maxSuperClass)) {
				return obtainAnnotationFrom(parent, classOfAnnotation, true, maxSuperClass);
			}
		}
		return fromClass.getAnnotation(classOfAnnotation);
	}

	/**
	 * <p>
	 * Same as {@link #iterateFields(Class, com.wit.android.fragment.util.FragmentAnnotations.FieldProcessor, boolean, Class)}
	 * with <var>checkSuper</var> set to <code>false</code>.
	 * </p>
	 */
	public static void iterateFields(Class<?> fieldsClass, FieldProcessor processor) {
		iterateFields(fieldsClass, processor, false, null);
	}

	/**
	 * <p>
	 * Iterates all declared fields of the given <var>ofClass</var>.
	 * </p>
	 *
	 * @param ofClass       The class of which fields to iterate.
	 * @param processor     The field processor callback to be invoked for each of iterated fields.
	 * @param checkSuper    If set to <code>true</code>, this method will be called recursively for all
	 *                      super classes of the given annotated class.
	 * @param maxSuperClass The class to which should be recursion running if <var>checkSuper</var>
	 *                      is set to <code>true</code>.
	 * @see #iterateFields(Class, FragmentAnnotations.FieldProcessor)
	 */
	public static void iterateFields(Class<?> ofClass, FieldProcessor processor, boolean checkSuper, Class<?> maxSuperClass) {
		final Field[] fields = ofClass.getDeclaredFields();
		if (fields.length > 0) {
			for (Field field : fields) {
				processor.onProcessField(field, field.getName());
			}
		}
		// If check also super iterate trough super fields too.
		if (checkSuper) {
			final Class<?> parent = ofClass.getSuperclass();
			if (parent != null && !parent.equals(maxSuperClass)) {
				iterateFields(parent, processor, true, maxSuperClass);
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
	 * Simple callback which allows processing of all declared members of a desired class using one of
	 * {@link #iterateFields(Class, com.wit.android.fragment.util.FragmentAnnotations.FieldProcessor)},
	 * {@link #iterateFields(Class, com.wit.android.fragment.util.FragmentAnnotations.FieldProcessor, boolean, Class)}
	 * methods.
	 * </p>
	 *
	 * @author Martin Albedinsky
	 */
	public static interface FieldProcessor {

		/**
		 * <p>
		 * Invoked for each of iterated fields.
		 * </p>
		 *
		 * @param field The currently iterated field.
		 * @param name The name of the currently iterated field.
		 */
		public void onProcessField(Field field, String name);
	}
}
