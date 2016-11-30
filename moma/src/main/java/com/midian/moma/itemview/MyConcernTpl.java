package com.midian.moma.itemview;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.midian.configlib.ServerConstant;
import com.midian.moma.R;
import com.midian.moma.model.MyCollectsBean.CollectContent;
import com.midian.moma.ui.advert.AdvertDetailActivity;
import com.midian.moma.ui.personal.MyConcernActivity;
import com.midian.moma.utils.AppUtil;

import midian.baselib.api.ApiCallback;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.UIHelper;
import midian.baselib.view.BaseTpl;

/**
 * Created by chu on 2015.11.23.023.
 */
public class MyConcernTpl extends BaseTpl<CollectContent> implements View.OnClickListener, ApiCallback {
    private ImageView image;
    private TextView title_tv, start_time_tv, loc_tv, money_tv;
    private View item, delete_ll;

    private String collect_id;//收藏id
    private String record_id;//被收藏对象id
    private String type;//类型
    private CollectContent bean;
    public static boolean isEdit;
    private int position;

    public MyConcernTpl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyConcernTpl(Context context) {
        super(context);
    }

    @Override
    protected void initView() {
        image = (ImageView) findView(R.id.image);
        title_tv = (TextView) findView(R.id.title_tv);
        start_time_tv = (TextView) findView(R.id.start_time_tv);
        loc_tv = (TextView) findView(R.id.loc_tv);
        money_tv = (TextView) findView(R.id.money_tv);
        item = findView(R.id.item);
        delete_ll = findView(R.id.delete_ll);
        delete_ll.setOnClickListener(this);
        root.setOnClickListener(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_my_concern;
    }


    @Override
    public void setBean(CollectContent bean, int position) {
        this.bean = bean;
        this.position = position;
        if (bean == null) {

        } else {
            collect_id = bean.getCollect_id();//收藏id
            record_id = bean.getRecord_id();//被收藏对象的id
            type = bean.getType();
            if (bean.getPic_thumb_name() == null && bean.getPic_thumb_name().equals("")) {
                ac.setImage(image, R.drawable.icon_bg);
            } else {
                ac.setImage(image, bean.getPic_thumb_name());
            }
            title_tv.setText(bean.getAd_name());
            if (bean.getStart_time() != null && !bean.getStart_time().equals("")) {
                start_time_tv.setText(bean.getStart_time() + " 起");
            }
            if (bean.getMin_Price() != null && !bean.getMin_Price().equals("")) {
                money_tv.setText("¥" + bean.getMin_Price());
            }
            loc_tv.setText("距离" + bean.getDistance());

            if (isEdit) {
                delete_ll.setVisibility(View.VISIBLE);
                root.setEnabled(false);
                delete_ll.setEnabled(true);
            } else {
                delete_ll.setVisibility(View.GONE);
                root.setEnabled(true);
                delete_ll.setEnabled(false);
            }

        }

    }


    @Override
    public void onClick(View view) {
        if (isEdit) {
            AppUtil.getMomaApiClient(ac).cancelCollect(collect_id, this);
        } else {
            Bundle mBundle = new Bundle();
            mBundle.putString("id", record_id);
            UIHelper.jump(_activity, AdvertDetailActivity.class, mBundle);
        }

    }


    public static boolean isEdit() {
        return isEdit;
    }

    public static void setIsEdit(boolean isEdit) {
        MyConcernTpl.isEdit = isEdit;
    }


    @Override
    public void onApiStart(String tag) {
        ((MyConcernActivity) _activity).showLoadingDlg();
    }

    @Override
    public void onApiLoading(long count, long current, String tag) {

    }

    @Override
    public void onApiSuccess(NetResult res, String tag) {
        ((MyConcernActivity) _activity).hideLoadingDlg();
        if (res.isOK()) {
            if ("cancelCollect".equals(tag)) {
                UIHelper.t(_activity, "删除成功!");
                data.remove(position);
                adapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    public void onApiFailure(Throwable t, int errorNo, String strMsg, String tag) {

    }

    @Override
    public void onParseError(String tag) {

    }
}
