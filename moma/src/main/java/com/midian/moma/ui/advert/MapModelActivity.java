package com.midian.moma.ui.advert;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.model.LatLng;
import com.midian.configlib.ServerConstant;
import com.midian.maplib.MapFragment;
import com.midian.maplib.MyPositionFragment;
import com.midian.maplib.MyPostionActivity;
import com.midian.maplib.map.ItemBean;
import com.midian.moma.R;
import com.midian.moma.model.NearMapBean;
import com.midian.moma.model.NearMapBean.NearContent;
import com.midian.moma.model.ShoppingCartBean;
import com.midian.moma.utils.AppUtil;

import java.io.Serializable;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import midian.baselib.base.BaseFragmentActivity;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.FDDataUtils;
import midian.baselib.utils.UIHelper;

/**
 * 广告位-地图模式
 */
public class MapModelActivity extends BaseFragmentActivity implements MapFragment.MapFragmentListener, View.OnClickListener {

    private List<NearContent> listBean = new ArrayList<NearContent>();
    MapFragment mMapFragment;
    private String address, ad_id, lon, lat;
    private String type;

    List<ItemBean> itemBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_model);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.location).setOnClickListener(this);
        findView(R.id.refresh).setOnClickListener(this);
        mMapFragment = new MapFragment();
        fm.beginTransaction().replace(getFragmentContentId(), mMapFragment).commit();
    }

    @Override
    public int getFragmentContentId() {//换成布局id
        return R.id.content_fl;
    }

    @Override
    public void getNetData(String left_top_lon, String left_top_lat, String right_bottom_lon, String right_bottom_lat) {//数据请求
        AppUtil.getMomaApiClient(ac).nearAdvertisements(left_top_lon, left_top_lat, right_bottom_lon, right_bottom_lat, this);
    }


    @Override
    public void onApiSuccess(NetResult res, String tag) {
        super.onApiSuccess(res, tag);
        hideLoadingDlg();
        if (res.isOK()) {
            NearMapBean bean = (NearMapBean) res;
            listBean = bean.getContent();
            itemBeans = new ArrayList<>();
            for (NearContent content : listBean) {
                System.out.println("地图请求成功的图片：：：" + content.getPic_thumb_name());
                itemBeans.add(new ItemBean(content.getAd_id(), content.getPic_thumb_name(), content.getLat(), content.getLon(), null));
            }
            mMapFragment.refreshMap(itemBeans);//装载数据
        } else {
            ac.handleErrorCode(_activity, res.error_code);
        }
    }

    @Override
    public void onApiStart(String tag) {
        super.onApiStart(tag);
        showLoadingDlg();
    }

    @Override
    public void onApiFailure(Throwable t, int errorNo, String strMsg, String tag) {
        super.onApiFailure(t, errorNo, strMsg, tag);
        hideLoadingDlg();
        if ("网络异常".equals(strMsg)) {
            finish();
        }
    }

    @Override
    public void onClickMark(boolean isOne, Bundle b) {//跳转详情或者列表
        if (isOne) {
            System.out.println("onClickMark：不重叠：id：" + b.getString("id"));
            UIHelper.jump(_activity, AdvertDetailActivity.class, b);
        } else {
            System.out.println("重叠的数量::" + listBean.size() + "---ids为:::" + b.getString("ids"));
            UIHelper.jump(_activity, MapListActivity.class, b);//中转到地图附近列表页
        }

    }

    @Override
    public void initMarkView(TextView title, TextView number, ImageView img, String titleStr, String url, int count) {//图标初始化
        System.out.println("initMarkView---URL==" + url);
        System.out.println("initMarkView---count==" + count);
//        mMapFragment.getImage(url);
        if (count < 1) {
            number.setVisibility(View.GONE);
            img.setVisibility(View.GONE);
        } else if (count == 1) {
            number.setVisibility(View.GONE);
            ac.setImage(img, url);
        } else if (count > 1) {
            img.setVisibility(View.VISIBLE);
            number.setVisibility(View.VISIBLE);
            number.setText("" + count);
            ac.setImage(img, url);
        }
        title.setVisibility(View.GONE);
    }

    public void initBitmapMarkView(TextView title, TextView number, ImageView img, String titleStr, Bitmap bitmap, int count) {//图标初始化
        img.setImageBitmap(bitmap);
        number.setText("" + count);
        title.setVisibility(View.GONE);
    }

    @Override
    public void changeMarkView(TextView title, TextView number, ImageView img, String titleStr, String url, int count, boolean select) {//更新图标状态图标初始化

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.location:
                mMapFragment.location();
                break;
            case R.id.refresh:
                if (itemBeans == null) {
                    return;
                } else {
                    mMapFragment.refreshMap(itemBeans);//装载数据
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//		mMapFragment=null;
    }
}
