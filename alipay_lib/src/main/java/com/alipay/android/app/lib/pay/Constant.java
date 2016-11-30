package com.alipay.android.app.lib.pay;

import com.midian.configlib.ServerConstant;

/**
 * 支付常量
 * 
 * @author MIDIAN
 * 
 */
public class Constant {

	public final static String PayfeescallbackUrl = ServerConstant.BASEURL+"pay_ali_callback";
	// 合作身份者id，以2088开头的16位纯数字
	public static final String DEFAULT_PARTNER = "2088121369861782";

	// 收款支付宝账号
	public static final String DEFAULT_SELLER = "3068086694@qq.com";

	// 商户私钥，自助生成
	public static final String PRIVATE = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAMyIJGGKPeFnZ9aJSCCoYxokzKAJFfEsjZZYgdUarwMGgm4GW5oJlBFbGZ3avjBGQaLWqQnUiQZFdKXQqtdVKQX4AjizE6Kdt9eDvk2LowJlM+33XW0Tlt4YnL1ixpBRs2JXzXSRqzWtHWemk+dnL8h40I93nPV0l1IqWqosj6lNAgMBAAECgYAkCVy3nt7WRxzhjOCbeG+1nIQ9MjyeeKYdtkxx2ri5RL1YW2BqXIOtJyr1SCQ4ZziLU10HMV5PL4+k44YmWIfYh4mGLqvb8j+goET2iUU9Oa2uzaZf84e6f/5mOlFD7xG0F+sauDWphbPqTzuMHutuJeewuVN7IXy8Qk6xZ5TxVQJBAPXWyEurTtbqEezmzkVGqFiSjO5BI1DvV8VFCawOw5wr5H/exCAf1j8Oejuoi7cB/7cfUTBNX0PZT2nPj7RQnssCQQDU/EoF24KetQ4FkbWhkv2OjNa1PIhTswfIq3p/otfac2bW0cmIhsoa6P5rQU3uqiK8/u+j6Vl3fZFC+NpCL/1HAkAGvpvEJ7EECGvxeeuSzvtzYqHnL3ZOlCJuURSQ2d7X/ARK1cu/rd/s3vLPjDI5FR704TETCCxi6iVZm84vfj69AkA7U3cPAKD2sjoQpRCxy+7i2SopW44hulmttM4vZV241LJNniwD4n/DVSkhGRSrKSr5+A0qXCJ2nTxsKkMpcpKbAkAhT87+gPZLNN+tlxKPGJ+EHge29QsoY5llX27q2oG/kJbXUxUs5fIMpSrGplFVrfQTvsPrAKRi0emYUBruEAC8";
	public static final String PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDMiCRhij3hZ2fWiUggqGMaJMygCRXxLI2WWIHVGq8DBoJuBluaCZQRWxmd2r4wRkGi1qkJ1IkGRXSl0KrXVSkF+AI4sxOinbfXg75Ni6MCZTPt911tE5beGJy9YsaQUbNiV810kas1rR1nppPnZy/IeNCPd5z1dJdSKlqqLI+pTQIDAQAB";

	
	public static final String YES = "1";
	public static final String NO = "0";

}
