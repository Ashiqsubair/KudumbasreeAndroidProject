package com.example.kudumbasree;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import java.util.ArrayList;
import java.util.List;

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
        Cursor cursor=MyDB.rawQuery("select * from users where username=? and password=? and access=?",new String[]{username,password,"yes"});
        if (cursor.getCount()>0){
            return true;
        }
        else {
            return false;
        }
    }
    public List<String> newRequest(){
        ArrayList<String> list=new ArrayList<>();
        SQLiteDatabase MyDB=getWritableDatabase();
        Cursor cursor=MyDB.rawQuery("select * from users where access=+'no' order by name",null);
        if (cursor.getCount()>0){
            while (cursor.moveToNext()){
                list.add(cursor.getString(1));
            }
        }
        return list;
    }
    public List<String> membersdetails() {
        ArrayList<String> list = new ArrayList<String>();
        SQLiteDatabase MyDB = this.getWritableDatabase();
        String query="SELECT name FROM users where access=+'yes' order by name";
        Cursor cursor = MyDB.rawQuery(query, null);
        if (cursor.getCount()>0) {
            while (cursor.moveToNext()) {
                list.add(cursor.getString(0));
            }
        }
        return list;
    }
    public List<userdata> userdetails(String name) {
        ArrayList<userdata> list = new ArrayList<>();

        try (SQLiteDatabase MyDB = getWritableDatabase()) {
            Cursor cursor = MyDB.rawQuery(
                    "SELECT username, name, mobile, access, unit, email FROM users WHERE name=?",
                    new String[]{name}
            );

            if (cursor.moveToFirst()) {
                do {
                    userdata ud = new userdata();
                    ud.username = cursor.getString(0);
                    ud.name = cursor.getString(1);
                    ud.mobile = cursor.getString(2);
                    ud.access = cursor.getString(3);
                    ud.unit = cursor.getString(4);
                    ud.email = cursor.getString(5);
                    list.add(ud);
                } while (cursor.moveToNext());
            }

            cursor.close();
        } catch (SQLiteException e) {
            // Handle any SQLite exceptions here
            e.printStackTrace();
        }

        return list;
    }
    public boolean updateAccess(String name) {
        SQLiteDatabase myDB = getWritableDatabase();

        // Use execSQL to execute an UPDATE query
        String updateQuery = "UPDATE users SET access='yes' WHERE name=?";

        try {
            myDB.execSQL(updateQuery, new String[]{name});
            return true; // Update was successful
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false; // Update failed
        } finally {
            myDB.close(); // Close the database connection when done
        }
    }

    public boolean removeAccess(String username) {
        SQLiteDatabase myDB = getWritableDatabase();

        // Use execSQL to execute an UPDATE query
        String updateQuery = "UPDATE users SET access='no' WHERE username=?";

        try {
            myDB.execSQL(updateQuery, new String[]{username});
            return true; // Update was successful
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false; // Update failed
        } finally {
            myDB.close(); // Close the database connection when done
        }
    }


    public List<userdata> userdetailsUser(String username) {
        ArrayList<userdata> list = new ArrayList<>();

        try (SQLiteDatabase MyDB = getWritableDatabase()) {
            Cursor cursor = MyDB.rawQuery("SELECT * FROM users WHERE username=?",new String[]{username});

            if (cursor.moveToFirst()) {
                do {
                    userdata ud = new userdata();
                    ud.name = cursor.getString(1);
                    ud.mobile = cursor.getString(4);
                    ud.email = cursor.getString(2);
                    ud.unit=cursor.getString(3);
                    ud.access=cursor.getString(6);
                    ud.username= cursor.getString(0);
                    list.add(ud);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            // Handle any SQLite exceptions here
            e.printStackTrace();
        }

        return list;
    }

    public boolean updateUserDetails(String username,String name,String email,String mobile) {
        SQLiteDatabase myDB = getWritableDatabase();

        // Use execSQL to execute an UPDATE query
        String updateQuery = "UPDATE users SET name=?,email=?,mobile=? WHERE username=?";

        try {
            myDB.execSQL(updateQuery, new String[]{name,email,mobile,username});
            return true; // Update was successful
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false; // Update failed
        } finally {
            myDB.close(); // Close the database connection when done
        }
    }

    public String userMobileDetails(String username) {
        String mobile = null; // Initialize the mobile variable

        SQLiteDatabase MyDB = getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT mobile FROM users WHERE username=?", new String[]{username});

        try {
            if (cursor.moveToFirst()) { // Move to the first row if a result exists
                mobile = cursor.getString(0); // Get the mobile column value
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle any exceptions here
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close(); // Close the cursor to release resources
            }
            MyDB.close(); // Close the database connection
        }

        return mobile;
    }


}
