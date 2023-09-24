package com.example.kudumbasree;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelperUserLoan extends SQLiteOpenHelper {

    public static final String DBNAME="Loan.db";

    public DBHelperUserLoan(Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create table loan(username TEXT NOT NULL, applicationID NOT NULL PRIMARY KEY, reason TEXT NOT NULL, amount NOT NULL, amountwithInterest TEXT NOT NULL,amountToRepay TEXT , adminStatus TEXT,loanStatus TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        MyDB.execSQL("drop Table if exists loan");
    }

    public boolean insertData(String username, String applicationID, String reason, String amount, String amountwithInterest){
        SQLiteDatabase MyDB=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("username",username);
        contentValues.put("applicationID",applicationID);
        contentValues.put("reason",reason);
        contentValues.put("amount",amount);
        contentValues.put("amountwithInterest",amountwithInterest);
        contentValues.put("amountToRepay",amountwithInterest);
        contentValues.put("adminStatus","pending");
        contentValues.put("loanStatus","pending");
        long result=MyDB.insert("loan",null,contentValues);
        if (result==-1){
            return false;
        }
        else {
            return true;
        }
    }
    public boolean loanexist(String username){
        SQLiteDatabase MyDB=this.getWritableDatabase();
        Cursor cursor=MyDB.rawQuery("select * from loan where username=? and loanStatus=?",new String[]{username,"pending"});
        if (cursor.getCount()>0){
            return true;
        }
        else {
            return false;
        }
    }

    public List<loanRepayDetail> getLoanData(String username) {
        ArrayList<loanRepayDetail> list = new ArrayList<>();

        try (SQLiteDatabase MyDB = getWritableDatabase()) {
            Cursor cursor = MyDB.rawQuery(
                    "SELECT amountwithInterest,amountToRepay  FROM loan WHERE username=? and adminStatus='approved' and loanStatus='pending'", new String[]{username}
            );

            if (cursor.moveToFirst()) {
                do {
                    loanRepayDetail ld = new loanRepayDetail();
                    ld.amountWithInterest = cursor.getString(0);
                    ld.amounttoRepay = cursor.getString(1);
                    list.add(ld);
                } while (cursor.moveToNext());
            }

            cursor.close();
        } catch (SQLiteException e) {
            // Handle any SQLite exceptions here
            e.printStackTrace();
        }

        return list;
    }

    public List<String> membersloanAppied() {
        ArrayList<String> list = new ArrayList<String>();
        SQLiteDatabase MyDB = this.getWritableDatabase();
        String query="SELECT username FROM loan where adminStatus='pending' order by username";
        Cursor cursor = MyDB.rawQuery(query, null);
        if (cursor.getCount()>0) {
            while (cursor.moveToNext()) {
                list.add(cursor.getString(0));
            }
        }
        return list;
    }

    public List<loanRepayDetail> getUsersLoanDetails(String username) {
        ArrayList<loanRepayDetail> list = new ArrayList<>();

        try (SQLiteDatabase MyDB = getWritableDatabase()) {
            Cursor cursor = MyDB.rawQuery(
                    "SELECT amount,reason  FROM loan WHERE username=? and adminStatus='pending'", new String[]{username}
            );

            if (cursor.moveToFirst()) {
                do {
                    loanRepayDetail ld = new loanRepayDetail();
                    ld.amount = cursor.getString(0);
                    ld.reason = cursor.getString(1);
                    list.add(ld);
                } while (cursor.moveToNext());
            }

            cursor.close();
        } catch (SQLiteException e) {
            // Handle any SQLite exceptions here
            e.printStackTrace();
        }

        return list;
    }
    public boolean updateLoanApproval(String username) {
        SQLiteDatabase myDB = getWritableDatabase();

        // Use execSQL to execute an UPDATE query
        String updateQuery = "UPDATE loan SET adminStatus='approved' WHERE username=?";

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
    public boolean updateLoanRejection(String username) {
        SQLiteDatabase myDB = getWritableDatabase();

        // Use execSQL to execute an UPDATE query
        String updateQuery = "UPDATE loan SET adminStatus='rejected', loanStatus='closed' WHERE username=?";

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

    public boolean updateLoanRepayAmount(String username, String amount) {
        SQLiteDatabase myDB = getWritableDatabase();
        myDB.beginTransaction(); // Begin a transaction

        try {
            String updateQuery = "UPDATE loan SET amountToRepay=? WHERE username=?";
            myDB.execSQL(updateQuery, new String[]{amount, username});

            // If everything went well, commit the transaction
            myDB.setTransactionSuccessful();
            return true; // Update was successful
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false; // Update failed
        } finally {
            myDB.endTransaction(); // End the transaction (commit or rollback)
            myDB.close(); // Close the database connection when done
        }
    }
    public boolean loanComplete(String username) {
        SQLiteDatabase myDB = getWritableDatabase();
        myDB.beginTransaction(); // Begin a transaction

        try {
            String updateQuery = "UPDATE loan SET loanStatus='closed' WHERE username=?";
            myDB.execSQL(updateQuery, new String[]{username});

            // If everything went well, commit the transaction
            myDB.setTransactionSuccessful();
            return true; // Update was successful
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false; // Update failed
        } finally {
            myDB.endTransaction(); // End the transaction (commit or rollback)
            myDB.close(); // Close the database connection when done
        }
    }

    public int getloanAmount(){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT SUM(CAST(amount AS INTEGER)) FROM loan where loanStatus='pending'", null);

        int balance = 0; // Initialize the balance

        if (cursor.moveToFirst()) {
            balance = cursor.getInt(0); // Get the integer result
        }

        cursor.close(); // Close the cursor when you're done with it
        return balance;
    }

    public int getInterestAmount(){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT SUM(CAST(amount AS INTEGER)) FROM loan where loanStatus='closed'", null);

        int balance = 0; // Initialize the balance

        if (cursor.moveToFirst()) {
            balance = cursor.getInt(0); // Get the integer result
        }

        cursor.close(); // Close the cursor when you're done with it
        return balance;
    }



}
