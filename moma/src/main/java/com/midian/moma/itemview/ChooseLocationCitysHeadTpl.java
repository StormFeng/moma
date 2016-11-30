package com.midian.moma.itemview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.midian.login.R;
import com.midian.login.bean.CityBean;
import com.midian.login.bean.MultiItem;
import com.midian.maplib.LocationUtil;
import com.midian.moma.ui.home.ChooseCitysActivity;
import com.midian.moma.utils.AppUtil;

import midian.baselib.api.ApiCallback;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.UIHelper;
import midian.baselib.view.BaseTpl;

public class ChooseLocationCitysHeadTpl extends BaseTpl<MultiItem> implements View.OnClickListener, ApiCallback {
    private TextView loc_city_tv;

    public ChooseLocationCitysHeadTpl(Context context) {
        super(context);
    }

    public ChooseLocationCitysHeadTpl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initView() {
        loc_city_tv = findView(R.id.loc_city);
        root.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_choose_location_citys_head_tpl;
    }

    @Override
    public void setBean(MultiItem bean, int position) {

        if ("".equals(ac.loc_city)||ac.loc_city==null) {
            loc_city_tv.setText("定位失败  (点击重新定位)");
        }else if (ac.loc_city_id==null||"".equals(ac.loc_city_id)) {
            loc_city_tv.setText(ac.loc_citys + "   " + ac.loc_city + "  (该地区暂无数据)");
        } else {
            loc_city_tv.setText(ac.loc_citys + "   " + ac.loc_city);
        }
    }

    @Override
    public void onClick(View view) {
        if ("".equals(ac.loc_city)) {
            LocationBd();
        } else if (!TextUtils.isEmpty(ac.loc_city_id) && !TextUtils.isEmpty(ac.loc_city)) {
            ac.saveCityName(ac.loc_city, ac.loc_city_id);
            ac.loc_city_level = "3";
            _activity.finish();
        }
    }

    private void LocationBd() {
        LocationUtil.getInstance(_activity).startOneLocation(new LocationUtil.OneLocationListener() {
            @Override
            public void complete(BDLocation location) {
                String lon = location.getLongitude() + "";
                String lat = location.getLatitude() + "";
                ac.loc_citys = location.getCity();//获取百度定位的当前所在城市
                ac.loc_city = location.getDistrict();//获取百度定位当前所在的市区
//                ac.loc_city = ac.loc_city.replace("市", "");
//                ac.saveCityName(ac.loc_city, "");
//                System.out.println("百度定位失败重新定位的值：：ac.loc_citys::" + ac.loc_citys + ":::市区ac.loc_city:::" + ac.loc_city + ":::城市等级ac.level:" + ac.loc_city_level);
                loadData();
                ac.saveLocation(lon, lat);
            }
        });
    }

    private void loadData() {
        AppUtil.getMomaApiClient(ac).getCity(ac.loc_city,this);
    }


    @Override
    public void onApiStart(String tag) {

    }

    @Override
    public void onApiLoading(long count, long current, String tag) {

    }

    @Override
    public void onApiSuccess(NetResult res, String tag) {
        if ("getCity".equals(tag)) {
            if (res.isOK()) {
                CityBean mCityBean = (CityBean) res;
                if (mCityBean.getContent() != null) {
                    ac.saveLocCityName(mCityBean.getContent().getRegion_name(), mCityBean.getContent().getRegion_id(), mCityBean.getContent().getRegion_level());
                    if (TextUtils.isEmpty(ac.city_name)) {
                        ac.saveCityName(mCityBean.getContent().getRegion_name(), mCityBean.getContent().getRegion_id());
                        listViewHelper.refresh();
                    }
                }
            } else {
                //ac.handleErrorCode(_activity, res.error_code);
            }
        }
    }

    @Override
    public void onApiFailure(Throwable t, int errorNo, String strMsg, String tag) {

    }

    @Override
    public void onParseError(String tag) {

    }
}
