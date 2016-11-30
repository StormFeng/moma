package com.midian.moma.ui.advert;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.midian.moma.R;
import com.midian.moma.ui.MainActivity;
import com.midian.moma.ui.main.AdvertFragments;
import com.midian.moma.ui.main.AdvertFragments.SearchItem;

import java.util.ArrayList;
import java.util.List;

import midian.baselib.utils.UIHelper;
import midian.baselib.view.BaseTpl;

/**
 * 广告位搜索热词的条目
 * Created by chu on 2016/1/10.
 */

public class AdvertSearchViewTpl extends BaseTpl<SearchItem> implements View.OnClickListener {

    private TextView item_name, item_name2, item_name3;
    private List<TextView> list;
    private SearchItem bean;

    public AdvertSearchViewTpl(Context context) {
        super(context);
    }

    public AdvertSearchViewTpl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initView() {
        item_name = (TextView) findViewById(R.id.item_name);
        item_name2 = (TextView) findViewById(R.id.item_name2);
        item_name3 = (TextView) findViewById(R.id.item_name3);
        list = new ArrayList<TextView>();
        list.add(item_name);
        list.add(item_name2);
        list.add(item_name3);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setTag(i);
            list.get(i).setOnClickListener(this);
        }


    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_advert_search_view_tpl;
    }

    @Override
    public void setBean(SearchItem bean, int position) {
        this.bean = bean;
        for (int i = 0; i < 3; i++) {
            if (i < bean.getList().size()) {
                list.get(i).setText(bean.getList().get(i).getName());
                list.get(i).setVisibility(View.VISIBLE);
            } else {
                list.get(i).setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void onClick(View view) {
        int i = (Integer) view.getTag();
        ((MainActivity) _activity).onAdvertSearchKey(bean.getList().get(i).getName());
        UIHelper.t(_activity, i + "i::" + bean.getList().get(i).getName());
    }
}
