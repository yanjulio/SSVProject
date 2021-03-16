package com.soft.ssvapp.Fragment_Menu.Other_FillData;

import android.app.Application;
import android.os.AsyncTask;

import com.soft.ssvapp.Data.Entity_MvtCompte;
import com.soft.ssvapp.Data.Kp_BatimentData;
import com.soft.ssvapp.Data.tMvtCompteDao;

public class MvtRepository {

    private tMvtCompteDao tMvtCompteDao;

    public MvtRepository(Application application)
    {
        Kp_BatimentData db = Kp_BatimentData.getInstance(application);
        tMvtCompteDao = db.mvtCompteDao();
    }

    public void insert(Entity_MvtCompte entity_mvtCompte)
    {
        new insertAsyncTask(tMvtCompteDao).execute(entity_mvtCompte);
    }

    private class insertAsyncTask extends AsyncTask<Entity_MvtCompte, Void, Void>
    {
        private tMvtCompteDao tMvtCompteDao;

        private insertAsyncTask(tMvtCompteDao tMvtCompteDao)
        {
            this.tMvtCompteDao = tMvtCompteDao;
        }

        @Override
        protected Void doInBackground(Entity_MvtCompte... entity_mvtComptes) {
            try
            {
                this.tMvtCompteDao.insert(entity_mvtComptes[0]);
            }
            catch (Exception e)
            {

            }
            return null;
        }
    }
}
