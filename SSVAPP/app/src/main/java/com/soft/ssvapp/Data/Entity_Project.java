package com.soft.ssvapp.Data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "tProject", indices = {@Index(value = {"CodeProject"}, unique = true)})
public class Entity_Project {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "IdProject")
    private int idProject;
    @ColumnInfo(name = "CodeProject")
    private String codeProject;
    @ColumnInfo(name = "DesignationProject")
    private String designationProject;
    @ColumnInfo(name = "DateDebut")
    private String dateDebut;
    @ColumnInfo(name = "DateFin")
    private String dateFin;
    @ColumnInfo(name = "Lieu")
    private String lieu;
    @ColumnInfo(name = "ChefDuProjet")
    private String chefDuProjet;
    @ColumnInfo(name = "EtatProject")
    private int etat;
    @ColumnInfo(name = "EtatEnvoyer")
    private int etat_envoyer;
    @ColumnInfo(name = "Compte")
    private int compte;
    @ColumnInfo(name = "CompteClient")
    private int compteClient;

    public Entity_Project(String codeProject, String designationProject, String dateDebut, String dateFin,
                          String lieu, String chefDuProjet, int etat, int compte, int compteClient) {
        this.codeProject = codeProject;
        this.designationProject = designationProject;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.lieu = lieu;
        this.chefDuProjet = chefDuProjet;
        this.etat = etat;
        this.compte = compte;
        this.compteClient = compteClient;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    public void setEtat_envoyer(int etat_envoyer)
    {
        this.etat_envoyer = etat_envoyer;
    }

    public int getIdProject() {
        return idProject;
    }

    public String getCodeProject() {
        return codeProject;
    }

    public String getDesignationProject() {
        return designationProject;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public String getLieu() {
        return lieu;
    }

    public String getChefDuProjet() {
        return chefDuProjet;
    }

    public int getEtat() {
        return etat;
    }

    public int getEtat_envoyer()
    {
        return etat_envoyer;
    }

    public int getCompte() {
        return compte;
    }

    public int getCompteClient() {
        return compteClient;
    }
}
