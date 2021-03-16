package com.soft.ssvapp.Fragment_Menu.Fill_Projects.ProjectFagment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.soft.ssvapp.Data.Entity_Ligne;
import com.soft.ssvapp.R;

public class Update_Ligne extends DialogFragment {

    private String codeProjet;
    private String codeLigne;
    private int idLigne;
    private String designationLigne;
    private int idVolatile;
    private double prevision;
    private String fromActivity;

    public Update_Ligne(int idVolatile, String codeProjet, String codeLigne, String designationLigne,
                        int idLigne, double prevision, String fromActivity)
    {
        this.idVolatile = idVolatile;
        this.codeProjet = codeProjet;
        this.codeLigne = codeLigne;
        this.idLigne = idLigne;
        this.fromActivity = fromActivity;
        this.designationLigne = designationLigne;
        this.prevision = prevision;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.update_ligne, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        TextInputEditText edit_descriptions_ligne = view.findViewById(R.id.edit_descriptions_ligne);
        TextInputEditText edit_prevision_ligne = view.findViewById(R.id.edit_prevision_ligne);
        edit_descriptions_ligne.setText("" + designationLigne);
        edit_prevision_ligne.setText("" + prevision);
        MaterialButton btn_ok_modifier_ligne = view.findViewById(R.id.btn_ok_modifier_ligne);
        builder.setView(view);

        btn_ok_modifier_ligne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                Entity_Ligne entity_ligne = new Entity_Ligne(codeLigne, codeProjet,
                        edit_descriptions_ligne.getText().toString(), Double.valueOf(edit_prevision_ligne.getText().toString()));
                entity_ligne.setIdLigne(idLigne);
                if (fromActivity.equals("Nouveau"))
                {
                    Nouveau_Projet nouveau_projet = (Nouveau_Projet)getActivity();
                    nouveau_projet.updateLigne(idVolatile, entity_ligne);
                }
                else
                {
                    LigneActivity ligneActivity = (LigneActivity)getContext();
                    ligneActivity.updateLigneRoom(entity_ligne);
                    Toast.makeText(getContext(), "je suis modifier", Toast.LENGTH_LONG).show();
                }
            }
        });

        return builder.create();
    }
}
