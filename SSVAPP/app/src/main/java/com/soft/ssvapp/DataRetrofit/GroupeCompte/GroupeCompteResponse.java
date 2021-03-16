package com.soft.ssvapp.DataRetrofit.GroupeCompte;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GroupeCompteResponse {

    @SerializedName("groupeCompte")
    @Expose
    private int GroupeCompte;
    @SerializedName("designationGroupe")
    @Expose
    private String DesignationGroupe;
    @SerializedName("cadre")
    @Expose
    private int Cadre;

    public GroupeCompteResponse(int groupeCompte, String designationGroupe, int cadre) {
        GroupeCompte = groupeCompte;
        DesignationGroupe = designationGroupe;
        Cadre = cadre;
    }

    public int getGroupeCompte() {
        return GroupeCompte;
    }

    public String getDesignationGroupe() {
        return DesignationGroupe;
    }

    public int getCadre() {
        return Cadre;
    }
}
