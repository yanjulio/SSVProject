package com.soft.ssvapp.Fragment_Menu.CompteAjout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputEditText;
import com.soft.ssvapp.DataRetrofit.Compte.RapportCompteResponse;
import com.soft.ssvapp.R;

//import com.soft.freemanbusiness.DataRetrofit.Rapport.Rapport.DataPV.RapportCompteResponse;

public class CompteActivity extends AppCompatActivity {

    TextInputEditText designation_compte, num_compte;
    ProgressBar progressBar;

    CompteViewModel compteViewModel;

    public static final String GROUPE_COMPTE = "groupe_compte";
    String groupe_compte = "";
    int dernier_compte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compte);
        setUpToolBar();

        groupe_compte = getIntent().getStringExtra(GROUPE_COMPTE);

        designation_compte = findViewById(R.id.edit_designation_compte);
        num_compte = findViewById(R.id.edit_num_compte);

        progressBar = findViewById(R.id.progress_ajouter_compte);

        compteViewModel = ViewModelProviders.of(CompteActivity.this).get(CompteViewModel.class);

        compteViewModel.getIsLoading().observe(CompteActivity.this, new Observer<Boolean>() {
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

        compteViewModel.getDernierCompte(groupe_compte).observe(CompteActivity.this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                dernier_compte = integer + 1;
                num_compte.setText(""+dernier_compte);
            }
        });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_balance, menu);
        MenuItem menuItem_add_balance = menu.findItem(R.id.item_save_compte);
        menuItem_add_balance.setVisible(true);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.item_save_compte:
                    RapportCompteResponse comtpeResponse = new RapportCompteResponse(
                            dernier_compte,
                            designation_compte.getText().toString(),
                            Integer.valueOf(groupe_compte),
                            0,
                            "0"
                    );
                    compteViewModel.getEnregistrer(comtpeResponse).observe(CompteActivity.this,
                            new Observer<Integer>() {
                        @Override
                        public void onChanged(Integer integer) {
                            setResult(RESULT_OK);
                            finish();
                        }
                    });
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setUpToolBar()
    {
        Toolbar toolbar = findViewById(R.id.app_bar);
        if (CompteActivity.this != null)
        {
            CompteActivity.this.setSupportActionBar(toolbar);
            toolbar.setTitle("Nouveau Compte");
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CompteActivity.this.finish();
                }
            });
        }
    }
}
