package com.example.myblog.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DBAdapter {
    // Cơ sở dữ liệu lưu username và password

    static class DBHelper extends SQLiteOpenHelper {
        private static final String db_name = "user";
        private static final int db_version = 1;

        private static final String table_name = null;
        private static final String column_1 = "username varchar(50) primary key,";
        private static final String column_2 = "password varchar(50)";

        private static final String create_table = "CREATE TABLE " + table_name
                + "( " + column_1 + column_2 + ");";

        private static final String drop_table = "drop table if exists " + table_name;

        private Context context;

        public DBHelper(Context context){
            super(context, db_name, null, db_version);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(create_table);
            }catch (SQLException ignored){
                Toast.makeText(context, "Đã có lỗi xảy ra " + ignored.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try{
                db.execSQL(drop_table);
                onCreate(db);
            }catch (Exception ignored){
                Toast.makeText(context, "Đã có lỗi xảy ra " + ignored.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
