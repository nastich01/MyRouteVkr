package com.example.myroute.data_base;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

public class DataBaseHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "routs.db"; // название бд
    private static final int SCHEMA = 1; // версия базы данных

    static final String WAYTABLE = "way"; // название таблицы в бд
    // названия столбцов
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FROM = "from_loc";
    public static final String COLUMN_IN = "in_loc";
    public static final String COLUMN_WAYLENGTH = "way_length";
    public static final String COLUMN_AVERAGESPEED = "average_speed";
    public static final String COLUMN_COST = "cost";
    public static final String COLUMN_WEIGTHLIMIT = "weight_limit";
    public static final String COLUMN_HEIGHTLIMIT = "height_limit";
    public static final String COLUMN_MAXSPEED = "max_speed";
    public static final String COLUMN_ROADCAPACITY = "road_capacity";

    static final String LOCTABLE = "location"; // название таблицы в бд
    // названия столбцов
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_LATITUDE = "LATITUDE";
    public static final String COLUMN_LONGITUDE = "way_length";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Создание и заполнение таблицы location
        db.execSQL("CREATE TABLE " + LOCTABLE + " (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME
                + " TEXT, " + COLUMN_LATITUDE + " TEXT, "+ COLUMN_LONGITUDE + " TEXT);");
        // добавление начальных данных
        db.execSQL("INSERT INTO "+ LOCTABLE +" (" + COLUMN_NAME
                + ", " + COLUMN_LATITUDE +", "+COLUMN_LONGITUDE+ ") VALUES ('Чистополь','55.3631', '50.6424');");
        db.execSQL("INSERT INTO "+ LOCTABLE +" (" + COLUMN_NAME
                + ", "  + COLUMN_LATITUDE +", "+COLUMN_LONGITUDE+ ") VALUES ('Сорочьи горы','55.37520439999999', '49.9579134');");
        db.execSQL("INSERT INTO "+ LOCTABLE +" (" + COLUMN_NAME
                + ", " + COLUMN_LATITUDE +", "+COLUMN_LONGITUDE+ ") VALUES ('Сокуры','55.6178868', '49.3977443');");
        db.execSQL("INSERT INTO "+ LOCTABLE +" (" + COLUMN_NAME
                + ", " + COLUMN_LATITUDE +", "+COLUMN_LONGITUDE+ ") VALUES ('Шали','55.6828575', '49.662121');");
        db.execSQL("INSERT INTO "+ LOCTABLE +" (" + COLUMN_NAME
                + ", " + COLUMN_LATITUDE +", "+COLUMN_LONGITUDE+ ") VALUES ('Казань','55.7887', '49.1221');");
        db.execSQL("INSERT INTO "+ LOCTABLE +" (" + COLUMN_NAME
                + ", " + COLUMN_LATITUDE +", "+COLUMN_LONGITUDE+ ") VALUES ('Зеленодольск','55.84', '48.52');");
        db.execSQL("INSERT INTO "+ LOCTABLE +" (" + COLUMN_NAME
                + ", " + COLUMN_LATITUDE +", "+COLUMN_LONGITUDE+ ") VALUES ('Арск','56.08891', '49.8754019');");

        //Создание и заполнение таблицы way
        db.execSQL("CREATE TABLE " + WAYTABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_FROM + " TEXT, " + COLUMN_IN + " TEXT, "
                + COLUMN_WAYLENGTH + " INTEGER, " + COLUMN_AVERAGESPEED + " REAL, "
                + COLUMN_COST + " REAL, " + COLUMN_WEIGTHLIMIT + " REAL, "
                + COLUMN_HEIGHTLIMIT + " REAL, " + COLUMN_MAXSPEED + " INTEGER, " + COLUMN_ROADCAPACITY + " REAL, "
                + " FOREIGN KEY (from_loc)  REFERENCES location (_id)," + " FOREIGN KEY (in_loc)  REFERENCES location (_id));");
        // добавление начальных данных
        db.execSQL("INSERT INTO "+ WAYTABLE +" (" + COLUMN_FROM
                + ", " + COLUMN_IN +", "+COLUMN_WAYLENGTH +", " +COLUMN_AVERAGESPEED
                + ", " + COLUMN_COST +", "+COLUMN_WEIGTHLIMIT + ", "+COLUMN_HEIGHTLIMIT
                + ", " + COLUMN_MAXSPEED +", "+COLUMN_ROADCAPACITY + ") VALUES (1,2,120,70.0,0,100,200,90,100);");
        db.execSQL("INSERT INTO "+ WAYTABLE +" (" + COLUMN_FROM
                + ", " + COLUMN_IN +", "+COLUMN_WAYLENGTH + ", "+COLUMN_AVERAGESPEED
                + ", " + COLUMN_COST +", "+COLUMN_WEIGTHLIMIT + ", "+COLUMN_HEIGHTLIMIT
                + ", " + COLUMN_MAXSPEED +", "+COLUMN_ROADCAPACITY + ") VALUES (1,3,210,70.0,0,100,200,90,100);");
        db.execSQL("INSERT INTO "+ WAYTABLE +" (" + COLUMN_FROM
                + ", " + COLUMN_IN +", "+COLUMN_WAYLENGTH + ", "+COLUMN_AVERAGESPEED
                + ", " + COLUMN_COST +", "+COLUMN_WEIGTHLIMIT + ", "+COLUMN_HEIGHTLIMIT
                + ", " + COLUMN_MAXSPEED +", "+COLUMN_ROADCAPACITY + ") VALUES (2,3,70,70.0,0,100,200,90,100);");
        db.execSQL("INSERT INTO "+ WAYTABLE +" (" + COLUMN_FROM
                + ", " + COLUMN_IN +", "+COLUMN_WAYLENGTH + ", "+COLUMN_AVERAGESPEED
                + ", " + COLUMN_COST +", "+COLUMN_WEIGTHLIMIT + ", "+COLUMN_HEIGHTLIMIT
                + ", " + COLUMN_MAXSPEED +", "+COLUMN_ROADCAPACITY + ") VALUES (3,1,210,70.0,0,100,200,90,100);");
        db.execSQL("INSERT INTO "+ WAYTABLE +" (" + COLUMN_FROM
                + ", " + COLUMN_IN +", "+COLUMN_WAYLENGTH + ", "+COLUMN_AVERAGESPEED
                + ", " + COLUMN_COST +", "+COLUMN_WEIGTHLIMIT + ", "+COLUMN_HEIGHTLIMIT
                + ", " + COLUMN_MAXSPEED +", "+COLUMN_ROADCAPACITY + ") VALUES (3,4,60,70.0,0,100,200,90,100);");


    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+WAYTABLE);
        db.execSQL("DROP TABLE IF EXISTS "+LOCTABLE);
        onCreate(db);
    }
}
