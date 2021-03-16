package com.soft.ssvapp.Fragment_Menu.Fill_Projects.ProjectFagment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.soft.ssvapp.R;

public class Modifier_deatils extends DialogFragment {

    private String text_descriptions;

    public Modifier_deatils (String text_descriptions)
    {
        this.text_descriptions = text_descriptions;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());

        View view = getActivity().getLayoutInflater().inflate(R.layout.edit_descriptions_projet, null);

        MaterialButton btn_description = view.findViewById(R.id.btn_description_projet);
        final TextInputEditText edit = view.findViewById(R.id.edit_descriptions_projets);
        edit.setText("" + this.text_descriptions);
        alertDialogBuilder.setView(view);
        alertDialogBuilder.setCancelable(true);

        btn_description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                Details details = (Details)getActivity();
                details.sendResultat(edit.getText().toString());
            }
        });


        return alertDialogBuilder.create();
    }
}
