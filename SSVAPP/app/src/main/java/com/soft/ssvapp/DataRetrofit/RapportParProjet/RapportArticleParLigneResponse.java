package com.soft.ssvapp.DataRetrofit.RapportParProjet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RapportArticleParLigneResponse {

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
    @SerializedName("codeLigne")
    @Expose
    private String codeLigne;
    @SerializedName("designationLIgne")
    @Expose
    private String designationLIgne;
    @SerializedName("tauxCons")
    @Expose
    private double tauxCons;
    @SerializedName("prevision")
    @Expose
    private double prevision;

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

    public String getCodeLigne() {
        return codeLigne;
    }

    public String getDesignationLIgne() {
        return designationLIgne;
    }

    public double getTauxCons() {
        return tauxCons;
    }

    public double getPrevision() {
        return prevision;
    }
}
