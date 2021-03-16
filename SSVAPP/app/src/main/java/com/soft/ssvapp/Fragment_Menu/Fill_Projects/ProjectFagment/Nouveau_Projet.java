package com.soft.ssvapp.Fragment_Menu.Fill_Projects.ProjectFagment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.standalones.ProjectTimeline;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.soft.ssvapp.Data.Entity_Compte;
import com.soft.ssvapp.Data.Entity_Ligne;
import com.soft.ssvapp.Fragment_Menu.FillCompte.CompteViewModel;
import com.soft.ssvapp.Fragment_Menu.Navigation.Projects;
import com.soft.ssvapp.R;
import com.soft.ssvapp.ViewModels.LigneVolatitileViewModel;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import static java.util.Calendar.getInstance;

public class Nouveau_Projet extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    public static String CODE_PROJET = "nouveau_codeprojet";
    LigneViewModel viewModelLigne;
    int arraySize = 0;
    LigneVolatitileViewModel viewModelVolatile;
    Ligne_Adapter adapter;
    ListView listView_lignes;

// for the Date chooser
    private int mYear, mMoth, mDay_of_Moth;
    private int annee, mois, jour;
    DatePickerDialog datePickerDialog;
    Calendar calendar;
    public static String date;
    int click_date_chooser = 0;

    TextInputEditText edit_debut_date;
    TextInputEditText edit_fin_date;
    EditText designation_projet;
    String nouveau_code_projet;
    TextInputEditText edit_chef_projet;
    TextInputEditText edit_lieu;
//    Kp_BatimentDatabaseHelper helper;

    CompteViewModel compteViewModel;
    private ArrayList<Entity_Compte> spinner_list_compte = new ArrayList<Entity_Compte>();
    Spinner spinner_compte;
    int spinner_getValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nouveau__projet);
        setUpToolbar();

        calendar = getInstance();

        mYear = calendar.get(YEAR);
        mMoth = calendar.get(MONTH);
        mDay_of_Moth = calendar.get(DAY_OF_MONTH);

        spinner_compte = findViewById(R.id.spinner_compte_projet);
        compteViewModel = ViewModelProviders.of(Nouveau_Projet.this).get(CompteViewModel.class);
        compteViewModel.getProjetCompte().observe(Nouveau_Projet.this, new Observer<List<Entity_Compte>>() {
            @Override
            public void onChanged(List<Entity_Compte> entity_comptes) {
                spinner_list_compte.clear();
                spinner_list_compte.addAll(entity_comptes);
                spinnerValue(spinner_list_compte);
            }
        });

        nouveau_code_projet = getIntent().getStringExtra(CODE_PROJET);


        edit_chef_projet = findViewById(R.id.edit_projet_supervisor);
        edit_lieu = findViewById(R.id.edit_projet_lieu);

        ImageView imageView_debut = findViewById(R.id.text_debut_nouveau_projet);
        imageView_debut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click_date_chooser = 1;
                click_date_image();
            }
        });
        ImageView imageView_fin = findViewById(R.id.text_fin_nouveau_projet);
        imageView_fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click_date_chooser = 2;
                click_date_image();
            }
        });

        edit_debut_date = findViewById(R.id.edit_projet_debut_date);
        edit_fin_date = findViewById(R.id.edit_projet_fin_date);

        // pour la designation du projet
        designation_projet = findViewById(R.id.edit_new_descriptions_projets);

        // la liste de lignes
        adapter = new Ligne_Adapter(this, "Nouveau");
        listView_lignes = findViewById(R.id.listView_nouveau_ligne);
        // pour les viewModel de ligne
        viewModelLigne = ViewModelProviders.of(Nouveau_Projet.this).get(LigneViewModel.class);
        viewModelLigne.init(nouveau_code_projet);

        viewModelVolatile = ViewModelProviders.of(Nouveau_Projet.this).get(LigneVolatitileViewModel.class);
        viewModelVolatile.init();
        viewModelVolatile.getLigne().observe(this, new Observer<List<Entity_Ligne>>() {
            @Override
            public void onChanged(List<Entity_Ligne> entity_lignes) {
                int sizeList = adapter.setLigne(entity_lignes);
                arraySize = sizeList;
                listView_lignes.setAdapter(adapter);
            }
        });

        // pour enregistrer un noveau projet
        TextView textView_ajouter_ligne = findViewById(R.id.ajouter_nouveau_ligne);
        textView_ajouter_ligne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ajouter_Ligne_Dialog ajouter_ligne_dialog = new Ajouter_Ligne_Dialog(nouveau_code_projet, arraySize+1, "Nouveau");
                ajouter_ligne_dialog.show(getSupportFragmentManager(), "10");
            }
        });
    }

    void spinnerValue(ArrayList<Entity_Compte> list)
    {
        ArrayAdapter adapter_project = new ArrayAdapter(
                Nouveau_Projet.this,
                android.R.layout.simple_list_item_1,
                getString(list));
        adapter_project.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_compte.setAdapter(adapter_project);

        for (int i = 0; i < list.size(); i++)
        {
            if (list.get(i).getNumCompte()==571001)
            {
                spinner_compte.setSelection(i);
            }
        }
        spinner_compte.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner_getValue = list.get(position).getNumCompte();
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

    public void deleteLigne(Entity_Ligne entity_ligne)
    {
        viewModelVolatile.deleteVolatile(entity_ligne);
    }

    public void openLigneDialog(int idVolatile, String codeProjet, String codeLigne,
                                String designationLigne, int idLigne, double prevision, String fromActivity)
    {
        Update_Ligne update_ligne = new Update_Ligne(idVolatile, codeProjet, codeLigne, designationLigne, idLigne, prevision, fromActivity);
        update_ligne.show(getSupportFragmentManager(), "01");
    }

    public void insertLigne(Entity_Ligne entity_ligne)
    {
        viewModelVolatile.addNewValue(entity_ligne);
    }

    public void updateLigne(int id, Entity_Ligne entity_ligne)
    {
        viewModelVolatile.updateVolatile(id, entity_ligne);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_nouve_project, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.item_save_project:
                Projects.CODE_PROJET = nouveau_code_projet;
                Projects.DESIGNATION_PROJET = designation_projet.getText().toString();
                Projects.LIEU_PROJET = edit_lieu.getText().toString();

                Intent intent = new Intent();
                intent.putExtra(Projects.DATEDEBUT_PROJET, edit_debut_date.getText().toString());
                intent.putExtra(Projects.DATEFIN_PROJET, edit_fin_date.getText().toString());
                intent.putExtra(Projects.CHEF_PROJET, edit_chef_projet.getText().toString());
                intent.putExtra(Projects.CODE_PROJET, nouveau_code_projet);
                intent.putExtra(Projects.NUMERO_COMPTE, spinner_getValue);
                intent.putExtra(Projects.DESIGNATION_PROJET, designation_projet.getText().toString());
                intent.putExtra(Projects.LIEU_PROJET, edit_lieu.getText().toString());
                intent.putExtra(Projects.ARRAYLIGNE, viewModelVolatile.getLigne_serializables());
                if (viewModelVolatile.getLigne_serializables().size() != 0)
                {
                    Toast toast = Toast.makeText(Nouveau_Projet.this, "Enregistrement bien faite.", Toast.LENGTH_LONG);
                    toast.show();
                    setResult(RESULT_OK, intent);
                    this.finish();
                    viewModelVolatile.getClear();
                }
                else
                {
                    Toast.makeText(Nouveau_Projet.this, "Veillez créer des lignes svp!", Toast.LENGTH_LONG).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void click_date_image()
    {
        datePickerDialog = DatePickerDialog.newInstance(Nouveau_Projet.this, mYear, mMoth, mDay_of_Moth);
        datePickerDialog.setThemeDark(false);
        datePickerDialog.showYearPickerFirst(false);
        datePickerDialog.setAccentColor(Color.parseColor("#2d612c"));
        datePickerDialog.setTitle("Selectionner la date");
        datePickerDialog.show(getSupportFragmentManager(), "DatePickerDialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        annee = year;
        mois = monthOfYear + 1;
        jour = dayOfMonth;

        if (jour > 9 && mois > 9)date = annee + "-" + mois + "-" + jour;
        if (jour > 9 && !(mois > 9))date = annee + "-0" + mois + "-" + jour;
        if (!(jour > 9) && mois > 9)date = annee + "-" + mois + "-0" + jour;
        if (!(jour > 9) && !(mois > 9))date = annee + "-0" + mois + "-0" + jour;

        if (click_date_chooser == 1)
        {
            edit_debut_date.setText(""+ date);
        }
        else
        {
            edit_fin_date.setText("" + date);
        }
    }

    // to see if there is no null values
    private boolean is_null_value(boolean noErros)
    {
        View rootView = findViewById(android.R.id.content);
//        final List<TextInputLayout> textInputLayouts = Utils.findViewsWidthType(rootView, TextInputLayout.class);
        return noErros;
    }

    private void setUpToolbar()
    {
        Toolbar toolbar = findViewById(R.id.app_bar_project);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_clear_24);
        if (Nouveau_Projet.this != null) {
            Nouveau_Projet.this.setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Nouveau_Projet.this.finish();
                }
            });
        }
    }

    // todo : call this function to collapse in bottom the list
//    public static void getListViewSizeProjectNouveau(ListView myListView, Context context) {
//        Ligne_Adapter adapter = new Ligne_Adapter(context, "Nouveau");
////        Dashboard_Adapter adapter = new Dashboard_Adapter(context, list);
//        if (adapter==null) {
//            //do nothing return null
//            return;
//        }
//        //set listAdapter in loop for getting final size
//        int totalHeight=0;
//        for (int size=0; size < adapter.getCount(); size++) {
//            View listItem=adapter.getView(size, null, myListView);
//            listItem.measure(0, size);
//            totalHeight+=listItem.getMeasuredHeight();
//        }
//        //setting listview item in adapter
//        ViewGroup.LayoutParams params=myListView.getLayoutParams();
//        params.height=totalHeight + (myListView.getDividerHeight() * (adapter.getCount() - 1));
//        myListView.setLayoutParams(params);
//        // print height of adapter on log
//        Log.i("height of listItem:", String.valueOf(totalHeight));
//    }

//    public static void listCast(ListView listView, Context context)
//    {
//        Ligne_Adapter listAdapter = new Ligne_Adapter(context, "nouveau");
//        if (listAdapter == null) {
//            return;
//        }
//
//        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
//        for (int i = 0; i < listAdapter.getCount(); i++) {
//            View listItem = listAdapter.getView(i, null, listView);
//            if (listItem instanceof ViewGroup)
//                listItem.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//            listItem.measure(0, 0);
//            totalHeight += listItem.getMeasuredHeight();
//        }
//
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//        listView.setLayoutParams(params);
//    }
}
