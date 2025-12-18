/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */

package com.jakarta.udbl.jakartamission.business;

import com.jakarta.udbl.jakartamission.entities.Lieu;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class LieuEntrepriseBean {

    @PersistenceContext(unitName = "indonesiatPU")
    private EntityManager em;

    public void ajouterLieuEntreprise(String nom, String description, double latitude, double longitude) {
        Lieu lieu = new Lieu();
        lieu.setNom(nom);
        lieu.setDescription(description);
        lieu.setLatitude(latitude);
        lieu.setLongitude(longitude);
        em.persist(lieu);
    }

    public List<Lieu> listerTousLesLieux() {
        return em.createQuery("SELECT l FROM Lieu l", Lieu.class).getResultList();
    }

    // CORRECTION : Integer ici
    public Lieu trouverLieu(Integer id) {
        return em.find(Lieu.class, id);
    }

    // CORRECTION : Integer ici
    public void modifierLieu(Integer id, String nom, String description, double latitude, double longitude) {
        Lieu lieu = em.find(Lieu.class, id);
        if (lieu != null) {
            lieu.setNom(nom);
            lieu.setDescription(description);
            lieu.setLatitude(latitude);
            lieu.setLongitude(longitude);
            em.merge(lieu);
        }
    }

    // CORRECTION : Integer ici
    public void supprimerLieu(Integer id) {
        Lieu lieu = em.find(Lieu.class, id);
        if (lieu != null) {
            em.remove(lieu);
        }
    }
}