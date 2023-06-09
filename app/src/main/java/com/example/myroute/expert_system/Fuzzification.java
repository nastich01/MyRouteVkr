package com.example.myroute.expert_system;

import java.util.ArrayList;

public class Fuzzification {
    public static ArrayList<Double> fuzzifier(ArrayList<RuleBase> rules, double visib_x, double wind_x, double rain_x, double snow_x,
                                              int ice_x, double day_x, double traffic_x, int paid_x, double speed_x, double timeofday_x) {
        ArrayList<Double> b = new ArrayList();
        int j = 0;
        System.out.println("Фаззификация");
        while (j < rules.size()) {
            System.out.println("rule "+j);
            b.add(rules.get(j).visibility_res(visib_x));
            b.add(rules.get(j).wind_res(wind_x));
            b.add(rules.get(j).rain_res(rain_x));
            b.add(rules.get(j).snow_res(snow_x));
            b.add(rules.get(j).ice_res(ice_x));
            b.add(rules.get(j).day_res(day_x));
            b.add(rules.get(j).traffic_res(traffic_x));
            b.add(rules.get(j).paid_res(paid_x));
            b.add(rules.get(j).speed_res(speed_x));
            b.add(rules.get(j).time_of_day_res(timeofday_x));
            j++;
            System.out.println("++++++++++++++++++++++++++++++");
        }

        return b;
    }
}
