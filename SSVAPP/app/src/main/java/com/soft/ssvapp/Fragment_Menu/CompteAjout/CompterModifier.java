package com.soft.ssvapp.Fragment_Menu.CompteAjout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.soft.ssvapp.DataRetrofit.Compte.RapportCompteResponse;
import com.soft.ssvapp.Fragment_Menu.Fill_Rapport.BalanceViewModel;
import com.soft.ssvapp.R;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class CompterModifier extends DialogFragment {

    BalanceViewModel viewModel;
    private RapportCompteResponse rappotCompteResponse;

    public CompterModifier(RapportCompteResponse rapportCompteResponse) {
        this.rappotCompteResponse = rapportCompteResponse;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        viewModel = ViewModelProviders.of(getActivity()).get(BalanceViewModel.class);
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_compte_modifier, null);

        MaterialButton ok = view.findViewById(R.id.btn_ok_compte_modifier);
        MaterialButton quitter = view.findViewById(R.id.btn_quitter_compte_modifier);
        TextInputEditText edit_libelle = view.findViewById(R.id.edit_libelle_compte_modifier);
        edit_libelle.setText("" + rappotCompteResponse.getDesignationCompte());

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                RapportCompteResponse compteResponse = new RapportCompteResponse(
                        rappotCompteResponse.getNumCompte(),
                        edit_libelle.getText().toString(),
                        rappotCompteResponse.getGroupeCompte(),
                        0,
                        "0"
                );
                viewModel.modifierCompte(compteResponse);
            }
        });
        quitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        builder.setView(view);
        builder.setCancelable(true);

        return builder.create();
    }
}
