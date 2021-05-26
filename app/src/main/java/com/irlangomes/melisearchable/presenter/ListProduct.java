package com.irlangomes.melisearchable.presenter;

import com.irlangomes.melisearchable.model.Product;

import java.util.List;

public interface ListProduct {

    interface ListProductView {
        void displayProduct(List<Product> results);
        void displayError();
        void setGoneProgressBar();
    }

    interface ListProductPresenter {
        void findProduct(String query);
        void destroyView();
    }

}
