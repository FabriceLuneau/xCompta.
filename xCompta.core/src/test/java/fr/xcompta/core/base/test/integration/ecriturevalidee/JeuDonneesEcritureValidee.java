package fr.xcompta.core.base.test.integration.ecriturevalidee;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

/**
 * Jeu de données partagé par les tests d'intégration de
 * {@code EcritureValidee}.
 *
 * <p>
 * Chaque cas décrit une écriture complète reconstruite depuis la persistance :
 * l'en-tête, les mouvements et les identifiants persistés de ces mouvements.
 * </p>
 *
 * @author Fabrice Luneau
 */
final class JeuDonneesEcritureValidee {

    private JeuDonneesEcritureValidee() {
        // Classe utilitaire : pas d'instanciation.
    }

    record DonneesMouvement(
            int id,
            String numeroCompte,
            String libelleCompte,
            String montant) {
    }

    record DonneesEcriture(
            int id,
            LocalDate date,
            String codeJournal,
            String libelleJournal,
            String libelle,
            List<DonneesMouvement> mouvements) {
    }

    static Stream<DonneesEcriture> fournirEcritures() {
        LocalDate date = LocalDate.of(2026, 8, 19);

        return Stream.of(
                new DonneesEcriture(
                        3545, date, "BQ", "Banque",
                        "Retrait espèces pour alimentation caisse",
                        List.of(
                                m(1001, "580", "Virements internes", "500.00"),
                                m(1002, "512", "Banque", "-500.00"))),

                new DonneesEcriture(
                        3546, date, "CA", "Caisse",
                        "Alimentation de la caisse",
                        List.of(
                                m(1003, "530", "Caisse", "500.00"),
                                m(1004, "580", "Virements internes", "-500.00"))),

                new DonneesEcriture(
                        3547, date, "AC", "Achats",
                        "Achat de marchandises",
                        List.of(
                                m(1005, "607", "Achats de marchandises", "1000.00"),
                                m(1006, "44566", "TVA déductible sur autres biens et services", "200.00"),
                                m(1007, "401", "Fournisseurs", "-1200.00"))),

                new DonneesEcriture(
                        3548, date, "VE", "Ventes",
                        "Vente de marchandises",
                        List.of(
                                m(1008, "411", "Clients", "1200.00"),
                                m(1009, "707", "Ventes de marchandises", "-1000.00"),
                                m(1010, "44571", "TVA collectée", "-200.00"))),

                new DonneesEcriture(
                        3549, date, "AC", "Achats",
                        "Achat avec consignation d'emballages",
                        List.of(
                                m(1011, "607", "Achats de marchandises", "1000.00"),
                                m(1012, "44566", "TVA déductible sur autres biens et services", "200.00"),
                                m(1013, "4096", "Fournisseurs - Créances pour emballages à rendre", "150.00"),
                                m(1014, "401", "Fournisseurs", "-1350.00"))),

                new DonneesEcriture(
                        3550, date, "VE", "Ventes",
                        "Vente avec consignation d'emballages",
                        List.of(
                                m(1015, "411", "Clients", "1350.00"),
                                m(1016, "707", "Ventes de marchandises", "-1000.00"),
                                m(1017, "44571", "TVA collectée", "-200.00"),
                                m(1018, "4196", "Clients - Dettes sur emballages consignés", "-150.00"))),

                new DonneesEcriture(
                        3551, date, "OD", "Opérations diverses",
                        "Déclaration de TVA",
                        List.of(
                                m(1019, "44571", "TVA collectée", "3200.00"),
                                m(1020, "44566", "TVA déductible sur autres biens et services", "-2100.00"),
                                m(1021, "44562", "TVA déductible sur immobilisations", "-500.00"),
                                m(1022, "44551", "TVA à décaisser", "-600.00"))));
    }

    private static DonneesMouvement m(
            final int id,
            final String numeroCompte,
            final String libelleCompte,
            final String montant) {

        return new DonneesMouvement(id, numeroCompte, libelleCompte, montant);
    }
}
