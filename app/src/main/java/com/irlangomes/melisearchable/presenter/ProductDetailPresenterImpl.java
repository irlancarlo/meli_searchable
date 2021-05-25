package com.irlangomes.melisearchable.presenter;

import com.irlangomes.melisearchable.api.MeliService;
import com.irlangomes.melisearchable.helper.RetrofitConfig;
import com.irlangomes.melisearchable.model.Product;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailPresenterImpl implements ProductDetail.ProductDetailPresenter {

    private ProductDetail.ProductDetailView view;

    @Override
    public void setView(ProductDetail.ProductDetailView view) {
        this.view = view;
    }

    @Override
    public void findProductDetail(String idProduct) {
        view.setVisibleProgressBar();
        MeliService meliService = RetrofitConfig.initRetrofit().create(MeliService.class);
        meliService.findProductDetail(idProduct)
                .enqueue(new Callback<Product>() {
                    @Override
                    public void onResponse(Call<Product> call, Response<Product> response) {
                        if(response.isSuccessful()){
                            Product product = response.body();
                            view.displayTitle(product.title);
                            view.displayPrice(product.getPrice());
                            view.configViewPager(product.pictures);
                            countPictures(0, product.pictures.size());
                            view.setGoneProgressBar();
                        }else{
                            view.setGoneProgressBar();
                            view.displayError();
                        }
                    }

                    @Override
                    public void onFailure(Call<Product> call, Throwable t) {
                        view.setGoneProgressBar();
                        view.displayError();
                    }
                });
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
