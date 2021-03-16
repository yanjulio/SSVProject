package com.soft.ssvapp.Fragment_Menu.All_specificProject.RapportPartProjetEtatBesoin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import com.soft.ssvapp.DataRetrofit.RapportParProjet.RapportEBParProjetValideEtDecaisse;
import com.soft.ssvapp.Fragment_Menu.All_specificProject.RapportPartProjetEtatBesoin.RapportDetailEB.RapportDetailEBParProjet;
import com.soft.ssvapp.Login;
import com.soft.ssvapp.R;

import java.util.ArrayList;
import java.util.List;

public class RapportEBParProjetAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private ArrayList<RapportEBParProjetValideEtDecaisse> arrayList_etatBesoin_adapter;
    private String niveauUtlisateur;
    private String nomUtilisateur;
    SharedPreferences prefs;

    public RapportEBParProjetAdapter(Context mcontext)
    {
        this.context = mcontext;
        this.inflater = LayoutInflater.from(mcontext);
        this.arrayList_etatBesoin_adapter = new ArrayList<RapportEBParProjetValideEtDecaisse>();

        prefs = context.getSharedPreferences("lOGIN_USER", Context.MODE_PRIVATE);
        nomUtilisateur = prefs.getString(Login.USER_NAME, "");
        niveauUtlisateur = prefs.getString(Login.NIVEAUUTILISATEUR, "");
    }

    class HoldView
    {
        TextView textView_designation_eb;
        TextView textView_total;
        TextView textView_sommeDecaisse;
        TextView textView_solde;
        LinearLayout linearLayout_etat_besoin;
    }

    @Override
    public int getCount() {
        return arrayList_etatBesoin_adapter.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList_etatBesoin_adapter.get(position);
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

            convertView = inflater.inflate(R.layout.model_etat_besoin_rapport_par_projet, null);

            holdView.textView_designation_eb = convertView.findViewById(R.id.txt_designation_eb_rapport_model);
            holdView.textView_total = convertView.findViewById(R.id.txt_total_eb_rapport_par_projet_model);
            holdView.textView_sommeDecaisse = convertView.findViewById(R.id.txt_somme_decaisse_eb_rapport_par_projet_model);
            holdView.textView_solde = convertView.findViewById(R.id.txt_solde_eb_rapport_par_projet_model);
            holdView.linearLayout_etat_besoin = convertView.findViewById(R.id.linear_eb_rapport_par_projet_model);

            convertView.setTag(holdView);
        }
        else
        {
            holdView = (HoldView)convertView.getTag();
        }

        if (arrayList_etatBesoin_adapter.size() != 0)
        {
            holdView.textView_designation_eb.setText("" + arrayList_etatBesoin_adapter.get(position).getCodeEtatdeBesoin()+ "/" +
                    arrayList_etatBesoin_adapter.get(position).getDesignationEtatDeBesion());
            holdView.textView_total.setText(""+ arrayList_etatBesoin_adapter.get(position).getTotal());
            holdView.textView_sommeDecaisse.setText("" + arrayList_etatBesoin_adapter.get(position).getSommeDecaisse());
            holdView.textView_solde.setText("$" + (arrayList_etatBesoin_adapter.get(position).getTotal()-
                    arrayList_etatBesoin_adapter.get(position).getSommeDecaisse()));
            String reste = String.valueOf((arrayList_etatBesoin_adapter.get(position).getTotal()-
                    arrayList_etatBesoin_adapter.get(position).getSommeDecaisse()));
            holdView.linearLayout_etat_besoin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppCompatActivity appCompatActivity = (AppCompatActivity)context;
                    appCompatActivity.startActivity(new Intent(context, RapportDetailEBParProjet.class)
                            .putExtra(RapportDetailEBParProjet.CODEBESOIN,
                                    arrayList_etatBesoin_adapter.get(position).getCodeEtatdeBesoin())
                            .putExtra(RapportDetailEBParProjet.DESINGATION_BESOIN,
                                    arrayList_etatBesoin_adapter.get(position).getDesignationEtatDeBesion())
                            .putExtra(RapportDetailEBParProjet.RESTE_ASERVIR, reste));
                }
            });
        }

        return convertView;
    }

    public void setEtatBesoin(List<RapportEBParProjetValideEtDecaisse> list)
    {
        arrayList_etatBesoin_adapter.clear();
        arrayList_etatBesoin_adapter.addAll(list);
    }
}
