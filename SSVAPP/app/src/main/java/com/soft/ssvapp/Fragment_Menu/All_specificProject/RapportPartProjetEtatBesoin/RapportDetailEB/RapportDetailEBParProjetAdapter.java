package com.soft.ssvapp.Fragment_Menu.All_specificProject.RapportPartProjetEtatBesoin.RapportDetailEB;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.soft.ssvapp.DataRetrofit.DetailsEtatDeBesoin.DetailsEtatDeBesoinRetrofit;
import com.soft.ssvapp.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class RapportDetailEBParProjetAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<DetailsEtatDeBesoinRetrofit> arrayList_adapter;
    private String kind_besoin;
//    private double solde;

    public RapportDetailEBParProjetAdapter(Context mcontext) {
        this.kind_besoin = kind_besoin;
        this.context = mcontext;
        this.inflater = LayoutInflater.from(context);
        this.arrayList_adapter = new ArrayList<DetailsEtatDeBesoinRetrofit>();
//        this.solde = solde;
    }

    class HoldView
    {
        TextView textView_libelle;
        TextView textView_qte;
        TextView textView_pu;
        TextView textView_article;
        TextView textView_total;
//        TextView textView_solde;
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
        final HoldView holdView;

        if (convertView == null) {
            holdView = new HoldView();

            convertView = inflater.inflate(R.layout.model_detail_eb_rapport_par_projet, null);

            holdView.textView_libelle = convertView.findViewById(R.id.libelle_model_rapport_eb_par_projet);
            holdView.textView_qte = convertView.findViewById(R.id.qte_model_rapport_eb_par_projet);
            holdView.textView_pu = convertView.findViewById(R.id.pu_model_rapport_eb_par_projet);
            holdView.textView_total = convertView.findViewById(R.id.total_model_rapport_eb_par_projet);
//            holdView.textView_solde = convertView.findViewById(R.id.solde_model_rapport_eb_par_projet);
            holdView.textView_article = convertView.findViewById(R.id.article_model_rapport_eb_par_projet);
            convertView.setTag(holdView);
        }
        else {
            holdView = (HoldView)convertView.getTag();
        }

        holdView.textView_libelle.setText("" + arrayList_adapter.get(position).getLibelleDetail());
        holdView.textView_qte.setText("" + arrayList_adapter.get(position).getQte());
        holdView.textView_pu.setText("" + arrayList_adapter.get(position).getPu());
        holdView.textView_total.setText("" +
                new DecimalFormat("##.##").format(
                        (arrayList_adapter.get(position).getQte()*
                                arrayList_adapter.get(position).getPu())));
        holdView.textView_article.setText("" + arrayList_adapter.get(position).getDesignationArticle());
//        holdView.textView_solde.setText("$" + solde);

        return convertView;
    }

    public void setDetailsBesoin(List<DetailsEtatDeBesoinRetrofit> entity_detailBesoins)
    {
        this.arrayList_adapter.clear();
        this.arrayList_adapter.addAll(entity_detailBesoins);
    }
}
