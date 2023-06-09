package com.example.myroute.expert_system;

import java.util.ArrayList;
import java.util.Collections;

public class Aggregation {
    public static ArrayList<Double> aggregator(ArrayList<Double> b, int size){
        ArrayList<Double> c = new ArrayList<>();
        int i=0;
        int j=0;

        while(i<size){
            ArrayList<Double> nowArray = new ArrayList<Double>();
            nowArray.add(b.get(j));
            nowArray.add(b.get(j+1));
            nowArray.add(b.get(j+2));
            nowArray.add(b.get(j+3));
            nowArray.add(b.get(j+4));
            nowArray.add(b.get(j+5));
            nowArray.add(b.get(j+6));
            nowArray.add(b.get(j+7));
            nowArray.add(b.get(j+8));
            nowArray.add(b.get(j+9));
            c.add((double) Collections.max(nowArray));
            j+=10;
            i++;
        }
        return c;
    }
}
