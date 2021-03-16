package com.soft.ssvapp.DataRetrofit.Rapport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RapportResponse {

    @SerializedName("numCompte")
    @Expose
    private int NumeCompte;
    @SerializedName("designationCompte")
    @Expose
    private String DesignationCompte;
    @SerializedName("groupeCompte")
    @Expose
    private String GroupeCompte;
    @SerializedName("designationGroupe")
    @Expose
    private String DesignationGroupe;
    @SerializedName("nombre")
    @Expose
    private int Nombre;
    @SerializedName("solde")
    @Expose
    private double Solde;
    @SerializedName("dateOperation")
    @Expose
    private String DateOperation;
    @SerializedName("teleEnt")
    @Expose
    private String TeleEnt;

    public int getNumeCompte() {
        return NumeCompte;
    }

    public String getDesignationCompte() {
        return DesignationCompte;
    }

    public String getGroupeCompte() {
        return GroupeCompte;
    }

    public String getDesignationGroupe() {
        return DesignationGroupe;
    }

    public int getNombre() {
        return Nombre;
    }

    public double getSolde() {
        return Solde;
    }

    public String getDateOperation() {
        return DateOperation;
    }

    public String getTeleEnt() {
        return TeleEnt;
    }
}
