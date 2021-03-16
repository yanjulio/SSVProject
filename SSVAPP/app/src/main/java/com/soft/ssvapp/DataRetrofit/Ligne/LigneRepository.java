package com.soft.ssvapp.DataRetrofit.Ligne;

import androidx.lifecycle.MutableLiveData;

import com.soft.ssvapp.DataRetrofit.ApiUrl;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LigneRepository {

    private MutableLiveData<String> response_back = new MutableLiveData<>();

    private LigneConnexion ligneConnexion;
    private static LigneRepository instance;

    public static LigneRepository getInstance()
    {
        if (instance == null)
        {
            instance = new LigneRepository();
        }
        return instance;
    }

    public LigneRepository()
    {
        response_back = new MutableLiveData<>();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ligneConnexion = retrofit.create(LigneConnexion.class);
    }

    public LigneConnexion ligneConnexion()
    {
        return ligneConnexion;
    }
}
