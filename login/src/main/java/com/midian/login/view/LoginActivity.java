package com.midian.login.view;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.midian.login.R;
import com.midian.login.api.LoginApiClient;
import com.midian.login.bean.LoginBean;
import com.midian.login.bean.SaveDeviceBean;
import com.midian.login.utils.ObjectAnimatorTools;
import com.midian.login.utils.ValidateTools;

import midian.baselib.app.AppManager;
import midian.baselib.base.BaseActivity;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;

/**
 * 登陆
 */
public class LoginActivity extends BaseActivity {
    EditText phone_no_et, password_et;
    private Button login_btn;
    private LinearLayout ll_phone, ll_password;
    private float exitTime;
    private BaseLibTopbarView topbar;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);
        view = findView(R.id.view);
        login_btn = (Button) findViewById(R.id.login_btn);
        ll_phone = (LinearLayout) findViewById(R.id.ll_phone);
        ll_password = (LinearLayout) findViewById(R.id.ll_password);

        view.setOnClickListener(this);
        login_btn.setOnClickListener(this);
        findViewById(R.id.qq).setOnClickListener(this);
        findViewById(R.id.weixin).setOnClickListener(this);
        findViewById(R.id.forget_password_tv).setOnClickListener(this);
        findViewById(R.id.register_btn).setOnClickListener(this);
        phone_no_et = (EditText) findViewById(R.id.phone_no_et);
        password_et = (EditText) findViewById(R.id.password_et);

        initTitle();
        if (ac.isAccess()) {
            finish();
        }
    }


    private void initTitle() {
        topbar = (BaseLibTopbarView) findViewById(R.id.topbar);
        try {
            topbar.setLeftText(R.string.back, null).setLeftImageButton(R.drawable.icon_back, UIHelper.finish(_activity))
                    .setTitle(R.string.login).setMode(BaseLibTopbarView.MODE_1);
            topbar.setRightText("注册", this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        try {
            String phone = phone_no_et.getText().toString().trim();
            String pwd = password_et.getText().toString().trim();

            int id = v.getId();
            if (id == R.id.login_btn) {
                if (ValidateTools.isEmptyString(phone)) {
                    ObjectAnimatorTools.onVibrationView(ll_phone);
                    UIHelper.t(this, R.string.hint_account_not_empty);
                    return;
                }
                if (!ValidateTools.isPhoneNumber(phone)) {
                    ObjectAnimatorTools.onVibrationView(ll_phone);
                    UIHelper.t(this, R.string.hint_account_error);
                    return;
                }
                if (ValidateTools.isEmptyString(pwd)) {
                    ObjectAnimatorTools.onVibrationView(ll_password);
                    UIHelper.t(this, R.string.hint_pwd_not_empty);
                    return;
                }
                showLoadingDlg();
                login_btn.setClickable(false);
                ac.saveAccount(phone);// 保存账号
                ac.savePassword(pwd);// 保存密码
                ac.api.getApiClient(LoginApiClient.class).login(phone, pwd,
                        ac.device_token, this);
            } else if (id == R.id.forget_password_tv) {
                UIHelper.jump(_activity, ForgetPasswordOneActivity.class);
            } else if (id == R.id.qq) {// qq登录

            } else if (id == R.id.weixin) {// weixin 登录

            } else if (id == R.id.register_btn) {//注册
                UIHelper.jump(_activity, RegisterOneActivity.class);
            } else if (id == R.id.view) {//取消输入框焦点
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            } else if (id == R.id.right_tv) {
                UIHelper.jump(_activity, RegisterOneActivity.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String is_receive = "";
    @Override
    public void onApiSuccess(NetResult res, String tag) {
        super.onApiSuccess(res, tag);
        hideLoadingDlg();
        login_btn.setClickable(true);
        if (res.isOK()) {
            if (tag.equals("login") || tag.equals("thirdUserLogin")) {
                UIHelper.t(_activity, "登陆成功");
                LoginBean item = (LoginBean) res;
                ac.saveUserInfo(item.getContent().getUser_id()
                        , item.getContent().getAccess_token()
                        , item.getContent().getName()
                        , item.getContent().getUser_type()
                        , item.getContent().getHead_pic(), "", "", "");
                System.out.println("登陆后device_token::::" + ac.device_token);//3729802399301311275
                if (TextUtils.isEmpty(ac.device_token)) {
                    setResult(RESULT_OK);
                    finish();
                } else {
                    ac.api.getApiClient(LoginApiClient.class).saveDevice(ac.device_token, this);
                }

            }

            if ("saveDevice".equals(tag)) {
                SaveDeviceBean mSaveDevice = (SaveDeviceBean) res;
                is_receive = mSaveDevice.getContent().getIs_receive();
                System.out.println("发送设备号成功的状态;::"+is_receive);
                if ("0".equals(is_receive)) {
                    ac.api.getApiClient(LoginApiClient.class).updateReceiveStatus("1", this);
                } else {
                    ac.isClosePush("1".equals(is_receive));
                    System.out.println("saveDevice登陆成功提交设备号后推送接收状态::::" + ac.getBoolean("isClosePush"));
                    setResult(RESULT_OK);
                    finish();
                }

            }
            if ("updateReceiveStatus".equals(tag)) {
                ac.isClosePush("1".equals(is_receive));
                System.out.println("updateReceiveStatus登陆成功提交设备号后推送接收状态::::" + ac.getBoolean("isClosePush"));
                setResult(RESULT_OK);
                finish();
            }


        } else {
            ac.handleErrorCode(_activity, res.error_code);
        }


    }

    // @Override
    // protected void onActivityResult(int requestCode, int resultCode, Intent
    // data) {
    // super.onActivityResult(requestCode, resultCode, data);
    // UMengLoginUtil.getInstance(this).onActivityResult(requestCode,
    // resultCode, data);
    // if (resultCode != RESULT_OK) {
    // hideLoadingDlg();
    // }
    // }

    @Override
    public void onApiFailure(Throwable t, int errorNo, String strMsg, String tag) {
        super.onApiFailure(t, errorNo, strMsg, tag);

        hideLoadingDlg();
        login_btn.setClickable(true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            UIHelper.t(getApplicationContext(), R.string.exit_text);
            exitTime = System.currentTimeMillis();
        } else {
            AppManager.getAppManager().appExit(this);
            finish();
        }
    }

}
