package com.soft.ssvapp.Fragment_Menu.All_specificProject.RapportReferenceEtatBesoinCons;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.soft.ssvapp.DataRetrofit.RapportParProjet.RapportReferenceEtatBesoinConsomme;
import com.soft.ssvapp.R;

import java.util.List;

public class RapportReferenceEtatBesoinConsActivity extends AppCompatActivity {

    RapportReferenceEtatBesoinConsViewModel rapportReferenceEtatBesoinConsViewModel;
    RapportReferenceEtatBesoinConsAdapter adapter_EB_Cons;
    ListView listView_eb_cons;
    ProgressBar progressBar_eb_cons;

    public static final String CODE_PROJET = "codeProjet";
    public static final String CODE_ARTICLE = "codeArticle";
    public static final String CODE_LIGNE = "codeLigne";
    public static final String DESIGNATION_ARTICLE = "designation_aricle";
    public static final String DESIGNATION_LIGNE = "designation_ligne";
    String code_projet = "";
    String code_article = "";
    String code_ligne = "";
    String designation_article = "";
    String designation_ligne = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rapport_reference_etat_besoin_cons);
        code_projet = getIntent().getStringExtra(CODE_PROJET);
        code_article = getIntent().getStringExtra(CODE_ARTICLE);
        code_ligne = getIntent().getStringExtra(CODE_LIGNE);
        designation_article = getIntent().getStringExtra(DESIGNATION_ARTICLE);
        designation_ligne = getIntent().getStringExtra(DESIGNATION_LIGNE);

        if (designation_article.length() >= 13)
        {
            designation_article = designation_article.substring(0,13).trim()+"...";
        }

        setUpBar(designation_article+"/"+designation_ligne.trim());

        adapter_EB_Cons = new RapportReferenceEtatBesoinConsAdapter(this);
        listView_eb_cons = findViewById(R.id.listView_eb_cons);
        progressBar_eb_cons = findViewById(R.id.progress_bar_eb_cons);

        rapportReferenceEtatBesoinConsViewModel =
                ViewModelProviders.of(this).get(RapportReferenceEtatBesoinConsViewModel.class);
        rapportReferenceEtatBesoinConsViewModel.getRapportReferenceEtatBesoinCons(code_projet, code_article, code_ligne)
                .observe(RapportReferenceEtatBesoinConsActivity.this,
                        new Observer<List<RapportReferenceEtatBesoinConsomme>>() {
            @Override
            public void onChanged(List<RapportReferenceEtatBesoinConsomme> rapportReferenceEtatBesoinConsommes) {
                adapter_EB_Cons.setRapportRefEbCons(rapportReferenceEtatBesoinConsommes);
                listView_eb_cons.setAdapter(adapter_EB_Cons);
            }
        });

        rapportReferenceEtatBesoinConsViewModel.getLoading().observe(RapportReferenceEtatBesoinConsActivity.this,
                new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean)
                {
                    progressBar_eb_cons.setVisibility(View.VISIBLE);
                }
                else
                {
                    progressBar_eb_cons.setVisibility(View.GONE);
                }
            }
        });
    }

    void setUpBar(String title)
    {
        Toolbar toolbar = findViewById(R.id.app_bar);
        RapportReferenceEtatBesoinConsActivity.this.setSupportActionBar(toolbar);
        toolbar.setTitle(title);
        if (RapportReferenceEtatBesoinConsActivity.this != null)
        {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }
}
