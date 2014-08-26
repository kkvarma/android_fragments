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

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wit.android.fragment.ActionBarFragment;
import com.wit.android.fragment.examples.R;

import java.util.Random;

/**
 * todo:
 *
 * @author Martin Albedinsky
 */
public class ImageFragment extends ActionBarFragment {

	/**
	 * Log TAG.
	 */
	// private static final String TAG = ImageFragment.class.getSimpleName();

	/**
	 *
	 */
	private static final String BUNDLE_IMAGE = "com.wit.android.fragment.examples.fragment.ImageFragment.BUNDLE.Image";

	/**
	 *
	 */
	private enum Image {
		NATURE_01(R.drawable.nature_01),
		NATURE_02(R.drawable.nature_02),
		NATURE_03(R.drawable.nature_03),
		NATURE_04(R.drawable.nature_04),
		NATURE_05(R.drawable.nature_05),
		NATURE_06(R.drawable.nature_06),
		NATURE_07(R.drawable.nature_07),
		NATURE_08(R.drawable.nature_08),
		NATURE_09(R.drawable.nature_09),
		NATURE_10(R.drawable.nature_10),
		NATURE_11(R.drawable.nature_11),
		NATURE_12(R.drawable.nature_12);

		private static final Random RAND = new Random();

		private final int RES;

		private Image(int resID) {
			this.RES = resID;
		}

		/**
		 * @return
		 */
		static Image random() {
			return values()[RAND.nextInt(values().length)];
		}
	}

	/**
	 *
	 */
	private Image mImage;

	/**
	 * @return
	 */
	public static ImageFragment newInstance() {
		return new ImageFragment();
	}

	/**
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			this.mImage = Image.values()[savedInstanceState.getInt(BUNDLE_IMAGE, Image.NATURE_01.ordinal())];
		} else {
			this.mImage = Image.random();
		}
	}

	/**
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(BUNDLE_IMAGE, mImage.ordinal());
	}

	/**
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_image, container, false);
	}

	/**
	 */
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		final ImageView imageView = (ImageView) view.findViewById(R.id.fragment_image_image_view);
		imageView.setImageResource(mImage.RES);
	}
}
