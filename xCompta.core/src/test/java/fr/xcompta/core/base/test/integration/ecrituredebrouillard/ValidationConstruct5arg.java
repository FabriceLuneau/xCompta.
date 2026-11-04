package fr.xcompta.core.base.test.integration.ecrituredebrouillard;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import fr.xcompta.core.base.*;
import fr.xcompta.core.base.exception.XComptaCoreException;
import fr.xcompta.core.base.test.integration.ecrituredebrouillard.JeuDonneesEcritureDeBrouillard.DonneesEcriture;

/** Teste la copie pour validation après construction avec 5 argument(s).
 * @author Fabrice Luneau
 */
class ValidationConstruct5arg {
    private static final String SOURCE =
            "fr.xcompta.core.base.test.integration.ecrituredebrouillard."
            + "JeuDonneesEcritureDeBrouillard#fournirEcritures";

    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource(SOURCE)
    @DisplayName("Copie pour validation : constructeur à 5 argument(s)")
    void verifierValidation(DonneesEcriture donnees) throws XComptaCoreException {
        Journal journal = VerificationCopieValidation.creerJournal(donnees);
        List<MouvementDeBrouillard> mouvements =
                VerificationCopieValidation.creerMouvements(donnees);
        EcritureDeBrouillard brouillard = new EcritureDeBrouillard(
                donnees.id(), donnees.libelle(), donnees.date(), journal, mouvements);
        assertSame(journal, brouillard.getJournal());
        assertNotNull(brouillard.getMouvements());

        VerificationCopieValidation.verifier(brouillard, donnees);
    }
}
