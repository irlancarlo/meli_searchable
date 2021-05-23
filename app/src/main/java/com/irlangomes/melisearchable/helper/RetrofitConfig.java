package com.irlangomes.melisearchable.helper;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {

    public static Retrofit initRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(MeliConfig.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
