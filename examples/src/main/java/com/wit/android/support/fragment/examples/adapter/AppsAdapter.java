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
package com.wit.android.support.fragment.examples.adapter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wit.android.support.examples.libs.adapter.ExSimpleAdapter;
import com.wit.android.support.examples.libs.adapter.ExViewHolder;
import com.wit.android.support.examples.libs.adapter.annotation.ExItemViewHolder;
import com.wit.android.support.fragment.examples.R;

/**
 * <p>
 * Description.
 * </p>
 *
 * @author Martin Albedinsky
 */
@ExItemViewHolder(AppsAdapter.ViewHolder.class)
public class AppsAdapter extends ExSimpleAdapter<ApplicationInfo> {

	/**
	 * Log TAG.
	 */
	// private static final String TAG = Adapter.class.getSimpleName();

	/**
	 *
	 */
	private final boolean bAsGrid;

	/**
	 *
	 */
	final PackageManager mPackageManager;

	/**
	 * @param context
	 * @param asGrid
	 */
	public AppsAdapter(Context context, boolean asGrid) {
		super(context);
		this.mPackageManager = context.getPackageManager();
		this.bAsGrid = asGrid;
	}

	/**
	 */
	@Override
	public View onCreateView(int position, LayoutInflater inflater, ViewGroup parent) {
		return bAsGrid ? inflate(R.layout.item_grid_app, parent) : inflate(R.layout.item_list_app, parent);
	}

	/**
	 *
	 */
	public static class ViewHolder implements ExViewHolder<ApplicationInfo, AppsAdapter> {

		/**
		 *
		 */
		private TextView label;

		/**
		 *
		 */
		private ImageView icon;

		/**
		 */
		@Override
		public void create(int position, View view) {
			this.label = (TextView) view.findViewById(R.id.item_app_text_view_label);
			this.icon = (ImageView) view.findViewById(R.id.item_app_image_view_icon);
		}

		/**
		 */
		@Override
		public void bind(int position, ApplicationInfo item, AppsAdapter adapter) {
			label.setText(item.loadLabel(adapter.mPackageManager));
			icon.setImageDrawable(item.loadIcon(adapter.mPackageManager));
		}
	}
}
