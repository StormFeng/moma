package com.midian.moma.itemview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.midian.moma.R;

import midian.baselib.bean.NetResult;
import midian.baselib.view.BaseTpl;

/**
 * Created by chu on 2016/4/28.
 */
public class AllNationalTpl extends BaseTpl implements View.OnClickListener {
    private TextView tv_all;
    public AllNationalTpl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AllNationalTpl(Context context) {
        super(context);
    }

    @Override
    protected void initView() {
        tv_all = (TextView) findView(R.id.tv_all);
        root.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_all_national_tpl;
    }

    @Override
    public void setBean(NetResult bean, int position) {

    }


    @Override
    public void onClick(View view) {
        ac.saveCityName("全国", null);
        ac.loc_city_level = null;
//        ac.saveLocCityName(null, null, null);
        _activity.finish();
    }
}
