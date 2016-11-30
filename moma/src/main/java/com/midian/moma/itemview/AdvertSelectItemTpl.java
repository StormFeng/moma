package com.midian.moma.itemview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.midian.moma.R;
import com.midian.moma.ui.main.AdvertFragments.ChooseItem;

import midian.baselib.view.BaseTpl;

/**
 * 广告位fragments筛选列表条目
 * Created by chu on 2016/4/1.
 */
public class AdvertSelectItemTpl extends BaseTpl<ChooseItem> {
    private TextView item_name;

    public AdvertSelectItemTpl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdvertSelectItemTpl(Context context) {
        super(context);
    }

    @Override
    protected void initView() {
        item_name = (TextView) findView(R.id.item_name);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_advert_fragment_select_tpl;
    }

    @Override
    public void setBean(ChooseItem bean, int position) {
        item_name.setText(bean.getName());
    }
}
