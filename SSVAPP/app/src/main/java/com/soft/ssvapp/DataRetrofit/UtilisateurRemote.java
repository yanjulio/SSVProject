package com.soft.ssvapp.DataRetrofit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class UtilisateurRemote {

    private UtilisateurConnexion utilisateurConnexion;
    private MutableLiveData<UtilisateurResponse> utilisateurResponseLiveData;

    private static UtilisateurRemote instance;

    public static UtilisateurRemote getInstance()
    {
        if (instance==null)
        {
            instance = new UtilisateurRemote();
        }
        return instance;
    }

    public UtilisateurRemote()
    {
        utilisateurResponseLiveData = new MutableLiveData<>();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        utilisateurConnexion = new retrofit2.Retrofit.Builder().baseUrl(ApiUrl.URL)
                .client(client).addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UtilisateurConnexion.class);
    }

    public UtilisateurConnexion utilisateurConnexion()
    {
        return utilisateurConnexion;
    }

    public void Login(String nomUtilisateur, String motDePasse)
    {
        utilisateurConnexion.Login(nomUtilisateur, motDePasse)
                .enqueue(new Callback<UtilisateurResponse>() {
                    @Override
                    public void onResponse(Call<UtilisateurResponse> call, Response<UtilisateurResponse> response) {
                        if (response.body() != null)
                        {
                            utilisateurResponseLiveData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<UtilisateurResponse> call, Throwable throwable) {
                        utilisateurResponseLiveData.postValue(null);
                    }
                });
    }

    public LiveData<UtilisateurResponse> getResponseLiveData()
    {
        return utilisateurResponseLiveData;
    }
}
