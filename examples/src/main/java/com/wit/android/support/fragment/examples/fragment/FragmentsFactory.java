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
package com.wit.android.support.fragment.examples.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.wit.android.support.fragment.annotation.FactoryFragment;
import com.wit.android.support.fragment.annotation.FactoryFragments;
import com.wit.android.support.fragment.manage.BaseFragmentFactory;
import com.wit.android.support.fragment.manage.FragmentController;
import com.wit.android.support.fragment.manage.FragmentTransition;

/**
 * <p>
 * Description.
 * </p>
 *
 * @author Martin Albedinsky
 */
@FactoryFragments({
		FragmentsFactory.TRANSITIONS,
		FragmentsFactory.LIST
})
public class FragmentsFactory extends BaseFragmentFactory {

	/**
	 * Log TAG.
	 */
	//private static final String TAG = FragmentsFactory.class.getSimpleName();

	public static final int TRANSITIONS = 0x01;

	public static final int LIST = 0x02;

	@FactoryFragment(type = SampleGridFragment.class, taggedName = "GridFragment")
	public static final int GRID = 0x03;

	@FactoryFragment(type = SampleActionBarFragment.class, taggedName = "ActionBarFragment")
	public static final int ACTION_BAR = 0x04;

	@FactoryFragment(type = ViewsFragment.class, taggedName = "ViewsFragment")
	public static final int VIEWS = 0x05;

	/**
	 *
	 */
	private static final String PARAMS_TRANSITION = "com.wit.android.support.fragment.examples.fragment.FragmentsFactory.PARAMS.Transition";

	/**
	 *
	 */
	private static final String PARAMS_ADD_TO_BACK_STACK = "com.wit.android.support.fragment.examples.fragment.FragmentsFactory.PARAMS.AddToBackStack";

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
				return SampleListFragment.newInstance();
		}
		// Super will create instances for annotated ids.
		return super.onCreateFragmentInstance(fragmentId, params);
	}

	/**
	 */
	@Override
	protected FragmentController.TransactionOptions onGetFragmentTransactionOptions(int fragmentId, Bundle params) {
		switch (fragmentId) {
			case TRANSITIONS:
				if (params != null) {
					return new FragmentController.TransactionOptions()
							// Set up requested transition.
							.transition((FragmentTransition) params.getParcelable(PARAMS_TRANSITION))
							.addToBackStack(params.getBoolean(PARAMS_ADD_TO_BACK_STACK, false))
							.tag(getFragmentTag(fragmentId));
				}
				break;
		}
		return super.onGetFragmentTransactionOptions(fragmentId, params);
	}
}
