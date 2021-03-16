package com.soft.ssvapp.Fragment_Menu.Fill_Rapport.RapportEtatDeBesoin.RapportDetailEtatBesoin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.soft.ssvapp.Data.Entity_DetailBesoin;
import com.soft.ssvapp.DataRetrofit.DetailsEtatDeBesoin.DetailsEtatDeBesoinRetrofit;
import com.soft.ssvapp.DataRetrofit.EtatDeBesoin.EtatDeBesoinRetrofit;
import com.soft.ssvapp.Fragment_Menu.Fill_Rapport.RapportEtatDeBesoin.RapportEtatBesoinDataRemote.RapportEtatBesoinViewModel;
import com.soft.ssvapp.Login;
import com.soft.ssvapp.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class DetailBesoinRapport extends AppCompatActivity {

    DetailBesoinRapportViewModel detailBesoinRapportViewModel;
    RapportEtatBesoinViewModel rapportEtatBesoinViewModel;
    DetailBesoinRapportAdapter detailBesoinRapportAdapter;
    ListView listView_detailBesoinRapport;
    TextView textView_total_value;
    ImageView imageView_valider_eb_rapport;
    ProgressBar progressBar_details;
    public static final String DESINGTION_ETAT_BESOIN = "designation_etat_besoin";
    public static final String CODE_ETAT_BESOIN = "code_etat_besoin";
    public static final String CODE_PROJET = "code_projet";
    public static final String NOM_UTILISATEUR = "nom_utilisateur";
    public static final String DATE_1 = "date1";
    public static final String DATE_2 = "date2";
    public static final String ETAT = "etat";
    String desingation_etat_besoin = "";
    String code_etat_besoin = "";
    String code_projet = "";
    String nomUtilisateur = "";
    String date_1 = "";
    String date_2 = "";
    String etat = "";

    // les valeurs du details
    String libelle_ajouter_details;
    double qte_ajouter_details;
    double pu_ajouter_details;

    String etatClick="";
    SharedPreferences prefs;
    String niveauUtilisateur, password;
    String date_operation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_besoin_rapport);
        prefs = getSharedPreferences("lOGIN_USER", MODE_PRIVATE);
        niveauUtilisateur = prefs.getString(Login.NIVEAUUTILISATEUR, "");
        password = prefs.getString(Login.PASS_WORD, "");

        desingation_etat_besoin = getIntent().getStringExtra(DESINGTION_ETAT_BESOIN);
        code_etat_besoin = getIntent().getStringExtra(CODE_ETAT_BESOIN);
        code_projet = getIntent().getStringExtra(CODE_PROJET);
        nomUtilisateur = getIntent().getStringExtra(NOM_UTILISATEUR);
        date_1 = getIntent().getStringExtra(DATE_1);
        date_2 = getIntent().getStringExtra(DATE_2);
        etat = getIntent().getStringExtra(ETAT);

        progressBar_details = findViewById(R.id.progress_bar_detail_etat_besoin_rapport);
        detailBesoinRapportAdapter = new DetailBesoinRapportAdapter(this, etat);

        listView_detailBesoinRapport = findViewById(R.id.listView_details_besoin_rapport);
        textView_total_value = findViewById(R.id.textView_total_value_detail_besoin_rapport);

        rapportEtatBesoinViewModel = ViewModelProviders.of(DetailBesoinRapport.this)
                .get(RapportEtatBesoinViewModel.class);
        rapportEtatBesoinViewModel.setEtat_set(etat);

        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        date_operation = df.format(c.getTime());

        imageView_valider_eb_rapport = findViewById(R.id.imageView_valider_eb_rapport);
        rapportEtatBesoinViewModel.getEtat_set().observe(DetailBesoinRapport.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null && !s.equals("ENVOYE"))
                {
                    etatClick = s;
//                    imageView_valider_eb_rapport.setVisibility(View.GONE);
                    imageView_valider_eb_rapport.setImageResource(R.drawable.ic_baseline_clear_24);
                }
            }
        });
        imageView_valider_eb_rapport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etatClick.equals(""))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DetailBesoinRapport.this);
                    builder.setMessage("Voulez-vous vraiment valider " + desingation_etat_besoin);
                    builder.setCancelable(true);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EtatDeBesoinRetrofit etatDeBesoinRetrofit =
                                    new EtatDeBesoinRetrofit(
                                            code_etat_besoin,
                                            desingation_etat_besoin,
                                            code_projet,
                                            1,
                                            "",
                                            nomUtilisateur,
                                            "2020-09-23T01:58:31.680Z",
                                            "2020-09-23T01:58:31.680Z",
                                            date_operation
                                    );
                            rapportEtatBesoinViewModel.modifierEtatBesoin(etatDeBesoinRetrofit, code_etat_besoin, date_1, date_2);
                            Intent intent = new Intent();
                            intent.putExtra(DATE_1, date_1);
                            intent.putExtra(DATE_2, date_2);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }).setNegativeButton("Non", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // nothing till now
                        }
                    });
                    builder.show();
                }
                else
                {
                    AnnulerOperation annulerOperation = new AnnulerOperation(desingation_etat_besoin);
                    annulerOperation.show(getSupportFragmentManager(), "annuler");
                }
            }
        });
//        textView_titre_details_besoin.setText("" + desingation_etat_besoin);
        setUpToolbar();

        detailBesoinRapportViewModel = ViewModelProviders.of(DetailBesoinRapport.this).get(DetailBesoinRapportViewModel.class);
        detailBesoinRapportViewModel.getDetailBesoinRapport(code_etat_besoin).observe(DetailBesoinRapport.this,
                new Observer<List<DetailsEtatDeBesoinRetrofit>>() {
            @Override
            public void onChanged(List<DetailsEtatDeBesoinRetrofit> detailsEtatDeBesoinRetrofits) {
                detailBesoinRapportAdapter.setDetailBesoinRapport(detailsEtatDeBesoinRetrofits);
                listView_detailBesoinRapport.setAdapter(detailBesoinRapportAdapter);
                setTotalValue(textView_total_value, detailsEtatDeBesoinRetrofits);
            }
        });

        detailBesoinRapportViewModel.getIsLoading().observe(DetailBesoinRapport.this, new Observer<Boolean>() {
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

    }

    public void annulerValidation(String mot_de_passe)
    {
        Toast.makeText(DetailBesoinRapport.this, "niveauUtilisateur : " + niveauUtilisateur, Toast.LENGTH_LONG).show();
        if (mot_de_passe.equals(password) && niveauUtilisateur.equals("ADMIN"))
        {
            EtatDeBesoinRetrofit etatDeBesoinRetrofit =
                    new EtatDeBesoinRetrofit(
                            code_etat_besoin,
                            desingation_etat_besoin,
                            code_projet,
                            0,
                            "",
                            nomUtilisateur,
                            "2020-09-23T01:58:31.680Z",
                            "2020-09-23T01:58:31.680Z",
                            date_operation
                    );
            rapportEtatBesoinViewModel.modifierEtatBesoin(etatDeBesoinRetrofit, code_etat_besoin, date_1, date_2);
            Intent intent = new Intent();
            intent.putExtra(DATE_1, date_1);
            intent.putExtra(DATE_2, date_2);
            setResult(RESULT_OK, intent);
            finish();
        }
        else
        {
            Toast.makeText(DetailBesoinRapport.this,
                    "ok, vous n'avez pas l'autorisation d'éffectuer cette opération",
                    Toast.LENGTH_LONG).show();
        }
    }

    void setTotalValue(TextView textView_total_value, List<DetailsEtatDeBesoinRetrofit> list)
    {
        double total_value =0;
        for (int a = 0; a < list.size(); a++)
        {
            total_value += (list.get(a).getQte() * list.get(a).getPu());
        }
        textView_total_value.setText("$"+ new DecimalFormat("##.##").format(total_value));
    }

    void openModifierDilogValider(int idDetailEBOnline, int idDetailsBesoin, String libelle_ajouter_details,
                                  String codeArticle, double qte, double pu)
    {

        ModifierDetailRemote modifier_deatils =
                new ModifierDetailRemote(idDetailEBOnline, idDetailsBesoin, codeArticle,
                        libelle_ajouter_details, qte, pu);
        modifier_deatils.show(getSupportFragmentManager(), "0");
    }

    public void sendModifierDetailResultatValider(int idDetailEBOnline, int idDetatialsBeosin, String libelle, String codeArticle,
                                                  double qte, double pu)
    {
        libelle_ajouter_details = libelle;
        qte_ajouter_details = qte;
        pu_ajouter_details = pu;
        Entity_DetailBesoin entity_detailBesoin =
                new Entity_DetailBesoin(idDetailEBOnline, code_etat_besoin, codeArticle, "codeLigne", libelle,
                        qte_ajouter_details, pu_ajouter_details, 0.0, 0.0);
        entity_detailBesoin.setIdDetailEB(idDetatialsBeosin);
        detailBesoinRapportViewModel.updateDetailRemote(entity_detailBesoin);
    }

    public void deleteRemote(Entity_DetailBesoin entity_detailBesoin)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Voulez-vous supprimer la détaille "+entity_detailBesoin.getLibelleDetail()+" ?");
        builder.setCancelable(true);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                detailBesoinRapportViewModel.deleteDetailRemote(entity_detailBesoin);
            }
        });
        builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    public void setUpToolbar()
    {
        Toolbar toolbar = findViewById(R.id.app_bar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_clear_24);
        if (DetailBesoinRapport.this != null)
        {
            DetailBesoinRapport.this.setSupportActionBar(toolbar);
            toolbar.setTitle(""+desingation_etat_besoin);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra(DATE_1, date_1);
                    intent.putExtra(DATE_2, date_2);
                    setResult(RESULT_OK, intent);
                    finish();
                    DetailBesoinRapport.this.finish();
                }
            });
        }
    }
}
