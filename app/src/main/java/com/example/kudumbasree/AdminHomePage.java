package com.example.kudumbasree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.kudumbasree.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AdminHomePage extends AppCompatActivity {

    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_page);
        Intent intent=getIntent();

        username=intent.getStringExtra("username");
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
            intent.putExtra("username",username);
            startActivity(intent);
        }
        if(item.getItemId()==R.id.logout){
            Intent intent=new Intent(this, MainActivity.class);
            finish();
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    public void adminMemberslist(View v){
        Intent intent=new Intent("act.memberslistAdmin");
        intent.putExtra("username",username);
        startActivity(intent);
    }
    public void newRequestAdmin(View v){
        Intent intent=new Intent("act.membersrequest");
        intent.putExtra("username",username);
        startActivity(intent);
    }
    public void loanRequest(View v){
        Intent intent=new Intent("act.loanrequest");
        intent.putExtra("username",username);
        startActivity(intent);
    }
    public void monthlyAudit(View v){
        Intent intent=new Intent("act.monthlyAudit");
        intent.putExtra("username",username);
        startActivity(intent);
    }

    public void balanceAdmin(View v){
        Intent intent=new Intent("act.balanceAdmin");
        intent.putExtra("username",username);
        startActivity(intent);
    }
}