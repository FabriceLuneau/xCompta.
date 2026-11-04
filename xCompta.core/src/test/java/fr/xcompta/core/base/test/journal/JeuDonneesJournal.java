package fr.xcompta.core.base.test.journal;

import java.util.stream.Stream;

/**
 * Jeu de données utilisé par les tests paramétrés de {@code Journal}.
 *
 * <p>
 * Les codes et libellés reprennent les journaux définis dans le fichier XML
 * utilisé par XCompta.
 * </p>
 *
 * @author Fabrice Luneau
 */
final class JeuDonneesJournal {

    private JeuDonneesJournal() {
        // Classe utilitaire : pas d'instanciation.
    }

    /**
     * Décrit les valeurs attendues pour un journal comptable.
     *
     * @param code code du journal
     * @param libelle libellé du journal
     */
    record DonneesJournal(
            String code,
            String libelle) {
    }

    /**
     * Fournit l'ensemble des journaux définis dans le fichier XML.
     *
     * @return flux des journaux de test
     */
    static Stream<DonneesJournal> fournirJournaux() {
        return Stream.of(
                new DonneesJournal(
                        "AC",
                        "Journal des achats"),

                new DonneesJournal(
                        "VE",
                        "Journal des ventes"),

                new DonneesJournal(
                        "CAI",
                        "Journal de caisse"),

                new DonneesJournal(
                        "OD",
                        "Journal des opérations diverses"),

                new DonneesJournal(
                        "BQ",
                        "Journal de banque"),

                new DonneesJournal(
                        "AN",
                        "Journal de des à nouveaux"));
    }
}
