package com.midian.moma.itemview;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.midian.moma.R;
import com.midian.moma.model.HomeBean;
import com.midian.moma.ui.MainActivity;
import com.midian.moma.ui.home.CaseDetailActivity1;
import com.midian.moma.ui.home.HomeCaseFragment;
import com.midian.moma.ui.main.IndexMultiItem;
import com.midian.moma.urlconstant.UrlConstant;
import com.midian.moma.utils.AppUtil;

import midian.baselib.api.ApiCallback;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.FDDataUtils;
import midian.baselib.utils.UIHelper;
import midian.baselib.view.BaseTpl;
import midian.baselib.widget.CircleImageView;

/**
 * Created by chu on 2016/1/26.
 */
public class HomeCaseListTpl extends BaseTpl<IndexMultiItem> implements View.OnClickListener, ApiCallback {
    private ImageView image;
    private CircleImageView head;
    private TextView title, name;
    private View item, no_item;
    private String caseId;
    private View saved_ll;
    private ImageView like_iv;
    private TextView like_conunt;
    private String caseType;
    //    private String lideCount;
    private int count;

    public HomeCaseListTpl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HomeCaseListTpl(Context context) {
        super(context);
    }

    @Override
    protected void initView() {
        image = findView(R.id.image);
        head = findView(R.id.head);
        title = findView(R.id.title);
        name = findView(R.id.name);
        item = findView(R.id.item);
        no_item = findView(R.id.no_item);
        saved_ll = findView(R.id.saved_ll);
        like_iv = findView(R.id.like_iv);
        like_conunt = findView(R.id.like_conunt);
        saved_ll.setOnClickListener(this);
        item.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_home_case_list_tpl;
    }
    IndexMultiItem bean;
    @Override
    public void setBean(IndexMultiItem bean, int position) {
        this.bean = bean;
        if (bean.getItemViewType() == 1) {
            if (bean.examps != null) {
                item.setVisibility(View.VISIBLE);
                no_item.setVisibility(View.GONE);

                if (TextUtils.isEmpty(bean.examps.getCover_pic_thumb_name())) {
                    ac.setImage(image, R.drawable.default_button);
                } else {
                    ac.setImage(image, bean.examps.getCover_pic_thumb_name());
                }

                if (!TextUtils.isEmpty(bean.examps.getServe_pic_thumb_name())) {
                    ac.setImage(head, UrlConstant.BASEFILEURL + bean.examps.getServe_pic_thumb_name());
                } else {
                    head.setBackgroundResource(R.drawable.head1);
                }
                caseId = bean.examps.getCase_id();
                title.setText(bean.examps.getCase_title());
                name.setText(bean.examps.getCase_serve());
                String isLike = bean.examps.getIs_like();
                count = Integer.parseInt(bean.examps.getLike_count());
                like_conunt.setText(count + "");
                if ("2".equals(isLike)) {
                    saved_ll.setBackgroundResource(R.drawable.icon_zan_n_bg);//未点赞背景
                    like_iv.setBackgroundResource(R.drawable.icon_zan_n);//未点赞红心
                    caseType = "1";
                    saved_ll.setClickable(true);
                } else {
                    saved_ll.setBackgroundResource(R.drawable.icon_zan_ok_bg);
                    like_iv.setBackgroundResource(R.drawable.icon_zan_ok);
                    caseType = "2";
                    saved_ll.setClickable(false);
                }
            } else {
                item.setVisibility(View.GONE);
                no_item.setVisibility(View.VISIBLE);//暂时没有案例
            }
        }
    }

    @Override
    public void onClick(View v) {
        Bundle mBundle = new Bundle();
        switch (v.getId()) {
            case R.id.saved_ll:
                if (!ac.isRequireLogin(_activity)) {
                    return;
                }
                AppUtil.getMomaApiClient(ac).opCaseLike(caseId, caseType, this);
                break;
            case R.id.item:
                if (TextUtils.isEmpty(caseId)) {
                    UIHelper.t(_activity, "id为空,请重新进入app");
                    return;
                } else {
                    mBundle.putString("caseId", caseId);
                    UIHelper.jumpForResult(_activity, CaseDetailActivity1.class, mBundle,10088);// 案例详情
                }
                break;
        }

    }

    @Override
    public void onApiStart(String tag) {
//        ((MainActivity) _activity).showLoadingDlg();
    }

    @Override
    public void onApiLoading(long count, long current, String tag) {

    }

    @Override
    public void onApiSuccess(NetResult res, String tag) {
        ((MainActivity) _activity).hideLoadingDlg();
        if (res.isOK()) {
            if ("1".equals(caseType)) {
                caseType = "2";
                bean.examps.setIs_like("1");
                count = FDDataUtils.getInteger(bean.examps.getLike_count()) + 1;
                adapter.notifyDataSetChanged();
            } else {
                caseType = "1";
                bean.examps.setIs_like("2");
                count = FDDataUtils.getInteger(bean.examps.getLike_count()) - 1;
                adapter.notifyDataSetChanged();
            }
            bean.examps.setLike_count(count + "");
        } else {
            ac.handleErrorCode(_activity, res.error_code);
        }

    }

    @Override
    public void onApiFailure(Throwable t, int errorNo, String strMsg, String tag) {

    }

    @Override
    public void onParseError(String tag) {

    }
}
