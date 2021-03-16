package com.soft.ssvapp.Fragment_Menu.All_specificProject.RapportReferenceEtatBesoinCons;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.soft.ssvapp.DataRetrofit.RapportParProjet.RapportParprojetRetrofitRepository;
import com.soft.ssvapp.DataRetrofit.RapportParProjet.RapportReferenceEtatBesoinConsomme;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RapportReferenceEtatBesoinConsRepository {

    private Application application;
    private RapportParprojetRetrofitRepository rapportParprojetRetrofitRepository;
    private MutableLiveData<List<RapportReferenceEtatBesoinConsomme>> rapportReferenceEtatBesoinCons;
    private MutableLiveData<Boolean> isLoading;

    public RapportReferenceEtatBesoinConsRepository(Application application)
    {
        this.application = application;
        rapportParprojetRetrofitRepository = RapportParprojetRetrofitRepository.getInstance();
        this.rapportReferenceEtatBesoinCons = new MutableLiveData<>();
        this.isLoading = new MutableLiveData<>();
    }

    public MutableLiveData<Boolean> getIsLoading()
    {
        return isLoading;
    }

    public MutableLiveData<List<RapportReferenceEtatBesoinConsomme>> getRapportReferenceEtatBesoinCons(
            String codeProjet, String codeArticle, String codeLigne)
    {
        setRapportReferenceEtatBesoinCons(codeProjet, codeArticle, codeLigne);
        return rapportReferenceEtatBesoinCons;
    }

    void setRapportReferenceEtatBesoinCons(String codeProjet, String codeArticle, String codeLigne)
    {
        Call<List<RapportReferenceEtatBesoinConsomme>> call_article_parligne =
                rapportParprojetRetrofitRepository.rapportParProjetConnexion()
                        .rapportReferenceEtatBesoinConsomme(codeProjet, codeArticle, codeLigne);
        isLoading.postValue(true);
        call_article_parligne.enqueue(new Callback<List<RapportReferenceEtatBesoinConsomme>>() {
            @Override
            public void onResponse(Call<List<RapportReferenceEtatBesoinConsomme>> call,
                                   Response<List<RapportReferenceEtatBesoinConsomme>> response) {
                if (response.isSuccessful())
                {
                    isLoading.postValue(false);
                    rapportReferenceEtatBesoinCons.postValue(response.body());
                }
                else
                {
                    Erreur(response.code());
                }
            }

            @Override
            public void onFailure(Call<List<RapportReferenceEtatBesoinConsomme>> call, Throwable t) {
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
