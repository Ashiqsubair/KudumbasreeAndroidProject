package com.example.kudumbasree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class UsersProfileAdminpage extends AppCompatActivity {

    Button btnsave;
    Switch swh;
    TextView e1,e2,e3,e4,e5,e6;
    DBHelperUser DB;
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_profile_adminpage);
        btnsave=findViewById(R.id.btn_save);
        swh=findViewById(R.id.switch1);
        txt = findViewById(R.id.txt1);
        Intent intent = getIntent();
        String userName = intent.getStringExtra("name");

        // Check if userName is not null before setting it in the TextView
        if (userName != null) {
            txt.setText(userName);
        }

        DB = new DBHelperUser(this);
        List<userdata> list = DB.userdetails(userName);

        // Check if the list is not empty before accessing its elements
        if (!list.isEmpty()) {
            userdata ud = list.get(0);

            e1 = findViewById(R.id.txt1);
            e2 = findViewById(R.id.txt2);
            e3 = findViewById(R.id.txt3);
            e4 = findViewById(R.id.txt4);
            e5 = findViewById(R.id.txt5);

            e1.setText(ud.name.toUpperCase());
            e2.setText(ud.username);
            e3.setText(ud.unit);
            e4.setText(ud.email);
            e5.setText(ud.mobile);
            // Set other TextViews as needed
        }

        // Close the database when you're done with it
        DB.close();
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (swh.isChecked()==false){
                    DB.removeAccess(e2.getText().toString());
                    finish();
                    startActivity(getIntent());
                    finish();

                    Toast.makeText(getApplication(),"Access Removed",Toast.LENGTH_LONG).show();
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
        if (item.getItemId()==R.id.changePass){
            Intent intent=new Intent("act.adminPasschange");
            startActivity(intent);
        }
        if(item.getItemId()==R.id.logout){
            Intent intent=new Intent(this, MainActivity.class);
            finish();
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}