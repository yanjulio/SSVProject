package com.soft.ssvapp.Fragment_Menu.FillPayements;

import android.app.Application;
import android.app.TaskInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.soft.ssvapp.Data.EntityOperationWithEntityMvtCompte;
import com.soft.ssvapp.Data.Entity_Besoin;
import com.soft.ssvapp.Data.Entity_MvtCompte;
import com.soft.ssvapp.Data.Entity_Operation;
import com.soft.ssvapp.Data.Kp_BatimentData;
import com.soft.ssvapp.Data.tMvtCompteDao;
import com.soft.ssvapp.Data.tOperationDao;
import com.soft.ssvapp.DataRetrofit.EtatDeBesoin.EtatDeBesoinRepositoryRetrofit;
import com.soft.ssvapp.DataRetrofit.EtatDeBesoin.EtatDeBesoinRetrofit;
import com.soft.ssvapp.DataRetrofit.MvtCompte.MvtCompteRepositoryRetrofit;
import com.soft.ssvapp.DataRetrofit.MvtCompte.MvtCompteResponse;
import com.soft.ssvapp.DataRetrofit.Operation.OperationRepository;
import com.soft.ssvapp.DataRetrofit.Operation.OperationRetrofit;
import com.soft.ssvapp.Fragment_Menu.Fill_EtatBesoin.Besoin_Repository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayementRepository {

    OperationRepository operationRepository;
    MvtCompteRepositoryRetrofit mvtCompteRepositoryRetrofit;
    EtatDeBesoinRepositoryRetrofit etatDeBesoinRepositoryRetrofit;
    // je veux update l'etat de besoin
    Besoin_Repository besoin_repository;
    private tOperationDao operationDao;
    private tMvtCompteDao mvtCompteDao;
//    MutableLiveData<List<Entity_MvtCompte>> entity_mvtEnregistrer = new MutableLiveData<>();
//    List<Entity_MvtCompte> list_enregistrer = new ArrayList<>();
    MutableLiveData<List<Entity_Operation>> recentOperation;

    MutableLiveData<Boolean> progress = new MutableLiveData<>();
    Application application;

    public PayementRepository(Application application)
    {
        this.application = application;
        Kp_BatimentData data = Kp_BatimentData.getInstance(application);
        operationDao = data.tOperationDao();
        mvtCompteDao = data.mvtCompteDao();
        operationRepository = OperationRepository.getInstance();
        mvtCompteRepositoryRetrofit = MvtCompteRepositoryRetrofit.getInstance();
        etatDeBesoinRepositoryRetrofit = EtatDeBesoinRepositoryRetrofit.getInstance();
        recentOperation = new MutableLiveData<>();
        besoin_repository = new Besoin_Repository(application);
    }

    public LiveData<List<EntityOperationWithEntityMvtCompte>> getRecentOperation(String timeStamp){
//        Toast.makeText(application,
//                "size Operation : " + timeStamp, Toast.LENGTH_LONG).show();
        return operationDao.getOperation(timeStamp);
    }

    public LiveData<List<Entity_MvtCompte>> getRecentMvt(){
        return mvtCompteDao.getRecentmvt();
    }

    public int getDernierOperation()
    {
        final int[] dernier_operation = {0};
        Call<Integer> call_dernier_operation = operationRepository.operationConnexion().dernierOperation();
        call_dernier_operation.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful())
                {
                    dernier_operation[0] = response.body();
                }
                else
                {
                    Erreur(response.code());
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(application, "Problème de Connexion", Toast.LENGTH_LONG).show();
            }
        });
        return dernier_operation[0];
    }

    private void update_etatBesoinOnline(EtatDeBesoinRetrofit retrofit, String codeBesoin)
    {
        etatDeBesoinRepositoryRetrofit.etatDeBesoinConnexion().modifierEtatDeBesoin(retrofit, codeBesoin).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
//                Toast.makeText(application, "les valeur a update sont : EtatBesoin : "
//                        + retrofit.getEtat() + "\n et DateRequise : " + retrofit.getDateRequise()
//                        + " \n where codeEtatBesoin : " + codeBesoin, Toast.LENGTH_LONG).show();
                if (response.isSuccessful())
                {
//                    Toast.makeText(application, "je viens d'update tEtatBesoin et CodeEtatBesoin est : "
//                            + codeBesoin, Toast.LENGTH_LONG).show();
                }
                else
                {
                    Erreur(response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(application, "Connexion Problem lors de mis de l'etat besoin.", Toast.LENGTH_LONG).show();
            }
        });
    }

//    public MutableLiveData<List<Entity_MvtCompte>> getMvtEnregistrer()
//    {
//        return entity_mvtEnregistrer;
//    }

    public void insertOpCompteOnline(OperationRetrofit operationRetrofit, MvtCompteResponse mvtCompteResponse_debit,
                                     MvtCompteResponse mvtCompteResponse_credit, int idEtatBesoin)
    {
        Call<Void> call_op = operationRepository.operationConnexion().createOperation(operationRetrofit);
        progress.postValue(true);
        call_op.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful())
                {
                    progress.postValue(false);
                    Toast.makeText(application, "Operation bien enregistrée", Toast.LENGTH_LONG).show();
                    insertMvtOnline(mvtCompteResponse_debit);
                    insertMvtOnline(mvtCompteResponse_credit);

                    // je fais l'insertion dans la table de tOperation et tMvtCompte
                    insertOp(new Entity_Operation(operationRetrofit.getNumOperation(), operationRetrofit.getLibelle(),
                            operationRetrofit.getNomUt(), operationRetrofit.getDateOperation(), operationRetrofit.getDateSysteme(),
                            operationRetrofit.getCodeEtatdeBesoin()));

                    Entity_MvtCompte entity_compte_credit = new Entity_MvtCompte(mvtCompteResponse_credit.getNumCompte(),
                            mvtCompteResponse_credit.getNumOperation(),
                            mvtCompteResponse_credit.getDetails(), mvtCompteResponse_credit.getQte(), mvtCompteResponse_credit.getEntree(),
                            mvtCompteResponse_credit.getSortie(), mvtCompteResponse_credit.getCodeProject());
                    insertMvt(entity_compte_credit); // pour le credit
                    Entity_MvtCompte entity_mvtCompte_debit =
                            new Entity_MvtCompte(mvtCompteResponse_debit.getNumCompte(), mvtCompteResponse_debit.getNumOperation(),
                            mvtCompteResponse_debit.getDetails(), mvtCompteResponse_debit.getQte(), mvtCompteResponse_debit.getEntree(),
                            mvtCompteResponse_debit.getSortie(), mvtCompteResponse_debit.getCodeProject());
                    insertMvt(entity_mvtCompte_debit); // pour le debit

                    if (!operationRetrofit.getCodeEtatdeBesoin().equals("0"))
                    {
                        // je fais l'update de la table tEtaBesoin en locale
                        Entity_Besoin entity_besoin  = new Entity_Besoin("", "",
                                "", "", "", "", operationRetrofit.getDateOperation(),
                                "", 2, "");
                        entity_besoin.setIdEtatDuBesoin(idEtatBesoin);
                        besoin_repository.update_etatDeBesoin(entity_besoin);
                        update_etatBesoinOnline(new EtatDeBesoinRetrofit(operationRetrofit.getCodeEtatdeBesoin(), "",
                                        "", 2 ,"", "", "2020-09-02T14:10:51.300Z",
                                        operationRetrofit.getDateOperation(), "2020-09-02T14:10:51.300Z"),
                                operationRetrofit.getCodeEtatdeBesoin());
                    }
                }
                else
                {
                    Erreur(response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                progress.postValue(false);
                Toast.makeText(application, "Problème de Connexion", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void insertOpOnline(OperationRetrofit operationRetrofit, MvtCompteResponse mvtCompteResponse, int idEtatBesoin)
    {
        Call<Void> call_op = operationRepository.operationConnexion().createOperation(operationRetrofit);
        call_op.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful())
                {
                    Toast.makeText(application, "Operation bien enregistrée.", Toast.LENGTH_LONG).show();
                    insertMvtOnline(mvtCompteResponse);
                    // insertion en locale dans la table tMvt et tOpration
                    insertOp(new Entity_Operation(operationRetrofit.getNumOperation(), operationRetrofit.getLibelle(),
                            operationRetrofit.getNomUt(), operationRetrofit.getDateOperation(), operationRetrofit.getDateSysteme(),
                            operationRetrofit.getCodeEtatdeBesoin()));
                    insertMvt(new Entity_MvtCompte(mvtCompteResponse.getNumCompte(), mvtCompteResponse.getNumOperation(),
                            mvtCompteResponse.getDetails(), mvtCompteResponse.getQte(), mvtCompteResponse.getEntree(),
                            mvtCompteResponse.getSortie(), mvtCompteResponse.getCodeProject()));

                    if (!operationRetrofit.getCodeEtatdeBesoin().equals("0"))
                    {
                        // update en locale de la table tEtatBesoin
                        Entity_Besoin entity_besoin  = new Entity_Besoin(operationRetrofit.getCodeEtatdeBesoin(), "",
                                "", "", "", "", operationRetrofit.getDateOperation(), "",
                                2, "");
                        entity_besoin.setIdEtatDuBesoin(idEtatBesoin);
                        besoin_repository.update_etatDeBesoin(entity_besoin);

                        // je fais l'update online de la table tEtatBesoin
                        EtatDeBesoinRetrofit etatretrofit = new EtatDeBesoinRetrofit("", "",
                                "", 2 ,"", "", "2020-09-02T14:10:51.300Z",
                                operationRetrofit.getDateOperation(), "2020-09-02T14:10:51.300Z");
                        update_etatBesoinOnline(etatretrofit, operationRetrofit.getCodeEtatdeBesoin());
                    }
                }
                else
                {
                    Erreur(response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(application, "Problème de Connexion", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void insertMvtOnline(MvtCompteResponse mvtCompteResponse)
    {
        Call<Void> call_mvt = mvtCompteRepositoryRetrofit.mvtCompteConnexion().createMvtCompte(mvtCompteResponse);
        call_mvt.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful())
                {
                    Toast.makeText(application, "MvtCompte Bien enregistré.", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Erreur(response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(application, "Problème de Connexion", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void insertMvt(Entity_MvtCompte entity_mvtCompte)
    {
//        list_enregistrer.clear();
//        list_enregistrer.add(entity_mvtCompte);
//        entity_mvtEnregistrer.postValue(list_enregistrer);
        new inserMvttAsyncTask(mvtCompteDao).execute(entity_mvtCompte);
    }

    public void insertOp(Entity_Operation entity_operation)
    {
        new insertOpAsncTask(operationDao).execute(entity_operation);
//        Entity_MvtCompte entity_compte_credit = new Entity_MvtCompte(mvtCompte_credit.getNumCompte(),
//                mvtCompte_credit.getNumOperation(),
//                mvtCompte_credit.getDetails(), mvtCompte_credit.getQte(), mvtCompte_credit.getEntree(),
//                mvtCompte_credit.getSortie(), mvtCompte_credit.getCodeProject());
//        insertMvt(entity_compte_credit); // pour le credit
//        Entity_MvtCompte entity_mvtCompte_debit =
//                new Entity_MvtCompte(mvtCompte_debit.getNumCompte(), mvtCompte_debit.getNumOperation(),
//                        mvtCompte_debit.getDetails(), mvtCompte_debit.getQte(), mvtCompte_debit.getEntree(),
//                        mvtCompte_debit.getSortie(), mvtCompte_debit.getCodeProject());
//        insertMvt(entity_mvtCompte_debit); // pour le debit
    }

    private class inserMvttAsyncTask extends AsyncTask<Entity_MvtCompte, Void, Void>
    {
        private tMvtCompteDao mvtCompteDao;

        private inserMvttAsyncTask(tMvtCompteDao mvtCompteDao)
        {
            this.mvtCompteDao = mvtCompteDao;
        }

        @Override
        protected Void doInBackground(Entity_MvtCompte... entity_mvtComptes) {
            try {
                this.mvtCompteDao.insert(entity_mvtComptes[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private class insertOpAsncTask extends AsyncTask<Entity_Operation, Void, Void>
    {

        private tOperationDao operationDao;

        private insertOpAsncTask(tOperationDao operationDao)
        {
            this.operationDao = operationDao;
        }

        @Override
        protected Void doInBackground(Entity_Operation... entity_operations) {
            try {
                this.operationDao.insert(entity_operations[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private void Erreur(int code)
    {
        switch (code)
        {
            case 404:
                Toast.makeText(application, "Serveur introuvable", Toast.LENGTH_LONG).show();
                break;
            case 500:
                Toast.makeText(application, "Erreur serveur", Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(application, "Problème inconnu", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
