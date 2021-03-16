package com.soft.ssvapp.Data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "tDetailEtatBesoin",
        indices = {@Index(value = "CodeEtatdeBesoinDetails", unique = false),
                @Index(value = "CodeArticleDetailBesoin", unique = false)},//, @Index(value = "IdDetailEBOnline", unique = true)
        foreignKeys = {@ForeignKey(
                entity = Entity_Besoin.class,
                parentColumns = "CodeEtatdeBesoin",
                childColumns = "CodeEtatdeBesoinDetails",
                onDelete = CASCADE),
                @ForeignKey(
                        entity = Entity_Article.class,
                        parentColumns = "CodeArticle",
                        childColumns = "CodeArticleDetailBesoin",
                        onDelete = ForeignKey.CASCADE)})
public class Entity_DetailBesoin {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "IdDetailEB")
    private int idDetailEB;
    @ColumnInfo(name = "IdDetailEBOnline")
    private int idDetailEBOnline;
    @ColumnInfo(name = "CodeEtatdeBesoinDetails")
    private String codeEtatdeBesoin;
    @ColumnInfo(name = "CodeArticleDetailBesoin")
    private String codeArticle;
    @ColumnInfo(name = "CodeLigne")
    private String codeLigne;
    @ColumnInfo(name = "LibelleDetail")
    private String libelleDetail;
    @ColumnInfo(name = "Qte")
    private double qte;
    @ColumnInfo(name = "Pu")
    private double pu;
    @ColumnInfo(name = "Entree")
    private double entree;
    @ColumnInfo(name = "Sortie")
    private double sortie;

//    private double result = 0;

    public Entity_DetailBesoin(int idDetailEBOnline, String codeEtatdeBesoin, String codeArticle,
                               String codeLigne, String libelleDetail, double qte,
                               double pu, double entree, double sortie) {
        this.idDetailEBOnline = idDetailEBOnline;
        this.codeEtatdeBesoin = codeEtatdeBesoin;
        this.codeArticle = codeArticle;
        this.codeLigne = codeLigne;
        this.libelleDetail = libelleDetail;
        this.qte = qte;
        this.pu = pu;
        this.entree = entree;
        this.sortie = sortie;
    }

    public void setIdDetailEB(int idDetailEB) {
        this.idDetailEB = idDetailEB;
    }

    public int getIdDetailEB() {
        return idDetailEB;
    }

    public int getIdDetailEBOnline() {
        return idDetailEBOnline;
    }

    public String getCodeEtatdeBesoin() {
        return codeEtatdeBesoin;
    }

    public String getCodeLigne() {
        return codeLigne;
    }

    public String getLibelleDetail() {
        return libelleDetail;
    }

    public double getQte() {
        return qte;
    }

    public double getPu() {
        return pu;
    }

    public String getCodeArticle() {
        return codeArticle;
    }

    public double getEntree() {
        return entree;
    }

    public double getSortie() {
        return sortie;
    }

//    public double getTotalDetails()
//    {
//        result += this.pu * this.qte;
//        return result;
//    }
}
