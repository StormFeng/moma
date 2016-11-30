package com.midian.moma.itemview;

import midian.baselib.view.BaseTpl;
import android.content.Context;
import android.util.AttributeSet;

import com.midian.login.bean.MultiItem;
import com.midian.moma.R;

public class ChooseCitysHeadTpl extends BaseTpl<MultiItem> {

	public ChooseCitysHeadTpl(Context context) {
		super(context);
	}

	public ChooseCitysHeadTpl(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void initView() {
	}

	@Override
	protected int getLayoutId() {
		return R.layout.item_choose_citys_head_tpl;
	}

	@Override
	public void setBean(MultiItem bean, int position ) {

	}

}
