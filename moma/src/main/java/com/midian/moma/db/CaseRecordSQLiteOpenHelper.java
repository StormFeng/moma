package com.midian.moma.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 案例搜索记录数据存储
 * Created by chu on 2016/3/30.
 */
public class CaseRecordSQLiteOpenHelper extends SQLiteOpenHelper{
    private static String name = "case.db";
    private static Integer version = 1;

    public CaseRecordSQLiteOpenHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table records(id integer primary key autoincrement,name varchar(200))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
