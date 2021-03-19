package com.soft.ssvapp.Fragment_Menu.Navigation;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.Toast;
//import android.widget.PopupMenu;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.soft.ssvapp.DataRetrofit.Operation.OperationRepository;
import com.soft.ssvapp.Fragment_Menu.All_Controleur.ListOperationcontrole;
import com.soft.ssvapp.Fragment_Menu.FillArticle.ArticleViewModel;
import com.soft.ssvapp.Fragment_Menu.FillPayements.FormulairePayement_Ravitaement_Cpte;
import com.soft.ssvapp.Login;
import com.soft.ssvapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Menus_All extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    ArticleViewModel articleViewModel;
    LinearLayout linearLayout_rapport;
    LinearLayout linearLayout_projet;
    LinearLayout linearLayout_operation_generales;
    LinearLayout linearLayout_poste_controle;
    SharedPreferences prefs;
    String niveauUtilisateur = null;
    String username = null;
    String password = null;
//    String receiverUtilisateur = null;
    ProgressBar progressBar;

    OperationRepository operationRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.fragment_menus__all);


        operationRepository = OperationRepository.getInstance(); // initialiser la la classe operation repository

        articleViewModel = ViewModelProviders.of(Menus_All.this).get(ArticleViewModel.class);
        articleViewModel.refresh_debut();

        prefs = getSharedPreferences("lOGIN_USER", MODE_PRIVATE);
        niveauUtilisateur = prefs.getString(Login.NIVEAUUTILISATEUR, "");
        username = prefs.getString(Login.USER_NAME, "");
        password = prefs.getString(Login.PASS_WORD, "");

        linearLayout_projet = findViewById(R.id.linear_projet);
        linearLayout_rapport = findViewById(R.id.linear_rapport);
        linearLayout_operation_generales = findViewById(R.id.linear_operation);
        linearLayout_poste_controle = findViewById(R.id.linear_poste_controle);
        progressBar = findViewById(R.id.progress_bar_menu_all);

//        Toast.makeText(Menus_All.this, "Niveau utilisteur :" + niveauUtilisateur, Toast.LENGTH_LONG).show();
        if(niveauUtilisateur.equals("ADMIN"))
        {
            FirebaseMessaging.getInstance().subscribeToTopic(niveauUtilisateur).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    String msg = "Succesful";
                    if (!task.isSuccessful())
                    {
                        msg = "Failed";
                    }
                }
            });
        }
        else
        {
            if (!password.equals(""))
            {
                FirebaseMessaging.getInstance().subscribeToTopic(password).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Succesful";
                        if (!task.isSuccessful())
                        {
                            msg = "Failed";
                        }
                    }
                });
            }
//            linearLayout_rapport.setEnabled(false);
        }

        linearLayout_projet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menus_All.this, Projects.class)
                        .putExtra(Projects.PROJECT_KIND, "Encours."));
//                PopupMenu popup = new PopupMenu(Menus_All.this, v);
//                popup.setOnMenuItemClickListener(Menus_All.this);
//                popup.inflate(R.menu.menu_projects);
//                popup.show();
            }
        });

        linearLayout_rapport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menus_All.this, Rapport.class));
            }
        });

        linearLayout_operation_generales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu_operation = new PopupMenu(Menus_All.this, v);
                popupMenu_operation.setOnMenuItemClickListener(Menus_All.this);
                popupMenu_operation.inflate(R.menu.menu_operation);
                popupMenu_operation.show();
//                Toast.makeText(Menus_All.this,
//                        "Niveau de Operation generale : " + niveauUtilisateur, Toast.LENGTH_LONG).show();
                if (niveauUtilisateur.startsWith("UT"))
                {
                    MenuItem menuItem_comptabilite =
                            popupMenu_operation.getMenu().findItem(R.id.item_comptabilite_operation);
                    menuItem_comptabilite.setVisible(false);
                    MenuItem menuItem_ravitaement  =
                            popupMenu_operation.getMenu().findItem(R.id.item_ravitaement_operation);
                    menuItem_ravitaement.setVisible(false);
                    MenuItem menuItem_approvisionement =
                            popupMenu_operation.getMenu().findItem(R.id.item_approvisionement_operation);
                    menuItem_approvisionement.setVisible(false);
                }
            }
        });

        linearLayout_poste_controle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menus_All.this, ListOperationcontrole.class));
            }
        });

    }

    public boolean onMenuItemClick(MenuItem item)
    {
        int dernier_operation = 0;
        switch (item.getItemId())
        {
//            case R.id.item_nouveau_projet:
////                startActivity(new Intent(MenuFirst.this, Plan_en_cours.class));
//                break;
            case R.id.item_encours_projet:
                startActivity(
                        new Intent(Menus_All.this, Projects.class)
                                .putExtra(Projects.PROJECT_KIND, "Encours."));
                break;
            case R.id.item_cloturer_projet:
                startActivity(
                        new Intent(Menus_All.this, Projects.class)
                                .putExtra(Projects.PROJECT_KIND, "Cloture."));
                break;
            case R.id.item_approvisionement_operation:
                progressBar.setVisibility(View.VISIBLE);
                operationRepository.operationConnexion().dernierOperation().enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if (response.isSuccessful())
                        {
                            progressBar.setVisibility(View.GONE);
                            startActivity(new Intent(Menus_All.this, FormulairePayement_Ravitaement_Cpte.class)
                                    .putExtra(FormulairePayement_Ravitaement_Cpte.KIND_PAYEMENT, "Approvisionement.")
                                    .putExtra(FormulairePayement_Ravitaement_Cpte.CODE_PROJET, "")
                                    .putExtra(FormulairePayement_Ravitaement_Cpte.ID_ETATBESOIN, 1)
                                    .putExtra(FormulairePayement_Ravitaement_Cpte.CODE_BESOIN, "0") //EB01DEFAULT
                                    .putExtra(FormulairePayement_Ravitaement_Cpte.DERNIER_OPERATION, response.body())
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
                        Toast.makeText(Menus_All.this, "Connexion problem.", Toast.LENGTH_LONG).show();
                    }
                });
                break;
            case R.id.item_ravitaement_operation:
                progressBar.setVisibility(View.VISIBLE);
                operationRepository.operationConnexion().dernierOperation().enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if (response.isSuccessful())
                        {
                            progressBar.setVisibility(View.GONE);
                            if (response.body() > 0)
                            {
                                startActivity(new Intent(Menus_All.this, FormulairePayement_Ravitaement_Cpte.class)
                                        .putExtra(FormulairePayement_Ravitaement_Cpte.KIND_PAYEMENT, "Ravitaillement.")
                                        .putExtra(FormulairePayement_Ravitaement_Cpte.CODE_PROJET, "")
                                        .putExtra(FormulairePayement_Ravitaement_Cpte.ID_ETATBESOIN, 1)
                                        .putExtra(FormulairePayement_Ravitaement_Cpte.CODE_BESOIN, "0")
                                        .putExtra(FormulairePayement_Ravitaement_Cpte.DERNIER_OPERATION, response.body()));
                            }
                        }
                        else
                        {
                            Erreur(response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(Menus_All.this, "Connexion problem.", Toast.LENGTH_LONG).show();
                    }
                });
                break;
            case R.id.item_comptabilite_operation:
                progressBar.setVisibility(View.VISIBLE);
                operationRepository.operationConnexion().dernierOperation().enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if (response.isSuccessful())
                        {
                            progressBar.setVisibility(View.GONE);
                            if (response.body() > 0)
                            {
                                startActivity(new Intent(Menus_All.this, FormulairePayement_Ravitaement_Cpte.class)
                                        .putExtra(FormulairePayement_Ravitaement_Cpte.KIND_PAYEMENT, "Comptabilité.")
                                        .putExtra(FormulairePayement_Ravitaement_Cpte.CODE_PROJET, "")
                                        .putExtra(FormulairePayement_Ravitaement_Cpte.ID_ETATBESOIN, 1)
                                        .putExtra(FormulairePayement_Ravitaement_Cpte.CODE_BESOIN, "0")
                                        .putExtra(FormulairePayement_Ravitaement_Cpte.DERNIER_OPERATION, response.body()));
                            }
                        }
                        else
                        {
                            Erreur(response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(Menus_All.this, "Connexion problem.", Toast.LENGTH_LONG).show();
                    }
                });
                break;
        }
        return false;
    }

    private void Erreur(int code){
        switch (code)
        {
            case 404:
                Toast.makeText(Menus_All.this, "server not found.", Toast.LENGTH_LONG).show();
                break;
            case 500:
                Toast.makeText(Menus_All.this, "server broken.", Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(Menus_All.this, "Unknown problem.", Toast.LENGTH_LONG).show();
                break;
        }
    }

}
