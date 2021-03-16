package com.soft.ssvapp.Fragment_Menu.Fill_Rapport.RapportSuiviProjet;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.soft.ssvapp.DataRetrofit.Rapport.RapportProjetResponse;
import com.soft.ssvapp.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SuiviProjetAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private ArrayList<RapportProjetResponse> arrayList_balalance_adapter;

    public SuiviProjetAdapter(Context mcontext)
    {
        this.context = mcontext;
        this.inflater = LayoutInflater.from(mcontext);
        this.arrayList_balalance_adapter = new ArrayList<RapportProjetResponse>();
    }

    class HoldView
    {
        TextView textView_projet;
        TextView textView_prevision;
        TextView textView_consomation;
        TextView textView_taux;
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

            convertView = inflater.inflate(R.layout.model_suivi_projet, null);

            holdView.textView_projet = convertView.findViewById(R.id.txt_projet_plan);
            holdView.textView_prevision = convertView.findViewById(R.id.txt_prevision_plan);
            holdView.textView_consomation = convertView.findViewById(R.id.txt_consomation_plan);
            holdView.textView_taux = convertView.findViewById(R.id.txt_taux_plan);
            holdView.linearLayout_balance = convertView.findViewById(R.id.linear_plan);

            convertView.setTag(holdView);
        }
        else
        {
            holdView = (HoldView)convertView.getTag();
        }

//        holdView.textView_compte.setText("" + arrayList_balalance_adapter.get(position).getCompte());
        if (arrayList_balalance_adapter.size() != 0)
        {
//            + arrayList_balalance_adapter.get(position).getCodeProject() + "/"
            holdView.textView_projet.setText(""
                    +arrayList_balalance_adapter.get(position).getDesignationProject());
            holdView.textView_prevision.setText("" +
                    new DecimalFormat("##.##").format(arrayList_balalance_adapter.get(position).gettOtalPrevision()));
            holdView.textView_consomation.setText("" +
                    new DecimalFormat("##.##").format(arrayList_balalance_adapter.get(position).getTotalConsommation()));
            holdView.textView_taux.setText("" +
                    new DecimalFormat("##.##").format(arrayList_balalance_adapter.get(position).getTauxCons()));
            holdView.linearLayout_balance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(context, "numCompte est : "
//                            + arrayList_balalance_adapter.get(position).getCompte(), Toast.LENGTH_LONG).show();
                    AppCompatActivity appCompatActivity = (AppCompatActivity)context;
                    appCompatActivity.startActivity(new Intent(context, DetailSuiviProjet.class)
                            .putExtra(DetailSuiviProjet.CODE_PROJET, arrayList_balalance_adapter.get(position).getCodeProject())
                            .putExtra(DetailSuiviProjet.DESIGNATION_PROJET,arrayList_balalance_adapter.get(position).getDesignationProject()));
                }
            });
        }



        return convertView;
    }

    public void setBalance(List<RapportProjetResponse> list)
    {
        arrayList_balalance_adapter.clear();
        arrayList_balalance_adapter.addAll(list);
    }

}
