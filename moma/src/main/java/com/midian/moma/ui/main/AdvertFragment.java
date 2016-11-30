package com.midian.moma.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.midian.moma.R;
import com.midian.moma.customview.DimedView;
import com.midian.moma.model.AdvertListBean;
import com.midian.moma.model.KeywordsBean;
import com.midian.moma.model.KeywordsBean.KeywordsContent;
import com.midian.moma.ui.MainActivity;
import com.midian.moma.ui.advert.AdvertDetailActivity;
import com.midian.moma.ui.advert.AdvertSearchViewTpl;
import com.midian.moma.ui.advert.ChooseAdvertActivity;
import com.midian.moma.ui.advert.CooperationActivity;
import com.midian.moma.ui.advert.MapModelActivity;
import com.midian.moma.ui.advert.fragment.AllTypeFragment;
import com.midian.moma.ui.advert.fragment.PlaneTypeFragment;
import com.midian.moma.ui.advert.fragment.VideoTypeFragment;
import com.midian.moma.ui.home.ChooseCitysActivity;
import com.midian.moma.ui.home.SignInActivity;
import com.midian.moma.urlconstant.UrlConstant;
import com.midian.moma.utils.AppUtil;

import java.util.ArrayList;
import java.util.List;

import midian.baselib.api.ApiCallback;
import midian.baselib.app.AppContext;
import midian.baselib.base.BaseFragment;
import midian.baselib.base.BaseListAdapter;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.Func;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;
import midian.baselib.widget.ScrollViewWidthListener;
import midian.baselib.widget.ScrollViewWidthListener.onChildViewVisibilityChangedListener;
import midian.baselib.widget.pulltorefresh.PullToRefreshBase;
import midian.baselib.widget.pulltorefresh.PullToRefreshScrollView;

/**
 * 广告位fragment【旧页面，暂时不用】
 *
 * @author chu
 */
public class AdvertFragment extends BaseFragment implements OnClickListener, ApiCallback, TextView.OnEditorActionListener, View.OnFocusChangeListener, DimedView.OpenCloseListener {

    private BaseLibTopbarView topbar;
    private ImageView image1_iv, image2_iv;
    private View map_model, cooperation, tab1, tab2;
    private TextView f1, f2, f3, f4, f5, f6;
    private ScrollViewWidthListener mScrollViewWidthListener;
    private AllTypeFragment mAllTypeFragment;// 全部类型
    private PlaneTypeFragment mPlaneTypeFragment;// 平面类型
    private VideoTypeFragment mVideoTypeFragment;// 视频类型
    public Fragment mFragment;
    public PullToRefreshScrollView mPullToRefreshScrollView;
    private String keywords, type;
    private int page;
    private int tabIndex;
    private EditText input_et;
    private DimedView dime_view;
    private String ad_id_one;//推荐位置的广告id
    private String ad_id_tow;//推荐位置的广告id


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_advert, null);
        initView(v);
        return v;
    }


    private void initView(View v) {
        topbar = (BaseLibTopbarView) v.findViewById(R.id.topbar);
        topbar.setMode(BaseLibTopbarView.MODE_WITH_INPUT).setLeftImageButton(R.drawable.icon_title_logo, this);
        View view = LayoutInflater.from(_activity).inflate(R.layout.scrolllayout, null);
        topbar.getInput_et().setHint("请输入搜索内容");
        topbar.getRight_tv().setCompoundDrawablePadding(Func.Dp2Px(_activity, 6));
        topbar.getRight_tv().setCompoundDrawablesWithIntrinsicBounds(null, null,getResources().getDrawable(R.drawable.icon_location_arrow_down), null);
        input_et = topbar.getInput_et();
        input_et.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        input_et.setOnEditorActionListener(this);
        input_et.setOnFocusChangeListener(this);
        dime_view = (DimedView) v.findViewById(R.id.dime_view1);
        dime_view.setOpenCloseListener(this);
//        input_et.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dime_view.show();
//                dime_view.getListView().setVisibility(View.GONE);
//                search();
//            }
//        });

        image1_iv = (ImageView) view.findViewById(R.id.image1);
        image2_iv = (ImageView) view.findViewById(R.id.image2);
        map_model = view.findViewById(R.id.map_model);
        cooperation = view.findViewById(R.id.cooperation);
        tab1 = v.findViewById(R.id.tab1);
        tab2 = view.findViewById(R.id.tab2);
        f1 = (TextView) v.findViewById(R.id.f1);
        f2 = (TextView) v.findViewById(R.id.f2);
        f3 = (TextView) v.findViewById(R.id.f3);
        f4 = (TextView) view.findViewById(R.id.f4);
        f5 = (TextView) view.findViewById(R.id.f5);
        f6 = (TextView) view.findViewById(R.id.f6);
        mPullToRefreshScrollView = (PullToRefreshScrollView) v.findViewById(R.id.scroll);
        mPullToRefreshScrollView.setPullLoadEnabled(true);
        mPullToRefreshScrollView.scrollView.addView(view);


        mPullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            //下拉刷新
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {

                if (0 == tabIndex) {
                    mAllTypeFragment.onRefreshAll(true);
                } else if (1 == tabIndex) {
                    mPlaneTypeFragment.onRefreshPlane(true);
                } else if (2 == tabIndex) {
                    mVideoTypeFragment.onRefreshVideo(true);
                }

            }


            //上拉刷新
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
               /* mPullToRefreshScrollView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshScrollView.onPullUpRefreshComplete();
                    }
                }, 2000);*/

                if (0 == tabIndex) {
                    mAllTypeFragment.onLoadMore(true);
                } else if (1 == tabIndex) {
                    mPlaneTypeFragment.onLoadMorePlane(true);
                } else if (2 == tabIndex) {
                    mVideoTypeFragment.onLoadMoreVideo(true);
                }
            }
        });


        mScrollViewWidthListener = mPullToRefreshScrollView.scrollView;
//        mScrollViewWidthListener = (ScrollViewWidthListener) v.findViewById(R.id.scroll);
        mScrollViewWidthListener.setOnChildViewVisibilityChangedListener(new onChildViewVisibilityChangedListener() {

            @Override
            public void onChildViewVisibilityChanged(int index, View v, boolean becameVisible) {
                if (index == 0) {
                    if (becameVisible) {
                        tab1.setVisibility(View.GONE);
                    } else {
                        tab1.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        image1_iv.setOnClickListener(this);
        image2_iv.setOnClickListener(this);
        map_model.setOnClickListener(this);
        cooperation.setOnClickListener(this);

        f1.setOnClickListener(this);
        f2.setOnClickListener(this);
        f3.setOnClickListener(this);
        f4.setOnClickListener(this);
        f5.setOnClickListener(this);
        f6.setOnClickListener(this);
        f1.setEnabled(false);
        f2.setEnabled(true);
        f3.setEnabled(true);
        f4.setEnabled(false);
        f5.setEnabled(true);
        f6.setEnabled(true);
        mFragment = new Fragment();
        mAllTypeFragment = new AllTypeFragment();
        mPlaneTypeFragment = new PlaneTypeFragment();
        mVideoTypeFragment = new VideoTypeFragment();
        switchFragment(mFragment, mAllTypeFragment);


    }


    private void loadData() {
        keywords = null;
        page = 0;
        type = null;
        try {
            // TODO: 2016/3/31 广告位列表接口
//            AppUtil.getMomaApiClient(ac).advertisements(keywords, ac.lon, ac.lat, type, ac.city_id,ac.loc_city_level, page + "", AppContext.PAGE_SIZE, this);//获取广告列表接口
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //关闭下拉刷新
    public void closeRefresh() {
        mPullToRefreshScrollView.onPullDownRefreshComplete();
    }

    //关闭加载更多
    public void closeLoadMore() {
        mPullToRefreshScrollView.onPullUpRefreshComplete();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshCity();
        input_et.clearFocus();
    }

    public void refreshCity() {
        if (ac == null) return;
        if ("".equals(ac.city_name)) {
            topbar.setRightText("未定位", this);
        } else {
            topbar.setRightText(ac.city_name, this);
        }
        loadData();
    }


    public void switchFragment(Fragment from, Fragment to) {
        FragmentTransaction mTransaction = fm.beginTransaction();
        if (mFragment != to) {
            mFragment = to;
            if (to.isAdded()) {
                mTransaction.hide(from).show(to).commit();
            } else {
                mTransaction.hide(from).add(R.id.fl_content, to).commit();
            }
        }
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();

        switch (v.getId()) {
            //广告位推荐位置
            case R.id.image1:
                if (ad_id_one == null) {
                    return;
                }else {
                    bundle.putString("id", ad_id_one);
                    UIHelper.jump(_activity, AdvertDetailActivity.class, bundle);
                }
                break;
            case R.id.image2:
                if (ad_id_tow == null) {
                    return;
                }else {
                    bundle.putString("id", ad_id_tow);
                    UIHelper.jump(_activity, AdvertDetailActivity.class, bundle);
                }
                break;
            case R.id.map_model:// 地图模式
                String mapType = "1";//为1时跳转为地图模式
                bundle.putString("type", mapType);
                UIHelper.jump(_activity, MapModelActivity.class,bundle);
                break;
            case R.id.cooperation:// 合作流程
                bundle.putString("title", "合作流程");
                UIHelper.jump(_activity, CooperationActivity.class, bundle);
                break;
            case R.id.f1:
            case R.id.f4:
                switchFragment(mFragment, mAllTypeFragment);
                f1.setEnabled(false);
                f2.setEnabled(true);
                f3.setEnabled(true);
                f4.setEnabled(false);
                f5.setEnabled(true);
                f6.setEnabled(true);
                tabIndex = 0;
                break;
            case R.id.f2:
            case R.id.f5:
                switchFragment(mFragment, mPlaneTypeFragment);
                f1.setEnabled(true);
                f2.setEnabled(false);
                f3.setEnabled(true);
                f4.setEnabled(true);
                f5.setEnabled(false);
                f6.setEnabled(true);
                tabIndex = 1;
                break;
            case R.id.f3:
            case R.id.f6:
                switchFragment(mFragment, mVideoTypeFragment);
                f1.setEnabled(true);
                f2.setEnabled(true);
                f3.setEnabled(false);
                f4.setEnabled(true);
                f5.setEnabled(true);
                f6.setEnabled(false);
                tabIndex = 2;
                break;
            case R.id.right_tv:
                UIHelper.jump(_activity, ChooseCitysActivity.class);
                break;
            case R.id.left_ll:
                if (!ac.isRequireLogin(_activity)) {
                    return;
                }
//        ac.isRequireLogin(_activity);//判断是否登陆
                UIHelper.jump(_activity, SignInActivity.class);
                break;
        }
    }

    @Override
    public void onApiStart(String tag) {
        _activity.showLoadingDlg();
    }

    @Override
    public void onApiLoading(long count, long current, String tag) {

    }

//    List<Recommend_ad> recommendAds = new ArrayList<Recommend_ad>();

    @Override
    public void onApiSuccess(NetResult res, String tag) {
        _activity.hideLoadingDlg();
        if (res.isOK()) {
            // TODO: 2016/3/31 广告的两条推荐位置
          /*  if ("advertisements".equals(tag)) {
                AdvertListBean listBean = (AdvertListBean) res;
//                recommendAds = listBean.getContent().getRecommend_ad();
                int i = 1;
                for (Recommend_ad bean : listBean.getContent().getRecommend_ad()) {
                    if (i == 1) {
                        if (TextUtils.isEmpty(bean.getAd_id())) {
                            ac.setImage(image1_iv, R.drawable.default_button);
                        } else {
                            ad_id_one = bean.getAd_id();
                            if (TextUtils.isEmpty(bean.getAd_pic_name())) {
                                ac.setImage(image1_iv, R.drawable.default_button);
                            } else {
                                ac.setImage(image1_iv, UrlConstant.BASEFILEURL + bean.getAd_pic_name());
                            }
                        }
                        i++;
                    } else if (i == 2) {
                        if (TextUtils.isEmpty(bean.getAd_id())) {
                            ac.setImage(image2_iv, R.drawable.default_button);
                        } else {
                            ad_id_tow = bean.getAd_id();
                            if (TextUtils.isEmpty(bean.getAd_pic_name())) {
                                ac.setImage(image2_iv, R.drawable.default_button);
                            } else {
                                ac.setImage(image2_iv, UrlConstant.BASEFILEURL + bean.getAd_pic_name());
                            }
                        }
                    }
                }
            }*/

            if ("keywords".equals(tag)) {
                KeywordsBean keywordsBean = (KeywordsBean) res;
                initKeywords(keywordsBean.getContent());
            }

        } else {
            input_et.clearFocus();
            ac.handleErrorCode(_activity, res.error_code);
        }

    }

    @Override
    public void onApiFailure(Throwable t, int errorNo, String strMsg, String tag) {
        t.printStackTrace();
        _activity.hideLoadingDlg();
        UIHelper.t(_activity, "网络异常");
    }

    @Override
    public void onParseError(String tag) {
        _activity.hideLoadingDlg();
        UIHelper.t(_activity, "数据解析异常");
    }

    @Override
    public void open() {

    }

    @Override
    public void close() {
        input_et.clearFocus();
        //关闭软键盘
        InputMethodManager im = ((InputMethodManager) _activity.getSystemService(_activity.INPUT_METHOD_SERVICE));
        im.showSoftInput(input_et, 0);
        im.hideSoftInputFromWindow(input_et.getWindowToken(), 0);
    }


    private void search() {
        //请求热词的接口
        AppUtil.getMomaApiClient(ac).keywords(this);
    }

    public void initKeywords(ArrayList<KeywordsContent> contentList) {
        ArrayList<SearchItem> morelist = new ArrayList<SearchItem>();
        ArrayList<Item> mlist = new ArrayList<Item>();
        for (KeywordsContent item : contentList) {
            mlist.add(new Item(item.getKeywords(), 0));
        }
        /*for (KeywordsContent item : contentList) {
            mlist.add(new Item(item.getKeywords(), 0));
        }
        for (KeywordsContent item : contentList) {
            mlist.add(new Item(item.getKeywords(), 0));
        }*/
//        mlist.remove(0);
        int n = 0;
        for (int i = 0; i < mlist.size() / 3; i++) {
            morelist.add(new SearchItem(mlist.subList(i * 3, i * 3 + 3)));
            n += 3;
        }
        if (mlist.size() - n > 0)
            morelist.add(new SearchItem(mlist.subList(n, mlist.size())));
        initItemShow(morelist);
    }

    private void initItemShow(ArrayList<SearchItem> contentList) {
        final BaseListAdapter<SearchItem> adapter = new BaseListAdapter<SearchItem>(dime_view.getListView(), _activity, contentList,
                AdvertSearchViewTpl.class, null);
        dime_view.getListView().setVisibility(View.VISIBLE);
        dime_view.getListView().setAdapter(adapter);
    }


    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            String keyword = input_et.getText().toString().trim();
            //软键盘
            InputMethodManager im = ((InputMethodManager) _activity.getSystemService(_activity.INPUT_METHOD_SERVICE));
            im.showSoftInput(input_et, 0);
            if (!TextUtils.isEmpty(keyword)) {
                onSearchKey(keyword);
            } else {
                UIHelper.t(_activity, "请输入关键字");
            }
            return true;
        }
        return false;

    }


    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (hasFocus) {
            dime_view.show();
            dime_view.getListView().setVisibility(View.GONE);
            search();
        } else {
            input_et.clearFocus();
        }
    }


    public void onSearchKey(String keywords) {
        Bundle mBundle = new Bundle();
        mBundle.putString("keywords", keywords);
        UIHelper.jump(_activity, ChooseAdvertActivity.class, mBundle);
        input_et.clearFocus();
        dime_view.close();
    }

    public class SearchItem extends NetResult {
        List<Item> list;
        public SearchItem(List<Item> list) {
            super();
            this.list = list;
        }
        public List<Item> getList() {
            return list;
        }
        public void setList(List<Item> list) {
            this.list = list;
        }
    }
    public class Item extends NetResult {
        private String name;
        int type;
        public Item(String name, int type) {
            super();
            this.setName(name);
            this.type = type;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }


}
