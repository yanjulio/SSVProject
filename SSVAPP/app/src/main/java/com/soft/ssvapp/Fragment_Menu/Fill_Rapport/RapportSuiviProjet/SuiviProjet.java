package com.soft.ssvapp.Fragment_Menu.Fill_Rapport.RapportSuiviProjet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.soft.ssvapp.DataRetrofit.Rapport.RapportProjetResponse;
import com.soft.ssvapp.DataRetrofit.Rapport.RapportRetrofitRepository;
import com.soft.ssvapp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuiviProjet extends AppCompatActivity {

    RapportRetrofitRepository rapportRetrofitRepository;
    ProgressBar progressBar;
    SuiviProjetAdapter planAdapter;
    ListView listView_plan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suivi_projet);
        rapportRetrofitRepository = RapportRetrofitRepository.getInstance();
        progressBar = findViewById(R.id.progress_bar_plan);
        planAdapter = new SuiviProjetAdapter(SuiviProjet.this);
        listView_plan = findViewById(R.id.listView_balance);

        suiviprojet();
    }

    public void suiviprojet()
    {
        Call<List<RapportProjetResponse>> call_suiviProjet = rapportRetrofitRepository.rapportConnexion().plan_suivi_projet();
        progressBar.setVisibility(View.VISIBLE);
        call_suiviProjet.enqueue(new Callback<List<RapportProjetResponse>>() {
            @Override
            public void onResponse(Call<List<RapportProjetResponse>> call, Response<List<RapportProjetResponse>> response) {
                if (response.isSuccessful())
                {
                    progressBar.setVisibility(View.GONE);
                    planAdapter.setBalance(response.body());
                    listView_plan.setAdapter(planAdapter);
                }
                else
                {
                    switch (response.code())
                    {
                        case 404:
                            Toast.makeText(SuiviProjet.this, "sever not found.", Toast.LENGTH_LONG).show();
                            break;
                        case 500:
                            Toast.makeText(SuiviProjet.this, "server broken.", Toast.LENGTH_LONG).show();
                            break;
                        default:
                            Toast.makeText(SuiviProjet.this, "unknown problem.", Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<RapportProjetResponse>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(SuiviProjet.this, "Connexion problem.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
