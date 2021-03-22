package com.soft.ssvapp.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.exceptions.Exceptions;

@Dao
public interface tMvtCompteDao {

    @Insert
    void insert(Entity_MvtCompte entity_mvtCompte) throws Exception;

    @Query("select * from tMvtCompte")
    LiveData<List<Entity_MvtCompte>> getRecentmvt();
}
