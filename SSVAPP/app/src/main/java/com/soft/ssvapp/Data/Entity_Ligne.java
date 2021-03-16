package com.soft.ssvapp.Data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "tLigne", indices = {@Index(value = {"CodeProject"}, unique = false), @Index(value = {"CodeLigne"}, unique = true)},
        foreignKeys = @ForeignKey(
        entity = Entity_Project.class,
        parentColumns = "CodeProject",
        childColumns = "CodeProject",
        onDelete = ForeignKey.CASCADE))
public class Entity_Ligne {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "IdLigne")
    private int idLigne;
    @ColumnInfo(name = "CodeLigne")
    private String codeLigne;
    @ColumnInfo(name = "CodeProject")
    private String codeProject;
    @ColumnInfo(name = "DesignationLigne")
    private String designationLigne;
    @ColumnInfo(name = "Prevision")
    private double prevision;

    public Entity_Ligne(String codeLigne, String codeProject, String designationLigne, double prevision) {
        this.codeLigne = codeLigne;
        this.codeProject = codeProject;
        this.designationLigne = designationLigne;
        this.prevision = prevision;
    }

    public void setIdLigne(int idLigne) {
        this.idLigne = idLigne;
    }

    public int getIdLigne() {
        return idLigne;
    }

    public String getCodeLigne() {
        return codeLigne;
    }

    public String getCodeProject() {
        return codeProject;
    }

    public String getDesignationLigne() {
        return designationLigne;
    }

    public double getPrevision() {
        return prevision;
    }
}
