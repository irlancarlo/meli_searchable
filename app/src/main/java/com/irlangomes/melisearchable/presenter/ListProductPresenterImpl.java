package com.irlangomes.melisearchable.presenter;

import com.irlangomes.melisearchable.api.MeliService;
import com.irlangomes.melisearchable.model.Product;
import com.irlangomes.melisearchable.model.ResultProduct;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListProductPresenterImpl implements ListProduct.ListProductPresenter {

    private ListProduct.ListProductView view;
    private MeliService meliService;

    public ListProductPresenterImpl(ListProduct.ListProductView view, MeliService meliService) {
        this.view = view;
        this.meliService = meliService;
    }

    @Override
    public void findProduct(String query) {
        meliService.findProduct(query).enqueue(new Callback<ResultProduct>() {
            @Override
            public void onResponse(Call<ResultProduct> call, Response<ResultProduct> response) {
                responseCallback(response);
            }

            @Override
            public void onFailure(Call<ResultProduct> call, Throwable t) {
                view.setGoneProgressBar();
                view.displayErrorMsg();
            }
        });
    }

    private void responseCallback(Response<ResultProduct> response) {
        view.setGoneProgressBar();
        if (response.isSuccessful()
                && response.body() != null
                && !response.body().results.isEmpty()) {

            List<Product> results = response.body().results;
            view.displayProduct(results);
        } else {
            view.displayEmptySearchMsg();
        }
    }

    @Override
    public void destroyView() {
        view = null;
    }
}
