package com.example.kudumbasree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Repay_Loan extends AppCompatActivity {

    DBHelperUserLoan DB = new DBHelperUserLoan(this);

    TextView amountToRepay,TotalAmount;
    String amtr;
    EditText amountRepaying;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repay_loan);

// Initialize UI elements after setContentView
        amountRepaying = findViewById(R.id.txt_RepayingAmount);
        amountToRepay = findViewById(R.id.txt_balanceAmount);
        TotalAmount = findViewById(R.id.txt_totalAmount);
        Button pay=findViewById(R.id.btn_pay);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        List<loanRepayDetail> list = DB.getLoanData(username);
        if (!list.isEmpty()) {
            loanRepayDetail lr = list.get(0);
            TotalAmount.setText("Total Amount Rs: " + lr.amountWithInterest.toString());
            amountToRepay.setText("Balance Amount You Have to Pay Rs: " + lr.amounttoRepay.toString());
            amtr = lr.amounttoRepay.toString();
        }
        else {
            Toast.makeText(getApplication(),"You Don't have any Active Loans",Toast.LENGTH_SHORT).show();
        }

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(amountRepaying.getText().toString().equals("")){
                    Toast.makeText(getApplication(),"Field Can't be Blank",Toast.LENGTH_SHORT).show();
                }else{

                    double a=Double.parseDouble(amtr.toString());
                    double b=Double.parseDouble(amountRepaying.getText().toString());
                    if(b>a){
                        Toast.makeText(getApplication(),"Please Enter a valid Amount",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        double c=a-b;
                        Boolean result=DB.updateLoanRepayAmount(username.toString(),String.valueOf(c).toString());
                        if (result == true) {
                            Toast.makeText(getApplication(),"Rs: "+amountRepaying.getText().toString()+" paid successfully",Toast.LENGTH_SHORT).show();
                            startActivity(getIntent());
                        }
                        else {
                            Toast.makeText(getApplication(),"Error Occured",Toast.LENGTH_SHORT).show();
                        }
                        if (c==0){
                            Boolean result2 = DB.loanComplete(username);
                            if (result2==true){
                                Toast.makeText(getApplication(),"You've Successfully closed the Loan",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                }
            }
        });











    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
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