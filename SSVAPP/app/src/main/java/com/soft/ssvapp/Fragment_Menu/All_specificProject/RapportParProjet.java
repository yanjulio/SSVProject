package com.soft.ssvapp.Fragment_Menu.All_specificProject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.soft.ssvapp.DataRetrofit.RapportParProjet.RapportEtatBesoin;
import com.soft.ssvapp.DataRetrofit.RapportParProjet.RapportGrandLivre;
import com.soft.ssvapp.DataRetrofit.RapportParProjet.RapportProjet;
import com.soft.ssvapp.Fragment_Menu.All_specificProject.RapportParProjetArticles.RapportArticle;
import com.soft.ssvapp.Fragment_Menu.All_specificProject.RapportParProjetData.RapportParProjetViewModel;
import com.soft.ssvapp.Fragment_Menu.All_specificProject.RapportPartProjetEtatBesoin.RapportEBParProjet;
import com.soft.ssvapp.Fragment_Menu.Fill_Rapport.DetailRapport.DetailBalance;
import com.soft.ssvapp.Fragment_Menu.Fill_Rapport.RapportSuiviProjet.DetailSuiviProjet;
import com.soft.ssvapp.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import static java.util.Calendar.getInstance;

public class RapportParProjet extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    public static final String CODE_PROJET = "code_projet";
    public static final String COMPTE_PROJET = "compte_projet";
    public static final String COMPTE_CLIENT = "compte_client";
    String code_projet = "";
    int compte_projet=0;
    int compte_client=0;
    String designation_grand_livre ="";
    String designation_client ="";

    RapportParProjetViewModel rapportParProjetViewModel;
//    MergerQueriesViewModel mergerQueriesViewModel;

    ProgressBar progressBar_projet;
    TextView textView_desingation_projet, textView_consommation_projet, textView_prevision, textView_taux_consommation;
    LinearLayout linearLayout_projet_titre;

    // pour le grand livre de caisse
    TextView textView_solde_initial, textView_entree, textView_sortie, textView_solde;

    // pour les clients
    TextView textView_solde_client_initial, textView_client_entree, textView_client_sortie, textView_client_solde;

    //pour les etatBesoin
//    TextView textView_nbre_EB_envoye;
    TextView textView_somme_EB_envoye, textView_somme_EB_valide, textView_somme_EB_servis,
            textView_somme_EB_reste_a_servir;
    LinearLayout linearLayout_EB_par_projet, linearLayout_client_par_projet, linearLayout_livre_caisse_par_projet;

    ImageView imageView_start;
    TextView textView_start;
    String text_start = null;

    private int mYear, mMoth, mDay_of_Moth;
    private int annee, mois, jour;
    DatePickerDialog datePickerDialog;
    Calendar calendar;
    public static String date;

    ImageView imageView_end;
    TextView textView_end;
    String text_end = null;

    int click = 2;

    RapportGrandLivre rapportGrandLivre_local = new RapportGrandLivre();
    RapportGrandLivre rapportClient_local = new RapportGrandLivre(); // pour les clients vu qu'ils se ressemblent
    RapportEtatBesoin rapportEtatBesoin_local = new RapportEtatBesoin();
    double rapportEtatBesoin_local_somme_non_valide = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rapport_par_projet);
        code_projet = getIntent().getStringExtra(CODE_PROJET);
        compte_projet = getIntent().getIntExtra(COMPTE_PROJET, 0);
        compte_client = getIntent().getIntExtra(COMPTE_CLIENT, 0);

        // pour le projet
        progressBar_projet = findViewById(R.id.progress_bar_rapport_project);
        textView_desingation_projet = findViewById(R.id.designation_par_projet);
        textView_consommation_projet = findViewById(R.id.consommation_par_projet);
        textView_prevision = findViewById(R.id.prevision_par_projet);
        textView_taux_consommation = findViewById(R.id.taux_consommation_par_projet);
        linearLayout_projet_titre = findViewById(R.id.linear_project_titre);
        linearLayout_projet_titre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RapportParProjet.this, DetailSuiviProjet.class)
                        .putExtra(DetailSuiviProjet.CODE_PROJET, code_projet)
                        .putExtra(DetailSuiviProjet.DESIGNATION_PROJET, textView_desingation_projet.getText().toString()));
            }
        });

        // pour les EB
        textView_solde_initial = findViewById(R.id.solde_initial_par_projet);
        textView_entree = findViewById(R.id.entree_par_projet);
        textView_sortie = findViewById(R.id.sortie_par_projet);
        textView_solde = findViewById(R.id.solde_par_projet);

        //pour les clients
        textView_solde_client_initial = findViewById(R.id.solde_initial_client_par_projet);
        textView_client_entree = findViewById(R.id.entree_client_par_projet);
        textView_client_sortie = findViewById(R.id.sortie_client_par_projet);
        textView_client_solde = findViewById(R.id.solde_client_par_projet);

        // pour les etats de besoins
//        textView_nbre_EB_envoye = findViewById(R.id.textView_nbre_EB_envoye);
        textView_somme_EB_envoye = findViewById(R.id.textView_somme_EB_envoye);
        textView_somme_EB_valide = findViewById(R.id.textView_somme_EB_valide);
        textView_somme_EB_servis = findViewById(R.id.textView_somme_EB_servis);
        textView_somme_EB_reste_a_servir = findViewById(R.id.textView_somme_EB_reste_a_servis);

        MaterialButton materialButton_article = findViewById(R.id.article_rapport);
        materialButton_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RapportParProjet.this, RapportArticle.class)
                        .putExtra(RapportArticle.CODE_PRJET, code_projet)
                        .putExtra(RapportArticle.DESIGNATION_PROJET, textView_desingation_projet.getText().toString()));
            }
        });

        rapportParProjetViewModel = ViewModelProviders.of(RapportParProjet.this).get(RapportParProjetViewModel.class);
        rapportParProjetViewModel.getRapportProjet(code_projet).observe(RapportParProjet.this, new Observer<RapportProjet>() {
            @Override
            public void onChanged(RapportProjet rapportProjet) {
                textView_desingation_projet.setText("" + rapportProjet.getDesignationProject());
                textView_consommation_projet.setText("" + rapportProjet.getTotalConsommation());
                textView_prevision.setText("" + rapportProjet.gettOtalPrevision());
                textView_taux_consommation.setText(rapportProjet.getTauxCons() + "%");
            }
        });

        rapportParProjetViewModel.getIsLoading().observe(RapportParProjet.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean)
                {
                    progressBar_projet.setVisibility(View.VISIBLE);
                }
                else
                {
                    progressBar_projet.setVisibility(View.GONE);
                }
            }
        });

        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd "); // 'T' HH:mm:ss z"

        String current_time = df.format(to_number_format(c)); // for the end time
        Log.e("current_time ", current_time);

        calendar = getInstance();

        mYear = calendar.get(YEAR);
        mMoth = calendar.get(MONTH);
        mDay_of_Moth = calendar.get(DAY_OF_MONTH);

        textView_start = findViewById(R.id.text_date1_par_projet);
        String start_time = minus_30(c, df); // start time, we have change the thirty days to one
        textView_start.setText("" + start_time);
        imageView_start = findViewById(R.id.imageVie_pick_date1_par_projet);
        imageView_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click_date_image();
                click = 1;
            }
        });

        textView_end = findViewById(R.id.text_date2_par_projet);
        textView_end.setText("" + current_time);
        imageView_end = findViewById(R.id.imageVie_pick_date2_par_projet);
        imageView_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click_date_image();
                click = 2;
            }
        });

        if (!textView_start.getText().toString().equals(null) && !textView_end.getText().toString().equals(null))
        {
            GrangLivre(compte_projet, textView_start.getText().toString(), textView_end.getText().toString());
            Clients(compte_client, textView_start.getText().toString(), textView_end.getText().toString());
            rapport_EB(code_projet, textView_start.getText().toString(), textView_end.getText().toString());
            rapport_Somme_EB_NonValide(code_projet, textView_start.getText().toString(), textView_end.getText().toString());
        }

        linearLayout_EB_par_projet = findViewById(R.id.linear_EB_par_project);
        linearLayout_EB_par_projet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RapportParProjet.this, RapportEBParProjet.class)
                        .putExtra(RapportEBParProjet.CODE_PROEJET, code_projet));
            }
        });

        linearLayout_client_par_projet = findViewById(R.id.linear_client_par_projet);
        linearLayout_client_par_projet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RapportParProjet.this, DetailBalance.class)
                        .putExtra(DetailBalance.NUM_COMPTE, compte_client)
                        .putExtra(DetailBalance.DESIGANTION, designation_client));
            }
        });
        linearLayout_livre_caisse_par_projet = findViewById(R.id.linear_livre_caisse_par_projet);
        linearLayout_livre_caisse_par_projet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RapportParProjet.this, DetailBalance.class)
                        .putExtra(DetailBalance.NUM_COMPTE, compte_projet)
                        .putExtra(DetailBalance.DESIGANTION, designation_grand_livre));
            }
        });

//        mergerQueriesViewModel = new ViewModelProvider(RapportParProjet.this).get(MergerQueriesViewModel.class);
//        mergerQueriesViewModel.init_mergerQueries(
//                code_projet, compte_client, compte_projet, "2020-08-30", "2020-09-30");
//        mergerQueriesViewModel.getRapportEBNonValide().observe(RapportParProjet.this, new Observer<Double>() {
//            @Override
//            public void onChanged(Double aDouble) {
//                Toast.makeText(RapportParProjet.this, "Valeur Non EB : " + aDouble + "", Toast.LENGTH_LONG).show();
//            }
//        });
//        mergerQueriesViewModel.getRapportGrandLivre().observe(RapportParProjet.this, new Observer<RapportGrandLivre>() {
//            @Override
//            public void onChanged(RapportGrandLivre rapportGrandLivre) {
//                Toast.makeText(RapportParProjet.this, "Designation : " +
//                        rapportGrandLivre.getDesignationCompte(), Toast.LENGTH_LONG).show();
//            }
//        });

    }

    public String minus_30(Calendar c,SimpleDateFormat df)
    {
        c.add(Calendar.DATE, -30);
        Date d = null;
        try {
            d = df.parse(df.format(c.getTime()));
        }catch (ParseException ex)
        {
            ex.printStackTrace();
        }
        String start_time = df.format(d);
        return start_time;
    }

    public Date to_number_format(Calendar calendar)
    {
        Date d = calendar.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd "); // 'T' HH:mm:ss z"
        try {
            d = df.parse(d.toString()); // calendar.getTime().toString()
        }catch (ParseException ex)
        {
        }
        return d;
    }

    public void click_date_image()
    {
        datePickerDialog = DatePickerDialog.newInstance(RapportParProjet.this, mYear, mMoth, mDay_of_Moth);
        datePickerDialog.setThemeDark(false);
        datePickerDialog.showYearPickerFirst(false);
        datePickerDialog.setAccentColor(Color.parseColor("#2d612c"));
        datePickerDialog.setTitle("Selectionner la date");
        datePickerDialog.show(getSupportFragmentManager(), "DatePickerDialog");
    }

    public boolean is_textNotNull(TextView text)
    {
        return text.getText().toString() != null && text.getText().toString().length() >= 1;
    }

    public boolean is_date1(int a)
    {
        return a == 1;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        annee = year;
        mois = monthOfYear + 1;
        jour = dayOfMonth;

        if (jour > 9 && mois > 9)date = annee + "-" + mois + "-" + jour;
        if (jour > 9 && !(mois > 9))date = annee + "-0" + mois + "-" + jour;
        if (!(jour > 9) && mois > 9)date = annee + "-" + mois + "-0" + jour;
        if (!(jour > 9) && !(mois > 9))date = annee + "-0" + mois + "-0" + jour;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        if (is_date1(click))
        {
            textView_start.setText(""+date);

            if (is_textNotNull(textView_start))
            {
                text_start = textView_start.getText().toString();
                text_end = textView_end.getText().toString();
                if (text_end != null)
                {
                    GrangLivre(compte_projet, text_start, text_end);
                    Clients(compte_client, text_start, text_end);
                    rapport_EB(code_projet, text_start, text_end);
                    rapport_Somme_EB_NonValide(code_projet, text_start, text_end);
                }
            }
        }
        else
        {
            textView_end.setText(""+date);
            if (is_textNotNull(textView_end))
            {
                text_end = textView_end.getText().toString();
                text_start = textView_start.getText().toString();
                if (text_start != null)
                {
                    GrangLivre(compte_projet, text_start, text_end);
                    Clients(compte_client, text_start, text_end);
                    rapport_EB(code_projet, text_start, text_end);
                    rapport_Somme_EB_NonValide(code_projet, text_start, text_end);
//                    if (rapportGrandLivre_local == null)
//                    {
//                        Toast.makeText(RapportParProjet.this,
//                                "il y a pas de rapport pour le projet " + textView_desingation_projet.getText().toString() ,
//                                Toast.LENGTH_LONG).show();
//                    }
//                    else
//                    {
//                        // pour le grand livre
//                        textView_solde_initial.setText("" +
//                                new DecimalFormat("##.##").format(rapportGrandLivre_local.getSoldeInitial()));
//                        textView_entree.setText("" + new DecimalFormat("##.##").format(rapportGrandLivre_local.getDebit()));
//                        textView_sortie.setText("" + new DecimalFormat("##.##").format(rapportGrandLivre_local.getCredit()));
//                        textView_solde.setText("" + new DecimalFormat("##.##").format(rapportGrandLivre_local.getSolde()));
//
//                        // pour les clients
//                        textView_solde_client_initial.setText("" +
//                                new DecimalFormat("##.##").format(rapportClient_local.getSoldeInitial()));
//                        textView_client_entree.setText("" + new DecimalFormat("##.##").format(rapportClient_local.getDebit()));
//                        textView_client_sortie.setText("" + new DecimalFormat("##.##").format(rapportClient_local.getCredit()));
//                        textView_client_solde.setText("" + new DecimalFormat("##.##").format(rapportClient_local.getSolde()));
//
//                        // pour les etatBesoin
////                        textView_nbre_EB_envoye.setText("" +
////                                new DecimalFormat("##.##").format(rapportEtatBesoin_local.getSommeEbEntenteDeValidation()));
//                        textView_somme_EB_valide.setText("" +
//                                new DecimalFormat("##.##").format(rapportEtatBesoin_local.getSommeEbValide()));
//                        textView_somme_EB_servis.setText("" +
//                                new DecimalFormat("##.##").format(rapportEtatBesoin_local.getSommeEbDecaisse()));
//                        textView_somme_EB_reste_a_servir.setText("" + new DecimalFormat("##.##").format(rapportEtatBesoin_local.getSommeEbValide() -
//                                rapportEtatBesoin_local.getSommeEbDecaisse()));
//
//                        // pour la somme de non valide etatBesoin
//                        textView_somme_EB_envoye.setText("" + new DecimalFormat("##.##").format(rapportEtatBesoin_local_somme_non_valide));
//                    }
                }
            }
        }
    }

    void GrangLivre(int compte, String date1, String date2){
        rapportParProjetViewModel.getRapportGrandLivre(compte, date1, date2).observe(RapportParProjet.this,
                new Observer<RapportGrandLivre>() {
            @Override
            public void onChanged(RapportGrandLivre rapportGrandLivre) {
                rapportGrandLivre_local = rapportGrandLivre;
                textView_solde_initial.setText("" +
                        new DecimalFormat("##.##").format(rapportGrandLivre_local.getSoldeInitial()));
                textView_entree.setText("" + new DecimalFormat("##.##").format(rapportGrandLivre_local.getDebit()));
                textView_sortie.setText("" + new DecimalFormat("##.##").format(rapportGrandLivre_local.getCredit()));
                textView_solde.setText("" + new DecimalFormat("##.##").format(rapportGrandLivre_local.getSolde()));
                designation_grand_livre = rapportGrandLivre.getDesignationCompte();
            }
        });
    }

    void Clients(int compte, String date1, String date2){
        rapportParProjetViewModel.getRapportClients(compte, date1, date2).observe(RapportParProjet.this,
                new Observer<RapportGrandLivre>() {
                    @Override
                    public void onChanged(RapportGrandLivre rapportGrandLivre) {
                        rapportClient_local = rapportGrandLivre;
                        textView_solde_client_initial.setText("" +
                                new DecimalFormat("##.##").format(rapportClient_local.getSoldeInitial()));
                        textView_client_entree.setText("" + new DecimalFormat("##.##").format(rapportClient_local.getDebit()));
                        textView_client_sortie.setText("" + new DecimalFormat("##.##").format(rapportClient_local.getCredit()));
                        textView_client_solde.setText("" + new DecimalFormat("##.##").format(rapportClient_local.getSolde()));
                        designation_client = rapportGrandLivre.getDesignationCompte();
                    }
                });
    }

    void rapport_EB(String code_projet, String date1, String date2)
    {
        rapportParProjetViewModel.getRapportEtatBesoin(code_projet, date1, date2).observe(RapportParProjet.this,
                new Observer<RapportEtatBesoin>() {
            @Override
            public void onChanged(RapportEtatBesoin rapportEtatBesoin) {
                rapportEtatBesoin_local = rapportEtatBesoin;
//                textView_nbre_EB_envoye.setText("" + rapportEtatBesoin_local.getSommeEbEntenteDeValidation());
                textView_somme_EB_valide.setText("" + rapportEtatBesoin_local.getSommeEbValide());
                textView_somme_EB_servis.setText("" + rapportEtatBesoin_local.getSommeEbDecaisse());
                textView_somme_EB_reste_a_servir.setText("" +
                        new DecimalFormat("##.##").format(rapportEtatBesoin_local.getSommeEbValide() -
                        rapportEtatBesoin_local.getSommeEbDecaisse()));
            }
        });
    }

    void rapport_Somme_EB_NonValide(String code_projet, String date1, String date2)
    {
        rapportParProjetViewModel.getRapportSommeNonValide(code_projet, date1, date2)
                .observe(RapportParProjet.this,
                new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                rapportEtatBesoin_local_somme_non_valide = aDouble;
                textView_somme_EB_envoye.setText("" + rapportEtatBesoin_local_somme_non_valide);
            }
        });
    }
}
