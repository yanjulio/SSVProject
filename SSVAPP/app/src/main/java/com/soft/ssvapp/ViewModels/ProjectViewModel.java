package com.soft.ssvapp.ViewModels;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.soft.ssvapp.Fragment_Menu.Fill_Projects.ProjectFagment.Projects_list_Objects;
import com.soft.ssvapp.Repositories.Project_Repositories;

import java.util.List;

public class ProjectViewModel extends ViewModel {

    private MutableLiveData<List<Projects_list_Objects>> projet_encours;
    private Project_Repositories project_repositories;
    private MutableLiveData<Boolean> mIsUpdating = new MutableLiveData<>();
    private Context context;

    public void init(Context context)
    {
        if (projet_encours != null)
        {
            return;
        }
        this.context = context;
        projet_encours = null;
        project_repositories = Project_Repositories.getInstance();
        projet_encours = project_repositories.getProject(context);
    }

    public LiveData<List<Projects_list_Objects>> getProjet()
    {
        return projet_encours;
    }

    public void addNewValue(final Projects_list_Objects projects_elts)
    {
        mIsUpdating.setValue(true);
        new values(projects_elts).execute();
    }

    private class values extends AsyncTask<Void, Void, Void>
    {

        List<Projects_list_Objects> currentProjects = projet_encours.getValue();
        private Projects_list_Objects projects;

        values(Projects_list_Objects projects)
        {
            this.projects = projects;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            this.currentProjects.add(this.projects);
            projet_encours.postValue(this.currentProjects);
            mIsUpdating.postValue(false);

            Toast.makeText(context, "values asyc " + projet_encours.toString().length(), Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try
            {
                Thread.sleep(2000);
            }catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            return null;
        }
    }

    public LiveData<Boolean> getIsLoading()
    {
        return mIsUpdating;
    }
}
