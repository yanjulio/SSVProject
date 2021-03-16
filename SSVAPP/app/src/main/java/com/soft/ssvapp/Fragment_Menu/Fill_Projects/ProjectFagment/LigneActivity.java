package com.soft.ssvapp.Fragment_Menu.Fill_Projects.ProjectFagment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.soft.ssvapp.Data.Entity_Ligne;
import com.soft.ssvapp.Data.Entity_ProjectWithEntity_Ligne;
import com.soft.ssvapp.R;

import java.util.ArrayList;
import java.util.List;

public class LigneActivity extends AppCompatActivity {

    ListView listView_ligne;
    Ligne_Adapter ligne_adapter;
    LigneViewModel ligneViewModel;
    int arraySize = 0;
    public static final String CODE_PROJET = "code_projet";
    public static final String DESIGNATION_PROJET = "designation_projet";
    String designation_projet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ligne);
        String project_code = getIntent().getStringExtra(CODE_PROJET);
        designation_projet = getIntent().getStringExtra(DESIGNATION_PROJET);

//        TextView textView_cancel = findViewById(R.id.text_cancel_ligne);
//        textView_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

//        TextView textView_titre_projet = findViewById(R.id.text_titre_ligne);
//        textView_titre_projet.setText("" + designation_projet);
        setUpToolBar();

        // la liste pour les lignes
        listView_ligne = findViewById(R.id.listView_ligne);
        ligne_adapter = new Ligne_Adapter(LigneActivity.this, "Modifier");
        ligneViewModel = ViewModelProviders.of(LigneActivity.this).get(LigneViewModel.class);
        ligneViewModel.init(project_code);
        ligneViewModel.getCodeLigne().observe(LigneActivity.this, new Observer<List<Entity_ProjectWithEntity_Ligne>>() {
            @Override
            public void onChanged(List<Entity_ProjectWithEntity_Ligne> entity_projectWithEntity_lignes) {
                ligne_adapter.setLigne(addListLigne(entity_projectWithEntity_lignes));
                listView_ligne.setAdapter(ligne_adapter);
            }
        });

        FloatingActionButton button_ajouter_ligne = findViewById(R.id.float_add_ligne);
        button_ajouter_ligne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo: je suis la
//                int size = arraySize+1;
                int size = ligneViewModel.NombreLigneParProjet(project_code)+1;
                Ajouter_Ligne_Dialog ajouter_ligne_dialog = new Ajouter_Ligne_Dialog(project_code, size, "Modifier");
                ajouter_ligne_dialog.show(getSupportFragmentManager(), "10");
            }
        });

        ProgressBar progressBar_ligne = findViewById(R.id.progress_bar_ligne);
        ligneViewModel.isLoading().observe(LigneActivity.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean)
                {
                    progressBar_ligne.setVisibility(View.VISIBLE);
                }
                else
                {
                    progressBar_ligne.setVisibility(View.GONE);
                }
            }
        });
    }

    List<Entity_Ligne> addListLigne(List<Entity_ProjectWithEntity_Ligne> list)
    {
        ArrayList<Entity_Ligne> list1_entity = new ArrayList<>();
        list1_entity.clear();
        arraySize = list.size();
        for (int a = 0; a < list.size(); a++)
        {
            Entity_Ligne entity_ligne =
                    new Entity_Ligne(list.get(a).getEntity_ligne().getCodeLigne(), list.get(a).getEntity_ligne().getCodeProject(),
                            list.get(a).getEntity_ligne().getDesignationLigne(), list.get(a).getEntity_ligne().getPrevision());
            entity_ligne.setIdLigne(list.get(a).getEntity_ligne().getIdLigne());
            list1_entity.add(entity_ligne);
        }
        return  list1_entity;
    }

    public void insertLigneRoom(Entity_Ligne entity_ligne)
    {
//        ligneViewModel.insert(entity_ligne);
        ligneViewModel.insertSimpleLigneOnline(entity_ligne);
    }

    public void openLigneDialogRoom(int idVolatile, String codeProjet, String codeLigne,
                                String designationLigne, int idLigne, double prevision, String fromActivity)
    {
        Update_Ligne update_ligne = new Update_Ligne(idVolatile, codeProjet, codeLigne, designationLigne, idLigne, prevision, fromActivity);
        update_ligne.show(getSupportFragmentManager(), "01");
    }

    public void updateLigneRoom(Entity_Ligne entity_ligne)
    {
//        ligneViewModel.update(entity_ligne);
        ligneViewModel.updateSimpleLigneOnline(entity_ligne);
    }

    public void deleteLigneRoom(Entity_Ligne entity_ligne)
    {
//        ligneViewModel.delete(entity_ligne);
        ligneViewModel.deleteSimpleLigneOnline(entity_ligne);
    }

    public void setUpToolBar()
    {
        Toolbar toolbar = findViewById(R.id.app_bar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_clear_24);
        if (LigneActivity.this != null)
        {
            LigneActivity.this.setSupportActionBar(toolbar);
            toolbar.setTitle("" + designation_projet);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LigneActivity.this.finish();
                }
            });
        }
    }
}
