package fr.xcompta.core.base.test.integration.ecrituredebrouillard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import fr.xcompta.core.base.EcritureDeBrouillard;
import fr.xcompta.core.base.MouvementDeBrouillard;
import fr.xcompta.core.base.exception.XComptaCoreException;
import fr.xcompta.core.base.test.integration.ecrituredebrouillard.JeuDonneesEcritureDeBrouillard.DonneesEcriture;

/** Teste la copie pour validation après construction avec 0 argument(s).
 * @author Fabrice Luneau
 */
class ValidationConstruct0arg {
    private static final String SOURCE =
            "fr.xcompta.core.base.test.integration.ecrituredebrouillard."
            + "JeuDonneesEcritureDeBrouillard#fournirEcritures";

    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource(SOURCE)
    @DisplayName("Copie pour validation : constructeur à 0 argument(s)")
    void verifierValidation(DonneesEcriture donnees) throws XComptaCoreException {
        EcritureDeBrouillard brouillard = new EcritureDeBrouillard();
        assertEquals(0, brouillard.getId());
        assertEquals(LocalDate.now(), brouillard.getDate());
        assertNull(brouillard.getJournal());
        assertEquals("nouvelle écriture", brouillard.getLibelle());
        assertNotNull(brouillard.getMouvements());
        assertTrue(brouillard.getMouvements().isEmpty());

        if (donnees.id() > 0) {
            brouillard.attribuerIdentifiants(donnees.id(), donnees.id() + 1000);
        }
        brouillard.setDate(donnees.date());
        brouillard.setLibelle(donnees.libelle());
        brouillard.setJournal(VerificationCopieValidation.creerJournal(donnees));
        for (MouvementDeBrouillard mouvement : VerificationCopieValidation.creerMouvements(donnees)) {
            brouillard.ajouterMouvementALaFin(mouvement);
        }

        VerificationCopieValidation.verifier(brouillard, donnees);
    }
}
