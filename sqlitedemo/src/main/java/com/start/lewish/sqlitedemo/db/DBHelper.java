package com.start.lewish.sqlitedemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "blacknumber";

    public DBHelper(Context context, int version) {
        super(context, "balacknumber.db", null, version);
        Log.e("TAG", "DBHelper()");

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table blacknumber(_id integer primary key autoincrement,number varchar)");
        db.execSQL("insert into blacknumber(number)values('110')");
        db.execSQL("insert into blacknumber(number)values('220')");
        Log.e("TAG", "onCreate()");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
