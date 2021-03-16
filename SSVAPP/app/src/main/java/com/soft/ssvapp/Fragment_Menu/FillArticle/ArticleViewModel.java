package com.soft.ssvapp.Fragment_Menu.FillArticle;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.soft.ssvapp.Data.Entity_Article;

import org.w3c.dom.Entity;

import java.util.List;

public class ArticleViewModel extends AndroidViewModel {

    private ArticleRepository articleRepository;
    private LiveData<List<Entity_Article>> getAllArticle;

    public ArticleViewModel(@NonNull Application application) {
        super(application);
        articleRepository = new ArticleRepository(application);
//        articleRepository.insertOnlineArticle();
        getAllArticle = articleRepository.getGetAllArticle();
    }

    public void refresh_debut()
    {
        articleRepository.insertOnlineArticle();
    }

    public LiveData<List<Entity_Article>> getArticleSearchQuery(String searchQuery)
    {
        return articleRepository.getArticleSearch(searchQuery);
    }

    public LiveData<List<Entity_Article>> getGetAllArticle()
    {
        return getAllArticle;
    }

    public Entity_Article getEntity_article(int idArticle)
    {
        return articleRepository.entity_article(idArticle);
    }

    public int getListSize()
    {
        return articleRepository.sizeList();
    }

    public MutableLiveData<Boolean> getIsLoading()
    {
        return articleRepository.getIsLoading();
    }

    public void insertArticle(Entity_Article entity_article)
    {
        articleRepository.insertArticleOnline(entity_article);
    }

    public void updateArticle(Entity_Article entity_article)
    {
        articleRepository.updateArticleOnline(entity_article);
    }

    public void deleteArticle(Entity_Article entity_article)
    {
        articleRepository.deleteArticleOnline(entity_article);
    }
}
