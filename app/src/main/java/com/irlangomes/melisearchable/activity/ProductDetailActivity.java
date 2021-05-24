package com.irlangomes.melisearchable.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.irlangomes.melisearchable.R;
import com.irlangomes.melisearchable.adapter.ProductDetailAdapter;

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        String product = getIntent().getExtras().getString("idProduct");
        Log.d("product", product);

        ViewPager viewPager = findViewById(R.id.viewPager);
        ProductDetailAdapter adapter = new ProductDetailAdapter(this);
        viewPager.setAdapter(adapter);
    }
}