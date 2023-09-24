package com.example.kudumbasree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserLogin extends AppCompatActivity {

    EditText username,password;
    Button login,register;
    DBHelperUser DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        DB=new DBHelperUser(this);
        login=findViewById(R.id.btnuserLogin);
        register=findViewById(R.id.btnuserregister);
        username=findViewById(R.id.edtxtusername);
        password=findViewById(R.id.edtxtpassword);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1,s2;
                s1=username.getText().toString();
                s2=password.getText().toString();

               if (s1.equals("")||s2.equals("")){
                   Toast.makeText(getApplication(),"Field cannot be blank",Toast.LENGTH_SHORT).show();
               }
               else {
                   Boolean result= DB.userLogin(s1,s2);
                   if (result==true){
                       Toast.makeText(getApplication(),"login successfull",Toast.LENGTH_SHORT).show();
                       Intent intent=new Intent(getApplication(), userHomePage.class);
                       intent.putExtra("username",s1);
                       startActivity(intent);
                   }
                   else {
                       Toast.makeText(getApplication(),"login failed, Invalid Credential or Admin haven't Approved access",Toast.LENGTH_LONG).show();
                   }
               }
            }
        });
    }
    public void gotoRegister(View v){
        Intent intent=new Intent(getApplication(),UserRegister.class);
        startActivity(intent);
    }
}