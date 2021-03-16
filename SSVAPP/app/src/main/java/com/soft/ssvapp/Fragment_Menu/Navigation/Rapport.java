package com.soft.ssvapp.Fragment_Menu.Navigation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import com.google.android.material.button.MaterialButton;
import com.soft.ssvapp.Fragment_Menu.Fill_Rapport.Balance;
import com.soft.ssvapp.Fragment_Menu.Fill_Rapport.BalanceGroupeDe;
import com.soft.ssvapp.Fragment_Menu.Fill_Rapport.RapportEtatDeBesoin.EtatBesoinRapport;
import com.soft.ssvapp.Fragment_Menu.Fill_Rapport.RapportSuiviProjet.SuiviProjet;
import com.soft.ssvapp.Login;
import com.soft.ssvapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Rapport extends AppCompatActivity {

    LinearLayout linearLayout_plan_voyage;
    LinearLayout linearLayout_accueil;
    LinearLayout linearLayout_balance;
    LinearLayout linearLayout_clients;
    LinearLayout linearLayout_banque;
    LinearLayout linearLayout_livre_caisse;
    LinearLayout linearLayout_caisse_secondaire;
    LinearLayout linearLayout_etat_besoin;

    SharedPreferences prefs;
    String niveauUtilisateur = null;
    String username = null;
    String password = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.fragment_rapport);
//        getSupportActionBar().hide();

        prefs = getSharedPreferences("lOGIN_USER", MODE_PRIVATE);
        niveauUtilisateur = prefs.getString(Login.NIVEAUUTILISATEUR, "");
        username = prefs.getString(Login.USER_NAME, "");
        password = prefs.getString(Login.PASS_WORD, "");

        linearLayout_plan_voyage = findViewById(R.id.linear_plan_voyage);
        linearLayout_plan_voyage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Rapport.this, SuiviProjet.class));
            }
        });

        linearLayout_accueil = findViewById(R.id.linear_acceuil);
        linearLayout_accueil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(Rapport.this, Accceuil_show.class));
            }
        });

        linearLayout_balance = findViewById(R.id.linear_balance);
        linearLayout_balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Rapport.this, BalanceGroupeDe.class));
            }
        });

        linearLayout_clients = findViewById(R.id.lienar_clients);
        linearLayout_clients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Rapport.this, Balance.class)
                        .putExtra(Balance.TYPE_RAPPORT, "Clients"));
            }
        });

        linearLayout_banque = findViewById(R.id.linear_banque);
        linearLayout_banque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Rapport.this, Balance.class)
                        .putExtra(Balance.TYPE_RAPPORT, "Banque"));
            }
        });

        linearLayout_livre_caisse = findViewById(R.id.linear_livre_de_caisse);
        linearLayout_livre_caisse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Rapport.this, Balance.class)
                        .putExtra(Balance.TYPE_RAPPORT, "Livre caisse"));
            }
        });

        linearLayout_caisse_secondaire = findViewById(R.id.linear_caisse_secondaire);
        linearLayout_caisse_secondaire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Rapport.this, Balance.class)
                        .putExtra(Balance.TYPE_RAPPORT, "Caisse seconaire"));
            }
        });

        linearLayout_etat_besoin = findViewById(R.id.linear_etat_besoin);
        linearLayout_etat_besoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Rapport.this, EtatBesoinRapport.class));
            }
        });

        if (!niveauUtilisateur.equals("ADMIN"))
        {
            if (niveauUtilisateur.trim().equals("UTLILISATEUR"))
            {
                linearLayout_caisse_secondaire.setEnabled(false);
//            linearLayout_etat_besoin.setEnabled(false);
                linearLayout_accueil.setEnabled(false);
                linearLayout_balance.setEnabled(false);
                linearLayout_banque.setEnabled(false);
                linearLayout_clients.setEnabled(false);
                linearLayout_plan_voyage.setEnabled(false);
                linearLayout_livre_caisse.setEnabled(false);
            }
            else
            {
                // pour le caissier
//                linearLayout_caisse_secondaire.setEnabled(false);
//            linearLayout_etat_besoin.setEnabled(false);
                linearLayout_accueil.setEnabled(false);
                linearLayout_balance.setEnabled(false);
                linearLayout_banque.setEnabled(false);
//                linearLayout_clients.setEnabled(false);
                linearLayout_plan_voyage.setEnabled(false);
                linearLayout_livre_caisse.setEnabled(false);
            }
        }

    }

}
