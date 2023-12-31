package com.example.kudumbasree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class balanceAdmin extends AppCompatActivity {

    EditText dailyCollectionBalance,loanAmount,loanInterestIncome;
    DBDailyCollection DB;
    DBHelperUserLoan DBl;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_admin);
        dailyCollectionBalance = findViewById(R.id.txt_dailyCollectionIncome);
        loanAmount=findViewById(R.id.txt_loanExpense);
        loanInterestIncome=findViewById(R.id.txt_loanInterestIncome);
        DB = new DBDailyCollection(this);
        int result = DB.getBalanceOfCollection();
        dailyCollectionBalance.setText("Rs: "+String.valueOf(result));

        DBl=new DBHelperUserLoan(this);
        int resultofLoanAmount=DBl.getloanAmount();
        loanAmount.setText("Rs: "+String.valueOf(resultofLoanAmount));

        int resultInterestAmount=DBl.getInterestAmount();
        double interest=(resultInterestAmount*1.09)-resultInterestAmount;

        loanInterestIncome.setText("Rs: "+String.valueOf(interest));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.exit){
            finishAffinity();
        }
        if (item.getItemId()==R.id.logout){
            finish();
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        if (item.getItemId()==R.id.changePass){
            finish();
            Intent intent=new Intent("act.changePass");
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}