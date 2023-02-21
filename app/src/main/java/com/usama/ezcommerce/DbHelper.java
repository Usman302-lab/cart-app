package com.usama.ezcommerce;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Ecommerce.db";

    public DbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        String sql = "CREATE TABLE Products (ImgID TEXT , " +
                "ProductName TEXT, " +
                "Stars TEXT ," +
                "Price TEXT )";
        String sql1 = "CREATE TABLE Cart (ImgID TEXT , " +
                "ProductName TEXT, " +
                "Price TEXT ," +
                "Quantity TEXT )";

        db.execSQL(sql);
        db.execSQL(sql1);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Products");
        db.execSQL("DROP TABLE IF EXISTS Cart");
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db,oldVersion,newVersion);
    }
}
