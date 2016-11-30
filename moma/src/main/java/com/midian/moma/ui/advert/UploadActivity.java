package com.midian.moma.ui.advert;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.midian.moma.R;
import com.midian.moma.model.AdMaterialDetailBean;
import com.midian.moma.model.AdMaterialDetailBean.Pics;
import com.midian.moma.utils.AppUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.iwf.photopicker.PhotoPagerActivity;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;
import midian.baselib.base.BaseActivity;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.ScreenUtils;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;

/**
 * 上传广告资料
 * Created by chu on 2015.11.26.026.
 */
public class UploadActivity extends BaseActivity {

    RecyclerView recyclerView;
    MAdapter photoAdapter;
    ArrayList<String> selectedPhotos = new ArrayList<>();
    public final static int REQUEST_CODE = 1;
    public final static int PHOTO_REQUEST_CODE = 2;
    private BaseLibTopbarView topbar;
    private View upload_ll;
    private TextView replay_tv;
    private EditText content;
    private Button upload;
    private List<String> photos = null;
    private String order_id, type;
    private String ad_demand;
    private File mImages = null;
    HashMap<String, String> netPath = new HashMap<>();
    String contents;
    List<String> upLoadPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        order_id = mBundle.getString("order_id");
        if (mBundle.getString("type") != null) {
            type = mBundle.getString("type");
        }
        System.out.println("上传资料跳转后::订单id::" + order_id + "type---" + type);
        topbar = findView(R.id.topbar);
        upload_ll = findView(R.id.upload_ll);
        content = findView(R.id.content);
        upload = findView(R.id.upload);
        replay_tv = findView(R.id.replay);
        upload_ll.setOnClickListener(this);
        upload.setOnClickListener(this);
        replay_tv.setOnClickListener(this);
        recyclerView = findView(R.id.recycler_view);
        photoAdapter = new MAdapter(this, selectedPhotos);

        //3表示入口为编辑资料
        if ("3".equals(type)) {
            topbar.setTitle("编辑广告资料").setLeftImageButton(R.drawable.icon_back, null).setLeftText("返回", UIHelper.finish(_activity));
            replay_tv.setVisibility(View.VISIBLE);
            loadData();
        } else {
            topbar.setTitle("上传广告资料").setLeftImageButton(R.drawable.icon_back, null).setLeftText("返回", UIHelper.finish(_activity));
        }

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        recyclerView.setAdapter(photoAdapter);

        if (photos != null) {
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.GONE);
        }


    }

    // 8.2广告资料详情接口
    private void loadData() {
        AppUtil.getMomaApiClient(ac).adMaterialDetail(order_id, this);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.replay://回复列表
                Bundle mBundle = new Bundle();
                mBundle.putString("order_id", order_id);
                UIHelper.jump(_activity, ReplayListActivity.class, mBundle);
                break;
            case R.id.upload_ll:
                if (selectedPhotos.size() < 10) {
                    PhotoPickerIntent intent = new PhotoPickerIntent(UploadActivity.this);
                    intent.setPhotoCount(10 - selectedPhotos.size());
                    intent.setShowCamera(true);
                    startActivityForResult(intent, PHOTO_REQUEST_CODE);
                } else {
                    UIHelper.t(_activity, "最多只能上传10张图片");
                }
                break;
            case R.id.upload:
                if (photos == null) {
                    UIHelper.t(_activity, "请上传广告图片");
                    return;
                }

                contents = content.getText().toString().trim();
                if (TextUtils.isEmpty(contents)) {
                    UIHelper.t(_activity, "请输入广告要求");
                    return;
                }

                upLoadPath = new ArrayList<String>();
                for (String path : selectedPhotos) {
                    if (netPath.containsKey(path)) {
                    } else {
                        upLoadPath.add(path);
                    }
                }
                UIHelper.t(_activity, "正在上传广告资料，请稍后...");
                AppUtil.getMAppContext(ac).upLoad(order_id, upLoadPath, contents, UploadActivity.this);
////                try {
//                    recyclerView.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                AppUtil.getMomaApiClient(ac).uploadMaterial(order_id, upLoadPath, contents, UploadActivity.this);
//                            } catch (FileNotFoundException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    });

//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
                break;
        }
    }

    public void previewPhoto(Intent intent) {
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
            }
            selectedPhotos.clear();
            if (photos != null) {
                recyclerView.setVisibility(View.VISIBLE);

                selectedPhotos.addAll(photos);
                System.out.println("返回的照片数据：：：" + photos);
            } else {
                recyclerView.setVisibility(View.GONE);
            }
            resizePhotoView();
            photoAdapter.notifyDataSetChanged();
        } else if (resultCode == RESULT_OK && requestCode == PHOTO_REQUEST_CODE) {
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
            }
            if (photos != null) {
                recyclerView.setVisibility(View.VISIBLE);
                selectedPhotos.addAll(photos);
                System.out.println("返回的照片数据：：：" + photos);
            } else {
                recyclerView.setVisibility(View.GONE);
            }
            resizePhotoView();
            photoAdapter.notifyDataSetChanged();

        }
    }

    public void resizePhotoView() {
        LinearLayout.LayoutParams p = (LinearLayout.LayoutParams) recyclerView.getLayoutParams();
        int itemHeight = (ScreenUtils.GetScreenWidthPx(_activity) - recyclerView.getPaddingLeft() * 2) / 4;
        int low = selectedPhotos.size() / 4;
        if (selectedPhotos.size() % 4 > 0)
            low++;
        p.height = itemHeight * low + recyclerView.getPaddingTop() * 2;
        if (selectedPhotos.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.GONE);
        }

    }


    @Override
    public void onApiStart(String tag) {
        super.onApiStart(tag);
        showLoadingDlg();
    }


    public class MAdapter extends RecyclerView.Adapter<MAdapter.PhotoViewHolder> {

        private ArrayList<String> photoPaths = new ArrayList<String>();
        private LayoutInflater inflater;

        private Context mContext;

        public MAdapter() {

        }

        public MAdapter(Context mContext, ArrayList<String> photoPaths) {
            this.photoPaths = photoPaths;
            this.mContext = mContext;
            inflater = LayoutInflater.from(mContext);

        }


        @Override
        public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = inflater.inflate(me.iwf.photopicker.R.layout.item_photo, parent, false);
            return new PhotoViewHolder(itemView);
        }


        @Override
        public void onBindViewHolder(final PhotoViewHolder holder, final int position) {

            final String path = photoPaths.get(position);
            final Uri uri;
            if (path.startsWith("http")) {
                uri = Uri.parse(path);
            } else {
                uri = Uri.fromFile(new File(path));
            }
            Glide.with(mContext)
                    .load(uri)
                    .centerCrop()
                    .thumbnail(0.1f)
                    .placeholder(me.iwf.photopicker.R.drawable.ic_photo_black_48dp)
                    .error(me.iwf.photopicker.R.drawable.ic_broken_image_black_48dp)
                    .into(holder.ivPhoto);

//            }
            holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, PhotoPagerActivity.class);
                    intent.putExtra(PhotoPagerActivity.EXTRA_CURRENT_ITEM, position);
                    intent.putExtra(PhotoPagerActivity.EXTRA_PHOTOS, photoPaths);
                    if (mContext instanceof UploadActivity) {
                        ((UploadActivity) mContext).previewPhoto(intent);
                    }
                }
            });

            holder.vSelected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String path = photoPaths.get(position);
                    if (netPath.containsKey(path)) {
                        AppUtil.getMomaApiClient(ac).deleteMaterialPic(order_id, netPath.get(path), UploadActivity.this);
                        netPath.remove(path);
                    }
                    photoPaths.remove(position);
                    resizePhotoView();
                    photoAdapter.notifyDataSetChanged();
                }
            });

        }


        //        /**
//         * 压缩聊天图片
//         *
//         * @param picFileName
//         * @return
//         */
//        public String compressImage(String picFileName) {
//            Bitmapp image = FDBitmapUtil.decodeFile(picFileName, 1000);
//            FDFileUtil.deleteFile(new File(picFileName), context);
//            ByteArrayOutputStreameam baos = new ByteArrayOutputStream();
//
//            image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
//            float options = 90;
//            while (baos.toByteArray().length / 1024 > 200 && options > 10) { // 循环判断如果压缩后图片
//                baos.reset();// 重置baos即清空baos
//                image.compress(Bitmap.CompressFormat.JPEG, (int) options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
//                options -= 10f;// 每次都减少10
//            }
//            FDDebug.d("压缩后大小 " + (baos.toByteArray().length / 1024));
//
//            // 保存新文件
//            try {
//                FileOutputStream out = new FileOutputStream(new File(picFileName));
//                out.write(baos.toByteArray());
//                out.flush();
//                out.close();
//                return picFileName;
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//                return "";
//            } catch (IOException e) {
//                e.printStackTrace();
//                return "";
//            }
//        }
        @Override
        public int getItemCount() {
            return photoPaths.size();
        }


        public class PhotoViewHolder extends RecyclerView.ViewHolder {
            private ImageView ivPhoto;
            private ImageView vSelected;

            public PhotoViewHolder(View itemView) {
                super(itemView);
                ivPhoto = (ImageView) itemView.findViewById(me.iwf.photopicker.R.id.iv_photo);
                vSelected = (ImageView) itemView.findViewById(me.iwf.photopicker.R.id.v_selected);
                vSelected.setVisibility(View.VISIBLE);
                vSelected.setImageResource(R.drawable.icon_delete);
            }
        }
    }

    List<Pics> pics = new ArrayList<Pics>();

    @Override
    public void onApiSuccess(NetResult res, String tag) {
        super.onApiSuccess(res, tag);
        hideLoadingDlg();
        if (res.isOK()) {
            //广告资料详情接口
            if ("adMaterialDetail".equals(tag)) {
                AdMaterialDetailBean adBean = (AdMaterialDetailBean) res;
                ad_demand = adBean.getContent().getAd_demand();//广告要求
                System.out.println("广告资料返回的要求::::" + adBean.getContent().getAd_demand());
                if (ad_demand != null) {
                    content.setText(ad_demand);
                }
                pics = adBean.getContent().getPics();
//                recyclerView.setVisibility(View.VISIBLE);
                selectedPhotos.clear();
                for (Pics pic : pics) {
                    System.out.println("资料详情返回id为::" + pic.getPic_id() + "图片:::" + pic.getPic_thumb_name());
                    String p = ac.getFileUrl(pic.getPic_thumb_name());
                    netPath.put(p, pic.getPic_id());
                    selectedPhotos.add(p);
                }
                resizePhotoView();
                photoAdapter.notifyDataSetChanged();
            }

            //上传广告资料接口
            else if ("uploadMaterial".equals(tag)) {
                UIHelper.t(_activity, "上传成功,请等待管理员审核!");
                finish();
            } else if ("deleteMaterialPic".equals(tag)) {
                UIHelper.t(_activity, "删除图片成功");
            }
        } else {
            ac.handleErrorCode(_activity, res.error_code);
        }
    }
}
