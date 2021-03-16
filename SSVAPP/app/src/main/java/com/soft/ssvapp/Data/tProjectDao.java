package com.soft.ssvapp.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface tProjectDao {

    @Insert //(onConflict = OnConflictStrategy.REPLACE)
    void insert(Entity_Project project) throws Exception;

    @Update
    void update(Entity_Project project);

    @Query("UPDATE tproject SET DesignationProject = :designationProject, DateDebut = :dateDebut, " +
            "DateFin = :dateFin, Lieu = :lieu, ChefDuProjet = :chefDuProjet WHERE  CodeProject = :codeProjet")
    void custom_update(String codeProjet, String designationProject, String dateDebut, String dateFin, String lieu, String chefDuProjet);

    @Delete
    void delete(Entity_Project project);

    @Query("delete from tProject")
    void deleteAllProject();

    @Query("select * from tProject order by IdProject desc ")
    LiveData<List<Entity_Project>> getOnGoingProjects();

    @Query("select * from tProject")
    List<Entity_Project> getListProjet();

    @Query("select * from tProject where EtatProject != 0 order by IdProject")
    LiveData<List<Entity_Project>> getEndProjjects();

}
