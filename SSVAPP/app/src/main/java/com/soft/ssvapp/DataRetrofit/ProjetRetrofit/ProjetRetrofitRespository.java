package com.soft.ssvapp.DataRetrofit.ProjetRetrofit;


import com.soft.ssvapp.DataRetrofit.ApiUrl;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProjetRetrofitRespository {

    private static ProjetRetrofitRespository instance;

    private ProjetConnexion projetConnexion;

    public static ProjetRetrofitRespository getInstance() {
        if (instance == null) {
            instance = new ProjetRetrofitRespository();
        }
        return instance;
    }

    public ProjetRetrofitRespository() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        projetConnexion = retrofit.create(ProjetConnexion.class);

    }

    public ProjetConnexion projetConnexion()
    {
        return projetConnexion;
    }

}
