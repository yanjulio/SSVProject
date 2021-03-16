package com.soft.ssvapp.Fragment_Menu.CompteAjout;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.soft.ssvapp.DataRetrofit.Compte.CompteRemote;
import com.soft.ssvapp.DataRetrofit.Compte.RapportCompteResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompteRepository {

    private Application application;
    private CompteRemote compteRemote;
    private MutableLiveData<List<RapportCompteResponse>> listeCompteParGroupe;
//    private MutableLiveData<List<RapportLibelleCompteResponse>> listeLibelleCompte;
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<Integer> dernierCompte;
    private MutableLiveData<Integer> ajouter;

    public CompteRepository(Application application)
    {
        this.application = application;
        this.compteRemote = CompteRemote.getInstance();
//        this.rapportRetrofitRepository = RapportRetrofitRepository.getInstance();
        this.isLoading = new MutableLiveData<>();
        this.dernierCompte = new MutableLiveData<>();
        listeCompteParGroupe = new MutableLiveData<>();
//        this.listeLibelleCompte = new MutableLiveData<>();
        this.ajouter = new MutableLiveData<>();
    }

    public MutableLiveData<Boolean> getIsLoading()
    {
        return isLoading;
    }

    public LiveData<Integer> getDernieCompte(String groupeCompte)
    {
        setDernierCompte(groupeCompte);
        return dernierCompte;
    }

    public void setDernierCompte(String groupe_compte)
    {
        Call<Integer> call_dernier_compte = compteRemote.compteConnexion().dernierCompte(groupe_compte);
        isLoading.postValue(true);
        call_dernier_compte.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful())
                {
                    isLoading.postValue(false);
                    dernierCompte.postValue(response.body());
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

//    public LiveData<List<RapportCompteResponse>> getListeCompteParGroupe(String groupeCompte)
//    {
//        setCompteParGroupe(groupeCompte);
//        return listeCompteParGroupe;
//    }

//    public void modifierLibelle(RapportAjouterLibelleCompte rapportAjouterLibelleCompte)
//    {
//        Call<Integer> call_modifierLibelle = rapportRetrofitRepository.rapportConnexion().modifierLibelle(rapportAjouterLibelleCompte);
//        isLoading.postValue(true);
//        call_modifierLibelle.enqueue(new Callback<Integer>() {
//            @Override
//            public void onResponse(Call<Integer> call, Response<Integer> response) {
//                if (response.isSuccessful())
//                {
//                    isLoading.postValue(false);
//                    setLibelleCompte(String.valueOf(rapportAjouterLibelleCompte.getCompte()));
//                }
//                else
//                {
//                    Erreur(response.code());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Integer> call, Throwable t) {
//                isLoading.postValue(false);
//                Toast.makeText(application, "Problème de connexion", Toast.LENGTH_LONG).show();
//            }
//        });
//    }

//    public void AjouterLibelle(RapportAjouterLibelleCompte rapportLibelleCompteResponse, String compte)
//    {
//        Call<Integer> call_ajouteLibelle =
//                rapportRetrofitRepository.rapportConnexion().ajouterLibelle(rapportLibelleCompteResponse);
//        isLoading.postValue(true);
//        call_ajouteLibelle.enqueue(new Callback<Integer>() {
//            @Override
//            public void onResponse(Call<Integer> call, Response<Integer> response) {
//                if (response.isSuccessful())
//                {
//                    isLoading.postValue(false);
//                    setLibelleCompte(compte);
//                }
//                else
//                {
//                    Erreur(response.code());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Integer> call, Throwable t) {
//                isLoading.postValue(false);
//                Toast.makeText(application, "Problème de connexion", Toast.LENGTH_LONG).show();
//            }
//        });
//    }

//    public LiveData<List<RapportLibelleCompteResponse>> getListeLibelleCompte(String compte)
//    {
//        setLibelleCompte(compte);
//        return listeLibelleCompte;
//    }

//    public void setLibelleCompte(String compte)
//    {
//        Call<List<RapportLibelleCompteResponse>> call_libelle_compte =
//                rapportRetrofitRepository.rapportConnexion().rapportLibelleCompte(compte);
//        isLoading.postValue(true);
//        call_libelle_compte.enqueue(new Callback<List<RapportLibelleCompteResponse>>() {
//            @Override
//            public void onResponse(Call<List<RapportLibelleCompteResponse>> call, Response<List<RapportLibelleCompteResponse>> response) {
//                if (response.isSuccessful())
//                {
//                    isLoading.postValue(false);
//                    Toast.makeText(application, "compte : " + compte
//                            + " \n size : " + response.body().size(), Toast.LENGTH_LONG).show();
//                    listeLibelleCompte.postValue(response.body());
//                }
//                else
//                {
//                    Erreur(response.code());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<RapportLibelleCompteResponse>> call, Throwable t) {
//                isLoading.postValue(false);
//                Toast.makeText(application, "Problème de connexion", Toast.LENGTH_LONG).show();
//            }
//        });
//    }

//    public void setCompteParGroupe(String groupeCompte)
//    {
//        Call<List<RapportCompteResponse>> call_compte = rapportRetrofitRepository.rapportConnexion().listeDeCompteParGroupe(groupeCompte);
//        isLoading.postValue(true);
//        call_compte.enqueue(new Callback<List<RapportCompteResponse>>() {
//            @Override
//            public void onResponse(Call<List<RapportCompteResponse>> call, Response<List<RapportCompteResponse>> response) {
//                if (response.isSuccessful())
//                {
//                    isLoading.postValue(false);
//                    listeCompteParGroupe.postValue(response.body());
//                }
//                else
//                {
//                    Erreur(response.code());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<RapportCompteResponse>> call, Throwable t) {
//                isLoading.postValue(false);
//                Toast.makeText(application, "Probleme de connexion", Toast.LENGTH_LONG).show();
//            }
//        });
//    }

    public LiveData<Integer> getEnregistrerCompte(RapportCompteResponse rapportCompteResponse)
    {
        AjouterCompte(rapportCompteResponse);
        return ajouter;
    }

    public void AjouterCompte(RapportCompteResponse rapportCompteResponse)
    {
        Call<Integer> call_ajouter_compte = compteRemote.compteConnexion().addCompte(rapportCompteResponse);
        isLoading.postValue(true);
        call_ajouter_compte.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful())
                {
                    isLoading.postValue(false);
                    if (response.body() ==1)
                    {
                        ajouter.postValue(response.body());
                        Toast.makeText(application, "le Compte a été bien ajouté",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(application, "Echec d'enregistrement", Toast.LENGTH_LONG).show();
                    }
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

    void Erreur(int code)
    {
        switch (code)
        {
            case 404:
                Toast.makeText(application, "Serveur introuvable", Toast.LENGTH_LONG).show();
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
