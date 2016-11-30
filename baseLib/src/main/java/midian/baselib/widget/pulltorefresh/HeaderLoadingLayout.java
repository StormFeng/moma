package midian.baselib.widget.pulltorefresh;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.midian.baselib.R;

import midian.baselib.widget.BGAMoocStyleRefreshView;

/**
 * 这个类封装了下拉刷新的布局
 *
 * @author Li Hong
 * @since 2013-7-30
 */
public class HeaderLoadingLayout extends LoadingLayout {
    /**
     * 旋转动画时间
     */
    private static final int ROTATE_ANIM_DURATION = 150;
    /**
     * Header的容器
     */
    private RelativeLayout mHeaderContainer;
    /** 箭头图片 */
//	private ImageView mArrowImageView;
    /** 进度条 */
//	private ProgressBar mProgressBar;
    /** 状态提示TextView */
//	private TextView mHintTextView;
    /**
     * 最后更新时间的TextView
     */
    private TextView mHeaderTimeView;
    /**
     * 最后更新时间的标题
     */
    private TextView mHeaderTimeViewTitle;
    /** 向上的动画 */
//	private Animation mRotateUpAnim;
    /**
     * 向下的动画
     */
//	private Animation mRotateDownAnim;

    private BGAMoocStyleRefreshView mMoocRefreshView;
    /**
     * 原始的图片
     */
    private Bitmap mOriginalBitmap;
    /**
     * 最终生成图片的填充颜色
     */
    private int mUltimateColor = -1;

    /**
     * 构造方法
     *
     * @param context context
     */
    public HeaderLoadingLayout(Context context) {
        super(context);
        init(context);
    }

    /**
     * 构造方法
     *
     * @param context context
     * @param attrs   attrs
     */
    public HeaderLoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * 初始化
     *
     * @param context context
     */
    private void init(Context context) {
        mHeaderContainer = (RelativeLayout) findViewById(R.id.pull_to_refresh_header_content);
//		mArrowImageView = (ImageView) findViewById(R.id.pull_to_refresh_header_arrow);//下拉刷新的箭头
//		mHintTextView = (TextView) findViewById(R.id.pull_to_refresh_header_hint_textview);
//		mProgressBar = (ProgressBar) findViewById(R.id.pull_to_refresh_header_progressbar);//刷新图标后的圆形进度图标
        mMoocRefreshView = (BGAMoocStyleRefreshView) findViewById(R.id.moocView);
        if (mOriginalBitmap != null) {
            mMoocRefreshView.setOriginalBitmap(mOriginalBitmap);
        }
        if (mUltimateColor != -1) {
            mMoocRefreshView.setUltimateColor(mUltimateColor);
        }

        float pivotValue = 0.5f; // SUPPRESS CHECKSTYLE
        float toDegree = -180f; // SUPPRESS CHECKSTYLE


        // 初始化旋转动画
//        mRotateUpAnim = new RotateAnimation(0.0f, toDegree, Animation.RELATIVE_TO_SELF, pivotValue, Animation.RELATIVE_TO_SELF, pivotValue);
//        mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
//        mRotateUpAnim.setFillAfter(true);
//        mRotateDownAnim = new RotateAnimation(toDegree, 0.0f, Animation.RELATIVE_TO_SELF, pivotValue, Animation.RELATIVE_TO_SELF, pivotValue);
//        mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
//        mRotateDownAnim.setFillAfter(true);
    }

    /**
     * 设置原始的图片
     *
     * @param originalBitmap
     */
    public void setOriginalBitmap(Bitmap originalBitmap) {
        mOriginalBitmap = originalBitmap;
    }

    /**
     * 设置最终生成图片的填充颜色
     *
     * @param ultimateColor
     */
    public void setUltimateColor(int ultimateColor) {
        mUltimateColor = ultimateColor;
    }


    @Override
    public void setLastUpdatedLabel(CharSequence label) {
        // 如果最后更新的时间的文本是空的话，隐藏前面的标题
        mHeaderTimeViewTitle.setVisibility(TextUtils.isEmpty(label) ? View.INVISIBLE : View.VISIBLE);
        mHeaderTimeView.setText(label);
    }

    @Override
    public int getContentSize() {
        if (null != mHeaderContainer) {
            return mHeaderContainer.getHeight();
        }
        return (int) (getResources().getDisplayMetrics().density * 60);
    }

    @Override
    protected View createLoadingView(Context context, AttributeSet attrs) {
//		View container = LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_header, null);//下拉刷新库本身的布局
        View container = LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_header1, null);
        return container;
    }

    @Override
    protected void onStateChanged(State curState, State oldState) {
//		mArrowImageView.setVisibility(View.VISIBLE);
//		mProgressBar.setVisibility(View.INVISIBLE);

        super.onStateChanged(curState, oldState);
    }

    @Override
    protected void onReset() {
//        mArrowImageView.clearAnimation();
        mMoocRefreshView.clearAnimation();
//		mHintTextView.setText(R.string.pull_to_refresh_header_hint_normal);
    }

    /**
     * 下拉刷新时
     */
    @Override
    protected void onPullToRefresh() {
        if (State.RELEASE_TO_REFRESH == getPreState()) {
            mMoocRefreshView.clearAnimation();
        }

//		mHintTextView.setText(R.string.pull_to_refresh_header_hint_normal);
    }

    /**
     * 结束下拉刷新（此方法待确认）
     */
    @Override
    protected void onEndRefresh() {
        mMoocRefreshView.clearAnimation();
        mMoocRefreshView.stopRefreshing();
    }

    /**
     * 释放刷新时
     */
    @Override
    protected void onReleaseToRefresh() {
//        mMoocRefreshView.stopRefreshing();
//		mArrowImageView.clearAnimation();
        mMoocRefreshView.startRefreshing();//下拉刷新时的动画
//        mArrowImageView.startAnimation(mRotateUpAnim);//释放刷新时要展现的动画
//		mHintTextView.setText(R.string.pull_to_refresh_header_hint_ready);
    }

    @Override
    protected void onRefreshing() {
//		mArrowImageView.clearAnimation();
//		mArrowImageView.setVisibility(View.INVISIBLE);
//        mProgressBar.setVisibility(View.VISIBLE);
//		mHintTextView.setText(R.string.pull_to_refresh_header_hint_loading);
    }


    @Override
    protected void onNoMoreData() {
        super.onNoMoreData();
        mMoocRefreshView.stopRefreshing();
    }
}
