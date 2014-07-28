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
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.wit.android.support.examples.libs.adapter.ExSimpleSpinnerAdapter;
import com.wit.android.support.examples.libs.adapter.ExViewHolder;
import com.wit.android.support.examples.libs.adapter.annotation.ExItemView;
import com.wit.android.support.examples.libs.adapter.annotation.ExItemViewHolder;
import com.wit.android.support.fragment.examples.R;
import com.wit.android.support.fragment.manage.FragmentTransition;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Description.
 * </p>
 *
 * @author Martin Albedinsky
 */
@ExItemView(R.layout.spinner_item_transition)
@ExItemViewHolder(TransitionsAdapter.ViewHolder.class)
public class TransitionsAdapter extends ExSimpleSpinnerAdapter<FragmentTransition> {

	/**
	 * Log TAG.
	 */
	// private static final String TAG = TransitionsAdapter.class.getSimpleName();

	/**
	 *
	 * @param context
	 */
	public TransitionsAdapter(Context context) {
		super(context);
		// Fill up data set.
		final List<FragmentTransition> items = new ArrayList<>(14);
		items.add(FragmentTransition.NONE);
		items.add(FragmentTransition.FADE_IN);
		items.add(FragmentTransition.SLIDE_TO_LEFT);
		items.add(FragmentTransition.SLIDE_TO_RIGHT);
		items.add(FragmentTransition.SLIDE_TO_BOTTOM);
		items.add(FragmentTransition.SLIDE_TO_TOP);
		items.add(FragmentTransition.SLIDE_TO_LEFT_AND_SCALE_OUT);
		items.add(FragmentTransition.SLIDE_TO_RIGHT_AND_SCALE_OUT);
		items.add(FragmentTransition.SLIDE_TO_BOTTOM_AND_SCALE_OUT);
		items.add(FragmentTransition.SLIDE_TO_TOP_AND_SCALE_OUT);
		items.add(FragmentTransition.SCALE_IN_AND_SLIDE_TO_LEFT);
		items.add(FragmentTransition.SCALE_IN_AND_SLIDE_TO_RIGHT);
		items.add(FragmentTransition.SCALE_IN_AND_SLIDE_TO_BOTTOM);
		items.add(FragmentTransition.SCALE_IN_AND_SLIDE_TO_TOP);
		changeItems(items);
	}

	/**
	 */
	@Override
	public void onBindView(int position, Object viewHolder) {
		super.onBindView(position, viewHolder);
		((ViewHolder) viewHolder).label.setPadding(0, 0, 0, 0);
	}

	/**
	 *
	 */
	public static class ViewHolder implements ExViewHolder<FragmentTransition, TransitionsAdapter> {

		/**
		 *
		 */
		private TextView label;

		/**
		 */
		@Override
		public void create(int position, View view) {
			this.label = (TextView) view;
		}

		/**
		 */
		@Override
		public void bind(int position, FragmentTransition item, TransitionsAdapter adapter) {
			final String name = item.name();
			if (TextUtils.isEmpty(name)) {
				label.setText("Unknown transition");
			} else {
				label.setText(name.replace("_", " "));
			}
		}
	}
}
