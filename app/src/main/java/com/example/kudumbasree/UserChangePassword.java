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

public class UserChangePassword extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Button save;
        EditText oldPass,newPass,confPass;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_change_password);
        oldPass=(EditText) findViewById(R.id.txt_oldPass);
        newPass=(EditText) findViewById(R.id.txt_newPass);
        confPass=(EditText) findViewById(R.id.txt_confirmpass);
        save=findViewById(R.id.updatepassbtn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(oldPass.getText().toString().equals("") || newPass.getText().toString().equals("")||confPass.getText().toString().equals("")){
                    Toast.makeText(getApplication(),"Field Cant be blank",Toast.LENGTH_SHORT).show();
                }
                if (confPass.getText().toString().equals(newPass.getText().toString())==false) {
                    Toast.makeText(getApplication(),"Password Doesn't match",Toast.LENGTH_SHORT).show();
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