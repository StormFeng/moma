package com.midian.moma.ui.personal;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.midian.login.view.ModifyPwdOneActivity;
import com.midian.login.view.LoginActivity;
import com.midian.moma.R;
import com.midian.moma.utils.ShareDialogUtil;
import com.qihoo.appstore.common.updatesdk.lib.UpdateHelper;
import midian.baselib.base.BaseActivity;
import midian.baselib.utils.UIHelper;
import midian.baselib.version.VersionUpdateUtil;
import midian.baselib.widget.BaseLibTopbarView;

/**
 * 设置
 * Created by chu on 2015.11.24.024.
 */
public class SettingActivity extends BaseActivity implements ShareDialogUtil.ShareDialogActionListenr {
    private BaseLibTopbarView topba;
    private View change_pwd_ll, update_ll, back_ll, share_ll, about_ll;
    private Button cancel;
    private TextView version_tv;
    private VersionUpdateUtil mVersionUpdateUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();
    }

    private void initView() {
        topba = findView(R.id.topbar);
        topba.setTitle("设置").setLeftImageButton(R.drawable.icon_back, null)
                .setLeftText("返回", UIHelper.finish(_activity));
        change_pwd_ll = findView(R.id.change_pwd_ll);
        update_ll = findView(R.id.update_ll);
        version_tv = findView(R.id.version);
        back_ll = findView(R.id.back_ll);
        share_ll = findView(R.id.share_ll);
        about_ll = findView(R.id.about_ll);
        cancel = findView(R.id.cancel);
        change_pwd_ll.setOnClickListener(this);
        update_ll.setOnClickListener(this);
        back_ll.setOnClickListener(this);
        about_ll.setOnClickListener(this);
        cancel.setOnClickListener(this);
        share_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String share_url = "http://mobile.baidu.com/item?docid=10082549&from=1010680m";
                String share_title = "摩范APP";
                String share_content = "万千优惠在手中，精彩生活我做主！";
                String share_img = null;
                new ShareDialogUtil(_activity).show(share_url, share_title, share_content, share_img,"1");
            }
        });

        if (ac.isHasNewVersion) {
            version_tv.setText("有新版本");
            version_tv.setTextColor(getResources().getColor(R.color.red));
        } else {
            version_tv.setText(getVersionName());
        }


    }

    public String getVersionName() {
        PackageManager manager = this.getPackageManager();
        PackageInfo info;
        String version = "beta";
        try {
            info = manager.getPackageInfo(this.getPackageName(), 0);
            version = version + " " + info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return version;
    }



    @Override
    protected void onResume() {
        super.onResume();
        if (!ac.isAccess()) {
            cancel.setText("立即登陆");
        } else {
            cancel.setText("退出登陆");
        }
    }

    @Override
    public void onClick(View v) {
        if (!ac.isAccess() && v.getId() != R.id.upload_ll && v.getId() != R.id.share_ll && v.getId() != R.id.about_ll) {
            UIHelper.jumpForResult(_activity, LoginActivity.class, new Bundle(), 5000);
            // _activity.overridePendingTransition(R.anim.activity_open, 0);
            return;
        }
        super.onClick(v);
        switch (v.getId()) {
            case R.id.change_pwd_ll://修改密码
                UIHelper.jump(_activity, ModifyPwdOneActivity.class);
                break;
            case R.id.update_ll:
//                UIHelper.t(_activity, "正在检查更新，请稍等");
//                if (ac.isHasNewVersion) {
//                    mVersionUpdateUtil.BDCheckUpdate();
//                } else {
//                    UIHelper.t(this, "已经是最新版本");
//                }
                UpdateHelper.getInstance().init(getApplicationContext(), Color.parseColor("#0A93DB"));
                UpdateHelper.getInstance().setDebugMode(true);
                UpdateHelper.getInstance().manualUpdate("com.midian.moma");
                break;

            case R.id.back_ll://意见反馈
                UIHelper.jump(_activity, AdviceBackActivity.class);
                break;
           /* case R.id.share_ll://分享
                ShareDialogUtil shareDialogUtil = new ShareDialogUtil(_activity, R.style.bottom_dialog);
                shareDialogUtil.show();
                break;*/
            case R.id.about_ll://关于我们
                UIHelper.jump(_activity, AboutActivity.class);
                break;
            case R.id.cancel://退出登陆
                if (cancel.getText().toString().equals("立即登陆")) {
                    UIHelper.jump(_activity, LoginActivity.class);
                    return;
                } else {
                    ac.clearUserInfo();
                    finish();
                }

                break;

        }
    }

    @Override
    public void onClickWechat() {
        UIHelper.t(_activity, "11");
    }

    @Override
    public void onClickWechatFrients() {
        UIHelper.t(_activity, "11");
    }

    @Override
    public void onClickQzone() {
        UIHelper.t(_activity, "11");
    }

    @Override
    public void onClickQQ() {
        UIHelper.t(_activity, "11");
    }

    @Override
    public void onClickWeibo() {
        UIHelper.t(_activity, "11");
    }

    @Override
    public void onClickSms() {
        UIHelper.t(_activity, "11");
    }
}
