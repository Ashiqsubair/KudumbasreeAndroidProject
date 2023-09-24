package com.example.kudumbasree;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBDailyCollection extends SQLiteOpenHelper {
    public static final String DBNAME="DailyCollection.db";
    public DBDailyCollection(Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create table DailyCollection(username TEXT NOT NULL,date TEXT NOT NULL, amount TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        MyDB.execSQL("drop Table if exists DailyCollection");
    }
    public boolean insertData(String username,String date,String amount){
        SQLiteDatabase MyDB=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("username",username);
        contentValues.put("date",date);
        contentValues.put("amount",amount);
        long result=MyDB.insert("DailyCollection",null,contentValues);
        if (result==-1){
            return false;
        }
        else {
            return true;
        }
    }
    public boolean getDataInCollection(String username,String date){
        SQLiteDatabase MyDB=this.getWritableDatabase();
        Cursor cursor=MyDB.rawQuery("select * from DailyCollection where username=? and date=?",new String[]{username,date});
        if (cursor.getCount()>0){
            return true;
        }
        else {
            return false;
        }

    }

    public int getBalanceOfCollection(){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT SUM(CAST(amount AS INTEGER)) FROM DailyCollection", null);

        int balance = 0; // Initialize the balance

        if (cursor.moveToFirst()) {
            balance = cursor.getInt(0); // Get the integer result
        }

        cursor.close(); // Close the cursor when you're done with it
        return balance;
    }


}
