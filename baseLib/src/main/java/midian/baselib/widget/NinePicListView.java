package midian.baselib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.midian.baselib.R;

import java.util.List;

import midian.baselib.app.AppContext;
import midian.baselib.utils.ScreenUtils;

/**
 * Created by Administrator on 2016/3/17.
 */
public class NinePicListView extends FlowLayout {
    public NinePicListView(Context context) {
        super(context);
        init(context);
    }

    private int padding = 0;
    private int imgHeight = 0;
    Context context;
    List<String> pics;
    AppContext ac;
    final int n = 3;

    public NinePicListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context) {
        this.context = context;
        ac = (AppContext) context.getApplicationContext();
        padding = ScreenUtils.dpToPxInt(context, 5);

//        imgHeight = (getMeasuredWidth() - padding * 2) / n;
//        imgHeight=Math.max(0,imgHeight);
        ViewTreeObserver obs=this.getViewTreeObserver();
        obs.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(imgHeight==0){
                imgHeight = (getWidth() - padding * 2) / n;
                imgHeight=Math.max(0,imgHeight);
                initPic(pics);
                }
            }
        });
//        ViewTreeObserver.OnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){});
//        setOrientation(LinearLayout.VERTICAL);
//        setPadding(padding, padding, padding, padding);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width=MeasureSpec.getSize(widthMeasureSpec);
        if(imgHeight==0){
            imgHeight = (width - padding * 2) / n;
            imgHeight=Math.max(0,imgHeight);
            initPic(pics);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void initPic(List<String> pics) {
        if (pics == null || pics.size() == 0) {
            setVisibility(View.GONE);
            return;
        }
        this.pics = pics;
        removeAllViews();
        System.out.println("imgHeight"+imgHeight);
        for (int i = 0; i < pics.size(); i++) {
            String pic = pics.get(i);

            TextView img = new TextView(context);
//            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            RadioGroup.LayoutParams p = new RadioGroup.LayoutParams(imgHeight, imgHeight);
            if (i%3==0) {

            } else {
                p.leftMargin = padding;
            }
            if(i>2){
                p.topMargin = padding;
            }
            addView(img);
//            img.setBackgroundResource(R.drawable.icon_mask);
//            ac.setImage(img, pic);
            img.setBackgroundColor(getResources().getColor(R.color.grey));
            img.setText(pic);
        }

        setVisibility(View.VISIBLE);
    }


}
