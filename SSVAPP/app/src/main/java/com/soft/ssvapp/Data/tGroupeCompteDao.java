package com.soft.ssvapp.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface tGroupeCompteDao {

    @Insert
    void insert(Entity_GroupeCompte entity_groupeCompte) throws Exception;

    @Query("select * from tGroupeCompte")
    LiveData<List<Entity_GroupeCompte>> getAllGroupeCompte();

    @Query("select * from tGroupeCompte")
    List<Entity_GroupeCompte> getAllGroupeCompteList();
}
