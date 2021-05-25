package com.irlangomes.melisearchable.presenter;

import com.irlangomes.melisearchable.api.MeliService;
import com.irlangomes.melisearchable.helper.RetrofitConfig;
import com.irlangomes.melisearchable.model.Product;
import com.irlangomes.melisearchable.model.ResultProduct;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListProductPresenterImpl implements ListProduct.ListProductPresenter {

    private ListProduct.ListProductView view;

    @Override
    public void setView(ListProduct.ListProductView view) {
        this.view = view;
    }

    @Override
    public void findProduct(String query) {
        MeliService meliService = RetrofitConfig.initRetrofit().create(MeliService.class);
        meliService.findProduct(query)
                .enqueue(new Callback<ResultProduct>() {
                    @Override
                    public void onResponse(Call<ResultProduct> call, Response<ResultProduct> response) {
                        if(response.isSuccessful()){
                            view.setGoneProgressBar();
                            List<Product> results = response.body().results;
                            view.displayProduct(results);
                        } else {
                            view.setGoneProgressBar();
                            view.displayError();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResultProduct> call, Throwable t) {
                        view.setGoneProgressBar();
                        view.displayError();
                    }
                });
    }

    @Override
    public void destroyView() {
        view = null;
    }
}
