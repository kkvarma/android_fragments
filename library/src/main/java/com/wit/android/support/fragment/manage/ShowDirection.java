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
package com.wit.android.support.fragment.manage;

import android.os.Parcel;
import android.os.Parcelable;

import com.wit.android.support.fragment.R;

/**
 * <h4>Class Overview</h4>
 * <p>
 * Description.
 * </p>
 *
 * @author Martin Albedinsky
 */
public class ShowDirection implements Parcelable {

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * Log TAG.
	 */
	// private static final String TAG = ShowDirection.class.getSimpleName();

	/**
	 * Flag indicating whether the debug output trough log-cat is enabled or not.
	 */
	// private static final boolean DEBUG = true;

	/**
	 * Flag indicating whether the output for user trough log-cat is enabled or not.
	 */
	// private static final boolean USER_LOG = true;

	/**
	 * Enums =======================================================================================
	 */

	/**
	 * Static members ==============================================================================
	 */

	/**
	 * <p>
	 * Parcelable creator.
	 * </p>
	 */
	public static final Creator<ShowDirection> CREATOR = new Creator<ShowDirection>() {
		@Override
		public ShowDirection createFromParcel(Parcel source) {
			return new ShowDirection(source);
		}

		@Override
		public ShowDirection[] newArray(int size) {
			return new ShowDirection[size];
		}
	};

	/**
	 * <p>
	 * Use this to show new incoming fragment which will replace the current fragment without any
	 * animation.
	 * </p>
	 */
	public static final ShowDirection NONE = new ShowDirection(0, 0, 0, 0, "NONE");

	/**
	 * <p>
	 * Use this direction to slide a new incoming fragment from the left and outgoing (the current
	 * one) will be slided to the right.
	 * </p>
	 * <h6>Powered by animations:</h6>
	 * <ul>
	 * <li><b>Incoming:</b> {@link com.wit.android.support.fragment.R.anim#fragment_slide_in_right}</li>
	 * <li><b>Outgoing:</b> {@link com.wit.android.support.fragment.R.anim#fragment_slide_out_right}</li>
	 * <li><b>Incoming (back-stack):</b> {@link com.wit.android.support.fragment.R.anim#fragment_slide_in_left_back}</li>
	 * <li><b>Outgoing (back-stack):</b> {@link com.wit.android.support.fragment.R.anim#fragment_slide_out_left_back}</li>
	 * </ul>
	 */
	public static final ShowDirection FROM_LEFT_TO_RIGHT = new ShowDirection(
			// Incoming animation.
			R.anim.fragment_slide_in_right,
			// Outgoing animation.
			R.anim.fragment_slide_out_right,
			// Incoming back-stack animation.
			R.anim.fragment_slide_in_left_back,
			// Outgoing back-stack animation.
			R.anim.fragment_slide_out_left_back,
			"FROM_LEFT_TO_RIGHT"
	);

	/**
	 * <p>
	 * Use this direction to slide a new incoming fragment from the right and outgoing (the current
	 * one) will be slided to the left.
	 * </p>
	 * <h6>Powered by animations:</h6>
	 * <ul>
	 * <li><b>Incoming:</b> {@link R.anim#fragment_slide_in_left}</li>
	 * <li><b>Outgoing:</b> {@link R.anim#fragment_slide_out_left}</li>
	 * <li><b>Incoming (back-stack):</b> {@link R.anim#fragment_slide_in_right_back}</li>
	 * <li><b>Outgoing (back-stack):</b> {@link R.anim#fragment_slide_out_right_back}</li>
	 * </ul>
	 */
	public static final ShowDirection FROM_RIGHT_TO_LEFT = new ShowDirection(
			// Incoming animation.
			R.anim.fragment_slide_in_left,
			// Outgoing animation.
			R.anim.fragment_slide_out_left,
			// Incoming back-stack animation.
			R.anim.fragment_slide_in_right_back,
			// Outgoing back-stack animation.
			R.anim.fragment_slide_out_right_back,
			"FROM_RIGHT_TO_LEFT"
	);

	/**
	 * <p>
	 * Use this direction to slide a new incoming fragment from the top and outgoing (the current
	 * one) will be slided to the bottom.
	 * </p>
	 * <h6>Powered by animations:</h6>
	 * <ul>
	 * <li><b>Incoming:</b> {@link R.anim#fragment_slide_in_bottom}</li>
	 * <li><b>Outgoing:</b> {@link R.anim#fragment_slide_out_bottom}</li>
	 * <li><b>Incoming (back-stack):</b> {@link R.anim#fragment_slide_in_top_back}</li>
	 * <li><b>Outgoing (back-stack):</b> {@link R.anim#fragment_slide_out_top_back}</li>
	 * </ul>
	 */
	public static final ShowDirection FROM_TOP_TO_BOTTOM = new ShowDirection(
			// Incoming animation.
			R.anim.fragment_slide_in_bottom,
			// Outgoing animation.
			R.anim.fragment_slide_out_bottom,
			// Incoming back-stack animation.
			R.anim.fragment_slide_in_top_back,
			// Outgoing back-stack animation.
			R.anim.fragment_slide_out_top_back,
			"FROM_TOP_TO_BOTTOM"
	);

	/**
	 * <p>
	 * Use this direction to slide a new incoming fragment from the bottom and outgoing (the current
	 * one) will be slided to the top.
	 * </p>
	 * <h6>Powered by animations:</h6>
	 * <ul>
	 * <li><b>Incoming:</b> {@link R.anim#fragment_slide_in_top}</li>
	 * <li><b>Outgoing:</b> {@link R.anim#fragment_slide_out_top}</li>
	 * <li><b>Incoming (back-stack):</b> {@link R.anim#fragment_slide_in_bottom_back}</li>
	 * <li><b>Outgoing (back-stack):</b> {@link R.anim#fragment_slide_out_bottom_back}</li>
	 * </ul>
	 */
	public static final ShowDirection FROM_BOTTOM_TO_TOP = new ShowDirection(
			// Incoming animation.
			R.anim.fragment_slide_in_top,
			// Outgoing animation.
			R.anim.fragment_slide_out_top,
			// Incoming back-stack animation.
			R.anim.fragment_slide_in_bottom_back,
			// Outgoing back-stack animation.
			R.anim.fragment_slide_out_bottom_back,
			"FROM_BOTTOM_TO_TOP"
	);

	/**
	 * <p>
	 * Use this direction to scale (with fade) a new incoming fragment from the background and
	 * outgoing (the current one) will be slided to the left.
	 * </p>
	 * <h6>Powered by animations:</h6>
	 * <ul>
	 * <li><b>Incoming:</b> {@link R.anim#fragment_scale_in}</li>
	 * <li><b>Outgoing:</b> {@link R.anim#fragment_slide_out_left}</li>
	 * <li><b>Incoming (back-stack):</b> {@link R.anim#fragment_slide_in_right_back}</li>
	 * <li><b>Outgoing (back-stack):</b> {@link R.anim#fragment_scale_out_back}</li>
	 * </ul>
	 */
	public static final ShowDirection FROM_BACKGROUND_TO_LEFT = new ShowDirection(
			// Incoming animation.
			R.anim.fragment_scale_in,
			// Outgoing animation.
			R.anim.fragment_slide_out_left,
			// Incoming back-stack animation.
			R.anim.fragment_slide_in_right_back,
			// Outgoing back-stack animation.
			R.anim.fragment_scale_out_back,
			"FROM_BACKGROUND_TO_LEFT"
	);

	/**
	 * <p>
	 * Use this direction to scale (with fade) a new incoming fragment from the background and
	 * outgoing (the current one) will be slided to the right.
	 * </p>
	 * <h6>Powered by animations:</h6>
	 * <ul>
	 * <li><b>Incoming:</b> {@link R.anim#fragment_scale_in}</li>
	 * <li><b>Outgoing:</b> {@link R.anim#fragment_slide_out_right}</li>
	 * <li><b>Incoming (back-stack):</b> {@link R.anim#fragment_slide_in_left_back}</li>
	 * <li><b>Outgoing (back-stack):</b> {@link R.anim#fragment_scale_out_back}</li>
	 * </ul>
	 */
	public static final ShowDirection FROM_BACKGROUND_TO_RIGHT = new ShowDirection(
			// Incoming animation.
			R.anim.fragment_scale_in,
			// Outgoing animation.
			R.anim.fragment_slide_out_right,
			// Incoming back-stack animation.
			R.anim.fragment_slide_in_left_back,
			// Outgoing back-stack animation.
			R.anim.fragment_scale_out_back,
			"FROM_BACKGROUND_TO_RIGHT"
	);

	/**
	 * <p>
	 * Use this direction to scale (with fade) a new incoming fragment from the background and
	 * outgoing (the current one) will be slided to the top.
	 * </p>
	 * <h6>Powered by animations:</h6>
	 * <ul>
	 * <li><b>Incoming:</b> {@link R.anim#fragment_scale_in}</li>
	 * <li><b>Outgoing:</b> {@link R.anim#fragment_slide_out_top}</li>
	 * <li><b>Incoming (back-stack):</b> {@link R.anim#fragment_slide_in_bottom_back}</li>
	 * <li><b>Outgoing (back-stack):</b> {@link R.anim#fragment_scale_out_back}</li>
	 * </ul>
	 */
	public static final ShowDirection FROM_BACKGROUND_TO_TOP = new ShowDirection(
			// Incoming animation.
			R.anim.fragment_scale_in,
			// Outgoing animation.
			R.anim.fragment_slide_out_top,
			// Incoming back-stack animation.
			R.anim.fragment_slide_in_bottom_back,
			// Outgoing back-stack animation.
			R.anim.fragment_scale_out_back,
			"FROM_BACKGROUND_TO_TOP"
	);

	/**
	 * <p>
	 * Use this direction to scale (with fade) a new incoming fragment from the background and
	 * outgoing (the current one) will be slided to the bottom.
	 * </p>
	 * <h6>Powered by animations:</h6>
	 * <ul>
	 * <li><b>Incoming:</b> {@link R.anim#fragment_scale_in}</li>
	 * <li><b>Outgoing:</b> {@link R.anim#fragment_slide_out_bottom}</li>
	 * <li><b>Incoming (back-stack):</b> {@link R.anim#fragment_slide_in_top_back}</li>
	 * <li><b>Outgoing (back-stack):</b> {@link R.anim#fragment_scale_out_back}</li>
	 * </ul>
	 */
	public static final ShowDirection FROM_BACKGROUND_TO_BOTTOM = new ShowDirection(
			// Incoming animation.
			R.anim.fragment_scale_in,
			// Outgoing animation.
			R.anim.fragment_slide_out_bottom,
			// Incoming back-stack animation.
			R.anim.fragment_slide_in_top_back,
			// Outgoing back-stack animation.
			R.anim.fragment_scale_out_back,
			"FROM_BACKGROUND_TO_BOTTOM"
	);

	/**
	 * <p>
	 * Use this direction to slide a new incoming fragment from the left and outgoing (the current
	 * one) will be scaled (with fade) to the background.
	 * </p>
	 * <h6>Powered by animations:</h6>
	 * <ul>
	 * <li><b>Incoming:</b> {@link R.anim#fragment_slide_in_right}</li>
	 * <li><b>Outgoing:</b> {@link R.anim#fragment_scale_out}</li>
	 * <li><b>Incoming (back-stack):</b> {@link R.anim#fragment_scale_in_back}</li>
	 * <li><b>Outgoing (back-stack):</b> {@link R.anim#fragment_slide_out_left_back}</li>
	 * </ul>
	 */
	public static final ShowDirection FROM_LEFT_TO_BACKGROUND = new ShowDirection(
			// Incoming animation.
			R.anim.fragment_slide_in_right,
			// Outgoing animation.
			R.anim.fragment_scale_out,
			// Incoming back-stack animation.
			R.anim.fragment_scale_in_back,
			// Outgoing back-stack animation.
			R.anim.fragment_slide_out_left_back,
			"FROM_LEFT_TO_BACKGROUND"
	);

	/**
	 * <p>
	 * Use this direction to slide a new incoming fragment from the right and outgoing (the current
	 * one) will be scaled (with fade) to the background.
	 * </p>
	 * <h6>Powered by animations:</h6>
	 * <ul>
	 * <li><b>Incoming:</b> {@link R.anim#fragment_slide_in_left}</li>
	 * <li><b>Outgoing:</b> {@link R.anim#fragment_scale_out}</li>
	 * <li><b>Incoming (back-stack):</b> {@link R.anim#fragment_scale_in_back}</li>
	 * <li><b>Outgoing (back-stack):</b> {@link R.anim#fragment_slide_out_right_back}</li>
	 * </ul>
	 */
	public static final ShowDirection FROM_RIGHT_TO_BACKGROUND = new ShowDirection(
			// Incoming animation.
			R.anim.fragment_slide_in_left,
			// Outgoing animation.
			R.anim.fragment_scale_out,
			// Incoming back-stack animation.
			R.anim.fragment_scale_in_back,
			// Outgoing back-stack animation.
			R.anim.fragment_slide_out_right_back,
			"FROM_RIGHT_TO_BACKGROUND"
	);

	/**
	 * <p>
	 * Use this direction to slide a new incoming fragment from the top and outgoing (the current
	 * one) will be scaled (with fade) to the background.
	 * </p>
	 * <h6>Powered by animations:</h6>
	 * <ul>
	 * <li><b>Incoming:</b> {@link R.anim#fragment_slide_in_bottom}</li>
	 * <li><b>Outgoing:</b> {@link R.anim#fragment_scale_out}</li>
	 * <li><b>Incoming (back-stack):</b> {@link R.anim#fragment_scale_in_back}</li>
	 * <li><b>Outgoing (back-stack):</b> {@link R.anim#fragment_slide_out_top_back}</li>
	 * </ul>
	 */
	public static final ShowDirection FROM_TOP_TO_BACKGROUND = new ShowDirection(
			// Incoming animation.
			R.anim.fragment_slide_in_bottom,
			// Outgoing animation.
			R.anim.fragment_scale_out,
			// Incoming back-stack animation.
			R.anim.fragment_scale_in_back,
			// Outgoing back-stack animation.
			R.anim.fragment_slide_out_top_back,
			"FROM_TOP_TO_BACKGROUND"
	);

	/**
	 * <p>
	 * Use this direction to slide a new incoming fragment from the bottom and outgoing (the current
	 * one) will be scaled (with fade) to the background.
	 * </p>
	 * <h6>Powered by animations:</h6>
	 * <ul>
	 * <li><b>Incoming:</b> {@link R.anim#fragment_slide_in_top}</li>
	 * <li><b>Outgoing:</b> {@link R.anim#fragment_scale_out}</li>
	 * <li><b>Incoming (back-stack):</b> {@link R.anim#fragment_scale_in_back}</li>
	 * <li><b>Outgoing (back-stack):</b> {@link R.anim#fragment_slide_out_bottom_back}</li>
	 * </ul>
	 */
	public static final ShowDirection FROM_BOTTOM_TO_BACKGROUND = new ShowDirection(
			// Incoming animation.
			R.anim.fragment_slide_in_top,
			// Outgoing animation.
			R.anim.fragment_scale_out,
			// Incoming back-stack animation.
			R.anim.fragment_scale_in_back,
			// Outgoing back-stack animation.
			R.anim.fragment_slide_out_bottom_back,
			"FROM_BOTTOM_TO_BACKGROUND"
	);

	/**
	 * Members =====================================================================================
	 */

	/**
	 * Animation resource id.
	 */
	private int mInAnimResId, mOutAnimResId, mInAnimBackResId, mOutAnimBackResId;

	/**
	 * Name of this show direction.
	 */
	private String mName = "";

	/**
	 * Listeners -----------------------------------------------------------------------------------
	 */

	/**
	 * Arrays --------------------------------------------------------------------------------------
	 */

	/**
	 * Booleans ------------------------------------------------------------------------------------
	 */

	/**
	 * Constructors ================================================================================
	 *//**
	 * <p>
	 * Creates a new instance of ShowDirection with the given animations. The back-stack animation
	 * resource ids will be set to <code>0</code>.
	 * </p>
	 *
	 * @param inAnim  Animation resource id for a new incoming fragment.
	 * @param outAnim Animation resource id for the current outgoing fragment to the back stack.
	 */
	public ShowDirection(int inAnim, int outAnim) {
		this(inAnim, outAnim, 0, 0);
	}

	/**
	 * <p>
	 * Same as {@link #ShowDirection(int, int, int, int, String)} with empty name.
	 * </p>
	 */
	public ShowDirection(int inAnim, int outAnim, int inAnimBack, int outAnimBack) {
		this(inAnim, outAnim, inAnimBack, outAnimBack, "");
	}

	/**
	 * <p>
	 * Creates a new instance of ShowDirection with the given animations and name.
	 * </p>
	 *
	 * @param inAnim      Animation resource id for a new incoming fragment.
	 * @param outAnim     Animation resource id for the current outgoing fragment to the back stack.
	 * @param inAnimBack  Animation resource id for the incoming fragment from the back stack.
	 * @param outAnimBack Animation resource id for the current outgoing fragment.
	 * @param name        Name for this show direction. Can be obtained by {@link #name()}.
	 */
	public ShowDirection(int inAnim, int outAnim, int inAnimBack, int outAnimBack, String name) {
		this.mInAnimResId = inAnim;
		this.mOutAnimResId = outAnim;
		this.mInAnimBackResId = inAnimBack;
		this.mOutAnimBackResId = outAnimBack;
		this.mName = name;
	}

	/**
	 * <p>
	 * Called by {@link #CREATOR}.
	 * </p>
	 *
	 * @param input Parcelable source with saved data.
	 */
	protected ShowDirection(Parcel input) {
		this.mInAnimResId = input.readInt();
		this.mOutAnimResId = input.readInt();
		this.mInAnimBackResId = input.readInt();
		this.mOutAnimBackResId = input.readInt();
		this.mName = input.readString();
	}

	/**
	 * Methods =====================================================================================
	 */

	/**
	 * Public --------------------------------------------------------------------------------------
	 */

	/**
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(mInAnimResId);
		dest.writeInt(mOutAnimResId);
		dest.writeInt(mInAnimBackResId);
		dest.writeInt(mOutAnimBackResId);
		dest.writeString(mName);
	}

	/**
	 */
	@Override
	public int describeContents() {
		return 0;
	}

	/**
	 * Getters + Setters ---------------------------------------------------------------------------
	 */

	/**
	 * <p>
	 * Returns the name of this show direction.
	 * </p>
	 *
	 * @return Direction name.
	 */
	public String name() {
		return mName;
	}

	/**
	 * <p>
	 * Returns the id of the animation for a new incoming fragment.
	 * </p>
	 *
	 * @return Animation resource id.
	 */
	public int getInAnimResId() {
		return mInAnimResId;
	}

	/**
	 * <p>
	 * Returns the id of the animation for the current outgoing fragment to back stack.
	 * </p>
	 *
	 * @return Animation resource id.
	 */
	public int getOutAnimResId() {
		return mOutAnimResId;
	}

	/**
	 * <p>
	 * Returns the id of the animation for the current outgoing fragment.
	 * </p>
	 *
	 * @return Animation resource id.
	 */
	public int getOutAnimBackResId() {
		return mOutAnimBackResId;
	}

	/**
	 * <p>
	 * Returns the id of the animation for the incoming fragment from the back stack.
	 * </p>
	 *
	 * @return Animation resource id.
	 */
	public int getInAnimBackResId() {
		return mInAnimBackResId;
	}

	/**
	 * Protected -----------------------------------------------------------------------------------
	 */

	/**
	 * Private -------------------------------------------------------------------------------------
	 */

	/**
	 * Abstract methods ----------------------------------------------------------------------------
	 */

	/**
	 * Inner classes ===============================================================================
	 */

	/**
	 * Interface ===================================================================================
	 */
}
