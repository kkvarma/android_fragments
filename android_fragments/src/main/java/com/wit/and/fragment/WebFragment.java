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
package com.wit.and.fragment;

import android.support.v4.app.Fragment;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

/**
 * <h4>Class Overview</h4>
 * <p>
 * </p>
 *
 * @author Martin Albedinsky
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
    // private static final boolean DEBUG = true;

    /**
     * Indicates if logging for user output trough log-cat is enabled.
     */
    // private static final boolean USER_LOG = true;

    /**
     * Bundle identifiers.
     */

    /**
     *
     */
    private static final String BUNDLE_WEB_VIEW_CONTENT_TYPE = "com.wit.and.fragment.WebFragment.Bundle.ContentType";
    private static final String BUNDLE_WEB_VIEW_JAVA_SCRIPT_ENABLED = "com.wit.and.fragment.WebFragment.Bundle.JavaScriptEnabled";

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
     */
    private static final int DROP_SHADOW_SIZE = 10;

    /**
     * Enums =================================
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
         * Indicates url to load in the web view.
         * </p>
         */
        URL,
        /**
         * <p>
         * Indicates HTML content to load in the web view.
         * </p>
         */
        HTML
    }

    /**
     * Static members ========================
     */

    /**
     * Members ===============================
     */

    /**
     * Main web view to handle showing html content or some useful url.
     */
    private WebView mWebView = null;

    /**
     * Loading bar in the body view.
     */
    private ProgressBar mBodyLoadingBar;

    /**
     * Content to load into the web view.
     */
    private String mContent = "";

    /**
     * Dialog views.
     */
    private View mBodyView, mButtonsView;

    /**
     * Content type to load into the web view.
     */
    private ContentType mContentType = ContentType.UNKNOWN;

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
     * Flag indicating, whether the Java-Script is enabled or not.
     */
    private boolean bJavaScriptEnabled = true;

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
     * Getters + Setters ---------------------
     */

    /**
     * Protected -----------------------------
     */

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
     * <h4>Class Overview</h4>
     * <p>
     * Web options for {@link com.wit.and.fragment.WebFragment}.
     * </p>
     *
     * @author Martin Albedinsky
     */
    public static class WebOptions {
        /**
         * Members ===============================
         */

        /**
         * Content to show in the web view.
         */
        private String content = "";

        /**
         * Flag indicating, whether Java-Script should be enabled in the web view.
         */
        private boolean javascriptEnabled = true;

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
         * Getters + Setters ---------------------
         */

        /**
         * <p>
         * </p>
         *
         * @param content
         * @return This options.
         */
        public WebOptions content(String content) {
            this.content = content;
            return this;
        }

        /**
         * <p>
         * </p>
         *
         * @param enable
         * @return This options.
         */
        public WebOptions enableJavascript(boolean enable) {
            this.javascriptEnabled = enable;
            return this;
        }
    }

    /**
     * Interface =============================
     */
}
