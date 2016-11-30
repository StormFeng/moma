package com.midian.moma.ui.advert;

import android.app.Activity;
import android.os.Bundle;

import com.midian.baidupush.MyPushMessageReceiver;
import com.midian.moma.R;
import com.midian.moma.datasource.ReplayListDataSource;
import com.midian.moma.itemview.ReplayListTpl;

import java.util.ArrayList;

import midian.baselib.base.BaseListActivity;
import midian.baselib.shizhefei.view.listviewhelper.IDataAdapter;
import midian.baselib.shizhefei.view.listviewhelper.IDataSource;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;

/**
 * 编辑广告资料回复列表
 * Created by chu on 2016/1/20.
 */
public class ReplayListActivity extends BaseListActivity {
    private BaseLibTopbarView topbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        topbar = (BaseLibTopbarView) findView(R.id.topbar);
        topbar.setTitle("回复列表").setLeftImageButton(R.drawable.icon_back, null)
                .setLeftText("返回", UIHelper.finish(_activity));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_replay_list;
    }

    @Override
    protected IDataSource<ArrayList> getDataSource() {
        return new ReplayListDataSource(_activity, getIntent().getExtras().getString("order_id"));
    }


    @Override
    protected Class getTemplateClass() {
        return ReplayListTpl.class;
    }


}
