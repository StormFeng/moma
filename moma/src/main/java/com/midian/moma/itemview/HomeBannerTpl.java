package com.midian.moma.itemview;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.midian.moma.R;
import com.midian.moma.model.HomeBean.Banner;
import com.midian.moma.ui.advert.AdvertDetailActivity;
import com.midian.moma.ui.home.BannerWebViewActivity;
import com.midian.moma.ui.main.IndexMultiItem;
import com.midian.moma.urlconstant.UrlConstant;

import java.util.ArrayList;
import java.util.List;

import midian.baselib.utils.UIHelper;
import midian.baselib.view.BaseTpl;
import midian.baselib.widget.BannerView;
import midian.baselib.widget.BannerView.OnItemClickListener;

/**
 * 首页banner图模板
 *
 * @author chu
 */
public class HomeBannerTpl extends BaseTpl<IndexMultiItem> implements OnItemClickListener {
    private BannerView bannerView;
    private List<Banner> banners;
    private String ad_id, url;

    public HomeBannerTpl(Context context) {
        super(context);
    }

    public HomeBannerTpl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initView() {
        bannerView = findView(R.id.banner);
        bannerView.setOnItemClickListener(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_home_banner;
    }

    @Override
    public void setBean(IndexMultiItem bean, int position) {
        if (bean.getItemViewType() == 0) {
            banners = bean.banners;
            if (banners != null) {
                ArrayList<String> picUrls = new ArrayList<String>();
                for (int i = 0; i < banners.size(); i++) {
                    picUrls.add(UrlConstant.BASEFILEURL + banners.get(i).getPic_name());
                    url = banners.get(i).getUrl();
                }
                bannerView.config(750, 400, picUrls);
            }
        }

    }

    @Override
    public void onItemClick(View v, int position) {
        Bundle bundle = new Bundle();
        url = banners.get(position).getUrl();
        ad_id = banners.get(position).getAd_id();
        if (TextUtils.isEmpty(url) && TextUtils.isEmpty(ad_id)) {
            UIHelper.t(_activity, "跳转链接为空");
            return;
        }
        if (!TextUtils.isEmpty(ad_id)) {
            ad_id = banners.get(position).getAd_id();
            bundle.putString("id", ad_id);
            UIHelper.jump(_activity, AdvertDetailActivity.class, bundle);
        }else if (!TextUtils.isEmpty(url)) {
            url = banners.get(position).getUrl();
            bundle.putString("url", url);
            UIHelper.jump(_activity, BannerWebViewActivity.class, bundle);
        }


    }

}
