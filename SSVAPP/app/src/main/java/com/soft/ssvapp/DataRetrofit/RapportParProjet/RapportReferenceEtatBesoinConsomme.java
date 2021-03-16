package com.soft.ssvapp.DataRetrofit.RapportParProjet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RapportReferenceEtatBesoinConsomme {

    @SerializedName("codeArticle")
    @Expose
    private String codeArticle;
    @SerializedName("desegnationArticle")
    @Expose
    private String desegnationArticle;
    @SerializedName("qte")
    @Expose
    private double qte;
    @SerializedName("pu")
    @Expose
    private double pu;
    @SerializedName("totalConsommation")
    @Expose
    private double totalConsommation;
    @SerializedName("codeEtatdeBesoin")
    @Expose
    private String codeEtatdeBesoin;
    @SerializedName("designationEtatDeBesion")
    @Expose
    private String designationEtatDeBesion;
    @SerializedName("demandeur")
    @Expose
    private String demandeur;
    @SerializedName("dateEmision")
    @Expose
    private String dateEmision;

    public String getCodeArticle() {
        return codeArticle;
    }

    public String getDesegnationArticle() {
        return desegnationArticle;
    }

    public double getQte() {
        return qte;
    }

    public double getPu() {
        return pu;
    }

    public double getTotalConsommation() {
        return totalConsommation;
    }

    public String getCodeEtatdeBesoin() {
        return codeEtatdeBesoin;
    }

    public String getDesignationEtatDeBesion() {
        return designationEtatDeBesion;
    }

    public String getDemandeur() {
        return demandeur;
    }

    public String getDateEmision() {
        return dateEmision;
    }
}
