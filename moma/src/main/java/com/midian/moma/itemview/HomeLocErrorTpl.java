package com.midian.moma.itemview;

import com.midian.moma.R;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import midian.baselib.bean.NetResult;
import midian.baselib.view.BaseTpl;

/**
 * 首页案例模板--定位失败
 * 
 * @author XuYang
 *
 */
public class HomeLocErrorTpl extends BaseTpl<NetResult> implements OnClickListener {

	private TextView tip_tv;
	private TextView refresh_tv;
	private TextView set_tv;

	public HomeLocErrorTpl(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public HomeLocErrorTpl(Context context) {
		super(context);
	}

	@Override
	protected void initView() {
		tip_tv = findView(R.id.tip);
		refresh_tv = findView(R.id.refresh);
		set_tv = findView(R.id.set);

		refresh_tv.setOnClickListener(this);
		set_tv.setOnClickListener(this);
	}

	@Override
	protected int getLayoutId() {
		return R.layout.item_main_home_loc_error;
	}

	@Override
	public void setBean(NetResult bean, int position ) {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.refresh:
			listViewHelper.doPullRefreshing(true, 0);
			break;
		case R.id.set:
			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			_activity.startActivity(intent);
			break;
		}
	}

}
