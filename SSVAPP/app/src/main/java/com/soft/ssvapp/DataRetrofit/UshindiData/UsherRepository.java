package com.soft.ssvapp.DataRetrofit.UshindiData;

import com.soft.ssvapp.DataRetrofit.ApiUrl;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UsherRepository {
    private static UsherRepository instance;

    private UserConnexion userConnexion;

    public static UsherRepository getInstance() {
        if (instance == null) {
            instance = new UsherRepository();
        }
        return instance;
    }

    public UsherRepository() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        userConnexion = retrofit.create(UserConnexion.class);

    }

    public UserConnexion userConnexion()
    {
        return userConnexion;
    }
}
