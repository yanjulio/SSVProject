package com.soft.ssvapp.Fragment_Menu.Fill_EtatBesoin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.soft.ssvapp.Data.Entity_Besoin;
import com.soft.ssvapp.Data.Entity_ProjectWithEntity_Besoin;
import com.soft.ssvapp.DataRetrofit.DetailsEtatDeBesoin.DetailsEtatDeBesoinRetrofitViewModel;
import com.soft.ssvapp.DataRetrofit.EtatDeBesoin.EtatDeBesoinRepositoryRetrofit;
import com.soft.ssvapp.DataRetrofit.EtatDeBesoin.EtatDeBesoinRetrofit;
import com.soft.ssvapp.DataRetrofit.EtatDeBesoin.EtatDeBesoinRetrofitViewModel;
import com.soft.ssvapp.Firebasefiles.EnvoiNotification;
import com.soft.ssvapp.Fragment_Menu.Navigation.Etat_Besoin;
import com.soft.ssvapp.Login;
import com.soft.ssvapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Besoin_Adapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private String kind_list;
    private String codeProjet;
    private String designationProjet;
    private String initialUtilisateur;
    private int Etat = 0;
    private ArrayList<Entity_ProjectWithEntity_Besoin> arrayList_adapter ;
    EtatDeBesoinRetrofitViewModel viewModel_etatBesoin;
    DetailsEtatDeBesoinRetrofitViewModel viewModel_detailsEtatBesoin;
    EtatDeBesoinRepositoryRetrofit etatDeBesoinRepository;
    SharedPreferences prefs;

    public Besoin_Adapter(Context context, String kind_list, String codeProjet, String designationProjet, String initialUtilisateur) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.kind_list = kind_list;
        this.codeProjet = codeProjet;
        this.designationProjet = designationProjet;
        this.initialUtilisateur = initialUtilisateur;
        this.arrayList_adapter = new ArrayList<Entity_ProjectWithEntity_Besoin>();
        viewModel_etatBesoin = ViewModelProviders.of((Etat_Besoin)context).get(EtatDeBesoinRetrofitViewModel.class);
        viewModel_etatBesoin.init();
        viewModel_detailsEtatBesoin = ViewModelProviders.of((Etat_Besoin)context).get(DetailsEtatDeBesoinRetrofitViewModel.class);
        viewModel_detailsEtatBesoin.init();
        prefs = context.getSharedPreferences("lOGIN_USER", Context.MODE_PRIVATE);

        etatDeBesoinRepository = EtatDeBesoinRepositoryRetrofit.getInstance();
    }

    class Hold
    {
        TextView code_hold, description_hold, demandeur_hold, date_emission_hold;
        TextView voir_hold;
        LinearLayout linearLayout_delete;
        ImageView imageView_holde;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Hold hold;

        if (convertView == null)
        {
            hold = new Hold();
            convertView = inflater.inflate(R.layout.model_etat_besoin, null);

            hold.code_hold = convertView.findViewById(R.id.besoin_title);
            hold.description_hold = convertView.findViewById(R.id.besoin_descriptions);
            hold.voir_hold = convertView.findViewById(R.id.besoin_voir_details);
            hold.demandeur_hold = convertView.findViewById(R.id.besoin_demandeur);
            hold.date_emission_hold = convertView.findViewById(R.id.besoin_date_emission);
            hold.linearLayout_delete = convertView.findViewById(R.id.linear_delete_etat_besoin);
            hold.imageView_holde = convertView.findViewById(R.id.imageView_envoyer_etat_besoin);
            convertView.setTag(hold);
        }
        else
        {
            hold = (Hold)convertView.getTag();
        }

        if (arrayList_adapter.get(position).getEntity_besoin().getCodeEtatDeBesoinOnline().equals(""))
        {
            hold.code_hold.setText("" + arrayList_adapter.get(position).getEntity_besoin().getCodeEtatdeBesoin());
        }
        else
        {
            hold.code_hold.setText("" + arrayList_adapter.get(position).getEntity_besoin().getCodeEtatDeBesoinOnline());
        }
        hold.description_hold.setText("" + arrayList_adapter.get(position).getEntity_besoin().getDesignationEtatDeBesion());
        hold.demandeur_hold.setText("" + arrayList_adapter.get(position).getEntity_besoin().getDemandeur());
        hold.date_emission_hold.setText("" + arrayList_adapter.get(position).getEntity_besoin().getDateEmision());

        if (arrayList_adapter.get(position).getEntity_besoin().getEtat_besoin_envoyer()==1)
        {
            hold.imageView_holde.setVisibility(View.GONE);
        }

        hold.voir_hold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity)context;
                activity.startActivityForResult(
                        new Intent(context, Details_Besoin.class)
                                .putExtra(Details_Besoin.BESOIN_KIND, kind_list)
                                .putExtra(Details_Besoin.ETAT_BESOIN,
                                        arrayList_adapter.get(position).getEntity_besoin().getEtat_besoin_envoyer())
                                .putExtra(Details_Besoin.BESOIN_CODE,
                                        arrayList_adapter.get(position).getEntity_besoin().getCodeEtatdeBesoin())
                                .putExtra(Details_Besoin.DESIGNATION_BESOIN,
                                        arrayList_adapter.get(position).getEntity_besoin().getDesignationEtatDeBesion())
                                .putExtra(Details_Besoin.BESOIN_CODE_ONLINE,
                                        arrayList_adapter.get(position).getEntity_besoin().getCodeEtatDeBesoinOnline())
                                .putExtra(Details_Besoin.INITIAL_UTILISATEUR, initialUtilisateur)
                                .putExtra(Details_Besoin.CODE_PROJET, arrayList_adapter.get(position).getEntity_besoin().getCodeProject())
                                .putExtra(Details_Besoin.CODE_LIGNE, arrayList_adapter.get(position).getEntity_besoin().getCodeLigne()),
                        6
                        );
            }
        });

        hold.linearLayout_delete.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Entity_Besoin entity_besoin = new Entity_Besoin(
                        arrayList_adapter.get(position).getEntity_besoin().getCodeEtatdeBesoin(),
                        arrayList_adapter.get(position).getEntity_besoin().getDesignationEtatDeBesion(),
                        arrayList_adapter.get(position).getEntity_besoin().getCodeProject(),
                        arrayList_adapter.get(position).getEntity_besoin().getDemandeur(),
                        arrayList_adapter.get(position).getEntity_besoin().getDateEmision(),
                        arrayList_adapter.get(position).getEntity_besoin().getDateValidation(),
                        arrayList_adapter.get(position).getEntity_besoin().getDateRequise(),
                        arrayList_adapter.get(position).getEntity_besoin().getValiderPar(),0,
                        arrayList_adapter.get(position).getEntity_besoin().getCodeEtatDeBesoinOnline());
                entity_besoin.setIdEtatDuBesoin(arrayList_adapter.get(position).getEntity_besoin().getIdEtatDuBesoin());
                Etat_Besoin etat_besoin = (Etat_Besoin)context;
                etat_besoin.deleteEtatBesoin(entity_besoin);
                return false;
            }
        });

        hold.imageView_holde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EtatDeBesoinRetrofit retrofitEtatBesoin = new EtatDeBesoinRetrofit(
                        arrayList_adapter.get(position).getEntity_besoin().getCodeEtatdeBesoin(),
                        arrayList_adapter.get(position).getEntity_besoin().getDesignationEtatDeBesion(),
                        arrayList_adapter.get(position).getEntity_besoin().getCodeProject(),
                        arrayList_adapter.get(position).getEntity_besoin().getEtat(),
                        arrayList_adapter.get(position).getEntity_besoin().getDemandeur(),
                        arrayList_adapter.get(position).getEntity_besoin().getValiderPar(),
                        arrayList_adapter.get(position).getEntity_besoin().getDateEmision(),
                        arrayList_adapter.get(position).getEntity_besoin().getDateRequise(),
                        arrayList_adapter.get(position).getEntity_besoin().getDateValidation());
                retrofitEtatBesoin.setCodeLigne(arrayList_adapter.get(position).getEntity_besoin().getCodeLigne());

                Etat_Besoin etat_besoin = (Etat_Besoin)context;

                etatDeBesoinRepository.etatDeBesoinConnexion()
                        .createEtatDeBesoin(retrofitEtatBesoin, initialUtilisateur).enqueue(new Callback<EtatDeBesoinRetrofit>() {
                    @Override
                    public void onResponse(Call<EtatDeBesoinRetrofit> call, Response<EtatDeBesoinRetrofit> response) {
                        if (response.isSuccessful())
                        {
                            Entity_Besoin entity_besoin = new Entity_Besoin(
                                    arrayList_adapter.get(position).getEntity_besoin().getCodeEtatdeBesoin(),
                                    arrayList_adapter.get(position).getEntity_besoin().getDesignationEtatDeBesion(),
                                    arrayList_adapter.get(position).getEntity_besoin().getCodeProject(),
                                    arrayList_adapter.get(position).getEntity_besoin().getDemandeur(),
                                    arrayList_adapter.get(position).getEntity_besoin().getDateEmision(),
                                    arrayList_adapter.get(position).getEntity_besoin().getDateValidation(),
                                    arrayList_adapter.get(position).getEntity_besoin().getDateRequise(),
                                    arrayList_adapter.get(position).getEntity_besoin().getValiderPar(),0,
                                    response.body().getCodeEtatdeBesoin());
                            entity_besoin.setEtat_besoin_envoyer(1);
                            entity_besoin.setIdEtatDuBesoin(arrayList_adapter.get(position).getEntity_besoin().getIdEtatDuBesoin());
                            etat_besoin.update_etat(entity_besoin);
                            etat_besoin.createDetailsBesoinOnline(
                                    arrayList_adapter.get(position).getEntity_besoin().getCodeEtatdeBesoin(),
                                    response.body().getCodeEtatdeBesoin()
                            );
                            String nomUtilisateur = prefs.getString(Login.PASS_WORD, ""); // je prend le password comme le username
//                            Toast.makeText(context, "nomutilisateur : " + nomUtilisateur, Toast.LENGTH_LONG).show();
                            new EnvoiNotification("SSV",
                                    "Nouveau état de besoin "
                                             +arrayList_adapter.get(position).getEntity_besoin().getDesignationEtatDeBesion()
                                            + " du Projet " +
                                            designationProjet,
                                    codeProjet, designationProjet, "Envoyés.", nomUtilisateur,
                                    nomUtilisateur, "ADMIN").execute();
                        }
                        else
                        {
                            switch (response.code())
                            {
                                case 404:
                                    Toast.makeText(context, "the server not found", Toast.LENGTH_LONG).show();
                                    break;
                                case 500:
                                    Toast.makeText(context, "the server has broken", Toast.LENGTH_LONG).show();
                                    break;
                                default:
                                    Toast.makeText(context, "Connexion error " + "code Erreur = " +response.code(),
                                            Toast.LENGTH_LONG).show();
                                    break;
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<EtatDeBesoinRetrofit> call, Throwable t) {
                        Toast.makeText(context, "Probleme de connexion EB", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

        return convertView;
    }

    public void setBesoin(List<Entity_ProjectWithEntity_Besoin> entity_besoins)
    {
        this.arrayList_adapter.clear();
        this.arrayList_adapter.addAll(entity_besoins);
    }
}
