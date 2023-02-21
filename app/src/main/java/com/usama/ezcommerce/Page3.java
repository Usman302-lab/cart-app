package com.usama.ezcommerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class Page3 extends AppCompatActivity {
    public static  TextView textView;
    CartDB doa;
    ArrayList<CartData> cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page3);

        textView = (TextView) findViewById(R.id.textView10);
        Button pay = (Button) findViewById(R.id.button5);
        Button cancel = (Button) findViewById(R.id.button6);


        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Page3.this, "Payment Done", Toast.LENGTH_LONG).show();
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Page3.this, "Payment Cancel", Toast.LENGTH_LONG).show();
            }
        });


        Intent intent = getIntent();
        String item = intent.getStringExtra("UniqueItem1");
        String price = intent.getStringExtra("UniquePrice1");
        int imgID = intent.getIntExtra("UniqueImage1", 0);


        doa = new CartDB(this);

        CartData ITEM = new CartData(imgID, item, price, 1);

        CartData cartitem = new CartData(doa);
        cartitem.saveCartItemToDB(ITEM);


        //// receive array from business Layer ////
        cart = cartitem.loadCart();

        populateItems(cart);

        textView.setText(String.valueOf(cartitem.getCartPrice()));              //// set total price ////
    }



    public void populateItems(ArrayList<CartData> cart){

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView2);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        CartAdapter adapter = new CartAdapter(Page3.this, cart);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

    }

}
