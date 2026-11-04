package fr.xcompta.core.base.test.compte;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import fr.xcompta.core.base.Compte;

/**
 * Teste les constructions invalides d'un objet {@link Compte} avec le
 * constructeur actuel à deux arguments.
 *
 * <p>
 * Les tests couvrent les contraintes portant sur le libellé ainsi que les
 * règles syntaxiques du numéro de compte : longueur minimale de trois
 * caractères, trois premiers caractères numériques, premier chiffre différent
 * de zéro, deuxième et troisième chiffres non simultanément nuls et absence de
 * caractères autres que des lettres ou des chiffres.
 * </p>
 *
 * @author Fabrice Luneau
 */
class ConstructInvalide {

    private final String numeroDeCompte1 = "101";
    private final String libelleDeCompte1 = "Capital";

    @ParameterizedTest(name = "libellé invalide = [{0}], exception = {1}")
    @MethodSource("libellesInvalides")
    @DisplayName("Constructeur à deux arguments : refuse un libellé invalide")
    void constructeurLibelleInvalide(
            final String libelle,
            final Class<? extends Throwable> exceptionAttendue) {

        assertThrows(
                exceptionAttendue,
                () -> new Compte(numeroDeCompte1, libelle));
    }

    @ParameterizedTest(name = "numéro invalide = [{0}], exception = {1}")
    @MethodSource("numerosInvalides")
    @DisplayName("Constructeur à deux arguments : refuse un numéro invalide")
    void constructeurNumeroInvalide(
            final String numero,
            final Class<? extends Throwable> exceptionAttendue) {

        assertThrows(
                exceptionAttendue,
                () -> new Compte(numero, libelleDeCompte1));
    }

    private static Stream<Arguments> libellesInvalides() {
        return Stream.of(
                Arguments.of(null, NullPointerException.class),
                Arguments.of("", IllegalArgumentException.class),
                Arguments.of(" ", IllegalArgumentException.class),
                Arguments.of("    ", IllegalArgumentException.class),
                Arguments.of("\t", IllegalArgumentException.class));
    }

    private static Stream<Arguments> numerosInvalides() {
        return Stream.of(
                Arguments.of(null, NullPointerException.class),
                Arguments.of("", IllegalArgumentException.class),
                Arguments.of(" ", IllegalArgumentException.class),
                Arguments.of("1", IllegalArgumentException.class),
                Arguments.of("53", IllegalArgumentException.class),
                Arguments.of("010", IllegalArgumentException.class),
                Arguments.of("0000", IllegalArgumentException.class),
                Arguments.of("100", IllegalArgumentException.class),
                Arguments.of("400", IllegalArgumentException.class),
                Arguments.of("A01", IllegalArgumentException.class),
                Arguments.of("4A1", IllegalArgumentException.class),
                Arguments.of("41A", IllegalArgumentException.class),
                Arguments.of("401-01", IllegalArgumentException.class),
                Arguments.of("401 01", IllegalArgumentException.class),
                Arguments.of("401_01", IllegalArgumentException.class));
    }
}
