package com.example.kudumbasree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

public class User_daily_collectionPage extends AppCompatActivity {

    CalendarView calender;
    EditText Epay;
    Button pay;
    String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_daily_collection_page);

        CalendarView calendar = findViewById(R.id.calendarView);
        calendar.setMaxDate(System.currentTimeMillis());

        EditText Epay = findViewById(R.id.txt_payingamount);
        Button pay = findViewById(R.id.btn_payAmount);
        DBDailyCollection DB = new DBDailyCollection(this);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");


        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // Update the date when a day is selected
                date = String.valueOf(dayOfMonth)+"/"+String.valueOf(month)+"/"+String.valueOf(year); // Adding 1 to month because it is zero-based
            }
        });

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String money = Epay.getText().toString();

               Boolean dataResult= DB.getDataInCollection(username,date);
                if (dataResult){
                    Toast.makeText(getApplicationContext(), "You've already Made an Entry on this day", Toast.LENGTH_SHORT).show();
                }
                else {
                    Boolean result = DB.insertData(username, date, money);
                    if (result) {
                        Toast.makeText(getApplicationContext(), "Today's Collection Has been Updated", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Some Error has Occurred", Toast.LENGTH_SHORT).show();
                    }
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