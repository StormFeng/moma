package com.midian.moma.utils;

import com.midian.moma.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 签到自定义弹窗
 *
 * @author chu
 */
public class SignCustomDialog extends Dialog {

    private Context context;
    private String title;
    private String content;
    private String confirmButtonText;
    private String cancelButtonText;
    private VerificationDialogInterface mDialogInterface;

    private TextView title_tv, content_hint;
    private EditText content_et;
    private ImageView img;
    private Button confirm_bt;

    // 自定义回调，用于Dialog事件监听
    public interface VerificationDialogInterface {
        public void doConfirm();

        public void doCancel();

    }

    public SignCustomDialog(Context context, VerificationDialogInterface mDialogInterface) {
        super(context);
        this.mDialogInterface = mDialogInterface;
    }

    public SignCustomDialog(Context context, String title, String content, String confirmButtonText,
                            String cancelButtonText) {
        super(context, R.style.confirm_dialog);
        this.context = context;
        this.title = title;
        this.content = content;
        this.confirmButtonText = confirmButtonText;
        this.cancelButtonText = cancelButtonText;
    }

    public static void make(Context context, String title, String content, String confirmButtonText,
                            String cancelButtonText) {
        SignCustomDialog verificationDialog = new SignCustomDialog(context, title, content, confirmButtonText,
                cancelButtonText);
        verificationDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        verificationDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //弹窗的布局
        setContentView(R.layout.sign_custom_dialog);

        title_tv = (TextView) findViewById(R.id.dialog_title);// 标题
        confirm_bt = (Button) findViewById(R.id.confirm_bt);// 确认
//        cancel_bt = (Button) findViewById(R.id.cancel_bt);// 取消
        content_et = (EditText) findViewById(R.id.input);// 输入框
        img = (ImageView) findViewById(R.id.img);//弹窗显示图片
        content_hint = (TextView) findViewById(R.id.hint);// 状态提示文字

        title_tv.setText(title);
        content_hint.setText(content);
        confirm_bt.setText(confirmButtonText);
//        cancel_bt.setText(cancelButtonText);

        confirm_bt.setOnClickListener(new clickListener());
//        cancel_bt.setOnClickListener(new clickListener());

        // 设置对齐方式
        setCanceledOnTouchOutside(false);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        // 设置排列方式
        LayoutParams p = window.getAttributes();
        p.width = LayoutParams.MATCH_PARENT;
        window.setAttributes(p);

    }

    public void setClicklistener(VerificationDialogInterface mDialogInterface) {
        this.mDialogInterface = mDialogInterface;
    }

    private class clickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                /*case R.id.cancel_bt:// 取消
                    mDialogInterface.doCancel();
                    dismiss();
                    break;*/
                case R.id.confirm_bt:// 确认
                    // if ("".equals(content_et.getText().toString()) ||
                    // content_et.getText().length() < 0) {
                    // UIHelper.t(getContext(), "请输入密码！");
                    // return;
                    // }
                    // if (mDialogInterface != null) {
                    // mDialogInterface.doConfirm(content_et.getText().toString());
                    // }
                    mDialogInterface.doConfirm();
                    dismiss();
                    break;

            }
        }

    }

    ;

}
