package com.example.kudumbasree;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelperAdmin extends SQLiteOpenHelper {
    public static final String DBNAME="admin.db";
    public DBHelperAdmin(Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create table admintbl(username TEXT NOT NULL PRIMARY KEY, name TEXT NOT NULL,password TEXT, unit TEXT)");
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", "admin");
        contentValues.put("name", "Ashiq");
        contentValues.put("password", "admin");
        contentValues.put("unit", "123");
        MyDB.insert("admintbl", null, contentValues);

    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        MyDB.execSQL("drop Table if exists admintbl");
    }
    public boolean adminLogin(String username,String password){

        SQLiteDatabase MyDB=this.getWritableDatabase();
        Cursor cursor=MyDB.rawQuery("select * from admintbl where username=? and password=?",new  String[]{username,password});
        if (cursor.getCount()>0){
            return true;
        }else {
            return false;
        }
    }

    public boolean changePassword(String username,String oldpass,String newpass) {
        SQLiteDatabase myDB = getWritableDatabase();

        // Use execSQL to execute an UPDATE query
        String updateQuery = "UPDATE admintbl SET password=? WHERE username=? and password=?";

        try {
            myDB.execSQL(updateQuery, new String[]{newpass,username,oldpass});
            return true; // Update was successful
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false; // Update failed
        } finally {
            myDB.close(); // Close the database connection when done
        }
    }

}
