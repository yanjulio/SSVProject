package com.soft.ssvapp.Fragment_Menu.All_Controleur;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.soft.ssvapp.DataRetrofit.Operation.OperationRepository;
import com.soft.ssvapp.DataRetrofit.PostControle.PosteControleResponse;
import com.soft.ssvapp.Fragment_Menu.FillPayements.FormulairePayement_Ravitaement_Cpte;
import com.soft.ssvapp.Fragment_Menu.Navigation.Menus_All;
import com.soft.ssvapp.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListOperationcontrole extends AppCompatActivity {

    public static final int REQUESTCODE = 11;
    public static final String MATRICULE="MATRICULE";
    public static final String COMPTE="COMPTE";
    public static final String MONTANT = "MONTANT";
    public static final String DESIGNATION = "DESIGNATION";
    public static final String DATE = "DATE";

    FloatingActionButton float_operation_controle;
    RecyclerView recyclerView_operation_controle;
    ProgressBar progressBar;
    ListOperationControleAdapter adapter;
    OperationRepository operationRepository;

    public static ArrayList<PosteControleResponse> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_operationcontrole);
        setUpToolbar();

        operationRepository = OperationRepository.getInstance();

        float_operation_controle = findViewById(R.id.float_add_operation_controle);
        recyclerView_operation_controle = findViewById(R.id.recycler_operation_controle);
        progressBar = findViewById(R.id.progress_bar_operation_controle);
        adapter = new ListOperationControleAdapter(this);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView_operation_controle.setHasFixedSize(true);
        recyclerView_operation_controle.setLayoutManager(manager);

        adapter.setArrayList_operation_controle(PosteControleResponse.getList());
        recyclerView_operation_controle.setAdapter(adapter);

        float_operation_controle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                operationRepository.operationConnexion().dernierOperation().enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if (response.isSuccessful())
                        {
                            progressBar.setVisibility(View.GONE);
                            startActivityForResult(new Intent(ListOperationcontrole.this, OperationControle.class)
                                    .putExtra(OperationControle.KIND_PAYEMENT, "Approvisionement.")
                                    .putExtra(OperationControle.CODE_PROJET, "")
                                    .putExtra(OperationControle.ID_ETATBESOIN, 1)
                                    .putExtra(OperationControle.CODE_BESOIN, "0") //EB01DEFAULT
                                    .putExtra(OperationControle.DERNIER_OPERATION, response.body()), REQUESTCODE
                            );
                        }
                        else
                        {
                            Erreur(response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(ListOperationcontrole.this, "Problème de connexion", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(ListOperationcontrole.this, "Bien enregistré", Toast.LENGTH_LONG);
        if (requestCode == REQUESTCODE && resultCode == RESULT_OK){
            Toast.makeText(ListOperationcontrole.this, "Bien enregistré", Toast.LENGTH_LONG);
            String designation = data.getStringExtra(DESIGNATION);
            double montant = data.getDoubleExtra(MONTANT, 0);
            int matricule = data.getIntExtra(MATRICULE, 0);
            int compte = data.getIntExtra(COMPTE, 0);
            String date = data.getStringExtra(DATE);
            PosteControleResponse.list.add(new PosteControleResponse(designation, montant, matricule, compte, date));
//            adapter.setArrayList_operation_controle(arrayList);
            recyclerView_operation_controle.setAdapter(adapter);
        }
    }

    private void Erreur(int code){
        switch (code)
        {
            case 404:
                Toast.makeText(ListOperationcontrole.this, "server not found.", Toast.LENGTH_LONG).show();
                break;
            case 500:
                Toast.makeText(ListOperationcontrole.this, "server broken.", Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(ListOperationcontrole.this, "Unknown problem.", Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void setUpToolbar()
    {
        Toolbar toolbar = findViewById(R.id.app_bar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_clear_24);
        toolbar.setTitle("Opération de Controle");
        if (ListOperationcontrole.this != null) {
            ListOperationcontrole.this.setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ListOperationcontrole.this.finish();
                }
            });
        }
    }
}