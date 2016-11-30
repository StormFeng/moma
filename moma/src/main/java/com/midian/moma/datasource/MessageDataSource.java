package com.midian.moma.datasource;

import android.app.Activity;
import android.content.Context;

import com.midian.baidupush.DeviceMessage;
import com.midian.baidupush.MessageTool;
import com.midian.moma.model.PushMessageListBean;
import com.midian.moma.model.PushMessageListBean.PushMessageContent;
import com.midian.moma.utils.AppUtil;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import midian.baselib.base.BaseListDataSource;
import midian.baselib.bean.NetResult;
import midian.baselib.db.DbUtil;
import midian.baselib.shizhefei.view.listviewhelper.IDataSource;

/**
 * Created by Administrator on 2015/11/22 0022.
 */
public class MessageDataSource extends BaseListDataSource {

    public MessageDataSource(Context context) {
        super(context);
    }

    @Override
    protected ArrayList load(int page) throws Exception {
        /*ArrayList<PushMessageContent>morelist=new ArrayList<PushMessageContent>();
        *//*for(int i=0;i<10;i++){
                morelist.add(new NetResult());
        }*/

        ArrayList<DeviceMessage> morelist = new ArrayList<DeviceMessage>();
        PushMessageListBean bean = AppUtil.getMomaApiClient(ac).pushMessageList();
        if (bean.isOK()) {
            List<PushMessageContent> list=bean.getContent();
            for (PushMessageContent item:list){
                MessageTool.getMessageTool(ac).saveMessage(new DeviceMessage(item.getMsg_title(),item.getMsg_content(),item.getOrder_no(),item.getOrder_id(),item.getAd_title(),item.getType(),item.getTime(),"0",ac.user_id));
            }
        }
        List<DeviceMessage> messages= MessageTool.getMessageTool(ac).getMessageList(ac.user_id);
        if (messages != null) {
            morelist.addAll(messages);
        }
        hasMore = false;
        return morelist;
    }
}
