/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jakarta.udbl.jakartamission.beans;

import com.jakarta.udbl.jakartamission.business.SessionManager;
import com.jakarta.udbl.jakartamission.business.UtilisateurEntrepriseBean;
import com.jakarta.udbl.jakartamission.entities.Utilisateur;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject; 
import jakarta.inject.Named;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

/**
 *
 * @author mukebo
 */
@RequestScoped
@Named("welcomeBean")
public class WelcomBean {

   

    private String nom;
    private String message;
    private String email;
    private String password;
    
    @Inject
    private UtilisateurEntrepriseBean utilisateurEntrepriseBean;
    
    @Inject
    private SessionManager sessionManager; 
    
    public String getNom(){ return nom; }
    public void setNom(String nom){ this.nom = nom; }
    
    public String getMessage(){ return message; }
    public void setMessage(String message){ this.message = message; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String sAuthentifier() {
        Utilisateur utilisateur = utilisateurEntrepriseBean.authentifier(email, password);
        FacesContext context = FacesContext.getCurrentInstance();
        if (utilisateur != null) {
            sessionManager.createSession("user", email);
        
            return "home?faces-redirect=true"; // Redirection après connexion réussie
    
        } else {
        
            this.message = "Email ou mot de passe incorrect.";
        
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
        
            return null;
        }
    } 
}