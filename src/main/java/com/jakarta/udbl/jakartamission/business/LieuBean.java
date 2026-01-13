package com.jakarta.udbl.jakartamission.business;

import com.jakarta.udbl.jakartamission.entities.Lieu;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import java.io.Serializable;
import java.util.List;

@Named(value = "lieuBean")
@ViewScoped
public class LieuBean implements Serializable {

    private Long id; 
    private String nom;
    private String description;
    private double longitude;
    private double latitude;

    private String nomSelectionne; 
    private Long selectedLieu;    
    private String weatherMessage = "";
    
    private List<Lieu> lieux;

    @Inject
    private LieuEntrepriseBean lieuEntrepriseBean;

    @PostConstruct
    public void init() {
        chargerLieux();
    }

    public void chargerLieux() {
        this.lieux = lieuEntrepriseBean.listerTousLesLieux();
    }

    public void onLieuSelected() {
    if (nomSelectionne != null && !nomSelectionne.isEmpty()) {
        for (Lieu l : lieux) {
            if (l.getNom().equalsIgnoreCase(nomSelectionne)) {
                this.selectedLieu = l.getId(); // CHANGÉ ICI (l.getId() au lieu de Long.getId())
                fetchWeatherMessage(l);
                return;
            }
        }
    }
    this.selectedLieu = null;
    this.weatherMessage = "";
}

    private void fetchWeatherMessage(Lieu l) {
        try {
            String serviceURL = "http://localhost:8080/j-weather/resources/JakartaWeather?latitude="
                    + l.getLatitude() + "&longitude=" + l.getLongitude();
            Client client = ClientBuilder.newClient();
            this.weatherMessage = client.target(serviceURL)
                                        .request(MediaType.TEXT_PLAIN)
                                        .get(String.class);
        } catch (Exception e) {
            this.weatherMessage = "Service météo indisponible.";
        }
    }

    public String enregistrerNouveauLieu() {
        Lieu nouveau = new Lieu();
        nouveau.setNom(this.nom);
        nouveau.setDescription(this.description);
        nouveau.setLatitude(this.latitude);
        nouveau.setLongitude(this.longitude);

        lieuEntrepriseBean.ajouterLieu(nouveau);
        chargerLieux(); 
        resetForm();    
        return null;
    }

    public void chargerPourModification(Long idLieu) {
    // Retrait de .intValue() car l'EJB accepte désormais Long
    Lieu l = lieuEntrepriseBean.trouverLieuParId(idLieu); 
    if (l != null) {
        this.id = l.getId();
        this.nom = l.getNom();
        this.description = l.getDescription();
        this.latitude = l.getLatitude();
        this.longitude = l.getLongitude();
    }
}

    public void supprimer(Long idLieu) {
        lieuEntrepriseBean.supprimerLieu(idLieu);
        chargerLieux();
    }

    public String visiterLieu() {
        if (selectedLieu != null && selectedLieu != 0L) {
            return "formulaire_visite?faces-redirect=true&lieuId=" + selectedLieu;
        }
        return null; 
    } // L'accolade manquante était ici

    private void resetForm() {
        this.id = null;
        this.nom = "";
        this.description = "";
        this.latitude = 0.0;
        this.longitude = 0.0;
    }

    // --- GETTERS ET SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }
    public String getNomSelectionne() { return nomSelectionne; }
    public void setNomSelectionne(String nomSelectionne) { this.nomSelectionne = nomSelectionne; }
    public Long getSelectedLieu() { return selectedLieu; }
    public void setSelectedLieu(Long selectedLieu) { this.selectedLieu = selectedLieu; }
    public String getWeatherMessage() { return weatherMessage; }
    public void setWeatherMessage(String weatherMessage) { this.weatherMessage = weatherMessage; }
    public List<Lieu> getLieux() { return lieux; }
    public void setLieux(List<Lieu> lieux) { this.lieux = lieux; }
}