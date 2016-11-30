package com.midian.moma.itemview;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.midian.moma.R;
import com.midian.moma.model.AdvertListBean;
import com.midian.moma.model.HomeBean;
import com.midian.moma.ui.advert.AdvertDetailActivity;
import com.midian.moma.ui.home.CaseDetailActivity1;
import com.midian.moma.ui.main.IndexMultiItem;
import com.midian.moma.urlconstant.UrlConstant;

import midian.baselib.utils.UIHelper;
import midian.baselib.view.BaseTpl;
import midian.baselib.widget.CircleImageView;


/**
 * 选择案例
 * Created by chu on 2016/1/12.
 */
public class SearchCaseTpl extends BaseTpl<IndexMultiItem> implements View.OnClickListener {

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
    public SearchCaseTpl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SearchCaseTpl(Context context) {
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
        return R.layout.item_search_case_tpl;
    }

    @Override
    public void setBean(IndexMultiItem bean, int position) {
        if (bean.getItemViewType() == 1) {
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
            } else {
                saved_ll.setBackgroundResource(R.drawable.icon_zan_ok_bg);
                like_iv.setBackgroundResource(R.drawable.icon_zan_ok);
                caseType = "2";
            }
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item:
                Bundle bundle = new Bundle();
                if (caseId == null) {
                    return;
                } else {
                    bundle.putString("caseId", caseId);
                    UIHelper.jump(_activity, CaseDetailActivity1.class, bundle);
                }
                break;
        }
    }
}
