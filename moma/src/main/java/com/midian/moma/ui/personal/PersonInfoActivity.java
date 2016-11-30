package com.midian.moma.ui.personal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.midian.configlib.ServerConstant;
import com.midian.login.R;
import com.midian.login.api.LoginApiClient;
import com.midian.login.bean.UpdateUserBean;
import com.midian.login.bean.UserDetailBean;
import com.midian.login.view.ChooseSexActivity;
import com.midian.login.view.EditPersonInfoActivity;
import com.midian.moma.ui.home.ChooseCitysActivity;

import java.io.File;
import java.io.FileNotFoundException;

import midian.baselib.base.BasePicActivity;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;
import midian.baselib.widget.CircleImageView;

/**
 * 个人资料
 *
 * @author chu
 */
public class PersonInfoActivity extends BasePicActivity {

    public static final String TYPE = "type";
    public static final String NAME = "name";

    private BaseLibTopbarView topbar;
    private View head_ll, name_ll, phone_l, phone_ll, sex_ll, area_ll, adress_ll;
    private CircleImageView head_iv;

    private TextView name_tv, phone_tv, phone, sex_tv, area_tv, adress_tv;
    private TextView head_hint;
    private UserDetailBean mUserDetailBean;
    private File mHead = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);

        init();
        loadData();


    }

    private void loadData() {
        ac.api.getApiClient(LoginApiClient.class).getUserDetail(this);
    }

    private void init() {
        topbar = (BaseLibTopbarView) findViewById(R.id.topbar);
        topbar.setTitle("个人资料").setLeftImageButton(R.drawable.icon_back, null)
                .setLeftText("返回", UIHelper.finish(_activity));
        head_iv = findView(R.id.head_iv);
        name_tv = findView(R.id.user_name);
        phone_tv = findView(R.id.user_phone);
        phone = findView(R.id.phone);
        sex_tv = findView(R.id.sex);
        area_tv = findView(R.id.area);
        adress_tv = findView(R.id.adress);
        head_hint = findView(R.id.head_hint);

        head_ll = findViewById(R.id.head_ll);
        name_ll = findViewById(R.id.name_ll);
        phone_l = findViewById(R.id.phone_l);
        phone_ll = findViewById(R.id.phone_ll);
        sex_ll = findViewById(R.id.sex_ll);
        area_ll = findViewById(R.id.area_ll);
        adress_ll = findViewById(R.id.adress_ll);


        head_ll.setOnClickListener(this);
        name_ll.setOnClickListener(this);
        phone_ll.setOnClickListener(this);
        sex_ll.setOnClickListener(this);
        area_ll.setOnClickListener(this);
        adress_ll.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Bundle mBundle = new Bundle();
        super.onClick(v);
        int id = v.getId();
        if (id == R.id.head_ll) {
            takePhoto();
        } else if (id == R.id.name_ll) {
            mBundle.putString("title", "姓名");
            mBundle.putString("content", name_tv.getText().toString().trim());
            UIHelper.jumpForResult(_activity, EditPersonInfoActivity.class, mBundle, 1001);
        } else if (id == R.id.phone_ll) {//可修改的联系电话
            mBundle.putString("title", "联系电话");
            mBundle.putString("content", phone.getText().toString().trim());
            mBundle.putBoolean("isPhone", true);// 设置为数字
            UIHelper.jumpForResult(_activity, EditPersonInfoActivity.class, mBundle, 1002);
//            UIHelper.jumpForResult(_activity, EditPhoneActivity.class, 1002);
        } else if (id == R.id.sex_ll) {
            mBundle.putString("title", "性别");
            mBundle.putString("content", sex_tv.getText().toString().trim());
            UIHelper.jumpForResult(_activity, ChooseSexActivity.class, mBundle, 1003);
        } else if (id == R.id.area_ll) {
            mBundle.putString("title", "选择城市");
            mBundle.putString("content", area_tv.getText().toString().trim());
            UIHelper.jumpForResult(_activity, ChooseCitysActivity.class, mBundle, 1004);
        } else if (id == R.id.adress_ll) {
            mBundle.putString("title", "联系地址");
            mBundle.putString("content", adress_tv.getText().toString().trim());
            UIHelper.jumpForResult(_activity, EditPersonInfoActivity.class, mBundle, 1005);
        } else {
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1001:
                    name_tv.setText(data.getExtras().getString("content"));
                    ac.setProperty("name", data.getExtras().getString("content"));
                    break;
                case 1002:
                    phone.setText(data.getExtras().getString("content"));
                    break;
                case 1003:
                    if ("1".equals(data.getExtras().getString("sex"))) {
                        sex_tv.setText("男");
                    } else {
                        sex_tv.setText("女");
                    }
                    ac.setProperty("sex", data.getExtras().getString("sex"));
                    break;
                case 1004:
                    // area_tv.setText(data.getExtras().getString("city" + "area"));
                    area_tv.setText("选择得到的城市");
                    // ac.api...
                    break;
                case 1005:
                    adress_tv.setText(data.getExtras().getString("content"));
                    ac.setProperty("address", data.getExtras().getString("content"));
                    break;
            }
        }

    }

    @Override
    public void outputBitmap(Bitmap bitmap, String path) {
        super.outputBitmap(bitmap, path);
        head_iv.setImageBitmap(bitmap);
        mHead = new File(path);
        try {
            ac.api.getApiClient(LoginApiClient.class).updateUser(mHead, null, null, null, null, this);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
            if ("getUserDetail".equals(tag)) {
                render(res);
            }
            if ("updateUser".equals(tag)) {
                UIHelper.t(_activity, "头像修改成功");
                UpdateUserBean mUpdateUserBean = (UpdateUserBean) res;
                ac.setProperty("head_pic", mUpdateUserBean.getContent().getHead_pic());
            }
        } else {
            ac.handleErrorCode(_activity, res.error_code);
        }

    }

    private void render(NetResult res) {
        if (res != null) {
            mUserDetailBean = (UserDetailBean) res;
            ac.saveUserInfo(ac.user_id, ac.access_token, mUserDetailBean.getContent().getName(), ac.user_type
                    , mUserDetailBean.getContent().getHead_pic(), mUserDetailBean.getContent().getPhone()
                    , mUserDetailBean.getContent().getSex(), mUserDetailBean.getContent().getAddress());

            if (TextUtils.isEmpty(mUserDetailBean.getContent().getHead_pic())) {
                head_iv.setBackgroundResource(R.drawable.head1);
            } else {
                head_hint.setVisibility(View.GONE);
                ac.setImage(head_iv, ServerConstant.BASEFILEURL + mUserDetailBean.getContent().getHead_pic());
            }
            name_tv.setText(ac.getProperty("name"));
            String phone_num = mUserDetailBean.getContent().getMobile_phone();
            phone_tv.setText(phone_num.substring(0, 3) + "****" + phone_num.substring(7, phone_num.length()));
            if ("1".equals(mUserDetailBean.getContent().getSex())) {
                sex_tv.setText("男");
            } else if ("2".equals(mUserDetailBean.getContent().getSex())) {
                sex_tv.setText("女");
            }
            if (TextUtils.isEmpty(mUserDetailBean.getContent().getPhone())) {
                phone.setText("未填写联系电话");
            } else {
                phone.setText(mUserDetailBean.getContent().getPhone());
            }

            if (TextUtils.isEmpty(mUserDetailBean.getContent().getAddress())) {
                adress_tv.setText("未填写地址");
            } else {
                adress_tv.setText(ac.address);
            }

        }
    }
}
