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
package com.wit.android.fragment.examples.fragment;

import android.app.Fragment;
import android.os.Bundle;

import com.wit.android.fragment.annotation.FactoryFragments;
import com.wit.android.fragment.manage.BaseFragmentFactory;
import com.wit.android.fragment.manage.FragmentController;
import com.wit.android.fragment.manage.FragmentTransition;

/**
 * <p>
 * Description.
 * </p>
 *
 * @author Martin Albedinsky
 */
@FactoryFragments({
		FragmentsFactory.TRANSITIONS,
		FragmentsFactory.LIST,
		FragmentsFactory.GRID,
		FragmentsFactory.ACTION_BAR,
		FragmentsFactory.ANNOTATIONS
})
public class FragmentsFactory extends BaseFragmentFactory {

	/**
	 * Log TAG.
	 */
	//private static final String TAG = FragmentsFactory.class.getSimpleName();

	/**
	 *
	 */
	public static final int TRANSITIONS = 0x01;

	/**
	 *
	 */
	public static final int LIST = 0x02;

	/**
	 *
	 */
	public static final int GRID = 0x03;

	/**
	 *
	 */
	public static final int ACTION_BAR = 0x04;

	/**
	 *
	 */
	public static final int ANNOTATIONS = 0x05;

	/**
	 *
	 */
	private static final String PARAMS_TRANSITION = "com.wit.android.fragment.examples.fragment.FragmentsFactory.PARAMS.Transition";

	/**
	 *
	 */
	private static final String PARAMS_ADD_TO_BACK_STACK = "com.wit.android.fragment.examples.fragment.FragmentsFactory.PARAMS.AddToBackStack";

	/**
	 *
	 * @param transition
	 * @param addToBackStack
	 */
	public static Bundle createParams(FragmentTransition transition, boolean addToBackStack) {
		final Bundle params = new Bundle();
		params.putParcelable(PARAMS_TRANSITION, transition);
		params.putBoolean(PARAMS_ADD_TO_BACK_STACK, addToBackStack);
		return params;
	}

	/**
	 */
	@Override
	protected Fragment onCreateFragmentInstance(int fragmentId, Bundle params) {
		switch (fragmentId) {
			case TRANSITIONS:
				return ImageFragment.newInstance();
			case LIST:
				return ListFragmentImpl.newInstance();
			case GRID:
				return GridFragmentImpl.newInstance();
			case ACTION_BAR:
				return ActionBarFragmentImpl.newInstance();
			case ANNOTATIONS:
				return AnnotatedFragment.newInstance();
		}
		return null;
	}

	/**
	 */
	@Override
	protected FragmentController.ShowOptions onGetFragmentShowOptions(int fragmentId, Bundle params) {
		final FragmentController.ShowOptions options = super.onGetFragmentShowOptions(fragmentId, params);
		switch (fragmentId) {
			case TRANSITIONS:
				if (params != null) {
					// Set up requested transition.
					options.transition((FragmentTransition) params.getParcelable(PARAMS_TRANSITION));
					options.addToBackStack(params.getBoolean(PARAMS_ADD_TO_BACK_STACK, false));
				}
				break;
			default:
				options.transition(FragmentTransition.FADE_IN);
				break;
		}
		return options;
	}
}
