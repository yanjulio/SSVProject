package com.soft.ssvapp.Data;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class Entity_ProjectWithEntity_Ligne {

    @Embedded
    Entity_Ligne entity_ligne;

    @Relation(
            parentColumn = "CodeProject",
            entityColumn = "CodeProject"
    )
    List<Entity_Project> entity_project;

    public Entity_Ligne getEntity_ligne() {
        return entity_ligne;
    }
}
