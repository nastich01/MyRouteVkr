package com.example.myroute.expert_system;

public class AccessoryFunction {
    public static double left(double a, double b, double c, double d, double x){
        if (x<=a){
            return 1;}
        if (x>=b){
            return 0;}
        return ((double)(b-x)/(b-a));
    }

    public static double middle(double a, double b, double c, double d, double x){
        if ((x<=a)||(x>=d)){
            return 0;}
        if ((x>=b)&&(x<=c)){
            return 1;}
        if ((x<=b)&&(x>=a)) return ((double)(x-a)/(b-a));
        return ((double)(d-x)/(d-c));
    }

    public static double right(double a, double b, double c, double d, double x){
        if (x<=c)
            return 0;
        if (x>=d)
            return 1;
        return ((double)(x-c)/(d-c));
    }


    public static double left(double a, double b, double x){
        if (x<=a){
            return 1;}
        if (x>=b){
            return 0;}
        return ((double)(b-x)/(b-a));
    }

    public static double right(double a, double b, double x){
        if (x<=a)
            return 0;
        if (x>=b)
            return 1;
        return ((double)(x-a)/(b-a));
    }

}
