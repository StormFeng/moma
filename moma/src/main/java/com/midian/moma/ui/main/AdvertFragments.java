package com.midian.moma.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.midian.login.bean.CityBean;
import com.midian.login.bean.CityBean.AdvertisementCircle;
import com.midian.moma.R;
import com.midian.moma.customview.DimedView;
import com.midian.moma.datasource.AdvertFragmentsDataSource;
import com.midian.moma.itemview.AdvertFragmentTpl;
import com.midian.moma.itemview.AdvertSelectItemTpl;
import com.midian.moma.model.AdvertListBean.AdvertListContent;
import com.midian.moma.model.KeywordsBean;
import com.midian.moma.ui.advert.AdvertSearchViewTpl;
import com.midian.moma.ui.advert.ChooseAdvertActivity;
import com.midian.moma.ui.advert.MapModelActivity;
import com.midian.moma.ui.home.ChooseCitysActivity;
import com.midian.moma.ui.home.SearchActivity;
import com.midian.moma.ui.home.SignInActivity;
import com.midian.moma.utils.AppUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import midian.baselib.api.ApiCallback;
import midian.baselib.base.BaseListAdapter;
import midian.baselib.base.BaseListFragment;
import midian.baselib.bean.NetResult;
import midian.baselib.shizhefei.view.listviewhelper.IDataAdapter;
import midian.baselib.shizhefei.view.listviewhelper.IDataSource;
import midian.baselib.utils.Func;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;
import midian.baselib.widget.SelectFilterView;

/**
 * 广告位fragment
 * Created by chu on 2016/3/31.
 */
public class AdvertFragments extends BaseListFragment implements SelectFilterView.onTabChangeListener, DimedView.OpenCloseListener, View.OnClickListener, TextView.OnEditorActionListener, View.OnFocusChangeListener, ApiCallback {
    private BaseLibTopbarView topbar;
    private SelectFilterView select;//选择过滤视图
    private View map;
    private DimedView select_view;
    private DimedView search_view;
    private EditText input_et;
    private int index;
    private AdvertFragmentsDataSource advertDataSource;
    private Context context;
    private String current_time;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        advertDataSource = new AdvertFragmentsDataSource(getActivity());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        //自定义列表无数据时的提示文字和事件 DeFaultLoadViewFactory.java
        listViewHelper.loadViewFactory.config(false, "暂无该类型的广告位");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragments_advert;
    }

    /**
     * 初始化控件
     */
    private void initView(View v) {
        topbar = (BaseLibTopbarView) v.findViewById(R.id.topbar);
        topbar.setMode(BaseLibTopbarView.MODE_WITH_INPUT).setLeftImageButton(R.drawable.icon_title_logo, this);
//        View view = LayoutInflater.from(_activity).inflate(R.layout.scrolllayout, null);
        topbar.getInput_et().setHint("请输入搜索内容");
        topbar.getRight_tv().setCompoundDrawablePadding(Func.Dp2Px(_activity, 6));
        topbar.getRight_tv().setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.icon_location_arrow_down), null);
        input_et = topbar.getInput_et();
        input_et.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        input_et.setOnEditorActionListener(this);
        input_et.setOnFocusChangeListener(this);

        select = (SelectFilterView) v.findViewById(R.id.select);
        select_view = (DimedView) v.findViewById(R.id.select_view);

        search_view = (DimedView) v.findViewById(R.id.search_view);//editText的布局

        select.setOnTabChangeListener(this);
        select_view.setOpenCloseListener(this);
//        String[] tabs={"全广州","载体类型","租/售","更多"};
//        select.initTab(tabs);//设置条件过滤标题
        map = v.findViewById(R.id.map);//更多
        map.setOnClickListener(this);
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
            ac.city_name = "全国";
        } else {
//            System.out.println("onResume::" + ac.city_name + ac.city_id);
            topbar.setRightText(ac.city_name, this);
            advertDataSource.setRegionId(ac.city_id);
            listViewHelper.refresh();
        }
    }

    @Override
    protected IDataSource<ArrayList<AdvertListContent>> getDataSource() {
        return advertDataSource;
    }

    @Override
    protected Class getTemplateClass() {
        return AdvertFragmentTpl.class;
    }

    @Override
    public void onEndRefresh(IDataAdapter adapter, Object result) {
        if ( result == null) {
            //自定义列表无数据时的提示文字和事件 DeFaultLoadViewFactory.java
            listViewHelper.loadViewFactory.config(false, "暂无该类型的广告位");
        }
    }

    @Override
    public void onEndLoadMore(IDataAdapter adapter, Object result) {

    }

    /**
     * 普通监听事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.map:
                UIHelper.jump(_activity, MapModelActivity.class);
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

    /**
     * 以下方法用于监听dimeview的开关
     */
    @Override
    public void open() {
    }

    @Override
    public void close() {
        select.setchangeState(index, false);
        input_et.clearFocus();
        //关闭软键盘
        InputMethodManager im = ((InputMethodManager) _activity.getSystemService(_activity.INPUT_METHOD_SERVICE));
        im.showSoftInput(input_et, 0);
        im.hideSoftInputFromWindow(input_et.getWindowToken(), 0);
    }

    /**
     * 以下方法用于监听tab过滤条件改变
     */
    @Override
    public void onTabChange(int index, boolean isSelect) {
        this.index = index;
//        System.out.println("点击后的index:::" + index);
        switch (index) {
            case 0:
                if (isSelect) {
                    select_view.show();
                    select_view.getListView().setVisibility(View.GONE);
                    initType();//初始化类型筛选条件
                } else {
                    select_view.close();
                }
                break;
            case 1:
                if (isSelect) {
                    if ("全国".equals(ac.city_name)) {
                        UIHelper.t(_activity, "请选择具体城市");
                        select.changeState(index, false);
                        return;
                    }
                    select_view.show();
                    select_view.getListView().setVisibility(View.GONE);
                    AppUtil.getMomaApiClient(ac).getCity(ac.city_name, this);
                } else {
                    select_view.close();
                }
                break;
            case 2:
                if (isSelect) {
                    select_view.show();
                    select_view.getListView().setVisibility(View.GONE);
                    initTime();
                } else {
                    select_view.close();
                }
                break;
        }
    }


    /**
     * 加载类型条目数据
     */
    private void initType() {
        ArrayList<ChooseItem> morelist = new ArrayList<ChooseItem>();
        morelist.add(new ChooseItem(null, "全部", 0));
        String[] typeArr = getResources().getStringArray(R.array.type);
        for (int i = 0; i < typeArr.length; i++) {
            ChooseItem item = new ChooseItem(i + "", typeArr[i], 0);
            morelist.add(item);
        }
        initItemShow(morelist);

    }

    /**
     * 加载广告圈条目数据
     */
    private void initCommunity(ArrayList<AdvertisementCircle> advertisement_circle) {
        ArrayList<ChooseItem> morelist = new ArrayList<ChooseItem>();
        morelist.add(new ChooseItem(null, "全部", 1));
        for (AdvertisementCircle circle : advertisement_circle) {
            morelist.add(new ChooseItem(circle.getCommunity_id(), circle.getCommunity_name(), 1));
        }
        initItemShow(morelist);
    }

    /**
     * 加载时间条目数据
     */
    private void initTime() {
        ArrayList<ChooseItem> morelist = new ArrayList<ChooseItem>();
        morelist.add(new ChooseItem(null, "全部", 2));
        String[] timeArr = getResources().getStringArray(R.array.time);
        for (int i = 0; i < timeArr.length; i++) {
            ChooseItem item = new ChooseItem(i + "", timeArr[i], 2);
            morelist.add(item);
        }
        initItemShow(morelist);
    }
    String typeName = "类型";
    String adCircle = "广告圈";
    String timeName = "时间";
    /**
     * 渲染筛选条目界面传递参数至DataSource
     */
    private void initItemShow(ArrayList<ChooseItem> morelist) {
        final BaseListAdapter<ChooseItem> chooseAdapter = new BaseListAdapter<ChooseItem>(select_view.getListView(), _activity, morelist, AdvertSelectItemTpl.class, null);
        select_view.getListView().setVisibility(View.VISIBLE);
        select_view.getListView().setAdapter(chooseAdapter);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        current_time = sdf.format(new Date());
        select_view.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("TAG:::::::", "" + position);
                ChooseItem item = chooseAdapter.getData().get(position);
                String ids = item.getId();

                if (item.type == 0) {//item.type == 0时为类型传参
                    if (position == 0) {//position=0时，选全部，参数传Null
                        advertDataSource.setType(null);//选择全部时把类型参数设空
                        typeName = "全部";
                    } else {
                        advertDataSource.setType(position + "");
                        if (position == 1) {
                            typeName = "平面类型";
                        } else {
                            typeName = "视频类型";
                        }
                    }
                    //自定义列表无数据时的提示文字和事件 DeFaultLoadViewFactory.java
                    listViewHelper.loadViewFactory.config(false, "暂无该类型的广告位");
                } else if (item.type == 1) {//item.type=1时为广告圈传参
                    if (position == 0) {
                        advertDataSource.setCommunity_id(null);//选择全部时把类型参数设空
                        adCircle = "全部";
                    } else {
                        advertDataSource.setCommunity_id(ids);
                        adCircle = item.getName();
                    }
                    //自定义列表无数据时的提示文字和事件 DeFaultLoadViewFactory.java
                    listViewHelper.loadViewFactory.config(false, "该商圈暂无广告位,请选择其他商圈");
                } else if (item.type == 2) {//item.type=2时为时间条件传参
                    if (position == 0) {
                        advertDataSource.setDate_time(null);//选择全部时把类型参数设空
                        timeName = "全部";
                    } else if (position == 1) {//position=1 ：一个星期
                        long cur_time = (getStringToDate(current_time) + 7 * 24 * 60 * 60 * 1000l);
                        Log.i("tag:::一个星期时间戳：：", "我是当前时间戳：：：" + getDateToString(cur_time));
                        timeName = "一个星期";
                        advertDataSource.setDate_time(getDateToString(cur_time));
                    } else if (position == 2) {//position=2 一个月
                        long cur_time = (getStringToDate(current_time) + 30 * 24 * 60 * 60 * 1000l);
                        timeName = "一个月";
                        advertDataSource.setDate_time(getDateToString(cur_time));
                    } else if (position == 3) {//position=3 三个月
                        long cur_time = (getStringToDate(current_time) + 90 * 24 * 60 * 60 * 1000l);
                        timeName = "三个月";
                        advertDataSource.setDate_time(getDateToString(cur_time));
                    } else if (position == 4) {//position=4：半年
                        long cur_time = (getStringToDate(current_time) + 180 * 24 * 60 * 60 * 1000l);
                        timeName = "半年";
                        advertDataSource.setDate_time(getDateToString(cur_time));
                    } else if (position == 5) {//position=5：一年
                        long cur_time = (getStringToDate(current_time) + 365 * 24 * 60 * 60 * 1000l);
                        timeName = "一年";
                        advertDataSource.setDate_time(getDateToString(cur_time));
                    }
                    listViewHelper.loadViewFactory.config(false, "暂无该类型的广告位");
                }
                String[] tabs={typeName,adCircle,timeName};
                select.init(tabs);
                listViewHelper.refresh();
                select_view.close();
            }
        });
    }

    /**
     * 监听editText事件
     */
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
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

    //响应热词搜索条目事件跳转
    public void onSearchKey(String keywords) {
        Bundle mBundle = new Bundle();
        mBundle.putString("keywords", keywords);
        UIHelper.jump(_activity, ChooseAdvertActivity.class, mBundle);
        input_et.clearFocus();
        search_view.close();
    }

    /**
     * 监听editText焦点改变事件
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
//            search();
//            search_view.show();
//            search_view.getListView().setVisibility(View.GONE);
            UIHelper.jump(_activity, SearchActivity.class);
        } else {
            input_et.clearFocus();
        }
    }


    private void search() {
        //请求热词的接口
        AppUtil.getMomaApiClient(ac).keywords(this);
    }

    @Override
    public void onApiStart(String tag) {
    }

    @Override
    public void onApiLoading(long count, long current, String tag) {
    }

    @Override
    public void onApiSuccess(NetResult res, String tag) {
        _activity.hideLoadingDlg();
        if (res.isOK()) {
            //请求广告圈列表时回调
            if ("getCity".equals(tag)) {
                CityBean cityBean = (CityBean) res;
                initCommunity(cityBean.getContent().getAdvertisement_circle());
            }
            //请求热词搜索回词
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
    }

    @Override
    public void onParseError(String tag) {
    }

    //加载热词数据到布局
    public void initKeywords(ArrayList<KeywordsBean.KeywordsContent> contentList) {
        ArrayList<SearchItem> morelist = new ArrayList<SearchItem>();
        ArrayList<Item> mlist = new ArrayList<Item>();
        for (KeywordsBean.KeywordsContent item : contentList) {
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
        initShowSearch(morelist);
    }

    //渲染热词布局
    private void initShowSearch(ArrayList<SearchItem> contentList) {
        final BaseListAdapter<SearchItem> searchAdapter = new BaseListAdapter<SearchItem>(search_view.getListView(), _activity, contentList,
                AdvertSearchViewTpl.class, null);
        search_view.getListView().setVisibility(View.VISIBLE);
        search_view.getListView().setAdapter(searchAdapter);
//        System.out.println("热词请求回来的结果3：：：" + contentList.size());
    }

    /**
     * 筛选条件entity
     */
    public class ChooseItem extends NetResult {
        private String id;
        private String name;
        int type;

        public ChooseItem(String id, String name, int type) {
            super();
            this.id = id;
            this.name = name;
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    //热词entity1
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

    //热词entity2
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


    /**
     * 时间戳转换成字符窜
     *
     * @param time
     * @return
     */
    public static String getDateToString(long time) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date(time);
        return sf.format(d);
    }

    /**
     * 将字符串转为时间戳
     *
     * @param time
     * @return
     */
    public static long getStringToDate(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }
}
