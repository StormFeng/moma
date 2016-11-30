package com.midian.moma.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.midian.UMengUtils.ShareContent;
import com.midian.UMengUtils.UMengShareUtil;
import com.midian.UMengUtils.UMengShareUtil.UMengShareUtilListener;
import com.midian.moma.R;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;

import midian.baselib.api.ApiCallback;
import midian.baselib.app.AppContext;
import midian.baselib.bean.NetResult;


public class ShareDialogUtil extends Dialog implements View.OnClickListener {


    private Context context;
    private ShareDialogActionListenr listener;
    private UMengShareUtil mShareUtil;
    public static String defaultContent = "分享给好友";
    public static String defaultTitle = "摩范Mofan";
    public static String defaultTitleUrl = "";
    public static String defaultImg = "";
    private String fileToPath;
    private ShareContent mImageContent = new ShareContent();
    private String type;

    public interface ShareDialogActionListenr {

        void onClickWechat();

        void onClickWechatFrients();

        void onClickQzone();

        void onClickQQ();

        void onClickWeibo();

        void onClickSms();

    }

    public ShareDialogUtil(Context context) {
        super(context, R.style.bottom_dialog);
        init(context);
    }

    public ShareDialogUtil(Context context, int themeResId) {
        super(context, R.style.bottom_dialog);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        Window w = this.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.gravity = Gravity.BOTTOM;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        w.setAttributes(lp);
        this.setCanceledOnTouchOutside(true);
        View contentView = View.inflate(context, R.layout.share_dialog, null);
        this.setContentView(contentView);
        contentView.findViewById(R.id.wechat).setOnClickListener(this);
        contentView.findViewById(R.id.wechat_friend).setOnClickListener(this);
        contentView.findViewById(R.id.qq).setOnClickListener(this);
        contentView.findViewById(R.id.qzone).setOnClickListener(this);
        contentView.findViewById(R.id.cancel).setOnClickListener(this);
        contentView.findViewById(R.id.weibo).setOnClickListener(this);
        contentView.findViewById(R.id.sms).setOnClickListener(this);
        try {
            initShareUtil();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void show(String url, String title, String content, String share_img, String type) {
        this.type = type;
        defaultTitleUrl = url;
        if (!TextUtils.isEmpty(title))
            defaultTitle = title;
        if (!TextUtils.isEmpty(content))
            defaultContent = content;
        if (!TextUtils.isEmpty(share_img))
            defaultImg = share_img;
        refresh();
        super.show();
    }

    private void initShareUtil() {
        mShareUtil = UMengShareUtil.getInstance((Activity) context);
        mShareUtil.setUMengShareUtilListener(mUMengShareUtilListener);
        mImageContent.setAppName("摩范");
        mImageContent.setImage(defaultImg);// 分享的图片
        mImageContent.setSummary(defaultContent);// 分享的内容
        mImageContent.setTitle(defaultTitle);// 分享的标题
        mImageContent.setUrl(defaultTitleUrl);// 分享链接
        System.out.println("分享initShareUtil---defaultImg=：：：" + defaultImg);
    }

    private void refresh() {
        if (type.equals("1")) {
            mImageContent.setmBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher));// 传本地图片如果没一定传null
        }
        mImageContent.setImage(defaultImg);
        mImageContent.setSummary(defaultContent);
        mImageContent.setTitle(defaultTitle);
        mImageContent.setUrl(defaultTitleUrl);// 分享链接
        System.out.println("分享refresh----defaultImg=" + defaultImg);
    }

    UMengShareUtilListener mUMengShareUtilListener = new UMengShareUtilListener() {

        @Override
        public void onStart() {

        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {

            // if (SHARE_MEDIA.QQ == platform) {
            // qqRl.setEnabled(true);
            // } else if (SHARE_MEDIA.QZONE == platform) {
            // qqzoneRl.setEnabled(true);
            // } else if (SHARE_MEDIA.WEIXIN == platform) {
            // weixinRl.setEnabled(true);
            // } else if (SHARE_MEDIA.WEIXIN_CIRCLE == platform) {
            // weixinfriendsRl.setEnabled(true);
            // } else if (SHARE_MEDIA.SINA == platform) {
            // microblogRl.setEnabled(true);
            // }
        }

    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wechat:
                mShareUtil.share(SHARE_MEDIA.WEIXIN, mImageContent);
                break;
            case R.id.wechat_friend:
                mShareUtil.share(SHARE_MEDIA.WEIXIN_CIRCLE, mImageContent);
                break;
            case R.id.qq:
                mShareUtil.share(SHARE_MEDIA.QQ, mImageContent);
                break;
            case R.id.qzone:
                mShareUtil.share(SHARE_MEDIA.QZONE, mImageContent);
                break;
            case R.id.weibo:
                mShareUtil.share(SHARE_MEDIA.SINA, mImageContent);
                break;
            case R.id.sms:
                mShareUtil.share(SHARE_MEDIA.SMS, mImageContent);
                break;

            case R.id.cancel:
                dismiss();
                break;
        }
        dismiss();
    }

    public Dialog config(final String fileToPath) {
        return config(fileToPath, defaultContent, defaultTitle, defaultTitleUrl);
    }

    public Dialog config(final String fileToPath, final String defaultContent, final String defaultTitle,
                         final String defaultTitleUrl) {
        this.fileToPath = fileToPath;
        this.defaultContent = defaultContent;
        this.defaultTitle = defaultTitle;
        this.defaultTitleUrl = defaultTitleUrl;
        return this;
    }
}
