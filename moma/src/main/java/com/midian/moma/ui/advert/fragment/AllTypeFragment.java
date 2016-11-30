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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.midian.moma.R;
import com.midian.moma.app.MAppContext;
import com.midian.moma.model.AdvertListBean;
//import com.midian.moma.model.AdvertListBean.Advertisements;
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
import midian.baselib.shizhefei.view.listviewhelper.ListViewHelper;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.ListViewForScrollView;
import midian.baselib.widget.pulltorefresh.PullToRefreshListView;

/**
 * 广告--全部类型fragment
 *
 * @author chu
 */
public class AllTypeFragment extends BaseFragment implements OnItemClickListener, ApiCallback {
    private ListView scroll_list;
    private ListAdapter listAdapter;
//    private List<Advertisements> advertBean = new ArrayList<Advertisements>();
    private String ad_id;
    private String keywords, type;
    private int page;
    private boolean refresh;
    private boolean loadMore;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_all_type, null);
        initView(v);
        return v;
    }

    private void initView(View v) {
            scroll_list = (ListViewForScrollView) v.findViewById(R.id.scroll_list);

    //        mPullToRefreshListView = (PullToRefreshListView) v.findViewById(R.id.pullToRefreshListView);
    //        scroll_list=mPullToRefreshListView.getRefreshableView();
            scroll_list.setOnItemClickListener(this);
            listAdapter = new ListAdapter();
            scroll_list.setAdapter(listAdapter);
        loadData();


    }


    /**
     * 正常状态下加载的方法
     */
    private void loadData() {
        keywords = null;
        String lon = "113.416872134704";// 经度
        String lat = "23.1567751389201";// 纬度
        type = null;//1：平面类型，2：视频类型
        String cityId = null;
        page = 1;

        System.out.println("loadData:::全部类型Fragment::::");
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
    public void onRefreshAll(boolean isRefresh) {
        this.refresh = isRefresh;
//        advertBean.removeAll(advertBean);
        loadData();
    }

    /**
     * 加载更多时调用的方法
     *
     * @param
     */
    public void onLoadMore(boolean isMore) {
        this.loadMore = isMore;
        page++;
        try {
//            AppUtil.getMomaApiClient(ac).advertisements(keywords, ac.lon, ac.lat, type, ac.city_id, ac.loc_city_level,page + "", AppContext.PAGE_SIZE, this);
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



            measureHeight(scroll_list);
            listAdapter.notifyDataSetChanged();
        }

    }

    private int measureHeight(ListView mListView) {
        mListView.setFocusable(false);
        mListView.setFocusableInTouchMode(false);
        // get ListView adapter
        android.widget.ListAdapter adapter = mListView.getAdapter();
        if (null == adapter) {
            return 0;
        }

        int totalHeight = 0;

        for (int i = 0, len = adapter.getCount(); i < len; i++) {
            View item = adapter.getView(i, null, mListView);
            if (null == item) continue;
            // measure each item width and height
            item.measure(0, 0);
            // calculate all height
            totalHeight += item.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = mListView.getLayoutParams();

        if (null == params) {
            params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        // calculate ListView height
        params.height = totalHeight + (mListView.getDividerHeight() * (adapter.getCount() - 1));

        mListView.setLayoutParams(params);

        return params.height;
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
                LayoutInflater inflater = LayoutInflater.from(_activity);
                convertView = inflater.inflate(R.layout.item_all_type, null);

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
            ad_id = bean.getAd_id();
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // adapter.notifyDataSetChanged();
        Bundle mBundle = new Bundle();
        //mBundle.putString("ad_id", ad_id);
//        mBundle.putString("id", advertBean.get(position).getAd_id());
        UIHelper.jumpForResult(_activity, AdvertDetailActivity.class, mBundle, 10000);

    }

}
