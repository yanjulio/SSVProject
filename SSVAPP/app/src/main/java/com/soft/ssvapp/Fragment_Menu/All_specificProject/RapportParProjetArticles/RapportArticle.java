package com.soft.ssvapp.Fragment_Menu.All_specificProject.RapportParProjetArticles;

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
import android.widget.Toast;


import com.soft.ssvapp.DataRetrofit.RapportParProjet.RapportArticleResponse;
import com.soft.ssvapp.R;

import java.text.DecimalFormat;
import java.util.List;

public class RapportArticle extends AppCompatActivity {

    public static final String CODE_PRJET="codeProjet";
    public static final String DESIGNATION_PROJET="desingation_projet";
    String codeProjet = "";
    String designation_projet = "";

    ProgressBar progressBar;
    ListView listView_article;
    TextView textView_total;
    SearchView searchView_article;

    RapportArticleParProjetViewModel rapportArticleParProjetViewModel;
    RapportArticleAdapter articleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rapport_article2);
        codeProjet = getIntent().getStringExtra(CODE_PRJET);
        designation_projet = getIntent().getStringExtra(DESIGNATION_PROJET);

        setUpBar(designation_projet);

        rapportArticleParProjetViewModel = ViewModelProviders.of(this).get(RapportArticleParProjetViewModel.class);
        articleAdapter = new RapportArticleAdapter(this, codeProjet);

        progressBar = findViewById(R.id.progress_bar_list_article_rapport);
        textView_total = findViewById(R.id.text_total_rapport_projet_article);
        listView_article = findViewById(R.id.listView_article_rapport);

        rapportArticleParProjetViewModel.getRapportArticles(codeProjet).observe(RapportArticle.this,
                new Observer<List<RapportArticleResponse>>() {
            @Override
            public void onChanged(List<RapportArticleResponse> rapportArticleResponses) {
                articleAdapter.setArticle(rapportArticleResponses);
                listView_article.setAdapter(articleAdapter);
                setTotalArticle(rapportArticleResponses);
            }
        });

        rapportArticleParProjetViewModel.getLoading().observe(RapportArticle.this, new Observer<Boolean>() {
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

        searchView_article = findViewById(R.id.searchView_article_rapport);
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

    void setTotalArticle(List<RapportArticleResponse> list_totauxCons)
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
        if (RapportArticle.this != null)
        {
            RapportArticle.this.setSupportActionBar(toolbar);
            toolbar.setTitle(""+title);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RapportArticle.this.finish();
                }
            });
        }
    }
}
