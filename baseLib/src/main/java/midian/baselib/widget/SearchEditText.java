package midian.baselib.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.midian.baselib.R;

public class SearchEditText extends LinearLayout {
	LinearLayout conter;
	EditText mEditText;
	TextWatcher mTextWatcher;
	View clear;

	public SearchEditText(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public SearchEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public void init(Context context) {
		setWillNotDraw(false);
		setOrientation(LinearLayout.HORIZONTAL);
		conter = (LinearLayout) inflate(context, R.layout.search_edittext, null);
		LinearLayout.LayoutParams p = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		setPadding(dp2px(context, 15), dp2px(context, 15), dp2px(context, 15),
				dp2px(context, 15));
		addView(conter, p);

		mEditText = (EditText) findViewById(R.id.edit);
		clear = findViewById(R.id.clear);
		clear.setVisibility(View.INVISIBLE);
		clear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				setText("");
			}
		});
		mEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				if (TextUtils.isEmpty(arg0.toString())) {
					clear.setVisibility(View.INVISIBLE);
				} else {
					clear.setVisibility(View.VISIBLE);
				}

				if (mTextWatcher != null) {
					mTextWatcher.onTextChanged(arg0, arg1, arg2, arg3);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
				if (mTextWatcher != null)
					mTextWatcher.beforeTextChanged(arg0, arg1, arg2, arg3);
			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				if (mTextWatcher != null)
					mTextWatcher.afterTextChanged(arg0);
			}
		});

		// setbackground(0x550000);
	}

	public void setTextWatcher(TextWatcher mTextWatcher) {
		this.mTextWatcher = mTextWatcher;
	}

	public void setHint(String hint) {
		if (mEditText != null)
			mEditText.setHint(hint);
	}

	public EditText getEditText() {
		return mEditText;
	}

	public String getText() {
		String text = "";
		if (mEditText != null)
			text = mEditText.getText().toString();
		return text;
	}

	public void setText(String text) {
		if (mEditText != null)
			mEditText.setText(text);
	}

	public void setBackgroundResource(final int color) {
		conter.setBackgroundResource(color);
	}

	/**
	 * 把dp转为px
	 */
	public int dp2px(Context context, float dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}
}
