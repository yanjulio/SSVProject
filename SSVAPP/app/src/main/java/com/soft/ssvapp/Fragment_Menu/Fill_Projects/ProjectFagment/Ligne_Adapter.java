package com.soft.ssvapp.Fragment_Menu.Fill_Projects.ProjectFagment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.soft.ssvapp.Data.Entity_Ligne;
import com.soft.ssvapp.R;

import java.util.ArrayList;
import java.util.List;

public class Ligne_Adapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Entity_Ligne> arrayList_adpter;
    private String from_activity;

    public Ligne_Adapter(Context mcontext, String from_activity)
    {
        this.context = mcontext;
        this.inflater = LayoutInflater.from(context);
        this.arrayList_adpter = new ArrayList<Entity_Ligne>();
        this.from_activity = from_activity;
    }

    class Holdview
    {
        LinearLayout linearLayout;
        TextView textView_designation;
        TextView textView_prevision;
    }

    @Override
    public int getCount() {
        return arrayList_adpter.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList_adpter.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Holdview holdview;

        if (convertView == null)
        {
            holdview = new Holdview();
            convertView = inflater.inflate(R.layout.model_ligne, null);

            holdview.textView_designation = convertView.findViewById(R.id.textView_ligne_model_deisignation);
            holdview.textView_prevision = convertView.findViewById(R.id.textView_ligne_model_prevision);
            holdview.linearLayout = convertView.findViewById(R.id.linear_ligne_model);

            convertView.setTag(holdview);
        }
        else
        {
            holdview = (Holdview)convertView.getTag();
        }

        holdview.textView_designation.setText("" + arrayList_adpter.get(position).getDesignationLigne());
        holdview.textView_prevision.setText("" + arrayList_adpter.get(position).getPrevision());

        holdview.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (from_activity.equals("Nouveau"))
                {
                    Nouveau_Projet nouveau_projet = (Nouveau_Projet)context;
                    nouveau_projet.openLigneDialog(position , arrayList_adpter.get(position).getCodeProject(),
                            arrayList_adpter.get(position).getCodeLigne(),
                            arrayList_adpter.get(position).getDesignationLigne(),
                            arrayList_adpter.get(position).getIdLigne(),
                            arrayList_adpter.get(position).getPrevision(), from_activity);
                }
                else if(from_activity.equals("Modifier"))
                {
                    LigneActivity ligneActivity = (LigneActivity)context;
                    ligneActivity.openLigneDialogRoom(position , arrayList_adpter.get(position).getCodeProject(),
                            arrayList_adpter.get(position).getCodeLigne(),
                            arrayList_adpter.get(position).getDesignationLigne(),
                            arrayList_adpter.get(position).getIdLigne(),
                            arrayList_adpter.get(position).getPrevision(), from_activity);
                }
            }
        });

        holdview.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true);
                builder.setMessage("Voulez-vous supprimer " + arrayList_adpter.get(position).getDesignationLigne());
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Entity_Ligne entity_ligne = new Entity_Ligne(arrayList_adpter.get(position).getCodeLigne(),
                                arrayList_adpter.get(position).getCodeProject(),
                                arrayList_adpter.get(position).getDesignationLigne(), arrayList_adpter.get(position).getPrevision());
                        entity_ligne.setIdLigne(arrayList_adpter.get(position).getIdLigne());
                        if (from_activity.equals("Nouveau"))
                        {
                            Nouveau_Projet nouveau_projet = (Nouveau_Projet)context;
                            nouveau_projet.deleteLigne(position);
                        }
                        else
                        {
                            LigneActivity ligneActivity = (LigneActivity)context;
                            ligneActivity.deleteLigneRoom(entity_ligne);
                        }
                    }
                });
                builder.show();
                return false;
            }
        });

        return convertView;
    }

    public int setLigne(List<Entity_Ligne> entity_lignes)
    {
        this.arrayList_adpter.clear();
        this.arrayList_adpter.addAll(entity_lignes);
        return arrayList_adpter.size();
    }
}
