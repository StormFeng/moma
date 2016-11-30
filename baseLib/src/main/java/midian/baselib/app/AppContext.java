package midian.baselib.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.midian.baselib.R;
import com.midian.configlib.ServerConstant;
import com.midian.fastdevelop.afinal.http.AjaxParams;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import midian.baselib.api.ApiClient;
import midian.baselib.utils.EncryptionUtil;
import midian.baselib.utils.ResourceUtil;
import midian.baselib.utils.UIHelper;

/**
 * Created by XuYang on 15/4/13.
 */
public class AppContext extends Application {

    public static String PAGE_SIZE = "15";

    public String androidKey = "A_iAS5d583PiR3kt2UQA";
    public String user_id = "";// 用户id
    public String access_token = "";// 访问token
    public String name = "";// 姓名
    public String phone = "";//手机
    public String address = "";//地址
    public String sex = "";//性别
    public String user_type = "";
    public String portrait = "";// 头像链接
    public String hardwareCount = "";// 媒介设备
    public String equipmentCount = "";// 智能设备
    public String account = "";// 账号
    public String password = "";// 密码
    public String authorization_code = "";// 授权码
    public String xid = "";// 协议转换器物理地址
    public String hardware_id = "";// 当前协议转换器id
    public String head_pic = "";// 头像链接
    public String lat = "";// 当前经纬度
    public String lon = "";// 当前经纬度
    public boolean ischange = false;
    public Activity mHome;
    public DisplayImageOptions options;
    public ImageLoader imageLoader;// 图片加载类
    public ApiClient api;// 网络请求帮助类
    private Class login;
    public String device_token = "";// 设备号
    private ResourceUtil resourceUtil;
    public String city_name = "";//选择城市的值
    public String city_id = "";//城市的id
    public String area_id = "";//区的id
    public String area_name = "";//区的name
    public String loc_citys = "";//百度定位的城市
    public String loc_city = "";//百度定位的城市城市的区域
    public String loc_city_id = "";//百度定位的城市城市区id值
    public String loc_city_level = "";//百度定位的城市等级
    public boolean isClosePush = false;// 是否接收推送
    public boolean isHasNewVersion = false;// 是否是新版本
    public boolean isSign = false;//是否提醒签到
    public boolean isSendFirst = false;//是否第一次发送闹钟注册
    public boolean isFirst = false;
    public boolean homeIsFirst = false;//判断首页的浮层提示只启动一次
    public boolean advertIsFirst = false;//判断广告页的浮层提示只启动一次
    @Override
    public void onCreate() {
        super.onCreate();
        api = new ApiClient(this);
        resourceUtil = new ResourceUtil(this);
        initData();
        // 关闭友萌的自动统计分析功能
        initUImageLoader();
        final TelephonyManager tm = (TelephonyManager) getBaseContext()
                .getSystemService(Context.TELEPHONY_SERVICE);

    }

    private void initData() {
        user_id = getProperty("user_id");
        access_token = getProperty("access_token");
        name = getProperty("name");
        phone = getProperty("phone");
        address = getProperty("address");
        sex = getProperty("sex");
        user_type = getProperty("user_type");
        account = getProperty("account");
        portrait = getProperty("portrait");
        head_pic = getProperty("head_pic");
        hardwareCount = getProperty("hardwareCount");
        equipmentCount = getProperty("equipmentCount");
        xid = getProperty("xid");
        authorization_code = getProperty("authorization_code");
        password = getProperty("password");
        hardware_id = getProperty("hardware_id");
        city_name = getProperty("city_name");
        city_id = getProperty("city_id");
        area_id = getProperty("area_id");
        area_name = getProperty("area_name");
        loc_city = getProperty("loc_city");
        loc_citys = getProperty("loc_citys");
        loc_city_id = getProperty("loc_city_id");
        loc_city_level = getProperty("loc_city_level");
        lon = getProperty("lon");
        lat = getProperty("lat");
        isClosePush = getBoolean("isClosePush");
        isHasNewVersion = getBoolean("isHasNewVersion");
        device_token = getProperty("device_token");
        isSign = getBoolean("isSign");
        isSendFirst = getBoolean("isSendFirst");
        isFirst = getBoolean("isFirst");
        homeIsFirst = getBoolean("homeIsFirst");
        advertIsFirst = getBoolean("advertIsFirst");
    }

    public boolean isLogin() {
        return !TextUtils.isEmpty(access_token);
    }


    public String getClientKey() {
        return EncryptionUtil.getEncryptionStr();
    }

    public void setImage(final ImageView iv, String url) {
        if (url.contains(ServerConstant.BASEFILEURL) || url.contains("https://") || url.contains("http://")) {

        } else {
            url = ServerConstant.BASEFILEURL + url;
        }
        imageLoader.displayImage(url, iv, new SimpleImageLoadingListener() {

            @Override
            public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
                // TODO Auto-generated method stub
                // iv.setImageBitmap(arg2);
            }

        });
    }

    public String getFileUrl(String url) {
        if (url.contains(ServerConstant.BASEFILEURL) || url.contains("https://") || url.contains("http://")) {

        } else {
            url = ServerConstant.BASEFILEURL + url;
        }
        return url;
    }

    /**
     * 设置推送id
     *
     * @param device_token
     */
    public void saveDeviceToken(String device_token) {
        this.device_token = device_token;
        setProperty("device_token", device_token);
    }

    /**
     * 设置是否提醒签到
     * @param isSign
     */
    public void isSign(boolean isSign) {
        this.isSign = isSign;
        setBoolean("isSign", isSign);
    }
    /**
     * 设置第一次闹钟发送注册
     * @param isSendFirst
     */
    public void isSendFirst(boolean isSendFirst) {
        this.isSendFirst = isSendFirst;
        setBoolean("isSendFirst", isSendFirst);
    }
    /**
     * 是否第一次启动
     * @param isFirst
     */
    public void isFirst(boolean isFirst) {
        this.isFirst = isFirst;
        setBoolean("isFirst", isFirst);
    }

    public void setHomeIsFirst(boolean isFirst) {
        this.homeIsFirst = isFirst;
        setBoolean("homeIsFirst", isFirst);
    }

    public void setAdvertIsFirst(boolean isFirst) {
        this.advertIsFirst = isFirst;
        setBoolean("advertIsFirst", isFirst);
    }

    /**
     * 设置是否接收
     *
     * @param isClosePush
     */
    public void isClosePush(boolean isClosePush) {
        this.isClosePush = isClosePush;
        setBoolean("isClosePush", isClosePush);
    }

    /**
     * 是否有新版本
     *
     * @param isHasNewVersion
     */
    public void isHasNewVersion(boolean isHasNewVersion) {
        this.isHasNewVersion = isHasNewVersion;
        setBoolean("isHasNewVersion", isHasNewVersion);
    }

    /**
     * 保存定位获取到的经纬度
     *
     * @param lon
     * @param lat
     */
    public void saveLocation(String lon, String lat) {
        this.lat = lat;
        this.lon = lon;
        setProperty("lat", lat);
        setProperty("lon", lon);
    }

    /**
     * 保存选择的城市
     *
     * @param city_name
     * @param city_id***
     */
    public void saveCityName(String city_name, String city_id) {
        this.city_name = city_name;
        this.city_id = city_id;
        setProperty("city_id", city_id);
        setProperty("city_name", city_name);
    }

    /**
     * 定位的城市
     */
    public void saveLocCityName(String loc_city, String loc_city_id,String loc_city_level) {
        this.loc_city = loc_city;
        this.loc_city_id = loc_city_id;
        this.loc_city_level=loc_city_level;
        setProperty("loc_city", loc_city);
        setProperty("loc_city_id", loc_city_id);
        setProperty("loc_city_level", loc_city_level);
    }

    /**
     * 获取通用的请求参数
     *
     * @return
     */
    public AjaxParams getCAjaxParams() {
        AjaxParams params = new AjaxParams();
        if (!TextUtils.isEmpty(user_id))
            params.put("user_id", user_id + "");

        if (!TextUtils.isEmpty(access_token))
            params.put("access_token", access_token + "");

        params.put("client_key", getClientKey());
        return params;
    }

    public void setImage(ImageView iv, int id) {
        imageLoader.displayImage("drawable://" + id, iv);
    }

    // public void setImage(View iv, int id) {
    //
    // imageLoader.displayImage("drawable://" + id,(ImageAware) iv,new
    // SimpleImageLoadingListener(){
    // @Override
    // public void onLoadingComplete(String imageUri, View view,
    // Bitmap loadedImage) {
    //
    // // iv.setBackground(loadedImage.);
    // }
    // });
    // }

    private void initUImageLoader() {
        Options decodingOptions = new Options();
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.c_loading)
                .showImageForEmptyUri(R.drawable.c_loading_failure)
                .showImageOnFail(R.drawable.c_loading_failure)
                .decodingOptions(decodingOptions).cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext()).defaultDisplayImageOptions(options)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs().build();
        ImageLoader.getInstance().init(config);
        imageLoader = ImageLoader.getInstance();
    }

    /**
     * 是否存在access_token
     *
     * @return
     */
    public boolean isAccess() {
        if (TextUtils.isEmpty(access_token)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 是否包含指定key的配置信息
     *
     * @param key
     * @return
     */
    public boolean containsProperty(String key) {
        SharedPreferences sp = AppConfig.getSharedPreferences(this);
        return sp.contains(key);
    }

    /**
     * 设置指定key的配置信息
     *
     * @param key
     * @param value
     */
    public void setProperty(String key, String value) {
        AppConfig.getAppConfig(this).set(key, value);
    }

    /**
     * 设置指定key的配置信息
     *
     * @param key
     * @param value
     */
    public void setBoolean(String key, boolean value) {
        AppConfig.getAppConfig(this).set(key, value);
    }

    /**
     * 设置指定key的配置信息s
     *
     * @param key
     * @param value
     */
    public void setInteger(String key, int value) {
        AppConfig.getAppConfig(this).set(key, value);
    }

    /**
     * 获得指定key的配置信息
     *
     * @param key
     * @return
     */
    public String getProperty(String key) {
        return AppConfig.getAppConfig(this).get(key);
    }

    /**
     * 获得指定key的配置信息
     *
     * @param key
     * @return
     */
    public boolean getBoolean(String key) {
        return AppConfig.getAppConfig(this).getBoolean(key);
    }

    /**
     * 获得指定key的配置信息
     *
     * @param key
     * @return
     */
    public int getInteger(String key) {
        return AppConfig.getAppConfig(this).getInteger(key);
    }

    /**
     * 刪除指定key的配置信息
     *
     * @param key
     */
    public void removeProperty(String... key) {
        AppConfig.getAppConfig(this).remove(key);
    }

    /**
     * 保存对象
     *
     * @param ser
     * @param file
     * @throws IOException
     */
    public boolean saveObject(Serializable ser, String file) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = openFileOutput(file, MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(ser);
            oos.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                oos.close();
            } catch (Exception e) {
            }
            try {
                fos.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * 读取对象
     *
     * @param file
     * @return
     * @throws IOException
     */
    public Serializable readObject(String file) {
        if (!isExistDataCache(file))
            return null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = openFileInput(file);
            ois = new ObjectInputStream(fis);
            return (Serializable) ois.readObject();
        } catch (FileNotFoundException e) {
        } catch (Exception e) {
            e.printStackTrace();
            // 反序列化失败 - 删除缓存文件
            if (e instanceof InvalidClassException) {
                File data = getFileStreamPath(file);
                data.delete();
            }
        } finally {
            try {
                ois.close();
            } catch (Exception e) {
            }
            try {
                fis.close();
            } catch (Exception e) {
            }
        }
        return null;
    }

    /**
     * 判断缓存是否存在
     *
     * @param cachefile
     * @return
     */
    public boolean isExistDataCache(String cachefile) {
        boolean exist = false;
        File data = getFileStreamPath(cachefile);
        if (data.exists())
            exist = true;
        return exist;
    }

    /**
     * 保存用户信息
     *
     * @param user_id
     * @param access_token
     * @param name
     * @param head_pic
     */
    public void saveUserInfo(String user_id, String access_token, String name, String user_type
            , String head_pic, String phone, String sex, String address) {
        ischange = true;
        this.user_id = user_id;
        this.access_token = access_token;
        this.name = name;
        this.user_type = user_type;
        this.phone = phone;
        this.sex = sex;
        this.address = address;


        if (!TextUtils.isEmpty(head_pic)) {
            this.head_pic = ServerConstant.BASEFILEURL + head_pic;
        }
        setProperty("user_id", user_id);
        setProperty("access_token", access_token);
        setProperty("name", name);
        setProperty("user_type", user_type);
        setProperty("head_pic", head_pic);
        setProperty("phone", phone);
        setProperty("sex", sex);
        setProperty("address", address);
    }


    /**
     * 清除用户信息
     */
    public void clearUserInfo() {
        user_id = "";
        account = "";
        access_token = "";
        name = "";
        phone = "";
        address = "";
        sex = "";
        user_type = "";
        head_pic = "";
        removeProperty("user_id");
        removeProperty("account");
        removeProperty("access_token");
        removeProperty("name");
        removeProperty("user_type");
        removeProperty("head_pic");
        removeProperty("phone");
        removeProperty("sex");
        removeProperty("address");
    }

    public void saveAccount(String account) {
        this.account = account;
        setProperty("account", account);
    }

    public void savePassword(String password) {
        this.password = password;
        setProperty("password", password);
    }

    public void saveAuthorization_code(String authorization_code) {
        this.authorization_code = authorization_code;
        setProperty("authorization_code", authorization_code);
    }

    /**
     * 要求登录
     *
     * @param context
     * @return
     */
    public boolean isRequireLogin(final Activity context) {
        if (TextUtils.isEmpty(user_id)) {
            if (login != null)
                UIHelper.jump(context, login);
            return false;
        } else {
            return true;
        }
    }

    /**
     * 设置登录页面
     *
     * @param context
     */
    public void setLogin(Class context) {
        this.login = context;
    }

    /**
     * 处理后台返回的错误码
     *
     * @param context
     * @param error_code
     */
    public void handleErrorCode(final Context context, final String error_code) {

        if (TextUtils.isEmpty(error_code)) {
            return;
        }
        // 登录过期
        if ("token_error".equals(error_code) || "tokenTimeLimit".equals(error_code)
                || "tokenError".equals(error_code)) {
            UIHelper.t(context, "您的账号已经在其他设备登陆，请重新登录");
            clearUserInfo();
            UIHelper.jump((Activity) context, login);
        } else {
            UIHelper.t(context, resourceUtil.getString(error_code));
        }

    }

    /**
     * 编辑个人资料的引用类
     */
    public CityData p;
    public CityData city;
    public boolean addresschage;


}
