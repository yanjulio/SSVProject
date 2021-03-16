package com.soft.ssvapp.Fragment_Menu.FillPayements.DetailsPayement.remote;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.soft.ssvapp.Data.Entity_DetailBesoin;
import com.soft.ssvapp.DataRetrofit.DetailsEtatDeBesoin.DetailsEtatDeBesoinRetrofit;
import com.soft.ssvapp.DataRetrofit.EtatDeBesoin.EtatDeBesoinRetrofitDetailEB;

import java.util.List;

public class DetailPayementRemoteViewModel extends AndroidViewModel {
    DetailPayementRemoteRepository detailBesoinRemoteRepository;
    public DetailPayementRemoteViewModel(@NonNull Application application) {
        super(application);
        detailBesoinRemoteRepository = new DetailPayementRemoteRepository(application);
    }

    public MutableLiveData<List<DetailsEtatDeBesoinRetrofit>> getDetailBesoinRapport(String codeEtatBesoin)
    {
        return detailBesoinRemoteRepository.getDetailsEtatBesoin(codeEtatBesoin);
    }

    public LiveData<List<EtatDeBesoinRetrofitDetailEB>> getEtatBesoinMvt(String codeEtatBesion)
    {
        return detailBesoinRemoteRepository.getDetailEBMvt(codeEtatBesion);
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
