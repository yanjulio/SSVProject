package com.soft.ssvapp.Fragment_Menu.FillPayements;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.soft.ssvapp.Data.EntityOperationWithEntityMvtCompte;
import com.soft.ssvapp.Data.Entity_MvtCompte;
import com.soft.ssvapp.Data.Entity_Operation;
import com.soft.ssvapp.DataRetrofit.MvtCompte.MvtCompteResponse;
import com.soft.ssvapp.DataRetrofit.Operation.OperationRetrofit;

import java.util.List;

public class PayementViewModel extends AndroidViewModel {

    PayementRepository payementRepository;

    public PayementViewModel(@NonNull Application application) {
        super(application);
        payementRepository = new PayementRepository(application);
    }

    // online
//    public void insertMvtOnline(MvtCompteResponse mvtCompteResponse)
//    {
//        payementRepository.insertMvtOnline(mvtCompteResponse);
//    }

//    public void insetOpOnline(OperationRetrofit operationRetrofit, MvtCompteResponse mvtCompteResponse, int idEtatBesoin)
//    {
//        payementRepository.insertOpOnline(operationRetrofit, mvtCompteResponse, idEtatBesoin);
//    }

    public void insertOpCompteOnline(OperationRetrofit operationRetrofit, MvtCompteResponse mvtCompteResponse_debit,
                                     MvtCompteResponse mvtCompteResponse_credit, int idEtatBesoin)
    {
        payementRepository.insertOpCompteOnline(operationRetrofit, mvtCompteResponse_debit, mvtCompteResponse_credit, idEtatBesoin);
    }

//    public MutableLiveData<List<Entity_MvtCompte>> getMvtCompteEnregistrer()
//    {
//        return payementRepository.getMvtEnregistrer();
//    }


    // en locale
    public void insertOp(Entity_Operation entity_operation)
    {
        payementRepository.insertOp(entity_operation);
    }

    public void insertMvt(Entity_MvtCompte entity_mvtCompte)
    {
        payementRepository.insertMvt(entity_mvtCompte);
    }

    public int getDernierOperation() // je l'aurai utiliser dans payementAdapter.
    {
        return payementRepository.getDernierOperation();
    }

    public LiveData<List<EntityOperationWithEntityMvtCompte>> getRecenteOperation(String timeStamp){
        return payementRepository.getRecentOperation(timeStamp);
    }

    public LiveData<List<Entity_MvtCompte>> getRecentMvt(){
        return payementRepository.getRecentMvt();
    }
}
