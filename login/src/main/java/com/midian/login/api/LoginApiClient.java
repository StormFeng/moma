package com.midian.login.api;

import android.text.TextUtils;

import com.midian.configlib.LoginConstant;
import com.midian.fastdevelop.afinal.http.AjaxParams;
import com.midian.login.bean.CityBean;
import com.midian.login.bean.LoginBean;
import com.midian.login.bean.ProvincesBean;
import com.midian.login.bean.PushMessageListBean;
import com.midian.login.bean.RegisterBean;
import com.midian.login.bean.SaveDeviceBean;
import com.midian.login.bean.SysConfListBean;
import com.midian.login.bean.UpdateUserBean;
import com.midian.login.bean.UserDetailBean;
import com.midian.login.bean.ValidatePwdBean;

import java.io.File;
import java.io.FileNotFoundException;

import midian.baselib.api.ApiCallback;
import midian.baselib.api.BaseApiClient;
import midian.baselib.app.AppContext;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.Md5Utils;

/**
 * 网络请求客户端基类
 *
 * @author moshouguan
 */
public class LoginApiClient extends BaseApiClient {

    public LoginApiClient(AppContext api) {
        super(api);
    }

    /**
     * 1.1.注册
     *
     * @param account
     * @param password
     * @param code
     * @param head
     * @param name
     * @param sex
     * @param device_token
     * @param callback
     * @throws FileNotFoundException
     */
    public void register(String account, String password, String code,
                         File head, String name, String sex, String device_token,
                         ApiCallback callback) throws FileNotFoundException {

        AjaxParams params = new AjaxParams();
        params.setHasFile(true);
        params.put("client_key", ac.getClientKey());
        params.put("account", account);
        params.put("password", Md5Utils.md5(password));
        params.put("code", code);
        if (head != null) {
            params.put("head", head);
        }
        if (!TextUtils.isEmpty(name)) {
            params.put("name", name);
        }
        if (!TextUtils.isEmpty(sex)) {
            params.put("sex", sex);
        }
        if (!TextUtils.isEmpty(ac.device_token)) {
            params.put("device_token", ac.device_token);
        }

        post(callback, LoginConstant.REGISTER, params, RegisterBean.class,
                getMethodName(Thread.currentThread().getStackTrace()));
    }

    /**
     * 1.2登录
     *
     * @param account
     * @param password
     * @param device_token
     * @param callback
     */
    public void login(String account, String password, String device_token,
                      ApiCallback callback) {
        AjaxParams params = ac.getCAjaxParams();
        params.put("account", account);
        params.put("password", Md5Utils.md5(password));
        params.put("device_token", device_token);
        post(callback, LoginConstant.LOGIN, params, LoginBean.class,
                getMethodName(Thread.currentThread().getStackTrace()));
    }


    /**
     * 1.3发送验证码
     *
     * @param phone
     * @param callback
     */
    public void sendCode(String phone, String type, ApiCallback callback) {
        AjaxParams params = ac.getCAjaxParams();
//        params.put("client_key", ac.getClientKey());
        params.put("phone", phone);
        params.put("type", type);
        post(callback, LoginConstant.SEND_CODE, params, NetResult.class,
                getMethodName(Thread.currentThread().getStackTrace()));
    }


    /**
     * 1.5验证验证码
     *
     * @param phone
     * @param code
     * @param callback
     */
    public void validateCode(String phone, String code, ApiCallback callback) {
        // if (TextUtils.isEmpty(phone)) {
        // UIHelper.t(ac,
        // ac.getResources().getString(R.string.phone_cannot_empty));
        // return;
        // }
        // if (!Func.isMobileNO(phone)) {
        // UIHelper.t(ac,
        // ac.getResources().getString(R.string.phone_format_error));
        // return;
        // }
        // if (TextUtils.isEmpty(validCode)) {
        // UIHelper.t(
        // ac,
        // ac.getResources().getString(
        // R.string.verification_cannot_empty));
        // return;
        // }

        AjaxParams params = ac.getCAjaxParams();
        params.put("phone", phone);
        params.put("code", code);
        get(callback, LoginConstant.VALIDATE_CODE, params,
                NetResult.class, getMethodName(Thread.currentThread()
                        .getStackTrace()));
    }

    /**
     * 1.6验证原密码
     *
     * @param pwd
     * @param callback
     */
    public void validatePwd(String pwd, ApiCallback callback) {
        AjaxParams params = ac.getCAjaxParams();
        params.put("pwd", pwd);
        get(callback, LoginConstant.VALIDATE_PWD, params,
                ValidatePwdBean.class, getMethodName(Thread.currentThread()
                        .getStackTrace()));
    }

    /**
     * 1.7修改密码
     *
     * @param pwd
     * @param callback
     */
    public void updatePwd(String pwd, ApiCallback callback) {

        // if (TextUtils.isEmpty(pwd)) {
        // DpUIHelper
        // .t(ac,
        // ac.getResources().getString(
        // R.string.password_cannot_empty));
        // return;
        // }
        // if (!pwd.equals(pwd_again)) {
        // DpUIHelper.t(
        // ac,
        // ac.getResources().getString(
        // R.string.two_time_password_not_same));
        // return;
        // }
        AjaxParams params = ac.getCAjaxParams();
        params.put("pwd", Md5Utils.md5(pwd));
        post(callback, LoginConstant.UPDATE_PWD, params, NetResult.class,
                getMethodName(Thread.currentThread().getStackTrace()));
    }


    /**
     * 1.8 个人资料
     *
     * @param callback
     */
    public void getUserDetail(ApiCallback callback) {
        AjaxParams params = ac.getCAjaxParams();
        get(callback, LoginConstant.USER_DETAIL, params, UserDetailBean.class,
                getMethodName(Thread.currentThread().getStackTrace()));
    }

    /**
     * 1.9修改个人资料
     *
     * @param callback
     */
    public void updateUser(File head, String name, String sex, String phone
            , String address, ApiCallback callback) throws FileNotFoundException {

        AjaxParams params = ac.getCAjaxParams();
        params.setHasFile(true);
        if (head != null) {
            params.put("head", head);
        }
        if (!TextUtils.isEmpty(name)) {
            params.put("name", name);
        }
        if (!TextUtils.isEmpty(sex)) {
            params.put("sex", sex);
        }
        if (!TextUtils.isEmpty(phone)) {
            params.put("phone", phone);
        }
        if (!TextUtils.isEmpty(address)) {
            params.put("address", address);
        }

//        if (!TextUtils.isEmpty(province_id)) {
//            params.put("province_id", province_id);
//        }
//        if (!TextUtils.isEmpty(province_name)) {
//            params.put("province_name", province_name);
//        }
//        if (!TextUtils.isEmpty(city_id)) {
//            params.put("city_id", city_id);
//        }
//        if (!TextUtils.isEmpty(city_name)) {
//            params.put("city_name", city_name);
//        }
//        if (!TextUtils.isEmpty(area_id)) {
//            params.put("area_id", area_id);
//        }
//        if (!TextUtils.isEmpty(area_name)) {
//            params.put("area_name", area_name);
//        }


        post(callback, LoginConstant.UPDATE_USER, params, UpdateUserBean.class,
                getMethodName(Thread.currentThread().getStackTrace()));
    }

    /**
     * 预留接口
     * 1.10修改手机号
     *
     * @param user_id
     * @param pwd
     * @param callback

    public void updatePhone(String new_phone, ApiCallback callback) {
    AjaxParams params = ac.getCAjaxParams();
    params.put("new_phone", new_phone);
    post(callback, LoginConstant.UPDATE_PHONE, params,
    UpdatePhoneBean.class, getMethodName(Thread.currentThread()
    .getStackTrace()));
    }
     */


    /**
     * 1.11更改会员推送接收状态
     *
     * @param receive_status
     * @param callback
     */
    public void updateReceiveStatus(String receive_status, ApiCallback callback) {
        AjaxParams params = ac.getCAjaxParams();
        params.put("receive_status", receive_status);
        post(callback, LoginConstant.UPDATE_RECEIVE_STATUS, params,
                NetResult.class, getMethodName(Thread
                        .currentThread().getStackTrace()));
    }

    /**
     * 1.12找回密码
     *
     * @param pwd
     * @param callback
     */
    public void getFindPwd(String phone, String pwd, ApiCallback callback) {
        AjaxParams params = ac.getCAjaxParams();
        params.put("phone", phone);
        params.put("pwd", Md5Utils.md5(pwd));
        post(callback, LoginConstant.FIND_PWD, params, NetResult.class,
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

        get(callback, LoginConstant.SYS_CONF_LIST, params, SysConfListBean.class,
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
        post(callback, LoginConstant.SAVE_DEVICE, params, SaveDeviceBean.class,
                getMethodName(Thread.currentThread().getStackTrace()));
    }


    /**
     * 10.1省份、直辖市列表
     *
     */
    public ProvincesBean getProvinces( ) throws Exception {
        AjaxParams params = ac.getCAjaxParams();
        return (ProvincesBean) getSync(LoginConstant.PROVINCES, params, ProvincesBean.class);
    }

    /**
     * 10.2根据城市名称获取城市
     *
     * @param city_name
     * @param callback
     */
    public void getCity(String city_name, ApiCallback callback) {
        AjaxParams params = ac.getCAjaxParams();
        params.put("city_name", city_name);
        get(callback, LoginConstant.CITY, params, CityBean.class,
                getMethodName(Thread.currentThread().getStackTrace()));
    }

    /**
     * 11.1推送消息列表
     *
     */
    public PushMessageListBean pushMessageList( ) throws Exception {
        AjaxParams params = ac.getCAjaxParams();
        return (PushMessageListBean) getSync(LoginConstant.PUSH_MESSAGE_LIST, params, PushMessageListBean.class);
    }



    static public void init(AppContext appcontext) {
        if (appcontext == null)
            return;
        appcontext.api.addApiClient(new LoginApiClient(appcontext));
    }


}
