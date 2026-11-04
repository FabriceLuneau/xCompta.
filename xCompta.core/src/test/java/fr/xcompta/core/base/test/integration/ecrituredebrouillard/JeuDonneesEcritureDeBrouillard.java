package fr.xcompta.core.base.test.integration.ecrituredebrouillard;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

/**
 * Jeu de données partagé par les tests d'intégration de
 * {@code EcritureDeBrouillard}.
 *
 * <p>Chaque cas décrit une écriture complète : son en-tête et la liste
 * ordonnée de ses mouvements. Le jeu de données est indépendant de la manière
 * dont l'objet métier est construit.</p>
 *
 * @author Fabrice Luneau
 */
final class JeuDonneesEcritureDeBrouillard {

    private JeuDonneesEcritureDeBrouillard() {
        // Classe utilitaire.
    }

    /**
     * Décrit un mouvement comptable attendu.
     *
     * @param numeroCompte numéro du compte
     * @param libelleCompte libellé du compte
     * @param montant montant signé sous forme textuelle
     */
    record DonneesMouvement(
            String numeroCompte,
            String libelleCompte,
            String montant) {
    }

    /**
     * Décrit une écriture comptable complète.
     *
     * @param id identifiant de l'écriture
     * @param date date comptable
     * @param codeJournal code du journal
     * @param libelleJournal libellé du journal
     * @param libelle libellé de l'écriture
     * @param mouvements mouvements attendus, dans leur ordre comptable
     */
    record DonneesEcriture(
            int id,
            LocalDate date,
            String codeJournal,
            String libelleJournal,
            String libelle,
            List<DonneesMouvement> mouvements) {
    }

    /**
     * Fournit un jeu varié d'écritures équilibrées.
     *
     * @return flux des écritures de test
     */
    static Stream<DonneesEcriture> fournirEcritures() {
        LocalDate date = LocalDate.of(2026, 8, 19);

        return Stream.of(
                new DonneesEcriture(
                        3545, date, "BQ", "Banque",
                        "Retrait espèces pour alimentation caisse",
                        List.of(
                                m("580", "Virements internes", "500.00"),
                                m("512", "Banque", "-500.00"))),

                new DonneesEcriture(
                        3546, date, "CA", "Caisse",
                        "Alimentation de la caisse",
                        List.of(
                                m("530", "Caisse", "500.00"),
                                m("580", "Virements internes", "-500.00"))),

                new DonneesEcriture(
                        3547, date, "AC", "Achats",
                        "Achat de marchandises",
                        List.of(
                                m("607", "Achats de marchandises", "1000.00"),
                                m("44566", "TVA déductible sur autres biens et services", "200.00"),
                                m("401", "Fournisseurs", "-1200.00"))),

                new DonneesEcriture(
                        3548, date, "VE", "Ventes",
                        "Vente de marchandises",
                        List.of(
                                m("411", "Clients", "1200.00"),
                                m("707", "Ventes de marchandises", "-1000.00"),
                                m("44571", "TVA collectée", "-200.00"))),

                new DonneesEcriture(
                        3549, date, "AC", "Achats",
                        "Achat avec consignation d'emballages",
                        List.of(
                                m("607", "Achats de marchandises", "1000.00"),
                                m("44566", "TVA déductible sur autres biens et services", "200.00"),
                                m("4096", "Fournisseurs - Créances pour emballages à rendre", "150.00"),
                                m("401", "Fournisseurs", "-1350.00"))),

                new DonneesEcriture(
                        3550, date, "VE", "Ventes",
                        "Vente avec consignation d'emballages",
                        List.of(
                                m("411", "Clients", "1350.00"),
                                m("707", "Ventes de marchandises", "-1000.00"),
                                m("44571", "TVA collectée", "-200.00"),
                                m("4196", "Clients - Dettes sur emballages consignés", "-150.00"))),

                new DonneesEcriture(
                        3551, date, "OD", "Opérations diverses",
                        "Déclaration de TVA",
                        List.of(
                                m("44571", "TVA collectée", "3200.00"),
                                m("44566", "TVA déductible sur autres biens et services", "-2100.00"),
                                m("44562", "TVA déductible sur immobilisations", "-500.00"),
                                m("44551", "TVA à décaisser", "-600.00"))));
    }

    private static DonneesMouvement m(
            final String numeroCompte,
            final String libelleCompte,
            final String montant) {

        return new DonneesMouvement(numeroCompte, libelleCompte, montant);
    }
}
