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

import java.util.ArrayList;

import midian.baselib.utils.UIHelper;
import midian.baselib.view.BaseTpl;

public class ChooseNextAreaTpl extends BaseTpl<MultiItem> implements OnClickListener {

    private TextView areaName_tv;
    private String area_name, area_id;
    private ArrayList<ProvincesBean.Area> areas = new ArrayList<ProvincesBean.Area>();

    public ChooseNextAreaTpl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChooseNextAreaTpl(Context context) {
        super(context);
    }

    @Override
    protected void initView() {
        areaName_tv = findView(R.id.area_name);
        root.setOnClickListener(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_choose_next_area_tpl;
    }

    @Override
    public void setBean(MultiItem bean, int position ) {

        area_name = bean.area.getArea_name();
        area_id = bean.area.getArea_id();
        areaName_tv.setText(area_name);

    }

    @Override
    public void onClick(View v) {
        //        ac.setProperty("city_name",city_name);
//        System.out.println("选择的城市 ：：：：："+area_name+"::::"+area_id);
        ac.area_name = area_name;
        ac.area_id = area_id;
        ac.saveCityName(area_name,area_id);
        ac.loc_city_level = "3";
        _activity.finish();
    }


}
