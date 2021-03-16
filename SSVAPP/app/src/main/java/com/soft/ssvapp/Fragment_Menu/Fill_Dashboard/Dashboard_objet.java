package com.soft.ssvapp.Fragment_Menu.Fill_Dashboard;

public class Dashboard_objet {

    private String title;
    private String details;
    private String pourcentage;

    public Dashboard_objet(String title, String details, String pourcentage) {
        this.title = title;
        this.details = details;
        this.pourcentage = pourcentage;
    }

    public String getTitle() {
        return title;
    }

    public String getDetails() {
        return details;
    }

    public String getPourcentage() {
        return pourcentage;
    }
}
