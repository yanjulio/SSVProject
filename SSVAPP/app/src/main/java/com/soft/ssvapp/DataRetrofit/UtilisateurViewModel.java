package com.soft.ssvapp.DataRetrofit;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class UtilisateurViewModel extends AndroidViewModel {

    private UtilisateurRemote utilisateurRepository;
    private LiveData<UtilisateurResponse> utilisateurResponseLiveData;

    public UtilisateurViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(Context context)
    {
        utilisateurRepository = new UtilisateurRemote();
        utilisateurResponseLiveData = utilisateurRepository.getResponseLiveData();
    }

    public void Login(String nomUtilisateur, String motDePasse)
    {
        utilisateurRepository.Login(nomUtilisateur, motDePasse);
//        return utilisateurRepository.getRetrun_value(); // ce return est independant a la methode
    }

    public LiveData<UtilisateurResponse> getConnexionLiveData()
    {
        return utilisateurResponseLiveData;
    }
}
