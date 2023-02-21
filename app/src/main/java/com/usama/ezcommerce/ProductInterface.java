package com.usama.ezcommerce;

import java.util.ArrayList;
import java.util.Hashtable;

public interface ProductInterface {
    public void saveProduct(Hashtable<String,String> item);
    public ArrayList<ProductData> readProducts();
    public void saveProducts(ArrayList<Hashtable<String, String>> objects);
}
