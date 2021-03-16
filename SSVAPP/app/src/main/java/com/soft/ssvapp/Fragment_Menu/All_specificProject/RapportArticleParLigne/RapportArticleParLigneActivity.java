package com.soft.ssvapp.Fragment_Menu.All_specificProject.RapportArticleParLigne;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.soft.ssvapp.DataRetrofit.RapportParProjet.RapportArticleParLigneResponse;
import com.soft.ssvapp.R;

import java.text.DecimalFormat;
import java.util.List;

public class RapportArticleParLigneActivity extends AppCompatActivity {

    public static final String CODE_PRJET="codeProjet";
    public static final String CODE_LIGNE="codeLigne";
    public static final String DESIGNATION_LIGNE="designationLigne";
    String codeProjet = "";
    String codeLigne = "";
    String designationLigne= "";

    ProgressBar progressBar;
    ListView listView_article;
    TextView textView_total;
    SearchView searchView_article;

    RapportArticleParLigneViewModel rapportArticleParLigneViewModel;
    RapportArticleParLigneAdapter articleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rapport_article_par_ligne);
        codeProjet = getIntent().getStringExtra(CODE_PRJET);
        codeLigne = getIntent().getStringExtra(CODE_LIGNE);
        designationLigne = getIntent().getStringExtra(DESIGNATION_LIGNE);

        setUpBar(designationLigne);

        articleAdapter = new RapportArticleParLigneAdapter(this, codeProjet);
        listView_article = findViewById(R.id.listView_article_rapport_par_ligne);
        textView_total = findViewById(R.id.text_total_rapport_projet_article_par_ligne);
        progressBar = findViewById(R.id.progress_bar_list_article_rapport_par_ligne);

        rapportArticleParLigneViewModel = ViewModelProviders.of(this).get(RapportArticleParLigneViewModel.class);
        rapportArticleParLigneViewModel.getRapportArticleParLigne(codeProjet, codeLigne).observe(RapportArticleParLigneActivity.this,
                new Observer<List<RapportArticleParLigneResponse>>() {
            @Override
            public void onChanged(List<RapportArticleParLigneResponse> rapportArticleParLigneResponses) {
                articleAdapter.setArticleParLigne(rapportArticleParLigneResponses);
                listView_article.setAdapter(articleAdapter);
                setTotalLigneParArticle(rapportArticleParLigneResponses);
            }
        });

        searchView_article = findViewById(R.id.searchView_article_par_ligne_rapport);
        searchView_article.setSubmitButtonEnabled(true);
        searchView_article.setOnQueryTextListener(onQueryTextListener);
    }

    private SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            getArticleFromDb(query);
//            articleAdapter.getFilter().filter(query);
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            getArticleFromDb(newText);
//            articleAdapter.getFilter().filter(newText);
            return true;
        }

        private void getArticleFromDb(String searchQuery)
        {
            articleAdapter.getFilter().filter(searchQuery);
//            searchQuery = "%"+searchQuery+"%";
//            rapportArticleParProjetViewModel.getArticleSearchQuery(searchQuery).observe(RapportArticle.this,
//                    new Observer<List<RapportArticleResponse>>() {
//                @Override
//                public void onChanged(List<RapportArticleResponse> rapportArticleResponses) {
//                    if (rapportArticleResponses == null)
//                    {
//                        return;
//                    }
//                    articleAdapter.setArticle(rapportArticleResponses);
//                    listView_article.setAdapter(articleAdapter);
//                    articleAdapter.notifyDataSetChanged();
//                }
//            });
        }
    };

    void setTotalLigneParArticle(List<RapportArticleParLigneResponse> list_totauxCons)
    {
        double total=0;
        for (int a =0; a < list_totauxCons.size(); a++)
        {
            total += list_totauxCons.get(a).getTotalConsommation();
        }
        textView_total.setText("$" + new DecimalFormat("##.##").format(total));
    }

    void setUpBar(String title)
    {
        Toolbar toolbar = findViewById(R.id.app_bar);
        if (RapportArticleParLigneActivity.this != null)
        {
            RapportArticleParLigneActivity.this.setSupportActionBar(toolbar);
            toolbar.setTitle(""+title);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RapportArticleParLigneActivity.this.finish();
                }
            });
        }
    }
}
