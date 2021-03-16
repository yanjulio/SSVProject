package com.soft.ssvapp.Data;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface tMvtCompteDao {

    @Insert
    void insert(Entity_MvtCompte entity_mvtCompte) throws Exception;
}
