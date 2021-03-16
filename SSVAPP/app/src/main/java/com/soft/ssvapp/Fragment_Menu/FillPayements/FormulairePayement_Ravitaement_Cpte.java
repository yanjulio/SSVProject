package com.soft.ssvapp.Fragment_Menu.FillPayements;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.SharedPreferences;
import android.graphics.Path;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.core.annotations.Line;
import com.google.android.material.textfield.TextInputEditText;
import com.soft.ssvapp.Data.Entity_Compte;
import com.soft.ssvapp.DataRetrofit.MvtCompte.MvtCompteResponse;
import com.soft.ssvapp.DataRetrofit.Operation.OperationRetrofit;
import com.soft.ssvapp.Fragment_Menu.FillCompte.CompteViewModel;
import com.soft.ssvapp.Fragment_Menu.Fill_EtatBesoin.Besoin_View_model;
import com.soft.ssvapp.Login;
import com.soft.ssvapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FormulairePayement_Ravitaement_Cpte extends AppCompatActivity {

    PayementViewModel payementViewModel;
    Besoin_View_model besoin_view_model;

    String date_emission;
    public static final String KIND_PAYEMENT = "kind_payement";
    public static final String CODE_BESOIN = "code_besoin";
    public static final String CODE_PROJET = "code_projet";
    public static final String DERNIER_OPERATION = "dernier_operation";
    public static final String ID_ETATBESOIN = "id_etat_besoin";
    public static final String COMPTE_LIER_PROJET_CREDIT = "compte_projet";
    public static final String COMPTE_LIER_DESIGNATION_PROJET = "compte_designation_projet";
    public static final String MONTANT = "montant";

    // spinner for the debit
    Spinner spinner_compte_debit;
    int spinner_numero_compte_debit;
    private ArrayList<Entity_Compte> spinner_list = new ArrayList<Entity_Compte>();
    private ArrayList<Entity_Compte> spinner_list_credit_ravitaement = new ArrayList<Entity_Compte>();
    private ArrayList<Entity_Compte> spinner_list_debit_approvisionement = new ArrayList<Entity_Compte>();
    private ArrayList<Entity_Compte> spinner_list_credit_payement = new ArrayList<Entity_Compte>();

    // spinner for the credit
    Spinner spinner_compte_credit;
    int spinner_numero_compte_credit;
    String edit_libelle_value_credit = "";
    String edit_libelle_value_debit = "";

    CompteViewModel compteViewModel;
    String kind_payement="", code_besoin="", code_projet="", compte_designation_projet="", montant="";
    int compte_projet_credit;
    int dernier_operation;
    int idEtatBesoin;
    int numero_compte_general;

    // SharePreference reading data from login
    SharedPreferences prefs;
    String username_prefs;

    LinearLayout linear_credit, linear_debit;
    TextInputEditText edit_montant, edit_libelle;
    TextView textView_date_operation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulaire_payement__ravitaement__cpte);
        besoin_view_model = ViewModelProviders.of(FormulairePayement_Ravitaement_Cpte.this).get(Besoin_View_model.class);
        payementViewModel = ViewModelProviders.of(FormulairePayement_Ravitaement_Cpte.this).get(PayementViewModel.class);

        prefs = getSharedPreferences("lOGIN_USER", MODE_PRIVATE);
        username_prefs = prefs.getString(Login.USER_NAME, "");

        kind_payement = getIntent().getStringExtra(KIND_PAYEMENT);
        code_besoin = getIntent().getStringExtra(CODE_BESOIN);
        code_projet = getIntent().getStringExtra(CODE_PROJET);
        dernier_operation = getIntent().getIntExtra(DERNIER_OPERATION, 0);
        montant = getIntent().getStringExtra(MONTANT);
//        idEtatBesoin = getIntent().getIntExtra(ID_ETATBESOIN, 0);
        compte_projet_credit = getIntent().getIntExtra(COMPTE_LIER_PROJET_CREDIT, 0);
        compte_designation_projet = getIntent().getStringExtra(COMPTE_LIER_DESIGNATION_PROJET);

        setUpToolbar();

        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        date_emission = df.format(c.getTime());

        linear_debit = findViewById(R.id.linear_debit);
        linear_credit = findViewById(R.id.linear_credit);
        spinner_compte_debit = findViewById(R.id.spinner_compte_debit); // for the debit
        spinner_compte_credit = findViewById(R.id.spinner_compte_credit);// for the credit
        edit_montant = findViewById(R.id.edit_montant_payement);
        if (kind_payement.equals("Payements."))
        {
            Toast.makeText(this , "Reste : " + montant, Toast.LENGTH_LONG).show();
            edit_montant.setText("" + montant);
        }
        edit_libelle = findViewById(R.id.edit_libelle_payement);
        edit_libelle.setText("" + kind_payement);
        textView_date_operation = findViewById(R.id.textView_date_operation_payement);
        textView_date_operation.setText("" + date_emission);

        compteViewModel = ViewModelProviders.of(FormulairePayement_Ravitaement_Cpte.this).get(CompteViewModel.class);
        if (kind_payement.equals("Ravitaillement."))
        {
            spinner_compte_debit.setEnabled(true);
            spinner_compte_credit.setEnabled(false);
            spinner_numero_compte_credit = 0;
            compteViewModel.getGetRavitaementCompte().observe(FormulairePayement_Ravitaement_Cpte.this,
                    new Observer<List<Entity_Compte>>() {
                @Override
                public void onChanged(List<Entity_Compte> entity_comptes) {
                    spinner_list.clear();
                    spinner_list.addAll(entity_comptes);

                    // for the spinner, just call it
                    spinnerValue_debit(spinner_list);
//                    spinnerValue_credit(spinner_list);
                }
            });
            compteViewModel.getGetAllcompte().observe(FormulairePayement_Ravitaement_Cpte.this,
                    new Observer<List<Entity_Compte>>() {
                @Override
                public void onChanged(List<Entity_Compte> entity_comptes) {
                    spinner_list_credit_ravitaement.clear();
                    spinner_list_credit_ravitaement.addAll(entity_comptes);
                    spinnerValue_credit(spinner_list_credit_ravitaement);
                }
            });
        }
        else if (kind_payement.equals("Approvisionement."))
        {
            spinner_compte_debit.setEnabled(false);
            spinner_compte_credit.setEnabled(true);
//            edit_libelle_value = "Approvisionement";
            compteViewModel.getGetApprovisionementCompte().observe(FormulairePayement_Ravitaement_Cpte.this,
                    new Observer<List<Entity_Compte>>() {
                @Override
                public void onChanged(List<Entity_Compte> entity_comptes) {
                    spinner_list.clear();
                    spinner_list.addAll(entity_comptes);

                    // for the spinner, just call it
//                    spinnerValue_debit(spinner_list);
                    spinnerValue_credit(spinner_list);
                }
            });

            compteViewModel.getGetAllcompte().observe(FormulairePayement_Ravitaement_Cpte.this,
                    new Observer<List<Entity_Compte>>() {
                @Override
                public void onChanged(List<Entity_Compte> entity_comptes) {
                    spinner_list_debit_approvisionement.clear();
                    spinner_list_debit_approvisionement.addAll(entity_comptes);
                    spinnerValue_debit(spinner_list_debit_approvisionement);
                }
            });
        }
        else if (kind_payement.equals("Payements."))
        {
            spinner_compte_debit.setEnabled(false);
            spinner_compte_credit.setEnabled(true);
            spinner_numero_compte_debit=0;
//            edit_libelle_value = "Payement";

            compteViewModel.getGetAllcompte().observe(FormulairePayement_Ravitaement_Cpte.this,
                    new Observer<List<Entity_Compte>>() {
                @Override
                public void onChanged(List<Entity_Compte> entity_comptes) {
                    spinner_list.clear();
                    spinner_list.addAll(entity_comptes);

                    // for the spinner, just call it
                    spinnerValue_debit(spinner_list);
//                    spinnerValue_credit(spinner_list);
                }
            });
            compteViewModel.getGetPayements(compte_projet_credit).observe(FormulairePayement_Ravitaement_Cpte.this,
                    new Observer<List<Entity_Compte>>() {
                @Override
                public void onChanged(List<Entity_Compte> entity_comptes) {
                    spinner_list_credit_payement.clear();
                    spinner_list_credit_payement.addAll(entity_comptes);
                    spinnerValue_credit(spinner_list_credit_payement);
                }
            });
        }
        else
        {
            compteViewModel.getGetAllcompte().observe(FormulairePayement_Ravitaement_Cpte.this,
                    new Observer<List<Entity_Compte>>() {
                @Override
                public void onChanged(List<Entity_Compte> entity_comptes) {
                    spinner_list.clear();
                    spinner_list.addAll(entity_comptes);

                    // for the spinner, just call it
                    spinnerValue_debit(spinner_list);
                    spinnerValue_credit(spinner_list);
                }
            });
        }
    }

    void spinnerValue_debit(ArrayList<Entity_Compte> list)
    {
        ArrayAdapter adapter_project = new ArrayAdapter(
                FormulairePayement_Ravitaement_Cpte.this,
                android.R.layout.simple_list_item_1,
                getString(list));
        adapter_project.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_compte_debit.setAdapter(adapter_project);
        for (int i = 0; i < list.size(); i++)
        {
            if (kind_payement.equals("Payements."))
            {
                if (list.get(i).getNumCompte() == 601001)
                {
                    spinner_compte_debit.setSelection(i);
                }
            }
            if (kind_payement.equals("Approvisionement."))
            {
                if (list.get(i).getNumCompte() == 571001)
                {
                    spinner_compte_debit.setSelection(i);
                }
            }
        }

        spinner_compte_debit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner_numero_compte_debit = list.get(position).getNumCompte();
                if (kind_payement.equals("Ravitaillement."))
                {
                    edit_libelle.setText("Ravitaillement du compte " + list.get(position).getDesignationCompte());
                }
//                if (kind_payement.equals("Comptablié."))
//                {
//                    edit_libelle.setText("Comptabilité du compte " + list.get(position).getDesignationCompte() + " pour ");
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    void spinnerValue_credit(ArrayList<Entity_Compte> list)
    {
        ArrayAdapter adapter_project = new ArrayAdapter(
                FormulairePayement_Ravitaement_Cpte.this,
                android.R.layout.simple_list_item_1,
                getString(list));
        adapter_project.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_compte_credit.setAdapter(adapter_project);

        for (int i = 0; i < list.size(); i++)
        {
            if (kind_payement.equals("Approvisionement."))
            {
                spinner_compte_credit.setSelection(i);
            }
            if (kind_payement.equals("Ravitaillement."))
            {
                if (list.get(i).getNumCompte() == 571001)
                {
                    spinner_compte_credit.setSelection(i);
                }
            }
        }
        spinner_compte_credit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner_numero_compte_credit = list.get(position).getNumCompte();
                if (kind_payement.equals("Approvisionement."))
                {
                    edit_libelle.setText("Approvisionement du compte " + list.get(position).getDesignationCompte());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    ArrayList<String> getString(ArrayList<Entity_Compte> list)
    {
        ArrayList<String> list_local = new ArrayList<>();
        for (int a = 0; a < list.size(); a++)
        {
            list_local.add(list.get(a).getDesignationCompte());
        }
        return list_local;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_payement, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.item_save_payement:
                OperationRetrofit operationRetrofit =
                        new OperationRetrofit("OP"+String.valueOf(dernier_operation), edit_libelle.getText().toString(),
                                username_prefs,
                                date_emission, date_emission, code_besoin);
                if (kind_payement.equals("Payements."))
                {
                    MvtCompteResponse mvtCompteResponse_credit = new MvtCompteResponse(String.valueOf(compte_projet_credit),
                            "OP"+dernier_operation, edit_libelle.getText().toString(), 1, 0,
                            Double.valueOf(edit_montant.getText().toString()), code_projet);
                    // pour enregistrer l'operation, pour le credit.
                    MvtCompteResponse mvtCompteResponse_debit = new MvtCompteResponse(String.valueOf(spinner_numero_compte_debit),
                            "OP"+dernier_operation, edit_libelle.getText().toString(), 1,
                            Double.valueOf(edit_montant.getText().toString()), 0, code_projet); // pour le debit
                    payementViewModel.insertOpCompteOnline(operationRetrofit, mvtCompteResponse_debit,
                            mvtCompteResponse_credit, idEtatBesoin);
                    setResult(RESULT_OK);
                }
                else if (kind_payement.equals("Ravitaillement."))
                {
                    MvtCompteResponse mvtCompteResponse_credit = new MvtCompteResponse(String.valueOf(spinner_numero_compte_credit),
                            "OP"+dernier_operation, edit_libelle.getText().toString(), 1, 0,
                            Double.valueOf(edit_montant.getText().toString()), code_projet); // pour le credit
                    MvtCompteResponse mvtCompteResponse_debit = new MvtCompteResponse(String.valueOf(spinner_numero_compte_debit),
                            "OP"+dernier_operation, edit_libelle.getText().toString(), 1,
                            Double.valueOf(edit_montant.getText().toString()), 0, code_projet);
                    payementViewModel.insertOpCompteOnline(operationRetrofit, mvtCompteResponse_debit,
                            mvtCompteResponse_credit, idEtatBesoin);
                }
                else if (kind_payement.equals("Approvisionement."))
                {
                    MvtCompteResponse mvtCompteResponse_credit = new MvtCompteResponse(String.valueOf(spinner_numero_compte_credit),
                            "OP"+dernier_operation, edit_libelle.getText().toString(), 1, 0,
                            Double.valueOf(edit_montant.getText().toString()), code_projet); // pour le credit
                    MvtCompteResponse mvtCompteResponse_debit = new MvtCompteResponse(String.valueOf(spinner_numero_compte_debit),
                            "OP"+dernier_operation, edit_libelle.getText().toString(), 1,
                            Double.valueOf(edit_montant.getText().toString()), 0, code_projet);
                    // pour enregistrer l'operation, pour le debit.
                    payementViewModel.insertOpCompteOnline(operationRetrofit, mvtCompteResponse_debit,
                            mvtCompteResponse_credit, idEtatBesoin);
                }
                else if (kind_payement.equals("Comptabilité.")){
                    MvtCompteResponse mvtCompteResponse_credit = new MvtCompteResponse(String.valueOf(spinner_numero_compte_credit),
                            "OP"+dernier_operation, edit_libelle.getText().toString(), 1, 0,
                            Double.valueOf(edit_montant.getText().toString()), code_projet); // pour le credit
                    MvtCompteResponse mvtCompteResponse_debit = new MvtCompteResponse(String.valueOf(spinner_numero_compte_debit),
                            "OP"+dernier_operation, edit_libelle.getText().toString(), 1,
                            Double.valueOf(edit_montant.getText().toString()), 0, code_projet); // pour le debit
                    payementViewModel.insertOpCompteOnline(operationRetrofit, mvtCompteResponse_debit, mvtCompteResponse_credit,
                            idEtatBesoin);
                }
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpToolbar()
    {
        Toolbar toolbar = findViewById(R.id.app_bar_payement);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_clear_24);
        toolbar.setTitle(kind_payement);
        if (FormulairePayement_Ravitaement_Cpte.this != null) {
            FormulairePayement_Ravitaement_Cpte.this.setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FormulairePayement_Ravitaement_Cpte.this.finish();
                }
            });
        }
    }
}
