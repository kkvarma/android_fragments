/*
 * =================================================================================
 * Copyright (C) 2013 Martin Albedinsky [Wolf-ITechnologies]
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
package com.wit.android.fragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.regex.Pattern;

/**
 * <h4>Class Overview</h4>
 * <p>
 * </p>
 *
 * @author Martin Albedinsky
 * @see com.wit.android.fragment.WebFragment.OnWebContentLoadingListener
 */
public class WebFragment extends BaseFragment {

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * Log TAG.
	 */
	private static final String TAG = WebFragment.class.getSimpleName();

	/**
	 * Flag indicating whether the debug output trough log-cat is enabled or not.
	 */
	// private static final boolean DEBUG = false;

	/**
	 * Flag indicating whether the output for user trough log-cat is enabled or not.
	 */
	private static final boolean USER_LOG = true;

	/**
	 * Bundle identifiers.
	 */
	/**
	 */
	private static final String BUNDLE_WEB_VIEW_CONTENT = "com.wit.android.fragment.WebFragment.Bundle.Content";

	private static final String BUNDLE_WEB_VIEW_CONTENT_TYPE = "com.wit.android.fragment.WebFragment.Bundle.ContentType";

	private static final String BUNDLE_WEB_VIEW_JAVA_SCRIPT_ENABLED = "com.wit.android.fragment.WebFragment.Bundle.JavaScriptEnabled";

	/**
	 * Source code copied from Android SDK [START] =================================================
	 * to preserve min library SDK version at 7.
	 */
	private static final String GOOD_IRI_CHAR = "a-zA-Z0-9\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF";

	/**
	 * Regular expression to match all IANA top-level domains for WEB_URL.
	 * List accurate as of 2011/07/18.  List taken from:
	 * http://data.iana.org/TLD/tlds-alpha-by-domain.txt
	 * This pattern is auto-generated by frameworks/ex/common/tools/make-iana-tld-pattern.py
	 */
	private static final String TOP_LEVEL_DOMAIN_STR_FOR_WEB_URL =
			"(?:"
					+ "(?:aero|arpa|asia|a[cdefgilmnoqrstuwxz])"
					+ "|(?:biz|b[abdefghijmnorstvwyz])"
					+ "|(?:cat|com|coop|c[acdfghiklmnoruvxyz])"
					+ "|d[ejkmoz]"
					+ "|(?:edu|e[cegrstu])"
					+ "|f[ijkmor]"
					+ "|(?:gov|g[abdefghilmnpqrstuwy])"
					+ "|h[kmnrtu]"
					+ "|(?:info|int|i[delmnoqrst])"
					+ "|(?:jobs|j[emop])"
					+ "|k[eghimnprwyz]"
					+ "|l[abcikrstuvy]"
					+ "|(?:mil|mobi|museum|m[acdeghklmnopqrstuvwxyz])"
					+ "|(?:name|net|n[acefgilopruz])"
					+ "|(?:org|om)"
					+ "|(?:pro|p[aefghklmnrstwy])"
					+ "|qa"
					+ "|r[eosuw]"
					+ "|s[abcdeghijklmnortuvyz]"
					+ "|(?:tel|travel|t[cdfghjklmnoprtvwz])"
					+ "|u[agksyz]"
					+ "|v[aceginu]"
					+ "|w[fs]"
					+ "|(?:\u03b4\u03bf\u03ba\u03b9\u03bc\u03ae|\u0438\u0441\u043f\u044b\u0442\u0430\u043d\u0438\u0435|\u0440\u0444|\u0441\u0440\u0431|\u05d8\u05e2\u05e1\u05d8|\u0622\u0632\u0645\u0627\u06cc\u0634\u06cc|\u0625\u062e\u062a\u0628\u0627\u0631|\u0627\u0644\u0627\u0631\u062f\u0646|\u0627\u0644\u062c\u0632\u0627\u0626\u0631|\u0627\u0644\u0633\u0639\u0648\u062f\u064a\u0629|\u0627\u0644\u0645\u063a\u0631\u0628|\u0627\u0645\u0627\u0631\u0627\u062a|\u0628\u06be\u0627\u0631\u062a|\u062a\u0648\u0646\u0633|\u0633\u0648\u0631\u064a\u0629|\u0641\u0644\u0633\u0637\u064a\u0646|\u0642\u0637\u0631|\u0645\u0635\u0631|\u092a\u0930\u0940\u0915\u094d\u0937\u093e|\u092d\u093e\u0930\u0924|\u09ad\u09be\u09b0\u09a4|\u0a2d\u0a3e\u0a30\u0a24|\u0aad\u0abe\u0ab0\u0aa4|\u0b87\u0ba8\u0bcd\u0ba4\u0bbf\u0baf\u0bbe|\u0b87\u0bb2\u0b99\u0bcd\u0b95\u0bc8|\u0b9a\u0bbf\u0b99\u0bcd\u0b95\u0baa\u0bcd\u0baa\u0bc2\u0bb0\u0bcd|\u0baa\u0bb0\u0bbf\u0b9f\u0bcd\u0b9a\u0bc8|\u0c2d\u0c3e\u0c30\u0c24\u0c4d|\u0dbd\u0d82\u0d9a\u0dcf|\u0e44\u0e17\u0e22|\u30c6\u30b9\u30c8|\u4e2d\u56fd|\u4e2d\u570b|\u53f0\u6e7e|\u53f0\u7063|\u65b0\u52a0\u5761|\u6d4b\u8bd5|\u6e2c\u8a66|\u9999\u6e2f|\ud14c\uc2a4\ud2b8|\ud55c\uad6d|xn\\-\\-0zwm56d|xn\\-\\-11b5bs3a9aj6g|xn\\-\\-3e0b707e|xn\\-\\-45brj9c|xn\\-\\-80akhbyknj4f|xn\\-\\-90a3ac|xn\\-\\-9t4b11yi5a|xn\\-\\-clchc0ea0b2g2a9gcd|xn\\-\\-deba0ad|xn\\-\\-fiqs8s|xn\\-\\-fiqz9s|xn\\-\\-fpcrj9c3d|xn\\-\\-fzc2c9e2c|xn\\-\\-g6w251d|xn\\-\\-gecrj9c|xn\\-\\-h2brj9c|xn\\-\\-hgbk6aj7f53bba|xn\\-\\-hlcj6aya9esc7a|xn\\-\\-j6w193g|xn\\-\\-jxalpdlp|xn\\-\\-kgbechtv|xn\\-\\-kprw13d|xn\\-\\-kpry57d|xn\\-\\-lgbbat1ad8j|xn\\-\\-mgbaam7a8h|xn\\-\\-mgbayh7gpa|xn\\-\\-mgbbh1a71e|xn\\-\\-mgbc0a9azcg|xn\\-\\-mgberp4a5d4ar|xn\\-\\-o3cw4h|xn\\-\\-ogbpf8fl|xn\\-\\-p1ai|xn\\-\\-pgbs0dh|xn\\-\\-s9brj9c|xn\\-\\-wgbh1c|xn\\-\\-wgbl6a|xn\\-\\-xkc2al3hye2a|xn\\-\\-xkc2dl3a5ee0h|xn\\-\\-yfro4i67o|xn\\-\\-ygbi2ammx|xn\\-\\-zckzah|xxx)"
					+ "|y[et]"
					+ "|z[amw]))";

	private static final Pattern WEB_URL = Pattern.compile(
			"((?:(http|https|Http|Https|rtsp|Rtsp):\\/\\/(?:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)"
					+ "\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,64}(?:\\:(?:[a-zA-Z0-9\\$\\-\\_"
					+ "\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,25})?\\@)?)?"
					+ "((?:(?:[" + GOOD_IRI_CHAR + "][" + GOOD_IRI_CHAR + "\\-]{0,64}\\.)+"   // named host
					+ TOP_LEVEL_DOMAIN_STR_FOR_WEB_URL
					+ "|(?:(?:25[0-5]|2[0-4]" // or ip address
					+ "[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(?:25[0-5]|2[0-4][0-9]"
					+ "|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1]"
					+ "[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}"
					+ "|[1-9][0-9]|[0-9])))"
					+ "(?:\\:\\d{1,5})?)" // plus option port number
					+ "(\\/(?:(?:[" + GOOD_IRI_CHAR + "\\;\\/\\?\\:\\@\\&\\=\\#\\~"  // plus option query params
					+ "\\-\\.\\+\\!\\*\\'\\(\\)\\,\\_])|(?:\\%[a-fA-F0-9]{2}))*)?"
					+ "(?:\\b|$)"); // and finally, a word boundary or end of input.  This is to stop foo.sure from matching as foo.su
	/**
	 * Source code copied from Android SDK [END] ===================================================
	 */

	/**
	 * Content data encoding.
	 */
	private static final String DATA_ENCODING = "UTF-8";

	/**
	 * Content data mime type.
	 */
	private static final String DATA_MIME_TYPE = "text/html";

	/**
	 * Width of dialog window drop shadow. In pixels.

	 /**
	 * Enums =======================================================================================
	 */

	/**
	 * <h5>Enum Overview</h5>
	 * <p>
	 * </p>
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
		 * Indicates URL to load into the web view.
		 * </p>
		 */
		URL,
		/**
		 * <p>
		 * Indicates HTML content to load into the web view.
		 * </p>
		 */
		HTML
	}

	/**
	 * Static members ==============================================================================
	 */

	/**
	 * Members =====================================================================================
	 */

	/**
	 * Main web view to manage HTML or URL content.
	 */
	private WebView mWebView = null;

	/**
	 * Content to load into the web view.
	 */
	private String mContent = "";

	/**
	 * Type of the current content.
	 */
	private ContentType mContentType = ContentType.UNKNOWN;

	/**
	 * Listeners -----------------------------
	 */

	/**
	 * Content loading listener.
	 */
	private OnWebContentLoadingListener iWebContentLoadingListener;

	/**
	 * Arrays --------------------------------------------------------------------------------------
	 */

	/**
	 * Booleans ------------------------------------------------------------------------------------
	 */

	/**
	 * Flag indicating whether the Java-Script is enabled or not.
	 */
	private boolean bJavaScriptEnabled = true;

	/**
	 * Flag indicating whether this web fragment instance is ready to load the current content into
	 * web view.
	 */
	private boolean bReadyToLoadContent = false;

	/**
	 * Constructors ================================================================================
	 */

	/**
	 * Methods =====================================================================================
	 */

	/**
	 * Public --------------------------------------------------------------------------------------
	 */

	/**
	 * <p>
	 * Checks whether the given <var>url</var> is valid and can be loaded into web view.
	 * </p>
	 *
	 * @param url Url to check.
	 * @return <code>True</code> if url matches valid web URL format, <code>false</code> otherwise.
	 */
	public static boolean isValidWebUrl(String url) {
		return WEB_URL.matcher("").reset(url).matches();
	}

	/**
	 * <p>
	 * Creates a new instance of WebFragment with the given options.
	 * </p>
	 *
	 * @param options WebOptions to manage WebFragment.
	 * @return New instance of WebFragment.
	 */
	public static WebFragment newInstance(WebOptions options) {
		final WebFragment fragment = new WebFragment();

		Bundle args = new Bundle();
		args.putBoolean(BUNDLE_WEB_VIEW_JAVA_SCRIPT_ENABLED, options.javascriptEnabled);
		args.putString(BUNDLE_WEB_VIEW_CONTENT, options.content);
		fragment.setArguments(args);

		return fragment;
	}

	/**
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle args = getArguments();
		if (savedInstanceState == null && args != null) {
			// Resolving of the content type also saves that content type.
			this.resolveContentType(args.getString(BUNDLE_WEB_VIEW_CONTENT));
			this.bJavaScriptEnabled = args.getBoolean(BUNDLE_WEB_VIEW_JAVA_SCRIPT_ENABLED);
		}
	}

	/**
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.mWebView = new WebView(inflater.getContext());
		mWebView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

		// Set up web view.
		mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

		// Set custom WebViewClient and WebChromeClient.
		WebViewClient client = onCreateWebViewClient();
		if (client != null) {
			mWebView.setWebViewClient(client);
		}
		WebChromeClient chromeClient = onCreateWebChromeClient();
		if (chromeClient != null) {
			mWebView.setWebChromeClient(chromeClient);
		}
		return mWebView;
	}

	/**
	 */
	@Override
	@SuppressLint("SetJavaScriptEnabled")
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		if (savedInstanceState != null) {
			this.mContentType = ContentType.values()[savedInstanceState.getInt(BUNDLE_WEB_VIEW_CONTENT_TYPE)];
			this.bJavaScriptEnabled = savedInstanceState.getBoolean(BUNDLE_WEB_VIEW_JAVA_SCRIPT_ENABLED);
			if (mWebView != null) {
				mWebView.getSettings().setJavaScriptEnabled(bJavaScriptEnabled);
			}
			this.mContent = savedInstanceState.getString(BUNDLE_WEB_VIEW_CONTENT);
		}
	}

	/**
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		this.bReadyToLoadContent = true;

		// Restore web view state.
		if (savedInstanceState != null && mWebView != null && mContentType == ContentType.URL) {
			mWebView.restoreState(savedInstanceState);
		} else {
			// Load content.
			loadContent(mContent);
		}

	}

	/**
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		// Save web view state.
		if (mWebView != null) {
			mWebView.saveState(outState);
		}

		outState.putInt(BUNDLE_WEB_VIEW_CONTENT_TYPE, mContentType.ordinal());
		outState.putBoolean(BUNDLE_WEB_VIEW_JAVA_SCRIPT_ENABLED, bJavaScriptEnabled);
		outState.putString(BUNDLE_WEB_VIEW_CONTENT, mContent);
	}

	/**
	 * <p>
	 * Loads the given content into the web view of this web fragment instance.
	 * </p>
	 *
	 * @param content Content to load. This can be raw HTML or URL.
	 * @return <code>True</code> if content was loaded, <code>false</code> otherwise.
	 * @see #getContent()
	 */
	public final boolean loadContent(String content) {
		this.resolveContentType(content);
		boolean loaded = false;
		if (bReadyToLoadContent) {
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
		}
		return loaded;
	}

	/**
	 * Getters + Setters ---------------------------------------------------------------------------
	 */

	/**
	 * <p>
	 * Returns the current content, which is loaded or prepared to load into the web view of this
	 * fragment instance.
	 * </p>
	 *
	 * @return Current content. This can be raw HTML or URL.
	 * @see #loadContent(String)
	 */
	public String getContent() {
		return mContent;
	}

	/**
	 * <p>
	 * Returns type of the current content, which is loaded or prepared to load into the web view of
	 * this fragment instance.
	 * </p>
	 *
	 * @return Type of the current content. See {@link com.wit.android.fragment.WebFragment.ContentType}
	 * for possible values.
	 */
	public ContentType getContentType() {
		return mContentType;
	}

	/**
	 * <p>
	 * Registers a callback to be invoked when loading process of the current content into web view
	 * starts or finishes.
	 * </p>
	 *
	 * @param listener Listener callback.
	 */
	public void setOnWebContentLoadingListener(OnWebContentLoadingListener listener) {
		this.iWebContentLoadingListener = listener;
	}

	/**
	 * <p>
	 * Removes the current <i>OnWebViewContentLoadingListener</i> callback.
	 * </p>
	 */
	public void removeOnWebContentLoadingListener() {
		this.iWebContentLoadingListener = null;
	}

	/**
	 * Protected -----------------------------------------------------------------------------------
	 */

	/**
	 */
	@Override
	protected boolean onBackPressed() {
		if (mWebView != null && mWebView.canGoBack()) {
			mWebView.goBack();
			return true;
		}
		return false;
	}

	/**
	 * <p>
	 * Returns an instance of web view.
	 * </p>
	 *
	 * @return The WebView of this web fragment instance.
	 */
	protected final WebView getWebView() {
		return mWebView;
	}

	/**
	 * <p>
	 * Invoked during web view's initialization process. You can create here your custom implementation
	 * of WebViewClient to manage specific callbacks for such a client.
	 * </p>
	 *
	 * @return Default web view client.
	 * @see #onCreateWebChromeClient()
	 */
	protected WebViewClient onCreateWebViewClient() {
		return new WebViewClient() {

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				if (USER_LOG) {
					Log.i(TAG, "onPageFinished('" + url + "')");
				}
				dispatchLoadingFinished(url);
			}

			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				super.onReceivedError(view, errorCode, description, failingUrl);
				Log.e(TAG, "onReceivedError(errorCode(" + errorCode + "), description('" + description + "'), failingUrl('" + failingUrl + "'))");
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				if (USER_LOG) {
					Log.i(TAG, "onPageStarted('" + url + "')");
				}
				dispatchLoadingStarted(url);
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if (USER_LOG) {
					Log.i(TAG, "shouldOverrideUrlLoading('" + url + "')");
				}
				return super.shouldOverrideUrlLoading(view, url);
			}

		};
	}

	/**
	 * <p>
	 * Invoked during web view's initialization process. You can create here your custom implementation
	 * of WebChromeClient to manage specific callbacks for such a client.
	 * </p>
	 *
	 * @return Default web chrome client.
	 * @see #onCreateWebViewClient()
	 */
	protected WebChromeClient onCreateWebChromeClient() {
		return new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int progress) {
				super.onProgressChanged(view, progress);
			}
		};
	}

	/**
	 * <p>
	 * Invoked to load the given <var>url</var> into web view.
	 * </p>
	 * <p/>
	 *
	 * @param url URL to load into the current web view.
	 * @return <code>True</code> if URL was successfully loaded, <code>false</code> otherwise.
	 */
	protected boolean onLoadURL(String url) {
		boolean success = false;

		// Check web view and URL to load.
		if (mWebView != null && url != null && url.length() != 0) {
			mWebView.loadUrl(url);
			success = true;

			if (USER_LOG) {
				Log.i(TAG, "Loaded URL('" + url + "') into web view.");
			}
		} else {
			Log.e(TAG, "Can't load URL('" + url + "') into web view. URL or web view is invalid.");
		}
		return success;
	}

	/**
	 * <p>
	 * Invoked to load the given <var>data</var> into web view.
	 * </p>
	 *
	 * @param data HTML data to load into the current web view.
	 * @return <code>True</code> if data were successfully loaded, <code>false</code> otherwise.
	 */
	protected boolean onLoadData(String data) {
		boolean success = false;

		// Check web view and data to load.
		if (mWebView != null && data != null && data.length() != 0) {
			mWebView.loadDataWithBaseURL("", data, DATA_MIME_TYPE, DATA_ENCODING, "");
			success = true;

			if (USER_LOG) {
				Log.i(TAG, "Loaded data('" + data + "') into web view.");
			}
		} else {
			Log.e(TAG, "Can't load data('" + data + "') into web view. Data or web view is invalid.");
		}
		return success;
	}

	/**
	 * <p>
	 * Called to dispatch message, that loading process of the specified <var>webUrl</var> was started.
	 * </p>
	 *
	 * @param webUrl Web url which is currently being loaded into the current web view.
	 */
	protected final void dispatchLoadingStarted(String webUrl) {
		if (iWebContentLoadingListener != null) {
			iWebContentLoadingListener.onLoadingStarted(webUrl);
		}
	}

	/**
	 * <p>
	 * Called to dispatch message, that loading process of the specified <var>webUrl</var> was finished.
	 * </p>
	 *
	 * @param webUrl Web url which was currently loaded into the current web view.
	 */
	protected final void dispatchLoadingFinished(String webUrl) {
		if (iWebContentLoadingListener != null) {
			iWebContentLoadingListener.onLoadingFinished(webUrl);
		}
	}

	/**
	 * Private -------------------------------------------------------------------------------------
	 */

	/**
	 * Resolves type of the given content.
	 * <b>This also saves as current content.</b>
	 *
	 * @param content Content to resolve and save.
	 */
	private void resolveContentType(String content) {
		if (content != null && content.length() > 0) {
			if (isValidWebUrl(content)) {
				this.mContentType = ContentType.URL;
			} else {
				this.mContentType = ContentType.HTML;
			}
			if (USER_LOG) {
				Log.i(TAG, "Resolved content for web view as(" + mContentType + ").");
			}
			this.mContent = content;
		} else {
			Log.e(TAG, "Can't resolve type of content(" + content + ")");
		}
	}

	/**
	 * Abstract methods ----------------------------------------------------------------------------
	 */

	/**
	 * Inner classes ===============================================================================
	 */

	/**
	 * <h4>Class Overview</h4>
	 * <p>
	 * Web options for {@link com.wit.android.fragment.WebFragment}.
	 * </p>
	 *
	 * @author Martin Albedinsky
	 */
	public static class WebOptions {
		/**
		 * Members =================================================================================
		 */

		/**
		 * Content to load into web view.
		 */
		private String content = "";

		/**
		 * Flag indicating whether Java-Script should be enabled or not.
		 */
		private boolean javascriptEnabled = true;

		/**
		 * Constructors ============================================================================
		 */

		/**
		 * Methods =================================================================================
		 */

		/**
		 * <p>
		 * Sets the content to load into web view.
		 * </p>
		 *
		 * @param content Content to load. This can be raw HTML or URL.
		 * @return This options.
		 */
		public WebOptions content(String content) {
			this.content = content;
			return this;
		}

		/**
		 * <p>
		 * Sets flag indicating whether to enable Java-Script or not.
		 * </p>
		 *
		 * @param enable <code>True</code> to enable, <code>false</code> otherwise.
		 * @return This options.
		 */
		public WebOptions enableJavascript(boolean enable) {
			this.javascriptEnabled = enable;
			return this;
		}
	}

	/**
	 * Interface ===================================================================================
	 */

	/**
	 * <h4>Interface Overview</h4>
	 * <p>
	 * Simple listener for {@link com.wit.android.fragment.WebFragment} with callbacks fired whenever
	 * loading process of specific <b>web url</b> was started/finished.
	 * </p>
	 *
	 * @author Martin Albedinsky
	 */
	public static interface OnWebContentLoadingListener {

		/**
		 * <p>
		 * Fired whenever loading process of the specified <var>webUrl</var> within an instance of
		 * {@link com.wit.android.fragment.WebFragment} for which is this callback registered was started.
		 * </p>
		 *
		 * @param webUrl Web url which is currently being loaded into web view.
		 */
		public void onLoadingStarted(String webUrl);

		/**
		 * <p>
		 * Fired whenever loading process of the specified <var>webUrl</var> within an instance of
		 * {@link com.wit.android.fragment.WebFragment} for which is this callback registered was finished.
		 * </p>
		 *
		 * @param webUrl Web url which was currently loaded into web view.
		 */
		public void onLoadingFinished(String webUrl);
	}
}
