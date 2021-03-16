package com.soft.ssvapp.DataRetrofit.Rapport;

import com.soft.ssvapp.DataRetrofit.ApiUrl;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RapportRetrofitRepository {

    private static RapportRetrofitRepository instance;

    private RapportConnexion rapportConnexion;

    public static RapportRetrofitRepository getInstance()
    {
        if (instance == null)
        {
            instance = new RapportRetrofitRepository();
        }
        return instance;
    }

    public RapportRetrofitRepository() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        rapportConnexion = retrofit.create(RapportConnexion.class);

    }

    public RapportConnexion rapportConnexion()
    {
        return rapportConnexion;
    }
}
