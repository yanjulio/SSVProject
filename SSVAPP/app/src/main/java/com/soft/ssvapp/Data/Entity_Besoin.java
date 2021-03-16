package com.soft.ssvapp.Data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "tEtatDeBesoin", indices = {@Index(value = {"CodeProjectBesoin"}, unique = false),
        @Index(value ={"CodeEtatdeBesoin"}, unique = true)},
        foreignKeys = @ForeignKey(
                entity = Entity_Project.class,
                parentColumns = "CodeProject",
                childColumns = "CodeProjectBesoin",
                onDelete = ForeignKey.CASCADE))
public class Entity_Besoin {

    @ColumnInfo(name = "IdEtatDuBesoin")
    @PrimaryKey(autoGenerate = true)
    private int idEtatDuBesoin;

    @ColumnInfo(name = "CodeEtatdeBesoin")
    private String codeEtatdeBesoin;

    @ColumnInfo(name = "CodeEtatDeBesoinOnline")
    private String codeEtatDeBesoinOnline;

    @ColumnInfo(name = "DesignationEtatDeBesion")
    private String designationEtatDeBesion;

    @ColumnInfo(name = "CodeProjectBesoin") // , index = true
    private String codeProject;

    @ColumnInfo(name = "CodeLigneBesoin")
    private String codeLigne = "";

    @ColumnInfo(name = "Demandeur")
    private String demandeur;
    @ColumnInfo(name = "DateEmision")
    private String dateEmision;
    @ColumnInfo(name = "DateValidation")
    private String dateValidation;
    @ColumnInfo(name = "DateRequise")
    private String dateRequise;
    @ColumnInfo(name = "ValiderPar")
    private String validerPar;
    @ColumnInfo(name = "EtatBesoin")
    private int etat;
    @ColumnInfo(name = "EtatBesoinEnvoyer")
    private int etat_besoin_envoyer;

    public Entity_Besoin(String codeEtatdeBesoin, String designationEtatDeBesion, String codeProject, String demandeur,
                         String dateEmision, String dateValidation, String dateRequise, String validerPar,
                         int etat, String codeEtatDeBesoinOnline) {
        this.codeEtatdeBesoin = codeEtatdeBesoin;
        this.designationEtatDeBesion = designationEtatDeBesion;
        this.codeProject = codeProject;
        this.demandeur = demandeur;
        this.dateEmision = dateEmision;
        this.dateValidation = dateValidation;
        this.dateRequise = dateRequise;
        this.validerPar = validerPar;
        this.etat = etat;
        this.codeEtatDeBesoinOnline = codeEtatDeBesoinOnline;
    }

    public void setIdEtatDuBesoin(int idEtatDuBesoin) {
        this.idEtatDuBesoin = idEtatDuBesoin;
    }

    public void setEtat_besoin_envoyer(int etat_besoin_envoyer)
    {
        this.etat_besoin_envoyer = etat_besoin_envoyer;
    }

    public void setCodeEtatdeBesoin(String codeEtatDeBesoin)
    {
        this.codeEtatdeBesoin = codeEtatDeBesoin;
    }

    public void setCodeLigne(String codeLigne)
    {
        this.codeLigne = codeLigne;
    }

    public String getCodeLigne()
    {
        return codeLigne;
    }

    public int getIdEtatDuBesoin() {
        return idEtatDuBesoin;
    }

    public String getCodeEtatdeBesoin() {
        return codeEtatdeBesoin;
    }

    public String getDesignationEtatDeBesion() {
        return designationEtatDeBesion;
    }

    public String getCodeProject() {
        return codeProject;
    }

    public String getDemandeur() {
        return demandeur;
    }

    public String getDateEmision() {
        return dateEmision;
    }

    public String getDateValidation() {
        return dateValidation;
    }

    public String getDateRequise() {
        return dateRequise;
    }

    public String getValiderPar() {
        return validerPar;
    }

    public int getEtat() {
        return etat;
    }

    public int getEtat_besoin_envoyer()
    {
        return etat_besoin_envoyer;
    }

    public String getCodeEtatDeBesoinOnline() {
        return codeEtatDeBesoinOnline;
    }
}
