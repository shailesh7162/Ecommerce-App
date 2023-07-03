package com.example.ecommerceapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retro_Instance_Class
{
    public static RetrofitAPI CallApi()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ecommrecexyz.000webhostapp.com/mydata/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        return  retrofitAPI;
    }

}
