package com.example.myroute;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.myroute.data_base.Location;
import com.example.myroute.data_base.LocationDBAdapter;
import com.example.myroute.data_base.Way;
import com.example.myroute.data_base.WayDBAdapter;

import java.util.ArrayList;
import java.util.List;

public class EditWayActivity extends AppCompatActivity {

    private Spinner fromBox;
    private Spinner inBox;
    private EditText waylengthBox;
    private EditText averagespeedBox;
    private EditText costBox;
    private EditText weightBox;
    private EditText heightBox;
    private EditText maxspeedBox;
    private EditText roadcapacityBox;
    private Button delButton;

    private WayDBAdapter adapter;
    private LocationDBAdapter adapterLoc;
    private long storeId =0;

    List<Location> stores;
    ArrayList<String> locations = new ArrayList<>();
    ArrayList<Long> id_locs = new ArrayList<>();
    int fromIndex, inIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_way);

        fromBox = findViewById(R.id.from_loc);
        inBox = findViewById(R.id.in_loc);
        waylengthBox = findViewById(R.id.way_length);
        averagespeedBox = findViewById(R.id.average_speed);
        costBox = findViewById(R.id.cost);
        weightBox = findViewById(R.id.weight_limit);
        heightBox = findViewById(R.id.height_limit);
        maxspeedBox = findViewById(R.id.max_speed);
        roadcapacityBox = findViewById(R.id.road_capacity);

        delButton = findViewById(R.id.deleteButton);
        adapter = new WayDBAdapter(this);
        adapterLoc = new LocationDBAdapter(this);

        LocationDBAdapter locationDBAdapter = new LocationDBAdapter(this);
        locationDBAdapter.open();
        stores = locationDBAdapter.getStores();
        locationDBAdapter.close();
        for (int i=0;i<stores.size();i++){
            locations.add(i,stores.get(i).getName());
            id_locs.add(stores.get(i).getId());
        }
        ArrayAdapter<String> aradapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, locations);
        aradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromBox.setAdapter(aradapter);
        inBox.setAdapter(aradapter);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            storeId = extras.getLong("id");
        }
        // если 0, то добавление
        if (storeId > 0) {
            // получаем элемент по id из бд
            adapter.open();
            adapterLoc.open();
            Way store = adapter.getStore(storeId);
            //fromBox.setText(String.valueOf(adapterLoc.getStore(store.getFrom_loc()).getName()));
            /*inBox.setText(String.valueOf(store.getIn_loc()));*/
            int i = locations.indexOf(String.valueOf(adapterLoc.getStore(store.getFrom_loc()).getName()));
            fromBox.setSelection(i);
            //inBox.setText(String.valueOf(adapterLoc.getStore(store.getIn_loc()).getName()));
            int j = locations.indexOf(String.valueOf(adapterLoc.getStore(store.getIn_loc()).getName()));
            inBox.setSelection(j);
            waylengthBox.setText(String.valueOf(store.getWay_length()));
            averagespeedBox.setText(String.valueOf(store.getAverage_speed()));
            costBox.setText(String.valueOf(store.getCost()));
            weightBox.setText(String.valueOf(store.getWeight_limit()));
            heightBox.setText(String.valueOf(store.getHeight_limit()));
            maxspeedBox.setText(String.valueOf(store.getMax_speed()));
            roadcapacityBox.setText(String.valueOf(store.getRoad_capacity()));

            adapter.close();
        } else {
            // скрываем кнопку удаления
            delButton.setVisibility(View.GONE);
        }

        //получаем данные о выбранных пользователем городах
        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Получаем выбранный объект
                String item = (String)parent.getItemAtPosition(position);
                fromIndex = id_locs.get(locations.indexOf(item)).intValue();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        fromBox.setOnItemSelectedListener(itemSelectedListener);

        //получаем данные о выбранных пользователем городах
        AdapterView.OnItemSelectedListener itemSelectedListener1 = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Получаем выбранный объект
                String item = (String)parent.getItemAtPosition(position);
                inIndex = id_locs.get(locations.indexOf(item)).intValue();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        inBox.setOnItemSelectedListener(itemSelectedListener1);

    }

    public void save(View view){

        //int from_loc = Integer.parseInt(fromBox.getText().toString());
        int from_loc = fromIndex;
        //int in_loc = Integer.parseInt(inBox.getText().toString());
        int in_loc = inIndex;
        int way_length = Integer.parseInt(waylengthBox.getText().toString());
        double average_speed = Double.parseDouble(averagespeedBox.getText().toString());
        double cost = Double.parseDouble(costBox.getText().toString());
        double weight_limit = Double.parseDouble(weightBox.getText().toString());
        double height_limit = Double.parseDouble(heightBox.getText().toString());
        int max_speed = Integer.parseInt(maxspeedBox.getText().toString());
        double road_capacity = Double.parseDouble(roadcapacityBox.getText().toString());

        Way store = new Way(storeId, from_loc,in_loc,way_length,average_speed,cost,weight_limit,height_limit,max_speed,road_capacity);

        adapter.open();
        if (storeId > 0) {
            adapter.update(store);
        } else {
            adapter.insert(store);
        }
        adapter.close();
        goHome();
    }
    public void delete(View view){

        adapter.open();
        adapter.delete(storeId);
        adapter.close();
        goHome();
    }
    private void goHome(){
        // переход к главной activity
        Intent intent = new Intent(this, ListWayActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}