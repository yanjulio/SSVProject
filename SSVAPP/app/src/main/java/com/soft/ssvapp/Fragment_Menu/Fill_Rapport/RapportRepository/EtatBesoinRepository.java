package com.soft.ssvapp.Fragment_Menu.Fill_Rapport.RapportRepository;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.soft.ssvapp.DataRetrofit.Rapport.RapportEtatBesoinResponse;
import com.soft.ssvapp.DataRetrofit.Rapport.RapportRetrofitRepository;

import java.util.List;

public class EtatBesoinRepository {

    RapportRetrofitRepository rapportRetrofitRepository;
    MutableLiveData<List<RapportEtatBesoinResponse>> mutableLiveData_rapportEtatBesoin;

    public EtatBesoinRepository(Application application)
    {
        rapportRetrofitRepository = RapportRetrofitRepository.getInstance();
//        mutableLiveData_rapportEtatBesoin = new MutableLiveData<>();
    }

//    public MutableLiveData<List<RapportEtatBesoinResponse>> getMutableLiveData_rapportEtatBesoin()
//    {
//        return
//    }
}
