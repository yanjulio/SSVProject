package com.soft.ssvapp.DataRetrofit.Operation;

import com.google.gson.annotations.SerializedName;

public class OperationRetrofit {

    @SerializedName("numOpSource")
    private int NumOpSource ;
    @SerializedName("numOperation")
    private String NumOperation;
    @SerializedName("libelle")
    private String Libelle ;
    @SerializedName("nomUt")
    private String NomUt;
    @SerializedName("dateOp")
    private String DateOperation ;
    @SerializedName("dateSysteme")
    private String DateSysteme ;
    @SerializedName("codeEtatdeBesoin")
    private String CodeEtatdeBesoin ;

    public OperationRetrofit(String numOperation, String libelle, String nomUt,
                             String dateOperation, String dateSysteme, String codeEtatdeBesoin) {
        NumOperation = numOperation;
        Libelle = libelle;
        DateOperation = dateOperation;
        DateSysteme = dateSysteme;
        NomUt = nomUt;
        CodeEtatdeBesoin = codeEtatdeBesoin;
    }

    public void setNumOpSource(int numOpSource) {
        NumOpSource = numOpSource;
    }

    public int getNumOpSource() {
        return NumOpSource;
    }

    public String getNumOperation() {
        return NumOperation;
    }

    public String getLibelle() {
        return Libelle;
    }

    public String getNomUt() {
        return NomUt;
    }

    public String getDateOperation() {
        return DateOperation;
    }

    public String getDateSysteme() {
        return DateSysteme;
    }

    public String getCodeEtatdeBesoin() {
        return CodeEtatdeBesoin;
    }
}
