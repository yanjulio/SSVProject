package com.soft.ssvapp.Fragment_Menu.Navigation;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.soft.ssvapp.Data.Entity_Besoin;
import com.soft.ssvapp.Data.Entity_ProjectWithEntity_Besoin;
import com.soft.ssvapp.Fragment_Menu.FillArticle.ArticleViewModel;
import com.soft.ssvapp.Fragment_Menu.Fill_EtatBesoin.Besoin_View_model;
import com.soft.ssvapp.Fragment_Menu.Fill_Operation.OperationAdapter;
import com.soft.ssvapp.Login;
import com.soft.ssvapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Operation_Cpte extends AppCompatActivity {
    Besoin_View_model view_model_dao;

    public static String KIND_OPERATION = "kind_operation";
    public static String CODE_PROJECT = "code_projet";
    public static String DESIGNATIONPROJET = "designationProjet";
    String codeProjet;
    String designationProjet;
    SharedPreferences prefs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.fragment_operation_cpte);
        setUpToolbar();
        // Met à jour les articles lors de reception de l'etat besoin.
        ArticleViewModel articleViewModel = ViewModelProviders.of(Operation_Cpte.this).get(ArticleViewModel.class);

        prefs = getSharedPreferences("lOGIN_USER", MODE_PRIVATE);
        String niveauUtilisateur = prefs.getString(Login.NIVEAUUTILISATEUR, "");

        codeProjet = getIntent().getStringExtra(CODE_PROJECT);
        designationProjet = getIntent().getStringExtra(DESIGNATIONPROJET);
        this.setTitle(designationProjet);

        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        String date_operation = df.format(c.getTime());

        TextView textView_kind = findViewById(R.id.genre_list_operation);
        String kind_operation = getIntent().getStringExtra(KIND_OPERATION);
        textView_kind.setText("" + kind_operation);

        ListView listView_operation = findViewById(R.id.list_operation);
        OperationAdapter operationAdapter =
                new OperationAdapter(Operation_Cpte.this, date_operation, codeProjet,
                        designationProjet, kind_operation, niveauUtilisateur);

        view_model_dao = ViewModelProviders.of(Operation_Cpte.this).get(Besoin_View_model.class);
        view_model_dao.init(codeProjet, kind_operation);
        if (niveauUtilisateur.equals("ADMIN"))
        {
            view_model_dao.getBesoinAvalider().observe(Operation_Cpte.this, new Observer<List<Entity_ProjectWithEntity_Besoin>>() {
                @Override
                public void onChanged(List<Entity_ProjectWithEntity_Besoin> entity_projectWithEntity_besoins) {
                    operationAdapter.setBesoinAvalider(entity_projectWithEntity_besoins);
                    listView_operation.setAdapter(operationAdapter);
                }
            });
        }
        else
        {
            view_model_dao.getAvaliderBesoinUtilisateur().observe(Operation_Cpte.this,
                    new Observer<List<Entity_ProjectWithEntity_Besoin>>() {
                @Override
                public void onChanged(List<Entity_ProjectWithEntity_Besoin> entity_projectWithEntity_besoins) {
                    operationAdapter.setBesoinAvalider(entity_projectWithEntity_besoins);
                    listView_operation.setAdapter(operationAdapter);
                }
            });
        }

    }

    public void deleteEtatBesoin(Entity_Besoin entity_besoin)
    {
        view_model_dao.delete(entity_besoin);
    }

    // getting it from the adapter
    public void insert(Entity_Besoin entity_besoin)
    {
        view_model_dao.insert(entity_besoin);
    }

    // getting it from the adapter
    public void update(Entity_Besoin entity_besoin)
    {
        view_model_dao.update(entity_besoin);
    }

    private void setUpToolbar()
    {
        Toolbar toolbar = findViewById(R.id.app_bar);
        if (Operation_Cpte.this != null)
        {
            Operation_Cpte.this.setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Operation_Cpte.this.finish();
                }
            });
        }
    }
}
