package com.usama.ezcommerce;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class CartDB implements CartInterface{
    private Context context;

    public CartDB(Context ctx){
        context = ctx;
    }

    //// save a single item in cart /////
    @Override
    public void saveCartItem(Hashtable<String,String> item){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues content = new ContentValues();
        Enumeration<String> keys = item.keys();
        while (keys.hasMoreElements()){
            String key = keys.nextElement();
            content.put(key, item.get(key));
        }

        db.insert("Cart",null,content);
    }


    @Override
    public ArrayList<CartData> readCart(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM Cart";
        Cursor cursor = db.rawQuery(query,null);

        ArrayList<CartData> cart = new ArrayList<CartData>();

        while(cursor.moveToNext()){
            cart.add(new CartData(cursor.getInt(0),
                    cursor.getString(1), cursor.getString(2), cursor.getInt(3)));
        }
        cursor.close();
        return cart;
    }


    public boolean checkInDB(String item){
        boolean flag = false;
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM Cart where ProductName=?";

        Cursor cursor = db.rawQuery(query, new String[] {item});

        //// if item already present in database, return true
        if(cursor.moveToFirst() && cursor.getCount() > 0)
            flag = true;
        else
            flag = false;

        cursor.close();
        return flag;
    }


    public void IncreaseQuantity(CartData cartItem){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        SQLiteDatabase db0 = dbHelper.getReadableDatabase();
        int quantity = 0;
        String query = "SELECT * FROM Cart where ProductName=?" ;

        Cursor cursor = db0.rawQuery(query, new String[] {cartItem.getProductname()});
        if(cursor.moveToNext()) {                              /// cursor will point to the 1st row
            quantity = cursor.getInt(cursor.getColumnIndexOrThrow("Quantity"));
            quantity++;

            ContentValues content = new ContentValues();
            content.put("ImgId", String.valueOf(cartItem.getImgId()));
            content.put("ProductName", cartItem.getProductname());
            content.put("Price", cartItem.getPrice());
            content.put("Quantity", quantity);

            db.update("Cart", content, "ProductName=?", new String[]{cartItem.getProductname()});
            cursor.close();
        }

    }


    public void DecreaseQuantity(CartData cartItem){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        SQLiteDatabase db0 = dbHelper.getReadableDatabase();
        int quantity = 0;
        String query = "SELECT * FROM Cart where ProductName=?" ;

        Cursor cursor = db0.rawQuery(query, new String[] {cartItem.getProductname()});
        if(cursor.moveToNext()) {                              /// cursor will point to the 1st row
            quantity = cursor.getInt(cursor.getColumnIndexOrThrow("Quantity"));
            if (quantity > 1) {
                quantity--;

                ContentValues content = new ContentValues();
                content.put("ImgId", String.valueOf(cartItem.getImgId()));
                content.put("ProductName", cartItem.getProductname());
                content.put("Price", cartItem.getPrice());
                content.put("Quantity", quantity);

                db.update("Cart", content, "ProductName=?", new String[]{cartItem.getProductname()});
                cursor.close();
            }

        }
    }


    public void deleteCartItem(String itemName){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("Cart", "ProductName=?", new String[]{itemName});
    }



    public int countCartItems(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int rows = 0;
        String query = "SELECT * FROM Cart";
        Cursor cursor = db.rawQuery(query,null);
       while(cursor.moveToNext()){
           rows++;
       }

       cursor.close();
       return rows;

  //      return cursor.getCount();
    }


    public int getPrice(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int totalPrice = 0, price = 0, quantity = 0;
        String strprice;
        String query = "SELECT * FROM Cart";
        Cursor cursor = db.rawQuery(query,null);

        while(cursor.moveToNext()){
            strprice = cursor.getString(cursor.getColumnIndexOrThrow("Price"));
            price = convertString(strprice);
            quantity = cursor.getInt(cursor.getColumnIndexOrThrow("Quantity"));
            totalPrice = totalPrice + (price * quantity);
        }

        cursor.close();
        return totalPrice;
    }

    public int convertString(String str) {
        char[] ch = str.toCharArray();
        int len = ch.length;

        char[] arr = new char[len-1];
        for(int i=0; i<(len-1); i++){
           arr[i] = ch[i+1];
        }

        int price = Integer.parseInt(String.valueOf(arr));
        return price;
    }
}
