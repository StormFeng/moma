package com.midian.login.view;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.midian.login.R;
import com.midian.login.api.LoginApiClient;
import com.midian.login.utils.ObjectAnimatorTools;
import com.midian.login.utils.ValidateTools;

import midian.baselib.base.BaseActivity;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.Func;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;

/**
 * 修改密码第一步
 */
public class ModifyPwdOneActivity extends BaseActivity implements OnClickListener {
    private CountDownTimer mCountDownTimer;
    private BaseLibTopbarView mBaseLibTopbarView;
    private LinearLayout ll_account_et, ll_auth_code_et;
    private String  code;
    private EditText auth_code_et;
    private TextView  phone_tv;
    private Button send_code_bt, next_btn;
    private String phone_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_verify);
        mBaseLibTopbarView = (BaseLibTopbarView) findViewById(R.id.topbar_note);
        mBaseLibTopbarView.setLeftText("返回", null).setLeftImageButton(R.drawable.icon_back, UIHelper.finish(_activity))
                .setTitle("修改密码");
        initView();
    }

    private void initView() {
//        account_tv = (TextView) findViewById(R.id.phone);
        auth_code_et = (EditText) findViewById(R.id.auth_code_et);
        next_btn = (Button) findViewById(R.id.next_btn);
        send_code_bt = (Button) findViewById(R.id.send_code_bt);
        ll_account_et = (LinearLayout) findViewById(R.id.ll_account_et);
        ll_auth_code_et = (LinearLayout) findViewById(R.id.ll_auth_code_et);
        phone_tv = findView(R.id.phone);
//        account_et.setOnClickListener(this);
//        auth_code_et.setOnClickListener(this);
        next_btn.setOnClickListener(this);
        send_code_bt.setOnClickListener(this);
        phone_num = ac.getProperty("account");
        phone_tv.setText(phone_num.substring(0, 3) + "****" + phone_num.substring(7, phone_num.length()));

    }

    @Override
    public void onClick(View v) {
        String type = "3";//1：注册，2：忘记密码，3：其他
        code = auth_code_et.getText().toString().trim();
        // UIHelper.jump(this, ModifyPwdTwoActivity.class);
        int id = v.getId();
        if (id == R.id.next_btn) {// 下一步
            if (ValidateTools.isEmptyString(phone_num)) {
                ObjectAnimatorTools.onVibrationView(ll_account_et);
                UIHelper.t(this, R.string.hint_phone_not_empty);
                return;
            }
            if (!Func.isMobileNO(phone_num)) {
                ObjectAnimatorTools.onVibrationView(ll_account_et);
                UIHelper.t(this, R.string.hint_phone_error);
                return;
            }
            if (ValidateTools.isEmptyString(code)) {
                ObjectAnimatorTools.onVibrationView(ll_auth_code_et);
                UIHelper.t(this, R.string.hint_auth_code_not_empty);
                return;
            }
            ac.api.getApiClient(LoginApiClient.class).validateCode(phone_num, code,
                    this);// 验证验证码
            showLoadingDlg();
            next_btn.setClickable(false);
            // UIHelper.jump(_activity, ForgetPasswordTwoActivity.class);
        } else
            try {
                if (id == R.id.send_code_bt) {
                    if (ValidateTools.isEmptyString(phone_num)) {
                        ObjectAnimatorTools.onVibrationView(ll_account_et);
                        UIHelper.t(this, R.string.hint_phone_not_empty);
                        return;
                    }
                    if (!ValidateTools.isPhoneNumber(phone_num)) {
                        ObjectAnimatorTools.onVibrationView(ll_account_et);
                        UIHelper.t(this, R.string.hint_phone_error);
                        return;
                    }
                    ac.api.getApiClient(LoginApiClient.class).sendCode(phone_num, type, this);//发送验证码
                    showLoadingDlg();
                    next_btn.setClickable(false);
                    downTime();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

    }

    @Override
    public void onApiSuccess(NetResult res, String tag) {
        super.onApiSuccess(res, tag);
        hideLoadingDlg();
        next_btn.setClickable(true);
        if (res.isOK()) {
            if ("sendCode".equals(tag)) {
                UIHelper.t(_activity, "发送成功");
            }
            if ("validateCode".equals(tag)) {
                Bundle mBundle = new Bundle();
                mBundle.putString("phone", phone_num);
                UIHelper.jump(_activity, ModifyPwdTwoActivity.class, mBundle);
                finish();
            }
        } else {
            ac.handleErrorCode(_activity, res.error_code);
        }
    }

    @Override
    public void onApiFailure(Throwable t, int errorNo, String strMsg, String tag) {
        super.onApiFailure(t, errorNo, strMsg, tag);
        hideLoadingDlg();
        next_btn.setClickable(true);
        if ("validate_error".equals(errorNo)) {
            UIHelper.t(_activity, "验证失败");
        }
    }

    private void downTime() {
        mCountDownTimer = new CountDownTimer(59 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String timeText = getResources().getString(
                        R.string.hint_time_text);
                send_code_bt.setClickable(false);
                send_code_bt.setText(millisUntilFinished / 1000 + timeText);
            }

            @Override
            public void onFinish() {
                send_code_bt.setClickable(true);
                send_code_bt.setText(R.string.auth_code);
            }
        };
        mCountDownTimer.start();
    }
}
