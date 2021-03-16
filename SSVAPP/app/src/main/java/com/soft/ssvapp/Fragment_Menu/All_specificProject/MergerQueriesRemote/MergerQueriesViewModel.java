package com.soft.ssvapp.Fragment_Menu.All_specificProject.MergerQueriesRemote;

import android.app.Application;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.soft.ssvapp.DataRetrofit.RapportParProjet.RapportEtatBesoin;
import com.soft.ssvapp.DataRetrofit.RapportParProjet.RapportGrandLivre;
import com.soft.ssvapp.DataRetrofit.RapportParProjet.RapportProjet;

public class MergerQueriesViewModel extends AndroidViewModel {

    private MergerQueriesRepository mergerQueriesRepository;

    public MergerQueriesViewModel(@NonNull Application application) {
        super(application);
        mergerQueriesRepository = new MergerQueriesRepository(application);
    }

    public void init_mergerQueries(String codeProjet, int compte_client, int compte_projet, String date1, String date2)
    {
        mergerQueriesRepository.setMergerQueries(codeProjet, compte_client, compte_projet, date1, date2);
    }

    public LiveData<Boolean> getIsLoading()
    {
        return mergerQueriesRepository.getIsLoading();
    }

    public LiveData<RapportProjet> getRapportParProjet()
    {
        return mergerQueriesRepository.getRapportParProjet();
    }

    public LiveData<RapportEtatBesoin> getRapporteEB()
    {
        return mergerQueriesRepository.getRapporteEB();
    }

    public LiveData<RapportGrandLivre> getRapportGrandLivre()
    {
        return mergerQueriesRepository.getRapportGrandLivre();
    }

    public LiveData<Double> getRapportEBNonValide()
    {
        return mergerQueriesRepository.getRapportEBNonValide();
    }
}
