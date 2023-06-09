package com.example.myroute.data_base;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class LocationDBAdapter {
    private DataBaseHelper dbHelper;
    private SQLiteDatabase database;

    public LocationDBAdapter(Context context){
        dbHelper = new DataBaseHelper(context.getApplicationContext());
    }

    public LocationDBAdapter open(){
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    private Cursor getAllEntries(){
        String[] columns = new String[] {DataBaseHelper.COLUMN_ID, DataBaseHelper.COLUMN_NAME, DataBaseHelper.COLUMN_LATITUDE, DataBaseHelper.COLUMN_LONGITUDE};
        return  database.query(DataBaseHelper.LOCTABLE, columns, null, null, null, null, null);
    }

    public List<Location> getStores(){
        ArrayList<Location> locations = new ArrayList<>();
        Cursor cursor = getAllEntries();
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_NAME));
            double latitude = cursor.getDouble(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_LATITUDE));
            double longitude = cursor.getDouble(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_LONGITUDE));
            locations.add(new Location(id, name, latitude, longitude));
        }
        cursor.close();
        return locations;
    }

    public long getCount(){
        return DatabaseUtils.queryNumEntries(database, DataBaseHelper.LOCTABLE);
    }

    public Location getStore(long id){
        Location location = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?",DataBaseHelper.LOCTABLE, DataBaseHelper.COLUMN_ID);
        Cursor cursor = database.rawQuery(query, new String[]{ String.valueOf(id)});
        if(cursor.moveToFirst()){
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_NAME));
            double latitude = cursor.getDouble(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_LATITUDE));
            double longitude = cursor.getDouble(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_LONGITUDE));
            location = new Location(id, name, latitude, longitude);
        }
        cursor.close();
        return location;
    }

    public long insert(Location location){

        ContentValues cv = new ContentValues();
        cv.put(DataBaseHelper.COLUMN_NAME, location.getName());
        cv.put(DataBaseHelper.COLUMN_LATITUDE, location.getLatitude());
        cv.put(DataBaseHelper.COLUMN_LONGITUDE, location.getLongitude());

        return  database.insert(DataBaseHelper.LOCTABLE, null, cv);
    }

    public long delete(long id){

        String whereClause = "_id = ?";
        String[] whereArgs = new String[]{String.valueOf(id)};
        return database.delete(DataBaseHelper.LOCTABLE, whereClause, whereArgs);
    }

    public long update(Location location){

        String whereClause = DataBaseHelper.COLUMN_ID + "=" + location.getId();
        ContentValues cv = new ContentValues();
        cv.put(DataBaseHelper.COLUMN_NAME, location.getName());
        cv.put(DataBaseHelper.COLUMN_LATITUDE, location.getLatitude());
        cv.put(DataBaseHelper.COLUMN_LONGITUDE, location.getLongitude());
        return database.update(DataBaseHelper.LOCTABLE, cv, whereClause, null);
    }
}
