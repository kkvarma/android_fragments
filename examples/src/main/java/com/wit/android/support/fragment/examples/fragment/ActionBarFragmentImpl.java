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
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.wit.android.support.fragment.ActionBarFragment;
import com.wit.android.support.fragment.annotation.ContentView;
import com.wit.android.support.fragment.annotation.MenuOptions;
import com.wit.android.support.fragment.examples.R;

/**
 * <p>
 * Description.
 * </p>
 *
 * @author Martin Albedinsky
 */
@MenuOptions(R.menu.menu_test)
@ContentView(R.layout.fragment_action_bar)
public class ActionBarFragmentImpl extends ActionBarFragment {

	/**
	 * Log TAG.
	 */
	// private static final String TAG = ActionBarFragmentImpl.class.getSimpleName();

	/**
	 *
	 */
	private static final String PARAMS_TITLE_RES = "com.wit.android.support.fragment.examples.fragment.ActionBarFragmentImpl.PARAMS.TitleRes";

	/**
	 *
	 * @param titleRes
	 * @return
	 */
	public static Bundle createParams(int titleRes) {
		final Bundle params = new Bundle();
		params.putInt(PARAMS_TITLE_RES, titleRes);
		return params;
	}

	/**
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.ex_menu_action_search:
				Toast.makeText(getActivity(), "SEARCH menu item", Toast.LENGTH_SHORT).show();
				return true;
			case R.id.ex_menu_action_edit:
				Toast.makeText(getActivity(), "EDIT menu item", Toast.LENGTH_SHORT).show();
				return true;
			case R.id.ex_menu_action_new:
				Toast.makeText(getActivity(), "NEW menu item", Toast.LENGTH_SHORT).show();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 */
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setActionBarTitle(getArguments().getInt(PARAMS_TITLE_RES));
	}
}
