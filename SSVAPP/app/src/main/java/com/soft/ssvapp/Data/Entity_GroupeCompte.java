package com.soft.ssvapp.Data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "tGroupeCompte", indices = {@Index(value = {"GroupeCompte"}, unique = true)})
public class Entity_GroupeCompte {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "IdgroupeCompte")
    private int idgroupeCompte;
    @ColumnInfo(name = "GroupeCompte")
    private int groupeCompte;
    @ColumnInfo(name = "Cadre")
    private int cadre;
    @ColumnInfo(name = "DesignationGroupe")
    private String designationGroupe;

    public Entity_GroupeCompte(int groupeCompte, int cadre, String designationGroupe) {
        this.groupeCompte = groupeCompte;
        this.cadre = cadre;
        this.designationGroupe = designationGroupe;
    }

    public void setIdgroupeCompte(int idgroupeCompte) {
        this.idgroupeCompte = idgroupeCompte;
    }

    public int getIdgroupeCompte() {
        return idgroupeCompte;
    }

    public int getGroupeCompte() {
        return groupeCompte;
    }

    public int getCadre() {
        return cadre;
    }

    public String getDesignationGroupe() {
        return designationGroupe;
    }
}
