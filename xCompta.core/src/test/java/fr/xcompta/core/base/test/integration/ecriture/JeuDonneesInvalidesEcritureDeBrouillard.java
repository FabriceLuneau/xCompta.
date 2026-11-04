package fr.xcompta.core.base.test.integration.ecriture;

import java.time.LocalDate;
import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

/**
 * Fournit les données invalides communes aux tests d'intégration des
 * constructeurs de {@code EcritureDeBrouillard}.
 *
 * <p>
 * Ces données concernent uniquement l'entête de l'écriture. Les tests de
 * validation comptable des mouvements sont traités séparément.
 * </p>
 *
 * @author Fabrice Luneau
 */
final class JeuDonneesInvalidesEcritureDeBrouillard {

    private JeuDonneesInvalidesEcritureDeBrouillard() {
    }

    static Stream<Arguments> idsInvalides() {
        return Stream.of(
                Arguments.of(-1),
                Arguments.of(-10),
                Arguments.of(Integer.MIN_VALUE));
    }

    static Stream<Arguments> datesInvalides() {
        return Stream.of(
                Arguments.of((LocalDate) null));
    }

    static Stream<Arguments> journauxInvalides() {
        return Stream.of(
                Arguments.of((Object) null));
    }

    static Stream<Arguments> libellesInvalides() {
        return Stream.of(
                Arguments.of((String) null),
                Arguments.of(""),
                Arguments.of(" "),
                Arguments.of("   "),
                Arguments.of("\t"));
    }
}
