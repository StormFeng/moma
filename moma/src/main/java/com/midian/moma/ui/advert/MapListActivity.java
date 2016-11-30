package com.midian.moma.ui.advert;

import android.os.Bundle;

import com.midian.moma.R;
import com.midian.moma.datasource.MapListDataSource;
import com.midian.moma.itemview.MapListTpl;

import java.util.ArrayList;

import midian.baselib.base.BaseActivity;
import midian.baselib.base.BaseListActivity;
import midian.baselib.shizhefei.view.listviewhelper.IDataAdapter;
import midian.baselib.shizhefei.view.listviewhelper.IDataSource;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;

/**
 * 地图附近重叠广告位列表
 * Created by chu on 2016/1/8.
 */
public class MapListActivity extends BaseListActivity {
    private BaseLibTopbarView topbar;
    private String ad_ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        topbar = findView(R.id.topbar);
        topbar.setTitle("地图附近列表").setLeftImageButton(R.drawable.icon_back, null).setLeftText("返回", UIHelper.finish(_activity));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_map_list;

    }

    @Override
    protected IDataSource<ArrayList> getDataSource() {
        return new MapListDataSource(_activity, mBundle.getString("ids"));

    }

    @Override
    protected Class getTemplateClass() {
        return MapListTpl.class;
    }


}
