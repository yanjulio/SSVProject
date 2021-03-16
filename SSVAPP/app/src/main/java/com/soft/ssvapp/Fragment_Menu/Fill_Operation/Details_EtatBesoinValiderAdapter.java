package com.soft.ssvapp.Fragment_Menu.Fill_Operation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.soft.ssvapp.Data.Entity_DetailBesoin;
import com.soft.ssvapp.Data.Entity_DetailBesoinWithEntity_Article;
import com.soft.ssvapp.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Details_EtatBesoinValiderAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Entity_DetailBesoinWithEntity_Article> arrayList_adapter;
    private String kind_besoin;

    public Details_EtatBesoinValiderAdapter(Context mcontext) {
        this.kind_besoin = kind_besoin;
        this.context = mcontext;
        this.inflater = LayoutInflater.from(context);
        this.arrayList_adapter = new ArrayList<Entity_DetailBesoinWithEntity_Article>();
    }

    class HoldView
    {
        TextView textView_libelle;
        TextView textView_qte;
        TextView textView_pu;
        TextView textView_article;
        TextView textView_total;
        LinearLayout linearLayout_modifier;
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

            convertView = inflater.inflate(R.layout.model_details_besoin_valider, null);

            holdView.textView_libelle = convertView.findViewById(R.id.libelle_model_detail_besoin_valider);
            holdView.textView_qte = convertView.findViewById(R.id.qte_model_detail_besoin_valider);
            holdView.textView_pu = convertView.findViewById(R.id.pu_model_detail_besoin_valider);
            holdView.textView_total = convertView.findViewById(R.id.total_model_detail_besoin_valider);
            holdView.linearLayout_modifier = convertView.findViewById(R.id.linear_details_element_valider);
            holdView.textView_article = convertView.findViewById(R.id.article_model_detail_besoin_valider);
            convertView.setTag(holdView);
        }
        else {
            holdView = (HoldView)convertView.getTag();
        }

        holdView.textView_libelle.setText("" + arrayList_adapter.get(position).getDetailBesoin().getLibelleDetail());
        holdView.textView_qte.setText("" + arrayList_adapter.get(position).getDetailBesoin().getQte());
        holdView.textView_pu.setText("" + arrayList_adapter.get(position).getDetailBesoin().getPu());
        holdView.textView_total.setText("" +
                new DecimalFormat("##.##").format(
                        (arrayList_adapter.get(position).getDetailBesoin().getQte()*
                                arrayList_adapter.get(position).getDetailBesoin().getPu())));
        holdView.textView_article.setText("" + arrayList_adapter.get(position).getEntity_article().getDesignationArticle());


        holdView.linearLayout_modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Details_EtatBesoinValider details_besoin = (Details_EtatBesoinValider) context;
                details_besoin.openModifierDilogValider(
                        arrayList_adapter.get(position).getDetailBesoin().getIdDetailEBOnline(),
                        arrayList_adapter.get(position).getDetailBesoin().getIdDetailEB(),
                        arrayList_adapter.get(position).getDetailBesoin().getLibelleDetail(),
                        arrayList_adapter.get(position).getDetailBesoin().getCodeArticle(),
                        arrayList_adapter.get(position).getDetailBesoin().getQte(),
                        arrayList_adapter.get(position).getDetailBesoin().getPu());
            }
        });

        holdView.linearLayout_modifier.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Entity_DetailBesoin entity_detailBesoin = new Entity_DetailBesoin(
                        arrayList_adapter.get(position).getDetailBesoin().getIdDetailEBOnline(),
                        arrayList_adapter.get(position).getDetailBesoin().getCodeEtatdeBesoin(),
                        arrayList_adapter.get(position).getDetailBesoin().getCodeArticle(),
                        arrayList_adapter.get(position).getDetailBesoin().getCodeLigne(),
                        arrayList_adapter.get(position).getDetailBesoin().getLibelleDetail(),
                        arrayList_adapter.get(position).getDetailBesoin().getQte(),
                        arrayList_adapter.get(position).getDetailBesoin().getPu(),
                        arrayList_adapter.get(position).getDetailBesoin().getEntree(),
                        arrayList_adapter.get(position).getDetailBesoin().getSortie());
                entity_detailBesoin.setIdDetailEB(arrayList_adapter.get(position).getDetailBesoin().getIdDetailEB());
                Details_EtatBesoinValider details_etatBesoinValider = (Details_EtatBesoinValider) context;
                details_etatBesoinValider.deleteOnlineDetail(entity_detailBesoin);
                return false;
            }
        });

        return convertView;
    }

    public void setDetailsBesoin(List<Entity_DetailBesoinWithEntity_Article> entity_detailBesoins)
    {
        this.arrayList_adapter.clear();
        this.arrayList_adapter.addAll(entity_detailBesoins);
    }
}
