package com.soft.ssvapp.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface tArticleDao {

    @Insert
    void insert(Entity_Article entity_article) throws Exception;

    @Update
    void update(Entity_Article entity_article) throws Exception;

    @Delete
    void delete(Entity_Article entity_article) throws Exception;

    @Query("select * from tArticle order by DesegnationArticle ASC")
    LiveData<List<Entity_Article>> getAllArticle();

    @Query("select * from tArticle")
    List<Entity_Article> getAllArticleList();

    @Query("select MAX(IdArticle) from tArticle")
    int getListSize();

    @Query("select * from tArticle where IdArticle = :idArticle")
    Entity_Article entity_article(int idArticle);

    @Query("select * from tArticle where DesegnationArticle LIKE :searchQuery")
    LiveData<List<Entity_Article>> getArticleSearch(String searchQuery);

}
