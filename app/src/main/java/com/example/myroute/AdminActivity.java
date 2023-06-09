package com.example.myroute;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminActivity extends AppCompatActivity {

    Button mainButton;
    Button wayButton;
    Button locButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        mainButton = findViewById(R.id.mainButton);
        wayButton = findViewById(R.id.wayButton);
        locButton = findViewById(R.id.locButton);
    }

    public void goMain(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goWay(View view){
        Intent intent = new Intent(this, ListWayActivity.class);
        startActivity(intent);
    }

    public void goLoc(View view){
        Intent intent = new Intent(this, ListLocationActivity.class);
        startActivity(intent);
    }
}