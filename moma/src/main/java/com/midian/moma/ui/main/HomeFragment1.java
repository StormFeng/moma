package com.midian.moma.ui.main;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.midian.maplib.LocationUtil;
import com.midian.moma.R;
import com.midian.moma.customview.DimedView;
import com.midian.moma.itemview.SearchViewTpl;
import com.midian.moma.model.CaseTypesBean;
import com.midian.moma.model.CaseTypesBean.CaseTypesContent;
import com.midian.moma.model.HomeBean;
import com.midian.moma.model.KeywordsBean;
import com.midian.moma.tabs.activity.ChannelActivity;
import com.midian.moma.tabs.bean.ChannelItem;
import com.midian.moma.tabs.bean.ChannelManage;
import com.midian.moma.ui.advert.AdvertDetailActivity;
import com.midian.moma.ui.home.ChooseCitysActivity;
import com.midian.moma.ui.home.SearchAdvertResultActivity;
import com.midian.moma.ui.home.HomeCaseFragment;
import com.midian.moma.ui.home.SearchCaseActivity;
import com.midian.moma.ui.home.SignInActivity;
import com.midian.moma.utils.AppUtil;

import java.util.ArrayList;
import java.util.List;

import midian.baselib.adapter.PagerTabAdapter;
import midian.baselib.api.ApiCallback;
import midian.baselib.base.BaseFragment;
import midian.baselib.base.BaseListAdapter;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.Func;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BannerView;
import midian.baselib.widget.BaseLibTopbarView;
import com.midian.moma.utils.GuideViewUtil;
import com.midian.moma.utils.MenuDialog;

import midian.baselib.widget.PagerSlidingTabStripForOther;

/**
 * 首页
 *
 * @author chu
 */
public class HomeFragment1 extends BaseFragment implements BannerView.OnItemClickListener, ApiCallback, OnClickListener, TextView.OnEditorActionListener, View.OnFocusChangeListener, DimedView.OpenCloseListener {
    private BaseLibTopbarView topbar;
    private DimedView dime_view;
    private EditText input_et;
    private ViewPager pager;
    PagerSlidingTabStripForOther mPagerSlidingTabStrip;
    private ArrayList<HomeBean.Banner> banners;
    private PagerTabAdapter pagerTabAdapter;
    //    private BannerView bannerView;
    private ArrayList<Fragment> fragments;
    private ArrayList<String> titles;
    private String type_id;
    private TextView edit;
    PagerTabAdapter pagerTabAdapterF;
    private GuideViewUtil mGuideViewUtil;
    /**
     * 用户选择的分类列表
     */
    private ArrayList<ChannelItem> userChannelList = new ArrayList<ChannelItem>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home1, null);
        if (container != null) {
            initView(v);
//            mGuideViewUtil=new GuideViewUtil(_activity, R.drawable.icon_guide.sign);
        }
        return v;
    }

    private void initView(View v) {
        topbar = (BaseLibTopbarView) v.findViewById(R.id.topbar_home);
        topbar.setMode(BaseLibTopbarView.MODE_WITH_INPUT).setLeftImageButton(R.drawable.icon_title_logo, this);
        topbar.getInput_et().setHint("请输入搜索内容");
        topbar.getRight_tv().setCompoundDrawablePadding(Func.Dp2Px(_activity, 6));
        topbar.getRight_tv().setCompoundDrawablesWithIntrinsicBounds(null, null,
                getResources().getDrawable(R.drawable.icon_location_arrow_down), null);
        input_et = topbar.getInput_et();
        input_et.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        input_et.setOnEditorActionListener(this);
        input_et.setOnFocusChangeListener(this);
        dime_view = (DimedView) v.findViewById(R.id.dimeView);
        dime_view.setOpenCloseListener(this);

        mPagerSlidingTabStrip = (PagerSlidingTabStripForOther) v.findViewById(R.id.tabs1);

        pager = (ViewPager) v.findViewById(R.id.pager1);
        edit = (TextView) v.findViewById(R.id.edit);
        edit.setOnClickListener(this);
        fragments = new ArrayList<Fragment>();
        titles = new ArrayList<String>();
        pagerTabAdapterF = new PagerTabAdapter(fm, titles, fragments);
        pager.setAdapter(pagerTabAdapterF);
        loadData();
    }

//    private void getBannerData() {
//        AppUtil.getMomaApiClient(ac).momaIndex(null, "1", AppContext.PAGE_SIZE, this);
//    }

    private void loadData() {
        AppUtil.getMomaApiClient(ac).getCaseTypes(this);
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
            topbar.setRightText("全国", this);
        } else {
            topbar.setRightText(ac.city_name, this);
        }
    }


    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (hasFocus) {
            // TODO: 2016/3/29  点击搜索框，改变焦点后跳转到搜索页面
//            dime_view.show();
//            dime_view.getListView().setVisibility(View.GONE);
//            search();//输入樞获得焦点后请求接口
            UIHelper.jump(_activity, SearchCaseActivity.class);//案例搜索页
        } else {
            input_et.clearFocus();
        }
    }

    //请求热词的接口
    private void search() {
        AppUtil.getMomaApiClient(ac).keywords(this);
    }


    @Override
    public void onItemClick(View v, int position) {
        Bundle mBundle = new Bundle();
        /*mBundle.putString("url", banners.get(position).getBanner_id());
        UIHelper.jump(_activity, BannerWebViewActivity.class, mBundle);*/

        if ("".equals(banners.get(position).getAd_id())) {
            UIHelper.t(_activity, "广告id为空");
        } else {
            mBundle.putString("id", banners.get(position).getAd_id());
            UIHelper.jump(_activity, AdvertDetailActivity.class, mBundle);
        }
    }

    @Override
    public void onApiStart(String tag) {

    }

    @Override
    public void onApiLoading(long count, long current, String tag) {

    }

    @Override
    public void onApiSuccess(NetResult res, String tag) {
        if (res.isOK()) {
            if ("getCaseTypes".equals(tag)) {
                CaseTypesBean bean = (CaseTypesBean) res;
                render(bean);
            }
//            if ("momaIndex".equals(tag)) {
//                HomeBean homeBean = (HomeBean) res;
//                banners = homeBean.getContent().getBanner();
//                if (banners != null) {
//                    ArrayList<String> picUrls = new ArrayList<String>();
//                    for (int i = 0; i < banners.size(); i++) {
//                        picUrls.add(ServerConstant.BASEFILEURL + banners.get(i).getPic_name());
//                    }
//                    bannerView.config(750, 400, picUrls);
//                }
//            }
            if ("keywords".equals(tag)) {
                try {
                    KeywordsBean keywordsBean = (KeywordsBean) res;
                    initKeywords(keywordsBean.getContent());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            input_et.clearFocus();
            ac.handleErrorCode(_activity, res.error_code);
        }

    }

    private void render(CaseTypesBean bean) {
        ArrayList<CaseTypesContent> list = bean.getContent();
        List<ChannelItem> channelList = new ArrayList<ChannelItem>();
        for (int i = 0; i < list.size(); i++) {
            CaseTypesContent item = list.get(i);
            channelList.add(new ChannelItem(item.getType_id(), item.getName(), i + 2, 0));
        }

      /*  if (!ac.getBoolean("isFirst")) {
            ChannelManage.getManage(AppUtil.getMAppContext(ac).getSQLHelper()).updateinit(channelList);
            ac.setBoolean("isFirst", true);
        }*/

        if (!ac.isFirst) {
            ChannelManage.getManage(AppUtil.getMAppContext(ac).getSQLHelper()).updateinit(channelList);
            ac.isFirst(true);
        }
        ChannelManage.getManage(AppUtil.getMAppContext(ac).getSQLHelper()).update(channelList);
        setChangeView();
//            type_id = item.getType_id();
//            if (i == 0) {
//                AppUtil.getMomaApiClient(ac).momaIndex(type_id, 1 + "", MAppContext.PAGE_SIZE, this);
//            }
//            HomeCaseFragment caseFragment = new HomeCaseFragment();
//            caseFragment.setArguments(new Bundle());
//            caseFragment.getArguments().putString("type_id", type_id);
//            views.add(caseFragment);
//            titles.add(item.getName());
//        }
//        pagerTabAdapter = new PagerTabAdapter(fm, titles, views);
//        pager.setAdapter(pagerTabAdapter);
//        mPagerSlidingTabStrip.setViewPager(pager);
    }


    /**
     * 获取Column栏目 数据
     */
    private void initColumnData() {
        userChannelList = ((ArrayList<ChannelItem>) ChannelManage.getManage(AppUtil.getMAppContext(ac).getSQLHelper()).getUserChannel());
    }

    public void initFragment() {
        titles.clear();
        fragments.clear();
        for (int i = 0; i < userChannelList.size(); i++) {
            ChannelItem item = userChannelList.get(i);
            /// TODO: 2016/3/15 渲染首页fragment
            HomeCaseFragment caseFragment = new HomeCaseFragment();
            caseFragment.setArguments(new Bundle());
            caseFragment.getArguments().putString("type_id", item.getId());
            fragments.add(caseFragment);
            titles.add(item.getName());
        }
        try {
//            pagerTabAdapterF.notifyDataSetChanged();
//            pager.setOffscreenPageLimit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        pagerTabAdapterF.notifyDataSetChanged();
//        pager.setAdapter(new PagerTabAdapter(fm, titles, fragments));
        mPagerSlidingTabStrip.setViewPager(pager);


    }


    public void setChangeView() {
        initColumnData();
        initFragment();
    }

    public void setChangeView(int index) {
        initColumnData();
        initFragment();
        if (userChannelList.size() > 0)
            pager.setCurrentItem(Math.min(index, userChannelList.size() - 1));
    }

    @Override
    public void onApiFailure(Throwable t, int errorNo, String strMsg, String tag) {

    }

    @Override
    public void onParseError(String tag) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit:
                UIHelper.jumpForResult(_activity, ChannelActivity.class, 10001);
                break;
            case R.id.right_tv:
                UIHelper.jump(_activity, ChooseCitysActivity.class);
                break;
            case R.id.left_ll:
                if (!ac.isRequireLogin(_activity)) {
                    return;
                }
                UIHelper.jump(_activity, SignInActivity.class);
                break;
        }
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

    public void onSearchKey(String keywords) {
        Bundle mBundle = new Bundle();
        mBundle.putString("keywords", keywords);
        //首页案例搜索列表页
        UIHelper.jump(_activity, SearchAdvertResultActivity.class, mBundle);
//                listViewHelper.refresh();
        input_et.clearFocus();
        dime_view.close();
    }

    public void initKeywords(ArrayList<KeywordsBean.KeywordsContent> contentList) {
        try {
            ArrayList<SearchItem> morelist = new ArrayList<SearchItem>();
            ArrayList<Item> mlist = new ArrayList<Item>();
//        morelist.add(new Item("全部", 0));
            for (KeywordsBean.KeywordsContent item : contentList) {
                mlist.add(new Item(item.getKeywords(), 0));
            }
       /* for (KeywordsContent item : contentList) {
            mlist.add(new Item(item.getKeywords(), 0));
        }
        for (KeywordsContent item : contentList) {
            mlist.add(new Item(item.getKeywords(), 0));
        }*/
//        mlist.addAll(mlist);
//        mlist.addAll(mlist);
//        mlist.remove(0);
            int n = 0;
            for (int i = 0; i < mlist.size() / 3; i++) {
                morelist.add(new SearchItem(mlist.subList(i * 3, i * 3 + 3)));
                n += 3;
            }
            if (mlist.size() - n > 0)
                morelist.add(new SearchItem(mlist.subList(n, mlist.size())));
            initItemShow(morelist);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initItemShow(ArrayList<SearchItem> contentList) {
        final BaseListAdapter<SearchItem> adapter = new BaseListAdapter<SearchItem>(dime_view.getListView(), _activity, contentList,
                SearchViewTpl.class, null);
//        adapter.getCount();
        dime_view.getListView().setVisibility(View.VISIBLE);
        dime_view.getListView().setAdapter(adapter);

//        dime_view.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//                Item item = adapter.getData().get(arg2);
//                System.out.println("点选的条目:::---" + item.getName());
//
//                Bundle mBundle = new Bundle();
//                mBundle.putString("keywords", item.getName());
////                UIHelper.jump(_activity, ChooseAdvertActivity.class,mBundle );
//                listViewHelper.refresh();
//                dime_view.close();
//            }
//        });
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        //按返回退出应用时停止定位
        LocationUtil.getInstance(_activity).stopLocation();
    }
}