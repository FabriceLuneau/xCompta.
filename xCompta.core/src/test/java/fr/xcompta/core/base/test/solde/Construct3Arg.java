package fr.xcompta.core.base.test.solde;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import fr.xcompta.core.base.Compte;
import fr.xcompta.core.base.Solde;

/**
 * Teste les cas nominaux de construction d'un objet {@link Solde}.
 *
 * <p>
 * Les tests combinent plusieurs comptes, plusieurs dates et plusieurs
 * montants afin de vérifier que le constructeur conserve correctement
 * toutes les valeurs qui lui sont fournies.
 * </p>
 *
 * <p>
 * Les montants testés couvrent notamment les soldes débiteurs, créditeurs
 * et nuls, avec différentes représentations décimales.
 * </p>
 *
 * @author Fabrice Luneau
 */
class Construct3Arg {

    /**
     * Fournit toutes les combinaisons possibles entre les comptes,
     * les dates et les montants de test.
     *
     * @return les jeux de paramètres utilisés pour construire les soldes
     */
    static Stream<Arguments> soldesValides() {

        List<Compte> comptes = List.of(
                new Compte("512", "Banque"),
                new Compte("531", "Caisse principale"),
                new Compte("607", "Achats de marchandises"),
                new Compte("401001", "Fournisseur A"));

        LocalDate aujourdHui = LocalDate.now();

        List<LocalDate> dates = List.of(
                aujourdHui.minusYears(1),
                aujourdHui,
                aujourdHui.plusYears(1));

        List<BigDecimal> montants = List.of(
                new BigDecimal("100"),
                new BigDecimal("100.12"),
                new BigDecimal("0"),
                new BigDecimal("0.00"),
                new BigDecimal("0.0"),
                new BigDecimal("-0"),
                new BigDecimal("-0.00"),
                new BigDecimal("-100"),
                new BigDecimal("-100.12"),
                new BigDecimal("-42.453"));

        return comptes.stream()
                .flatMap(compte ->
                        dates.stream()
                                .flatMap(date ->
                                        montants.stream()
                                                .map(montant ->
                                                        Arguments.of(
                                                                compte,
                                                                date,
                                                                montant))));
    }

    @ParameterizedTest(
            name = "compte={0}, date={1}, montant={2}")
    @MethodSource("soldesValides")
    @DisplayName(
            "Vérifie toutes les combinaisons valides de compte, date et montant")
    void constructeurAffectation(
            Compte compte,
            LocalDate date,
            BigDecimal montant) {

        Solde solde = new Solde(
                compte,
                date,
                montant);

        assertNotNull(solde);
        assertEquals(compte, solde.getCompte());
        assertEquals(date, solde.getDate());
        assertEquals(montant, solde.getMontant());
    }
}