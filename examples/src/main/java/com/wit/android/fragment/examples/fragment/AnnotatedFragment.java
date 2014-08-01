/*
 * =================================================================================================
 *                    Copyright (C) 2014 Martin Albedinsky [Wolf-ITechnologies]
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
package com.wit.android.fragment.examples.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.wit.android.fragment.BaseFragment;
import com.wit.android.fragment.annotation.ContentView;
import com.wit.android.fragment.annotation.InjectView;
import com.wit.android.fragment.annotation.InjectViews;
import com.wit.android.fragment.examples.R;

/**
 * <p>
 * Description.
 * </p>
 *
 * @author Martin Albedinsky
 */
@InjectViews
@ContentView(R.layout.fragment_annotations)
public class AnnotatedFragment extends BaseFragment {

	/**
	 * Log TAG.
	 */
	// private static final String TAG = AnnotatedFragment.class.getSimpleName();

	@InjectView(R.id.Fragment_Annotations_TextView_Content)
	private TextView mTextContent;

	@InjectView(R.id.Fragment_Annotations_TextView_Title)
	private TextView mTextTitle;

	/**
	 * Views below are only for testing purpose ----------------------------------------------------
	 */

	@InjectView(0)
	private View mViewInjectFirst;

	@InjectView(0)
	private View mViewInjectSecond;

	@InjectView.Last(0)
	private View mViewInjectThird;

	@InjectView(0)
	private Button mButton;

	@InjectView(0)
	private AutoCompleteTextView mAutoCompleteTextView;

	/**
	 * ---------------------------------------------------------------------------------------------
	 */

	/**
	 * @return
	 */
	public static AnnotatedFragment newInstance() {
		return new AnnotatedFragment();
	}

	/**
	 */
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mTextTitle.setText("Title section");
		mTextContent.setText(
				"Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut " +
						"labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco " +
						"laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in" +
						" voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat " +
						"non proident, sunt in culpa qui officia deserunt mollit anim id est laborum"
		);
	}
}
