package com.soft.ssvapp.Fragment_Menu.Fill_EtatBesoin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.soft.ssvapp.Data.Entity_Ligne;
import com.soft.ssvapp.Data.Entity_ProjectWithEntity_Ligne;
import com.soft.ssvapp.DataRetrofit.Ligne.LigneRepository;
import com.soft.ssvapp.DataRetrofit.Ligne.LigneRetrofit;
import com.soft.ssvapp.Fragment_Menu.Fill_Projects.ProjectFagment.LigneViewModel;
import com.soft.ssvapp.R;

import org.w3c.dom.Entity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AjouterDetailBesoin extends DialogFragment {

    LigneRepository ligneRepository;
    LigneViewModel view_model;

    private Spinner spinner_code_ligne;
    private ArrayList<Entity_ProjectWithEntity_Ligne> spinner_list = new ArrayList<Entity_ProjectWithEntity_Ligne>();
    private String codeLigne ="";
    private String codeProjet;

    public AjouterDetailBesoin(String codeProjet) {
        // Required empty public constructor
        this.codeProjet = codeProjet;
        ligneRepository = LigneRepository.getInstance();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());

        // Inflate the layout for this fragment
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_ajouter_detail_besoin, null);

//        MaterialButton btn_enregistre_details = view.findViewById(R.id.btn_submit_ajouter_details);
        TextInputEditText edit_libelle_details = view.findViewById(R.id.edit_libelle_ajouter_detail_besoin);
        final TextInputEditText edit_pu = view.findViewById(R.id.edit_pu_ajouter_detail_besoin);
        final TextInputEditText edit_qte = view.findViewById(R.id.edit_qte_ajouter_detail_besoin);

//        spinner_code_ligne = view.findViewById(R.id.spinner_code_ligne);

        view_model = ViewModelProviders.of(this).get(LigneViewModel.class);
        view_model.init(codeProjet);
        view_model.getCodeLigne().observe(this, new Observer<List<Entity_ProjectWithEntity_Ligne>>() {
            @Override
            public void onChanged(List<Entity_ProjectWithEntity_Ligne> codeLigne) {
                spinner_list.clear();
                spinner_list.addAll(codeLigne);
//                ligne();
                // for the spinner
                spinnerValue();
            }
        });

        alertDialogBuilder.setView(view);
        alertDialogBuilder.setCancelable(true);

        return alertDialogBuilder.create();
    }

    void spinnerValue()
    {
        ArrayAdapter adapter_project = new ArrayAdapter(
                getContext(),
                android.R.layout.simple_list_item_1,
                getString(spinner_list));
        adapter_project.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_code_ligne.setAdapter(adapter_project);

        spinner_code_ligne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                codeLigne = spinner_list.get(position).getEntity_ligne().getCodeLigne();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    ArrayList<String> getString(ArrayList<Entity_ProjectWithEntity_Ligne> list)
    {
        ArrayList<String> list_local = new ArrayList<>();
        for (int a = 0; a < list.size(); a++)
        {
            list_local.add(list.get(a).getEntity_ligne().getDesignationLigne());
        }
        return list_local;
    }

    public void ligne()
    {
        ligneRepository.ligneConnexion().getLignesParProjet(codeProjet).enqueue(new Callback<List<LigneRetrofit>>() {
            @Override
            public void onResponse(Call<List<LigneRetrofit>> call, Response<List<LigneRetrofit>> response) {
                if (response.isSuccessful())
                {
                    Entity_Ligne entity_ligne_from_response;
                    if(spinner_list.isEmpty())
                    {
                        for (int a = 0; a < response.body().size(); a++)
                        {
//                            Toast.makeText(getContext(), "au debut je suis null", Toast.LENGTH_LONG).show();
                            entity_ligne_from_response = new Entity_Ligne(
                                    response.body().get(a).getCodeLigne(),
                                    response.body().get(a).getCodeProject(),
                                    response.body().get(a).getDesignationLIgne(),
                                    response.body().get(a).getPrevision()
                            );
                            view_model.insert(entity_ligne_from_response);
                        }
                    }
                    else
                    {
                        for (int a = 0; a < response.body().size(); a++)
                        {
                            entity_ligne_from_response = new Entity_Ligne(
                                    response.body().get(a).getCodeLigne(),
                                    response.body().get(a).getCodeProject(),
                                    response.body().get(a).getDesignationLIgne(),
                                    response.body().get(a).getPrevision()
                            );
                            int e = 0;
                            for (int i = 0; i < spinner_list.size(); i++)
                            {
                                if (response.body().get(a).getCodeProject()
                                        .equals(spinner_list.get(i).getEntity_ligne().getCodeProject()))
                                {
                                    int idligne = spinner_list.get(i).getEntity_ligne().getIdLigne();
                                    entity_ligne_from_response.setIdLigne(idligne);
                                    view_model.update(entity_ligne_from_response);
                                    e = 1;
                                }
                                else if(e!=1)
                                {
//                                    Toast.makeText(getContext(), "j'insert Ligne.", Toast.LENGTH_LONG).show();
                                    view_model.insert(entity_ligne_from_response);
                                    e = 0;
                                }
                            }
                        }
                    }

                }
                else
                {
                    switch (response.code())
                    {
                        case 404:
                            Toast.makeText(getContext(), "server not found.", Toast.LENGTH_LONG).show();
                            break;
                        case 500:
                            Toast.makeText(getContext(), "server broken.", Toast.LENGTH_LONG).show();
                            break;
                        default:
                            Toast.makeText(getContext(), "problem connexion unknown.", Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<LigneRetrofit>> call, Throwable t) {
                Toast.makeText(getContext(), "Connexion Probleme, Verifier votre connexion.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
