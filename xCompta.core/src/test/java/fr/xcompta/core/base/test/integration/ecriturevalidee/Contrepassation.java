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
import fr.xcompta.core.base.EcritureDeBrouillard;
import fr.xcompta.core.base.EcritureValidee;
import fr.xcompta.core.base.Journal;
import fr.xcompta.core.base.MouvementDeBrouillard;
import fr.xcompta.core.base.MouvementValidee;
import fr.xcompta.core.base.exception.XComptaCoreException;

/**
 * Tests d'intégration de la contrepassation
 * d'une écriture validée.
 *
 * <p>
 * La contrepassation d'une écriture validée produit une écriture
 * de brouillard. Celle-ci doit utiliser les mêmes comptes avec
 * des montants de signes opposés.
 * </p>
 *
 * @author Fabrice Luneau
 */
class Contrepassation {

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
                new EcritureValidee(3545, "Achat fournisseur", LocalDate.of(
                                2026,
                                Month.AUGUST,
                                19), journal, List.of(
                                achat,
                                tva,
                                fournisseur));
    }

    @Test
    @DisplayName(
            "Contrepasser une écriture validée produit son inverse")
    void contrepasserProduitInverse()
            throws XComptaCoreException {

        EcritureDeBrouillard contrepassation =
                ecriture.contrepasser();

        assertTrue(
                ecriture.estInverse(
                        contrepassation));

        assertTrue(
                contrepassation.estInverse(
                        ecriture));
    }

    @Test
    @DisplayName(
            "La contrepassation conserve le nombre de mouvements")
    void contrepasserConserveNombreMouvements()
            throws XComptaCoreException {

        EcritureDeBrouillard contrepassation =
                ecriture.contrepasser();

        assertEquals(
                ecriture.getMouvements().size(),
                contrepassation.getMouvements().size());
    }

    @Test
    @DisplayName(
            "La contrepassation inverse les montants des mouvements")
    void contrepasserInverseMontants()
            throws XComptaCoreException {

        EcritureDeBrouillard contrepassation =
                ecriture.contrepasser();

        for (int i = 0;
                i < ecriture.getMouvements().size();
                i++) {

            MouvementValidee original =
                    ecriture.getMouvements().get(i);

            MouvementDeBrouillard inverse =
                    contrepassation.getMouvements().get(i);

            assertEquals(
                    0,
                    original.getMontant()
                            .negate()
                            .compareTo(
                                    inverse.getMontant()));
        }
    }

    @Test
    @DisplayName(
            "Les mouvements contrepassés sont rattachés au brouillard")
    void contrepasserRattacheMouvements()
            throws XComptaCoreException {

        EcritureDeBrouillard contrepassation =
                ecriture.contrepasser();

        for (MouvementDeBrouillard mouvement :
                contrepassation.getMouvements()) {

            assertSame(
                    contrepassation,
                    mouvement.getEcriture());
        }
    }

    @Test
    @DisplayName(
            "La contrepassation conserve le journal")
    void contrepasserConserveJournal()
            throws XComptaCoreException {

        EcritureDeBrouillard contrepassation =
                ecriture.contrepasser();

        assertSame(
                ecriture.getJournal(),
                contrepassation.getJournal());
    }
}