package com.soft.ssvapp.Fragment_Menu.Fill_EtatBesoin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.soft.ssvapp.Fragment_Menu.Fill_Operation.Details_EtatBesoinValider;
import com.soft.ssvapp.R;

public class Modifier_Details_Besoin extends DialogFragment {

    private String text_descriptions;
    private String codeArticle;
    private double qte;
    private double pu;
    private int idDetails_Besoin;
    private int idDetailEBOnline;
    private String origin_call;

    public Modifier_Details_Besoin(int idDetailEBOnline, int idDetailsBesoin, String codeArticle, String text_descriptions,
                                   double qte, double pu, String origin_call)
    {
        this.idDetailEBOnline = idDetailEBOnline;
        this.idDetails_Besoin = idDetailsBesoin;
        this.codeArticle = codeArticle;
        this.text_descriptions = text_descriptions;
        this.qte = qte;
        this.pu = pu;
        this.origin_call = origin_call;
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
                    if (origin_call.equals("details_besoin"))
                    {
                        Details_Besoin ajouter_details = (Details_Besoin) getActivity();
                        ajouter_details.sendModifierDetailResultat(
                                idDetailEBOnline, idDetails_Besoin, codeArticle, edit_libelle_details.getText().toString(),
                                Double.valueOf(edit_qte.getText().toString()), Double.valueOf(edit_pu.getText().toString()));
                    }
                    else
                    {
                        Details_EtatBesoinValider ajouter_details = (Details_EtatBesoinValider) getActivity();
                        ajouter_details.sendModifierDetailResultatValider(
                                idDetailEBOnline, idDetails_Besoin, edit_libelle_details.getText().toString(),
                                codeArticle,
                                Double.valueOf(edit_qte.getText().toString()), Double.valueOf(edit_pu.getText().toString()));
                    }
                }
            }
        });


        return alertDialogBuilder.create();
    }

}
