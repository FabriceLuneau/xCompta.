package fr.xcompta.core.base.test.integration.ecrituredebrouillard;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import fr.xcompta.core.base.EcritureDeBrouillard;
import fr.xcompta.core.base.Journal;
import fr.xcompta.core.base.exception.XComptaCoreIllegalArgumentException;
import fr.xcompta.core.base.exception.XComptaCoreNullPointerException;

/**
 * Teste les entêtes invalides d'une écriture de brouillard créée avec le
 * constructeur sans argument.
 *
 * <p>
 * Aucun mouvement n'est ajouté : les anomalies d'entête doivent être détectées
 * avant les contrôles portant sur les mouvements.
 * </p>
 *
 * @author Fabrice Luneau
 */
class ConstructInvalide0arg {

	private static final String SOURCE = "fr.xcompta.core.base.test.integration.ecrituredebrouillard."
			+ "JeuDonneesInvalidesEcritureDeBrouillard#";

	@ParameterizedTest
	@MethodSource(SOURCE + "idsInvalides")
	@DisplayName("Constructeur vide : refuse ensuite un identifiant négatif")
	void idInvalide(final int id) throws Exception {
		final EcritureDeBrouillard ecriture = new EcritureDeBrouillard();

		assertThrows(XComptaCoreIllegalArgumentException.class, () -> ecriture.setId(id));
	}
}
