package com.soft.ssvapp.Fragment_Menu.Fill_Operation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.soft.ssvapp.Data.Entity_DetailBesoin;
import com.soft.ssvapp.Data.Entity_DetailBesoinWithEntity_Article;
import com.soft.ssvapp.Fragment_Menu.FillArticle.ArticleViewModel;
import com.soft.ssvapp.Fragment_Menu.Fill_EtatBesoin.DetailBesoinViewModel;
import com.soft.ssvapp.Fragment_Menu.Fill_EtatBesoin.Modifier_Details_Besoin;
import com.soft.ssvapp.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.ObjLongConsumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Details_EtatBesoinValider extends AppCompatActivity {

    public static String CODEBESOIN = "codeBesoin_valider";
    public static String DESINGATION_BESOIN = "designation_besoin";
//    DetailsEtatDeBesoinRepository repository_detailsEtatBesoin;
    ArticleViewModel articleViewModel;
    DetailBesoinViewModel viewModel;
    Details_EtatBesoinValiderAdapter adapter;
    ListView listView_details;

    // les valeurs du details
    String libelle_ajouter_details;
    double qte_ajouter_details;
    double pu_ajouter_details;

    String codeBesoin;
    String designation_besoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details__etat_besoin_valider);
        articleViewModel = ViewModelProviders.of(Details_EtatBesoinValider.this).get(ArticleViewModel.class);
        codeBesoin = getIntent().getStringExtra(CODEBESOIN);
        designation_besoin = getIntent().getStringExtra(DESINGATION_BESOIN);
        setUpToolBar();

//        Toast.makeText(Details_EtatBesoinValider.this, "codeBesoin valider : " + codeBesoin, Toast.LENGTH_LONG).show();

//        TextView cancel_detail_valider = findViewById(R.id.text_cancel_details_besoin_valider);
//        TextView titre_detail_valider = findViewById(R.id.text_titre_details_besoin_valider);
//        titre_detail_valider.setText("" + designation_besoin);
        TextView total = findViewById(R.id.textView_total_value_detail_besoin_valider);
//        cancel_detail_valider.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

        listView_details = findViewById(R.id.listView_details_besoin_valider);
        adapter = new Details_EtatBesoinValiderAdapter(this);

        viewModel = ViewModelProviders.of(Details_EtatBesoinValider.this).get(DetailBesoinViewModel.class);
        viewModel.init(codeBesoin);
//        List<Entity_DetailBesoinWithEntity_Article> list_locale = new ArrayList<>();
        ProgressBar progressBar_details_valider = findViewById(R.id.progress_bar_detail_valider_etat_besoin);
        viewModel.isLoaging().observe(Details_EtatBesoinValider.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean)
                {
                    progressBar_details_valider.setVisibility(View.VISIBLE);
                }
                else
                {
                    progressBar_details_valider.setVisibility(View.GONE);
                }
            }
        });
        viewModel.getDetailBesoinArticleLiveData().observe(Details_EtatBesoinValider.this,
                new Observer<List<Entity_DetailBesoinWithEntity_Article>>() {
            @Override
            public void onChanged(List<Entity_DetailBesoinWithEntity_Article> entity_detailBesoinWithEntity_articles) {
                adapter.setDetailsBesoin(entity_detailBesoinWithEntity_articles);
                listView_details.setAdapter(adapter);
                setTotalValue(total, entity_detailBesoinWithEntity_articles);
            }
        });
    }

    void setTotalValue(TextView textView_total_value, List<Entity_DetailBesoinWithEntity_Article> list)
    {
        double total_value =0;
        for (int a = 0; a < list.size(); a++)
        {
            total_value += (list.get(a).getDetailBesoin().getQte() * list.get(a).getDetailBesoin().getPu());
        }
        textView_total_value.setText("$"+ new DecimalFormat("##.##").format(total_value));
    }

    void openModifierDilogValider(int idDetailEBOnline, int idDetailsBesoin, String libelle_ajouter_details, String codeArticle, double qte, double pu)
    {
        Modifier_Details_Besoin modifier_deatils =
                new Modifier_Details_Besoin(idDetailEBOnline, idDetailsBesoin, codeArticle,
                        libelle_ajouter_details, qte, pu, "details_besoin_valider");
        modifier_deatils.show(getSupportFragmentManager(), "0");
    }

    public void sendModifierDetailResultatValider(int idDetailEBOnline, int idDetatialsBeosin, String libelle, String codeArticle,
                                                  double qte, double pu)
    {
        libelle_ajouter_details = libelle;
        qte_ajouter_details = qte;
        pu_ajouter_details = pu;
        Entity_DetailBesoin entity_detailBesoin =
                new Entity_DetailBesoin(idDetailEBOnline, codeBesoin, codeArticle, "codeLigne", libelle,
                qte_ajouter_details, pu_ajouter_details, 0.0, 0.0);
        entity_detailBesoin.setIdDetailEB(idDetatialsBeosin);
        viewModel.updateDetailOnline(entity_detailBesoin);
    }

    public void deleteOnlineDetail(Entity_DetailBesoin entity_detailBesoin)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Voulez-vous supprimer cette detaille?");
        builder.setCancelable(true);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                viewModel.deleteOnline(entity_detailBesoin);
            }
        });
        builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    public void setUpToolBar()
    {
        Toolbar toolbar = findViewById(R.id.app_bar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_clear_24);

        if (Details_EtatBesoinValider.this != null)
        {
            Details_EtatBesoinValider.this.setSupportActionBar(toolbar);
            toolbar.setTitle(designation_besoin);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Details_EtatBesoinValider.this.finish();
                }
            });
        }
    }
}
