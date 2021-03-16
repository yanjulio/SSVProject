package com.soft.ssvapp.Data;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class Entity_ProjectWithEntity_Besoin {
//    @Embedded Entity_Project entity_project;
    @Embedded Entity_Besoin entity_besoin;

    @Relation(
            parentColumn = "CodeProjectBesoin",
            entityColumn = "CodeProject"
    )
    List<Entity_Project> entity_projects;

    public Entity_Besoin getEntity_besoin() {
        return entity_besoin;
    }

}
