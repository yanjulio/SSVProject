package com.soft.ssvapp.Fragment_Menu.All_specificProject.RapportParProjetData;

import android.app.Application;
import android.text.method.MultiTapKeyListener;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.soft.ssvapp.DataRetrofit.RapportParProjet.RapportEtatBesoin;
import com.soft.ssvapp.DataRetrofit.RapportParProjet.RapportGrandLivre;
import com.soft.ssvapp.DataRetrofit.RapportParProjet.RapportParprojetRetrofitRepository;
import com.soft.ssvapp.DataRetrofit.RapportParProjet.RapportProjet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RapportParProjetRemote {

    RapportParprojetRetrofitRepository rapportParProjetConnexion;
    private Application application;
    private MutableLiveData<RapportProjet> rapportProjet;
    private MutableLiveData<RapportGrandLivre> rapportGrandLivre;
    private MutableLiveData<RapportGrandLivre> rapportClients;
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<RapportEtatBesoin> rapportEtatbesoin;
    private MutableLiveData<Double> rapportSommeNonValide;

    public RapportParProjetRemote(Application application)
    {
        this.application = application;
        rapportParProjetConnexion = RapportParprojetRetrofitRepository.getInstance();
        this.rapportProjet = new MutableLiveData<>();
        this.isLoading = new MutableLiveData<>();
        this.rapportGrandLivre = new MutableLiveData<>();
        this.rapportClients = new MutableLiveData<>();
        this.rapportEtatbesoin = new MutableLiveData<>();
        this.rapportSommeNonValide = new MutableLiveData<>();
    }

    public MutableLiveData<Boolean> getIsLoading()
    {
        return isLoading;
    }

    public MutableLiveData<Double> getRapportSommeNonValide(String codeProjet, String date1, String date2)
    {
        setRapportSommeNonValide(codeProjet, date1, date2);
        return rapportSommeNonValide;
    }

    void setRapportSommeNonValide(String codeProjet, String date1, String date2)
    {
        Call<Double> call_somme_non_valide = rapportParProjetConnexion.rapportParProjetConnexion().rapportResponseCallSommeEBNonValide(
                codeProjet, date1, date2
        );
        isLoading.postValue(true);
        call_somme_non_valide.enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                if (response.isSuccessful())
                {
                    isLoading.postValue(false);
                    rapportSommeNonValide.postValue(response.body());
                }
                else
                {
                    Erreur(response.code());
                }
            }

            @Override
            public void onFailure(Call<Double> call, Throwable t) {
                isLoading.postValue(false);
                Toast.makeText(application, "Problème de connexion", Toast.LENGTH_LONG).show();
            }
        });
    }

    public MutableLiveData<RapportGrandLivre> getRapportGrandLivre(int compte, String date1, String date2)
    {
        setRapportGrandLivre(compte, date1, date2);
        return rapportGrandLivre;
    }

    public MutableLiveData<RapportGrandLivre> getRapportClients(int compte, String date1, String date2)
    {
        setRapportClients(compte, date1, date2);
        return rapportClients;
    }

    void setRapportClients(int compte, String date1, String date2)
    {
        Call<RapportGrandLivre> call_rapport_grand_livre =
                rapportParProjetConnexion.rapportParProjetConnexion().rapportResponseCallGrandLivre(compte, date1, date2);
        isLoading.postValue(true);
        call_rapport_grand_livre.enqueue(new Callback<RapportGrandLivre>() {
            @Override
            public void onResponse(Call<RapportGrandLivre> call, Response<RapportGrandLivre> response) {
                if (response.isSuccessful())
                {
                    isLoading.postValue(false);
                    rapportClients.postValue(response.body());
                }
                else
                {
                    Erreur(response.code());
                }
            }

            @Override
            public void onFailure(Call<RapportGrandLivre> call, Throwable t) {
                isLoading.postValue(false);
                Toast.makeText(application, "Problème de connexion", Toast.LENGTH_LONG).show();
            }
        });
    }

    public MutableLiveData<RapportEtatBesoin> getRapportEtatbesoin(String codeProjet, String date1, String date2)
    {
        setRapportEtatBesoin(codeProjet, date1, date2);
        return rapportEtatbesoin;
    }

    void setRapportEtatBesoin(String codeProjet, String date1, String date2)
    {
        Call<RapportEtatBesoin> call_etat_besoin =
                rapportParProjetConnexion.rapportParProjetConnexion().rapportResponseCallEtatBesoin(
                        codeProjet, date1, date2
                );
        isLoading.postValue(true);
        call_etat_besoin.enqueue(new Callback<RapportEtatBesoin>() {
            @Override
            public void onResponse(Call<RapportEtatBesoin> call, Response<RapportEtatBesoin> response) {
                if (response.isSuccessful())
                {
                    isLoading.postValue(false);
                    rapportEtatbesoin.postValue(response.body());
                }
                else
                {
                    Erreur(response.code());
                }
            }

            @Override
            public void onFailure(Call<RapportEtatBesoin> call, Throwable t) {
                isLoading.postValue(false);
                Toast.makeText(application, "Problème de connexion", Toast.LENGTH_LONG).show();
            }
        });
    }

    void setRapportGrandLivre(int compte, String date1, String date2)
    {
        Call<RapportGrandLivre> call_rapport_grand_livre =
                rapportParProjetConnexion.rapportParProjetConnexion().rapportResponseCallGrandLivre(compte, date1, date2);
        isLoading.postValue(true);
        call_rapport_grand_livre.enqueue(new Callback<RapportGrandLivre>() {
            @Override
            public void onResponse(Call<RapportGrandLivre> call, Response<RapportGrandLivre> response) {
                if (response.isSuccessful())
                {
                    isLoading.postValue(false);
                    rapportGrandLivre.postValue(response.body());
                }
                else
                {
                    Erreur(response.code());
                }
            }

            @Override
            public void onFailure(Call<RapportGrandLivre> call, Throwable t) {
                isLoading.postValue(false);
                Toast.makeText(application, "Problème de connexion", Toast.LENGTH_LONG).show();
            }
        });
    }

    public MutableLiveData<RapportProjet> getRapportProjet(String codeProjet)
    {
        setRapportProjet(codeProjet);
        return rapportProjet;
    }

    void setRapportProjet(String codeProjet) {
        Call<RapportProjet> call_projet = rapportParProjetConnexion.rapportParProjetConnexion().rapportResponseCallParProjet(codeProjet);
        isLoading.postValue(true);
        call_projet.enqueue(new Callback<RapportProjet>() {
            @Override
            public void onResponse(Call<RapportProjet> call, Response<RapportProjet> response) {
                if (response.isSuccessful())
                {
                    isLoading.postValue(false);
                    rapportProjet.postValue(response.body());
                }
                else
                {
                    Erreur(response.code());
                }
            }

            @Override
            public void onFailure(Call<RapportProjet> call, Throwable t) {
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
                Toast.makeText(application, "Erreur inconnue", Toast.LENGTH_LONG);
                break;
        }
    }
}
