package com.usama.ezcommerce;

import android.util.Log;

import java.util.ArrayList;
import java.util.Hashtable;

public class CartData {
    private int imgId;
    private String productname;
    private String price;
    private int quantity;
    private CartInterface Interface = null;

    public  CartData(CartInterface Interface){
        imgId = 0;
        productname = "";
        price = "";
        quantity = 0;
        this.Interface = Interface;
    }


    public CartData(int imgId, String productname, String price, int quantity){
        this.imgId = imgId;
        this.productname = productname;
        this.price = price;
        this.quantity = quantity;
    }


    //// Receive Cart Items from UI Layer & store them in Database //////
    public void saveCartItemToDB(CartData data){

        /// if item is present in database, then only increase it's quantity  ///
        if(Interface.checkInDB(data.getProductname())){
            Interface.IncreaseQuantity(data);
            return;
        }

        /// if not present, then add it ///
        Hashtable<String, String> obj = new Hashtable<String, String>();
        obj.put("ImgId", String.valueOf(data.getImgId()));
        obj.put("ProductName", data.getProductname());
        obj.put("Price", data.getPrice());
        obj.put("Quantity", String.valueOf(data.getQuantity()));

        /// store it in database ////
        Interface.saveCartItem(obj);
    }


    //// Read Cart from Database and send them to UI Layer ////
    public ArrayList<CartData> loadCart(){
        return Interface.readCart();
    }

    public int getCartPrice(){
        return Interface.getPrice();
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public void incrementQuantity(){
        quantity++;
    }

    public void decrementnQuantity(){
        quantity--;
    }

    public int getQuantity(){
        return quantity;
    }

    public int getImgId() {
        return imgId;
    }

    public String getProductname() {
        return productname;
    }

    public String getPrice() {
        return price;
    }
}
