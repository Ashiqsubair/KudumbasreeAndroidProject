package com.example.kudumbasree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class AdminLoanApprovalPage extends AppCompatActivity {


    EditText username,amount,description,mobile;
    Button approve,reject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_loan_approval_page);
        Intent intent=getIntent();
        username=findViewById(R.id.txt_username);
        amount=findViewById(R.id.txt_amount);
        description=findViewById(R.id.txt_reason);
        mobile=findViewById(R.id.txt_mobile);
        username.setText(intent.getStringExtra("username"));
        approve=findViewById(R.id.btn_approve);
        reject=findViewById(R.id.btn_reject);

        DBHelperUserLoan DB=new DBHelperUserLoan(this);
        DBHelperUser DBu=new DBHelperUser(this);

        List<loanRepayDetail> list = DB.getUsersLoanDetails(intent.getStringExtra("username"));
        String listmobile = DBu.userMobileDetails(intent.getStringExtra("username"));
        // Check if the list is not empty before accessing its elements
        if (!list.isEmpty()) {
            loanRepayDetail ld = list.get(0);
            amount.setText(ld.amount.toString());
            description.setText(ld.reason);
            mobile.setText(listmobile.toString());
            // Set other TextViews as needed
        }

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean result=DB.updateLoanApproval(username.getText().toString());
                if (result==true){
                    Toast.makeText(getApplication(),username.getText().toString()+"Loan has been Approved",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean result=DB.updateLoanRejection(username.getText().toString());
                if (result==true){
                    Toast.makeText(getApplication(),username.getText().toString()+"Loan has been Rejected",Toast.LENGTH_SHORT).show();
                    finish();
                    Intent intent1=new Intent(getApplication(), AdminLoanRequest.class);
                    startActivity(getIntent());
                }
            }
        });



    }
}