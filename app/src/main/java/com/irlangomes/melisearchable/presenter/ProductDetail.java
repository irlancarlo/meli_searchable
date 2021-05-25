package com.irlangomes.melisearchable.presenter;

import com.irlangomes.melisearchable.model.Picture;

import java.util.List;

public interface ProductDetail {

    interface ProductDetailView {
        void displayError();
        void setGoneProgressBar();
        void setVisibleProgressBar();
        void displayTitle(String title);
        void displayPrice(String price);
        void displayCountPictures(String label);
        void configViewPager(List<Picture> pictureList);
    }

    interface ProductDetailPresenter {
        void setView(ProductDetailView view);
        void findProductDetail(String idProduct);
        String countPictures(int currentPosition, int total);
        void destroyView();
    }
}
