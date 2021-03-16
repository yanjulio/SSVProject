package com.soft.ssvapp.Fragment_Menu.Fill_EtatBesoin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.soft.ssvapp.Data.Entity_ProjectWithEntity_Ligne;
import com.soft.ssvapp.Fragment_Menu.Fill_Projects.ProjectFagment.LigneViewModel;
import com.soft.ssvapp.Login;
import com.soft.ssvapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Nouveau_Besoin extends AppCompatActivity {

    public static String CODE_PROJECT = "code_projet";

    public static String CODE_BESOIN  = "besoin_code";
    public static String BESOIN_CODE_PROJET = "projet_code";
    public static String BESOIN_DEMANDEUR = "besoin_demandeur";
    public static String DATE_EMISSION = "date_emission";
    public static String BESOIN_DESCRIPTION = "besoin_description";
    public static String CODE_LIGNE = "code_ligne";
//    public static String QTE_AJOUTER_DEATILS = "qte_ajouter_details";
//    public static String PU_AJOUTER_DETAILS = "pu_ajouter_details";

//    Spinner spinner_code_projet;
    ArrayList<String> spinner_list = new ArrayList<String>();
    String code_project_value = "je suis vide..../";
    Besoin_View_model view_model;
    String code_besoin;

    TextInputEditText edit_demandeur;
    EditText edit_designation;
    String date_emission;

    // apropos du ligne
    LigneViewModel view_model_ligne;
    private Spinner spinner_code_ligne;
    private String codeLigne ="";
    private ArrayList<Entity_ProjectWithEntity_Ligne> spinner_list_ligne = new ArrayList<Entity_ProjectWithEntity_Ligne>();

    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nouveau__besoin);
        setUpToolbar();
        code_project_value = getIntent().getStringExtra(CODE_PROJECT);

        code_besoin = getIntent().getStringExtra(CODE_BESOIN);

        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 'T'hh:mm:ss
        date_emission = df.format(c.getTime());
//        Toast.makeText(this, "date " + date_emission, Toast.LENGTH_LONG).show();
        prefs = getSharedPreferences("lOGIN_USER", MODE_PRIVATE);
        String username = prefs.getString(Login.USER_NAME,"");
        edit_demandeur = findViewById(R.id.edit_demandeur_besoin);
        edit_demandeur.setText(""+ username);
        edit_designation = findViewById(R.id.edit_new_descriptions_besoin);

        view_model = ViewModelProviders.of(this).get(Besoin_View_model.class);

        spinner_code_ligne  = findViewById(R.id.spinner_code_ligne_nouveaux_besoin);

//        spinner_code_ligne = findViewById(R.id.spinner_code_ligne);
        view_model_ligne = ViewModelProviders.of(this).get(LigneViewModel.class);
        view_model_ligne.init(code_project_value);
        view_model_ligne.getCodeLigne().observe(this, new Observer<List<Entity_ProjectWithEntity_Ligne>>() {
            @Override
            public void onChanged(List<Entity_ProjectWithEntity_Ligne> codeLigne) {
                spinner_list_ligne.clear();
                spinner_list_ligne.addAll(codeLigne);
                // for the spinner
                spinnerValue();
            }
        });

    }

    void spinnerValue()
    {
        ArrayAdapter adapter_project = new ArrayAdapter(
                Nouveau_Besoin.this,
                android.R.layout.simple_list_item_1,
                getString(spinner_list_ligne));
        adapter_project.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_code_ligne.setAdapter(adapter_project);

        spinner_code_ligne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                codeLigne = spinner_list_ligne.get(position).getEntity_ligne().getCodeLigne();
                edit_designation.setText("Etat de besoin " + spinner_list_ligne.get(position).getEntity_ligne().getDesignationLigne());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    ArrayList<String> getString(ArrayList<Entity_ProjectWithEntity_Ligne> list)
    {
        ArrayList<String> list_local = new ArrayList<>();
        list_local.clear();
        for (int a = 0; a < list.size(); a++)
        {
            list_local.add(list.get(a).getEntity_ligne().getDesignationLigne());
        }
        return list_local;
    }

//    void sendDetailResultat(String libelle_detail, double qte, double pu)
//    {
//        libelle_ajouter_details = libelle_detail;
//        qte_ajouter_details = qte;
//        pu_ajouter_details = pu;
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_nouveau_besoin, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.item_save_besoin:

                // j'envoie les donnees pour etre  enregistrer dans l'activite precedent
                Intent intent = new Intent();
                intent.putExtra(BESOIN_CODE_PROJET, code_project_value);
                intent.putExtra(CODE_BESOIN, code_besoin);
                intent.putExtra(CODE_LIGNE, codeLigne);
                intent.putExtra(BESOIN_DEMANDEUR, edit_demandeur.getText().toString());
                intent.putExtra(BESOIN_DESCRIPTION, edit_designation.getText().toString());
                intent.putExtra(DATE_EMISSION, date_emission);
                setResult(RESULT_OK, intent);

                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

//    void getSpinnerValue(String code)
//    {
//        code_project_value = code;
//    }

//    void spinnerValue()
//    {
//        ArrayAdapter adapter_project = new ArrayAdapter(
//                this,
//                android.R.layout.simple_list_item_1,
//                spinner_list);
//        adapter_project.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner_code_projet.setAdapter(adapter_project);
//
//        spinner_code_projet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                getSpinnerValue(spinner_list.get(position));
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//    }

    private void setUpToolbar()
    {
        Toolbar toolbar = findViewById(R.id.app_bar_besoin);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_clear_24);
        if (Nouveau_Besoin.this != null) {
            Nouveau_Besoin.this.setSupportActionBar(toolbar);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Nouveau_Besoin.this.finish();
                }
            });
        }
    }
}
