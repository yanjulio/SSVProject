package com.soft.ssvapp.Fragment_Menu.FillArticle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.SearchEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.soft.ssvapp.Data.Entity_Article;
import com.soft.ssvapp.DataRetrofit.Article.ArticleRetrofitRepository;
import com.soft.ssvapp.Fragment_Menu.Fill_EtatBesoin.AjouterArticle;
import com.soft.ssvapp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListArticle extends AppCompatActivity {

    ArticleRetrofitRepository articleRetrofitRepository;
    ListArticleAdapter articleAdapter;
    ListView listView_article;
    ArticleViewModel articleViewModel;
    SearchView searchView_article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_article);
        setUpToolBar();
        articleRetrofitRepository = ArticleRetrofitRepository.getInstance();

        articleAdapter = new ListArticleAdapter(this);
        listView_article = findViewById(R.id.listView_article);
        articleViewModel = ViewModelProviders.of(ListArticle.this).get(ArticleViewModel.class);
        articleViewModel.getGetAllArticle().observe(ListArticle.this, new Observer<List<Entity_Article>>() {
            @Override
            public void onChanged(List<Entity_Article> entity_articles) {
                articleAdapter.setArticle(entity_articles);
                listView_article.setAdapter(articleAdapter);
            }
        });

        searchView_article = findViewById(R.id.searchView_article);
//                .getActionView();
        searchView_article.setSubmitButtonEnabled(true);
        searchView_article.setOnQueryTextListener(onQueryTextListener);


        FloatingActionButton add_article = findViewById(R.id.float_add_list_article);
        add_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dernierArticle();
            }
        });

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_search, menu);
//        searchView_article = (SearchView) menu.findItem(R.id.item_serarch_article)
//                .getActionView();
//        searchView_article.setSubmitButtonEnabled(true);
//        searchView_article.setOnQueryTextListener(onQueryTextListener);
//        return super.onCreateOptionsMenu(menu);
//    }

    private SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            getArticleFromDb(query);
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            getArticleFromDb(newText);
            return true;
        }

        private void getArticleFromDb(String searchQuery)
        {
            searchQuery = "%"+searchQuery+"%";
            articleViewModel.getArticleSearchQuery(searchQuery).observe(ListArticle.this, new Observer<List<Entity_Article>>() {
                @Override
                public void onChanged(List<Entity_Article> entity_articles) {
                        if (entity_articles == null)
                        {
                            return;
                        }
                        articleAdapter.setArticle(entity_articles);
                        listView_article.setAdapter(articleAdapter);
                }
            });
        }
    };

    void dernierArticle()
    {
        Call<Integer> call_article = articleRetrofitRepository.articleConnexion().dernierArticle();
        call_article.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful())
                {
                    startActivity(
                            new Intent(
                                    ListArticle.this,
                                    AjouterArticle.class)
                                    .putExtra(AjouterArticle.DERNIERARTICLE, response.body()));
                }
                else
                {
                    switch (response.code())
                    {
                        case 404:
                            Toast.makeText(ListArticle.this, "server not found.", Toast.LENGTH_LONG).show();
                            break;
                        case 500:
                            Toast.makeText(ListArticle.this, "server broken.", Toast.LENGTH_LONG).show();
                            break;
                        default:
                            Toast.makeText(ListArticle.this, "unkonwn problem.", Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(ListArticle.this, "Connexion Problem.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void deleteArticle(Entity_Article entity_article)
    {
        articleViewModel.deleteArticle(entity_article);
    }

    public void setUpToolBar()
    {
        Toolbar toolbar = findViewById(R.id.app_bar);
        if (ListArticle.this != null)
        {
            ListArticle.this.setSupportActionBar(toolbar);
            toolbar.setTitle("Articles");
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ListArticle.this.finish();
                }
            });
        }
    }
}
