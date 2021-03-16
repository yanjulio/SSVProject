package com.soft.ssvapp.Fragment_Menu.Fill_Projects.ProjectFagment;

import android.content.Context;
import android.content.Entity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.soft.ssvapp.Data.Entity_Ligne;
import com.soft.ssvapp.Data.Entity_ProjectWithEntity_Ligne;
import com.soft.ssvapp.Fragment_Menu.FillArticle.ListArticle;
import com.soft.ssvapp.Fragment_Menu.Navigation.Projects;
import com.soft.ssvapp.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Details extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    // for the Date chooser
    private int mYear, mMoth, mDay_of_Moth;
    private int annee, mois, jour;
    DatePickerDialog datePickerDialog;
    Calendar calendar;
    public static String date;
    int click_date_chooser = 0;

    EditText edit_fin;
    EditText edit_debut;

    boolean edit_value = false;
    public static String PROJECT_KIND = "project_kind";
    public static String PROJECT_CODE = "project_code";
    public static String PROJECT_LIEU = "project_lieu";
    public static String PROJECT_CHEFPROJET = "project_chef";
    public static String PROJECT_DATEDEBUT = "project_date_debut";
    public static String PROJECT_DATEFIN = "project_date_fin";
    public static String PROJECT_DESCRIPTION = "project_description";
    public static String COMPTE_CLIENT = "compte_client";
    int compte_client;

    TextView edit_description_details;
//    ListView listView_ligne;
//    Ligne_Adapter ligne_adapter;
//    LigneViewModel ligneViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_details_projet);
        setUpToolBar();

        String project_kind = getIntent().getStringExtra(PROJECT_KIND);

        String project_code = getIntent().getStringExtra(PROJECT_CODE);
        String project_lieu = getIntent().getStringExtra(PROJECT_LIEU);
        String project_chef = getIntent().getStringExtra(PROJECT_CHEFPROJET);
        String project_date_debut = getIntent().getStringExtra(PROJECT_DATEDEBUT);
        String project_date_fin = getIntent().getStringExtra(PROJECT_DATEFIN);
        String project_designation = getIntent().getStringExtra(PROJECT_DESCRIPTION);
        compte_client = getIntent().getIntExtra(COMPTE_CLIENT, 0);

        final MaterialButton btn_submit = findViewById(R.id.btn_modifier_details);

        final EditText edit_code = findViewById(R.id.edit_code_projet);
        edit_code.setText("" + project_code);
        final EditText edit_lieu = findViewById(R.id.edit_lieu_projet);
        edit_lieu.setText("" + project_lieu);
        final EditText edit_chef = findViewById(R.id.edit_chef_projet);
        edit_chef.setText("" + project_chef);
        edit_debut = findViewById(R.id.edit_debut_date_projet);
        edit_debut.setText("" + project_date_debut);
        edit_fin = findViewById(R.id.edit_fin_date_projet);
        edit_fin.setText("" + project_date_fin);
        final TextView edit_descriptions = findViewById(R.id.edit_descriptions_details);
        edit_descriptions.setText("" + project_designation);

        final TextView btn_lieu = findViewById(R.id.text_edit_lieu_projet);
        btn_lieu.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                edit_lieu.setEnabled(true);
                edit_lieu.requestFocus();
                getvisible_submit(btn_submit);
                getshow_keboard();
            }
        });
        final TextView btn_chef = findViewById(R.id.text_edit_chef_projet);
        btn_chef.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                edit_chef.setEnabled(true);
                edit_chef.requestFocus();
                getvisible_submit(btn_submit);
                getshow_keboard();
            }
        });
        final ImageView imageView_debut = findViewById(R.id.imageView_edit_debut_date_projet);
        imageView_debut.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                click_date_chooser = 1;
                click_date_image();
                getvisible_submit(btn_submit);
            }
        });
        final ImageView imageView_fin = findViewById(R.id.imageView_edit_fin_date_projet);
        imageView_fin.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                click_date_chooser = 2;
                click_date_image();
                getvisible_submit(btn_submit);
            }
        });

        MaterialButton button_lignes = findViewById(R.id.button_lignes);
        button_lignes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Details.this, LigneActivity.class)
                        .putExtra(LigneActivity.CODE_PROJET, project_code)
                        .putExtra(LigneActivity.DESIGNATION_PROJET, project_designation));
            }
        });

        MaterialButton button_article = findViewById(R.id.button_ajout_article);
        button_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Details.this, ListArticle.class));
            }
        });

        // la liste pour les lignes
//        listView_ligne = findViewById(R.id.listView_ligne_projet);
//        ligne_adapter = new Ligne_Adapter(Details.this, "details");
//        ligneViewModel = ViewModelProviders.of(Details.this).get(LigneViewModel.class);
//        ligneViewModel.init(project_code);
//        ligneViewModel.getCodeLigne().observe(Details.this, new Observer<List<Entity_ProjectWithEntity_Ligne>>() {
//            @Override
//            public void onChanged(List<Entity_ProjectWithEntity_Ligne> entity_projectWithEntity_lignes) {
//                ligne_adapter.setLigne(addListLigne(entity_projectWithEntity_lignes));
//                listView_ligne.setAdapter(ligne_adapter);
//            }
//        });
//        getListViewSize(listView_ligne, Details.this);


        // pour le text de description du projet
        edit_description_details = findViewById(R.id.edit_descriptions_details);
        edit_description_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FragmentManager fm = getSupportFragmentManager();
                Modifier_deatils modifier_deatils = new Modifier_deatils(edit_descriptions.getText().toString());
                modifier_deatils.show(getSupportFragmentManager(), "9");
                getvisible_submit(btn_submit);
            }
        });

        if (project_kind.equals("Cloture."))
        {
            btn_chef.setVisibility(View.INVISIBLE);
            imageView_debut.setVisibility(View.INVISIBLE);
            imageView_fin.setVisibility(View.INVISIBLE);
            btn_lieu.setVisibility(View.INVISIBLE);
            edit_description_details.setEnabled(false);
        }

//        final TextView text_close = findViewById(R.id.text_cancel_details);
//        text_close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.putExtra(Projects.DATEDEBUT_PROJET, edit_debut.getText().toString());
                intent.putExtra(Projects.DATEFIN_PROJET, edit_fin.getText().toString());
                intent.putExtra(Projects.CHEF_PROJET, edit_chef.getText().toString());
                intent.putExtra(Projects.CODE_PROJET, edit_code.getText().toString());
                intent.putExtra(Projects.DESIGNATION_PROJET, edit_descriptions.getText().toString());
                intent.putExtra(Projects.LIEU_PROJET, edit_lieu.getText().toString());
                intent.putExtra(Projects.COMPTE_CLIENT, compte_client);
//                Toast.makeText(Details.this, "je das details : "
//                        + edit_descriptions.getText().toString(), Toast.LENGTH_LONG).show();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    List<Entity_Ligne> addListLigne(List<Entity_ProjectWithEntity_Ligne> list)
    {
        ArrayList<Entity_Ligne> list1_entity = new ArrayList<>();
        list1_entity.clear();
        for (int a = 0; a < list.size(); a++)
        {
            Entity_Ligne entity_ligne =
                    new Entity_Ligne(list.get(a).getEntity_ligne().getCodeLigne(), list.get(a).getEntity_ligne().getCodeProject(),
                            list.get(a).getEntity_ligne().getDesignationLigne(), list.get(a).getEntity_ligne().getPrevision());
            entity_ligne.setIdLigne(list.get(a).getEntity_ligne().getIdLigne());
            list1_entity.add(entity_ligne);
        }
        return  list1_entity;
    }

    void sendResultat(String data_descriptions)
    {
        edit_description_details.setText(""+ data_descriptions);
    }

    void getvisible_submit(MaterialButton btn)
    {
        btn.setVisibility(View.VISIBLE);
    }


//    The code below let show the keyboard
    void getshow_keboard()
    {
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null)
        {
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
    }

    public void click_date_image()
    {
        datePickerDialog = DatePickerDialog.newInstance(Details.this, mYear, mMoth, mDay_of_Moth);
        datePickerDialog.setThemeDark(false);
        datePickerDialog.showYearPickerFirst(false);
        datePickerDialog.setAccentColor(Color.parseColor("#2d612c"));
        datePickerDialog.setTitle("Selectionner la date");
        datePickerDialog.show(getSupportFragmentManager(), "DatePickerDialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        annee = year;
        mois = monthOfYear + 1;
        jour = dayOfMonth;

        if (jour > 9 && mois > 9)date = annee + "-" + mois + "-" + jour;
        if (jour > 9 && !(mois > 9))date = annee + "-0" + mois + "-" + jour;
        if (!(jour > 9) && mois > 9)date = annee + "-" + mois + "-0" + jour;
        if (!(jour > 9) && !(mois > 9))date = annee + "-0" + mois + "-0" + jour;

        if (click_date_chooser == 1)
        {
            edit_debut.setText(""+ date);
        }
        else
        {
            edit_fin.setText("" + date);
        }
    }

    public void setUpToolBar()
    {
        Toolbar toolbar = findViewById(R.id.app_bar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_clear_24);
        if (Details.this != null)
        {
            Details.this.setSupportActionBar(toolbar);
            toolbar.setTitle("Détailles");
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Details.this.finish();
                }
            });
        }
    }

//    public void getListViewSize(ListView myListView, Context context) {
////        ListAdapter myListAdapter=myListView.getAdapter();
//        Ligne_Adapter adapter = new Ligne_Adapter(context, "details");
//        if (adapter==null) {
//            //do nothing return null
//            return;
//        }
//        //set listAdapter in loop for getting final size
//        int totalHeight=0;
//        for (int size=0; size < adapter.getCount(); size++) {
//            View listItem=adapter.getView(size, null, myListView);
//            listItem.measure(0, 0);
//            totalHeight+=listItem.getMeasuredHeight();
//        }
//        //setting listview item in adapter
//        ViewGroup.LayoutParams params=myListView.getLayoutParams();
//        params.height=totalHeight + (myListView.getDividerHeight() * (adapter.getCount() - 1));
//        myListView.setLayoutParams(params);
//        // print height of adapter on log
//        Log.i("height of listItem:", String.valueOf(totalHeight));
//    }
}
