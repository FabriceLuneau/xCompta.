package fr.xcompta.core.base.test.integration.ecriturevalidee;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
 * Tests d'intégration de la représentation textuelle
 * d'une écriture validée.
 *
 * <p>
 * Ces tests vérifient le contrat général de toString()
 * sans imposer inutilement la totalité de son format.
 * </p>
 *
 * @author Fabrice Luneau
 */
class ToString {

    @Test
    @DisplayName(
            "toString retourne une chaîne non null")
    void toStringNonNull()
            throws XComptaCoreException {

        EcritureValidee ecriture =
                creerEcriture();

        assertNotNull(
                ecriture.toString());
    }

    @Test
    @DisplayName(
            "toString retourne une chaîne non vide")
    void toStringNonVide()
            throws XComptaCoreException {

        EcritureValidee ecriture =
                creerEcriture();

        assertFalse(
                ecriture.toString().isBlank());
    }

    @Test
    @DisplayName(
            "Deux appels successifs à toString retournent le même résultat")
    void toStringStable()
            throws XComptaCoreException {

        EcritureValidee ecriture =
                creerEcriture();

        String resultat1 =
                ecriture.toString();

        String resultat2 =
                ecriture.toString();

        assertTrue(
                resultat1.equals(resultat2));
    }

    private EcritureValidee creerEcriture()
            throws XComptaCoreException {

        return new EcritureValidee(200, 1200, 2200, "Achat de marchandises", LocalDate.of(2026, 9, 17), new Journal(
                        "AC",
                        "Achats"), List.of(
                        mouvement(
                                1,
                                "607",
                                "Achats de marchandises",
                                "1000.00"),
                        mouvement(
                                2,
                                "44566",
                                "TVA déductible",
                                "200.00"),
                        mouvement(
                                3,
                                "401",
                                "Fournisseurs",
                                "-1200.00")));
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
