package com.soft.ssvapp.DataRetrofit;

public class UtilisateurObjet {
    private int IdUtilisateur;
    private String NomUtilisateur;
    private String MotDePassUtilisateur;
    private String NiveauUtilisateur;

    public UtilisateurObjet(int idUtilisateur, String nomUtilisateur, String motDePassUtilisateur, String niveauUtilisateur) {
        IdUtilisateur = idUtilisateur;
        NomUtilisateur = nomUtilisateur;
        MotDePassUtilisateur = motDePassUtilisateur;
        NiveauUtilisateur = niveauUtilisateur;
    }

    public int getIdUtilisateur() {
        return IdUtilisateur;
    }

    public String getNomUtilisateur() {
        return NomUtilisateur;
    }

    public String getMotDePassUtilisateur() {
        return MotDePassUtilisateur;
    }

    public String getNiveauUtilisateur() {
        return NiveauUtilisateur;
    }

}
