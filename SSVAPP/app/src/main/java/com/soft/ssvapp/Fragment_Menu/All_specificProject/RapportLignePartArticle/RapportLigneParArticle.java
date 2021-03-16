package com.soft.ssvapp.Fragment_Menu.All_specificProject.RapportLignePartArticle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.soft.ssvapp.DataRetrofit.RapportParProjet.RapportLigneParArticleResponse;
import com.soft.ssvapp.R;

import java.text.DecimalFormat;
import java.util.List;

public class RapportLigneParArticle extends AppCompatActivity {

    ProgressBar progressBar_ligne_rapport_par_projet;
    RapportLigneParArticleAdapter rapportLigneParArticleAdapter;
    ListView listView_ligne_rapport_par_projet;
    TextView textView_total, textView_qte, textView_pu;
    public static final String CODE_PROJET = "code_projet";
    public static final String CODE_ARTICLE = "code_article";
    public static final String DESIGNATION_ARTICLE = "designation_article";
    String code_projet="";
    String code_article="";
    String designation_article="";
    RapportLigneParArticleViewModel ligneParArticleViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rapport_ligne_par_article);
        code_projet = getIntent().getStringExtra(CODE_PROJET);
        code_article = getIntent().getStringExtra(CODE_ARTICLE);
        designation_article = getIntent().getStringExtra(DESIGNATION_ARTICLE);

        setUp_appBar();

        rapportLigneParArticleAdapter = new RapportLigneParArticleAdapter(this, code_projet);
        listView_ligne_rapport_par_projet = findViewById(R.id.listView_ligne_rapport_par_article);
        textView_total = findViewById(R.id.text_total_rapport_projet_ligne_par_article);
        textView_qte = findViewById(R.id.text_Qte_rapport_projet_ligne_par_article);
        textView_pu = findViewById(R.id.text_pu_rapport_projet_ligne_par_article);
        progressBar_ligne_rapport_par_projet = findViewById(R.id.progress_bar_ligne_rapport_par_article);

        ligneParArticleViewModel = ViewModelProviders.of(this).get(RapportLigneParArticleViewModel.class);
        ligneParArticleViewModel.getRapportLigneParArticle(code_projet, code_article).observe(RapportLigneParArticle.this,
                new Observer<List<RapportLigneParArticleResponse>>() {
            @Override
            public void onChanged(List<RapportLigneParArticleResponse> rapportLigneParArticleResponses) {
                rapportLigneParArticleAdapter.setRapportLigneParArticle(rapportLigneParArticleResponses);
                listView_ligne_rapport_par_projet.setAdapter(rapportLigneParArticleAdapter);
                setTotalLigneParArticle(rapportLigneParArticleResponses);
            }
        });

        ligneParArticleViewModel.getIsLoading().observe(RapportLigneParArticle.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean)
                {
                    progressBar_ligne_rapport_par_projet.setVisibility(View.VISIBLE);
                }
                else
                {
                    progressBar_ligne_rapport_par_projet.setVisibility(View.GONE);
                }
            }
        });
    }

    void setTotalLigneParArticle(List<RapportLigneParArticleResponse> list_totauxCons)
    {
        double total=0;
        double qte=0;
        double pu=0;
        for (int a =0; a < list_totauxCons.size(); a++)
        {
            qte += list_totauxCons.get(a).getQte();
            pu +=list_totauxCons.get(a).getPu();
            total += list_totauxCons.get(a).getTotalConsommation();
        }
        textView_qte.setText(""+ new DecimalFormat("##.##").format(qte));
        textView_pu.setText("$"+ new DecimalFormat("##.##").format(pu));
        textView_total.setText("$" + new DecimalFormat("##.##").format(total));
    }

    public void setUp_appBar()
    {
        Toolbar toolbar = findViewById(R.id.app_bar);
        if (RapportLigneParArticle.this != null)
        {
            RapportLigneParArticle.this.setSupportActionBar(toolbar);
            toolbar.setTitle(designation_article);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RapportLigneParArticle.this.finish();
                }
            });
        }
    }
}
