package com.midian.moma.ui.home;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.midian.configlib.ServerConstant;
import com.midian.moma.R;

import midian.baselib.base.BaseActivity;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;

/**
 * Created by chu on 2015.12.4.004.
 */
public class BannerWebViewActivity extends BaseActivity {

    private BaseLibTopbarView topbar;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        initView();
    }

    private void initView() {

        topbar = findView(R.id.topbar);
        topbar.setTitle("详情");
        topbar.setLeftImageButton(R.drawable.icon_back, UIHelper.finish(this))
                .setLeftText("返回", UIHelper.finish(_activity));
        String urls = mBundle.getString("url");
        webView = (WebView) findViewById(R.id.webview);
        // 设置WebView属性，能够执行JavaScript脚本
        webView.getSettings().setJavaScriptEnabled(true);
        // 加载需要显示的网页
        System.out.println(" + urls++++++++++++++++" + urls);
        webView.loadUrl(ServerConstant.BASEURL + urls);
//        webView.loadUrl("http://m.mdkg.net/");

        // 设置Web视图
        webView.setWebViewClient(new WebViewClient());
    }
}
