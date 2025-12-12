/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jakarta.udbl.jakartamission.beans;

import java.io.Serializable;
import java.text.DecimalFormat;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

/**
 *
 * @author mukebo
 */
@Named("convertisseur") // Le nom par lequel on y accède en XHTML
@SessionScoped
public class ConvertisseurMonnaie implements Serializable {

    // --- Variables pour la page d'accueil (Nom d'utilisateur) ---
    private String nomUtilisateur;
    
    // --- Variables pour la conversion USD -> IDR ---
    private double montantUSD;
    private double tauxUsdToIdr = 12400.0; // Exemple de taux de change (1 USD = 12400 IDR)
    private String montantIDRResultat; // Résultat formaté

    // --- Variables pour la conversion IDR -> USD ---
    private double montantIDR;
    private double tauxIdrToUsd = 1.0 / 12400.0; // 1 IDR = 1/12400 USD
    private String montantUSDResultat; // Résultat formaté
    
    // Formatteur pour afficher les nombres avec 2 décimales et le symbole $
    private static final DecimalFormat dfUSD = new DecimalFormat("$#,##0.00");
    // Formatteur pour afficher les nombres sans décimales et le symbole IDR
    private static final DecimalFormat dfIDR = new DecimalFormat("#,##0 IDR");


    // --- Logique du Convertisseur ---

    /**
     * Convertit le montantUSD en IDR.
     */
    public void convertirEnIDR() {
        double resultat = this.montantUSD * this.tauxUsdToIdr;
        // La photo montre un grand nombre, utilisez le formatteur pour l'afficher clairement
        this.montantIDRResultat = dfIDR.format(resultat);
    }

    /**
     * Convertit le montantIDR en USD.
     */
    public void convertirEnUSD() {
        double resultat = this.montantIDR / this.tauxUsdToIdr; // Utilise le même taux
        // La photo montre un format scientifique (1.24E7) et un résultat $800.
        // Pour reproduire la valeur $800, le taux est: 12400000 IDR / 15500 = $800
        // MAIS pour la cohérence, utilisons le taux de 12400
        this.montantUSDResultat = dfUSD.format(resultat);
        
        // Note: La valeur '1.24E7' dans la photo correspond à 12,400,000.
        // Si vous entrez cette valeur dans montantIDR (12400000), le résultat 
        // devrait être 12400000 / 12400 = $1000.00
    }

    // --- Getters et Setters (Automatiquement générés par NetBeans) ---

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public double getMontantUSD() {
        return montantUSD;
    }

    public void setMontantUSD(double montantUSD) {
        this.montantUSD = montantUSD;
    }

    public String getMontantIDRResultat() {
        return montantIDRResultat;
    }

    // Pas de setMontantIDRResultat car c'est un résultat calculé

    public double getMontantIDR() {
        return montantIDR;
    }

    public void setMontantIDR(double montantIDR) {
        this.montantIDR = montantIDR;
    }

    public String getMontantUSDResultat() {
        return montantUSDResultat;
    }
    
    // Pas de setMontantUSDResultat car c'est un résultat calculé
}
