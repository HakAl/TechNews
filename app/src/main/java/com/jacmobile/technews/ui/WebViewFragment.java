package com.jacmobile.technews.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jacmobile.technews.R;
import com.jacmobile.technews.ui.views.LoadingRelativeLayout;

import javax.inject.Inject;

/**
 * Created by acorll on 12/20/14.
 */
public class WebViewFragment extends DaggerFragment
{
    public static final String WEBVIEW_URL = "url";
    public static final String WINDOW_TITLE = "title";


    private WebView webView;
    private LoadingRelativeLayout loadingContainer;

    public static WebViewFragment newInstance(String title, String url)
    {
        Bundle args = new Bundle();
        args.putString(WINDOW_TITLE, title);
        args.putString(WEBVIEW_URL, url);
        WebViewFragment fragment = new WebViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStop()
    {
        super.onStop();
        this.loadingContainer.removeAllViews();
        this.webView = null;
        this.loadingContainer = null;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        this.loadingContainer = (LoadingRelativeLayout) inflater.inflate(R.layout.fragment_webview, container, false);
        this.loadingContainer.setLoadingView("Loading");

        getActivity().setTitle(getArguments().getString(WINDOW_TITLE));

        this.webView = (WebView) inflater.inflate(R.layout.webview, loadingContainer, false);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setUserAgentString(System.getProperty("http.agent"));

//        webView.z
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setWebViewClient(new WebClient());
        webView.loadUrl(getArguments().getString(WEBVIEW_URL));

        return loadingContainer;
    }

    void loadWebClient()
    {
        this.loadingContainer.removeAllViews();
        this.loadingContainer.addView(webView);
    }

    class WebClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            view.loadUrl(url);
            return true;
        }

        public void onPageFinished(WebView view, String url)
        {
            loadWebClient();
        }
    }
}