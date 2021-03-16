package com.soft.ssvapp.Fragment_Menu.Fill_Rapport.RapportEtatDeBesoin.RapportDetailEtatBesoin;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.soft.ssvapp.Data.Entity_DetailBesoin;
import com.soft.ssvapp.DataRetrofit.DetailsEtatDeBesoin.DetailsEtatDeBesoinRetrofit;

import java.util.List;

public class DetailBesoinRapportViewModel extends AndroidViewModel {
    DetailBesoinRemoteRepository detailBesoinRemoteRepository;
    public DetailBesoinRapportViewModel(@NonNull Application application) {
        super(application);
        detailBesoinRemoteRepository = new DetailBesoinRemoteRepository(application);
    }

    public MutableLiveData<List<DetailsEtatDeBesoinRetrofit>> getDetailBesoinRapport(String codeEtatBesoin)
    {
        return detailBesoinRemoteRepository.getDetailsEtatBesoin(codeEtatBesoin);
    }

    public MutableLiveData<Boolean> getIsLoading()
    {
        return detailBesoinRemoteRepository.getIsLoading();
    }

    public void updateDetailRemote(Entity_DetailBesoin entity_detailBesoin)
    {
        detailBesoinRemoteRepository.updateDetailRemote(entity_detailBesoin);
    }

    public void deleteDetailRemote(Entity_DetailBesoin entity_detailBesoin)
    {
        detailBesoinRemoteRepository.deleteDetailRemote(entity_detailBesoin);
    }
}
