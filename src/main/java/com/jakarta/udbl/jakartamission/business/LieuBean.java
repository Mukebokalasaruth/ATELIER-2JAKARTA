/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jakarta.udbl.jakartamission.business;

import com.jakarta.udbl.jakartamission.entities.Lieu;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named(value = "lieuBean")
@RequestScoped
public class LieuBean implements Serializable {

    // CORRECTION : Utilisation de Integer au lieu de Long
    private Integer id; 
    
    private String nom;
    private String description;
    private double longitude;
    private double latitude;
    private List<Lieu> lieux;

    @Inject
    private LieuEntrepriseBean lieuEntrepriseBean;

    // --- Getters et Setters ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public List<Lieu> getLieux() {
        return lieuEntrepriseBean.listerTousLesLieux();
    }

    // --- Actions ---

    public void ajouterLieu() {
        if (nom != null && !nom.isEmpty()) {
            lieuEntrepriseBean.ajouterLieuEntreprise(nom, description, latitude, longitude);
            reinitialiserFormulaire();
        }
    }

    // CORRECTION : Paramètre Integer
    public void supprimer(Integer idASupprimer) {
        lieuEntrepriseBean.supprimerLieu(idASupprimer);
    }

    // CORRECTION : Paramètre Integer
    public void chargerPourModification(Integer idAModifier) {
        Lieu lieu = lieuEntrepriseBean.trouverLieu(idAModifier);
        if (lieu != null) {
            // C'est ici que l'erreur se produisait avant (int -> Long impossible)
            // Maintenant (int -> Integer) c'est valide.
            this.id = lieu.getId(); 
            this.nom = lieu.getNom();
            this.description = lieu.getDescription();
            this.latitude = lieu.getLatitude();
            this.longitude = lieu.getLongitude();
        }
    }

    public void modifier() {
        lieuEntrepriseBean.modifierLieu(id, nom, description, latitude, longitude);
        reinitialiserFormulaire();
    }
    
    private void reinitialiserFormulaire() {
        this.id = null;
        this.nom = null;
        this.description = null;
        this.latitude = 0.0;
        this.longitude = 0.0;
    }
}