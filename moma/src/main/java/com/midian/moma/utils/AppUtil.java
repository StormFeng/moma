package com.midian.moma.utils;

import com.midian.moma.api.MomaApiClient;
import com.midian.moma.app.MAppContext;

import midian.baselib.app.AppContext;

/**
 * app工具
 * 
 * @author MIDIAN
 * 
 */
public class AppUtil {
	public static MAppContext getMAppContext(AppContext ac) {
		return (MAppContext) ac;
	}

	public static MomaApiClient getMomaApiClient(AppContext ac) {
		return ac.api.getApiClient(MomaApiClient.class);
	}
}
