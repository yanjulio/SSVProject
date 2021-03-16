package com.soft.ssvapp.Fragment_Menu.Fill_Operation.True_Operations;

import android.app.Application;
import android.os.AsyncTask;

import com.soft.ssvapp.Data.Entity_Operation;
import com.soft.ssvapp.Data.Kp_BatimentData;
import com.soft.ssvapp.Data.tOperationDao;

public class OperationRepository {

    private tOperationDao tOperationDao;

    public OperationRepository(Application application)
    {
        Kp_BatimentData db = Kp_BatimentData.getInstance(application);
        tOperationDao = db.tOperationDao();
    }

    public void insert(Entity_Operation entity_operation)
    {
        new insertAsyncTask(tOperationDao).equals(entity_operation);
    }

    private class insertAsyncTask extends AsyncTask<Entity_Operation, Void, Void>
    {
        private tOperationDao tOperationDao;

        private insertAsyncTask(tOperationDao tOperationDao)
        {
            this.tOperationDao = tOperationDao;
        }

        @Override
        protected Void doInBackground(Entity_Operation... entity_operations) {
            try {
                this.tOperationDao.insert(entity_operations[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
