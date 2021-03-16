package com.soft.ssvapp.DataRetrofit.EtatDeBesoin;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class EtatDeBesoinRetrofitViewModel extends AndroidViewModel {

    private EtatDeBesoinRepositoryRetrofit repository;
//    private MutableLiveData<String> response_back;
    private LiveData<List<EtatDeBesoinRetrofit>> etatDeBesoinRetrofitMutableLiveData;


    public EtatDeBesoinRetrofitViewModel(@NonNull Application application) {
        super(application);
    }

    public void init()
    {
        repository = new EtatDeBesoinRepositoryRetrofit();
        repository.GetEtatDeBesoin();
        etatDeBesoinRetrofitMutableLiveData = repository.getLiveDataEtatBesoin();

    }

    // todo: yan tu dois continnue par ici
    public LiveData<List<EtatDeBesoinRetrofit>> GetEtatBesoin()
    {
        return etatDeBesoinRetrofitMutableLiveData;
    }

    public String getResponse_backget()
    {
        return repository.responseBak_get();
    }

    /*
    all down is useless
     */
//    public void CreateEtatDeBesoin(EtatDeBesoinRetrofit etatDeBesoinRetrofit)
//    {
//        repository.CreateEtatDeBesoin(etatDeBesoinRetrofit);
//        response_back = repository.getResponse_back();
//    }

//    public MutableLiveData<String> getResponse()
//    {
//        return response_back;
//    }
}
