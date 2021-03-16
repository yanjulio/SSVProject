package com.soft.ssvapp.Utilisateurs;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.soft.ssvapp.DataRetrofit.Compte.CompteRemote;
import com.soft.ssvapp.DataRetrofit.Compte.RapportCompteResponse;
import com.soft.ssvapp.DataRetrofit.UtilisateurRemote;
import com.soft.ssvapp.DataRetrofit.UtilisateurResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UtilisateurRepository {

    private Application application;
//    private DataPvRespository data;
    private UtilisateurRemote utilisateurRemote;
    private CompteRemote compteRetrofitRepository;
    private MutableLiveData<List<UtilisateurResponse>> utilisateur_list;
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<Integer> IsCreated;
    private MutableLiveData<List<UtilisateurResponse>> listeUtilsateur;
    private MutableLiveData<List<RapportCompteResponse>> listeCompte;
    private MutableLiveData<List<RapportCompteResponse>> listeCompteDeclaration;

    public UtilisateurRepository(Application application)
    {
        this.application = application;
        this.utilisateurRemote = UtilisateurRemote.getInstance();
        this.compteRetrofitRepository = CompteRemote.getInstance();
        utilisateur_list = new MutableLiveData<>();
        isLoading = new MutableLiveData<>();
        IsCreated = new MutableLiveData<>();
        listeUtilsateur = new MutableLiveData<>();
        listeCompte = new MutableLiveData<>();
        listeCompteDeclaration = new MutableLiveData<>();
    }

    public MutableLiveData<Integer> getIsCreated()
    {
        return IsCreated;
    }

    public MutableLiveData<Boolean> getIsLoading()
    {
        return isLoading;
    }

    public LiveData<List<UtilisateurResponse>> getListeUtilisateur()
    {
        setListeUtilsateur();
        return listeUtilsateur;
    }

//    public LiveData<List<RapportCompteResponse>> getListeCompteDeclaration()
//    {
//        setListe_compte_declaration();
//        return listeCompteDeclaration;
//    }

//    public void setListe_compte_declaration()
//    {
//        Call<List<RapportCompteResponse>> call_compte =
//                compteRetrofitRepository.compteConnexion().compte_ajout_depense("461");
//        call_compte.enqueue(new Callback<List<RapportCompteResponse>>() {
//            @Override
//            public void onResponse(Call<List<RapportCompteResponse>> call, Response<List<RapportCompteResponse>> response) {
//                if (response.isSuccessful())
//                {
//                    listeCompteDeclaration.postValue(response.body());
//                }
//                else
//                {
//                    Erreur(response.code());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<RapportCompteResponse>> call, Throwable t) {
//                Toast.makeText(application, "Problème de connexion", Toast.LENGTH_LONG).show();
//            }
//        });
//    }

    public LiveData<List<RapportCompteResponse>> getListeCompte()
    {
        setListCompte();
        return listeCompte;
    }

    public void setListCompte()
    {
        Call<List<RapportCompteResponse>> call_compte =
                compteRetrofitRepository.compteConnexion().compte_ajout_depense();
        isLoading.postValue(true);
        call_compte.enqueue(new Callback<List<RapportCompteResponse>>() {
            @Override
            public void onResponse(Call<List<RapportCompteResponse>> call, Response<List<RapportCompteResponse>> response) {
                if (response.isSuccessful())
                {
                    isLoading.postValue(false);
                    listeCompte.postValue(response.body());
                }
                else
                {
                    Erreur(response.code());
                }
            }

            @Override
            public void onFailure(Call<List<RapportCompteResponse>> call, Throwable t) {
                isLoading.postValue(false);
                Toast.makeText(application, "Problème de connexion", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setListeUtilsateur()
    {
        Call<List<UtilisateurResponse>> call_listeUtiliateur = utilisateurRemote.utilisateurConnexion().listeUtilisateur();
        isLoading.postValue(true);
        call_listeUtiliateur.enqueue(new Callback<List<UtilisateurResponse>>() {
            @Override
            public void onResponse(Call<List<UtilisateurResponse>> call, Response<List<UtilisateurResponse>> response) {
                if (response.isSuccessful())
                {
                    isLoading.postValue(false);
                    listeUtilsateur.postValue(response.body());
                }
                else
                {
                    Erreur(response.code());
                }
            }

            @Override
            public void onFailure(Call<List<UtilisateurResponse>> call, Throwable t) {
                isLoading.postValue(false);
                Toast.makeText(application, "Problème de connexion", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void modifier(UtilisateurResponse utilisateurResponse)
    {
        Call<Integer> call_modifierUtilisateur =
                utilisateurRemote.utilisateurConnexion().modifierUtilisateur(utilisateurResponse);
        isLoading.postValue(true);
        call_modifierUtilisateur.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful())
                {
                    isLoading.postValue(false);
                    setListeUtilsateur();
                    setListCompte();
//                    setListe_compte_declaration();
                    Toast.makeText(application, "Modification bien faite", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Erreur(response.code());
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                isLoading.postValue(false);
                Toast.makeText(application, "Problème de connexion", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void CreerUtilisateur(UtilisateurResponse utilisateurResponse)
    {
        Call<Integer> call_creer_utilisateur = utilisateurRemote.utilisateurConnexion().creerUtilisateur(utilisateurResponse);
        isLoading.postValue(true);
        call_creer_utilisateur.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful())
                {
                    isLoading.postValue(false);
                    IsCreated.postValue(response.body());
                    Toast.makeText(application, "Enregistrement bien faite", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Erreur(response.code());
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                isLoading.postValue(false);
                Toast.makeText(application, "Problème de connexion", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void Erreur(int code)
    {
        switch (code)
        {
            case 404:
                Toast.makeText(application, "Erreur introuvable", Toast.LENGTH_LONG).show();
                break;
            case 500:
                Toast.makeText(application, "Erreur serveur", Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(application, "Erreur inconnue", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
