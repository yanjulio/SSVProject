package com.soft.ssvapp.Fragment_Menu.Fill_Projects.ProjectFagment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.soft.ssvapp.Data.Entity_Ligne;
import com.soft.ssvapp.R;

public class Ajouter_Ligne_Dialog extends DialogFragment {

    private String codeProjet;
    private int codeLigne;
    private String genre_ajout;
    int nouveau_code_ligne = 0;

    public Ajouter_Ligne_Dialog(String codeProjet, int codeLigne, String genre)
    {
        this.codeProjet = codeProjet;
        this.nouveau_code_ligne = codeLigne;
        this.genre_ajout = genre;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.ajout_ligne, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        TextInputLayout ligne_input = view.findViewById(R.id.text_nouvelle_ligne_input);
        TextInputEditText edit_ligne = view.findViewById(R.id.edit_nouvelle_ligne);
        TextInputLayout prevision_input = view.findViewById(R.id.text_prevision_input);
        TextInputEditText edit_prevision = view.findViewById(R.id.edit_prevision_ligne);
        MaterialButton ajouter_ligne = view.findViewById(R.id.text_ajout_ligne_instantner);
        MaterialButton ok_ligne = view.findViewById(R.id.btn_ok_pour_lignes);
        builder.setView(view);
        builder.setCancelable(true);

        ajouter_ligne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValueNotNull(edit_ligne.getText(), edit_prevision.getText()))
                {
                    if (genre_ajout.equals("Nouveau"))
                    {
                        Nouveau_Projet nouveau_projet = (Nouveau_Projet)getContext();
                        nouveau_projet.insertLigne(
                                new Entity_Ligne(codeProjet+"CL"+nouveau_code_ligne,
                                        codeProjet, edit_ligne.getText().toString(), Double.valueOf(edit_prevision.getText().toString())));
                    }
                    else
                    {
                        LigneActivity ligneActivity = (LigneActivity)getContext();
                        ligneActivity.insertLigneRoom(new Entity_Ligne(codeProjet+"CL"+nouveau_code_ligne,
                                codeProjet, edit_ligne.getText().toString(), Double.valueOf(edit_prevision.getText().toString())));
                    }
                    nouveau_code_ligne++;
                    edit_ligne.setText("");
                    edit_prevision.setText("");
                    ligne_input.setError(null);
                    prevision_input.setError(null);
                }
                else
                {
                    ligne_input.setError("Veillez remplir ce champ.");
                    prevision_input.setError("Veillez remplir ce champ.");
                }
            }
        });

        ok_ligne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (!edit_ligne.getText().toString().equals("") && !edit_prevision.getText().toString().equals(""))
                {
                    if (genre_ajout.equals("Nouveau")) // if nouveau j'ajoute dans volatile
                    {
                        Nouveau_Projet nouveau_projet = (Nouveau_Projet)getContext();
                        nouveau_projet.insertLigne(
                                new Entity_Ligne(codeProjet+"CL"+ nouveau_code_ligne,
                                        codeProjet, edit_ligne.getText().toString(), Double.valueOf(edit_prevision.getText().toString())));
                    }
                    else // if Modifier j'ajoute dans room
                    {
                        LigneActivity ligneActivity = (LigneActivity)getContext();
                        ligneActivity.insertLigneRoom(new Entity_Ligne(codeProjet+"CL"+ nouveau_code_ligne,
                                codeProjet, edit_ligne.getText().toString(), Double.valueOf(edit_prevision.getText().toString())));
                    }
                }
            }
        });

        return builder.create();
    }

    private boolean isValueNotNull(Editable editable_ligne, Editable editable_prevision)
    {
        return (editable_ligne != null && editable_ligne.length() != 0)
                && (editable_prevision != null && editable_prevision.length() != 0);
    }
}
