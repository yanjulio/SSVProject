package com.soft.ssvapp.Fragment_Menu.Navigation;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.soft.ssvapp.Data.Entity_Besoin;
import com.soft.ssvapp.Data.Entity_ProjectWithEntity_Besoin;
import com.soft.ssvapp.DataRetrofit.DetailsEtatDeBesoin.DetailsBesoinSerialize;
import com.soft.ssvapp.DataRetrofit.DetailsEtatDeBesoin.DetailsEtatDeBesoinRetrofit;
import com.soft.ssvapp.DataRetrofit.DetailsEtatDeBesoin.DetailsEtatDeBesoinRetrofitViewModel;
import com.soft.ssvapp.DataRetrofit.EtatDeBesoin.EtatDeBesoinRepositoryRetrofit;
import com.soft.ssvapp.DataRetrofit.EtatDeBesoin.EtatDeBesoinRetrofitViewModel;
import com.soft.ssvapp.Fragment_Menu.Fill_EtatBesoin.Besoin_Adapter;
import com.soft.ssvapp.Fragment_Menu.Fill_EtatBesoin.Besoin_View_model;
import com.soft.ssvapp.Fragment_Menu.Fill_EtatBesoin.DetailBesoinViewModel;
import com.soft.ssvapp.Fragment_Menu.Fill_EtatBesoin.Details_Besoin;
import com.soft.ssvapp.Fragment_Menu.Fill_EtatBesoin.Nouveau_Besoin;
import com.soft.ssvapp.Login;
import com.soft.ssvapp.R;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Etat_Besoin extends AppCompatActivity {

    EtatDeBesoinRepositoryRetrofit etatDeBesoinRepositoryRetrofit;
    public static ArrayList<Entity_ProjectWithEntity_Besoin> arrayList_locale_valider = new ArrayList<Entity_ProjectWithEntity_Besoin>();

    Besoin_Adapter adapter;
    Besoin_View_model view_model; // pour les etat de besoin
    DetailBesoinViewModel detailBesoinViewModel;

    EtatDeBesoinRetrofitViewModel retrofitViewModelBesoin;
    DetailsEtatDeBesoinRetrofitViewModel retrofitViewModelDetailsBesoin;
    ArrayList<DetailsEtatDeBesoinRetrofit> detailsRetrofit = new ArrayList<>();

    public static String BESOIN_KIND = "besoin_kind";
    public static String CODE_PROJECT = "code_projet";
    public static String DESIGANTION_PROJET = "designationProjet";
    String code_project, designationProjet;
    String besoin_kind;
    ProgressBar progressBar;

    SharedPreferences prefs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_etat_besoin);
        setUpToolbar();
        detailBesoinViewModel = ViewModelProviders.of(Etat_Besoin.this).get(DetailBesoinViewModel.class);
        prefs = getSharedPreferences("lOGIN_USER", MODE_PRIVATE);
        String initialUtilisateur = prefs.getString(Login.USER_NAME, "").substring(0,3);
        etatDeBesoinRepositoryRetrofit = EtatDeBesoinRepositoryRetrofit.getInstance();
        code_project = getIntent().getStringExtra(CODE_PROJECT);
        designationProjet = getIntent().getStringExtra(DESIGANTION_PROJET);
        this.setTitle(designationProjet);

        progressBar = findViewById(R.id.progress_bar_etat_besoin);

        // for the DetailsBesoin
        retrofitViewModelDetailsBesoin = ViewModelProviders.of(Etat_Besoin.this).get(DetailsEtatDeBesoinRetrofitViewModel.class);
        retrofitViewModelDetailsBesoin.init();

        TextView etat_besoin = findViewById(R.id.genre_projects_list_etatBesoin);
        besoin_kind = getIntent().getStringExtra(BESOIN_KIND);
        etat_besoin.setText(besoin_kind);

        retrofitViewModelBesoin = ViewModelProviders.of(Etat_Besoin.this).get(EtatDeBesoinRetrofitViewModel.class);
        retrofitViewModelBesoin.init();

        final ListView listView = findViewById(R.id.list_besoin_etatBesoin);
        final FloatingActionButton float_nouveau_etatBesoin = findViewById(R.id.float_add_etatBesoin);
        adapter = new Besoin_Adapter(this, besoin_kind, code_project, designationProjet, initialUtilisateur);

        view_model = ViewModelProviders.of(this).get(Besoin_View_model.class);
        view_model.init(code_project, besoin_kind);
        if (besoin_kind.equals("Valides."))
        {
            float_nouveau_etatBesoin.setVisibility(View.INVISIBLE);
            view_model.getValiderBesoin().observe(this, new Observer<List<Entity_ProjectWithEntity_Besoin>>() {
                @Override
                public void onChanged(List<Entity_ProjectWithEntity_Besoin> entity_besoins) {
                    adapter.setBesoin(entity_besoins);
                    listView.setAdapter(adapter);
                }
            });
        }
        else
        {
            view_model.getNouveauBesoin().observe(this, new Observer<List<Entity_ProjectWithEntity_Besoin>>() {
                @Override
                public void onChanged(List<Entity_ProjectWithEntity_Besoin> entity_besoins) {
                    adapter.setBesoin(entity_besoins);
                    listView.setAdapter(adapter);
                }
            });
        }

        float_nouveau_etatBesoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int siz = view_model.getDernierBesoin() + 1;
                if (siz > 0)
                {
                    startActivityForResult(new Intent(Etat_Besoin.this, Nouveau_Besoin.class)
                            .putExtra(Nouveau_Besoin.CODE_BESOIN,  "EB" + siz)
                            .putExtra(Nouveau_Besoin.CODE_PROJECT, code_project), 4);
                }
            }
        });

        retrofitViewModelDetailsBesoin.getResponse().observe(Etat_Besoin.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(Etat_Besoin.this, s, Toast.LENGTH_LONG).show();
            }
        });

        view_model.getIsLoading().observe(Etat_Besoin.this, new Observer<Boolean>() {
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

        detailBesoinViewModel.isLoaging().observe(Etat_Besoin.this, new Observer<Boolean>() {
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

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 4 && resultCode == RESULT_OK)
        {
            if (data != null)
            {
                String codeBesoin = data.getStringExtra(Nouveau_Besoin.CODE_BESOIN);
                String codeProjet = data.getStringExtra(Nouveau_Besoin.BESOIN_CODE_PROJET);
                String codeLinge = data.getStringExtra(Nouveau_Besoin.CODE_LIGNE);
                String demandeur = data.getStringExtra(Nouveau_Besoin.BESOIN_DEMANDEUR);
                String date_emission = data.getStringExtra(Nouveau_Besoin.DATE_EMISSION);
                String designation = data.getStringExtra(Nouveau_Besoin.BESOIN_DESCRIPTION);

                // je fais l'enregistrement de l'etat de besoin
                Entity_Besoin entity_besoin = new Entity_Besoin(codeBesoin, designation, codeProjet, demandeur,
                        date_emission, "2020-08-14T09:19:03.992Z", "2020-08-14T09:19:03.992Z",
                        "", 0, "");
                entity_besoin.setEtat_besoin_envoyer(0);
                entity_besoin.setCodeLigne(codeLinge);
                view_model.insert(entity_besoin);

            }
            else
            {
                Toast.makeText(Etat_Besoin.this, "le data est vide.", Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == 6 && resultCode == RESULT_OK)
        {
            if (data != null)
            {
                ArrayList<DetailsBesoinSerialize> serializeArrayList = new ArrayList<>();
                serializeArrayList = (ArrayList<DetailsBesoinSerialize>) data.getSerializableExtra(Details_Besoin.LISTSERIALIZE);
                popInDetailsBesoin(serializeArrayList);
            }
        }
    }

    public void popInDetailsBesoin(ArrayList<DetailsBesoinSerialize> list_serialize)
    {
        detailsRetrofit.clear();
        for (int a = 0; a < list_serialize.size(); a++)
        {
            detailsRetrofit.add(
                    new DetailsEtatDeBesoinRetrofit(
                            list_serialize.get(a).getCodeEtatdeBesoin(),
                            list_serialize.get(a).getCodeArticle(),
                            list_serialize.get(a).getCodeLigne(),
                            list_serialize.get(a).getLibelleDetail(),
                            list_serialize.get(a).getQte(),
                            list_serialize.get(a).getPu(),
                            list_serialize.get(a).getSortie(),
                            list_serialize.get(a).getEntree()));
        }
    }

    public void createDetailsBesoinOnline(String codeEtatDeBesoin, String nouveauCodeEtatDeBesoin)
    {
        detailBesoinViewModel.init(codeEtatDeBesoin);
        for (int a = 0; a < detailBesoinViewModel.getList_toputOnline().size(); a++)
        {
            retrofitViewModelDetailsBesoin.CreateDetailsBesoin(
                    new DetailsEtatDeBesoinRetrofit(
                            nouveauCodeEtatDeBesoin,
                            detailBesoinViewModel.getList_toputOnline().get(a).getDetailBesoin().getCodeArticle(),
                            detailBesoinViewModel.getList_toputOnline().get(a).getDetailBesoin().getCodeLigne(),
                            detailBesoinViewModel.getList_toputOnline().get(a).getDetailBesoin().getLibelleDetail(),
                            detailBesoinViewModel.getList_toputOnline().get(a).getDetailBesoin().getQte(),
                            detailBesoinViewModel.getList_toputOnline().get(a).getDetailBesoin().getPu(),
                            0.0,
                            0.0
                    ));
        }
    }

    public void insert(Entity_Besoin entity_besoin)
    {
        view_model.insert(entity_besoin);
    }

    public void update_etat(Entity_Besoin entity_besoin)
    {
        view_model.update_codeEtatBesoin(entity_besoin);
    }

    public void deleteEtatBesoin(Entity_Besoin entity_besoin)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Voulez-vous supprimer ce Besoin");
        builder.setCancelable(true);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                view_model.delete(entity_besoin);
            }
        });
        builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    private void setUpToolbar()
    {
        Toolbar toolbar = findViewById(R.id.app_bar);
        toolbar.setTitle(designationProjet);
        if (Etat_Besoin.this != null)
        {
            Etat_Besoin.this.setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Etat_Besoin.this.finish();
                }
            });
        }
    }
}
