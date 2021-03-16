package com.soft.ssvapp.Fragment_Menu.FillPayements.DetailsPayement.remote;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.soft.ssvapp.Data.Entity_DetailBesoin;
import com.soft.ssvapp.DataRetrofit.DetailsEtatDeBesoin.DetailsEtatDeBesoinRepository;
import com.soft.ssvapp.DataRetrofit.DetailsEtatDeBesoin.DetailsEtatDeBesoinRetrofit;
import com.soft.ssvapp.DataRetrofit.EtatDeBesoin.EtatDeBesoinRetrofitDetailEB;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPayementRemoteRepository {
    DetailsEtatDeBesoinRepository detailsEtatDeBesoinRepository;
    private Application application;
    private MutableLiveData<List<DetailsEtatDeBesoinRetrofit>> detailsEtatBesoin ;
    private MutableLiveData<List<EtatDeBesoinRetrofitDetailEB>> detailEBMvt;
    private MutableLiveData<Boolean> isLoading;

    public DetailPayementRemoteRepository(Application application)
    {
        this.application = application;
        detailsEtatBesoin = new MutableLiveData<>();
        isLoading = new MutableLiveData<>();
        detailsEtatDeBesoinRepository = DetailsEtatDeBesoinRepository.getInstance();
        detailEBMvt = new MutableLiveData<>();
    }

    public MutableLiveData<List<DetailsEtatDeBesoinRetrofit>> getDetailsEtatBesoin(String codeEtatBesoin)
    {
        setDetailRapport(codeEtatBesoin);
        return detailsEtatBesoin;
    }

    public LiveData<List<EtatDeBesoinRetrofitDetailEB>> getDetailEBMvt(String codeEtatBesoin)
    {
        setDetailEBMvt(codeEtatBesoin);
        return detailEBMvt;
    }

    private void setDetailEBMvt(String codeEtatBesoin)
    {
        Call<List<EtatDeBesoinRetrofitDetailEB>> call_eb_mvt =
                detailsEtatDeBesoinRepository.getDetailsEtatDeBesoinConnexion().detailMvtEb(codeEtatBesoin);
        isLoading.postValue(true);
        call_eb_mvt.enqueue(new Callback<List<EtatDeBesoinRetrofitDetailEB>>() {
            @Override
            public void onResponse(Call<List<EtatDeBesoinRetrofitDetailEB>> call,
                                   Response<List<EtatDeBesoinRetrofitDetailEB>> response) {
                if (response.isSuccessful())
                {
                    isLoading.postValue(false);
                    detailEBMvt.postValue(response.body());
                }
                else
                {
                    Erreur(response.code());
                }
            }

            @Override
            public void onFailure(Call<List<EtatDeBesoinRetrofitDetailEB>> call, Throwable t) {

            }
        });
    }

    public MutableLiveData<Boolean> getIsLoading()
    {
        return isLoading;
    }

    public void deleteDetailRemote(Entity_DetailBesoin entity_detailBesoin)
    {
        DetailsEtatDeBesoinRetrofit detailsRetrofit =
                new DetailsEtatDeBesoinRetrofit(
                        entity_detailBesoin.getCodeEtatdeBesoin(),
                        entity_detailBesoin.getCodeArticle(),
                        entity_detailBesoin.getCodeLigne(),
                        entity_detailBesoin.getLibelleDetail(),
                        entity_detailBesoin.getQte(),
                        entity_detailBesoin.getPu(),
                        entity_detailBesoin.getSortie(),
                        entity_detailBesoin.getEntree());
        detailsRetrofit.setIdDetailEB(entity_detailBesoin.getIdDetailEBOnline());
        Call<Boolean> call_effacer_detail =
                detailsEtatDeBesoinRepository.getDetailsEtatDeBesoinConnexion().effaceDetailEtatBesoin(detailsRetrofit);
        isLoading.postValue(true);
        call_effacer_detail.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful())
                {
                    isLoading.postValue(false);
                    if (response.body() == true)
                    {
                        Toast.makeText(application, "1 element effacé", Toast.LENGTH_LONG).show();
                        setDetailRapport(entity_detailBesoin.getCodeEtatdeBesoin());
                    }
                    else
                    {
                        Toast.makeText(application, "la tantative de suppression a échouée : "
                                + response.body(), Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Erreur(response.code());
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                isLoading.postValue(false);
                Toast.makeText(application, "Connexion problem.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void updateDetailRemote(Entity_DetailBesoin entity_detailBesoin)
    {
        DetailsEtatDeBesoinRetrofit detailsRetrofit =
                new DetailsEtatDeBesoinRetrofit(
                        entity_detailBesoin.getCodeEtatdeBesoin(),
                        entity_detailBesoin.getCodeArticle(),
                        entity_detailBesoin.getCodeLigne(),
                        entity_detailBesoin.getLibelleDetail(),
                        entity_detailBesoin.getQte(),
                        entity_detailBesoin.getPu(),
                        entity_detailBesoin.getSortie(),
                        entity_detailBesoin.getEntree());
        detailsRetrofit.setIdDetailEB(entity_detailBesoin.getIdDetailEBOnline());
        Call<Boolean> call_modifier_detail =
                detailsEtatDeBesoinRepository.getDetailsEtatDeBesoinConnexion().modifierDetailsEtatBesoin(detailsRetrofit);
        isLoading.postValue(true);
        call_modifier_detail.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful())
                {
                    isLoading.postValue(false);
                    if (response.body() == true)
                    {
                        Toast.makeText(application, "Mofication a été bien faite.", Toast.LENGTH_LONG).show();
                        setDetailRapport(entity_detailBesoin.getCodeEtatdeBesoin());
                    }
                    else
                    {
                        Toast.makeText(application, "Mofication échoue : " + response.body(), Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Erreur(response.code());
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                isLoading.postValue(false);
                Toast.makeText(application, "Connexion problem.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setDetailRapport(String code_etat_besoin)
    {
        Call<List<DetailsEtatDeBesoinRetrofit>> call_detail_besoin =
                detailsEtatDeBesoinRepository.getDetailsEtatDeBesoinConnexion().getListDetailsBesoin(code_etat_besoin);
        isLoading.postValue(true);
        call_detail_besoin.enqueue(new Callback<List<DetailsEtatDeBesoinRetrofit>>() {
            @Override
            public void onResponse(Call<List<DetailsEtatDeBesoinRetrofit>> call, Response<List<DetailsEtatDeBesoinRetrofit>> response) {
                if (response.isSuccessful())
                {
                    isLoading.postValue(false);
                    detailsEtatBesoin.postValue(response.body());
                }
                else
                {
                    Erreur(response.code());
                }
            }

            @Override
            public void onFailure(Call<List<DetailsEtatDeBesoinRetrofit>> call, Throwable t) {
                isLoading.postValue(false);
                Toast.makeText(application, "Connexion Problem.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void Erreur(int code)
    {
        switch (code)
        {
            case 404:
                Toast.makeText(application, "server not found.", Toast.LENGTH_LONG).show();
                break;
            case 500:
                Toast.makeText(application, "server broken.", Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(application, "unknown problem.", Toast.LENGTH_LONG).show();
                break;
        }
    }
}

