/*
 * =================================================================================
 * Copyright (C) 2012 Martin Albedinsky [Wolf-ITechnologies]
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
package com.wit.android.support.fragment.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * <h4>Class Overview</h4>
 * <p>
 * </p>
 *
 * @author Martin Albedinsky
 */
public abstract class BasePagerAdapter extends FragmentPagerAdapter {

	/**
	 * Constants =============================
	 */

	/**
	 * Log TAG.
	 */
	// private static final String TAG = BasePagerAdapter.class.getSimpleName();

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
	 * Context in which will be this adapter used.
	 */
	private Context mContext = null;

	/**
	 * Application resources from the passed context.
	 */
	private Resources mResources = null;

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
	 * <p>
	 * Creates a new instance of BasePagerAdapter with the given fragment manager.
	 * </p>
	 *
	 * @param fragmentManager An instance of FragmentManager for parent
	 *                        {@link android.support.v4.app.FragmentPagerAdapter}.
	 */
	public BasePagerAdapter(FragmentManager fragmentManager) {
		this(fragmentManager, null);
	}

	/**
	 * <p>
	 * Creates a new instance of BasePagerAdapter with the given fragment manager and context.
	 * {@link android.content.res.Resources} and the given context can be accessed immediately after
	 * an instance of this adapter is created.
	 * </p>
	 *
	 * @param fragmentManager An instance of FragmentManager for parent
	 *                        {@link android.support.v4.app.FragmentPagerAdapter}.
	 * @param context         Context in which will be this adapter used.
	 */
	public BasePagerAdapter(FragmentManager fragmentManager, Context context) {
		super(fragmentManager);
		if (context != null) {
			this.mContext = context;
			this.mResources = context.getResources();
		}
	}

	/**
	 */
	@Override
	public CharSequence getPageTitle(int position) {
		return "";
	}

	/**
	 * Methods ===============================
	 */

	/**
	 * Public --------------------------------
	 */

	/**
	 * Getters + Setters ---------------------
	 */

	/**
	 * <p>
	 * Returns the context with which was this adapter created.
	 * </p>
	 *
	 * @return Same context as passed during initialization of this adapter.
	 */
	public Context getContext() {
		return mContext;
	}

	/**
	 * <p>
	 * Returns an application's resources provided by the context passed during initialization of
	 * this adapter.
	 * </p>
	 * <p>
	 * <b>Note</b>, that Resources are provided only in case, when this adapter was created with a
	 * valid context.
	 * </p>
	 *
	 * @return An application's resources.
	 */
	public Resources getResources() {
		return mResources;
	}

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
