package com.soft.ssvapp.Utilisateurs;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.soft.ssvapp.DataRetrofit.Compte.RapportCompteResponse;
import com.soft.ssvapp.DataRetrofit.UtilisateurResponse;

import java.util.List;

public class UtilisateurViewModel extends AndroidViewModel {

    UtilisateurRepository utilisateurRepository;

    public UtilisateurViewModel(@NonNull Application application) {
        super(application);
        utilisateurRepository = new UtilisateurRepository(application);
    }

    public void creerUtilisateur(UtilisateurResponse utilisateurResponse)
    {
        utilisateurRepository.CreerUtilisateur(utilisateurResponse);
    }

    public void modifierUtilisateur(UtilisateurResponse utilisateurResponse)
    {
        utilisateurRepository.modifier(utilisateurResponse);
    }

    public MutableLiveData<Integer> getIsCreated()
    {
        return utilisateurRepository.getIsCreated();
    }

    public MutableLiveData<Boolean> getIsLoagin()
    {
        return utilisateurRepository.getIsLoading();
    }

    public LiveData<List<UtilisateurResponse>> getListeUtilisateur()
    {
        return utilisateurRepository.getListeUtilisateur();
    }

    public LiveData<List<RapportCompteResponse>> getListCompte()
    {
        return utilisateurRepository.getListeCompte();
    }

//    public LiveData<List<RapportCompteResponse>> getListeCompteDeclaration()
//    {
//        return utilisateurRepository.getListeCompteDeclaration();
//    }
}
