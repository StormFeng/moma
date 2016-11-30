package me.iwf.photopicker;

import android.app.Activity;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.utils.PhotoPickerIntent;

/**
 * Created by moshouguan on 2016/1/14.
 */
public class Tmp extends Activity {
    public final static int PHOTO_REQUEST_CODE = 2;

    private void getPhoto() {
        PhotoPickerIntent intent = new PhotoPickerIntent(this);
        intent.setPhotoCount(1);
        intent.setShowCamera(false);
        startActivityForResult(intent, PHOTO_REQUEST_CODE);
    }

    List<String> photos = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PHOTO_REQUEST_CODE) {
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);//获取选择图片
            }

        }
    }
}
