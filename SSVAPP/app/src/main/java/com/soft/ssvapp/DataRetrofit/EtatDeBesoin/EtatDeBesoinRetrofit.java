package com.soft.ssvapp.DataRetrofit.EtatDeBesoin;

import com.google.gson.annotations.SerializedName;

public class EtatDeBesoinRetrofit {

    @SerializedName("idEtatDuBesoin")
    private int IdEtatDuBesoin;
    @SerializedName("codeEtatdeBesoin")
    private String CodeEtatdeBesoin;
    @SerializedName("designationEtatDeBesion")
    private String DesignationEtatDeBesion;
    @SerializedName("codeProject")
    private String CodeProject ;
    @SerializedName("codeLigne")
    private String CodeLigne="";
    @SerializedName("etat")
    private int Etat;
    @SerializedName("demandeur")
    private String Demandeur ;
    @SerializedName("validerPar")
    private String ValiderPar ;
    @SerializedName("dateEmision")
    private String DateEmision ;
    @SerializedName("dateRequise")
    private String DateRequise ;
    @SerializedName("dateValidation")
    private String DateValidation;

    public EtatDeBesoinRetrofit(String codeEtatdeBesoin, String designationEtatDeBesion, String codeProject, int etat,
                                String demandeur, String validerPar, String dateEmision, String dateRequise, String dateValidation) {
        CodeEtatdeBesoin = codeEtatdeBesoin;
        DesignationEtatDeBesion = designationEtatDeBesion;
        CodeProject = codeProject;
        Etat = etat;
        Demandeur = demandeur;
        ValiderPar = validerPar;
        DateEmision = dateEmision;
        DateRequise = dateRequise;
        DateValidation = dateValidation;
    }

    public void setCodeLigne(String codeLigne)
    {
        this.CodeLigne = codeLigne;
    }

    public int getIdEtatDuBesoin() {
        return IdEtatDuBesoin;
    }

    public String getCodeEtatdeBesoin() {
        return CodeEtatdeBesoin;
    }

    public String getDesignationEtatDeBesion() {
        return DesignationEtatDeBesion;
    }

    public String getCodeProject() {
        return CodeProject;
    }

    public int getEtat() {
        return Etat;
    }

    public String getDemandeur() {
        return Demandeur;
    }

    public String getValiderPar() {
        return ValiderPar;
    }

    public String getDateEmision() {
        return DateEmision;
    }

    public String getDateRequise() {
        return DateRequise;
    }

    public String getDateValidation() {
        return DateValidation;
    }
}
