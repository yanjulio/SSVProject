package com.soft.ssvapp.Fragment_Menu.Fill_Operation;

import android.content.Context;
import android.content.DialogInterface;
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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.soft.ssvapp.Data.Entity_Besoin;
import com.soft.ssvapp.Data.Entity_ProjectWithEntity_Besoin;
import com.soft.ssvapp.DataRetrofit.EtatDeBesoin.EtatDeBesoinRepositoryRetrofit;
import com.soft.ssvapp.DataRetrofit.EtatDeBesoin.EtatDeBesoinRetrofit;
import com.soft.ssvapp.DataRetrofit.Operation.OperationRepository;
import com.soft.ssvapp.Firebasefiles.EnvoiNotification;
import com.soft.ssvapp.Fragment_Menu.Fill_EtatBesoin.Besoin_View_model;
import com.soft.ssvapp.Fragment_Menu.Navigation.Operation_Cpte;
import com.soft.ssvapp.Login;
import com.soft.ssvapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OperationAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    private ArrayList<Entity_ProjectWithEntity_Besoin> arrayList_adapter;
    OperationRepository operationRepository;
    EtatDeBesoinRepositoryRetrofit etatDeBesoinRepository;
    SharedPreferences prefs;
    private int idUtilisateur;
    private String nomUtilisateur;
    private String date_Operation;
    private String kind_operation;
    private String codeProjet, designationProjet;
    Besoin_View_model view_model;
    private String niveauUtilisateur;

    public OperationAdapter(Context mcontext, String date_operation, String codeProjet,
                            String designationProjet, String kind_operation, String niveauUtilisateur)
    {
        this.context = mcontext;
        this.date_Operation = date_operation;
        this.kind_operation = kind_operation;
        this.codeProjet = codeProjet;
        this.designationProjet = designationProjet;
        this.inflater = LayoutInflater.from(context);
        this.niveauUtilisateur = niveauUtilisateur;
        this.arrayList_adapter = new ArrayList<Entity_ProjectWithEntity_Besoin>();
        operationRepository = OperationRepository.getInstance();
        etatDeBesoinRepository = EtatDeBesoinRepositoryRetrofit.getInstance();
        prefs = context.getSharedPreferences("lOGIN_USER", Context.MODE_PRIVATE);
        idUtilisateur = prefs.getInt(Login.IDUTILISATEUR, 0);
        nomUtilisateur = prefs.getString(Login.USER_NAME, "");
        Operation_Cpte operation_cpte = (Operation_Cpte)context;
        view_model = ViewModelProviders.of(operation_cpte).get(Besoin_View_model.class);
    }

    class Hold
    {
        TextView textView_titre, textView_description, textView_demandeur, textView_emission, textView_voir_detail;
        ImageView  imageView_valider;
        LinearLayout linearLayout_operation;
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
        final Hold hold;
        if (convertView == null)
        {
            hold = new Hold();
            convertView = inflater.inflate(R.layout.model_valider_besoin, null);

            hold.textView_titre = convertView.findViewById(R.id.besoin_title_validation);
            hold.textView_description = convertView.findViewById(R.id.besoin_descriptions_validation);
            hold.textView_demandeur = convertView.findViewById(R.id.besoin_demandeur_validation);
            hold.textView_emission = convertView.findViewById(R.id.besoin_date_emission_validation);
            hold.textView_voir_detail = convertView.findViewById(R.id.besoin_voir_details_validation);
            hold.imageView_valider = convertView.findViewById(R.id.imageView_valider_besoin_validation);
            hold.linearLayout_operation = convertView.findViewById(R.id.linear_besoin_valider_operation);

            convertView.setTag(hold);
        }
        else
        {
            hold = (Hold)convertView.getTag();
        }

        hold.textView_titre.setText("" + arrayList_adapter.get(position).getEntity_besoin().getCodeEtatDeBesoinOnline());
        hold.textView_description.setText("" + arrayList_adapter.get(position).getEntity_besoin().getDesignationEtatDeBesion());
        hold.textView_emission.setText("" + arrayList_adapter.get(position).getEntity_besoin().getDateEmision());
        hold.textView_demandeur.setText("" + arrayList_adapter.get(position).getEntity_besoin().getDemandeur());

        String niveauUtilisateur = prefs.getString(Login.NIVEAUUTILISATEUR, "");
//        Toast.makeText(context, "niveau Utilisateur : " + niveauUtilisateur, Toast.LENGTH_LONG).show();
        if (arrayList_adapter.get(position).getEntity_besoin().getEtat()==1 || !niveauUtilisateur.equals("ADMIN"))
        {
            hold.imageView_valider.setVisibility(View.GONE);
        }

        hold.textView_voir_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity details = (AppCompatActivity) context;
                details.startActivity(new Intent(context, Details_EtatBesoinValider.class)
                        .putExtra(
                                Details_EtatBesoinValider.CODEBESOIN,
                                arrayList_adapter.get(position).getEntity_besoin().getCodeEtatdeBesoin())
                        .putExtra(Details_EtatBesoinValider.DESINGATION_BESOIN,
                                arrayList_adapter.get(position).getEntity_besoin().getDesignationEtatDeBesion()));
            }
        });

        hold.imageView_valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hold.imageView_valider.setVisibility(View.GONE);
                Entity_Besoin entity_besoin =
                        new Entity_Besoin(arrayList_adapter.get(position).getEntity_besoin().getCodeEtatdeBesoin(),
                                arrayList_adapter.get(position).getEntity_besoin().getDesignationEtatDeBesion(),
                                arrayList_adapter.get(position).getEntity_besoin().getCodeProject(),
                                arrayList_adapter.get(position).getEntity_besoin().getDemandeur(),
                                arrayList_adapter.get(position).getEntity_besoin().getDateEmision(),
                                date_Operation,
                                arrayList_adapter.get(position).getEntity_besoin().getDateRequise(),
                                nomUtilisateur,
                                1,
                                arrayList_adapter.get(position).getEntity_besoin().getCodeEtatDeBesoinOnline());
                entity_besoin.setIdEtatDuBesoin(
                        arrayList_adapter.get(position).getEntity_besoin().getIdEtatDuBesoin());
                entity_besoin.setEtat_besoin_envoyer(1);

                EtatDeBesoinRetrofit etatDeBesoinRetrofit =
                        new EtatDeBesoinRetrofit(
                                arrayList_adapter.get(position).getEntity_besoin().getCodeEtatdeBesoin(),
                                arrayList_adapter.get(position).getEntity_besoin().getDesignationEtatDeBesion(),
                                arrayList_adapter.get(position).getEntity_besoin().getCodeProject(),
                                1,
                                arrayList_adapter.get(position).getEntity_besoin().getDemandeur(),
                                nomUtilisateur,
                                arrayList_adapter.get(position).getEntity_besoin().getDateEmision(),
                                arrayList_adapter.get(position).getEntity_besoin().getDateRequise(),
                                date_Operation
                        );

                etatDeBesoinRepository.etatDeBesoinConnexion().modifierEtatDeBesoin(etatDeBesoinRetrofit,
                        arrayList_adapter.get(position).getEntity_besoin().getCodeEtatDeBesoinOnline()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful())
                        {
                            int value = view_model.update(entity_besoin);
                            String nomtUtilisateur_receiver = prefs.getString("USERNAME_RECEIVER", "");
                            new EnvoiNotification("SSV" ,
                                    "L'état de besoin " +entity_besoin.getDesignationEtatDeBesion()+
                                            " du projet " + designationProjet +" a été validé.",
                                    arrayList_adapter.get(position).getEntity_besoin().getCodeProject(),
                                    designationProjet,"Valides.", "",
                                    "ADMIN", nomtUtilisateur_receiver).execute();
                            Toast.makeText(context, "Validation bien faite.", Toast.LENGTH_LONG).show();
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
                                    Toast.makeText(context, "unknown probleme.", Toast.LENGTH_LONG).show();
                                    break;
                            }
                            Toast.makeText(context, "an error has occured.", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(context,
                                "Connexion Problem, verfier votre connexion puis reessayer",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        hold.linearLayout_operation.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Voulez-vous supprimer le Besoin '" +
                        arrayList_adapter.get(position).getEntity_besoin().getDesignationEtatDeBesion() +"'");
                builder.setCancelable(true);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Call<Boolean> call_supprimer_etatBesoin =
                                etatDeBesoinRepository.etatDeBesoinConnexion()
                                        .supprimerEtatBesoin(arrayList_adapter.get(position).getEntity_besoin().getCodeEtatDeBesoinOnline());
                        call_supprimer_etatBesoin.enqueue(new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                if (response.isSuccessful())
                                {
                                    if (response.body() == true)
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
                                                arrayList_adapter.get(position).getEntity_besoin().getCodeEtatDeBesoinOnline());
                                        entity_besoin.setIdEtatDuBesoin(arrayList_adapter.get(position).getEntity_besoin().getIdEtatDuBesoin());
                                        Operation_Cpte operation_cpte = (Operation_Cpte) context;
                                        operation_cpte.deleteEtatBesoin(entity_besoin);
                                        Toast.makeText(context, "Suppression réussi", Toast.LENGTH_LONG).show();
                                    }
                                }
                                else
                                {
                                    switch (response.code())
                                    {
                                        case 404:
                                            Toast.makeText(context, "Serveur introuvable", Toast.LENGTH_LONG).show();
                                            break;
                                        case 500:
                                            Toast.makeText(context, "Erreur Serveur", Toast.LENGTH_LONG).show();
                                            break;
                                        default:
                                            Toast.makeText(context, "Problème inconnue", Toast.LENGTH_LONG).show();
                                            break;
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Boolean> call, Throwable t) {
                                Toast.makeText(context,
                                        "Problème de connexion, verfier votre connexion puis reessayer",
                                        Toast.LENGTH_LONG).show();
                            }
                        });
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

        return convertView;
    }

    public void setBesoinAvalider(List<Entity_ProjectWithEntity_Besoin> list) {
        arrayList_adapter.clear();
        arrayList_adapter.addAll(list);
    }

}
