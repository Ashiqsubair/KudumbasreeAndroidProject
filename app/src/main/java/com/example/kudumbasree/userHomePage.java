package com.example.kudumbasree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class userHomePage extends AppCompatActivity {
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_page);
        Intent intent=getIntent();
        username=intent.getStringExtra("username");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.exit){
            finishAffinity();
        }
        if(item.getItemId()==R.id.logout){
            Intent intent=new Intent(this, MainActivity.class);
            finish();
            startActivity(intent);
        }
        if(item.getItemId()==R.id.changePass){
            Intent intent=new Intent("act.changePass");
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    public void Userprofile(View v){
        Intent intent=new Intent(this, User_Profile_page.class);
        intent.putExtra("username",username.toString());
        startActivity(intent);
    }
    public void Applyloan(View v){
        Intent intent=new Intent(this, User_Loan_ApplyPage.class);
        intent.putExtra("username",username.toString());
        startActivity(intent);
    }
    public void repayLoanUser(View v){
        Intent intent=new Intent(this, Repay_Loan.class);
        intent.putExtra("username",username.toString());
        startActivity(intent);
    }
    public void dailyCollection(View v){
        Intent intent=new Intent(this, User_daily_collectionPage.class);
        intent.putExtra("username",username.toString());
        startActivity(intent);
    }
}