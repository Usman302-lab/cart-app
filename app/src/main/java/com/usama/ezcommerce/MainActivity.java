package com.usama.ezcommerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    ArrayList<ProductData> Data = new ArrayList<>();
    ProductAdapter adapter;
    ProductDB doa;

    private void filter(String text){
        ArrayList<ProductData> filteredData = new ArrayList<>();  ///// initialize empty list every time after search /////

        //// filter the data that is present in original arrayList  ////
        for(ProductData item: Data){
            if(item.getProductname().toLowerCase().contains(text.toLowerCase())){
                filteredData.add(item);
            }
        }

        adapter.filteredList(filteredData);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        doa = new ProductDB(MainActivity.this);
        editText = (EditText) findViewById(R.id.Editsearch);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());       /// call this function after every search
            }
        });


        Data.add(new ProductData(R.drawable.my_shirt, "Shirt", 3f, "$220"));
        Data.add(new ProductData(R.drawable.my_shoe, "Shoes", 2.3f, "$200"));
        Data.add(new ProductData(R.drawable.dumbbell, "Dumbbell", 4f, "$250"));
        Data.add(new ProductData(R.drawable.bag, "Bag", 3.5f, "$300"));
        Data.add(new ProductData(R.drawable.laptop, "Laptop", 3.5f, "$500"));

        //// save this arrayList in Database (send it to business layer [ProductData.java] ) //////
        ProductData dataobj = new ProductData(doa);
        dataobj.saveToDB(Data);

        //// receive array from business Layer ////
        ArrayList<ProductData> DBProducts = dataobj.load();

        // setting recyclerview
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        adapter = new ProductAdapter(MainActivity.this, DBProducts);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
    }
}