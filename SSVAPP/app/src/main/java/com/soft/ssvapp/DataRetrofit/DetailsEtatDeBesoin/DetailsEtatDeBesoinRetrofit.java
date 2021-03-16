package com.soft.ssvapp.DataRetrofit.DetailsEtatDeBesoin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailsEtatDeBesoinRetrofit {

    @SerializedName("idDetailEB")
    @Expose
    private int IdDetailEB ;
    @SerializedName("codeEtatdeBesoin")
    @Expose
    private String CodeEtatdeBesoin;
    @SerializedName("codeArticle")
    @Expose
    private String CodeArticle;
    @SerializedName("codeLigne")
    @Expose
    private String CodeLigne ;
    @SerializedName("desegnationArticle")
    @Expose
    private String designationArticle;
    @SerializedName("libelleDetail")
    @Expose
    private String LibelleDetail ;
    @SerializedName("qte")
    @Expose
    private double Qte ;
    @SerializedName("pu")
    @Expose
    private double Pu ;
    @SerializedName("sortie")
    @Expose
    private double Sortie;
    @SerializedName("entree")
    @Expose
    private double Entree;

    public DetailsEtatDeBesoinRetrofit(String codeEtatde, String codeArticle, String codeLigne, String libelleDetail, double qte,
                                       double pu, double sortie, double entree) {
        CodeEtatdeBesoin = codeEtatde;
        CodeArticle = codeArticle;
        CodeLigne = codeLigne;
        LibelleDetail = libelleDetail;
        Qte = qte;
        Pu = pu;
        Sortie = sortie;
        Entree = entree;
    }

    public void setIdDetailEB(int idDetailEB)
    {
        this.IdDetailEB = idDetailEB;
    }

    public int getIdDetailEB() {
        return IdDetailEB;
    }

    public String getCodeEtatdeBesoin() {
        return CodeEtatdeBesoin;
    }

    public String getCodeLigne() {
        return CodeLigne;
    }

    public String getDesignationArticle() {
        return designationArticle;
    }

    public String getLibelleDetail() {
        return LibelleDetail;
    }

    public double getQte() {
        return Qte;
    }

    public double getPu() {
        return Pu;
    }

    public String getCodeArticle() {
        return CodeArticle;
    }

    public double getSortie() {
        return Sortie;
    }

    public double getEntree() {
        return Entree;
    }
}
