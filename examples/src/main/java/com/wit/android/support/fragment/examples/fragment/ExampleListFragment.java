package com.wit.android.support.fragment.examples.fragment;

import android.os.Bundle;

import com.wit.android.support.fragment.ListFragment;

/**
 * @author Martin Albedinsky
 */
public class ExampleListFragment extends ListFragment {

	/**
	 * Log TAG.
	 */
	// private static final String TAG = ExampleListFragment.class.getSimpleName();

	public static ExampleListFragment newInstance() {
		return new ExampleListFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setEmptyViewText("Empty");
	}
}
