package com.soft.ssvapp.Fragment_Menu.All_specificProject.RapportLignePartArticle;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.soft.ssvapp.DataRetrofit.RapportParProjet.RapportLigneParArticleResponse;

import java.util.List;

public class RapportLigneParArticleViewModel extends AndroidViewModel {

    private RapportLigneParArticleRepository rapportLigneParArticleRepository;

    public RapportLigneParArticleViewModel(@NonNull Application application) {
        super(application);
        rapportLigneParArticleRepository = new RapportLigneParArticleRepository(application);
    }

    public MutableLiveData<List<RapportLigneParArticleResponse>> getRapportLigneParArticle(String codeProjet, String codeArticle)
    {
        return rapportLigneParArticleRepository.getRapportLigne(codeProjet, codeArticle);
    }

    public MutableLiveData<Boolean> getIsLoading()
    {
        return rapportLigneParArticleRepository.getIsloading();
    }
}
