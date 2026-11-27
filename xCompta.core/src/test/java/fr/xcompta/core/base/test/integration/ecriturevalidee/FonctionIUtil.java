package fr.xcompta.core.base.test.integration.ecriturevalidee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.xcompta.core.base.Compte;
import fr.xcompta.core.base.EcritureValidee;
import fr.xcompta.core.base.Journal;
import fr.xcompta.core.base.MouvementValidee;
import fr.xcompta.core.base.exception.XComptaCoreException;

/**
 * Tests d'intégration des fonctions de consultation
 * d'une écriture validée.
 *
 * <p>
 * Ces tests vérifient notamment le nombre de mouvements,
 * les totaux débit et crédit, le solde, l'équilibre
 * de l'écriture ainsi que l'accès aux mouvements.
 * </p>
 *
 * @author Fabrice Luneau
 */
class FonctionIUtil {

    private EcritureValidee ecriture;

    @BeforeEach
    void initialiser()
            throws XComptaCoreException {

        Compte compteAchat =
                new Compte(
                        "607",
                        "Achats");

        Compte compteTva =
                new Compte(
                        "44566",
                        "TVA déductible");

        Compte compteFournisseur =
                new Compte(
                        "401",
                        "Fournisseurs");

        Journal journal =
                new Journal(
                        "AC",
                        "Achats");

        MouvementValidee achat =
                new MouvementValidee(
                        1001,
                        new BigDecimal("1000.00"),
                        compteAchat);

        MouvementValidee tva =
                new MouvementValidee(
                        1002,
                        new BigDecimal("200.00"),
                        compteTva);

        MouvementValidee fournisseur =
                new MouvementValidee(
                        1003,
                        new BigDecimal("-1200.00"),
                        compteFournisseur);

        ecriture =
                new EcritureValidee(3545, 4545, 5545, "Achat fournisseur", LocalDate.of(
                                2026,
                                Month.AUGUST,
                                19), journal, List.of(
                                achat,
                                tva,
                                fournisseur));
    }

    @Test
    @DisplayName(
            "Une écriture validée retourne son nombre de mouvements")
    void nombreMouvements() {

        assertEquals(
                3,
                ecriture.getMouvements().size());
    }

    @Test
    @DisplayName(
            "Le total débit est correctement calculé")
    void totalDebit() {

        assertBigDecimalEquals(
                new BigDecimal("1200.00"),
                ecriture.getTotalDebit());
    }

    @Test
    @DisplayName(
            "Le total crédit est correctement calculé")
    void totalCredit() {

        assertBigDecimalEquals(
                new BigDecimal("-1200.00"),
                ecriture.getTotalCredit());
    }

    @Test
    @DisplayName(
            "Le solde d'une écriture équilibrée est nul")
    void soldeNul() {

        assertBigDecimalEquals(
                BigDecimal.ZERO,
                ecriture.getSolde());
    }

    @Test
    @DisplayName(
            "Une écriture validée équilibrée est reconnue comme équilibrée")
    void ecritureEquilibree() {

        assertTrue(
                ecriture.estEquilibree());
    }

    @Test
    @DisplayName(
            "Les mouvements sont accessibles dans leur ordre")
    void obtenirMouvementsParIndex() {

        assertEquals(
                "607",
                ecriture.getMouvements()
                        .get(0)
                        .getCompte()
                        .getNumero());

        assertEquals(
                "44566",
                ecriture.getMouvements()
                        .get(1)
                        .getCompte()
                        .getNumero());

        assertEquals(
                "401",
                ecriture.getMouvements()
                        .get(2)
                        .getCompte()
                        .getNumero());
    }

    @Test
    @DisplayName(
            "Les mouvements sont rattachés à leur écriture validée")
    void mouvementsRattaches() {

        for (MouvementValidee mouvement :
                ecriture.getMouvements()) {

            assertSame(
                    ecriture,
                    mouvement.getEcriture());
        }
    }

    private void assertBigDecimalEquals(
            BigDecimal attendu,
            BigDecimal obtenu) {

        assertEquals(
                0,
                attendu.compareTo(obtenu));
    }
}