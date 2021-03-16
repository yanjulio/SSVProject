package com.soft.ssvapp.Utilisateurs;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputEditText;
import com.soft.ssvapp.DataRetrofit.UtilisateurResponse;
import com.soft.ssvapp.Login;
import com.soft.ssvapp.R;

public class CreerUtilisateurAtivity extends AppCompatActivity {

    TextInputEditText edit_nomUtilisateur, edit_motDePass, edit_designation_utilisateur, edit_fonction_utilisateur;
    ProgressBar progressBar;
    UtilisateurViewModel utilisateurViewModel;
    int create_utilisateur = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creer_utilisateur_dialog);
        setUpBar();

        edit_nomUtilisateur = findViewById(R.id.edit_nomutilisateur);
        edit_motDePass = findViewById(R.id.edit_motDePass);
        edit_designation_utilisateur = findViewById(R.id.edit_designation_utilisateur);
        edit_fonction_utilisateur = findViewById(R.id.edit_fonction_utilisateur);
        progressBar  = findViewById(R.id.progress_bar_creer_utilisateur);

        utilisateurViewModel = ViewModelProviders.of(CreerUtilisateurAtivity.this).get(UtilisateurViewModel.class);
        utilisateurViewModel.getIsLoagin().observe(CreerUtilisateurAtivity.this, new Observer<Boolean>() {
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
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_creer_utilisateur, menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.item_creer_utilisateur:
                UtilisateurResponse utilisateurResponse =
                        new UtilisateurResponse(edit_nomUtilisateur.getText().toString(),
                                edit_designation_utilisateur.getText().toString(), edit_motDePass.getText().toString(),
                                edit_fonction_utilisateur.getText().toString());
                utilisateurViewModel.creerUtilisateur(utilisateurResponse);

                utilisateurViewModel.getIsCreated().observe(CreerUtilisateurAtivity.this, new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
//                        create_utilisateur = integer;
                        if (integer==1)
                        {
                            Intent intent = new Intent();
                            String nomUtilisateur = edit_nomUtilisateur.getText().toString();
                            String motDepass = edit_motDePass.getText().toString();
                            intent.putExtra(Login.USER_NAME, nomUtilisateur);
                            intent.putExtra(Login.PASS_WORD, motDepass);
                            CreerUtilisateurAtivity.this.setResult(RESULT_OK, intent);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(CreerUtilisateurAtivity.this,
                                    "Cet utilisateur existe déja", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpBar()
    {
        Toolbar toolbar = findViewById(R.id.app_bar);
        if (CreerUtilisateurAtivity.this != null)
        {
            CreerUtilisateurAtivity.this.setSupportActionBar(toolbar);
            toolbar.setTitle("Nouveau Utilisateur");
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }
}
