package fr.xcompta.core.base.test.integration.ecrituredebrouillard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.xcompta.core.base.Compte;
import fr.xcompta.core.base.EcritureDeBrouillard;
import fr.xcompta.core.base.MouvementDeBrouillard;
import fr.xcompta.core.base.exception.XComptaCoreException;

/**
 * Tests d'intégration complémentaires de l'ajout et de
 * l'insertion de mouvements dans une écriture de brouillard.
 *
 * <p>
 * Une écriture de brouillard étant destinée à la saisie progressive,
 * elle peut notamment contenir temporairement un mouvement vide.
 * </p>
 *
 * @author Fabrice Luneau
 */
class AjoutInsertionCasLimites {

    @Test
    @DisplayName(
            "Un mouvement existant peut être inséré entre deux mouvements")
    void insererMouvementExistant()
            throws XComptaCoreException {

        EcritureDeBrouillard ecriture =
                new EcritureDeBrouillard();

        MouvementDeBrouillard premier =
                mouvement(
                        "607",
                        "Achats",
                        "100.00");

        MouvementDeBrouillard dernier =
                mouvement(
                        "401",
                        "Fournisseurs",
                        "-120.00");

        MouvementDeBrouillard milieu =
                mouvement(
                        "44566",
                        "TVA déductible",
                        "20.00");

        ecriture.ajouterMouvementALaFin(
                premier);

        ecriture.ajouterMouvementALaFin(
                dernier);

        ecriture.insererMouvementA(
                1,
                milieu);

        assertEquals(
                3,
                ecriture.getMouvements().size());

        assertSame(
                premier,
                ecriture.getMouvements().get(0));

        assertSame(
                milieu,
                ecriture.getMouvements().get(1));

        assertSame(
                dernier,
                ecriture.getMouvements().get(2));

        assertSame(
                ecriture,
                milieu.getEcriture());
    }

    @Test
    @DisplayName(
            "Un mouvement vide peut être ajouté à une écriture de brouillard")
    void ajouterMouvementVide()
            throws XComptaCoreException {

        EcritureDeBrouillard ecriture =
                new EcritureDeBrouillard();

        MouvementDeBrouillard mouvementVide =
                new MouvementDeBrouillard();

        ecriture.ajouterMouvementALaFin(
                mouvementVide);

        assertEquals(
                1,
                ecriture.getMouvements().size());

        assertSame(
                mouvementVide,
                ecriture.getMouvements().get(0));

        assertSame(
                ecriture,
                mouvementVide.getEcriture());
    }

    @Test
    @DisplayName(
            "Un mouvement vide peut être inséré dans une écriture de brouillard")
    void insererMouvementVide()
            throws XComptaCoreException {

        EcritureDeBrouillard ecriture =
                new EcritureDeBrouillard();

        MouvementDeBrouillard premier =
                mouvement(
                        "607",
                        "Achats",
                        "100.00");

        MouvementDeBrouillard dernier =
                mouvement(
                        "401",
                        "Fournisseurs",
                        "-100.00");

        MouvementDeBrouillard mouvementVide =
                new MouvementDeBrouillard();

        ecriture.ajouterMouvementALaFin(
                premier);

        ecriture.ajouterMouvementALaFin(
                dernier);

        ecriture.insererMouvementA(
                1,
                mouvementVide);

        assertEquals(
                3,
                ecriture.getMouvements().size());

        assertSame(
                mouvementVide,
                ecriture.getMouvements().get(1));

        assertSame(
                ecriture,
                mouvementVide.getEcriture());
    }

    @Test
    @DisplayName(
            "L'insertion à un indice négatif échoue")
    void insererIndiceNegatif()
            throws XComptaCoreException {

        EcritureDeBrouillard ecriture =
                new EcritureDeBrouillard();

        MouvementDeBrouillard mouvement =
                mouvement(
                        "607",
                        "Achats",
                        "100.00");

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> ecriture.insererMouvementA(
                        -1,
                        mouvement));
    }

    @Test
    @DisplayName(
            "L'insertion au-delà de la taille de la liste échoue")
    void insererIndiceSuperieurTaille()
            throws XComptaCoreException {

        EcritureDeBrouillard ecriture =
                new EcritureDeBrouillard();

        MouvementDeBrouillard mouvement =
                mouvement(
                        "607",
                        "Achats",
                        "100.00");

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> ecriture.insererMouvementA(
                        1,
                        mouvement));
    }

    private MouvementDeBrouillard mouvement(
            String numeroCompte,
            String libelleCompte,
            String montant)
            throws XComptaCoreException {

        return new MouvementDeBrouillard(
                new BigDecimal(montant),
                new Compte(
                        numeroCompte,
                        libelleCompte),
                null);
    }
}
