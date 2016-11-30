package com.midian.moma.ui.home;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.midian.moma.R;
import com.midian.moma.model.CaseTypesBean;
import com.midian.moma.utils.AppUtil;

import java.util.ArrayList;
import java.util.List;

import midian.baselib.base.BaseActivity;
import midian.baselib.base.BaseListAdapter;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.ScreenUtils;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;
import midian.baselib.widget.ListViewForScrollView;

/**
 * 编辑首页tabs标签
 * Created by chu on 2016/1/27.
 */
public class EditTabsActivity extends BaseActivity {
    private BaseLibTopbarView topbar;
    private TextView text_hint, edit_save;
    private ListView curlistView;
    private ListView unselectListView;


    // 保存编辑模式
    private View.OnClickListener saveListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // todo 本地化beans
            showNormal();
        }
    };
    // 保存编辑模式
    private View.OnClickListener editListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // todo 本地化beans
            showEdit();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tabs);
        topbar = findView(R.id.topbar);
        text_hint = findView(R.id.text_hint);
        edit_save = findView(R.id.edit_save_bt);
        curlistView = (ListView)findViewById(R.id.curlist);
        unselectListView= (ListView)findViewById(R.id.unselectlist);
        curlistView.setDivider(null);
        curlistView.setDividerHeight(ScreenUtils.dpToPxInt(_activity,10));
        unselectListView.setDivider(null);
        unselectListView.setDividerHeight(ScreenUtils.dpToPxInt(_activity,10));
        showNormal();
        loadData();
    }

    private void loadData() {
        try {
            AppUtil.getMomaApiClient(ac).getCaseTypes(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    protected void showNormal() {
        topbar.getTitle_tv().setVisibility(View.GONE);
        topbar.setLeftImageButton(R.drawable.icon_back, null).setLeftText("返回", UIHelper.finish(_activity));
        text_hint.setText("点击进入对应的案例频道");
        edit_save.setText("编辑");
        edit_save.setOnClickListener(editListener);
    }

    private void showEdit() {
        topbar.getTitle_tv().setVisibility(View.GONE);
        topbar.setLeftImageButton(R.drawable.icon_back, null).setLeftText("返回", UIHelper.finish(_activity));
        text_hint.setText("点击移除对应的案例频道");
        edit_save.setText("保存");
        edit_save.setOnClickListener(saveListener);
    }


    @Override
    public void onApiSuccess(NetResult res, String tag) {
        super.onApiSuccess(res, tag);
        hideLoadingDlg();
        if (res.isOK()) {
            if ("getCaseTypes".equals(tag)) {
                CaseTypesBean bean = (CaseTypesBean) res;
                System.out.println("类型请求成功:::"+bean.getContent().size());
                initKeywords(bean.getContent());
            }
        }
    }


    public void initKeywords(ArrayList<CaseTypesBean.CaseTypesContent> contentList) {
        ArrayList<SearchItem> morelist = new ArrayList<SearchItem>();
        ArrayList<Item> mlist = new ArrayList<Item>();

        for (CaseTypesBean.CaseTypesContent item : contentList) {
            mlist.add(new Item(item.getName(), 0));
            mlist.add(new Item(item.getName(), 0));
            mlist.add(new Item(item.getName(), 0));
            mlist.add(new Item(item.getName(), 0));
            mlist.add(new Item(item.getName(), 0));

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
        final BaseListAdapter<SearchItem> adapter = new BaseListAdapter<SearchItem>(curlistView,_activity, contentList,
                HomeTabsTpl.class, null);
        curlistView.setAdapter(adapter);

        final BaseListAdapter<SearchItem> adapter2 = new BaseListAdapter<SearchItem>(curlistView,_activity, contentList,
                HomeTabsTpl.class, null);
        unselectListView.setAdapter(adapter2);

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
