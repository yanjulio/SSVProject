package com.soft.ssvapp.Utilisateurs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.soft.ssvapp.DataRetrofit.UtilisateurResponse;
import com.soft.ssvapp.Login;
import com.soft.ssvapp.R;

public class ModifierUtilisateur extends DialogFragment {

    UtilisateurViewModel utilisateurViewModel;
    private UtilisateurResponse utilisateurResponse;
    MaterialButton quitter_btn, ok_btn;

    public ModifierUtilisateur(UtilisateurResponse utilisateurResponse)
    {
        this.utilisateurResponse = utilisateurResponse;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Login login = (Login)getContext();
        utilisateurViewModel = ViewModelProviders.of(getActivity()).get(UtilisateurViewModel.class);
        View view = getActivity().getLayoutInflater().inflate(R.layout.modifier_utilisateur, null);
        TextView textView_text_title = view.findViewById(R.id.text_titre_modifier_utilisateur);
        textView_text_title.setText("Modifier Utilisateur");
        TextInputEditText edit_nomUtilisateur = view.findViewById(R.id.edit_modifier_nomutilisateur_utilisateur);
        edit_nomUtilisateur.setText("" + utilisateurResponse.getNomUtilisateur());
        edit_nomUtilisateur.setEnabled(false);
        TextInputEditText edit_motDePass = view.findViewById(R.id.edit_modifier_motDePass_utilisateur);
        edit_motDePass.setText(""+ utilisateurResponse.getMotDePassUtilisateur());
//        Toast.makeText(getContext(), "password : " + utilisateurResponse.getMotDePassUtilisateur(), Toast.LENGTH_LONG).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setCancelable(true);

        quitter_btn = view.findViewById(R.id.btn_quitter_modifier_utilisateur);
        quitter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        ok_btn = view.findViewById(R.id.btn_ok_modifier_utilisateur);
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilisateurResponse utilisateurResponse_locale =
                        new UtilisateurResponse(utilisateurResponse.getIdUtilisateur(), edit_nomUtilisateur.getText().toString(),
                                utilisateurResponse.getDesignationUt(), edit_motDePass.getText().toString(),
                                utilisateurResponse.getFonctionUt(), utilisateurResponse.getServiceAffe(),
                                utilisateurResponse.getCompte(), utilisateurResponse.getActif());
                utilisateurViewModel.modifierUtilisateur(utilisateurResponse_locale);
                login.setIdentifiant(utilisateurResponse);
                dismiss();
            }
        });

        utilisateurViewModel.getIsLoagin().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                login.ModifierDialog(aBoolean);
            }
        });

        return builder.create();
    }
}
