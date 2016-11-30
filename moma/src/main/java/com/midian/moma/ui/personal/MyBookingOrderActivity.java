package com.midian.moma.ui.personal;

import java.util.ArrayList;

import com.midian.moma.R;
import com.midian.moma.ui.personal.fragment.BookingAllFragment;
import com.midian.moma.ui.personal.fragment.BookingPendingAuditFragment;
import com.midian.moma.ui.personal.fragment.BookingRefuseFragment;
import com.midian.moma.ui.personal.fragment.BookingThroughFragment;
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
 * 我的预订
 *
 * @author chu
 */
public class MyBookingOrderActivity extends BaseFragmentActivity implements OnPageChangeListener {

    private BaseLibTopbarView topbar;
    private PagerSlidingTabStrip tabs;
    private BaseLibViewPager pager;
    private PagerTabAdapter adapter;

    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    private ArrayList<String> titles = new ArrayList<String>();
    private BookingAllFragment mAllFragment;
    private BookingPendingAuditFragment mAuditFragment;
    private BookingThroughFragment mThroughFragment;
    private BookingRefuseFragment mRefuseFragment;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        position= mBundle.getInt("position");
        initView();
    }

    private void initView() {
        topbar = findView(R.id.topbar);
        topbar.setTitle("我的预订").setLeftImageButton(R.drawable.icon_back, null).setLeftText("返回",
                UIHelper.finish(_activity));
        tabs = findView(R.id.tabs);
        pager = findView(R.id.pager);
        tabs.setShowRigthIcon(false);
        tabs.setUpLine(false);
//		 tabs.setIndicatorColor(getResources().getColor(R.color.orange_button));//
        // 设置指示线的颜色
        // tabs.setDividerColor(getResources().getColor(R.color.orange_button));//
        // 设置TAB的标签分隔竖线的颜色
//        tabs.setUnderlineColor(getResources().getColor(R.color.grey));// 设置下划线的颜色

        mAllFragment = new BookingAllFragment();
        mAuditFragment = new BookingPendingAuditFragment();
        mThroughFragment = new BookingThroughFragment();
        mRefuseFragment = new BookingRefuseFragment();

        fragments.add(mAllFragment);
        fragments.add(mAuditFragment);
        fragments.add(mThroughFragment);
        fragments.add(mRefuseFragment);
        titles.add("全部");
        titles.add("待审核");
        titles.add("已通过");
        titles.add("已拒绝");
        adapter = new PagerTabAdapter(fm, titles, fragments);
//        pager.getCurrentItem();
        pager.setAdapter(adapter);
        tabs.setViewPager(pager);
        tabs.setOnPageChangeListener(this);
        pager.setCurrentItem(position);

    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    @Override
    public void onPageSelected(int arg0) {

    }
}
