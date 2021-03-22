package com.soft.ssvapp.Fragment_Menu.All_Controleur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.soft.ssvapp.Data.Entity_Compte;
import com.soft.ssvapp.DataRetrofit.MvtCompte.MvtCompteResponse;
import com.soft.ssvapp.DataRetrofit.Operation.OperationRetrofit;
import com.soft.ssvapp.Fragment_Menu.FillCompte.CompteViewModel;
import com.soft.ssvapp.Fragment_Menu.FillPayements.FormulairePayement_Ravitaement_Cpte;
import com.soft.ssvapp.Fragment_Menu.FillPayements.PayementViewModel;
import com.soft.ssvapp.Fragment_Menu.Fill_EtatBesoin.Besoin_View_model;
import com.soft.ssvapp.Fragment_Menu.Navigation.Menus_All;
import com.soft.ssvapp.Login;
import com.soft.ssvapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OperationControle extends AppCompatActivity {

    //todo : Need to change these Views model to the conform ones like
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
    private ArrayList<Entity_Compte> spinner_list_debit = new ArrayList<Entity_Compte>();
    private ArrayList<Entity_Compte> spinner_list_credit = new ArrayList<Entity_Compte>();

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
    String niveauUtilisateur="";
    int numCompteUtilisateur;


    LinearLayout linear_credit, linear_debit;
    TextInputEditText edit_montant, edit_libelle;
    TextInputLayout input_montant;
    TextView textView_date_operation;
    ProgressBar progressBar;

    String current_date="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_controle);

        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); // 'T' HH:mm:ss z"
        current_date = df.format(to_number_format(c));

        besoin_view_model = ViewModelProviders.of(OperationControle.this).get(Besoin_View_model.class);
        payementViewModel = ViewModelProviders.of(OperationControle.this).get(PayementViewModel.class);

        prefs = getSharedPreferences("lOGIN_USER", MODE_PRIVATE);
        username_prefs = prefs.getString(Login.USER_NAME, "");
        niveauUtilisateur = prefs.getString(Login.NIVEAUUTILISATEUR, "");
        numCompteUtilisateur = prefs.getInt(Login.COMPTE, 0);

//        Toast.makeText(this, "Compte utilisteur :" + numCompteUtilisateur, Toast.LENGTH_LONG).show();

        kind_payement = getIntent().getStringExtra(KIND_PAYEMENT);
//        Toast.makeText(OperationControle.this, "kind : " + kind_payement, Toast.LENGTH_LONG).show();
        code_besoin = getIntent().getStringExtra(CODE_BESOIN);
        code_projet = getIntent().getStringExtra(CODE_PROJET);
        dernier_operation = getIntent().getIntExtra(DERNIER_OPERATION, 0);
        montant = getIntent().getStringExtra(MONTANT);
        progressBar = findViewById(R.id.progress_bar_operation_controle);
//        idEtatBesoin = getIntent().getIntExtra(ID_ETATBESOIN, 0);
        compte_projet_credit = getIntent().getIntExtra(COMPTE_LIER_PROJET_CREDIT, 0);
        compte_designation_projet = getIntent().getStringExtra(COMPTE_LIER_DESIGNATION_PROJET);

        setUpToolbar();

//        Calendar c = Calendar.getInstance();
//
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
//        date_emission = df.format(c.getTime());

        linear_debit = findViewById(R.id.linear_debit_operation_controle);
        linear_credit = findViewById(R.id.linear_credit_operation_controle);
        spinner_compte_debit = findViewById(R.id.spinner_compte_debit_operation_controle); // for the debit
        spinner_compte_credit = findViewById(R.id.spinner_compte_credit_operation_controle);// for the credit
        edit_montant = findViewById(R.id.edit_montant_operation_controle);
        input_montant = findViewById(R.id.montant_operation_controle_input);
        edit_libelle = findViewById(R.id.edit_libelle_operation_controle);
        edit_libelle.setText("" + kind_payement);
        textView_date_operation = findViewById(R.id.textView_date_operation_controle);
        textView_date_operation.setText("" + current_date);

        compteViewModel = ViewModelProviders.of(OperationControle.this).get(CompteViewModel.class);
        if (niveauUtilisateur.equals("ADMIN"))
        {
            spinner_compte_debit.setEnabled(true);
            spinner_compte_credit.setEnabled(false);
//            edit_libelle_value = "Approvisionement";
            compteViewModel.getPosteAdminDebitCompte().observe(OperationControle.this,
                    new Observer<List<Entity_Compte>>() {
                        @Override
                        public void onChanged(List<Entity_Compte> entity_comptes) {
                            spinner_list_debit.clear();
                            spinner_list_debit.addAll(entity_comptes);

                            // for the spinner, just call it
                            spinnerValue_debit(spinner_list_debit);
//                            spinnerValue_credit(spinner_list);
                        }
                    });

            compteViewModel.getPosteCreditCompte().observe(OperationControle.this,
                    new Observer<List<Entity_Compte>>() {
                        @Override
                        public void onChanged(List<Entity_Compte> entity_comptes) {
                            spinner_list_credit.clear();
                            spinner_list_credit.addAll(entity_comptes);
                            spinnerValue_credit(spinner_list_credit);
                        }
                    });
        }
        else
        {
            spinner_compte_debit.setEnabled(true);
            spinner_compte_credit.setEnabled(false);
            spinner_numero_compte_debit=0;
//            edit_libelle_value = "Payement";

            compteViewModel.getPosteUserDebitCompte(numCompteUtilisateur).observe(OperationControle.this,
                    new Observer<List<Entity_Compte>>() {
                        @Override
                        public void onChanged(List<Entity_Compte> entity_comptes) {
                            spinner_list_debit.clear();
                            spinner_list_debit.addAll(entity_comptes);

                            // for the spinner, just call it
                            spinnerValue_debit(spinner_list_debit);
//                    spinnerValue_credit(spinner_list);
                        }
                    });
            compteViewModel.getPosteCreditCompte().observe(OperationControle.this,
                    new Observer<List<Entity_Compte>>() {
                        @Override
                        public void onChanged(List<Entity_Compte> entity_comptes) {
                            spinner_list_credit.clear();
                            spinner_list_credit.addAll(entity_comptes);
                            spinnerValue_credit(spinner_list_credit);
                        }
                    });
        }

        edit_montant.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (isPass_word_valid(edit_montant.getText())){
                    input_montant.setError(null);
                }
                    return false;
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

    void spinnerValue_debit(ArrayList<Entity_Compte> list)
    {
        ArrayAdapter adapter_project = new ArrayAdapter(
                OperationControle.this,
                android.R.layout.simple_list_item_1,
                getString(list));
        adapter_project.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_compte_debit.setAdapter(adapter_project);
        for (int i = 0; i < list.size(); i++)
        {
//            if (kind_payement.equals("Payements."))
//            {
//                if (list.get(i).getNumCompte() == 601001)
//                {
//                    spinner_compte_debit.setSelection(i);
//                }
//            }
//            if (kind_payement.equals("Approvisionement."))
//            {
                if (list.get(i).getNumCompte() == 571001)
                {
                    spinner_compte_debit.setSelection(i);
                }
//            }
        }

        spinner_compte_debit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner_numero_compte_debit = list.get(position).getNumCompte();
//                if (kind_payement.equals("Ravitaillement."))
//                {
                    edit_libelle.setText("Ravitaillement du compte " + list.get(position).getDesignationCompte());
//                }
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
                OperationControle.this,
                android.R.layout.simple_list_item_1,
                getString(list));
        adapter_project.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_compte_credit.setAdapter(adapter_project);

        for (int i = 0; i < list.size(); i++)
        {
//            if (kind_payement.equals("Approvisionement."))
//            {
                spinner_compte_credit.setSelection(i);
//            }
//            if (kind_payement.equals("Ravitaillement."))
//            {
//                if (list.get(i).getNumCompte() == 571001)
//                {
//                    spinner_compte_credit.setSelection(i);
//                }
//            }
        }
        spinner_compte_credit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner_numero_compte_credit = list.get(position).getNumCompte();
//                if (kind_payement.equals("Approvisionement."))
//                {
                    edit_libelle.setText("Approvisionement du compte " + list.get(position).getDesignationCompte());
//                }
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
                Toast.makeText(OperationControle.this, "time current : " + current_date, Toast.LENGTH_LONG).show();
                OperationRetrofit operationRetrofit =
                        new OperationRetrofit("OP"+String.valueOf(dernier_operation), edit_libelle.getText().toString(),
                                username_prefs,
                                current_date, current_date, code_besoin);
                if(isPass_word_valid(edit_montant.getText())){

                    MvtCompteResponse mvtCompteResponse_credit = new MvtCompteResponse(String.valueOf(spinner_numero_compte_credit),
                            "OP"+dernier_operation, edit_libelle.getText().toString(), 1, 0,
                            Double.valueOf(edit_montant.getText().toString()), code_projet); // pour le credit
                    MvtCompteResponse mvtCompteResponse_debit = new MvtCompteResponse(String.valueOf(spinner_numero_compte_debit),
                            "OP"+dernier_operation, edit_libelle.getText().toString(), 1,
                            Double.valueOf(edit_montant.getText().toString()), 0, code_projet);
                    // pour enregistrer l'operation, pour le debit.
                    payementViewModel.insertOpCompteOnline(operationRetrofit, mvtCompteResponse_debit,
                            mvtCompteResponse_credit, idEtatBesoin);

                    Intent intent = new Intent();
                    intent.putExtra(ListOperationcontrole.COMPTE, spinner_numero_compte_debit);
                    intent.putExtra(ListOperationcontrole.MATRICULE, "OP"+dernier_operation);
                    intent.putExtra(ListOperationcontrole.MONTANT, Double.valueOf(edit_montant.getText().toString()));
                    intent.putExtra(ListOperationcontrole.DESIGNATION, edit_libelle.getText().toString());
                    intent.putExtra(ListOperationcontrole.DATE, "17/03/2021");
                    setResult(RESULT_OK, intent);
                    finish();
                    input_montant.setError(null);
                }else{
                    input_montant.setError(""+getString(R.string.error_montant));
                    Toast.makeText(
                            OperationControle.this, "Veillez ajouter le montant svp!", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                Toast.makeText(OperationControle.this, "Un problème a subvenu", Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    private boolean isPass_word_valid(Editable editable)
    {
        return editable != null && editable.length() >= 1 && Double.valueOf(editable.toString()) >=1;
    }

    private void setUpToolbar()
    {
        Toolbar toolbar = findViewById(R.id.app_bar_operation_controle);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_clear_24);
        toolbar.setTitle("Opération Poste");
        if (OperationControle.this != null) {
            OperationControle.this.setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(OperationControle.this, "Bien enregistré OperationControle", Toast.LENGTH_LONG);
                    OperationControle.this.finish();
                }
            });
        }
    }
}