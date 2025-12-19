package com.jakarta.udbl.jakartamission.beans;

import com.jakarta.udbl.jakartamission.business.UtilisateurEntrepriseBean;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named
@RequestScoped
public class UtilisateurBean {

    private String username;
    private String email;
    private String password;
    private String confirmPassword;
    private String description;

    @EJB
    private UtilisateurEntrepriseBean entrepriseBean;

    public String ajouterUtilisateur() {

        FacesContext context = FacesContext.getCurrentInstance();

        // Vérification confirmation mot de passe
        if (!password.equals(confirmPassword)) {
            context.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Les mots de passe ne correspondent pas.", null));
            return null;
        }

        // Vérification Email existant
        if (entrepriseBean.trouverUtilisateurParEmail(email) != null) {
            context.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Cet email existe déjà.", null));
            return null;
        }

        // Vérification Username existant
        if (entrepriseBean.trouverUtilisateurParNom(username) != null) {
            context.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Ce nom d'utilisateur existe déjà.", null));
            return null;
        }

        // Ajouter l'utilisateur
        try {

            entrepriseBean.ajouterUtilisateurEntreprise(username, email, password, description);

            context.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Utilisateur ajouté avec succès !", null));

            // Réinitialiser les champs
            username = email = password = confirmPassword = description = "";

        } catch (Exception e) {
            context.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Erreur lors de l'ajout.", null));
        }

        return null;
    }

    // Getters & Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
