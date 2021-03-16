package com.soft.ssvapp.Fragment_Menu.FillCompte;

import android.app.Application;
import android.app.ListActivity;
import android.text.style.AlignmentSpan;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.soft.ssvapp.Data.Entity_Compte;

import org.w3c.dom.Entity;

import java.util.List;

public class CompteViewModel extends AndroidViewModel {

    CompteRepository compteRepository;
    private LiveData<List<Entity_Compte>> getAllcompte;
    private LiveData<List<Entity_Compte>> getRavitaementCompte;
    private LiveData<List<Entity_Compte>> getProjetCompte;
    private LiveData<List<Entity_Compte>> getApprovisionementCompte;

    public CompteViewModel(@NonNull Application application) {
        super(application);
        compteRepository = new CompteRepository(application);
        getAllcompte = compteRepository.getGetAllcompte();
        getRavitaementCompte = compteRepository.getGetRavitaementCompte();
        getApprovisionementCompte = compteRepository.getGetApprovisionement();
        getProjetCompte = compteRepository.getGetProjetCompte();
        compteRepository.insertOnline();
    }

    public LiveData<List<Entity_Compte>> getGetAllcompte()
    {
        return getAllcompte;
    }

    public LiveData<List<Entity_Compte>> getGetPayements(int NumCompte)
    {
        return compteRepository.getGetPayementsCompte(NumCompte);
    }

    public LiveData<List<Entity_Compte>> getProjetCompte()
    {
        return getProjetCompte;
    }

    public LiveData<List<Entity_Compte>> getGetRavitaementCompte()
    {
        return getRavitaementCompte;
    }

    public LiveData<List<Entity_Compte>> getGetApprovisionementCompte()
    {
        return getApprovisionementCompte;
    }
}
