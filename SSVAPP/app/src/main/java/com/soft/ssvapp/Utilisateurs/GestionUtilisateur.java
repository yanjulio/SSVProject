package com.soft.ssvapp.Utilisateurs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.soft.ssvapp.DataRetrofit.UtilisateurResponse;
import com.soft.ssvapp.R;

import java.util.List;

public class GestionUtilisateur extends AppCompatActivity {

    ListView listView_utilisateur;
    ProgressBar progressBar;
    UtilisateurViewModel utilisateurViewModel;
    GestionUtilisateurAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_utilisateur);
        setUpBar();

        utilisateurViewModel = ViewModelProviders.of(GestionUtilisateur.this).get(UtilisateurViewModel.class);
        listView_utilisateur = findViewById(R.id.listView_utilisateur);
        progressBar = findViewById(R.id.progress_bar_gestion_utilisateur);
        adapter = new GestionUtilisateurAdapter(this);

        ListeUtilisateur();

        utilisateurViewModel.getIsLoagin().observe(GestionUtilisateur.this, new Observer<Boolean>() {
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
        if (requestCode == 10 && resultCode == RESULT_OK)
        {
            ListeUtilisateur();
        }
    }


    private void ListeUtilisateur()
    {
        utilisateurViewModel.getListeUtilisateur().observe(GestionUtilisateur.this, new Observer<List<UtilisateurResponse>>() {
            @Override
            public void onChanged(List<UtilisateurResponse> utilisateurResponses) {
                adapter.setUtilisateur(utilisateurResponses);
                listView_utilisateur.setAdapter(adapter);
            }
        });
    }

    private void setUpBar()
    {
        Toolbar toolbar = findViewById(R.id.app_bar);
        if (GestionUtilisateur.this != null)
        {
            GestionUtilisateur.this.setSupportActionBar(toolbar);
            toolbar.setTitle("Utilisateurs");
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }
}