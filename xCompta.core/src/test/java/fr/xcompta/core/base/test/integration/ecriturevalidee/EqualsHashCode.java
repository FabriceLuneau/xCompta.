package fr.xcompta.core.base.test.integration.ecriturevalidee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.xcompta.core.base.Compte;
import fr.xcompta.core.base.EcritureValidee;
import fr.xcompta.core.base.Journal;
import fr.xcompta.core.base.MouvementValidee;
import fr.xcompta.core.base.exception.XComptaCoreException;

/**
 * Tests d'intégration du contrat equals/hashCode
 * d'une écriture validée.
 *
 * <p>
 * Ces tests vérifient les propriétés générales de equals et hashCode
 * ainsi que leur comportement dans une collection utilisant le
 * hachage.
 * </p>
 *
 * @author Fabrice Luneau
 */
class EqualsHashCode {

    @Test
    @DisplayName(
            "Une écriture validée est égale à elle-même")
    void equalsReflexif()
            throws XComptaCoreException {

        EcritureValidee ecriture =
                creerEcriture(200);

        assertEquals(
                ecriture,
                ecriture);
    }

    @Test
    @DisplayName(
            "Deux écritures construites avec les mêmes données sont égales")
    void equalsMemeContenu()
            throws XComptaCoreException {

        EcritureValidee ecriture1 =
                creerEcriture(200);

        EcritureValidee ecriture2 =
                creerEcriture(200);

        assertEquals(
                ecriture1,
                ecriture2);

        assertEquals(
                ecriture2,
                ecriture1);
    }

    @Test
    @DisplayName(
            "Deux écritures égales possèdent le même hashCode")
    void hashCodeCoherentAvecEquals()
            throws XComptaCoreException {

        EcritureValidee ecriture1 =
                creerEcriture(200);

        EcritureValidee ecriture2 =
                creerEcriture(200);

        assertEquals(
                ecriture1,
                ecriture2);

        assertEquals(
                ecriture1.hashCode(),
                ecriture2.hashCode());
    }

    @Test
    @DisplayName(
            "HashSet retrouve une écriture égale")
    void hashSetRetrouveEcritureEgale()
            throws XComptaCoreException {

        EcritureValidee ecriture1 =
                creerEcriture(200);

        EcritureValidee ecriture2 =
                creerEcriture(200);

        Set<EcritureValidee> ecritures =
                new HashSet<>();

        ecritures.add(ecriture1);

        assertTrue(
                ecritures.contains(ecriture2));
    }

    @Test
    @DisplayName(
            "HashSet ne conserve qu'une occurrence de deux écritures égales")
    void hashSetElimineDoublon()
            throws XComptaCoreException {

        EcritureValidee ecriture1 =
                creerEcriture(200);

        EcritureValidee ecriture2 =
                creerEcriture(200);

        Set<EcritureValidee> ecritures =
                new HashSet<>();

        ecritures.add(ecriture1);
        ecritures.add(ecriture2);

        assertEquals(
                1,
                ecritures.size());
    }

    @Test
    @DisplayName(
            "Une écriture validée n'est pas égale à null")
    void equalsNull()
            throws XComptaCoreException {

        EcritureValidee ecriture =
                creerEcriture(200);

        assertFalse(
                ecriture.equals(null));
    }

    @Test
    @DisplayName(
            "Une écriture validée n'est pas égale à un objet d'un autre type")
    void equalsAutreType()
            throws XComptaCoreException {

        EcritureValidee ecriture =
                creerEcriture(200);

        assertNotEquals(
                ecriture,
                "Achat de marchandises");
    }

    private EcritureValidee creerEcriture(int id)
            throws XComptaCoreException {

        return new EcritureValidee(id, "Achat de marchandises", LocalDate.of(2026, 9, 17), new Journal(
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
