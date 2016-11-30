package com.midian.moma.ui.personal.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.ArrayList;

import com.midian.moma.datasource.BookingRefuseDataSource;
import com.midian.moma.itemview.BookingRefuseTpl;
import com.midian.moma.model.MyBookingListBean.BookingContent;

import midian.baselib.base.BaseListFragment;
import midian.baselib.bean.NetResult;
import midian.baselib.shizhefei.view.listviewhelper.IDataSource;

/**
 * 我的预订-已拒绝
 * 
 * @author chu
 *
 */
public class BookingRefuseFragment extends BaseListFragment<BookingContent> {


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //自定义列表无数据时的提示文字和事件 DeFaultLoadViewFactory.java
        listViewHelper.loadViewFactory.config(false, "没有被拒绝的预定");
    }

	@Override
	protected IDataSource<ArrayList<BookingContent>> getDataSource() {
		return new BookingRefuseDataSource(_activity);
	}

	@Override
	protected Class getTemplateClass() {
		return BookingRefuseTpl.class;
	}

}
