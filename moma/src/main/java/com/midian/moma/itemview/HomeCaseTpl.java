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
import com.midian.moma.model.HomeBean.Examp;
import com.midian.moma.ui.home.CaseDetailActivity1;
import com.midian.moma.ui.main.IndexMultiItem;
import com.midian.moma.urlconstant.UrlConstant;

import midian.baselib.utils.UIHelper;
import midian.baselib.view.BaseTpl;
import midian.baselib.widget.CircleImageView;

/**
 * 首页精彩案例模板
 *
 * @author chu
 */
public class HomeCaseTpl extends BaseTpl<IndexMultiItem> implements OnClickListener {
    private ImageView image;
    private CircleImageView head;
    private TextView title, name;
    private View item;
    private Examp bean;
    private String caseId;


    public HomeCaseTpl(Context context) {
        super(context);
    }

    public HomeCaseTpl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initView() {
        image = findView(R.id.image);
        head = findView(R.id.head);
        title = findView(R.id.title);
        name = findView(R.id.name);
        item = findView(R.id.item);
        item.setOnClickListener(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_home_casestpl;
    }

    @Override
    public void setBean(IndexMultiItem bean, int position ) {

        this.bean = bean.examps;
        if (bean != null) {
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
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item:
                Bundle mBundle = new Bundle();
                mBundle.putString("caseId", caseId);
                UIHelper.jump(_activity, CaseDetailActivity1.class, mBundle);// 案例详情
                break;
        }
    }

}
