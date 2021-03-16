package com.soft.ssvapp.Fragment_Menu.Fill_Rapport.RapportEtatDeBesoin.RapportDetailEtatBesoin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseIidMessengerCompat;
import com.soft.ssvapp.Data.Entity_DetailBesoin;
import com.soft.ssvapp.DataRetrofit.DetailsEtatDeBesoin.DetailsEtatDeBesoinRetrofit;
import com.soft.ssvapp.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DetailBesoinRapportAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private String etat;
    private ArrayList<DetailsEtatDeBesoinRetrofit> arrayList_adapter;

    public DetailBesoinRapportAdapter(Context context, String etat)
    {
        this.context = context;
        this.etat = etat;
        this.layoutInflater = LayoutInflater.from(context);
        this.arrayList_adapter = new ArrayList<>();
    }

    class HoldView
    {
        TextView textView_Libelle, textView_qte, textView_pu, textView_article, textView_total;
        LinearLayout linearLayout;
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

        if (convertView == null)
        {
            holdView = new HoldView();

            convertView =
                    layoutInflater.inflate(R.layout.model_details_besoin_valider, null); // ici on utilise le mode pour valider a la place

            holdView.textView_Libelle = convertView.findViewById(R.id.libelle_model_detail_besoin_valider);
            holdView.textView_qte = convertView.findViewById(R.id.qte_model_detail_besoin_valider);
            holdView.textView_pu = convertView.findViewById(R.id.pu_model_detail_besoin_valider);
            holdView.textView_article = convertView.findViewById(R.id.article_model_detail_besoin_valider);
            holdView.textView_total = convertView.findViewById(R.id.total_model_detail_besoin_valider);
            holdView.linearLayout = convertView.findViewById(R.id.linear_details_element_valider);
            convertView.setTag(holdView);
        }
        else
        {
            holdView = (HoldView)convertView.getTag();
        }

        holdView.textView_Libelle.setText("" + arrayList_adapter.get(position).getLibelleDetail());
        holdView.textView_qte.setText("" + arrayList_adapter.get(position).getQte());
        holdView.textView_pu.setText("" + arrayList_adapter.get(position).getPu());
        holdView.textView_article.setText("" + arrayList_adapter.get(position).getDesignationArticle());
        holdView.textView_total.setText("" + (
                new DecimalFormat("##.##").format(arrayList_adapter.get(position).getQte() *
                        arrayList_adapter.get(position).getPu())));


        if (!etat.equals("VALIDE") && !etat.equals("SERVI"))
        {
            holdView.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DetailBesoinRapport details_besoin = (DetailBesoinRapport) context;
                    details_besoin.openModifierDilogValider(
                            arrayList_adapter.get(position).getIdDetailEB(),
                            arrayList_adapter.get(position).getIdDetailEB(),
                            arrayList_adapter.get(position).getLibelleDetail(),
                            arrayList_adapter.get(position).getCodeArticle(),
                            arrayList_adapter.get(position).getQte(),
                            arrayList_adapter.get(position).getPu());
                }
            });

            holdView.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Entity_DetailBesoin entity_detailBesoin = new Entity_DetailBesoin(
                            arrayList_adapter.get(position).getIdDetailEB(),
                            arrayList_adapter.get(position).getCodeEtatdeBesoin(),
                            arrayList_adapter.get(position).getCodeArticle(),
                            arrayList_adapter.get(position).getCodeLigne(),
                            arrayList_adapter.get(position).getLibelleDetail(),
                            arrayList_adapter.get(position).getQte(),
                            arrayList_adapter.get(position).getPu(),
                            arrayList_adapter.get(position).getEntree(),
                            arrayList_adapter.get(position).getSortie());
                    entity_detailBesoin.setIdDetailEB(arrayList_adapter.get(position).getIdDetailEB());
                    DetailBesoinRapport details_etatBesoinValider = (DetailBesoinRapport) context;
                    details_etatBesoinValider.deleteRemote(entity_detailBesoin);
                    return false;
                }
            });
        }

        return convertView;
    }

    public void setDetailBesoinRapport(List<DetailsEtatDeBesoinRetrofit> list)
    {
        this.arrayList_adapter.clear();
        this.arrayList_adapter.addAll(list);
    }
}
