package com.soft.ssvapp.Fragment_Menu.All_specificProject.RapportLignePartArticle;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soft.ssvapp.DataRetrofit.RapportParProjet.RapportLigneParArticleResponse;
import com.soft.ssvapp.Fragment_Menu.All_specificProject.RapportReferenceEtatBesoinCons.RapportReferenceEtatBesoinConsActivity;
import com.soft.ssvapp.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class RapportLigneParArticleAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<RapportLigneParArticleResponse> arrayList_adapter;
    private String codeProjet;

    public RapportLigneParArticleAdapter(Context context, String codeProjet)
    {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.arrayList_adapter = new ArrayList<>();
        this.codeProjet = codeProjet;
    }

    class HoldView
    {
        TextView textView_ligne, textView_prevision, textView_consommation, textView_taux;
        LinearLayout linearLayout_model;
    }

    @Override
    public int getCount() {
        return arrayList_adapter.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList_adapter.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final  HoldView holdView;

        if (convertView == null)
        {
            holdView = new HoldView();
            convertView = inflater.inflate(R.layout.model_detail_suivi_projet, null);
            // n'oubli pas que ici tu utilise ce model sur deux endroit, ici et dans les du projet pour voir les lignes aussi

            holdView.textView_ligne = convertView.findViewById(R.id.txt_ligne_model_detail_suivi_projet);
            holdView.textView_prevision = convertView.findViewById(R.id.txt_prevision_model_details_suivi_projet);
            holdView.textView_consommation = convertView.findViewById(R.id.txt_consomation_model_detail_suivi_projet);
            holdView.textView_taux = convertView.findViewById(R.id.txt_taux_model_detail_suivi_projet);
            holdView.linearLayout_model = convertView.findViewById(R.id.linear_model_detail_suivi_projet);
            convertView.setTag(holdView);
        }
        else
        {
            holdView = (HoldView)convertView.getTag();
        }

//        + arrayList_adapter.get(position).getCodeLigne() + "/"
        holdView.textView_ligne.setText(""
                + arrayList_adapter.get(position).getDesignationLIgne());
        holdView.textView_prevision.setText("" +
                new DecimalFormat("##.##").format(arrayList_adapter.get(position).getQte()));
        holdView.textView_consommation.setText("" +
                new DecimalFormat("##.##").format(arrayList_adapter.get(position).getPu()));
        holdView.textView_taux.setText("" + new DecimalFormat("##.##").format(arrayList_adapter.get(position).getTotalConsommation()));

        holdView.linearLayout_model.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, RapportReferenceEtatBesoinConsActivity.class)
                        .putExtra(RapportReferenceEtatBesoinConsActivity.CODE_PROJET, codeProjet)
                        .putExtra(RapportReferenceEtatBesoinConsActivity.CODE_ARTICLE,
                                arrayList_adapter.get(position).getCodeArticle())
                        .putExtra(RapportReferenceEtatBesoinConsActivity.CODE_LIGNE,
                                arrayList_adapter.get(position).getCodeLigne())
                        .putExtra(RapportReferenceEtatBesoinConsActivity.DESIGNATION_ARTICLE,
                                arrayList_adapter.get(position).getDesegnationArticle())
                        .putExtra(RapportReferenceEtatBesoinConsActivity.DESIGNATION_LIGNE,
                                arrayList_adapter.get(position).getDesignationLIgne()));
            }
        });

        return convertView;
    }

    public void setRapportLigneParArticle(List<RapportLigneParArticleResponse> list)
    {
        this.arrayList_adapter.clear();
        this.arrayList_adapter.addAll(list);
    }
}
