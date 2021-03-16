package com.soft.ssvapp.Fragment_Menu.FillPayements.Details;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.soft.ssvapp.DataRetrofit.DetailsEtatDeBesoin.DetailsEtatDeBesoinRetrofit;
import com.soft.ssvapp.DataRetrofit.EtatDeBesoin.EtatDeBesoinRetrofitDetailEB;
import com.soft.ssvapp.Fragment_Menu.FillPayements.DetailsPayement.remote.DetailPayementRemoteViewModel;
import com.soft.ssvapp.R;

import java.text.DecimalFormat;
import java.util.List;

public class PayementDetails extends AppCompatActivity {

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
    PayementDetailAdapter adapter;
    ListView listView_details;

    String codeBesoin="";
    String designation_besoin;
    String reste_aservir="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payement_details);
        detailPayementRemoteViewModel =
                ViewModelProviders.of(PayementDetails.this).get(DetailPayementRemoteViewModel.class);
//        articleViewModel = new ViewModelProvider(RapportDetailEBParProjet.this).get(ArticleViewModel.class);
        codeBesoin = getIntent().getStringExtra(CODEBESOIN);
        designation_besoin = getIntent().getStringExtra(DESINGATION_BESOIN);
        reste_aservir = getIntent().getStringExtra(RESTE_ASERVIR);
        setUpToolbar();
        TextView total = findViewById(R.id.textView_total_value_detail_payement);
        total.setText("$"+ new DecimalFormat("##.##").format(Double.valueOf(reste_aservir)));

        listView_details = findViewById(R.id.listView_details_payement);
        adapter = new PayementDetailAdapter(this);

        detailPayementRemoteViewModel.getDetailBesoinRapport(codeBesoin).observe(PayementDetails.this,
                new Observer<List<DetailsEtatDeBesoinRetrofit>>() {
            @Override
            public void onChanged(List<DetailsEtatDeBesoinRetrofit> detailsEtatDeBesoinRetrofits) {
                adapter.setDetailsBesoin(detailsEtatDeBesoinRetrofits);
                listView_details.setAdapter(adapter);
//                setTotalValue(total, detailsEtatDeBesoinRetrofits);
            }
        });
        ProgressBar progressBar_details_valider = findViewById(R.id.progress_bar_detail_payement);
        detailPayementRemoteViewModel.getIsLoading().observe(PayementDetails.this, new Observer<Boolean>() {
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

        linearLayout_list_detail_eb_general = findViewById(R.id.linear_detail_eb_generale);
        linearLayout_details_mvt = findViewById(R.id.linear_details_mvt);
//        listView_detail_eb_general.setVisibility(View.VISIBLE);
        listView_detail_eb_general = findViewById(R.id.listView_detail_eb_generale);
        imageView_cache = findViewById(R.id.imageView_expand_less);
        imageView_montre = findViewById(R.id.imageView_expand);

        PayementDetailEBAdapter payementDetailEBAdapter = new PayementDetailEBAdapter(PayementDetails.this);
        detailPayementRemoteViewModel.getEtatBesoinMvt(codeBesoin).observe(PayementDetails.this,
                new Observer<List<EtatDeBesoinRetrofitDetailEB>>() {
            @Override
            public void onChanged(List<EtatDeBesoinRetrofitDetailEB> etatDeBesoinRetrofitDetailEBS) {
                payementDetailEBAdapter.setDetailEBMvt(etatDeBesoinRetrofitDetailEBS);
                listView_detail_eb_general.setAdapter(payementDetailEBAdapter);
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
//                Toast.makeText(PayementDetails.this, "expande less", Toast.LENGTH_LONG).show();
            }
        });
        imageView_montre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView_montre.setVisibility(View.GONE);
                linearLayout_details_mvt.setVisibility(View.VISIBLE);
                imageView_cache.setVisibility(View.VISIBLE);
//                Toast.makeText(PayementDetails.this, "expande more", Toast.LENGTH_LONG).show();
            }
        });
    }

    void setTotalValue(TextView textView_total_value, List<DetailsEtatDeBesoinRetrofit> list)
    {
        double total_value =0;
        for (int a = 0; a < list.size(); a++)
        {
            total_value += (list.get(a).getQte() * list.get(a).getPu());
        }
        textView_total_value.setText("$"+ new DecimalFormat("##.##").format(total_value));
    }

    private void setUpToolbar()
    {
        Toolbar toolbar = findViewById(R.id.app_bar_payement_details);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_clear_24);
        toolbar.setTitle(""+ designation_besoin);
        if (PayementDetails.this != null) {
            PayementDetails.this.setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PayementDetails.this.finish();
                }
            });
        }
    }
}
