package com.soft.ssvapp.Fragment_Menu.Fill_EtatBesoin;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.soft.ssvapp.Data.Entity_DetailBesoin;
import com.soft.ssvapp.Data.Entity_DetailBesoinWithEntity_Article;
import com.soft.ssvapp.DataRetrofit.DetailsEtatDeBesoin.DetailsBesoinSerialize;
import com.soft.ssvapp.DataRetrofit.DetailsEtatDeBesoin.DetailsEtatDeBesoinRepository;
import com.soft.ssvapp.R;

import org.w3c.dom.Text;

import java.io.DataOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Details_Besoin extends AppCompatActivity {

    DetailsEtatDeBesoinRepository detailsEtatDeBesoinRepository;
    DetailBesoinViewModel viewModel;
    DetailsBesoinAdapter adapter;
    String codeEtatDeBesoin = "";
    String codeEtatDeBesoinOnline ="";
    String initialUtilisateur = "";
    String designation_besoin ="";

    public static String BESOIN_KIND = "besoin_kind";
    public static String ETAT_BESOIN = "etat_Besoin";
    public static String BESOIN_ID = "besoin_id";
    public static String BESOIN_CODE = "besoin_code";
    public static String DESIGNATION_BESOIN = "desingationtion_besoin";
    public static String BESOIN_CODE_ONLINE = "besoin_code_online";
    public static String INITIAL_UTILISATEUR = "initial_utilsateur";
    public static String CODE_PROJET = "codeProjet";
    public static String CODE_LIGNE = "code_ligne";
    public static String LISTSERIALIZE = "serializeDetail";

    int etat_Besoin;

//    TextView edit_description_details_besoin;
    ArrayList<DetailsBesoinSerialize> serializes = new ArrayList<>();

    // les valeurs du details
    String libelle_ajouter_details;
    double qte_ajouter_details;
    double pu_ajouter_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details__besoin);
        ProgressBar progressBar_details = findViewById(R.id.progress_bar_detail_etat_besoin);
        detailsEtatDeBesoinRepository = DetailsEtatDeBesoinRepository.getInstance();

        String besoin_kind = getIntent().getStringExtra(BESOIN_KIND);
        etat_Besoin = getIntent().getIntExtra(ETAT_BESOIN, 0);
        String codeProjet = getIntent().getStringExtra(CODE_PROJET);
        String codeLigne = getIntent().getStringExtra(CODE_LIGNE);
        codeEtatDeBesoin = getIntent().getStringExtra(BESOIN_CODE);
        codeEtatDeBesoinOnline = getIntent().getStringExtra(BESOIN_CODE_ONLINE);
        initialUtilisateur = getIntent().getStringExtra(INITIAL_UTILISATEUR);
        designation_besoin = getIntent().getStringExtra(DESIGNATION_BESOIN);

        setUpToolBar();
        adapter = new DetailsBesoinAdapter(Details_Besoin.this, codeEtatDeBesoin);
        ListView listView_details_besoin = findViewById(R.id.listView_details_besoin);

        FloatingActionButton float_add_details = findViewById(R.id.float_add_details_besoin);

        if(besoin_kind.equals("Validés.")) // Valider
        {
            float_add_details.setVisibility(View.INVISIBLE);
        }
        if (etat_Besoin == 1)
        {
            float_add_details.setVisibility(View.INVISIBLE);
        }
//        // pour le text de description du projet
        float_add_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AjouterDetailBesoin modifier_deatils = new AjouterDetailBesoin(codeProjet);
//                modifier_deatils.show(getSupportFragmentManager(), "9");
                startActivityForResult(
                        new Intent(Details_Besoin.this, AjouterDetailBesoinActive.class)
                                .putExtra(AjouterDetailBesoinActive.CODE_PROJET, codeProjet)
                                .putExtra(AjouterDetailBesoinActive.CODE_LIGNE, codeLigne)
                                .putExtra(AjouterDetailBesoinActive.INITIAL_UTILISATEUR, initialUtilisateur)
                                .putExtra(AjouterDetailBesoinActive.DERNIER_DETAIL_BESOIN, 0)
                        , 8);
            }
        });

        TextView textView_total_value = findViewById(R.id.textView_total_value_detail_besoin);
        viewModel = ViewModelProviders.of(this).get(DetailBesoinViewModel.class);
        if(!codeEtatDeBesoinOnline.equals(""))
        {
//            Toast.makeText(Details_Besoin.this,
//                    "codeEtatBesoinOnline : " + codeEtatDeBesoinOnline, Toast.LENGTH_LONG).show();
            viewModel.init(codeEtatDeBesoinOnline);
        }
        else if (codeEtatDeBesoinOnline.equals(""))
        {
//            Toast.makeText(Details_Besoin.this, "codeEtatBesoin : " + codeEtatDeBesoin, Toast.LENGTH_LONG).show();
            viewModel.init(codeEtatDeBesoin);
        }

        viewModel.isLoaging().observe(Details_Besoin.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean)
                {
                    progressBar_details.setVisibility(View.VISIBLE);
                }
                else
                {
                    progressBar_details.setVisibility(View.GONE);
                }
            }
        });

        viewModel.getDetailBesoinArticleLiveData().observe(Details_Besoin.this,
                new Observer<List<Entity_DetailBesoinWithEntity_Article>>() {
            @Override
            public void onChanged(List<Entity_DetailBesoinWithEntity_Article> entity_detailBesoinWithEntity_articles) {
                adapter.setDetailsBesoin(entity_detailBesoinWithEntity_articles);
                listView_details_besoin.setAdapter(adapter);
                getSerialize(entity_detailBesoinWithEntity_articles);
                setTotalValue(textView_total_value, entity_detailBesoinWithEntity_articles);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 8 && resultCode == RESULT_OK)
        {
            if (data != null)
            {
                int IdDetailEB = data.getIntExtra(AjouterDetailBesoinActive.IDDETAIL, 0);
                String CodeEtatdeBesoin = data.getStringExtra(AjouterDetailBesoinActive.CODEETATDEBESOIN);
                int idDetailEBOnline = data.getIntExtra(AjouterDetailBesoinActive.DERNIER_DETAIL_BESOIN, 0);
                String CodeArticle = data.getStringExtra(AjouterDetailBesoinActive.CODEARTICLE);
                String CodeLigne = data.getStringExtra(AjouterDetailBesoinActive.CODELIGNE);
                String LibelleDetail = data.getStringExtra(AjouterDetailBesoinActive.LIBELLEDETAIL);
                double Qte = data.getDoubleExtra(AjouterDetailBesoinActive.QTE, 0.0);
                double Pu = data.getDoubleExtra(AjouterDetailBesoinActive.PU, 0.0);
                double Sortie = data.getDoubleExtra(AjouterDetailBesoinActive.SORTIE, 0.0);
                double Entree = data.getDoubleExtra(AjouterDetailBesoinActive.ENTREE, 0.0);
                sendDetailResultat(idDetailEBOnline, CodeLigne, CodeArticle, LibelleDetail, Qte, Pu);
            }
        }
    }

    public void getSerialize(List<Entity_DetailBesoinWithEntity_Article> entity_Besoin)
    {
        serializes.clear();
        for (int a =0; a < entity_Besoin.size(); a++)
        {
            serializes.add(
                    new DetailsBesoinSerialize(
                            entity_Besoin.get(a).getDetailBesoin().getIdDetailEBOnline(),
                            entity_Besoin.get(a).getDetailBesoin().getCodeEtatdeBesoin(),
                            entity_Besoin.get(a).getDetailBesoin().getCodeArticle(),
                            entity_Besoin.get(a).getDetailBesoin().getCodeLigne(),
                            entity_Besoin.get(a).getDetailBesoin().getLibelleDetail(),
                            entity_Besoin.get(a).getDetailBesoin().getQte(),
                            entity_Besoin.get(a).getDetailBesoin().getPu(),
                            entity_Besoin.get(a).getDetailBesoin().getSortie(),
                            entity_Besoin.get(a).getDetailBesoin().getEntree()));
        }
    }

    void setTotalValue(TextView textView_total_value, List<Entity_DetailBesoinWithEntity_Article> list)
    {
        double total_value =0;
        for (int a = 0; a < list.size(); a++)
        {
            total_value += (list.get(a).getDetailBesoin().getQte() * list.get(a).getDetailBesoin().getPu());
        }
        textView_total_value.setText("$"+ new DecimalFormat("##.##").format(total_value));
    }

    public void openModifierDilog(int idDetailEBOnline, int idDetailsBesoin, String codeArticle,
                                  String libelle_ajouter_details, double qte, double pu)
    {
        if (etat_Besoin == 0)
        {
            Modifier_Details_Besoin modifier_deatils =
                    new Modifier_Details_Besoin(idDetailEBOnline, idDetailsBesoin, codeArticle,
                            libelle_ajouter_details, qte, pu, "details_besoin");
            modifier_deatils.show(getSupportFragmentManager(), "5");
//            Toast.makeText(Details_Besoin.this, "je vais modifier " , Toast.LENGTH_LONG).show();
        }
    }

    void sendModifierDetailResultat(int idDetailEBOnline, int idDetatialsBeosin, String codeArticle, String libelle, double qte, double pu)
    {
        libelle_ajouter_details = libelle;
        qte_ajouter_details = qte;
        pu_ajouter_details = pu;
        Entity_DetailBesoin entity_detailBesoin =
                new Entity_DetailBesoin(idDetailEBOnline, codeEtatDeBesoin, "codeArticle","codeLigne", libelle,
                qte_ajouter_details, pu_ajouter_details, 0.0, 0.0);
        entity_detailBesoin.setIdDetailEB(idDetatialsBeosin);
        viewModel.updateDetail(entity_detailBesoin);
    }

    void deleteDetails(Entity_DetailBesoin entity_detailBesoin)
    {
        if (etat_Besoin == 0)
        {
            viewModel.deleteDetails(entity_detailBesoin);
        }
    }

    void sendDetailResultat(int idDetailEBOnline, String codeLigne, String codeArticle, String libelle_detail, double qte, double pu)
    {
        libelle_ajouter_details = libelle_detail;
        qte_ajouter_details = qte;
        pu_ajouter_details = pu;
        // je fais l'enregistrement de details ici
        viewModel.insertDetails(new Entity_DetailBesoin(idDetailEBOnline, codeEtatDeBesoin, codeArticle, codeLigne,
                libelle_ajouter_details, qte_ajouter_details, pu_ajouter_details, 0.0, 0.0));
    }

    public void setUpToolBar()
    {
        Toolbar toolbar = findViewById(R.id.app_bar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_clear_24);
        if (Details_Besoin.this != null)
        {
            Details_Besoin.this.setSupportActionBar(toolbar);
            toolbar.setTitle(""+designation_besoin);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra(LISTSERIALIZE, serializes);
                    setResult(RESULT_OK, intent);
                    Details_Besoin.this.finish();
                }
            });
        }
    }

    void getvisible_submit(MaterialButton btn)
    {
        btn.setVisibility(View.VISIBLE);
    }

    void getshow_keboard()
    {
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null)
        {
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
    }
}
