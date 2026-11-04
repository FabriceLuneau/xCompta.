package fr.xcompta.core.base.test.integration.ecriturevalidee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.xcompta.core.base.Compte;
import fr.xcompta.core.base.EcritureValidee;
import fr.xcompta.core.base.Journal;
import fr.xcompta.core.base.MouvementValidee;
import fr.xcompta.core.base.exception.XComptaCoreException;

/**
 * Tests d'intégration des garanties propres à une écriture validée :
 * copie défensive, collection non modifiable et revalidation.
 *
 * @author Fabrice Luneau
 */
class AutoVerification {

    @Test
    @DisplayName("La liste fournie au constructeur est copiée défensivement")
    void copieDefensiveCollection() throws XComptaCoreException {
        List<MouvementValidee> source = creerMouvements();

        EcritureValidee ecriture = creerEcriture(source);
        source.clear();

        assertEquals(3, ecriture.size());
    }

    @Test
    @DisplayName("La liste retournée par getMouvements n'est pas modifiable")
    void collectionExposeeNonModifiable() throws XComptaCoreException {
        EcritureValidee ecriture = creerEcriture(creerMouvements());

        assertThrows(
                UnsupportedOperationException.class,
                () -> ecriture.getMouvements().clear());

        assertEquals(3, ecriture.size());
    }

    @Test
    @DisplayName("Les mouvements reconstruits sont rattachés à l'écriture validée")
    void mouvementsRattaches() throws XComptaCoreException {
        List<MouvementValidee> mouvements = creerMouvements();

        EcritureValidee ecriture = creerEcriture(mouvements);

        for (MouvementValidee mouvement : ecriture.getMouvements()) {
            assertTrue(mouvement.getEcriture() == ecriture);
        }
    }

    @Test
    @DisplayName(
            "Une écriture validée construite correctement passe à nouveau la pré-validation")
    void preValidationValidee() throws XComptaCoreException {
        EcritureValidee ecriture = creerEcriture(creerMouvements());

        ecriture.testPreValidation();

        assertTrue(ecriture.estValide());
    }

    private EcritureValidee creerEcriture(
            List<MouvementValidee> mouvements)
            throws XComptaCoreException {

        return new EcritureValidee(200, "Achat de marchandises", LocalDate.of(2026, 9, 17), new Journal("AC", "Achats"), mouvements);
    }

    private List<MouvementValidee> creerMouvements()
            throws XComptaCoreException {

        List<MouvementValidee> mouvements = new ArrayList<>();

        mouvements.add(
                new MouvementValidee(
                        1,
                        new BigDecimal("1000.00"),
                        new Compte(
                                "607",
                                "Achats de marchandises")));

        mouvements.add(
                new MouvementValidee(
                        2,
                        new BigDecimal("200.00"),
                        new Compte(
                                "44566",
                                "TVA déductible")));

        mouvements.add(
                new MouvementValidee(
                        3,
                        new BigDecimal("-1200.00"),
                        new Compte(
                                "401",
                                "Fournisseurs")));

        return mouvements;
    }
}
