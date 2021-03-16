package com.soft.ssvapp.DataRetrofit.EtatDeBesoin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EtatDeBesoinRetrofitDetailEB {

    @SerializedName("numCompte")
    @Expose
    private int numCompte;
    @SerializedName("designationCompte")
    @Expose
    private String designationCompte;
    @SerializedName("numOperation")
    @Expose
    private String numOperation;
    @SerializedName("libelle")
    @Expose
    private String libelle;
    @SerializedName("dateOperation")
    @Expose
    private String dateOperation;
    @SerializedName("details")
    @Expose
    private String details;
    @SerializedName("qte")
    @Expose
    private double qte;
    @SerializedName("debit")
    @Expose
    private double debit;
    @SerializedName("credit")
    @Expose
    private double credit;
    @SerializedName("solde")
    @Expose
    private double solde;

    public int getNumCompte() {
        return numCompte;
    }

    public String getDesignationCompte() {
        return designationCompte;
    }

    public String getNumOperation() {
        return numOperation;
    }

    public String getLibelle() {
        return libelle;
    }

    public String getDateOperation() {
        return dateOperation;
    }

    public String getDetails() {
        return details;
    }

    public double getQte() {
        return qte;
    }

    public double getDebit() {
        return debit;
    }

    public double getCredit() {
        return credit;
    }

    public double getSolde() {
        return solde;
    }
}
