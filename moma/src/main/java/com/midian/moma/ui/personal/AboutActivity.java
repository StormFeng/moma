package com.midian.moma.ui.personal;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.midian.configlib.ServerConstant;
import com.midian.moma.R;
import com.midian.moma.model.SysConfListBean;
import com.midian.moma.utils.AppUtil;

import midian.baselib.base.BaseActivity;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;

/**
 * Created by chu on 2015.11.24.024.
 */
public class AboutActivity extends BaseActivity {
    private WebView webview;
    private BaseLibTopbarView topbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        webview = findView(R.id.webview);
        topbar = findView(R.id.topbar);
        topbar.setTitle("关于我们").setLeftImageButton(R.drawable.icon_back, null).setLeftText("返回",
                UIHelper.finish(_activity));
        webview = (WebView) findViewById(R.id.webview);

        AppUtil.getMomaApiClient(ac).getSysConfList(null, this);

        // 设置WebView属性，能够执行JavaScript脚本
        webview.getSettings().setJavaScriptEnabled(true);
        // 加载需要显示的网页
        webview.loadUrl("http://m.mdkg.net/");
        // 设置Web视图
        webview.setWebViewClient(new WebViewClient());
    }

    @Override
    public void onApiStart(String tag) {
        super.onApiStart(tag);
        showLoadingDlg();
    }

    @Override
    public void onApiSuccess(NetResult res, String tag) {
        super.onApiSuccess(res, tag);
        hideLoadingDlg();
        if (res.isOK()) {
            SysConfListBean bean = (SysConfListBean) res;
//			webview = (WebView) findViewById(R.id.webview);
//			// 设置WebView属性，能够执行JavaScript脚本
//			webview.getSettings().setJavaScriptEnabled(true);
//			// 加载需要显示的网页

            webview.loadUrl(ServerConstant.BASEURL + bean.getContent().getAbout_us());
//			// 设置Web视图
//			webview.setWebViewClient(new WebViewClient());


        }
    }

}
