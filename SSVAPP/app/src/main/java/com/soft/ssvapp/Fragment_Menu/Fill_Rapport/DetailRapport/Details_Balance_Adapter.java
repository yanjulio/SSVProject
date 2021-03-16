package com.soft.ssvapp.Fragment_Menu.Fill_Rapport.DetailRapport;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.soft.ssvapp.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class Details_Balance_Adapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private Context context;
    private String date1, date2;
    private ArrayList<Details_Balance_objet> arrayList_adapter_balance;

    public Details_Balance_Adapter(Context mcontext, String date1, String date2)
    {
        this.context = mcontext;
        this.layoutInflater = LayoutInflater.from(mcontext);
        this.date1 = date1;
        this.date2 = date2;
        this.arrayList_adapter_balance = new ArrayList<Details_Balance_objet>();
    }

    private class HoldView
    {
        TextView textView_hold_date;
        TextView textView_hold_reference;
        TextView textView_hold_libelle;
        TextView textView_holde_debit;
        TextView textView_hold_credit;
        LinearLayout linearLayout_details_pv;
    }

    @Override
    public int getCount() {
        return arrayList_adapter_balance.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList_adapter_balance.get(position);
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

            convertView = layoutInflater.inflate(R.layout.model_details_balance, null);

            holdView.textView_hold_date = convertView.findViewById(R.id.text_date_balance_details);
            holdView.textView_hold_reference = convertView.findViewById(R.id.text_reference_balance_details);
            holdView.textView_hold_libelle = convertView.findViewById(R.id.text_libelle_balance_details);
            holdView.textView_holde_debit = convertView.findViewById(R.id.text_debit_balance_details);
            holdView.textView_hold_credit = convertView.findViewById(R.id.text_credit_balance_details);
            holdView.linearLayout_details_pv = convertView.findViewById(R.id.linear_details_balance_pv);
            convertView.setTag(holdView);
        }
        else
        {
            holdView = (HoldView)convertView.getTag();
        }

        holdView.textView_hold_date.setText(""+ arrayList_adapter_balance.get(position).getDate());
        holdView.textView_hold_reference.setText("" + arrayList_adapter_balance.get(position).getReference());
        holdView.textView_hold_libelle.setText("" +arrayList_adapter_balance.get(position).getLibelle());
        holdView.textView_holde_debit.setText("" +
                new DecimalFormat("##.##").format(Double.valueOf(arrayList_adapter_balance.get(position).getDebit())));
        holdView.textView_hold_credit.setText("" +
                new DecimalFormat("##.##").format(Double.valueOf(arrayList_adapter_balance.get(position).getCredit())));

        for (int a = 0; a < arrayList_adapter_balance.size(); a++)
        {
            Log.e(" ligne " + a, arrayList_adapter_balance.get(a).getDate());
        }

        holdView.linearLayout_details_pv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AppCompatActivity appCompatActivity = (AppCompatActivity)context;
                int num_compte = arrayList_adapter_balance.get(position).getNum_compte();
                Supprimer_details_pv supprimer_details_pv =
                        new Supprimer_details_pv(arrayList_adapter_balance.get(position).getNumOperation(),
                                num_compte, date1, date2);
                supprimer_details_pv.show(appCompatActivity.getSupportFragmentManager(), "11");
                return false;
            }
        });

        return convertView;
    }

    public void setDetailBalance(List<Details_Balance_objet> list)
    {
        this.arrayList_adapter_balance.clear();
        this.arrayList_adapter_balance.addAll(list);
    }
}
