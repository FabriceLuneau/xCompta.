package fr.xcompta.core.base.test.integration.ecrituredebrouillard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.xcompta.core.base.Compte;
import fr.xcompta.core.base.EcritureDeBrouillard;
import fr.xcompta.core.base.Journal;
import fr.xcompta.core.base.MouvementDeBrouillard;
import fr.xcompta.core.base.exception.XComptaCoreException;

/**
 * Tests d'intégration de la modification des mouvements
 * d'une écriture de brouillard.
 *
 * <p>
 * Ces tests vérifient qu'un mouvement peut être modifié
 * pendant la saisie et que ces modifications sont immédiatement
 * prises en compte dans la validité de l'écriture.
 * </p>
 *
 * @author Fabrice Luneau
 */
class MutationMouvement {

    private EcritureDeBrouillard ecriture;
    private MouvementDeBrouillard debit;
    private MouvementDeBrouillard credit;

    @BeforeEach
    void initialiser()
            throws XComptaCoreException {

        ecriture = new EcritureDeBrouillard();

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

        debit = mouvement(
                "607",
                "Achats",
                "100.00");

        credit = mouvement(
                "401",
                "Fournisseurs",
                "-100.00");

        ecriture.ajouterMouvementALaFin(
                debit);

        ecriture.ajouterMouvementALaFin(
                credit);
    }

    @Test
    @DisplayName(
            "Le montant d'un mouvement de brouillard peut être modifié")
    void modifierMontant()
            throws XComptaCoreException {

        debit.setMontant(
                new BigDecimal("120.00"));

        assertBigDecimalEquals(
                new BigDecimal("120.00"),
                debit.getMontant());

        assertFalse(
                ecriture.estValide());
    }

    @Test
    @DisplayName(
            "La correction du montant permet de retrouver une écriture valide")
    void corrigerMontant()
            throws XComptaCoreException {

        debit.setMontant(
                new BigDecimal("120.00"));

        assertFalse(
                ecriture.estValide());

        debit.setMontant(
                new BigDecimal("100.00"));

        assertTrue(
                ecriture.estValide());
    }

    @Test
    @DisplayName(
            "Le compte d'un mouvement de brouillard peut être modifié")
    void modifierCompte()
            throws XComptaCoreException {

        Compte nouveauCompte =
                new Compte(
                        "606",
                        "Achats non stockés");

        debit.setCompte(
                nouveauCompte);

        assertSame(
                nouveauCompte,
                debit.getCompte());

        assertTrue(
                ecriture.estValide());
    }

    @Test
    @DisplayName(
            "L'utilisation du même compte deux fois rend l'écriture invalide")
    void compteDupliqueRendEcritureInvalide()
            throws XComptaCoreException {

        debit.setCompte(
                credit.getCompte());

        assertFalse(
                ecriture.estValide());
    }

    @Test
    @DisplayName(
            "La correction d'un compte dupliqué rétablit la validité")
    void corrigerCompteDuplique()
            throws XComptaCoreException {

        debit.setCompte(
                credit.getCompte());

        assertFalse(
                ecriture.estValide());

        debit.setCompte(
                new Compte(
                        "607",
                        "Achats"));

        assertTrue(
                ecriture.estValide());
    }

    @Test
    @DisplayName(
            "La modification d'un mouvement conserve son rattachement")
    void rattachementConserve()
            throws XComptaCoreException {

        debit.setMontant(
                new BigDecimal("120.00"));

        assertSame(
                ecriture,
                debit.getEcriture());
    }

    @Test
    @DisplayName(
            "La modification d'un mouvement ne modifie pas les autres")
    void autresMouvementsNonModifies()
            throws XComptaCoreException {

        BigDecimal montantCredit =
                credit.getMontant();

        Compte compteCredit =
                credit.getCompte();

        debit.setMontant(
                new BigDecimal("120.00"));

        debit.setCompte(
                new Compte(
                        "606",
                        "Achats non stockés"));

        assertBigDecimalEquals(
                montantCredit,
                credit.getMontant());

        assertSame(
                compteCredit,
                credit.getCompte());

        assertEquals(
                "401",
                credit.getCompte().getNumero());
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

    private void assertBigDecimalEquals(
            BigDecimal attendu,
            BigDecimal obtenu) {

        assertEquals(
                0,
                attendu.compareTo(obtenu));
    }
}