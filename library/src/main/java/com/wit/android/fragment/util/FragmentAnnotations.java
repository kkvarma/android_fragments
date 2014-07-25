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

import android.app.Activity;
import android.app.Fragment;
import android.util.Log;
import android.view.View;

import com.wit.android.fragment.annotation.InjectView;
import com.wit.android.fragment.annotation.InjectViews;
import com.wit.android.fragment.config.FragmentsConfig;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * <h4>Class Overview</h4>
 * <p>
 * Annotation utils.
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
	private static final String TAG = FragmentAnnotations.class.getSimpleName();

	/**
	 * Flag indicating whether the debug output trough log-cat is enabled or not.
	 */
	private static final boolean DEBUG = FragmentsConfig.LIBRARY_DEBUG_LOG_ENABLED;

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
	 * Obtains the specified type of an annotation from the given <var>fromClass</var> if it is presented.
	 * </p>
	 *
	 * @param fromClass         A class from which should be the requested annotation obtained.
	 * @param classOfAnnotation A class of the requested annotation.
	 * @param maxSuperClass     If <code>not null</code>, this method will be called (recursively)
	 *                          for all super classes of the given annotated class (max to the specified
	 *                          <var>maxSuperClass</var>) until the requested annotation is presented
	 *                          and obtained, otherwise annotation will be obtained only from the given
	 *                          annotated class.
	 * @param <A>               The type of the requested annotation.
	 * @return Obtained annotation or <code>null</code> if the requested annotation is not presented
	 * for the given class or its supers if requested.
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
	 * Same as {@link #iterateFields(Class, FieldProcessor, Class)} with no <var>maxSuperClass</var>
	 * specified.
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
	 * @param ofClass       A class of which fields to iterate.
	 * @param processor     A field processor callback to be invoked for each of iterated fields.
	 * @param maxSuperClass If <code>not null</code>, this method will be called (recursively)
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
	 * must be marked with {@link com.wit.android.fragment.annotation.InjectView @InjectView}
	 * or {@link com.wit.android.fragment.annotation.InjectView.Last @InjectView.Last}
	 * annotation and a class of <var>fragment</var> (also its super classes) must be marked with
	 * {@link com.wit.android.fragment.annotation.InjectViews @InjectViews}, otherwise views
	 * of classes without @InjectViews annotation will be not injected. This is due to optimization,
	 * because some of super classes of the given <var>fragment</var> may not have @InjectView annotated
	 * fields, but still all their fields would be without optimization iterated.
	 * </p>
	 * <p>
	 * <b>Note</b>, that views to inject will be obtained from the current root view of the given
	 * fragment ({@link android.app.Fragment#getView()}).
	 * </p>
	 * <p>
	 * <b>If the context of which views you want to inject has like really a lot of fields, consider
	 * to not use this approach, because it can really decrease performance (increase time of displaying
	 * new screen, etc.).</b>
	 * </p>
	 *
	 * @param fragment      An instance of the fragment into which context should be views injected.
	 * @param maxSuperClass If <code>not null</code>, this method will be called (recursively)
	 *                      for all super classes of the given fragment (max to the specified
	 *                      <var>maxSuperClass</var>), otherwise only fields of the given fragment's
	 *                      class will be iterated.
	 * @throws java.lang.IllegalStateException If the given fragment does not have created root view
	 *                                         yet or it is already invalid.
	 * @see #injectActivityViews(android.app.Activity, Class)
	 */
	public static void injectFragmentViews(Fragment fragment, Class<?> maxSuperClass) {
		final View root = fragment.getView();
		if (root != null) {
			injectViews(fragment, ((Object) fragment).getClass(), root, maxSuperClass);
		} else {
			throw new IllegalStateException("Can not to inject views. Fragment(" + fragment + ") does not have root view created yet or it is already invalid.");
		}
	}

	/**
	 * <p>
	 * Same as {@link #injectFragmentViews(android.app.Fragment, Class)}, where the given
	 * <var>activity</var> will be used as context into which will be views injected.
	 * </p>
	 * <p>
	 * <b>Note</b>, that views to inject will be obtained from the current root content view of the
	 * given activity (<code>activity.getWindow().getDecorView().findViewById(android.R.id.content)</code>).
	 * </p>
	 *
	 * @param activity      An instance of the activity into which context should be views injected.
	 * @param maxSuperClass If <code>not null</code>, this method will be called (recursively)
	 *                      for all super classes of the given activity (max to the specified
	 *                      <var>maxSuperClass</var>), otherwise only fields of the given activity's
	 *                      class will be iterated.
	 * @throws java.lang.IllegalStateException If a view with the {@link android.R.id#content} id can
	 *                                         not be found from the given activity.
	 */
	public static void injectActivityViews(Activity activity, Class<?> maxSuperClass) {
		final View content = activity.getWindow().getDecorView().findViewById(android.R.id.content);
		if (content != null) {
			injectViews(activity, ((Object) activity).getClass(), content, maxSuperClass);
		} else {
			throw new IllegalStateException("Can not to inject views. Activity(" + activity + ") does not have root view with id(android.R.id.content).");
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
	 * Injects all annotated field views. Note, that this can run recursively, so it will check all
	 * fields for {@link com.wit.android.fragment.annotation.InjectView @InjectView} or
	 * {@link com.wit.android.fragment.annotation.InjectView.Last @InjectView.Last}
	 * annotation presented above each of fields of the given <var>classOfRootContext</var>.
	 *
	 * @param rootContext        A context in which is the passed <var>root</var> view presented and
	 *                           into which should be views injected.
	 * @param classOfRootContext A class of a context for the current recursive iteration.
	 * @param root               A root view of the given context.
	 * @param maxSuperClass      If <code>not null</code>, this method will be called (recursively)
	 *                           for all super classes of the given class (max to the specified
	 *                           <var>maxSuperClass</var>), otherwise only fields of the given class
	 *                           will be iterated.
	 */
	private static void injectViews(Object rootContext, Class<?> classOfRootContext, View root, Class<?> maxSuperClass) {
		// Class of fragment must have @InjectViews annotation present to really iterate and inject
		// annotated views.
		if (classOfRootContext.isAnnotationPresent(InjectViews.class)) {
			// Process annotated fields.
			final Field[] fields = classOfRootContext.getDeclaredFields();
			if (fields.length > 0) {
				for (Field field : fields) {
					if (field.isAnnotationPresent(InjectView.class)) {
						injectView(field, rootContext, root, field.getAnnotation(InjectView.class).value());
					} else if (field.isAnnotationPresent(InjectView.Last.class)) {
						injectView(field, rootContext, root, field.getAnnotation(InjectView.Last.class).value());
						if (DEBUG) {
							Log.d(TAG, "Finishing injecting views of(" + rootContext + ") on field(" + field + ").");
						}
						break;
					}
				}
			}
		}

		// Inject also views of supper class, but only to this BaseFragment super.
		final Class<?> superOfRootContext = classOfRootContext.getSuperclass();
		if (superOfRootContext != null && !superOfRootContext.equals(maxSuperClass)) {
			injectViews(rootContext, superOfRootContext, root, maxSuperClass);
		}
	}

	/**
	 * Sets a view obtained by the given <var>id</var> from the given <var>root</var> view as value
	 * the the given <var>field</var>.
	 *
	 * @param field       A field to which should be obtained view set as value.
	 * @param rootContext A context in which is the passed view <var>field</var> presented.
	 * @param root        A root view of the given context.
	 * @param id          An id of the view to look up by in the root view.
	 */
	private static void injectView(Field field, Object rootContext, View root, int id) {
		// Check correct type of the field.
		final Class<?> classOfField = field.getType();
		if (View.class.isAssignableFrom(classOfField)) {
			field.setAccessible(true);
			try {
				field.set(rootContext, root.findViewById(id));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
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
	 * Simple callback which allows processing of all declared fields of a desired class using one of
	 * {@link #iterateFields(Class, FragmentAnnotations.FieldProcessor)},
	 * {@link #iterateFields(Class, FragmentAnnotations.FieldProcessor, Class)} methods.
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
		 * @param name  A name of the currently iterated field.
		 */
		public void onProcessField(Field field, String name);
	}
}
