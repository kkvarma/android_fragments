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
import android.support.annotation.NonNull;

import com.wit.android.support.fragment.R;

/**
 * <h4>Class Overview</h4>
 * todo: description
 * <h6>Provided Transitions</h6>
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
 * <ul>
 * <li>{@link FragmentTransition#SCALE_IN_AND_SLIDE_TO_LEFT}</li>
 * <li>{@link FragmentTransition#SCALE_IN_AND_SLIDE_TO_RIGHT}</li>
 * <li>{@link FragmentTransition#SCALE_IN_AND_SLIDE_TO_TOP}</li>
 * <li>{@link FragmentTransition#SCALE_IN_AND_SLIDE_TO_BOTTOM}</li>
 * <li>{@link FragmentTransition#SLIDE_TO_RIGHT_AND_SCALE_OUT}</li>
 * <li>{@link FragmentTransition#SLIDE_TO_LEFT_AND_SCALE_OUT}</li>
 * <li>{@link FragmentTransition#SLIDE_TO_BOTTOM_AND_SCALE_OUT}</li>
 * <li>{@link FragmentTransition#SLIDE_TO_TOP_AND_SCALE_OUT}</li>
 * </ul>
 *
 * @author Martin Albedinsky
 */
public class FragmentTransition implements Parcelable {

	/**
	 * Interface ===================================================================================
	 */

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * Log TAG.
	 */
	// private static final String TAG = "FragmentTransition";

	/**
	 * Flag indicating whether the debug output trough log-cat is enabled or not.
	 */
	// private static final boolean DEBUG_ENABLED = true;

	/**
	 * Flag indicating whether the output trough log-cat is enabled or not.
	 */
	// private static final boolean LOG_ENABLED = true;

	/**
	 * Static members ==============================================================================
	 */

	/**
	 * Creator used to create an instance or array of instances of FragmentTransition from {@link android.os.Parcel}.
	 */
	public static final Creator<FragmentTransition> CREATOR = new Creator<FragmentTransition>() {
		/**
		 */
		@Override
		public FragmentTransition createFromParcel(Parcel source) {
			return new FragmentTransition(source);
		}

		/**
		 */
		@Override
		public FragmentTransition[] newArray(int size) {
			return new FragmentTransition[size];
		}
	};

	/**
	 * Use this to show a new incoming fragment which will replace the current fragment without any
	 * animation.
	 */
	public static final FragmentTransition NONE = new FragmentTransition(0, 0, 0, 0, "NONE");

	/**
	 * Transition to fade a new incoming fragment in and outgoing (the current one) will be faded out.
	 * <h6>Powered by animations:</h6>
	 * <ul>
	 * <li><b>Incoming:</b> {@link R.anim#fragment_fade_in}</li>
	 * <li><b>Outgoing:</b> {@link R.anim#fragment_fade_out}</li>
	 * <li><b>Incoming (back-stack):</b> {@link R.anim#fragment_fade_in_back}</li>
	 * <li><b>Outgoing (back-stack):</b> {@link R.anim#fragment_fade_out_back}</li>
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
	 * Transition to slide a new incoming fragment from the left and outgoing (the current one) will
	 * be slided to the right.
	 * <h6>Powered by animations:</h6>
	 * <ul>
	 * <li><b>Incoming:</b> {@link R.anim#fragment_slide_in_right}</li>
	 * <li><b>Outgoing:</b> {@link R.anim#fragment_slide_out_right}</li>
	 * <li><b>Incoming (back-stack):</b> {@link R.anim#fragment_slide_in_left_back}</li>
	 * <li><b>Outgoing (back-stack):</b> {@link R.anim#fragment_slide_out_left_back}</li>
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
	 * Transition to slide a new incoming fragment from the right and outgoing (the current one) will
	 * be slided to the left.
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
	 * Transition to slide a new incoming fragment from the top and outgoing (the current one) will
	 * be slided to the bottom.
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
	 * Transition to slide a new incoming fragment from the bottom and outgoing (the current one) will
	 * be slided to the top.
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
	 * Transition to scale (with fade) a new incoming fragment from the background and outgoing
	 * (the current one) will be slided to the left.
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
	 * Transition to scale (with fade) a new incoming fragment from the background and outgoing (the
	 * current one) will be slided to the right.
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
	 * Transition to scale (with fade) a new incoming fragment from the background and outgoing (the
	 * current one) will be slided to the top.
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
	 * Transition to scale (with fade) a new incoming fragment from the background and outgoing (the
	 * current one) will be slided to the bottom.
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
	 * Transition to slide a new incoming fragment from the left and outgoing (the current one) will
	 * be scaled (with fade) to the background.
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
	 * Transition to slide a new incoming fragment from the right and outgoing (the current one) will
	 * be scaled (with fade) to the background.
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
	 * Transition to slide a new incoming fragment from the top and outgoing (the current one) will
	 * be scaled (with fade) to the background.
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
	 * Transition to slide a new incoming fragment from the bottom and outgoing (the current one) will
	 * be scaled (with fade) to the background.
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
	private final int mInAnimResId, mOutAnimResId, mInAnimBackResId, mOutAnimBackResId;

	/**
	 * Name of this transition.
	 */
	private final String mName;

	/**
	 * Constructors ================================================================================
	 */

	/**
	 * Same as {@link #FragmentTransition(int, int, int, int)} with back-stack animations set to
	 * <code>0</code>.
	 */
	public FragmentTransition(int inAnim, int outAnim) {
		this(inAnim, outAnim, 0, 0);
	}

	/**
	 * Same as {@link #FragmentTransition(int, int, int, int, String)} with empty name.
	 */
	public FragmentTransition(int inAnim, int outAnim, int inAnimBack, int outAnimBack) {
		this(inAnim, outAnim, inAnimBack, outAnimBack, "");
	}

	/**
	 * Creates a new instance of FragmentTransition with the given animations and name.
	 *
	 * @param inAnim      A resource id of the animation for an incoming fragment.
	 * @param outAnim     A resource id of the animation for an outgoing fragment to be added to the
	 *                    back stack or to be destroyed and replaced by the incoming one.
	 * @param inAnimBack  A resource id of the animation for an incoming fragment to be showed from
	 *                    the back stack.
	 * @param outAnimBack A resource id of the animation for an outgoing fragment to be destroyed and
	 *                    replaced by the incoming one.
	 * @param name        The name of this transition.
	 */
	public FragmentTransition(int inAnim, int outAnim, int inAnimBack, int outAnimBack, @NonNull String name) {
		this.mInAnimResId = inAnim;
		this.mOutAnimResId = outAnim;
		this.mInAnimBackResId = inAnimBack;
		this.mOutAnimBackResId = outAnimBack;
		this.mName = name;
	}

	/**
	 * Called form {@link #CREATOR} to create an instance of FragmentTransition form the given parcel
	 * <var>source</var>.
	 *
	 * @param source Parcel with data for a new instance.
	 */
	protected FragmentTransition(@NonNull Parcel source) {
		this.mInAnimResId = source.readInt();
		this.mOutAnimResId = source.readInt();
		this.mInAnimBackResId = source.readInt();
		this.mOutAnimBackResId = source.readInt();
		this.mName = source.readString();
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
	public void writeToParcel(@NonNull Parcel dest, int flags) {
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
	 * Returns the name of this transition.
	 *
	 * @return Transition name.
	 */
	@NonNull
	public String name() {
		return mName;
	}

	/**
	 * Returns a resource id of the animation for a new incoming fragment.
	 *
	 * @return Animation resource id.
	 */
	public int getInAnimResId() {
		return mInAnimResId;
	}

	/**
	 * Returns a resource id of the animation for the current outgoing fragment to be added to the
	 * back stack or to be destroyed and replaced by the incoming one.
	 *
	 * @return Animation resource id.
	 */
	public int getOutAnimResId() {
		return mOutAnimResId;
	}

	/**
	 * Returns a resource id of the animation for the incoming fragment to be showed from the back
	 * stack.
	 *
	 * @return Animation resource id.
	 */
	public int getInAnimBackResId() {
		return mInAnimBackResId;
	}

	/**
	 * Returns a resource id of the animation for the current outgoing fragment to be destroyed and
	 * replaced by the incoming one.
	 *
	 * @return Animation resource id.
	 */
	public int getOutAnimBackResId() {
		return mOutAnimBackResId;
	}

	/**
	 * Protected -----------------------------------------------------------------------------------
	 */

	/**
	 * Private -------------------------------------------------------------------------------------
	 */

	/**
	 * Inner classes ===============================================================================
	 */
}
