package com.midian.moma.itemview;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.midian.moma.R;
import com.midian.moma.model.MaterialReplyBean.MaterialReply;
import com.midian.moma.ui.advert.ReplayDetailActivity;

import midian.baselib.utils.UIHelper;
import midian.baselib.view.BaseTpl;

/**
 * Created by chu on 2016/1/20.
 */
public class ReplayListTpl extends BaseTpl<MaterialReply> implements View.OnClickListener {
    private TextView title_tv, time_tv;
    private String reply_id, url;
    private View item_ll;
    private TextView cancel;
    private String title;

    public ReplayListTpl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ReplayListTpl(Context context) {
        super(context);
    }

    @Override
    protected void initView() {
        title_tv = findView(R.id.title_tv);
        time_tv = findView(R.id.time_tv);

        item_ll = findView(R.id.item_ll);
        cancel = findView(R.id.cancel);
        root.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_replay_list_tpl;
    }

    @Override
    public void setBean(MaterialReply bean, int position) {
        if (bean == null) {
            item_ll.setVisibility(View.GONE);
            cancel.setVisibility(View.VISIBLE);
        } else {

            item_ll.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.GONE);
            title = bean.getTitle();
            reply_id = bean.getReply_id();
            url = bean.getUrl();
            title_tv.setText(title);
            time_tv.setText(bean.getReply_time());

        }
    }

    @Override
    public void onClick(View view) {
        Bundle mBundle = new Bundle();
        mBundle.putString("title", title);
        mBundle.putString("url", url);
        UIHelper.jump(_activity, ReplayDetailActivity.class, mBundle);
    }
}
