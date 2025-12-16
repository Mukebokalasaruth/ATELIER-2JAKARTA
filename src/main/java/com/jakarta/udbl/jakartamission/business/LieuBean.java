/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jakarta.udbl.jakartamission.business;

// ATTENTION : Assurez-vous d'importer la bonne entité Lieu
import com.jakarta.udbl.jakartamission.entities.Lieu;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
// Si vous souhaitez utiliser la validation standard Java (recommandé) :
// import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Named(value = "lieuBean")
@RequestScoped
public class LieuBean implements Serializable {

    // NOTE SUR LA VALIDATION :
    // Au lieu de faire des 'if' dans la méthode ajouterLieu,
    // il est recommandé d'utiliser des annotations ici, par exemple :
    // @NotEmpty(message = "Le nom est obligatoire")
    private String nom;

    // @NotEmpty(message = "La description est obligatoire")
    private String description;

    private double longitude;
    private double latitude;

    // CORRECTION 2 : Suppression de la liste 'lieux' qui était inutile ici
    // car le getter appelle directement le service.

    @Inject
    private LieuEntrepriseBean lieuEntrepriseBean;

    // --- Getters et Setters pour les champs du formulaire ---
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }


    // --- Méthodes métier utilisées par la vue JSF ---

    /**
     * Méthode appelée par <h:dataTable value="#{lieuBean.lieux}" ...>
     * CORRECTION 1 : Le type de retour doit être List<Lieu> (l'entité),
     * et non pas List<LieuBean> (le contrôleur).
     * @return 
     */
    // LA MÉTHODE MANQUANTE DOIT ÊTRE ICI :
    public List<Lieu> listerTousLesLieux() {
        // Exemple d'implémentation JPA :
        // return em.createQuery("SELECT l FROM Lieu l", Lieu.class).getResultList();
        return null; // (Code temporaire si vous n'avez pas encore la BDD)
    }

    /**
     * Méthode appelée par <h:commandButton action="#{lieuBean.ajouterLieu}" ...>
     */
    public void ajouterLieu() {
        // Validation manuelle (Si vous n'utilisez pas @NotEmpty ou required="true" dans le JSF)
        if (nom != null && !nom.isEmpty() && description != null && !description.isEmpty()) {

            lieuEntrepriseBean.ajouterLieuEntreprise(nom, description, latitude, longitude);

            // AMÉLIORATION 4 : Ajout d'un message de succès pour l'utilisateur
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès", "Le lieu a été ajouté."));

            // Comme le bean est @RequestScoped, les champs (nom, description...)
            // seront automatiquement vidés au prochain affichage de la page,
            // ce qui est le comportement désiré après un ajout réussi.

        } else {
             // AMÉLIORATION : Message d'erreur si la validation manuelle échoue
             FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Le nom et la description sont obligatoires."));
        }
    }
    
}