package com.soft.ssvapp.DataRetrofit.Ligne;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class LigneRetrofitViewModel extends AndroidViewModel {

    private LigneRepository ligneRepository;

    public LigneRetrofitViewModel(@NonNull Application application) {
        super(application);
    }

    public void init()
    {
        ligneRepository = new LigneRepository();
    }

//    public void createLigne(LigneRetrofit ligneRetrofit)
//    {
//        ligneRepository.CreateLigne(ligneRetrofit);
//    }

//    public LiveData<String> getResponse_bak()
//    {
//        return ligneRepository.getResponse_back();
//    }
}
