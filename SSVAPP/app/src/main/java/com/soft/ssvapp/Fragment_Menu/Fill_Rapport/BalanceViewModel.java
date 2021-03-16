package com.soft.ssvapp.Fragment_Menu.Fill_Rapport;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.soft.ssvapp.DataRetrofit.Compte.RapportCompteResponse;

import java.util.List;

public class BalanceViewModel extends AndroidViewModel {

    BalanceRapository balanceRepository;

    public BalanceViewModel(@NonNull Application application) {
        super(application);
        balanceRepository = new BalanceRapository(application);
    }

    public void modifierCompte(RapportCompteResponse rapportCompteResponse)
    {
        balanceRepository.modifierCompte(rapportCompteResponse);
    }

    public LiveData<Boolean> getIsLoading()
    {
        return balanceRepository.getIsloading();
    }

    public LiveData<List<Balance_objet>> getMutableBalance(String compte_balance)
    {
        return balanceRepository.getBalanceMutable(compte_balance);
    }
}
