package com.soft.ssvapp.Data;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface tOperationDao {

    @Insert
    void insert(Entity_Operation entity_operation) throws Exception;

}
