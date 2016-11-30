package com.midian.moma.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.annotation.IntegerRes;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.midian.moma.R;
import com.midian.moma.model.AdvertDetailBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import midian.baselib.utils.FDDataUtils;
import midian.baselib.utils.UIHelper;

/**
 * 广告位详情选择月份对话框
 * Created by Administrator on 2015/12/8.
 */
public class MyCalenderDialog extends Dialog implements OnClickListener {
    private Context context;
    private ViewPager viewPager;
    private MyAdapter adapter; //页面适配器
    private TextView year_tv;
    private ArrayList<View> viewArrayList = new ArrayList<View>(); //子页面 集合
    private HashMap<Integer, Integer> calendarMap = new HashMap<Integer, Integer>(); //月份子集合
    private ArrayList<HashMap<Integer, Integer>> list = new ArrayList<HashMap<Integer, Integer>>(); //月份集合
    //private int fristPosition = -1; //第一次点击的位置
//    private int firstSeletedYer = 0; //第一次点击记录的年份
//    private int firstSeletedMonth = 0; //第一次点击记录的月份
    private int endSeletedYer = 0;//第二次点击记录的年份
    private int endSeleteMont = 0;//第二次点击记录的月份
    private int currenYear = 0;  //当前子页年份
    private ArrayList<Integer> yearList = new ArrayList<Integer>(); //年份集合
    private int calendaryear;
    private ArrayList<GridViewApater> gridViewApaterArrayList = new ArrayList<GridViewApater>();
    private int index = 0;

    //    private String fristSeletedTime = null; //选择的开始时间
    private String endSeletedTime = null;//选择的结束时间
    private String startTime = null;  //广告的开始时间
    private int startYear = 0;  //广告开始的年份
    private int startMonth = 0; //广告开始的月份
    private String endTime = null;  // 广告的结束时间
    private int endYear = 0;   //广告结束的年份
    private int endMonth = 0;  //广告结束的月份
    private String flg = null; // 标志 开始（start） 还是结束（end）
    private int size = 0; //日历卡数
    private boolean isClearEndSeletedTime = false; //清除选择的结束时间

    private int sysYear; //系统当前年份
    private int sysMonth; //系统当前月份

    private int currentCar = -1; //当前页
    private int sYear;//开始时间的年份
    private int sMonth;//开始时间的月份
    private ArrayList<AdvertDetailBean.Month_buy_status> month_buy_statuses = new ArrayList<AdvertDetailBean.Month_buy_status>(); // 销售情况
    private CalendarListenner calendarListenner;

    /**
     * 日历监听
     */
    public interface CalendarListenner {
        void getFristSeleteTime(String fristSeleteTime); // 选择开始的时间

        void getEndSeleteTime(String endSeletedTime);// 选择结束的时间

        void getCountDay(int count);// 选择的月数

        void isClearEndSeletedTime(boolean isClear);
    }

    public MyCalenderDialog(Context context) {
        super(context);
        init(context);
    }


    public MyCalenderDialog(Context context, int theme, String startTime, String endTime, String endSeletedTime, String flg, boolean isClearEndSeletedTime, ArrayList<AdvertDetailBean.Month_buy_status> month_buy_statuses, CalendarListenner calendarListenner) {
        super(context, theme);
        this.month_buy_statuses = month_buy_statuses;
        this.isClearEndSeletedTime = isClearEndSeletedTime;
        this.startTime = startTime;
        this.endTime = endTime;
        //this.fristSeletedTime = fristSeletedTime;
        this.endSeletedTime = endSeletedTime;
        this.flg = flg;
        this.calendarListenner = calendarListenner;
        init(context);
    }

    protected MyCalenderDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        Window w = this.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER_VERTICAL;
        w.setAttributes(lp);
        this.setCanceledOnTouchOutside(true);
        View contentView = View.inflate(context, R.layout.view_calendar, null);
        this.setContentView(contentView);
        this.viewPager = (ViewPager) contentView.findViewById(R.id.viewPager);
        this.year_tv = (TextView) contentView.findViewById(R.id.year);

        //初始化广告开始结束时间
        initStartTime(startTime, endTime);
        //初始化 选择开始结束时间
        inintSeleteDate(startTime, endSeletedTime);
        //初始化日历开始年份
        currenYear = startYear;
        this.year_tv.setText(currenYear + "年");

        //初始化日历月份
        initMonthDate();
        //设置viewpager数据
        setViewPagerDate();


        this.adapter = new MyAdapter();
        this.viewPager.setAdapter(this.adapter);
        this.viewPager.setOffscreenPageLimit(0);


        /**
         *
         * viwePager 滑动监听
         *
         */
        this.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                index = position;
                currenYear = yearList.get(position);
                year_tv.setText(yearList.get(position) + "年");
                gridViewApaterArrayList.get(index).setYear(yearList.get(position));
                gridViewApaterArrayList.get(position).notifyDataSetChanged();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //设置选页面
        setCurrentCar();
        if (0 <= currentCar && currentCar < size) {
            viewPager.setCurrentItem(currentCar);
        }
    }

    /**
     * 初始化ViewPager 数据
     */
    public void setViewPagerDate() {
        final Calendar calendar = Calendar.getInstance();
        this.sysYear = calendar.get(Calendar.YEAR);
        this.sysMonth = calendar.get(Calendar.MONTH) + 1;

        for (int i = 0; i < size; i++) {
            yearList.add(startYear + i);
            View view = LayoutInflater.from(context).inflate(R.layout.view_calendar_gridview, null);
            GridView gridView = (GridView) view.findViewById(R.id.calendarGridView);
            GridViewApater gridViewApater = new GridViewApater(startYear + i);
            gridView.setAdapter(gridViewApater);


            /**
             *点击事件
             *
             */
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    if (getStatus(yearList.get(index), position + 1) == 2) {
                        UIHelper.t(context, "已售");
                        return;
                    }
                    if (isOverTime(yearList.get(index), position + 1)) {
                        UIHelper.t(context, "已过期");
                        return;
                    }
                    if (isNotSeleted(yearList.get(index), position + 1)) {
                        UIHelper.t(context, "不可选");
                        return;
                    }

                   /* if (flg.equals("start")) {
                        if (isClearEndSeletedTime) {
                            endSeletedYer = 0;
                            endSeleteMont = 0;
                        }
                        fristPosition = position;
                        firstSeletedMonth = 0;
                        firstSeletedYer = 0;
                        firstSeletedMonth = position + 1;
                        firstSeletedYer = yearList.get(index);
                        calendarListenner.getFristSeleteTime(firstSeletedYer + "-" + firstSeletedMonth);
                    } else*/
                    if (flg.equals("end")) {
                        if (yearList.get(index) == startYear ) {
                            if ((position + 1) >= startMonth&&currenYear>=sysYear) {
                                if (isContinuous(startYear, startMonth, yearList.get(index), position + 1)) {
                                    return;
                                }
                                endSeleteMont = position + 1;
                                endSeletedYer = yearList.get(index);
//                                calendarListenner.getCountDay(getDay(sYear, sMonth, endSeletedYer, endSeleteMont));
                                calendarListenner.getCountDay(getMonth(endSeletedYer, endSeleteMont));//获得月分的
                                calendarListenner.getEndSeleteTime(endSeletedYer + "-" + endSeleteMont);
                                calendarListenner.isClearEndSeletedTime(true);
                            } else {
                                UIHelper.t(context, "请选择大于等于开始月份");
                                return;
                            }
                        } else if (yearList.get(index) > startYear) {
                            if (isContinuous(startYear, startMonth, yearList.get(index), position + 1)) {
                                return;
                            }
                            endSeleteMont = position + 1;
                            endSeletedYer = yearList.get(index);
//                            calendarListenner.getCountDay(getDay(sYear, sMonth, endSeletedYer, endSeleteMont));
                            calendarListenner.getCountDay(getMonth(endSeletedYer, endSeleteMont));
                            calendarListenner.getEndSeleteTime(endSeletedYer + "-" + endSeleteMont);
                            calendarListenner.isClearEndSeletedTime(true);
                        } else {
                            UIHelper.t(context, "请选择大于等于开始时间");
                            return;
                        }

                    }
                    gridViewApaterArrayList.get(index).setYear(currenYear);
                    gridViewApaterArrayList.get(index).notifyDataSetChanged();
                    dismiss();
                }
            });

            gridViewApaterArrayList.add(gridViewApater);
            viewArrayList.add(view);
        }
    }

    /**
     * 初始化月份数据
     */
    public void initMonthDate() {
        for (int j = 0; j < 12; j++) {

            calendarMap = new HashMap<Integer, Integer>();
            calendarMap.put(j, j + 1);
            list.add(calendarMap);
        }

    }

    @Override
    public void onClick(View view) {

    }

    /**
     * 月份适配器
     * GridViewApater
     */
    class GridViewApater extends BaseAdapter {

        private int year;

        public GridViewApater(int years) {
            this.year = years;
        }

        public void setYear(int year) {
            this.year = year;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View contentView, ViewGroup viewGroup) {
            ViewHolde viewHolde = null;
            if (contentView == null) {
                viewHolde = new ViewHolde();
                contentView = LayoutInflater.from(context).inflate(R.layout.view_calendar_item, null);
                viewHolde.month_tv = (TextView) contentView.findViewById(R.id.month_tv);
                viewHolde.status_tv = (TextView) contentView.findViewById(R.id.status_tv);
                viewHolde.calendar_item_ll = (LinearLayout) contentView.findViewById(R.id.calendar_item_ll);
                contentView.setTag(viewHolde);
            } else {
                viewHolde = (ViewHolde) contentView.getTag();
            }
            contentView.setEnabled(true);
            viewHolde.month_tv.setText(list.get(position).get(position) + "月");
            viewHolde.status_tv.setText("在售");
            /*viewHolde.month_tv.setTextColor(Color.BLACK);
            viewHolde.status_tv.setTextColor(Color.parseColor("#FF3333"));
            viewHolde.calendar_item_ll.setBackgroundResource(R.drawable.bg_calendar_isshop);*/
            setDate(viewHolde.month_tv, viewHolde.status_tv, Color.BLACK, Color.parseColor("#FF3333"), viewHolde.calendar_item_ll, R.drawable.bg_calendar_isshop);


            //设置开始购买时间以前的月份为不可选状态
            if (isNotSeleted(this.year, list.get(position).get(position))) {
                viewHolde.status_tv.setText("不可选");
                /*viewHolde.month_tv.setTextColor(Color.BLACK);
                viewHolde.status_tv.setTextColor(Color.BLACK);
                viewHolde.calendar_item_ll.setBackgroundResource(R.drawable.bg_calendar_isnotsel);*/
                setDate(viewHolde.month_tv, viewHolde.status_tv, Color.BLACK, Color.BLACK, viewHolde.calendar_item_ll, R.drawable.bg_calendar_isnotsel);
            }

            //给当前月设置明显标志
            if (isCurrnetDate(this.year, list.get(position).get(position))) {
                viewHolde.status_tv.setText("当前月");
                setDate(viewHolde.month_tv, viewHolde.status_tv, Color.BLACK, Color.BLACK, viewHolde.calendar_item_ll, R.drawable.bg_calendar_isnotsel);
                //起售月份同款背景
                //setDate(viewHolde.month_tv, viewHolde.status_tv, Color.parseColor("#FFFF00"), Color.parseColor("#FFFF00"), viewHolde.calendar_item_ll, R.drawable.bg_calendar_isseleted);
            }


            int stuta = getStatus(this.year, position + 1);
            if (stuta == 1) {
                viewHolde.status_tv.setText("在售");
                setDate(viewHolde.month_tv, viewHolde.status_tv, Color.BLACK, Color.parseColor("#FF3333"), viewHolde.calendar_item_ll, R.drawable.bg_calendar_isshop);
            } else if (stuta == 2) {
                viewHolde.status_tv.setText("已售");
                setDate(viewHolde.month_tv, viewHolde.status_tv, Color.GRAY, Color.parseColor("#CC9999"), viewHolde.calendar_item_ll, R.drawable.bg_calendar_overtime);
            }
            if (isOverTime(this.year, list.get(position).get(position))) {
                viewHolde.status_tv.setText("已过期");
                /*viewHolde.month_tv.setTextColor(Color.GRAY);
                viewHolde.status_tv.setTextColor(Color.GRAY);
                viewHolde.calendar_item_ll.setBackgroundResource(R.drawable.bg_calendar_overtime);*/
                setDate(viewHolde.month_tv, viewHolde.status_tv, Color.GRAY, Color.GRAY, viewHolde.calendar_item_ll, R.drawable.bg_calendar_overtime);
            }

            //用开始年月与当前年月判断，设置开始时间的月份
            sYear = Integer.parseInt(startTime.split("-")[0]);
            sMonth = Integer.parseInt(startTime.split("-")[1]);
            if (sMonth == (position + 1) &&  sYear == this.year) {//
                viewHolde.status_tv.setText("起售月份");
                /*viewHolde.month_tv.setTextColor(Color.parseColor("#FFFF00"));
                viewHolde.status_tv.setTextColor(Color.parseColor("#FFFF00"));
                viewHolde.calendar_item_ll.setBackgroundResource(R.drawable.bg_calendar_isseleted);*/
                setDate(viewHolde.month_tv, viewHolde.status_tv, Color.parseColor("#FFFF00"), Color.parseColor("#FFFF00"), viewHolde.calendar_item_ll, R.drawable.bg_calendar_isseleted);
            }

            if (isSeleted(this.year, list.get(position).get(position))) {
                viewHolde.status_tv.setText("已选");
               /* viewHolde.month_tv.setTextColor(Color.parseColor("#FFFF00"));
                viewHolde.status_tv.setTextColor(Color.parseColor("#FFFF00"));
                viewHolde.calendar_item_ll.setBackgroundResource(R.drawable.bg_calendar_isseleted);*/
                setDate(viewHolde.month_tv, viewHolde.status_tv, Color.parseColor("#FFFF00"), Color.parseColor("#FFFF00"), viewHolde.calendar_item_ll, R.drawable.bg_calendar_isseleted);
            }
            return contentView;
        }

        /**
         * 改变状态颜色
         *
         * @param month_tv
         * @param status_tv
         * @param month_tv_color
         * @param status_tv_color
         * @param calendar_item_ll
         * @param drawable
         */
        private void setDate(TextView month_tv, TextView status_tv, int month_tv_color, int status_tv_color, LinearLayout calendar_item_ll, int drawable) {
            month_tv.setTextColor(month_tv_color);
            status_tv.setTextColor(status_tv_color);
            calendar_item_ll.setBackgroundResource(drawable);
        }

        class ViewHolde {
            private TextView month_tv;
            private TextView status_tv;
            private LinearLayout calendar_item_ll;
        }
    }

    /**
     * 判断已选
     * 以最后选择的月份及所在年份与开始时间的月份及年份对比，判断已选
     *
     * @param year
     * @param month
     * @return
     */
    public boolean isSeleted(int year, int month) {

        if (endSeletedYer >= sYear && sYear > 0) {
            if (endSeletedYer == sYear) {
                if (year == sYear) {
                    if (month >= sMonth && month <= endSeleteMont) {
                        return true;
                    }
                }
            } else if (endSeletedYer > sYear) {
                if (year == sYear) {
                    if (month >= sMonth) {
                        return true;
                    }
                } else if (year > sYear) {
                    if (year < endSeletedYer) {
                        return true;
                    } else if (year == endSeletedYer) {
                        if (month <= endSeleteMont) {
                            return true;
                        }
                    }
                }
            }
        }


        return false;
    }

    /**
     * 当前月份
     *
     * @param year
     * @param month
     * @return
     */
    public boolean isCurrnetDate(int year, int month) {
        if (year == this.sysYear && this.sysMonth == month)
            return true;
        return false;
    }

    /**
     * 标志过期日期
     *
     * @param year
     * @param month
     * @return
     */
    public boolean isOverTime(int year, int month) {
        if (year == this.sysYear && month < this.sysMonth)
            return true;
        return false;
    }

    /**
     * 设置不可以选
     *
     * @param year
     * @param month
     * @return
     */
    public boolean isNotSeleted(int year, int month) {
        if (startYear > 0 && endYear > 0) {
            if (year < startYear) {
                return true;
            } else if (year == sysYear) {
                if (month < startMonth) {
                    return true;
                }
            }

            if (year == endYear) {
                if (month > endMonth) {
                    return true;
                }
            } else if (year > endYear) {
                return true;
            }
        }
        return false;
    }


    /**
     * 销售状态
     *
     * @param year
     * @param month
     * @return
     */
    public int getStatus(int year, int month) {
        int stutas = -1;
        try {
            for (int i = 0; i < month_buy_statuses.size(); i++) {
                String[] str = month_buy_statuses.get(i).getMonth().split("-");
                if (year == Integer.parseInt(str[0]) && month == Integer.parseInt(str[1])) {
                    return Integer.parseInt(month_buy_statuses.get(i).getStatus());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stutas;
    }

    /**
     * 判断是否连续选择日期
     *
     * @param firstSeletedYer
     * @param firstSeletedMonth
     * @param endSeletedYer
     * @param endSeleteMont
     * @return
     */
    public boolean isContinuous(int firstSeletedYer, int firstSeletedMonth, int endSeletedYer, int endSeleteMont) {
        try {
            for (int i = 0; i < month_buy_statuses.size(); i++) {
                if (Integer.parseInt(month_buy_statuses.get(i).getStatus()) == 2) {
                    String[] str = month_buy_statuses.get(i).getMonth().split("-");
                    int cYear = Integer.parseInt(str[0]);
                    int cMonth = Integer.parseInt(str[1]);
                    if (endSeletedYer == firstSeletedYer) {
                        if (firstSeletedYer == cYear && (firstSeletedMonth < cMonth && cMonth < endSeleteMont)) {
                            dialog(month_buy_statuses.get(i).getMonth());
                            return true;
                        }
                    } else if (endSeletedYer > firstSeletedYer) {
                        if (firstSeletedYer == cYear) {
                            if (cMonth > firstSeletedMonth) {
                                dialog(month_buy_statuses.get(i).getMonth());
                                return true;
                            }
                        } else if (cYear > firstSeletedYer) {
                            if (cYear < endSeletedYer) {
                                dialog(month_buy_statuses.get(i).getMonth());
                                return true;
                            } else if (cYear == endSeletedYer) {
                                if (endSeleteMont > cMonth) {
                                    dialog(month_buy_statuses.get(i).getMonth());
                                    return true;
                                }
                            }
                        }
                    }


                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public void dialog(String str) {
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        dialog.getWindow().setContentView(R.layout.view_calendar_dialog);
        TextView textView = (TextView) dialog.getWindow().findViewById(R.id.dialog_tv);
        textView.setText("不能包含于" + str);


    }

    /**
     * 适配器
     * viewPager adapter
     */
    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return viewArrayList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewArrayList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewArrayList.get(position), 0);
            return viewArrayList.get(position);
        }
    }

    /**
     * 初始化广告开始 和结束的日期
     *
     * @param startTime
     * @param endTime
     */
    public void initStartTime(String startTime, String endTime) {
        if (startTime != null && endTime != null) {
            try {
                startYear = Integer.parseInt(startTime.split("-")[0]);
                startMonth = Integer.parseInt(startTime.split("-")[1]);//开始月份
                endYear = Integer.parseInt(endTime.split("-")[0]);
                endMonth = Integer.parseInt(endTime.split("-")[1]);
                if ((endYear - startYear) < 0)
                    return;
                size = endYear - startYear + 1;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 初始化选择的日期
     *
     * @param startTime
     * @param endSeletedTime
     */
    public void inintSeleteDate(String startTime, String endSeletedTime) {
        if (startTime != null) {
            try {
                startYear = Integer.parseInt(startTime.split("-")[0]);
                startMonth = Integer.parseInt(startTime.split("-")[1]);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        if (endSeletedTime != null) {
            try {
                endSeletedYer = Integer.parseInt(endSeletedTime.split("-")[0]);
                endSeleteMont = Integer.parseInt(endSeletedTime.split("-")[1]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 定位到已选选项卡
     */
    public void setCurrentCar() {
        if (startYear != 0) {
            for (int i = 0; i < size; i++) {
                if (startYear == yearList.get(i)) {
                    currentCar = i;
                }

            }
        } else {
            for (int i = 0; i < size; i++) {
                if (sysYear == yearList.get(i)) {
                    currentCar = i;
                }

            }
        }
    }


    /**
     * 得到二个日期间的间隔天数
     * 计算选择的月数
     *
     * @param endSeletedYer
     * @param endSeleteMont
     * @return
     */
    public int getMonth(int endSeletedYer, int endSeleteMont) {
        if (endSeletedYer != 0 && endSeleteMont != 0) {
            String dateStart = sYear + "-" + sMonth + "-" + monthDay(sYear,sMonth);//
            String dateEnd = endSeletedYer + "-" + endSeleteMont + "-" + monthDay(endYear,endMonth);//getDaysInMonth(endSeletedYer, endSeleteMont)
            SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
            long day = 0;
            long d = 0;
            try {
                java.util.Date date = myFormatter.parse(dateStart);
                java.util.Date mydate = myFormatter.parse(dateEnd);

                day = (mydate.getTime() - date.getTime()) / (24 * 60 * 60 * 1000);
                System.out.println("date开始时间:::" + date);
                System.out.println("mydate结束时间:::" + mydate);
                System.out.println("day时间差:::" + day);
                d = (day / 30) + 1;
                System.out.println("月数:::" + d);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Integer.parseInt(String.valueOf(d));
        }
        return 0;
    }


    /**
     * 计算天数
     *
     * @param endSeletedYer
     * @param endSeleteMont
     * @return
     */

    public int getDay(int sYear, int sMonth, int endSeletedYer, int endSeleteMont) {
        if (sYear != 0 && sMonth != 0 && endSeletedYer != 0 && endSeleteMont != 0) {
            try {
                String dateStart = sYear + "-" + sMonth + "-" + 1;
                String dateEnd = endSeletedYer + "-" + endSeleteMont + "-" + monthDay(endSeletedYer, endSeleteMont);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar cal = Calendar.getInstance();
                cal.setTime(sdf.parse(dateStart));
                long time1 = cal.getTimeInMillis();//
                cal.setTime(sdf.parse(dateEnd));
                long time2 = cal.getTimeInMillis();
                long between_days = (time2 - time1) / (1000 * 3600 * 24) + 1;
                return Integer.parseInt(String.valueOf(between_days));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return 0;
    }

    /**
     * 根据不同的月份来获取每个月的天数
     *
     * @param month
     * @param year
     * @return
     */
    public static int getDaysInMonth(int year, int month) {
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.MARCH:
            case Calendar.MAY:
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.OCTOBER:
            case Calendar.DECEMBER:
                return 31;
            case Calendar.APRIL:
            case Calendar.JUNE:
            case Calendar.SEPTEMBER:
            case Calendar.NOVEMBER:
                return 30;
            case Calendar.FEBRUARY:
                return ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) ? 29 : 28;
            default:
                throw new IllegalArgumentException("Invalid Month");
        }
    }


    /**
     * 计算月的天数
     *
     * @param year
     * @param month
     * @return
     */
    public int monthDay(int year, int month) {
        boolean isRunnian = false;
        if ((year % 4 == 0 && year % 100 != 0)
                || year % 400 == 0) {
            isRunnian = true; // 润年
        }
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                if (isRunnian) {
                    return 29;
                }
                return 28;
        }
        return 0;

    }


}
