package com.soft.ssvapp.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface tLigneDao {

    @Query("select MAX(IdLigne) from tLigne")
    int NombreLigne();

    @Insert
    void insert(Entity_Ligne entity_ligne) throws Exception;

    @Update
    void update(Entity_Ligne entity_ligne) throws Exception;

    @Delete
    void delete(Entity_Ligne entity_ligne);

    @Query("delete from tLigne where CodeProject = :codeProjet")
    void deleteCustom(String codeProjet);

    @Query("select MAX(IdLigne) from tLigne where CodeProject = :codeProjet")
    int NombreLigneParProjet(String codeProjet);

//    @Query("select * from tLigne, tProject where tProject.CodeProject = tLigne.CodeProject " +
//            "and tProject.EtatProject = 0 and tLigne.CodeProject = :codeProjet")
    @Query("select * from tLigne where CodeProject = :codeProjet")
    @Transaction
    LiveData<List<Entity_ProjectWithEntity_Ligne>> getCodeLigne(String codeProjet);

    @Query("select * from tLigne where CodeProject = :codeProjet")
    @Transaction
    List<Entity_ProjectWithEntity_Ligne> getCodeLigneList(String codeProjet);

}
