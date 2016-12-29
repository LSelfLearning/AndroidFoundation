package com.start.lewish.sqlitedemo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.start.lewish.sqlitedemo.bean.BlackNumber;
import com.start.lewish.sqlitedemo.db.DBHelper;

import java.util.ArrayList;
import java.util.List;


/*
 * dao:data(base) access object
 * 包含对数据表的增、删、改、查操作的工具类
 * 
 * 
 */
public class BlackNumberDAO {
    private DBHelper dbHelper;

    public BlackNumberDAO(Context context) {
        dbHelper = new DBHelper(context, 1);
    }

    //查询：将数据表中的所有数据查询出来
    public List<BlackNumber> getAll() {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        List<BlackNumber> list = new ArrayList<BlackNumber>();

        Cursor cursor = database.query(DBHelper.TABLE_NAME, null, null, null, null, null, "_id desc");
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String number = cursor.getString(cursor.getColumnIndex("number"));
            BlackNumber blackNumber = new BlackNumber(id, number);
            list.add(blackNumber);
        }
        cursor.close();
        database.close();

        return list;
    }

    /*
     * 修改
     * 修改数据表中_id为blackNumber的属性id值的数据，改为blackNumber的number
     *
     */
    public void update(BlackNumber blackNumber) {//new BlackNumber(3,"119")    _id = 3  120
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put("number", blackNumber.getNumber());
        int updateNum = database.update(DBHelper.TABLE_NAME, values, "_id = ?", new String[]{blackNumber.getId() + ""});
        Log.e("TAG", "修改了" + updateNum + "条记录");

        database.close();
    }

    /*
     * 删除：
     * 按照数据表中指定的id进行删除
     */
    public void deleteById(int id) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        int deleteNum = database.delete(DBHelper.TABLE_NAME, "_id = ?", new String[]{id + ""});
        Log.e("TAG", "删除了" + deleteNum + "条记录");

        database.close();
    }

    /*
     * 添加
     * 将blackNumber的属性number值保存在数据表中。
     */
    public void insert(BlackNumber blackNumber) {//_id  BlackNumber的属性id  列表显示的position
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put("number", blackNumber.getNumber());
        long rowID = database.insert(DBHelper.TABLE_NAME, null, values);
        Log.e("TAG", "新增一条记录。_id = " + rowID);

        database.close();
    }
}
