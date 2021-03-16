package com.soft.ssvapp.Fragment_Menu.All_specificProject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.soft.ssvapp.DataRetrofit.Operation.OperationRepository;
import com.soft.ssvapp.Fragment_Menu.FillArticle.ArticleViewModel;
import com.soft.ssvapp.Fragment_Menu.Fill_Projects.ProjectFagment.LigneViewModel;
import com.soft.ssvapp.Fragment_Menu.Navigation.Etat_Besoin;
import com.soft.ssvapp.Fragment_Menu.Navigation.Operation_Cpte;
import com.soft.ssvapp.Fragment_Menu.Navigation.Payements;
import com.soft.ssvapp.Login;
import com.soft.ssvapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Specific_Project extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

//    LinearLayout linearLayout_rapport;
    LinearLayout linearLayout_dashboard;
    LinearLayout linearLayout_Operations;
    LinearLayout linearLayout_etat_besoin;

    ProgressBar progressBar_refresh;

    SharedPreferences prefs;
    String niveauUtilisateur;

    public static String CODE_PROJECT = "code_project";
    public static String DESIGNATION_PROJECT = "designation_project";
    public static final String COMPTE_LIER_PROJET_CREDIT = "compte_lier_projet";
    public static final String COMPTE_LIER_DESIGNATION_PROJET = "compte_designation_projet";
    public static final String COMPTE_CLIENT = "compte_client";
    String code_projet;
    String designation_project;
    int compte_projet =0;
    String compte_designatioin_projet;
    int compte_client;

    OperationRepository operationRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_specific__project);
        operationRepository = OperationRepository.getInstance(); // initialiser la la classe operation repository

        code_projet = getIntent().getStringExtra(CODE_PROJECT);
        designation_project = getIntent().getStringExtra(DESIGNATION_PROJECT);
        compte_projet = getIntent().getIntExtra(COMPTE_LIER_PROJET_CREDIT, 0);
        compte_designatioin_projet = getIntent().getStringExtra(COMPTE_LIER_DESIGNATION_PROJET);
        compte_client = getIntent().getIntExtra(COMPTE_CLIENT, 0);
//        Toast.makeText(this, "compteClient specific project : " + compte_client, Toast.LENGTH_LONG).show();
        TextView textView_designation = findViewById(R.id.textView_designation_specific_project);
        textView_designation.setText("" + designation_project);

        prefs = getSharedPreferences("lOGIN_USER", MODE_PRIVATE);
        niveauUtilisateur = prefs.getString(Login.NIVEAUUTILISATEUR, "");

        linearLayout_dashboard = findViewById(R.id.linear_dashboard_projet);
        linearLayout_dashboard.setEnabled(false);
        linearLayout_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Specific_Project.this, RapportParProjet.class)
                        .putExtra(RapportParProjet.CODE_PROJET, code_projet)
                        .putExtra(RapportParProjet.COMPTE_PROJET, compte_projet)
                        .putExtra(RapportParProjet.COMPTE_CLIENT, compte_client));
            }
        });

        linearLayout_Operations = findViewById(R.id.lienar_operations_specific_project);
        linearLayout_Operations.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(Specific_Project.this, Payements.class) // this for the payement.
                        .putExtra(Payements.KIND_PAYEMENT, "Payements.")
                        .putExtra(Payements.CODE_PROJET, code_projet)
                        .putExtra(Payements.COMPTE_LIER_PROJET, compte_projet)
                        .putExtra(Payements.COMPTE_LIER_DESIGNATION_PROJET, compte_designatioin_projet)
                        .putExtra(Payements.DESIGNATION_PROJET, designation_project));
            }
        });


        linearLayout_etat_besoin = findViewById(R.id.linear_etat_besoin_specific_project);
        linearLayout_etat_besoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu_etatBesoin = new PopupMenu(Specific_Project.this, v);
                popupMenu_etatBesoin.setOnMenuItemClickListener(Specific_Project.this);
                popupMenu_etatBesoin.inflate(R.menu.menu_etat_besoin);
                popupMenu_etatBesoin.show();
//                MenuItem menuItem_besoin_envoyer = popupMenu_etatBesoin.getMenu().findItem(R.id.item_envoyer_besoin);
//                Toast.makeText(Specific_Project.this, "niveau utilisateur : " + niveauUtilisateur, Toast.LENGTH_LONG).show();
//                if (niveauUtilisateur.equals("ADMIN"))
//                {
//                    menuItem_besoin_envoyer.setVisible(true);
//                }
//                else
//                {
//                    menuItem_besoin_envoyer.setVisible(false);
//                }
            }
        });

        if(niveauUtilisateur.equals("ADMIN"))
        {
            linearLayout_dashboard.setEnabled(true);
        }

        ArticleViewModel articleViewModel = ViewModelProviders.of(Specific_Project.this).get(ArticleViewModel.class);
        LigneViewModel ligneViewModel = ViewModelProviders.of(Specific_Project.this).get(LigneViewModel.class);
        ImageView imageView_refresh = findViewById(R.id.imageView_refresh_specific_project);
        imageView_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                articleViewModel.refresh_debut();
                ligneViewModel.init(code_projet);
            }
        });

        progressBar_refresh = findViewById(R.id.progress_bar_specific_project);
        articleViewModel.getIsLoading().observe(Specific_Project.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean)
                {
                    progressBar_refresh.setVisibility(View.VISIBLE);
                }
                else
                {
                    progressBar_refresh.setVisibility(View.GONE);
                }
            }
        });
    }

    public boolean onMenuItemClick(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.item_nouveau_besoin:
                startActivity(new Intent(Specific_Project.this, Etat_Besoin.class)
                        .putExtra(Etat_Besoin.BESOIN_KIND, "Nouveaux.")
                        .putExtra(Etat_Besoin.CODE_PROJECT, code_projet)
                        .putExtra(Etat_Besoin.DESIGANTION_PROJET, designation_project));
                break;
            case R.id.item_valider_besoin:
                startActivity(new Intent(Specific_Project.this, Etat_Besoin.class)
                        .putExtra(Etat_Besoin.BESOIN_KIND, "Valides.")
                        .putExtra(Etat_Besoin.CODE_PROJECT, code_projet)
                        .putExtra(Etat_Besoin.DESIGANTION_PROJET, designation_project));
                break;
            case R.id.item_envoyer_besoin:
                startActivity(new Intent(Specific_Project.this, Operation_Cpte.class)
                        .putExtra(Operation_Cpte.KIND_OPERATION, "Envoyés.")
                        .putExtra(Operation_Cpte.CODE_PROJECT, code_projet)
                        .putExtra(Operation_Cpte.DESIGNATIONPROJET, designation_project));
                break;
        }
        return false;
    }

}
