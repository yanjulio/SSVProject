package com.soft.ssvapp.Fragment_Menu.All_specificProject.RapportParProjetData;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.soft.ssvapp.DataRetrofit.RapportParProjet.RapportEtatBesoin;
import com.soft.ssvapp.DataRetrofit.RapportParProjet.RapportGrandLivre;
import com.soft.ssvapp.DataRetrofit.RapportParProjet.RapportProjet;

public class RapportParProjetViewModel extends AndroidViewModel {

    RapportParProjetRemote rapportParProjetRemote;
    public RapportParProjetViewModel(@NonNull Application application) {
        super(application);
        rapportParProjetRemote = new RapportParProjetRemote(application);
    }

    public MutableLiveData<Boolean> getIsLoading()
    {
        return rapportParProjetRemote.getIsLoading();
    }

    public MutableLiveData<RapportGrandLivre> getRapportGrandLivre(int compte, String date1, String date2)
    {
        return rapportParProjetRemote.getRapportGrandLivre(compte, date1, date2);
    }

    public MutableLiveData<RapportGrandLivre> getRapportClients(int compte, String date1, String date2)
    {
        return rapportParProjetRemote.getRapportClients(compte, date1, date2);
    }

    public MutableLiveData<Double> getRapportSommeNonValide(String codeProjet, String date1, String date2)
    {
        return rapportParProjetRemote.getRapportSommeNonValide(codeProjet, date1, date2);
    }

    public MutableLiveData<RapportEtatBesoin> getRapportEtatBesoin(String codeProjet, String date1, String date2)
    {
        return rapportParProjetRemote.getRapportEtatbesoin(codeProjet, date1, date2);
    }

    public MutableLiveData<RapportProjet> getRapportProjet(String codeProjet)
    {
        return rapportParProjetRemote.getRapportProjet(codeProjet);
    }
}
