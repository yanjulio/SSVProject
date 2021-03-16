package com.soft.ssvapp.DataRetrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UtilisateurResponse {

    @SerializedName("idUtilisateur")
    @Expose
    private int idUtilisateur;
    @SerializedName("nomUtilisateur")
    @Expose
    private String nomUtilisateur;
    @SerializedName("designationUt")
    @Expose
    private String designationUt;
    @SerializedName("motPasseUtilisateur")
    @Expose
    private String motPasseUtilisateur;
    @SerializedName("niveauUtilisateur")
    @Expose
    private String niveauUtilisateur;
    @SerializedName("fonctionUt")
    @Expose
    private String fonctionUt;
    @SerializedName("serviceAffe")
    @Expose
    private String serviceAffe;
    @SerializedName("compte")
    @Expose
    private int compte;
    @SerializedName("compteDeclaration")
    @Expose
    private int compteDeclaration;
    @SerializedName("actif")
    @Expose
    private int actif;

    public UtilisateurResponse(int idUtilisateur, String nomUtilisateur, String motPasseUtilisateur, String niveauUtilisateur,
                               String fonctionUt, String serviceAffe, int compte) {
        this.idUtilisateur = idUtilisateur;
        this.nomUtilisateur = nomUtilisateur;
        this.motPasseUtilisateur = motPasseUtilisateur;
        this.niveauUtilisateur = niveauUtilisateur;
        this.fonctionUt = fonctionUt;
        this.serviceAffe = serviceAffe;
        this.compte = compte;
    }

    public UtilisateurResponse(int idUtilisateur, String nomUtilisateur, String motPasseUtilisateur, String niveauUtilisateur,
                               String fonctionUt, String serviceAffe, int compte, int actif) {
        this.idUtilisateur = idUtilisateur;
        this.nomUtilisateur = nomUtilisateur;
        this.motPasseUtilisateur = motPasseUtilisateur;
        this.niveauUtilisateur = niveauUtilisateur;
        this.fonctionUt = fonctionUt;
        this.serviceAffe = serviceAffe;
        this.compte = compte;
        this.actif= actif;
    }

    public UtilisateurResponse(String nomUtilisateur, String designationUt, String motPasseUtilisateur, String fonctionUt) {
        this.nomUtilisateur = nomUtilisateur;
        this.designationUt = designationUt;
        this.motPasseUtilisateur = motPasseUtilisateur;
        this.fonctionUt = fonctionUt;
        this.serviceAffe = "";
        this.compte = 0;
        this.compteDeclaration = 0;
        this.actif=0;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public String getMotDePassUtilisateur() {
        return motPasseUtilisateur;
    }

    public String getNiveauUtilisateur() {
        return niveauUtilisateur;
    }

    public String getFonctionUt() {
        return fonctionUt;
    }

    public String getServiceAffe() {
        return serviceAffe;
    }

    public int getCompte() {
        return compte;
    }

    public String getDesignationUt() {
        return designationUt;
    }

    public int getCompteDeclaration() {
        return compteDeclaration;
    }

    public int getActif() {
        return actif;
    }
}
