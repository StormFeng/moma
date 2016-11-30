package com.midian.moma.app;

import android.graphics.Bitmap;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.baidu.mapapi.SDKInitializer;
import com.midian.baidupush.PushMessage;
import com.midian.login.utils.LoginLibHelp;
import com.midian.moma.api.MomaApiClient;
import com.midian.moma.db.SQLHelper;
import com.midian.moma.utils.AppUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import midian.baselib.api.ApiCallback;
import midian.baselib.app.AppContext;
import midian.baselib.utils.FDBitmapUtil;
import midian.baselib.utils.FDFileUtil;
import midian.baselib.utils.FileUtils;
import midian.baselib.utils.ImageUtils;

/**
 * Created by XuYang on 15/4/13.
 */
public class MAppContext extends AppContext {
    private static ExecutorService poolForUpload;

    @Override
    public void onCreate() {
        super.onCreate();
        // 在使用SDK各组件之前初始化context信息，传入ApplicationContext
        // 注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(this);//百度地图库初始化
        LoginLibHelp.init(this);//登陆库初始化
        MomaApiClient.init(this);//项目接口类初始化
        poolForUpload = Executors.newFixedThreadPool(2);
    }
    SQLHelper sqlHelper;
    /** 获取数据库Helper */
    public SQLHelper getSQLHelper() {
        if (sqlHelper == null)
            sqlHelper = new SQLHelper(this);
        return sqlHelper;
    }



    public void startPush() {
        setBoolean("is_close_push", false);
        PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY, "WuCF2If6aOFjOoi3jOYEWTlX");
    }

    public void stopPush() {
        setBoolean("is_close_push", true);
        PushManager.stopWork(getApplicationContext());
    }


    public void upLoad(String order_id, List<String> selectedPhotos, String content, ApiCallback callback){
        poolForUpload.submit(new RunnableForUpload(order_id,  selectedPhotos,  content, callback));
    }

    /**
     * 上传文件Runnable
     *
     *
     *
     */
    class RunnableForUpload implements Runnable {
        String order_id;
        List<String> selectedPhotos;
        String content;
        ApiCallback callback;

        public RunnableForUpload(String order_id, List<String> selectedPhotos, String content, ApiCallback callback) {
            super();
            this.order_id = order_id;
            this.selectedPhotos = selectedPhotos;
            this.content = content;
            this.callback = callback;
        }

        @Override
        public void run() {
            upload();
        }

        private void upload() {
            try {
                List<String> paths= new ArrayList<String>();
                for (String pic:selectedPhotos){
                  String path=  compressImage(pic);
                    paths.add(path);
                }
                AppUtil.getMomaApiClient(MAppContext.this).uploadMaterial(order_id, paths, content, callback);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 压缩聊天图片
     *
     * @param picFileName
     * @return
     */
    public String compressImage(String picFileName) {
        Bitmap image = FDBitmapUtil.decodeFile(picFileName, 1000);
        FDFileUtil.deleteFile(new File(picFileName), this);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        float options = 90;
        while (baos.toByteArray().length / 1024 > 200 && options > 10) { // 循环判断如果压缩后图片
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, (int) options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10f;// 每次都减少10
        }
       System.out.println("压缩后大小 " + (baos.toByteArray().length / 1024));

        // 保存新文件
        try {
            FileOutputStream out = new FileOutputStream(new File(picFileName));
            out.write(baos.toByteArray());
            out.flush();
            out.close();
            return picFileName;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

}
