package com.midian.moma.utils;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.midian.moma.R;
import com.midian.moma.datasource.ImageViewerAdapter;

import java.util.ArrayList;

import midian.baselib.adapter.OnPageChangeAdapter;
import midian.baselib.base.BaseActivity;
import midian.baselib.widget.BaseLibTopbarView;
import midian.baselib.widget.uk.co.senab.photoview.HackyViewPager;

/**
 * 动态-查看大图
 * 
 * @author XuYang
 *
 */
public class ImageViewerActivity extends BaseActivity implements OnCheckedChangeListener {
	private BaseLibTopbarView topbar;
	private HackyViewPager pager;
	private TextView indicator_tv;
	private ArrayList<ImageViewerAdapter.ImageBean> imageBeans = new ArrayList<ImageViewerAdapter.ImageBean>();
	private int startIndex;
	private CheckBox checkbox;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.c_imageviewer);
		startIndex = mBundle != null ? mBundle.getInt("startIndex", 0) : 0;
		initView();

	}

	private void initView() {
		topbar = findView(R.id.topbar);
		pager = findView(R.id.pager);
		indicator_tv = findView(R.id.indicator);
		checkbox = findView(R.id.checkbox);
		if (mBundle != null && mBundle.getBoolean("isShowCheckbox")) {
			checkbox.setVisibility(View.VISIBLE);
		} else {
			checkbox.setVisibility(View.GONE);
		}
		checkbox.setOnCheckedChangeListener(this);

		topbar.setVisibility(View.GONE);

		ArrayList<ImageViewerAdapter.ImageBean> beans = (ArrayList<ImageViewerAdapter.ImageBean>) mBundle.getSerializable("imageBeans");

		if (beans != null) {
			imageBeans.addAll(beans);
		}
		pager.setAdapter(new ImageViewerAdapter(this, imageBeans));
		pager.setCurrentItem(startIndex);
		indicator_tv.setText((startIndex + 1) + "/" + imageBeans.size());
		pager.setOnPageChangeListener(new OnPageChangeAdapter() {
			@Override
			public void onPageSelected(int position) {
				indicator_tv.setText(position + 1 + "/" + imageBeans.size());
				checkbox.setChecked(imageBeans.get(position).isChecked);
			}
		});
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		imageBeans.get(pager.getCurrentItem()).isChecked = isChecked;
	}

	@Override
	public void finish() {
		Bundle bundle = new Bundle();
		bundle.putSerializable("imageBeans", imageBeans);
		setResult(RESULT_OK, bundle);
		super.finish();
	}

}