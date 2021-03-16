package com.soft.ssvapp.ViewModels;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.soft.ssvapp.Data.Entity_Ligne;
import com.soft.ssvapp.Fragment_Menu.Fill_Projects.ProjectFagment.Entity_Ligne_Serializable;
import com.soft.ssvapp.Repositories.LigneVolatileRepository;

import java.util.ArrayList;
import java.util.List;

public class LigneVolatitileViewModel extends ViewModel {

    private MutableLiveData<List<Entity_Ligne>> ligne_encours_creation;
    private ArrayList<Entity_Ligne_Serializable> ligne_serializables;
    private LigneVolatileRepository ligne_repositories;

    public void init()
    {
        if (ligne_encours_creation != null)
        {
            return;
        }
        ligne_encours_creation = null;
        ligne_repositories = LigneVolatileRepository.getInstance();
        ligne_encours_creation = new MutableLiveData<>();
        ligne_serializables = new ArrayList<>();
//        ligne_encours_creation = ligne_repositories.getLigne();
//        ligne_serializables = ligne_repositories.getLigneSerializable();
    }

    public void getClear()
    {
        ligne_repositories.clearDataset();
    }

    public LiveData<List<Entity_Ligne>> getLigne()
    {
        return ligne_repositories.getLigne();
    }

    public ArrayList<Entity_Ligne_Serializable> getLigne_serializables()
    {
        return ligne_repositories.getLigneSerializable();
    }

    public void addNewValue(final Entity_Ligne entity_ligne)
    {
//        mIsUpdating.setValue(true);
        new insertValues(entity_ligne).execute();
    }

    public void deleteVolatile(final Entity_Ligne entity_ligne)
    {
        new deleteValues(entity_ligne) .execute();
    }

    public void updateVolatile(int id, final Entity_Ligne entity_ligne)
    {
        new updateValues(id, entity_ligne).execute();
    }

    private class insertValues extends AsyncTask<Void, Void, Void>
    {
        private Entity_Ligne entity_ligne;

        insertValues(Entity_Ligne entity_ligne)
        {
            this.entity_ligne = entity_ligne;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ligne_repositories.setLignes(this.entity_ligne);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }

    private class deleteValues extends AsyncTask<Void, Void, Void>
    {
        private Entity_Ligne entity_ligne;

        deleteValues(Entity_Ligne entity_ligne)
        {
            this.entity_ligne = entity_ligne;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ligne_repositories.delete(this.entity_ligne);
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

    private class updateValues extends AsyncTask<Void, Void, Void>
    {
        private Entity_Ligne entity_ligne;
        private int id;

        updateValues(int id, Entity_Ligne entity_ligne)
        {
            this.entity_ligne = entity_ligne;
            this.id = id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ligne_repositories.update(this.id, this.entity_ligne);
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

}
