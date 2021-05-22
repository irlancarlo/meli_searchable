package com.irlangomes.melisearchable.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.irlangomes.melisearchable.R;
import com.irlangomes.melisearchable.adapter.ProductAdapter;
import com.irlangomes.melisearchable.model.Product;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerProduct;
    private List<Product> products = new ArrayList<>();
    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerProduct = findViewById(R.id.recyclerProduct);


        // Config recycler
        generateProduct();
        productAdapter = new ProductAdapter(products, this);
        recyclerProduct.setHasFixedSize(true);
        recyclerProduct.setLayoutManager(new LinearLayoutManager(this));
        recyclerProduct.setAdapter(productAdapter);

    }

    public void generateProduct(){
        Product product = new Product();
        product.setPicture("mot1.jpg");
        product.setTitle("Motorola Moto G6 Play Bueno Negro Liberado");
        product.setPrice("22699");
        products.add(product);

        product = new Product();
        product.setPicture("mot2.jpg");
        product.setTitle("Motorola Moto G6 Play Bueno Negro Liberado 32 GB cinza-titatinum 2 GB RAM ");
        product.setPrice("33500.99");
        products.add(product);
    }
}