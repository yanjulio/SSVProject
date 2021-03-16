package com.soft.ssvapp.Fragment_Menu.All_specificProject.RapportParProjetArticles;

import android.app.Application;
import android.text.method.Touch;
import android.widget.Toast;

import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.lifecycle.MutableLiveData;

import com.soft.ssvapp.DataRetrofit.RapportParProjet.RapportArticleResponse;
import com.soft.ssvapp.DataRetrofit.RapportParProjet.RapportParprojetRetrofitRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RapportArticleParProjetRepository {

    private Application application;
    private RapportParprojetRetrofitRepository rapportParprojetRetrofitRepository;
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<List<RapportArticleResponse>> rapportArticle;
    private MutableLiveData<List<RapportArticleResponse>> filteredUserMutable;

    public RapportArticleParProjetRepository(Application application)
    {
        this.application = application;
        rapportParprojetRetrofitRepository = RapportParprojetRetrofitRepository.getInstance();
        this.isLoading = new MutableLiveData<>();
        this.rapportArticle = new MutableLiveData<>();
        this.filteredUserMutable = new MutableLiveData<>();
    }

    public MutableLiveData<Boolean> getIsLoading()
    {
        return isLoading;
    }

    public MutableLiveData<List<RapportArticleResponse>> getRapportArticle(String codeProjet)
    {
        setRapportArticle(codeProjet);
        return rapportArticle;
    }

    void setRapportArticle(String codeProjet)
    {
        Call<List<RapportArticleResponse>> article_call =
                rapportParprojetRetrofitRepository.rapportParProjetConnexion().rapportResponseCallArticle(codeProjet);
        isLoading.postValue(true);
        article_call.enqueue(new Callback<List<RapportArticleResponse>>() {
            @Override
            public void onResponse(Call<List<RapportArticleResponse>> call, Response<List<RapportArticleResponse>> response) {
                if (response.isSuccessful())
                {
                    isLoading.postValue(false);
                    rapportArticle.postValue(response.body());
                }
                else
                {
                    Erreur(response.code());
                }
            }

            @Override
            public void onFailure(Call<List<RapportArticleResponse>> call, Throwable t) {
                isLoading.postValue(false);
                Toast.makeText(application, "Problème de connexion", Toast.LENGTH_LONG).show();
            }
        });
    }

    public MutableLiveData<List<RapportArticleResponse>> getArticleSearchQuery(String searchQuery)
    {
        if (searchQuery.isEmpty()) {
            filteredUserMutable = rapportArticle;
            return filteredUserMutable;
        } else {

            ArrayList<RapportArticleResponse> tempFilteredList = new ArrayList<>();
            MutableLiveData<List<RapportArticleResponse>> tempFilteredMutable = new MutableLiveData<>();

            for (RapportArticleResponse response : rapportArticle.getValue())
            {
                // search for user title
                if (response.getDesegnationArticle().toLowerCase().contains(searchQuery))
                {
                    tempFilteredList.add(response);
                }
                tempFilteredMutable.postValue(tempFilteredList);
            }
            filteredUserMutable = tempFilteredMutable;
            return filteredUserMutable;
        }
//        FilterResults filterResults = new FilterResults();
//        filterResults.values = filteredUserList;
//        return filterResults;
    }

    void Erreur(int code)
    {
        switch (code)
        {
            case 404:
                Toast.makeText(application, "Serveur introuvable", Toast.LENGTH_LONG).show();
                break;
            case 500:
                Toast.makeText(application, "Erreur Serveur", Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(application, "Erreur inconnue", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
