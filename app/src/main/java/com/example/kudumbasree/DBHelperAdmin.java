package com.example.kudumbasree;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    public List<String> membersdetails() {
        ArrayList<String> list = new ArrayList<String>();
        SQLiteDatabase MyDB = this.getReadableDatabase();
        String query="SELECT * FROM users";
        Cursor cursor = MyDB.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        return list;
    }
    public List<String> newRequest(){
        ArrayList<String> list=new ArrayList<>();
        SQLiteDatabase mydb=getWritableDatabase();
        Cursor cursor=mydb.rawQuery("select * from users where access=?",new String[]{"no"});
        if (cursor.moveToFirst()){
            while (cursor.moveToNext()){
                list.add(cursor.getString(1));
            }
        }
        return list;
    }
}
