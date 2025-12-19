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

    @Transactional
    public void ajouterUtilisateurEntreprise(String username, String email, String password, String description) {

        // Vérification si l’email existe
        if (trouverUtilisateurParEmail(email) != null) {
            throw new IllegalArgumentException("Cet email existe déjà.");
        }

        // Vérification du username
        if (trouverUtilisateurParNom(username) != null) {
            throw new IllegalArgumentException("Ce nom d'utilisateur existe déjà.");
        }

        // Hashage mot de passe
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        // Création objet utilisateur
        Utilisateur utilisateur = new Utilisateur(username, email, hashedPassword, description);

        // Persistance BDD
        em.persist(utilisateur);
    }

    public boolean verifierMotDePasse(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
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
}
