package com.soft.ssvapp.Fragment_Menu.Navigation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.soft.ssvapp.R;

public class Comptabilite extends AppCompatActivity {

    public static String KIND_COMPTABILITE = "kind_comptabilite";
    public static String CODE_PROJET = "code_projet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comptabilite);

        String kind_comptablite = getIntent().getStringExtra(KIND_COMPTABILITE);
        String code_projet = getIntent().getStringExtra(CODE_PROJET);

        TextView textView_titre = findViewById(R.id.genre_projects_list_comptabilite);
        textView_titre.setText("" + kind_comptablite);

    }
}
