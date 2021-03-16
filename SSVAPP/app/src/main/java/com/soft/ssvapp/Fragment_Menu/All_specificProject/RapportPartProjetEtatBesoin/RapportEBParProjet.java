package com.soft.ssvapp.Fragment_Menu.All_specificProject.RapportPartProjetEtatBesoin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.soft.ssvapp.DataRetrofit.RapportParProjet.RapportEBParProjetValideEtDecaisse;
import com.soft.ssvapp.R;

import java.text.DecimalFormat;
import java.util.List;

public class RapportEBParProjet extends AppCompatActivity {

    RapportEBParProjetAdapter rapportEBParProjetAdapter;
    RapportEBParProjetValideEtDecaisseViewModel rapportEBParProjetValideEtDecaisseViewModel;

    ProgressBar progressBar;
    public static final String CODE_PROEJET = "code_projet";
    String code_projet="";
    ListView listView_rapportEBParProjet;
    TextView textView_total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rapport_e_b_par_projet);
        setUpToolBar();
        code_projet = getIntent().getStringExtra(CODE_PROEJET);
        rapportEBParProjetValideEtDecaisseViewModel =
                ViewModelProviders.of(RapportEBParProjet.this).get(RapportEBParProjetValideEtDecaisseViewModel.class);
        progressBar = findViewById(R.id.progress_bar_etat_besoin_rapport_par_projet);
        textView_total = findViewById(R.id.textView_total_rapport_par_projet_EB);

        //ICI J'UTILISE LE MEME ADAPTER ET MODEL QUE LES ETAT DES BESOINS GLOBALS

        rapportEBParProjetAdapter = new RapportEBParProjetAdapter(RapportEBParProjet.this);
        listView_rapportEBParProjet = findViewById(R.id.listView_etat_besoin_rapport_par_projet);
        fill_list(code_projet);
        rapportEBParProjetValideEtDecaisseViewModel.getIsLoading().observe(RapportEBParProjet.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean)
                {
                    progressBar.setVisibility(View.VISIBLE);
                }
                else
                {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }

    void fill_list(String code_projet)
    {
        rapportEBParProjetValideEtDecaisseViewModel.getRapportEBValideDecaisse(code_projet).observe(RapportEBParProjet.this,
                new Observer<List<RapportEBParProjetValideEtDecaisse>>() {
            @Override
            public void onChanged(List<RapportEBParProjetValideEtDecaisse> rapportEtatBesoinResponses) {
                rapportEBParProjetAdapter.setEtatBesoin(rapportEtatBesoinResponses);
                listView_rapportEBParProjet.setAdapter(rapportEBParProjetAdapter);
                setTotalValue(textView_total, rapportEtatBesoinResponses);
            }
        });
    }

    //todo : set the total of Etat de Besoin.
    void setTotalValue(TextView textView_total_value, List<RapportEBParProjetValideEtDecaisse> list)
    {
        double total_value =0;
        for (int a = 0; a < list.size(); a++)
        {
            total_value += (list.get(a).getTotal() - list.get(a).getSommeDecaisse());
        }
        textView_total_value.setText("$"+ new DecimalFormat("##.##").format(total_value));
    }

    public void setUpToolBar()
    {
        Toolbar toolbar = findViewById(R.id.app_bar);
        if (RapportEBParProjet.this != null)
        {
            RapportEBParProjet.this.setSupportActionBar(toolbar);
            toolbar.setTitle("Rapport Etat de Bésoin");
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RapportEBParProjet.this.finish();
                }
            });
        }
    }
}
