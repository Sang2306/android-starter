package com.example.quanlybanhang.DBAdapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.quanlybanhang.Model.Customer;

import java.util.ArrayList;

public class CustomerDBAdapter {
    private CustomerDBHelper customerDBHelper;

    public CustomerDBAdapter(Context context) {
        customerDBHelper = new CustomerDBHelper(context);
    }

    public long insertData(String name, String product, String date, int amount) {
        SQLiteDatabase db = customerDBHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("product", product);
        contentValues.put("date", date);
        contentValues.put("amount", amount);
        long id = db.insert(CustomerDBHelper.TABLE_NAME, null, contentValues);
        return id;
    }

    public int updateName(String oldName, String newName) {
        SQLiteDatabase db = customerDBHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", newName);
        String[] whereArgs = {oldName};
        int count = db.update(CustomerDBHelper.TABLE_NAME, contentValues, "name" + " = ?", whereArgs);
        return count;
    }


    public ArrayList<Customer> getData() {
        SQLiteDatabase db = customerDBHelper.getWritableDatabase();
        //Danh sách các cột muốn lấy
        String[] columns = {"name", "product", "date", "amount"};
        Cursor cursor = db.query(CustomerDBHelper.TABLE_NAME, columns, null, null, null, null, null);
        ArrayList<Customer> customers = new ArrayList<>();
        while (cursor.moveToNext()){
            customers.add(new Customer(
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("product")),
                    cursor.getString(cursor.getColumnIndex("date")),
                    cursor.getInt(cursor.getColumnIndex("amount"))
            ));
        }
        return customers;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    static class CustomerDBHelper extends SQLiteOpenHelper {
        //PHẦN TỔNG QUAN CỦA
        private static final String DATABASE_NAME = "QUANLYBANHANG";
        private static final String TABLE_NAME = "Customer";
        private static final int DATABASE_VERSION = 1;
        private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" +
                "id integer primary key autoincrement," +
                "name varchar(255)," +
                "product varchar(255)," +
                "date varchar(255)," +
                "amount int" +
                ")";
        private final String DROP_TABLE = "drop table if exists " + TABLE_NAME;

        private Context context;

        public CustomerDBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE);
            } catch (SQLException e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL(DROP_TABLE);
                onCreate(db);
            } catch (SQLException e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
