/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jakarta.udbl.jakartamission.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.IOException;
/**
 *
 * @author mukebo
 */
@Named(value="navigationController")
@RequestScoped

public class NavigationBean {
    public void allerVers(String lien) {
        try {
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect(lien);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void apropos() {
        this.allerVers("pages/a_propos.xhtml");
    }
    public void lieu() {
        this.allerVers("pages/lieu.xhtml");
    }
}
