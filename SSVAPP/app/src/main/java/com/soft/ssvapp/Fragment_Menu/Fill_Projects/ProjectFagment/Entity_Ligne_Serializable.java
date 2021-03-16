package com.soft.ssvapp.Fragment_Menu.Fill_Projects.ProjectFagment;

import java.io.Serializable;

public class Entity_Ligne_Serializable implements Serializable {

    private int idLigne;
    private String codeLigne;
    private String codeProject;
    private String designationLigne;
    private double provision;

    public Entity_Ligne_Serializable(String codeLigne, String codeProject, String designationLigne, double provision) {
        this.codeLigne = codeLigne;
        this.codeProject = codeProject;
        this.designationLigne = designationLigne;
        this.provision = provision;
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

    public double getProvision() {
        return provision;
    }
}
