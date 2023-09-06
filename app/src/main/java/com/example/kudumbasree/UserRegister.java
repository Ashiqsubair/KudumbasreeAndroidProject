package com.example.kudumbasree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserRegister extends AppCompatActivity {
    EditText username,name,password,unit,email,mobile;
    Button register;
    DBHelperUser DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        username=findViewById(R.id.txt_username);
        name=findViewById(R.id.txt_name);
        password=findViewById(R.id.txt_password);
        unit=findViewById(R.id.txt_unit);
        email=findViewById(R.id.txt_email);
        mobile=findViewById(R.id.txt_mobile);
        register=findViewById(R.id.btn_register);
        DB=new DBHelperUser(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usern=username.getText().toString();
                String pass=password.getText().toString();
                String nam=name.getText().toString();
                String un=unit.getText().toString();
                String ema=email.getText().toString();
                String mob=mobile.getText().toString();

                if (usern.equals("")||pass.equals("")||nam.equals("")||unit.equals("")||ema.equals("")||mob.equals("")){
                    Toast.makeText(getApplicationContext(),"Fields cannot be blank",Toast.LENGTH_SHORT).show();
                }
                else {
                    Boolean result=DB.insertData(usern,nam,ema,un,mob,pass);
                    if (result) {
                        Toast.makeText(getApplicationContext(), "Successfully Registered, You will have login access after admin Approval", Toast.LENGTH_LONG).show();
                        finish();
                        Intent intent=new Intent(getApplication(), UserLogin.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(getApplicationContext(), "Error on registration", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }
}