package com.soft.ssvapp.DataRetrofit.Rapport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RapportOperationResponse {

    @SerializedName("numCompte")
    @Expose
    private int numCompte;
    @SerializedName("designationCompte")
    @Expose
    private String designationCompte;
    @SerializedName("numOperation")
    @Expose
    private String numOperation;
    @SerializedName("qte")
    @Expose
    private int qte;
    @SerializedName("debit")
    @Expose
    private double debit;
    @SerializedName("credit")
    @Expose
    private double credit;

    public int getNumCompte() {
        return numCompte;
    }

    public String getDesignationCompte() {
        return designationCompte;
    }

    public String getNumOperation() {
        return numOperation;
    }

    public int getQte() {
        return qte;
    }

    public double getDebit() {
        return debit;
    }

    public double getCredit() {
        return credit;
    }
}
