package com.soft.ssvapp.Fragment_Menu.FillGroupeCompte;

import android.app.Application;
import android.net.CaptivePortal;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.soft.ssvapp.Data.Entity_GroupeCompte;
import com.soft.ssvapp.Data.Kp_BatimentData;
import com.soft.ssvapp.Data.tGroupeCompteDao;
import com.soft.ssvapp.DataRetrofit.GroupeCompte.GroupeCompteResponse;
import com.soft.ssvapp.DataRetrofit.GroupeCompte.GroupeCompteRetrofitRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupeCompteRepository {

    GroupeCompteRetrofitRepository groupeCompteRetrofitRepository;
    tGroupeCompteDao groupeCompteDao;
    private LiveData<List<Entity_GroupeCompte>> getAllGroupeCompte;
    private List<Entity_GroupeCompte> getAllGroupecompteList;
    private Application application;

    public GroupeCompteRepository(Application application)
    {
        this.application = application;
        Kp_BatimentData data = Kp_BatimentData.getInstance(application);
        groupeCompteDao = data.tGroupeCompteDao();
        getAllGroupeCompte = groupeCompteDao.getAllGroupeCompte();
        getAllGroupecompteList = groupeCompteDao.getAllGroupeCompteList();
        groupeCompteRetrofitRepository = GroupeCompteRetrofitRepository.getInstance();
    }

    public LiveData<List<Entity_GroupeCompte>> getGetAllGroupeCompte()
    {
        return getAllGroupeCompte;
    }

    public void insertOnlineGroupeCompte()
    {
        groupeCompteRetrofitRepository.groupeCompteConnexion().getGroupeCompte().enqueue(new Callback<List<GroupeCompteResponse>>() {
            @Override
            public void onResponse(Call<List<GroupeCompteResponse>> call, Response<List<GroupeCompteResponse>> response) {
                if (response.isSuccessful())
                {
                    if (getAllGroupecompteList.isEmpty())
                    {
                        for (int e = 0; e < response.body().size(); e++)
                        {
                            Entity_GroupeCompte entity_groupeCompte = new Entity_GroupeCompte(
                                    response.body().get(e).getGroupeCompte(),
                                    response.body().get(e).getCadre(),
                                    response.body().get(e).getDesignationGroupe()
                            );
                            insert(entity_groupeCompte);
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
            public void onFailure(Call<List<GroupeCompteResponse>> call, Throwable t) {
                Toast.makeText(application, "Connexion Problem", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void insert(Entity_GroupeCompte entity_groupeCompte)
    {
        new insertAsyncTask(groupeCompteDao).execute(entity_groupeCompte);
    }

    private class insertAsyncTask extends AsyncTask<Entity_GroupeCompte, Void, Void>
    {
        private tGroupeCompteDao tGroupeCompteDaos;

        private insertAsyncTask(tGroupeCompteDao groupeCompteDao)
        {
            this.tGroupeCompteDaos = groupeCompteDao;
        }

        @Override
        protected Void doInBackground(Entity_GroupeCompte... entity_groupeComptes) {
            try {
                this.tGroupeCompteDaos.insert(entity_groupeComptes[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
