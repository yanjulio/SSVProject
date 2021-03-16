package com.soft.ssvapp.Fragment_Menu.Other_FillData;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.soft.ssvapp.Data.Entity_MvtCompte;

public class MvtViewModel extends AndroidViewModel {

    private MvtRepository mvtRepository;

    public MvtViewModel(@NonNull Application application) {
        super(application);
        mvtRepository = new MvtRepository(application);
    }

    public void insert(Entity_MvtCompte entity_mvtCompte)
    {
        mvtRepository.insert(entity_mvtCompte);
    }
}
