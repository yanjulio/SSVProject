package com.soft.ssvapp.Fragment_Menu.Other_FillData;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.soft.ssvapp.Data.Entity_Compte;

public class CompteViewModel extends AndroidViewModel {

    private CompteRepository compteRepository;

    public CompteViewModel(@NonNull Application application) {
        super(application);
        compteRepository = new CompteRepository(application);
    }

    public void insert(Entity_Compte entity_compte)
    {
        compteRepository.insert(entity_compte);
    }
}
