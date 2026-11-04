package fr.xcompta.core.base.test.integration.ecrituredebrouillard;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.xcompta.core.base.Compte;
import fr.xcompta.core.base.EcritureDeBrouillard;
import fr.xcompta.core.base.Journal;
import fr.xcompta.core.base.MouvementDeBrouillard;
import fr.xcompta.core.base.exception.XComptaCoreException;

/**
 * Tests d'intégration des calculs d'une écriture de brouillard
 * pendant sa saisie.
 *
 * <p>
 * Ces tests vérifient que les totaux et le solde sont recalculés
 * correctement lorsque des mouvements sont ajoutés, modifiés
 * ou supprimés.
 * </p>
 *
 * @author Fabrice Luneau
 */
class CalculsComptables {

    @Test
    @DisplayName(
            "Une écriture sans mouvement possède des totaux nuls")
    void totauxNulsInitialement()
            throws XComptaCoreException {

        EcritureDeBrouillard ecriture =
                creerEcriture();

        assertBigDecimalEquals(
                BigDecimal.ZERO,
                ecriture.getTotalDebit());

        assertBigDecimalEquals(
                BigDecimal.ZERO,
                ecriture.getTotalCredit());

        assertBigDecimalEquals(
                BigDecimal.ZERO,
                ecriture.getSolde());
    }

    @Test
    @DisplayName(
            "L'ajout d'un débit modifie le total débit et le solde")
    void ajoutDebit()
            throws XComptaCoreException {

        EcritureDeBrouillard ecriture =
                creerEcriture();

        ecriture.ajouterMouvementALaFin(
                mouvement(
                        "607",
                        "Achats",
                        "100.00"));

        assertBigDecimalEquals(
                new BigDecimal("100.00"),
                ecriture.getTotalDebit());

        assertBigDecimalEquals(
                BigDecimal.ZERO,
                ecriture.getTotalCredit());

        assertBigDecimalEquals(
                new BigDecimal("100.00"),
                ecriture.getSolde());
    }

    @Test
    @DisplayName(
            "L'ajout d'un crédit modifie le total crédit et le solde")
    void ajoutCredit()
            throws XComptaCoreException {

        EcritureDeBrouillard ecriture =
                creerEcriture();

        ecriture.ajouterMouvementALaFin(
                mouvement(
                        "401",
                        "Fournisseurs",
                        "-100.00"));

        assertBigDecimalEquals(
                BigDecimal.ZERO,
                ecriture.getTotalDebit());

        assertBigDecimalEquals(
                new BigDecimal("-100.00"),
                ecriture.getTotalCredit());

        assertBigDecimalEquals(
                new BigDecimal("-100.00"),
                ecriture.getSolde());
    }

    @Test
    @DisplayName(
            "Une écriture équilibrée possède un solde nul")
    void ecritureEquilibree()
            throws XComptaCoreException {

        EcritureDeBrouillard ecriture =
                creerEcriture();

        ecriture.ajouterMouvementALaFin(
                mouvement(
                        "607",
                        "Achats",
                        "100.00"));

        ecriture.ajouterMouvementALaFin(
                mouvement(
                        "401",
                        "Fournisseurs",
                        "-100.00"));

        assertBigDecimalEquals(
                new BigDecimal("100.00"),
                ecriture.getTotalDebit());

        assertBigDecimalEquals(
                new BigDecimal("-100.00"),
                ecriture.getTotalCredit());

        assertBigDecimalEquals(
                BigDecimal.ZERO,
                ecriture.getSolde());
    }

    @Test
    @DisplayName(
            "La modification d'un montant recalcule le solde")
    void modificationMontant()
            throws XComptaCoreException {

        EcritureDeBrouillard ecriture =
                creerEcriture();

        MouvementDeBrouillard debit =
                mouvement(
                        "607",
                        "Achats",
                        "100.00");

        MouvementDeBrouillard credit =
                mouvement(
                        "401",
                        "Fournisseurs",
                        "-100.00");

        ecriture.ajouterMouvementALaFin(
                debit);

        ecriture.ajouterMouvementALaFin(
                credit);

        debit.setMontant(
                new BigDecimal("120.00"));

        assertBigDecimalEquals(
                new BigDecimal("120.00"),
                ecriture.getTotalDebit());

        assertBigDecimalEquals(
                new BigDecimal("-100.00"),
                ecriture.getTotalCredit());

        assertBigDecimalEquals(
                new BigDecimal("20.00"),
                ecriture.getSolde());
    }

    @Test
    @DisplayName(
            "La correction d'un montant rétablit l'équilibre")
    void correctionMontant()
            throws XComptaCoreException {

        EcritureDeBrouillard ecriture =
                creerEcriture();

        MouvementDeBrouillard debit =
                mouvement(
                        "607",
                        "Achats",
                        "120.00");

        MouvementDeBrouillard credit =
                mouvement(
                        "401",
                        "Fournisseurs",
                        "-100.00");

        ecriture.ajouterMouvementALaFin(
                debit);

        ecriture.ajouterMouvementALaFin(
                credit);

        assertBigDecimalEquals(
                new BigDecimal("20.00"),
                ecriture.getSolde());

        debit.setMontant(
                new BigDecimal("100.00"));

        assertBigDecimalEquals(
                BigDecimal.ZERO,
                ecriture.getSolde());
    }

    @Test
    @DisplayName(
            "La suppression d'un mouvement recalcule les totaux")
    void suppressionMouvement()
            throws XComptaCoreException {

        EcritureDeBrouillard ecriture =
                creerEcriture();

        MouvementDeBrouillard debit =
                mouvement(
                        "607",
                        "Achats",
                        "100.00");

        MouvementDeBrouillard credit =
                mouvement(
                        "401",
                        "Fournisseurs",
                        "-100.00");

        ecriture.ajouterMouvementALaFin(
                debit);

        ecriture.ajouterMouvementALaFin(
                credit);

        ecriture.supprimerMouvement(
                credit);

        assertBigDecimalEquals(
                new BigDecimal("100.00"),
                ecriture.getTotalDebit());

        assertBigDecimalEquals(
                BigDecimal.ZERO,
                ecriture.getTotalCredit());

        assertBigDecimalEquals(
                new BigDecimal("100.00"),
                ecriture.getSolde());
    }

    @Test
    @DisplayName(
            "Un mouvement vide ne modifie pas les totaux")
    void mouvementVideNeModifiePasTotaux()
            throws XComptaCoreException {

        EcritureDeBrouillard ecriture =
                creerEcriture();

        ecriture.ajouterMouvementALaFin(
                mouvement(
                        "607",
                        "Achats",
                        "100.00"));

        ecriture.ajouterMouvementALaFin(
                new MouvementDeBrouillard());

        ecriture.ajouterMouvementALaFin(
                mouvement(
                        "401",
                        "Fournisseurs",
                        "-100.00"));

        assertBigDecimalEquals(
                new BigDecimal("100.00"),
                ecriture.getTotalDebit());

        assertBigDecimalEquals(
                new BigDecimal("-100.00"),
                ecriture.getTotalCredit());

        assertBigDecimalEquals(
                BigDecimal.ZERO,
                ecriture.getSolde());
    }

    private EcritureDeBrouillard creerEcriture()
            throws XComptaCoreException {

        EcritureDeBrouillard ecriture =
                new EcritureDeBrouillard();

        ecriture.setDate(
                LocalDate.of(
                        2026,
                        Month.AUGUST,
                        19));

        ecriture.setJournal(
                new Journal(
                        "AC",
                        "Achats"));

        ecriture.setLibelle(
                "Achat fournisseur");

        return ecriture;
    }

    private MouvementDeBrouillard mouvement(
            String numeroCompte,
            String libelleCompte,
            String montant)
            throws XComptaCoreException {

        return new MouvementDeBrouillard(
                new BigDecimal(montant),
                new Compte(
                        numeroCompte,
                        libelleCompte),
                null);
    }

    private void assertBigDecimalEquals(
            BigDecimal attendu,
            BigDecimal obtenu) {

        assertEquals(
                0,
                attendu.compareTo(obtenu));
    }
}