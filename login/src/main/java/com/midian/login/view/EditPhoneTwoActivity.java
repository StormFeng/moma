package com.midian.login.view;

import midian.baselib.base.BaseActivity;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.midian.login.R;

/**
 * 重置手机号
 * 
 * @author chu
 *
 */
public class EditPhoneTwoActivity extends BaseActivity {
	private BaseLibTopbarView topbar;
	private EditText phone_et, phone_ets;
	private Button next_btn;
	private View phone_ll, phone_lls;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_phone_two);

		topbar = (BaseLibTopbarView) findViewById(R.id.topbar);
		topbar.setLeftText(R.string.back, null).setLeftImageButton(R.drawable.icon_back, UIHelper.finish(_activity))
				.setTitle("修改手机号");

		phone_ll = findViewById(R.id.phone_ll);
		phone_lls = findViewById(R.id.phone_lls);
		phone_et = findView(R.id.phone_et);
		phone_ets = findView(R.id.phone_ets);
		next_btn = findView(R.id.next_btn);

		next_btn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		int id = v.getId();
		if (id == R.id.next_btn) {
			Bundle mBundle = new Bundle();
			String phone = phone_ets.getText().toString();
			System.out.println("sex:::::::::" + phone);
			mBundle.putString("phone", phone);
			setResult(RESULT_OK, mBundle);
			finish();
		}
	}

}
