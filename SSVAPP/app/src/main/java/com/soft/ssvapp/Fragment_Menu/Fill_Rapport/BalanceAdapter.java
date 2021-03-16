package com.soft.ssvapp.Fragment_Menu.Fill_Rapport;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import com.soft.ssvapp.DataRetrofit.Compte.RapportCompteResponse;
import com.soft.ssvapp.Fragment_Menu.Fill_Rapport.DetailRapport.DetailBalance;
import com.soft.ssvapp.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class BalanceAdapter extends BaseAdapter implements PopupMenu.OnMenuItemClickListener{
    private LayoutInflater inflater;
    private Context context;
    private ArrayList<Balance_objet> arrayList_balalance_adapter;

    private String compte, groupeCompte, designation;
    private String type_balance;

    public BalanceAdapter(Context mcontext, String type_balance)
    {
        this.context = mcontext;
        this.inflater = LayoutInflater.from(mcontext);
        this.arrayList_balalance_adapter = new ArrayList<Balance_objet>();
        this.type_balance = type_balance;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        AppCompatActivity appCompatActivity = (AppCompatActivity)context;
        switch (item.getItemId())
        {
            case R.id.item_modifier_compte:
                Balance balance = (Balance)context;
                RapportCompteResponse compteResponse = new RapportCompteResponse(
                        Integer.valueOf(compte),
                        designation,
                        Integer.valueOf(groupeCompte),
                        0,
                        "0"
                );
                balance.ModifierCompte(compteResponse);
                break;
//            case R.id.item_voir_libelle:
//                appCompatActivity.startActivity(new Intent(context, ListeLibelle.class)
//                        .putExtra(ListeLibelle.COMPTE, compte).putExtra(ListeLibelle.DESIGNATION_COMPTE, designation));
//                break;
        }
        return false;
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
            holdView.textView_designation.setText("" + arrayList_balalance_adapter.get(position).getDesignation());
            holdView.textView_solde.setText("" +
                    new DecimalFormat("##.##")
                            .format(Double.valueOf(arrayList_balalance_adapter.get(position).getSolde())));
            holdView.linearLayout_balance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppCompatActivity appCompatActivity = (AppCompatActivity)context;
                    appCompatActivity.startActivityForResult(new Intent(context, DetailBalance.class)
                            .putExtra(DetailBalance.NUM_COMPTE, arrayList_balalance_adapter.get(position).getCompte())
                            .putExtra(DetailBalance.DESIGANTION, arrayList_balalance_adapter.get(position).getDesignation()+""),
                            6);
                }
            });
        }

        if (type_balance.equals("Clients") || type_balance.equals("Balance"))
        {
            holdView.linearLayout_balance.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    compte = String.valueOf(arrayList_balalance_adapter.get(position).getCompte());
                    groupeCompte = arrayList_balalance_adapter.get(position).getGroupeCompte();
                    designation = arrayList_balalance_adapter.get(position).getDesignation();
                    PopupMenu menu = new PopupMenu(context, v);
                    menu.setOnMenuItemClickListener(BalanceAdapter.this::onMenuItemClick);
                    menu.inflate(R.menu.menu_click_compte_balance);
                    menu.show();

                    return false;
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
