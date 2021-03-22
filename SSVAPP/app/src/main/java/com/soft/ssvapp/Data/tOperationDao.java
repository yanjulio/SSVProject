package com.soft.ssvapp.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface tOperationDao {

    @Insert
    void insert(Entity_Operation entity_operation) throws Exception;
//    void insert(Entity_Operation entity_operation);

    @Query(" SELECT tOperation.NumOperationOp, tOperation.Libelle,"+
            "tMvtCompte.Qte, tMvtCompte.Entree, tMvtCompte.Sortie\n" +
            "FROM tOperation INNER JOIN\n" +
            "tMvtCompte ON tOperation.NumOperationOp = tMvtCompte.NumOperation" +
            " where tOperation.DateOperation = :timestamp")
    LiveData<List<EntityOperationWithEntityMvtCompte>> getOperation(String timestamp);

//    @Query(" SELECT tOperation.NumOperationOp, tOperation.Libelle,"+
//            "tMvtCompte.Qte, tMvtCompte.Entree, tMvtCompte.Sortie\n" +
//            "FROM tOperation INNER JOIN\n" +
//            "tMvtCompte ON tOperation.NumOperationOp = tMvtCompte.NumOperation" +
//            " where tOperation.NumOperationOp = 'OP19'")
//    LiveData<List<EntityOperationWithEntityMvtCompte>> getOperation();

//    @Query("select * from tOperation where DateOperation = :timestamp")
//    LiveData<List<Entity_Operation>> getRecentOperation(String timestamp);

}
