package com.soft.ssvapp.Fragment_Menu.Fill_Rapport;

public class Balance_objet {

    int compte;
    String groupe_compte;
    String designation, groupeDesignation;
    double solde;

    public Balance_objet(int compte, String designation, double solde, String groupeDesignation, String groupe_compte) {
        this.compte = compte;
        this.designation = designation;
        this.solde = solde;
        this.groupeDesignation = groupeDesignation;
        this.groupe_compte = groupe_compte;
    }

    public int getCompte() {
        return compte;
    }

    public String getDesignation() {
        return designation;
    }

    public double getSolde() {
        return solde;
    }

    public String getGroupeDesignation()
    {
        return groupeDesignation;
    }

    public String getGroupeCompte()
    {
        return groupe_compte;
    }
}
