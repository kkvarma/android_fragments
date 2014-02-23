/*
 * =================================================================================
 * Copyright (C) 2014 Martin Albedinsky [Wolf-ITechnologies]
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
package com.wit.android.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * <h4>Class Overview</h4>
 * <p>
 * Description.
 * </p>
 *
 * @author Martin Albedinsky
 */
public abstract class BaseAdapterFragment<V extends AdapterView, A extends Adapter> extends ActionBarFragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

	/**
	 * Constants =============================
	 */

	/**
	 * Log TAG.
	 */
	// private static final String TAG = BaseAdapterFragment.class.getSimpleName();

	/**
	 * Flag indicating whether the debug output trough log-cat is enabled or not.
	 */
	// private static final boolean DEBUG = true;

	/**
	 * Flag indicating whether the output for user trough log-cat is enabled or not.
	 */
	// private static final boolean USER_LOG = true;

	/**
	 * Enums =================================
	 */

	/**
	 * Static members ========================
	 */

	/**
	 * Members ===============================
	 */

	/**
	 *
	 */
	V mAdapterView;

	/**
	 *
	 */
	View mEmptyView;

	/**
	 *
	 */
	View mLoadingView;

	/**
	 *
	 */
	A mAdapter;

	/**
	 *
	 */
	private CharSequence mEmptyText = "";

	/**
	 * Listeners -----------------------------
	 */

	/**
	 * Arrays --------------------------------
	 */

	/**
	 * Booleans ------------------------------
	 */

	/**
	 * Constructors ==========================
	 */

	/**
	 * Methods ===============================
	 */

	/**
	 * Public --------------------------------
	 */

	/**
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final FrameLayout layout = new FrameLayout(inflater.getContext());
		// Resolve empty view.
		final View emptyView = onCreateEmptyView(inflater, layout, savedInstanceState);
		if (emptyView != null) {
			layout.addView(emptyView, createEmptyViewParams());
		}
		// Resolve loading view.
		final View loadingView = onCreateLoadingView(inflater, layout, savedInstanceState);
		if (loadingView != null) {
			layout.addView(loadingView, createLoadingViewParams());
		}
		// Resolve adapter view.
		final View adapterView = onCreateAdapterView(inflater, layout, savedInstanceState);
		if (adapterView != null) {
			layout.addView(adapterView, createAdapterViewParams());
		}
		return layout;
	}

	/**
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		this.ensureAdapterView(view);
		// Base set up.
		if (mAdapter != null) {
			mAdapterView.setAdapter(mAdapter);
		}
		if (mEmptyView != null) {
			mAdapterView.setEmptyView(mEmptyView);
			if (mEmptyView instanceof TextView) {
				((TextView) mEmptyView).setText(mEmptyText);
			}
		}
		mAdapterView.setOnItemClickListener(this);
		mAdapterView.setOnItemLongClickListener(this);
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 */
	public boolean isEmpty() {
		return mAdapter == null || mAdapter.isEmpty();
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param visible
	 */
	public void setLoadingViewVisible(boolean visible) {
		// TODO: animate
		if (mLoadingView != null) {
			if (visible && mLoadingView.getVisibility() != View.VISIBLE) {
				mLoadingView.setVisibility(View.VISIBLE);
			} else if (!visible && mLoadingView.getVisibility() == View.VISIBLE) {
				mLoadingView.setVisibility(View.GONE);
			}
		}
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 */
	public boolean isLoadingViewVisible() {
		return mLoadingView != null && mLoadingView.getVisibility() == View.VISIBLE;
	}

	/**
	 * Getters + Setters ---------------------
	 */

	/**
	 * <p>
	 * </p>
	 *
	 * @param emptyView
	 */
	public void setEmptyView(View emptyView) {
		this.mEmptyView = emptyView;
		if (mAdapterView != null) {
			mAdapterView.setEmptyView(mEmptyView);
		}
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param resId
	 */
	public void setEmptyViewText(int resId) {
		setEmptyViewText(obtainString(resId));
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param text
	 */
	public void setEmptyViewText(CharSequence text) {
		this.mEmptyText = text;
		if (mEmptyView instanceof TextView) {
			((TextView) mEmptyView).setText(text);
		}
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 */
	public View getEmptyView() {
		return mEmptyView;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 */
	public V getAdapterView() {
		return mAdapterView;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param adapter
	 */
	@SuppressWarnings("unchecked")
	public void setAdapter(A adapter) {
		this.mAdapter = adapter;
		if (mAdapterView != null) {
			mAdapterView.setAdapter(mAdapter);
		}
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 */
	public A getAdapter() {
		return mAdapter;
	}

	/**
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	}

	/**
	 */
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		return false;
	}

	/**
	 * Protected -----------------------------
	 */

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 */
	protected FrameLayout.LayoutParams createAdapterViewParams() {
		return new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param inflater
	 * @param container
	 * @param savedInstanceState
	 * @return
	 */
	protected View onCreateEmptyView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return new TextView(inflater.getContext(), null, android.R.attr.textViewStyle);
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 */
	protected FrameLayout.LayoutParams createEmptyViewParams() {
		final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.CENTER;
		return params;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param inflater
	 * @param container
	 * @param savedInstanceState
	 * @return
	 */
	protected View onCreateLoadingView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View loadingView = new ProgressBar(inflater.getContext(), null, android.R.attr.progressBarStyleLarge);
		loadingView.setVisibility(View.GONE);
		return loadingView;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 */
	protected FrameLayout.LayoutParams createLoadingViewParams() {
		final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.CENTER;
		return params;
	}

	/**
	 * Private -------------------------------
	 */

	/**
	 *
	 * @param view
	 */
	@SuppressWarnings("unchecked")
	private void ensureAdapterView(View view) {
		if ((mAdapterView = (V) view.findViewById(android.R.id.list)) == null) {
			throw new IllegalArgumentException("Missing adapter view(@id/android:list) within adapter fragment layout.");
		}
		// Get also empty view if is presented.
		this.mEmptyView = view.findViewById(android.R.id.empty);
	}

	/**
	 * Abstract methods ----------------------
	 */

	/**
	 * <p>
	 * </p>
	 *
	 * @param inflater
	 * @param container
	 * @param savedInstanceState
	 * @return
	 */
	protected abstract V onCreateAdapterView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

	/**
	 * Inner classes =========================
	 */

	/**
	 * Interface =============================
	 */
}
