package com.soft.ssvapp.DataRetrofit.Ligne;

import com.google.gson.annotations.SerializedName;

public class LigneRetrofit {

    @SerializedName("idLigne")
    private int IdLigne ;
    @SerializedName("codeLigne")
    private String CodeLigne ;
    @SerializedName("codeProject")
    private String CodeProject ;
    @SerializedName("designationLIgne")
    private String DesignationLIgne ;
    @SerializedName("prevision")
    private double Prevision;
    private String code = "je suis vide pour le monent Ligne";

    public LigneRetrofit(String codeLigne, String codeProject, String designationLIgne, double prevision) {
        CodeLigne = codeLigne;
        CodeProject = codeProject;
        DesignationLIgne = designationLIgne;
        Prevision = prevision;
    }

    public int getIdLigne() {
        return IdLigne;
    }

    public String getCodeLigne() {
        return CodeLigne;
    }

    public String getCodeProject() {
        return CodeProject;
    }

    public String getDesignationLIgne() {
        return DesignationLIgne;
    }

    public void setResponse(String code)
    {
        this.code = code;
    }

    public String getResponse()
    {
        return code;
    }

    public double getPrevision() {
        return Prevision;
    }
}
