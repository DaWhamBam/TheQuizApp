package com.example.thequizapp.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static String baseUrl = "http://10.0.2.2/quiz/";
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static Retrofit getRetrofitInstance() {
        return retrofit;
    }
}
