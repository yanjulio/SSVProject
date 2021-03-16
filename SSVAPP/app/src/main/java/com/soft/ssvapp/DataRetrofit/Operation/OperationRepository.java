package com.soft.ssvapp.DataRetrofit.Operation;


import com.soft.ssvapp.DataRetrofit.ApiUrl;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OperationRepository {

    private OperationConnexion operationConnexion;

    public static OperationRepository instance;

    public static OperationRepository getInstance()
    {
        if (instance == null)
        {
            instance = new OperationRepository();
        }
        return instance;
    }

    public OperationRepository() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        operationConnexion = retrofit.create(OperationConnexion.class);
    }

    public OperationConnexion operationConnexion()
    {
        return operationConnexion;
    }
}
