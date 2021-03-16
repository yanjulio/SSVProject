package com.soft.ssvapp.DataRetrofit.RapportParProjet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RapportGrandLivre {

    @SerializedName("numCompte")
    @Expose
    private int numCompte;
    @SerializedName("designationCompte")
    @Expose
    private String designationCompte;
    @SerializedName("debit")
    @Expose
    private double debit;
    @SerializedName("credit")
    @Expose
    private double credit;
    @SerializedName("solde")
    @Expose
    private double solde;
    @SerializedName("soldeInitial")
    @Expose
    private double soldeInitial;

    public int getNumCompte() {
        return numCompte;
    }

    public String getDesignationCompte() {
        return designationCompte;
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

    public double getSoldeInitial() {
        return soldeInitial;
    }
}
