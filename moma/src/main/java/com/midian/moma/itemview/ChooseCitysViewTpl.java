package com.midian.moma.itemview;

import midian.baselib.utils.UIHelper;
import midian.baselib.view.BaseTpl;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.midian.login.bean.MultiItem;
import com.midian.login.bean.ProvincesBean.City;
import com.midian.moma.R;
import com.midian.moma.ui.home.ChooseAreaActivity;

import java.util.ArrayList;

public class ChooseCitysViewTpl extends BaseTpl<MultiItem> implements OnClickListener {
    private TextView cityName_tv;
    private String province_id;
    private ArrayList<City> citys = new ArrayList<City>();

    public ChooseCitysViewTpl(Context context) {
        super(context);
    }

    public ChooseCitysViewTpl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initView() {
        cityName_tv = (TextView) findView(R.id.city_name);

        root.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_choose_citys_tpl;
    }

    @Override
    public void setBean(MultiItem bean, int position ) {

        province_id = bean.provincesContent.getProvince_id();
        cityName_tv.setText(bean.provincesContent.getProvince_name());
        citys=bean.provincesContent.getCity();
    }

    @Override
    public void onClick(View v) {

        Bundle mBundle = new Bundle();
        mBundle.putSerializable("citys", citys);
        UIHelper.jump(_activity, ChooseAreaActivity.class, mBundle);
        _activity.finish();

    }

}
