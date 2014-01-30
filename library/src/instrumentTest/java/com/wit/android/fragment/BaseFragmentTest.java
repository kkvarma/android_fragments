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
package com.wit.android.fragment;

import android.test.suitebuilder.annotation.SmallTest;

/**
 * <p>
 * Tests logic of {@link } in the {@link android.test.AndroidTestCase} environment.
 * </p>
 *
 * @author Martin Albedinsky
 */
public class BaseFragmentTest extends android.test.AndroidTestCase {

	/**
	 * Test Constants =============================
	 */

	/**
	 * Debug/Test TAG.
	 */
	private static final String TAG = BaseFragmentTest.class.getSimpleName();

	/**
	 * Test Members ===============================
	 */

	/**
	 * Methods ====================================
	 */

	/**
	 * Set-up methods -----------------------------
	 */

	/**
	 * Set ups before each test.
	 *
	 * @throws Exception
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();

		// FIXME: to obtain context
		// getContext();
	}

	/**
	 * Test methods -------------------------------
	 */

	@SmallTest
	public void testDispatchBackPress() throws Exception {

	}

	@SmallTest
	public void testIsRestored() throws Exception {

	}

	@SmallTest
	public void testIsViewRestored() throws Exception {

	}
}
