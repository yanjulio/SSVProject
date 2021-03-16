package com.soft.ssvapp.Fragment_Menu.Fill_Rapport.DetailRapport;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Details_Balance_objet {

    private String date, reference, libelle, debit, credit, solde, numOperation;
    private int num_compte;


    public Details_Balance_objet(String date, String reference, String libelle, String debit, String credit,
                                 String solde, String numOperation, int num_compte) {
//        this.date = date;
        this.reference = reference;
        this.libelle = libelle;
        this.debit = debit;
        this.credit = credit;
        this.date = get_date_onyly(date);
        this.solde = solde;
        this.numOperation = numOperation;
        this.num_compte = num_compte;
    }

    private String get_date_onyly(String date)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date dat = null;
        try {
            dat = df. parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        Log.e("This is the date String", df.format(dat));
        return df.format(dat);
    }

    public String getDate() {
        return date;
    }

    public String getReference() {
        return reference;
    }

    public String getLibelle() {
        return libelle;
    }

    public String getDebit() {
        return debit;
    }

    public String getCredit() {
        return credit;
    }

    public String getSolde() {
        return solde;
    }

    public String getNumOperation() {
        return numOperation;
    }

    public int getNum_compte() {
        return num_compte;
    }
}
