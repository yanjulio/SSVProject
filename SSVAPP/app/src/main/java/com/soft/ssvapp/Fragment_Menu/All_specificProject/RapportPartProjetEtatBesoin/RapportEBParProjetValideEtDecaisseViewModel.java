package com.soft.ssvapp.Fragment_Menu.All_specificProject.RapportPartProjetEtatBesoin;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.soft.ssvapp.DataRetrofit.RapportParProjet.RapportEBParProjetValideEtDecaisse;

import java.util.List;

public class RapportEBParProjetValideEtDecaisseViewModel extends AndroidViewModel {

    RapportEBValideEtDecaisseRepository rapportEBValideEtDecaisseRepository;

    public RapportEBParProjetValideEtDecaisseViewModel(@NonNull Application application) {
        super(application);
        rapportEBValideEtDecaisseRepository = new RapportEBValideEtDecaisseRepository(application);
    }

    public LiveData<List<RapportEBParProjetValideEtDecaisse>> getRapportEBValideDecaisse(String codeProjet)
    {
        return rapportEBValideEtDecaisseRepository.getRapportParProjetValideDecaisse(codeProjet);
    }

    public MutableLiveData<Boolean> getIsLoading()
    {
        return rapportEBValideEtDecaisseRepository.getIsLoading();
    }
}
