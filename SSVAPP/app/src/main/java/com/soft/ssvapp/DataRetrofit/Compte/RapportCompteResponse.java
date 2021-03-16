package com.soft.ssvapp.DataRetrofit.Compte;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RapportCompteResponse {

    @SerializedName("numCompte")
    @Expose
    private int numCompte;
    @SerializedName("designationCompte")
    @Expose
    private String designationCompte;
    @SerializedName("groupeCompte")
    @Expose
    private int groupeCompte;
    @SerializedName("unite")
    @Expose
    private int unite;
    @SerializedName("solde")
    @Expose
    private String solde;

    public RapportCompteResponse(int numCompte, String designationCompte, int groupeCompte, int unite, String solde) {
        this.numCompte = numCompte;
        this.designationCompte = designationCompte;
        this.groupeCompte = groupeCompte;
        this.unite = unite;
        this.solde = solde;
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
