package fr.xcompta.core.base.test.integration.ecrituredebrouillard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.xcompta.core.base.Compte;
import fr.xcompta.core.base.EcritureDeBrouillard;
import fr.xcompta.core.base.Journal;
import fr.xcompta.core.base.MouvementDeBrouillard;
import fr.xcompta.core.base.exception.XComptaCoreException;

/**
 * Tests d'intégration de la contrepassation
 * d'une écriture de brouillard.
 *
 * <p>
 * La contrepassation doit produire une nouvelle écriture de brouillard
 * utilisant les mêmes comptes avec des montants de signes opposés.
 * L'écriture obtenue doit donc être l'inverse comptable de l'écriture
 * d'origine.
 * </p>
 *
 * @author Fabrice Luneau
 */
class Contrepassation {

    @Test
    @DisplayName(
            "Contrepasser un brouillard produit son inverse comptable")
    void contrepasserProduitInverse()
            throws XComptaCoreException {

        EcritureDeBrouillard source =
                creerEcriture();

        EcritureDeBrouillard contrepassation =
                source.contrepasser();

        assertTrue(
                source.estInverse(contrepassation));

        assertTrue(
                contrepassation.estInverse(source));

        assertFalse(
                source.estEquivalente(contrepassation));
    }

    @Test
    @DisplayName(
            "La contrepassation produit une nouvelle écriture")
    void contrepasserProduitNouvelleEcriture()
            throws XComptaCoreException {

        EcritureDeBrouillard source =
                creerEcriture();

        EcritureDeBrouillard contrepassation =
                source.contrepasser();

        assertFalse(
                source == contrepassation);
    }

    @Test
    @DisplayName(
            "La contrepassation conserve le nombre de mouvements")
    void contrepasserConserveNombreMouvements()
            throws XComptaCoreException {

        EcritureDeBrouillard source =
                creerEcriture();

        EcritureDeBrouillard contrepassation =
                source.contrepasser();

        assertEquals(
                source.size(),
                contrepassation.size());
    }

    @Test
    @DisplayName(
            "La contrepassation conserve les comptes")
    void contrepasserConserveComptes()
            throws XComptaCoreException {

        EcritureDeBrouillard source =
                creerEcriture();

        EcritureDeBrouillard contrepassation =
                source.contrepasser();

        for (int i = 0; i < source.size(); i++) {

            assertEquals(
                    source.getMouvement(i).getCompte(),
                    contrepassation
                            .getMouvement(i)
                            .getCompte());
        }
    }

    @Test
    @DisplayName(
            "La contrepassation inverse tous les montants")
    void contrepasserInverseMontants()
            throws XComptaCoreException {

        EcritureDeBrouillard source =
                creerEcriture();

        EcritureDeBrouillard contrepassation =
                source.contrepasser();

        for (int i = 0; i < source.size(); i++) {

            BigDecimal montantSource =
                    source.getMouvement(i)
                            .getMontant();

            BigDecimal montantContrepassation =
                    contrepassation
                            .getMouvement(i)
                            .getMontant();

            assertEquals(
                    0,
                    montantSource
                            .negate()
                            .compareTo(
                                    montantContrepassation));
        }
    }

    @Test
    @DisplayName(
            "Les mouvements de la contrepassation sont rattachés à la nouvelle écriture")
    void contrepasserRattacheMouvements()
            throws XComptaCoreException {

        EcritureDeBrouillard source =
                creerEcriture();

        EcritureDeBrouillard contrepassation =
                source.contrepasser();

        for (int i = 0;
                i < contrepassation.size();
                i++) {

            assertSame(
                    contrepassation,
                    contrepassation
                            .getMouvement(i)
                            .getEcriture());
        }
    }

    @Test
    @DisplayName(
            "La contrepassation conserve le journal")
    void contrepasserConserveJournal()
            throws XComptaCoreException {

        EcritureDeBrouillard source =
                creerEcriture();

        EcritureDeBrouillard contrepassation =
                source.contrepasser();

        assertSame(
                source.getJournal(),
                contrepassation.getJournal());
    }

    @Test
    @DisplayName(
            "Contrepasser deux fois restitue une écriture comptablement équivalente")
    void doubleContrepassation()
            throws XComptaCoreException {

        EcritureDeBrouillard source =
                creerEcriture();

        EcritureDeBrouillard premiere =
                source.contrepasser();

        EcritureDeBrouillard seconde =
                premiere.contrepasser();

        assertTrue(
                source.estEquivalente(seconde));

        assertFalse(
                source.estInverse(seconde));
    }

    private EcritureDeBrouillard creerEcriture()
            throws XComptaCoreException {

        Journal journal =
                new Journal(
                        "AC",
                        "Achats");

        List<MouvementDeBrouillard> mouvements =
                List.of(
                        mouvement(
                                "607",
                                "Achats de marchandises",
                                "1000.00"),

                        mouvement(
                                "44566",
                                "TVA déductible",
                                "200.00"),

                        mouvement(
                                "401",
                                "Fournisseurs",
                                "-1200.00"));

        return new EcritureDeBrouillard(
                        100, 1100,
                        "Achat de marchandises",
                        LocalDate.of(2026, 9, 17),
                        journal,
                        mouvements
                );
    }

    private MouvementDeBrouillard mouvement(
            String numeroCompte,
            String libelleCompte,
            String montant) {

        return new MouvementDeBrouillard(
                new Compte(
                        numeroCompte,
                        libelleCompte),
                new BigDecimal(montant));
    }
}
