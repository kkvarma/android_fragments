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

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.view.ActionMode;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.wit.android.examples.internal.app.ExBaseActionBarActivity;
import com.wit.android.fragment.WebFragment;
import com.wit.android.fragment.examples.R;

/**
 * <p>
 * Description.
 * </p>
 *
 * @author Martin Albedinsky
 */
public class WebActivity extends ExBaseActionBarActivity {

	/**
	 *
	 */
	public static final int NAVIGATION_ID = 0xa01;

	/**
	 * Log TAG.
	 */
	private static final String TAG = WebActivity.class.getSimpleName();

	/**
	 *
	 */
	private EditText mUrlEdit;
	private View mActionView;

	/**
	 */
	@Override
	public void onBackPressed() {
		if (!dispatchBackPressed()) {
			super.onBackPressed();
		}
	}

	/**
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.web_activity, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean processed = false;
		switch (item.getItemId()) {
			case R.id.Ex_App_Menu_Edit:
				startSupportActionMode(new ActionModeCallback());
				processed = true;
				break;
			case android.R.id.home:
				if (!dispatchBackPressed()) {
					finish();
				}
				processed = true;
				break;
		}
		return processed || super.onOptionsItemSelected(item);
	}

	/**
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final FrameLayout contentLayout = new FrameLayout(this);
		contentLayout.setId(R.id.Ex_App_Content_Container);
		setContentView(contentLayout);

		this.mActionView = getLayoutInflater().inflate(R.layout.web_action_mode_url_edit, null);
		if (mActionView != null) {
			mUrlEdit = (EditText) mActionView.findViewById(R.id.Web_ActionMode_EditText_UrlEdit);
		}

		// Set up action bar.
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		getFragmentController().setFragmentContainerID(R.id.Ex_App_Content_Container);
		if (savedInstanceState == null) {
			final WebFragment webFragment = WebFragment.newInstance(
					new WebFragment.WebOptions().content("" +
							"http://www.google.com"
					)
			);
			webFragment.setOnWebContentLoadingListener(new LoadingListener());

			getFragmentController().showFragment(webFragment);
		}
	}

	/**
	 *
	 * @param url
	 */
	private void loadUrl(String url) {
		final WebFragment fragment = (WebFragment) getFragmentController().getVisibleFragment();
		if (fragment != null) {
			fragment.loadContent(url);
		}
	}

	/**
	 *
	 * @return
	 */
	private boolean dispatchBackPressed() {
		final WebFragment webFragment = (WebFragment) getFragmentController().getVisibleFragment();
		return (webFragment != null && webFragment.dispatchBackPress());
	}

	/**
	 *
	 */
	private class ActionModeCallback implements ActionMode.Callback {

		@Override
		public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
			actionMode.setCustomView(mActionView);
			return true;
		}

		@Override
		public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
			return false;
		}

		@Override
		public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
			return false;
		}

		@Override
		public void onDestroyActionMode(ActionMode actionMode) {
			final Editable urlEditable = mUrlEdit.getText();
			if (urlEditable != null && urlEditable.length() > 0) {
				final String url = urlEditable.toString();
				if (WebFragment.isValidWebUrl(url)) {
					// Load entered url into web fragment.
					loadUrl(url);
				} else {
					Toast.makeText(WebActivity.this, R.string.Activity_Web_Toast_OnlyUrlIsAllowed, Toast.LENGTH_SHORT).show();
				}
			}
		}
	}

	private class LoadingListener implements WebFragment.OnWebContentLoadingListener {

		@Override
		public void onLoadingStarted(String webContent) {

		}

		@Override
		public void onLoadingFinished(String webContent) {

		}
	}

}