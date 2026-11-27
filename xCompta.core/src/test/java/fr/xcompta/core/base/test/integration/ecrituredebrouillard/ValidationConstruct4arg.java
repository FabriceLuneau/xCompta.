package fr.xcompta.core.base.test.integration.ecrituredebrouillard;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import fr.xcompta.core.base.EcritureDeBrouillard;
import fr.xcompta.core.base.Journal;
import fr.xcompta.core.base.MouvementDeBrouillard;
import fr.xcompta.core.base.exception.XComptaCoreException;
import fr.xcompta.core.base.test.integration.ecrituredebrouillard.JeuDonneesEcritureDeBrouillard.DonneesEcriture;

/** Teste la copie pour validation après construction avec 4 argument(s).
 * @author Fabrice Luneau
 */
class ValidationConstruct4arg {
    private static final String SOURCE =
            "fr.xcompta.core.base.test.integration.ecrituredebrouillard."
            + "JeuDonneesEcritureDeBrouillard#fournirEcritures";

    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource(SOURCE)
    @DisplayName("Copie pour validation : constructeur à 4 argument(s)")
    void verifierValidation(DonneesEcriture donnees) throws XComptaCoreException {
        Journal journal = VerificationCopieValidation.creerJournal(donnees);
        EcritureDeBrouillard brouillard = new EcritureDeBrouillard(
                donnees.id(), (donnees.id() == 0 ? 0 : donnees.id() + 1000), donnees.libelle(), donnees.date(), journal);
        assertSame(journal, brouillard.getJournal());
        assertNotNull(brouillard.getMouvements());
        assertTrue(brouillard.getMouvements().isEmpty());
        for (MouvementDeBrouillard mouvement : VerificationCopieValidation.creerMouvements(donnees)) {
            brouillard.ajouterMouvementALaFin(mouvement);
        }

        VerificationCopieValidation.verifier(brouillard, donnees);
    }
}
