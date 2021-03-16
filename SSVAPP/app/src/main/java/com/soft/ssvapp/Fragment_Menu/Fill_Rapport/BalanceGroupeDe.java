package com.soft.ssvapp.Fragment_Menu.Fill_Rapport;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.soft.ssvapp.DataRetrofit.Rapport.RapportResponse;
import com.soft.ssvapp.DataRetrofit.Rapport.RapportRetrofitRepository;
import com.soft.ssvapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BalanceGroupeDe extends AppCompatActivity {

    RapportRetrofitRepository rapportRetrofitRepository;
    ProgressBar progressBar;
    BalanceGroupeDeAdapter balance_adapter;
    ListView listView_balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_groupe_de);

        rapportRetrofitRepository = RapportRetrofitRepository.getInstance();
        progressBar = findViewById(R.id.progress_bar_balance_groupe_de);
        balance_adapter = new BalanceGroupeDeAdapter(BalanceGroupeDe.this);
        listView_balance = findViewById(R.id.listView_balance_groupe_de);

        balance_groupe_de();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 7 && resultCode == RESULT_OK)
        {
            balance_groupe_de();
        }
    }

    public void balance_groupe_de()
    {
        Call<List<RapportResponse>> call_balance = rapportRetrofitRepository.rapportConnexion().balanceDeGroupe();
        progressBar.setVisibility(View.VISIBLE);
        call_balance.enqueue(new Callback<List<RapportResponse>>() {
            @Override
            public void onResponse(Call<List<RapportResponse>> call, Response<List<RapportResponse>> response) {
                if (response.isSuccessful())
                {
                    progressBar.setVisibility(View.GONE);
                    List<Balance_objet> list_local = new ArrayList<>();
                    for (int a = 0; a < response.body().size(); a++)
                    {
                        Balance_objet balance_objet = new Balance_objet(
                                response.body().get(a).getNumeCompte(),
                                response.body().get(a).getDesignationCompte(),
                                response.body().get(a).getSolde(),
                                response.body().get(a).getDesignationGroupe(),
                                response.body().get(a).getGroupeCompte());
                        list_local.add(balance_objet);
                    }
                    balance_adapter.setBalance(list_local);
                    listView_balance.setAdapter(balance_adapter);
                }
                else
                {
                    switch (response.code())
                    {
                        case 404:
                            Toast.makeText(BalanceGroupeDe.this,"server not found.", Toast.LENGTH_LONG).show();
                            break;
                        case 500:
                            Toast.makeText(BalanceGroupeDe.this, "server broken.", Toast.LENGTH_LONG).show();
                            break;
                        default:
                            Toast.makeText(BalanceGroupeDe.this, "unknown problem.", Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<RapportResponse>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(BalanceGroupeDe.this, "Connexion Problem.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
