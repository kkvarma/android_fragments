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
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
@ContentView(R.layout.fragment_views)
public class ViewsFragment extends BaseFragment {

	/**
	 * Log TAG.
	 */
	// private static final String TAG = "ViewsFragment";

	@InjectView(value = R.id.fragment_views_button_sign_in, clickable = true)
	private Button mButtonSignIn;

	@InjectView(R.id.fragment_views_text_view_content)
	private TextView mTextContent;

	@InjectView.Last(R.id.fragment_views_text_view_title)
	private TextView mTextTitle;

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

	/**
	 */
	@Override
	protected boolean onViewClick(@NonNull View view, int id) {
		switch (id) {
			case R.id.fragment_views_button_sign_in:
				Toast.makeText(getActivity(), mButtonSignIn.getText() + " button clicked", Toast.LENGTH_SHORT).show();
				return true;
		}
		return super.onViewClick(view, id);
	}
}
