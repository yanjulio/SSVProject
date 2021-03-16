package com.soft.ssvapp.Fragment_Menu.All_specificProject.RapportReferenceEtatBesoinCons;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.soft.ssvapp.DataRetrofit.RapportParProjet.RapportReferenceEtatBesoinConsomme;
import com.soft.ssvapp.R;

import java.util.ArrayList;
import java.util.List;

public class RapportReferenceEtatBesoinConsAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private ArrayList<RapportReferenceEtatBesoinConsomme> arrayList;

    public RapportReferenceEtatBesoinConsAdapter(Context context)
    {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.arrayList = new ArrayList<RapportReferenceEtatBesoinConsomme>();
    }

    class HoldView
    {
        TextView textView_designationEB, textView_qte, textView_pu, textView_total, textView_date_emission;
        LinearLayout linearLayout_RapportRefEB;
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
        if (convertView==null)
        {
            holdView = new HoldView();

            convertView = inflater.inflate(R.layout.model_rapport_ref_eb_cons, null);

            holdView.textView_designationEB = convertView.findViewById(R.id.txt_model__designation_ref_eb_cons);
            holdView.textView_qte = convertView.findViewById(R.id.txt_model_qte_ref_eb_cons);
            holdView.textView_pu = convertView.findViewById(R.id.txt_model_pu_ref_eb_cons);
            holdView.textView_total = convertView.findViewById(R.id.txt_model_total_ref_eb_cons);
            holdView.textView_date_emission = convertView.findViewById(R.id.txt_model_date_emission_ref_eb_cons);
            holdView.linearLayout_RapportRefEB = convertView.findViewById(R.id.linear_model_ref_eb_cons);

            convertView.setTag(holdView);
        }
        else
        {
            holdView = (HoldView)convertView.getTag();
        }

        holdView.textView_designationEB.setText(""+ arrayList.get(position).getDesignationEtatDeBesion());
        holdView.textView_qte.setText(""+ arrayList.get(position).getQte());
        holdView.textView_pu.setText(""+ arrayList.get(position).getPu());
        holdView.textView_total.setText(""+ arrayList.get(position).getTotalConsommation());
        holdView.textView_date_emission.setText(""+ arrayList.get(position).getDateEmision());

        return convertView;
    }

    public void setRapportRefEbCons(List<RapportReferenceEtatBesoinConsomme> list)
    {
        this.arrayList.clear();
        this.arrayList.addAll(list);
    }
}
