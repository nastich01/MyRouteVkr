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
import com.example.myroute.data_base.Way;
import com.example.myroute.data_base.WayDBAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListWayActivity extends AppCompatActivity {

    private ListView wayList;
    ArrayAdapter<Ways> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_way);

        wayList = findViewById(R.id.list);

        wayList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Ways store =arrayAdapter.getItem(position);
                if(store!=null) {
                    Intent intent = new Intent(getApplicationContext(), EditWayActivity.class);
                    intent.putExtra("id", store.getId());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        WayDBAdapter adapter = new WayDBAdapter(this);
        adapter.open();
        LocationDBAdapter adapter1 = new LocationDBAdapter(this);
        adapter1.open();

        List<Way> stores = adapter.getStores();
        List <Ways> ways = new ArrayList<>();
        for (int i=0; i<stores.size(); i++){
            ways.add(new Ways(adapter1.getStore(stores.get(i).getFrom_loc()).getName(), adapter1.getStore(stores.get(i).getIn_loc()).getName(), stores.get(i).getFrom_loc(), stores.get(i).getIn_loc(), stores.get(i).getId()));
            System.out.println(ways.get(ways.size()-1)+" "+ways.get(ways.size()-1).id);
        }
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ways);
        wayList.setAdapter(arrayAdapter);
        adapter.close();
    }

    public void add(View view){
        Intent intent = new Intent(this, EditWayActivity.class);
        startActivity(intent);
    }
    public void goBack(View view){
        // переход к главной activity
        Intent intent = new Intent(this, AdminActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public class Ways{
        String from;
        String in;
        int fromIndex, inIndex;
        long id;

        public Ways(String from, String in, int fromIndex, int inIndex, long id) {
            this.from = from;
            this.in = in;
            this.fromIndex = fromIndex;
            this.inIndex = inIndex;
            this.id = id;
        }

        @Override
        public String toString() {
            return this.from +
                    "\n"+this.in;
        }
        public long getId() {
            return id;
        }
    }
}