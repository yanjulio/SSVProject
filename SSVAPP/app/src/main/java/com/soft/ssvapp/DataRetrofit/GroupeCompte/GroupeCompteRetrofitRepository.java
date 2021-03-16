package com.soft.ssvapp.DataRetrofit.GroupeCompte;

import com.soft.ssvapp.DataRetrofit.ApiUrl;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GroupeCompteRetrofitRepository {
//    private static final String URL ="http://192.168.0.108/ApisKpBatiment2/";

    private static GroupeCompteRetrofitRepository instance;

    private GroupeCompteConnexion groupeCompteConnexion;

    public static GroupeCompteRetrofitRepository getInstance() {
        if (instance == null) {
            instance = new GroupeCompteRetrofitRepository();
        }
        return instance;
    }

    public GroupeCompteRetrofitRepository() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        groupeCompteConnexion = retrofit.create(GroupeCompteConnexion.class);

    }

    public GroupeCompteConnexion groupeCompteConnexion()
    {
        return groupeCompteConnexion;
    }
}
