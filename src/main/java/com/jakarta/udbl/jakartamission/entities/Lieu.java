/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jakarta.udbl.jakartamission.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable; // C'est une bonne pratique d'ajouter ceci

@Entity
@Table(name = "lieu")
public class Lieu implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    private String nom;
    @Column(name="descripton") 
    private String description;
    private double longitude;
    private double latitude;

    public Lieu() {
    }

    public Lieu(String nom, String description, double longitude, double latitude) {
        this.nom = nom;
        this.description = description;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    // --- GETTERS ET SETTERS ---

    public Long getId() { 
        return id; 
    }

    // AJOUT IMPORTANT : Le Setter manquant
    public void setId(Long id) { 
        this.id = id; 
    }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }
}