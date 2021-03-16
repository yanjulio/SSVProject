package com.soft.ssvapp.DataRetrofit.DetailsEtatDeBesoin;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;


import com.soft.ssvapp.DataRetrofit.ApiUrl;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailsEtatDeBesoinRepository {

    private static DetailsEtatDeBesoinRepository instance;
    private DetailsEtatDeBesoinConnexion detailsEtatDeBesoinConnexion;
    private MutableLiveData<String> response_back;
    Application application;

    public static DetailsEtatDeBesoinRepository getInstance() {
        if (instance == null)
        {
            instance = new DetailsEtatDeBesoinRepository();
        }
        return instance;
    }

    public DetailsEtatDeBesoinRepository()
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

        detailsEtatDeBesoinConnexion = retrofit.create(DetailsEtatDeBesoinConnexion.class);
    }

    public DetailsEtatDeBesoinConnexion getDetailsEtatDeBesoinConnexion()
    {
        return detailsEtatDeBesoinConnexion;
    }

    public void CreateDetailsBesoin(DetailsEtatDeBesoinRetrofit detailsBesoin)
    {
        detailsEtatDeBesoinConnexion.createDetailsEtatDeBesoin(detailsBesoin).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful())
                {
                    response_back.postValue("Enregistrement du details effectué");
                }
                else
                {
                    switch (response.code())
                    {
                        case 404:
                            response_back.postValue("the server not found");
                            break;
                        case 500:
                            response_back.postValue("the server has broken" + "from DetailsBesoin");
                            break;
                        default:
                            response_back.postValue("Connexion error");
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                response_back.postValue("Probleme de connexion");
            }
        });
    }

    public MutableLiveData<String> getResponseBack()
    {
        return response_back;
    }
}
