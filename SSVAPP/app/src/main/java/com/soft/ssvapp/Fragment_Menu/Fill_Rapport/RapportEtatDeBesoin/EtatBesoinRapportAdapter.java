package com.soft.ssvapp.Fragment_Menu.Fill_Rapport.RapportEtatDeBesoin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.soft.ssvapp.DataRetrofit.Rapport.RapportEtatBesoinResponse;
import com.soft.ssvapp.Fragment_Menu.Fill_Rapport.RapportEtatDeBesoin.RapportDetailEtatBesoin.DetailBesoinRapport;
import com.soft.ssvapp.Fragment_Menu.Fill_Rapport.RapportEtatDeBesoin.RapportEtatBesoinDataRemote.RapportEtatBesoinViewModel;
import com.soft.ssvapp.Login;
import com.soft.ssvapp.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EtatBesoinRapportAdapter extends BaseAdapter /*implements PopupMenu.OnMenuItemClickListener*/{

//    EtatDeBesoinRepositoryRetrofit etatDeBesoinRepositoryRetrofit;
    RapportEtatBesoinViewModel rapportEtatBesoinViewModel;
    private LayoutInflater inflater;
    private Context context;
    private ArrayList<RapportEtatBesoinResponse> arrayList_etatBesoin_adapter;
    private String codeEtatBesoin ="", designationEtatBesoin ="";
    private String niveauUtlisateur;
    private String nomUtilisateur;
    private int idUtilisateur;
    private String codeProjet;
    private String date1, date2;
    SharedPreferences prefs;

    public EtatBesoinRapportAdapter(Context mcontext, String date1, String date2)
    {
        this.context = mcontext;
        this.date1 = date1;
        this.date2 = date2;
        this.inflater = LayoutInflater.from(mcontext);
        this.arrayList_etatBesoin_adapter = new ArrayList<RapportEtatBesoinResponse>();
//        etatDeBesoinRepositoryRetrofit = EtatDeBesoinRepositoryRetrofit.getInstance();

        prefs = context.getSharedPreferences("lOGIN_USER", Context.MODE_PRIVATE);
        idUtilisateur = prefs.getInt(Login.IDUTILISATEUR, 0);
        nomUtilisateur = prefs.getString(Login.USER_NAME, "");
        niveauUtlisateur = prefs.getString(Login.NIVEAUUTILISATEUR, "");
        EtatBesoinRapport rapport = (EtatBesoinRapport)context;
        rapportEtatBesoinViewModel = ViewModelProviders.of(rapport).get(RapportEtatBesoinViewModel.class);
    }

//    @Override
//    public boolean onMenuItemClick(MenuItem item) {
//        AppCompatActivity appCompatActivity = (AppCompatActivity)context;
//        Calendar c = Calendar.getInstance();
//
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
//        String date_operation = df.format(c.getTime());
//        switch (item.getItemId())
//        {
//            case R.id.item_valider_rapport_etat_besoin:
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setMessage("Voulez-vous vraiment valider " + designationEtatBesoin);
//                builder.setCancelable(true);
//                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        EtatDeBesoinRetrofit etatDeBesoinRetrofit =
//                                new EtatDeBesoinRetrofit(
//                                        codeEtatBesoin,
//                                        designationEtatBesoin,
//                                        codeProjet,
//                                        1,
//                                        "",
//                                        nomUtilisateur,
//                                        "2020-09-23T01:58:31.680Z",
//                                        "2020-09-23T01:58:31.680Z",
//                                        date_operation
//                                );
//                        rapportEtatBesoinViewModel.modifierEtatBesoin(etatDeBesoinRetrofit, codeEtatBesoin, date1, date2);
//                        Toast.makeText(context, "NomUtilisateur : " + nomUtilisateur, Toast.LENGTH_LONG).show();
//                    }
//                }).setNegativeButton("Non", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // nothing till now
//                    }
//                });
//                builder.show();
//                break;
//            case R.id.item_voir_details_rapport_etat_besoin:
//                appCompatActivity.startActivity(new Intent(context, DetailBesoinRapport.class)
//                        .putExtra(DetailBesoinRapport.CODE_ETAT_BESOIN, codeEtatBesoin)
//                        .putExtra(DetailBesoinRapport.DESINGTION_ETAT_BESOIN, designationEtatBesoin));
//                break;
//        }
//        return false;
//    }

    class HoldView
    {
        TextView textView_etat_besoin;
        TextView textView_projet;
        TextView textView_etat;
        LinearLayout linearLayout_etat_besoin;
    }

    @Override
    public int getCount() {
        return arrayList_etatBesoin_adapter.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList_etatBesoin_adapter.get(position);
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

            convertView = inflater.inflate(R.layout.model_etat_besoin_rapport, null);

            holdView.textView_etat_besoin = convertView.findViewById(R.id.txt_etat_besoin_rapport_model);
            holdView.textView_projet = convertView.findViewById(R.id.txt_projet_rapport_model);
            holdView.textView_etat = convertView.findViewById(R.id.txt_etat_etat_besoin_rapport_model);
            holdView.linearLayout_etat_besoin = convertView.findViewById(R.id.linear_etat_besoin_rapport_model);

            convertView.setTag(holdView);
        }
        else
        {
            holdView = (HoldView)convertView.getTag();
        }

        if (arrayList_etatBesoin_adapter.size() != 0)
        {
            holdView.textView_etat_besoin.setText("" + arrayList_etatBesoin_adapter.get(position).getCodeEtatdeBesoin()+ "/" +
                    arrayList_etatBesoin_adapter.get(position).getDesignationEtatDeBesion());
            holdView.textView_projet.setText(""+ arrayList_etatBesoin_adapter.get(position).getDesignationProject());
            holdView.textView_etat.setText("" + arrayList_etatBesoin_adapter.get(position).getDesignationEtat());
            holdView.linearLayout_etat_besoin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppCompatActivity appCompatActivity = (AppCompatActivity)context;
                    if (niveauUtlisateur.equals("ADMIN"))
                    {
                        appCompatActivity.startActivityForResult(new Intent(context, DetailBesoinRapport.class)
                                        .putExtra(DetailBesoinRapport.CODE_ETAT_BESOIN,
                                                arrayList_etatBesoin_adapter.get(position).getCodeEtatdeBesoin())
                                        .putExtra(DetailBesoinRapport.DESINGTION_ETAT_BESOIN,
                                                arrayList_etatBesoin_adapter.get(position).getDesignationEtatDeBesion())
                                        .putExtra(DetailBesoinRapport.CODE_PROJET,
                                                arrayList_etatBesoin_adapter.get(position).getCodeProject())
                                        .putExtra(DetailBesoinRapport.NOM_UTILISATEUR, nomUtilisateur)
                                        .putExtra(DetailBesoinRapport.DATE_1, date1)
                                        .putExtra(DetailBesoinRapport.DATE_2, date2)
                                        .putExtra(DetailBesoinRapport.ETAT,
                                                arrayList_etatBesoin_adapter.get(position).getDesignationEtat()),
                                5);
                    }
                    else
                    {
                        appCompatActivity.startActivityForResult(new Intent(context, DetailBesoinRapport.class)
                                        .putExtra(DetailBesoinRapport.CODE_ETAT_BESOIN,
                                                arrayList_etatBesoin_adapter.get(position).getCodeEtatdeBesoin())
                                        .putExtra(DetailBesoinRapport.DESINGTION_ETAT_BESOIN,
                                                arrayList_etatBesoin_adapter.get(position).getDesignationEtatDeBesion())
                                        .putExtra(DetailBesoinRapport.CODE_PROJET,
                                                arrayList_etatBesoin_adapter.get(position).getCodeProject())
                                        .putExtra(DetailBesoinRapport.NOM_UTILISATEUR, nomUtilisateur)
                                        .putExtra(DetailBesoinRapport.DATE_1, date1)
                                        .putExtra(DetailBesoinRapport.DATE_2, date2)
                                        .putExtra(DetailBesoinRapport.ETAT,
                                                "VALIDE"),
                                5);
                    }
//                    PopupMenu rapportEtatBeoin_menu = new PopupMenu(context, v);
//                    rapportEtatBeoin_menu.setOnMenuItemClickListener(EtatBesoinRapportAdapter.this::onMenuItemClick);
//                    rapportEtatBeoin_menu.inflate(R.menu.menu_rapport_etat_besoin);
//                    rapportEtatBeoin_menu.show();
//                    designationEtatBesoin = arrayList_etatBesoin_adapter.get(position).getDesignationEtatDeBesion();
//                    codeEtatBesoin = arrayList_etatBesoin_adapter.get(position).getCodeEtatdeBesoin();
//                    codeProjet = arrayList_etatBesoin_adapter.get(position).getCodeProject();
                }
            });

            holdView.linearLayout_etat_besoin.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Voulez-vous supprimer le Besoin '"
                            + arrayList_etatBesoin_adapter.get(position).getDesignationEtatDeBesion() + "'");
                    builder.setCancelable(true);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            rapportEtatBesoinViewModel.supprimerEtatBesoin(
                                    arrayList_etatBesoin_adapter.get(position).getCodeEtatdeBesoin(), date1, date2);
                        }
                    });
                    builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                    return false;
                }
            });
        }



        return convertView;
    }

    public void setEtatBesoin(List<RapportEtatBesoinResponse> list)
    {
        EtatBesoinRapport.list_load_size.clear();
        EtatBesoinRapport.list_load_size.addAll(list);
        arrayList_etatBesoin_adapter.clear();
        arrayList_etatBesoin_adapter.addAll(list);
    }
}
