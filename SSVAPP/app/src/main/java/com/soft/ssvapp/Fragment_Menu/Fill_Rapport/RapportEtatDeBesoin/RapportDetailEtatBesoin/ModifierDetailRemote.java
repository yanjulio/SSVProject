package com.soft.ssvapp.Fragment_Menu.Fill_Rapport.RapportEtatDeBesoin.RapportDetailEtatBesoin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.soft.ssvapp.R;

public class ModifierDetailRemote extends DialogFragment {
    private String text_descriptions;
    private String codeArticle;
    private double qte;
    private double pu;
    private int idDetails_Besoin;
    private int idDetailEBOnline;

    public ModifierDetailRemote(int idDetailEBOnline, int idDetailsBesoin, String codeArticle, String text_descriptions,
                                   double qte, double pu)
    {
        this.idDetailEBOnline = idDetailEBOnline;
        this.idDetails_Besoin = idDetailsBesoin;
        this.codeArticle = codeArticle;
        this.text_descriptions = text_descriptions;
        this.qte = qte;
        this.pu = pu;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());

        View view = getActivity().getLayoutInflater().inflate(R.layout.modifier_details_besoin, null);

        MaterialButton btn_enregistre_details = view.findViewById(R.id.btn_submit_modifier_details);
        TextInputEditText edit_libelle_details = view.findViewById(R.id.edit_libelle_modifier_detail_besoin);
        edit_libelle_details.setText("" + this.text_descriptions);
        final TextInputEditText edit_pu = view.findViewById(R.id.edit_pu_modifier_detail_besoin);
        edit_pu.setText("" + this.pu);
        final TextInputEditText edit_qte = view.findViewById(R.id.edit_qte_modifier_detail_besoin);
        edit_qte.setText("" + this.qte);
        alertDialogBuilder.setView(view);
        alertDialogBuilder.setCancelable(true);

        btn_enregistre_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (!edit_libelle_details.getText().toString().equals("") ||
                        edit_qte.getText().toString() != null || edit_pu.getText().toString() != null)
                {
                    DetailBesoinRapport ajouter_details = (DetailBesoinRapport) getActivity();
                    ajouter_details.sendModifierDetailResultatValider(
                            idDetailEBOnline, idDetails_Besoin, edit_libelle_details.getText().toString(),
                            codeArticle,
                            Double.valueOf(edit_qte.getText().toString()), Double.valueOf(edit_pu.getText().toString()));
                }
            }
        });


        return alertDialogBuilder.create();
    }
}
