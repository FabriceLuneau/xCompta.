package fr.xcompta.core.base.test.journal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import fr.xcompta.core.base.Journal;
import fr.xcompta.core.base.test.journal.JeuDonneesJournal.DonneesJournal;

/**
 * Tests paramétrés des constructions nominales de {@link Journal}.
 *
 * <p>
 * Le jeu de données reprend l'ensemble des journaux définis dans le fichier
 * XML utilisé par XCompta. Le test vérifie que le constructeur conserve
 * correctement le code et le libellé de chaque journal.
 * </p>
 *
 * @author Fabrice Luneau
 */
class Construct2Arg {

    private static final String SOURCE =
            "fr.xcompta.core.base.test.journal."
            + "JeuDonneesJournal#fournirJournaux";

    /**
     * Vérifie la construction des journaux définis dans le fichier XML.
     *
     * @param donnees données du journal à construire
     */
    @ParameterizedTest(name = "[{index}] journal {0}")
    @MethodSource(SOURCE)
    @DisplayName("Construit les journaux du fichier XML")
    void constructeurAvecArguments(final DonneesJournal donnees) {

        Journal journal = new Journal(
                donnees.code(),
                donnees.libelle());

        assertNotNull(journal);

        assertEquals(
                donnees.code(),
                journal.code());

        assertEquals(
                donnees.libelle(),
                journal.libelle());
    }
}
