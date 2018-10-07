package com.example.sharepreferencetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by hangli on 2018/10/6.
 * DatabaseHelper 数据库
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_TABLE = "create table Book("
            +"id integer primary key autoincreament,"
            + "author text,"
            + "price real,"
            + "pages integer"
            + "name text)";

    public static final String CRETE_CATEGORY = "create table Category("
            + "id integer primary key autoincrement,"
            + "categroy_name text,"
            + "categroy_code integer)";

    private Context mContext;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    // 创建数据库
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
//        db.execSQL(CRETE_CATEGORY);// 必须险删除旧的数据，新数据库才可以创建成功
        Toast.makeText(mContext,"创建数据库",Toast.LENGTH_LONG).show();
    }

     // 更新数据库，实现删除旧数据
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists Book");
//        db.execSQL("drop table if exists Category");
        onCreate(db);
    }
}
