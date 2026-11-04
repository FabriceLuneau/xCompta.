package fr.xcompta.core.base.test.classe;

import java.util.stream.Stream;

/**
 * Jeu de données utilisé par les tests paramétrés de {@code Classe}.
 *
 * <p>
 * Les numéros et libellés proviennent du fichier XML décrivant les classes
 * comptables utilisées par XCompta.
 * </p>
 *
 * @author Fabrice Luneau
 */
final class JeuDonneesClasse {

    private JeuDonneesClasse() {
        // Classe utilitaire : pas d'instanciation.
    }

    /**
     * Décrit une classe comptable attendue.
     *
     * @param numero numéro de la classe comptable
     * @param libelle libellé de la classe
     */
    record DonneesClasse(
            byte numero,
            String libelle) {
    }

    /**
     * Fournit les neuf classes du plan comptable.
     *
     * @return flux des classes comptables
     */
    static Stream<DonneesClasse> fournirClasses() {
        return Stream.of(
                new DonneesClasse(
                        (byte) 1,
                        "Comptes de capitaux"),

                new DonneesClasse(
                        (byte) 2,
                        "Comptes d'immobilisations"),

                new DonneesClasse(
                        (byte) 3,
                        "Comptes de stocks et en-cours"),

                new DonneesClasse(
                        (byte) 4,
                        "Comptes de tiers"),

                new DonneesClasse(
                        (byte) 5,
                        "Comptes financiers"),

                new DonneesClasse(
                        (byte) 6,
                        "Comptes de charges"),

                new DonneesClasse(
                        (byte) 7,
                        "Comptes de produits"),

                new DonneesClasse(
                        (byte) 8,
                        "Comptes spéciaux"),

                new DonneesClasse(
                        (byte) 9,
                        "Comptes analytiques d'exploitation"));
    }
}
