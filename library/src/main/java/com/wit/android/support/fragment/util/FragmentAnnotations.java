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
	// private static final String TAG = FragmentAnnotations.class.getSimpleName();

	/**
	 * Flag indicating whether the debug output trough log-cat is enabled or not.
	 */
	// private static final boolean DEBUG = FragmentsConfig.LIBRARY_DEBUG_LOG_ENABLED;

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
	 * Same as {@link #injectView(java.lang.reflect.Field, Object, android.view.View, android.view.View.OnClickListener)}
	 * with <code>null</code> OnClickListener.
	 * </p>
	 */
	public static boolean injectView(Field field, Object fieldHolder, View root) {
		return injectView(field, fieldHolder, root, null);
	}


	/**
	 * <p>
	 * Injects view obtained from the given <var>root</var> view by id presented within
	 * {@link com.wit.android.support.fragment.annotation.InjectView @InjectView} or
	 * {@link com.wit.android.support.fragment.annotation.InjectView.Last @InjectView.Last} annotation
	 * as value to the given <var>field</var>.
	 * </p>
	 *
	 * @param field           Field to which should be obtained view set as value.
	 * @param fieldParent     Context in which is the passed view <var>field</var> presented.
	 * @param root            Root view from which can be requested view obtained.
	 * @param onClickListener An instance of OnClickListener which should be set to injected view
	 *                        if {@link com.wit.android.support.fragment.annotation.InjectView#clickable() @InjectView.clickable()}
	 *                        flag is set to <code>true</code>.
	 * @return @return <code>True</code> when view obtained from the given root view by id presented within
	 * annotation placed above the given field was successfully set to that field, <code>false</code>
	 * if such a view was not found or the given field does not have InjectView or InjectView.Last
	 * presented.
	 * @throws RuntimeException If the given field is not instance of {@link android.view.View}.
	 */
	public static boolean injectView(Field field, Object fieldParent, View root, View.OnClickListener onClickListener) {
		return injectViewInner(field, fieldParent, root, onClickListener);
	}

	/**
	 * <p>
	 * Same as {@link #injectFragmentViews(android.support.v4.app.Fragment, Class, android.view.View.OnClickListener)}
	 * with <code>null</code> <var>onClickListener</var>.
	 * </p>
	 */
	public static void injectFragmentViews(Fragment fragment, Class<?> maxSuperClass) {
		injectFragmentViews(fragment, maxSuperClass, null);
	}

	/**
	 * <p>
	 * Injects all annotated field views into the given <var>fragment</var> context. Each view to inject
	 * must be marked with {@link com.wit.android.support.fragment.annotation.InjectView @InjectView}
	 * or {@link com.wit.android.support.fragment.annotation.InjectView.Last @InjectView.Last}
	 * annotation and a class of <var>fragment</var> (also its super classes) must be marked with
	 * {@link com.wit.android.support.fragment.annotation.InjectViews @InjectViews}, otherwise views
	 * of classes without @InjectViews annotation will be not injected. This is due to optimization,
	 * because some of super classes of the given <var>fragment</var> may not have @InjectView annotated
	 * fields, but still all their fields would be without optimization iterated.
	 * </p>
	 * <p>
	 * <b>Note</b>, that views to inject will be obtained from the current root view of the given
	 * fragment ({@link android.support.v4.app.Fragment#getView()}).
	 * </p>
	 * <p>
	 * <b>If the context of which views you want to inject has like really a lot of fields, consider
	 * to not use this approach, because it can really decrease performance (increase time of displaying
	 * new screen, etc.).</b>
	 * </p>
	 *
	 * @param fragment        An instance of the fragment into which context should be views injected.
	 * @param maxSuperClass   If <code>not null</code>, this method will be called (recursively)
	 *                        for all super classes of the given fragment (max to the specified
	 *                        <var>maxSuperClass</var>), otherwise only fields of the given fragment's
	 *                        class will be iterated.
	 * @param onClickListener An instance of OnClickListener which should be set to injected views
	 *                        if {@link com.wit.android.support.fragment.annotation.InjectView#clickable() @InjectView.clickable()}
	 *                        flag is set to <code>true</code>.
	 * @throws java.lang.IllegalStateException If the given fragment does not have created root view
	 *                                         yet or it is already invalid.
	 * @throws RuntimeException                If one of the marked fields of the fragment (or its super)
	 *                                         to inject is not instance of {@link android.view.View}.
	 * @see #injectActivityViews(android.app.Activity, Class)
	 */
	public static void injectFragmentViews(Fragment fragment, Class<?> maxSuperClass, View.OnClickListener onClickListener) {
		final View root = fragment.getView();
		if (root != null) {
			injectViews(fragment, ((Object) fragment).getClass(), root, maxSuperClass, onClickListener);
		} else {
			throw new IllegalStateException("Can not to inject views. Fragment(" + fragment + ") does not have root view created yet or it is already invalid.");
		}
	}

	/**
	 * <p>
	 * Same as {@link #injectActivityViews(android.app.Activity, Class, android.view.View.OnClickListener)}
	 * with <code>null</code> <var>onClickListener</var>.
	 * </p>
	 */
	public static void injectActivityViews(Activity activity, Class<?> maxSuperClass) {
		injectActivityViews(activity, maxSuperClass, null);
	}

	/**
	 * <p>
	 * Same as {@link #injectFragmentViews(android.support.v4.app.Fragment, Class)}, where the given
	 * <var>activity</var> will be used as context into which will be views injected.
	 * </p>
	 * <p>
	 * <b>Note</b>, that views to inject will be obtained from the current root content view of the
	 * given activity (<code>activity.getWindow().getDecorView().findViewById(android.R.id.content)</code>).
	 * </p>
	 *
	 * @param activity        An instance of the activity into which context should be views injected.
	 * @param maxSuperClass   If <code>not null</code>, this method will be called (recursively)
	 *                        for all super classes of the given activity (max to the specified
	 *                        <var>maxSuperClass</var>), otherwise only fields of the given activity's
	 *                        class will be iterated.
	 * @param onClickListener An instance of OnClickListener which should be set to injected views
	 *                        if {@link com.wit.android.support.fragment.annotation.InjectView#clickable() @InjectView.clickable()}
	 *                        flag is set to <code>true</code>.
	 * @throws java.lang.IllegalStateException If view with the {@link android.R.id#content} id can
	 *                                         not be found within the given activity's view hierarchy.
	 * @throws RuntimeException                If one of the marked fields of the given activity (or its
	 *                                         super) to inject is not instance of {@link android.view.View}.
	 */
	public static void injectActivityViews(Activity activity, Class<?> maxSuperClass, View.OnClickListener onClickListener) {
		final View content = activity.getWindow().getDecorView().findViewById(android.R.id.content);
		if (content != null) {
			injectViews(activity, ((Object) activity).getClass(), content, maxSuperClass, onClickListener);
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
	 * fields for {@link com.wit.android.support.fragment.annotation.InjectView @InjectView} or
	 * {@link com.wit.android.support.fragment.annotation.InjectView.Last @InjectView.Last}
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
	 * @param onClickListener    An instance of OnClickListener which should be set to injected views
	 *                           if {@link com.wit.android.support.fragment.annotation.InjectView#clickable() @InjectView.clickable()}
	 *                           flag is set to <code>true</code>.
	 */
	private static void injectViews(Object rootContext, Class<?> classOfRootContext, View root, Class<?> maxSuperClass, View.OnClickListener onClickListener) {
		// Class of fragment must have @InjectViews annotation present to really iterate and inject
		// annotated views.
		if (classOfRootContext.isAnnotationPresent(InjectViews.class)) {
			// Process annotated fields.
			final Field[] fields = classOfRootContext.getDeclaredFields();
			if (fields.length > 0) {
				for (Field field : fields) {
					injectViewInner(field, rootContext, root, onClickListener);
					if (field.isAnnotationPresent(InjectView.Last.class)) {
						break;
					}
				}
			}
		}

		// Inject also views of supper class, but only to this BaseFragment super.
		final Class<?> superOfRootContext = classOfRootContext.getSuperclass();
		if (superOfRootContext != null && !superOfRootContext.equals(maxSuperClass)) {
			injectViews(rootContext, superOfRootContext, root, maxSuperClass, onClickListener);
		}
	}

	/**
	 * Sets a view obtained by the given <var>id</var> from the given <var>root</var> view as value
	 * the the given <var>field</var>.
	 *
	 * @param field           A field to which should be obtained view set as value.
	 * @param fieldParent     A context in which is the passed view <var>field</var> presented.
	 * @param root            A root view of the given context.
	 * @param onClickListener An instance of OnClickListener which should be set to injected view
	 *                        if {@link com.wit.android.support.fragment.annotation.InjectView#clickable() @InjectView.clickable()}
	 *                        flag is set to <code>true</code>.
	 * @return <code>True</code> when view obtained from the given root view by id presented within
	 * annotation placed above the given field was successfully set to that field, <code>false</code>
	 * if such a view was not found or the given field does not have InjectView or InjectView.Last
	 * presented.
	 */
	private static boolean injectViewInner(Field field, Object fieldParent, View root, View.OnClickListener onClickListener) {
		View view;
		if (field.isAnnotationPresent(InjectView.class)) {
			final InjectView injectView = field.getAnnotation(InjectView.class);
			if ((view = root.findViewById(injectView.value())) != null && attachView(field, fieldParent, view)) {
				if (injectView.clickable()) {
					view.setOnClickListener(onClickListener);
				}
				return true;
			}
		} else if (field.isAnnotationPresent(InjectView.Last.class)) {
			final InjectView.Last injectLastView = field.getAnnotation(InjectView.Last.class);
			if ((view = root.findViewById(injectLastView.value())) != null && attachView(field, fieldParent, view)) {
				if (injectLastView.clickable()) {
					view.setOnClickListener(onClickListener);
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * Attaches the given <var>view</var> instance to the given <var>field</var> as its value for the
	 * given <var>fieldParent</var> object.
	 *
	 * @param field       A field to which should be the given view set as value.
	 * @param fieldParent A context in which is the passed view <var>field</var> presented.
	 * @param view        View to be set as value of the given field.
	 * @return <code>True</code> when attaching succeeded, <code>false</code> otherwise.
	 * @throws RuntimeException If the given field is not instance of {@link android.view.View}.
	 */
	private static boolean attachView(Field field, Object fieldParent, View view) {
		// Check correct type of the field.
		if (View.class.isAssignableFrom(field.getType())) {
			field.setAccessible(true);
			try {
				field.set(fieldParent, view);
				return true;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		throw new RuntimeException("Field(" + fieldParent.getClass().getSimpleName() + "." + field.getName() + ") is not instance of view, thus can not be injected.");
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
