package com.midian.moma.tabs.activity;

import java.util.ArrayList;

import com.midian.moma.R;
import com.midian.moma.customview.DragGrid;
import com.midian.moma.customview.OtherGridView;
import com.midian.moma.tabs.bean.ChannelItem;
import com.midian.moma.tabs.bean.ChannelManage;
import com.midian.moma.ui.MainActivity;
import com.midian.moma.utils.AppUtil;


import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import midian.baselib.base.BaseActivity;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;

/**
 * 编辑首页tabs标签
 */
public class ChannelActivity extends BaseActivity implements OnItemClickListener {
	public static String TAG = "ChannelActivity";
	/** 用户栏目的GRIDVIEW */
	private DragGrid userGridView;
	/** 其它栏目的GRIDVIEW */
	private OtherGridView otherGridView;
	/** 用户栏目对应的适配器，可以拖动 */
	DragAdapter userAdapter;
	/** 其它栏目对应的适配器 */
	OtherAdapter otherAdapter;
	/** 其它栏目列表 */
	ArrayList<ChannelItem> otherChannelList = new ArrayList<ChannelItem>();
	/** 用户栏目列表 */
	ArrayList<ChannelItem> userChannelList = new ArrayList<ChannelItem>();
	/** 是否在移动，由于这边是动画结束后才进行的数据更替，设置这个限制为了避免操作太频繁造成的数据错乱。 */	
	boolean isMove = false;
	private BaseLibTopbarView topbar;
	private TextView text_hint, edit_save,tv_hint;
	boolean isEdit=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.channel);
		initView();
		initData();
		showNormal();
	}

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
	
	/** 初始化数据*/
	private void initData() {
	    userChannelList = ((ArrayList<ChannelItem>) ChannelManage.getManage(AppUtil.getMAppContext(ac).getSQLHelper()).getUserChannel());
	    otherChannelList = ((ArrayList<ChannelItem>)ChannelManage.getManage(AppUtil.getMAppContext(ac).getSQLHelper()).getOtherChannel());
	    userAdapter = new DragAdapter(this, userChannelList);
	    userGridView.setAdapter(userAdapter);
	    otherAdapter = new OtherAdapter(this, otherChannelList);
	    otherGridView.setAdapter(otherAdapter);
	    //设置GRIDVIEW的ITEM的点击监听
	    otherGridView.setOnItemClickListener(this);
	    userGridView.setOnItemClickListener(this);
	}
	
	/** 初始化布局*/
	private void initView() {
		topbar = findView(R.id.topbar);
		text_hint = findView(R.id.text_hint);
        tv_hint = findView(R.id.tv_hint);
        edit_save = findView(R.id.edit_save_bt);
		userGridView = (DragGrid) findViewById(R.id.userGridView);
		otherGridView = (OtherGridView) findViewById(R.id.otherGridView);
	}

	protected void showNormal() {
		topbar.getTitle_tv().setVisibility(View.GONE);
		topbar.setLeftImageButton(R.drawable.icon_back, null).setLeftText("返回", new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});
		text_hint.setText("点击进入对应的案例频道");
		edit_save.setText("编辑");
        tv_hint.setText("点击编辑添加更多案例频道");
        edit_save.setOnClickListener(editListener);
		isEdit=false;
		userAdapter.setEdit(false);
	}

	private void showEdit() {
		topbar.getTitle_tv().setVisibility(View.GONE);
		topbar.setLeftImageButton(R.drawable.icon_back, null).setLeftText("返回", UIHelper.finish(_activity));
		text_hint.setText("点击移除对应的案例频道");
		edit_save.setText("保存");
        tv_hint.setText("点击以下频道进行添加");
		edit_save.setOnClickListener(saveListener);
		isEdit=true;
		userAdapter.setEdit(true);
	}

	
	/** GRIDVIEW对应的ITEM点击监听接口  */
	@Override
	public void onItemClick(AdapterView<?> parent, final View view, final int position,long id) {
		//如果点击的时候，之前动画还没结束，那么就让点击事件无效
		if(isMove){
			return;
		}
		if(isEdit){

		}else{
			if(parent.getId()==R.id.userGridView){
				onSelect(position);
			}
			return;
		}

		switch (parent.getId()) {
		case R.id.userGridView:

			//position为 0，1 的不可以进行任何操作
			if (!userAdapter.isHoldPosition(position)) {
				view.findViewById(R.id.icon_delete).setVisibility(View.GONE);
				final ImageView moveImageView = getView(view);
				if (moveImageView != null) {
					TextView newTextView = (TextView) view.findViewById(R.id.text_item);
					final int[] startLocation = new int[2];
					newTextView.getLocationInWindow(startLocation);
					final ChannelItem channel = ((DragAdapter) parent.getAdapter()).getItem(position);//获取点击的频道内容
					otherAdapter.setVisible(false);
					//添加到最后一个
					otherAdapter.addItem(channel);
					new Handler().postDelayed(new Runnable() {
						public void run() {
							try {
								int[] endLocation = new int[2];
								//获取终点的坐标
								otherGridView.getChildAt(otherGridView.getLastVisiblePosition()).getLocationInWindow(endLocation);
								MoveAnim(moveImageView, startLocation , endLocation, channel,userGridView);
								userAdapter.setRemove(position);
							} catch (Exception localException) {
							}
						}
					}, 50L);
				}
			}
			break;
		case R.id.otherGridView:
			final ImageView moveImageView = getView(view);
			if (moveImageView != null){
				TextView newTextView = (TextView) view.findViewById(R.id.text_item);
				final int[] startLocation = new int[2];
				newTextView.getLocationInWindow(startLocation);
				final ChannelItem channel = ((OtherAdapter) parent.getAdapter()).getItem(position);
				userAdapter.setVisible(false);
				//添加到最后一个
				userAdapter.addItem(channel);
				new Handler().postDelayed(new Runnable() {
					public void run() {
						try {
							int[] endLocation = new int[2];
							//获取终点的坐标
							userGridView.getChildAt(userGridView.getLastVisiblePosition()).getLocationInWindow(endLocation);
							MoveAnim(moveImageView, startLocation , endLocation, channel,otherGridView);
							otherAdapter.setRemove(position);
						} catch (Exception localException) {
						}
					}
				}, 50L);
			}
			break;
		default:
			break;
		}
	}
	/**
	 * 点击ITEM移动动画
	 * @param moveView
	 * @param startLocation
	 * @param endLocation
	 * @param moveChannel
	 * @param clickGridView
	 */
	private void MoveAnim(View moveView, int[] startLocation,int[] endLocation, final ChannelItem moveChannel,
			final GridView clickGridView) {
		int[] initLocation = new int[2];
		//获取传递过来的VIEW的坐标
		moveView.getLocationInWindow(initLocation);
		//得到要移动的VIEW,并放入对应的容器中
		final ViewGroup moveViewGroup = getMoveViewGroup();
		final View mMoveView = getMoveView(moveViewGroup, moveView, initLocation);
		//创建移动动画
		TranslateAnimation moveAnimation = new TranslateAnimation(
				startLocation[0], endLocation[0], startLocation[1],
				endLocation[1]);
		moveAnimation.setDuration(300L);//动画时间
		//动画配置
		AnimationSet moveAnimationSet = new AnimationSet(true);
		moveAnimationSet.setFillAfter(false);//动画效果执行完毕后，View对象不保留在终止的位置
		moveAnimationSet.addAnimation(moveAnimation);
		mMoveView.startAnimation(moveAnimationSet);
		moveAnimationSet.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				isMove = true;
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				moveViewGroup.removeView(mMoveView);
				// instanceof 方法判断2边实例是不是一样，判断点击的是DragGrid还是OtherGridView
				if (clickGridView instanceof DragGrid) {
					otherAdapter.setVisible(true);
					otherAdapter.notifyDataSetChanged();
					userAdapter.remove();
				}else{
					userAdapter.setVisible(true);
					userAdapter.notifyDataSetChanged();
					otherAdapter.remove();
				}
				isMove = false;
			}
		});
	}
	
	/**
	 * 获取移动的VIEW，放入对应ViewGroup布局容器
	 * @param viewGroup
	 * @param view
	 * @param initLocation
	 * @return
	 */
	private View getMoveView(ViewGroup viewGroup, View view, int[] initLocation) {
		int x = initLocation[0];
		int y = initLocation[1];
		viewGroup.addView(view);
		LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		mLayoutParams.leftMargin = x;
		mLayoutParams.topMargin = y;
		view.setLayoutParams(mLayoutParams);
		return view;
	}
	
	/**
	 * 创建移动的ITEM对应的ViewGroup布局容器
	 */
	private ViewGroup getMoveViewGroup() {
		ViewGroup moveViewGroup = (ViewGroup) getWindow().getDecorView();
		LinearLayout moveLinearLayout = new LinearLayout(this);
		moveLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		moveViewGroup.addView(moveLinearLayout);
		return moveLinearLayout;
	}
	
	/**
	 * 获取点击的Item的对应View，
	 * @param view
	 * @return
	 */
	private ImageView getView(View view) {
		view.destroyDrawingCache();
		view.setDrawingCacheEnabled(true);
		Bitmap cache = Bitmap.createBitmap(view.getDrawingCache());
		view.setDrawingCacheEnabled(false);
		ImageView iv = new ImageView(this);
		iv.setImageBitmap(cache);
		return iv;
	}
	
	/** 退出时候保存选择后数据库的设置  */
	private void saveChannel() {
		ChannelManage.getManage(AppUtil.getMAppContext(ac).getSQLHelper()).deleteAllChannel();
		ChannelManage.getManage(AppUtil.getMAppContext(ac).getSQLHelper()).saveUserChannel(userAdapter.getChannnelLst());
		ChannelManage.getManage(AppUtil.getMAppContext(ac).getSQLHelper()).saveOtherChannel(otherAdapter.getChannnelLst());
	}
	public void onSelect(int index) {
		saveChannel();
		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		Bundle b=new Bundle();
		b.putInt("index",index);
		intent.putExtras(b);
		setResult(MainActivity.CHANNELRESULT, intent);
		finish();
	}

	public void onBackPressed() {
		saveChannel();
		if(userAdapter.isListChanged()){
			Intent intent = new Intent(getApplicationContext(), MainActivity.class);
			setResult(MainActivity.CHANNELRESULT, intent);
			Log.d(TAG, "数据发生改变");
		}
		finish();
	}
}
