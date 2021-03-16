package com.soft.ssvapp.Fragment_Menu.Fill_Rapport.DetailRapport;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.soft.ssvapp.DataRetrofit.Rapport.RapportOperationResponse;

import java.util.List;

public class DetailRapportRemoteViewModel extends AndroidViewModel {

    DetailRapportRemoteRepository detailRapportRemoteRepository;

    public DetailRapportRemoteViewModel(@NonNull Application application) {
        super(application);
        detailRapportRemoteRepository = new DetailRapportRemoteRepository(application);
    }

    public MutableLiveData<List<RapportOperationResponse>> getDetailOperation(String numOperation)
    {
        return detailRapportRemoteRepository.getListMutableLiveData(numOperation);
    }

    public void supprimerOperation(String numOperation)
    {
        detailRapportRemoteRepository.supprimerOperation(numOperation);
    }

    public MutableLiveData<Boolean> getIsloading()
    {
        return detailRapportRemoteRepository.getIsLoading();
    }
}
