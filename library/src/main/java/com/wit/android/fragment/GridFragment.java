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
package com.wit.android.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;

/**
 * <h4>Class Overview</h4>
 * <p>
 * todo: description
 * </p>
 *
 * @param <A> A type of the adapter used within the context of an instance of this GridFragment implementation.
 * @author Martin Albedinsky
 * @see com.wit.android.fragment.ListFragment
 */
public class GridFragment<A extends ListAdapter> extends AdapterFragment<GridView, A> {

	/**
	 */
	@Override
	protected GridView onCreateAdapterView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return new GridView(inflater.getContext(), null, android.R.attr.gridViewStyle);
	}
}
