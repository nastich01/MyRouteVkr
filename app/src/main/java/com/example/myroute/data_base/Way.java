package com.example.myroute.data_base;

import com.example.myroute.EditWayActivity;

public class Way {
    private long id;
    private int from_loc;
    private int in_loc;
    private int way_length;
    private double average_speed;
    private double cost;
    private double weight_limit;
    private double height_limit;
    private int max_speed;
    private double road_capacity;

    public Way(long id, int from_loc, int in_loc, int way_length, double average_speed, double cost, double weight_limit, double height_limit, int max_speed, double road_capacity) {
        this.id = id;
        this.from_loc = from_loc;
        this.in_loc = in_loc;
        this.way_length = way_length;
        this.average_speed = average_speed;
        this.cost = cost;
        this.weight_limit = weight_limit;
        this.height_limit = height_limit;
        this.max_speed = max_speed;
        this.road_capacity = road_capacity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getFrom_loc() {
        return from_loc;
    }

    public void setFrom_loc(int from_loc) {
        this.from_loc = from_loc;
    }

    public int getIn_loc() {
        return in_loc;
    }

    public void setIn_loc(int in_loc) {
        this.in_loc = in_loc;
    }

    public int getWay_length() {
        return way_length;
    }

    public void setWay_length(int way_length) {
        this.way_length = way_length;
    }

    public double getAverage_speed() {
        return average_speed;
    }

    public void setAverage_speed(double average_speed) {
        this.average_speed = average_speed;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getWeight_limit() {
        return weight_limit;
    }

    public void setWeight_limit(double weight_limit) {
        this.weight_limit = weight_limit;
    }

    public double getHeight_limit() {
        return height_limit;
    }

    public void setHeight_limit(double height_limit) {
        this.height_limit = height_limit;
    }

    public int getMax_speed() {
        return max_speed;
    }

    public void setMax_speed(int max_speed) {
        this.max_speed = max_speed;
    }

    public double getRoad_capacity() {
        return road_capacity;
    }

    public void setRoad_capacity(double road_capacity) {
        this.road_capacity = road_capacity;
    }

    @Override
    public String toString() {
        return this.from_loc +
                "\n"+this.in_loc;

    }

}
