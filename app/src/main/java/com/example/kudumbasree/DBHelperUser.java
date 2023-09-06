package com.example.kudumbasree;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

public class DBHelperUser extends SQLiteOpenHelper {
    public static final String DBNAME="Kudumbasree.db";

    public DBHelperUser(Context context) {
        super(context, "Kudumbasree.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create table users(username TEXT NOT NULL PRIMARY KEY, name TEXT NOT NULL, email TEXT NOT NULL, unit TEXT NOT NULL, mobile TEXT NOT NULL,password TEXT NOT NULL, access TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {

        MyDB.execSQL("drop Table if exists users");
    }
    public boolean insertData(String username, String name, String email, String unit, String mobile,String password){
        SQLiteDatabase MyDB=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("username",username);
        contentValues.put("name",name);
        contentValues.put("email",email);
        contentValues.put("unit",unit);
        contentValues.put("mobile",mobile);
        contentValues.put("password",password);
        contentValues.put("access","no");
        long result=MyDB.insert("users",null,contentValues);
        if (result==-1){
            return false;
        }
        else {
            return true;
        }
    }
    public boolean userLogin(String username,String password){
        SQLiteDatabase MyDB=this.getWritableDatabase();
        Cursor cursor=MyDB.rawQuery("select * from users where username=? and password=? and access=?",new String[]{username,password,"no"});
        if (cursor.getCount()>0){
            return true;
        }
        else {
            return false;
        }
    }
}
