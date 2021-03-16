package com.soft.ssvapp.DataRetrofit.ProjetRetrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProjetRetrofit {

    @SerializedName("idProject")
    @Expose
    private   int IdProject ;
    @SerializedName("codeProject")
    @Expose
    private String CodeProject;
    @SerializedName("designationProject")
    @Expose
    private String DesignationProject ;
    @SerializedName("lieu")
    @Expose
    private String Lieu ;
    @SerializedName("dateDebut")
    @Expose
    private String DateDebut ;
    @SerializedName("dateFin")
    @Expose
    private String DateFin ;
    @SerializedName("chefDuProjet")
    @Expose
    private String ChefDuProjet ;
    @SerializedName("etat")
    @Expose
    private int Etat ;
    @SerializedName("compte")
    @Expose
    private int Compte;
    @SerializedName("compteClient")
    @Expose
    private int compteClient;

    private String code = "je suis vide pour le moment projet";

    public ProjetRetrofit(String codeProject, String designationProject, String lieu, String dateDebut,
                          String dateFin, String chefDuProjet, int etat, int compte) {
        CodeProject = codeProject;
        DesignationProject = designationProject;
        Lieu = lieu;
        DateDebut = dateDebut;
        DateFin = dateFin;
        ChefDuProjet = chefDuProjet;
        Etat = etat;
        Compte = compte;
    }

    public int getIdProject() {
        return IdProject;
    }

    public String getCodeProject() {
        return CodeProject;
    }

    public String getDesignationProject() {
        return DesignationProject;
    }

    public String getLieu() {
        return Lieu;
    }

    public String getDateDebut() {
        return DateDebut;
    }

    public String getDateFin() {
        return DateFin;
    }

    public String getChefDuProjet() {
        return ChefDuProjet;
    }

    public int getEtat() {
        return Etat;
    }

    public void setResponse(String code)
    {
        this.code = code;
    }

    public String getResponse()
    {
        return code;
    }

    public int getCompte() {
        return Compte;
    }

    public int getCompteClient() {
        return compteClient;
    }
}
