package com.example.myroute;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.example.myroute.data_base.Location;
import com.example.myroute.data_base.LocationDBAdapter;
import com.example.myroute.data_base.Way;
import com.example.myroute.data_base.WayDBAdapter;
import com.example.myroute.expert_system.Aggregation;
import com.example.myroute.expert_system.Defuzzification;
import com.example.myroute.expert_system.Fuzzification;
import com.example.myroute.expert_system.RuleBase;
import com.example.myroute.yen_algorithm.Path;
import com.example.myroute.yen_algorithm.VariableGraph;
import com.example.myroute.yen_algorithm.YenTopKShortestPathsAlg;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    List<Location> stores;
    List<Way> stores1;
    ArrayList<String> locations = new ArrayList<>();
    ArrayList<Long> id_locs = new ArrayList<>();
    ArrayList<ArrayList<String>> ways = new ArrayList<>();
    ArrayList<Double> lengths = new ArrayList<>();
    List<Path> paths= new ArrayList<>();
    List<Path> paths_before_del ;
    int k_loc;
    Button search_button;
    RadioButton free_button;
    EditText height_val, weight_val;
    int from, in, number;
    int year_x, month_x, day_x, hour_x, min_x;
    Date date_x;
    private double latitude,longitude;
    double visibility, wind, rain, snow, traffic, day, speed, timeofday;
    int weather_id, ice, cost;
    ArrayList<Integer> costs = new ArrayList<Integer>();
    ArrayList<Double> speeds = new ArrayList<Double>();
    ArrayList<Double> results = new ArrayList<Double>();
    ArrayList<Double> values = new ArrayList<Double>();
    int results_size=0, index=0, index_i=0;
    ArrayList<Integer> sizes = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText dateBox = findViewById(R.id.dateBox);
        EditText timeBox = findViewById(R.id.timeBox);
        free_button = findViewById(R.id.radioButton);
        weight_val = findViewById(R.id.weight);
        height_val = findViewById(R.id.height);


        //формирование списка путей
        LocationDBAdapter locationDBAdapter = new LocationDBAdapter(this);
        locationDBAdapter.open();

        stores = locationDBAdapter.getStores();

        locationDBAdapter.close();
        for (int i=0;i<stores.size();i++){
            locations.add(i,stores.get(i).getName());
            id_locs.add(stores.get(i).getId());
        }
        System.out.println("locations: "+locations);

        k_loc = locations.size();
        System.out.println("Количество городов: "+ k_loc);
        WayDBAdapter wayDBAdapter = new WayDBAdapter(this);
        wayDBAdapter.open();
        stores1 = wayDBAdapter.getStores();
        wayDBAdapter.close();
        for (int i=0;i<stores1.size();i++){
            System.out.println(stores1.get(i));
        }




        Spinner spinner = findViewById(R.id.spinnerFrom);
        Spinner spinner1 = findViewById(R.id.spinnerIn);
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> aradapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, locations);
        // Определяем разметку для использования при выборе элемента
        aradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinner.setAdapter(aradapter);
        spinner1.setAdapter(aradapter);

        //запуск метода приска путей по нажатии кнопки
        number = 2;
        search_button = findViewById(R.id.expertButton);
        search_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean onlyfree = false;
                if (free_button.isChecked()) {
                    onlyfree = true;
                }
                double weight_limit=0;
                double height_limit=0;
                if (weight_val.getText().toString().length()==0) {
                    weight_limit = 0;
                    weight_val.setText("0");
                } else weight_limit = Double.parseDouble(weight_val.getText().toString());
                if (height_val.getText().toString().length()==0) {
                    height_limit = 0;
                    height_val.setText("0");
                } else height_limit = Double.parseDouble(height_val.getText().toString());
                System.out.println(onlyfree+" "+weight_limit+" "+height_limit);
                search_best_ways(stores,stores1, k_loc, from, in, number, onlyfree, weight_limit, height_limit);
            }
        });


        //получаем данные о выбранных пользователем городах
        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Получаем выбранный объект
                String item = (String)parent.getItemAtPosition(position);
                from = id_locs.get(locations.indexOf(item)).intValue();
                System.out.println("from"+from);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);

        AdapterView.OnItemSelectedListener itemSelectedListener1 = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Получаем выбранный объект
                String item = (String)parent.getItemAtPosition(position);
                in = id_locs.get(locations.indexOf(item)).intValue();
                System.out.println("in"+in);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        spinner1.setOnItemSelectedListener(itemSelectedListener1);

        //получаем данные о дате и времени
        Bundle arguments = getIntent().getExtras();
        if(arguments!=null){
            System.out.println(arguments);
            year_x = (int) arguments.get("year");
            month_x = (int) arguments.get("month");
            day_x = (int) arguments.get("day");
            hour_x = (int) arguments.get("hour");
            min_x = (int) arguments.get("minute");
            dateBox.setText(day_x+"."+month_x+"."+year_x);
            timeBox.setText(hour_x+":"+min_x);
            System.out.println(year_x+" "+month_x+" "+day_x+" "+hour_x+" "+min_x);
        }
        //System.out.println(date_x+" "+hour_x+" "+min_x);
        date_x = new Date(year_x,month_x,day_x);
    }

    public void goAdmin(View view){
        Intent intent = new Intent(this, AdminEnterActivity.class);
        startActivity(intent);
    }

    public void goChangeDate(View view){
        Intent intent = new Intent(this, DateActivity.class);
        startActivity(intent);
    }


    public void search_best_ways(List<Location> stores, List<Way> stores1, int k, int from, int in, int number, boolean onlyfree, double weight_limit, double height_limit){
        System.out.println(stores1);
        VariableGraph graph = new VariableGraph(stores1, k);
        YenTopKShortestPathsAlg alg1 = new YenTopKShortestPathsAlg(graph);
        //запускаем алгоритм подбора k кратчайших путей
        paths_before_del = alg1.get_shortest_paths(graph.get_vertex(from), graph.get_vertex(in),number);
        System.out.println("пути: "+paths_before_del);


        //удаляем пути не подходящие по стоимости, по весовому и высотному лимиту
        for (int z=0; z<paths_before_del.size(); z++){
            boolean check = true;
            //ArrayList<Integer> del = new ArrayList<>();
            System.out.println(paths_before_del.get(z).get_vertices().size()-1);
            for (int i=0; i<paths_before_del.get(z).get_vertices().size()-1; i++){
                int from_loc = paths_before_del.get(z).get_vertices().get(i).get_id();
                int in_loc = paths_before_del.get(z).get_vertices().get(i+1).get_id();
                for (int j=0; j<stores1.size(); j++){
                    if ((stores1.get(j).getFrom_loc()==from_loc) && (stores1.get(j).getIn_loc()==in_loc)){
                        if (onlyfree){
                            if ((stores1.get(j).getCost()!=0)||(stores1.get(j).getHeight_limit()<height_limit)||(stores1.get(j).getWeight_limit()<weight_limit)) check=false;
                        } else{
                            if ((stores1.get(j).getHeight_limit()<height_limit)||(stores1.get(j).getWeight_limit()<weight_limit)) check=false;
                        }
                    }
                }
            }
            //System.out.println(paths_before_del.get(z));
            if (check) paths.add(paths_before_del.get(z));
        }
        System.out.println("пути после удаления: "+paths);


        //формируем список путей (в строковом формате)
        for (int i=0;i<paths.size();i++){
            ArrayList<String> way = new ArrayList<>();
            int n=paths.get(i).get_vertices().size();
            //System.out.println("кол-во вершин в пути "+i+" :"+n);
            for (int j=0; j<n; j++){
                way.add(locations.get(id_locs.indexOf((long) paths.get(i).get_vertices().get(j).get_id())));
            }
            ways.add(way);
            lengths.add(paths.get(i).get_weight());
            System.out.println(way);
        }


        //запускаем экспертную систему по каждому пути и по каждому отрезку в пути
        for (int i=0; i<paths.size(); i++){ //по каждому пути

            int n=paths.get(i).get_vertices().size();
            results_size=results_size+n-1;
            sizes.add(n-1);
            System.out.println(n+" "+results_size);
            for (int j=0; j<n-1; j++){ //перебираем все вершины в пути

                int from_loc = paths.get(i).get_vertices().get(j).get_id();
                int in_loc = paths.get(i).get_vertices().get(j+1).get_id();

                //ищем нужные отрезки в базе
                for (int z=0; z<stores1.size(); z++){
                    if ((stores1.get(z).getFrom_loc()==from_loc) && (stores1.get(z).getIn_loc()==in_loc)){
                        cost = (int) stores1.get(z).getCost();
                        speed = stores1.get(z).getAverage_speed();
                        costs.add(cost);
                        speeds.add(speed);
                        System.out.println("СТОМОСТЬ И СКОРОСТЬ "+ cost + speed);
                    }
                }

                //день недели
                int day_of_week = getDayNumberOld(date_x);
                System.out.println(day_of_week);
                if ((day_of_week==1)||(day_of_week==7)) day=2;
                else if ((day_of_week==2)||(day_of_week==6)) day=1;
                else day=0;

                //время дня
                timeofday = hour_x;


                //получаем координаты
                LocationDBAdapter locationDBAdapter = new LocationDBAdapter(this);
                locationDBAdapter.open();
                latitude = locationDBAdapter.getStore(from_loc).getLatitude();
                longitude = locationDBAdapter.getStore(from_loc).getLongitude();
                locationDBAdapter.close();


                System.out.println("-----------путь "+i+"вершина"+j+"----------");
                //запрос на API
                //проверяем: есть ли прогноз на выбранную дату
                if ((month_x == LocalDate.now().getMonthValue()) && (day_x >= LocalDate.now().getDayOfMonth()) && (day_x <= LocalDate.now().getDayOfMonth()+4)){
                    loadLocation(latitude,longitude);
                } else{ //если нет - берем идеальные данные
                    double visibility=10000;
                    double wind=0;
                    double rain=0;
                    double snow=0;
                    int ice=0;
                    traffic = Math.random();
                    int costss = costs.get(0);
                    costs.remove(0);
                    double speedss = speeds.get(0);
                    speeds.remove(0);
                    double res = expert_evaluation(visibility, wind, rain, snow, ice, day,traffic,costss, speedss,timeofday);
                    results.add(res);
                    System.out.println("--------------ВРЕМЕННЫЕ РЕЗУЛЬТАТЫ"+results);
                    index=index+1;
                    if (index==results_size){
                        Intent intent = new Intent(this, ResultActivity.class);
                        intent.putExtra("res", results);
                        intent.putExtra("ways", ways);
                        intent.putExtra("lengths", lengths);
                        startActivity(intent);
                    }
                }
            }
            /*try {
                //Thread.sleep(5000); //Приостанавливает поток на 5 секунд
            } catch (Exception e) {
            }*/
            System.out.println("!!!!!!!!!!!!!!!!!!!!!RESULTS"+ results);
            //double res = results.stream().reduce((x, y) -> x + y).get()/results.size();
            //values.add((double) (Math.round(res*10)/10));
        }
        /*System.out.println(values);
        try {
            //Thread.sleep(1000); //Приостанавливает поток на 5 секунд
        } catch (Exception e) {
        }*/
        /*Intent intent = new Intent(this, ResultActivity.class);
        //intent.putExtra("values", values);
        intent.putExtra("ways", ways);
        intent.putExtra("lengths", lengths);
        startActivity(intent);*/

    }

    public static double expert_evaluation(double visibility, double wind, double rain, double snow, int ice, double day, double traffic, int cost, double speed, double timeofday){
        /*double visibility=5.5;
        double wind=4.3;
        double rain=12.5;
        double snow=0.3;
        int ice=0;
        double day=0.9;
        double traffic=0.45;
        int paid=1;
        double speed=30;
        double timeofday=0.6;*/
        double defRes=0;
        ArrayList<RuleBase> rules = RuleBase.genRules();
        ArrayList<Double> b = Fuzzification.fuzzifier(rules, visibility,wind,rain,snow,ice,day,traffic,cost,speed,timeofday);
        System.out.println(b);
        ArrayList<Double> c = Aggregation.aggregator(b,rules.size());
        System.out.println("==================");
        System.out.println(c);
        defRes = Defuzzification.defuzzier(c, rules);
        System.out.println("==================");
        System.out.println("defRes "+defRes);
        return defRes;
    }

    public static int getDayNumberOld(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    @SuppressLint("MissingPermission")
    private void loadLocation(double latitude, double longitude) {
        System.out.println("LOAD LOCATION "+latitude+"  "+longitude);
        String url = "https://api.openweathermap.org/data/2.5/forecast?lat=" + latitude + "&lon=" + longitude + "&appid=0cba86bdb8e1ce963ff29120d08c4f0b";
        new GetUrlData().execute(url);
    }


    private class GetUrlData extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... strings) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line;

                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                }
                System.out.println(buffer);
                return buffer.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null)
                    connection.disconnect();
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }
        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONObject obj = new JSONObject(result);
                //System.out.println(result);
                System.out.println("ГОРОД "+obj.getJSONObject("city").getString("name"));
                //System.out.println(latitude+" "+longitude);

                for (int i=0; i<40; i++){
                    if (hour_x%3 != 0) hour_x = hour_x + 3 - hour_x%3;
                    String dt_txt = obj.getJSONArray("list").getJSONObject(i).getString("dt_txt");
                    String d = month_x+"-"+day_x;
                    String h = " "+hour_x+":";
                    //System.out.println("ДАТА И ВРЕМЯ!!!!!!"+d+" "+h);
                    if ((dt_txt.contains(d)) && (dt_txt.contains(h))){
                        String vis = obj.getJSONArray("list").getJSONObject(i).getString("visibility");
                        visibility = Double.parseDouble(vis);
                        System.out.println("visibility="+visibility);

                        String w = obj.getJSONArray("list").getJSONObject(i).getJSONObject("wind").getString("speed");
                        wind = Double.parseDouble(w);
                        System.out.println("wind="+wind);

                        String weather_id = obj.getJSONArray("list").getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("id");
                        //weather_id = Integer.getInteger(weather);
                        System.out.println("weather="+weather_id);

                        /*String d = obj.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("description");
                        System.out.println("d="+d);*/

                        String rain1 = "230 231 300 500 611 613";
                        String rain2 = "200 301 302 310 321 501 520 612 615 620";
                        String rain3 = "201 311 312 313 502 521 616 621";
                        String rain4 = "202 232 314 503 504 511 522 531 622";

                        String snow1 = "600";
                        String snow2 = "601";
                        String snow3 = "611 612 613 615 616 620 621";
                        String snow4 = "602 622";

                        String ice1 = "511 611 613 615 616 620 621 622";

                        //дождь
                        if (rain1.contains(weather_id)) rain=1;
                        else if (rain2.contains(weather_id)) rain=2;
                        else if (rain3.contains(weather_id)) rain=3;
                        else if (rain4.contains(weather_id)) rain=4;
                        else rain=0;

                        //снег
                        if (snow1.contains(weather_id)) snow=1;
                        else if (snow2.contains(weather_id)) snow=2;
                        else if (snow3.contains(weather_id)) snow=3;
                        else if (snow4.contains(weather_id)) snow=4;
                        else snow=0;

                        //снег
                        if (ice1.contains(weather_id)) ice=1;
                        else ice=0;

                        //время дня
                        if ((hour_x>4)&&(hour_x<12)) timeofday = hour_x+11;

                        traffic = Math.random();

                        int costss = costs.get(0);
                        costs.remove(0);

                        double speedss = speeds.get(0);
                        speeds.remove(0);

                        System.out.println("    vis"+visibility);
                        System.out.println("    wind"+wind);
                        System.out.println("    rain"+rain);
                        System.out.println("    snow"+snow);
                        System.out.println("    ice"+ice);
                        System.out.println("    day"+day);
                        System.out.println("    traffic"+traffic);
                        System.out.println("    cost"+costss);
                        System.out.println("    speed"+speedss);
                        System.out.println("    timeofday"+timeofday);

                        double res = expert_evaluation(visibility, wind, rain, snow, ice, day, traffic, costss, speedss, timeofday);
                        results.add(res);
                        System.out.println("---------------------------ВРЕМЕННЫЕ РЕЗУЛЬТАТЫ "+ results);
                        index=index+1;
                        index_i=index_i+1;
                        if (index_i==sizes.get(0)){
                            values.add(results.stream().reduce((x, y) -> x + y).get()/results.size());
                            results.clear();
                            sizes.remove(0);
                            index_i=0;
                        }
                        System.out.println(results_size);
                        System.out.println(sizes.size());
                        //if (index==results_size){
                        if (sizes.size()==0){
                            System.out.println("Values "+values);
                            Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                            intent.putExtra("values", values);
                            intent.putExtra("ways", ways);
                            intent.putExtra("lengths", lengths);
                            startActivity(intent);
                        }
                    }
                }




            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public void goResults (View view){
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("values", values);
        intent.putExtra("ways", ways);
        intent.putExtra("lengths", lengths);
        startActivity(intent);
    }


}