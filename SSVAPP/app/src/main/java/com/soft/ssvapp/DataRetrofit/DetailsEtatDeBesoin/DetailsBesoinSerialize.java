package com.soft.ssvapp.DataRetrofit.DetailsEtatDeBesoin;

import java.io.Serializable;

public class DetailsBesoinSerialize implements Serializable {

    private int IdDetailEB ;
    private int idDetailEBOnline;
    private String CodeEtatdeBesoin;
    private String CodeArticle;
    private String CodeLigne ;
    private String LibelleDetail ;
    private double Qte ;
    private double Pu ;
    private double Sortie;
    private double Entree;

    public DetailsBesoinSerialize(int idDetailEBOnline, String codeEtatde, String codeArticle, String codeLigne, String libelleDetail,
                                  double qte, double pu, double sortie, double entree) {
        this.idDetailEBOnline = idDetailEBOnline;
        CodeEtatdeBesoin = codeEtatde;
        CodeArticle = codeArticle;
        CodeLigne = codeLigne;
        LibelleDetail = libelleDetail;
        Qte = qte;
        Pu = pu;
        Sortie = sortie;
        Entree = entree;
    }

    public int getIdDetailEB() {
        return IdDetailEB;
    }

    public int getIdDetailEBOnline() {
        return idDetailEBOnline;
    }

    public String getCodeEtatdeBesoin() {
        return CodeEtatdeBesoin;
    }

    public String getCodeLigne() {
        return CodeLigne;
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
