package com.irlangomes.melisearchable.presenter;

import com.irlangomes.melisearchable.api.MeliService;
import com.irlangomes.melisearchable.model.Product;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailPresenterImpl implements ProductDetail.ProductDetailPresenter {

    private ProductDetail.ProductDetailView view;
    private MeliService meliService;

    public ProductDetailPresenterImpl(ProductDetail.ProductDetailView view, MeliService meliService) {
        this.view = view;
        this.meliService = meliService;
    }

    @Override
    public void findProductDetail(String idProduct) {
        view.setVisibleProgressBar();
        meliService.findProductDetail(idProduct).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                responseCallback(response);
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                view.setGoneProgressBar();
                view.displayError();
            }
        });
    }

    private void responseCallback(Response<Product> response) {
        view.setGoneProgressBar();
        if (response.isSuccessful()) {
            Product product = response.body();
            view.displayTitle(product.title);
            view.displayPrice(product.getPrice());
            view.configViewPager(product.pictures);
            countPictures(0, product.pictures.size());
        } else {
            view.displayError();
        }
    }

    @Override
    public String countPictures(int currentPosition, int total) {
        int addOne = currentPosition + 1;
        String label = String.format("%d / %d", addOne, total);
        view.displayCountPictures(label);
        return label;
    }


    @Override
    public void destroyView() {
        this.view = null;
    }
}
