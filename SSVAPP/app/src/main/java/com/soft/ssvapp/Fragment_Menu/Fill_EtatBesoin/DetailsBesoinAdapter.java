package com.soft.ssvapp.Fragment_Menu.Fill_EtatBesoin;

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

import androidx.fragment.app.FragmentManager;

import com.soft.ssvapp.Data.Entity_DetailBesoin;
import com.soft.ssvapp.Data.Entity_DetailBesoinWithEntity_Article;
import com.soft.ssvapp.DataRetrofit.DetailsEtatDeBesoin.DetailsEtatDeBesoinRepository;
import com.soft.ssvapp.R;

import java.lang.reflect.Modifier;
import java.text.DecimalFormat;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailsBesoinAdapter extends BaseAdapter {

    DetailsEtatDeBesoinRepository detailsEtatDeBesoinRepository;
    private LayoutInflater inflater;
    private Context context;
    private ArrayList<Entity_DetailBesoinWithEntity_Article> arrayList_adapter;
    private String kind_besoin;
    private String codeEtatDeBesoin;

    public DetailsBesoinAdapter(Context mcontext, String codeEtatDeBesoin) {
        this.kind_besoin = kind_besoin;
        this.codeEtatDeBesoin = codeEtatDeBesoin;
        this.context = mcontext;
        this.inflater = LayoutInflater.from(context);
        this.arrayList_adapter = new ArrayList<Entity_DetailBesoinWithEntity_Article>();
        detailsEtatDeBesoinRepository = DetailsEtatDeBesoinRepository.getInstance();
    }

    class HoldView
    {
        TextView textView_libelle;
        TextView textView_qte;
        TextView textView_pu;
        TextView textView_article;
        TextView textView_total_article;
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

        if (convertView == null) {
            holdView = new HoldView();
            convertView = inflater.inflate(R.layout.model_afficher_details_besoin, null);
            holdView.textView_libelle = convertView.findViewById(R.id.libelle_model_detail_besoin);
            holdView.textView_qte = convertView.findViewById(R.id.qte_model_detail_besoin);
            holdView.textView_pu = convertView.findViewById(R.id.pu_model_detail_besoin);
            holdView.linearLayout = convertView.findViewById(R.id.linear_details_element);
            holdView.textView_article = convertView.findViewById(R.id.article_model_detail_besoin);
            holdView.textView_total_article = convertView.findViewById(R.id.total_article_model_detail_besoin);
            convertView.setTag(holdView);
        }
        else {
                holdView = (HoldView)convertView.getTag();
        }

        holdView.textView_libelle.setText("" + arrayList_adapter.get(position).getDetailBesoin().getLibelleDetail());
        holdView.textView_qte.setText("" + arrayList_adapter.get(position).getDetailBesoin().getQte());
        holdView.textView_pu.setText("" + arrayList_adapter.get(position).getDetailBesoin().getPu());
        holdView.textView_article.setText("" + arrayList_adapter.get(position).getEntity_article().getDesignationArticle());
        holdView.textView_total_article.setText("" +
                (new DecimalFormat("##.##").format(
                        arrayList_adapter.get(position).getDetailBesoin().getQte()*
                                arrayList_adapter.get(position).getDetailBesoin().getPu())));

        holdView.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Details_Besoin details_besoin = (Details_Besoin)context;
                    details_besoin.openModifierDilog(
                            arrayList_adapter.get(position).getDetailBesoin().getIdDetailEBOnline(),
                            arrayList_adapter.get(position).getDetailBesoin().getIdDetailEB(),
                            arrayList_adapter.get(position).getDetailBesoin().getCodeArticle(),
                            arrayList_adapter.get(position).getDetailBesoin().getLibelleDetail(),
                            arrayList_adapter.get(position).getDetailBesoin().getQte(),
                            arrayList_adapter.get(position).getDetailBesoin().getPu());
            }
        });

//        Toast.makeText(context, "size of the details : " + arrayList_adapter.size(), Toast.LENGTH_LONG).show();
        holdView.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Voulez-vous supprimer ce besoin.");
                builder.setCancelable(true);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Entity_DetailBesoin entity_detailBesoin =
                                new Entity_DetailBesoin(
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
                        Details_Besoin details_besoin = (Details_Besoin)context;
                        details_besoin.deleteDetails(arrayList_adapter.get(position).getDetailBesoin());
                    }
                });
                builder.show();
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
