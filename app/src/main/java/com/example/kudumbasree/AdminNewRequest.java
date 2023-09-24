package com.example.kudumbasree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AdminNewRequest extends AppCompatActivity {
    ListView lv;
    DBHelperUser DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_request);
        lv=findViewById(R.id.listviewNewReq);
        List<String> list=new ArrayList<>();
        DB=new DBHelperUser(this);
        list= DB.newRequest();
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, list);
        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str=lv.getAdapter().getItem(position).toString();
                AlertDialog.Builder myAlertBuilder=new AlertDialog.Builder(AdminNewRequest.this);
                myAlertBuilder.setTitle("Approve Request");
                myAlertBuilder.setMessage("Do you want to approve this user");
                myAlertBuilder.setCancelable(true);
                myAlertBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String str=lv.getAdapter().getItem(position).toString();
                       Boolean result= DB.updateAccess(str);
                       if (result){
                           finish();
                           startActivity(getIntent());
                           Toast.makeText(getApplication(),"Access Updated",Toast.LENGTH_LONG).show();
                       }
                       else {
                           Toast.makeText(getApplication(),"Error occured",Toast.LENGTH_LONG).show();
                       }
                    }
                });
                myAlertBuilder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplication(),"Request Rejected",Toast.LENGTH_LONG).show();
                    }
                });
                myAlertBuilder.show();

            }
        });
    }

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