package com.soft.ssvapp.DataRetrofit.RapportParProjet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RapportEBParProjetValideEtDecaisse {

    @SerializedName("codeEtatdeBesoin")
    @Expose
    private String codeEtatdeBesoin;
    @SerializedName("designationEtatDeBesion")
    @Expose
    private String designationEtatDeBesion;
    @SerializedName("dateEmision")
    @Expose
    private String dateEmision;
    @SerializedName("total")
    @Expose
    private double total;
    @SerializedName("sommeDecaisse")
    @Expose
    private double sommeDecaisse;


    public String getCodeEtatdeBesoin() {
        return codeEtatdeBesoin;
    }

    public String getDesignationEtatDeBesion() {
        return designationEtatDeBesion;
    }

    public String getDateEmision() {
        return dateEmision;
    }

    public double getTotal() {
        return total;
    }

    public double getSommeDecaisse() {
        return sommeDecaisse;
    }
}
