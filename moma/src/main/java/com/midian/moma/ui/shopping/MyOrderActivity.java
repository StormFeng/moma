package com.midian.moma.ui.shopping;

import java.util.ArrayList;

import com.midian.moma.R;
import com.midian.moma.ui.shopping.fragment.AllOrderFragment;
import com.midian.moma.ui.shopping.fragment.PendingAuditFragment;
import com.midian.moma.ui.shopping.fragment.PendingCommitFragment;
import com.midian.moma.ui.shopping.fragment.PendingFinishFragment;
import com.midian.moma.ui.shopping.fragment.PendingPayFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import midian.baselib.adapter.PagerTabAdapter;
import midian.baselib.base.BaseFragmentActivity;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;
import midian.baselib.widget.BaseLibViewPager;
import midian.baselib.widget.PagerSlidingTabStrip;

/**
 * 我的订单
 *
 * @author chu
 */
public class MyOrderActivity extends BaseFragmentActivity implements OnPageChangeListener {

    private BaseLibTopbarView topbar;
    private PagerSlidingTabStrip tabs;
    private BaseLibViewPager pager;
    private PagerTabAdapter adapter;

    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    private ArrayList<String> titles = new ArrayList<String>();
    private AllOrderFragment mAllOrderFragment;
    private PendingPayFragment mPayFragment;
    private PendingCommitFragment mCommitFragment;
    private PendingAuditFragment mAuditFragment;
    private PendingFinishFragment mFinishFragment;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        position = mBundle.getInt("position");

        initView();
    }

    private void initView() {
        topbar = findView(R.id.topbar);
        topbar.setTitle("我的订单").setLeftImageButton(R.drawable.icon_back, null).setLeftText("返回",
                UIHelper.finish(_activity));
        tabs = findView(R.id.tabs);
        pager = findView(R.id.pager);
        tabs.setShowRigthIcon(false);
        // tabs.setUpLine(false);
        // tabs.setIndicatorColor(getResources().getColor(R.color.orange_button));//
        // 设置指示线的颜色
        // tabs.setDividerColor(getResources().getColor(R.color.orange_button));//
        // 设置TAB的标签分隔竖线的颜色
        // tabs.setUnderlineColor(getResources().getColor(R.color.grey));//
        // 设置下划线的颜色
        tabs.setOnPageChangeListener(this);
        mAllOrderFragment = new AllOrderFragment();
        mPayFragment = new PendingPayFragment();
        mCommitFragment = new PendingCommitFragment();
        mAuditFragment = new PendingAuditFragment();
        mFinishFragment = new PendingFinishFragment();

        fragments.add(mAllOrderFragment);
        fragments.add(mPayFragment);
        fragments.add(mCommitFragment);
        fragments.add(mAuditFragment);
        fragments.add(mFinishFragment);


        titles.add("全部");
        titles.add("待付款");
        titles.add("待提交");
        titles.add("待审核");
        titles.add("已完成");
        adapter = new PagerTabAdapter(fm, titles, fragments);
        pager.setAdapter(adapter);
        tabs.setViewPager(pager);
        tabs.setOnPageChangeListener(this);
        pager.setCurrentItem(position);

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
//        tabs.setViewPager();


    }
}
