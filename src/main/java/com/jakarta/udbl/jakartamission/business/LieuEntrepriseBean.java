// ---- LA CORRECTION ----
package com.jakarta.udbl.jakartamission.business;

// IMPORTANT : Importez la classe d'entité (celle qui va en base de données)
import com.jakarta.udbl.jakartamission.entities.Lieu;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless // Ou @RequestScoped si c'est un bean CDI simple
public class LieuEntrepriseBean {

   @PersistenceContext(unitName = "my_persistence_unit")
private EntityManager em;

    // La méthode reçoit les données brutes
    public void ajouterLieuEntreprise(String nom, String description, double lat, double lon) {

        // 1. Créer une instance de l'ENTITÉ (Lieu), pas du Bean JSF (LieuBean)
        Lieu nouveauLieuEntity = new Lieu();

        // 2. Remplir les données via les setters
        // (L'entité Lieu doit avoir ces setters)
        nouveauLieuEntity.setNom(nom);
        nouveauLieuEntity.setDescription(description);
        nouveauLieuEntity.setLatitude(lat);
        nouveauLieuEntity.setLongitude(lon);

        // 3. Sauvegarder l'entité en base de données
        em.persist(nouveauLieuEntity);
    }

    // ... le reste de la classe, comme listerTousLesLieux ...
}