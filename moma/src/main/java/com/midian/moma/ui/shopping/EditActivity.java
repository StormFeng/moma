package com.midian.moma.ui.shopping;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.midian.moma.R;

import midian.baselib.base.BaseActivity;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;

/**
 * Created by chu on 2015/12/28.
 */
public class EditActivity extends BaseActivity {

    private BaseLibTopbarView topbar;
    private EditText content_ed;
    private View invoiceType;
    private TextView personal, company;
    private String title;
    private String content;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        title = mBundle.getString("title");
        invoiceType = findView(R.id.invoiceType);
        content = mBundle.getString("content");
//        System.out.println("修改接收的content：：："+content);
        topbar = findView(com.midian.login.R.id.topbar);
        topbar.setTitle(title).setLeftImageButton(R.drawable.icon_back, null) .setLeftText("返回", UIHelper.finish(_activity)).setRightText("完成", this);
        personal = findView(R.id.personal);
        company = findView(R.id.company);
        personal.setOnClickListener(this);
        company.setOnClickListener(this);
        content_ed = findView(R.id.content_et);
        content_ed.setHint(content);

       /* if ("请输入公司名称".equals(mBundle.getString("content"))) {
            content_ed.setHint("请输入公司名称");
        } else {

        }
        if ("请输入公司地址".equals(mBundle.getString("content"))) {
            content_ed.setHint("请输入公司地址");
        } else {
            content_ed.setHint(content);
        }*/


        if ("请选择发票抬头类型".equals(content)) {
            content_ed.setVisibility(View.GONE);
            invoiceType.setVisibility(View.VISIBLE);
        }

        if ("请输入发票抬头".equals(content)) {
            content_ed.setHint("请输入" + title);
        }
        try {
            // 如果为isPhone,则只能输入数字
            if (mBundle.getBoolean("isPhone")) {
                // 设置限制输入的数字长度
//                content_ed.setSelection(content.length());
                content_ed.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
                content_ed.setInputType(InputType.TYPE_CLASS_PHONE);
//                System.out.println("初始化后的content：：：" + content);
            }
        } catch (Exception e) {
        }

    }


    @Override
    public void onClick(View v) {
        preferences = getSharedPreferences("componey", Context.MODE_PRIVATE);
        Bundle bundle = new Bundle();
        super.onClick(v);
        switch (v.getId()) {
            case R.id.right_tv:
                // 把获取到的输入内容返回前一页
                if (!TextUtils.isEmpty(content_ed.getText().toString().trim())) {
                    content = content_ed.getText().toString().trim();
                }

                if ("请输入公司名称".equals(mBundle.getString("content"))) {
                    editor = preferences.edit();
                    editor.putString("company_name", content);
                    editor.commit();
                }
                if ("请输入公司地址".equals(mBundle.getString("content"))) {
                    editor = preferences.edit();
                    editor.putString("company_address", content);
                    editor.commit();
                }
                bundle.putString("content", content);
                setResult(RESULT_OK, bundle);
                finish();
                break;
            case R.id.personal:
                content = "1";
                bundle.putString("content", content);
                setResult(RESULT_OK, bundle);
                finish();
                break;
            case R.id.company:
                content = "2";
                bundle.putString("content", content);
                setResult(RESULT_OK, bundle);
                finish();
                break;
        }
    }
}
