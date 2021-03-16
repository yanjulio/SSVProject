package com.soft.ssvapp.Fragment_Menu.Fill_EtatBesoin;

import android.app.Application;
import android.os.AsyncTask;
import android.text.style.AlignmentSpan;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.soft.ssvapp.Data.Entity_BesoinWithEntity_DetailBesoin;
import com.soft.ssvapp.Data.Entity_DetailBesoin;
import com.soft.ssvapp.Data.Entity_DetailBesoinWithEntity_Article;
import com.soft.ssvapp.Data.Kp_BatimentData;
import com.soft.ssvapp.Data.tDetailBesoinDao;
import com.soft.ssvapp.DataRetrofit.DetailsEtatDeBesoin.DetailsEtatDeBesoinRepository;
import com.soft.ssvapp.DataRetrofit.DetailsEtatDeBesoin.DetailsEtatDeBesoinRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailBesoinRepository {

    DetailsEtatDeBesoinRepository repository_detailsEtatBesoin;
    private tDetailBesoinDao detailBesoinDao;
    private LiveData<List<Entity_BesoinWithEntity_DetailBesoin>> detailsBesoin;
    private LiveData<List<Entity_DetailBesoinWithEntity_Article>> detailsBesoinArticle;
    private List<Entity_DetailBesoinWithEntity_Article> detailBesoinList;
    private Application application;
    private MutableLiveData<Boolean> progressbarObservable;

    public DetailBesoinRepository(Application application)
    {
        this.application = application;
        Kp_BatimentData data = Kp_BatimentData.getInstance(application);
        detailBesoinDao = data.detailBesoinDao();
        repository_detailsEtatBesoin = DetailsEtatDeBesoinRepository.getInstance();
        progressbarObservable = new MutableLiveData<>();
    }

    public LiveData<List<Entity_BesoinWithEntity_DetailBesoin>> getDetailsBesoin(String codeEtatDeBesoin)
    {
        detailsBesoin = detailBesoinDao.getDetailBesoin(codeEtatDeBesoin);
        return detailsBesoin;
    }

    public LiveData<List<Entity_DetailBesoinWithEntity_Article>> getDetailBesoinArticle(String codeEtatDeBesoin)
    {
        detailsBesoinArticle = detailBesoinDao.getDetailBesoinArticle(codeEtatDeBesoin);
        return detailsBesoinArticle;
    }

    public List<Entity_DetailBesoinWithEntity_Article> getDetailsBesoinList(String codeEtatDeBesoin)
    {
        detailBesoinList = detailBesoinDao.getDeatilsBesoinArticleList(codeEtatDeBesoin);
        insertOrUpdateDetailsBesoin(codeEtatDeBesoin);
        return detailBesoinList;
    }

    public void insertOrUpdateDetailsBesoin(String codeEtatDeBesoin)
    {
        Call<List<DetailsEtatDeBesoinRetrofit>> details_retrofit =
                repository_detailsEtatBesoin.getDetailsEtatDeBesoinConnexion().getListDetailsBesoin(codeEtatDeBesoin);
        progressbarObservable.postValue(true);
                details_retrofit.enqueue(new Callback<List<DetailsEtatDeBesoinRetrofit>>() {
                    @Override
                    public void onResponse(Call<List<DetailsEtatDeBesoinRetrofit>> call,
                                           Response<List<DetailsEtatDeBesoinRetrofit>> response) {
                        if (response.isSuccessful())
                        {
                            progressbarObservable.postValue(false);
                            if (detailBesoinList.isEmpty())
                            {
                                for (int e = 0; e < response.body().size(); e++)
                                {
                                    Entity_DetailBesoin entity_detailBesoin =
                                            new Entity_DetailBesoin(
                                                    response.body().get(e).getIdDetailEB(),
                                                    response.body().get(e).getCodeEtatdeBesoin(),
                                                    response.body().get(e).getCodeArticle(),
                                                    response.body().get(e).getCodeLigne(),
                                                    response.body().get(e).getLibelleDetail(),
                                                    response.body().get(e).getQte(),
                                                    response.body().get(e).getPu(),
                                                    response.body().get(e).getEntree(),
                                                    response.body().get(e).getSortie()
                                            );
                                    insert(entity_detailBesoin);
                                }
                            }
                            else
                            {
                                for (int c = 0; c < response.body().size(); c++)
                                {
                                    Entity_DetailBesoin entity_detailBesoin =
                                            new Entity_DetailBesoin(
                                                    response.body().get(c).getIdDetailEB(),
                                                    response.body().get(c).getCodeEtatdeBesoin(),
                                                    response.body().get(c).getCodeArticle(),
                                                    response.body().get(c).getCodeLigne(),
                                                    response.body().get(c).getLibelleDetail(),
                                                    response.body().get(c).getQte(),
                                                    response.body().get(c).getPu(),
                                                    response.body().get(c).getEntree(),
                                                    response.body().get(c).getSortie()
                                            );

                                    for (int i = 0; i < detailBesoinList.size(); i++)
                                    {
                                        if (detailBesoinList.get(i).getDetailBesoin().getIdDetailEBOnline()
                                                ==entity_detailBesoin.getIdDetailEBOnline()
                                                || detailBesoinList.get(i).getDetailBesoin().getIdDetailEBOnline()==0)
                                        {
                                            entity_detailBesoin.setIdDetailEB(
                                                    detailBesoinList.get(i).getDetailBesoin().getIdDetailEB());
                                            update(entity_detailBesoin);
                                        }
                                        else
                                        {
                                            insert(entity_detailBesoin);
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
                    public void onFailure(Call<List<DetailsEtatDeBesoinRetrofit>> call, Throwable t) {
                        progressbarObservable.postValue(false);
                        Toast.makeText(application, "Connexion problem. check your connexion and try again.", Toast.LENGTH_LONG).show();
                    }
                });
    }

    public MutableLiveData<Boolean> isLoading()
    {
        return progressbarObservable;
    }

    public void insert(Entity_DetailBesoin entity_detailBesoin)
    {
        new insertAsyncTask(detailBesoinDao, progressbarObservable).execute(entity_detailBesoin);
    }

    public void deleteDetailOnline(Entity_DetailBesoin entity_detailBesoin)
    {
        DetailsEtatDeBesoinRetrofit detailsRetrofit =
                new DetailsEtatDeBesoinRetrofit(
                        entity_detailBesoin.getCodeEtatdeBesoin(),
                        entity_detailBesoin.getCodeArticle(),
                        entity_detailBesoin.getCodeLigne(),
                        entity_detailBesoin.getLibelleDetail(),
                        entity_detailBesoin.getQte(),
                        entity_detailBesoin.getPu(),
                        entity_detailBesoin.getSortie(),
                        entity_detailBesoin.getEntree());
        detailsRetrofit.setIdDetailEB(entity_detailBesoin.getIdDetailEBOnline());
        Call<Boolean> call_effacer_detail =
                repository_detailsEtatBesoin.getDetailsEtatDeBesoinConnexion().effaceDetailEtatBesoin(detailsRetrofit);
        progressbarObservable.postValue(true);
        call_effacer_detail.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful())
                {
                    progressbarObservable.postValue(false);
                    if (response.body() == true)
                    {
                        Toast.makeText(application, "1 element effacé", Toast.LENGTH_LONG).show();
                        deleteDetail(entity_detailBesoin);
                    }
                    else
                    {
                        Toast.makeText(application, "la tantative de suppression a échouée : "
                                + response.body(), Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Erreur(response.code());
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                progressbarObservable.postValue(false);
                Toast.makeText(application, "Connexion problem.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void updateDetailOnline(Entity_DetailBesoin entity_detailBesoin)
    {
        DetailsEtatDeBesoinRetrofit detailsRetrofit =
                new DetailsEtatDeBesoinRetrofit(
                        entity_detailBesoin.getCodeEtatdeBesoin(),
                        entity_detailBesoin.getCodeArticle(),
                        entity_detailBesoin.getCodeLigne(),
                        entity_detailBesoin.getLibelleDetail(),
                        entity_detailBesoin.getQte(),
                        entity_detailBesoin.getPu(),
                        entity_detailBesoin.getSortie(),
                        entity_detailBesoin.getEntree());
        detailsRetrofit.setIdDetailEB(entity_detailBesoin.getIdDetailEBOnline());
        Call<Boolean> call_modifier_detail =
                repository_detailsEtatBesoin.getDetailsEtatDeBesoinConnexion().modifierDetailsEtatBesoin(detailsRetrofit);
        progressbarObservable.postValue(true);
        call_modifier_detail.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful())
                {
                    progressbarObservable.postValue(false);
                    if (response.body() == true)
                    {
                        Toast.makeText(application, "Mofication a été bien faite.", Toast.LENGTH_LONG).show();
                        update(entity_detailBesoin);
                    }
                    else
                    {
                        Toast.makeText(application, "Mofication echoue : " + response.body(), Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Erreur(response.code());
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                progressbarObservable.postValue(false);
                Toast.makeText(application, "Connexion problem.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public int update(Entity_DetailBesoin entity_detailBesoin)
    {
        new updateDetailAsyncTask(detailBesoinDao, progressbarObservable).execute(entity_detailBesoin);
        return -1;
    }

    public void deleteDetail(Entity_DetailBesoin entity_detailBesoin)
    {
        new deleteDetailAsyncTask(detailBesoinDao, progressbarObservable).execute(entity_detailBesoin);
    }

    public void deleteAll()
    {
        new deleteAllDetailAsyncTask(detailBesoinDao, progressbarObservable).execute();
    }

    private static class insertAsyncTask extends AsyncTask<Entity_DetailBesoin, Void, Void>
    {

        private tDetailBesoinDao besoinDao;
        private MutableLiveData<Boolean> isLoading;

        private insertAsyncTask(tDetailBesoinDao besoinDao, MutableLiveData<Boolean> isLoading)
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
        protected Void doInBackground(Entity_DetailBesoin... entity_detailBesoins) {
            try {
                this.besoinDao.insert(entity_detailBesoins[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private static class updateDetailAsyncTask extends AsyncTask<Entity_DetailBesoin, Void, Void>
    {
        private tDetailBesoinDao detailBesoinDao;
        private MutableLiveData<Boolean> isLoading;

        private updateDetailAsyncTask(tDetailBesoinDao detailBesoinDao, MutableLiveData<Boolean> isLoading)
        {
            this.detailBesoinDao = detailBesoinDao;
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
        protected Void doInBackground(Entity_DetailBesoin... entity_detailBesoins) {
            try {
                this.detailBesoinDao.update(entity_detailBesoins[0].getLibelleDetail(),
                        entity_detailBesoins[0].getQte(),
                        entity_detailBesoins[0].getPu(), entity_detailBesoins[0].getIdDetailEB());
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }
    }

    private static class deleteDetailAsyncTask extends AsyncTask<Entity_DetailBesoin, Void, Void>
    {

        private tDetailBesoinDao detailBesoinDao;
        private MutableLiveData<Boolean> isLoading;

        private deleteDetailAsyncTask(tDetailBesoinDao detailBesoinDao, MutableLiveData<Boolean> isLoading)
        {
            this.detailBesoinDao = detailBesoinDao;
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
        protected Void doInBackground(Entity_DetailBesoin... entity_detailBesoins) {
            try {
                this.detailBesoinDao.delete(entity_detailBesoins[0]);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }
    }

    private static class deleteAllDetailAsyncTask extends AsyncTask<Void, Void, Void>
    {
        private tDetailBesoinDao detailBesoinDao;
        private MutableLiveData<Boolean> isLoading;

        private deleteAllDetailAsyncTask(tDetailBesoinDao detailBesoinDao, MutableLiveData<Boolean> isLoading)
        {
            this.detailBesoinDao = detailBesoinDao;
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
            this.detailBesoinDao.deleteAll();
            return null;
        }
    }

    private void Erreur(int code)
    {
        switch (code)
        {
            case 404:
                Toast.makeText(application, "server not found.", Toast.LENGTH_LONG).show();
                break;
            case 500:
                Toast.makeText(application, "server broken.", Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(application, "unknown problem.", Toast.LENGTH_LONG).show();
                break;
        }
    }

}
