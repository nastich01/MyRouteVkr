package com.example.myroute.data_base;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class WayDBAdapter {
    private DataBaseHelper dbHelper;
    private SQLiteDatabase database;

    public WayDBAdapter(Context context){
        dbHelper = new DataBaseHelper(context.getApplicationContext());
    }

    public WayDBAdapter open(){
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    private Cursor getAllEntries(){
        String[] columns = new String[] {DataBaseHelper.COLUMN_ID, DataBaseHelper.COLUMN_FROM, DataBaseHelper.COLUMN_IN, DataBaseHelper.COLUMN_WAYLENGTH, DataBaseHelper.COLUMN_AVERAGESPEED, DataBaseHelper.COLUMN_COST, DataBaseHelper.COLUMN_WEIGTHLIMIT, DataBaseHelper.COLUMN_HEIGHTLIMIT, DataBaseHelper.COLUMN_MAXSPEED, DataBaseHelper.COLUMN_ROADCAPACITY};
        return  database.query(DataBaseHelper.WAYTABLE, columns, null, null, null, null, null);
    }

    public List<Way> getStores(){
        ArrayList<Way> ways = new ArrayList<>();
        Cursor cursor = getAllEntries();
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_ID));
            int from_loc = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_FROM));
            int in_loc = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_IN));
            int way_length = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_WAYLENGTH));
            double average_speed = cursor.getDouble(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_AVERAGESPEED));
            double cost = cursor.getDouble(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_COST));
            double weight_limit = cursor.getDouble(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_WEIGTHLIMIT));
            double height_limit = cursor.getDouble(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_HEIGHTLIMIT));
            int max_speed = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_MAXSPEED));
            double road_capacity = cursor.getDouble(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_ROADCAPACITY));
            ways.add(new Way(id,from_loc,in_loc,way_length,average_speed,cost,weight_limit,height_limit,max_speed,road_capacity));
        }
        cursor.close();
        return  ways;
    }

    public long getCount(){
        return DatabaseUtils.queryNumEntries(database, DataBaseHelper.WAYTABLE);
    }

    public Way getStore(long id){
        Way way = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?",DataBaseHelper.WAYTABLE, DataBaseHelper.COLUMN_ID);
        Cursor cursor = database.rawQuery(query, new String[]{ String.valueOf(id)});
        if(cursor.moveToFirst()){
            int from_loc = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_FROM));
            int in_loc = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_IN));
            int way_length = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_WAYLENGTH));
            double average_speed = cursor.getDouble(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_AVERAGESPEED));
            double cost = cursor.getDouble(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_COST));
            double weight_limit = cursor.getDouble(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_WEIGTHLIMIT));
            double height_limit = cursor.getDouble(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_HEIGHTLIMIT));
            int max_speed = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_MAXSPEED));
            double road_capacity = cursor.getDouble(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_ROADCAPACITY));
            way = new Way(id,from_loc,in_loc,way_length,average_speed,cost,weight_limit,height_limit,max_speed,road_capacity);
        }
        cursor.close();
        return  way;
    }

    public long insert(Way way){

        ContentValues cv = new ContentValues();
        cv.put(DataBaseHelper.COLUMN_FROM, way.getFrom_loc());
        cv.put(DataBaseHelper.COLUMN_IN, way.getIn_loc());
        cv.put(DataBaseHelper.COLUMN_WAYLENGTH, way.getWay_length());
        cv.put(DataBaseHelper.COLUMN_AVERAGESPEED, way.getAverage_speed());
        cv.put(DataBaseHelper.COLUMN_COST, way.getCost());
        cv.put(DataBaseHelper.COLUMN_WEIGTHLIMIT, way.getWeight_limit());
        cv.put(DataBaseHelper.COLUMN_HEIGHTLIMIT, way.getHeight_limit());
        cv.put(DataBaseHelper.COLUMN_MAXSPEED, way.getMax_speed());
        cv.put(DataBaseHelper.COLUMN_ROADCAPACITY, way.getRoad_capacity());

        return  database.insert(DataBaseHelper.WAYTABLE, null, cv);
    }

    public long delete(long id){

        String whereClause = "_id = ?";
        String[] whereArgs = new String[]{String.valueOf(id)};
        return database.delete(DataBaseHelper.WAYTABLE, whereClause, whereArgs);
    }

    public long update(Way way){

        String whereClause = DataBaseHelper.COLUMN_ID + "=" + way.getId();
        ContentValues cv = new ContentValues();
        cv.put(DataBaseHelper.COLUMN_FROM, way.getFrom_loc());
        cv.put(DataBaseHelper.COLUMN_IN, way.getIn_loc());
        cv.put(DataBaseHelper.COLUMN_WAYLENGTH, way.getWay_length());
        cv.put(DataBaseHelper.COLUMN_AVERAGESPEED, way.getAverage_speed());
        cv.put(DataBaseHelper.COLUMN_COST, way.getCost());
        cv.put(DataBaseHelper.COLUMN_WEIGTHLIMIT, way.getWeight_limit());
        cv.put(DataBaseHelper.COLUMN_HEIGHTLIMIT, way.getHeight_limit());
        cv.put(DataBaseHelper.COLUMN_MAXSPEED, way.getMax_speed());
        cv.put(DataBaseHelper.COLUMN_ROADCAPACITY, way.getRoad_capacity());
        return database.update(DataBaseHelper.WAYTABLE, cv, whereClause, null);
    }
}
