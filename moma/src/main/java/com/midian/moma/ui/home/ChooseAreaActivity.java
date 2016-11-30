package com.midian.moma.ui.home;

import java.util.ArrayList;
import java.util.List;

import midian.baselib.base.BaseListActivity;
import midian.baselib.shizhefei.view.listviewhelper.IDataSource;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;
import android.os.Bundle;

import com.midian.login.R;
import com.midian.login.bean.MultiItem;
import com.midian.login.bean.ProvincesBean.City;
import com.midian.moma.datasource.ChooseAreaDataSource;
import com.midian.moma.itemview.ChooseAreaTpl;

/**
 * 选择地区
 *
 * @author chu
 *
 */
public class ChooseAreaActivity extends BaseListActivity<MultiItem> {

	private BaseLibTopbarView topbar;
    private List<City> city = new ArrayList<City>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        city = (List<City>) mBundle.getSerializable("citys");
		initView();
	}

	private void initView() {
		topbar = (BaseLibTopbarView) findViewById(R.id.topbar);
		topbar.setTitle("选择地区");
		topbar.setLeftImageButton(R.drawable.icon_back, UIHelper.finish(this));

    }

	@Override
	protected IDataSource<ArrayList<MultiItem>> getDataSource() {
		return new ChooseAreaDataSource(_activity,(List<City>) mBundle.getSerializable("citys"));
    }

	@Override
	protected Class getTemplateClass() {

		return ChooseAreaTpl.class;
	}

}
