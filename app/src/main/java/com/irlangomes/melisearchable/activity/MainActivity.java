package com.irlangomes.melisearchable.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.irlangomes.melisearchable.R;
import com.irlangomes.melisearchable.adapter.ProductAdapter;
import com.irlangomes.melisearchable.api.MeliService;
import com.irlangomes.melisearchable.helper.RetrofitConfig;
import com.irlangomes.melisearchable.listener.RecyclerItemClickListener;
import com.irlangomes.melisearchable.model.Product;
import com.irlangomes.melisearchable.presenter.ListProduct;
import com.irlangomes.melisearchable.presenter.ListProductPresenterImpl;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ListProduct.ListProductView {

    private static final String LIST_STATE_KEY = "PRODUCT_LIST";
    private RecyclerView recyclerProduct;
    private ProductAdapter productAdapter;
    private MaterialSearchView searchView;
    private ProgressBar progressBar;
    private ListProduct.ListProductPresenter presenter;
    private View view;
    private List<Product> productListState = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //init presenter
        presenter = new ListProductPresenterImpl(this,
                RetrofitConfig.initRetrofit().create(MeliService.class));

        // init components
        recyclerProduct = findViewById(R.id.recyclerProduct);
        searchView = findViewById(R.id.searchView);
        progressBar = findViewById(R.id.progressBarAllProduct);
        progressBar.setVisibility(View.GONE);
        view = findViewById(R.id.linearLayoutAllProduct);

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
                if (productAdapter != null) {
                    productAdapter.clear();
                }
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
        if (productAdapter != null) {
            productAdapter.clear();
        }
        searchView.hideKeyboard(getCurrentFocus());
        progressBar.setVisibility(View.VISIBLE);
        presenter.findProduct(query);
    }

    public void configRecycleView(List<Product> products) {
        this.productListState = products;
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

    @Override
    public void displayProduct(List<Product> results) {
        configRecycleView(results);
    }

    @Override
    public void displayErrorMsg() {
        generateMsg("Erro ao carregar a lista de produtos.");
    }

    private void generateMsg(String title) {
        if (recyclerProduct != null) {
            recyclerProduct.getRecycledViewPool().clear();
        }
        Snackbar snackbar = Snackbar.make(view, title, Snackbar.LENGTH_LONG);
        snackbar.setTextColor(getResources().getColor(android.R.color.white));
        snackbar.show();
    }

    @Override
    public void setGoneProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void displayEmptySearchMsg() {
        generateMsg("Pesquisa não retornou dados.");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroyView();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle state) {
        super.onSaveInstanceState(state);
        state.putParcelableArrayList(LIST_STATE_KEY, new ArrayList<>(productListState));
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle state) {
        super.onRestoreInstanceState(state);

        if(state != null){
            productListState = state.getParcelableArrayList(LIST_STATE_KEY);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!productListState.isEmpty()){
            configRecycleView(productListState);
        }
    }
}