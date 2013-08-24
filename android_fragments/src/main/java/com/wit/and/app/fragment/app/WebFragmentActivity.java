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
package com.wit.and.app.fragment.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.webkit.WebView;

import com.wit.and.fragment.R;
import com.wit.and.app.fragment.WebFragment;
import com.wit.and.app.fragment.WebFragment.OnWebViewInitListener;

/**
 * <h4>Class Overview</h4>
 * <p>
 * Web view activity with simple web view fragment.
 * </p>
 *
 * @author Martin Albedinsky
 */
public class WebFragmentActivity extends FragmentActivity implements OnWebViewInitListener {

    /**
     * Constants =============================
     */

    /**
     * Log TAG.
     */
    // private static final String TAG = WebFragmentActivity.class.getSimpleName();

    /**
     * Indicates if debug private output trough log-cat is enabled.
     */
    // private static final boolean DEBUG = true;

    /**
     * Indicates if logging for user output trough log-cat is enabled.
     */
    // private static final boolean USER_LOG = true;

    /**
     *
     */
    public static final String INTENT_EXTRAS_CONTENT = "com.wit.and.app.fragment.app.WebFragmentActivity.Intent.Content";

    /**
     *
     */
    private static final String DEFAULT_URL = "http://www.google.com";

    /**
     * Enums =================================
     */

    /**
     * Static members ========================
     */

    /**
     * Members ===============================
     */

    /**
     *
     */
    private WebView mWebView;

    /**
     * Listeners -----------------------------
     */

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
     *
     */
    @Override
    public void onWebViewInitialized(WebView webView) {
        this.mWebView = webView;
    }

    /**
     * Getters + Setters ---------------------
     */

    /**
     * Protected -----------------------------
     */

    /**
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.and_activity_webview);

        // Set container id for web view fragment.
        getFragmentController().setFragmentContainerID(R.id.Activity_WebView_Layout_Container);

        // Default.
        String content = DEFAULT_URL;

        if (savedInstanceState == null) {
            Bundle extras = this.getIntent().getExtras();

            if (extras != null && extras.containsKey(INTENT_EXTRAS_CONTENT)) {
                content = extras.getString(INTENT_EXTRAS_CONTENT);
            }
        }

        if (savedInstanceState == null) {
            getFragmentController().showFragment(WebFragment.newInstance(content));
        }
    }

    /**
     * @return
     */
    protected WebView getWebView() {
        return mWebView;
    }

    /**
     * Private -------------------------------
     */

    /**
     * Abstract methods ----------------------
     */

    /**
     * Inner classes =========================
     */

    /**
     * Interface =============================
     */
}
