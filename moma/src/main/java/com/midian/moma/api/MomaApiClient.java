package com.midian.moma.api;

import com.midian.fastdevelop.afinal.http.AjaxParams;
import com.midian.login.bean.CityBean;
import com.midian.login.bean.ProvincesBean;
import com.midian.login.bean.SaveDeviceBean;
import com.midian.moma.model.AdMaterialDetailBean;
import com.midian.moma.model.AdvertDetailBean;
import com.midian.moma.model.AdvertListBean;
import com.midian.moma.model.BetaStatusBean;
import com.midian.moma.model.CaseDetailBean;
import com.midian.moma.model.CaseTypesBean;
import com.midian.moma.model.ConfirmOrderBean;
import com.midian.moma.model.DiscountBean;
import com.midian.moma.model.HomeBean;
import com.midian.moma.model.KeywordsBean;
import com.midian.moma.model.MapOverlapBean;
import com.midian.moma.model.MaterialReplyBean;
import com.midian.moma.model.MyBookingListBean;
import com.midian.moma.model.MyCollectsBean;
import com.midian.moma.model.MyOrdersBean;
import com.midian.moma.model.NearMapBean;
import com.midian.moma.model.OrderDetailBean;
import com.midian.moma.model.PayDetailBean;
import com.midian.moma.model.PushMessageListBean;
import com.midian.moma.model.ShoppingCartBean;
import com.midian.moma.model.ShowConfirmOrderBean;
import com.midian.moma.model.SignScoreBean;
import com.midian.moma.model.StartAdvertiseBean;
import com.midian.moma.model.SysConfListBean;
import com.midian.moma.model.UploadMaterialBean;
import com.midian.moma.model.UserCenterBean;
import com.midian.moma.model.VersionBean;
import com.midian.moma.model.WaitCommitOrderBean;
import com.midian.moma.urlconstant.UrlConstant;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import midian.baselib.api.ApiCallback;
import midian.baselib.api.BaseApiClient;
import midian.baselib.app.AppContext;
import midian.baselib.bean.NetResult;

/**
 * 摩玛接口
 */
public class MomaApiClient extends BaseApiClient {

    public MomaApiClient(AppContext ac) {
        super(ac);
    }

    static public void init(AppContext appcontext) {
        if (appcontext == null)
            return;
        appcontext.api.addApiClient(new MomaApiClient(appcontext));
    }


    /**
     * 1.13增加签到积分
     *
     * @param callback
     */
    public void addSignScore(ApiCallback callback) {
        AjaxParams params = ac.getCAjaxParams();
        post(callback, UrlConstant.ADD_SIGN_SCORE, params, NetResult.class, getMethodName(Thread.currentThread().getStackTrace()));
    }

    /**
     * 1.13当月 签到积分（总积分、当月签到日期
     *
     * @param callback
     */
    public void signScore(ApiCallback callback) {
        AjaxParams params = ac.getCAjaxParams();
        get(callback, UrlConstant.SIGN_SCORE, params, SignScoreBean.class, getMethodName(Thread.currentThread().getStackTrace()));
    }


    /**
     * 1.14个人中心-订单数量
     * @param callback
     */
    public void userCenter(ApiCallback callback) {
        AjaxParams params = ac.getCAjaxParams();
        get(callback, UrlConstant.USER_CENTER, params, UserCenterBean.class, getMethodName(Thread.currentThread().getStackTrace()));
    }



    /**
     * 1.4意见反馈
     *
     * @param text
     * @param callback
     */
    public void feedBack(String text, ApiCallback callback) {
        AjaxParams params = ac.getCAjaxParams();
        params.put("text", text);
        post(callback, UrlConstant.FEEDBACK, params, NetResult.class,
                getMethodName(Thread.currentThread().getStackTrace()));
    }


    /**
     * 2.1获取最新版本信息
     *
     * @param callback
     */
    public void version(ApiCallback callback) {
        AjaxParams params = ac.getCAjaxParams();
        get(callback, UrlConstant.VERSION, params, VersionBean.class,
                getMethodName(Thread.currentThread().getStackTrace()));
    }

    /**
     * 2.2获取系统配置信息
     *
     * @param callback
     */
    public void getSysConfList(String code, ApiCallback callback) {
        AjaxParams params = ac.getCAjaxParams();
        if (code != null) {
            params.put("code", code);
        }
        get(callback, UrlConstant.SYS_CONF_LIST, params, SysConfListBean.class,
                getMethodName(Thread.currentThread().getStackTrace()));
    }

    /**
     * 2.3获取测试包状态
     *
     * @param callback
     */
    public void betaStatus(ApiCallback callback) {
        AjaxParams params = ac.getCAjaxParams();
        get(callback, UrlConstant.BETA_STATUS, params, BetaStatusBean.class,
                getMethodName(Thread.currentThread().getStackTrace()));
    }

    /**
     * 2.4发送用户设备号、清空设备号
     *
     * @param device_token
     * @param callback
     */
    public void saveDevice(String device_token, ApiCallback callback) {
        AjaxParams params = ac.getCAjaxParams();
        params.put("device_token", device_token);
        post(callback, UrlConstant.SAVE_DEVICE, params, SaveDeviceBean.class,
                getMethodName(Thread.currentThread().getStackTrace()));
    }


    /**
     * 2.5获取系统配置网页连接
     *
     * @param code
     * @param callback
     */
    public void getLinkPage(String code, ApiCallback callback) {
        AjaxParams params = new AjaxParams();
        params.put("code", code);
        get(callback, UrlConstant.LINK_PAGE, params, SysConfListBean.class, getMethodName(Thread.currentThread().getStackTrace()));

    }


    /**
     * 3.1关注
     *
     * @param record_id
     * @param type
     * @param callback
     */
    public void collect(String record_id, String type, ApiCallback callback) {
        AjaxParams params = ac.getCAjaxParams();
        params.put("record_id", record_id);
        params.put("type", type);
        post(callback, UrlConstant.COLLECT, params, NetResult.class,
                getMethodName(Thread.currentThread().getStackTrace()));

    }

    /**
     * 3.2取消关注
     *
     * @param collect_ids
     * @param callback
     */
    public void cancelCollect(String collect_ids, ApiCallback callback) {
        AjaxParams params = ac.getCAjaxParams();
        params.put("collect_ids", collect_ids);
        post(callback, UrlConstant.CANCEL_COLLECT, params, NetResult.class,
                getMethodName(Thread.currentThread().getStackTrace()));

    }

    /**
     * 3.3我的关注
     *
     * @param type
     * @param lon
     * @param lat
     * @param page
     * @param size
     */
    public MyCollectsBean myCollects(String type, String lon, String lat, String page, String size) throws Exception {
        AjaxParams params = ac.getCAjaxParams();
        params.put("type", type);
        params.put("lon", lon);
        params.put("lat", lat);
        params.put("page", page);
        params.put("size", size);
        return (MyCollectsBean) getSync(UrlConstant.MY_COLLECTS, params, MyCollectsBean.class);
    }

    /**
     * 4.1首页
     *
     * @param page
     * @param size
     */
    public HomeBean momaIndex(String type_id,String keyWord, String page, String size) throws Exception {
        AjaxParams params = ac.getCAjaxParams();
        if (type_id != null) {
            params.put("type_id", type_id);
        }
        if ("keyWord" != null) {
            params.put("keyWord", keyWord);
        }
        params.put("page", page);
        params.put("size", size);
        return (HomeBean) getSync(UrlConstant.MOMA_INDEX, params, HomeBean.class);

    }


    /**
     * 4.1首页
     *
     * @param page
     * @param size
     */
    public void momaIndex(String type_id,String keyWord, String page, String size, ApiCallback callback) {
        AjaxParams params = ac.getCAjaxParams();
        params.put("type_id", type_id);
        if ("keyWord" != null) {
            params.put("keyWord", keyWord);
        }
        params.put("page", page);
        params.put("size", size);
        get(callback, UrlConstant.MOMA_INDEX, params, HomeBean.class, getMethodName(Thread.currentThread().getStackTrace()));
    }

    /**
     * 4.2案例详情
     *
     * @param case_id
     * @param callback
     */
    public void caseDetail(String case_id, ApiCallback callback) {
        AjaxParams params = ac.getCAjaxParams();
        params.put("case_id", case_id);
        get(callback, UrlConstant.CASE_DETAIL, params, CaseDetailBean.class,
                getMethodName(Thread.currentThread().getStackTrace()));
    }


    /**
     * 4.3案例类型列表
     *
     * @param callback
     */
    public void getCaseTypes(ApiCallback callback) {
        AjaxParams params = new AjaxParams();
        params.put("client_key", ac.getClientKey());
        get(callback, UrlConstant.CASE_TYPES, params, CaseTypesBean.class, getMethodName(Thread.currentThread().getStackTrace()));

    }

    /**
     * 4.4点攒
     *
     * @param case_id
     * @param type
     * @param callback
     */
    public void opCaseLike(String case_id, String type, ApiCallback callback) {
        AjaxParams params = ac.getCAjaxParams();
        params.put("case_id", case_id);
        params.put("type", type);
        post(callback, UrlConstant.OP_CASE_LIKE, params, NetResult.class, getMethodName(Thread.currentThread().getStackTrace()));
    }


    /**
     * 5.1广告位列表
     *
     * @param keywords
     * @param lon
     * @param lat
     * @param type
     * @param page
     * @param size
     */
    public AdvertListBean advertList(String keywords, String lon, String lat, String type, String region_id, String level,
                                     String community_id, String date_time, String page, String size) throws Exception {
        AjaxParams params = ac.getCAjaxParams();
        if (keywords != null) {
            params.put("keywords", keywords);
        }
        params.put("lon", lon);
        params.put("lat", lat);
        if (type != null) {
            params.put("type", type);
        }
        if (region_id != null) {
            params.put("region_id", region_id);
        }
        if (level != null) {
            params.put("level", level);
        }
        if (community_id != null) {
            params.put("community_id", community_id);
        }
        if (date_time != null) {
            params.put("date_time", date_time);
        }
        if (page != null) {
            params.put("page", page);
        }
        if (size != null) {
            params.put("size", size);
        }
        return (AdvertListBean) getSync(UrlConstant.ADVERTISEMENTS, params, AdvertListBean.class);

    }

    /**
     * 5.1广告位列表
     *
     * @param keywords
     * @param lon
     * @param lat
     * @param type
     * @param level
     * @param page
     * @param size
     */
    public void advertList(String keywords, String lon, String lat, String type, String regionId, String level,
                           String community_id, String page, String size, ApiCallback callback) throws Exception {
        AjaxParams params = ac.getCAjaxParams();

        if (keywords != null) {
            params.put("keywords", keywords);
        }
        params.put("lon", lon);
        params.put("lat", lat);
        if (type != null) {
            params.put("type", type);
        }
        if (regionId != null) {
            params.put("regionId", regionId);
        }
        if (level != null) {
            params.put("level", level);
        }
        if (community_id != null) {
            params.put("community_id", community_id);
        }
        if (page != null) {
            params.put("page", page);
        }
        if (size != null) {
            params.put("size", size);
        }
        get(callback, UrlConstant.ADVERTISEMENTS, params, AdvertListBean.class,
                getMethodName(Thread.currentThread().getStackTrace()));

    }

    /**
     * 5.2 广告位详情
     *
     * @param ad_id
     * @param callback
     */
    public void advertisementDetail(String ad_id, ApiCallback callback) {
        AjaxParams params = ac.getCAjaxParams();
        params.put("ad_id", ad_id);
        get(callback, UrlConstant.ADVERTISEMENT_DETAIL, params, AdvertDetailBean.class,
                getMethodName(Thread.currentThread().getStackTrace()));

    }

    /**
     * 5.3地图附近广告位列表
     *
     * @param left_top_lon
     * @param left_top_lat
     * @param right_bottom_lon
     * @param right_bottom_lat
     */
    public NearMapBean nearAdvertisements(String left_top_lon, String left_top_lat, String right_bottom_lon, String right_bottom_lat) throws Exception {
        AjaxParams params = ac.getCAjaxParams();
        params.put("left_top_lon", left_top_lon);
        params.put("left_top_lat", left_top_lat);
        params.put("right_bottom_lon", right_bottom_lon);
        params.put("right_bottom_lat", right_bottom_lat);
        return (NearMapBean) getSync(UrlConstant.NEAR_ADVERTISEMENTS, params, NearMapBean.class);
    }

    /**
     * 5.3地图附近广告位列表
     *
     * @param left_top_lon
     * @param left_top_lat
     * @param right_bottom_lon
     * @param right_bottom_lat
     */
    public void nearAdvertisements(String left_top_lon, String left_top_lat, String right_bottom_lon,
                                   String right_bottom_lat, ApiCallback callback) {
        AjaxParams params = new AjaxParams();
        params.put("client_key", ac.getClientKey());
        params.put("left_top_lon", left_top_lon);
        params.put("left_top_lat", left_top_lat);
        params.put("right_bottom_lon", right_bottom_lon);
        params.put("right_bottom_lat", right_bottom_lat);
        get(callback, UrlConstant.NEAR_ADVERTISEMENTS, params, NearMapBean.class, getMethodName(Thread.currentThread().getStackTrace()));
    }

    /**
     * 5.4热词接口
     */
    public void keywords(ApiCallback callback) {
        AjaxParams params = ac.getCAjaxParams();
        get(callback, UrlConstant.KEYWORDS, params, KeywordsBean.class, getMethodName(Thread.currentThread().getStackTrace()));
    }


    /**
     * 5.5地图重叠广告位列表
     *
     * @param ad_ids
     * @param lon
     * @param lat
     */
    public MapOverlapBean mapOverlapAdvertisement(String ad_ids, String lon, String lat) throws Exception {
        AjaxParams params = new AjaxParams();
        params.put("client_key", ac.getClientKey());
        params.put("ad_ids", ad_ids);
        params.put("lon", lon);
        params.put("lat", lat);
        return (MapOverlapBean) postSync(UrlConstant.MAP_OVERLAP_ADVERTISEMENT, params, MapOverlapBean.class);

    }

    /**
     * 5.6通过子广告位获取优惠信息
     * @param ad_child_id
     * @param callback
     */
    public void getDiscounts(String ad_child_id, ApiCallback callback) {
        AjaxParams params = new AjaxParams();
        params.put("client_key", ac.getClientKey());
        params.put("ad_child_id", ad_child_id);
        get(callback, UrlConstant.DISCOUNTS, params, DiscountBean.class, getMethodName(Thread.currentThread().getStackTrace()));
    }


    /**
     * 6.1购物车列表
     *
     * @param type
     * @param page
     * @param size
     */
    public ShoppingCartBean shoppingCart(String type, String page, String size) throws Exception {
        AjaxParams params = ac.getCAjaxParams();
        params.put("type", type);
        params.put("page", page);
        params.put("size", size);
        return (ShoppingCartBean) getSync(UrlConstant.SHOPPING_CART, params, ShoppingCartBean.class);
    }


    /**
     * 6.2加入购物车
     *
     * @param ad_child_id
     * @param start_time
     * @param end_time
     * @param order_type
     * @param callback
     */
    public void addShoppingCart(String ad_child_id, String start_time, String end_time, String order_type, ApiCallback callback) {
        AjaxParams params = ac.getCAjaxParams();
        params.put("ad_child_id", ad_child_id);
        params.put("start_time", start_time);
        params.put("end_time", end_time);
        params.put("order_type", order_type);
        post(callback, UrlConstant.ADD_SHOPPING_CART, params, NetResult.class,
                getMethodName(Thread.currentThread().getStackTrace()));

    }

    /**
     * 6.3删除购物车物品
     *
     * @param cart_ids
     * @param callback
     */
    public void cancleShoppingCart(String cart_ids, ApiCallback callback) {
        AjaxParams params = ac.getCAjaxParams();
        params.put("cart_ids", cart_ids);
        post(callback, UrlConstant.CANCLE_SHOPPING_CART, params, NetResult.class,
                getMethodName(Thread.currentThread().getStackTrace()));

    }


    /**
     * 7.1确认订单
     *
     * @param pay_type
     * @param user_name
     * @param user_phone
     * @param user_company
     * @param user_address
     * @param cart_ids
     * @param ad_child_id
     * @param start_time
     * @param end_time
     * @param callback
     */
    public void confirmAdvertisement(String pay_type, String user_name, String user_phone, String user_company, String user_address
            , String invoice_type, String invoice_title, String cart_ids, String ad_child_id, String start_time, String end_time, ApiCallback callback) {
        AjaxParams params = ac.getCAjaxParams();
        params.put("pay_type", pay_type);
        params.put("user_name", user_name);
        params.put("user_phone", user_phone);
        params.put("user_company", user_company);
        params.put("user_address", user_address);

        if (invoice_type != null) {
            params.put("invoice_type", invoice_type);
        }
        if (invoice_title != null) {
            params.put("invoice_title", invoice_title);
        }
        if (cart_ids != null) {
            params.put("cart_ids", cart_ids);
        }
        if (ad_child_id != null) {
            params.put("ad_child_id", ad_child_id);
        }
        if (start_time != null) {
            params.put("start_time", start_time);
        }
        if (end_time != null) {
            params.put("end_time", end_time);
        }
        post(callback, UrlConstant.CONFIRM_ADVERTISEMENT, params, ConfirmOrderBean.class,
                getMethodName(Thread.currentThread().getStackTrace()));
    }

    /**
     * 7.2购买广告位支付宝回调
     */

    /**
     * 7.3获取已支付待提交资料订单（删）
     *
     * @param page
     * @param size
     * @param callback
     */
    public void getWaitMaterialOrder(String page, String size, ApiCallback callback) {
        AjaxParams params = ac.getCAjaxParams();
        params.put("page", page);
        params.put("size", size);
        get(callback, UrlConstant.get_waitmaterialorder, params, WaitCommitOrderBean.class,
                getMethodName(Thread.currentThread().getStackTrace()));
    }


    /**
     * 7.4我的订单列表
     *
     * @param type
     * @param page
     * @param size
     */
    public MyOrdersBean myOrders(String type, String page, String size) throws Exception {
        AjaxParams params = ac.getCAjaxParams();
        params.put("type", type);
        params.put("page", page);
        params.put("size", size);
        return (MyOrdersBean) getSync(UrlConstant.MY_ORDERS, params, MyOrdersBean.class);
    }

    /**
     * 7.5订单详情
     *
     * @param order_id
     * @param callback
     */
    public void orderDetail(String order_id, ApiCallback callback) {
        AjaxParams params = ac.getCAjaxParams();
        params.put("order_id", order_id);
        get(callback, UrlConstant.ORDER_DETAIL, params, OrderDetailBean.class,
                getMethodName(Thread.currentThread().getStackTrace()));

    }

    /**
     * 7.6生成确认订单列表（立即购买接口）
     *
     * @param cart_ids
     * @param ad_child_id
     * @param start_time
     * @param end_time
     * @param type
     * @param callback
     */
    public void showConfirmOrders(String cart_ids, String ad_child_id, String start_time, String end_time, String type, ApiCallback callback) {
        AjaxParams params = ac.getCAjaxParams();
        if (cart_ids != null) {
            params.put("cart_ids", cart_ids);
        }
        if (ad_child_id != null) {
            params.put("ad_child_id", ad_child_id);
        }
        if (start_time != null) {
            params.put("start_time", start_time);
        }
        if (end_time != null) {
            params.put("end_time", end_time);
        }
        params.put("type", type);
        get(callback, UrlConstant.SHOW_CONFIRM_ORDERS, params, ShowConfirmOrderBean.class,
                getMethodName(Thread.currentThread().getStackTrace()));
    }

    /**
     * 7.7取消订单
     *
     * @param order_id
     * @param callback
     */
    public void cancleOrder(String order_id, ApiCallback callback) {
        AjaxParams params = ac.getCAjaxParams();
        params.put("order_id", order_id);
        post(callback, UrlConstant.CANCLE_ORDER, params, NetResult.class,
                getMethodName(Thread.currentThread().getStackTrace()));

    }

    /**
     * 7.8支付详情接口
     *
     * @param order_id
     * @param order_sn
     * @param callback
     */
    public void payOrderDetail(String order_id, String order_sn, ApiCallback callback) {
        AjaxParams params = ac.getCAjaxParams();
        if (order_id != null) {
            params.put("order_id", order_id);
        }
        if (order_sn != null) {
            params.put("order_sn", order_sn);
        }
        post(callback, UrlConstant.PAY_ORDER_DETAIL, params, PayDetailBean.class,
                getMethodName(Thread.currentThread().getStackTrace()));
    }

    /**
     * 7.9更改订单编号
     *
     * @param pay_type
     * @param order_id
     * @param callback
     */
    public void updateOrderSn(String pay_type, String order_id, ApiCallback callback) {
        AjaxParams params = ac.getCAjaxParams();
        params.put("pay_type", pay_type);
        params.put("order_id", order_id);
        post(callback, UrlConstant.UPDATE_ORDER_SN, params, ConfirmOrderBean.class,
                getMethodName(Thread.currentThread().getStackTrace()));

    }

    public void payWxOrder(String order_sn, ApiCallback callback) {
        AjaxParams params = ac.getCAjaxParams();
        params.put("order_sn", order_sn);
        post(callback, UrlConstant.PAY_WX_ORDER, params, ConfirmOrderBean.class, getMethodName(Thread.currentThread().getStackTrace()));
    }


    /**
     * 8.1上传广告资料
     *
     * @param order_id
     * @param selectedPhotos
     * @param content
     * @param callback
     * @throws FileNotFoundException
     */
    public void uploadMaterial(String order_id, List<String> selectedPhotos, String content, ApiCallback callback) throws FileNotFoundException {
        AjaxParams params = ac.getCAjaxParams();
        params.setHasFile(true);
        params.put("order_id", order_id);
        for (String s : selectedPhotos) {
            File file = new File(s);
            if (file.exists())
                params.put("pics", file);
        }
        if (content != null) {
            params.put("content", content);
        }
        post(callback, UrlConstant.UPLOAD_MATERIAL, params, UploadMaterialBean.class,
                getMethodName(Thread.currentThread().getStackTrace()));
    }


    /**
     * 8.2广告资料详情
     *
     * @param order_id
     * @param callback
     */
    public void adMaterialDetail(String order_id, ApiCallback callback) {
        AjaxParams params = ac.getCAjaxParams();
        params.put("order_id", order_id);
        get(callback, UrlConstant.AD_MATERIAL_DETAIL, params, AdMaterialDetailBean.class,
                getMethodName(Thread.currentThread().getStackTrace()));
    }


    /**
     * 8.3查看回复列表
     *
     * @param order_id
     * @param page
     * @param size
     */
    public MaterialReplyBean materialReply(String order_id, String page, String size) throws Exception {
        AjaxParams params = ac.getCAjaxParams();
        params.put("order_id", order_id);
        if (page != null) {
            params.put("page", page);
        }
        if (size != null) {
            params.put("size", size);
        }
        return (MaterialReplyBean) getSync(UrlConstant.MATERIAL_REPLY, params, MaterialReplyBean.class);
    }


    /**
     * 8.4删除广告资料图片
     *
     * @param order_id
     * @param pic_ids
     * @param callback
     */
    public void deleteMaterialPic(String order_id, String pic_ids, ApiCallback callback) {
        AjaxParams params = ac.getCAjaxParams();
        params.put("order_id", order_id);
        params.put("pic_ids", pic_ids);
        post(callback, UrlConstant.DELETE_MATERIAL_PIC, params, NetResult.class,
                getMethodName(Thread.currentThread().getStackTrace()));

    }


    /**
     * 9.1确认预订
     *
     * @param user_name
     * @param user_phone
     * @param user_company
     * @param user_address
     * @param cart_ids
     * @param ad_id
     * @param start_time
     * @param end_time
     * @param callback
     */
    public void reserveAdvertisement(String user_name, String user_phone, String user_company, String user_address, String cart_ids,
                                     String ad_id, String start_time, String end_time, ApiCallback callback) {
        AjaxParams params = ac.getCAjaxParams();
        params.put("user_name", user_name);
        params.put("user_phone", user_phone);
        params.put("user_company", user_company);
        params.put("user_address", user_address);
        if (cart_ids != null) {
            params.put("cart_ids", cart_ids);//可选,从购物车进来必选(拼接购物车id)
        }
        params.put("ad_child_id", ad_id);
        if (start_time != null) {
            params.put("start_time", start_time);//可选，自定义时间的情况下必选
        }
        if (end_time != null) {
            params.put("end_time", end_time);//可选，自定义时间的情况下必选
        }

        post(callback, UrlConstant.RESERVE_ADVERTISEMENT, params, NetResult.class,
                getMethodName(Thread.currentThread().getStackTrace()));

    }


    /**
     * 9.2我的预订列表
     *
     * @param type
     * @param page
     * @param size
     * @throws Exception
     */
    public MyBookingListBean myReserves(String type, String page, String size) throws Exception {
        AjaxParams params = ac.getCAjaxParams();
        params.put("type", type);
        params.put("page", page);
        params.put("size", size);
        return (MyBookingListBean) getSync(UrlConstant.MY_RESERVES, params, MyBookingListBean.class);

    }


    /**
     * 9.3取消预订
     *
     * @param reserve_id
     * @param callback
     */
    public void cancelReserve(String reserve_id, ApiCallback callback) {
        AjaxParams params = ac.getCAjaxParams();
        params.put("reserve_id", reserve_id);
        post(callback, UrlConstant.CANCEL_RESERVE, params, NetResult.class,
                getMethodName(Thread.currentThread().getStackTrace()));
    }

    /**
     * 10.1省份、直辖市列表
     *
     * @param callback
     */
    public ProvincesBean getProvinces(ApiCallback callback) throws Exception {
        AjaxParams params = ac.getCAjaxParams();
        return (ProvincesBean) getSync(UrlConstant.PROVINCES, params, ProvincesBean.class);
    }

    /**
     * 10.2根据城市名称获取城市
     *
     * @param name
     * @param callback
     */
    public void getCity(String name, ApiCallback callback) {
        AjaxParams params = new AjaxParams();
        params.put("client_key", ac.getClientKey());
        params.put("name", name);
        get(callback, UrlConstant.CITY, params, CityBean.class,
                getMethodName(Thread.currentThread().getStackTrace()));
    }

    /**
     * 11.1推送消息列表
     */
    public PushMessageListBean pushMessageList() throws Exception {
        AjaxParams params = ac.getCAjaxParams();
        return (PushMessageListBean) getSync(UrlConstant.PUSH_MESSAGE_LIST, params, PushMessageListBean.class);
    }

    /**
     * 10.2根据城市名称获取城市
     *
     * @param
     * @param callback
     */
    public void getStartAdvertise(ApiCallback callback) {
        AjaxParams params = new AjaxParams();
        params.put("client_key", ac.getClientKey());
        get(callback, UrlConstant.START_ADVERTISE, params, StartAdvertiseBean.class,
                getMethodName(Thread.currentThread().getStackTrace()));
    }
}
