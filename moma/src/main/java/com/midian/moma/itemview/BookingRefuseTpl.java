package com.midian.moma.itemview;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.midian.moma.R;
import com.midian.moma.model.MyBookingListBean.BookingContent;
import com.midian.moma.ui.shopping.OrderDetailActivity;
import com.midian.moma.urlconstant.UrlConstant;

import midian.baselib.utils.UIHelper;
import midian.baselib.view.BaseTpl;

/**
 * 预订-已拒绝
 * 
 * @author chu
 *
 */
public class BookingRefuseTpl extends BaseTpl<BookingContent> implements OnClickListener {
	private ImageView image;
	private TextView title, money, time, status, button;
	private View item;
    private String ad_id, reserve_id;//预订订单id”，
    private int position;
    private String type;
    private TextView type_loc;

	public BookingRefuseTpl(Context context) {
		super(context);
	}

	public BookingRefuseTpl(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void initView() {
		image = findView(R.id.image);
		title = findView(R.id.title);
		money = findView(R.id.money);
		time = findView(R.id.time);
		status = findView(R.id.status);
		button = findView(R.id.button);
		item = findView(R.id.item);
        type_loc = findView(R.id.type_loc);
//		item.setOnClickListener(this);


	}

	@Override
	protected int getLayoutId() {
		return R.layout.item_booking_refuse_tpl;
	}

	@Override
	public void setBean(BookingContent bean, int position) {
        this.position = position;
        //0：全部，1：待审核，2：已通过，3已拒绝
        if (bean == null) {
            return;
        } else {
            if (TextUtils.isEmpty(bean.getPic_thumb_name())) {
                image.setBackgroundResource(R.drawable.icon_home_bg);
            } else {
                ac.setImage(image, UrlConstant.BASEFILEURL + bean.getPic_thumb_name());
            }
            ad_id = bean.getAd_child_id();
            type_loc.setText("类型:"+bean.getType_name()+"；位置："+bean.getPosition_name()+";");
            reserve_id = bean.getReserve_id();
            title.setText(bean.getAd_title());
            money.setText(bean.getPrice());
            time.setText(bean.getStartTime() + " - " + bean.getEndTime());
            if ("3".equals(bean.getStatus())) {
                status.setText("已拒绝");
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item:
                // 1待审核，2已通过,3已拒绝
                if ("1".equals(status)) {
                    type = "5";
                }else if ("2".equals(status)) {
                    type = "6";
                }else if ("3".equals(status)) {
                    type = "7";
                }
                Bundle mBundle = new Bundle();
                mBundle.putString("order_id", reserve_id);
                mBundle.putString("type", type);
                UIHelper.jump(_activity, OrderDetailActivity.class, mBundle);

                break;

        }
    }

}
