package com.irlangomes.melisearchable.api;

import com.irlangomes.melisearchable.model.Product;
import com.irlangomes.melisearchable.model.ResultProduct;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MeliService {

    @GET("sites/MLA/search")
    Call<ResultProduct> findProduct(@Query("q") String query);

    @GET("items/{ids}")
    Call<Product> findProductDetail(@Path("ids") String ids);

//    @GET("reviews/item/{itemId}")
//    Call<Rating> findRating(@Path("itemId") String itemId);

}
