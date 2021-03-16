package com.soft.ssvapp.Fragment_Menu.All_specificProject.RapportPartProjetEtatBesoin;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.soft.ssvapp.DataRetrofit.RapportParProjet.RapportEBParProjetValideEtDecaisse;
import com.soft.ssvapp.DataRetrofit.RapportParProjet.RapportParprojetRetrofitRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RapportEBValideEtDecaisseRepository {

    private Application application;
    private RapportParprojetRetrofitRepository rapportParprojetRetrofitRepository;
    private MutableLiveData<List<RapportEBParProjetValideEtDecaisse>> rapportValideEtDecaisse;
    private MutableLiveData<Boolean> isLoading;

    public RapportEBValideEtDecaisseRepository(Application application)
    {
        this.application = application;
        rapportParprojetRetrofitRepository = RapportParprojetRetrofitRepository.getInstance();
        this.rapportValideEtDecaisse = new MutableLiveData<>();
        this.isLoading = new MutableLiveData<>();
    }

    public LiveData<List<RapportEBParProjetValideEtDecaisse>> getRapportParProjetValideDecaisse(String codeProjet)
    {
        setRapportValideEtDecaisse(codeProjet);
        return rapportValideEtDecaisse;
    }

    public MutableLiveData<Boolean> getIsLoading()
    {
        return isLoading;
    }

    private void setRapportValideEtDecaisse(String codeProejet)
    {
        Call<List<RapportEBParProjetValideEtDecaisse>> call_valide_decaisse =
                rapportParprojetRetrofitRepository.rapportParProjetConnexion().rapportEBParProjetValideEtDecaisse(codeProejet, 1);
        isLoading.postValue(true);
        call_valide_decaisse.enqueue(new Callback<List<RapportEBParProjetValideEtDecaisse>>() {
            @Override
            public void onResponse(Call<List<RapportEBParProjetValideEtDecaisse>> call, Response<List<RapportEBParProjetValideEtDecaisse>> response) {
                if (response.isSuccessful())
                {
                    isLoading.postValue(false);
                    rapportValideEtDecaisse.postValue(response.body());
                }
                else
                {
                    Erreur(response.code());
                }
            }

            @Override
            public void onFailure(Call<List<RapportEBParProjetValideEtDecaisse>> call, Throwable t) {
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
