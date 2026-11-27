package fr.xcompta.core.base.test.integration.ecriturevalidee;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.xcompta.core.base.Compte;
import fr.xcompta.core.base.EcritureValidee;
import fr.xcompta.core.base.Journal;
import fr.xcompta.core.base.MouvementValidee;
import fr.xcompta.core.base.exception.XComptaCoreException;

/**
 * Tests d'intégration de la détection de l'inverse comptable
 * entre deux écritures validées.
 *
 * <p>
 * Deux écritures sont inverses lorsque leurs mouvements utilisent
 * les mêmes comptes avec des montants de signes opposés.
 * L'ordre des mouvements ne doit pas intervenir dans cette comparaison.
 * </p>
 *
 * @author Fabrice Luneau
 */
class EstInverse {

    @Test
    @DisplayName(
            "Deux écritures avec les mêmes comptes et des montants opposés sont inverses")
    void ecrituresInverses()
            throws XComptaCoreException {

        EcritureValidee ecriture = creerEcritureNormale();
        EcritureValidee inverse = creerEcritureInverse();

        assertTrue(ecriture.estInverse(inverse));
        assertTrue(inverse.estInverse(ecriture));
    }

    @Test
    @DisplayName(
            "L'ordre des mouvements n'intervient pas dans la détection de l'inverse")
    void inverseOrdreDifferent()
            throws XComptaCoreException {

        EcritureValidee ecriture = creerEcritureNormale();

        EcritureValidee inverse =
                creerEcriture(
                        201,
                        List.of(
                                mouvement(
                                        11,
                                        "401",
                                        "Fournisseurs",
                                        new BigDecimal("1200.00")),
                                mouvement(
                                        12,
                                        "44566",
                                        "TVA déductible",
                                        new BigDecimal("-200.00")),
                                mouvement(
                                        13,
                                        "607",
                                        "Achats de marchandises",
                                        new BigDecimal("-1000.00"))));

        assertTrue(ecriture.estInverse(inverse));
        assertTrue(inverse.estInverse(ecriture));
    }

    @Test
    @DisplayName(
            "Deux écritures identiques ne sont pas inverses")
    void ecrituresIdentiquesNonInverses()
            throws XComptaCoreException {

        EcritureValidee ecriture1 =
                creerEcritureNormale();

        EcritureValidee ecriture2 =
                creerEcritureNormaleAvecAutreId();

        assertFalse(ecriture1.estInverse(ecriture2));
        assertFalse(ecriture2.estInverse(ecriture1));
    }

    @Test
    @DisplayName(
            "Un montant différent empêche deux écritures d'être inverses")
    void montantDifferent()
            throws XComptaCoreException {

        EcritureValidee ecriture =
                creerEcritureNormale();

        EcritureValidee autre =
                creerEcriture(
                        202,
                        List.of(
                                mouvement(
                                        21,
                                        "607",
                                        "Achats de marchandises",
                                        new BigDecimal("-1001.00")),
                                mouvement(
                                        22,
                                        "44566",
                                        "TVA déductible",
                                        new BigDecimal("-199.00")),
                                mouvement(
                                        23,
                                        "401",
                                        "Fournisseurs",
                                        new BigDecimal("1200.00"))));

        assertFalse(ecriture.estInverse(autre));
        assertFalse(autre.estInverse(ecriture));
    }

    @Test
    @DisplayName(
            "Un compte différent empêche deux écritures d'être inverses")
    void compteDifferent()
            throws XComptaCoreException {

        EcritureValidee ecriture =
                creerEcritureNormale();

        EcritureValidee autre =
                creerEcriture(
                        203,
                        List.of(
                                mouvement(
                                        31,
                                        "606",
                                        "Achats non stockés",
                                        new BigDecimal("-1000.00")),
                                mouvement(
                                        32,
                                        "44566",
                                        "TVA déductible",
                                        new BigDecimal("-200.00")),
                                mouvement(
                                        33,
                                        "401",
                                        "Fournisseurs",
                                        new BigDecimal("1200.00"))));

        assertFalse(ecriture.estInverse(autre));
        assertFalse(autre.estInverse(ecriture));
    }

    @Test
    @DisplayName(
            "Une écriture n'est pas inverse de null")
    void inverseNull()
            throws XComptaCoreException {

        EcritureValidee ecriture =
                creerEcritureNormale();

        assertFalse(ecriture.estInverse(null));
    }

    private EcritureValidee creerEcritureNormale()
            throws XComptaCoreException {

        return creerEcriture(
                200,
                List.of(
                        mouvement(
                                1,
                                "607",
                                "Achats de marchandises",
                                new BigDecimal("1000.00")),
                        mouvement(
                                2,
                                "44566",
                                "TVA déductible",
                                new BigDecimal("200.00")),
                        mouvement(
                                3,
                                "401",
                                "Fournisseurs",
                                new BigDecimal("-1200.00"))));
    }

    private EcritureValidee creerEcritureNormaleAvecAutreId()
            throws XComptaCoreException {

        return creerEcriture(
                204,
                List.of(
                        mouvement(
                                41,
                                "607",
                                "Achats de marchandises",
                                new BigDecimal("1000.00")),
                        mouvement(
                                42,
                                "44566",
                                "TVA déductible",
                                new BigDecimal("200.00")),
                        mouvement(
                                43,
                                "401",
                                "Fournisseurs",
                                new BigDecimal("-1200.00"))));
    }

    private EcritureValidee creerEcritureInverse()
            throws XComptaCoreException {

        return creerEcriture(
                201,
                List.of(
                        mouvement(
                                11,
                                "607",
                                "Achats de marchandises",
                                new BigDecimal("-1000.00")),
                        mouvement(
                                12,
                                "44566",
                                "TVA déductible",
                                new BigDecimal("-200.00")),
                        mouvement(
                                13,
                                "401",
                                "Fournisseurs",
                                new BigDecimal("1200.00"))));
    }

    private EcritureValidee creerEcriture(
            int id,
            List<MouvementValidee> mouvements)
            throws XComptaCoreException {

        return new EcritureValidee(id, (id == 0 ? 0 : id + 1000), (id <= 0 ? 0 : id + 2000), "Achat de marchandises", LocalDate.of(2026, 9, 17), new Journal(
                        "AC",
                        "Achats"), mouvements);
    }

    private MouvementValidee mouvement(
            int id,
            String numeroCompte,
            String libelleCompte,
            BigDecimal montant)
            throws XComptaCoreException {

        return new MouvementValidee(
                id,
                montant,
                new Compte(
                        numeroCompte,
                        libelleCompte));
    }
}
