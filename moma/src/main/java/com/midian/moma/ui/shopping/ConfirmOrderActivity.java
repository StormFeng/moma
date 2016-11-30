package com.midian.moma.ui.shopping;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.android.app.lib.pay.PayUtil;
import com.midian.login.utils.ObjectAnimatorTools;
import com.midian.moma.R;
import com.midian.moma.itemview.ItemConfirmOrderTpl;
import com.midian.moma.itemview.ItemPayDetailOrderTpl;
import com.midian.moma.model.ConfirmOrderBean;
import com.midian.moma.model.PayDetailBean;
import com.midian.moma.model.ShowConfirmOrderBean;
import com.midian.moma.ui.advert.CooperationActivity;
import com.midian.moma.ui.advert.PaySucessActivity;
import com.midian.moma.utils.AppUtil;
import com.midian.moma.wxapi.WXPayEntryActivity;

import java.util.ArrayList;

import midian.baselib.base.BaseActivity;
import midian.baselib.bean.NetResult;
import midian.baselib.tooglebutton.ToggleButton;
import midian.baselib.utils.FDDataUtils;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;
import midian.baselib.widget.ListViewForScrollView;

/**
 * 确认订单
 *
 * @author chu
 */
public class ConfirmOrderActivity extends BaseActivity implements ToggleButton.OnToggleChanged {
    private BaseLibTopbarView topbar;
    private View name_ll, phone_ll, company_ll, adress_ll, invoice_ll, pay_ll;
    private TextView name, phone, company, adress;

    private TextView totalMoney, welf;//商品总额 优惠金额
    private ToggleButton toggle_button;
    private View type_ll, upHead_ll;
    private CheckBox alpay, wechat;
    private TextView type_tv, upHead_tv, notice_tv;
    private CheckBox select;//购买须知
    private ListViewForScrollView listView;//订单条目列表
    private LinearLayout liner_list;

    private TextView total, confirm;// 合计、总价、确认
    private ArrayList<ShowConfirmOrderBean.Advertisements> discount_list = new ArrayList<ShowConfirmOrderBean.Advertisements>();
    private ArrayList<PayDetailBean.Advertisements> payDetailDiscount_list = new ArrayList<PayDetailBean.Advertisements>();
    private ShowConfirmOrderBean bean;

    private String card_ids, ad_child_id, start_time, end_time, orderType;
    private int j = 0;//计算优惠金额
    private String pay_type;//支付方式、总价
    private String totalPrice,payMoney;//合计、支付的钱
    private String user_name, user_phone, user_company, user_address, invoice_type, invoice_title;
    private String buyType;
    private String order_id, order_sn;//订单id，订单编号


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        //2：会员，3：销售员
        if ("2".equals(ac.user_type)) {
            orderType = "1"; //1：普通订单，2：预定订单
        } else if ("3".equals(ac.user_type)) {
            orderType = "2";//1：普通订单，2：预定订单
        }


        buyType = mBundle.getString("buyType");
        //1为购物车传参、2为广告位详情立即购买传参;3、我的订单及推送入口的参数
        if ("1".equals(buyType)) {
            card_ids = mBundle.getString("card_ids");
            ad_child_id = null;
            start_time = null;
            end_time = null;
        } else /*if ("2".equals(buyType) && "3".equals(buyType))*/ {
            card_ids = null;
            ad_child_id = mBundle.getString("ad_id");//
            start_time = mBundle.getString("start_time");
            end_time = mBundle.getString("end_time");
        }


        if (mBundle.getString("order_id") != null) {
            order_id = mBundle.getString("order_id");
        } else {
            order_id = null;
        }

        if (mBundle.getString("order_sn") != null) {
            order_sn = mBundle.getString("order_sn");
        } else {
            order_sn = null;
        }

        initView();
    }

    private void initView() {
        topbar = findView(R.id.topbar);

        name = findView(R.id.name);
        phone = findView(R.id.phone);
        company = findView(R.id.company);
        adress = findView(R.id.adress);

        name_ll = findView(R.id.name_ll);
        phone_ll = findView(R.id.phone_ll);
        company_ll = findView(R.id.company_ll);
        adress_ll = findView(R.id.adress_ll);
        invoice_ll = findView(R.id.invoice_ll);//发票信息toggle_button  View
        pay_ll = findView(R.id.pay_ll);
        notice_tv = findView(R.id.notice);//广告须知text

        alpay = findView(R.id.alpay);
        wechat = findView(R.id.wechat);
        total = findView(R.id.total);//合计
        confirm = findView(R.id.confirm);
        listView = findView(R.id.item_list);//订单条目的list
        liner_list = (LinearLayout) findView(R.id.liner_list);

        toggle_button = findView(R.id.toggle_button);//发票开关
        toggle_button.setOnToggleChanged(this);
        type_ll = findView(R.id.type_ll);//发票抬头类型View
        upHead_ll = findView(R.id.upHead_ll);//发票抬头View
        type_tv = findView(R.id.type);//发票抬头类型text_tv
        upHead_tv = findView(R.id.upHead);////发票抬头text_tv
        totalMoney = findView(R.id.totalMoney);//商品金额
        welf = findView(R.id.welf);
        select = findView(R.id.select);

        name_ll.setOnClickListener(this);
        phone_ll.setOnClickListener(this);
        company_ll.setOnClickListener(this);
        adress_ll.setOnClickListener(this);
        type_ll.setOnClickListener(this);
        upHead_ll.setOnClickListener(this);

        alpay.setOnClickListener(this);
        wechat.setOnClickListener(this);
        confirm.setOnClickListener(this);
        select.setOnClickListener(this);
        notice_tv.setOnClickListener(this);

        //2：会员，3：销售员
        if ("2".equals(ac.user_type)) {
            topbar.setTitle("确认订单").setLeftImageButton(R.drawable.icon_back, null).setLeftText("返回",
                    UIHelper.finish(_activity));
        } else if ("3".equals(ac.user_type)) {
            topbar.setTitle("确认预订").setLeftImageButton(R.drawable.icon_back, null).setLeftText("返回",
                    UIHelper.finish(_activity));
            confirm.setText("预订");
            invoice_ll.setVisibility(View.GONE);
            pay_ll.setVisibility(View.GONE);
        }

        initPay();
        loadData();


    }

    private void initPay() {
        alpay.setChecked(true);
        wechat.setChecked(false);
        pay_type = "1";//支付方式：1：支付宝，2：微信
    }

    private void loadData() {
        if ("3".equals(buyType)) {
            //从我的订单或推送中入口过来确认订单支付，需要调7.8支付详情接口，以便重新获取订单编号，
            AppUtil.getMomaApiClient(ac).payOrderDetail(order_id, order_sn, this);//7.8支付详情接口
        } else {
            //购物车和立即购买中入口过来调用的生成确认订单接口
            AppUtil.getMomaApiClient(ac).showConfirmOrders(card_ids, ad_child_id, start_time, end_time, orderType, this);
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
            //7.6生成订单列表接口
            if ("showConfirmOrders".equals(tag)) {
                bean = (ShowConfirmOrderBean) res;
                discount_list = bean.getContent().getAdvertisements();
                for (ShowConfirmOrderBean.Advertisements i : discount_list) {
                    double d = FDDataUtils.getDouble(i.getDiscount_money());
                    j += d;
                    ItemConfirmOrderTpl m = new ItemConfirmOrderTpl(_activity);
//                    System.out.println("优惠的金额：：：getDiscount_money的和="+j+";;;;getDiscount_money的和="+d);
                    liner_list.addView(m);
                    m.setBean(i, 0);
                }
                render(bean);
            }

            //7.1确认订单接口--返回订单号
            if ("confirmAdvertisement".equals(tag) || "updateOrderSn".equals(tag)) {
                ConfirmOrderBean orderBean = (ConfirmOrderBean) res;
                Log.i("订单提交信息TAG:::", "提交订单成功，您的订单号为:" + orderBean.getContent().getOrder_no());
//                System.out.println("订单号为:::" + orderBean.getContent().getOrder_no() + "支付方式:::" + orderBean.getContent().getPay_type());
                order_sn = orderBean.getContent().getOrder_no();
                new PayUtil(_activity, new PayUtil.CallBack() {
                    @Override
                    public void complete(boolean stat) {
                        PaySucessActivity.gotoActivity(_activity, stat ? PaySucessActivity.SUCCESS : PaySucessActivity.FAILED, "1", order_sn, order_id, payMoney);
                    }
                }).pay(orderBean.getContent().getOrder_no(), payMoney, "moma", "moma");//总价totalPrice替换0.01

            }


            //预订订单
            if ("reserveAdvertisement".equals(tag)) {
                UIHelper.t(_activity, "预订成功");
                finish();
            }

            //7.8支付详情接口（用于重新支付：我的订单和推送入口支付）
            if ("payOrderDetail".equals(tag)) {
                PayDetailBean payDetailBean = (PayDetailBean) res;
//                System.out.println("支付详情接口返回的订单号为:::" + payDetailBean.getContent().getOrder_no());
                // 如果从我的订单列表入口，则订单编号order_sn响应为空，则先调用7.9更改订单编号的接口，再支付，如果order_sn响应不为空，则直接进行支付
                payDetailDiscount_list = payDetailBean.getContent().getAdvertisements();
                for (PayDetailBean.Advertisements i : payDetailDiscount_list) {
                    double d = FDDataUtils.getDouble(i.getDiscount_money());
                    j += d;
//                    System.out.println("优惠的金额：：：getDiscount_money="+j);
                    //ItemConfirmOrderTpl m = new ItemConfirmOrderTpl(_activity);
                    ItemPayDetailOrderTpl pdo = new ItemPayDetailOrderTpl(_activity);
                    liner_list.addView(pdo);
                    pdo.setBean(i, 0);
                }
                payOrderDetailRender(payDetailBean);//渲染界面
            }

//            //7.9更改订单编号响应
//            if ("updateOrderSn".equals(tag)) {
//                UpdateOrderSnBean updateOrderSnBean = (UpdateOrderSnBean) res;
//                System.out.println("7.9接口更改后的订单号为:::" + updateOrderSnBean.getContent().getOrder_no() + "支付方式:::" + updateOrderSnBean.getContent().getPay_type());
//
//                new PayUtil(_activity, new PayUtil.CallBack() {
//                    @Override
//                    public void complete(boolean stat) {
//                        PaySucessActivity.gotoActivity(_activity, stat ? PaySucessActivity.SUCCESS : PaySucessActivity.FAILED, order_id);
//                    }
//                }).pay(updateOrderSnBean.getContent().getOrder_no(), "0.01", "moma", "moma");//总价totalPrice替换0.01
//            }


        } else {
            ac.handleErrorCode(_activity, res.error_code);
            if ("currdate_sold".equals(res.error_code)) {
                finish();
            }
        }
    }

    //入口为我的订单或推送的确认订单---重新支付界面
    private void payOrderDetailRender(PayDetailBean payDetailBean) {
        name.setText(payDetailBean.getContent().getUser_name());
        phone.setText(payDetailBean.getContent().getUser_phone());
        company.setText(payDetailBean.getContent().getCompany());
        adress.setText(payDetailBean.getContent().getCompany_address());
        totalPrice = payDetailBean.getContent().getTotal_price();//总价
        totalMoney.setText("¥ " + totalPrice);//商品金额
        welf.setText("-¥ " + j);//优惠
        payMoney = (FDDataUtils.getInteger(totalPrice) - j)+"";
        total.setText("¥ " +payMoney);//合计
    }

    private void render(ShowConfirmOrderBean bean) {

        //从SharedPreferences获取数据:
        SharedPreferences preferences=getSharedPreferences("componey", Context.MODE_PRIVATE);
        String companys=preferences.getString("company_name", "");
        String company_address=preferences.getString("company_address", "");
        name.setText(bean.getContent().getUser_name());
        phone.setText(bean.getContent().getUser_phone());
        if (companys .equals("")) {
            company.setText("请输入公司名称");
        } else {
            company.setText(companys);
        }

        if (company_address .equals("")) {
            adress.setText("请输入公司地址");
        } else {
            adress.setText(company_address);
        }

        totalPrice = bean.getContent().getTotal_price();//总价
        totalMoney.setText("¥ " + totalPrice);
        welf.setText("-¥ " + j);
        payMoney = (FDDataUtils.getInteger(totalPrice) - j)+"";
        total.setText("¥ " + payMoney);
    }

    @Override
    public void onClick(View v) {
        Bundle mBundle = new Bundle();
        super.onClick(v);
        switch (v.getId()) {
            case R.id.name_ll://更改名字
                if ("3".equals(buyType)) return;
                mBundle.putString("title", "姓名");
                mBundle.putString("content", name.getText().toString().trim());
                UIHelper.jumpForResult(_activity, EditActivity.class, mBundle, 2001);
                break;
            case R.id.phone_ll://更改电话
                if ("3".equals(buyType)) return;
                mBundle.putString("title", "电话");
                mBundle.putBoolean("isPhone", true);
                mBundle.putString("content", phone.getText().toString().trim());
                UIHelper.jumpForResult(_activity, EditActivity.class, mBundle, 2002);
                break;
            case R.id.company_ll://更改公司名称
                if ("3".equals(buyType)) return;
                mBundle.putString("title", "公司名称");
                mBundle.putString("content", company.getText().toString().trim());
                UIHelper.jumpForResult(_activity, EditActivity.class, mBundle, 2003);
                break;
            case R.id.adress_ll://更改地址
                if ("3".equals(buyType)) return;
                mBundle.putString("title", "公司地址");
                mBundle.putString("content", adress.getText().toString().trim());
                UIHelper.jumpForResult(_activity, EditActivity.class, mBundle, 2004);
                break;
            case R.id.toggle_button:
                if ("3".equals(buyType)) return;
                toggle_button.toggle();
                break;
            case R.id.type_ll:
                if ("3".equals(buyType)) return;
                mBundle.putString("title", "发票类型");
                mBundle.putString("content", type_tv.getText().toString().trim());
                UIHelper.jumpForResult(_activity, EditActivity.class, mBundle, 2005);
                break;
            case R.id.upHead_ll:
                if ("3".equals(buyType)) return;
                mBundle.putString("title", "发票抬头");
                mBundle.putString("content", upHead_tv.getText().toString().trim());
                UIHelper.jumpForResult(_activity, EditActivity.class, mBundle, 2006);
                break;
            case R.id.alpay:
                alpay.setChecked(true);
                wechat.setChecked(false);
                pay_type = "1";//支付方式：1：支付宝，2：微信
                break;
            case R.id.wechat:
                alpay.setChecked(false);
                wechat.setChecked(true);
                pay_type = "2";//支付方式：1：支付宝，2：微信
                break;
            case R.id.select:
                select.setChecked(true);
                break;
            case R.id.notice:
                mBundle.putString("title", "广告支付须知");
                UIHelper.jump(_activity, CooperationActivity.class, mBundle);
                break;
            case R.id.confirm://确认
                user_name = name.getText().toString().trim();
                user_phone = phone.getText().toString().trim();
                user_company = company.getText().toString().trim();
                user_address = adress.getText().toString().trim();

                if ("请输入公司名称".equals(company.getText().toString())) {
                    UIHelper.t(_activity, "请输入公司名称");
                    ObjectAnimatorTools.onVibrationView(company_ll);
                    return;
                }

                if ("请输入公司地址".equals(adress.getText().toString())) {
                    UIHelper.t(_activity, "请输入公司地址");
                    ObjectAnimatorTools.onVibrationView(adress_ll);
                    return;
                }

                if (toggle_button.isToggleOn()) {
                    if ("请选择发票抬头类型".equals(type_tv.getText().toString())) {
                        UIHelper.t(_activity, "请选择发票抬头类型");
                        ObjectAnimatorTools.onVibrationView(type_ll);
                        return;
                    }
                    if ("请输入发票抬头".equals(adress.getText().toString())) {
                        UIHelper.t(_activity, "请输入发票抬头");
                        ObjectAnimatorTools.onVibrationView(upHead_ll);
                        return;
                    }

                } else {
                    invoice_type = null;
                    invoice_title = null;
                }

                if (pay_type == null) {
                    UIHelper.t(_activity, "请选择支付方式");
                    return;
                }

                if (!select.isChecked()) {
                    UIHelper.t(_activity, "购物广告位请确认购买须知！");
                    return;
                }



                //2：会员，3：销售员
                if ("2".equals(ac.user_type)) {
                    //1为购物车传参、2、订单详情的立即购买入口参数;3、我的订单及推送入口的参数
                    if ("1".equals(buyType) || "2".equals(buyType)) {
                        PayRightNow();
                    } else {
                        RePay();
                    }

                } else if ("3".equals(ac.user_type)) {
                    //确认预订接口
                    AppUtil.getMomaApiClient(ac).reserveAdvertisement(user_name, user_phone, user_company
                            , user_address, card_ids, ad_child_id, start_time, end_time, this);
                }


                break;
        }
    }

    private void PayRightNow() {
        if ("2".equals(pay_type)) {//微信支付
            WXPayEntryActivity.gotoActivity(_activity, buyType, order_sn, "1", user_name, user_phone
                    , user_company, user_address, invoice_type, invoice_title
                    , card_ids, ad_child_id, start_time, end_time, order_id);
        } else {
            //确认订单接口（返回订单号）
            AppUtil.getMomaApiClient(ac).confirmAdvertisement(pay_type, user_name, user_phone
                    , user_company, user_address, invoice_type, invoice_title
                    , card_ids, ad_child_id, start_time, end_time, this);
        }
    }

    private void RePay() {
        if ("2".equals(pay_type)) {//微信支付
            WXPayEntryActivity.gotoActivity(_activity, buyType, order_sn, "1", user_name, user_phone
                    , user_company, user_address, invoice_type, invoice_title
                    , card_ids, ad_child_id, start_time, end_time, order_id);
        } else {
            //确认订单接口（返回订单号）
            if (TextUtils.isEmpty(order_sn)) {
                AppUtil.getMomaApiClient(ac).updateOrderSn(pay_type, order_id, this);
            } else {
                new PayUtil(_activity, new PayUtil.CallBack() {
                    @Override
                    public void complete(boolean stat) {
                        PaySucessActivity.gotoActivity(_activity, stat ? PaySucessActivity.SUCCESS : PaySucessActivity.FAILED, "1", order_sn, order_id, "0.01");
                    }
                }).pay(order_sn, "0.01", "moma", "moma");//总价totalPrice替换0.01
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 2001:
                    user_name = data.getExtras().getString("content");
                    name.setText(data.getExtras().getString(user_name));
                    break;
                case 2002:
                    user_phone = data.getExtras().getString("content");
                    phone.setText(user_phone);
                    break;
                case 2003:
                    user_company = data.getExtras().getString("content");
                    company.setText(user_company);
//                    System.out.println("修改公司名称后：：" + user_company);
                    break;
                case 2004:
                    user_address = data.getExtras().getString("content");
                    adress.setText(user_address);
//                    System.out.println("修改地址后：：" + user_address);
                    break;
                case 2005:
                    //1:个人，2：公司
                    if ("1".equals(data.getExtras().getString("content"))) {
                        invoice_type = data.getExtras().getString("content");
                        type_tv.setText("个人");
                    } else {
                        invoice_type = data.getExtras().getString("content");
                        type_tv.setText("公司");
                    }
                    break;
                case 2006:
                    invoice_title = data.getExtras().getString("content");
                    upHead_tv.setText(invoice_title);
                    break;
            }
        }
    }

    @Override
    public void onToggle(boolean on) {
        if ("3".equals(buyType)) return;
        if (on) {
            type_ll.setVisibility(View.VISIBLE);
            upHead_ll.setVisibility(View.VISIBLE);
        } else {
            type_ll.setVisibility(View.GONE);
            upHead_ll.setVisibility(View.GONE);
        }
    }


}
