package com.alipay.android.app.lib.pay;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

//import com.alipay.android.app.sdk.AliPay;
import com.alipay.sdk.app.PayTask;

import java.net.URLEncoder;

/**
 * 支付工具类
 * 
 * @author admin
 * 
 */
public class PayUtil {

	private Context mContext;
	private CallBack mCallBack;

	public PayUtil(Context mContext, CallBack mCallBack) {
		super();
		this.mContext = mContext;
		this.mCallBack = mCallBack;
	}

	/**
	 * 支付方法
	 * 
	 * @param order_sn 订单号
	 * @param money 价格
	 * @param subject 标题
	 * @param body 描述
	 */
	public void pay(String order_sn, String money, String subject, String body) {
		try {
			String info = getNewOrderInfo(order_sn, money, subject, body);
			String sign = Rsa.sign(info, Constant.PRIVATE);
			sign = URLEncoder.encode(sign);
			info += "&sign=\"" + sign + "\"&" + getSignType();
			Log.i("ExternalPartner", "start pay");
			// start the pay.

			final String orderInfo = info;
			new Thread() {
				public void run() {

//					AliPay alipay = new AliPay((Activity) mContext, mHandler);
					PayTask alipay = new PayTask((Activity)mContext);
					// 设置为沙箱模式，不设置默认为线上环境
					// alipay.setSandBox(true);
					String result = alipay.pay(orderInfo);
					Message msg = new Message();

					msg.what = 1;
					msg.obj = result;
					mHandler.sendMessage(msg);
				}
			}.start();

		} catch (Exception ex) {
			ex.printStackTrace();
			// FDToastUtil.show(mContext, "支付失败");
		}
	}

	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			Result result = new Result((String) msg.obj);

			switch (msg.what) {
			case 1:
			case 2: {
				if (result.isSuccess()) {
					if (mCallBack != null)
						mCallBack.complete(true);
				} else {
					if (mCallBack != null)
						mCallBack.complete(false);
				}
			}
				break;
			default:
				break;
			}
		};
	};

	private String getSignType() {
		return "sign_type=\"RSA\"";
	}

	/**
	 * 获取订单信息
	 * 
	 *
	 * @return
	 */
	private String getNewOrderInfo(String order_sn, String money,
			String subject, String body) {
		StringBuilder sb = new StringBuilder();
		sb.append("partner=\"");
		sb.append(Constant.DEFAULT_PARTNER);
		sb.append("\"&out_trade_no=\"");
		// 订单号
		sb.append(order_sn);
		sb.append("\"&subject=\"");
		sb.append(subject);
		sb.append("\"&body=\"");
		sb.append(body);
		sb.append("\"&total_fee=\"");
		sb.append(money);
		sb.append("\"&notify_url=\"");

		// 网址需要做URL编码
		sb.append(URLEncoder.encode(Constant.PayfeescallbackUrl));
		sb.append("\"&service=\"mobile.securitypay.pay");
		sb.append("\"&_input_charset=\"UTF-8");
		sb.append("\"&return_url=\"");
		sb.append(URLEncoder.encode("http://m.alipay.com"));
		sb.append("\"&payment_type=\"1");
		sb.append("\"&seller_id=\"");
		sb.append(Constant.DEFAULT_SELLER);

		// 如果show_url值为空，可不传
		// sb.append("\"&show_url=\"");
		sb.append("\"&it_b_pay=\"1m");
		sb.append("\"");

		return new String(sb);
	}

	/**
	 * 支付回调接口
	 * 
	 * @author admin
	 * 
	 */
	public interface CallBack {
		void complete(boolean stat);
	}

}
