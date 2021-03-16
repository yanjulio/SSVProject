package com.soft.ssvapp.DataRetrofit.Rapport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailRapportResponse {

    @SerializedName("numCompte")
    @Expose
    private int NumCompte ;
    @SerializedName("designationCompte")
    @Expose
    private String DesignationCompte ;
    @SerializedName("numOperation")
    @Expose
    private String NumOperation ;
    @SerializedName("libelle")
    @Expose
    private String Libelle ;
    @SerializedName("dateOperation")
    @Expose
    private String DateOperation ;
    @SerializedName("details")
    @Expose
    private String Details ;
    @SerializedName("qte")
    @Expose
    private double Qte ;
    @SerializedName("debit")
    @Expose
    private double Debit ;
    @SerializedName("credit")
    @Expose
    private double Credit ;
    @SerializedName("solde")
    @Expose
    private double Solde ;

    public int getNumCompte() {
        return NumCompte;
    }

    public String getDesignationCompte() {
        return DesignationCompte;
    }

    public String getNumOperation() {
        return NumOperation;
    }

    public String getLibelle() {
        return Libelle;
    }

    public String getDateOperation() {
        return DateOperation;
    }

    public String getDetails() {
        return Details;
    }

    public double getQte() {
        return Qte;
    }

    public double getDebit() {
        return Debit;
    }

    public double getCredit() {
        return Credit;
    }

    public double getSolde() {
        return Solde;
    }
}
