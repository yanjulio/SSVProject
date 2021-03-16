package com.soft.ssvapp.Fragment_Menu.FillGroupeCompte;

import android.app.Application;
import android.app.ListActivity;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.soft.ssvapp.Data.Entity_GroupeCompte;

import java.util.List;

public class GroupeCompteViewModel extends AndroidViewModel {

    GroupeCompteRepository groupeCompteRepository;
    private LiveData<List<Entity_GroupeCompte>> getAllGroupeCompte;

    public GroupeCompteViewModel(@NonNull Application application) {
        super(application);
        groupeCompteRepository = new GroupeCompteRepository(application);
        groupeCompteRepository.insertOnlineGroupeCompte();
        getAllGroupeCompte = groupeCompteRepository.getGetAllGroupeCompte();
    }

    public LiveData<List<Entity_GroupeCompte>> getGetAllGroupeCompte()
    {
        return getAllGroupeCompte;
    }
}
