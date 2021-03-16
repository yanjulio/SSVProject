package com.soft.ssvapp.Fragment_Menu.CompteAjout;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.soft.ssvapp.DataRetrofit.Compte.RapportCompteResponse;

public class CompteViewModel extends AndroidViewModel {

    CompteRepository compteRepository;
    public CompteViewModel(@NonNull Application application) {
        super(application);
        compteRepository = new CompteRepository(application);
    }

    public MutableLiveData<Boolean> getIsLoading()
    {
        return compteRepository.getIsLoading();
    }

//    public int DernierCompte(String groupeCompte)
//    {
//        return compteRepository.DernierCompte(groupeCompte);
//    }

//    public int ajouterCompte(RapportCompteResponse rapportCompteResponse)
//    {
//       return compteRepository.AjouterCompte(rapportCompteResponse);
//    }

    public LiveData<Integer> getEnregistrer(RapportCompteResponse rapportCompteResponse)
    {
        return compteRepository.getEnregistrerCompte(rapportCompteResponse);
    }
//
//    public LiveData<List<RapportCompteResponse>> getListeCompteParGroupe(String groupeCompte)
//    {
//        return compteRepository.getListeCompteParGroupe(groupeCompte);
//    }
//
//    public void AjouterLibelle(RapportAjouterLibelleCompte rapportLibelleCompteResponse, String compte)
//    {
//        compteRepository.AjouterLibelle(rapportLibelleCompteResponse, compte);
//    }
//
//    public void ModifierLibelle(RapportAjouterLibelleCompte rapportAjouterLibelleCompte)
//    {
//        compteRepository.modifierLibelle(rapportAjouterLibelleCompte);
//    }
//
//    public LiveData<List<RapportLibelleCompteResponse>> getListeLibelleCompte(String compte)
//    {
//        return compteRepository.getListeLibelleCompte(compte);
//    }

    public LiveData<Integer> getDernierCompte(String groupeCompte)
    {
        return compteRepository.getDernieCompte(groupeCompte);
    }
}
