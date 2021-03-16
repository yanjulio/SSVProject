package com.soft.ssvapp.Fragment_Menu.Fill_EtatBesoin;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.soft.ssvapp.Data.Entity_Besoin;
import com.soft.ssvapp.Data.Entity_BesoinWithEntity_DetailBesoin;
import com.soft.ssvapp.Data.Entity_ProjectWithEntity_Besoin;
import com.soft.ssvapp.Login;

import java.util.List;

public class Besoin_View_model extends AndroidViewModel {

    private Besoin_Repository repository;
    private LiveData<List<Entity_ProjectWithEntity_Besoin>> nouveauBesoin;
    private LiveData<List<Entity_ProjectWithEntity_Besoin>> validerBesoin;
    private LiveData<List<Entity_ProjectWithEntity_Besoin>> avaliderBesoinUtilisateur;
    private LiveData<List<Entity_BesoinWithEntity_DetailBesoin>> validerBesoinAvecDetails;
    private LiveData<List<Entity_ProjectWithEntity_Besoin>> besoinAvalider;
    private LiveData<List<String>> codeProject;
    private int dernierBesoin;
    SharedPreferences prefs;

    public Besoin_View_model(Application application) {
        super(application);
        repository = new Besoin_Repository(application);
        codeProject = repository.getCodeProject();
        prefs = application.getSharedPreferences("lOGIN_USER", application.MODE_PRIVATE);
    }

    public void init(String codeProjet, String kind_list)
    {
        String niveauUtilisateur = prefs.getString(Login.NIVEAUUTILISATEUR, "");
        nouveauBesoin = repository.getAllBesoin(codeProjet);
        if (kind_list.equals("Valides."))
        {
            validerBesoin = repository.getValiderBesoin(codeProjet);
            validerBesoinAvecDetails = repository.getAllBesoinAvecDetails(codeProjet);
            repository.insertOrUpdateValider(codeProjet);
        }
        else
        {
            if (niveauUtilisateur.equals("ADMIN"))
            {
                besoinAvalider = repository.getBesoinAvalider(codeProjet);
                repository.insertOrUpdateOperationAvalider(codeProjet);
            }
            else
            {
                avaliderBesoinUtilisateur = repository.getBesoinAvaliderUtilisateur(codeProjet);
            }
        }
    }

    public void insert(Entity_Besoin entity_besoin)
    {
        repository.insert(entity_besoin);
    }

    public int update(Entity_Besoin entity_besoin)
    {
        repository.update_custom(entity_besoin);
        return 1;
    }

    public int update_etat(Entity_Besoin entity_besoin)
    {
        repository.update(entity_besoin);
        return 1;
    }

    public void update_codeEtatBesoin(Entity_Besoin entity_besoin)
    {
        repository.update_codeEtatBesoin(entity_besoin);
    }

    public void update_etatDeBesoin(Entity_Besoin entity_besoin)
    {
        repository.update_etatDeBesoin(entity_besoin);
    }

    public int delete(Entity_Besoin entity_besoin)
    {
        repository.delete(entity_besoin);
        return 1;
    }

    public void deleteCustom()
    {
        repository.deleteCustom();
    }

    public void deleteAllBesoin()
    {
        repository.deleteAllBesoin();
    }

    public LiveData<List<String>> getCodeProject()
    {
        return codeProject;
    }

    public LiveData<List<Entity_ProjectWithEntity_Besoin>> getNouveauBesoin()
    {
        return nouveauBesoin;
    }

    public LiveData<List<Entity_ProjectWithEntity_Besoin>> getValiderBesoin()
    {
        return validerBesoin;
    }

    public LiveData<List<Entity_BesoinWithEntity_DetailBesoin>> getValiderBesoinAvecDetails()
    {
        return validerBesoinAvecDetails;
    }

    public LiveData<List<Entity_ProjectWithEntity_Besoin>> getBesoinAvalider()
    {
        return besoinAvalider;
    }

    public LiveData<List<Entity_ProjectWithEntity_Besoin>> getAvaliderBesoinUtilisateur()
    {
        return avaliderBesoinUtilisateur;
    }

    public int getDernierBesoin()
    {
        dernierBesoin = repository.getDernierBesoin();
        return dernierBesoin;
    }

    public MutableLiveData<Boolean> getIsLoading()
    {
        return repository.getIsLoading();
    }
}
