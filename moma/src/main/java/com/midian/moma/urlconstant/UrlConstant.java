package com.midian.moma.urlconstant;

import com.midian.configlib.ServerConstant;

public class UrlConstant {

    public static final String BASEURL = ServerConstant.HOST
            + "MomaApp/";// URL


    public static final String BASEFILEURL = ServerConstant.BASEFILEURL;

    /**
     * 1.13增加签到积分
     */
    public static final String ADD_SIGN_SCORE = BASEURL + "add_sign_score";

    /**
     * 1.13当月签到积分
     */
    public static final String SIGN_SCORE = BASEURL + "sign_score";
    /**
     * 1.14个人中心-订单数量
     */
    public static final String USER_CENTER = BASEURL + "user_center";
    /**
     * 1.4意见反馈
     */
    public static final String FEEDBACK = BASEURL + "feedback";
    /**
     * 2.1.获取最新版本信息
     */
    public static final String VERSION = BASEURL + "feedBack";
    /**
     * 2.2获取系统配置信息
     */
    public static final String SYS_CONF_LIST = BASEURL + "sys_conf_list";

    /**
     * 2.3获取测试包状态
     */
    public static final String BETA_STATUS = BASEURL + "beta_status";
    /**
     * 2.4发送用户设备号、清空设备号
     */
    public static final String SAVE_DEVICE = BASEURL + "save_device";
    /**
     * 2.5
     * 获取系统配置网页连接
     */
    public static final String LINK_PAGE = BASEURL + "link_page";

    /**
     * 3.1.关注
     */
    public static final String COLLECT = BASEURL + "collect";
    /**
     * 3.2取消关注
     */
    public static final String CANCEL_COLLECT = BASEURL + "cancel_collect";

    /**
     * 3.3我的关注
     */
    public static final String MY_COLLECTS = BASEURL + "my_collects";
    /**
     * 4.1首页
     */
    public static final String MOMA_INDEX = BASEURL + "moma_index";

    /**
     * 4.2案例详情
     */
    public static final String CASE_DETAIL = BASEURL + "case_detail";
    /**
     * 4.3
     * 案例类型
     */
    public static final String CASE_TYPES = BASEURL + "case_types";
    /**
     * 4.4点攒
     */
    public static final String OP_CASE_LIKE = BASEURL + "op_case_like";
    /**
     * 5.1广告位列表
     */
    public static final String ADVERTISEMENTS = BASEURL + "advertisements";

    /**
     * 5.2广告位详情
     */
    public static final String ADVERTISEMENT_DETAIL = BASEURL + "advertisement_detail";
    /**
     * 5.3地图附近广告位列表
     */
    public static final String NEAR_ADVERTISEMENTS = BASEURL + "near_advertisements";

    /**
     * 5.4热词接口
     */
    public static final String KEYWORDS = BASEURL + "keywords";
    /**
     * 5.5地图重叠的广告位列表
     */
    public static final String MAP_OVERLAP_ADVERTISEMENT = BASEURL + "map_overlap_advertisement";


    /**
     * 5.6通过子广告获取优惠信息
     */
    public static final String DISCOUNTS = BASEURL + "discounts";

    /**
     * 6.1购物车列表
     */
    public static final String SHOPPING_CART = BASEURL + "shopping_cart";

    /**
     * 6.2加入购物车
     */
    public static final String ADD_SHOPPING_CART = BASEURL + "add_shopping_cart";
    /**
     * 6.3删除购物车物品
     */
    public static final String CANCLE_SHOPPING_CART = BASEURL + "cancle_shopping_cart";

    /**
     * 7.1确认订单
     */
    public static final String CONFIRM_ADVERTISEMENT = BASEURL + "confirm_advertisement";
    /**
     * 7.2购买广告位支付宝回调
     */
    public static final String PAY_ALI_CALLBACK = BASEURL + "pay_ali_callback";

    /**
     * 7.3获取已支付待提交资料订单
     */
    public static final String get_waitmaterialorder = BASEURL + "get_waitMaterialOrder";
    /**
     * 7.4我的订单列表
     */
    public static final String MY_ORDERS = BASEURL + "my_orders";

    /**
     * 7.5订单详情
     */
    public static final String ORDER_DETAIL = BASEURL + "order_detail";
    /**
     * 7.6生成确认订单列表（立即购物接口）
     */
    public static final String SHOW_CONFIRM_ORDERS = BASEURL + "show_confirm_orders";

    /**
     * 7.7取消订单
     */
    public static final String CANCLE_ORDER = BASEURL + "cancle_order";
    /**
     * 7.8支付详情接口
     */
    public static final String PAY_ORDER_DETAIL = BASEURL + "pay_order_detail";
    /**
     * 7.9更改订单编号
     */
    public static final String UPDATE_ORDER_SN = BASEURL + "update_order_sn";
    /**
     * 7.11微信支付订单
     */
    public static final String PAY_WX_ORDER = BASEURL + "pay_wx_order";
    /**
     * 8.1上传广告资料
     */
    public static final String UPLOAD_MATERIAL = BASEURL + "upload_material";

    /**
     * 8.2广告资料详情
     */
    public static final String AD_MATERIAL_DETAIL = BASEURL + "ad_material_detail";

    /**
     * 8.3查看回复列表
     */
    public static final String MATERIAL_REPLY = BASEURL + "material_reply";
    /**
     * 8.4删除广告资料图片
     */
    public static final String DELETE_MATERIAL_PIC = BASEURL + "delete_material_pic";
    /**
     * 9.1确认订单
     */
    public static final String RESERVE_ADVERTISEMENT = BASEURL + "reserve_advertisement";
    /**
     * 9.2我的预订列表
     */
    public static final String MY_RESERVES = BASEURL + "my_reserves";
    /**
     * 9.3取消预订
     */
    public static final String CANCEL_RESERVE = BASEURL + "cancel_reserve";
    /**
     * 10.1省份、直辖市列表
     */
    public static final String PROVINCES = BASEURL + "provinces";

    /**
     * 10.2根据城市名称获取城市
     */
    public static final String CITY = BASEURL + "city";
    /**
     * 11.1推送消息
     */
    public static final String PUSH_MESSAGE_LIST = BASEURL + "push_message_list";
    /**
     * 12.1启动页广告
     */
    public static final String START_ADVERTISE = BASEURL + "start_advertise";
}
