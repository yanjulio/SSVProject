package com.soft.ssvapp.DataRetrofit.EtatDeBesoin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.soft.ssvapp.DataRetrofit.ApiUrl;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EtatDeBesoinRepositoryRetrofit {

    private static EtatDeBesoinRepositoryRetrofit instance;
    private MutableLiveData<List<EtatDeBesoinRetrofit>> etatDeBesoinRetrofitMutableLiveData;
    private String respnse_back_get;
    private EtatDeBesoinConnexion etatDeBesoinConnexion;

    public static EtatDeBesoinRepositoryRetrofit getInstance() {
        if (instance == null) {
            instance = new EtatDeBesoinRepositoryRetrofit();
        }
        return instance;
    }

    public EtatDeBesoinRepositoryRetrofit()
    {
        etatDeBesoinRetrofitMutableLiveData = new MutableLiveData<>();
        respnse_back_get = "";
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        etatDeBesoinConnexion = retrofit.create(EtatDeBesoinConnexion.class);
    }

    public EtatDeBesoinConnexion etatDeBesoinConnexion()
    {
        return etatDeBesoinConnexion;
    }

    public void GetEtatDeBesoin()
    {
        etatDeBesoinConnexion.getEtatDeBesoin().enqueue(new Callback<List<EtatDeBesoinRetrofit>>() {
            @Override
            public void onResponse(Call<List<EtatDeBesoinRetrofit>> call, Response<List<EtatDeBesoinRetrofit>> response) {
                if (response.isSuccessful())
                {
                    etatDeBesoinRetrofitMutableLiveData.postValue(response.body());
                }
                else {
                    switch (response.code()) {
                        case 404:
                            respnse_back_get = "not found";
                            break;
                        case 500:
                            respnse_back_get = "server broken";
                            break;
                        default:
                            respnse_back_get = "connexion error unknown";
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<EtatDeBesoinRetrofit>> call, Throwable t) {
                respnse_back_get = "Probleme de connexion";
            }
        });
    }

    // to get the list to valide read this method
    public LiveData<List<EtatDeBesoinRetrofit>> getLiveDataEtatBesoin()
    {
        return etatDeBesoinRetrofitMutableLiveData;
    }

    // for the list to get valide varifier the error with this methode
    public String responseBak_get()
    {
        return respnse_back_get;
    }
}
