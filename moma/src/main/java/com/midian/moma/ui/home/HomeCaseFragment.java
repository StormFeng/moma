package com.midian.moma.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.midian.moma.datasource.HomeCaseListDataSource;
import com.midian.moma.itemview.HomeBannerTpl;
import com.midian.moma.itemview.HomeCaseListTpl;
import java.util.ArrayList;
import midian.baselib.base.BaseMultiTypeListFragment;
import midian.baselib.bean.NetResult;
import midian.baselib.shizhefei.view.listviewhelper.IDataSource;

/**
 * Created by chu on 2016/1/26.
 */
public class HomeCaseFragment extends BaseMultiTypeListFragment<NetResult> {
    HomeCaseListDataSource homeDataSource;
    String type_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        type_id = getArguments().getString("type_id");
        homeDataSource = new HomeCaseListDataSource(_activity, type_id);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //自定义列表无数据时的提示文字和事件 DeFaultLoadViewFactory.java
//        listViewHelper.loadViewFactory.config(false, "");
    }

    @Override
    public void onResume() {
        super.onResume();
//        listViewHelper.refresh();
    }

    @Override
    protected IDataSource<ArrayList<NetResult>> getDataSource() {
        return homeDataSource;
    }

    @Override
    protected ArrayList<Class> getTemplateClasses() {
        ArrayList<Class> list = new ArrayList<Class>();
        list.add(HomeBannerTpl.class);
        list.add(HomeCaseListTpl.class);
        return list;
    }

//    @Override
//    protected Class getTemplateClass() {
//        return HomeCaseListTpl.class;
//    }


}
