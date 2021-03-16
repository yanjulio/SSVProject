package com.soft.ssvapp.Fragment_Menu.Fill_Rapport.DetailRapport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.soft.ssvapp.DataRetrofit.Rapport.RapportOperationResponse;
import com.soft.ssvapp.R;

import java.util.ArrayList;
import java.util.List;

public class Supprimer_details_pvAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private ArrayList<RapportOperationResponse> arrayList_adapter;

    public Supprimer_details_pvAdapter(Context context)
    {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.arrayList_adapter = new ArrayList<>();
    }

    class HoldView{
        TextView textView_designation, textView_compte, textView_debit, textView_credit;
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
            convertView = inflater.inflate(R.layout.model_details_pv, null);

            holdView.textView_designation = convertView.findViewById(R.id.libelle_details_pv);
            holdView.textView_compte = convertView.findViewById(R.id.compte_details_pv);
            holdView.textView_debit = convertView.findViewById(R.id.entree_details_pv);
            holdView.textView_credit = convertView.findViewById(R.id.sortie_deails_pv);
            convertView.setTag(holdView);
        }
        else
        {
            holdView = (HoldView)convertView.getTag();
        }

        holdView.textView_designation.setText("" + arrayList_adapter.get(position).getDesignationCompte());
        holdView.textView_compte.setText(""+ arrayList_adapter.get(position).getNumCompte());
        holdView.textView_debit.setText("" + arrayList_adapter.get(position).getDebit());
        holdView.textView_credit.setText("" + arrayList_adapter.get(position).getCredit());

        return convertView;
    }

    public void setDetails_Pv(List<RapportOperationResponse> list)
    {
        this.arrayList_adapter.clear();
        this.arrayList_adapter.addAll(list);
    }
}
