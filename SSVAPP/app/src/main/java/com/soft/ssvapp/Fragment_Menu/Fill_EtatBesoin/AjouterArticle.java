package com.soft.ssvapp.Fragment_Menu.Fill_EtatBesoin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.soft.ssvapp.Data.Entity_Article;
import com.soft.ssvapp.Fragment_Menu.FillArticle.ArticleViewModel;
import com.soft.ssvapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AjouterArticle extends AppCompatActivity {

    public static final String DERNIERARTICLE = "dernier_article";
    public static final String MODIFIERARTICLE = "modifier_article";
    public static final String IDARTICLE = "id_article";
    public static final String CODEARTICLE = "codeArticle";

    Spinner categorie_article;
    int categorie_value = 0;
    String[] list_categorie = {"1", "2", "3"};

    TextInputEditText designation_arcticle, prixAchat, prixVente;

    MaterialButton button_ajouter, button_ok;

    int dernier_article;
    int id_article;
    String codeArticle;
    String modifier_article = "";

    ArticleViewModel articleViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_article);
        articleViewModel = ViewModelProviders.of(AjouterArticle.this).get(ArticleViewModel.class);
        dernier_article = getIntent().getIntExtra(DERNIERARTICLE, 0);
        id_article = getIntent().getIntExtra(IDARTICLE, 0);
        if (id_article != 0)
        {
            modifier_article = getIntent().getStringExtra(MODIFIERARTICLE);
        }

        // spinner list
        categorie_article = findViewById(R.id.spinner_categorie_ajoute_article);
        ArrayAdapter adapter_project = new ArrayAdapter(
                AjouterArticle.this,
                android.R.layout.simple_list_item_1,
                list_categorie);
        adapter_project.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorie_article.setAdapter(adapter_project);
        categorie_article.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categorie_value = Integer.valueOf(list_categorie[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // autre champs
        designation_arcticle = findViewById(R.id.edit_designation_article);
        prixAchat = findViewById(R.id.edit_prixAchatarticle);
        prixVente = findViewById(R.id.edit_prixVentearticle);
//        critique = findViewById(R.id.edit_critiquearticle);

        // button
//        TextView textView_cancel_article = findViewById(R.id.text_cancel_article);
//        textView_cancel_article.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        setUpToolBar();
        button_ajouter = findViewById(R.id.button_ajouter_ajouter_article);
        button_ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!designation_arcticle.getText().toString().equals("") && !prixAchat.getText().toString().equals("") &&
                        !prixVente.getText().toString().equals(""))
                {
                    articleViewModel.insertArticle(new Entity_Article("AR"+dernier_article, "CD1",
                            designation_arcticle.getText().toString(), categorie_value,
                            Double.valueOf(prixAchat.getText().toString()),
                            Double.valueOf(prixVente.getText().toString()), "", 0.0,
                            0.0, 0.0, 0));
                    designation_arcticle.setText("");
                    prixAchat.setText("");
                    prixVente.setText("");
                    dernier_article += 1;
                }
            }
        });
        button_ok = findViewById(R.id.button_ajout_ok_article);
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!designation_arcticle.getText().toString().equals("") && !prixAchat.getText().toString().equals("") &&
                        !prixVente.getText().toString().equals(""))
                {
                    if (id_article == 0)
                    {
                        articleViewModel.insertArticle(new Entity_Article("AR"+dernier_article, "CD1",
                                designation_arcticle.getText().toString(), categorie_value,
                                Double.valueOf(prixAchat.getText().toString()), Double.valueOf(prixVente.getText().toString()),
                                "", 0.0,
                                0.0, 0.0, 0));
                    }
                    else
                    {
                        codeArticle = getIntent().getStringExtra(CODEARTICLE);
//                        Toast.makeText(AjouterArticle.this, "je viens de modifier", Toast.LENGTH_LONG).show();
//                        Toast.makeText(AjouterArticle.this, "dernier article : " + dernier_article, Toast.LENGTH_LONG).show();
                        Entity_Article entity_article = new Entity_Article(codeArticle, "CD1",
                                designation_arcticle.getText().toString(), categorie_value,
                                Double.valueOf(prixAchat.getText().toString()), Double.valueOf(prixVente.getText().toString()),
                                "", 0.0,
                                0.0, 0.0, 0);
                        entity_article.setIdArticle(id_article);
                        articleViewModel.updateArticle(entity_article);
                    }
                }
                finish();
            }
        });

        ProgressBar progressBar = findViewById(R.id.progress_bar_article);
        articleViewModel.getIsLoading().observe(AjouterArticle.this, new Observer<Boolean>() {
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

        // pour la modification
        if (id_article != 0)
        {
            if (!modifier_article.equals(""))
            {
//                TextView textView_afficher = findViewById(R.id.text_titre_article);
//                textView_afficher.setText(modifier_article + " article");
                Entity_Article entity_article = articleViewModel.getEntity_article(id_article);
                designation_arcticle.setText(""+entity_article.getDesignationArticle().trim());
                prixAchat.setText(""+entity_article.getPrixAchat());
                prixVente.setText("" +entity_article.getPrixVente());
                button_ajouter.setVisibility(View.GONE);
            }
        }

    }

    public void setUpToolBar()
    {
        Toolbar toolbar = findViewById(R.id.app_bar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_clear_24);
        if (AjouterArticle.this != null)
        {
            AjouterArticle.this.setSupportActionBar(toolbar);
            if (!modifier_article.equals(""))
            {
                toolbar.setTitle(modifier_article + " article");
            }
            else
            {
                toolbar.setTitle("Ajouter article");
            }
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AjouterArticle.this.finish();
                }
            });
        }
    }
}
