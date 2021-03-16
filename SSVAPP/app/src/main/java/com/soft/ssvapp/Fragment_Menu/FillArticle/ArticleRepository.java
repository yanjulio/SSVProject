package com.soft.ssvapp.Fragment_Menu.FillArticle;

import android.app.Application;
import android.net.CaptivePortal;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.PrimaryKey;

import com.soft.ssvapp.Data.Entity_Article;
import com.soft.ssvapp.Data.Kp_BatimentData;
import com.soft.ssvapp.Data.tArticleDao;
import com.soft.ssvapp.DataRetrofit.Article.ArticleRespone;
import com.soft.ssvapp.DataRetrofit.Article.ArticleRetrofitRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleRepository {

    ArticleRetrofitRepository articleRetrofitRepository;
    private tArticleDao tArticleDao;
    private LiveData<List<Entity_Article>> getAllArticle;
    private List<Entity_Article> getAllArticleList;
    private int getListsize;
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<Boolean>();
    private Application application;

    public ArticleRepository(Application application)
    {
        this.application = application;
        Kp_BatimentData data = Kp_BatimentData.getInstance(application);
        articleRetrofitRepository = ArticleRetrofitRepository.getInstance();
        tArticleDao = data.tArticleDao();
        getAllArticle = tArticleDao.getAllArticle();
        getAllArticleList = tArticleDao.getAllArticleList();
        getListsize = tArticleDao.getListSize();
    }

    public LiveData<List<Entity_Article>> getArticleSearch(String searchQuery)
    {
        return tArticleDao.getArticleSearch(searchQuery);
    }

    public LiveData<List<Entity_Article>> getGetAllArticle()
    {
        return getAllArticle;
    }

    public Entity_Article entity_article(int idArticle)
    {
        return tArticleDao.entity_article(idArticle);
    }

    public void insertOnlineArticle()
    {
        getAllArticleList = tArticleDao.getAllArticleList();
        isLoading.postValue(true);
        articleRetrofitRepository.articleConnexion().getArticle().enqueue(new Callback<List<ArticleRespone>>() {
            @Override
            public void onResponse(Call<List<ArticleRespone>> call, Response<List<ArticleRespone>> response) {
                if (response.isSuccessful())
                {
                    isLoading.postValue(false);
                    if (getAllArticleList.isEmpty())
                    {
                        for (int e = 0; e < response.body().size(); e++)
                        {
                            Entity_Article entity_article =
                                    new Entity_Article(
                                            response.body().get(e).getCodeArticle(), "",
                                            response.body().get(e).getDesegnationArticle(), response.body().get(e).getCategorieArticle(),
                                            response.body().get(e).getPrixAchat(), response.body().get(e).getPrixVente(),
                                            "",
                                            0.0, 0.0,
                                            0.0, 0
                                    );
                                    insert(entity_article);
                        }
                    }
                    else
                    {
                        for (int e = 0; e < response.body().size(); e++)
                        {
                            Entity_Article entity_article =
                                    new Entity_Article(
                                            response.body().get(e).getCodeArticle(), "",
                                            response.body().get(e).getDesegnationArticle(), response.body().get(e).getCategorieArticle(),
                                            response.body().get(e).getPrixAchat(),response.body().get(e).getPrixVente(),
                                            "",
                                            0.0, 0.0,
                                            0.0, 0
                                    );
                            for (int b = 0; b < getAllArticleList.size(); b++)
                            {
                                if (getAllArticleList.get(b).getCodeArticle().trim().equals(entity_article.getCodeArticle().trim()))
                                {
                                    entity_article.setIdArticle(getAllArticleList.get(b).getIdArticle());
                                    update(entity_article);
                                }
                                else
                                {
                                    insert(entity_article);
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
                            Toast.makeText(application, "sever not found.", Toast.LENGTH_LONG).show();
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

            @Override
            public void onFailure(Call<List<ArticleRespone>> call, Throwable t) {
                isLoading.postValue(false);
                Toast.makeText(application, "Connexion Problem.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void insertArticleOnline(Entity_Article entity_article)
    {
        ArticleRespone articleRespone = new ArticleRespone(entity_article.getCodeArticle(),
                entity_article.getDesignationArticle(), entity_article.getCategorieArticle(), entity_article.getPrixAchat(),
                entity_article.getPrixVente());
        Call<Void> call_insert = articleRetrofitRepository.articleConnexion().ajouterArticle(articleRespone);
        isLoading.postValue(true);
        call_insert.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful())
                {
                    isLoading.postValue(false);
                    insert(entity_article);
                    Toast.makeText(application, "Enregistrement bien fait", Toast.LENGTH_LONG).show();
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
                            Toast.makeText(application, "unknown problem.", Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(application, "Connexion Problem.", Toast.LENGTH_LONG).show();
                isLoading.postValue(false);
            }
        });
    }

    public void updateArticleOnline(Entity_Article entity_article)
    {
        ArticleRespone articleRespone = new ArticleRespone(entity_article.getCodeArticle(),
                entity_article.getDesignationArticle(), entity_article.getCategorieArticle(), entity_article.getPrixAchat(),
                entity_article.getPrixVente());
        Call<Boolean> call_update = articleRetrofitRepository.articleConnexion().modifierArticle(articleRespone);
        isLoading.postValue(true);
        call_update.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful())
                {
                    isLoading.postValue(false);
//                    Toast.makeText(application, "response est : " + response.body(), Toast.LENGTH_LONG).show();
                    if (response.body() == true)
                    {
                        update(entity_article);
                        Toast.makeText(application, "Modification bien faite", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(application, "Un Problem est subvenu, Veillez contacter votre Ir", Toast.LENGTH_LONG).show();
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
                            Toast.makeText(application, "unknown problem.", Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(application, "Connexion Problem.", Toast.LENGTH_LONG).show();
                isLoading.postValue(false);
            }
        });
    }

    public void deleteArticleOnline(Entity_Article entity_article)
    {
        ArticleRespone articleRespone = new ArticleRespone(entity_article.getCodeArticle(),
                entity_article.getDesignationArticle(), entity_article.getCategorieArticle(), entity_article.getPrixAchat(),
                entity_article.getPrixVente());
        Call<Boolean> call_delete = articleRetrofitRepository.articleConnexion().effacerArticle(articleRespone);
        isLoading.postValue(true);
        call_delete.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful())
                {
                    isLoading.postValue(false);
                    if (response.body() == true)
                    {
                        delete(entity_article);
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
                            Toast.makeText(application, "unknown problem.", Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(application, "Connexion Problem.", Toast.LENGTH_LONG).show();
                isLoading.postValue(false);
            }
        });
    }

    public MutableLiveData<Boolean> getIsLoading()
    {
        return isLoading;
    }

    public int sizeList()
    {
        return getListsize;
    }

    public void insert(Entity_Article entity_article)
    {
        new insertAsyncTask(tArticleDao).execute(entity_article);
    }

    public void update(Entity_Article entity_article)
    {
        new updateAsyncTask(tArticleDao).execute(entity_article);
    }

    public void delete(Entity_Article entity_article)
    {
        new deleteAsyncTask(tArticleDao).execute(entity_article);
    }

    private class insertAsyncTask extends AsyncTask<Entity_Article, Void, Void>
    {
        private tArticleDao tArticleDao;

        private insertAsyncTask(tArticleDao tArticleDao)
        {
            this.tArticleDao = tArticleDao;
        }

        @Override
        protected Void doInBackground(Entity_Article... entity_articles) {
            try {
                this.tArticleDao.insert(entity_articles[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private class updateAsyncTask extends AsyncTask<Entity_Article, Void, Void>
    {
        private tArticleDao tArticleDao;

        private updateAsyncTask(tArticleDao tArticleDao)
        {
            this.tArticleDao = tArticleDao;
        }

        @Override
        protected Void doInBackground(Entity_Article... entity_articles) {
            try {
                this.tArticleDao.update(entity_articles[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private class deleteAsyncTask extends AsyncTask<Entity_Article, Void, Void>
    {
        private tArticleDao tArticleDao;

        private deleteAsyncTask(tArticleDao tArticleDao)
        {
            this.tArticleDao = tArticleDao;
        }

        @Override
        protected Void doInBackground(Entity_Article... entity_articles) {
            try {
                this.tArticleDao.delete(entity_articles[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
