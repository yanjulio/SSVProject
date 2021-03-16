package com.soft.ssvapp.Fragment_Menu.All_specificProject.RapportArticleParLigne;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import com.soft.ssvapp.DataRetrofit.RapportParProjet.RapportArticleParLigneResponse;
import com.soft.ssvapp.DataRetrofit.RapportParProjet.RapportParprojetRetrofitRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RapportArticleParLigneRepository {
    private Application application;
    private RapportParprojetRetrofitRepository rapportParprojetRetrofitRepository;
    private MutableLiveData<List<RapportArticleParLigneResponse>> rapportArticleParLigne;
    private MutableLiveData<Boolean> isLoading;

    public RapportArticleParLigneRepository(Application application)
    {
        this.application = application;
        rapportParprojetRetrofitRepository = RapportParprojetRetrofitRepository.getInstance();
        this.rapportArticleParLigne = new MutableLiveData<>();
        this.isLoading = new MutableLiveData<>();
    }

    public MutableLiveData<Boolean> getIsLoading()
    {
        return isLoading;
    }

    public MutableLiveData<List<RapportArticleParLigneResponse>> getRapportArticleParLigne(String codeProjet, String codeLigne)
    {
        setRapportArticleParLigne(codeProjet, codeLigne);
        return rapportArticleParLigne;
    }

    void setRapportArticleParLigne(String codeProjet, String codeLigne)
    {
        Call<List<RapportArticleParLigneResponse>> call_article_parligne =
                rapportParprojetRetrofitRepository.rapportParProjetConnexion().rapportArticleParLigne(codeProjet, codeLigne);
        isLoading.postValue(true);
        call_article_parligne.enqueue(new Callback<List<RapportArticleParLigneResponse>>() {
            @Override
            public void onResponse(Call<List<RapportArticleParLigneResponse>> call, Response<List<RapportArticleParLigneResponse>> response) {
                if (response.isSuccessful())
                {
                    isLoading.postValue(false);
                    rapportArticleParLigne.postValue(response.body());
                }
                else
                {
                    Erreur(response.code());
                }
            }

            @Override
            public void onFailure(Call<List<RapportArticleParLigneResponse>> call, Throwable t) {
                isLoading.postValue(false);
                Toast.makeText(application, "Problème de connexion", Toast.LENGTH_LONG);
            }
        });
    }

    void Erreur(int code)
    {
        switch (code)
        {
            case 404:
                Toast.makeText(application, "Serveur introuvable", Toast.LENGTH_LONG);
                break;
            case 500:
                Toast.makeText(application, "Erreur Serveur", Toast.LENGTH_LONG);
                break;
            default:
                Toast.makeText(application, "Erreur Inconnue", Toast.LENGTH_LONG);
                break;
        }
    }
}
