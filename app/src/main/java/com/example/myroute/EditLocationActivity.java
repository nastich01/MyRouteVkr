package com.example.myroute;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myroute.data_base.Location;
import com.example.myroute.data_base.LocationDBAdapter;

public class EditLocationActivity extends AppCompatActivity {

    private EditText nameBox;
    private EditText latBox;
    private EditText longBox;
    private Button delButton;

    private LocationDBAdapter adapter;
    private long storeId =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_location);

        nameBox = findViewById(R.id.name);
        latBox = findViewById(R.id.latitude);
        longBox = findViewById(R.id.longitude);

        delButton = findViewById(R.id.deleteButton);
        adapter = new LocationDBAdapter(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            storeId = extras.getLong("id");
        }
        // если 0, то добавление
        if (storeId > 0) {
            // получаем элемент по id из бд
            adapter.open();
            Location store = adapter.getStore(storeId);
            System.out.println(storeId);
            nameBox.setText(store.getName());
            latBox.setText(String.valueOf(store.getLatitude()));
            longBox.setText(String.valueOf(store.getLongitude()));
            adapter.close();
        } else {
            // скрываем кнопку удаления
            delButton.setVisibility(View.GONE);
        }

    }

    public void save(View view){

        String name = nameBox.getText().toString();
        double latitude = Double.parseDouble(latBox.getText().toString());
        double longitude = Double.parseDouble(longBox.getText().toString());

        Location store = new Location(storeId, name, latitude, longitude);

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
        Intent intent = new Intent(this, ListLocationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}