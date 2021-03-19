package com.soft.ssvapp.DataRetrofit.PostControle;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PosteControleResponse {

//    @SerializedName("")
    private String designation;
//    @SerializedName("")
    private double montant;
//    @SerializedName("")
    private int matricule_operation;
//    @SerializedName("")
    private int compte;
//    @SerializedName("")
    private String date;

    public PosteControleResponse(String designation, double montant, int matricule_operation, int compte, String date) {
        this.designation = designation;
        this.montant = montant;
        this.matricule_operation = matricule_operation;
        this.compte = compte;
        this.date = date;
    }

    public static ArrayList<PosteControleResponse>  list = new ArrayList<>();

    public String getDesignation() {
        return designation;
    }

    public double getMontant() {
        return montant;
    }

    public int getMatricule_operation() {
        return matricule_operation;
    }

    public int getCompte() {
        return compte;
    }

    public String getDate() {
        return date;
    }

    public void setList(ArrayList<PosteControleResponse> list) {
        this.list = list;
    }

    public static ArrayList<PosteControleResponse> getList() {
        return list;
    }
}
