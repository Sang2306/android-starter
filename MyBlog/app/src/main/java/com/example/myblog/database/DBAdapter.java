package com.example.myblog.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class DBAdapter {

    private DBHelper dbHelper;

    public DBAdapter(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void insertData(String username, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        db.insert(dbHelper.table_name, null, contentValues);
    }

    public void updateName(String username, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        String[] whereArgs = {username};
        db.update(dbHelper.table_name, contentValues, "username = ?", whereArgs);
    }

    public void delete(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] whereArgs = {username};
        db.delete(dbHelper.table_name, "username = ?", whereArgs);
    }

    public ArrayList<User> getData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //Danh sách các cột muốn lấy
        String[] columns = {"username", "password", "logedin_date"};
        ArrayList<User> users = new ArrayList<>();
        try (Cursor cursor = db.query(dbHelper.table_name, columns, null, null, null, null, "logedin_date")) {
            while (cursor.moveToNext()) {
                User user = new User(
                        cursor.getString(cursor.getColumnIndex("username")),
                        cursor.getString(cursor.getColumnIndex("password"))
                );
                users.add(user);
            }
        }
        return users;
    }


    // Cơ sở dữ liệu lưu username và password
    static class DBHelper extends SQLiteOpenHelper {
        private static final String db_name = "thongtindanhnhap";
        private static final int db_version = 1;
        private static final String table_name = "user";
        private static final String column_1 = "username varchar(50),";
        private static final String column_2 = "password varchar(50),";
        private static final String column_3 = "logedin_date datetime primary_key default current_timestamp";

        private static final String create_table = "CREATE TABLE " + table_name
                + "( " + column_1 + column_2 + column_3 + ");";

        private static final String drop_table = "drop table if exists " + table_name;

        private Context context;

        DBHelper(Context context){
            super(context, db_name, null, db_version);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(create_table);
            }catch (SQLException sqle){
                Toast.makeText(context, "Đã có lỗi xảy ra " + sqle.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try{
                db.execSQL(drop_table);
                onCreate(db);
            }catch (Exception sqle){
                Toast.makeText(context, "Đã có lỗi xảy ra " + sqle.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
