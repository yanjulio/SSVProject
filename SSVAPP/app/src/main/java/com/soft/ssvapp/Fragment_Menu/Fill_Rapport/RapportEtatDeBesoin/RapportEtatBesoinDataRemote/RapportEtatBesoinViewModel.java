package com.soft.ssvapp.Fragment_Menu.Fill_Rapport.RapportEtatDeBesoin.RapportEtatBesoinDataRemote;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.soft.ssvapp.DataRetrofit.EtatDeBesoin.EtatDeBesoinRetrofit;
import com.soft.ssvapp.DataRetrofit.Rapport.RapportEtatBesoinResponse;

import java.util.List;

public class RapportEtatBesoinViewModel extends AndroidViewModel {

    EtatBesoinRemoteRepository etatBesoinRemoteRepository;
    public RapportEtatBesoinViewModel(@NonNull Application application) {
        super(application);
        etatBesoinRemoteRepository = new EtatBesoinRemoteRepository(application);
    }

    public void setEtat_set(String etat_set)
    {
        etatBesoinRemoteRepository.setEtat_set(etat_set);
    }

    public MutableLiveData<String> getEtat_set()
    {
        return etatBesoinRemoteRepository.getEtat_set();
    }

    public MutableLiveData<List<RapportEtatBesoinResponse>> getEtatbesoin(String date1, String date2)
    {
        return etatBesoinRemoteRepository.getEtatBesoin(date1, date2);
    }

    public void modifierEtatBesoin(EtatDeBesoinRetrofit etatDeBesoinRetrofit, String codeEtatbesoin, String date1, String date2)
    {
        etatBesoinRemoteRepository.ModifierEtatBesoin(etatDeBesoinRetrofit, codeEtatbesoin, date1, date2);
    }

    public void supprimerEtatBesoin(String codeEtatBesoin, String date1, String date2)
    {
        etatBesoinRemoteRepository.supprimerEtatBesoin(codeEtatBesoin, date1, date2);
    }

    public MutableLiveData<Boolean> getIsLoading()
    {
        return etatBesoinRemoteRepository.getIsLoading();
    }
}
