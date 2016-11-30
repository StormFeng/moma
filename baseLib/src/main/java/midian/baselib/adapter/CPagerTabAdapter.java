package midian.baselib.adapter;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by XuYang on 15/4/21.
 */
public class CPagerTabAdapter extends PagerAdapter {
	private ArrayList<View> views;
	private ArrayList<String> titles;

	public CPagerTabAdapter(ArrayList<View> views, ArrayList<String> titles) {
		this.views = views;
		this.titles = titles;
	}

	@Override
	public int getCount() {
		return views.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {

		if(position<views.size())
		container.removeView(views.get(position));
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View view = views.get(position);
		container.addView(view);
		return view;
	}
	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}
	@Override
	public CharSequence getPageTitle(int position) {
		return titles.get(position % titles.size());
	}
}
