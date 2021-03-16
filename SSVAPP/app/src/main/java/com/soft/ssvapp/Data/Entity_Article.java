package com.soft.ssvapp.Data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "tArticle", indices = {@Index(value = "CodeArticle", unique = true)})
public class Entity_Article {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "IdArticle")
    private int idArticle;
    @ColumnInfo(name = "CodeArticle")
    private String codeArticle;
    @ColumnInfo(name = "CodeDepartement")
    private String codeDepartement;
    @ColumnInfo(name = "DesegnationArticle")
    private String designationArticle;
    @ColumnInfo(name = "CategorieArticle")
    private int categorieArticle;
    @ColumnInfo(name = "PrixAchat")
    private double prixAchat;
    @ColumnInfo(name = "PrixVente")
    private double prixVente;
    @ColumnInfo(name = "UiniteEnDetaille")
    private String uniteEnDEtaille;
    @ColumnInfo(name = "QteEnDet")
    private double qteEnDet;
    @ColumnInfo(name = "Enstock")
    private double enStock;
    @ColumnInfo(name = "Solde")
    private double solde;
    @ColumnInfo(name = "CompteFournisseur")
    private int comptefournisseur;

    public Entity_Article(String codeArticle, String codeDepartement, String designationArticle, int categorieArticle,
                          double prixAchat, double prixVente,String uniteEnDEtaille,
                          double qteEnDet, double enStock, double solde, int comptefournisseur) {
        this.codeArticle = codeArticle;
        this.codeDepartement = codeDepartement;
        this.designationArticle = designationArticle;
        this.categorieArticle = categorieArticle;
        this.prixAchat = prixAchat;
        this.prixVente = prixVente;
        this.uniteEnDEtaille = uniteEnDEtaille;
        this.qteEnDet = qteEnDet;
        this.enStock = enStock;
        this.solde = solde;
        this.comptefournisseur = comptefournisseur;
    }

    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }

    public int getIdArticle() {
        return idArticle;
    }

    public String getCodeArticle() {
        return codeArticle;
    }

    public String getCodeDepartement() {
        return codeDepartement;
    }

    public String getDesignationArticle() {
        return designationArticle;
    }

    public int getCategorieArticle() {
        return categorieArticle;
    }

    public double getPrixAchat() {
        return prixAchat;
    }

    public double getPrixVente() {
        return prixVente;
    }

    public String getUniteEnDEtaille() {
        return uniteEnDEtaille;
    }

    public double getQteEnDet() {
        return qteEnDet;
    }

    public double getEnStock() {
        return enStock;
    }

    public double getSolde() {
        return solde;
    }

    public int getComptefournisseur() {
        return comptefournisseur;
    }
}
