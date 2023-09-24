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

public class AdminChangePassword extends AppCompatActivity {

    DBHelperAdmin DB=new DBHelperAdmin(this);;
    Button change;
    EditText oldpass,newpass,confirmpass;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_change_password);
        Intent intent=getIntent();
        String username=intent.getStringExtra("username");
        oldpass=findViewById(R.id.txt_orginalpass);
        newpass=findViewById(R.id.txt_newPassword);
        confirmpass=findViewById(R.id.txt_confirmPass);
        change=findViewById(R.id.updatepassbtn);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newpass.getText().toString().equals("")||confirmpass.getText().toString().equals("")||oldpass.getText().toString().equals("")){
                    Toast.makeText(AdminChangePassword.this, "Fields can't be blank", Toast.LENGTH_SHORT).show();
                }
                if (newpass.getText().toString().equals(confirmpass.getText().toString())) {
                    Boolean result=DB.changePassword(username.toString(),oldpass.getText().toString(),newpass.getText().toString());
                    if (result){
                        Toast.makeText(getApplication(),"Password Changed",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplication(),"Error in changing password",Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Toast.makeText(getApplication(),"Password Doesn't Match",Toast.LENGTH_SHORT).show();
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
        if (item.getItemId()==R.id.changePass){
            Intent intent=new Intent("act.adminPasschange");
            startActivity(intent);
        }
        if (item.getItemId()==R.id.exit){
            finishAffinity();
        }
        if (item.getItemId()==R.id.logout){
            Intent intent=new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}