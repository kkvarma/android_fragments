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
package com.wit.android.support.fragment;

import android.view.View;

/**
 * <h4>Interface Overview</h4>
 * todo: description
 *
 * @author Martin Albedinsky
 */
public interface ViewClickWatcher {

	/**
	 * Called to dispatch a view click event to this watcher instance.
	 *
	 * @param view The view which was clicked.
	 * @return <code>True</code> if this watcher processed the click event for the specified <var>view</var>,
	 * <code>false</code> otherwise.
	 */
	public boolean dispatchViewClick(View view);
}
