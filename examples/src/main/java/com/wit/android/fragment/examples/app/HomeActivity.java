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
package com.wit.android.fragment.examples.app;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.wit.android.examples.app.ExHomeActivity;
import com.wit.android.examples.model.navigation.INavigationItem;
import com.wit.android.examples.model.navigation.NavigationHeader;
import com.wit.android.examples.model.navigation.NavigationItem;
import com.wit.android.fragment.examples.R;
import com.wit.android.fragment.examples.fragment.ImageFragment;
import com.wit.android.fragment.examples.fragment.FragmentsFactory;
import com.wit.android.fragment.manage.FragmentController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Description.
 * </p>
 *
 * @author Martin Albedinsky
 */
public class HomeActivity extends ExHomeActivity {

	/**
	 * Log TAG.
	 */
	private static final String TAG = HomeActivity.class.getSimpleName();

	private final FragmentController FRAGMENT_CONTROLLER = new FragmentController(getSupportFragmentManager());

	{
		// Set up fragment container id.
		FRAGMENT_CONTROLLER.setFragmentContainerID(R.id.Ex_App_Content_Container);
	}

	private boolean bAddFragmentToBackStack = true;

	/**
	 * @param view
	 */
	public void onAddToBackStackClick(View view) {
		this.bAddFragmentToBackStack = ((CheckBox) view).isChecked();
	}

	/**
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		// Set up fragment factory.
		FRAGMENT_CONTROLLER.setFragmentFactory(new FragmentsFactory());
	}

	/**
	 */
	@Override
	protected List<INavigationItem> onCreateNavigationItems(Resources res) {
		final List<INavigationItem> items = new ArrayList<INavigationItem>();

		/**
		 * Fragment implementations.
		 */
		items.add(NavigationHeader.create(R.string.Navigation_Header_Implementations, res));
		items.add(NavigationItem.create(
				WebActivity.NAVIGATION_ID,
				R.string.Navigation_Label_WebFragment,
				res
		).setSelectable(false));

		/**
		 * Show directions.
		 */
		items.add(NavigationHeader.create(R.string.Navigation_Header_ShowDirections, res));
		items.add(NavigationItem.create(
				FragmentsFactory.FRAGMENT_DIRECTION_NONE,
				R.string.Navigation_Label_None,
				res
		));
		items.add(NavigationItem.create(
				FragmentsFactory.FRAGMENT_DIRECTION_FROM_RIGHT_TO_LEFT,
				R.string.Navigation_Label_FromRightToLeft,
				res
		));
		items.add(NavigationItem.create(
				FragmentsFactory.FRAGMENT_DIRECTION_FROM_LEFT_TO_RIGHT,
				R.string.Navigation_Label_FromLeftToRight,
				res
		));
		items.add(NavigationItem.create(
				FragmentsFactory.FRAGMENT_DIRECTION_FROM_TOP_TO_BOTTOM,
				R.string.Navigation_Label_FromTopToBottom,
				res
		));
		items.add(NavigationItem.create(
				FragmentsFactory.FRAGMENT_DIRECTION_FROM_BOTTOM_TO_TOP,
				R.string.Navigation_Label_FromBottomToTop,
				res
		));
		items.add(NavigationItem.create(
				FragmentsFactory.FRAGMENT_DIRECTION_FROM_BACKGROUND_TO_LEFT,
				R.string.Navigation_Label_FromBackgroundToLeft,
				res
		));
		items.add(NavigationItem.create(
				FragmentsFactory.FRAGMENT_DIRECTION_FROM_BACKGROUND_TO_RIGHT,
				R.string.Navigation_Label_FromBackgroundToRight,
				res
		));
		items.add(NavigationItem.create(
				FragmentsFactory.FRAGMENT_DIRECTION_FROM_BACKGROUND_TO_TOP,
				R.string.Navigation_Label_FromBackgroundToTop,
				res
		));
		items.add(NavigationItem.create(
				FragmentsFactory.FRAGMENT_DIRECTION_FROM_BACKGROUND_TO_BOTTOM,
				R.string.Navigation_Label_FromBackgroundToBottom,
				res
		));
		items.add(NavigationItem.create(
				FragmentsFactory.FRAGMENT_DIRECTION_FROM_LEFT_TO_BACKGROUND,
				R.string.Navigation_Label_FromLeftToBackground,
				res
		));
		items.add(NavigationItem.create(
				FragmentsFactory.FRAGMENT_DIRECTION_FROM_RIGHT_TO_BACKGROUND,
				R.string.Navigation_Label_FromRightToBackground,
				res
		));
		items.add(NavigationItem.create(
				FragmentsFactory.FRAGMENT_DIRECTION_FROM_TOP_TO_BACKGROUND,
				R.string.Navigation_Label_FromTopToBackground,
				res
		));
		items.add(NavigationItem.create(
				FragmentsFactory.FRAGMENT_DIRECTION_FROM_BOTTOM_TO_BACKGROUND,
				R.string.Navigation_Label_FromBottomToBackground,
				res
		));

		return items;
	}

	/**
	 */
	@Override
	protected boolean onNavigationItemClick(int position, long id) {
		final int itemID = (int) id;
		switch (itemID) {
			case WebActivity.NAVIGATION_ID:
				startActivity(new Intent(this, WebActivity.class));
				break;
			default:
				// Create parameters for fragment factory.
				final Bundle params = new Bundle();
				params.putBoolean(FragmentsFactory.PARAM_ADD_TO_BACK_STACK, bAddFragmentToBackStack);
				params.putString(FragmentsFactory.PARAM_ACTION_BAR_TITLE, getNavigationItem(position).getText());

				// Register action which will be executed, after the
				// navigation closes.
				registerAction(new Runnable() {
					@Override
					public void run() {
						FRAGMENT_CONTROLLER.showFragment(itemID, params);
					}
				});
		}
		return true;
	}

	/**
	 */
	@Override
	protected int onShowInitialFragment() {
		final int initialItemPos = 3;
		FRAGMENT_CONTROLLER.showFragment(ImageFragment.newInstance(getNavigationItem(initialItemPos).getText()));
		return 1;
	}
}