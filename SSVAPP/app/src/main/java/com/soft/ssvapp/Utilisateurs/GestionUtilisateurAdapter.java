package com.soft.ssvapp.Utilisateurs;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.soft.ssvapp.DataRetrofit.UtilisateurResponse;
import com.soft.ssvapp.R;

import java.util.ArrayList;
import java.util.List;

public class GestionUtilisateurAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private ArrayList<UtilisateurResponse> arrayList;

    public GestionUtilisateurAdapter(Context context)
    {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.arrayList = new ArrayList<>();
    }

    class HoldView
    {
        TextView textView_designgation, textView_fonction, textView_service_aff, textView_actif;
        LinearLayout linearLayout_gestion_utilisateur;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final HoldView holdView;

        if (convertView == null)
        {
            holdView = new HoldView();

            convertView = inflater.inflate(R.layout.model_gestion_utilisateur, null);

            holdView.textView_designgation = convertView.findViewById(R.id.text_designation_gestion_utilisateur);
            holdView.textView_fonction = convertView.findViewById(R.id.text_fonction_gestion_utilisateur);
            holdView.textView_service_aff = convertView.findViewById(R.id.text_service_gestion_utilisateur);
            holdView.textView_actif = convertView.findViewById(R.id.text_actif_gestion_utilisateur);
            holdView.linearLayout_gestion_utilisateur = convertView.findViewById(R.id.linear_gestion_utilisateur);

            convertView.setTag(holdView);
        }
        else
        {
            holdView = (HoldView)convertView.getTag();
        }

        holdView.textView_designgation.setText("" + arrayList.get(position).getDesignationUt());
        holdView.textView_fonction.setText("" + arrayList.get(position).getFonctionUt());
        holdView.textView_service_aff.setText("" + arrayList.get(position).getServiceAffe());
        if (arrayList.get(position).getActif() == 1)
        {
            holdView.textView_actif.setText("ACTIF");
        }
        else
        {
            holdView.textView_actif.setText("NON ACTIF");
        }
        holdView.linearLayout_gestion_utilisateur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity appCompatActivity = (AppCompatActivity)context;
                appCompatActivity.startActivityForResult(new Intent(context, ValiderUtilisateur.class)
                        .putExtra(ValiderUtilisateur.ID_UTILISATEUR, arrayList.get(position).getIdUtilisateur())
                        .putExtra(ValiderUtilisateur.NOM_UTILISATEUR, arrayList.get(position).getNomUtilisateur())
                        .putExtra(ValiderUtilisateur.MOT_DEPASSE, arrayList.get(position).getMotDePassUtilisateur())
                        .putExtra(ValiderUtilisateur.DESIGNATION_UTILISATEUR, arrayList.get(position).getDesignationUt())
                        .putExtra(ValiderUtilisateur.FONCTION_UTILISATEUR, arrayList.get(position).getFonctionUt())
                        .putExtra(ValiderUtilisateur.ACTIVER, arrayList.get(position).getActif())
                        .putExtra(ValiderUtilisateur.COMPTE, arrayList.get(position).getCompte())
//                        .putExtra(ValiderUtilisateur.COMPTE_DECLARATION, arrayList.get(position).getCompteDeclaration())
                        .putExtra(ValiderUtilisateur.SERVICE, arrayList.get(position).getServiceAffe())
                        , 10);

            }
        });

        return convertView;
    }

    public void setUtilisateur(List<UtilisateurResponse> list)
    {
        this.arrayList.clear();
        this.arrayList.addAll(list);
    }
}
