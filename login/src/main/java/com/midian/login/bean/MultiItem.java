package com.midian.login.bean;

import midian.baselib.bean.NetResult;
import com.midian.login.bean.ProvincesBean.City;
import com.midian.login.bean.ProvincesBean.ProvincesContent;

import java.util.ArrayList;

/**
 * Created by chu on 2016/1/6.
 */
public class MultiItem extends NetResult {
    public ProvincesContent provincesContent;
    public City city;
    public ProvincesBean.Area area;
}
