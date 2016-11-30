package com.midian.moma.ui.personal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.midian.baidupush.DeviceMessage;
import com.midian.baidupush.MyPushMessageReceiver;
import com.midian.baidupush.PushMessage;
import com.midian.moma.R;
import com.midian.moma.datasource.MessageDataSource;
import com.midian.moma.itemview.MessageTpl;

import java.util.ArrayList;

import midian.baselib.base.BaseActivity;
import midian.baselib.base.BaseListActivity;
import midian.baselib.db.DbUtil;
import midian.baselib.shizhefei.view.listviewhelper.IDataAdapter;
import midian.baselib.shizhefei.view.listviewhelper.IDataSource;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;

/**
 * Created by Administrator on 2015/11/22 0022.
 */
public class MessageActivity extends BaseListActivity {
    private BaseLibTopbarView topbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        //自定义列表无数据时的提示文字和事件 DeFaultLoadViewFactory.java
        listViewHelper.loadViewFactory.config(true, "暂未收到通知消息");
        listViewHelper.loadViewFactory.BtOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listViewHelper.refresh();
            }
        }, "刷新");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message;
    }

    private void initView() {
        topbar = (BaseLibTopbarView) findView(R.id.topbar);
        topbar.setTitle("通知消息").setLeftImageButton(R.drawable.icon_back, null)
                .setLeftText("返回", UIHelper.finish(_activity)).setRightText("清空", this);
        MyPushMessageReceiver.addPushListener(mPushListener);
    }
    MyPushMessageReceiver.PushListener mPushListener=new MyPushMessageReceiver.PushListener() {
        @Override
        public void updateContent(PushMessage msg) {
            listViewHelper.refresh();
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyPushMessageReceiver.removePushListener(mPushListener);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        DbUtil.getDbUtil(_activity).removeAll(DeviceMessage.class);//清空数据
        listViewHelper.refresh();
        UIHelper.t(_activity, "消息列表已清空!");
    }

    @Override
    protected IDataSource<ArrayList> getDataSource() {
        return new MessageDataSource(_activity);
    }

    @Override
    protected Class getTemplateClass() {
        return MessageTpl.class;
    }


    @Override
    protected void onResume() {
        super.onResume();
        listViewHelper.refresh();
    }
}
