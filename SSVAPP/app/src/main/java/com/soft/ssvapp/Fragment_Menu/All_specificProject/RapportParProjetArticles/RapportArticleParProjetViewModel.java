package com.soft.ssvapp.Fragment_Menu.All_specificProject.RapportParProjetArticles;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.soft.ssvapp.DataRetrofit.RapportParProjet.RapportArticleResponse;

import java.util.List;

public class RapportArticleParProjetViewModel extends AndroidViewModel {

    RapportArticleParProjetRepository articleParProjetRepository;

    public RapportArticleParProjetViewModel(@NonNull Application application) {
        super(application);
        articleParProjetRepository = new RapportArticleParProjetRepository(application);
    }

    public MutableLiveData<List<RapportArticleResponse>> getRapportArticles(String codeProjet)
    {
        return articleParProjetRepository.getRapportArticle(codeProjet);
    }

    public MutableLiveData<Boolean> getLoading()
    {
        return  articleParProjetRepository.getIsLoading();
    }

    public MutableLiveData<List<RapportArticleResponse>> getArticleSearchQuery(String searchQuery)
    {
        return articleParProjetRepository.getArticleSearchQuery(searchQuery);
    }
}
