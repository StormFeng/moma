package com.midian.moma.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.midian.moma.R;
import com.midian.moma.datasource.SearchAdvertDataSource;
import com.midian.moma.datasource.SearchCaseDataSource;
import com.midian.moma.itemview.SearchAdvertTpl;
import com.midian.moma.itemview.SearchCaseTpl;

import java.util.ArrayList;

import midian.baselib.base.BaseListActivity;
import midian.baselib.shizhefei.view.listviewhelper.IDataSource;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;

/**
 * 案例搜索结果列表页
 * Created by chu on 2016/1/12.
 */
public class SearchCaseResultActivity extends BaseListActivity {
    private BaseLibTopbarView topbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        //自定义列表无数据时的提示文字和事件 DeFaultLoadViewFactory.java
        listViewHelper.loadViewFactory.config(true, "暂无相关案例");
        listViewHelper.loadViewFactory.BtOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        }, "重新搜索");
    }

    private void initView() {
        topbar = findView(R.id.topbar);
        topbar.setTitle("案例搜索").setLeftImageButton(R.drawable.icon_back, null).setLeftText("返回", UIHelper.finish(_activity));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_home_advert;
    }

    @Override
    protected IDataSource<ArrayList> getDataSource() {
        return new SearchCaseDataSource(_activity, mBundle.getString("keywords"), mBundle.getString("city_id"));
    }

    @Override
    protected Class getTemplateClass() {
        return SearchCaseTpl.class;
    }

}
