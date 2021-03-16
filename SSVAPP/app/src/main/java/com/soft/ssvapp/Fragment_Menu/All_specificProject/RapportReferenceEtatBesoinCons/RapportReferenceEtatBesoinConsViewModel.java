package com.soft.ssvapp.Fragment_Menu.All_specificProject.RapportReferenceEtatBesoinCons;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.soft.ssvapp.DataRetrofit.RapportParProjet.RapportReferenceEtatBesoinConsomme;

import java.util.List;

public class RapportReferenceEtatBesoinConsViewModel extends AndroidViewModel {

    RapportReferenceEtatBesoinConsRepository rapportReferenceEtatBesoinConsRepository;

    public RapportReferenceEtatBesoinConsViewModel(@NonNull Application application) {
        super(application);
        rapportReferenceEtatBesoinConsRepository = new RapportReferenceEtatBesoinConsRepository(application);
    }

    public MutableLiveData<List<RapportReferenceEtatBesoinConsomme>> getRapportReferenceEtatBesoinCons(String codeProjet,
                                                                                                       String codeArticle,
                                                                                                       String codeLigne)
    {
        return rapportReferenceEtatBesoinConsRepository.getRapportReferenceEtatBesoinCons(codeProjet, codeArticle, codeLigne);
    }

    public MutableLiveData<Boolean> getLoading()
    {
        return rapportReferenceEtatBesoinConsRepository.getIsLoading();
    }
}
