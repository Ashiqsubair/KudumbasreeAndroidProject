package com.example.kudumbasree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLogin extends AppCompatActivity {

    DBHelperAdmin DB;
    Button Loginbtn;
    EditText username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        Loginbtn=findViewById(R.id.loginbtn);
        username=findViewById(R.id.adminUsername);
        password=findViewById(R.id.adminPassword);
        DB=new DBHelperAdmin(this);
        Loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s1,s2;
                s1=username.getText().toString();
                s2=password.getText().toString();
                if (s1.equals("")||s2.equals("")){
                    Toast.makeText(getApplication(),"Field blank",Toast.LENGTH_SHORT).show();
                }
                else {
                    Boolean result= DB.adminLogin(s1,s2);
                    if (result){
                        Toast.makeText(getApplication(),"Login Successfull",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplication(),AdminHomePage.class);
                        intent.putExtra("username",username.getText().toString());
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplication(),"Invalid Credential",Toast.LENGTH_SHORT).show();
                    }

                }

                }
        });
    }
}