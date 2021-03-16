package com.soft.ssvapp.Fragment_Menu.Fill_Rapport.DetailRapport;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.soft.ssvapp.DataRetrofit.Rapport.RapportOperationResponse;
import com.soft.ssvapp.DataRetrofit.Rapport.RapportRetrofitRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailRapportRemoteRepository {

    RapportRetrofitRepository rapportRetrofitRepository;
    private Application application;
    private MutableLiveData<List<RapportOperationResponse>> listMutableLiveData;
    private MutableLiveData<Boolean> isLoading;
    public DetailRapportRemoteRepository(Application application)
    {
        this.application = application;
        rapportRetrofitRepository = RapportRetrofitRepository.getInstance();
        this.listMutableLiveData = new MutableLiveData<>();
        this.isLoading = new MutableLiveData<>();
    }

    public MutableLiveData<Boolean> getIsLoading()
    {
        return isLoading;
    }

    public void supprimerOperation(String numOperation)
    {
        Call<Integer> call_supprimerOperation = rapportRetrofitRepository.rapportConnexion().supprimerOperation(numOperation);
        isLoading.postValue(true);
        call_supprimerOperation.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful())
                {
                    isLoading.postValue(false);
                    if (response.body() == 1)
                    {
                        Toast.makeText(application, "Opération de suppression effectué", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(application, "la tantative de suppression a échouée", Toast.LENGTH_LONG).show();
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
                            Toast.makeText(application, "Problème inconnue", Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                isLoading.postValue(false);
                Toast.makeText(application, "Problème de connexion", Toast.LENGTH_LONG).show();
            }
        });

    }

    public MutableLiveData<List<RapportOperationResponse>> getListMutableLiveData(String numOperation)
    {
        setListMutableLiveData(numOperation);
        return listMutableLiveData;
    }

    public void setListMutableLiveData(String numOperation)
    {
        Call<List<RapportOperationResponse>> call_listOperation =
                rapportRetrofitRepository.rapportConnexion().rapportDetailOperation(numOperation);
        isLoading.postValue(true);
        call_listOperation.enqueue(new Callback<List<RapportOperationResponse>>() {
            @Override
            public void onResponse(Call<List<RapportOperationResponse>> call, Response<List<RapportOperationResponse>> response) {
                if (response.isSuccessful())
                {
                    isLoading.postValue(false);
                    listMutableLiveData.postValue(response.body());
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
                            Toast.makeText(application, "Probleme inconnue", Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<RapportOperationResponse>> call, Throwable t) {
                isLoading.postValue(false);
                Toast.makeText(application, "Probleme de connexion", Toast.LENGTH_LONG).show();
            }
        });
    }
}
