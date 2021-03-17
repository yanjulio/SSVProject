package com.soft.ssvapp.Repositories;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import com.soft.ssvapp.Data.Entity_Ligne;
import com.soft.ssvapp.Fragment_Menu.Fill_Projects.ProjectFagment.Entity_Ligne_Serializable;

import java.util.ArrayList;
import java.util.List;

public class LigneVolatileRepository {

    private static LigneVolatileRepository instance;

    private ArrayList<Entity_Ligne> dataset = new ArrayList<>();
    private ArrayList<Entity_Ligne_Serializable> datasetSerializable = new ArrayList<Entity_Ligne_Serializable>();
    private MutableLiveData<List<Entity_Ligne>> data = new MutableLiveData<>();

    public static LigneVolatileRepository getInstance()
    {
        if (instance == null)
        {
            instance = new LigneVolatileRepository();
        }
        return instance;
    }

    public MutableLiveData<List<Entity_Ligne>> getLigne()
    {
        return data;
    }

    public ArrayList<Entity_Ligne_Serializable> getLigneSerializable()
    {
        return datasetSerializable;
    }

    public void setLignes(Entity_Ligne list)
    {
        dataset.add(list);
        setLigneSeriablizable(list);
        data.setValue(dataset);
    }

    public void setLigneSeriablizable(Entity_Ligne entity)
    {
        Entity_Ligne_Serializable entity_ligne_serializable =
                new Entity_Ligne_Serializable(entity.getCodeLigne(), entity.getCodeProject(),
                        entity.getDesignationLigne(), entity.getPrevision());
        entity_ligne_serializable.setIdLigne(entity.getIdLigne());
        datasetSerializable.add(entity_ligne_serializable);
    }

    public void clearDataset()
    {
        dataset.clear();
        datasetSerializable.clear();
    }

    public void delete(int id)
    {
        dataset.remove(id);
        datasetSerializable.remove(id);
        data.setValue(dataset);
    }
    public void update(int id, Entity_Ligne entity_ligne)
    {
        dataset.set(id, entity_ligne);
    }
}
