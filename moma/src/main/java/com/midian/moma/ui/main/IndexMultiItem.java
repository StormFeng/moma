package com.midian.moma.ui.main;

import com.midian.moma.model.HomeBean.Banner;
import com.midian.moma.model.HomeBean.Examp;

import java.util.ArrayList;
import java.util.List;

import midian.baselib.bean.NetResult;

/**
 * Created by chu on 2015.12.4.004.
 */
public class IndexMultiItem extends NetResult {
    public ArrayList<Banner> banners;
    public ArrayList<Examp> exampsList;
    public Examp examps;
}
