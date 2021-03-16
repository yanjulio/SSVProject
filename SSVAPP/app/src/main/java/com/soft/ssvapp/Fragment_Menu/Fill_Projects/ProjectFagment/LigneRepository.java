package com.soft.ssvapp.Fragment_Menu.Fill_Projects.ProjectFagment;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.soft.ssvapp.Data.Entity_Ligne;
import com.soft.ssvapp.Data.Entity_ProjectWithEntity_Ligne;
import com.soft.ssvapp.Data.Kp_BatimentData;
import com.soft.ssvapp.Data.tLigneDao;
import com.soft.ssvapp.DataRetrofit.Ligne.LigneRetrofit;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LigneRepository {

    com.soft.ssvapp.DataRetrofit.Ligne.LigneRepository ligneRepository;
    private tLigneDao tLigneDao;
    private LiveData<List<Entity_ProjectWithEntity_Ligne>> ligneLiveData;
    private List<Entity_ProjectWithEntity_Ligne> ligneLiveDataList = new ArrayList<>();
    private MutableLiveData<Boolean> isLoding_value = new MutableLiveData<Boolean>();
    Application application;

    public LigneRepository(Application application)
    {
        Kp_BatimentData dataLigne = Kp_BatimentData.getInstance(application);
        this.application = application;
        tLigneDao = dataLigne.ligneDao();
        ligneRepository = com.soft.ssvapp.DataRetrofit.Ligne.LigneRepository.getInstance();
    }

    public int NombreLigne()
    {
        return tLigneDao.NombreLigne();
    }

    public int NombreLigneParProjet(String codeProjet)
    {
        return tLigneDao.NombreLigneParProjet(codeProjet);
    }

    public void insertSimpleLigneOnLine(Entity_Ligne entity_ligne)
    {
        LigneRetrofit ligneRetrofit = new LigneRetrofit(entity_ligne.getCodeLigne(), entity_ligne.getCodeProject(),
                entity_ligne.getDesignationLigne(), entity_ligne.getPrevision());
        Call<Void> call_ligne = ligneRepository.ligneConnexion().createLigne(ligneRetrofit);
        isLoding_value.postValue(true);
        call_ligne.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful())
                {
                    isLoding_value.postValue(false);
                    insert(entity_ligne);
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
            public void onFailure(Call<Void> call, Throwable t) {
                isLoding_value.postValue(false);
                Toast.makeText(application, "Connexion problem.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void UpdateSimpleLigneOnline(Entity_Ligne entity_ligne)
    {
        LigneRetrofit ligneRetrofit = new LigneRetrofit(entity_ligne.getCodeLigne(), entity_ligne.getCodeProject(),
                entity_ligne.getDesignationLigne(), entity_ligne.getPrevision());
        Call<Boolean> call_update = ligneRepository.ligneConnexion().updateLigne(ligneRetrofit);
        isLoding_value.postValue(true);
        call_update.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful())
                {
                    isLoding_value.postValue(false);
                    if (response.body() == true)
                    {
                        update(entity_ligne);
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
            public void onFailure(Call<Boolean> call, Throwable t) {
                isLoding_value.postValue(false);
                Toast.makeText(application, "Connexion problem.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void DeleteSimpleLigneOnline(Entity_Ligne entity_ligne)
    {
        LigneRetrofit ligneRetrofit = new LigneRetrofit(entity_ligne.getCodeLigne(), entity_ligne.getCodeProject(),
                entity_ligne.getDesignationLigne(), entity_ligne.getPrevision());
        Call<Boolean> call_delete = ligneRepository.ligneConnexion().deleteLigne(ligneRetrofit);
        isLoding_value.postValue(true);
        call_delete.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful())
                {
                    isLoding_value.postValue(false);
                    if (response.body() == true)
                    {
                        delete(entity_ligne);
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
            public void onFailure(Call<Boolean> call, Throwable t) {
                isLoding_value.postValue(false);
                Toast.makeText(application, "Connexion Problem.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public MutableLiveData<Boolean> getIsLoding_value()
    {
        return isLoding_value;
    }

    public void insert(Entity_Ligne entity_ligne)
    {
//        insertAsyncTask insert =
                new insertAsyncTask(tLigneDao).execute(entity_ligne);
//        insert.execute(entity_ligne);
//        return insert.getBackResponse();
    }

    public int update(Entity_Ligne entity_ligne)
    {
        new updateAsyncTask(tLigneDao).execute(entity_ligne);
        return 1;
    }

    public void deleteCustom(String codeProjet)
    {
        new deletetCustomAsyncTask(tLigneDao).execute(codeProjet);
    }

    public void delete(Entity_Ligne entity_ligne)
    {
        new deletetAsyncTask(tLigneDao).execute(entity_ligne);
    }

    public LiveData<List<Entity_ProjectWithEntity_Ligne>> getCodeLigne(String codeProjet)
    {
        ligneLiveData = tLigneDao.getCodeLigne(codeProjet);
        return ligneLiveData;
    }

    public void getLigneLiveDataList(String codeProjet)
    {
        ligneLiveDataList.clear();
        ligneLiveDataList = tLigneDao.getCodeLigneList(codeProjet);
        insertOnlineLigne(codeProjet);
    }

    private void insertOnlineLigne(String codeProjet)
    {
        ligneRepository.ligneConnexion().getLignesParProjet(codeProjet).enqueue(new Callback<List<LigneRetrofit>>() {
            @Override
            public void onResponse(Call<List<LigneRetrofit>> call, Response<List<LigneRetrofit>> response) {
                if (response.isSuccessful())
                {
                    if (ligneLiveDataList.isEmpty())
                    {
                        for (int a = 0; a < response.body().size(); a++)
                        {
                            Entity_Ligne entity_ligne =
                                    new Entity_Ligne(
                                            response.body().get(a).getCodeLigne(),
                                            response.body().get(a).getCodeProject(),
                                            response.body().get(a).getDesignationLIgne(),
                                            response.body().get(a).getPrevision()
                                    );
                            insert(entity_ligne);
                        }
                    }
                    else
                    {
                        for (int i = 0; i < response.body().size(); i++)
                        {
                            Entity_Ligne entity_ligne =
                                    new Entity_Ligne(
                                            response.body().get(i).getCodeLigne().trim(),
                                            response.body().get(i).getCodeProject().trim(),
                                            response.body().get(i).getDesignationLIgne().trim(),
                                            response.body().get(i).getPrevision()
                                    );
                            for (int e = 0; e < ligneLiveDataList.size(); e++)
                            {
                                String codeLigne_list = ligneLiveDataList.get(e).getEntity_ligne().getCodeLigne().trim();
                                String codeLigne_entity = entity_ligne.getCodeLigne();
                                if (codeLigne_list.equals(codeLigne_entity))
                                {
                                    entity_ligne.setIdLigne(ligneLiveDataList.get(e).getEntity_ligne().getIdLigne());
                                    update(entity_ligne);
                                }
                                else if (codeLigne_list!=codeLigne_entity)
                                {
                                    insert(entity_ligne);
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
            public void onFailure(Call<List<LigneRetrofit>> call, Throwable t) {
                Toast.makeText(application, "Connexion Problem.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private static class insertAsyncTask extends AsyncTask<Entity_Ligne, Void, Void>
    {
        private tLigneDao ligneDao;
        private long insertResponse;

        private insertAsyncTask(tLigneDao ligneDao)
        {
            this.ligneDao = ligneDao;
        }

        long getBackResponse()
        {
            return this.insertResponse;
        }

        @Override
        protected Void doInBackground(Entity_Ligne... entity_lignes) {
            try {
//                this.insertResponse =
                        this.ligneDao.insert(entity_lignes[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Entity_Ligne, Void, Void>
    {
        private tLigneDao tLigneDao;

        private updateAsyncTask(tLigneDao ligneDao)
        {
            this.tLigneDao = ligneDao;
        }

        @Override
        protected Void doInBackground(Entity_Ligne... entity_lignes) {
            try {
                this.tLigneDao.update(entity_lignes[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private static class deletetAsyncTask extends AsyncTask<Entity_Ligne, Void, Void>
    {
        private tLigneDao tLigneDao;

        private deletetAsyncTask(tLigneDao ligneDao)
        {
            this.tLigneDao = ligneDao;
        }

        @Override
        protected Void doInBackground(Entity_Ligne... entity_lignes) {
            this.tLigneDao.delete(entity_lignes[0]);
            return null;
        }
    }

    private static class deletetCustomAsyncTask extends AsyncTask<String, Void, Void>
    {
        private tLigneDao tLigneDao;

        private deletetCustomAsyncTask(tLigneDao ligneDao)
        {
            this.tLigneDao = ligneDao;
        }

        @Override
        protected Void doInBackground(String... codeProjet) {
            this.tLigneDao.deleteCustom(codeProjet[0]);
            return null;
        }
    }
}
