package com.soft.ssvapp.Repositories;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.soft.ssvapp.Data.Kp_BatimentDatabaseHelper;
import com.soft.ssvapp.Fragment_Menu.Fill_Projects.ProjectFagment.Projects_list_Objects;

import java.util.ArrayList;
import java.util.List;

public class Project_Repositories {

    Kp_BatimentDatabaseHelper helper;
    private static Project_Repositories instance;
    private ArrayList<Projects_list_Objects> dataset = new ArrayList<>();

    public static Project_Repositories getInstance()
    {
        if (instance == null)
        {
            instance = new Project_Repositories();
        }
        return instance;
    }

    public MutableLiveData<List<Projects_list_Objects>> getProject(Context context)
    {
        setProject(context);
        MutableLiveData<List<Projects_list_Objects>> data = new MutableLiveData<>();
        data.setValue(dataset);
        return data;
    }

    private void setProject(Context context)
    {
        Log.e("value select_tProjet",
                Kp_BatimentDatabaseHelper.getInstance(context).select_tProjets().toString() + " Regarde la valeur");
        Cursor cursor = Kp_BatimentDatabaseHelper.getInstance(context).select_tProjets();
        if (cursor != null && cursor.getCount() > 0)
        {
            dataset.clear();
            if (cursor.moveToFirst())
            {
                do {
                    dataset.add(
                            new Projects_list_Objects(
                                    cursor.getString(cursor.getColumnIndex("CodeProject")),
                                    cursor.getString(cursor.getColumnIndex("DesignationProject")),
                                    cursor.getString(cursor.getColumnIndex("Lieu"))));
                }while (cursor.moveToNext());
            }
        }
    }

}
