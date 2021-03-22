package com.soft.ssvapp.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface tCompteDao {

    @Insert
    void insert(Entity_Compte entity_compte) throws Exception;

    @Update
    void update(Entity_Compte entity_compte) throws Exception;

    @Query("select * from tCompte")
    LiveData<List<Entity_Compte>> getAllCompte();

    @Query("select * from tCompte")
    List<Entity_Compte> getAllCompteList();

    @Query("select * from tCompte where GroupeCompte = 572")
    LiveData<List<Entity_Compte>> getRavitaementCompte();

    @Query("select * from tCompte where GroupeCompte = 521")
    LiveData<List<Entity_Compte>> getApprovisionementCompte();

    @Query("select * from tCompte where GroupeCompte = 571")
    LiveData<List<Entity_Compte>> getProjetCompte();

    @Query("select * from tCompte where NumCompte = :numCompte")
    LiveData<List<Entity_Compte>> getPayements(int numCompte);

    // LES REQUETTES POUR LES POSTES
    // si ADMIN
    @Query("select * from tCompte where GroupeCompte = 572")
    LiveData<List<Entity_Compte>> getPosteAdminDebitCompte();

    // si UTILISATEUR POSTE
    @Query("select * from tCompte where NumCompte = :numCompte")
    LiveData<List<Entity_Compte>> getPosteUserDebitCompte(int numCompte);

    // pour le credit
    @Query("select * from tCompte where NumCompte = 701001")
    LiveData<List<Entity_Compte>> getPosteCreditCompte();
}
