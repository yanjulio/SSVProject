package com.soft.ssvapp.Fragment_Menu.All_specificProject.RapportLignePartArticle;

import android.app.Application;
import android.text.method.MultiTapKeyListener;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.soft.ssvapp.DataRetrofit.RapportParProjet.RapportLigneParArticleResponse;
import com.soft.ssvapp.DataRetrofit.RapportParProjet.RapportParprojetRetrofitRepository;

import java.util.List;
import java.util.function.BinaryOperator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RapportLigneParArticleRepository {
    private Application application;
    RapportParprojetRetrofitRepository rapportParprojetRetrofitRepository;
    private MutableLiveData<List<RapportLigneParArticleResponse>> rapportLigne;
    private MutableLiveData<Boolean> isloading;

    public RapportLigneParArticleRepository(Application application)
    {
        this.application = application;
        rapportParprojetRetrofitRepository = RapportParprojetRetrofitRepository.getInstance();
        this.rapportLigne = new MutableLiveData<>();
        this.isloading = new MutableLiveData<>();
    }

    public MutableLiveData<Boolean> getIsloading()
    {
        return isloading;
    }

    public MutableLiveData<List<RapportLigneParArticleResponse>> getRapportLigne(String codeProjet, String codeArticle)
    {
        setRapportLigne(codeProjet, codeArticle);
        return rapportLigne;
    }

    void setRapportLigne(String codeProjet, String codeArticle)
    {
        Call<List<RapportLigneParArticleResponse>> rapport_ligne =
                rapportParprojetRetrofitRepository.rapportParProjetConnexion().rapportLigneParArticle(codeProjet, codeArticle);
        isloading.postValue(true);
        rapport_ligne.enqueue(new Callback<List<RapportLigneParArticleResponse>>() {
            @Override
            public void onResponse(Call<List<RapportLigneParArticleResponse>> call, Response<List<RapportLigneParArticleResponse>> response) {
                if (response.isSuccessful())
                {
                    isloading.postValue(false);
                    rapportLigne.postValue(response.body());
                }
                else
                {
                    Erreur(response.code());
                }
            }

            @Override
            public void onFailure(Call<List<RapportLigneParArticleResponse>> call, Throwable t) {
                isloading.postValue(false);
                Toast.makeText(application, "Problème de connexion", Toast.LENGTH_LONG).show();
            }
        });
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
                Toast.makeText(application, "Problème inconnue", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
