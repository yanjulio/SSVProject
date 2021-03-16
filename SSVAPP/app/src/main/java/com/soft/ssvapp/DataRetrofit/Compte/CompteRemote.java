package com.soft.ssvapp.DataRetrofit.Compte;


import com.soft.ssvapp.DataRetrofit.ApiUrl;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompteRemote {

//    private static final String URL ="http://192.168.0.108/ApisKpBatiment2/";

    private static CompteRemote instance;

    private CompteConnexion compteConnexion;

    public static CompteRemote getInstance() {
        if (instance == null) {
            instance = new CompteRemote();
        }
        return instance;
    }

    public CompteRemote() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        compteConnexion = retrofit.create(CompteConnexion.class);

    }

    public CompteConnexion compteConnexion()
    {
        return compteConnexion;
    }
}
