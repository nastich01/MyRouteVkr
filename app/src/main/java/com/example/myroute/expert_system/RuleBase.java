package com.example.myroute.expert_system;

import java.util.ArrayList;

public class RuleBase {
    String visibility;
    String wind;
    String rain;
    String snow;
    String ice;
    String day;
    String traffic;
    String paid;
    String speed;
    String time_of_day;
    double result;

    public RuleBase(String visibility, String wind, String rain, String snow, String ice, String day, String traffic, String paid, String speed, String time_of_day, double result) {
        this.visibility = visibility;
        this.wind = wind;
        this.rain = rain;
        this.snow = snow;
        this.ice = ice;
        this.day = day;
        this.traffic = traffic;
        this.paid = paid;
        this.speed = speed;
        this.time_of_day = time_of_day;
        this.result = result;
    }

    public double visibility_res(double x){
        double res;
        System.out.println("visibility_res "+x);
        if (this.visibility.equals("visibility_good")){
            res= Parameters.visibility_good(x);
            System.out.println("visibility_good");
        }
        else if  (this.visibility.equals("visibility_norm")){
            res= Parameters.visibility_norm(x);
            System.out.println("visibility_norm");
        }
        else{
            res= Parameters.visibility_bad(x);
            System.out.println("visibility_bad");
        }
        System.out.println(res);
        System.out.println("----");
        return res;
    }

    public double wind_res(double x){
        System.out.println("wind_res "+x);
        double res;
        if (this.wind.equals("strong_wind")){
            res= Parameters.strong_wind(x);
            System.out.println("strong_wind");
        }
        else if  (this.wind.equals("medium_wind")){
            res= Parameters.medium_wind(x);System.out.println("medium_wind");}
        else{
            res= Parameters.light_wind(x);System.out.println("light_wind");}
        System.out.println(res);
        System.out.println("----");
        return res;
    }

    public double rain_res(double x){
        System.out.println("rain_res "+x);
        double res;
        if (this.rain.equals("strong_rain")){
            res= Parameters.strong_rain(x);System.out.println("strong_rain");}
        else if  (this.rain.equals("medium_rain")){
            res= Parameters.medium_rain(x);System.out.println("medium_rain");}
        else{
            res= Parameters.light_rain(x);System.out.println("light_rain");}
        System.out.println(res);System.out.println("----");
        return res;
    }

    public double snow_res(double x){
        System.out.println("snow_res "+x);
        double res;
        if (this.snow.equals("strong_snow")){
            res= Parameters.strong_snow(x);System.out.println("strong_snow");}
        else if  (this.snow.equals("medium_snow")){
            res= Parameters.medium_snow(x);System.out.println("medium_snow");}
        else{
            res= Parameters.light_snow(x);System.out.println("light_snow");}
        System.out.println(res);System.out.println("----");
        return res;
    }

    public double ice_res(int x){
        System.out.println("ice_res "+x);
        double res;
        if (this.ice.equals("ice_yes")){
            res= Parameters.ice_yes(x);System.out.println("ice_yes");}
        else{
            res= Parameters.ice_no(x);System.out.println("ice_no");}
        System.out.println(res);System.out.println("----");
        return res;
    }

    public double day_res(double x){
        System.out.println("day_res "+x);
        double res;
        if (this.day.equals("weekends")){
            res= Parameters.weekends(x);System.out.println("weekends");}
        else if  (this.day.equals("monday_friday")){
            res= Parameters.monday_friday(x);System.out.println("monday_friday");}
        else{
            res= Parameters.tues_thursday(x);System.out.println("tues_thursday");}
        System.out.println(res);System.out.println("----");
        return res;
    }

    public double traffic_res(double x){
        System.out.println("traffic_res "+x);
        double res;
        if (this.traffic.equals("heavy_traffic")){
            res= Parameters.heavy_traffic(x);System.out.println("heavy_traffic");}
        else if  (this.traffic.equals("medium_traffic")){
            res= Parameters.medium_traffic(x);System.out.println("medium_traffic");}
        else{
            res= Parameters.light_traffic(x);System.out.println("light_traffic");}
        System.out.println(res);System.out.println("----");
        return res;
    }

    public double paid_res(int x){
        System.out.println("paid_res "+x);
        double res;
        if (this.paid.equals("paid_yes")){
            res= Parameters.paid_yes(x);System.out.println("paid_yes");}
        else{
            res= Parameters.paid_no(x);System.out.println("paid_no");}
        System.out.println(res);System.out.println("----");
        return res;
    }

    public double speed_res(double x){
        System.out.println("speed_res "+x);
        double res;
        if (this.speed.equals("high_speed")){
            res= Parameters.high_speed(x);System.out.println("high_speed");}
        else if  (this.speed.equals("medium_speed")){
            res= Parameters.medium_speed(x);System.out.println("medium_speed");}
        else{
            res= Parameters.low_speed(x);System.out.println("low_speed");}
        System.out.println(res);System.out.println("----");
        return res;
    }

    public double time_of_day_res(double x){
        System.out.println("time_of_day_res "+x);
        double res;
        if (this.time_of_day.equals("morning_evening")){
            res= Parameters.morning_evening(x);System.out.println("morning_evening");}
        else if (this.time_of_day.equals("day")){
            res= Parameters.day(x);System.out.println("day");}
        else{
            res= Parameters.night(x);System.out.println("night");}
        System.out.println(res);System.out.println("----");
        return res;
    }

    public double result_res(){
        return this.result;
    }

    public static ArrayList<RuleBase> genRules(){
        ArrayList<RuleBase> rules = new ArrayList<RuleBase>();


        rules.add(new RuleBase("visibility_bad","strong_wind","strong_rain","strong_snow","ice_yes","weekends","heavy_traffic","paid_yes","low_speed","morning_evening",5));
        rules.add(new RuleBase("visibility_norm","strong_wind","medium_rain","light_snow","ice_no","tues_thursday","heavy_traffic","paid_no","low_speed","morning_evening",3));
        rules.add(new RuleBase("visibility_bad","strong_wind","strong_rain","medium_snow","ice_no","tues_thursday","heavy_traffic","paid_no","low_speed","morning_evening",4));
        rules.add(new RuleBase("visibility_bad","light_wind","light_rain","strong_snow","ice_yes","weekends","light_traffic","paid_no","medium_speed","day",3));
        rules.add(new RuleBase("visibility_good","light_wind","light_rain","light_snow","ice_no","tues_thursday","medium_traffic","paid_no","medium_speed","morning_evening",1));
        rules.add(new RuleBase("visibility_good","medium_wind","light_rain","light_snow","ice_no","monday_friday","light_traffic","paid_yes","high_speed","morning_evening",2));
        rules.add(new RuleBase("visibility_good","light_wind","light_rain","light_snow","ice_no","monday_friday","light_traffic","paid_no","high_speed","night",0));
        rules.add(new RuleBase("visibility_good","light_wind","light_rain","light_snow","ice_no","tues_thursday","light_traffic","paid_no","high_speed","day",0));
        rules.add(new RuleBase("visibility_good","light_wind","light_rain","light_snow","ice_no","tues_thursday","light_traffic","paid_no","high_speed","night",0));


        return rules;
    }
}
