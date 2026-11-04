package fr.xcompta.core.base.test.integration.ecrituredebrouillard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.xcompta.core.base.Compte;
import fr.xcompta.core.base.EcritureDeBrouillard;
import fr.xcompta.core.base.MouvementDeBrouillard;
import fr.xcompta.core.base.exception.XComptaCoreException;

/**
 * Tests d'intégration de l'ajout et de l'insertion
 * de mouvements dans une écriture de brouillard.
 *
 * <p>
 * Ces tests vérifient notamment l'ordre des mouvements
 * et leur rattachement à l'écriture.
 * </p>
 *
 * @author Fabrice Luneau
 */
class AjoutInsertionMouvement {

    @Test
    @DisplayName(
            "Un mouvement peut être ajouté au début de l'écriture")
    void ajouterAuDebut()
            throws XComptaCoreException {

        EcritureDeBrouillard ecriture =
                new EcritureDeBrouillard();

        MouvementDeBrouillard mouvement =
                mouvement(
                        "607",
                        "Achats",
                        "100.00");

        ecriture.ajouterMouvementAuDebut(
                mouvement);

        assertEquals(
                1,
                ecriture.getMouvements().size());

        assertSame(
                mouvement,
                ecriture.getMouvements().get(0));

        assertSame(
                ecriture,
                mouvement.getEcriture());
    }

    @Test
    @DisplayName(
            "Un mouvement peut être ajouté à la fin de l'écriture")
    void ajouterALaFin()
            throws XComptaCoreException {

        EcritureDeBrouillard ecriture =
                new EcritureDeBrouillard();

        MouvementDeBrouillard premier =
                mouvement(
                        "607",
                        "Achats",
                        "100.00");

        MouvementDeBrouillard second =
                mouvement(
                        "401",
                        "Fournisseurs",
                        "-100.00");

        ecriture.ajouterMouvementALaFin(
                premier);

        ecriture.ajouterMouvementALaFin(
                second);

        assertEquals(
                2,
                ecriture.getMouvements().size());

        assertSame(
                premier,
                ecriture.getMouvements().get(0));

        assertSame(
                second,
                ecriture.getMouvements().get(1));

        assertSame(
                ecriture,
                premier.getEcriture());

        assertSame(
                ecriture,
                second.getEcriture());
    }

    @Test
    @DisplayName(
            "Un mouvement peut être inséré à une position donnée")
    void insererMouvement()
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
