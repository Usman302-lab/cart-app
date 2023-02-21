package com.usama.ezcommerce;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Hashtable;

public class ProductData {
    private int imgId;
    private String productname;
    private float stars;
    private String price;
    private ProductInterface Interface = null;

    public  ProductData(ProductInterface Interface){
        imgId = 0;
        productname = "";
        stars = 0;
        price = "";
        this.Interface = Interface;
    }


    public ProductData(int imgId, String productname, float stars, String price){
        this.imgId = imgId;
        this.productname = productname;
        this.stars = stars;
        this.price = price;
    }


    //// Receive products from UI Layer & store them in Database //////
    public void saveToDB(ArrayList<ProductData> data){
        ArrayList<Hashtable<String,String>> objects = new ArrayList<Hashtable<String, String>>();

        for(ProductData item: data) {
            Hashtable<String, String> obj = new Hashtable<String, String>();
            obj.put("ImgId", String.valueOf(item.imgId));
            obj.put("ProductName", item.productname);
            obj.put("Stars", String.valueOf(item.stars));
            obj.put("Price", item.price);
            objects.add(obj);
        }

        /// store it in database ////
        Interface.saveProducts(objects);
    }


    //// Read products from Database and send them to UI Layer ////
    public ArrayList<ProductData> load(){
        return Interface.readProducts();
    }


    public int getImgId() {
        return imgId;
    }

    public String getProductname() {
        return productname;
    }

    public float getStars() {
        return stars;
    }

    public String getPrice() {
        return price;
    }
}
