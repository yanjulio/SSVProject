package com.soft.ssvapp.Fragment_Menu.Other_FillData;

import android.app.Application;
import android.os.AsyncTask;

import com.soft.ssvapp.Data.Entity_Compte;
import com.soft.ssvapp.Data.Kp_BatimentData;
import com.soft.ssvapp.Data.tCompteDao;

public class CompteRepository {

    private tCompteDao tCompteDao;

    public CompteRepository(Application application)
    {
        Kp_BatimentData db = Kp_BatimentData.getInstance(application);
        tCompteDao = db.compteDao();
    }

    public void insert(Entity_Compte entity_compte)
    {
        new insertAsyncTask(tCompteDao).execute(entity_compte);
    }

    private class insertAsyncTask extends AsyncTask<Entity_Compte, Void, Void>
    {
        private tCompteDao tCompteDao;

        private insertAsyncTask(tCompteDao tCompteDao)
        {
            this.tCompteDao = tCompteDao;
        }

        @Override
        protected Void doInBackground(Entity_Compte... entity_comptes) {
            try {
                this.tCompteDao.insert(entity_comptes[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
