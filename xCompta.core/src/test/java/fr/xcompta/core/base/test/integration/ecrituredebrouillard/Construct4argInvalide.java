package fr.xcompta.core.base.test.integration.ecrituredebrouillard;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import fr.xcompta.core.base.EcritureDeBrouillard;
import fr.xcompta.core.base.Journal;
import fr.xcompta.core.base.exception.XComptaCoreIllegalArgumentException;

/**
 * Teste les entêtes invalides d'une écriture de brouillard créée avec le
 * constructeur à quatre arguments.
 *
 * <p>
 * Aucun mouvement n'est ajouté. Les tests vérifient uniquement les erreurs
 * d'entête pouvant être détectées à la construction ou avant le contrôle du
 * nombre de mouvements.
 * </p>
 *
 * @author Fabrice Luneau
 */
class Construct4argInvalide {

	private static final String SOURCE = "fr.xcompta.core.base.test.integration.ecrituredebrouillard."
			+ "JeuDonneesInvalidesEcritureDeBrouillard#";

	private static final LocalDate DATE = LocalDate.now();
	private static final Journal JOURNAL = new Journal("AC", "Journal des achats");
	private static final String LIBELLE = "Achat de marchandises";

	@ParameterizedTest
	@MethodSource(SOURCE + "idsInvalides")
	@DisplayName("Constructeur à 4 arguments : refuse un identifiant négatif")
	void idInvalide(final int id) {
		assertThrows(XComptaCoreIllegalArgumentException.class,
				() -> new EcritureDeBrouillard(id, (id == 0 ? 0 : id + 1000), LIBELLE, DATE, JOURNAL));
	}
}
