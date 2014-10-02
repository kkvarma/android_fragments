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

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.wit.android.fragment.GridFragment;
import com.wit.android.fragment.annotation.ActionModeOptions;
import com.wit.android.fragment.annotation.AdapterViewOptions;
import com.wit.android.fragment.examples.R;
import com.wit.android.fragment.examples.adapter.AppsAdapter;
import com.wit.android.fragment.examples.content.AppsAsyncTask;

/**
 * <p>
 * Description.
 * </p>
 *
 * @author Martin Albedinsky
 */
@ActionModeOptions
@AdapterViewOptions(emptyText = R.string.adapter_fragment_empty, longClickable = true)
public class SampleGridFragment extends GridFragment<AppsAdapter> {

	/**
	 * Log TAG.
	 */
	// private static final String TAG = "SampleGridFragment";

	/**
	 *
	 */
	private PackageManager mPackageManager;

	/**
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mPackageManager = getActivity().getPackageManager();

		final AppsAdapter adapter = new AppsAdapter(getActivity(), true);
		setAdapter(adapter);
		new AppsAsyncTask(adapter).execute();
	}

	/**
	 */
	@Override
	protected void onViewCreated(@NonNull View view, @NonNull GridView adapterView, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, adapterView, savedInstanceState);
		adapterView.setNumColumns(GridView.AUTO_FIT);
	}

	/**
	 */
	@Override
	public void onItemClick(@NonNull AdapterView<?> parent, @NonNull View view, int position, long id) {
		final ApplicationInfo appInfo = getAdapter().getItem(position);
		if (appInfo != null) {
			try {
				startActivity(mPackageManager.getLaunchIntentForPackage(appInfo.packageName));
			} catch (Exception e) {
				Toast.makeText(getActivity(), "No permission to launch " + appInfo.packageName, Toast.LENGTH_SHORT).show();
			}
		}
	}
}
