package com.soft.ssvapp.DataRetrofit.ProjetRetrofit;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class ProjetRetrofitViewModel extends AndroidViewModel {

    private ProjetRetrofitRespository projetRespository;
//    private LiveData<UtilisateurResponse> utilisateurResponseLiveData;
    private int nombre_projets;

    public ProjetRetrofitViewModel(@NonNull Application application) {
        super(application);
    }

    public void init()
    {
        projetRespository = new ProjetRetrofitRespository();
//        nombre_projets = projetRespository.getNombreProjet();
//        utilisateurResponseLiveData = utilisateurRepository.getResponseLiveData();
    }

//    public int getNombreProjets()
//    {
//        return projetRespository.NombreProjet();
//    }

//    public void createProject(ProjetRetrofit projetRetrofit)
//    {
//        projetRespository.CreateProjet(projetRetrofit);
//    }

//    public LiveData<ProjetRetrofit> getResponse_back()
//    {
//        return projetRespository.getRespone_back();
//    }

//    public LiveData<UtilisateurResponse> getConnexionLiveData()
//    {
//        return utilisateurResponseLiveData;
//    }
}
