package com.soft.ssvapp.Fragment_Menu.Fill_Projects.ProjectFagment;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.common.internal.Objects;
import com.soft.ssvapp.Data.Entity_Project;
import com.soft.ssvapp.Data.Kp_BatimentData;
import com.soft.ssvapp.Data.tProjectDao;
import com.soft.ssvapp.DataRetrofit.ProjetRetrofit.ProjetRetrofit;
import com.soft.ssvapp.DataRetrofit.ProjetRetrofit.ProjetRetrofitRespository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectRepository {

    // this is the repository online of the project
    ProjetRetrofitRespository projetRespositoryOnline;
    private tProjectDao projectDao;
    private LiveData<List<Entity_Project>> onGoingProjects;
    private LiveData<List<Entity_Project>> endProjects;
    private List<Entity_Project> listProjet = new ArrayList<>();
    private MutableLiveData<Boolean> isdownloaing;
    Application application;

    public ProjectRepository(Application application)
    {
        Kp_BatimentData database = Kp_BatimentData.getInstance(application);
        projetRespositoryOnline = ProjetRetrofitRespository.getInstance();
        projectDao = database.projectDao();
        onGoingProjects = projectDao.getOnGoingProjects();
        endProjects = projectDao.getEndProjjects();
        this.application = application;
        isdownloaing = new MutableLiveData<>();
    }

    public void insert(Entity_Project entity_project)
    {
        new insertAsyncTask(projectDao, isdownloaing).execute(entity_project);
    }

    public int update(Entity_Project entity_project)
    {
        new updateAsyncTask(projectDao, isdownloaing).execute(entity_project);
        return 1;
    }

    public void update_custom(Entity_Project entity_project)
    {
        ProjetRetrofit projetRetrofit =
                new ProjetRetrofit(
                  entity_project.getCodeProject(), entity_project.getDesignationProject(), entity_project.getLieu(),
                        entity_project.getDateDebut(), entity_project.getDateFin(), entity_project.getChefDuProjet(),
                        entity_project.getEtat(), entity_project.getCompte()
                );
        Call<Boolean> call_modifier = projetRespositoryOnline.projetConnexion().modifierProjet(projetRetrofit);
        isdownloaing.postValue(true);
        call_modifier.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful())
                {
                    isdownloaing.postValue(false);
                    if (response.body() == true)
                    {
                        new updatCustomAsyncTask(projectDao, isdownloaing).execute(entity_project);
                        Toast.makeText(application, "Modification bien faite", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(application, "échec de modification, ce projet n'existe pas" +
                                "Veillez synchroniser vos données", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Erreur(response.code());
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                isdownloaing.postValue(false);
                Toast.makeText(application, "Connexion problem", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void delete(Entity_Project entity_project)
    {
        new deleteAsyncTask(projectDao, isdownloaing).execute(entity_project);
    }

    public void deleteAllProjects()
    {
        new deleteAlltProjectsAsyncTask(projectDao, isdownloaing).execute();
    }

    public void UpdateListProjet()
    {
        listProjet.clear();
        listProjet = projectDao.getListProjet();
        InsertOnlineDataToRoom(listProjet);
    }

    public void InsertOnlineDataToRoom(List<Entity_Project> entity_projects)
    {
        isdownloaing.postValue(true);
        projetRespositoryOnline.projetConnexion().getToutLeProjetsEncours().enqueue(new Callback<List<ProjetRetrofit>>() {
            @Override
            public void onResponse(Call<List<ProjetRetrofit>> call, Response<List<ProjetRetrofit>> response) {
                if (response.isSuccessful())
                {
                    isdownloaing.postValue(false);
                    if(entity_projects.isEmpty())
                    {
                        for (int a = 0; a < response.body().size(); a++)
                        {
                            Entity_Project entity_project_from_response = new Entity_Project(
                                    response.body().get(a).getCodeProject(),
                                    response.body().get(a).getDesignationProject(),
                                    response.body().get(a).getDateDebut(),
                                    response.body().get(a).getDateFin(),
                                    response.body().get(a).getLieu(),
                                    response.body().get(a).getChefDuProjet(),
                                    response.body().get(a).getEtat(),
                                    response.body().get(a).getCompte(),
                                    response.body().get(a).getCompteClient()
                            );
                            entity_project_from_response.setEtat_envoyer(1);
                            insert(entity_project_from_response);
                        }
                    }
                    else
                    {
                        for (int a = 0; a < response.body().size(); a++)
                        {
                            Entity_Project entity_project_from_response = new Entity_Project(
                                    response.body().get(a).getCodeProject(),
                                    response.body().get(a).getDesignationProject(),
                                    response.body().get(a).getDateDebut(),
                                    response.body().get(a).getDateFin(),
                                    response.body().get(a).getLieu(),
                                    response.body().get(a).getChefDuProjet(),
                                    response.body().get(a).getEtat(),
                                    response.body().get(a).getCompte(),
                                    response.body().get(a).getCompteClient()
                            );
                            entity_project_from_response.setEtat_envoyer(1);
                            for (int i = 0; i < entity_projects.size(); i++)
                            {
                                if (entity_project_from_response.getCodeProject().equals(entity_projects.get(i).getCodeProject()))
                                {
//                                    Toast.makeText(application, "J'update projet " + entity_projects.get(i).getCodeProject(),
//                                            Toast.LENGTH_LONG).show();
                                    entity_project_from_response.setIdProject(entity_projects.get(i).getIdProject());
                                    update(entity_project_from_response);
                                }
                                else
                                {
                                    insert(entity_project_from_response);
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
            public void onFailure(Call<List<ProjetRetrofit>> call, Throwable t) {
                isdownloaing.postValue(false);
                Toast.makeText(application, "Problème de connexion", Toast.LENGTH_LONG).show();
            }
        });
    }

    public MutableLiveData<Boolean> getIsdownloaing()
    {
        return isdownloaing;
    }

    public LiveData<List<Entity_Project>> getOnGoingProjects()
    {
        return onGoingProjects;
    }

    public LiveData<List<Entity_Project>> getEnProjects()
    {
        return endProjects;
    }

    private static class insertAsyncTask extends AsyncTask<Entity_Project, Void, Void>
    {
        private tProjectDao projectDao;
        private MutableLiveData<Boolean> isLoading;

        private insertAsyncTask(tProjectDao projectDao, MutableLiveData<Boolean> isLoading)
        {
            this.projectDao = projectDao;
            this.isLoading = isLoading;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isLoading.postValue(true);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            isLoading.postValue(false);
        }

        @Override
        protected Void doInBackground(Entity_Project... entity_projects) {
            try {
                projectDao.insert(entity_projects[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    // update the updatecustom
    private static class updatCustomAsyncTask extends AsyncTask<Entity_Project, Void, Void>
    {
        private tProjectDao projectDao;
        private MutableLiveData<Boolean> isLoading;

        private updatCustomAsyncTask(tProjectDao projectDao, MutableLiveData<Boolean> isLoading)
        {
            this.projectDao = projectDao;
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
        protected Void doInBackground(Entity_Project... entity_projects) {
            this.projectDao.custom_update(entity_projects[0].getCodeProject(),
                    entity_projects[0].getDesignationProject(), entity_projects[0].getDateDebut(),
                    entity_projects[0].getDateFin(), entity_projects[0].getLieu(), entity_projects[0].getChefDuProjet());
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Entity_Project, Void, Void>
    {
        private tProjectDao projectDao;
        private MutableLiveData<Boolean> isLoading;

        private updateAsyncTask(tProjectDao projectDao, MutableLiveData<Boolean> isLoading)
        {
            this.projectDao = projectDao;
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
        protected Void doInBackground(Entity_Project... entity_projects) {
            this.projectDao.update(entity_projects[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Entity_Project, Void, Void>
    {
        private tProjectDao projectDao;
        private MutableLiveData<Boolean> isLoading;

        private deleteAsyncTask(tProjectDao projectDao, MutableLiveData<Boolean> isLoading)
        {
            this.projectDao = projectDao;
            this.isLoading=isLoading;
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
        protected Void doInBackground(Entity_Project... entity_projects) {
            projectDao.delete(entity_projects[0]);
            return null;
        }
    }

    private static class deleteAlltProjectsAsyncTask extends AsyncTask<Void, Void, Void>
    {
        private tProjectDao projectDao;
        private MutableLiveData<Boolean> isLoading;

        private deleteAlltProjectsAsyncTask(tProjectDao projectDao, MutableLiveData<Boolean> isLoading)
        {
            this.projectDao = projectDao;
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
        protected Void doInBackground(Void... voids) {
            projectDao.deleteAllProject();
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
                Toast.makeText(application, "Erreur Serveur", Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(application, "Problème inconnue", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
