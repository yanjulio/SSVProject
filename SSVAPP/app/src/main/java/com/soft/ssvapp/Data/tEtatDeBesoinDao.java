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
public interface tEtatDeBesoinDao {

    @Insert
    void insert(Entity_Besoin besoin) throws Exception;

    @Update
    void update(Entity_Besoin besoin) throws Exception;

    @Query("UPDATE tEtatDeBesoin SET EtatBesoin=:etatBesoin, DateRequise = :dateRequise " +
            "where IdEtatDuBesoin=:idEtatDeBesoin")
    void update_etatBesoin(int etatBesoin, String dateRequise, int idEtatDeBesoin);

    @Query("UPDATE tEtatDeBesoin SET CodeEtatdeBesoinONline=:codeEtatBesoin, EtatBesoin=:etat, EtatBesoinEnvoyer=:etatEnvoyer " +
            "where IdEtatDuBesoin=:idEtatDeBesoin")
    void update_CodeEtatBesoin(String codeEtatBesoin, int etat, int etatEnvoyer, int idEtatDeBesoin);

    @Query("UPDATE tEtatDeBesoin SET DesignationEtatDeBesion = :designationProject, DateRequise = :dateRequise, " +
            "DateEmision = :dateEmission, DateValidation = :dateValidation, CodeProjectBesoin = :codeProjet, Demandeur = :demandeur," +
            " ValiderPar = :validerPar, EtatBesoinEnvoyer = :etatBesoinEnvoyer, EtatBesoin = :etatBesoin " +
            "WHERE  CodeEtatdeBesoin = :codeEtatBesoin")
    void custom_update(String designationProject, String dateRequise, String dateEmission, String dateValidation,
                       String codeProjet, String demandeur, String validerPar, String codeEtatBesoin, int etatBesoin, int etatBesoinEnvoyer);

    @Delete
    void delete(Entity_Besoin besoin);

    @Query("delete from tEtatDeBesoin where EtatBesoinEnvoyer = 1")
    void deleteCustom(); // attention a ca, ca a effacer lors de l'utilisation ca a effacer toutes mes donnees de la liste

    @Query("delete from tEtatDeBesoin")
    void deleteAllBesoin();

    @Query(("select max(IdEtatDuBesoin) as IdEtatDuBesoin from tEtatDeBesoin"))
    int dernier_etatBesoin();

//    @Query("SELECT tEtatDeBesoin.IdEtatDuBesoin, tEtatDeBesoin.CodeEtatdeBesoin, tEtatDeBesoin.DesignationEtatDeBesion, " +
//        "tEtatDeBesoin.CodeProjectBesoin, tEtatDeBesoin.EtatBesoin, tEtatDeBesoin.Demandeur, tEtatDeBesoin.DateEmision," +
//        "tEtatDeBesoin.DateRequise, tEtatDeBesoin.DateValidation, tEtatDeBesoin.ValiderPar, tEtatDeBesoin.EtatBesoinEnvoyer " +
//        "FROM tEtatDeBesoin INNER JOIN" +
//        " tProject ON tEtatDeBesoin.CodeProjectBesoin = :codeProjet" +
//        " WHERE (tEtatDeBesoin.ValiderPar = '') and tEtatDeBesoin.EtatBesoin = 0")
    @Query("select * from tEtatDeBesoin where ValiderPar = '' and EtatBesoin = 0 and EtatBesoinEnvoyer = 0 " +
            "and CodeProjectBesoin = :codeProjet order by IdEtatDuBesoin desc")
    @Transaction
    LiveData<List<Entity_ProjectWithEntity_Besoin>> getNewBesoin(String codeProjet); // ce codeProjet is from the entity_projet

    @Query("select * from tEtatDeBesoin where " +
            "EtatBesoin = 0 and EtatBesoinEnvoyer = 1 and CodeEtatDeBesoinOnline != '' and " +
            "CodeProjectBesoin = :codeProjet  order by IdEtatDuBesoin desc")
    @Transaction
    LiveData<List<Entity_ProjectWithEntity_Besoin>> getBesoinAvalider(String codeProjet) throws Exception;

    @Query("select * from tEtatDeBesoin where " +
            "EtatBesoin = 0 and EtatBesoinEnvoyer = 1 and CodeEtatDeBesoinOnline != '' and " +
            "CodeProjectBesoin = :codeProjet  order by IdEtatDuBesoin desc")
    LiveData<List<Entity_ProjectWithEntity_Besoin>> getBesoinAvaliderUtilisateur(String codeProjet) throws Exception;

//    @Query("select * from tEtatDeBesoin")
//    @Transaction
//    List<Entity_BesoinWithEntity_DetailBesoin> getBesoinList(); // to see wheter i can update or insert

    @Query("select * from tEtatDeBesoin where ValiderPar = '' and " +
            "EtatBesoin = 0 and EtatBesoinEnvoyer = 1 and CodeEtatDeBesoinOnline != '' and " +
            "CodeProjectBesoin = :codeProjet ")
    @Transaction
    List<Entity_ProjectWithEntity_Besoin> getBesoinAvaliderList(String codeProjet);

    @Query("select * from tEtatDeBesoin where ValiderPar != '' and EtatBesoin = 1 and EtatBesoinEnvoyer = 1 and " +
            "tEtatDeBesoin.CodeProjectBesoin = :codeProjet order by IdEtatDuBesoin desc")
    @Transaction
    LiveData<List<Entity_ProjectWithEntity_Besoin>> getValiderBesoin(String codeProjet);

    @Query("select * from tEtatDeBesoin where ValiderPar != '' and EtatBesoin = 1 and EtatBesoinEnvoyer = 1 and " +
            "tEtatDeBesoin.CodeProjectBesoin = :codeProjet order by IdEtatDuBesoin desc")
    List<Entity_ProjectWithEntity_Besoin> getListValiderBesoin(String codeProjet);

    @Query("select * from tEtatDeBesoin where ValiderPar != '' and EtatBesoin = 1 and EtatBesoinEnvoyer = 1 and " +
            "tEtatDeBesoin.CodeProjectBesoin = :codeProjet")
    @Transaction
    List<Entity_ProjectWithEntity_Besoin> getvaliderBesoinList(String codeProjet);

    @Query("select CodeProject from tProject where EtatProject = 0")
    LiveData<List<String>> getCodeProject();

    @Query("SELECT tEtatDeBesoin.IdEtatDuBesoin, tEtatDeBesoin.CodeEtatdeBesoin, tEtatDeBesoin.DesignationEtatDeBesion, " +
            "tEtatDeBesoin.CodeProjectBesoin, tEtatDeBesoin.EtatBesoin, tEtatDeBesoin.Demandeur, tEtatDeBesoin.DateEmision, \n" +
            "tEtatDeBesoin.DateRequise, tEtatDeBesoin.DateValidation, tEtatDeBesoin.ValiderPar, tDetailEtatBesoin.IdDetailEBOnline, " +
            "tDetailEtatBesoin.Qte, " +
            "tDetailEtatBesoin.Pu, tDetailEtatBesoin.CodeEtatdeBesoinDetails , tDetailEtatBesoin.CodeArticleDetailBesoin, \n" +
            "tDetailEtatBesoin.CodeLigne, tDetailEtatBesoin.LibelleDetail, tDetailEtatBesoin.Sortie, tDetailEtatBesoin.Entree\n" +
            "FROM tEtatDeBesoin INNER JOIN\n" +
            "tDetailEtatBesoin ON tEtatDeBesoin.CodeEtatdeBesoin = tDetailEtatBesoin.CodeEtatdeBesoinDetails\n" +
            "WHERE (tEtatDeBesoin.CodeProjectBesoin = :codeProjet) AND (tEtatDeBesoin.ValiderPar != '')")
    @Transaction
    LiveData<List<Entity_BesoinWithEntity_DetailBesoin>> getEtatBesoinAvecDetailValider(String codeProjet);

}
