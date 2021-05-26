package com.irlangomes.melisearchable.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.snackbar.Snackbar;
import com.irlangomes.melisearchable.R;
import com.irlangomes.melisearchable.adapter.ProductDetailAdapter;
import com.irlangomes.melisearchable.api.MeliService;
import com.irlangomes.melisearchable.helper.RetrofitConfig;
import com.irlangomes.melisearchable.model.Picture;
import com.irlangomes.melisearchable.presenter.ProductDetail;
import com.irlangomes.melisearchable.presenter.ProductDetailPresenterImpl;

import java.util.List;

public class ProductDetailActivity extends AppCompatActivity implements ProductDetail.ProductDetailView {

    private ViewPager viewPager;
    private TextView txtTitleProductDetail;
    private TextView txtPriceProductDetail;
    private TextView txtCountProductDetail;
    private ProgressBar progressBar;
    private ProductDetail.ProductDetailPresenter presenter;
    private LinearLayout view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // init presenter
        presenter = new ProductDetailPresenterImpl(this,
                RetrofitConfig.initRetrofit().create(MeliService.class));

        // init components
        viewPager = findViewById(R.id.viewPager);
        txtTitleProductDetail = findViewById(R.id.txtTitleProductDetail);
        txtPriceProductDetail = findViewById(R.id.txtPriceProductDetail);
        txtCountProductDetail = findViewById(R.id.txtCountProductDetail);
        progressBar = findViewById(R.id.progressBarProductDetail);
        progressBar.setVisibility(View.GONE);
        view = findViewById(R.id.linearLayoutProductDetail);

        // get idProduct
        String idProduct = getIntent().getExtras().getString("idProduct");
        Log.d("product", idProduct);
        presenter.findProductDetail(idProduct);
    }

    @Override
    public void displayError() {
        Snackbar.make(view, "Erro ao carregar o produto.", Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void setGoneProgressBar() {
        progressBar.setVisibility(View.GONE);
        view.setVisibility(View.VISIBLE);
    }

    @Override
    public void setVisibleProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void displayTitle(String title) {
        txtTitleProductDetail.setText(title);
    }

    @Override
    public void displayPrice(String price) {
        txtPriceProductDetail.setText(price);
    }

    @Override
    public void displayCountPictures(String label) {
        txtCountProductDetail.setText(label);
    }

    @Override
    public void configViewPager(List<Picture> pictureList) {
        ProductDetailAdapter adapter = new ProductDetailAdapter(this, pictureList);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                presenter.countPictures(position, pictureList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroyView();
    }
}