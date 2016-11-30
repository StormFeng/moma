package com.midian.moma.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.midian.UMengUtils.UMengShareUtil;
import com.midian.configlib.ServerConstant;
import com.midian.moma.R;
import com.midian.moma.model.CaseDetailBean;
import com.midian.moma.urlconstant.UrlConstant;
import com.midian.moma.utils.AppUtil;
import com.midian.moma.utils.ShareDialogUtil;

import midian.baselib.base.BaseActivity;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;
import midian.baselib.widget.CircleImageView;
import midian.baselib.widget.MyWebView;

/**
 * 案例详情
 *
 * @author Administrator
 */
public class CaseDetailActivity1 extends BaseActivity {
    private BaseLibTopbarView topbar;
    private View saved_ll;
    private ImageView like_iv;
    private TextView like_conunt;
    private int count;
    private CircleImageView head;
    private TextView title, name;
    private MyWebView webView;
    private CaseDetailBean bean;
    private String caseId;
    private TextView send_tv;
    private String caseType;
    private String case_detail;
    private String share_url;
    private String case_title;
    private String share_img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_detail);
        initView();
    }


    private void initView() {
        topbar = findView(R.id.topbar);
        topbar.setTitle("案例详情").setLeftImageButton(R.drawable.icon_left_arrow, null).setLeftText("返回", UIHelper.finish(_activity));
        topbar.setRightImageButton(R.drawable.icon_detail_share, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String share_title = case_title;
                String share_content = "万千优惠在手中，精彩生活我做主！";
//                String share_img = R.drawable.ic_launcher+"";
                new ShareDialogUtil(_activity).show(share_url, share_title, share_content, share_img,"0");
            }
        });
        caseId = mBundle.getString("caseId");
        head = findView(R.id.head);
        title = findView(R.id.title);
        name = findView(R.id.name);
        webView = findView(R.id.webView);
        webView.setOnScrollChangedCallback(new MyWebView.OnScrollChangedCallback() {
            @Override
            public void onScroll(int dx, int dy) {
                if (dy < 0) {
                    send_tv.setVisibility(View.VISIBLE);
                } else {
                    send_tv.setVisibility(View.GONE);
                }
            }
        });
        webView.getSettings().setBlockNetworkImage(true);
        send_tv = findView(R.id.send_tv);
        send_tv.setOnClickListener(this);
        saved_ll = findView(R.id.saved_ll);
        like_iv = findView(R.id.like_iv);
        like_conunt = findView(R.id.like_conunt);
        saved_ll.setOnClickListener(this);

        loadData();
    }

    private void loadData() {
        AppUtil.getMomaApiClient(ac).caseDetail(caseId, this);
    }


    @Override
    public void onApiStart(String tag) {
        super.onApiStart(tag);
        topbar.showProgressBar();
        showLoadingDlg();
    }

    @Override
    public void onApiSuccess(NetResult res, String tag) {
        super.onApiSuccess(res, tag);
        topbar.hideProgressBar();
        hideLoadingDlg();
        if (res.isOK()) {
            if ("caseDetail".equals(tag)) {
                bean = (CaseDetailBean) res;
                if (!TextUtils.isEmpty(bean.getContent().getServe_pic_thumb_name())) {
                    ac.setImage(head, UrlConstant.BASEFILEURL + bean.getContent().getServe_pic_thumb_name());
                    share_img =UrlConstant.BASEFILEURL +  bean.getContent().getServe_pic_thumb_name();
                } else {
                    head.setBackgroundResource(R.drawable.head1);
                    share_img = R.drawable.ic_launcher + "";
                }
                case_title = bean.getContent().getCase_title();
                title.setText(bean.getContent().getCase_title());
                name.setText(bean.getContent().getCase_serve_name());
                case_detail = bean.getContent().getCase_detail();
                share_url = bean.getContent().getShare_url();

                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl(ServerConstant.BASEURL + case_detail);//"http://m.mdkg.net/"
                webView.setWebViewClient(new MyWebViewClient());

                String isLike = bean.getContent().getIs_like();
                count = Integer.parseInt(bean.getContent().getLike_count());
                like_conunt.setText(count + "");
                if ("2".equals(isLike)) {
                    saved_ll.setBackgroundResource(R.drawable.icon_zan_n_bg);//未点赞背景
                    like_iv.setBackgroundResource(R.drawable.icon_zan_n);//未点赞红心
                    caseType = "1";
                    saved_ll.setClickable(true);
                } else {
                    saved_ll.setBackgroundResource(R.drawable.icon_zan_ok_bg);
                    like_iv.setBackgroundResource(R.drawable.icon_zan_ok);
                    caseType = "2";
                    saved_ll.setClickable(false);
                }
                System.out.println("详情：：：" + ServerConstant.BASEURL + case_detail);
            }
            if ("opCaseLike".equals(tag)) {
                if ("1".equals(caseType)) {
                    caseType = "2";
                    like_conunt.setText((count+1)+"");
                    saved_ll.setBackgroundResource(R.drawable.icon_zan_ok_bg);
                    like_iv.setBackgroundResource(R.drawable.icon_zan_ok);
                    saved_ll.setClickable(false);
                   /* bean.getContent().setIs_like("1");
                    count = FDDataUtils.getInteger(bean.getContent().getLike_count()) + 1;*/
                } else {
                    caseType = "1";
                    like_conunt.setText((count-1)+"");
                    saved_ll.setBackgroundResource(R.drawable.icon_zan_n_bg);//未点赞背景
                    like_iv.setBackgroundResource(R.drawable.icon_zan_n);//未点赞红心
                    saved_ll.setClickable(true);
                    /*bean.getContent().setIs_like("2");
                    count = FDDataUtils.getInteger(bean.getContent().getLike_count()) - 1;*/
                }
//                bean.getContent().setLike_count(count + "");
            }
        } else {
            ac.handleErrorCode(_activity, res.error_code);
        }
    }

    class MyWebViewClient extends WebViewClient{

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            view.setVisibility(View.VISIBLE);
            webView.getSettings().setBlockNetworkImage(false);
            if(webView.getSettings().getLoadsImagesAutomatically()){
                webView.getSettings().setLoadsImagesAutomatically(true);
            }
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.send_tv:
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.saved_ll:
                if (!ac.isRequireLogin(_activity)) {
                    return;
                }
                AppUtil.getMomaApiClient(ac).opCaseLike(caseId, caseType, this);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent0) {
        super.onActivityResult(requestCode, resultCode, intent0);
        UMengShareUtil.getInstance(this).onActivityResult(
                requestCode, resultCode, intent0);
    }
}
