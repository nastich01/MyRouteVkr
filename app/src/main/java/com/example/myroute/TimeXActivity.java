package com.example.myroute;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;

import java.util.Date;

public class TimeXActivity extends AppCompatActivity {
    int h=-1, min=-1;
    Date date_x;
    int year_x=-1, month_x=-1, day_x=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        TimePicker timePicker = findViewById(R.id.timePicker);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                h = view.getHour();
                min = view.getMinute();
            }
        });

        Bundle arguments = getIntent().getExtras();
        if(arguments!=null){
            System.out.println(arguments);
            year_x = (int) arguments.get("year");
            month_x = (int) arguments.get("month");
            day_x = (int) arguments.get("day");
            System.out.println(year_x+" "+month_x+" "+day_x);
        }
        //date_x = new Date(year_x,month_x,day_x);
        //System.out.println(date_x);
    }

    public void save_time(View view){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        if ((h!=-1)&&(min!=-1)) {
            intent.putExtra("hour", h);
            intent.putExtra("minute", min);
            intent.putExtra("year", year_x);
            intent.putExtra("month", month_x);
            intent.putExtra("day", day_x);
        }
        startActivity(intent);
    }

    public void goChangeDate(View view){
        Intent intent = new Intent(this, DateActivity.class);
        startActivity(intent);
    }
}