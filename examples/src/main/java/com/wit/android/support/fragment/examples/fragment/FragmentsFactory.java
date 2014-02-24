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

import com.wit.android.support.fragment.annotation.FactoryFragments;
import com.wit.android.support.fragment.manage.BaseFragmentFactory;
import com.wit.android.support.fragment.manage.FragmentController;
import com.wit.android.support.fragment.manage.ShowDirection;

/**
 * <p>
 * Description.
 * </p>
 *
 * @author Martin Albedinsky
 */
@FactoryFragments({
		FragmentsFactory.FRAGMENT_DIRECTION_NONE,
		FragmentsFactory.FRAGMENT_DIRECTION_FROM_RIGHT_TO_BACKGROUND,
		FragmentsFactory.FRAGMENT_DIRECTION_NONE,
		FragmentsFactory.FRAGMENT_DIRECTION_FROM_RIGHT_TO_LEFT,
		FragmentsFactory.FRAGMENT_DIRECTION_FROM_LEFT_TO_RIGHT,
		FragmentsFactory.FRAGMENT_DIRECTION_FROM_TOP_TO_BOTTOM,
		FragmentsFactory.FRAGMENT_DIRECTION_FROM_BOTTOM_TO_TOP,
		FragmentsFactory.FRAGMENT_DIRECTION_FROM_BACKGROUND_TO_LEFT,
		FragmentsFactory.FRAGMENT_DIRECTION_FROM_BACKGROUND_TO_RIGHT,
		FragmentsFactory.FRAGMENT_DIRECTION_FROM_BACKGROUND_TO_TOP,
		FragmentsFactory.FRAGMENT_DIRECTION_FROM_BACKGROUND_TO_BOTTOM,
		FragmentsFactory.FRAGMENT_DIRECTION_FROM_LEFT_TO_BACKGROUND,
		FragmentsFactory.FRAGMENT_DIRECTION_FROM_RIGHT_TO_BACKGROUND,
		FragmentsFactory.FRAGMENT_DIRECTION_FROM_TOP_TO_BACKGROUND,
		FragmentsFactory.FRAGMENT_DIRECTION_FROM_BOTTOM_TO_BACKGROUND
})
public class FragmentsFactory extends BaseFragmentFactory {

	/**
	 * Log TAG.
	 */
	private static final String TAG = FragmentsFactory.class.getSimpleName();

	public static final int FRAGMENT_DIRECTION_NONE = 0x00;
	public static final int FRAGMENT_DIRECTION_FROM_RIGHT_TO_LEFT = 0x01;
	public static final int FRAGMENT_DIRECTION_FROM_LEFT_TO_RIGHT = 0x02;
	public static final int FRAGMENT_DIRECTION_FROM_TOP_TO_BOTTOM = 0x03;
	public static final int FRAGMENT_DIRECTION_FROM_BOTTOM_TO_TOP = 0x04;
	public static final int FRAGMENT_DIRECTION_FROM_BACKGROUND_TO_LEFT = 0x05;
	public static final int FRAGMENT_DIRECTION_FROM_BACKGROUND_TO_RIGHT = 0x06;
	public static final int FRAGMENT_DIRECTION_FROM_BACKGROUND_TO_TOP = 0x07;
	public static final int FRAGMENT_DIRECTION_FROM_BACKGROUND_TO_BOTTOM = 0x08;
	public static final int FRAGMENT_DIRECTION_FROM_LEFT_TO_BACKGROUND = 0x09;
	public static final int FRAGMENT_DIRECTION_FROM_RIGHT_TO_BACKGROUND = 0x0a;
	public static final int FRAGMENT_DIRECTION_FROM_TOP_TO_BACKGROUND = 0x0b;
	public static final int FRAGMENT_DIRECTION_FROM_BOTTOM_TO_BACKGROUND = 0x0c;

	public static final String PARAM_ACTION_BAR_TITLE = "com.wit.android.support.fragment.examples.fragment.FragmentsFactory.Param.ActionBarTitle";
	public static final String PARAM_ADD_TO_BACK_STACK = "com.wit.android.support.fragment.examples.fragment.FragmentsFactory.Params.AddToBackStack";

	@Override
	public Fragment createFragmentInstance(int fragmentID, Bundle params) {
		return ImageFragment.newInstance(params.getString(PARAM_ACTION_BAR_TITLE));
	}

	/**
	 */
	@Override
	public FragmentController.ShowOptions getFragmentShowOptions(int fragmentID, Bundle params) {
		final FragmentController.ShowOptions options = super.getFragmentShowOptions(fragmentID, params);
		switch (fragmentID) {
			case FRAGMENT_DIRECTION_FROM_RIGHT_TO_LEFT:
				options.showDirection(ShowDirection.FROM_RIGHT_TO_LEFT);
				break;
			case FRAGMENT_DIRECTION_FROM_LEFT_TO_RIGHT:
				options.showDirection(ShowDirection.FROM_LEFT_TO_RIGHT);
				break;
			case FRAGMENT_DIRECTION_FROM_TOP_TO_BOTTOM:
				options.showDirection(ShowDirection.FROM_TOP_TO_BOTTOM);
				break;
			case FRAGMENT_DIRECTION_FROM_BOTTOM_TO_TOP:
				options.showDirection(ShowDirection.FROM_BOTTOM_TO_TOP);
				break;
			case FRAGMENT_DIRECTION_FROM_BACKGROUND_TO_LEFT:
				options.showDirection(ShowDirection.FROM_BACKGROUND_TO_LEFT);
				break;
			case FRAGMENT_DIRECTION_FROM_BACKGROUND_TO_RIGHT:
				options.showDirection(ShowDirection.FROM_BACKGROUND_TO_RIGHT);
				break;
			case FRAGMENT_DIRECTION_FROM_BACKGROUND_TO_TOP:
				options.showDirection(ShowDirection.FROM_BACKGROUND_TO_TOP);
				break;
			case FRAGMENT_DIRECTION_FROM_BACKGROUND_TO_BOTTOM:
				options.showDirection(ShowDirection.FROM_BACKGROUND_TO_BOTTOM);
				break;
			case FRAGMENT_DIRECTION_FROM_LEFT_TO_BACKGROUND:
				options.showDirection(ShowDirection.FROM_LEFT_TO_BACKGROUND);
				break;
			case FRAGMENT_DIRECTION_FROM_RIGHT_TO_BACKGROUND:
				options.showDirection(ShowDirection.FROM_RIGHT_TO_BACKGROUND);
				break;
			case FRAGMENT_DIRECTION_FROM_TOP_TO_BACKGROUND:
				options.showDirection(ShowDirection.FROM_TOP_TO_BACKGROUND);
				break;
			case FRAGMENT_DIRECTION_FROM_BOTTOM_TO_BACKGROUND:
				options.showDirection(ShowDirection.FROM_BOTTOM_TO_BACKGROUND);
				break;
		}
		return options.addToBackStack(params.getBoolean(PARAM_ADD_TO_BACK_STACK, false));
	}
}
