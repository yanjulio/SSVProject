package com.soft.ssvapp.Fragment_Menu.Navigation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.soft.ssvapp.R;

public class VerificationCompte extends AppCompatActivity {

    public static String KIND_VERIFICATION_COMPTE = "kind_verification_compte";
    public static String CODE_PROJET = "code_projet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_compte);

        String kind_verification = getIntent().getStringExtra(KIND_VERIFICATION_COMPTE);
        String code_projet = getIntent().getStringExtra(CODE_PROJET);

        TextView textView_titre = findViewById(R.id.genre_projects_list_verification_compte);
        textView_titre.setText("" + kind_verification);

    }
}
