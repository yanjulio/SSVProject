package com.soft.ssvapp.Fragment_Menu.FillPayements.Details;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.soft.ssvapp.DataRetrofit.EtatDeBesoin.EtatDeBesoinRetrofitDetailEB;
import com.soft.ssvapp.R;

import java.util.ArrayList;
import java.util.List;

public class PayementDetailEBAdapter extends BaseAdapter {

    private ArrayList<EtatDeBesoinRetrofitDetailEB> arrayList;
    private Context context;
    private LayoutInflater layoutInflater;

    public PayementDetailEBAdapter(Context context)
    {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.arrayList = new ArrayList<>();
    }

    private class HoldView
    {
        TextView textView_date_operation, textView_libelle, textView_debit, textView_solde;
        LinearLayout linearLayout;
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

            convertView = layoutInflater.inflate(R.layout.model_detail_eb_mvt, null);

            holdView.textView_date_operation = convertView.findViewById(R.id.text_date_operation_mvt);
            holdView.textView_libelle = convertView.findViewById(R.id.text_libelle_mvt);
            holdView.textView_debit = convertView.findViewById(R.id.text_debit_mvt);
            holdView.textView_solde = convertView.findViewById(R.id.text_solde_mvt);
            holdView.linearLayout = convertView.findViewById(R.id.linear_model_mvt);

            convertView.setTag(holdView);
        }
        else
        {
            holdView = (HoldView)convertView.getTag();
        }

        holdView.textView_date_operation.setText("" + arrayList.get(position).getDateOperation());
        holdView.textView_libelle.setText("" + arrayList.get(position).getLibelle());
        holdView.textView_debit.setText("" + arrayList.get(position).getDebit());
        holdView.textView_solde.setText("" + arrayList.get(position).getCredit());

        holdView.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,
                        "selectionne : " + arrayList.get(position).getNumOperation(), Toast.LENGTH_LONG).show();
            }
        });

        return convertView;
    }

    public void setDetailEBMvt(List<EtatDeBesoinRetrofitDetailEB> list)
    {
        this.arrayList.clear();
        this.arrayList.addAll(list);
    }
}
