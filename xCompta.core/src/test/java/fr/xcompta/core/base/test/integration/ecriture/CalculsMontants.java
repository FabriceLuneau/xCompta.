package fr.xcompta.core.base.test.integration.ecriture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
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

/**
 * Tests d'intégration des fonctions communes de consultation et de calcul
 * définies dans Ecriture.
 *
 * <p>
 * Ces tests sont transversaux : les mêmes comportements sont vérifiés
 * avec une EcritureDeBrouillard et une EcritureValidee.
 * </p>
 *
 * @author Fabrice Luneau
 */
class CalculsMontants {

    @Test
    @DisplayName(
            "Les calculs comptables donnent le même résultat "
            + "sur brouillard et validée")
    void calculsComptablesSurLesDeuxTypes()
            throws XComptaCoreException {

        EcritureDeBrouillard brouillard = creerBrouillard();
        EcritureValidee validee = creerValidee();

        verifierCalculs(
                brouillard.size(),
                brouillard.isEmpty(),
                brouillard.getTotalDebit(),
                brouillard.getTotalCredit(),
                brouillard.getSolde(),
                brouillard.estEquilibree());

        verifierCalculs(
                validee.size(),
                validee.isEmpty(),
                validee.getTotalDebit(),
                validee.getTotalCredit(),
                validee.getSolde(),
                validee.estEquilibree());
    }

    @Test
    @DisplayName(
            "Une nouvelle écriture de brouillard est vide "
            + "et ses totaux sont nuls")
    void calculsSurBrouillardVide()
            throws XComptaCoreException {

        EcritureDeBrouillard brouillard =
                new EcritureDeBrouillard();

        assertTrue(brouillard.isEmpty());
        assertEquals(0, brouillard.size());

        assertEquals(
                0,
                BigDecimal.ZERO.compareTo(
                        brouillard.getTotalDebit()));

        assertEquals(
                0,
                BigDecimal.ZERO.compareTo(
                        brouillard.getTotalCredit()));

        assertEquals(
                0,
                BigDecimal.ZERO.compareTo(
                        brouillard.getSolde()));

        assertTrue(brouillard.estEquilibree());
    }

    @Test
    @DisplayName(
            "L'itérateur parcourt les mouvements "
            + "sans permettre leur suppression")
    void iterateurNonModifiable()
            throws XComptaCoreException {

        EcritureDeBrouillard brouillard =
                creerBrouillard();

        var iterator = brouillard.iterator();

        assertTrue(iterator.hasNext());

        iterator.next();

        org.junit.jupiter.api.Assertions.assertThrows(
                UnsupportedOperationException.class,
                iterator::remove);
    }

    /**
     * Vérifie les résultats attendus des fonctions de calcul
     * pour le jeu de données comptable utilisé par ce test.
     *
     * @param taille nombre de mouvements
     * @param vide indique si l'écriture est vide
     * @param debit total des débits
     * @param credit total des crédits
     * @param solde solde de l'écriture
     * @param equilibree indique si l'écriture est équilibrée
     */
    private void verifierCalculs(
            int taille,
            boolean vide,
            BigDecimal debit,
            BigDecimal credit,
            BigDecimal solde,
            boolean equilibree) {

        assertEquals(3, taille);
        assertFalse(vide);

        assertEquals(
                0,
                new BigDecimal("1200.00")
                        .compareTo(debit));

        assertEquals(
                0,
                new BigDecimal("-1200.00")
                        .compareTo(credit));

        assertEquals(
                0,
                BigDecimal.ZERO.compareTo(solde));

        assertTrue(equilibree);
    }

    /**
     * Construit une écriture de brouillard équilibrée.
     *
     * @return écriture de brouillard
     * @throws XComptaCoreException en cas d'erreur métier
     */
    private EcritureDeBrouillard creerBrouillard()
            throws XComptaCoreException {

        Journal journal =
                new Journal("AC", "Achats");

        List<MouvementDeBrouillard> mouvements =
                new ArrayList<>();

        mouvements.add(
                new MouvementDeBrouillard(
                        new Compte(
                                "607",
                                "Achats de marchandises"),
                        new BigDecimal("1000.00")));

        mouvements.add(
                new MouvementDeBrouillard(
                        new Compte(
                                "44566",
                                "TVA déductible"),
                        new BigDecimal("200.00")));

        mouvements.add(
                new MouvementDeBrouillard(
                        new Compte(
                                "401",
                                "Fournisseurs"),
                        new BigDecimal("-1200.00")));

        return new EcritureDeBrouillard(
                        100, 1100,
                        JeuDonneesEcriture.LIBELLE,
                        JeuDonneesEcriture.DATE,
                        journal,
                        mouvements
                );
    }

    /**
     * Construit une écriture validée équilibrée représentant
     * le même contenu comptable que le brouillard.
     *
     * @return écriture validée
     * @throws XComptaCoreException en cas d'erreur métier
     */
    private EcritureValidee creerValidee()
            throws XComptaCoreException {

        Journal journal =
                new Journal("AC", "Achats");

        List<MouvementValidee> mouvements =
                List.of(
                        new MouvementValidee(
                                1,
                                new BigDecimal("1000.00"),
                                new Compte(
                                        "607",
                                        "Achats de marchandises")),

                        new MouvementValidee(
                                2,
                                new BigDecimal("200.00"),
                                new Compte(
                                        "44566",
                                        "TVA déductible")),

                        new MouvementValidee(
                                3,
                                new BigDecimal("-1200.00"),
                                new Compte(
                                        "401",
                                        "Fournisseurs")));

        return new EcritureValidee(200, 1200, 2200, JeuDonneesEcriture.LIBELLE, JeuDonneesEcriture.DATE, journal, mouvements);
    }
}
