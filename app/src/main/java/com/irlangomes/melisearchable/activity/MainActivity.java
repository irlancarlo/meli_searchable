package com.irlangomes.melisearchable.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.irlangomes.melisearchable.R;
import com.irlangomes.melisearchable.adapter.ProductAdapter;
import com.irlangomes.melisearchable.api.MeliService;
import com.irlangomes.melisearchable.helper.RetrofitConfig;
import com.irlangomes.melisearchable.listener.RecyclerItemClickListener;
import com.irlangomes.melisearchable.model.Product;
import com.irlangomes.melisearchable.model.ResultProduct;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerProduct;
    private List<Product> products = new ArrayList<>();
    private ProductAdapter productAdapter;
    private MaterialSearchView searchView;
    private Retrofit retrofit;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // init components
        recyclerProduct = findViewById(R.id.recyclerProduct);
        searchView = findViewById(R.id.searchView);
        progressBar = findViewById(R.id.progressBarAllProduct);
        progressBar.setVisibility(View.GONE);

        // init config
        retrofit = RetrofitConfig.initRetrofit();

        // Find result product
        findProduct("");

        //config searchView
        configSearchView();

    }

    private void configSearchView() {
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                findProduct(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                findProduct("");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.menu_search);
        searchView.setMenuItem(item);

        return true;
    }

    public void findProduct(String query) {
        progressBar.setVisibility(View.VISIBLE);
        MeliService meliService = retrofit.create(MeliService.class);
        meliService.findProduct(query)
                .enqueue(new Callback<ResultProduct>() {
                    @Override
                    public void onResponse(Call<ResultProduct> call, Response<ResultProduct> response) {
                        resultFindProduct(response);
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<ResultProduct> call, Throwable t) {

                    }
                });
    }

    private void resultFindProduct(Response<ResultProduct> response) {
        if (response.isSuccessful()) {
            List<Product> results = response.body().results;
            products = results;
            configRecycleView();
        }
    }

    public void configRecycleView() {
        productAdapter = new ProductAdapter(products, this);
        recyclerProduct.setHasFixedSize(true);
        recyclerProduct.setLayoutManager(new LinearLayoutManager(this));
        recyclerProduct.setAdapter(productAdapter);
        recyclerProduct.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerProduct, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Product product = products.get(position);
                        Intent intent = new Intent(MainActivity.this, ProductDetailActivity.class);
                        intent.putExtra("idProduct", product.id);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    }
                }));
    }

}