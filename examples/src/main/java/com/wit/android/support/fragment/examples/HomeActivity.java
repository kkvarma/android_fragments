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
package com.wit.android.support.fragment.examples;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

import com.wit.android.support.examples.app.ExBaseHomeActivity;
import com.wit.android.support.examples.model.ExNavigationItem;
import com.wit.android.support.fragment.examples.adapter.TransitionsAdapter;
import com.wit.android.support.fragment.examples.fragment.ActionBarFragmentImpl;
import com.wit.android.support.fragment.examples.fragment.FragmentsFactory;
import com.wit.android.support.fragment.manage.FragmentController;
import com.wit.android.support.fragment.manage.FragmentTransition;

import java.util.List;

/**
 * <p>
 * Description.
 * </p>
 *
 * @author Martin Albedinsky
 */
public class HomeActivity extends ExBaseHomeActivity implements FragmentController.OnChangeListener, FragmentController.OnBackStackChangeListener, ActionBar.OnNavigationListener {

	/**
	 * Log TAG.
	 */
	private static final String TAG = HomeActivity.class.getSimpleName();

	/**
	 *
	 */
	private FragmentController mController;

	/**
	 *
	 */
	private TransitionsAdapter mTransitionsAdapter;

	/**
	 *
	 */
	private boolean bAddFragmentToBackStack = true;

	/**
	 *
	 */
	private ActionBar mActionBar;

	/**
	 *
	 */
	private boolean bRestored;

	/**
	 * @param view
	 */
	public void onAddToBackStackClick(View view) {
		this.bAddFragmentToBackStack = ((CheckBox) view).isChecked();
	}

	/**
	 */
	@Override
	public void onFragmentsBackStackChanged(boolean added, int id, String tag) {
		Log.d(TAG, "onFragmentsBackStackChanged(added:" + added + ") id: " + id + " | tag: " + tag);
	}

	/**
	 */
	@Override
	public void onFragmentChanged(int id, String tag, boolean fromFactory) {
		Log.d(TAG, "onFragmentChanged(factory:" + fromFactory + ") id: " + id + " | tag: " + tag);
	}

	/**
	 * Invoked when an item at the specific <var>position</var> was selected in the transitions
	 * navigation drop down menu.
	 */
	@Override
	public boolean onNavigationItemSelected(int position, long id) {
		mTransitionsAdapter.dispatchItemSelected(position);
		if (!bRestored) {
			final FragmentTransition transition = mTransitionsAdapter.getItem(position);
			if (transition != null) {
				mController.showFragment(
						FragmentsFactory.TRANSITIONS,
						FragmentsFactory.createParams(transition, bAddFragmentToBackStack)
				);
				return true;
			}
		}
		bRestored = false;
		return false;
	}

	/**
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				if (getNavigationAdapter().getSelectedItemPosition() == 0) {
					return false;
				}
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		// Set up fragment controller.
		this.mController = new FragmentController(this);
		mController.setFragmentContainerId(R.id.Ex_Content_Container);
		mController.setFragmentFactory(new FragmentsFactory());

		// Set up action bar.
		mActionBar = getSupportActionBar();
		this.mTransitionsAdapter = new TransitionsAdapter(this);
		mActionBar.setListNavigationCallbacks(mTransitionsAdapter, this);

		if (!(bRestored = savedInstanceState != null)) {
			final int selectedPosition = 0;
			setNavigationItemSelected(selectedPosition);
			onNavigationChange(FragmentsFactory.TRANSITIONS);
		}
	}

	/**
	 */
	@Override
	protected List<ExNavigationItem> onCreateNavigationItems(List<ExNavigationItem> list, Resources resources) {
		final ExNavigationItem.Builder builder = new ExNavigationItem.Builder(resources);
		list.add(createItem(
				FragmentsFactory.TRANSITIONS,
				R.string.Navigation_Label_Transitions,
				builder
		));
		list.add(createItem(
				FragmentsFactory.LIST,
				R.string.Navigation_Label_ListFragment,
				builder
		));
		list.add(createItem(
				FragmentsFactory.GRID,
				R.string.Navigation_Label_GridFragment,
				builder
		));
		list.add(builder
						.id(WebActivity.NAVIGATION_ID)
						.title(R.string.Navigation_Label_WebFragment)
						.selectable(false)
						.build()
		);
		list.add(createItem(
				FragmentsFactory.ACTION_BAR,
				R.string.Navigation_Label_ActionBarFragment,
				builder
		));
		list.add(createItem(
				FragmentsFactory.ANNOTATIONS,
				R.string.Navigation_Label_AnnotatedFragment,
				builder
		));
		return list;
	}

	/**
	 */
	@Override
	protected boolean onNavigationItemClick(int position, final int id) {
		onNavigationChange(id);
		switch (id) {
			case WebActivity.NAVIGATION_ID:
				startActivity(new Intent(this, WebActivity.class));
				break;
			case FragmentsFactory.TRANSITIONS:
				registerNavigationAction(new Runnable() {

					/**
					 */
					@Override
					public void run() {
						mController.showFragment(
								id,
								FragmentsFactory.createParams(FragmentTransition.FADE_IN, bAddFragmentToBackStack)
						);
					}
				});
				break;
			case FragmentsFactory.ACTION_BAR:
				registerNavigationAction(new Runnable() {

					/**
					 */
					@Override
					public void run() {
						mController.showFragment(id, ActionBarFragmentImpl.createParams(R.string.ActionBarFragment_Title));
					}
				});
				break;
			default:
				registerNavigationAction(new Runnable() {

					/**
					 */
					@Override
					public void run() {
						mController.showFragment(id);
					}
				});
		}
		return true;
	}

	/**
	 */
	@Override
	protected void onNavigationOpened() {
		super.onNavigationOpened();
		if (getNavigationAdapter().getSelectedItemPosition() == 0) {
			mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		}
	}

	/**
	 */
	@Override
	protected void onNavigationClosed() {
		super.onNavigationClosed();
		if (getNavigationAdapter().getSelectedItemPosition() == 0) {
			mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
			mActionBar.setTitle("");
		} else {
			mActionBar.setTitle(R.string.App_Name);
		}
	}

	/**
	 *
	 * @param itemId
	 */
	private void onNavigationChange(int itemId) {
		switch(itemId) {
			case FragmentsFactory.TRANSITIONS:
				mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
				mActionBar.setSelectedNavigationItem(0);
				mActionBar.setDisplayHomeAsUpEnabled(false);
				mActionBar.setHomeButtonEnabled(false);
				mActionBar.setTitle("");
				break;
			default:
				if (mController != null) {
					mController.clearBackStackImmediate();
				}
				mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
				mActionBar.setDisplayHomeAsUpEnabled(true);
				mActionBar.setHomeButtonEnabled(true);
		}
	}
}