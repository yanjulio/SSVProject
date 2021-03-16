package com.soft.ssvapp.Fragment_Menu.Fill_Projects.ProjectFagment;

import android.app.Application;
import android.telephony.CellIdentityNr;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.soft.ssvapp.Data.Entity_Ligne;
import com.soft.ssvapp.Data.Entity_ProjectWithEntity_Ligne;

import java.util.List;

public class LigneViewModel extends AndroidViewModel {

    private LigneRepository ligneRepository;
    private LiveData<List<Entity_ProjectWithEntity_Ligne>> ligneLiveData;
    private LiveData<List<Entity_ProjectWithEntity_Ligne>> ligneCodeLigneLiveData;

    public LigneViewModel(@NonNull Application application) {
        super(application);
        ligneRepository = new LigneRepository(application);
//        ligneLiveData = ligneRepository.getAllLigne();
    }

    public void init(String codeProjet)
    {
        ligneRepository.getLigneLiveDataList(codeProjet);
        ligneCodeLigneLiveData = ligneRepository.getCodeLigne(codeProjet);
    }

    public int NombreLigne()
    {
        return ligneRepository.NombreLigne();
    }

    public int NombreLigneParProjet(String codeProjet)
    {
        return ligneRepository.NombreLigneParProjet(codeProjet);
    }

    public void insertSimpleLigneOnline(Entity_Ligne entity_ligne)
    {
        ligneRepository.insertSimpleLigneOnLine(entity_ligne);
    }

    public void updateSimpleLigneOnline(Entity_Ligne entity_ligne)
    {
        ligneRepository.UpdateSimpleLigneOnline(entity_ligne);
    }

    public void deleteSimpleLigneOnline(Entity_Ligne entity_ligne)
    {
        ligneRepository.DeleteSimpleLigneOnline(entity_ligne);
    }

    public MutableLiveData<Boolean> isLoading()
    {
        return ligneRepository.getIsLoding_value();
    }

    public void insert(Entity_Ligne entity_ligne)
    {
        ligneRepository.insert(entity_ligne);
    }

    public void update(Entity_Ligne entity_ligne)
    {
        ligneRepository.update(entity_ligne);
    }

    public void delete(Entity_Ligne entity_ligne)
    {
        ligneRepository.delete(entity_ligne);
    }

    public void deleteCustom(String codeProjet)
    {
        ligneRepository.deleteCustom(codeProjet);
    }

//    public LiveData<List<Entity_ProjectWithEntity_Ligne>> getAllLigne()
//    {
//        return ligneLiveData;
//    }

    public LiveData<List<Entity_ProjectWithEntity_Ligne>> getCodeLigne()
    {
        return ligneCodeLigneLiveData;
    }
}
