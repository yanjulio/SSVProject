package com.soft.ssvapp.Utilisateurs;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.soft.ssvapp.DataRetrofit.Compte.RapportCompteResponse;
import com.soft.ssvapp.DataRetrofit.UtilisateurResponse;
import com.soft.ssvapp.R;

import java.util.ArrayList;
import java.util.List;

public class ValiderUtilisateur extends AppCompatActivity {

    Spinner spinner_service;
    String affectation="";

    Spinner spinner_compte;
    int compte_value;
    ArrayList<RapportCompteResponse> liste_compte = new ArrayList<>();

    UtilisateurViewModel utilisateurViewModel;

    TextInputEditText edit_nomUtilisateur, edit_motDepass, edit_nomComplet, edit_fonction;

    public static final String ID_UTILISATEUR = "ID_UTILISATEUR";
    public static final String NOM_UTILISATEUR = "NOM_UTILISATEUR";
    public static final String MOT_DEPASSE = "MOT_DEPASSE";
    public static final String DESIGNATION_UTILISATEUR = "DESIGNATION_UTILISATEUR";
    public static final String FONCTION_UTILISATEUR = "FONCTION_UTILISATEUR";
    public static final String ACTIVER = "ACTIVER";
    public static final String SERVICE = "SERVICE";
    public static final String COMPTE = "COMPTE";
    int idUtilisateur, activer_value;
    String nomUtilisateur, motDepasseUtilisateur, designationUtilisateur, fonctionUtilisateur;
    SwitchMaterial switchMaterial_active;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valider_utilisateur);

        idUtilisateur = getIntent().getIntExtra(ID_UTILISATEUR, 0);
        nomUtilisateur = getIntent().getStringExtra(NOM_UTILISATEUR);
        motDepasseUtilisateur = getIntent().getStringExtra(MOT_DEPASSE);
        designationUtilisateur = getIntent().getStringExtra(DESIGNATION_UTILISATEUR);
        fonctionUtilisateur = getIntent().getStringExtra(FONCTION_UTILISATEUR);
        activer_value = getIntent().getIntExtra(ACTIVER, 0);
        affectation = getIntent().getStringExtra(SERVICE);
        compte_value = getIntent().getIntExtra(COMPTE, 0);
//        compte_declaration_value = getIntent().getIntExtra(COMPTE_DECLARATION, 0);

        setUpToolBar(nomUtilisateur);

        utilisateurViewModel = ViewModelProviders.of(ValiderUtilisateur.this).get(UtilisateurViewModel.class);

        edit_nomUtilisateur = findViewById(R.id.edit_nomutilisateur_valider);
        edit_nomUtilisateur.setText("" + nomUtilisateur);
        edit_motDepass = findViewById(R.id.edit_motDePass_valider);
        edit_motDepass.setText("" + motDepasseUtilisateur);
        edit_nomComplet = findViewById(R.id.edit_designation_utilisateur_valider);
        edit_nomComplet.setText("" + designationUtilisateur);
        edit_fonction = findViewById(R.id.edit_fonction_utilisateur_valider);
        edit_fonction.setText("" + fonctionUtilisateur);

        spinner_service = findViewById(R.id.spinner_service_affecter_utilisateur);
        spinner_affecter_utilisateur();

        spinner_compte = findViewById(R.id.spinner_compte_utilisateur);
        utilisateurViewModel.getListCompte().observe(ValiderUtilisateur.this,
                new Observer<List<RapportCompteResponse>>() {
            @Override
            public void onChanged(List<RapportCompteResponse> rapportCompteResponses) {
                liste_compte.clear();
                liste_compte.addAll(rapportCompteResponses);
                spinner_compte_utilisateur(liste_compte);
            }
        });

        switchMaterial_active = findViewById(R.id.switch_activer_utilisateur);
        activer_swith(activer_value);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activer_utilisateur, menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.item_save_activer_utilisateur:
                if (switchMaterial_active.isChecked())
                {
                    activer_value = 1;
                }
                else
                {
                    activer_value = 0;
                }
                UtilisateurResponse utilisateurResponse =
                        new UtilisateurResponse(idUtilisateur, edit_nomUtilisateur.getText().toString(),
                                edit_nomComplet.getText().toString(), edit_motDepass.getText().toString(),
                                edit_fonction.getText().toString(), affectation,
                                compte_value, activer_value);
                utilisateurViewModel.modifierUtilisateur(utilisateurResponse);
                ValiderUtilisateur.this.setResult(RESULT_OK);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void activer_swith(int activer)
    {
        if (activer == 1)
        {
            switchMaterial_active.setChecked(true);
            activer_value = 1;
        }
    }

    private void spinner_affecter_utilisateur()
    {
        String[] list_local;
        list_local = getResources().getStringArray(R.array.compte_array);
        ArrayAdapter affecter = new ArrayAdapter(
                ValiderUtilisateur.this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.compte_array)
        );
        affecter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_service.setAdapter(affecter);

        for (int a =0; a < list_local.length; a++)
        {
            if (list_local[a].equals(affectation))
            {
                affectation = list_local[a];
                spinner_service.setSelection(a);
            }
        }

        spinner_service.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                affectation = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void spinner_compte_utilisateur(ArrayList<RapportCompteResponse> list)
    {
        ArrayAdapter compte = new ArrayAdapter(
                ValiderUtilisateur.this,
                android.R.layout.simple_list_item_1,
                getStringCompte(list)
        );
        compte.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_compte.setAdapter(compte);

        for (int a =0; a < list.size(); a++)
        {
            if (list.get(a).getNumCompte() == compte_value)
            {
                compte_value = list.get(a).getNumCompte();
                spinner_compte.setSelection(a);
            }
        }

        spinner_compte.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                compte_value = list.get(position).getNumCompte();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    ArrayList<String> getStringCompte(ArrayList<RapportCompteResponse> list_compte)
    {
        ArrayList<String> list_local = new ArrayList<>();
        list_local.clear();
        for (int a=0; a < list_compte.size(); a++)
        {
            list_local.add(list_compte.get(a).getDesignationCompte());
        }
        return list_local;
    }

    public void setUpToolBar(String nomUtilisateur) {
        Toolbar toolbar = findViewById(R.id.app_bar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_clear_24);
        if (ValiderUtilisateur.this != null) {
            ValiderUtilisateur.this.setSupportActionBar(toolbar);
            toolbar.setTitle("" + nomUtilisateur);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ValiderUtilisateur.this.finish();
                }
            });
        }
    }
}