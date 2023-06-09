package com.example.myroute.expert_system;

public class Parameters {
    //видимость
    public static double visibility_good(double x){
        return AccessoryFunction.right(4,5,7,8, x);
    }
    public static double visibility_norm(double x){
        return AccessoryFunction.middle(4,5,7,8, x);
    }
    public static double visibility_bad(double x){
        return AccessoryFunction.left(4,5,7,8, x);
    }

    //ветер
    public static double strong_wind(double x){ return AccessoryFunction.right(5,7,12,15, x); }
    public static double medium_wind(double x){
        return AccessoryFunction.middle(5,7,12,15, x);
    }
    public static double light_wind(double x){
        return AccessoryFunction.left(5,7,12,15, x);
    }

    //дождь
    public static double strong_rain(double x){ return AccessoryFunction.right(0.75,2.5,2.75,4, x); }
    public static double medium_rain(double x){
        return AccessoryFunction.middle(1.5,2.5,2.75,4, x);
    }
    public static double light_rain(double x){
        return AccessoryFunction.left(0.75,2.5,2.75,4, x);
    }

    //снег
    public static double strong_snow(double x){ return AccessoryFunction.right(0.75,2.5,2.75,4, x); }
    public static double medium_snow(double x){
        return AccessoryFunction.middle(1.5,2.5,2.75,4, x);
    }
    public static double light_snow(double x){
        return AccessoryFunction.left(0.75,2.5,2.75,4, x);
    }

    //лед
    public static double ice_yes(double x){ return AccessoryFunction.right(0.1,0.9, x); }
    public static double ice_no(double x){
        return AccessoryFunction.left(0.1,0.9, x);
    }

    //дни недели
    public static double weekends(double x){ return AccessoryFunction.right(0,0.5,0.75,2, x); }
    public static double monday_friday (double x){ return AccessoryFunction.middle(0,0.5,0.75,2, x); }
    public static double tues_thursday (double x){
        return AccessoryFunction.left(0,0.5,0.75,2, x);
    }

    //загруженность
    public static double heavy_traffic(double x){ return AccessoryFunction.left(0.4,0.5,0.7,0.85, x); }
    public static double medium_traffic(double x){
        return AccessoryFunction.middle(0.4,0.5,0.7,0.85, x);
    }
    public static double light_traffic(double x){
        return AccessoryFunction.right(0.4,0.5,0.7,0.85, x);
    }

    //стоимость проезда
    public static double paid_yes(double x){ return AccessoryFunction.right(0.1,0.9, x); }
    public static double paid_no(double x){
        return AccessoryFunction.left(0.1,0.9, x);
    }

    //средняя скорость
    public static double high_speed(double x){ return AccessoryFunction.right(60,70,90,110, x); }
    public static double medium_speed(double x){
        return AccessoryFunction.middle(60,70,90,110, x);
    }
    public static double low_speed(double x){
        return AccessoryFunction.left(60,70,90,110, x);
    }

    //время суток
    public static double morning_evening(double x){ return AccessoryFunction.right(4,10,15,18, x); }
    public static double day (double x){ return AccessoryFunction.middle(4,10,15,18, x); }
    public static double night (double x){
        return AccessoryFunction.left(4,10,15,18, x);
    }
}
