package com.soft.ssvapp.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface tDetailBesoinDao {

    @Insert
    void insert(Entity_DetailBesoin entity_besoin) throws Exception;

//    @Update
//    void update(Entity_DetailBesoin entity_detailBesoin);

    @Query("update tDetailEtatBesoin set LibelleDetail = :libelleDetail, Qte = :qte, Pu = :pu where IdDetailEB = :idDetailEB")
    void update(String libelleDetail, double qte, double pu, int idDetailEB) throws Exception;

//    @Query("UPDATE tDetailEtatBesoin SET CodeEtatdeBesoin = :codeEtaBesoin where CodeEtatdeBesoin = :codeEtaBesoin")
//    int update_codeEtaBesoin(String codeEtaBesoin);

    @Query("select Max(IdDetailEB) from tDetailEtatBesoin")
    int dernierDetailBesoin();

    @Delete
    void delete(Entity_DetailBesoin entity_besoin) throws Exception;

    @Query("delete from tDetailEtatBesoin")
    void deleteAll();

    @Query("SELECT tDetailEtatBesoin.IdDetailEB, tDetailEtatBesoin.IdDetailEBOnline, tDetailEtatBesoin.CodeEtatdeBesoinDetails, " +
            "tDetailEtatBesoin.CodeArticleDetailBesoin, \n" +
            "tDetailEtatBesoin.CodeLigne, tDetailEtatBesoin.LibelleDetail, \n" +
            " tDetailEtatBesoin.Qte, tDetailEtatBesoin.Pu, tDetailEtatBesoin.Sortie, tDetailEtatBesoin.Entree \n" +
            "FROM tDetailEtatBesoin where tDetailEtatBesoin.CodeEtatdeBesoinDetails = :codeEtatdeBesoin")
    @Transaction
    LiveData<List<Entity_BesoinWithEntity_DetailBesoin>> getDetailBesoin(String codeEtatdeBesoin);

    @Query("SELECT tDetailEtatBesoin.IdDetailEB, tDetailEtatBesoin.IdDetailEBOnline, tDetailEtatBesoin.CodeEtatdeBesoinDetails, " +
            "tDetailEtatBesoin.CodeArticleDetailBesoin, \n" +
            "tDetailEtatBesoin.CodeLigne, tDetailEtatBesoin.LibelleDetail, \n" +
            " tDetailEtatBesoin.Qte, tDetailEtatBesoin.Pu, tDetailEtatBesoin.Sortie, tDetailEtatBesoin.Entree " +
            "FROM tDetailEtatBesoin where tDetailEtatBesoin.CodeEtatdeBesoinDetails = :codeEtatdeBesoin")
    @Transaction
    List<Entity_BesoinWithEntity_DetailBesoin> getDeatilsBesoinList(String codeEtatdeBesoin);

    @Query("SELECT tDetailEtatBesoin.IdDetailEB, tDetailEtatBesoin.IdDetailEBOnline, tDetailEtatBesoin.CodeEtatdeBesoinDetails, " +
            "tDetailEtatBesoin.CodeArticleDetailBesoin, \n" +
            "tDetailEtatBesoin.CodeLigne, tDetailEtatBesoin.LibelleDetail, tDetailEtatBesoin.Qte, tDetailEtatBesoin.Pu, \n" +
            "tDetailEtatBesoin.Sortie, tDetailEtatBesoin.Entree, tArticle.DesegnationArticle, tArticle.CodeArticle, " +
            "tArticle.CompteFournisseur, tArticle.CategorieArticle, tArticle.PrixAchat, tArticle.PrixVente, \n" +
            "tArticle.QteEnDet, tArticle.Enstock, tArticle.Solde, tArticle.UiniteEnDetaille, " +
            "tArticle.CodeDepartement, tArticle.IdArticle\n" +
            "FROM tDetailEtatBesoin INNER JOIN\n" +
            "tArticle ON tDetailEtatBesoin.CodeArticleDetailBesoin = tArticle.CodeArticle" +
            " where tDetailEtatBesoin.CodeEtatdeBesoinDetails = :codeEtatdeBesoin")
    @Transaction
    List<Entity_DetailBesoinWithEntity_Article> getDeatilsBesoinArticleList(String codeEtatdeBesoin);

    @Query("SELECT tDetailEtatBesoin.IdDetailEB, tDetailEtatBesoin.IdDetailEBOnline, tDetailEtatBesoin.CodeEtatdeBesoinDetails, " +
            "tDetailEtatBesoin.CodeArticleDetailBesoin, \n" +
            "tDetailEtatBesoin.CodeLigne, tDetailEtatBesoin.LibelleDetail, tDetailEtatBesoin.Qte, tDetailEtatBesoin.Pu, \n" +
            "tDetailEtatBesoin.Sortie, tDetailEtatBesoin.Entree, tArticle.DesegnationArticle, tArticle.CodeArticle, " +
            "tArticle.CompteFournisseur, tArticle.CategorieArticle, tArticle.PrixAchat, tArticle.PrixVente, \n" +
            "tArticle.QteEnDet, tArticle.Enstock, tArticle.Solde, tArticle.UiniteEnDetaille, " +
            "tArticle.CodeDepartement, tArticle.IdArticle\n" +
            "FROM tDetailEtatBesoin INNER JOIN\n" +
            "tArticle ON tDetailEtatBesoin.CodeArticleDetailBesoin = tArticle.CodeArticle" +
            " where tDetailEtatBesoin.CodeEtatdeBesoinDetails = :codeEtatdeBesoin")
    @Transaction
    LiveData<List<Entity_DetailBesoinWithEntity_Article>> getDetailBesoinArticle(String codeEtatdeBesoin);

}
