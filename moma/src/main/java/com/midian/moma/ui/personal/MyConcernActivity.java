package com.midian.moma.ui.personal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.midian.moma.R;
import com.midian.moma.datasource.MyConcernDataSource;
import com.midian.moma.itemview.MyConcernTpl;

import java.util.ArrayList;

import midian.baselib.base.BaseListActivity;
import midian.baselib.shizhefei.view.listviewhelper.IDataAdapter;
import midian.baselib.shizhefei.view.listviewhelper.IDataSource;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;

/**
 * 我的关注
 * Created by chu on 2015.11.23.023.
 */
public class MyConcernActivity extends BaseListActivity {

    private BaseLibTopbarView topbar;
    private View delete_ll;


    private OnClickListener editClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (resultList.size() > 0) {
                showEdit();//编辑状态
            }

        }
    };

    private OnClickListener cancelClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            showNormal();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        //自定义列表无数据时的提示文字和事件 DeFaultLoadViewFactory.java
        listViewHelper.loadViewFactory.config(true, "您还没有关注内容哦");
        listViewHelper.loadViewFactory.BtOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        }, "去看看");
    }


    private void initView() {
        topbar = findView(R.id.topbar);
        showNormal();
    }

    private void showNormal() {
        topbar.setTitle("我的关注").setLeftImageButton(R.drawable.icon_back, null)
                .setLeftText("返回", UIHelper.finish(_activity))
                .setRightText("管理", editClickListener).showRight_tv();
        MyConcernTpl.setIsEdit(false);
        adapter.notifyDataSetChanged();
    }

    private void showEdit() {
        topbar.setTitle("我的关注").setLeftImageButton(R.drawable.icon_back, null)
                .setLeftText("返回", UIHelper.finish(_activity))
                .setRightText("完成", cancelClickListener).showRight_tv();
        MyConcernTpl.setIsEdit(true);
        adapter.notifyDataSetChanged();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_concern;
    }

    @Override
    protected IDataSource<ArrayList> getDataSource() {
        return new MyConcernDataSource(_activity);
    }

    @Override
    protected Class getTemplateClass() {

        return MyConcernTpl.class;
    }


    @Override
    public void onEndRefresh(IDataAdapter adapter, ArrayList result) {
        super.onEndRefresh(adapter, result);
        showNormal();
        if (resultList.size() > 0) {
            topbar.showRight_tv();
        } else {
            topbar.hideRight_tv();
        }
    }


}
