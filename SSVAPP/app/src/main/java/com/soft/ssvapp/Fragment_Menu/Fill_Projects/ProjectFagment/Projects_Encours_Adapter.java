package com.soft.ssvapp.Fragment_Menu.Fill_Projects.ProjectFagment;

import android.content.Context;
import android.content.Intent;
import android.icu.util.IslamicCalendar;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.soft.ssvapp.Data.Entity_Project;
import com.soft.ssvapp.DataRetrofit.ProjetRetrofit.ProjetRetrofit;
import com.soft.ssvapp.DataRetrofit.ProjetRetrofit.ProjetRetrofitRespository;
import com.soft.ssvapp.Fragment_Menu.All_specificProject.Specific_Project;
import com.soft.ssvapp.Fragment_Menu.Navigation.Projects;
import com.soft.ssvapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Projects_Encours_Adapter extends BaseAdapter {

    ProjetRetrofitRespository projetRespository;
    private Context context;
    private LayoutInflater inflater;
    private String kind_list;
    private ArrayList<Entity_Project> arrayList_adapter = new ArrayList<Entity_Project>();
    ProgressBar progressBar;

    public Projects_Encours_Adapter(Context context, String kind_list, ProgressBar progressBar) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.arrayList_adapter.clear();
        this.kind_list = kind_list;
        projetRespository = ProjetRetrofitRespository.getInstance();
        this.progressBar = progressBar;

        if (arrayList_adapter != null)
        {
            Log.e("size from Adapter get", arrayList_adapter.size() +"");
        }
    }

    class Hold
    {
        TextView title_hold, desctiptions_hold, voir_hold;
        LinearLayout linearLayout_voir_hold, linearLayout_delete_project;
        View view_hold;
        ImageView imageView_hold;
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
            convertView = inflater.inflate(R.layout.model_projects_encours, null);

            hold.title_hold = convertView.findViewById(R.id.project_title_encours);
            hold.desctiptions_hold = convertView.findViewById(R.id.project_descriptions_encours);
            hold.voir_hold = convertView.findViewById(R.id.project_voir_details_encours);
            hold.linearLayout_voir_hold = convertView.findViewById(R.id.project_linear_voir_details);
            hold.linearLayout_delete_project =convertView.findViewById(R.id.linear_project_delete);
            hold.view_hold = convertView.findViewById(R.id.project_view_line);
            hold.imageView_hold = convertView.findViewById(R.id.imageView_envoyer_projet);
            convertView.setTag(hold);
        }
        else
        {
            hold = (Hold)convertView.getTag();
        }

        hold.title_hold.setText("" + arrayList_adapter.get(position).getCodeProject());
        hold.desctiptions_hold.setText("" + arrayList_adapter.get(position).getDesignationProject());

        if (arrayList_adapter.get(position).getEtat_envoyer() == 1)
        {
            hold.imageView_hold.setVisibility(View.GONE);
        }

        AppCompatActivity activity = (AppCompatActivity)context;
        Projects projects = (Projects)context;
        hold.voir_hold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activity.startActivityForResult(
                        new Intent(context, Details.class)
                                .putExtra(Details.PROJECT_KIND, kind_list)
                                .putExtra(Details.PROJECT_CODE, arrayList_adapter.get(position).getCodeProject())
                                .putExtra(Details.PROJECT_LIEU, arrayList_adapter.get(position).getLieu())
                                .putExtra(Details.PROJECT_CHEFPROJET, arrayList_adapter.get(position).getChefDuProjet())
                                .putExtra(Details.PROJECT_DATEDEBUT, arrayList_adapter.get(position).getDateDebut())
                                .putExtra(Details.PROJECT_DATEFIN, arrayList_adapter.get(position).getDateFin())
                                .putExtra(Details.PROJECT_DESCRIPTION, arrayList_adapter.get(position).getDesignationProject())
                                .putExtra(Details.COMPTE_CLIENT, arrayList_adapter.get(position).getCompteClient()), 1);
            }
        });

//        hold.linearLayout_delete_project.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                Entity_Project entity_project = new Entity_Project(arrayList_adapter.get(position).getCodeProject(),
//                        arrayList_adapter.get(position).getDesignationProject(), arrayList_adapter.get(position).getDateDebut(),
//                        arrayList_adapter.get(position).getDateFin(), arrayList_adapter.get(position).getLieu(),
//                        arrayList_adapter.get(position).getChefDuProjet(),0, arrayList_adapter.get(position).getCompte());
//                entity_project.setIdProject(arrayList_adapter.get(position).getIdProject());
//                Projects projects = (Projects)context;
//                projects.deleteProject(entity_project);
//                return false;
//            }
//        });

        hold.linearLayout_delete_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AppCompatActivity activity = (AppCompatActivity)context;
                activity.startActivity(new Intent(context, Specific_Project.class)
                        .putExtra(Specific_Project.DESIGNATION_PROJECT, arrayList_adapter.get(position).getDesignationProject())
                        .putExtra(Specific_Project.CODE_PROJECT, arrayList_adapter.get(position).getCodeProject())
                        .putExtra(Specific_Project.COMPTE_LIER_PROJET_CREDIT, arrayList_adapter.get(position).getCompte())
                        .putExtra(Specific_Project.COMPTE_LIER_DESIGNATION_PROJET, "Payements.")
                        .putExtra(Specific_Project.COMPTE_CLIENT, arrayList_adapter.get(position).getCompteClient()));
            }
        });

        hold.imageView_hold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                CodeProject, DesignationProject, Lieu, ChefDuProjet, Etat, DateDebut, DateFin
                ProjetRetrofit retrofitProjet = new ProjetRetrofit(arrayList_adapter.get(position).getCodeProject(),
                        arrayList_adapter.get(position).getDesignationProject(),
                        arrayList_adapter.get(position).getLieu(),
                        arrayList_adapter.get(position).getDateDebut(),
                        arrayList_adapter.get(position).getDateFin(),
                        arrayList_adapter.get(position).getChefDuProjet(),
                        arrayList_adapter.get(position).getEtat(),
                        arrayList_adapter.get(position).getCompte());
                Entity_Project entity_project = new Entity_Project(arrayList_adapter.get(position).getCodeProject(),
                        arrayList_adapter.get(position).getDesignationProject(), arrayList_adapter.get(position).getDateDebut(),
                        arrayList_adapter.get(position).getDateFin(), arrayList_adapter.get(position).getLieu(),
                        arrayList_adapter.get(position).getChefDuProjet(),0, arrayList_adapter.get(position).getCompte(),
                        arrayList_adapter.get(position).getCompteClient());
                entity_project.setIdProject(arrayList_adapter.get(position).getIdProject());
                entity_project.setEtat_envoyer(1);

                progressBar.setVisibility(View.VISIBLE);
                projetRespository.projetConnexion().createProjet(retrofitProjet).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful())
                        {
                            progressBar.setVisibility(View.GONE);
                            projects.createLigne();
                            projects.update_etat(entity_project);
                            Toast.makeText(context, "Bien enregistré.", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            switch (response.code())
                            {
                                case 404:
                                    Toast.makeText(context, "server not found", Toast.LENGTH_LONG).show();
                                    break;
                                case 500:
                                    Toast.makeText(context, "server broken", Toast.LENGTH_LONG).show();
                                    break;
                                default:
                                    Toast.makeText(context, "unknown problem.", Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(context, "Connexion problem.", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

        return convertView;
    }

    public void setProject(List<Entity_Project> entity_project)
    {
        this.arrayList_adapter.clear();
        this.arrayList_adapter.addAll(entity_project);
    }
}
