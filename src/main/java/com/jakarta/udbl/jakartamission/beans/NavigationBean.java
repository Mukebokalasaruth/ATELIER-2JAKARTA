package com.jakarta.udbl.jakartamission.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named("navigationBean") // Utilisez "navigationBean" pour correspondre à vos pages xhtml
@RequestScoped
public class NavigationBean implements Serializable {

    private static final Logger logger = Logger.getLogger(NavigationBean.class.getName());

    public NavigationBean() {
    }

    /**
     * Méthode générique pour la redirection
     */
    public void redirection(String destination) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(destination);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Erreur lors de la redirection vers : " + destination, ex);
        }
    }

    public void voirApropos() {
        this.redirection("pages/a_propos.xhtml");
    }

    public String ajouterLieu() {
        return "pages/lieu?faces-redirect=true";
}

    public void voirVisite() {
        this.redirection("pages/visite.xhtml");
    }
    
    public void allerVersIndex() {
        this.redirection("index.xhtml");
    }
}