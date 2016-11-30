package com.midian.moma.ui.advert.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.midian.moma.R;
import com.midian.moma.app.MAppContext;
import com.midian.moma.model.AdvertListBean;
import com.midian.moma.ui.MainActivity;
import com.midian.moma.ui.advert.AdvertDetailActivity;
import com.midian.moma.ui.main.AdvertFragment;
import com.midian.moma.urlconstant.UrlConstant;
import com.midian.moma.utils.AppUtil;

import java.util.ArrayList;
import java.util.List;

import midian.baselib.api.ApiCallback;
import midian.baselib.app.AppContext;
import midian.baselib.base.BaseFragment;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.ListViewForScrollView;

/**
 * 广告--平面类型fragment
 *
 * @author chu
 */
public class PlaneTypeFragment extends BaseFragment implements OnItemClickListener, ApiCallback {

    private ListViewForScrollView scroll_list;
    private ListAdapter listAdapter;
//    private List<AdvertListBean.Advertisements> advertBean = new ArrayList<AdvertListBean.Advertisements>();
    private int page;
    boolean refresh;
    boolean loadMore;
    private String keywords, type;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_plane_type, null);
        scroll_list = (ListViewForScrollView) v.findViewById(R.id.list);
        scroll_list.setOnItemClickListener(this);
        listAdapter = new ListAdapter();
        scroll_list.setAdapter(listAdapter);
        loadData();
        return v;
    }

    private void loadData() {
        keywords = null;
        String lon = "113.416872134704";// 经度
        String lat = "23.1567751389201";// 纬度
        type = "1";//1：平面类型，2：视频类型
        String cityId = null;
        page = 1;
        try {
//            AppUtil.getMomaApiClient(ac).advertisements(keywords, ac.lon, ac.lat, type, ac.city_id,ac.loc_city_level, page + "", AppContext.PAGE_SIZE, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 下拉刷新时调用的方法
     *
     * @param isRefresh
     */
    public void onRefreshPlane(boolean isRefresh) {
        this.refresh = isRefresh;
//        advertBean.removeAll(advertBean);
        loadData();
    }

    /**
     * 加载更多时调用的方法
     *
     * @param
     */
    public void onLoadMorePlane(boolean isMore) {
        this.loadMore = isMore;
        page++;
        try {
//            AppUtil.getMomaApiClient(ac).advertisements(keywords, ac.lon, ac.lat, type, ac.city_id,ac.loc_city_level, page + "", AppContext.PAGE_SIZE, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onApiStart(String tag) {
        _activity.showLoadingDlg();
    }

    @Override
    public void onApiLoading(long count, long current, String tag) {

    }

    @Override
    public void onApiSuccess(NetResult res, String tag) {
        _activity.hideLoadingDlg();
        if (res.isOK()) {
            AdvertListBean beans = (AdvertListBean) res;
//            advertBean.addAll(beans.getContent().getAdvertisements());
            if (refresh) {
                try {
//                    ((MainActivity) _activity).advertFragment.closeRefresh();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (loadMore) {
//                ((MainActivity) _activity).advertFragment.closeLoadMore();
//                if (beans.getContent().getAdvertisements().size() <= 0) {
                    UIHelper.t(_activity, "没有更多数据了");
                    page--;
                    if (page == 1) {
                        page = 1;
                    }
                }


            listAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onApiFailure(Throwable t, int errorNo, String strMsg, String tag) {
        t.printStackTrace();
        _activity.hideLoadingDlg();
        UIHelper.t(_activity, "网络异常");
    }

    @Override
    public void onParseError(String tag) {
        _activity.hideLoadingDlg();
        UIHelper.t(_activity, "数据解析异常");
    }

    class ListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
//            return advertBean.size();
            return 5;
        }

        @Override
        public Object getItem(int position) {
//            return advertBean.get(position);
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder mHolder;
            if (convertView == null) {
                mHolder = new ViewHolder();
//                LayoutInflater inflater = LayoutInflater.from(_activity);
//                convertView = inflater.inflate(R.layout.item_plane_type, null);

                mHolder.image = (ImageView) convertView.findViewById(R.id.image);
                mHolder.title = (TextView) convertView.findViewById(R.id.title);
                mHolder.sell_status = (TextView) convertView.findViewById(R.id.sell_status);
                mHolder.loc = (TextView) convertView.findViewById(R.id.loc);
                mHolder.money = (TextView) convertView.findViewById(R.id.money);
                convertView.setTag(mHolder);
            }
            mHolder = (ViewHolder) convertView.getTag();

            // 在此渲染数据.settext
            /*AdvertListBean.Advertisements bean = advertBean.get(position);
            if (!TextUtils.isEmpty(bean.getAd_pic_thumb_name())) {
                ac.setImage(mHolder.image, UrlConstant.BASEFILEURL + bean.getAd_pic_thumb_name());
            } else {
                ac.setImage(mHolder.image, R.drawable.default_button);
            }
            mHolder.title.setText(bean.getAd_title());
            if ("1".equals(bean.getAd_status())) {
                mHolder.sell_status.setText("在售");
                mHolder.sell_status.setTextColor(getResources().getColor(R.color.orange_button));
                mHolder.sell_status.setBackgroundResource(R.drawable.orange_round_bg);
            } else if ("2".equals(bean.getAd_status())) {
                mHolder.sell_status.setText("已售");
                mHolder.sell_status.setTextColor(getResources().getColor(R.color.grey));
                mHolder.sell_status.setBackgroundResource(R.drawable.gray_round_bg);
            }
            mHolder.loc.setText("距离" + bean.getDistince());
            mHolder.money.setText("¥"+bean.getPrice());*/

            return convertView;
        }

        public class ViewHolder {
            TextView title, sell_status, loc, money;
            ImageView image;
        }
    }


    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // adapter.notifyDataSetChanged();
        Bundle mBundle = new Bundle();
        //mBundle.putString("ad_id", ad_id);
//        mBundle.putString("id", advertBean.get(arg2).getAd_id());
//        UIHelper.jumpForResult(_activity, AdvertDetailActivity.class, mBundle, 10000);
    }

}
