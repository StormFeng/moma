package com.midian.baidupush;

import com.midian.fastdevelop.afinal.annotation.sqlite.Id;
import com.midian.fastdevelop.afinal.annotation.sqlite.Table;

/**
 * 预约推送
 *
 * @author chu
 */
@Table(name = "DeviceMessage")
public class DeviceMessage extends MessageBase {

    @Id(column = "id")
    private int id; // 表id

    private String msg_title;//推送标题”,
    private String msg_content;//推送内容”
    private String order_no;//订单编号”,
    private String order_id;//订单id”，
    private String ad_title;//广告标题”,
    private String type;//消息类型”,
    private String time;//时间
    private String isRead;// 是否已度
    private String user_id;//用户id

    public DeviceMessage() {
        super();
    }

    public DeviceMessage(String msg_title, String msg_content, String order_no,
                         String order_id, String ad_title, String type, String time,String isRead,String user_id) {
        super();
        this.msg_title = msg_title;
        this.msg_content = msg_content;
        this.order_no = order_no;
        this.order_id = order_id;
        this.ad_title = ad_title;
        this.type = type;
        this.time = time;
        this.isRead=isRead;
        this.user_id=user_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMsg_title() {
        return msg_title;
    }

    public void setMsg_title(String msg_title) {
        this.msg_title = msg_title;
    }

    public String getMsg_content() {
        return msg_content;
    }

    public void setMsg_content(String msg_content) {
        this.msg_content = msg_content;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getAd_title() {
        return ad_title;
    }

    public void setAd_title(String ad_title) {
        this.ad_title = ad_title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }
}
