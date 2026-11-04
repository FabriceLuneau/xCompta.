package fr.xcompta.core.base.test.integration.ecriture;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.xcompta.core.base.Compte;
import fr.xcompta.core.base.EcritureDeBrouillard;
import fr.xcompta.core.base.EcritureValidee;
import fr.xcompta.core.base.Journal;
import fr.xcompta.core.base.MouvementDeBrouillard;
import fr.xcompta.core.base.MouvementValidee;
import fr.xcompta.core.base.exception.XComptaCoreException;
import fr.xcompta.core.base.exception.XComptaCoreNullPointerException;
import fr.xcompta.core.base.exception.mouvement.MouvementException;

/**
 * Tests d'intégration transversaux de la méthode estInverse().
 *
 * <p>
 * Deux écritures sont inverses lorsque les mêmes comptes sont utilisés
 * avec des montants de signes opposés. L'ordre des mouvements et le type
 * concret de l'écriture ne doivent pas modifier ce résultat.
 * </p>
 *
 * @author Fabrice Luneau
 */
class EstInverse {

    private static final LocalDate DATE =
            LocalDate.of(2026, 9, 17);

    @Test
    @DisplayName(
            "Deux brouillards aux mouvements opposés "
            + "sont inverses")
    void deuxBrouillardsInverses()
            throws XComptaCoreException {

        EcritureDeBrouillard ecriture =
                creerBrouillard(false);

        EcritureDeBrouillard inverse =
                creerBrouillard(true);

        assertTrue(
                ecriture.estInverse(inverse));

        assertTrue(
                inverse.estInverse(ecriture));
    }

    @Test
    @DisplayName(
            "Deux écritures validées aux mouvements opposés "
            + "sont inverses")
    void deuxValideesInverses()
            throws XComptaCoreException {

        EcritureValidee ecriture =
                creerValidee(100, false, false);

        EcritureValidee inverse =
                creerValidee(200, true, false);

        assertTrue(
                ecriture.estInverse(inverse));

        assertTrue(
                inverse.estInverse(ecriture));
    }

    @Test
    @DisplayName(
            "Un brouillard et une validée peuvent "
            + "être inverses")
    void brouillardEtValideeInverses()
            throws XComptaCoreException {

        EcritureDeBrouillard brouillard =
                creerBrouillard(false);

        EcritureValidee inverse =
                creerValidee(200, true, false);

        assertTrue(
                brouillard.estInverse(inverse));

        assertTrue(
                inverse.estInverse(brouillard));
    }

    @Test
    @DisplayName(
            "L'ordre des mouvements n'intervient pas "
            + "dans la détection de l'inverse")
    void inverseIndependantOrdre()
            throws XComptaCoreException {

        EcritureDeBrouillard ecriture =
                creerBrouillard(false);

        EcritureValidee inverse =
                creerValidee(200, true, true);

        assertTrue(
                ecriture.estInverse(inverse));

        assertTrue(
                inverse.estInverse(ecriture));
    }

    @Test
    @DisplayName(
            "Deux écritures équivalentes "
            + "ne sont pas inverses")
    void ecrituresEquivalentesNonInverses()
            throws XComptaCoreException {

        EcritureDeBrouillard brouillard =
                creerBrouillard(false);

        EcritureValidee validee =
                creerValidee(200, false, true);

        assertFalse(
                brouillard.estInverse(validee));

        assertFalse(
                validee.estInverse(brouillard));
    }

    @Test
    @DisplayName(
            "Un montant différent empêche "
            + "la reconnaissance de l'inverse")
    void montantDifferent()
            throws XComptaCoreException {

        EcritureDeBrouillard ecriture =
                creerBrouillard(false);

        Journal journal =
                new Journal("AC", "Achats");

        EcritureValidee autre =
                new EcritureValidee(200, "Autre écriture", DATE, journal, List.of(
                                mouvementValide(
                                        11,
                                        "607",
                                        "-1001.00"),
                                mouvementValide(
                                        12,
                                        "44566",
                                        "-200.00"),
                                mouvementValide(
                                        13,
                                        "401",
                                        "1201.00")));

        assertFalse(
                ecriture.estInverse(autre));

        assertFalse(
                autre.estInverse(ecriture));
    }

    @Test
    @DisplayName(
            "Un compte différent empêche "
            + "la reconnaissance de l'inverse")
    void compteDifferent()
            throws XComptaCoreException {

        EcritureDeBrouillard ecriture =
                creerBrouillard(false);

        Journal journal =
                new Journal("AC", "Achats");

        EcritureValidee autre =
                new EcritureValidee(200, "Autre écriture", DATE, journal, List.of(
                                mouvementValide(
                                        11,
                                        "606",
                                        "-1000.00"),
                                mouvementValide(
                                        12,
                                        "44566",
                                        "-200.00"),
                                mouvementValide(
                                        13,
                                        "401",
                                        "1200.00")));

        assertFalse(
                ecriture.estInverse(autre));

        assertFalse(
                autre.estInverse(ecriture));
    }

    @Test
    @DisplayName(
            "Une écriture n'est pas inverse de null")
    void comparaisonAvecNull()
            throws XComptaCoreException {

        EcritureDeBrouillard ecriture =
                creerBrouillard(false);

        assertFalse(
                ecriture.estInverse(null));
    }

    /**
     * Construit une écriture de brouillard.
     *
     * @param inverse true pour inverser tous les montants
     * @return écriture construite
     */
    private EcritureDeBrouillard creerBrouillard(
            boolean inverse)
            throws XComptaCoreException {

        BigDecimal coefficient =
                inverse
                        ? BigDecimal.ONE.negate()
                        : BigDecimal.ONE;

        Journal journal =
                new Journal("AC", "Achats");

        return new EcritureDeBrouillard(
                        100,
                        inverse
                        ? "Contrepassation"
                        : "Achat de marchandises",
                        DATE,
                        journal,
                        List.of(
                        mouvementBrouillard(
                                "607",
                                new BigDecimal("1000.00")
                                        .multiply(coefficient)),
                        mouvementBrouillard(
                                "44566",
                                new BigDecimal("200.00")
                                        .multiply(coefficient)),
                        mouvementBrouillard(
                                "401",
                                new BigDecimal("-1200.00")
                                        .multiply(coefficient)))
                );
    }

    /**
     * Construit une écriture validée.
     *
     * @param id identifiant de l'écriture
     * @param inverse true pour inverser les montants
     * @param ordreInverse true pour inverser l'ordre des mouvements
     * @return écriture construite
     */
    private EcritureValidee creerValidee(
            int id,
            boolean inverse,
            boolean ordreInverse)
            throws XComptaCoreException {

        BigDecimal coefficient =
                inverse
                        ? BigDecimal.ONE.negate()
                        : BigDecimal.ONE;

        MouvementValidee achat =
                mouvementValide(
                        1,
                        "607",
                        new BigDecimal("1000.00")
                                .multiply(coefficient));

        MouvementValidee tva =
                mouvementValide(
                        2,
                        "44566",
                        new BigDecimal("200.00")
                                .multiply(coefficient));

        MouvementValidee fournisseur =
                mouvementValide(
                        3,
                        "401",
                        new BigDecimal("-1200.00")
                                .multiply(coefficient));

        List<MouvementValidee> mouvements =
                ordreInverse
                        ? List.of(
                                fournisseur,
                                tva,
                                achat)
                        : List.of(
                                achat,
                                tva,
                                fournisseur);

        return new EcritureValidee(id, inverse
                        ? "Contrepassation"
                        : "Achat de marchandises", DATE, new Journal("AC", "Achats"), mouvements);
    }

    /**
     * Construit un mouvement de brouillard détaché.
     *
     * @param compte numéro de compte
     * @param montant montant signé
     * @return mouvement construit
     */
    private MouvementDeBrouillard mouvementBrouillard(
            String compte,
            BigDecimal montant) {

        return new MouvementDeBrouillard(
                new Compte(
                        compte,
                        "Compte " + compte),
                montant);
    }

    /**
     * Construit un mouvement validé détaché.
     *
     * @param id identifiant du mouvement
     * @param compte numéro de compte
     * @param montant montant signé
     * @return mouvement construit
     * @throws MouvementException 
     * @throws XComptaCoreNullPointerException 
     */
    private MouvementValidee mouvementValide(
            int id,
            String compte,
            String montant) throws MouvementException, XComptaCoreNullPointerException {

        return mouvementValide(
                id,
                compte,
                new BigDecimal(montant));
    }

    /**
     * Construit un mouvement validé détaché.
     *
     * @param id identifiant du mouvement
     * @param compte numéro de compte
     * @param montant montant signé
     * @return mouvement construit
     * @throws MouvementException 
     * @throws XComptaCoreNullPointerException 
     */
    private MouvementValidee mouvementValide(
            int id,
            String compte,
            BigDecimal montant) throws MouvementException, XComptaCoreNullPointerException {

        return new MouvementValidee(
                id,
                montant,
                new Compte(
                        compte,
                        "Compte " + compte));
    }
}