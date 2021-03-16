package com.soft.ssvapp.DataRetrofit.Rapport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RapportEtatBesoinResponse {

    @SerializedName("codeProject")
    @Expose
    private String codeProject;
    @SerializedName("designationProject")
    @Expose
    private String designationProject;
    @SerializedName("codeLigne")
    @Expose
    private String codeLigne;
    @SerializedName("designationLIgne")
    @Expose
    private String designationLIgne;
    @SerializedName("codeEtatdeBesoin")
    @Expose
    private String codeEtatdeBesoin;
    @SerializedName("designationEtatDeBesion")
    @Expose
    private String designationEtatDeBesion;
    @SerializedName("designationEtat")
    @Expose
    private String designationEtat;

    public String getCodeProject() {
        return codeProject;
    }

    public String getDesignationProject() {
        return designationProject;
    }

    public String getCodeLigne() {
        return codeLigne;
    }

    public String getDesignationLIgne() {
        return designationLIgne;
    }

    public String getCodeEtatdeBesoin() {
        return codeEtatdeBesoin;
    }

    public String getDesignationEtatDeBesion() {
        return designationEtatDeBesion;
    }

    public String getDesignationEtat() {
        return designationEtat;
    }
}
