package com.midian.moma.ui.home;

import android.os.Bundle;

import com.midian.login.R;
import com.midian.login.bean.MultiItem;
import com.midian.moma.datasource.ChooseCitysDataSource;
import com.midian.moma.itemview.AllNationalTpl;
import com.midian.moma.itemview.ChooseCitysHeadTpl;
import com.midian.moma.itemview.ChooseCitysViewTpl;
import com.midian.moma.itemview.ChooseLocationCitysHeadTpl;

import java.util.ArrayList;

import midian.baselib.base.BaseMultiTypeListActivity;
import midian.baselib.shizhefei.view.listviewhelper.IDataSource;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;

/**
 * 选择城市
 *
 * @author chu
 */
public class ChooseCitysActivity extends BaseMultiTypeListActivity<MultiItem> {

    private BaseLibTopbarView topbar;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        index = getIntent().getIntExtra("index", 0);
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        topbar = (BaseLibTopbarView) findViewById(R.id.topbar);
        topbar.setTitle("选择城市");
        topbar.setLeftImageButton(R.drawable.icon_back, null).setLeftText("返回", UIHelper.finish(_activity));

    }


    @Override
    protected IDataSource<ArrayList<MultiItem>> getDataSource() {
        return new ChooseCitysDataSource(_activity);
    }

    @Override
    protected ArrayList<Class> getTemplateClasses() {

        ArrayList<Class> tpls = new ArrayList<Class>();
        tpls.add(AllNationalTpl.class);//全国条目
        tpls.add(ChooseLocationCitysHeadTpl.class);//定位城市
        tpls.add(ChooseCitysHeadTpl.class);//选择城市文字提示条目
        tpls.add(ChooseCitysViewTpl.class);//城市列表条目

        return tpls;
    }

}
