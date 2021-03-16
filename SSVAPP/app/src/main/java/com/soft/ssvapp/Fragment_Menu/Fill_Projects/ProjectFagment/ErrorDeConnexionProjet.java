package com.soft.ssvapp.Fragment_Menu.Fill_Projects.ProjectFagment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.soft.ssvapp.Fragment_Menu.Navigation.Projects;
import com.soft.ssvapp.R;

public class ErrorDeConnexionProjet extends AppCompatActivity {

    public static String ERRORTYPE = "errortype_projet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_de_connexion_projet);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        String errortype = getIntent().getStringExtra(ERRORTYPE);

        TextView textView_errortype = findViewById(R.id.textView_errortype);
        textView_errortype.setText(""+errortype);
        MaterialButton btn_reessayer = findViewById(R.id.btn_reessayer_projet);
        btn_reessayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(ErrorDeConnexionProjet.this, Projects.class)
                        .putExtra(Projects.PROJECT_KIND, "Encours."));
            }
        });
    }
}
