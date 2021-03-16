package com.soft.ssvapp.Fragment_Menu.Fill_EtatBesoin;

import android.app.Application;
import android.os.AsyncTask;
import android.text.style.AlignmentSpan;
import android.util.Log;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.soft.ssvapp.Data.Entity_Besoin;
import com.soft.ssvapp.Data.Entity_BesoinWithEntity_DetailBesoin;
import com.soft.ssvapp.Data.Entity_ProjectWithEntity_Besoin;
import com.soft.ssvapp.Data.Kp_BatimentData;
import com.soft.ssvapp.Data.tEtatDeBesoinDao;
import com.soft.ssvapp.DataRetrofit.EtatDeBesoin.EtatDeBesoinRepositoryRetrofit;
import com.soft.ssvapp.DataRetrofit.EtatDeBesoin.EtatDeBesoinRetrofit;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Besoin_Repository {

    EtatDeBesoinRepositoryRetrofit etatDeBesoinRepositoryRetrofit;
    private tEtatDeBesoinDao besoinDao;
    private LiveData<List<Entity_ProjectWithEntity_Besoin>> nouveauBesoin;
    private LiveData<List<Entity_ProjectWithEntity_Besoin>> validerBesoin;
    private LiveData<List<Entity_BesoinWithEntity_DetailBesoin>> validerBesoinAvecDetails;
    private List<Entity_ProjectWithEntity_Besoin> validerBesoinList;
    private LiveData<List<Entity_ProjectWithEntity_Besoin>> besoinAvalider;
    private LiveData<List<Entity_ProjectWithEntity_Besoin>> besoinAvaliderUtilisateur;
    private List<Entity_ProjectWithEntity_Besoin> besoinAvaliderList = new ArrayList<>();
    private LiveData<List<String>> codeProject;
    private int dernierBesoin;
    private int dernierServer = 0;
    private MutableLiveData<Boolean> isLoading;
    private Application application;

    public Besoin_Repository (Application application)
    {
        this.application = application;
        Kp_BatimentData db = Kp_BatimentData.getInstance(application);
        besoinDao = db.besoinDao();
        codeProject = besoinDao.getCodeProject();
        etatDeBesoinRepositoryRetrofit = EtatDeBesoinRepositoryRetrofit.getInstance();
        isLoading = new MutableLiveData<>();
    }

    public MutableLiveData<Boolean> getIsLoading()
    {
        return isLoading;
    }

    public void insert(Entity_Besoin entity_besoin)
    {
        new insertAsyncTask(besoinDao, isLoading).execute(entity_besoin);
    }

    public void update_custom(Entity_Besoin entity_besoin)
    {
        new update_customAsyncTask(besoinDao, isLoading).execute(entity_besoin);
    }

    public int update(Entity_Besoin entity_besoin)
    {
        new updateAsyncTask(besoinDao, isLoading).execute(entity_besoin);
        return 1;
    }

    public void update_codeEtatBesoin(Entity_Besoin entity_besoin)
    {
        new update_codeEtatBesoinAsyncTask(besoinDao, isLoading).execute(entity_besoin);
    }

    public void update_etatDeBesoin(Entity_Besoin entity_besoin)
    {
        new update_etatBesoinAsyncTask(besoinDao, isLoading).execute(entity_besoin);
    }

    public void delete(Entity_Besoin entity_besoin)
    {
        new deleteAsyncTask(besoinDao, isLoading).execute(entity_besoin);
    }

    public void deleteCustom()
    {
        new deleteCustomAsyncTask(besoinDao, isLoading).execute();
    }

    public void deleteAllBesoin()
    {
        new deleteAllBesoinAsyncTask(besoinDao, isLoading).execute();
    }

    public LiveData<List<Entity_ProjectWithEntity_Besoin>> getAllBesoin(String codeProjet)
    {
        nouveauBesoin = besoinDao.getNewBesoin(codeProjet);
        return nouveauBesoin;
    }

    public LiveData<List<String>> getCodeProject()
    {
        return codeProject;
    }

    public int getDernierBesoin()
    {
        dernierBesoin = besoinDao.dernier_etatBesoin();
        return dernierBesoin;
    }

    public LiveData<List<Entity_ProjectWithEntity_Besoin>> getValiderBesoin(String codeProjet)
    {
        InsertOrUpdateEtatBesoinValiderParProjet(codeProjet);
        return besoinDao.getValiderBesoin(codeProjet);
    }

    public void InsertOrUpdateEtatBesoinValiderParProjet(String codeProjet)
    {
        List<Entity_ProjectWithEntity_Besoin> list_local = besoinDao.getListValiderBesoin(codeProjet);
        Call<List<EtatDeBesoinRetrofit>> call_etat_besoin_par_projet =
                etatDeBesoinRepositoryRetrofit.etatDeBesoinConnexion().getEtatDeBesoinValiderParProjet(codeProjet);
        isLoading.postValue(true);
        call_etat_besoin_par_projet.enqueue(new Callback<List<EtatDeBesoinRetrofit>>() {
            @Override
            public void onResponse(Call<List<EtatDeBesoinRetrofit>> call, Response<List<EtatDeBesoinRetrofit>> response) {
                if (response.isSuccessful())
                {
                    isLoading.postValue(false);
                    if (list_local.isEmpty())
                    {
                        for (int a = 0; a < response.body().size(); a++)
                        {
                            Entity_Besoin entity_besoin_from_response = new Entity_Besoin(
                                    response.body().get(a).getCodeEtatdeBesoin(),
                                    response.body().get(a).getDesignationEtatDeBesion(),
                                    response.body().get(a).getCodeProject(),
                                    response.body().get(a).getDemandeur(),
                                    response.body().get(a).getDateEmision(),
                                    response.body().get(a).getDateValidation(),
                                    response.body().get(a).getDateRequise(),
                                    response.body().get(a).getValiderPar(),
                                    response.body().get(a).getEtat(),
                                    response.body().get(a).getCodeEtatdeBesoin()
                            );
                            entity_besoin_from_response.setEtat_besoin_envoyer(1);
                            insert(entity_besoin_from_response);
                        }
                    }
                    else
                    {
                        for (int a=0; a < response.body().size(); a++)
                        {
                            Entity_Besoin entity_besoin_from_response = new Entity_Besoin(
                                    response.body().get(a).getCodeEtatdeBesoin(),
                                    response.body().get(a).getDesignationEtatDeBesion(),
                                    response.body().get(a).getCodeProject(),
                                    response.body().get(a).getDemandeur(),
                                    response.body().get(a).getDateEmision(),
                                    response.body().get(a).getDateValidation(),
                                    response.body().get(a).getDateRequise(),
                                    response.body().get(a).getValiderPar(),
                                    response.body().get(a).getEtat(),
                                    response.body().get(a).getCodeEtatdeBesoin());
                            entity_besoin_from_response.setEtat_besoin_envoyer(1);

                            for (int e =0; e < list_local.size(); e++)
                            {
                                if (entity_besoin_from_response.getCodeEtatdeBesoin()
                                        .equals(list_local.get(e).getEntity_besoin().getCodeEtatDeBesoinOnline()))
                                {
                                    entity_besoin_from_response.setIdEtatDuBesoin(list_local.get(e).getEntity_besoin().getIdEtatDuBesoin());
                                    update(entity_besoin_from_response);
                                }
                                else
                                {
                                    insert(entity_besoin_from_response);
                                }
                            }
                        }
                    }
                }
                else
                {
                    Erreur(response.code());
                }
            }

            @Override
            public void onFailure(Call<List<EtatDeBesoinRetrofit>> call, Throwable t) {
                isLoading.postValue(false);
                Toast.makeText(application, "Problème de connexion", Toast.LENGTH_LONG).show();
            }
        });
    }

    public LiveData<List<Entity_BesoinWithEntity_DetailBesoin>> getAllBesoinAvecDetails(String codeProjet)
    {
        validerBesoinAvecDetails = besoinDao.getEtatBesoinAvecDetailValider(codeProjet);
        return validerBesoinAvecDetails;
    }

    public LiveData<List<Entity_ProjectWithEntity_Besoin>> getBesoinAvalider(String codeProjet)
    {
        try {
            besoinAvalider = besoinDao.getBesoinAvalider(codeProjet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return besoinAvalider;
    }

    public LiveData<List<Entity_ProjectWithEntity_Besoin>> getBesoinAvaliderUtilisateur(String codeProjet)
    {
        try {
            besoinAvaliderUtilisateur = besoinDao.getBesoinAvaliderUtilisateur(codeProjet);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return besoinAvaliderUtilisateur;
    }

    public void insertOrUpdateOperationAvalider(String codeProjet)
    {
        besoinAvaliderList.clear();
        besoinAvaliderList = besoinDao.getBesoinAvaliderList(codeProjet);
        if (besoinAvaliderList != null)
        {
            isLoading.postValue(true);
            etatDeBesoinRepositoryRetrofit.etatDeBesoinConnexion().getEtatDeBesoin().
                    enqueue(new Callback<List<EtatDeBesoinRetrofit>>() {
                @Override
                public void onResponse(Call<List<EtatDeBesoinRetrofit>> call, Response<List<EtatDeBesoinRetrofit>> response) {
                    if (response.isSuccessful())
                    {
                        isLoading.postValue(false);
                        Entity_Besoin entity_besoin_from_response;
                        if(besoinAvaliderList.isEmpty())
                        {
                            for (int a = 0; a < response.body().size(); a++)
                            {
                                entity_besoin_from_response = new Entity_Besoin(
                                        response.body().get(a).getCodeEtatdeBesoin(),
                                        response.body().get(a).getDesignationEtatDeBesion(),
                                        response.body().get(a).getCodeProject(),
                                        response.body().get(a).getDemandeur(),
                                        response.body().get(a).getDateEmision(),
                                        response.body().get(a).getDateValidation(),
                                        response.body().get(a).getDateRequise(),
                                        response.body().get(a).getValiderPar(),
                                        response.body().get(a).getEtat(),
                                        response.body().get(a).getCodeEtatdeBesoin()
                                );
                                entity_besoin_from_response.setEtat_besoin_envoyer(1);
                                insert(entity_besoin_from_response);
                            }
                        }
                        else
                        {
                            for (int a = 0; a < response.body().size(); a++)
                            {
                                entity_besoin_from_response = new Entity_Besoin(
                                        response.body().get(a).getCodeEtatdeBesoin(),
                                        response.body().get(a).getDesignationEtatDeBesion(),
                                        response.body().get(a).getCodeProject(),
                                        response.body().get(a).getDemandeur(),
                                        response.body().get(a).getDateEmision(),
                                        response.body().get(a).getDateValidation(),
                                        response.body().get(a).getDateRequise(),
                                        response.body().get(a).getValiderPar(),
                                        response.body().get(a).getEtat(),
                                        response.body().get(a).getCodeEtatdeBesoin()
                                );
                                entity_besoin_from_response.setEtat_besoin_envoyer(1);
                                for (int i = 0; i < besoinAvaliderList.size(); i++)
                                {
                                    if (response.body().get(a).getCodeEtatdeBesoin()
                                            .equals(besoinAvaliderList.get(i).getEntity_besoin().getCodeEtatDeBesoinOnline()))
                                    {
                                        entity_besoin_from_response.setIdEtatDuBesoin(
                                                besoinAvaliderList.get(i).getEntity_besoin().getIdEtatDuBesoin());
                                        entity_besoin_from_response.setCodeEtatdeBesoin(
                                                besoinAvaliderList.get(i).getEntity_besoin().getCodeEtatdeBesoin());
                                        update(entity_besoin_from_response);
                                    }
                                    else
                                    {
                                        insert(entity_besoin_from_response);
                                    }
                                }
                            }
                        }

                    }
                    else
                    {
                        Erreur(response.code());
                    }
                }

                @Override
                public void onFailure(Call<List<EtatDeBesoinRetrofit>> call, Throwable t) {
                    isLoading.postValue(false);
                    Toast.makeText(application, "Problème de connexion", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void insertOrUpdateValider(String codeProjet)
    {
        validerBesoinList = besoinDao.getvaliderBesoinList(codeProjet);
        if (validerBesoinList != null)
        {
            isLoading.postValue(true);
            etatDeBesoinRepositoryRetrofit.etatDeBesoinConnexion()
                    .getEtatDeBesoinValider().enqueue(new Callback<List<EtatDeBesoinRetrofit>>() {
                @Override
                public void onResponse(Call<List<EtatDeBesoinRetrofit>> call, Response<List<EtatDeBesoinRetrofit>> response) {
                    if (response.isSuccessful())
                    {
                        isLoading.postValue(false);
                        Entity_Besoin entity_besoin_from_response;
                        if(validerBesoinList.isEmpty())
                        {
                            for (int a = 0; a < response.body().size(); a++)
                            {
                                entity_besoin_from_response = new Entity_Besoin(
                                        response.body().get(a).getCodeEtatdeBesoin(),
                                        response.body().get(a).getDesignationEtatDeBesion(),
                                        response.body().get(a).getCodeProject(),
                                        response.body().get(a).getDemandeur(),
                                        response.body().get(a).getDateEmision(),
                                        response.body().get(a).getDateValidation(),
                                        response.body().get(a).getDateRequise(),
                                        response.body().get(a).getValiderPar(),
                                        response.body().get(a).getEtat(),
                                        response.body().get(a).getCodeEtatdeBesoin()
                                );
                                entity_besoin_from_response.setEtat_besoin_envoyer(1);
                                insert(entity_besoin_from_response);
                            }
                        }
                        else
                        {
                            for (int a = 0; a < response.body().size(); a++)
                            {
                                entity_besoin_from_response = new Entity_Besoin(
                                        response.body().get(a).getCodeEtatdeBesoin(),
                                        response.body().get(a).getDesignationEtatDeBesion(),
                                        response.body().get(a).getCodeProject(),
                                        response.body().get(a).getDemandeur(),
                                        response.body().get(a).getDateEmision(),
                                        response.body().get(a).getDateValidation(),
                                        response.body().get(a).getDateRequise(),
                                        response.body().get(a).getValiderPar(),
                                        response.body().get(a).getEtat(),
                                        response.body().get(a).getCodeEtatdeBesoin()
                                );
                                entity_besoin_from_response.setEtat_besoin_envoyer(1);
                                for (int i = 0; i < validerBesoinList.size(); i++)
                                {
                                    if (response.body().get(a).getCodeEtatdeBesoin()
                                            .equals(validerBesoinList.get(i).getEntity_besoin().getCodeEtatDeBesoinOnline()))
                                    {
                                        entity_besoin_from_response.setIdEtatDuBesoin(
                                                validerBesoinList.get(i).getEntity_besoin().getIdEtatDuBesoin());
                                        entity_besoin_from_response.setCodeEtatdeBesoin(
                                                validerBesoinList.get(i).getEntity_besoin().getCodeEtatdeBesoin());
                                        update(entity_besoin_from_response);
                                    }
                                    else
                                    {
                                        insert(entity_besoin_from_response);
                                    }
                                }
                            }
                        }

                    }
                    else
                    {
                        Erreur(response.code());
                    }
                }

                @Override
                public void onFailure(Call<List<EtatDeBesoinRetrofit>> call, Throwable t) {
                    isLoading.postValue(false);
                    Toast.makeText(application, "Problème de connexion", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private static class insertAsyncTask extends AsyncTask<Entity_Besoin, Void, Void>
    {
        private tEtatDeBesoinDao besoinDao;
        private MutableLiveData<Boolean> isLoading;

        private insertAsyncTask(tEtatDeBesoinDao besoinDao, MutableLiveData<Boolean> isLoading)
        {
            this.besoinDao = besoinDao;
            this.isLoading = isLoading;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.isLoading.postValue(true);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            this.isLoading.postValue(false);
        }

        @Override
        protected Void doInBackground(Entity_Besoin... entity_besoins) {
            try {
                this.besoinDao.insert(entity_besoins[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private static class update_customAsyncTask extends AsyncTask<Entity_Besoin, Void, Void>
    {
        private tEtatDeBesoinDao besoinDao;
        private MutableLiveData<Boolean> isLoading;

        private update_customAsyncTask(tEtatDeBesoinDao besoinDao, MutableLiveData<Boolean> isLoading)
        {
            this.besoinDao = besoinDao;
            this.isLoading = isLoading;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.isLoading.postValue(true);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            this.isLoading.postValue(false);
        }

        @Override
        protected Void doInBackground(Entity_Besoin... entity_besoins) {
            this.besoinDao.custom_update(entity_besoins[0].getDesignationEtatDeBesion(), entity_besoins[0].getDateRequise(),
                    entity_besoins[0].getDateEmision(), entity_besoins[0].getDateValidation(),
                    entity_besoins[0].getCodeProject(), entity_besoins[0].getDemandeur(),
                    entity_besoins[0].getValiderPar(), entity_besoins[0].getCodeEtatdeBesoin(),
                    entity_besoins[0].getEtat(),
                    entity_besoins[0].getEtat_besoin_envoyer());
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Entity_Besoin, Void, Void>
    {
        private tEtatDeBesoinDao besoinDao;
        private MutableLiveData<Boolean> isLoading;

        private updateAsyncTask(tEtatDeBesoinDao besoinDao, MutableLiveData<Boolean> isLoading)
        {
            this.besoinDao = besoinDao;
            this.isLoading = isLoading;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.isLoading.postValue(true);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            this.isLoading.postValue(false);
        }

        @Override
        protected Void doInBackground(Entity_Besoin... entity_besoins) {
            try {
                this.besoinDao.update(entity_besoins[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private static class update_etatBesoinAsyncTask extends AsyncTask<Entity_Besoin, Void, Void>
    {
        private tEtatDeBesoinDao etatDeBesoinDao;
        private MutableLiveData<Boolean> isLaoding;

        private update_etatBesoinAsyncTask(tEtatDeBesoinDao etatDeBesoinDao, MutableLiveData<Boolean> isLaoding)
        {
            this.etatDeBesoinDao = etatDeBesoinDao;
            this.isLaoding = isLaoding;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.isLaoding.postValue(true);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            this.isLaoding.postValue(false);
        }

        @Override
        protected Void doInBackground(Entity_Besoin... entity_besoins) {
            this.etatDeBesoinDao.update_etatBesoin(entity_besoins[0].getEtat(),
                    entity_besoins[0].getDateRequise(),
                    entity_besoins[0].getIdEtatDuBesoin());
            return null;
        }
    }

    private static class update_codeEtatBesoinAsyncTask extends AsyncTask<Entity_Besoin, Void, Void>
    {
        private tEtatDeBesoinDao tEtatDeBesoinDao;
        private MutableLiveData<Boolean> isLoading;
        private update_codeEtatBesoinAsyncTask(tEtatDeBesoinDao etatDeBesoinDao, MutableLiveData<Boolean> isLoading)
        {
            this.tEtatDeBesoinDao = etatDeBesoinDao;
            this.isLoading = isLoading;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.isLoading.postValue(true);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            this.isLoading.postValue(false);
        }

        @Override
        protected Void doInBackground(Entity_Besoin... entity_besoins) {
            this.tEtatDeBesoinDao.update_CodeEtatBesoin(
                    entity_besoins[0].getCodeEtatDeBesoinOnline(),
                    entity_besoins[0].getEtat(),
                    entity_besoins[0].getEtat_besoin_envoyer(),
                    entity_besoins[0].getIdEtatDuBesoin()
            );
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Entity_Besoin, Void, Void>
    {
        private tEtatDeBesoinDao besoinDao;
        private MutableLiveData<Boolean> isLoading;

        private deleteAsyncTask(tEtatDeBesoinDao besoinDao, MutableLiveData<Boolean> isLoading)
        {
            this.besoinDao = besoinDao;
            this.isLoading = isLoading;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.isLoading.postValue(true);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            this.isLoading.postValue(false);
        }

        @Override
        protected Void doInBackground(Entity_Besoin... entity_besoins) {
            this.besoinDao.delete(entity_besoins[0]);
            return null;
        }
    }

    private static class deleteCustomAsyncTask extends AsyncTask<Void, Void, Void>
    {
        private tEtatDeBesoinDao besoinDao;
        private MutableLiveData<Boolean> isLoading;

        private deleteCustomAsyncTask(tEtatDeBesoinDao besoinDao, MutableLiveData<Boolean> isLoading)
        {
            this.besoinDao = besoinDao;
            this.isLoading = isLoading;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.isLoading.postValue(true);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            this.isLoading.postValue(false);
        }

        @Override
        protected Void doInBackground(Void... codeProjetBesoin) {
            this.besoinDao.deleteCustom();
            return null;
        }
    }

    private static class deleteAllBesoinAsyncTask extends AsyncTask<Void, Void, Void>
    {
        private tEtatDeBesoinDao besoinDao;
        private MutableLiveData<Boolean> isLoading;

        private deleteAllBesoinAsyncTask(tEtatDeBesoinDao besoinDao, MutableLiveData<Boolean> isLoading)
        {
            this.besoinDao = besoinDao;
            this.isLoading = isLoading;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.isLoading.postValue(true);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            this.isLoading.postValue(false);
        }

        @Override
        protected Void doInBackground(Void... entity_besoins) {
            this.besoinDao.deleteAllBesoin();
            return null;
        }
    }

    private void Erreur(int code)
    {
        switch (code)
        {
            case 404:
                Toast.makeText(application, "Seveur introuvable", Toast.LENGTH_LONG).show();
                break;
            case 500:
                Toast.makeText(application, "Erreur Serveur", Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(application, "Problème inconnue", Toast.LENGTH_LONG).show();
                break;
        }
    }

}
