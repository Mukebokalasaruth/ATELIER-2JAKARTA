package com.jakarta.udbl.jakartamission.beans;

import com.jakarta.udbl.jakartamission.business.LieuEntrepriseBean;
import com.jakarta.udbl.jakartamission.business.UtilisateurEntrepriseBean;
import com.jakarta.udbl.jakartamission.business.VisiteEntrepriseBean;
import com.jakarta.udbl.jakartamission.business.SessionManager;
import com.jakarta.udbl.jakartamission.entities.Lieu;
import com.jakarta.udbl.jakartamission.entities.Utilisateur;
import com.jakarta.udbl.jakartamission.entities.Visite;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped; // CHANGEMENT ICI
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named(value = "visiteBean")
@ViewScoped // CHANGEMENT : Vital pour garder les données du formulaire
public class VisiteBean implements Serializable {

    private Long lieuId; 
    private Double tempsPasse;
    private String observation;
    private Double depense;
    private List<Visite> mesVisites;

    @Inject
    private VisiteEntrepriseBean visiteEntrepriseBean;
    @Inject
    private LieuEntrepriseBean lieuEntrepriseBean;
    @Inject
    private UtilisateurEntrepriseBean utilisateurEntrepriseBean;
    @Inject
    private SessionManager sessionManager;

    @PostConstruct
    public void init() {
        refreshHistory();
    }

    // Méthode unique pour charger l'historique
    public void refreshHistory() {
        String email = sessionManager.getValueFromSession("user");
        if (email != null) {
            Utilisateur u = utilisateurEntrepriseBean.trouverUtilisateurParEmail(email);
            if (u != null) {
                this.mesVisites = visiteEntrepriseBean.listerVisitesParUtilisateur(u.getId());
            }
        }
    }

    public String afficherFormulaireVisite(Long id) {
        this.lieuId = id;
    // Ajoutez un "/" au début pour dire "partir de la racine du projet"
        return "/pages/formulaire_visite?faces-redirect=true&lieuId=" + id;
    }

    public String enregistrerVisite() {
        FacesContext context = FacesContext.getCurrentInstance();
        String email = sessionManager.getValueFromSession("user");
        
        if (email == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur session", "Connectez-vous."));
            return null;
        }

        if (lieuId == null || lieuId == 0L) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Lieu invalide."));
            return null;
        }

        Utilisateur u = utilisateurEntrepriseBean.trouverUtilisateurParEmail(email);
        Lieu l = lieuEntrepriseBean.trouverLieuParId(lieuId);

        if (u != null && l != null) {
            Visite visite = new Visite(u, l);
            visite.setTempsPasse(tempsPasse);
            visite.setObservation(observation);
            visite.setDepense(depense);
            
            visiteEntrepriseBean.enregistrerVisite(visite);
            
            // Notification de succès
            context.getExternalContext().getFlash().setKeepMessages(true);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès", "Visite enregistrée !"));
            
            return "visite?faces-redirect=true";
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Utilisateur ou Lieu introuvable."));
            return null;
        }
    }
    // Ajoutez cette méthode dans votre VisiteBean
    public Double getTotalDepenses() {
        if (mesVisites == null || mesVisites.isEmpty()) {
            return 0.0;
        }
        return mesVisites.stream()
                         .filter(v -> v.getDepense() != null)
                         .mapToDouble(Visite::getDepense)
                         .sum();
    }
    

    // Getters et Setters
    public Long getLieuId() { return lieuId; }
    public void setLieuId(Long lieuId) { this.lieuId = lieuId; }
    public Double getTempsPasse() { return tempsPasse; }
    public void setTempsPasse(Double tempsPasse) { this.tempsPasse = tempsPasse; }
    public String getObservation() { return observation; }
    public void setObservation(String observation) { this.observation = observation; }
    public Double getDepense() { return depense; }
    public void setDepense(Double depense) { this.depense = depense; }
    public List<Visite> getMesVisites() { return mesVisites; }
}