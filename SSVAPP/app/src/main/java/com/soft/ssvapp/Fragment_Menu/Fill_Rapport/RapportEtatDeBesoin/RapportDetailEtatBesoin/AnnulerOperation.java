package com.soft.ssvapp.Fragment_Menu.Fill_Rapport.RapportEtatDeBesoin.RapportDetailEtatBesoin;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.soft.ssvapp.R;

public class AnnulerOperation extends DialogFragment {

    private String desingation_etat_besoin;
    public AnnulerOperation(String desigantion_etat_besoin)
    {
        this.desingation_etat_besoin = desigantion_etat_besoin;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getActivity().getLayoutInflater().inflate(R.layout.model_annuler_operation, null);
        TextInputLayout input_modification_operation = view.findViewById(R.id.input_modification_operation);
        TextInputEditText edit_modification_operation = view.findViewById(R.id.edit_modification_operation);
        MaterialButton ok_annuler = view.findViewById(R.id.btn_ok_verification_annuler);
        MaterialButton quitter_annuler = view.findViewById(R.id.btn_quitter_quitter_verification_annuler);
        edit_modification_operation.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (isPasswordValid(edit_modification_operation.getText()))
                {
                    input_modification_operation.setError(null); // clear the error
                }
                return false;
            }
        });
        builder.setMessage("Voulez-vous annuler " + desingation_etat_besoin + "?");
        builder.setView(view);
        builder.setCancelable(true);
        ok_annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPasswordValid(edit_modification_operation.getText()))
                {
//                    Toast.makeText(getActivity(), R.string.kp_password_valide, Toast.LENGTH_LONG).show();
                    input_modification_operation.setError(getResources().getString(R.string.kp_password_valide));
                }
                else
                {
                    input_modification_operation.setError(null); // Clear the error
                    dismiss();
                    DetailBesoinRapport detailBesoinRapport = (DetailBesoinRapport)getActivity();
                    detailBesoinRapport.annulerValidation(edit_modification_operation.getText().toString());
                }
            }
        });
        quitter_annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                // nothing till now
            }
        });
        return builder.create();
    }

    private boolean isPasswordValid(Editable text)
    {
        return text != null && text.length() >= 3;
    }
}
