package com.midian.moma.ui.home;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import com.midian.moma.R;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;
import com.nineoldandroids.view.ViewHelper;

/**
 * 空调可拖动编辑按钮 Created by XuYang on 15/4/21.
 */
public class DpDragableButton extends FrameLayout implements
        View.OnClickListener {
    private Context context;

    private FrameLayout decorView;// 窗口视图

    private ImageView dotRect;// 虚线框
    private TextView content_tv;// 内容区域
    private ImageView delete_iv;// 删除按钮

    private Bitmap draggedBitmap;// 可拖动图片的bitmap
    private ImageView draggedImage;// 可拖动图片

    private int width;// 按钮宽度
    private int height;// 按钮高度

    private int originX;// 按钮初始位置左上角x坐标
    private int originY;// 按钮初始位置左上角y坐标

    private ArrayList<DpDragableButton> buttons;// 所有相关按钮
    private ArrayList<DpDragableBean> beans;// 所有相关按钮状态实体
    private DpDragableBean bean;// 状态实体

    private int intersectIndex = -1;// 相交的按钮索引

    //	private DpViewPager pager; // 按钮所在viewpager
    private DpScrollView scrollView1; // 按钮ScrollView
    private DpScrollView scrollView2; // 按钮ScrollView

    OnClickListener mOnClickListener;

    public DpDragableButton(Context context) {
        super(context);
        init(context);
    }

    public DpDragableButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        this.decorView = (FrameLayout) ((Activity) context).getWindow()
                .getDecorView();
        initView();
        setOnClickListener(this);
    }

    public void setOnClickListener(OnClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    private void initView() {
        dotRect = new ImageView(context);
        dotRect.setImageResource(R.drawable.dp_kongtiao_editable_btn_dot_rect);
        LayoutParams params1 = new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params1.leftMargin = 10;
        params1.rightMargin = 10;
        params1.topMargin = 10;
        params1.bottomMargin = 10;
        addView(dotRect, params1);

        content_tv = new TextView(context);
        content_tv.setGravity(Gravity.CENTER);
        content_tv.setText(context.getString(R.string.custom));
        content_tv.setTextColor(getResources().getColor(android.R.color.white));
        content_tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        content_tv.setBackgroundResource(R.color.window_bg);
        LayoutParams params2 = new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params2.gravity = Gravity.CENTER;
        /*content_tv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (bean.isEidtable()) {
                    new DpRenameDialog(context).setListner(
                            DpDragableButton.this).show(bean.getName());
                } else {
                    if (mOnClickListener != null)
                        mOnClickListener.onClick(DpDragableButton.this);
                }
            }
        });*/

        addView(content_tv, params2);

        delete_iv = new ImageView(context);
        delete_iv
                .setBackgroundResource(R.drawable.dp_ic_kongtiao_button_delete);
        LayoutParams params3 = new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params3.gravity = Gravity.RIGHT;
		/*delete_iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new DpConfirmDialog(getContext(), R.style.confirm_dialog)
						.config("提醒", "确定删除吗？", "确认",
								new OnClickListener() {
									@Override
									public void onClick(View view) {
										bean.setIsVisible(false);
										render();
									}
								}).show();
			}
		});*/
        addView(delete_iv, params3);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                touchDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(event);
                break;
            case MotionEvent.ACTION_UP:
                touchUp(event);
                break;

        }

        if (bean.isDraggable()) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 处理down事件
     *
     * @param event
     */
    private void touchDown(MotionEvent event) {
        if (!bean.isDraggable()) {
            return;
        }
        generateDraggedImage();

        bean.setIsVisible(false);
        setVisibility(INVISIBLE);

		/*if (pager != null) {
			pager.setPagingEnabled(false);
		}*/
        if (scrollView1 != null) {
            scrollView1.setScrollable(false);
        }
        if (scrollView2 != null) {
            scrollView2.setScrollable(false);
        }
    }

    /**
     * 处理move事件
     *
     * @param event
     */
    private void touchMove(MotionEvent event) {
        if (!bean.isDraggable()) {
            return;
        }
        drag((int) event.getRawX() - width / 2, (int) event.getRawY() - height
                / 2);

        boolean hasIntersect = false;
        for (int i = 0; i < buttons.size(); i++) {
            DpDragableButton btn = buttons.get(i);
            int[] loc1 = new int[2];
            int[] loc2 = new int[2];
            draggedImage.getLocationOnScreen(loc1);
            btn.getLocationOnScreen(loc2);

            PointF draggedCenterPoint = new PointF(loc1[0]
                    + draggedImage.getWidth() / 2, loc1[1]
                    + draggedImage.getHeight() / 2);
            RectF btnRect = new RectF(loc2[0], loc2[1], loc2[0]
                    + btn.getWidth(), loc2[1] + btn.getHeight());

            boolean isIntersect = btnRect.contains(draggedCenterPoint.x,
                    draggedCenterPoint.y);
            if (isIntersect) {
                hasIntersect = isIntersect;
                intersectIndex = i;
            }

        }
        // 如果不存在相交,还原
        if (!hasIntersect || buttons.get(intersectIndex) == this) {
            intersectIndex = -1;
        }
        // 更新高亮
        updateHightLight();
    }

    /**
     * 处理up事件
     */
    private void touchUp(MotionEvent event) {
        if (!bean.isDraggable()) {
            return;
        }

        /**
         * 松手时设置相交按钮状态
         */
        if (intersectIndex >= 0) {
            // 如果拖动的是自定义按钮,执行替换操作
            final DpDragableButton intersectBtn = buttons.get(intersectIndex);
            final DpDragableBean intersectBtnBean = intersectBtn.getBean();

            final boolean intersetBtnVisible = intersectBtnBean.isVisible();
            intersectBtnBean.setIsVisible(false);
            intersectBtn.setVisibility(INVISIBLE);

            Log.i("test", DpDragableButton.this.bean.toString());
            Log.i("test", intersectBtnBean.toString());

            if (!bean.isDeleteShow()) {

                int[] xys = getReplaceResXYs(event, intersectBtn);
                AnimatorSet animatorSet = buildReleaseAnimator(draggedImage,
                        xys[0], xys[1], xys[2], xys[3]);
                animatorSet.addListener(new DpAnimatorAdapter() {
                    @Override
                    public void onAnimationEnd(
                            com.nineoldandroids.animation.Animator arg0) {
                        decorView.removeView(draggedImage);
                        setBackgroundResource(android.R.color.transparent);

                        bean.setIsVisible(true);
                        setVisibility(VISIBLE);

                        intersectBtnBean.setIsDeleteShow(true);
                        intersectBtnBean.setIsDotRectShow(true);
                        intersectBtnBean.setIsDraggable(true);
                        intersectBtnBean.setIsEidtable(true);
                        intersectBtnBean.setIsVisible(true);
                        intersectBtnBean.setName(DpDragableButton.this.bean
                                .getName());
                        intersectBtnBean.setBgType(DpDragableButton.this.bean
                                .getBgType());
                        intersectBtnBean.setCommand(DpDragableButton.this.bean
                                .getCommand());
                        intersectBtn.render();
                        intersectBtn
                                .setBackgroundResource(android.R.color.transparent);
                    }
                });
                animatorSet.start();

            }
            // 如果拖动的时功能按钮，执行交换操作
            if (bean.isDeleteShow()) {

                int[] xys = getReplaceResXYs(event, intersectBtn);
                AnimatorSet animatorSet = buildReleaseAnimator(draggedImage,
                        xys[0], xys[1], xys[2], xys[3]);
                animatorSet.addListener(new DpAnimatorAdapter() {
                    @Override
                    public void onAnimationEnd(
                            com.nineoldandroids.animation.Animator arg0) {
                        decorView.removeView(draggedImage);
                        setBackgroundResource(android.R.color.transparent);

                        bean.setIsVisible(true);
                        setVisibility(VISIBLE);

                    }
                });
                animatorSet.start();

                final ImageView intersectDraggedImage = intersectBtn
                        .generateDraggedImage();
                if (!intersetBtnVisible) {
                    intersectDraggedImage.setVisibility(INVISIBLE);
                }
                int[] xys2 = getReplaceDesXYs(intersectDraggedImage);
                AnimatorSet animatorSet2 = buildReleaseAnimator(
                        intersectDraggedImage, xys2[0], xys2[1], xys2[2],
                        xys2[3]);
                animatorSet2.addListener(new DpAnimatorAdapter() {
                    public void onAnimationEnd(
                            com.nineoldandroids.animation.Animator arg0) {

                        decorView.removeView(intersectDraggedImage);

                        DpDragableBean tempBean = DpDragableButton.this.bean;
                        intersectBtnBean.setIsVisible(intersetBtnVisible);
                        setBean(intersectBtnBean);

                        intersectBtn
                                .setBackgroundResource(android.R.color.transparent);
                        intersectBtn.setBean(tempBean);
                        intersectBtn.render();
                        render();
                    }

                    ;
                });
                animatorSet2.start();
            }
        } else {
            int[] xys = getReturnXYs(event, draggedImage);
            AnimatorSet animatorSet = buildReleaseAnimator(draggedImage,
                    xys[0], xys[1], xys[2], xys[3]);
            animatorSet.addListener(new DpAnimatorAdapter() {
                @Override
                public void onAnimationEnd(
                        com.nineoldandroids.animation.Animator arg0) {
                    decorView.removeView(draggedImage);

                    setBackgroundResource(android.R.color.transparent);

                    bean.setIsVisible(true);
                    setVisibility(VISIBLE);
                }
            });
            animatorSet.start();

        }

        intersectIndex = -1;

		/*if (pager != null) {
			pager.setPagingEnabled(true);
		}*/
        if (scrollView1 != null) {
            scrollView1.setScrollable(true);
        }
        if (scrollView2 != null) {
            scrollView2.setScrollable(true);
        }
    }

    private int[] getReturnXYs(MotionEvent event, View view) {
        int fromX = (int) (event.getRawX() - width / 2);
        int fromY = (int) (event.getRawY() - height / 2);
        int toX = originX;
        int toY = originY;
        int[] arr = new int[]{fromX, fromY, toX, toY};
        return arr;
    }

    private int[] getReplaceResXYs(MotionEvent event, View view) {
        int fromX = (int) (event.getRawX() - width / 2);
        int fromY = (int) (event.getRawY() - height / 2);
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int toX = location[0];
        int toY = location[1];
        int[] arr = new int[]{fromX, fromY, toX, toY};
        return arr;
    }

    private int[] getReplaceDesXYs(View view) {
        int fromX = (int) ViewHelper.getX(view);
        int fromY = (int) ViewHelper.getY(view);
        int toX = originX;
        int toY = originY;
        int[] arr = new int[]{fromX, fromY, toX, toY};
        return arr;
    }

    /**
     * 创建位移动画
     *
     * @param view
     * @param fromX
     * @param fromY
     * @param toX
     * @param toY
     * @return
     */
    private AnimatorSet buildReleaseAnimator(final View view, int fromX,
                                             int fromY, int toX, int toY) {
        int duration = 300;
        Interpolator interpolator = new AccelerateDecelerateInterpolator();
        ObjectAnimator animatorX = ObjectAnimator.ofInt(view, "x", fromX, toX);
        animatorX.setDuration(duration);
        animatorX.setInterpolator(interpolator);
        animatorX.addUpdateListener(new AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator arg0) {
                int cVal = (Integer) arg0.getAnimatedValue();
                ViewHelper.setX(view, cVal);

            }
        });
        ObjectAnimator animatorY = ObjectAnimator.ofInt(view, "y", fromY, toY);
        animatorY.setDuration(duration);
        animatorX.setInterpolator(interpolator);
        animatorY.addUpdateListener(new AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator arg0) {
                int cVal = (Integer) arg0.getAnimatedValue();
                ViewHelper.setY(view, cVal);

            }
        });

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX, animatorY);

        return animatorSet;
    }

    /**
     * 移动拖动的图片
     */
    private void drag(int x, int y) {
        if (draggedImage != null) {
            ViewHelper.setX(draggedImage, x);
            ViewHelper.setY(draggedImage, y);
        }
    }

    /**
     * 获得拖动视图
     *
     * @param view
     * @param bitmapWidth
     * @param bitmapHeight
     * @return
     */
    public static Bitmap convertViewToBitmap(View view, int bitmapWidth,
                                             int bitmapHeight) {
        Bitmap bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight,
                Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(bitmap));
        return bitmap;
    }

    @Override
    public void onClick(View v) {
//		new DpRenameDialog(context).setListner(DpDragableButton.this).show();
        // DpUIHelper.t(context, bean.toString());
    }

    /**
     * 渲染按钮
     */
    public DpDragableButton render() {
        if (bean.isVisible()) {
            setVisibility(VISIBLE);

            // 是否显示虚线框
            if (bean.isDotRectShow()) {
                dotRect.setVisibility(VISIBLE);
            } else {
                dotRect.setVisibility(INVISIBLE);
            }
            // 是否显示删除按钮
            if (bean.isDeleteShow()) {
                delete_iv.setVisibility(VISIBLE);
            } else {
                delete_iv.setVisibility(INVISIBLE);
            }
            // 设置文本
            content_tv.setText(bean.getName());
            // 设置背景框
            if (bean.getBgType() == 100) {
                content_tv.setBackgroundResource(R.drawable.default_button);
            } else if (bean.getBgType() == 1000) {
                content_tv.setBackgroundResource(R.drawable.default_button);
            } else if (bean.getBgType() == 2) {
                content_tv.setBackgroundResource(R.drawable.default_button);
            } else if (bean.getBgType() == 3) {
                content_tv.setBackgroundResource(R.drawable.default_button);
            }

        } else {
            // System.out.println("bean::::::::::" + bean.getName());
            setVisibility(INVISIBLE);
        }
        return this;
    }

    /**
     * 更新按钮高亮状态
     */
    private void updateHightLight() {
        // 非拖动状态不高亮
        if (!bean.isDraggable()) {
            return;
        }
        for (int i = 0; i < buttons.size(); i++) {
            if (intersectIndex == i) {
                buttons.get(i).setBackgroundResource(R.drawable.default_button);
            } else {
                buttons.get(i).setBackgroundResource(
                        android.R.color.transparent);
            }
        }
    }

    private ImageView generateDraggedImage() {
        width = this.getWidth();
        height = this.getHeight();
        draggedBitmap = convertViewToBitmap(this, width, height);
        draggedImage = new ImageView(context);
        draggedImage.setImageBitmap(draggedBitmap);
        draggedImage.setBackgroundResource(R.drawable.default_button);

        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        decorView.addView(draggedImage, params);
        int[] location = new int[2];
        this.getLocationOnScreen(location);
        originX = location[0];
        originY = location[1];

        ViewHelper.setX(draggedImage, location[0]);
        ViewHelper.setY(draggedImage, location[1]);
        ViewHelper.setScaleX(draggedImage, 1.0f);
        ViewHelper.setScaleY(draggedImage, 1.0f);

        return draggedImage;
    }

    public DpDragableBean getBean() {
        return bean;
    }

    public void setBean(DpDragableBean bean) {
        this.bean = bean;
    }

    public void setBeans(ArrayList<DpDragableBean> beans) {
        this.beans = beans;
    }

    public ArrayList<DpDragableBean> getBeans() {
        return beans;
    }

    public void setButtons(ArrayList<DpDragableButton> buttons) {
        this.buttons = buttons;
    }

    public ArrayList<DpDragableButton> getButtons() {
        return buttons;
    }

    /*public void setPager(DpViewPager pager) {
        this.pager = pager;
    }*/

    public void setScrollView1(DpScrollView scrollView1) {
        this.scrollView1 = scrollView1;
    }

    public void setScrollView2(DpScrollView scrollView2) {
        this.scrollView2 = scrollView2;
    }

    /*@Override
    public void onRename(String name) {
        // TODO Auto-generated method stub
        content_tv.setText(name.substring(0, Math.min(3, name.length())));
        bean.setName(name.substring(0, Math.min(3, name.length())));
    }*/
}
