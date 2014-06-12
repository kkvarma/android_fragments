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
package com.wit.android.support.fragment.util;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;

import com.wit.android.support.fragment.annotation.InjectView;
import com.wit.android.support.fragment.annotation.InjectViews;

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
	 * Same as {@link #obtainAnnotationFrom(Class, Class, Class)} with no <var>maxSuperClass</var>
	 * specified.
	 * </p>
	 */
	public static <A extends Annotation> A obtainAnnotationFrom(Class<?> fromClass, Class<A> classOfAnnotation) {
		return obtainAnnotationFrom(fromClass, classOfAnnotation, null);
	}

	/**
	 * <p>
	 * Obtains the specified type of an annotation from the given <var>fromClass</var> if it is
	 * presented.
	 * </p>
	 *
	 * @param fromClass         The class from which should be the requested annotation obtained.
	 * @param classOfAnnotation The class of the requested annotation.
	 * @param maxSuperClass     If <code>not null</code>, this method will be also called (recursively)
	 *                          for all super classes of the given annotated class (max to the specified
	 *                          <var>maxSuperClass</var>) until the requested annotation is presented
	 *                          and obtained, otherwise annotation will be obtained only from the given
	 *                          annotated class.
	 * @param <A>               The type of the requested annotation.
	 * @return Obtained annotation or <code>null</code> if the requested annotation is not presented
	 * for the given class.
	 * @see #obtainAnnotationFrom(Class, Class)
	 */
	public static <A extends Annotation> A obtainAnnotationFrom(Class<?> fromClass, Class<A> classOfAnnotation, Class<?> maxSuperClass) {
		if (!fromClass.isAnnotationPresent(classOfAnnotation) && maxSuperClass != null) {
			final Class<?> parent = fromClass.getSuperclass();
			if (parent != null && !parent.equals(maxSuperClass)) {
				return obtainAnnotationFrom(parent, classOfAnnotation, maxSuperClass);
			}
		}
		return fromClass.getAnnotation(classOfAnnotation);
	}

	/**
	 * <p>
	 * Same as {@link #iterateFields(Class, FieldProcessor, Class)}
	 * with no <var>maxSuperClass</var> specified.
	 * </p>
	 */
	public static void iterateFields(Class<?> fieldsClass, FieldProcessor processor) {
		iterateFields(fieldsClass, processor, null);
	}

	/**
	 * <p>
	 * Iterates all declared fields of the given <var>ofClass</var>.
	 * </p>
	 *
	 * @param ofClass       The class of which fields to iterate.
	 * @param processor     The field processor callback to be invoked for each of iterated fields.
	 * @param maxSuperClass If <code>not null</code>, this method will be also called (recursively)
	 *                      for all super classes of the given class (max to the specified
	 *                      <var>maxSuperClass</var>), otherwise only fields of the given class will
	 *                      be iterated.
	 * @see #iterateFields(Class, FieldProcessor)
	 */
	public static void iterateFields(Class<?> ofClass, FieldProcessor processor, Class<?> maxSuperClass) {
		final Field[] fields = ofClass.getDeclaredFields();
		if (fields.length > 0) {
			for (Field field : fields) {
				processor.onProcessField(field, field.getName());
			}
		}
		if (maxSuperClass != null) {
			final Class<?> parent = ofClass.getSuperclass();
			if (parent != null && !parent.equals(maxSuperClass)) {
				iterateFields(parent, processor, maxSuperClass);
			}
		}
	}

	/**
	 * <p>
	 * Injects all annotated field views into the given <var>fragment</var> context. Each view to inject
	 * must be marked with {@link com.wit.android.support.fragment.annotation.InjectView @InjectView}
	 * annotation and the class of <var>fragment</var> (also its super classes) must be marked with
	 * {@link com.wit.android.support.fragment.annotation.InjectViews @InjectViews}, otherwise views
	 * of such a class will be not injected. This is due to optimization, because not all super classes
	 * can have @InjectView annotated fields but still all their fields will be without optimization
	 * iterated.
	 * </p>
	 * <p>
	 * <b>Note</b>, that views to inject will be obtained from the current root view of the given
	 * fragment ({@link android.support.v4.app.Fragment#getView()}).
	 * </p>
	 * <p>
	 * <b>If the context of which views you want to inject has like really a lot of fields, consider to
	 * not use this approach, because it can really decrease performance (increase time of displaying
	 * new screen, etc.).</b>
	 * </p>
	 *
	 * @param fragment      The instance of fragment into which context should be views injected.
	 * @param maxSuperClass If <code>not null</code>, this method will be also called (recursively)
	 *                      for all super classes of the given fragment (max to the specified
	 *                      <var>maxSuperClass</var>), otherwise only fields of the given fragment's
	 *                      class will be iterated.
	 * @throws java.lang.IllegalStateException If the given fragment does not have created root view yet.
	 * @see #injectActivityViews(android.app.Activity, Class)
	 */
	public static void injectFragmentViews(Fragment fragment, Class<?> maxSuperClass) {
		final View root = fragment.getView();
		if (root != null) {
			injectViews(fragment, ((Object) fragment).getClass(), root, maxSuperClass);
		} else {
			throw new IllegalStateException("Can not to inject views. Fragment does not have root view created yet.");
		}
	}

	/**
	 * <p>
	 * Same as {@link #injectFragmentViews(android.support.v4.app.Fragment, Class)}, where the given
	 * <var>activity</var> will be used as context in which will be views injected.
	 * </p>
	 * <p>
	 * <b>Note</b>, that views to inject will be obtained from the current root content view of the given
	 * activity (<code>activity.getWindow().getDecorView().findViewById(android.R.id.content)</code>).
	 * </p>
	 *
	 * @param activity      The instance of activity into which context should be views injected.
	 * @param maxSuperClass If <code>not null</code>, this method will be also called (recursively)
	 *                      for all super classes of the given activity (max to the specified
	 *                      <var>maxSuperClass</var>), otherwise only fields of the given activity's
	 *                      class will be iterated.
	 * @throws java.lang.IllegalStateException If the given activity does not contain a view with the
	 *                                         {@link android.R.id#content} id.
	 */
	public static void injectActivityViews(Activity activity, Class<?> maxSuperClass) {
		final View content = activity.getWindow().getDecorView().findViewById(android.R.id.content);
		if (content != null) {
			injectViews(activity, ((Object) activity).getClass(), content, maxSuperClass);
		} else {
			throw new IllegalStateException("");
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
	 * Injects all annotated field views. Note, that this can run recursively, so it will check
	 * all members for {@link com.wit.android.support.fragment.annotation.InjectView}
	 * annotation presented above each of members of the given <var>classOfViewContext</var>.
	 *
	 * @param rootContext   The context in which is the passed <var>root</var> view presented and into
	 *                      which should be views injected.
	 * @param contextClass  The class of a context for the current recursive iteration.
	 * @param root          The root view of the given context.
	 * @param maxSuperClass If <code>not null</code>, this method will be also called (recursively)
	 *                      for all super classes of the given class (max to the specified
	 *                      <var>maxSuperClass</var>), otherwise only fields of the given class will
	 *                      be iterated.
	 */
	private static void injectViews(Object rootContext, Class<?> contextClass, View root, Class<?> maxSuperClass) {
		// Class of fragment must have @InjectViews annotation present to really iterate and inject
		// annotated views.
		if (contextClass.isAnnotationPresent(InjectViews.class)) {
			// Process annotated fields.
			final Field[] fields = contextClass.getDeclaredFields();
			if (fields.length > 0) {
				for (Field field : fields) {
					if (field.isAnnotationPresent(InjectView.class)) {
						// Check correct type of the field.
						final Class<?> classOfField = field.getType();
						if (View.class.isAssignableFrom(classOfField)) {
							field.setAccessible(true);
							try {
								field.set(
										rootContext,
										root.findViewById(field.getAnnotation(InjectView.class).value())
								);
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}

		// Inject also views of supper class, but only to this BaseFragment super.
		final Class<?> superOfRootContext = contextClass.getSuperclass();
		if (superOfRootContext != null && !superOfRootContext.equals(maxSuperClass)) {
			injectViews(rootContext, superOfRootContext, root, maxSuperClass);
		}
	}

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
	 * {@link #iterateFields(Class, FieldProcessor)},
	 * {@link #iterateFields(Class, FieldProcessor, Class)} methods.
	 * </p>
	 *
	 * @author Martin Albedinsky
	 */
	public static interface FieldProcessor {

		/**
		 * <p>
		 * Invoked for each of the iterated fields.
		 * </p>
		 *
		 * @param field The currently iterated field.
		 * @param name  The name of the currently iterated field.
		 */
		public void onProcessField(Field field, String name);
	}
}
