package com.soft.ssvapp.Fragment_Menu.Fill_Rapport.RapportSuiviProjet;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.soft.ssvapp.DataRetrofit.Rapport.RapportDetailProjetResponse;
import com.soft.ssvapp.Fragment_Menu.All_specificProject.RapportArticleParLigne.RapportArticleParLigneActivity;
import com.soft.ssvapp.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class DetailSuiviProjetAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<RapportDetailProjetResponse> arrayList_adapter;
    private String codeProjet;

    public DetailSuiviProjetAdapter(Context context, String codeProjet)
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
                new DecimalFormat("##.##").format(arrayList_adapter.get(position).gettOtalPrevision()));
        holdView.textView_consommation.setText("" +
                new DecimalFormat("##.##").format(arrayList_adapter.get(position).getTotalConsommation()));
        holdView.textView_taux.setText("" + new DecimalFormat("##.##").format(arrayList_adapter.get(position).getTauxCons()));

        holdView.linearLayout_model.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, RapportArticleParLigneActivity.class)
                        .putExtra(RapportArticleParLigneActivity.CODE_PRJET, codeProjet)
                        .putExtra(RapportArticleParLigneActivity.CODE_LIGNE, arrayList_adapter.get(position).getCodeLigne())
                        .putExtra(RapportArticleParLigneActivity.DESIGNATION_LIGNE, arrayList_adapter.get(position).getDesignationLIgne()));
            }
        });

        return convertView;
    }

    public void setDetailSuiviProjet(List<RapportDetailProjetResponse> list)
    {
        this.arrayList_adapter.clear();
        this.arrayList_adapter.addAll(list);
    }
}
