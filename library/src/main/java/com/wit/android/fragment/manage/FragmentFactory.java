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

/**
 * <h4>Class Overview</h4>
 * <p>
 * This is only helper implementation of {@link com.wit.android.fragment.manage.FragmentController.IFragmentFactory}.
 * </p>
 * <p>
 * Provides some methods useful when using custom fragment factory.
 * </p>
 *
 * @author Martin Albedinsky
 */
public abstract class FragmentFactory implements FragmentController.IFragmentFactory {

	/**
	 * Constants =============================
	 */

    /**
     * Log TAG.
     */
    // private static final String TAG = FragmentFactory.class.getSimpleName();

    /**
     * Flag indicating whether the debug output trough log-cat is enabled or not.
     */
    // private static final boolean DEBUG = true;

    /**
     * Flag indicating whether the output for user trough log-cat is enabled or not.
     */
    // private static final boolean USER_LOG = true;

	/**
	 * Enums =================================
	 */

    /**
     * Static members ========================
     */

	/**
	 * Members ===============================
	 */

	/**
	 * Listeners -----------------------------
	 */

	/**
	 * Arrays --------------------------------
	 */

	/**
	 * Booleans ------------------------------
	 */

	/**
	 * Constructors ==========================
	 */

	/**
	 * Methods ===============================
	 */

	/**
	 * Public --------------------------------
	 */

    /**
     * <p>
     * Creates a fragment tag in required format depends on the passed <var>factory's</var> package
     * name and the specified <var>fragmentName</var>.
     * </p>
     * <p>
     * Example format: <u>com.android.app.fragment.factories.ScreenProfileFactory.EditProfile.TAG</u><br/><br/>
     * - where <b>com.android.app.fragment.factories</b> is name of package where <var>factory</var> is placed,
     * <b>ScreenProfileFactory</b> is factory class name, <b>EditProfile</b> is <var>fragmentName</var> and
     * <b>TAG</b> is tag identifier.
     * </p>
     *
     * @param factoryClass
     *          Class of factory for which is creation of fragment tag requested.
     * @param fragmentName
     *          Fragment name (can be fragment class name) for which tag should be created.
     * @return Fragment tag in required format, or <code>null</code> if <var>fragmentName</var> is <code>null</code> or empty.
     */
    public static String createFragmentTag(Class<? extends FragmentController.IFragmentFactory> factoryClass, String fragmentName) {
        // Only valid fragment name is allowed.
        if (fragmentName == null || fragmentName.length() == 0) {
            return null;
        }
        return factoryClass.getPackage().getName() + "." + factoryClass.getSimpleName() + "." + fragmentName + ".TAG";
    }

	/**
	 * Getters + Setters ---------------------
	 */

	/**
	 * Protected -----------------------------
	 */

	/**
	 * Private -------------------------------
	 */

	/**
	 * Abstract methods ----------------------
	 */

	/**
	 * Inner classes =========================
	 */

	/**
	 * Interface =============================
	 */
}
