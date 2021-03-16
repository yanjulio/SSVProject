package com.soft.ssvapp.Fragment_Menu.Fill_EtatBesoin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.soft.ssvapp.Data.Entity_Article;
import com.soft.ssvapp.DataRetrofit.Ligne.LigneRepository;
import com.soft.ssvapp.Fragment_Menu.FillArticle.ListArticle;
import com.soft.ssvapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AjouterDetailBesoinActive extends AppCompatActivity {
    public static final String DERNIER_DETAIL_BESOIN = "dernier_detail_besoin";
    public static final String INITIAL_UTILISATEUR = "initial_utilisateur";

//    ArticleRetrofitRepository articleRetrofitRepository;

    public static String  IDDETAIL = "idDetail";
    public static String CODEETATDEBESOIN = "codeetatDeBesoin";
    public static String CODE_DETAIL_ETAT_BESOIN = "codeDetailEtatdeBesoin";
    public static String CODEARTICLE = "codeArticle";
    public static String CODELIGNE = "codeligne";
    public static String LIBELLEDETAIL = "libelleDetail";
    public static String QTE = "qte";
    public static String PU = "pu";
    public static String SORTIE = "sortie";
    public static String ENTREE = "entree";

    public static String CODE_PROJET = "code_projet";
    public static String CODE_LIGNE = "code_ligne";
    String codeProjet;
    private String codeLigne ="";
    private String codeArticle="";
    private int dernier_detail_besoin;
    private String initial_utilisateur="";

//    private Spinner spinner_code_ligne;
//    private Spinner spinner_article;
//    private ArrayList<Entity_Article> spinner_article_list = new ArrayList<>();
//    private ArrayList<Entity_ProjectWithEntity_Ligne> spinner_list = new ArrayList<Entity_ProjectWithEntity_Ligne>();
//    LigneViewModel view_model;
    LigneRepository ligneRepository;
//    ArticleViewModel articleViewModel;

//    MaterialButton btn_enregistre_details;
    TextInputEditText edit_libelle_details;
    TextInputEditText edit_pu;
    TextInputEditText edit_qte;

    ImageView imageView_select_article;
    TextView textView_article_select;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_ajouter_detail_besoin);
        setUpToolbar();
//        articleRetrofitRepository = ArticleRetrofitRepository.getInstance();

        codeProjet = getIntent().getStringExtra(CODE_PROJET);
        codeLigne = getIntent().getStringExtra(CODE_LIGNE);
//        Toast.makeText(this, "codeLigne : " + codeLigne, Toast.LENGTH_LONG).show();
        dernier_detail_besoin = getIntent().getIntExtra(DERNIER_DETAIL_BESOIN, 0);
        initial_utilisateur = getIntent().getStringExtra(INITIAL_UTILISATEUR);


//        btn_enregistre_details = findViewById(R.id.btn_submit_ajouter_details);
        edit_libelle_details = findViewById(R.id.edit_libelle_ajouter_detail_besoin);
        edit_pu = findViewById(R.id.edit_pu_ajouter_detail_besoin);
        edit_qte = findViewById(R.id.edit_qte_ajouter_detail_besoin);

//        spinner_code_ligne = findViewById(R.id.spinner_code_ligne);
        ligneRepository = LigneRepository.getInstance();

        textView_article_select = findViewById(R.id.textView_article);
        imageView_select_article = findViewById(R.id.imageView_select_article);
        imageView_select_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArticleDialog articleDialog = new ArticleDialog();
                articleDialog.show(getSupportFragmentManager(), "12");
            }
        });

        MaterialButton materialButton_ajouter_article = findViewById(R.id.button_details_besoin_ajout_article);
        materialButton_ajouter_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AjouterDetailBesoinActive.this, ListArticle.class));
            }
        });
    }

    public void setCodeArticle_and_codeArticle(Entity_Article entity_article)
    {
        textView_article_select.setText(""+entity_article.getDesignationArticle());
        codeArticle = entity_article.getCodeArticle();
//        Toast.makeText(AjouterDetailBesoinActive.this, "codeArticle " + codeArticle, Toast.LENGTH_LONG).show();
        if (!entity_article.getDesignationArticle().equals("AUTRE"))
        {
            edit_libelle_details.setText("" + entity_article.getDesignationArticle().trim());
            edit_pu.setText("" + entity_article.getPrixAchat());
            edit_qte.setText("" + 1);
        }
    }

    ArrayList<String> getString_article(ArrayList<Entity_Article> list)
    {
        ArrayList<String> list_local = new ArrayList<>();
        list_local.clear();
        for (int a = 0; a < list.size(); a++)
        {
            list_local.add(list.get(a).getDesignationArticle());
        }
        return list_local;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_active, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.item_save_details_besoin_active:
                if (!edit_libelle_details.getText().toString().equals("") ||
                        edit_qte.getText().toString() != null || edit_pu.getText().toString() != null)
                {
                    Intent intent = new Intent();
                    intent.putExtra(CODELIGNE, codeLigne);
                    intent.putExtra(CODEARTICLE, codeArticle);
                    intent.putExtra(DERNIER_DETAIL_BESOIN, dernier_detail_besoin);
                    intent.putExtra(PU, Double.valueOf(edit_pu.getText().toString()));
                    intent.putExtra(QTE, Double.valueOf(edit_qte.getText().toString()));
                    intent.putExtra(LIBELLEDETAIL, edit_libelle_details.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();
                    break;
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpToolbar()
    {
        Toolbar toolbar = findViewById(R.id.app_bar_ajouteBesoin);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_clear_24);
        toolbar.setTitle("Ajouter détaille");
        if (AjouterDetailBesoinActive.this != null) {
            AjouterDetailBesoinActive.this.setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AjouterDetailBesoinActive.this.finish();
                }
            });
        }
    }
}
