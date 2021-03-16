package com.soft.ssvapp.DataRetrofit.DetailsEtatDeBesoin;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class DetailsEtatDeBesoinRetrofitViewModel extends AndroidViewModel {

    private DetailsEtatDeBesoinRepository repository;
    private MutableLiveData<String> response_back;

    public DetailsEtatDeBesoinRetrofitViewModel(@NonNull Application application) {
        super(application);
    }

    public void init()
    {
        repository = new DetailsEtatDeBesoinRepository();
        response_back = repository.getResponseBack();
    }

    public void CreateDetailsBesoin(DetailsEtatDeBesoinRetrofit detailsBesoin)
    {
        repository.CreateDetailsBesoin(detailsBesoin);
    }

    public MutableLiveData<String> getResponse()
    {
        return response_back;
    }
}
