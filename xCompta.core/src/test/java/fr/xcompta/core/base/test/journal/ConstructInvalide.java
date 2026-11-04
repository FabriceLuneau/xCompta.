package fr.xcompta.core.base.test.journal;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import fr.xcompta.core.base.Journal;

/**
 * Teste les constructions invalides d'un objet {@link Journal}.
 *
 * @author Fabrice Luneau
 */
class ConstructInvalide {

    private final String codeDeJournal1 = "AC";
    private final String libelleDeJournal1 = "Journal des achats";

    @ParameterizedTest(name = "libellé invalide = [{0}], exception = {1}")
    @MethodSource("libellesInvalides")
    @DisplayName("Vérifie qu'un libellé invalide est refusé")
    void constructeurLibelleInvalide(
            String libelle,
            Class<? extends Throwable> exceptionAttendue) {

        assertThrows(
                exceptionAttendue,
                () -> new Journal(codeDeJournal1, libelle));
    }

    @ParameterizedTest(name = "code invalide = [{0}], exception = {1}")
    @MethodSource("codesInvalides")
    @DisplayName("Vérifie qu'un code invalide est refusé")
    void constructeurCodeInvalide(
            String code,
            Class<? extends Throwable> exceptionAttendue) {

        assertThrows(
                exceptionAttendue,
                () -> new Journal(code, libelleDeJournal1));
    }

    private static Stream<Arguments> libellesInvalides() {
        return Stream.of(
                Arguments.of(null, NullPointerException.class),
                Arguments.of("", IllegalArgumentException.class),
                Arguments.of(" ", IllegalArgumentException.class),
                Arguments.of("    ", IllegalArgumentException.class),
                Arguments.of("\t", IllegalArgumentException.class));
    }

    private static Stream<Arguments> codesInvalides() {
        return Stream.of(
                Arguments.of(null, NullPointerException.class),
                Arguments.of("", IllegalArgumentException.class),
                Arguments.of(" ", IllegalArgumentException.class),
                Arguments.of("    ", IllegalArgumentException.class),
                Arguments.of("\t", IllegalArgumentException.class));
    }
}
