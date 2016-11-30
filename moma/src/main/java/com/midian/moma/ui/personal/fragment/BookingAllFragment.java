package com.midian.moma.ui.personal.fragment;

import java.util.ArrayList;

import com.midian.moma.datasource.AllOrderDataSource;
import com.midian.moma.datasource.BookingAllDataSource;
import com.midian.moma.itemview.AllOrderTpl;
import com.midian.moma.itemview.BookingAllTpl;
import com.midian.moma.model.MyBookingListBean.BookingContent;

import android.view.View;
import android.widget.AdapterView;
import midian.baselib.base.BaseListFragment;
import midian.baselib.bean.NetResult;
import midian.baselib.shizhefei.view.listviewhelper.IDataSource;
import midian.baselib.utils.UIHelper;

/**
 * 预订全部
 * 
 * @author chu
 *
 */
public class BookingAllFragment extends BaseListFragment<BookingContent> {

	@Override
	protected IDataSource<ArrayList<BookingContent>> getDataSource() {
		return new BookingAllDataSource(_activity);
	}

	@Override
	protected Class getTemplateClass() {
		return BookingAllTpl.class;
	}


}
