package com.example.sharepreferencetest;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button savebutton;
    private Button readbutton;
    private Button createbutton;
    private Button addbutton;
    private Button updatebutton;
    private Button querybutton;
    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        savebutton = (Button)findViewById(R.id.save_data);
        readbutton = (Button)findViewById(R.id.read_data);
        createbutton = (Button)findViewById(R.id.create_database);
        addbutton = (Button)findViewById(R.id.add_data);
        updatebutton = (Button)findViewById(R.id.update_data);
        querybutton = (Button)findViewById(R.id.query_data);
        saveData();
        ReadData();
        addData();
        updateData();
        dbHelper = new MyDatabaseHelper(this,"BookStore.db",null,2);
        createbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.getWritableDatabase();
            }
        });

    }

    public void saveData(){
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
                // 编辑数据
                editor.putString("name","hangli");
                editor.putInt("age",22);
                editor.putBoolean("married",false);
                editor.putString("兴趣爱好","看书写程序");
                editor.apply();   // 数据进行提交
            }
        });
    }

    public void ReadData(){
        readbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getSharedPreferences("data",MODE_PRIVATE);
                String name = preferences.getString("name","");
                int age  = preferences.getInt("age",1);
                boolean married = preferences.getBoolean("married",false);
                String hobby = preferences.getString("兴趣爱好","我的兴趣爱好");

                Log.d("存储功能", "name is "+name);
                Log.d("存储功能", "age is "+age);
                Log.d("存储功能", "married is "+married);
                Log.d("存储功能", "hobby is "+hobby);
            }
        });
    }

    public void addData(){
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                // 开启组装第一条数据
                values.put("name","数据库组织");
                values.put("author","杭立");
                values.put("pages",454);
                values.put("price",16.98);
                db.insert("Book",null,values);  // 插入第一条数据
                values.clear();
                // 开启组装第二条数据
                values.put("name","The Lost Symbol");
                values.put("author","Bob");
                values.put("page",510);
                values.put("price",19.95);
                db.insert("Book",null,values);  //插入第二条数据
            }
        });
    }

    public void updateData() {
        updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("price",24.00);
                db.update("Book",values,"name=?",new String[]{"the Da VinciCode"});
            }
        });
    }

    public void queryData(){
        querybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                // 查询Book表中所有的数据
                Cursor cursor = db.query("Book", new String[]{"author"},"name",null,null,null,null);
                if(cursor.moveToFirst()){
                    do{
                        // 遍历Cursor对象
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int page = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d("查询数据", "name is "+name);
                        Log.d("查询数据", "author is "+author);
                        Log.d("查询数据", "page is "+page);
                        Log.d("查询数据", "price is "+price);

                    }while (cursor.moveToNext());
                }
                cursor.close();
            }
        });
    }
}
