package fr.xcompta.core.base.test.integration.ecrituredebrouillard;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.xcompta.core.base.Compte;
import fr.xcompta.core.base.EcritureDeBrouillard;
import fr.xcompta.core.base.Journal;
import fr.xcompta.core.base.MouvementDeBrouillard;
import fr.xcompta.core.base.exception.XComptaCoreException;

/**
 * Tests d'intégration de la validité progressive
 * d'une écriture de brouillard.
 *
 * <p>
 * Une écriture de brouillard peut être temporairement incomplète
 * pendant sa saisie. Ces tests vérifient que son état de validité
 * évolue correctement au fur et à mesure de sa construction.
 * </p>
 *
 * @author Fabrice Luneau
 */
class ValidationProgressive {

    @Test
    @DisplayName(
            "Une écriture vide n'est pas valide")
    void ecritureVideNonValide() throws XComptaCoreException {

        EcritureDeBrouillard ecriture =
                new EcritureDeBrouillard();

        assertFalse(
                ecriture.estValide());
    }

    @Test
    @DisplayName(
            "Une écriture avec seulement son en-tête n'est pas valide")
    void enteteSeulNonValide()
            throws XComptaCoreException {

        EcritureDeBrouillard ecriture =
                creerEcritureAvecEntete();

        assertFalse(
                ecriture.estValide());
    }

    @Test
    @DisplayName(
            "Une écriture avec un seul mouvement n'est pas valide")
    void unMouvementNonValide()
            throws XComptaCoreException {

        EcritureDeBrouillard ecriture =
                creerEcritureAvecEntete();

        ecriture.ajouterMouvementALaFin(
                mouvement(
                        "607",
                        "Achats",
                        "100.00"));

        assertFalse(
                ecriture.estValide());
    }

    @Test
    @DisplayName(
            "Une écriture non équilibrée n'est pas valide")
    void ecritureNonEquilibree()
            throws XComptaCoreException {

        EcritureDeBrouillard ecriture =
                creerEcritureAvecEntete();

        ecriture.ajouterMouvementALaFin(
                mouvement(
                        "607",
                        "Achats",
                        "100.00"));

        ecriture.ajouterMouvementALaFin(
                mouvement(
                        "401",
                        "Fournisseurs",
                        "-90.00"));

        assertFalse(
                ecriture.estValide());
    }

    @Test
    @DisplayName(
            "Une écriture devient valide lorsqu'elle est complète et équilibrée")
    void ecritureDevientValide()
            throws XComptaCoreException {

        EcritureDeBrouillard ecriture =
                creerEcritureAvecEntete();

        ecriture.ajouterMouvementALaFin(
                mouvement(
                        "607",
                        "Achats",
                        "100.00"));

        assertFalse(
                ecriture.estValide());

        ecriture.ajouterMouvementALaFin(
                mouvement(
                        "401",
                        "Fournisseurs",
                        "-100.00"));

        assertTrue(
                ecriture.estValide());
    }

    @Test
    @DisplayName(
            "Un mouvement vide temporaire n'empêche pas la saisie d'une écriture valide")
    void mouvementVideTolerePendantSaisie()
            throws XComptaCoreException {

        EcritureDeBrouillard ecriture =
                creerEcritureAvecEntete();

        ecriture.ajouterMouvementALaFin(
                mouvement(
                        "607",
                        "Achats",
                        "100.00"));

        ecriture.ajouterMouvementALaFin(
                new MouvementDeBrouillard());

        ecriture.ajouterMouvementALaFin(
                mouvement(
                        "401",
                        "Fournisseurs",
                        "-100.00"));

        ecriture.supprimerMouvementsVides();

        assertTrue(
                ecriture.estValide());
    }

    @Test
    @DisplayName(
            "La suppression d'un mouvement nécessaire rend l'écriture invalide")
    void suppressionMouvementRendEcritureInvalide()
            throws XComptaCoreException {

        EcritureDeBrouillard ecriture =
                creerEcritureAvecEntete();

        MouvementDeBrouillard debit =
                mouvement(
                        "607",
                        "Achats",
                        "100.00");

        MouvementDeBrouillard credit =
                mouvement(
                        "401",
                        "Fournisseurs",
                        "-100.00");

        ecriture.ajouterMouvementALaFin(
                debit);

        ecriture.ajouterMouvementALaFin(
                credit);

        assertTrue(
                ecriture.estValide());

        ecriture.supprimerMouvement(
                credit);

        assertFalse(
                ecriture.estValide());
    }

    private EcritureDeBrouillard creerEcritureAvecEntete()
            throws XComptaCoreException {

        EcritureDeBrouillard ecriture =
                new EcritureDeBrouillard();

        ecriture.setDate(
                LocalDate.of(
                        2026,
                        Month.AUGUST,
                        19));

        ecriture.setJournal(
                new Journal(
                        "AC",
                        "Achats"));

        ecriture.setLibelle(
                "Achat fournisseur");

        return ecriture;
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