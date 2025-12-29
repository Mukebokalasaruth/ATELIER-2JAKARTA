/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jakarta.udbl.jakartamission.business;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import jakarta.faces.context.FacesContext;

@Named("profilBean")
@SessionScoped
public class ProfilBean implements Serializable {

    private String username = "Mukebo124";
    private String email = "22mk289@esisalama.org";
    private String nom = "Ruth";
    private String prenom = "";

    private String ancienMotDePasse;
    private String nouveauMotDePasse;

    // GETTERS & SETTERS

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAncienMotDePasse() {
        return ancienMotDePasse;
    }

    public void setAncienMotDePasse(String ancienMotDePasse) {
        this.ancienMotDePasse = ancienMotDePasse;
    }

    public String getNouveauMotDePasse() {
        return nouveauMotDePasse;
    }

    public void setNouveauMotDePasse(String nouveauMotDePasse) {
        this.nouveauMotDePasse = nouveauMotDePasse;
    }
    private String description = "";

// Ajoutez le getter et setter
public String getDescription() {
    return description;
}

public void setDescription(String description) {
    this.description = description;
}

    // ACTIONS

    public String enregistrerProfil() {
        return null; // reste sur la page
    }

    public String changerMotDePasse() {
        return null;
    }

    public String deconnexion() {
        FacesContext.getCurrentInstance()
                .getExternalContext()
                .invalidateSession();
        return "index?faces-redirect=true";
    }
}
