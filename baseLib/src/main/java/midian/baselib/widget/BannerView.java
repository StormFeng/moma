package midian.baselib.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.midian.baselib.R;

import java.util.ArrayList;

import midian.baselib.adapter.OnPageChangeAdapter;
import midian.baselib.app.AppContext;

public class BannerView extends LinearLayout {
	private static final int DELAY = 5000;
	private AppContext ac;
	private Context context;
	public FrameLayout rootView;
	public CustomIndicator indicator;
	public BaseLibViewPager pager;

	private ArrayList<ImageView> views = new ArrayList<ImageView>();
	private ArrayList<String> data = new ArrayList<String>();

	private OnItemClickListener onItemClickListener;

	private BannerAdapter adapter;

	private Handler handler;
	private Runnable runnable;

	public interface OnItemClickListener {
		void onItemClick(View v, int position);
	}

	public OnItemClickListener getOnItemClickListener() {
		return onItemClickListener;
	}

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}

	public BannerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public BannerView(Context context) {
		super(context);
		init(context);
	}

	public void setData(ArrayList<String> data) {
		this.data = data;
	}

	private void init(Context context) {
		this.context = context;
		ac = (AppContext) context.getApplicationContext();
		View root = View.inflate(context, R.layout.view_banner, null);
		addView(root);
		rootView = (FrameLayout) root.findViewById(R.id.banner);
		indicator = (CustomIndicator) root.findViewById(R.id.indicator);
		pager = (BaseLibViewPager) root.findViewById(R.id.pager);

	}

	public void clear() {
		BannerView.this.pager.removeAllViews();
	}

	public void config(int w, int h, ArrayList<String> picUrls) {
		ViewGroup.LayoutParams params = BannerView.this.rootView.getLayoutParams();
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		params.height = dm.widthPixels * h / w;
		params.width = dm.widthPixels;
		BannerView.this.rootView.setLayoutParams(params);
		data.clear();
		data.addAll(picUrls);
		initBanner();
	}

	public void initBanner() {
		views.clear();
		for (int i = 0; i < data.size(); i++) {
			final ImageView imageView = (ImageView) View.inflate(context, R.layout.view_banner_item, null);
			final int index = i;
			imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(imageView, index);
                    }
                }
            });
			views.add(imageView);
		}
		if (data.size() > 1) {
			BannerView.this.indicator.initIndicator(context, data.size(), 0);
		} else {
			BannerView.this.indicator.removeAll();
		}
		if (adapter == null) {
			adapter = new BannerAdapter();
			pager.setOffscreenPageLimit(adapter.getCount());
			BannerView.this.pager.setAdapter(adapter);
		} else {
			adapter.notifyDataSetChanged();
		}
		BannerView.this.pager.setOnPageChangeListener(new OnPageChangeAdapter() {
			@Override
			public void onPageSelected(int index) {
				BannerView.this.indicator.changeIndiccator(index);
				if (handler != null && runnable != null) {
					handler.removeCallbacks(runnable);
					handler.postDelayed(runnable, DELAY);
				}
			}
		});
		handler = new Handler();
		runnable = new Runnable() {

			@Override
			public void run() {
				if (adapter.getCount() == 0) {
					return;
				}
				int cur = BannerView.this.pager.getCurrentItem();
				cur = (++cur) % adapter.getCount();
				BannerView.this.pager.setCurrentItem(cur);
				BannerView.this.indicator.changeIndiccator(cur);
				handler.postDelayed(runnable, DELAY);
			}
		};
		if (data.size() > 1) {
			handler.postDelayed(runnable, DELAY);
		}
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		if (handler != null && runnable != null) {
			handler.removeCallbacks(runnable);
		}
	}

	private class BannerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView((View) object);
		}

		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView view = views.get(position);
            ac.imageLoader.displayImage(data.get(position), view);
			container.addView(view);
			return view;
		}
	}

}
