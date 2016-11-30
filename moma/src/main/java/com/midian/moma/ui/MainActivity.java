package com.midian.moma.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.baidu.location.BDLocation;
import com.midian.login.bean.CityBean;
import com.midian.maplib.LocationUtil;
import com.midian.moma.R;
import com.midian.moma.ui.main.AdvertFragments;
import com.midian.moma.ui.main.HomeFragment1;
import com.midian.moma.ui.main.PersonalFragment;
import com.midian.moma.ui.main.ShoppingFragment;
import com.midian.moma.utils.AppUtil;
import com.midian.moma.utils.MenuDialog;

import midian.baselib.app.AppManager;
import midian.baselib.base.BaseFragmentActivity;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibMainFooterView;
import midian.baselib.widget.BaseLibMainFooterView.onTabChangeListener;

/**
 * 首页Activity
 *
 * @author chu
 */
public class MainActivity extends BaseFragmentActivity implements onTabChangeListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    public int[][] iconsArr = {{R.drawable.tab_1_n, R.drawable.tab_1_p}, {R.drawable.tab_2_n, R.drawable.tab_2_p},
            {R.drawable.tab_3_n, R.drawable.tab_3_p}, {R.drawable.tab_4_n, R.drawable.tab_4_p}};

    private HomeFragment1 homeFragment;
    public AdvertFragments advertFragment;// 广告位;
    private ShoppingFragment shoppingFragment;
    private PersonalFragment personalFragment;
    private BaseLibMainFooterView footer;
    private float exitTime;
    private long days = 30 * 24 * 60 * 60 * 1000l;//一天的时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        footer = findView(R.id.footer);
        footer.setOnTabChangeListener(this);
        footer.initTab(new String[]{"摩范", "广告位", "购物车", "我"}, iconsArr);

        AppUtil.getMAppContext(ac).startPush();
//        System.out.println("MainActivity---.startPush()++推送接收状态为：：：" + ac.isClosePush);
        initFragment();

        LocationUtil.getInstance(_activity).startOneLocation(new LocationUtil.OneLocationListener() {
            @Override
            public void complete(BDLocation location) {
                String lon = location.getLongitude() + "";
                String lat = location.getLatitude() + "";
                ac.loc_citys = location.getCity();//获取百度定位的当前所在城市
                ac.loc_city = location.getDistrict();//获取百度定位当前所在的市区
//                ac.loc_city = ac.loc_city.replace("市", "");
//                ac.saveCityName(ac.loc_city, "");
//                System.out.println("百度自动定位值：：ac.loc_citys::" + ac.loc_citys + ":::市区ac.loc_city:::" + ac.loc_city + ":::城市等级ac.level:" + ac.loc_city_level);
                AppUtil.getMomaApiClient(ac).getCity(ac.loc_city, MainActivity.this);
                ac.saveLocation(lon, lat);
                LocationUtil.getInstance(_activity).stopLocation();
            }
        });

        if (!TextUtils.isEmpty(ac.loc_city)) {
            AppUtil.getMomaApiClient(ac).getCity(ac.loc_city, this);
        }
    }

    private void initFragment() {
        homeFragment = new HomeFragment1();// 主页案例展示
        advertFragment = new AdvertFragments();// 广告位;
        shoppingFragment = new ShoppingFragment();// 购物车
        personalFragment = new PersonalFragment();// 个人中心
        switchFragment(homeFragment);

        if (!ac.homeIsFirst) {
            MenuDialog menuDialog = new MenuDialog(_activity, "0", R.style.menu_dialog);

            menuDialog.show();
            ac.setHomeIsFirst(true);
        }

    }


    @Override
    public int getFragmentContentId() {
        return R.id.fl_content;
    }

    public void onAdvertSearchKey(String key) {
        if (advertFragment != null) {
            advertFragment.onSearchKey(key);
        }
    }

    public void onCaseSearchKey(String key) {
        if (homeFragment != null) {
            homeFragment.onSearchKey(key);
        }
    }

    @Override
    public void onTabChange(int index) {
        switch (index) {
            case 0:
                switchFragment(homeFragment);
                break;
            case 1:
                if (!ac.advertIsFirst) {
                    MenuDialog advertDialog = new MenuDialog(_activity, "1", R.style.menu_dialog);
                    advertDialog.show();
                    ac.setAdvertIsFirst(true);
                }
                switchFragment(advertFragment);
                break;
            case 2:
                ac.isRequireLogin(_activity);//判断是否登陆
                switchFragment(shoppingFragment);
                break;
            case 3:
                switchFragment(personalFragment);
                break;
        }
    }

    @Override
    public void onApiSuccess(NetResult res, String tag) {
        super.onApiSuccess(res, tag);
        try {
            if ("getCity".equals(tag)) {
                if (res.isOK()) {
                    CityBean mCityBean = (CityBean) res;
                    if (mCityBean.getContent() != null) {
                        ac.saveLocCityName(mCityBean.getContent().getRegion_name(), mCityBean.getContent().getRegion_id(), mCityBean.getContent().getRegion_level());
                        if (TextUtils.isEmpty(ac.city_name)) {
                            ac.saveCityName(mCityBean.getContent().getRegion_name(), mCityBean.getContent().getRegion_id());
                            homeFragment.refreshCity();
                            advertFragment.refreshCity();
                        }
                    }
                } else {
                    //ac.handleErrorCode(_activity, res.error_code);
                    ac.setProperty("loc_city_id", "");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 调整返回的RESULTCODE
     */
    public final static int CHANNELRESULT = 10;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10087 || requestCode == 10088 && resultCode == RESULT_OK) {
            footer.setCurIndex(1);
        } else if (requestCode == 10000 && resultCode == RESULT_OK) {
            footer.setCurIndex(2);
        } else if (requestCode == 10001 && resultCode == CHANNELRESULT) {
            Bundle b = data.getExtras();
            if (b == null)
                homeFragment.setChangeView();
            else {
                homeFragment.setChangeView(b.getInt("index"));
            }

        }
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            UIHelper.t(getApplicationContext(), R.string.exit_text);
            exitTime = System.currentTimeMillis();
        } else {
            LocationUtil.getInstance(_activity).stopLocation();
            AppManager.getAppManager().appExit(this);
            finish();
        }
    }


}
