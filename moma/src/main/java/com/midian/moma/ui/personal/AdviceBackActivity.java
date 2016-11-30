package com.midian.moma.ui.personal;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.midian.moma.R;
import com.midian.moma.utils.AppUtil;

import midian.baselib.base.BaseActivity;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;

/**
 * 意见反馈
 * Created by chu on 2015.11.24.024.
 */
public class AdviceBackActivity extends BaseActivity {
    private BaseLibTopbarView topbar;
    private EditText content_et;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice_back);

        topbar = findView(R.id.topbar);
        topbar.setTitle("意见反馈").setLeftImageButton(R.drawable.icon_back, null)
                .setLeftText("返回", UIHelper.finish(_activity));
        content_et = findView(R.id.content);
        content_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    content_et.setGravity(Gravity.LEFT | Gravity.TOP);
                    content_et.setHint("");
                } else {
                    content_et.setGravity(Gravity.CENTER);
                    content_et.setHint("请描述您遇到的问题或您的宝贵建议");
                }
            }
        });
        submit = findView(R.id.submit);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        String contents = content_et.getText().toString().trim();
        if ("".equals(contents)) {
            UIHelper.t(_activity, "请输入反馈内容！");
            return;
        }
        AppUtil.getMomaApiClient(ac).feedBack(contents, this);
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
            UIHelper.t(_activity, "反馈成功！感谢您的关注，我们会做的更优秀");
            finish();
        }
    }
}
