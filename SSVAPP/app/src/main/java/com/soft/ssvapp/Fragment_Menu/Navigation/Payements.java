package com.soft.ssvapp.Fragment_Menu.Navigation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.soft.ssvapp.DataRetrofit.RapportParProjet.RapportEBParProjetValideEtDecaisse;
import com.soft.ssvapp.Fragment_Menu.FillPayements.PayementAdapter;
import com.soft.ssvapp.Fragment_Menu.FillPayements.PayementRemote.PayementRemoteViewModel;
import com.soft.ssvapp.R;

import java.util.List;

public class Payements extends AppCompatActivity {
    //MERGE PAYEMENT WITH RAVITAEMENT.
//    Besoin_View_model view_model;
    PayementRemoteViewModel payementRemoteViewModel;
    ProgressBar progressBar;

    public static String KIND_PAYEMENT = "kind_payement";
    public static final String CODE_PROJET = "code_projet";
    public static final String DESIGNATION_PROJET = "designation_projet";
    public static final String COMPTE_LIER_PROJET = "compte_lier_projet";
    public static final String COMPTE_LIER_DESIGNATION_PROJET = "compte_designation_projet";
    PayementAdapter adapter;
    ListView listView_payement;
    String code_projet="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payement);

        String kind_payement = getIntent().getStringExtra(KIND_PAYEMENT);
        code_projet = getIntent().getStringExtra(CODE_PROJET);
        int compte_projet = getIntent().getIntExtra(COMPTE_LIER_PROJET, 0);
        String designation_projet = getIntent().getStringExtra(DESIGNATION_PROJET);
        String compte_designation_projet = getIntent().getStringExtra(COMPTE_LIER_DESIGNATION_PROJET);
        setUpToolbar(designation_projet);

        progressBar = findViewById(R.id.progress_bar_payement);
        TextView textView = findViewById(R.id.genre_projects_list_payement);
        textView.setText("" + kind_payement);

        listView_payement = findViewById(R.id.list_besoin_payement);

        adapter = new PayementAdapter(Payements.this, kind_payement, compte_projet, compte_designation_projet, code_projet);

        payementRemoteViewModel = ViewModelProviders.of(Payements.this).get(PayementRemoteViewModel.class);
        payementRemoteViewModel.getRapportEBValideDecaisse(code_projet).observe(Payements.this,
                new Observer<List<RapportEBParProjetValideEtDecaisse>>() {
            @Override
            public void onChanged(List<RapportEBParProjetValideEtDecaisse> rapportEBParProjetValideEtDecaisses) {
                adapter.setBesoinPayement(rapportEBParProjetValideEtDecaisses);
                listView_payement.setAdapter(adapter);
            }
        });

        payementRemoteViewModel.getIsLoading().observe(Payements.this, new Observer<Boolean>() {
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
        if (requestCode == 13 && resultCode == RESULT_OK)
        {
            payementRemoteViewModel = ViewModelProviders.of(Payements.this).get(PayementRemoteViewModel.class);
            payementRemoteViewModel.getRapportEBValideDecaisse(code_projet).observe(Payements.this,
                    new Observer<List<RapportEBParProjetValideEtDecaisse>>() {
                        @Override
                        public void onChanged(List<RapportEBParProjetValideEtDecaisse> rapportEBParProjetValideEtDecaisses) {
                            adapter.setBesoinPayement(rapportEBParProjetValideEtDecaisses);
                            listView_payement.setAdapter(adapter);
                        }
                    });
        }
    }

    private void setUpToolbar(String desigantion_projet)
    {
        Toolbar toolbar = findViewById(R.id.app_bar);
        if (Payements.this != null) {
            Payements.this.setSupportActionBar(toolbar);
            toolbar.setTitle("" + desigantion_projet);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Payements.this.finish();
                }
            });
        }
    }
}
