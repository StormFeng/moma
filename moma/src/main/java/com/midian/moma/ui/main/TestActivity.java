package com.midian.moma.ui.main;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;

import com.midian.moma.R;

import midian.baselib.base.BaseActivity;

/**
 * Created by chu on 2016/2/29.
 */
public class TestActivity extends View {
   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }*/

    private PorterDuffXfermode porterDuffXfermode;//Xfermode
    private Paint paint;
    private Bitmap bitmap;//源图片
    private final int RED = 0xc9394a;//暗红色
    private int width, height;//控件宽高
    private Path path;//画贝塞尔曲线需要用到
    private Canvas mCanvas;//在该画布上绘制目标图片
    private Bitmap bg;//目标图片
    private float controlX, controlY;//贝塞尔曲线控制点，使用三阶贝塞尔曲线曲线，需要两个控制点，两个控制点都在该变量基础上生成
    private float waveY;//上升的高度
    private boolean isIncrease;//用于控制控制点水平移动


    public TestActivity(Context context) {
        this(context, null);
    }

    public TestActivity(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestActivity(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        //初始化画笔
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#ffc9394a"));
        //获得资源文件
//        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_mooc);
        //设置宽高为图片的宽高
        width = bitmap.getWidth();
        height = bitmap.getHeight();

        //初始状态值
        waveY = 7 / 8F * height;
        controlY = 17 / 16F * height;

        //初始化Xfermode
        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        //初始化path
        path = new Path();
        //初始化画布
        mCanvas = new Canvas();
        //创建bitmap
        bg = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        //将新建的bitmap注入画布
        mCanvas.setBitmap(bg);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawTargetBitmap();// 画目标图，存在bg上
        canvas.drawBitmap(bg, 0, 0, null);//将目标图绘制在当前画布上
        invalidate();// 重绘，这里为了方便看效果，可以使用线程对这部分代码进行控制
    }

    private void drawTargetBitmap() {
        path.reset();// 重置path
        bg.eraseColor(Color.parseColor("#00ffffff"));// 擦除像素

        // 当控制点的x坐标大于或等于终点x坐标时更改标识值
        if (controlX >= width + 1 / 2 * width) {
            isIncrease = false;
        }
        // 当控制点的x坐标小于或等于起点x坐标时更改标识值
        else if (controlX <= -1 / 2 * width) {
            isIncrease = true;
        }

        // 根据标识值判断当前的控制点x坐标是该加还是减
        controlX = isIncrease ? controlX + 10 : controlX - 10;
        if (controlY >= 0) {
            // 波浪上移
            controlY -= 1;
            waveY -= 1;
        } else {
            // 超出则重置位置
            waveY = 7 / 8F * height;
            controlY = 17 / 16F * height;
        }

        //贝塞尔曲线的生成
        path.moveTo(0, waveY);
        path.cubicTo(controlX / 2, waveY - (controlY - waveY),
                (controlX + width) / 2, controlY, width, waveY);
        path.lineTo(width, height);
        path.lineTo(0, height);
        //进行闭合
        path.close();

        // 以上画贝塞尔曲线代码参考自爱哥博客
        // http://blog.csdn.net/aigestudio/article/details/41960507

        mCanvas.drawBitmap(bitmap, 0, 0, paint);// 画慕课网logo
        paint.setXfermode(porterDuffXfermode);// 设置Xfermode
        mCanvas.drawPath(path, paint);// 画三阶贝塞尔曲线
        paint.setXfermode(null);// 重置Xfermode
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        setMeasuredDimension(
                MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
        // 设置高度款第为logo宽度和高度,实际开发中应该判断MeasureSpec的模式，进行对应的逻辑处理
    }
}
