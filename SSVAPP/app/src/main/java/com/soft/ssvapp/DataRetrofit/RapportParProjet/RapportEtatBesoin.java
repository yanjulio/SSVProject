package com.soft.ssvapp.DataRetrofit.RapportParProjet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RapportEtatBesoin {

    @SerializedName("codeProject")
    @Expose
    private String codeProject;
    @SerializedName("sommeEbValide")
    @Expose
    private double sommeEbValide;
    @SerializedName("sommeEbDecaisse")
    @Expose
    private double sommeEbDecaisse;
    @SerializedName("sommeEbEntenteDeValidation")
    @Expose
    private int sommeEbEntenteDeValidation;

    public String getCodeProject() {
        return codeProject;
    }

    public double getSommeEbValide() {
        return sommeEbValide;
    }

    public double getSommeEbDecaisse() {
        return sommeEbDecaisse;
    }

    public int getSommeEbEntenteDeValidation() {
        return sommeEbEntenteDeValidation;
    }
}
