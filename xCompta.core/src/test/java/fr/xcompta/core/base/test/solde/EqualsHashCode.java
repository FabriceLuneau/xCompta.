package fr.xcompta.core.base.test.solde;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.xcompta.core.base.Compte;
import fr.xcompta.core.base.Solde;

/**
 * Teste l'identité métier de {@link Solde} ainsi que le contrat entre
 * {@link Solde#equals(Object)} et {@link Solde#hashCode()}.
 *
 * <p>
 * L'identité métier d'un solde repose sur le compte et la date.
 * Le montant ne participe pas à cette identité.
 * </p>
 *
 * <p>
 * Il ne doit donc pas exister deux soldes différents pour un même compte
 * à une même date. Si deux instances portent des montants différents pour
 * cette même clé métier, cela révèle une incohérence des données.
 * </p>
 *
 * <p>
 * Le couple compte/date constitue ainsi la clé métier du solde.
 * </p>
 *
 * @author Fabrice Luneau
 */
class EqualsHashCode {

    private final Compte compte1 =
            new Compte("512", "Banque");

    private final Compte compte2 =
            new Compte("401", "Fournisseurs A");

    private final LocalDate date =
            LocalDate.now();

    private final LocalDate hier =
            date.minusDays(1);

    private final LocalDate demain =
            date.plusDays(1);

    private final BigDecimal montant1 =
            new BigDecimal("123");

    private final BigDecimal montant2 =
            new BigDecimal("-123");

    @Test
    @DisplayName(
            "Vérifie l'égalité de deux soldes ayant le même compte et la même date")
    void equalsMemeCompteEtMemeDate() {

        Solde solde1 =
                new Solde(compte1, date, montant1);

        Solde solde2 =
                new Solde(compte1, date, montant1);

        assertEquals(solde1, solde2);
        assertEquals(solde1.hashCode(), solde2.hashCode());
    }

    @Test
    @DisplayName(
            "Vérifie que le montant ne participe pas à l'égalité métier")
    void equalsIgnoreMontant() {

        Solde solde1 =
                new Solde(compte1, date, montant1);

        Solde solde2 =
                new Solde(compte1, date, montant2);

        assertEquals(solde1, solde2);
        assertEquals(solde1.hashCode(), solde2.hashCode());
    }

    @Test
    @DisplayName(
            "Vérifie l'inégalité de deux soldes ayant des dates différentes pour le même compte")
    void notEqualsDatesDifferentes() {

        Solde solde1 =
                new Solde(compte1, date, montant1);

        Solde solde2 =
                new Solde(compte1, hier, montant1);

        assertNotEquals(solde1, solde2);
    }

    @Test
    @DisplayName(
            "Vérifie l'inégalité de deux soldes ayant des comptes différents à la même date")
    void notEqualsComptesDifferents() {

        Solde solde1 =
                new Solde(compte1, date, montant1);

        Solde solde2 =
                new Solde(compte2, date, montant1);

        assertNotEquals(solde1, solde2);
    }

    @Test
    @DisplayName(
            "Vérifie l'inégalité de deux soldes ayant un compte et une date différents")
    void notEqualsCompteEtDateDifferents() {

        Solde solde1 =
                new Solde(compte1, date, montant1);

        Solde solde2 =
                new Solde(compte2, demain, montant1);

        assertNotEquals(solde1, solde2);
    }
    
    @Test
    @DisplayName(
            "Vérifie le contrat equals et hashCode avec plusieurs combinaisons dans un HashSet")
    void hashSetRespecteCleMetierAvecMultiplesCombinaisons() {

        Compte banque =
                new Compte("512", "Banque");

        Compte caisse =
                new Compte("531", "Caisse principale");

        Compte achats =
                new Compte("607", "Achats de marchandises");

        LocalDate aujourdHui = LocalDate.now();
        LocalDate hier = aujourdHui.minusDays(1);
        LocalDate demain = aujourdHui.plusDays(1);

        /*
         * Même clé métier : banque + aujourd'hui.
         * Les montants sont différents mais les soldes sont égaux.
         */
        Solde banqueAujourdHui1 =
                new Solde(
                        banque,
                        aujourdHui,
                        new BigDecimal("100"));

        Solde banqueAujourdHui2 =
                new Solde(
                        banque,
                        aujourdHui,
                        new BigDecimal("-200"));

        Solde banqueAujourdHui3 =
                new Solde(
                        banque,
                        aujourdHui,
                        BigDecimal.ZERO);

        /*
         * Même compte mais dates différentes.
         */
        Solde banqueHier =
                new Solde(
                        banque,
                        hier,
                        new BigDecimal("100"));

        Solde banqueDemain =
                new Solde(
                        banque,
                        demain,
                        new BigDecimal("100"));

        /*
         * Même date mais comptes différents.
         */
        Solde caisseAujourdHui =
                new Solde(
                        caisse,
                        aujourdHui,
                        new BigDecimal("50"));

        Solde achatsAujourdHui =
                new Solde(
                        achats,
                        aujourdHui,
                        new BigDecimal("-50"));

        /*
         * Autres combinaisons compte/date.
         */
        Solde caisseHier =
                new Solde(
                        caisse,
                        hier,
                        new BigDecimal("25"));

        Solde achatsDemain =
                new Solde(
                        achats,
                        demain,
                        new BigDecimal("-25"));

        Set<Solde> collection = new HashSet<>();

        assertEquals(0, collection.size());

        /*
         * Première clé métier :
         * banque + aujourd'hui.
         */
        collection.add(banqueAujourdHui1);
        assertEquals(1, collection.size());

        /*
         * Même clé métier, montant différent :
         * pas de nouvel élément.
         */
        collection.add(banqueAujourdHui2);
        assertEquals(1, collection.size());

        collection.add(banqueAujourdHui3);
        assertEquals(1, collection.size());

        /*
         * Même compte, autres dates :
         * nouvelles clés métier.
         */
        collection.add(banqueHier);
        assertEquals(2, collection.size());

        collection.add(banqueDemain);
        assertEquals(3, collection.size());

        /*
         * Même date, autres comptes :
         * nouvelles clés métier.
         */
        collection.add(caisseAujourdHui);
        assertEquals(4, collection.size());

        collection.add(achatsAujourdHui);
        assertEquals(5, collection.size());

        /*
         * Compte et date différents :
         * nouvelles clés métier.
         */
        collection.add(caisseHier);
        assertEquals(6, collection.size());

        collection.add(achatsDemain);
        assertEquals(7, collection.size());
    }
}