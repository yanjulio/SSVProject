package com.soft.ssvapp.Fragment_Menu.All_Controleur;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.soft.ssvapp.Data.EntityOperationWithEntityMvtCompte;
import com.soft.ssvapp.Data.Entity_MvtCompte;
import com.soft.ssvapp.Data.Entity_Operation;
import com.soft.ssvapp.DataRetrofit.Operation.OperationRepository;
import com.soft.ssvapp.DataRetrofit.PostControle.PosteControleResponse;
import com.soft.ssvapp.Fragment_Menu.FillPayements.FormulairePayement_Ravitaement_Cpte;
import com.soft.ssvapp.Fragment_Menu.FillPayements.PayementViewModel;
import com.soft.ssvapp.Fragment_Menu.Navigation.Menus_All;
import com.soft.ssvapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListOperationcontrole extends AppCompatActivity {

    public static final int REQUESTCODE = 11;
    public static final String MATRICULE="MATRICULE";
    public static final String COMPTE="COMPTE";
    public static final String NIVEAUTILISATEUR="NIVEAUTILISATEUR";
    public static final String MONTANT = "MONTANT";
    public static final String DESIGNATION = "DESIGNATION";
    public static final String DATE = "DATE";

    FloatingActionButton float_operation_controle;
    RecyclerView recyclerView_operation_controle;
    ProgressBar progressBar;
    TextView textView_total;
    ListOperationControleAdapter adapter;
    OperationRepository operationRepository;

    PayementViewModel payementViewModel;
//    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_operationcontrole);
        setUpToolbar();

        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); // 'T' HH:mm:ss z"
        String current_date = df.format(to_number_format(c));

        operationRepository = OperationRepository.getInstance();
        payementViewModel = ViewModelProviders.of(ListOperationcontrole.this).get(PayementViewModel.class);

        float_operation_controle = findViewById(R.id.float_add_operation_controle);
        recyclerView_operation_controle = findViewById(R.id.recycler_operation_controle);
        progressBar = findViewById(R.id.progress_bar_list_operation_controle);
        textView_total = findViewById(R.id.total_list_operation_controle);
        adapter = new ListOperationControleAdapter(this, textView_total);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView_operation_controle.setHasFixedSize(true);
        recyclerView_operation_controle.setLayoutManager(manager);

//        Toast.makeText(this, "Current date is : " + current_date, Toast.LENGTH_LONG).show();

        payementViewModel.getRecenteOperation(current_date).observe(this, new Observer<List<EntityOperationWithEntityMvtCompte>>() {
            @Override
            public void onChanged(List<EntityOperationWithEntityMvtCompte> entityOperationWithEntityMvtComptes) {
                adapter.setArrayList_operation_controle(entityOperationWithEntityMvtComptes);
                recyclerView_operation_controle.setAdapter(adapter);
            }
        });

//        payementViewModel.getRecentMvt().observe(this, new Observer<List<Entity_MvtCompte>>() {
//            @Override
//            public void onChanged(List<Entity_MvtCompte> entity_mvtComptes) {
//                adapter.setArrayList_operation_controle(entity_mvtComptes);
//                recyclerView_operation_controle.setAdapter(adapter);
//            }
//        });

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

    public Date to_number_format(Calendar calendar)
    {
        Date d = calendar.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd "); // 'T' HH:mm:ss z"
        try {
            d = df.parse(d.toString()); // calendar.getTime().toString()
        }catch (ParseException ex)
        {
            Log.v("Exception", ex.getLocalizedMessage());
        }
        return d;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTCODE && resultCode == RESULT_OK){
//            Toast.makeText(ListOperationcontrole.this, "Bien enregistré", Toast.LENGTH_LONG).show();
            String designation = data.getStringExtra(DESIGNATION);
            double montant = data.getDoubleExtra(MONTANT, 0);
            String matricule = data.getStringExtra(MATRICULE);
            int compte = data.getIntExtra(COMPTE, 0);
            String niveau_user = data.getStringExtra(NIVEAUTILISATEUR);
            String date = data.getStringExtra(DATE);
//            adapter.setArrayList_operation_controle(payementViewModel.insertMvt(),);
//            recyclerView_operation_controle.setAdapter(adapter);
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
        toolbar.setNavigationIcon(R.drawable.ic_baseline_west_24);
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