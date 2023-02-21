package com.usama.ezcommerce;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class ProductDB implements  ProductInterface{
    private Context context;

    public ProductDB(Context ctx){
        context = ctx;
    }

    //// save a single Product /////
    @Override
    public void saveProduct(Hashtable<String,String> item){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues content = new ContentValues();
        Enumeration<String> keys = item.keys();

        while (keys.hasMoreElements()){
            String key = keys.nextElement();
            content.put(key, item.get(key));
        }

        db.insert("Products",null,content);
    }


    //// sava all Products /////
    @Override
    public void saveProducts(ArrayList<Hashtable<String, String>> products) {
        for(Hashtable<String,String> obj : products){
            saveProduct(obj);
        }
    }


    @Override
    public ArrayList<ProductData> readProducts(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM Products";
        Cursor cursor = db.rawQuery(query,null);

        ArrayList<ProductData> products = new ArrayList<ProductData>();

        while(cursor.moveToNext()){
            products.add(new ProductData(cursor.getInt(0),
                    cursor.getString(1), cursor.getFloat(2), cursor.getString(3)));

        }
        cursor.close();
        return products;
    }
}
