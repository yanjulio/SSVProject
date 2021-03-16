package com.soft.ssvapp.DataRetrofit.Compte;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompteReponse {
    @SerializedName("numCompte")
    @Expose
    private int NumCompte ;
    @SerializedName("designationCompte")
    @Expose
    private String DesignationCompte ;
    @SerializedName("groupeCompte")
    @Expose
    private int GroupeCompte ;
    @SerializedName("unite")
    @Expose
    private int Unite ;
    @SerializedName("solde")
    @Expose
    private String Solde ;

    public CompteReponse(String designationCompte, int groupeCompte, int unite, String solde) {
        DesignationCompte = designationCompte;
        GroupeCompte = groupeCompte;
        Unite = unite;
        Solde = solde;
    }

    public void setNumCompte(int numCompte) {
        NumCompte = numCompte;
    }

    public int getNumCompte() {
        return NumCompte;
    }

    public String getDesignationCompte() {
        return DesignationCompte;
    }

    public int getGroupeCompte() {
        return GroupeCompte;
    }

    public int getUnite() {
        return Unite;
    }

    public String getSolde() {
        return Solde;
    }
}
