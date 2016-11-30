package com.midian.moma.datasource;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import com.midian.moma.R;
import com.midian.moma.utils.RoundProgressBar;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

import midian.baselib.app.AppContext;
import midian.baselib.bean.NetResult;
import midian.baselib.widget.uk.co.senab.photoview.PhotoView;
import midian.baselib.widget.uk.co.senab.photoview.PhotoViewAttacher;

public class ImageViewerAdapter extends PagerAdapter {
	private Context context;
	private AppContext ac;
	private ArrayList<ImageBean> imageBeans;

	private DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.icon_qq_login_p).showImageForEmptyUri(R.drawable.icon_qq_login_p)
			.showImageOnFail(R.drawable.icon_qq_login_p).cacheInMemory(true).cacheOnDisk(true).considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).build();

	public ImageViewerAdapter(Context context, ArrayList<ImageBean> imageBeans) {
		this.imageBeans = imageBeans;
		this.context = context;
		this.ac = (AppContext) context.getApplicationContext();
	}



	@Override
	public int getCount() {
		return imageBeans.size();
	}

	@Override
	public View instantiateItem(ViewGroup container, int position) {
		View item = View.inflate(context, R.layout.item_imageviewer, null);
		final PhotoView photoView = (PhotoView) item.findViewById(R.id.image);
		final RoundProgressBar progressBar = (RoundProgressBar) item.findViewById(R.id.progressBar);
		ac.imageLoader.displayImage(imageBeans.get(position).getThumb(), photoView);
		photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {

			@Override
			public void onPhotoTap(View view, float x, float y) {
				Activity activity = (Activity) context;
				activity.finish();
			}
		});
		container.addView(item, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		this.ac.imageLoader.displayImage(imageBeans.get(position).url, photoView, options, new SimpleImageLoadingListener() {
			@Override
			public void onLoadingStarted(String imageUri, View view) {
				progressBar.setProgress(5);
				progressBar.setVisibility(View.VISIBLE);
			}

			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
				progressBar.setVisibility(View.GONE);
			}

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				progressBar.setVisibility(View.GONE);
				photoView.setImageBitmap(loadedImage);
			}
		}, new ImageLoadingProgressListener() {
			@Override
			public void onProgressUpdate(String imageUri, View view, int current, int total) {
				System.out.println(Math.round(100.0f * current / total));
				progressBar.setProgress(Math.round(100.0f * current / total));
			}
		});
		return item;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	public static class ImageBean extends NetResult {
		public String url;
		public String thumb;
		public boolean isChecked = false;

		public ImageBean(String url, boolean isChecked) {
			super();
			this.url = url;
			this.isChecked = isChecked;
		}

		public ImageBean(String url, String thumb, boolean isChecked) {
			super();
			this.url = url;
			this.thumb = thumb;
			this.isChecked = isChecked;
		}

		public ImageBean(String url) {
			this.url = url;
		}

		public ImageBean(String url, String thumb) {
			this.url = url;
			this.thumb = thumb;
		}

		public void setThumb(String thumb) {
			this.thumb = thumb;
		}

		public String getThumb() {
			return thumb;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getUrl() {
			return url;
		}
	}

}
