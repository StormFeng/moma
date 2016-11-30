package com.midian.moma.itemview;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.midian.moma.R;
import com.midian.moma.model.ShoppingCartBean.ShoppingContent;
import com.midian.moma.ui.advert.AdvertDetailActivity;

import midian.baselib.utils.FDDataUtils;
import midian.baselib.utils.UIHelper;
import midian.baselib.view.BaseTpl;

/**
 * 购物车模板
 *
 * @author chu
 */
public class ShoppingTpl extends BaseTpl<ShoppingContent> implements View.OnClickListener {
    private static boolean isEditable;
    private TextView title, sell_status, days, start_time, end_time, price, unit_price, discount_money;
    private TextView loc_tv;//新增类型位置
    private ImageView image;
    private CheckBox checkbox;
    private View select_ll, item_ll;
    private int position;
    private ShoppingContent bean;
   private String ad_id;

    public ShoppingTpl(Context context) {
        super(context);
    }

    public ShoppingTpl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initView() {
        title = findView(R.id.title);
//        sell_status = findView(R.id.sell_status);//广告位的售卖状态
        days = findView(R.id.days);
        start_time = findView(R.id.start_time);
        end_time = findView(R.id.end_time);
        unit_price = findView(R.id.unit_price);//单价
        price = findView(R.id.price);//总价
        discount_money = findView(R.id.discount_money);//优惠
        loc_tv = findView(R.id.loc_tv);

        image = findView(R.id.image);
        checkbox = findView(R.id.checkbox);
        select_ll = findView(R.id.select_ll);
        item_ll = findView(R.id.item_ll);
        checkbox.setOnClickListener(this);
        item_ll.setOnClickListener(this);
        select_ll.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_shopping_tpl;
    }

    @Override
    public void setBean(ShoppingContent bean, int position) {
        this.bean = bean;
        this.position = position;
        if (bean.isChecked()) {
            checkbox.setChecked(true);
        } else {
            checkbox.setChecked(false);
        }

        if (bean == null) {
            return;
        } else {
            if (TextUtils.isEmpty(bean.getPic_thumb_name())) {
                image.setImageResource(R.drawable.icon_home_bg);
            } else {
                ac.setImage(image, bean.getPic_thumb_name());
            }
            ad_id = bean.getAd_id();
            title.setText(bean.getAd_title());
            /*String status = bean.getStatus();
            if (status.equals("1")) {
                sell_status.setText("在售");
            } else if (status.equals("2")) {
                sell_status.setText("已售");
            }*/
            loc_tv.setText("类型：" + bean.getAd_type_name() + ";位置：" + bean.getAd_position_name() + ";");
            days.setText(bean.getMonths() + "个月");
            start_time.setText(bean.getStart_time());
            this.end_time.setText(bean.getEnd_time());

            price.setText("¥ "+bean.getPrice());
            discount_money.setText("¥ "+bean.getDiscount_money());

            int month = Integer.parseInt(bean.getMonths());
            double prices =FDDataUtils.getDouble( bean.getPrice());
            int p = FDDataUtils.getInteger(bean.getPrice());
//            System.out.println("转换后的单价：：：" + p);
            int unit = p / month;
            unit_price.setText(unit+"");
        }


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.select_ll:
            case R.id.checkbox:
                bean.setChecked(!bean.isChecked());
                adapter.notifyDataSetChanged();
                break;
            case R.id.item_ll:
                if (isEditable) {
                    bean.setChecked(!bean.isChecked());
                    adapter.notifyDataSetChanged();
                } else {
                    Bundle mBundle = new Bundle();
                    mBundle.putString("id", ad_id);
                    UIHelper.jump(_activity, AdvertDetailActivity.class, mBundle);
                }
                break;
        }

    }


    public static boolean isEditable() {
        return isEditable;
    }

    public static void setEditable(boolean isEditable) {
        ShoppingTpl.isEditable = isEditable;
    }
}
