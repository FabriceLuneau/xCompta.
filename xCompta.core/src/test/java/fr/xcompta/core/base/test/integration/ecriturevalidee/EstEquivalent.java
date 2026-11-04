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
 * Tests d'intégration de l'équivalence comptable
 * entre deux écritures validées.
 *
 * <p>
 * Deux écritures sont considérées comme équivalentes lorsque
 * leurs mouvements utilisent les mêmes comptes avec les mêmes
 * montants signés.
 * </p>
 *
 * <p>
 * L'ordre des mouvements ainsi que les informations techniques
 * telles que les identifiants ne doivent pas intervenir dans
 * l'équivalence comptable.
 * </p>
 *
 * @author Fabrice Luneau
 */
class EstEquivalent {

    @Test
    @DisplayName(
            "Deux écritures ayant le même contenu comptable sont équivalentes")
    void memeContenuComptable()
            throws XComptaCoreException {

        EcritureValidee ecriture1 =
                creerEcritureNormale(200);

        EcritureValidee ecriture2 =
                creerEcritureNormale(201);

        assertTrue(
                ecriture1.estEquivalente(ecriture2));

        assertTrue(
                ecriture2.estEquivalente(ecriture1));
    }

    @Test
    @DisplayName(
            "L'ordre des mouvements n'intervient pas dans l'équivalence")
    void ordreMouvementsDifferent()
            throws XComptaCoreException {

        EcritureValidee ecriture1 =
                creerEcritureNormale(200);

        EcritureValidee ecriture2 =
                creerEcriture(
                        201,
                        "Autre libellé",
                        List.of(
                                mouvement(
                                        11,
                                        "401",
                                        "Fournisseurs",
                                        "-1200.00"),
                                mouvement(
                                        12,
                                        "607",
                                        "Achats de marchandises",
                                        "1000.00"),
                                mouvement(
                                        13,
                                        "44566",
                                        "TVA déductible",
                                        "200.00")));

        assertTrue(
                ecriture1.estEquivalente(ecriture2));

        assertTrue(
                ecriture2.estEquivalente(ecriture1));
    }

    @Test
    @DisplayName(
            "Les identifiants des écritures et mouvements n'interviennent pas")
    void identifiantsDifferents()
            throws XComptaCoreException {

        EcritureValidee ecriture1 =
                creerEcritureNormale(200);

        EcritureValidee ecriture2 =
                creerEcriture(
                        999,
                        "Achat de marchandises",
                        List.of(
                                mouvement(
                                        101,
                                        "607",
                                        "Achats de marchandises",
                                        "1000.00"),
                                mouvement(
                                        102,
                                        "44566",
                                        "TVA déductible",
                                        "200.00"),
                                mouvement(
                                        103,
                                        "401",
                                        "Fournisseurs",
                                        "-1200.00")));

        assertTrue(
                ecriture1.estEquivalente(ecriture2));
    }

    @Test
    @DisplayName(
            "Le libellé n'intervient pas dans l'équivalence comptable")
    void libelleDifferent()
            throws XComptaCoreException {

        EcritureValidee ecriture1 =
                creerEcritureNormale(200);

        EcritureValidee ecriture2 =
                creerEcriture(
                        201,
                        "Libellé complètement différent",
                        creerMouvements(11));

        assertTrue(
                ecriture1.estEquivalente(ecriture2));
    }

    @Test
    @DisplayName(
            "Un montant différent rend les écritures non équivalentes")
    void montantDifferent()
            throws XComptaCoreException {

        EcritureValidee ecriture1 =
                creerEcritureNormale(200);

        EcritureValidee ecriture2 =
                creerEcriture(
                        201,
                        "Autre écriture",
                        List.of(
                                mouvement(
                                        11,
                                        "607",
                                        "Achats de marchandises",
                                        "999.00"),
                                mouvement(
                                        12,
                                        "44566",
                                        "TVA déductible",
                                        "201.00"),
                                mouvement(
                                        13,
                                        "401",
                                        "Fournisseurs",
                                        "-1200.00")));

        assertFalse(
                ecriture1.estEquivalente(ecriture2));

        assertFalse(
                ecriture2.estEquivalente(ecriture1));
    }

    @Test
    @DisplayName(
            "Un compte différent rend les écritures non équivalentes")
    void compteDifferent()
            throws XComptaCoreException {

        EcritureValidee ecriture1 =
                creerEcritureNormale(200);

        EcritureValidee ecriture2 =
                creerEcriture(
                        201,
                        "Autre écriture",
                        List.of(
                                mouvement(
                                        11,
                                        "606",
                                        "Achats non stockés",
                                        "1000.00"),
                                mouvement(
                                        12,
                                        "44566",
                                        "TVA déductible",
                                        "200.00"),
                                mouvement(
                                        13,
                                        "401",
                                        "Fournisseurs",
                                        "-1200.00")));

        assertFalse(
                ecriture1.estEquivalente(ecriture2));

        assertFalse(
                ecriture2.estEquivalente(ecriture1));
    }

    @Test
    @DisplayName(
            "Une écriture n'est pas équivalente à null")
    void equivalenceAvecNull()
            throws XComptaCoreException {

        EcritureValidee ecriture =
                creerEcritureNormale(200);

        assertFalse(
                ecriture.estEquivalente(null));
    }

    private EcritureValidee creerEcritureNormale(int id)
            throws XComptaCoreException {

        return creerEcriture(
                id,
                "Achat de marchandises",
                creerMouvements(1));
    }

    private List<MouvementValidee> creerMouvements(
            int premierId)
            throws XComptaCoreException {

        return List.of(
                mouvement(
                        premierId,
                        "607",
                        "Achats de marchandises",
                        "1000.00"),
                mouvement(
                        premierId + 1,
                        "44566",
                        "TVA déductible",
                        "200.00"),
                mouvement(
                        premierId + 2,
                        "401",
                        "Fournisseurs",
                        "-1200.00"));
    }

    private EcritureValidee creerEcriture(
            int id,
            String libelle,
            List<MouvementValidee> mouvements)
            throws XComptaCoreException {

        return new EcritureValidee(id, libelle, LocalDate.of(2026, 9, 17), new Journal(
                        "AC",
                        "Achats"), mouvements);
    }

    private MouvementValidee mouvement(
            int id,
            String numeroCompte,
            String libelleCompte,
            String montant)
            throws XComptaCoreException {

        return new MouvementValidee(
                id,
                new BigDecimal(montant),
                new Compte(
                        numeroCompte,
                        libelleCompte));
    }
}
