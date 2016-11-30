package com.midian.moma.ui.advert;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.midian.configlib.ServerConstant;
import com.midian.login.utils.ObjectAnimatorTools;
import com.midian.login.view.LoginActivity;
import com.midian.maplib.MyPostionActivity;
import com.midian.moma.R;
import com.midian.moma.model.AdvertDetailBean;
import com.midian.moma.model.DiscountBean;
import com.midian.moma.ui.shopping.ConfirmOrderActivity;
import com.midian.moma.urlconstant.UrlConstant;
import com.midian.moma.utils.AppUtil;
import com.midian.moma.utils.CalenderActivity;
import com.midian.moma.utils.EndTimeDialog;

import java.util.ArrayList;
import java.util.List;

import midian.baselib.base.BaseActivity;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BannerView;
import midian.baselib.widget.BaseLibTopbarView;
import midian.baselib.widget.FlowLayout;
import midian.baselib.widget.ListViewForScrollView;

/**
 * 广告位详情
 *
 * @author chu
 */
public class AdvertDetailActivity extends BaseActivity implements EndTimeDialog.ConfirmClickListener {
    private BaseLibTopbarView topbar;
    private BannerView bannerView;
    private List<AdvertDetailBean.Ad_pic> banners;
    private TextView title, times, money;// 标题、 出售状态、发布时间、价格
    private TextView phone, adress, days, start_day, end_day;// 公司电话、地址、天数、开始结束时间
    private View phone_ll, adress_ll, select_day_ll, discount_tv;
    private TextView concern_tv, addShopping, buy;// 关注、加入购物车、购买
    private ListViewForScrollView listView;//限时优惠
    private View concern_ll;//关注的布局view
    private ImageView concern;//关注image
    private WebView webView;
    private String ad_id, start_time, end_time;
    private AdvertDetailBean bean = null;
    private String lon, lat;//经纬度
    private String address;
    private Drawable drawable = null;
    private FlowLayout type_list;
    //    private NinePicListView p_list;
    private FlowLayout loc_list;
    private String ad_child_id;
    private List<DiscountBean.DiscountContent> discount_list = new ArrayList<DiscountBean.DiscountContent>();

    private int setIndex = -1;
    private MyAdapter mAdapter;
    private String fristSeletedTime = null; //选择的开始时间
    private String endSeletedTime = null;//选择的结束时间
    private String collect;
    private String type;
    private int count;//关注数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advert_detail);
        ad_id = mBundle.getString("id");
        loadData();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        AppUtil.getMomaApiClient(ac).advertisementDetail(ad_id, this);//广告位详情接口
    }

    private void initView() {
        topbar = findView(R.id.topbar);
        topbar.setTitle("广告位详情").setLeftImageButton(R.drawable.icon_back, null)
                .setLeftText("返回", UIHelper.finish(_activity)).setRightImageButton(R.drawable.icon_add_shopping, this);
        bannerView = findView(R.id.banner);
        title = findView(R.id.title);
        times = findView(R.id.time);
        money = findView(R.id.money);
        phone_ll = findView(R.id.phone_ll);
        phone = findView(R.id.phone);
        adress_ll = findViewById(R.id.adress_ll);
        adress = findView(R.id.adress);
        days = findView(R.id.days);//购买的总天数
        start_day = findView(R.id.start_day);
        end_day = findView(R.id.end_day);
        select_day_ll = findViewById(R.id.select_day_ll);//选择购买时间的View
        discount_tv = findView(R.id.discount_tv);
        listView = findView(R.id.discount_list);//限时优惠的数据list
        webView = findView(R.id.webView);
        type_list = findView(R.id.type_list);
        loc_list = findView(R.id.loc_list);
        concern_ll = findView(R.id.concern_ll);//关注的View
        concern = findView(R.id.concern);
        concern_tv = findView(R.id.concern_tv);
        addShopping = findView(R.id.addShopping);
        buy = findView(R.id.buy);

//        phone_ll.setOnClickListener(this);
        adress_ll.setOnClickListener(this);
        start_day.setOnClickListener(this);
        end_day.setOnClickListener(this);
        concern_ll.setOnClickListener(this);
        addShopping.setOnClickListener(this);
        buy.setOnClickListener(this);

        start_day.setText("未选择");
        end_day.setText("未选择");
        days.setText("共0月");

        //2：会员，3：销售员
        if ("2".equals(ac.user_type)) {
            type = "1"; //1：普通订单，2：预定订单
        } else if ("3".equals(ac.user_type)) {
            buy.setText("立即预订");
            type = "2";//1：普通订单，2：预定订单
        }


    }

    @Override
    public void onApiStart(String tag) {
        super.onApiStart(tag);
        showLoadingDlg("", true);
    }

    List<AdvertDetailBean.Positions> p_list;

    @Override
    public void onApiSuccess(NetResult res, String tag) {
        super.onApiSuccess(res, tag);
        hideLoadingDlg();
        if (res.isOK()) {
            //添加购物车接口回调
            if ("addShoppingCart".equals(tag)) {
                UIHelper.t(_activity, "加入购物车成功");
                addShopping.setEnabled(false);
                addShopping.setTextColor(getResources().getColor(R.color.grey));
                addShopping.setBackgroundResource(R.drawable.gray_round_bg);
            }
            //关注广告位接口回调
            if ("collect".equals(tag)) {
                UIHelper.t(_activity, "关注成功！");
                collect = "1";
                concern.setBackgroundResource(R.drawable.icon_concern_p);
                count = Integer.parseInt(bean.getContent().getCollect_count()) + 1;
                concern_tv.setText("已关注(" + count + ")");
            }
            //广告位详情回调
            if ("advertisementDetail".equals(tag)) {
                bean = (AdvertDetailBean) res;
                banners = bean.getContent().getAd_pic();
                if (banners != null) {
                    ArrayList<String> picUrls = new ArrayList<String>();
                    for (int i = 0; i < banners.size(); i++) {
                        picUrls.add(UrlConstant.BASEFILEURL + banners.get(i).getAd_pic_name());
                    }
                    bannerView.config(750, 400, picUrls);
                }
                ad_id = bean.getContent().getAd_id();
                title.setText(bean.getContent().getAd_title());
                times.setText(bean.getContent().getAd_min_startTime() + " 起");
                lon = bean.getContent().getLon();
                lat = bean.getContent().getLat();
                address = bean.getContent().getAddress();

                // TODO: 2016/4/6 广告类型 初始化类型
                type_list.removeAllViews();
                if (bean.getContent().getTypes() != null ) {
                    List<AdvertDetailBean.Types> typesBean = bean.getContent().getTypes();
                    //正序添加布局
                    for (int i = 0; i < typesBean.size(); i++) {
                        AdvertDetailBean.Types types = typesBean.get(i);
                        String type_id = types.getType_id();
                        p_list = typesBean.get(i).getPositions();
                        addTypeText(i, types);
                    }
                }
                loc_list.removeAllViews();
                //初始化位置
                if (p_list != null) {
                    for (int i = 0; i < p_list.size(); i++) {
                        AdvertDetailBean.Positions positions = p_list.get(i);
                        addTextView(i, positions);
                    }
                }


                money.setText("¥" + bean.getContent().getAd_min_price());
                adress.setText(bean.getContent().getAddress());
                collect = bean.getContent().getIs_collected();
                count = Integer.parseInt(bean.getContent().getCollect_count());
                if ("1".equals(collect)) {//1：关注，2：未关注
                    concern.setBackgroundResource(R.drawable.icon_concern_p);
                    concern_tv.setText("已关注(" + count + ")");
                } else {
                    concern.setBackgroundResource(R.drawable.icon_concern);
                    concern_tv.setText("关注(" + count + ")");
                }
                String ad_intro = bean.getContent().getAd_intro();
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl(ServerConstant.BASEURL + ad_intro);//"http://m.mdkg.net/"
                webView.setWebViewClient(new WebViewClient());
            }

            //子广告优惠接口回调
            if ("getDiscounts".equals(tag)) {
                DiscountBean discountBean = (DiscountBean) res;
                //优惠信息
                discount_list = discountBean.getContent();
                mAdapter = new MyAdapter();
                listView.setAdapter(mAdapter);
                //如果该子广告位没有优惠信息，则隐藏优惠布局
                if (discount_list.size() == 0) {
                    discount_list.clear();
                    discount_tv.setVisibility(View.GONE);
                    mAdapter.notifyDataSetChanged();
                } else {
                    discount_tv.setVisibility(View.VISIBLE);
                    mAdapter.notifyDataSetChanged();
                }
            }
        } else {
            ac.handleErrorCode(_activity, res.error_code);
            if ("currdate_sold".equals(res.error_code)) {
                finish();
            }
        }
    }

    @Override
    public void onApiFailure(Throwable t, int errorNo, String strMsg, String tag) {
        super.onApiFailure(t, errorNo, strMsg, tag);
        UIHelper.t(_activity, "网络请求错误，请返回重试");
        hideLoadingDlg();
    }

    /**
     * 加载子广告优惠数据
     *
     * @param ad_child_id
     */
    private void setDate(String ad_child_id) {
        AppUtil.getMomaApiClient(ac).getDiscounts(ad_child_id, this);
    }

    int selectId = -1;

    private void refereshState() {
        int count = type_list.getChildCount();
        for (int i = 0; i < count; i++) {
            if (selectId == i) {
                ((ViewGroup) type_list.getChildAt(i)).getChildAt(0).setBackgroundResource(R.drawable.item_ad_type_bg);
            } else {
                ((ViewGroup) type_list.getChildAt(i)).getChildAt(0).setBackgroundResource(R.drawable.bg);
            }
        }
    }

    int loc_selectid = -1;

    private void refereshLoc() {
        int count = loc_list.getChildCount();
        for (int i = 0; i < count; i++) {
            if (loc_selectid == i) {
                ((ViewGroup) loc_list.getChildAt(i)).getChildAt(0).setBackgroundResource(R.drawable.item_ad_type_bg);
            } else {
                ((ViewGroup) loc_list.getChildAt(i)).getChildAt(0).setBackgroundResource(R.drawable.bg);
            }
        }
    }

    /**
     * 添加类型布局
     *
     * @param i
     * @param typesBean
     */
    private void addTypeText(final int i, final AdvertDetailBean.Types typesBean) {
        final TextView tv = (TextView) LayoutInflater.from(this).inflate(R.layout.item_type, type_list, false);
        LinearLayout tl = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.leftMargin = 10;
        params.topMargin = 10;
        params.bottomMargin = 10;
        tv.setText(typesBean.getType_name());
        tl.addView(tv, params);
        //把TextView加入流式布局
        type_list.addView(tl);
        selectId = i;
        refereshState();
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectId = i;
                refereshState();
                start_day.setText("未选择");
                end_day.setText("未选择");
                start_time = null;
                loc_list.removeAllViews();
                for (int i = 0; i < typesBean.getPositions().size(); i++) {
                    AdvertDetailBean.Positions positions = typesBean.getPositions().get(i);
                    addTextView(i, positions);
                }
            }
        });
    }

    /**
     * 添加位置布局
     *
     * @param positionsBean
     */
    private void addTextView(final int i, final AdvertDetailBean.Positions positionsBean) {
        final TextView tv = (TextView) LayoutInflater.from(this).inflate(R.layout.item_type, loc_list, false);
        LinearLayout tl = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.leftMargin = 10;
        params.topMargin = 10;
        params.bottomMargin = 10;
        tv.setText(positionsBean.getPosition_name());
        tl.addView(tv, params);
        loc_list.addView(tl);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("1".equals(positionsBean.getFreeze_status())) {//冻结状态。1：正常，2：冻结
                    loc_selectid = i;
                    refereshLoc();
                    end_day.setText("未选择");
                    start_time = positionsBean.getStart_time();
                    end_time = positionsBean.getEnd_time();
                    start_day.setText(start_time);
                    money.setText("¥" + positionsBean.getPrice());
                    ad_child_id = positionsBean.getAd_child_id();//根据选择位置获取子广告位置id
                    if (!TextUtils.isEmpty(ad_child_id)) {
                        setDate(ad_child_id);//加载子广告位优惠数据
                    }
                } else {
                    UIHelper.t(_activity, "位置已冻结,请选择其它广告位");
                    ((ViewGroup) loc_list.getChildAt(i)).getChildAt(0).setBackgroundResource(R.drawable.bg);
                    return;
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        Bundle mBundle = new Bundle();
        switch (v.getId()) {
            /*case R.id.phone_ll:
                Uri uri = Uri.parse("tel:" + phone.getText().toString());
                Intent call_intent = new Intent(Intent.ACTION_CALL, uri);
                startActivity(call_intent);
                break;*/
            case R.id.adress_ll:
                if (lon == null || lat == null) {
                    return;
                }
                MyPostionActivity.gotoMyPostion(_activity, address, lon, lat);//跳转至地图显示位置
                break;
            case R.id.start_day:
                /*MyCalenderDialog startDialog = new MyCalenderDialog(_activity, R.style.bottom_dialog
                        , bean.getContent().getStart_time()
                        , bean.getContent().getEnd_time()
                        , endSeletedTime
                        , "start", isCrearEndSteletedTime, month_buy_statuses, this);
                startDialog.show();*/
               /* EndTimeDialog dialog = new EndTimeDialog(_activity);
                dialog.show();*/
                if (TextUtils.isEmpty(start_time)) {
                    UIHelper.t(_activity, "请选择广告类型和位置");
                    return;
                }
                /*if (start_time == null || start_time.equals("")) {
                    UIHelper.t(_activity,"请选择广告位置");
                    return;
                }*/
                mBundle.putString("start_time", start_time);
                UIHelper.jumpForResult(_activity, CalenderActivity.class, mBundle, 1007);
                end_day.setText("未选择");
                break;
            case R.id.end_day:
                if (start_day.getText().toString().equals("未选择")) {
                    UIHelper.t(_activity, "请选择开始时间");
                    break;
                }
                /*MyCalenderDialog endDialog = new MyCalenderDialog(_activity, R.style.bottom_dialog
                        , start_time, bean.getContent().getEnd_time(), endSeletedTime
                        , "end", isCrearEndSteletedTime, month_buy_statuses, this);
                endDialog.show();*/
                EndTimeDialog endTimeDialog = new EndTimeDialog(_activity, R.style.bottom_dialog, start_time, end_time, this);
                endTimeDialog.show();
                break;
            case R.id.concern_ll:
                if (ac.isLogin()) {
                    if ("1".equals(collect)) {
                        UIHelper.t(_activity, "已经关注了!");
                        return;
                    }
                    //is_collected;//1关注、2未关注
                    String type = "1";//关注类型1为广告位
                    AppUtil.getMomaApiClient(ac).collect(ad_id, type, this);
                } else {
                    UIHelper.jump(_activity, LoginActivity.class);
                }

                break;
            case R.id.addShopping://加入购物车
                if (ac.isRequireLogin(_activity)) {

                    if (isSubmit()) {
                        //AppUtil.getMomaApiClient(ac).addShoppingCart(bean.getContent().getAd_id(), fristSeletedTime, endSeletedTime, "1", this);
                        AppUtil.getMomaApiClient(ac).addShoppingCart(ad_child_id, start_time, endSeletedTime, "1", this);
                    }
                }
                break;
            case R.id.buy:
                if (ac.isRequireLogin(_activity)) {
                    if (isSubmit()) {

                        mBundle.putString("buyType", "2");//1为购物车传参/2为广告位详情立即购买传参
                        mBundle.putString("ad_id", ad_child_id);
                        mBundle.putString("start_time", start_time);
                        mBundle.putString("end_time", endSeletedTime);
                        mBundle.putString("orderType", type);
                        UIHelper.jump(_activity, ConfirmOrderActivity.class, mBundle);// 生成确认订单接口
                    }
                }
                break;
            case R.id.right_ib://加入购物车
                // UIHelper.t(_activity, "标题购物车");
                if (ac.isRequireLogin(_activity)) {
                   /* Intent intent = new Intent();
                    setResult(RESULT_OK, intent);*/
                    UIHelper.jumpForResult(_activity, ShoppingCartActivity.class, 10087);
                    finish();
                }
                break;

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1007) {
                String m = null;
                String d = null;
                String datas = data.getExtras().getString("times");//获取回返的选择的日期
                int sYear = Integer.parseInt(datas.split("-")[0]);//截取月份
                int sMonth = Integer.parseInt(datas.split("-")[1]);//截取月份
                int sDay = Integer.parseInt(datas.split("-")[2]);//截取天

//                String sYear = sTime.split("-")[0];
                if (sMonth < 10) {
                    m = "0" + sMonth;
                } else {
                    m = sMonth + "";
                }
                if (sDay < 10) {
                    d = "0" + sDay;
                } else {
                    d = sDay + "";
                }
                start_time = sYear + "-" + m + "-" + d;
                start_day.setText(start_time);
            }
        }
    }

    /**
     * 判断条件是否符合购买
     *
     * @return
     */
    private boolean isSubmit() {
        if (start_day.getText().toString().equals("未选择") || end_day.getText().toString().equals("未选择")) {
            UIHelper.t(_activity, "请选择购买时间");
            ObjectAnimatorTools.onVibrationView(select_day_ll);
            return false;
        }
        return true;
    }

    @Override
    public void getEndSeleteTime(String endSeletedTime) {
        this.endSeletedTime = endSeletedTime;
        end_day.setText(endSeletedTime);
    }

    @Override
    public void getCountDay(String count) {
        days.setText("共" + count + "月");//共计**月或天数
    }

    /**
     * 日历的监听
     *//*
    @Override
    public void getFristSeleteTime(String fristSeleteTime) {
        this.fristSeletedTime = fristSeleteTime;
        start_day.setText(fristSeleteTime);
        if (isCrearEndSteletedTime) {
            endSeletedTime = null;
            isCrearEndSteletedTime = false;
            end_day.setText("未选择");//选择日期按钮
        }
    }

    @Override
    public void getEndSeleteTime(String endSeletedTime) {
        this.endSeletedTime = endSeletedTime;
       *//* int year = Integer.parseInt(endSeletedTime.split("-")[0]);
        int month = Integer.parseInt(endSeletedTime.split("-")[1]);
        String m = "0" + endSeletedTime.split("-")[1];////截取日期：：2016：：月：06
        if (month < 10) {
            end_time = year + "-0" + month;
        } else {
            end_time = year + "-" + month;
        }*//*
        end_day.setText(endSeletedTime);
    }*/

    /*@Override
    public void getCountDay(int count) {
        days.setText("共" + count + "月");//共计**月或天数
    }

    @Override
    public void isClearEndSeletedTime(boolean isClear) {
        this.isCrearEndSteletedTime = isClear;
    }*/


    //限时优惠列表adapter
    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return discount_list.size();
        }

        @Override
        public Object getItem(int position) {
            return discount_list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View contetnView, ViewGroup viewGroup) {
            ViewHolde mHolde = null;
            if (contetnView == null) {
                mHolde = new ViewHolde();
                contetnView = LayoutInflater.from(ac).inflate(R.layout.item_advert_detail_setmeals, null);
                mHolde.item_ll = (LinearLayout) contetnView.findViewById(R.id.item_ll);
                mHolde.item_ll.setClickable(true);
                mHolde.select_image = (ImageView) contetnView.findViewById(R.id.select_image);
                mHolde.select_image.setSelected(false);
                mHolde.discount_content = (TextView) contetnView.findViewById(R.id.discount_content);
                mHolde.discount_title = (TextView) contetnView.findViewById(R.id.discount_title);
                contetnView.setTag(mHolde);
            } else {
                mHolde = (ViewHolde) contetnView.getTag();
            }


            //如果界面是select可选择的，设置以下可根据所选position条目来选择select_image选中效果
            /*if (setIndex == -1) {
                setIndex = position;
            }
            if (position == setIndex) {
                mHolde.select_image.setSelected(true);
            } else {
                mHolde.select_image.setSelected(false);
            }*/
//            mHolde.discount_title.setText(discount_list.get(position).getSetmeal_title());


            //获取限时优惠的内容
            mHolde.discount_content.setText(discount_list.get(position).getDiscount_name());
            mHolde.item_ll.setClickable(true);
            setOnclick(mHolde.item_ll, position);
            return contetnView;
        }

        class ViewHolde {
            private LinearLayout item_ll;
            private TextView discount_title;//套餐标题（已去掉）
            private TextView discount_content;//套餐内容
            private ImageView select_image;
        }

        public void setOnclick(LinearLayout item_ll, final int position) {
            item_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setIndex = position;
                    mAdapter.notifyDataSetChanged();
                }
            });
        }
    }
}
