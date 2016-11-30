package com.midian.moma.itemview;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.midian.login.bean.MultiItem;
import com.midian.login.bean.ProvincesBean;
import com.midian.moma.R;
import com.midian.moma.ui.home.ChooseAreaActivity;
import com.midian.moma.ui.home.ChooseNextAreaActivity;

import java.util.ArrayList;

import midian.baselib.utils.UIHelper;
import midian.baselib.view.BaseTpl;

public class ChooseAreaTpl extends BaseTpl<MultiItem> implements OnClickListener {

    private TextView areaName_tv;
    private String city_name, city_id;
    private ArrayList<ProvincesBean.Area> areas = new ArrayList<ProvincesBean.Area>();

    public ChooseAreaTpl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChooseAreaTpl(Context context) {
        super(context);
    }

    @Override
    protected void initView() {
        areaName_tv = findView(R.id.area_name);
        root.setOnClickListener(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_choose_area_tpl;
    }

    @Override
    public void setBean(MultiItem bean, int position ) {

        city_name = bean.city.getCity_name();
        city_id = bean.city.getCity_id();
        areaName_tv.setText(city_name);
        areas = bean.city.getArea();
    }

    @Override
    public void onClick(View v) {
        Bundle mBundle = new Bundle();
        mBundle.putSerializable("areas", areas);
        UIHelper.jump(_activity, ChooseNextAreaActivity.class, mBundle);
        _activity.finish();



    }


}
