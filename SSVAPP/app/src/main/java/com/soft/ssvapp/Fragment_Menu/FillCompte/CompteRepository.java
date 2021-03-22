package com.soft.ssvapp.Fragment_Menu.FillCompte;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.soft.ssvapp.Data.Entity_Compte;
import com.soft.ssvapp.Data.Kp_BatimentData;
import com.soft.ssvapp.Data.tCompteDao;
import com.soft.ssvapp.DataRetrofit.Compte.CompteRemote;
import com.soft.ssvapp.DataRetrofit.Compte.CompteReponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompteRepository {

    CompteRemote compteRetrofitRepository;
    private tCompteDao tCompteDao;
    private LiveData<List<Entity_Compte>> getAllcompte;
    private List<Entity_Compte> getAllCompteList;
    private LiveData<List<Entity_Compte>> getRavitaementCompte;
    private LiveData<List<Entity_Compte>> getApprovisionement;
    private LiveData<List<Entity_Compte>> getProjetCompte;
    private LiveData<List<Entity_Compte>> getPayementsCompte;

    private LiveData<List<Entity_Compte>> getPosteAdminDebitCompte;
    private LiveData<List<Entity_Compte>> getPosteUserDebitCompte;
    private LiveData<List<Entity_Compte>> getPosteCreditCompte;
    private Application application;

    public CompteRepository(Application application)
    {
        this.application = application;
        Kp_BatimentData data = Kp_BatimentData.getInstance(application);
        tCompteDao = data.compteDao();
        getAllcompte = tCompteDao.getAllCompte();
        getAllCompteList = tCompteDao.getAllCompteList();
        getRavitaementCompte = tCompteDao.getRavitaementCompte();
        getApprovisionement = tCompteDao.getApprovisionementCompte();
        getProjetCompte = tCompteDao.getProjetCompte();

        getPosteAdminDebitCompte = tCompteDao.getPosteAdminDebitCompte();
        getPosteCreditCompte = tCompteDao.getPosteCreditCompte();
        compteRetrofitRepository = CompteRemote.getInstance();
    }

    public LiveData<List<Entity_Compte>> getGetPosteAdminDebitCompte(){
        return getPosteAdminDebitCompte;
    }

    public LiveData<List<Entity_Compte>> getGetPosteUserDebitCompte(int numCompte){
        return getPosteUserDebitCompte = tCompteDao.getPosteUserDebitCompte(numCompte);
    }

    public LiveData<List<Entity_Compte>> getGetPosteCreditCompte(){
        return getPosteCreditCompte;
    }

    public LiveData<List<Entity_Compte>> getGetAllcompte()
    {
        return getAllcompte;
    }

    public LiveData<List<Entity_Compte>> getGetPayementsCompte(int Numcompte)
    {
        return getPayementsCompte = tCompteDao.getPayements(Numcompte);
    }

    public LiveData<List<Entity_Compte>> getGetProjetCompte()
    {
        return getProjetCompte;
    }

    public void insertOnline()
    {
        compteRetrofitRepository.compteConnexion().getCompteOnline().enqueue(new Callback<List<CompteReponse>>() {
            @Override
            public void onResponse(Call<List<CompteReponse>> call, Response<List<CompteReponse>> response) {
                if (response.isSuccessful())
                {
                    if (getAllCompteList.isEmpty())
                    {
                        for (int y = 0; y < response.body().size(); y++)
                        {
                            Entity_Compte entity_compte =
                                    new Entity_Compte(
                                            response.body().get(y).getNumCompte(),
                                            response.body().get(y).getDesignationCompte(),
                                            response.body().get(y).getGroupeCompte(),
                                            response.body().get(y).getUnite(),
                                            response.body().get(y).getSolde()
                                    );
                            insert(entity_compte);
                        }
                    }
                    else
                    {
                        for (int y = 0; y < response.body().size(); y++)
                        {
                            Entity_Compte entity_compte =
                                    new Entity_Compte(
                                            response.body().get(y).getNumCompte(),
                                            response.body().get(y).getDesignationCompte(),
                                            response.body().get(y).getGroupeCompte(),
                                            response.body().get(y).getUnite(),
                                            response.body().get(y).getSolde()
                                    );
                            for (int a = 0; a < getAllCompteList.size(); a++)
                            {
                                if (!(entity_compte.getNumCompte() == getAllCompteList.get(a).getNumCompte()))
                                {
                                    insert(entity_compte);
                                }
                                else
                                {
                                    entity_compte.setId(getAllCompteList.get(a).getId());
                                    update(entity_compte);
                                }
                            }
                        }
                    }
                }
                else
                {
                    switch (response.code())
                    {
                        case 404:
                            Toast.makeText(application, "server not found.", Toast.LENGTH_LONG).show();
                            break;
                        case 500:
                            Toast.makeText(application, "server broken.", Toast.LENGTH_LONG).show();
                            break;
                        default:
                            Toast.makeText(application, "Unknown problem.", Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CompteReponse>> call, Throwable t) {
                Toast.makeText(application, "Connexion Problem.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public LiveData<List<Entity_Compte>> getGetRavitaementCompte()
    {
        return getRavitaementCompte;
    }

    public LiveData<List<Entity_Compte>> getGetApprovisionement()
    {
        return getApprovisionement;
    }

    public void insert(Entity_Compte entity_compte)
    {
        new insertAnsycTask(tCompteDao).execute(entity_compte);
    }

    public void update(Entity_Compte entity_compte)
    {
        new updateAnsycTask(tCompteDao).equals(entity_compte);
    }

    private class insertAnsycTask extends AsyncTask<Entity_Compte, Void, Void>
    {

        private tCompteDao compteDao;

        private insertAnsycTask(tCompteDao tCompteDao)
        {
            this.compteDao = tCompteDao;
        }
        @Override
        protected Void doInBackground(Entity_Compte... entity_comptes) {
            try {
                this.compteDao.insert(entity_comptes[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private class updateAnsycTask extends AsyncTask<Entity_Compte, Void, Void>
    {
        private tCompteDao compteDao;

        private updateAnsycTask(tCompteDao tCompteDao)
        {
            this.compteDao = tCompteDao;
        }

        @Override
        protected Void doInBackground(Entity_Compte... entity_comptes) {
            try {
                this.compteDao.update(entity_comptes[0]);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }
    }
}
