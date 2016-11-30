package com.midian.moma.ui.shopping;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.midian.configlib.ServerConstant;
import com.midian.moma.R;
import com.midian.moma.model.OrderDetailBean;
import com.midian.moma.ui.advert.UploadActivity;
import com.midian.moma.utils.AppUtil;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import midian.baselib.base.BaseActivity;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.FDDataUtils;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;

/**
 * 订单详情
 *
 * @author chu
 */
public class OrderDetailActivity extends BaseActivity {

    private BaseLibTopbarView topbar;
    private TextView order_num, order_time, status_tv;// 订单号、时间、状态
    private TextView name, phone, company, company_adress;// 名、电话、公司名、地址
    private ImageView image;// 广告位图片
    private TextView title, total_price, pay_money, discount_money, time;// 标题 商品价钱 商品金额 优惠 租用时间
    private TextView actual_payment;//实付款
    private TextView order_explain;// 订单说明
    private View condition_ll, image_ll;// 客户广告要求View,图片View
    private TextView images;// 图片组
    private TextView condition;// 最后的要求内容
    private View view;
    private Button cancel, button;
    private TextView type_name, loc_name;
    private String type;
    private String ad_id, order_id, status, orderType, start_time, end_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        type = mBundle.getString("type");
        order_id = mBundle.getString("order_id");
        initView();
        loadData();
    }

    private void loadData() {
        AppUtil.getMomaApiClient(ac).orderDetail(order_id, this);
    }

    private void initView() {
        topbar = findView(R.id.topbar);
        topbar.setTitle("订单详情").setLeftImageButton(R.drawable.icon_back, null).setLeftText("返回",
                UIHelper.finish(_activity));


        order_num = findView(R.id.order_num);
        order_time = findView(R.id.order_time);
        status_tv = findView(R.id.status);
        name = findView(R.id.name);
        phone = findView(R.id.phone);
        company = findView(R.id.company);//公司名称
        company_adress = findView(R.id.company_adress);//公司地址
        image = findView(R.id.image);//广告位图片
        title = findView(R.id.title);//广告位标题
        total_price = findView(R.id.total_price);//多少钱/月
        pay_money = findView(R.id.pay_money);//商品金额
        discount_money = findView(R.id.discount_money);//优惠
        time = findView(R.id.time);//租用时间
        order_explain = findView(R.id.order_explain);// 订单说明
        type_name = findView(R.id.type_name);
        loc_name = findView(R.id.loc_name);

        condition_ll = findView(R.id.condition_ll);
        condition = findView(R.id.condition);// 最后的要求内容
        view = findView(R.id.view);
        actual_payment = findView(R.id.actual_payment);//实付款

        cancel = findView(R.id.cancel);
        button = findView(R.id.button);
        cancel.setOnClickListener(this);
        button.setOnClickListener(this);


        //2：会员，3：销售员
        if ("2".equals(ac.user_type)) {
            orderType = "1"; //1：普通订单，2：预定订单
        } else if ("3".equals(ac.user_type)) {
            orderType = "2";//1：普通订单，2：预定订单
        }
    }


    /**
     * 渲染数据
     *
     * @param bean
     */
    private void render(OrderDetailBean bean) {
        if (bean == null) {
            return;
        } else {
            order_num.setText("订单编号：" + bean.getContent().getOrder_sn());
            order_time.setText("订单时间：" + bean.getContent().getOrder_time());
            order_id = bean.getContent().getOrder_id();
            ad_id = bean.getContent().getAd_child_id();
            start_time = bean.getContent().getStart_time();
            end_time = bean.getContent().getEnd_time();
            name.setText(bean.getContent().getUser_name());
            phone.setText(bean.getContent().getUser_phone());
            company.setText(bean.getContent().getCompany_name());
            company_adress.setText(bean.getContent().getCompany_address());

            if (TextUtils.isEmpty(bean.getContent().getAd_pic_name())) {
                ac.setImage(image, R.drawable.icon_bg);
            } else {
                ac.setImage(image, ServerConstant.BASEFILEURL + bean.getContent().getAd_pic_name());
            }
            title.setText(bean.getContent().getAd_title());
            total_price.setText(bean.getContent().getUnit_price() + "/月");//多少钱/月
            pay_money.setText("¥ " +new BigDecimal(bean.getContent().getTotal_price()).toPlainString());//商品金额
            discount_money.setText("¥ " + new BigDecimal(bean.getContent().getDiscount_money()).toPlainString());//优惠
            type_name.setText(bean.getContent().getType_name());
            loc_name.setText(bean.getContent().getPosition_name());
            time.setText(bean.getContent().getStart_time() + " --- " + bean.getContent().getEnd_time());
            actual_payment.setText("¥ " + new BigDecimal(bean.getContent().getPay_money()).toPlainString());//实际付款
            status = bean.getContent().getOrder_status();
            //order_status;//订单状态。0：全部，1：待付款，2：待提交，3待审核，4已完成”
            // type1表示待付款
            if (type.equals("1")) {
                status_tv.setText("待付款");
                order_explain.setText("10:00后订单将自动取消，请尽快支付，以避免须再次下单。");
                view.setVisibility(View.VISIBLE);
                condition_ll.setVisibility(View.GONE);
            }
            // type2表示待提交
            if (type.equals("2")) {
                status_tv.setText("待提交");
                order_explain.setText("请按要求上传资料，如遇问题可联系我们的客服：400-8888-888");
                view.setVisibility(View.VISIBLE);
                condition_ll.setVisibility(View.GONE);
                cancel.setVisibility(View.GONE);
                button.setText("上传资料");
            }
            // type3表示待审核
            if (type.equals("3")) {
                status_tv.setText("待审核");
                order_explain.setText("审核完成前，您可以修改上传的资料，审核完成后将不可修改广告资料");
                cancel.setVisibility(View.GONE);
                button.setText("编辑资料");
            }
            // type2表示已完成
            if (type.equals("4")) {
                status_tv.setText("已完成");
                order_explain.setText("您的广告位已上架");
                cancel.setVisibility(View.GONE);
                button.setText("已完成");
                button.setClickable(false);
                button.setBackgroundResource(R.drawable.window_bg_ellipse);
            }


            //从预订入口显示的界面
            if (type.equals("5")) {
                //待审核
                status_tv.setText("待审核");
                order_explain.setText("审核完成前，您可以修改上传的资料，审核完成后将不可修改广告资料");
                cancel.setVisibility(View.VISIBLE);
                button.setVisibility(View.GONE);
            } else if (type.equals("6")) {
                //已通过
                status_tv.setText("已通过");
                order_explain.setText("审核完成前，您可以修改上传的资料，审核完成后将不可修改广告资料");
                cancel.setVisibility(View.VISIBLE);
                button.setVisibility(View.GONE);
            } else if (type.equals("7")) {
                //已拒绝
                status_tv.setText("已拒绝");
                order_explain.setText("审核完成前，您可以修改上传的资料，审核完成后将不可修改广告资料");
                cancel.setVisibility(View.GONE);
                button.setVisibility(View.GONE);
            }


        }
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.cancel:
                AppUtil.getMomaApiClient(ac).cancleOrder(order_id, this);
                break;
            case R.id.button:
                //status;//订单状态。0：全部，1：待付款，2：待提交，3待审核，4已完成”
                if ("1".equals(status)) {
                    mBundle.putString("order_id", order_id);
                    mBundle.putString("buyType", "3");//1为购物车入口/2为广告位详情传参(即立即购买)入口，3为我的订单及推送入口
                    mBundle.putString("ad_id", ad_id);
                    mBundle.putString("start_time", start_time);
                    mBundle.putString("end_time", end_time);
                    mBundle.putString("orderType", orderType);
                    UIHelper.jump(_activity, ConfirmOrderActivity.class, mBundle);// 生成确认订单接口

                } else if ("2".equals(status)) {
                    mBundle.putString("type", "2");
                    mBundle.putString("order_id", order_id);
                    UIHelper.jump(_activity, UploadActivity.class, mBundle);
                } else if ("3".equals(status)) {
                    status_tv.setText("待审核");
                    button.setText("编辑资料");
                    mBundle.putString("type", "3");//3表示编辑资料入口
                    mBundle.putString("order_id", order_id);
                    UIHelper.jump(_activity, UploadActivity.class, mBundle);
                } else if ("4".equals(status)) {
                    status_tv.setText("已完成");
                    button.setText("已完成");
                    button.setEnabled(false);
                }
                break;
        }
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
            if ("orderDetail".equals(tag)) {
                OrderDetailBean bean = (OrderDetailBean) res;
                render(bean);
            }

            if ("cancleOrder".equals(tag)) {
                UIHelper.t(_activity, "取消订单成功");
                finish();
            }
        } else {
            ac.handleErrorCode(_activity, res.error_code);
        }
    }


}
