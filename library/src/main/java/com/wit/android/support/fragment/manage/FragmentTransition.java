/*
 * =================================================================================================
 *                Copyright (C) 2013 - 2014 Martin Albedinsky [Wolf-ITechnologies]
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
package com.wit.android.support.fragment.manage;

import android.os.Parcel;
import android.os.Parcelable;

import com.wit.android.support.fragment.R;

/**
 * <h4>Class Overview</h4>
 * <p>
 * Description.
 * </p>
 * <h6>Provided Directions</h6>
 * <b>Alpha:</b>
 * <ul>
 * <li>{@link FragmentTransition#FADE_IN}</li>
 * </ul>
 * <b>Slide:</b>
 * <ul>
 * <li>{@link FragmentTransition#SLIDE_TO_RIGHT}</li>
 * <li>{@link FragmentTransition#SLIDE_TO_LEFT}</li>
 * <li>{@link FragmentTransition#SLIDE_TO_BOTTOM}</li>
 * <li>{@link FragmentTransition#SLIDE_TO_TOP}</li>
 * </ul>
 * <b>Scale & Slide:</b>
 * <li>{@link FragmentTransition#SCALE_IN_AND_SLIDE_TO_LEFT}</li>
 * <li>{@link FragmentTransition#SCALE_IN_AND_SLIDE_TO_RIGHT}</li>
 * <li>{@link FragmentTransition#SCALE_IN_AND_SLIDE_TO_TOP}</li>
 * <li>{@link FragmentTransition#SCALE_IN_AND_SLIDE_TO_BOTTOM}</li>
 * <li>{@link FragmentTransition#SLIDE_TO_RIGHT_AND_SCALE_OUT}</li>
 * <li>{@link FragmentTransition#SLIDE_TO_LEFT_AND_SCALE_OUT}</li>
 * <li>{@link FragmentTransition#SLIDE_TO_BOTTOM_AND_SCALE_OUT}</li>
 * <li>{@link FragmentTransition#SLIDE_TO_TOP_AND_SCALE_OUT}</li>
 * <ul>
 * </ul>
 * <b>Flip:</b>
 * <ul>
 * </ul>
 *
 * @author Martin Albedinsky
 */
public class FragmentTransition implements Parcelable {

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * Log TAG.
	 */
	// private static final String TAG = FragmentTransition.class.getSimpleName();

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
	public static final Creator<FragmentTransition> CREATOR = new Creator<FragmentTransition>() {
		@Override
		public FragmentTransition createFromParcel(Parcel source) {
			return new FragmentTransition(source);
		}

		@Override
		public FragmentTransition[] newArray(int size) {
			return new FragmentTransition[size];
		}
	};

	/**
	 * <p>
	 * Use this to show new incoming fragment which will replace the current fragment without any
	 * animation.
	 * </p>
	 */
	public static final FragmentTransition NONE = new FragmentTransition(0, 0, 0, 0, "NONE");

	/**
	 * <p>
	 * Use this direction to fade a new incoming fragment in and outgoing (the current one) will be
	 * faded out.
	 * </p>
	 * <h6>Powered by animations:</h6>
	 * <ul>
	 * <li><b>Incoming:</b> {@link com.wit.android.support.fragment.R.anim#fragment_fade_in}</li>
	 * <li><b>Outgoing:</b> {@link com.wit.android.support.fragment.R.anim#fragment_fade_out}</li>
	 * <li><b>Incoming (back-stack):</b> {@link com.wit.android.support.fragment.R.anim#fragment_fade_in_back}</li>
	 * <li><b>Outgoing (back-stack):</b> {@link com.wit.android.support.fragment.R.anim#fragment_fade_out_back}</li>
	 * </ul>
	 */
	public static final FragmentTransition FADE_IN = new FragmentTransition(
			// Incoming animation.
			R.anim.fragment_fade_in,
			// Outgoing animation.
			R.anim.fragment_fade_out,
			// Incoming back-stack animation.
			R.anim.fragment_fade_in_back,
			// Outgoing back-stack animation.
			R.anim.fragment_fade_out_back,
			"FADE_IN"
	);

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
	public static final FragmentTransition SLIDE_TO_RIGHT = new FragmentTransition(
			// Incoming animation.
			R.anim.fragment_slide_in_right,
			// Outgoing animation.
			R.anim.fragment_slide_out_right,
			// Incoming back-stack animation.
			R.anim.fragment_slide_in_left_back,
			// Outgoing back-stack animation.
			R.anim.fragment_slide_out_left_back,
			"SLIDE_TO_RIGHT"
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
	public static final FragmentTransition SLIDE_TO_LEFT = new FragmentTransition(
			// Incoming animation.
			R.anim.fragment_slide_in_left,
			// Outgoing animation.
			R.anim.fragment_slide_out_left,
			// Incoming back-stack animation.
			R.anim.fragment_slide_in_right_back,
			// Outgoing back-stack animation.
			R.anim.fragment_slide_out_right_back,
			"SLIDE_TO_LEFT"
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
	public static final FragmentTransition SLIDE_TO_BOTTOM = new FragmentTransition(
			// Incoming animation.
			R.anim.fragment_slide_in_bottom,
			// Outgoing animation.
			R.anim.fragment_slide_out_bottom,
			// Incoming back-stack animation.
			R.anim.fragment_slide_in_top_back,
			// Outgoing back-stack animation.
			R.anim.fragment_slide_out_top_back,
			"SLIDE_TO_BOTTOM"
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
	public static final FragmentTransition SLIDE_TO_TOP = new FragmentTransition(
			// Incoming animation.
			R.anim.fragment_slide_in_top,
			// Outgoing animation.
			R.anim.fragment_slide_out_top,
			// Incoming back-stack animation.
			R.anim.fragment_slide_in_bottom_back,
			// Outgoing back-stack animation.
			R.anim.fragment_slide_out_bottom_back,
			"SLIDE_TO_TOP"
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
	public static final FragmentTransition SCALE_IN_AND_SLIDE_TO_LEFT = new FragmentTransition(
			// Incoming animation.
			R.anim.fragment_scale_in,
			// Outgoing animation.
			R.anim.fragment_slide_out_left,
			// Incoming back-stack animation.
			R.anim.fragment_slide_in_right_back,
			// Outgoing back-stack animation.
			R.anim.fragment_scale_out_back,
			"SCALE_IN_AND_SLIDE_TO_LEFT"
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
	public static final FragmentTransition SCALE_IN_AND_SLIDE_TO_RIGHT = new FragmentTransition(
			// Incoming animation.
			R.anim.fragment_scale_in,
			// Outgoing animation.
			R.anim.fragment_slide_out_right,
			// Incoming back-stack animation.
			R.anim.fragment_slide_in_left_back,
			// Outgoing back-stack animation.
			R.anim.fragment_scale_out_back,
			"SCALE_IN_AND_SLIDE_TO_RIGHT"
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
	public static final FragmentTransition SCALE_IN_AND_SLIDE_TO_TOP = new FragmentTransition(
			// Incoming animation.
			R.anim.fragment_scale_in,
			// Outgoing animation.
			R.anim.fragment_slide_out_top,
			// Incoming back-stack animation.
			R.anim.fragment_slide_in_bottom_back,
			// Outgoing back-stack animation.
			R.anim.fragment_scale_out_back,
			"SCALE_IN_AND_SLIDE_TO_TOP"
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
	public static final FragmentTransition SCALE_IN_AND_SLIDE_TO_BOTTOM = new FragmentTransition(
			// Incoming animation.
			R.anim.fragment_scale_in,
			// Outgoing animation.
			R.anim.fragment_slide_out_bottom,
			// Incoming back-stack animation.
			R.anim.fragment_slide_in_top_back,
			// Outgoing back-stack animation.
			R.anim.fragment_scale_out_back,
			"SCALE_IN_AND_SLIDE_TO_BOTTOM"
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
	public static final FragmentTransition SLIDE_TO_RIGHT_AND_SCALE_OUT = new FragmentTransition(
			// Incoming animation.
			R.anim.fragment_slide_in_right,
			// Outgoing animation.
			R.anim.fragment_scale_out,
			// Incoming back-stack animation.
			R.anim.fragment_scale_in_back,
			// Outgoing back-stack animation.
			R.anim.fragment_slide_out_left_back,
			"SLIDE_TO_RIGHT_AND_SCALE_OUT"
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
	public static final FragmentTransition SLIDE_TO_LEFT_AND_SCALE_OUT = new FragmentTransition(
			// Incoming animation.
			R.anim.fragment_slide_in_left,
			// Outgoing animation.
			R.anim.fragment_scale_out,
			// Incoming back-stack animation.
			R.anim.fragment_scale_in_back,
			// Outgoing back-stack animation.
			R.anim.fragment_slide_out_right_back,
			"SLIDE_TO_LEFT_AND_SCALE_OUT"
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
	public static final FragmentTransition SLIDE_TO_BOTTOM_AND_SCALE_OUT = new FragmentTransition(
			// Incoming animation.
			R.anim.fragment_slide_in_bottom,
			// Outgoing animation.
			R.anim.fragment_scale_out,
			// Incoming back-stack animation.
			R.anim.fragment_scale_in_back,
			// Outgoing back-stack animation.
			R.anim.fragment_slide_out_top_back,
			"SLIDE_TO_BOTTOM_AND_SCALE_OUT"
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
	public static final FragmentTransition SLIDE_TO_TOP_AND_SCALE_OUT = new FragmentTransition(
			// Incoming animation.
			R.anim.fragment_slide_in_top,
			// Outgoing animation.
			R.anim.fragment_scale_out,
			// Incoming back-stack animation.
			R.anim.fragment_scale_in_back,
			// Outgoing back-stack animation.
			R.anim.fragment_slide_out_bottom_back,
			"SLIDE_TO_TOP_AND_SCALE_OUT"
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
	 * Creates a new instance of FragmentTransition with the given animations. The back-stack animation
	 * resource ids will be set to <code>0</code>.
	 * </p>
	 *
	 * @param inAnim  Animation resource id for a new incoming fragment.
	 * @param outAnim Animation resource id for the current outgoing fragment to the back stack.
	 */
	public FragmentTransition(int inAnim, int outAnim) {
		this(inAnim, outAnim, 0, 0);
	}

	/**
	 * <p>
	 * Same as {@link #FragmentTransition(int, int, int, int, String)} with empty name.
	 * </p>
	 */
	public FragmentTransition(int inAnim, int outAnim, int inAnimBack, int outAnimBack) {
		this(inAnim, outAnim, inAnimBack, outAnimBack, "");
	}

	/**
	 * <p>
	 * Creates a new instance of FragmentTransition with the given animations and name.
	 * </p>
	 *
	 * @param inAnim      Animation resource id for a new incoming fragment.
	 * @param outAnim     Animation resource id for the current outgoing fragment to the back stack.
	 * @param inAnimBack  Animation resource id for the incoming fragment from the back stack.
	 * @param outAnimBack Animation resource id for the current outgoing fragment.
	 * @param name        Name for this show direction. Can be obtained by {@link #name()}.
	 */
	public FragmentTransition(int inAnim, int outAnim, int inAnimBack, int outAnimBack, String name) {
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
	protected FragmentTransition(Parcel input) {
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
