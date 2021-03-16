package com.soft.ssvapp.Fragment_Menu.All_specificProject.RapportPartProjetEtatBesoin.RapportDetailEB;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.soft.ssvapp.DataRetrofit.DetailsEtatDeBesoin.DetailsEtatDeBesoinRetrofit;
import com.soft.ssvapp.DataRetrofit.EtatDeBesoin.EtatDeBesoinRetrofitDetailEB;
import com.soft.ssvapp.Fragment_Menu.FillPayements.Details.PayementDetailEBAdapter;
import com.soft.ssvapp.Fragment_Menu.FillPayements.DetailsPayement.remote.DetailPayementRemoteViewModel;
import com.soft.ssvapp.R;

import java.text.DecimalFormat;
import java.util.List;

public class RapportDetailEBParProjet extends AppCompatActivity {

    ////////////////////

    LinearLayout linearLayout_list_detail_eb_general;
    LinearLayout linearLayout_details_mvt;
    ListView listView_detail_eb_general;
    ImageView imageView_cache, imageView_montre;

    ////////////////////

    public static String CODEBESOIN = "codeBesoin_valider";
    public static String DESINGATION_BESOIN = "designation_besoin";
    public static String RESTE_ASERVIR = "reste_aservir";

//    ArticleViewModel articleViewModel;
//    DetailBesoinViewModel viewModel;
    DetailPayementRemoteViewModel detailPayementRemoteViewModel;
    RapportDetailEBParProjetAdapter adapter;
    ListView listView_details;

    String codeBesoin="";
    String designation_besoin;
    String reste_aservir="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rapport_detail_eb_par_projet);
        detailPayementRemoteViewModel =
                ViewModelProviders.of(RapportDetailEBParProjet.this).get(DetailPayementRemoteViewModel.class);
//        articleViewModel = new ViewModelProvider(RapportDetailEBParProjet.this).get(ArticleViewModel.class);
        codeBesoin = getIntent().getStringExtra(CODEBESOIN);
        designation_besoin = getIntent().getStringExtra(DESINGATION_BESOIN);
        reste_aservir = getIntent().getStringExtra(RESTE_ASERVIR);
        setUpToolbar();
        TextView total = findViewById(R.id.textView_total_value_rapport_detail_eb_par_projet);
        total.setText("$"+ new DecimalFormat("##.##").format(Double.valueOf(reste_aservir)));

        listView_details = findViewById(R.id.listView_rapport_detail_eb_par_projet);
        adapter = new RapportDetailEBParProjetAdapter(this);

//        Toast.makeText(RapportDetailEBParProjet.this, "code EtatBesoin : " + codeBesoin, Toast.LENGTH_LONG).show();

        detailPayementRemoteViewModel.getDetailBesoinRapport(codeBesoin).observe(RapportDetailEBParProjet.this,
                new Observer<List<DetailsEtatDeBesoinRetrofit>>() {
            @Override
            public void onChanged(List<DetailsEtatDeBesoinRetrofit> detailsEtatDeBesoinRetrofits) {
                adapter.setDetailsBesoin(detailsEtatDeBesoinRetrofits);
                listView_details.setAdapter(adapter);
//                setTotalValue(total, detailsEtatDeBesoinRetrofits);
            }
        });
        ProgressBar progressBar_details_valider = findViewById(R.id.progress_bar_rapport_detail_eb_par_projet);
        detailPayementRemoteViewModel.getIsLoading().observe(RapportDetailEBParProjet.this, new Observer<Boolean>() {
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

        linearLayout_list_detail_eb_general = findViewById(R.id.linear_detail_eb_generale_rapport);
        linearLayout_details_mvt = findViewById(R.id.linear_details_mvt_rapport);
//        listView_detail_eb_general.setVisibility(View.VISIBLE);
        listView_detail_eb_general = findViewById(R.id.listView_detail_eb_generale_rapport);
        imageView_cache = findViewById(R.id.imageView_expand_less_rapport);
        imageView_montre = findViewById(R.id.imageView_expand_rapport);

        PayementDetailEBAdapter payementDetailEBAdapter = new PayementDetailEBAdapter(RapportDetailEBParProjet.this);
        detailPayementRemoteViewModel.getEtatBesoinMvt(codeBesoin).observe(RapportDetailEBParProjet.this,
                new Observer<List<EtatDeBesoinRetrofitDetailEB>>() {
                    @Override
                    public void onChanged(List<EtatDeBesoinRetrofitDetailEB> etatDeBesoinRetrofitDetailEBS) {
                        payementDetailEBAdapter.setDetailEBMvt(etatDeBesoinRetrofitDetailEBS);
                        listView_detail_eb_general.setAdapter(payementDetailEBAdapter);
//                        setTotalValue(total, etatDeBesoinRetrofitDetailEBS);
                        if (!etatDeBesoinRetrofitDetailEBS.isEmpty())
                        {
                            linearLayout_list_detail_eb_general.setVisibility(View.VISIBLE);
                        }
                    }
                });

        imageView_cache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView_montre.setVisibility(View.VISIBLE);
                linearLayout_details_mvt.setVisibility(View.GONE);
                imageView_cache.setVisibility(View.INVISIBLE);
            }
        });
        imageView_montre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView_montre.setVisibility(View.GONE);
                linearLayout_details_mvt.setVisibility(View.VISIBLE);
                imageView_cache.setVisibility(View.VISIBLE);
            }
        });
    }

    void setTotalValue(TextView textView_total_value, List<EtatDeBesoinRetrofitDetailEB> list)
    {
        double total_value =0;
        for (int a = 0; a < list.size(); a++)
        {
//            total_value += (list.get(a).getQte() * list.get(a).getPu());
        }
        textView_total_value.setText("$"+ new DecimalFormat("##.##").format(total_value));
    }

    private void setUpToolbar()
    {
        Toolbar toolbar = findViewById(R.id.app_bar_rapport_detail_eb_par_projet);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_clear_24);
        toolbar.setTitle(""+ designation_besoin);
        if (RapportDetailEBParProjet.this != null) {
            RapportDetailEBParProjet.this.setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RapportDetailEBParProjet.this.finish();
                }
            });
        }
    }
}
