package com.midian.maplib;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import midian.baselib.base.BaseFragmentActivity;
import midian.baselib.bean.NetResult;

/*覆盖图标分布地图页面模版
 * Created by MIDIAN on 2015/12/16.
 */
public class TmpMap extends BaseFragmentActivity implements MapFragment.MapFragmentListener{

    MapFragment mMapFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapFragment=new MapFragment();
        switchFragment(mMapFragment);
    }


    @Override
    public void onApiSuccess(NetResult res, String tag) {
        super.onApiSuccess(res, tag);
        mMapFragment.refreshMap(null);//装载数据
    }

    public int getFragmentContentId() {//换成布局id
        return -1;
    }

    @Override
    public void getNetData(String left_top_lon, String left_top_lat, String right_bottom_lon, String right_bottom_lat) {//数据请求

    }

    @Override
    public void onClickMark(boolean isOne, Bundle b) {//跳转详情或者列表

    }

    @Override
    public void initMarkView(TextView title, TextView number, ImageView img, String titleStr, String url, int count) {//图标初始化

    }

    @Override
    public void changeMarkView(TextView title, TextView number, ImageView img, String titleStr, String url, int count, boolean select) {//更新图标状态图标初始化

    }

}
