package com.soft.ssvapp.Fragment_Menu.Fill_Rapport.RapportEtatDeBesoin.RapportEtatBesoinDataRemote;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import com.soft.ssvapp.DataRetrofit.EtatDeBesoin.EtatDeBesoinRepositoryRetrofit;
import com.soft.ssvapp.DataRetrofit.EtatDeBesoin.EtatDeBesoinRetrofit;
import com.soft.ssvapp.DataRetrofit.Rapport.RapportEtatBesoinResponse;
import com.soft.ssvapp.DataRetrofit.Rapport.RapportRetrofitRepository;
import com.soft.ssvapp.Firebasefiles.EnvoiNotification;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EtatBesoinRemoteRepository {

    RapportRetrofitRepository rapportRetrofitRepository;
    EtatDeBesoinRepositoryRetrofit etatDeBesoinRepositoryRetrofit;
    private Application application;
    private MutableLiveData<List<RapportEtatBesoinResponse>> etatBesoin ;
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<String> etat_set;
    SharedPreferences prefs;

    public EtatBesoinRemoteRepository(Application application)
    {
        rapportRetrofitRepository = RapportRetrofitRepository.getInstance();
        etatDeBesoinRepositoryRetrofit = EtatDeBesoinRepositoryRetrofit.getInstance();
        this.application = application;
        etatBesoin = new MutableLiveData<>();
        isLoading = new MutableLiveData<>();
        etat_set = new MutableLiveData<>();
        prefs = application.getSharedPreferences("lOGIN_USER", Context.MODE_PRIVATE);
    }

    public void setEtat_set(String etat_set)
    {
        this.etat_set.postValue(etat_set);
    }

    public MutableLiveData<String> getEtat_set()
    {
        return etat_set;
    }

    public MutableLiveData<Boolean> getIsLoading()
    {
        return isLoading;
    }

    public MutableLiveData<List<RapportEtatBesoinResponse>> getEtatBesoin(String date1, String  date2)
    {
        setEtatBesoinParperiode(date1, date2);
        return etatBesoin;
    }

    public void supprimerEtatBesoin(String codeEtatBeson, String date1, String date2)
    {
        Call<Boolean> call_supprimer_etatBesoin =
                etatDeBesoinRepositoryRetrofit.etatDeBesoinConnexion()
                        .supprimerEtatBesoin(codeEtatBeson);
        isLoading.postValue(true);
        call_supprimer_etatBesoin.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful())
                {
                    isLoading.postValue(false);
                    if (response.body()==true)
                    {
                        Toast.makeText(application, "Suppression réussi", Toast.LENGTH_LONG).show();
                        setEtatBesoinParperiode(date1, date2); // to refresh the list
                    }
                    else
                    {
                        Toast.makeText(application, "la tantative de suppression a échoué", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    switch (response.code())
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

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                isLoading.postValue(false);
                Toast.makeText(application,
                        "Problème de connexion, verfier votre connexion puis reessayer",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void ModifierEtatBesoin(EtatDeBesoinRetrofit etatDeBesoinRetrofit, String codeEtatBesoin, String date1, String date2)
    {
        Call<Void> call_modier =
                etatDeBesoinRepositoryRetrofit.etatDeBesoinConnexion().modifierEtatDeBesoin(etatDeBesoinRetrofit, codeEtatBesoin);
        isLoading.postValue(true);
        call_modier.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful())
                {
                    isLoading.postValue(false);
                    String nomtUtilisateur_receiver = prefs.getString("USERNAME_RECEIVER", "");
                    new EnvoiNotification("SSV" ,
                            "L'état de besoin " +etatDeBesoinRetrofit.getDesignationEtatDeBesion()+
                                    " du projet " + etatDeBesoinRetrofit.getCodeProject() +" a été validé.",
                            etatDeBesoinRetrofit.getCodeProject(),
                            etatDeBesoinRetrofit.getDesignationEtatDeBesion(),"Valides.", etatDeBesoinRetrofit.getDemandeur(),
                            "ADMIN", nomtUtilisateur_receiver).execute();
                    Toast.makeText(application, "Validation bien faite.",
                            Toast.LENGTH_LONG).show();
                    etat_set.postValue("VALIDE");
                    setEtatBesoinParperiode(date1, date2); // to get one more the new list after modifying
                }
                else
                {
                    switch (response.code())
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

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                isLoading.postValue(false);
                Toast.makeText(application, "Problème de connexion", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setEtatBesoinParperiode(String date1, String date2)
    {
        Call<List<RapportEtatBesoinResponse>> call_listEtatBesoin =
                rapportRetrofitRepository.rapportConnexion().listEtatBesoinParPeriode(date1, date2);
        isLoading.postValue(true);
        call_listEtatBesoin.enqueue(new Callback<List<RapportEtatBesoinResponse>>() {
            @Override
            public void onResponse(Call<List<RapportEtatBesoinResponse>> call, Response<List<RapportEtatBesoinResponse>> response) {
                if (response.isSuccessful())
                {
                    isLoading.postValue(false);
                    etatBesoin.postValue(response.body());
                }
                else
                {
                    switch (response.code())
                    {
                        case 404:
                            Toast.makeText(application,"serveur introuvable.", Toast.LENGTH_LONG).show();
                            break;
                        case 500:
                            Toast.makeText(application, "Erreur serveur.", Toast.LENGTH_LONG).show();
                            break;
                        default:
                            Toast.makeText(application, "Erreur inconnue", Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<RapportEtatBesoinResponse>> call, Throwable t) {
                isLoading.postValue(false);
                Toast.makeText(application, "Problème de connexion", Toast.LENGTH_LONG).show();
            }
        });
    }
}
