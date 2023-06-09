package com.example.myroute;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result2);
        TextView val1 = findViewById(R.id.val1);
        TextView val2 = findViewById(R.id.val2);
        TextView val3 = findViewById(R.id.val3);
        TextView val4 = findViewById(R.id.val4);
        TextView rout1 = findViewById(R.id.rout1);
        TextView rout2 = findViewById(R.id.rout2);
        TextView rout3 = findViewById(R.id.rout3);
        TextView rout4 = findViewById(R.id.rout4);
        TextView l1 = findViewById(R.id.l1);
        TextView l2 = findViewById(R.id.l2);
        TextView l3 = findViewById(R.id.l3);
        TextView l4 = findViewById(R.id.l4);
        ArrayList<ArrayList<String>> ways = new ArrayList<>();
        ArrayList<Double> values = new ArrayList<>();
        ArrayList<Double> lengths = new ArrayList<>();

        Bundle arguments = getIntent().getExtras();
        if(arguments!=null){
            System.out.println(arguments);
            ways = (ArrayList<ArrayList<String>>) arguments.get("ways");
            values = (ArrayList<Double>) arguments.get("values");
            lengths = (ArrayList<Double>) arguments.get("lengths");
            System.out.println(ways);
            System.out.println(values);
            System.out.println(lengths);
        }
        if (lengths.size()!=0){
            val1.setText(String.valueOf(values.get(0)));
            l1.setText(String.valueOf(lengths.get(0)));
            rout1.setText(way_toString(ways.get(0)));
            values.remove(0);
            lengths.remove(0);
            ways.remove(0);
        }
        if (lengths.size()!=0){
            val2.setText(String.valueOf(values.get(0)));
            l2.setText(String.valueOf(lengths.get(0)));
            rout2.setText(way_toString(ways.get(0)));
            values.remove(0);
            lengths.remove(0);
            ways.remove(0);
        }
        if (lengths.size()!=0){
            val3.setText(String.valueOf(values.get(0)));
            l3.setText(String.valueOf(lengths.get(0)));
            rout3.setText(way_toString(ways.get(0)));
            values.remove(0);
            lengths.remove(0);
            ways.remove(0);
        }
        if (lengths.size()!=0){
            val4.setText(String.valueOf(values.get(0)));
            l4.setText(String.valueOf(lengths.get(0)));
            rout4.setText(way_toString(ways.get(0)));
            values.remove(0);
            lengths.remove(0);
            ways.remove(0);
        }
    }

    public static String way_toString(ArrayList<String> ways){
        String r = "";
        for (String way: ways){
            r = r+way+" - ";
        }
        r = r.substring(0,r.length()-2);
        return r;
    }
}