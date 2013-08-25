/*
 * =================================================================================
 * Copyright (C) 2012 Martin Albedinsky [Wolf-ITechnologies]
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
package com.wit.and.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wit.and.fragment.R;
import com.wit.and.animation.BaseAnimation;

/**
 * <h4>Class Overview</h4>
 * <p>
 * Fragment with web view to handle displaying of web content in your fragments.
 * This fragment can handle HTML or URL content.
 * </p>
 * 
 * @author Martin Albedinsky
 * @see BaseFragment
 */
public class WebFragment extends Fragment {

	/**
	 * Constants =============================
	 */

    /**
     * Log TAG.
     */
    private static final String TAG = WebFragment.class.getSimpleName();

    /**
     * Indicates if debug private output trough log-cat is enabled.
     */
    private static final boolean DEBUG = false;

    /**
     * Indicates if logging for user output trough log-cat is enabled.
     */
    private static final boolean USER_LOG = true;

	/**
	 * Arguments identifiers.
	 */

	/**
	 */
	protected static final String ARGS_WEB_VIEW_CONTENT = "com.wit.and.fragment.WebFragment.Args.WebView.Content";
	protected static final String ARGS_WEB_VIEW_CONTENT_TYPE = "com.wit.and.fragment.WebFragment.Args.WebView.ContentType";

	private final BaseAnimation.OnAnimationEndListener HIDE_LOADING_VIEW_ANIM_LISTENER = new BaseAnimation.OnAnimationEndListener() {
		@Override
		public void onAnimationEnd(Animation animation) {
			mLoadingLayout.setVisibility(View.GONE);
		}
	};

	private final BaseAnimation.OnAnimationEndListener HIDE_WEB_VIEW_ANIM_LISTENER = new BaseAnimation.OnAnimationEndListener() {
		@Override
		public void onAnimationEnd(Animation animation) {
			mWebView.setVisibility(View.GONE);
		}
	};

	/**
	 * Enums =================================
	 */

	/**
	 * <p>
	 * public enum
	 * </p>
	 * <h5>ContentType</h5>
	 */
	public enum ContentType {
        /**
         * <p>
         * Indicates not resolved content type.
         * </p>
         */
        UNKNOWN,
		/**
		 * <p>
		 * Indicates url to load in the web view.
		 * </p>
		 */
		URL,
		/**
		 * <p>
		 * Indicates HTML content to load in the web view.
		 * </p>
		 */
		HTML;
	}

    /**
     * Static members ========================
     */

	/**
	 * Members ===============================
	 */

	/**
	 * Web view to load content in it.
	 */
	private WebView mWebView = null;

	/**
	 * Content to load in the web view.
	 */
	private String mContent = "";

	/**
	 * Content type to load in the web view.
	 */
	private ContentType mContentType = ContentType.HTML;

	/**
	 * AnimationsFactory to show/hide the web view when the content is loaded/loading.
	 */

	/**
	 */
	private Animation mShowWebViewAnim;
	private Animation mHideWebViewAnim;
	private Animation mShowLoadingViewAnim;
	private Animation mHideLoadingViewAnim;

	/**
	 * Layout with the progress bar and loading text view.
	 */
	private View mLoadingLayout;

	/**
	 * Progress bar to indicate that the content is loading into web view.
	 */
	private ProgressBar mProgressBar;

	/**
	 * Text view where the loading text is displayed.
	 */
	private TextView mLoadingTextView;

	/**
	 * Listeners -----------------------------
	 */

	private OnWebViewInitListener iListener = null;

	/**
	 * Arrays --------------------------------
	 */

	/**
	 * Booleans ------------------------------
	 */

	/**
	 * Constructors ==========================
	 */

	/**
	 * Methods ===============================
	 */

	/**
	 * Public --------------------------------
	 */

	/**
	 * <p>
	 * Returns new instance of the web view fragment.
	 * </p>
	 * 
	 * @param content
	 *            Content to load in the web view.
	 * @return
	 */
	public static Fragment newInstance(String content) {
		// Initialize web view fragment.
		Fragment fragment = new WebFragment();

		// Set arguments.
		Bundle args = new Bundle();
		args.putString(ARGS_WEB_VIEW_CONTENT, content);
		fragment.setArguments(args);

		//
		return fragment;
	}

	/**
	 * 
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		if (activity != null && activity instanceof OnWebViewInitListener) {
			this.iListener = (OnWebViewInitListener) activity;
		}
	}

	/**
	 * 
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Check saved instance state.
		if (savedInstanceState != null) {
			this.mContent = savedInstanceState.getString(ARGS_WEB_VIEW_CONTENT);
			this.mContentType = ContentType.values()[savedInstanceState.getInt(ARGS_WEB_VIEW_CONTENT_TYPE)];
		}
		// Check arguments.
		else {
			Bundle args = this.getArguments();
			if (args != null) {
				if (args.containsKey(ARGS_WEB_VIEW_CONTENT)) {
                    this.resolveContentType(args.getString(ARGS_WEB_VIEW_CONTENT));
				}
			}
		}

		// Load animations.
		Activity context = this.getActivity();
		if (context != null) {
            // FIXME:
			//this.mShowWebViewAnim = Animator.getAnimation(Animator.Anim.SLIDE_LEFT_FROM_RIGHT, context);
			//this.mHideWebViewAnim = Animator.getAnimation(Animator.Anim.SLIDE_RIGHT_FROM_RIGHT, context);
			//this.mShowLoadingViewAnim = Animator.getAnimation(Animator.Anim.SLIDE_RIGHT_FROM_LEFT, context);
			//this.mHideLoadingViewAnim = Animator.getAnimation(Animator.Anim.SLIDE_LEFT_FROM_LEFT, context);
		}
	}

	/**
	 * 
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		// Check web view and save its state.
		if (this.mWebView != null) {
			this.mWebView.saveState(outState);
		}

		// Save content and content type.
		outState.putString(ARGS_WEB_VIEW_CONTENT, this.mContent);
		outState.putInt(ARGS_WEB_VIEW_CONTENT_TYPE, this.mContentType.ordinal());
	}

	/**
	 * 
	 */
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate fragment view.
		View view = inflater.inflate(R.layout.fragment_webview, container, false);

		// Check inflated fragment view.
		if (view == null) {
			return super.onCreateView(inflater, container, savedInstanceState);
		} else {
			// Initialize web view from fragment view.
			this.mWebView = (WebView) view.findViewById(R.id.WebView);

			// Set web view parameters.
			if (this.mWebView != null) {
				// Set scroll bars style.
				this.mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

				// Enable java script.
				this.mWebView.getSettings().setJavaScriptEnabled(true);

				// Set custom WebViewClient and WebChromeClient.
				WebViewClient client = onGetWebViewClient();
				if (client != null) {
					this.mWebView.setWebViewClient(client);
				}
				WebChromeClient chromeClient = onGetWebChromeClient();
				if (chromeClient != null) {
					this.mWebView.setWebChromeClient(chromeClient);
				}

				// Initialize layout objects.
				this.mLoadingLayout = view.findViewById(R.id.Fragment_Layout_Loading);
				this.mProgressBar = (ProgressBar) view.findViewById(R.id.Fragment_ProgressBar);
				this.mLoadingTextView = (TextView) view.findViewById(R.id.Fragment_TextView_Loading);

				if (mProgressBar != null && mProgressBar.getIndeterminateDrawable() != null) {
					mProgressBar.getIndeterminateDrawable().setColorFilter(this.getResources().getColor(R.color.And_Dialog_ProgressBar),
							Mode.MULTIPLY);
				}

				// Invoke listener callback.
				if (iListener != null) {
					iListener.onWebViewInitialized(this.mWebView);
				}
			}

			// Return custom inflated fragment view.
			return view;
		}
	}

	/**
	 * <p>
	 * Called when the fragment will be soon displayed to the user. Here is also
	 * invoked loading content into web view.
	 * </p>
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (DEBUG)
			Log.d(TAG, "onActivityCreated(contentType = " + this.mContentType + ", content = " + this.mContent + ")");

		// Restore web view state.
		if (savedInstanceState != null && this.mWebView != null) {
			this.hideLoading(false);
			mWebView.restoreState(savedInstanceState);
		} else {
			// Load content.
			switch (mContentType) {
			case URL:
				onLoadURL(mContent);
				break;
			case HTML:
				onLoadData(mContent);
				break;
			}
		}
	}


    /**
     * <p>
     * </p>
     *
     * @param content
     * @return
     */
    public final boolean loadContent(String content) {
        this.resolveContentType(content);
        boolean loaded = false;

        switch (mContentType) {
            case URL:
                loaded = onLoadURL(content);
                break;
            case HTML:
                loaded = onLoadData(content);
                break;
            case UNKNOWN:
                break;
        }
        return loaded;
    }

	/**
	 * Getters + Setters ---------------------
	 */

	/**
	 * <p>
	 * Returns web view content.
	 * </p>
	 * 
	 * @return
	 */
	public final String getContent() {
		return mContent;
	}

	/**
	 * <p>
	 * Returns web view content type.
	 * </p>
	 * 
	 * @return
	 */
	public final ContentType getContentType() {
		return mContentType;
	}

	/**
	 * <p>
	 * Returns web view used in this web view fragment.
	 * </p>
	 * 
	 * @return
	 */
	public final WebView getWebView() {
		return mWebView;
	}

	/**
	 * 
	 * @return
	 */
	public final TextView getLoadingTextView() {
		return mLoadingTextView;
	}

	/**
	 * 
	 * @return
	 */
	public final ProgressBar getProgressBar() {
		return mProgressBar;
	}

	/**
	 * <p>
	 * Invoked when the back button is pressed in the parent activity. This must
	 * be connected in the <code>onBackPressed()</code> in the parent activity.
	 * </p>
	 * 
	 * @return True if the web view consumed on back pressed event otherwise
	 *         false.
	 */
	public boolean onBackPressed() {
		// Default don't consume on back pressed event.
		boolean consume = false;

		// Check web view.
		if (this.mWebView != null) {
			if (this.mWebView.canGoBack()) {
				this.mWebView.goBack();
				consume = true;
			}
		}

		return consume;
	}

	/**
	 * Protected -----------------------------
	 */

	/**
	 * <p>
	 * Invoked when the web view is being initialized. Return here your custom
	 * instance of the web view client to handle specific errors.
	 * </p>
	 * 
	 * @return
	 */
	protected WebViewClient onGetWebViewClient() {
		return new WebViewClient() {

			@Override
			public void onPageFinished(WebView view, String url) {
				if (USER_LOG)
					Log.i(TAG, "onPageFinished(view, url = " + url + ")");

				super.onPageFinished(view, url);
			}

			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				if (USER_LOG)
					Log.i(TAG, "onReceivedError(view, errorCode = " + errorCode + ", description = " + description + ", failingUrl = "
							+ failingUrl + ")");

				super.onReceivedError(view, errorCode, description, failingUrl);
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				if (USER_LOG)
					Log.i(TAG, "onPageStarted(view, url = " + url + ")");

				super.onPageStarted(view, url, favicon);
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if (USER_LOG)
					Log.i(TAG, "shouldOverrideUrlLoading(view, url = " + url + ")");

				return super.shouldOverrideUrlLoading(view, url);
			}

		};
	}

	/**
	 * <p>
	 * Invoked when the web view is being initialized. Return here your custom
	 * instance of the web chrome client to handle for example progress changed.
	 * </p>
	 * <p>
	 * You should hide from here loading layout when the progress reached
	 * <code>100</code>.
	 * </p>
	 * 
	 * @return
	 */
	protected WebChromeClient onGetWebChromeClient() {
		return new WebChromeClient() {

			@Override
			public void onProgressChanged(WebView view, int progress) {
				if (progress == 100) {
					hideLoading(true);
				}
			}
		};
	}

	/**
	 * <p>
	 * Loads the given URL into web view.
	 * </p>
	 * <p>
	 * <b>Note: </b>If the fragment view is not initialized yet, the given URL
	 * will be saved and loaded later.
	 * </p>
	 * 
	 * @param url
	 *            URL to load in the web view.
	 * @return True if the URL was successfully loaded otherwise false.
	 */
	protected boolean onLoadURL(String url) {
		// Default.
		boolean success = false;

		if (DEBUG)
			Log.d(TAG, "onLoadURL URL \"" + url + "\"");

		// Check web view and URL to load.
		if (this.mWebView != null && url != null && url.length() != 0) {
			this.showLoading(true);
			this.mWebView.loadUrl(url);
			success = true;

			if (USER_LOG)
				Log.i(TAG, "Loaded URL \"" + url + "\" into web view.");
		}

		return success;
	}

	/**
	 * <p>
	 * Loads the given data into web view.
	 * </p>
	 * <p>
	 * <b>Note: </b>If the fragment view is not initialized yet, the given data
	 * will be saved and loaded later.
	 * </p>
	 * 
	 * @param data
	 *            HTML data to load in the web view.
	 * @return True if the data were successfully loaded otherwise false.
	 */
	protected boolean onLoadData(String data) {
		// Default.
		boolean success = false;

		// Check web view and data to load.
		if (this.mWebView != null && data != null && data.length() != 0) {
			this.showLoading(true);
			this.mWebView.loadData(data, "text/html", "utf-8");
			success = true;

			if (USER_LOG)
				Log.i(TAG, "Loaded data \"" + data + "\" into web view.");
		}

		return success;
	}

	/**
	 * <p>
	 * Hides the progress bar when the web view content was loaded.
	 * </p>
	 * 
	 * @param animated
	 *            If set to true the loading layout will be animated.
	 */
	protected final void hideLoading(boolean animated) {
		if (this.mLoadingLayout == null || this.mWebView == null)
			return;

		// Check for already visible or animated web view.
		if (this.mWebView.getVisibility() == View.VISIBLE || this.mWebView.getAnimation() != null)
			return;

		if (animated) {
			// Start animation.
            // FIXME:
			//Animators.animator().animate(this.mLoadingLayout, this.mHideLoadingViewAnim, HIDE_LOADING_VIEW_ANIM_LISTENER);
			//Animators.animator().animate(this.mWebView, this.mShowWebViewAnim);
		} else {
			this.mWebView.setVisibility(View.VISIBLE);
			this.mLoadingLayout.setVisibility(View.GONE);
		}
	}

	/**
	 * <p>
	 * Shows the progress bar while the web view content is being loaded.
	 * </p>
	 * 
	 * @param animated
	 *            If set to true the loading layout will be animated.
	 */
	protected final void showLoading(boolean animated) {
		if (this.mLoadingLayout == null || this.mWebView == null)
			return;

		// Check for already hided or animated web view.
		if (this.mWebView.getVisibility() == View.GONE || this.mWebView.getAnimation() != null)
			return;

		if (animated) {
			// Start animation.
            // FIXME:
			//Animators.animator().animate(this.mWebView, this.mHideWebViewAnim, HIDE_WEB_VIEW_ANIM_LISTENER);
			//Animators.animator().animate(this.mLoadingLayout, this.mShowLoadingViewAnim);
		} else {
			this.mLoadingLayout.setVisibility(View.VISIBLE);
			this.mWebView.setVisibility(View.GONE);
		}
	}

	/**
	 * Private -------------------------------
	 */

    /**
     * Resolves type of the content depends on the given content.
     *
     * @param content
     */
    private void resolveContentType(String content) {
        if (content != null && content.length() > 0) {
            if (Patterns.WEB_URL.matcher("").reset(content).matches()) {
                this.mContentType = ContentType.URL;
            } else {
                this.mContentType = ContentType.HTML;
            }
            this.mContent = content;
        } else {
            if (USER_LOG)
                Log.e(TAG, "Can't resolve type of content(" + content + ")");
        }
    }

	/**
	 * Abstract methods ----------------------
	 */

	/**
	 * Inner classes =========================
	 */

	/**
	 * Interface =============================
	 */

	public static interface OnWebViewInitListener {
		public void onWebViewInitialized(WebView webView);
	}
}
