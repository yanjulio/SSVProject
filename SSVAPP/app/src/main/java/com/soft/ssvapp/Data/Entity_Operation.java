package com.soft.ssvapp.Data;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

//,
//@Index(value = {"CodeEtatdeBesoin"}, unique = false)}, foreignKeys = @ForeignKey(
//        entity = Entity_Besoin.class, parentColumns = "CodeEtatdeBesoin", childColumns = "CodeEtatdeBesoin")
@Entity(tableName = "tOperation", indices = {@Index(value = {"NumOperationOp"}, unique = true)})
public class Entity_Operation {
    @ColumnInfo(name = "IdOperation")
    @PrimaryKey(autoGenerate = true)
    private int idOperation;
    @ColumnInfo(name = "NumOperationOp")
    private String numOperationOp ;
    @ColumnInfo(name = "Libelle")
    private String libelle;
    @ColumnInfo(name = "NomUt")
    private String nomUt;
    @ColumnInfo(name = "CodeEtatdeBesoin")
    private String codeEtatdeBesoin;
    @ColumnInfo(name = "DateOperation")
    private String dateOperation;
    @ColumnInfo(name = "DateSysteme")
    private String dateSysteme ;

    public Entity_Operation(String numOperationOp, String libelle, String nomUt, String dateOperation,
                            String dateSysteme, String codeEtatdeBesoin) {
        this.numOperationOp = numOperationOp;
        this.libelle = libelle;
        this.nomUt = nomUt;
        this.codeEtatdeBesoin = codeEtatdeBesoin;
        this.dateOperation = dateOperation;
        this.dateSysteme = dateSysteme;
    }

    public void setIdOperation(int idOperation) {
        idOperation = idOperation;
    }

    public int getIdOperation() {
        return idOperation;
    }

    public String getNumOperationOp() {
        return numOperationOp;
    }

    public String getLibelle() {
        return libelle;
    }

    public String getDateOperation() {
        return dateOperation;
    }

    public String getDateSysteme() {
        return dateSysteme;
    }

    public String getCodeEtatdeBesoin() {
        return codeEtatdeBesoin;
    }

    public String getNomUt() {
        return nomUt;
    }
}
