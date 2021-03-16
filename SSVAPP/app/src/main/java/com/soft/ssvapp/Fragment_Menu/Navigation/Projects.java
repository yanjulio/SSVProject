package com.soft.ssvapp.Fragment_Menu.Navigation;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.soft.ssvapp.Data.Entity_Ligne;
import com.soft.ssvapp.Data.Entity_Project;
import com.soft.ssvapp.DataRetrofit.Ligne.LigneRepository;
import com.soft.ssvapp.DataRetrofit.Ligne.LigneRetrofit;
import com.soft.ssvapp.DataRetrofit.Ligne.LigneRetrofitViewModel;
import com.soft.ssvapp.DataRetrofit.ProjetRetrofit.ProjetRetrofitRespository;
import com.soft.ssvapp.DataRetrofit.ProjetRetrofit.ProjetRetrofitViewModel;
import com.soft.ssvapp.Fragment_Menu.FillCompte.CompteViewModel;
import com.soft.ssvapp.Fragment_Menu.Fill_Projects.ProjectFagment.Entity_Ligne_Serializable;
import com.soft.ssvapp.Fragment_Menu.Fill_Projects.ProjectFagment.ErrorDeConnexionProjet;
import com.soft.ssvapp.Fragment_Menu.Fill_Projects.ProjectFagment.LigneViewModel;
import com.soft.ssvapp.Fragment_Menu.Fill_Projects.ProjectFagment.Nouveau_Projet;
import com.soft.ssvapp.Fragment_Menu.Fill_Projects.ProjectFagment.ProjectViewModel;
import com.soft.ssvapp.Fragment_Menu.Fill_Projects.ProjectFagment.Projects_Encours_Adapter;
import com.soft.ssvapp.Login;
import com.soft.ssvapp.R;
//import com.soft.kp_batiment_project.ViewModels.ProjectViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.internal.operators.observable.ObservableElementAt;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Projects extends AppCompatActivity {
    public static final String COMPTE_CLIENT = "compte_client";
    ProjectViewModel viewModel;
    LigneViewModel viewModelLigne;
    ProgressBar progressBar;
    String code;
    String designation;
    String lieu;
    String chef_projet;
    String date_debut;
    String date_fin;
    int compte_client;
    int numero_compte;
    ArrayList<Entity_Ligne> arrayList_getLigne = new ArrayList<>();

    ProjetRetrofitViewModel retrofitViewModel;
    LigneRetrofitViewModel retrofitLigneViewModel;

    // the boolean fot checking if there is a new value added
    public static String CODE_PROJET = "code";
    public static String DESIGNATION_PROJET = "designation";
    public static String LIEU_PROJET = "lieu";
    public static String CHEF_PROJET = "chef";
    public static String DATEDEBUT_PROJET = "debut";
    public static String DATEFIN_PROJET = "fin";
    public static String NUMERO_COMPTE = "numero_compte";
    public static String ARRAYLIGNE = "tLigneData";

    public static String PROJECT_KIND = "project_kind";
    ListView listView_project_encours;
    Projects_Encours_Adapter adapter;
    public static ArrayList<Entity_Project> list_objects = new ArrayList<>();

    ProjetRetrofitRespository projetRespository; // online to enqueue
    LigneRepository ligneRepository; // online to enqueue
    String response_repository_projet = "";
    String response_repository_ligne = "";

    CompteViewModel viewModel_compte;

    SharedPreferences prefs;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.fragment_projects);
        setUpToolbar();
        prefs = getSharedPreferences("lOGIN_USER", MODE_PRIVATE);
        String niveauUtilisateur = prefs.getString(Login.NIVEAUUTILISATEUR, "");
        progressBar = findViewById(R.id.progress_bar_project);
//        Toast.makeText(Projects.this, "niveau utilisateur : " + niveauUtilisateur, Toast.LENGTH_LONG).show();

        // this will try to insert if the compte entity is null;
        viewModel_compte = ViewModelProviders.of(Projects.this).get(CompteViewModel.class);
        // the online respository
        projetRespository = ProjetRetrofitRespository.getInstance();

        ligneRepository = LigneRepository.getInstance();

        TextView projects = findViewById(R.id.genre_projects_list);
        final FloatingActionButton float_nouveau_projet = findViewById(R.id.float_add_project);
        final String project_kind = getIntent().getStringExtra(PROJECT_KIND);
        projects.setText(project_kind);

        retrofitLigneViewModel = ViewModelProviders.of(Projects.this).get(LigneRetrofitViewModel.class);
        retrofitLigneViewModel.init();

        retrofitViewModel = ViewModelProviders.of(Projects.this).get(ProjetRetrofitViewModel.class);
        retrofitViewModel.init();

        listView_project_encours = findViewById(R.id.list_projets_encours);
        adapter = new Projects_Encours_Adapter(Projects.this, project_kind, progressBar);

        viewModelLigne = ViewModelProviders.of(Projects.this).get(LigneViewModel.class);

        viewModel = ViewModelProviders.of(Projects.this).get(ProjectViewModel.class);

        if (project_kind.equals("Cloture."))
        {
            // observe les donnees encours
            viewModel.getEndProjects().observe(this, new Observer<List<Entity_Project>>() {
                @Override
                public void onChanged(List<Entity_Project> entity_projects) {
                    adapter.notifyDataSetChanged();
                    adapter.setProject(entity_projects);
                    listView_project_encours.setAdapter(adapter);
                }
            });
            float_nouveau_projet.setVisibility(View.INVISIBLE);
        }
        else
        {
            // observe les donnees cloturer
            viewModel.getOngoigProjects().observe(this, new Observer<List<Entity_Project>>() {
                @Override
                public void onChanged(List<Entity_Project> entity_projects) {
                    adapter.notifyDataSetChanged();
                    adapter.setProject(entity_projects);
                    listView_project_encours.setAdapter(adapter);
                }
            });
            if (!niveauUtilisateur.equals("ADMIN"))
            {
                float_nouveau_projet.setVisibility(View.INVISIBLE);
            }
        }

        viewModel.getIsLoding().observe(Projects.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean)
                {
                    progressBar.setVisibility(View.VISIBLE);
                }
                else
                {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        float_nouveau_projet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                projetRespository.projetConnexion().getNombreProjet().enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        Toast.makeText(Projects.this, " le nombre de projets est : " +
                                response.body(), Toast.LENGTH_LONG).show();
                        if (response.isSuccessful())
                        {
                            int siz = response.body()+ 1;
                            startActivityForResult(new Intent(Projects.this, Nouveau_Projet.class)
                                    .putExtra(Nouveau_Projet.CODE_PROJET, "PRO" + siz), 12);
                        }
                        else
                        {
                            switch (response.code())
                            {
                                case 404:
                                    Toast.makeText(Projects.this, "server not found",  Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(Projects.this, ErrorDeConnexionProjet.class)
                                            .putExtra(ErrorDeConnexionProjet.ERRORTYPE, "server not found."));
                                    break;
                                case 500:
                                    Toast.makeText(Projects.this, "server broken", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(Projects.this, ErrorDeConnexionProjet.class)
                                            .putExtra(ErrorDeConnexionProjet.ERRORTYPE, "server broken."));
                                    break;
                                default:
                                    Toast.makeText(Projects.this, "unknown error occured", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(Projects.this, ErrorDeConnexionProjet.class)
                                            .putExtra(ErrorDeConnexionProjet.ERRORTYPE, "Erreur inconnue."));
                                    break;
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        finish();
                        startActivity(new Intent(Projects.this, ErrorDeConnexionProjet.class)
                        .putExtra(ErrorDeConnexionProjet.ERRORTYPE, "Probleme de connexion, verifier votre connexion et réessayer."));
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 12 && resultCode == RESULT_OK)
        {
            // pour ajouter un nouveau projet
            if (data!=null)
            {
                code = data.getStringExtra(CODE_PROJET);
                designation = data.getStringExtra(DESIGNATION_PROJET);
                lieu = data.getStringExtra(LIEU_PROJET);
                chef_projet = data.getStringExtra(CHEF_PROJET);
                numero_compte = data.getIntExtra(NUMERO_COMPTE, 0);
                date_debut = data.getStringExtra(DATEDEBUT_PROJET);
                date_fin = data.getStringExtra(DATEFIN_PROJET);

                Entity_Project entity_project =
                        new Entity_Project(code, designation, date_debut, date_fin, lieu, chef_projet, 0, numero_compte,0);
                entity_project.setEtat_envoyer(0);

                viewModel.insert(entity_project);

                ArrayList<Entity_Ligne_Serializable> entity = new ArrayList<>();
                entity = (ArrayList<Entity_Ligne_Serializable>)data.getSerializableExtra(ARRAYLIGNE);
                getLigneToInsert(entity); // this get all the ligne information from the nouveauProject class
                for (int i = 0 ; i < entity.size(); i++)
                {
                    Entity_Ligne en =
                            new Entity_Ligne(entity.get(i).getCodeLigne(), entity.get(i).getCodeProject(),
                                    entity.get(i).getDesignationLigne(), entity.get(i).getProvision());
                    en.setIdLigne(entity.get(i).getIdLigne());
                    insertDbLigne(en);
                }
            }
        }
        if (requestCode == 1 && resultCode == RESULT_OK)
        {
            // Pour la modification
            if (data != null)
            {
                code = data.getStringExtra(CODE_PROJET);
                designation = data.getStringExtra(DESIGNATION_PROJET);
                lieu = data.getStringExtra(LIEU_PROJET);
                chef_projet = data.getStringExtra(CHEF_PROJET);
//                numero_compte = data.getIntExtra(NUMERO_COMPTE, 0); // TODO : POUR LE MOMENT JE MET PAR DEFAUT
                date_debut = data.getStringExtra(DATEDEBUT_PROJET);
                date_fin = data.getStringExtra(DATEFIN_PROJET);
                compte_client = data.getIntExtra(COMPTE_CLIENT, 0);
                viewModel.update_custom(new Entity_Project(code, designation, date_debut, date_fin, lieu, chef_projet,
                        0, 71001, compte_client));
            }
        }
    }

    // getting it from the adapter
    public void insert(Entity_Project entity_project)
    {
        viewModel.insert(entity_project);
    }

    // getting it from the adapter
//    public void update(Entity_Project entity_project)
//    {
//        viewModel.update(entity_project);
//    }

    public Boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getSystemService((Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    void getLigneToInsert(ArrayList<Entity_Ligne_Serializable> serializable)
    {
        arrayList_getLigne.clear();
        for (int a = 0; a < serializable.size(); a++)
        {
            arrayList_getLigne.add(
                    new Entity_Ligne(serializable.get(a).getCodeLigne(),
                            serializable.get(a).getCodeProject(), serializable.get(a).getDesignationLigne(),
                            serializable.get(a).getProvision()));
        }
    }

    public void createLigne()
    {
        for (int a= 0; a < arrayList_getLigne.size(); a++)
        {
            ligneRepository.ligneConnexion().createLigne(new LigneRetrofit(
                    arrayList_getLigne.get(a).getCodeLigne(),
                    arrayList_getLigne.get(a).getCodeProject(),
                    arrayList_getLigne.get(a).getDesignationLigne(),
                    arrayList_getLigne.get(a).getPrevision())).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    response_repository_ligne = "done.";
                    if (response.isSuccessful())
                    {
                        response_repository_ligne = "Enregistrement effectué.";
                    }
                    else
                    {
                        switch (response.code())
                        {
                            case 404:
                                response_repository_ligne = "Veillez verifier votre addresse svp!";
                                break;
                            case 500:
                                response_repository_ligne = "Erreur Serveur!";
                                break;
                            default:
                                response_repository_ligne = "Erreur inconnue";
                        }
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    response_repository_ligne = "Erreur de connexion";
                }
            });
        }
    }

    void insertDbLigne(Entity_Ligne list_entity)
    {
//        long response = 0;
        viewModelLigne.insert(list_entity);
    }

    public void update_etat(Entity_Project entity_project)
    {
        viewModel.update(entity_project);
        viewModel.init();
    }

    public void deleteProject(Entity_Project entity_project)
    {
        viewModelLigne = ViewModelProviders.of(Projects.this).get(LigneViewModel.class);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Voulez-vous supprimer ce Projet");
        builder.setCancelable(true);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                viewModel.delete(entity_project);
                viewModelLigne.deleteCustom(entity_project.getCodeProject());
            }
        });
        builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    void show_progress(ProgressBar progressBar)
    {
        progressBar.setVisibility(View.VISIBLE);
    }

    void hid_progress(ProgressBar progressBar)
    {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem search_item =menu.findItem(R.id.item_research);
        SearchView searchView = (SearchView)search_item.getActionView();
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String new_text) {
//                adapter.filter(new_text);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String new_text) {
//                adapter.filter(new_text);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.item_research:
                //
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.app_bar);
        if (Projects.this != null) {
            Projects.this.setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Projects.this.finish();
                }
            });
        }
    }

//    void getListViewSizeProjectEncours(ListView myListView, Context context, ArrayList<Projects_list_Objects> list) {
//        Projects_Encours_Adapter adapter = new Projects_Encours_Adapter(context, list, "");
//        if (adapter == null) {
//            //do nothing return null
//            return;
//        }
//        //set listAdapter in loop for getting final size
//        int totalHeight = 0;
//        for (int size = 0; size < adapter.getCount(); size++) {
//            View listItem = adapter.getView(size, null, myListView);
//            listItem.measure(0, 0);
//            totalHeight += listItem.getMeasuredHeight();
//        }
//        //setting listview item in adapter
//        ViewGroup.LayoutParams params = myListView.getLayoutParams();
//        params.height = totalHeight + (myListView.getDividerHeight() * (adapter.getCount() - 1));
//        myListView.setLayoutParams(params);
//        // print height of adapter on log
//        Log.i("height of listItem:", String.valueOf(totalHeight));
//    }

}
