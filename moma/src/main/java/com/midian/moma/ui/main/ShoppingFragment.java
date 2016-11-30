package com.midian.moma.ui.main;

import android.media.audiofx.AudioEffect;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.TextView;

import com.midian.moma.R;
import com.midian.moma.datasource.ShoppingDataSource;
import com.midian.moma.itemview.ShoppingTpl;
import com.midian.moma.model.ShoppingCartBean;
import com.midian.moma.model.ShoppingCartBean.ShoppingContent;
import com.midian.moma.ui.advert.AdvertDetailActivity;
import com.midian.moma.ui.shopping.ConfirmOrderActivity;
import com.midian.moma.utils.AppUtil;

import java.util.ArrayList;
import java.util.Iterator;

import midian.baselib.api.ApiCallback;
import midian.baselib.base.BaseListFragment;
import midian.baselib.bean.NetResult;
import midian.baselib.shizhefei.view.listviewhelper.IDataAdapter;
import midian.baselib.shizhefei.view.listviewhelper.IDataSource;
import midian.baselib.utils.FDDataUtils;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;

/**
 * 购物车
 *
 * @author chu
 */
public class ShoppingFragment extends BaseListFragment<ShoppingContent> implements OnClickListener, ApiCallback {

    private BaseLibTopbarView topbar;
    private CheckBox allCheck_cb;
    private TextView count_money, buy;//合计、结算
    private TextView totalMoney, welf;
    private View count_ll;//合计、总额View
    private View shopping_ll;
    private String type;

    private OnClickListener editClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            if (resultList.size() > 0) {
                showEdit();// 编辑状态的标题栏
            }
        }
    };
    private OnClickListener cancelClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            showNormal();// 正常标题栏
        }
    };

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
//        listViewHelper.loadViewFactory.config(false,"我的数据");
        initView(v);
    }



    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shopping;
    }

    private void initView(View v) {
        topbar = (BaseLibTopbarView) v.findViewById(R.id.topbar);
        count_ll = v.findViewById(R.id.count_ll);//合计、总额View
        allCheck_cb = (CheckBox) v.findViewById(R.id.allCheck);//全选
        count_money = (TextView) v.findViewById(R.id.count_money);//合计
        buy = (TextView) v.findViewById(R.id.buy);//结算
        totalMoney = (TextView) v.findViewById(R.id.totalMoney);//总额
        welf = (TextView) v.findViewById(R.id.welf);//优惠
        allCheck_cb.setOnClickListener(this);
        buy.setOnClickListener(this);
        allCheck_cb.setChecked(false);
        shopping_ll = v.findViewById(R.id.shopping_ll);
        adapter.setRunnable(new Runnable() {
            @Override
            public void run() {
                boolean isAllChecked = true;
                for (ShoppingContent mContent : resultList) {
                    if (!mContent.isChecked()) {
                        isAllChecked = false;
                    }
                }
                allCheck_cb.setChecked(isAllChecked);
                refreshBuyState();
            }
        });
        //c1ba4fa5e4ae08d967ab20982ec715d6  //摩玛APP 签名
        showNormal();
        //2：会员，3：销售员
        if ("2".equals(ac.user_type)) {
            type = "1"; //1：普通订单，2：预定订单
        } else if ("3".equals(ac.user_type)) {
            buy.setText("预订");
            type = "2";//1：普通订单，2：预定订单
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        listViewHelper.refresh();
    }

    @Override
    public void onEndRefresh(IDataAdapter<ArrayList<ShoppingContent>> adapter, ArrayList<ShoppingContent> result) {
        super.onEndRefresh(adapter, result);
        showNormal();
        if (resultList.size() > 0) {
            topbar.showRight_tv();
            shopping_ll.setVisibility(View.VISIBLE);
        } else {
            topbar.hideRight_tv();
            shopping_ll.setVisibility(View.GONE);
        }
    }


    private void showNormal() {
        topbar.recovery().setTitle("购物车").setRightText("编辑", editClickListener).showRight_tv();
        ShoppingTpl.setEditable(false);
        for (ShoppingContent mContent : resultList) {
            mContent.setChecked(false);
        }
        count_ll.setVisibility(View.VISIBLE);
        adapter.notifyDataSetChanged();

    }

    private void showEdit() {
        topbar.recovery().setTitle("购物车").setRightText("完成", cancelClickListener).showRight_tv();
        ShoppingTpl.setEditable(true);
        for (ShoppingContent mContent : resultList) {
            mContent.setChecked(false);
        }
        count_ll.setVisibility(View.INVISIBLE);
        adapter.notifyDataSetChanged();
    }


    public void refreshBuyState() {
        int count = 0;//计算选择条目数量
        int price = 0;//合计
        int total = 0;//总额
        double discount_money = 0;//优惠

        for (ShoppingContent mContent : adapter.getData()) {
            if (mContent.isChecked()) {
                count++;

                double p = FDDataUtils.getDouble(mContent.getPrice());
                double d = FDDataUtils.getDouble(mContent.getDiscount_money());
                price += p - d;
                total += FDDataUtils.getInteger(mContent.getPrice());
                discount_money += FDDataUtils.getDouble(mContent.getDiscount_money());
            }
        }
        if (ShoppingTpl.isEditable()) {
            buy.setText("删除(" + count + ")");
        } else if ("3".equals(ac.user_type)) {
            buy.setText("预订(" + count + ")");
        } else {
            buy.setText("结算(" + count + ")");
        }


        count_money.setText("¥" + FDDataUtils.getString(price) + "");//合计
        totalMoney.setText("¥" + total + "");//totalMoney总额
        welf.setText("¥" + discount_money + "");//立减
    }


    @Override
    protected IDataSource<ArrayList<ShoppingContent>> getDataSource() {
        return new ShoppingDataSource(_activity);
    }

    @Override
    protected Class getTemplateClass() {
        return ShoppingTpl.class;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.buy:
                StringBuilder card_ids = new StringBuilder();
                for (int i = 0; i < resultList.size(); i++) {
                    ShoppingContent mContent = resultList.get(i);
                    if (mContent.isChecked()) {
                        card_ids.append("," + mContent.getCart_id());
                    }
                }
                if (card_ids.length() > 0) {
                    card_ids.deleteCharAt(0);
                } else {
                    UIHelper.t(_activity, "请选择购物车物品");
                    return;
                }

                if (ShoppingTpl.isEditable()) {
                    AppUtil.getMomaApiClient(ac).cancleShoppingCart(card_ids.toString(), this);//6.3删除购物车物品
                } else {
                    Bundle mBundle = new Bundle();
                    mBundle.putString("buyType", "1");//1为购物车传参、2为广告位详情传参
                    mBundle.putString("card_ids", card_ids.toString());//购物车中拼接的订单id
//                    mBundle.putString("orderType", type);//1：普通订单，2：预定订单
                    UIHelper.jump(_activity, ConfirmOrderActivity.class, mBundle);// 生成确认订单接口
                }
                break;
            case R.id.allCheck:
//                listViewHelper.loadViewFactory.config(true,"我的数据");
                for (ShoppingContent mContent : resultList) {
                    mContent.setChecked(allCheck_cb.isChecked());
                }
                adapter.notifyDataSetChanged();
                break;
        }

    }


    @Override
    public void onApiStart(String tag) {
        _activity.showLoadingDlg();
        buy.setEnabled(false);

    }

    @Override
    public void onApiLoading(long count, long current, String tag) {

    }

    @Override
    public void onApiSuccess(NetResult res, String tag) {
        _activity.hideLoadingDlg();
        buy.setEnabled(true);
        if (res.isOK()) {
            UIHelper.t(_activity, "删除成功!");
            Iterator<ShoppingContent> iter = resultList.iterator();
            while (iter.hasNext()) {
                ShoppingContent mContent = iter.next();
                if (mContent.isChecked()) {
                    iter.remove();
                }
            }
            showNormal();
            if (resultList.size() > 0) {
                shopping_ll.setVisibility(View.VISIBLE);//判断购物车内=0时，隐藏底部的状态栏
                adapter.notifyDataSetChanged();
            } else {
                shopping_ll.setVisibility(View.GONE);//判断购物车内=0时，隐藏底部的状态栏
                allCheck_cb.setChecked(false);
                topbar.hideRight_tv();
                listViewHelper.refresh();
            }
        } else {
            ac.handleErrorCode(_activity, res.error_code);
        }
    }

    @Override
    public void onApiFailure(Throwable t, int errorNo, String strMsg, String tag) {
        _activity.hideLoadingDlg();
        buy.setEnabled(true);
    }

    @Override
    public void onParseError(String tag) {
        _activity.hideLoadingDlg();
        buy.setEnabled(true);
    }


}
