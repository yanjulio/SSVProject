package com.soft.ssvapp.Fragment_Menu.Fill_Rapport;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.soft.ssvapp.DataRetrofit.Compte.RapportCompteResponse;
import com.soft.ssvapp.DataRetrofit.Rapport.RapportResponse;
import com.soft.ssvapp.DataRetrofit.Rapport.RapportRetrofitRepository;
import com.soft.ssvapp.Fragment_Menu.CompteAjout.CompteActivity;
import com.soft.ssvapp.Fragment_Menu.CompteAjout.CompterModifier;
import com.soft.ssvapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Balance extends AppCompatActivity {

    public static final String GROUPE_DESIGNATION = "groupe_designation";
    RapportRetrofitRepository rapportRetrofitRepository;
    ProgressBar progressBar;
    BalanceAdapter balance_adapter;
    ListView listView_balance;
    public static final String TYPE_RAPPORT = "type_rapport";
    public static final String GROUPE_COMPTE = "groupe_compte";
    String groupe_compte="";
    String type_rapport = "";
    String groupe_designation = "";
    public static ArrayList<Balance_objet> list = new ArrayList<>();
    BalanceViewModel balanceViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        rapportRetrofitRepository = RapportRetrofitRepository.getInstance();
        progressBar = findViewById(R.id.progress_bar_balance);
        type_rapport = getIntent().getStringExtra(TYPE_RAPPORT);
        groupe_compte = getIntent().getStringExtra(GROUPE_COMPTE);
        groupe_designation = getIntent().getStringExtra(GROUPE_DESIGNATION);

        balance_adapter = new BalanceAdapter(Balance.this, type_rapport);
        listView_balance = findViewById(R.id.listView_balance);

        balanceViewModel = ViewModelProviders.of(Balance.this).get(BalanceViewModel.class);

        setUpToolbar();

        if (type_rapport.equals("Balance"))
        {
            balance(groupe_compte);
        }
        else if (type_rapport.equals("Banque"))
        {
            banque();
        }
        else if (type_rapport.equals("Clients"))
        {
            clients();
        }
        else if (type_rapport.equals("Livre caisse"))
        {
            livreCaisse();
        }
        else if(type_rapport.equals("Caisse seconaire"))
        {
            caisse_secondaire();
        }
        else
        {
            //todo: put the acceuil api
        }

        balanceViewModel.getIsLoading().observe(Balance.this, new Observer<Boolean>() {
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
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_balance, menu);
        MenuItem menuItem_add =
                menu.findItem(R.id.item_add_balance);
        if (type_rapport.equals("Clients") || type_rapport.equals("Balance"))
        {
            menuItem_add.setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.item_add_balance:
                if (type_rapport.equals("Clients"))
                {
                    startActivityForResult(new Intent(Balance.this, CompteActivity.class)
                            .putExtra(CompteActivity.GROUPE_COMPTE, "411"), 8);
                }
                else if (type_rapport.equals("Balance"))
                {
                    startActivityForResult(new Intent(Balance.this, CompteActivity.class)
                            .putExtra(CompteActivity.GROUPE_COMPTE, groupe_compte), 8);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_OK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 6 && resultCode == RESULT_OK)
        {
            if (type_rapport.equals("Balance"))
            {
                balance(groupe_compte);
                if (list.size() == 0)
                {
                    finish();
                }
            }
            else if (type_rapport.equals("Banque"))
            {
                banque();
            }
            else if (type_rapport.equals("Clients"))
            {
                clients();
            }
            else if (type_rapport.equals("Livre caisse"))
            {
                livreCaisse();
            }
            else if(type_rapport.equals("Caisse seconaire"))
            {
                caisse_secondaire();
            }
        }
        else if (requestCode == 8 && resultCode == RESULT_OK)
        {
            if (type_rapport.equals("Balance"))
            {
                balance(groupe_compte);
            }
            else if (type_rapport.equals("Clients"))
            {
                clients();
            }
        }
    }

    public void ModifierCompte(RapportCompteResponse rapportCompteResponse)
    {
        CompterModifier compterModifier = new CompterModifier(rapportCompteResponse);
        compterModifier.show(getSupportFragmentManager(), "modifier");
    }

    public void balance(String groupe_compte)
    {
        balanceViewModel.getMutableBalance(groupe_compte).observe(Balance.this, new Observer<List<Balance_objet>>() {
            @Override
            public void onChanged(List<Balance_objet> balance_objets) {
                list.addAll(balance_objets);
                balance_adapter.setBalance(balance_objets);
                listView_balance.setAdapter(balance_adapter);
            }
        });
    }

//    public void balance(String groupe_compte)
//    {
//        Call<List<RapportResponse>> call_balance = rapportRetrofitRepository.rapportConnexion().balanceParGroupe(groupe_compte);
//        progressBar.setVisibility(View.VISIBLE);
//        call_balance.enqueue(new Callback<List<RapportResponse>>() {
//            @Override
//            public void onResponse(Call<List<RapportResponse>> call, Response<List<RapportResponse>> response) {
//                if (response.isSuccessful())
//                {
//                    progressBar.setVisibility(View.GONE);
//                    List<Balance_objet> list_local = new ArrayList<>();
//                    for (int a = 0; a < response.body().size(); a++)
//                    {
//                        Balance_objet balance_objet = new Balance_objet(
//                                response.body().get(a).getNumeCompte(),
//                                response.body().get(a).getDesignationCompte(),
//                                response.body().get(a).getSolde(),
//                                response.body().get(a).getDesignationGroupe(),
//                                response.body().get(a).getGroupeCompte());
//                        list_local.add(balance_objet);
//                    }
//                    list.addAll(list_local);
//                    balance_adapter.setBalance(list_local);
//                    listView_balance.setAdapter(balance_adapter);
//                }
//                else
//                {
//                    switch (response.code())
//                    {
//                        case 404:
//                            Toast.makeText(Balance.this,"server not found.", Toast.LENGTH_LONG).show();
//                            break;
//                        case 500:
//                            Toast.makeText(Balance.this, "server broken.", Toast.LENGTH_LONG).show();
//                            break;
//                        default:
//                            Toast.makeText(Balance.this, "unknown problem.", Toast.LENGTH_LONG).show();
//                            break;
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<RapportResponse>> call, Throwable t) {
//                progressBar.setVisibility(View.GONE);
//                Toast.makeText(Balance.this, "Connexion Problem.", Toast.LENGTH_LONG).show();
//            }
//        });
//    }

    public void livreCaisse()
    {
        Call<List<RapportResponse>> call_livreCaisse = rapportRetrofitRepository.rapportConnexion().balanceParGroupe("571");
        progressBar.setVisibility(View.VISIBLE);
        call_livreCaisse.enqueue(new Callback<List<RapportResponse>>() {
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
//                    list.addAll(list_local);
                    balance_adapter.setBalance(list_local);
                    listView_balance.setAdapter(balance_adapter);
                }
                else
                {
                    switch (response.code())
                    {
                        case 404:
                            Toast.makeText(Balance.this,"server not found.", Toast.LENGTH_LONG).show();
                            break;
                        case 500:
                            Toast.makeText(Balance.this, "server broken.", Toast.LENGTH_LONG).show();
                            break;
                        default:
                            Toast.makeText(Balance.this, "unknown problem.", Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<RapportResponse>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(Balance.this, "Connexion Problem.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void banque()
    {
        Call<List<RapportResponse>> call_banque = rapportRetrofitRepository.rapportConnexion().balanceParGroupe("521");
        progressBar.setVisibility(View.VISIBLE);
        call_banque.enqueue(new Callback<List<RapportResponse>>() {
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
//                    list.addAll(list_local);
                    balance_adapter.setBalance(list_local);
                    listView_balance.setAdapter(balance_adapter);
                }
                else
                {
                    switch (response.code())
                    {
                        case 404:
                            Toast.makeText(Balance.this,"server not found.", Toast.LENGTH_LONG).show();
                            break;
                        case 500:
                            Toast.makeText(Balance.this, "server broken.", Toast.LENGTH_LONG).show();
                            break;
                        default:
                            Toast.makeText(Balance.this, "unknown problem.", Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<RapportResponse>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(Balance.this, "Connexion Problem.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void clients()
    {
        Call<List<RapportResponse>> call_clients = rapportRetrofitRepository.rapportConnexion().balanceParGroupe("411");
        progressBar.setVisibility(View.VISIBLE);
        call_clients.enqueue(new Callback<List<RapportResponse>>() {
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
//                    list.addAll(list_local);
                    balance_adapter.setBalance(list_local);
                    listView_balance.setAdapter(balance_adapter);
                }
                else
                {
                    switch (response.code())
                    {
                        case 404:
                            Toast.makeText(Balance.this,"server not found.", Toast.LENGTH_LONG).show();
                            break;
                        case 500:
                            Toast.makeText(Balance.this, "server broken.", Toast.LENGTH_LONG).show();
                            break;
                        default:
                            Toast.makeText(Balance.this, "unknown problem.", Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<RapportResponse>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(Balance.this, "Connexion Problem.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void caisse_secondaire()
    {
        Call<List<RapportResponse>> call_clients = rapportRetrofitRepository.rapportConnexion().balanceParGroupe("572");
        progressBar.setVisibility(View.VISIBLE);
        call_clients.enqueue(new Callback<List<RapportResponse>>() {
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
//                    list.addAll(list_local);
                    balance_adapter.setBalance(list_local);
                    listView_balance.setAdapter(balance_adapter);
                }
                else
                {
                    switch (response.code())
                    {
                        case 404:
                            Toast.makeText(Balance.this,"server not found.", Toast.LENGTH_LONG).show();
                            break;
                        case 500:
                            Toast.makeText(Balance.this, "server broken.", Toast.LENGTH_LONG).show();
                            break;
                        default:
                            Toast.makeText(Balance.this, "unknown problem.", Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<RapportResponse>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(Balance.this, "Connexion Problem.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setUpToolbar()
    {
        Toolbar toolbar = findViewById(R.id.app_bar);
        if (Balance.this != null)
        {
            Balance.this.setSupportActionBar(toolbar);
            if (type_rapport.equals("Balance"))
            {
                toolbar.setTitle(""+groupe_designation);
            }
            else
            {
                toolbar.setTitle(""+type_rapport);
            }
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setResult(RESULT_OK);
                    finish();
                    Balance.this.finish();
                }
            });
        }
    }
}
