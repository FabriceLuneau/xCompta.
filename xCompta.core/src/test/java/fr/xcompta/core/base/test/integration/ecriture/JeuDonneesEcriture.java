package fr.xcompta.core.base.test.integration.ecriture;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Jeu de données transversal pour les tests d'intégration des écritures.
 *
 * <p>
 * Il permet de construire le même contenu comptable sous forme
 * d'EcritureDeBrouillard ou d'EcritureValidee.
 * </p>
 *
 * @author Fabrice Luneau
 */
final class JeuDonneesEcriture {

    static final LocalDate DATE = LocalDate.of(2026, 9, 17);

    static final String CODE_JOURNAL = "AC";
    static final String LIBELLE_JOURNAL = "Achats";

    static final String LIBELLE = "Achat de marchandises";

    static final int ID_ECRITURE_1 = 100;
    static final int ID_ECRITURE_2 = 200;

    static final MouvementDonnees ACHAT =
            new MouvementDonnees(
                    "607",
                    "Achats de marchandises",
                    new BigDecimal("1000.00"));

    static final MouvementDonnees TVA =
            new MouvementDonnees(
                    "44566",
                    "TVA déductible",
                    new BigDecimal("200.00"));

    static final MouvementDonnees FOURNISSEUR =
            new MouvementDonnees(
                    "401",
                    "Fournisseurs",
                    new BigDecimal("-1200.00"));

    static final List<MouvementDonnees> MOUVEMENTS =
            List.of(ACHAT, TVA, FOURNISSEUR);

    static final List<MouvementDonnees> MOUVEMENTS_ORDRE_INVERSE =
            List.of(FOURNISSEUR, TVA, ACHAT);

    private JeuDonneesEcriture() {
    }

    /**
     * Données minimales nécessaires à la construction d'un mouvement.
     */
    record MouvementDonnees(
            String numeroCompte,
            String libelleCompte,
            BigDecimal montant) {
    }
}
