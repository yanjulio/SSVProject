package com.soft.ssvapp.Data;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "tOperation", indices = {@Index(value = {"NumOperation"}, unique = true),
        @Index(value = {"CodeEtatdeBesoin"}, unique = false)}, foreignKeys = @ForeignKey(
                entity = Entity_Besoin.class, parentColumns = "CodeEtatdeBesoin", childColumns = "CodeEtatdeBesoin"))
public class Entity_Operation {
    @ColumnInfo(name = "IdOperation")
    @PrimaryKey(autoGenerate = true)
    private int idOperation;
    @ColumnInfo(name = "NumOperation")
    private String numOperation ;
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

    public Entity_Operation(String numOperation, String libelle, String nomUt, String dateOperation,
                            String dateSysteme, String codeEtatdeBesoin) {
        this.numOperation = numOperation;
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

    public String getNumOperation() {
        return numOperation;
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
