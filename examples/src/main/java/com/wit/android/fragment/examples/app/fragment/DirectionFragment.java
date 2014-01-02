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
package com.wit.android.fragment.examples.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wit.android.examples.app.fragment.ExActionBarFragment;
import com.wit.android.fragment.examples.R;

import java.util.Random;

/**
 * <p>
 * Description.
 * </p>
 *
 * @author Martin Albedinsky
 */
public class DirectionFragment extends ExActionBarFragment {

	/**
	 * Log TAG.
	 */
	private static final String TAG = DirectionFragment.class.getSimpleName();

	public static DirectionFragment newInstance(String title) {
		final DirectionFragment fragment = new DirectionFragment();
		final Bundle args = new Bundle();
		args.putString(ARGS_ACTION_BAR_TITLE, title);
		fragment.setArguments(args);
		return fragment;
	}

	/**
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(this.randomLayoutRes(), null);
	}

	/**
	 */
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}

	private int randomLayoutRes() {
		switch (new Random().nextInt(3)) {
			case 1:
				return R.layout.fragment_direction_2;
			case 2:
				return R.layout.fragment_direction_3;
		}
		return R.layout.fragment_direction_1;
	}

}
