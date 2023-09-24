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
import android.widget.Toast;

import java.util.List;

public class User_Profile_page extends AppCompatActivity {

    DBHelperUser DB=new DBHelperUser(this);
    Button b1;
    EditText txtname,txtemail,txtusername,txtphone;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_page);
        b1=findViewById(R.id.btn_save);
        txtemail=(EditText)findViewById(R.id.txtemailUser);
        txtname=(EditText) findViewById(R.id.txtnameUser);
        txtphone=(EditText) findViewById(R.id.txtmobileUser);
        txtusername=(EditText) findViewById(R.id.txtusernameUser);

        Intent intent=getIntent();
        String username= intent.getStringExtra("username");
        txtusername.setText(username);

        List<userdata> list=DB.userdetailsUser(username);
        if (list.isEmpty()==false) {
            userdata ud = list.get(0);
            txtemail.setText(ud.email.toString());
            txtname.setText(ud.name.toString());
            txtphone.setText(ud.mobile.toString());
            // Set other TextViews as needed
        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Boolean result= DB.updateUserDetails(txtusername.getText().toString(),txtname.getText().toString(),txtemail.getText().toString(),txtphone.getText().toString());
               if (result==true){
                   Toast.makeText(getApplication(),"Changes Applied",Toast.LENGTH_SHORT).show();
               }
               else {
                   Toast.makeText(getApplication(),"Some Database Error Occured!!",Toast.LENGTH_SHORT).show();
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