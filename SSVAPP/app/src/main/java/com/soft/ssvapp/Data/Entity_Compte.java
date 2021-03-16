package com.soft.ssvapp.Data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "tCompte", indices = {@Index(value = {"NumCompte"}, unique = true)})
public class Entity_Compte {

    @ColumnInfo(name = "IdCompte")
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "NumCompte")
    private int numCompte;
    @ColumnInfo(name = "DesignationCompte")
    private String designationCompte;
    @ColumnInfo(name = "GroupeCompte")
    private int groupeCompte;
    @ColumnInfo(name = "Unite")
    private int unite;
    @ColumnInfo(name = "Solde")
    private String solde;

    public Entity_Compte(int numCompte, String designationCompte, int groupeCompte,int unite, String solde) {
        this.numCompte = numCompte;
        this.designationCompte = designationCompte;
        this.groupeCompte = groupeCompte;
        this.unite = unite;
        this.solde = solde;
    }

    public void setId(int id) {
        id = id;
    }

    public int getId() {
        return id;
    }

    public int getNumCompte() {
        return numCompte;
    }

    public String getDesignationCompte() {
        return designationCompte;
    }

    public int getGroupeCompte() {
        return groupeCompte;
    }

    public int getUnite() {
        return unite;
    }

    public String getSolde() {
        return solde;
    }
}
