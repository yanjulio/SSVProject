package com.soft.ssvapp.DataRetrofit.MvtCompte;


import com.soft.ssvapp.DataRetrofit.ApiUrl;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MvtCompteRepositoryRetrofit {

    private MvtCompteConnexion mvtCompteConnexion;

    public static MvtCompteRepositoryRetrofit  instance;
    public static MvtCompteRepositoryRetrofit getInstance()
    {
        if (instance == null)
        {
            instance = new MvtCompteRepositoryRetrofit();
        }
        return instance;
    }

    public MvtCompteRepositoryRetrofit()
    {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mvtCompteConnexion = retrofit.create(MvtCompteConnexion.class);
    }

    public MvtCompteConnexion mvtCompteConnexion()
    {
        return mvtCompteConnexion;
    }
}
