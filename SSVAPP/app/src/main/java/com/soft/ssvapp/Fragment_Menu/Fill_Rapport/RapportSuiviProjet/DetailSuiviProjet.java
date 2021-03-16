package com.soft.ssvapp.Fragment_Menu.Fill_Rapport.RapportSuiviProjet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.soft.ssvapp.DataRetrofit.Rapport.RapportDetailProjetResponse;
import com.soft.ssvapp.DataRetrofit.Rapport.RapportRetrofitRepository;
import com.soft.ssvapp.R;

import java.text.DecimalFormat;
import java.util.List;

import io.reactivex.internal.operators.single.SingleDelayWithSingle;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailSuiviProjet extends AppCompatActivity {

    RapportRetrofitRepository rapportRetrofitRepository;
    ProgressBar progressBar_detail_suivi_projet;
    DetailSuiviProjetAdapter detailSuiviProjetAdapter;
    ListView listView_detailSuiviProjet;
    TextView textView_total, textView_consom, textView_prevision;
    public static final String CODE_PROJET = "code_projet";
    public static final String DESIGNATION_PROJET = "designation_projet";
    String code_projet="";
    String designation_projet="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_suivi_projet);
        rapportRetrofitRepository = RapportRetrofitRepository.getInstance();
        code_projet = getIntent().getStringExtra(CODE_PROJET);
        designation_projet = getIntent().getStringExtra(DESIGNATION_PROJET);
//        TextView textView_titre_details_suivi_projet = findViewById(R.id.text_titre_details_suivi_projet_rapport);
//        TextView textView_cancel_details_suivi_projet = findViewById(R.id.text_cancel_details_suivi_projet_rapport);
//        textView_cancel_details_suivi_projet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//        textView_titre_details_suivi_projet.setText("" + code_projet + "/" +designation_projet);
        setUp_appBar();
        progressBar_detail_suivi_projet = findViewById(R.id.progress_bar_detail_suivi_projet_rapport);
        detailSuiviProjetAdapter = new DetailSuiviProjetAdapter(this, code_projet);
        listView_detailSuiviProjet = findViewById(R.id.listView_details_suivi_projet_rapport);
        textView_total = findViewById(R.id.text_total_rapport_projet_detail_suivi_projet);
        textView_prevision = findViewById(R.id.text_prevision_rapport_projet_detail_suivi_projet);
        textView_consom = findViewById(R.id.text_consom_rapport_projet_detail_suivi_projet);

        detailsSuiviprojet(code_projet);
    }

    public void detailsSuiviprojet(String code_projet)
    {
        Call<List<RapportDetailProjetResponse>> call_detailSuivi =
                rapportRetrofitRepository.rapportConnexion().rapportDetailsuiviProjet(code_projet);
        progressBar_detail_suivi_projet.setVisibility(View.VISIBLE);
        call_detailSuivi.enqueue(new Callback<List<RapportDetailProjetResponse>>() {
            @Override
            public void onResponse(Call<List<RapportDetailProjetResponse>> call, Response<List<RapportDetailProjetResponse>> response) {
                if (response.isSuccessful())
                {
                    progressBar_detail_suivi_projet.setVisibility(View.GONE);
                    detailSuiviProjetAdapter.setDetailSuiviProjet(response.body());
                    listView_detailSuiviProjet.setAdapter(detailSuiviProjetAdapter);
                    setTotalLigneParArticle(response.body());
                }
                else
                {
                    switch (response.code())
                    {
                        case 404:
                            Toast.makeText(DetailSuiviProjet.this, "server not found.", Toast.LENGTH_LONG).show();
                            break;
                        case 500:
                            Toast.makeText(DetailSuiviProjet.this, "server broken.", Toast.LENGTH_LONG).show();
                            break;
                        default:
                            Toast.makeText(DetailSuiviProjet.this, "unknown problem.", Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<RapportDetailProjetResponse>> call, Throwable t) {
                progressBar_detail_suivi_projet.setVisibility(View.GONE);
                Toast.makeText(DetailSuiviProjet.this, "Connexion problem.", Toast.LENGTH_LONG).show();
            }
        });
    }

    void setTotalLigneParArticle(List<RapportDetailProjetResponse> list_totauxCons)
    {
        double taux=0;
        double consommation=0;
        double prevision=0;
        for (int a =0; a < list_totauxCons.size(); a++)
        {
            consommation += list_totauxCons.get(a).getTotalConsommation();
            prevision +=list_totauxCons.get(a).gettOtalPrevision();
        }
        taux = (consommation/prevision)*100;
        textView_prevision.setText("$"+ new DecimalFormat("##.##").format(prevision));
        textView_consom.setText("$"+ new DecimalFormat("##.##").format(consommation));
        textView_total.setText("" + new DecimalFormat("##.##").format(taux)+"%");
    }

    public void setUp_appBar()
    {
        Toolbar toolbar = findViewById(R.id.app_bar);
        if (DetailSuiviProjet.this != null)
        {
            DetailSuiviProjet.this.setSupportActionBar(toolbar);
            toolbar.setTitle(code_projet + "/" +designation_projet);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DetailSuiviProjet.this.finish();
                }
            });
        }
    }
}
