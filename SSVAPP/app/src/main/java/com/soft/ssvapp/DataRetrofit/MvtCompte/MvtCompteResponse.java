package com.soft.ssvapp.DataRetrofit.MvtCompte;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MvtCompteResponse {

    @SerializedName("idMouvement")
    @Expose
    private int IdMouvement;
    @SerializedName("numCompte")
    @Expose
    private String NumCompte;
    @SerializedName("numOperation")
    @Expose
    private String NumOperation;
    @SerializedName("details")
    @Expose
    private String Details ;
    @SerializedName("qte")
    @Expose
    private double Qte;
    @SerializedName("entree")
    @Expose
    private double Entree ;
    @SerializedName("sortie")
    @Expose
    private double Sortie ;
    @SerializedName("codeProject")
    @Expose
    private String CodeProject;

    public MvtCompteResponse(String numCompte, String numOperation, String details, double qte, double entree,
                             double sortie, String codeProject) {
        NumCompte = numCompte;
        NumOperation = numOperation;
        Details = details;
        Qte = qte;
        Entree = entree;
        Sortie = sortie;
        CodeProject = codeProject;
    }

    public int getIdMouvement() {
        return IdMouvement;
    }

    public String getNumCompte() {
        return NumCompte;
    }

    public String getNumOperation() {
        return NumOperation;
    }

    public String getDetails() {
        return Details;
    }

    public double getQte() {
        return Qte;
    }

    public double getEntree() {
        return Entree;
    }

    public double getSortie() {
        return Sortie;
    }

    public String getCodeProject() {
        return CodeProject;
    }
}
