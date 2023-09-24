package com.example.kudumbasree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class User_Loan_ApplyPage extends AppCompatActivity {

    DBHelperUserLoan DB = new DBHelperUserLoan(this);
    Button submit;
    EditText reason,amount;
    TextView interest;
    double interest_amount;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_loan_apply_page);
        reason=findViewById(R.id.txt_reason);
        interest=findViewById(R.id.txt_interest);
        amount=findViewById(R.id.txt_amount);
        Intent intent=getIntent();
        username=intent.getStringExtra("username");

        amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    int amount_num=Integer.parseInt(amount.getText().toString());
                    interest_amount=Math.round(amount_num*(1.09));
                    interest.setText("After the interest of 9% you have to pay\n Rs: "+String.valueOf(interest_amount));

            }
        });
        submit=findViewById(R.id.btn_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String reasonvalue= reason.getText().toString();
               String amountvalue=amount.getText().toString();
               String interestvalue=String.valueOf(interest_amount);
               String usrname=username.toString();
               if(reasonvalue.equals("")||amountvalue.equals("")){
                   Toast.makeText(getApplication(),"Field can't be Blank",Toast.LENGTH_SHORT).show();
               }
               else {
                   Boolean loanexist=DB.loanexist(usrname);
                   if(loanexist==true){
                       Toast.makeText(getApplication(),"You've already applied for a loan, Status: Pending",Toast.LENGTH_SHORT).show();
                       finish();
                       startActivity(getIntent());
                   }
                   else {
                       Random rand=new Random();
                       int random_num=rand.nextInt(1000);
                       Boolean result= DB.insertData(username.toString(),username.toString()+String.valueOf(random_num).toString(),reasonvalue,amountvalue,interestvalue);
                       if (result==true){
                           Toast.makeText(getApplication(),"Loan Application is given. Wait for the Admin Approval",Toast.LENGTH_SHORT).show();
                           finish();
                           startActivity(getIntent());
                       }
                   }
               }


            }
        });


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