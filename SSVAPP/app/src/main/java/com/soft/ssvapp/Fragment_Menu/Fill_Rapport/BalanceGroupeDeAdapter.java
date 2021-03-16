package com.soft.ssvapp.Fragment_Menu.Fill_Rapport;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.soft.ssvapp.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class BalanceGroupeDeAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    private ArrayList<Balance_objet> arrayList_balalance_adapter;

    public BalanceGroupeDeAdapter(Context mcontext)
    {
        this.context = mcontext;
        this.inflater = LayoutInflater.from(mcontext);
        this.arrayList_balalance_adapter = new ArrayList<Balance_objet>();
    }

    class HoldView
    {
        TextView textView_designation;
        TextView textView_solde;
        LinearLayout linearLayout_balance;
    }

    @Override
    public int getCount() {
        return arrayList_balalance_adapter.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList_balalance_adapter.get(position);
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

            convertView = inflater.inflate(R.layout.model_balance, null);

//            holdView.textView_compte = convertView.findViewById(R.id.txt_compte_balance);
            holdView.textView_designation = convertView.findViewById(R.id.txt_designation_balance);
            holdView.textView_solde = convertView.findViewById(R.id.txt_solde_balance);
            holdView.linearLayout_balance = convertView.findViewById(R.id.linear_balance);

            convertView.setTag(holdView);
        }
        else
        {
            holdView = (HoldView)convertView.getTag();
        }

        if (arrayList_balalance_adapter.size() != 0)
        {
            holdView.textView_designation.setText("" + arrayList_balalance_adapter.get(position).getGroupeDesignation());
            holdView.textView_solde.setText("" +
                    new DecimalFormat("##.##")
                            .format(Double.valueOf(arrayList_balalance_adapter.get(position).getSolde())));
            holdView.linearLayout_balance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppCompatActivity appCompatActivity = (AppCompatActivity)context;
                    appCompatActivity.startActivityForResult(new Intent(context, Balance.class)
                            .putExtra(Balance.GROUPE_COMPTE, arrayList_balalance_adapter.get(position).getGroupeCompte())
                            .putExtra(Balance.TYPE_RAPPORT, "Balance")
                            .putExtra(Balance.GROUPE_DESIGNATION, arrayList_balalance_adapter.get(position).getGroupeDesignation()), 7);
                }
            });
        }



        return convertView;
    }

    public void setBalance(List<Balance_objet> list)
    {
        arrayList_balalance_adapter.clear();
        arrayList_balalance_adapter.addAll(list);
    }
}
