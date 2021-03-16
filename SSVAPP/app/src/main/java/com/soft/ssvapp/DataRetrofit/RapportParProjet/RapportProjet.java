package com.soft.ssvapp.DataRetrofit.RapportParProjet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RapportProjet {

    @SerializedName("codeProject")
    @Expose
    private String codeProject;
    @SerializedName("designationProject")
    @Expose
    private String designationProject;
    @SerializedName("totalConsommation")
    @Expose
    private double totalConsommation;
    @SerializedName("tOtalPrevision")
    @Expose
    private double tOtalPrevision;
    @SerializedName("tauxCons")
    @Expose
    private double tauxCons;

    public String getCodeProject() {
        return codeProject;
    }

    public String getDesignationProject() {
        return designationProject;
    }

    public double getTotalConsommation() {
        return totalConsommation;
    }

    public double gettOtalPrevision() {
        return tOtalPrevision;
    }

    public double getTauxCons() {
        return tauxCons;
    }
}
