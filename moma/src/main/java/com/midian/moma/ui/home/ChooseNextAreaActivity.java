package com.midian.moma.ui.home;

import android.os.Bundle;

import com.midian.login.R;
import com.midian.login.bean.MultiItem;
import com.midian.login.bean.ProvincesBean;
import com.midian.login.bean.ProvincesBean.City;
import com.midian.moma.datasource.ChooseAreaDataSource;
import com.midian.moma.datasource.ChooseNextAreaDataSource;
import com.midian.moma.itemview.ChooseAreaTpl;
import com.midian.moma.itemview.ChooseNextAreaTpl;

import java.util.ArrayList;
import java.util.List;

import midian.baselib.base.BaseListActivity;
import midian.baselib.shizhefei.view.listviewhelper.IDataSource;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;

/**
 * 选择地区的下一级区县
 *
 * @author chu
 *
 */
public class ChooseNextAreaActivity extends BaseListActivity<MultiItem> {

	private BaseLibTopbarView topbar;
    private List<ProvincesBean.Area> areas = new ArrayList<ProvincesBean.Area>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        areas = (List<ProvincesBean.Area>) mBundle.getSerializable("areas");
		initView();
	}

	private void initView() {
		topbar = (BaseLibTopbarView) findViewById(R.id.topbar);
		topbar.setTitle("选择地区");
		topbar.setLeftImageButton(R.drawable.icon_back, UIHelper.finish(this));

    }

	@Override
	protected IDataSource<ArrayList<MultiItem>> getDataSource() {
		return new ChooseNextAreaDataSource(_activity,(List<ProvincesBean.Area>) mBundle.getSerializable("areas"));
    }

	@Override
	protected Class getTemplateClass() {

		return ChooseNextAreaTpl.class;
	}

}
