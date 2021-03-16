package com.soft.ssvapp.Data;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class Entity_BesoinWithEntity_DetailBesoin {

    @Embedded
    Entity_DetailBesoin detailBesoin;

    @Embedded
    Entity_Besoin besoin;

    @Relation(
            parentColumn = "CodeEtatdeBesoinDetails",
            entityColumn = "CodeEtatdeBesoin"
    )
    List<Entity_Besoin> entity_besoins;

    public Entity_DetailBesoin getDetailBesoin() {
        return detailBesoin;
    }

    public Entity_Besoin getBesoin()
    {
        return besoin;
    }
}
