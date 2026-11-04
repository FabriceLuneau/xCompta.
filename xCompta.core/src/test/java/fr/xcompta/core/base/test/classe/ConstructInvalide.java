package fr.xcompta.core.base.test.classe;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import fr.xcompta.core.base.Classe;

/**
 * Teste les constructions invalides d'un objet {@link Classe}.
 *
 * @author Fabrice Luneau
 */
class ConstructInvalide {

    private final byte numeroDeClasse1 = (byte) 1;

    @ParameterizedTest(name = "libellé invalide = [{0}]")
    @NullSource
    @ValueSource(strings = { "", " ", "    ", "\t" })
    @DisplayName("Vérifie qu'un libellé null, vide ou blanc est refusé")
    void constructeurLibelleInvalide(String libelle) {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Classe(numeroDeClasse1, libelle));
    }

    @ParameterizedTest(name = "numéro invalide = {0}")
    @ValueSource(bytes = { 0, 10, -1, 127 })
    @DisplayName("Vérifie qu'un numéro hors de l'intervalle 1 à 9 est refusé")
    void constructeurNumeroInvalide(byte numero) {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Classe(numero, "Libellé"));
    }
}
