package fr.xcompta.core.base.test.classe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import fr.xcompta.core.base.Classe;
import fr.xcompta.core.base.test.classe.JeuDonneesClasse.DonneesClasse;

/**
 * Tests paramétrés des constructions nominales de {@link Classe}.
 *
 * <p>
 * Le jeu de données reprend les neuf classes comptables définies dans le
 * fichier XML de référence du projet. Chaque cas vérifie que le constructeur
 * conserve correctement le numéro et le libellé attendus.
 * </p>
 *
 * @author Fabrice Luneau
 */
class Construct2Arg {

    private static final String SOURCE =
            "fr.xcompta.core.base.test.classe."
            + "JeuDonneesClasse#fournirClasses";

    /**
     * Vérifie la construction des neuf classes comptables.
     *
     * @param donnees données de la classe à construire
     */
    @ParameterizedTest(name = "[{index}] classe {0}")
    @MethodSource(SOURCE)
    @DisplayName("Construit les neuf classes comptables du fichier XML")
    void constructeurAvecArguments(final DonneesClasse donnees) {

        Classe classe = new Classe(
                donnees.numero(),
                donnees.libelle());

        assertNotNull(classe);

        assertEquals(
                donnees.numero(),
                classe.numero());

        assertEquals(
                donnees.libelle(),
                classe.libelle());
    }
}
