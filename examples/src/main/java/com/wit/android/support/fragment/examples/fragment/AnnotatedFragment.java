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
package com.wit.android.support.fragment.examples.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wit.android.support.fragment.BaseFragment;
import com.wit.android.support.fragment.annotation.ContentView;
import com.wit.android.support.fragment.annotation.InjectView;
import com.wit.android.support.fragment.annotation.InjectViews;
import com.wit.android.support.fragment.examples.R;

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
	// private static final String TAG = ExampleListFragment.class.getSimpleName();

	@InjectView(R.id.Fragment_Annotations_TextView_Content)
	private TextView mContentTextView;

	@InjectView.Last(R.id.Fragment_Annotations_TextView_Title)
	private TextView mTitleTextView;

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
		mTitleTextView.setText("Title section");
		mContentTextView.setText(
				"Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut " +
						"labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco " +
						"laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in" +
						" voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat " +
						"non proident, sunt in culpa qui officia deserunt mollit anim id est laborum"
		);
	}
}
