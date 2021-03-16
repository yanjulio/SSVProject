package com.soft.ssvapp.DataRetrofit.RapportParProjet;


import com.soft.ssvapp.DataRetrofit.ApiUrl;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RapportParprojetRetrofitRepository {

    private static RapportParprojetRetrofitRepository instance;

    private RapportParProjetConnexion rapportParProjetConnexion;

    public static RapportParprojetRetrofitRepository getInstance()
    {
        if (instance == null)
        {
            instance = new RapportParprojetRetrofitRepository();
        }
        return instance;
    }

    public RapportParprojetRetrofitRepository()
    {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(ApiUrl.URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create()).build();

        rapportParProjetConnexion = retrofit.create(RapportParProjetConnexion.class);
    }

    public RapportParProjetConnexion rapportParProjetConnexion()
    {
        return rapportParProjetConnexion;
    }

}
