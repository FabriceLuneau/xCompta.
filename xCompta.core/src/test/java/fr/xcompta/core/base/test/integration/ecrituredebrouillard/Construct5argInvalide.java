package fr.xcompta.core.base.test.integration.ecrituredebrouillard;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import fr.xcompta.core.base.EcritureDeBrouillard;
import fr.xcompta.core.base.Journal;
import fr.xcompta.core.base.MouvementDeBrouillard;
import fr.xcompta.core.base.exception.XComptaCoreIllegalArgumentException;

/**
 * Teste les entêtes invalides d'une écriture de brouillard créée avec le
 * constructeur complet à cinq arguments.
 *
 * <p>
 * Une liste de mouvements vide mais non nulle est fournie afin d'isoler les
 * erreurs d'entête. Les anomalies doivent ainsi être détectées avant les règles
 * comptables portant sur le contenu de l'écriture.
 * </p>
 *
 * @author Fabrice Luneau
 */
class Construct5argInvalide {

	private static final String SOURCE = "fr.xcompta.core.base.test.integration.ecrituredebrouillard."
			+ "JeuDonneesInvalidesEcritureDeBrouillard#";

	private static final LocalDate DATE = LocalDate.now();
	private static final Journal JOURNAL = new Journal("AC", "Journal des achats");
	private static final String LIBELLE = "Achat de marchandises";

	@ParameterizedTest
	@MethodSource(SOURCE + "idsInvalides")
	@DisplayName("Constructeur à 5 arguments : refuse un identifiant négatif")
	void idInvalide(final int id) {
		assertThrows(XComptaCoreIllegalArgumentException.class,
				() -> new EcritureDeBrouillard(id, 1000, LIBELLE, DATE, JOURNAL, new ArrayList<MouvementDeBrouillard>()));
	}

}
