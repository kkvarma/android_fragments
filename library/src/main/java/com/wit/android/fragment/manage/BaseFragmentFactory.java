/*
 * =================================================================================
 * Copyright (C) 2013 Martin Albedinsky [Wolf-ITechnologies]
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
package com.wit.android.fragment.manage;

import android.os.Bundle;
import android.util.SparseArray;

import com.wit.android.fragment.annotation.FactoryFragments;

import java.util.ArrayList;
import java.util.List;

/**
 * <h4>Class Overview</h4>
 * <p>
 * This is only helper implementation of {@link com.wit.android.fragment.manage.FragmentController.FragmentFactory}.
 * </p>
 * <p>
 * Provides some methods useful when using custom fragment factory.
 * </p>
 *
 * @author Martin Albedinsky
 */
public abstract class BaseFragmentFactory implements FragmentController.FragmentFactory {

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * Log TAG.
	 */
	// private static final String TAG = BaseFragmentFactory.class.getSimpleName();

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
	 *
	 */
	private List<Integer> aFragmentIds = null;

	/**
	 *
	 */
	private SparseArray<String> aFragmentTags = null;

	/**
	 * Booleans ------------------------------------------------------------------------------------
	 */

	/**
	 * Constructors ================================================================================
	 */

	/**
	 * <p>
	 * </p>
	 */
	public BaseFragmentFactory() {
		final Class<?> classOfFactory = ((Object) this).getClass();
		/**
		 * Process class annotations.
		 */
		// Retrieve fragment ids.
		if (classOfFactory.isAnnotationPresent(FactoryFragments.class)) {
			final FactoryFragments fragments = classOfFactory.getAnnotation(FactoryFragments.class);

			final int[] ids = fragments.value();
			this.aFragmentIds = new ArrayList<>(ids.length);
			for (int id : ids) {
				aFragmentIds.add(id);
			}

			// Create also tags if requested.
			if (fragments.createTags()) {
				final SparseArray<String> tags = new SparseArray<>(ids.length);
				for (int id : ids) {
					tags.put(id, getFragmentTag(id));
				}
				this.aFragmentTags = tags;
			}
		}
	}

	/**
	 * Methods =====================================================================================
	 */

	/**
	 * Public --------------------------------------------------------------------------------------
	 */

	/**
	 * <p>
	 * Creates a tag for fragment in the required format for the specified class of factory and
	 * <var>fragmentName</var>.
	 * </p>
	 * <p>
	 * Example format: <u>com.android.app.fragment.factories.ProfileActivityFactory.EditProfile.TAG</u><br/><br/>
	 * - where <b>com.android.app.fragment.factories</b> is name of package where specified <var>classOfFactory</var>
	 * is situated, <b>ProfileActivityFactory</b> is name of factory class, <b>EditProfile</b> is
	 * <var>fragmentName</var> and <b>TAG</b> is tag identifier.
	 * </p>
	 *
	 * @param classOfFactory Class of factory for which should be requested tag created.
	 * @param fragmentName   Fragment name (can be fragment class name) for which should be requested
	 *                       tag created.
	 * @return Fragment tag in required format, or <code>null</code> if the <var>fragmentName</var> is
	 * <code>null</code> or empty.
	 */
	public static String createFragmentTag(Class<? extends FragmentController.FragmentFactory> classOfFactory, String fragmentName) {
		// Only valid fragment name is allowed.
		if (fragmentName == null || fragmentName.length() == 0) {
			return null;
		}
		return classOfFactory.getPackage().getName() + "." + classOfFactory.getSimpleName() + "." + fragmentName + ".TAG";
	}

	/**
	 */
	@Override
	public FragmentController.ShowOptions getFragmentShowOptions(int fragmentId, Bundle params) {
		return isFragmentProvided(fragmentId) ? new FragmentController.ShowOptions().tag(getFragmentTag(fragmentId)) : null;
	}

	/**
	 */
	@Override
	public String getFragmentTag(int fragmentId) {
		if (isFragmentProvided(fragmentId)) {
			return (aFragmentTags != null) ? aFragmentTags.get(fragmentId) : createFragmentTag(getClass(), Integer.toString(fragmentId));
		}
		return null;
	}

	/**
	 */
	@Override
	public boolean isFragmentProvided(int fragmentId) {
		return (aFragmentIds != null) && aFragmentIds.contains(fragmentId);
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
}
