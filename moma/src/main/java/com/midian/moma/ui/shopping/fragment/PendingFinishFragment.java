package com.midian.moma.ui.shopping.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.midian.moma.datasource.PendingFinishDataSource;
import com.midian.moma.itemview.PendingFinishTpl;
import com.midian.moma.model.MyOrdersBean.MyorderContent;

import midian.baselib.base.BaseListFragment;
import midian.baselib.bean.NetResult;
import midian.baselib.shizhefei.view.listviewhelper.IDataSource;

/**
 * 已完成
 * 
 * @author chu
 *
 */
public class PendingFinishFragment extends BaseListFragment<MyorderContent> {


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }
    private void initView() {
        //自定义列表无数据时的提示文字和事件 DeFaultLoadViewFactory.java
        listViewHelper.loadViewFactory.config(true, "您还没有相关的订单");
        listViewHelper.loadViewFactory.BtOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                _activity.setResult(_activity.RESULT_OK, intent);
                _activity.finish();
            }
        }, "去选购");
    }

	@Override
	protected IDataSource getDataSource() {
		return new PendingFinishDataSource(_activity);
	}

	@Override
	protected Class getTemplateClass() {
		return PendingFinishTpl.class;
	}

    @Override
    public void onResume() {
        super.onResume();
        listViewHelper.refresh();
    }
}
