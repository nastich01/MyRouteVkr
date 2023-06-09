package com.example.myroute;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.myroute.data_base.Location;
import com.example.myroute.data_base.LocationDBAdapter;

import java.util.List;

public class ListLocationActivity extends AppCompatActivity {

    private ListView locList;
    ArrayAdapter<Location> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_location);

        locList = findViewById(R.id.list);

        locList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Location store =arrayAdapter.getItem(position);
                if(store!=null) {
                    Intent intent = new Intent(getApplicationContext(), EditLocationActivity.class);
                    intent.putExtra("id", store.getId());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        LocationDBAdapter adapter = new LocationDBAdapter(this);
        adapter.open();

        List<Location> stores = adapter.getStores();

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stores);
        locList.setAdapter(arrayAdapter);
        adapter.close();
    }
    // по нажатию на кнопку запускаем Activity для добавления данных
    public void add(View view){
        Intent intent = new Intent(this, EditLocationActivity.class);
        startActivity(intent);
    }
    public void goBack(View view){
        // переход к главной activity
        Intent intent = new Intent(this, AdminActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}