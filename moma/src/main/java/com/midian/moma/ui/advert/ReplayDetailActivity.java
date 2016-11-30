package com.midian.moma.ui.advert;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.midian.configlib.ServerConstant;
import com.midian.moma.R;

import midian.baselib.base.BaseActivity;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;

/**
 * 编辑广告资料回复列表---详情页
 * Created by chu on 2016/1/20.
 */
public class ReplayDetailActivity extends BaseActivity {
    private BaseLibTopbarView topbar;
    private String title, url;
    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replay_detail);
        topbar = findView(R.id.topbar);
        title = mBundle.getString("title");
        url = mBundle.getString("url");

        topbar.setTitle(title).setLeftImageButton(R.drawable.icon_back, null).setLeftText("返回", UIHelper.finish(_activity));
        webview = findView(R.id.webview);

        // 设置WebView属性，能够执行JavaScript脚本
        webview.getSettings().setJavaScriptEnabled(true);
        // 加载需要显示的网页
        webview.loadUrl(ServerConstant.BASEURL + url);
        // 设置Web视图
        webview.setWebViewClient(new WebViewClient());
    }
}
