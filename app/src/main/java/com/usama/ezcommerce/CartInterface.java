package com.usama.ezcommerce;

import java.util.ArrayList;
import java.util.Hashtable;

public interface CartInterface {
    public void saveCartItem(Hashtable<String,String> item);
    public ArrayList<CartData> readCart();
    public boolean checkInDB(String item);          /// check if item is already in cart ?
    public void IncreaseQuantity(CartData cartItem);
    public void DecreaseQuantity(CartData cartItem);
    public void deleteCartItem(String name);
    public int countCartItems();
    public int getPrice();
    public int convertString(String strprice);
}
