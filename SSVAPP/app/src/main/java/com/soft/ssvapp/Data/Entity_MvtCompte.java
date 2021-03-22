package com.soft.ssvapp.Data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

//, @ForeignKey(
//        entity = Entity_Compte.class,
//        parentColumns = "NumCompte",
//        childColumns = "NumCompte",
//        onDelete = ForeignKey.CASCADE),
//@ForeignKey(
//        entity = Entity_Project.class,
//        parentColumns = "CodeProject",
//        childColumns = "CodeProject", onDelete = ForeignKey.CASCADE)
//, @Index(value = {"CodeProject"}, unique = false)
//@Index(value = {"NumCompte"}, unique = false),
@Entity(tableName = "tMvtCompte", indices = {
        @Index(value ={"NumOperation"}, unique = true)},
        foreignKeys = {@ForeignKey(
                entity = Entity_Operation.class,
                parentColumns = "NumOperationOp",
                childColumns = "NumOperation",
                onDelete = ForeignKey.CASCADE)})
public class Entity_MvtCompte {
    @ColumnInfo(name = "IdMouvement")
    @PrimaryKey(autoGenerate = true)
    private int idMouvement;
    @ColumnInfo(name = "NumCompte")
    private String numCompte;
    @ColumnInfo(name = "NumOperation")
    private String numOperation ;
    @ColumnInfo(name = "Details")
    private String details;
    @ColumnInfo(name = "Qte")
    private double qte ;
    @ColumnInfo(name = "Entree")
    private double entree = 0;
    @ColumnInfo(name = "Sortie")
    private double sortie = 0;
    @ColumnInfo(name = "CodeProject")
    private String codeProjet;

    public Entity_MvtCompte(String numCompte, String numOperation, String details, double qte, double entree, double sortie, String codeProjet) {
        this.numCompte = numCompte;
        this.numOperation = numOperation;
        this.details = details;
        this.qte = qte;
        this.entree = entree;
        this.sortie = sortie;
        this.codeProjet = codeProjet;
    }

    public void setIdMouvement(int idMouvement) {
        idMouvement = idMouvement;
    }

    public int getIdMouvement() {
        return idMouvement;
    }

    public String getNumCompte() {
        return numCompte;
    }

    public String getNumOperation() {
        return numOperation;
    }

    public String getDetails() {
        return details;
    }

    public double getQte() {
        return qte;
    }

    public double getEntree() {
        return entree;
    }

    public double getSortie() {
        return sortie;
    }

    public String getCodeProjet() {
        return codeProjet;
    }
}
