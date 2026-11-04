package fr.xcompta.core.base.test.integration.ecriturevalidee;

import java.time.LocalDate;
import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import fr.xcompta.core.base.exception.XComptaCoreNullPointerException;
import fr.xcompta.core.date.exception.DateInvalideException;

/**
 * Jeu de données invalides utilisé par les tests d'intégration de
 * {@code EcritureValidee}.
 *
 * <p>
 * Ces cas portent sur l'en-tête de l'écriture. Les mouvements utilisés par le
 * test restent valides afin que chaque scénario échoue pour la seule règle
 * indiquée par le jeu de données.
 * </p>
 *
 * @author Fabrice Luneau
 */
final class JeuDonneesInvalidesEcritureValidee {

	private static final LocalDate DATE = LocalDate.of(2026, 8, 19);

	private JeuDonneesInvalidesEcritureValidee() {
		// Classe utilitaire : pas d'instanciation.
	}

	/**
	 * Fournit les en-têtes invalides d'une écriture validée.
	 *
	 * @return cas invalides et exception attendue
	 */
	static Stream<Arguments> fournirEntetesInvalides() {
		return Stream.of(

				Arguments.of("identifiant négatif", -1, DATE, "OD", "Opérations diverses",
						"Écriture valide sauf identifiant", IllegalArgumentException.class),

				Arguments.of("date null", 1, null, "OD", "Opérations diverses", "Écriture valide sauf date",
						DateInvalideException.class),

				Arguments.of("journal null", 1, DATE, null, null, "Écriture valide sauf journal",
						XComptaCoreNullPointerException.class),

				Arguments.of("libellé null", 1, DATE, "OD", "Opérations diverses", null,
						XComptaCoreNullPointerException.class),

				Arguments.of("libellé vide", 1, DATE, "OD", "Opérations diverses", "",
						XComptaCoreNullPointerException.class),

				Arguments.of("libellé blanc", 1, DATE, "OD", "Opérations diverses", "   ",
						XComptaCoreNullPointerException.class));
	}
}
