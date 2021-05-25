package com.irlangomes.melisearchable.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.irlangomes.melisearchable.R;
import com.irlangomes.melisearchable.adapter.ProductDetailAdapter;
import com.irlangomes.melisearchable.api.MeliService;
import com.irlangomes.melisearchable.helper.RetrofitConfig;
import com.irlangomes.melisearchable.model.Product;
import com.irlangomes.melisearchable.model.ResultProduct;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProductDetailActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private ViewPager viewPager;
    private Product product;
    private TextView txtTitleProductDetail;
    private TextView txtPriceProductDetail;
    private TextView txtCountProductDetail;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // init config
        retrofit = RetrofitConfig.initRetrofit();

        // init components
        viewPager = findViewById(R.id.viewPager);
        txtTitleProductDetail = findViewById(R.id.txtTitleProductDetail);
        txtPriceProductDetail = findViewById(R.id.txtPriceProductDetail);
        txtCountProductDetail = findViewById(R.id.txtCountProductDetail);
        progressBar = findViewById(R.id.progressBarProductDetail);
        progressBar.setVisibility(View.GONE);

        // get idProduct
        String idProduct = getIntent().getExtras().getString("idProduct");
        Log.d("product", idProduct);
        findProductDetail(idProduct);
    }

    private void configViewPager() {
        ProductDetailAdapter adapter = new ProductDetailAdapter(this, product.pictures);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                ///Log.d("onPageScrolled", "onPageScrolled: "+position);
            }

            @Override
            public void onPageSelected(int position) {

                Log.d("page", "getChildCount: "+viewPager.getChildCount());
                Log.d("page", "onPageSelected: "+position);
                countPictures(position, product.pictures.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //Log.d("page", "onPageScrollStateChanged: "+state);
            }
        });
    }

    private void countPictures(int currentPosition, int total) {
        currentPosition = currentPosition + 1;
        String label = String.format("%d / %d", currentPosition, total);
        txtCountProductDetail.setText(label);
    }

    private void findProductDetail(String idProduct) {
        progressBar.setVisibility(View.VISIBLE);
        MeliService meliService = retrofit.create(MeliService.class);
        meliService.findProductDetail(idProduct)
        .enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(response.isSuccessful()){
                    product = response.body();
                    txtTitleProductDetail.setText(product.title);
                    txtPriceProductDetail.setText(product.getPrice());
                    configViewPager();
                    countPictures(0, product.pictures.size());
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Log.d("productDetail", t.toString());
            }
        });
    }
}