package com.midian.moma.tabs.bean;

import android.database.SQLException;
import android.text.TextUtils;
import android.util.Log;

import com.midian.moma.db.SQLHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ChannelManage {
    public static ChannelManage channelManage;
    /**
     * 默认的用户选择频道列表
     */
    public static List<ChannelItem> defaultUserChannels;
    /**
     * 默认的其他频道列表
     */
    public static List<ChannelItem> defaultOtherChannels;
    private ChannelDao channelDao;
    /**
     * 判断数据库中是否存在用户数据
     */
    private boolean userExist = false;

    static {
        defaultUserChannels = new ArrayList<ChannelItem>();
        defaultOtherChannels = new ArrayList<ChannelItem>();
//        defaultUserChannels.add(new ChannelItem("", "推荐", 1, 1));
    }

    private ChannelManage(SQLHelper paramDBHelper) throws SQLException {
        if (channelDao == null)
            channelDao = new ChannelDao(paramDBHelper.getContext());
        // NavigateItemDao(paramDBHelper.getDao(NavigateItem.class));
        return;
    }

    /**
     * 初始化频道管理类
     *
     * @throws SQLException
     */
    public static ChannelManage getManage(SQLHelper dbHelper) throws SQLException {
        if (channelManage == null)
            channelManage = new ChannelManage(dbHelper);
        return channelManage;
    }

    /**
     * 清除所有的频道
     */
    public void deleteAllChannel() {
        channelDao.clearFeedTable();
    }

    /**
     * 获取其他的频道
     *
     * @return 数据库存在用户配置 ? 数据库内的用户选择频道 : 默认用户选择频道 ;
     */
    public List<ChannelItem> getUserChannel() {
        Object cacheList = channelDao.listCache(SQLHelper.SELECTED + "= ?", new String[]{"1"});
        if (cacheList != null && !((List) cacheList).isEmpty()) {
            userExist = true;
            List<Map<String, String>> maplist = (List) cacheList;
            int count = maplist.size();
            List<ChannelItem> list = new ArrayList<ChannelItem>();
            for (int i = 0; i < count; i++) {
                ChannelItem navigate = new ChannelItem();
                navigate.setId(maplist.get(i).get(SQLHelper.ID));
                navigate.setName(maplist.get(i).get(SQLHelper.NAME));
                navigate.setOrderId(Integer.valueOf(maplist.get(i).get(SQLHelper.ORDERID)));
                navigate.setSelected(Integer.valueOf(maplist.get(i).get(SQLHelper.SELECTED)));
                list.add(navigate);
            }
            return list;
        }
        initDefaultChannel();
        return defaultUserChannels;
    }

    /**
     * 获取其他的频道
     *
     * @return 数据库存在用户配置 ? 数据库内的其它频道 : 默认其它频道 ;
     */
    public List<ChannelItem> getOtherChannel() {
        Object cacheList = channelDao.listCache(SQLHelper.SELECTED + "= ?", new String[]{"0"});
        List<ChannelItem> list = new ArrayList<ChannelItem>();
        if (cacheList != null && !((List) cacheList).isEmpty()) {
            List<Map<String, String>> maplist = (List) cacheList;
            int count = maplist.size();
            for (int i = 0; i < count; i++) {
                ChannelItem navigate = new ChannelItem();
                navigate.setId(maplist.get(i).get(SQLHelper.ID));
                navigate.setName(maplist.get(i).get(SQLHelper.NAME));
                navigate.setOrderId(Integer.valueOf(maplist.get(i).get(SQLHelper.ORDERID)));
                navigate.setSelected(Integer.valueOf(maplist.get(i).get(SQLHelper.SELECTED)));
                list.add(navigate);
            }
            return list;
        }
        if (userExist) {
            return list;
        }
        cacheList = defaultOtherChannels;
        return (List<ChannelItem>) cacheList;
    }


    public void updateinit(List<ChannelItem> newList) {
        if (newList == null) return;
        defaultUserChannels = newList.size() > 3 ? newList.subList(0, 3) : newList;
        defaultUserChannels.add(0, new ChannelItem("", "推荐", 1, 1));
        saveUserChannel(defaultUserChannels);

    }

    public void update(List<ChannelItem> newList) {
        try {
            List<ChannelItem> userList = getUserChannel();
            for (int i = userList.size() - 1; i > -1; i--) {
                boolean isUser = false;
//                Iterator iterator  = newList.iterator();

//                for (ChannelItem item : newList) {
//                }
                for (int j = newList.size() - 1; j > -1; j--) {
                    if (userList.get(i).getId().equals(newList.get(j).getId())) {
                        isUser = true;
                        newList.remove(newList.get(j));
                        break;
                    }
                }

                if (isUser) {

                } else {
                    if (!TextUtils.isEmpty(userList.get(i).getId()))
                        userList.remove(userList.get(i));
                }
            }
            deleteAllChannel();
            saveUserChannel(userList);
            saveOtherChannel(newList);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 保存用户频道到数据库
     *
     * @param userList
     */
    public void saveUserChannel(List<ChannelItem> userList) {
        for (int i = 0; i < userList.size(); i++) {
            ChannelItem channelItem = (ChannelItem) userList.get(i);
            channelItem.setOrderId(i);
            channelItem.setSelected(Integer.valueOf(1));
            channelDao.addCache(channelItem);
        }
    }

    /**
     * 保存其他频道到数据库
     *
     * @param otherList
     */
    public void saveOtherChannel(List<ChannelItem> otherList) {
        for (int i = 0; i < otherList.size(); i++) {
            ChannelItem channelItem = (ChannelItem) otherList.get(i);
            channelItem.setOrderId(i);
            channelItem.setSelected(Integer.valueOf(0));
            channelDao.addCache(channelItem);
        }
    }

    /**
     * 初始化数据库内的频道数据
     */
    private void initDefaultChannel() {
        Log.d("deleteAll", "deleteAll");
        deleteAllChannel();
        saveUserChannel(defaultUserChannels);
        saveOtherChannel(defaultOtherChannels);
    }
}
