package com.github.johntinashe.popularmovies.data;

import retrofit2.converter.gson.GsonConverterFactory;

class RetrofitClient {

    public static retrofit2.Retrofit getRetrofit() {
        return new retrofit2.Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}

