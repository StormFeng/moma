package com.midian.moma.ui.advert;

import android.os.Bundle;

import com.midian.moma.R;
import com.midian.moma.datasource.ChooseAdvertDataSource;
import com.midian.moma.itemview.ChooseadvertTpl;

import java.util.ArrayList;

import midian.baselib.base.BaseListActivity;
import midian.baselib.shizhefei.view.listviewhelper.IDataAdapter;
import midian.baselib.shizhefei.view.listviewhelper.IDataSource;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;

/**
 * 选择广告位
 * Created by chu on 2016/1/12.
 */
public class ChooseAdvertActivity extends BaseListActivity {
    private BaseLibTopbarView topbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

    }

    private void initView() {
        topbar = findView(R.id.topbar);
        topbar.setTitle("搜索相关广告位").setLeftImageButton(R.drawable.icon_back, null).setLeftText("返回", UIHelper.finish(_activity));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_advert;
    }

    @Override
    protected IDataSource<ArrayList> getDataSource() {
        return new ChooseAdvertDataSource(_activity,mBundle.getString("keywords"),mBundle.getString("city_id"));
    }

    @Override
    protected Class getTemplateClass() {
        return ChooseadvertTpl.class;
    }


}
