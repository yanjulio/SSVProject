package com.soft.ssvapp.DataRetrofit.Article;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArticleRespone {

    @SerializedName("idArticle")
    @Expose
    private int IdArticle ;
    @SerializedName("codeArticle")
    @Expose
    private String CodeArticle;
    @SerializedName("desegnationArticle")
    @Expose
    private String DesegnationArticle;
    @SerializedName("categorieArticle")
    @Expose
    private int CategorieArticle;
    @SerializedName("prixAchat")
    @Expose
    private double PrixAchat ;
    @SerializedName("prixVente")
    @Expose
    private double PrixVente ;

    public ArticleRespone(String codeArticle, String desegnationArticle, int categorieArticle,
                          double prixAchat,double prixVente) {
        CodeArticle = codeArticle;
        DesegnationArticle = desegnationArticle;
        CategorieArticle = categorieArticle;
        PrixAchat = prixAchat;
        PrixVente = prixVente;
    }

    public int getIdArticle() {
        return IdArticle;
    }

    public String getCodeArticle() {
        return CodeArticle;
    }

    public String getDesegnationArticle() {
        return DesegnationArticle;
    }

    public int getCategorieArticle() {
        return CategorieArticle;
    }

    public double getPrixAchat() {
        return PrixAchat;
    }

    public double getPrixVente() {
        return PrixVente;
    }
}
