package com.soft.ssvapp.Data;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class EntityOperationWithEntityMvtCompte {


    @Embedded
    Entity_Operation entity_operation;

    @Embedded
    Entity_MvtCompte entity_mvtCompte;

    @Relation(
            parentColumn= "NumOperationOp",
            entityColumn = "NumOperation"
    )

    public List<Entity_MvtCompte> list_operation;

    public Entity_Operation getEntity_operation(){
        return entity_operation;
    }

    public Entity_MvtCompte getEntity_mvtCompte(){
        return entity_mvtCompte;
    }

    public List<Entity_MvtCompte> getList_operation()
    {
        return list_operation;
    }

//    public void setEntity_operation(Entity_Operation entity_operation){
//        this.list_operation.add(entity_operation);
//    }


}
