package com.jakarta.udbl.jakartamission.business;

import com.jakarta.udbl.jakartamission.entities.Utilisateur;
import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

@Stateless
@LocalBean
public class UtilisateurEntrepriseBean {

    @PersistenceContext
    private EntityManager em;

    // --- VOTRE NOUVELLE MÉTHODE ---
    public Utilisateur authentifier(String email, String password){
        Utilisateur utilisateur = this.trouverUtilisateurParEmail(email);
        if (utilisateur != null && this.verifierMotDePasse(password, utilisateur.getPassword())){
            return utilisateur;
            }
        return null;
        
    }

    public boolean verifierMotDePasse(String password, String hashedPassword) { 
        return BCrypt.checkpw(password, hashedPassword); 
    }

    // ------------------------------

    @Transactional
    public void ajouterUtilisateurEntreprise(String username, String email, String password, String description) {
        if (trouverUtilisateurParEmail(email) != null) {
            throw new IllegalArgumentException("Cet email existe déjà.");
        }
        if (trouverUtilisateurParNom(username) != null) {
            throw new IllegalArgumentException("Ce nom d'utilisateur existe déjà.");
        }
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        Utilisateur utilisateur = new Utilisateur(username, email, hashedPassword, description);
        em.persist(utilisateur);
    }

    public List<Utilisateur> listerTousLesUtilisateurs() {
        return em.createQuery("SELECT u FROM Utilisateur u", Utilisateur.class).getResultList();
    }

    @Transactional
    public void supprimerUtilisateur(Long id) {
        Utilisateur utilisateur = em.find(Utilisateur.class, id);
        if (utilisateur != null) {
            em.remove(utilisateur);
        }
    }

    public Utilisateur trouverUtilisateurParId(Long id) {
        return em.find(Utilisateur.class, id);
    }

    public Utilisateur trouverUtilisateurParEmail(String email) {
        try {
            return em.createQuery("SELECT u FROM Utilisateur u WHERE u.email = :email", Utilisateur.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public Utilisateur trouverUtilisateurParNom(String username) {
        try {
            return em.createQuery("SELECT u FROM Utilisateur u WHERE u.username = :username", Utilisateur.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public void mettreAJour(Utilisateur utilisateur) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}