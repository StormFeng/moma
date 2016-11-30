package com.midian.baidupush;

import android.content.Context;

import com.midian.fastdevelop.afinal.annotation.view.Select;

import java.nio.channels.Selector;
import java.util.Collection;
import java.util.List;

import midian.baselib.db.DbUtil;

/**
 * 推送工具 处理数据存储
 *
 * @author MIDIAN
 */
public class MessageTool {
    private static MessageTool messageTool;
    Context context;

    public static MessageTool getMessageTool(Context context) {
        if (messageTool == null) {
            messageTool = new MessageTool(context);
        }
        return messageTool;
    }

    public MessageTool(Context context) {
        this.context = context;
    }

    /**
     * 保存消息
     *
     * @param mDeviceMessage
     */

    public void saveMessage(DeviceMessage mDeviceMessage) {
        if (checkIsNew(mDeviceMessage))
            DbUtil.getDbUtil(context).add(mDeviceMessage);
    }

    public boolean checkIsNew(DeviceMessage mDeviceMessage) {
        return getMessage(mDeviceMessage) == null;
    }

    /**
     * 设置为已读
     *
     * @param mDeviceMessage
     */
    public void setMessageReaded(DeviceMessage mDeviceMessage) {
        if (mDeviceMessage == null)
            return;
        mDeviceMessage.setIsRead("1");
        DbUtil.getDbUtil(context).updateOrAdd(mDeviceMessage.getId(),
                mDeviceMessage);
    }

    /**
     * 删除消息
     *
     * @param mDeviceMessage
     */
    public void removeMessage(DeviceMessage mDeviceMessage) {
        if (mDeviceMessage == null)
            return;
        mDeviceMessage.setIsRead("1");
        DbUtil.getDbUtil(context).remove(mDeviceMessage);
    }

    /**
     * 获取消息列表
     *
     * @return
     */
    public List<DeviceMessage> getMessageList(String user_id) {
        return DbUtil.getDbUtil(context).getAll(DeviceMessage.class, "user_id=\"" + user_id + "\"");
    }

    public DeviceMessage getMessage(DeviceMessage mDeviceMessage) {

        List<DeviceMessage> list = DbUtil.getDbUtil(context).getAll(DeviceMessage.class,"msg_title=\"" + mDeviceMessage.getMsg_title() + "\" and "
                        + "time=\"" + mDeviceMessage.getTime() + "\"");
        if (list == null || list.isEmpty())
            return null;

        return list.get(list.size() - 1);
    }

}
