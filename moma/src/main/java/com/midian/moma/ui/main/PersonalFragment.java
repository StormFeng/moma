package com.midian.moma.ui.main;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.midian.login.view.LoginActivity;
import com.midian.moma.model.UserCenterBean;
import com.midian.moma.ui.advert.CooperationActivity;
import com.midian.moma.ui.personal.PersonInfoActivity;
import com.midian.moma.R;
import com.midian.moma.ui.personal.MessageActivity;
import com.midian.moma.ui.personal.MyBookingOrderActivity;
import com.midian.moma.ui.personal.MyConcernActivity;
import com.midian.moma.ui.personal.SettingActivity;
import com.midian.moma.ui.shopping.MyOrderActivity;
import com.midian.moma.utils.AppUtil;

import midian.baselib.api.ApiCallback;
import midian.baselib.base.BaseFragment;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;

/**
 * 个人中心
 *
 * @author chu
 */
public class PersonalFragment extends BaseFragment implements OnClickListener, ApiCallback {

    private BaseLibTopbarView topbar;
    private ImageView head;
    private TextView name_tv, phone_tv;
    private View my_order_ll, book_order_ll;
    private View personal_ll, allOrder_ll, booking_ll;
    private TextView payMoney, commit, audit, finish,book_audit,book_pass,book_reject;
    private View concern_ll,process_ll, message_ll, setting_ll;
    private TextView pay_count_tv,commit_count_tv,audit_count_tv, finish_count_tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_personal, null);

        initView(v);

        return v;
    }

    private void initView(View v) {
        topbar = (BaseLibTopbarView) v.findViewById(R.id.topbar);
//        topbar.setMode(BaseLibTopbarView.MODE_WITHOUT_INPUT);
        topbar.recovery().setTitle("个人中心");

        head = (ImageView) v.findViewById(R.id.head);
        name_tv = (TextView) v.findViewById(R.id.name);
        phone_tv = (TextView) v.findViewById(R.id.phone);
        personal_ll = v.findViewById(R.id.personal_ll);
        my_order_ll = v.findViewById(R.id.my_order_ll);
        book_order_ll = v.findViewById(R.id.book_order_ll);
        allOrder_ll = v.findViewById(R.id.allOrder_ll);
        booking_ll = v.findViewById(R.id.booking_ll);
        concern_ll = v.findViewById(R.id.concern_ll);
        process_ll = v.findViewById(R.id.process_ll);
        message_ll = v.findViewById(R.id.message_ll);
        setting_ll = v.findViewById(R.id.setting_ll);

        payMoney = (TextView) v.findViewById(R.id.payMoney);
        commit = (TextView) v.findViewById(R.id.commit);
        audit = (TextView) v.findViewById(R.id.audit);
        finish = (TextView) v.findViewById(R.id.finish);

        book_audit = (TextView) v.findViewById(R.id.book_audit);
        book_pass = (TextView) v.findViewById(R.id.book_pass);
        book_reject = (TextView) v.findViewById(R.id.book_reject);

        pay_count_tv = (TextView) v.findViewById(R.id.pay_count_tv);
        commit_count_tv = (TextView) v.findViewById(R.id.commit_count_tv);
        audit_count_tv = (TextView) v.findViewById(R.id.audit_count_tv);
        finish_count_tv = (TextView) v.findViewById(R.id.finish_count_tv);


        personal_ll.setOnClickListener(this);
        allOrder_ll.setOnClickListener(this);
        booking_ll.setOnClickListener(this);
        concern_ll.setOnClickListener(this);
        process_ll.setOnClickListener(this);
        message_ll.setOnClickListener(this);
        setting_ll.setOnClickListener(this);

        payMoney.setOnClickListener(this);
        commit.setOnClickListener(this);
        audit.setOnClickListener(this);
        finish.setOnClickListener(this);

        book_audit.setOnClickListener(this);
        book_pass.setOnClickListener(this);
        book_reject.setOnClickListener(this);

//        loadData();

    }

    private void loadData() {
        AppUtil.getMomaApiClient(ac).userCenter(this);
    }


    @Override
    public void onResume() {
        super.onResume();

        if (ac.isAccess()) {
            if (!TextUtils.isEmpty(ac.getProperty("head_pic"))) {
                ac.setImage(head, ac.getProperty("head_pic"));
            } else {
                ac.setImage(head, R.drawable.head1);
            }
            if (!TextUtils.isEmpty(ac.name)) {
                name_tv.setText(ac.getProperty("name"));
            } else {
                name_tv.setText("");
            }

            //2：会员，3：销售员
            if ("2".equals(ac.user_type)) {
                my_order_ll.setVisibility(View.VISIBLE);
                book_order_ll.setVisibility(View.GONE);
            } else {
                my_order_ll.setVisibility(View.GONE);
                book_order_ll.setVisibility(View.VISIBLE);
            }

            phone_tv.setVisibility(View.VISIBLE);
            String phone_num = ac.getProperty("account");
            phone_tv.setText(phone_num.substring(0, 3) + "****" + phone_num.substring(7, phone_num.length()));
            loadData();
        } else {
            ac.clearUserInfo();
            name_tv.setText("未登陆");
            phone_tv.setVisibility(View.GONE);
            ac.setImage(head, R.drawable.head1);
        }
    }

    @Override
    public void onClick(View v) {
        Bundle mBundle = new Bundle();
        if (!ac.isAccess() && v.getId() != R.id.setting_ll) {
            UIHelper.jump(_activity, LoginActivity.class);
            return;
        }
        switch (v.getId()) {

            case R.id.personal_ll://个人中心
                UIHelper.jump(_activity, PersonInfoActivity.class);
                break;

            case R.id.allOrder_ll://我的订单
                mBundle.putInt("position", 0);
                UIHelper.jumpForResult(_activity, MyOrderActivity.class, mBundle,10088);
                break;
            case R.id.payMoney://待付款
                mBundle.putInt("position", 1);
                UIHelper.jumpForResult(_activity, MyOrderActivity.class, mBundle,10088);
                break;
            case R.id.commit://待提交
                mBundle.putInt("position", 2);
                UIHelper.jumpForResult(_activity, MyOrderActivity.class, mBundle,10088);
                break;
            case R.id.audit://待审核
                mBundle.putInt("position", 3);
                UIHelper.jumpForResult(_activity, MyOrderActivity.class, mBundle,10088);
                break;
            case R.id.finish://已完成
                mBundle.putInt("position", 4);
                UIHelper.jumpForResult(_activity, MyOrderActivity.class, mBundle,10088);
                break;
            case R.id.booking_ll://我的预订
                mBundle.putInt("position", 0);
                UIHelper.jumpForResult(_activity, MyBookingOrderActivity.class,mBundle,10088);
                break;
            case R.id.book_audit://预订--待审核
                mBundle.putInt("position", 1);
                UIHelper.jumpForResult(_activity, MyBookingOrderActivity.class,mBundle,10088);
                break;
            case R.id.book_pass://预订--已通过
                mBundle.putInt("position", 2);
                UIHelper.jumpForResult(_activity, MyBookingOrderActivity.class,mBundle,10088);
                break;
            case R.id.book_reject://预订--已通过
                mBundle.putInt("position", 3);
                UIHelper.jump(_activity, MyBookingOrderActivity.class,mBundle);
                break;
            case R.id.concern_ll://我的关注
                UIHelper.jumpForResult(_activity, MyConcernActivity.class,10087);
                break;
            case R.id.process_ll://合作流程
                Bundle bundle = new Bundle();
                bundle.putString("title", "合作流程");
                UIHelper.jump(_activity, CooperationActivity.class, bundle);
                break;
            case R.id.message_ll://通知消息
                UIHelper.jump(_activity, MessageActivity.class);
                break;
            case R.id.setting_ll://设置
                UIHelper.jump(_activity, SettingActivity.class);
                break;
        }
    }

    @Override
    public void onApiStart(String tag) {

    }

    @Override
    public void onApiLoading(long count, long current, String tag) {

    }

    @Override
    public void onApiSuccess(NetResult res, String tag) {
        if (res.isOK()) {
            UserCenterBean bean = (UserCenterBean) res;
            String wait_pay_count = bean.getContent().getWait_pay_count();
            String wait_commit_count = bean.getContent().getWait_commit_count();
            String wait_audit_count = bean.getContent().getWait_audit_count();
            String complete_count = bean.getContent().getComplete_count();

            if (wait_pay_count == null || wait_pay_count.equals("") || wait_pay_count.equals("0")) {
                pay_count_tv.setVisibility(View.GONE);
            } else {
                pay_count_tv.setVisibility(View.VISIBLE);
                pay_count_tv.setText(bean.getContent().getWait_pay_count());
            }
            if (wait_commit_count == null || wait_commit_count.equals("") || wait_commit_count.equals("0")) {
                commit_count_tv.setVisibility(View.GONE);
            } else {
                commit_count_tv.setVisibility(View.VISIBLE);
                commit_count_tv.setText(wait_commit_count);
            }
            if (wait_audit_count == null || wait_audit_count.equals("") || wait_audit_count.equals("0")) {
                audit_count_tv.setVisibility(View.GONE);
            } else {
                audit_count_tv.setVisibility(View.VISIBLE);
                audit_count_tv.setText(wait_audit_count);
            }
            if (complete_count == null || complete_count.equals("") || complete_count.equals("0")) {
                finish_count_tv.setVisibility(View.GONE);
            } else {
                finish_count_tv.setVisibility(View.VISIBLE);
                finish_count_tv.setText(complete_count);
            }
        }
    }

    @Override
    public void onApiFailure(Throwable t, int errorNo, String strMsg, String tag) {

    }

    @Override
    public void onParseError(String tag) {

    }
}
