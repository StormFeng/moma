package com.midian.moma.ui.shopping;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.midian.configlib.ServerConstant;
import com.midian.moma.R;

import midian.baselib.base.BaseActivity;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;

/**
 * Created by Administrator on 2016/3/11.
 */
public class WelUrlActivity extends BaseActivity{
    String url;
    private WebView webview;
    private BaseLibTopbarView topbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        url = getIntent().getStringExtra("url");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wel_url);
        initView();

    }

    private void initView() {
        topbar = findView(R.id.topbar);
        topbar.setTitle("").setLeftImageButton(R.drawable.icon_left_arrow, null).setLeftText("返回",
                UIHelper.finish(_activity));
        webview = (WebView) findViewById(R.id.web);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setBuiltInZoomControls(true);//缩放网页属性
        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webview.loadUrl(getUrl(url));
        webview.setWebViewClient(webViewClient);
    }
    private String getUrl(String url) {
        if (url.equals(ServerConstant.BASEURL) || url.contains("http://")
                || url.contains("https://")) {
            return url;
        } else {
            return ServerConstant.BASEURL + url;
        }
    }
    private WebViewClient webViewClient = new WebViewClient() {

        public void onPageFinished(WebView view, String url) {
            hideLoadingDlg();

        };

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            // TODO Auto-generated method stub
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

    };
}
