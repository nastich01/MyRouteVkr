package com.example.myroute;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.time.LocalDate;

public class DateActivity extends AppCompatActivity {
    int year_x=-1, month_x=-1, day_x=-1;
    Button savedatebutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);
        savedatebutton = findViewById(R.id.savedatebutton);
        DatePicker datePicker = this.findViewById(R.id.datePicker);
        // Месяц начиная с нуля. Для отображения добавляем 1.
        datePicker.init(LocalDate.now().getYear(), LocalDate.now().getMonthValue()-1, LocalDate.now().getDayOfMonth(), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                year_x = view.getYear();
                month_x = view.getMonth() + 1;
                day_x = view.getDayOfMonth();

            }
        });



    }

    public void save_date(View view){
        Intent intent = new Intent(getApplicationContext(), TimeXActivity.class);
        if ((year_x!=-1)&&(month_x!=-1)&&(day_x!=-1)) {
            intent.putExtra("year", year_x);
            intent.putExtra("month", month_x);
            intent.putExtra("day", day_x);
        }
        startActivity(intent);
    }

    public void goAdmin(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}