package com.soft.ssvapp.Fragment_Menu.Fill_Rapport;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.soft.ssvapp.DataRetrofit.Compte.RapportCompteResponse;
import com.soft.ssvapp.DataRetrofit.Rapport.RapportResponse;
import com.soft.ssvapp.DataRetrofit.Rapport.RapportRetrofitRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import com.soft.freemanbusiness.Balance.Balance_objet;

public class BalanceRapository {

    RapportRetrofitRepository rapportRetrofitRepository;
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<List<Balance_objet>> balance_mutable;
    Application application;

    public BalanceRapository(Application application)
    {
        rapportRetrofitRepository = RapportRetrofitRepository.getInstance();
        this.application = application;
        this.isLoading = new MutableLiveData<>();
        this.balance_mutable = new MutableLiveData<>();
    }

    public void modifierCompte(RapportCompteResponse rapportCompteResponse)
    {
        Call<Integer> call_modifier_compte = rapportRetrofitRepository.rapportConnexion().modifierCompte(rapportCompteResponse);
        isLoading.postValue(true);
        call_modifier_compte.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful())
                {
                    isLoading.postValue(false);
                    balance(String.valueOf(rapportCompteResponse.getGroupeCompte()));
                    Toast.makeText(application, "la mofication a été bien faite", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Erreur(response.code());
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                isLoading.postValue(false);
                Toast.makeText(application, "Connexion Problem.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public LiveData<Boolean> getIsloading()
    {
        return isLoading;
    }

    public LiveData<List<Balance_objet>> getBalanceMutable(String compte_groupe)
    {
        balance(compte_groupe);
        return balance_mutable;
    }

    public void balance(String compte_groupte)
    {
        Call<List<RapportResponse>> call_balance = rapportRetrofitRepository.rapportConnexion().balanceParGroupe(compte_groupte);
        isLoading.postValue(true);
        call_balance.enqueue(new Callback<List<RapportResponse>>() {
            @Override
            public void onResponse(Call<List<RapportResponse>> call, Response<List<RapportResponse>> response) {
                if (response.isSuccessful())
                {
                    isLoading.postValue(false);
                    List<Balance_objet> list_local = new ArrayList<>();
                    for (int a = 0; a < response.body().size(); a++)
                    {
                        Balance_objet balance_objet = new Balance_objet(
                                response.body().get(a).getNumeCompte(),
                                response.body().get(a).getDesignationCompte(),
                                response.body().get(a).getSolde(),
                                response.body().get(a).getDesignationGroupe(),
                                response.body().get(a).getGroupeCompte());
                        list_local.add(balance_objet);
                    }
                    balance_mutable.postValue(list_local);
                }
                else
                {
                    Erreur(response.code());
                }
            }

            @Override
            public void onFailure(Call<List<RapportResponse>> call, Throwable t) {
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
                Toast.makeText(application,"server not found.", Toast.LENGTH_LONG).show();
                break;
            case 500:
                Toast.makeText(application, "server broken. Affichage", Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(application, "unknown problem.", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
