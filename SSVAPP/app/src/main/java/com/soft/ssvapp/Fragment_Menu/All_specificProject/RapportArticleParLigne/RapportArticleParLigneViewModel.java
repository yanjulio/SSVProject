package com.soft.ssvapp.Fragment_Menu.All_specificProject.RapportArticleParLigne;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.soft.ssvapp.DataRetrofit.RapportParProjet.RapportArticleParLigneResponse;

import java.util.List;

public class RapportArticleParLigneViewModel extends AndroidViewModel {

    private RapportArticleParLigneRepository rapportArticleParLigneRepository;

    public RapportArticleParLigneViewModel(@NonNull Application application) {
        super(application);
        rapportArticleParLigneRepository = new RapportArticleParLigneRepository(application);
    }

    public MutableLiveData<List<RapportArticleParLigneResponse>> getRapportArticleParLigne(String codeProjet, String codeLigne)
    {
        return rapportArticleParLigneRepository.getRapportArticleParLigne(codeProjet, codeLigne);
    }

    public MutableLiveData<Boolean> getIsLoading()
    {
        return rapportArticleParLigneRepository.getIsLoading();
    }
}
