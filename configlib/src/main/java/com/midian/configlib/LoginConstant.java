package com.midian.configlib;

/**
 * 注册登录url
 * 
 * @author MIDIAN
 * 
 */
public class LoginConstant {
	public static final String BASEURL = ServerConstant.HOST
			+ "MomaApp/";// 摩玛
	/**
	 * 1.1.注册
	 */
	public static final String REGISTER = BASEURL + "register";
	/**
	 * 1.2登录
	 */
	public static final String LOGIN = BASEURL + "login";
	/**
	 * 1.3第三方登录
	 */
	public static final String THIRD_USER_LOGIN = BASEURL + "third_user_login";
	/**
	 * 1.4发送验证码
	 */
	public static final String SEND_CODE = BASEURL + "send_code";
	/**
	 * 1.5验证验证码
	 */
	public static final String VALIDATE_CODE = BASEURL + "validate_code";
	/**
	 * 1.6验证原密码
	 */
	public static final String VALIDATE_PWD = BASEURL + "validate_pwd";
	/**
	 * 1.7修改密码
	 */
	public static final String UPDATE_PWD = BASEURL + "update_pwd";
	/**
	 * 1.8个人资料
	 */
	public static final String USER_DETAIL = BASEURL + "user_detail";
	/**
	 * 1.9修改个人资料
	 */
	public static final String UPDATE_USER = BASEURL + "update_user";
	/**
	 * 1.10修改手机号
	 */
	public static final String UPDATE_PHONE = BASEURL + "update_phone";
	/**
	 * 1.11更改会员推送接收状态
	 */
	public static final String UPDATE_RECEIVE_STATUS = BASEURL
			+ "update_receive_status";
	/**
	 * 1.12找回密码
	 */
	public static final String FIND_PWD = BASEURL + "find_pwd";
    /**
     * 2.2系统配置
     */
    public static final String SYS_CONF_LIST = BASEURL + "sys_conf_list";
    /**
     * 2.4发送用户设备号、清空设备号
     */
    public static final String SAVE_DEVICE = BASEURL + "save_device";
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
}
