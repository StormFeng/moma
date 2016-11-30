package com.midian.moma.ui.main;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.midian.moma.R;
import com.midian.moma.customview.DimedView;
import com.midian.moma.datasource.HomeDataSource;
import com.midian.moma.itemview.HomeBannerTpl;
import com.midian.moma.itemview.HomeCaseTpl;
import com.midian.moma.itemview.SearchViewTpl;
import com.midian.moma.model.KeywordsBean;
import com.midian.moma.model.KeywordsBean.KeywordsContent;
import com.midian.moma.ui.home.ChooseCitysActivity;
import com.midian.moma.ui.home.SearchAdvertResultActivity;
import com.midian.moma.utils.AppUtil;

import java.util.ArrayList;
import java.util.List;

import midian.baselib.api.ApiCallback;
import midian.baselib.base.BaseListAdapter;
import midian.baselib.base.BaseMultiTypeListFragment;
import midian.baselib.bean.NetResult;
import midian.baselib.shizhefei.view.listviewhelper.IDataSource;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;

/**
 * 首页(暂时不用页面)
 *
 * @author chu
 */
public class HomeFragment extends BaseMultiTypeListFragment<IndexMultiItem> implements OnClickListener, TextView.OnEditorActionListener, DimedView.OpenCloseListener, View.OnFocusChangeListener, ApiCallback {

    private BaseLibTopbarView topbar;
    private DimedView dime_view;
    private EditText input_et;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View v) {
        topbar = (BaseLibTopbarView) v.findViewById(R.id.topbar);
        topbar.setMode(BaseLibTopbarView.MODE_WITH_INPUT).setLeftImageButton(R.drawable.icon_title_logo, null);
        topbar.getInput_et().setHint("请输入搜索内容");
        topbar.getRight_tv().setCompoundDrawablesWithIntrinsicBounds(null, null,
                getResources().getDrawable(R.drawable.icon_location_arrow_down), null);
        input_et = topbar.getInput_et();
        input_et.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        input_et.setOnEditorActionListener(this);
        input_et.setOnFocusChangeListener(this);
        dime_view = (DimedView) v.findViewById(R.id.dime_view);
        dime_view.setOpenCloseListener(this);


    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected IDataSource<ArrayList<IndexMultiItem>> getDataSource() {
        return new HomeDataSource(_activity);
    }

    @Override
    protected ArrayList<Class> getTemplateClasses() {
        ArrayList<Class> tpls = new ArrayList<Class>();
        tpls.add(HomeBannerTpl.class);
        tpls.add(HomeCaseTpl.class);
        // tpls.add(HomeLocErrorTpl.class);// 显示定位失败界面
        return tpls;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right_tv:
                UIHelper.jump(_activity, ChooseCitysActivity.class);
                break;
        }
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

    private void search() {
        //请求热词的接口
        AppUtil.getMomaApiClient(ac).keywords(this);
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

    @Override
    public void onApiStart(String tag) {
//        _activity.showLoadingDlg();
    }

    @Override
    public void onApiLoading(long count, long current, String tag) {

    }

    @Override
    public void onApiSuccess(NetResult res, String tag) {
//        _activity.hideLoadingDlg();
        if (res.isOK()) {
            KeywordsBean keywordsBean = (KeywordsBean) res;
            initKeywords(keywordsBean.getContent());
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

    public void initKeywords(ArrayList<KeywordsContent> contentList) {
        ArrayList<SearchItem> morelist = new ArrayList<SearchItem>();
        ArrayList<Item> mlist = new ArrayList<Item>();
//        morelist.add(new Item("全部", 0));
        for (KeywordsContent item : contentList) {
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

    public void onSearchKey(String keywords) {
        Bundle mBundle = new Bundle();
        mBundle.putString("keywords", keywords);
        //首页案例搜索列表页
        UIHelper.jump(_activity, SearchAdvertResultActivity.class, mBundle);
//                listViewHelper.refresh();
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
