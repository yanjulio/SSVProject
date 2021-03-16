package com.soft.ssvapp.Fragment_Menu.FillPayements;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.anychart.scales.Linear;
import com.soft.ssvapp.DataRetrofit.Operation.OperationRepository;
import com.soft.ssvapp.DataRetrofit.RapportParProjet.RapportEBParProjetValideEtDecaisse;
import com.soft.ssvapp.Fragment_Menu.All_specificProject.RapportPartProjetEtatBesoin.RapportDetailEB.RapportDetailEBParProjet;
import com.soft.ssvapp.Fragment_Menu.FillPayements.Details.PayementDetails;
import com.soft.ssvapp.R;
import com.wdullaer.materialdatetimepicker.date.TextViewWithCircularIndicator;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayementAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private String kind_payement;
    private int compte_projet;
    private String compte_designation_projet;
    private ArrayList<RapportEBParProjetValideEtDecaisse> arrayList;
    private String codeProjet;
//    PayementViewModel payementViewModel;
    OperationRepository operationRepository;
    public PayementAdapter(Context context, String kind_payement, int compte_projet,
                           String compte_designation_projet, String codeProjet)
    {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.kind_payement = kind_payement;
        this.compte_projet = compte_projet;
        this.codeProjet = codeProjet;
        this.compte_designation_projet = compte_designation_projet;
        this.arrayList = new ArrayList<RapportEBParProjetValideEtDecaisse>();
        operationRepository = OperationRepository.getInstance();
//        Payements payements = (Payements)context;
//        payementViewModel = ViewModelProviders.of(payements).get(PayementViewModel.class);
    }

    class HoldView
    {
        TextView textView_title;
        TextView textView_description;
        TextView textView_date_emission;
        TextView textView_reste_payement;
        TextView textView_total, textView_decaisser;
        TextView textView_voir_detail;
        LinearLayout linearLayout_linear_payement;
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
            convertView = inflater.inflate(R.layout.model_payement, null);

            holdView.textView_title = convertView.findViewById(R.id.payement_title);
            holdView.textView_description = convertView.findViewById(R.id.payement_descriptions);
            holdView.textView_reste_payement = convertView.findViewById(R.id.txt_reste_payement);
            holdView.textView_total = convertView.findViewById(R.id.txt_total_payement);
            holdView.textView_decaisser = convertView.findViewById(R.id.txt_decaisser_payement);
            holdView.textView_date_emission = convertView.findViewById(R.id.payement_date_emission);
            holdView.textView_voir_detail = convertView.findViewById(R.id.payement_voir_details);
            holdView.linearLayout_linear_payement = convertView.findViewById(R.id.linear_payement);

            convertView.setTag(holdView);
        }
        else
        {
            holdView = (HoldView)convertView.getTag();
        }

        holdView.textView_title.setText("" + arrayList.get(position).getCodeEtatdeBesoin());
        holdView.textView_description.setText("" + arrayList.get(position).getDesignationEtatDeBesion());
        holdView.textView_date_emission.setText("" + arrayList.get(position).getDateEmision());
        holdView.textView_reste_payement.setText("$" +
                (arrayList.get(position).getTotal()-arrayList.get(position).getSommeDecaisse()));
        holdView.textView_decaisser.setText("$" + arrayList.get(position).getSommeDecaisse());
        holdView.textView_total.setText("$" + arrayList.get(position).getTotal());

        holdView.linearLayout_linear_payement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operationRepository.operationConnexion().dernierOperation().enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if (response.isSuccessful())
                        {
                            double reste = arrayList.get(position).getTotal() - arrayList.get(position).getSommeDecaisse();
                            AppCompatActivity appCompatActivity = (AppCompatActivity)context;
                            appCompatActivity.startActivityForResult(new Intent(context, FormulairePayement_Ravitaement_Cpte.class)
                                    .putExtra(FormulairePayement_Ravitaement_Cpte.KIND_PAYEMENT, kind_payement)
                                    .putExtra(FormulairePayement_Ravitaement_Cpte.CODE_BESOIN,
                                            arrayList.get(position).getCodeEtatdeBesoin())
                                    .putExtra(FormulairePayement_Ravitaement_Cpte.DERNIER_OPERATION, response.body())
                                    .putExtra(FormulairePayement_Ravitaement_Cpte.CODE_PROJET,codeProjet)
                                    .putExtra(FormulairePayement_Ravitaement_Cpte.COMPTE_LIER_PROJET_CREDIT,
                                            compte_projet)
                                    .putExtra(FormulairePayement_Ravitaement_Cpte.MONTANT,
                                            ""+reste)
                                    .putExtra(FormulairePayement_Ravitaement_Cpte.COMPTE_LIER_DESIGNATION_PROJET,
                                            compte_designation_projet), 13);
                        }
                        else
                        {
                            switch (response.code())
                            {
                                case 404:
                                    Toast.makeText(context, "server not found.", Toast.LENGTH_LONG).show();
                                    break;
                                case 500:
                                    Toast.makeText(context, "server broken.", Toast.LENGTH_LONG).show();
                                    break;
                                default:
                                    Toast.makeText(context, "Unknown problem.", Toast.LENGTH_LONG).show();
                                    break;
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {

                    }
                });
            }
        });

        String reste = String.valueOf((arrayList.get(position).getTotal()-
                arrayList.get(position).getSommeDecaisse()));
        holdView.textView_voir_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity appCompatActivity = (AppCompatActivity)context;
                appCompatActivity.startActivityForResult(new Intent(context, PayementDetails.class)
                        .putExtra(PayementDetails.CODEBESOIN,
                                arrayList.get(position).getCodeEtatdeBesoin())
                        .putExtra(PayementDetails.DESINGATION_BESOIN,
                                arrayList.get(position).getDesignationEtatDeBesion()
                ).putExtra(RapportDetailEBParProjet.RESTE_ASERVIR, reste), 11);
            }
        });

        return convertView;
    }

    public void setBesoinPayement(List<RapportEBParProjetValideEtDecaisse> list)
    {
        arrayList.clear();
        arrayList.addAll(list);
    }
}
