package com.soft.ssvapp.Fragment_Menu.Fill_Rapport.DetailRapport;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.soft.ssvapp.DataRetrofit.Rapport.RapportOperationResponse;
import com.soft.ssvapp.Login;
import com.soft.ssvapp.R;

import java.util.List;

public class Supprimer_details_pv extends DialogFragment {

        DetailRapportRemoteViewModel detailRapportRemoteViewModel;
    Supprimer_details_pvAdapter adapter;
    private String date1, date2;
    private ListView listView_details_pv;
    private MaterialButton btn_suprimer_details_pv;
    private MaterialButton btn_quitter_details_pv;
    private String numOperation;
    private int num_compte;

    SharedPreferences preferences;

    public Supprimer_details_pv(String numOperation, int num_compte, String date1, String date2)
    {
        this.numOperation = numOperation;
        this.num_compte = num_compte;
        this.date1 = date1;
        this.date2 = date2;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        preferences = getActivity().getSharedPreferences("lOGIN_USER", Context.MODE_PRIVATE);
        String password = preferences.getString(Login.PASS_WORD, "");
        String niveau = preferences.getString(Login.NIVEAUUTILISATEUR, "");
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        detailRapportRemoteViewModel = ViewModelProviders.of(getActivity()).get(DetailRapportRemoteViewModel.class);

        View view = getActivity().getLayoutInflater().inflate(R.layout.detaille_pv, null);
        listView_details_pv = view.findViewById(R.id.listView_details_pv);
        btn_suprimer_details_pv = view.findViewById(R.id.btn_supprimer_detail_pv);
        btn_quitter_details_pv = view.findViewById(R.id.btn_quitter_detail_pv);
        adapter = new Supprimer_details_pvAdapter(getContext());
        setList();

        builder.setView(view);

        final AlertDialog alert = builder.create();
        DetailBalance balance = (DetailBalance)getActivity();

        btn_suprimer_details_pv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();

                AlertDialog.Builder builder_supprimer_verification = new AlertDialog.Builder(getActivity());
                builder_supprimer_verification.setMessage("Voulez-vous vraiment supprimer cette opération?");
                View view_supprimer_verification =
                        getActivity().getLayoutInflater().inflate(R.layout.code_verification_compte_suppression, null);
                TextInputEditText editText_password = view_supprimer_verification.findViewById(R.id.edit_code_supprimer_verification);
                MaterialButton button_ok = view_supprimer_verification.findViewById(R.id.btn_supprimer_verification);
                MaterialButton button_non = view_supprimer_verification.findViewById(R.id.btn_quitter_supprimer_verification);
                builder_supprimer_verification.setView(view_supprimer_verification);
                builder_supprimer_verification.setCancelable(true);

                final AlertDialog aler_ok = builder_supprimer_verification.create();
                button_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        aler_ok.dismiss();
                        if ((password.equals(editText_password.getText().toString())) && niveau.equals("ADMIN"))
                        {
                                detailRapportRemoteViewModel.supprimerOperation(numOperation);
                            balance.loadData(num_compte, date1, date2);
                        }
                        else
                        {
                            Toast.makeText(view_supprimer_verification.getContext(),
                                    "Vous n'avez pas la permission de supprimer une operation",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
                button_non.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        aler_ok.dismiss();
                    }
                });
                aler_ok.show();
            }
        });

        btn_quitter_details_pv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return alert;
    }

    public void setList()
    {
        detailRapportRemoteViewModel.getDetailOperation(numOperation).observe(getActivity(),
                new Observer<List<RapportOperationResponse>>() {
            @Override
            public void onChanged(List<RapportOperationResponse> rapportOperationResponses) {
                adapter.setDetails_Pv(rapportOperationResponses);
                listView_details_pv.setAdapter(adapter);
            }
        });
    }
}
