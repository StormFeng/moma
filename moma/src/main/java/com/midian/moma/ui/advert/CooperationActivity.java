package com.midian.moma.ui.advert;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.midian.configlib.ServerConstant;
import com.midian.moma.R;
import com.midian.moma.model.SysConfListBean;
import com.midian.moma.model.SysConfListBean.SysContent;
import com.midian.moma.urlconstant.UrlConstant;
import com.midian.moma.utils.AppUtil;

import midian.baselib.base.BaseActivity;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;

/**
 * Created by chu on 2016/1/8.
 */
public class CooperationActivity extends BaseActivity {
    private BaseLibTopbarView topbar;
    private WebView webview;
    private String code;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooperation);
        title = mBundle.getString("title");
        topbar = findView(R.id.topbar);
        topbar.setTitle(title).setLeftImageButton(R.drawable.icon_back, null).setLeftText("返回", UIHelper.finish(_activity));
        webview = findView(R.id.webview);
        if (title.equals("合作流程")) {
            code = "cooperation_process";//about_us:关于我们；about_pay：支付须知；cooperation_process：合作流程
        } else {
            code = "about_pay";//支付须知
        }


        loadData();
    }

    private void loadData() {
        AppUtil.getMomaApiClient(ac).getSysConfList(code, this);
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
            SysConfListBean sysContent = (SysConfListBean) res;
            // 设置WebView属性，能够执行JavaScript脚本
            webview.getSettings().setJavaScriptEnabled(true);
            // 加载需要显示的网页
            if (title.equals("合作流程")) {
                webview.loadUrl(ServerConstant.BASEURL + sysContent.getContent().getCooperation_process());
            } else {
                webview.loadUrl(ServerConstant.BASEURL + sysContent.getContent().getAbout_pay());
            }

            // 设置Web视图
            webview.setWebViewClient(new WebViewClient());
        }
    }
}
