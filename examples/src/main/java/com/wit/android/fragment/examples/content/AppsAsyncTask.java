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
package com.wit.android.fragment.examples.content;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;

import com.wit.android.fragment.examples.adapter.AppsAdapter;

import java.util.List;

/**
 * <p>
 * Description.
 * </p>
 *
 * @author Martin Albedinsky
 */
public class AppsAsyncTask extends AsyncTask<Void, Void, List<ApplicationInfo>> {

	/**
	 * Log TAG.
	 */
	// private static final String TAG = AppsAsyncTask.class.getSimpleName();

	/**
	 *
	 */
	private AppsAdapter mAppsAdapter;

	/**
	 *
	 * @param appsAdapter
	 */
	public AppsAsyncTask(AppsAdapter appsAdapter) {
		this.mAppsAdapter = appsAdapter;
	}

	/**
	 */
	@Override
	protected List<ApplicationInfo> doInBackground(Void... params) {
		if (mAppsAdapter != null) {
			final Context context = mAppsAdapter.getContext();
			final PackageManager packageManager = context.getPackageManager();
			if (packageManager != null) {
				return packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
			}

		}
		return null;
	}

	/**
	 */
	@Override
	protected void onPostExecute(List<ApplicationInfo> activities) {
		if (mAppsAdapter != null) {
			mAppsAdapter.changeItems(activities);
		}
	}
}
