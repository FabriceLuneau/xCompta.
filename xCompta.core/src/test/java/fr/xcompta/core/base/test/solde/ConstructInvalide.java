package fr.xcompta.core.base.test.solde;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import fr.xcompta.core.base.Compte;
import fr.xcompta.core.base.Solde;

/**
 * Teste les constructions invalides d'un objet {@link Solde}.
 *
 * <p>
 * Les tests vérifient le comportement du constructeur lorsqu'une valeur
 * obligatoire est absente.
 * </p>
 *
 * <p>
 * Les tests portant sur des représentations textuelles invalides d'un montant
 * concernent directement {@link BigDecimal} : l'exception est levée avant
 * l'appel du constructeur de {@link Solde}.
 * </p>
 *
 * @author Fabrice Luneau
 */
class ConstructInvalide {

    private final Compte compte =
            new Compte("512", "Banque");

    private final LocalDate date =
            LocalDate.now();
    private final BigDecimal montant =
            new BigDecimal("123");

    @Test
    @DisplayName(
            "Vérifie qu'un compte null lève une exception à la construction")
    void constructeurCompteNull() {

        assertThrows(
                NullPointerException.class,
                () -> new Solde(
                        null,
                        date,
                        montant));
    }

    @Test
    @DisplayName(
            "Vérifie qu'une date null lève une exception à la construction")
    void constructeurDateNull() {

        assertThrows(
                NullPointerException.class,
                () -> new Solde(
                        compte,
                        null,
                        montant));
    }

    @Test
    @DisplayName(
            "Vérifie qu'un montant null lève une exception à la construction")
    void constructeurMontantNull() {

        assertThrows(
                NullPointerException.class,
                () -> new Solde(
                        compte,
                        date,
                        null));
    }

    @ParameterizedTest(name = "montant source = [{0}]")
    @ValueSource(strings = {
            "AA",
            "cent,25",
            "cent virgule vingt cinq",
            "1230€",
            "123€34",
            
            "145,46 euro",
            " "
    })
    @DisplayName(
            "Vérifie qu'une représentation invalide d'un montant lève une NumberFormatException")
    void montantBigDecimalInvalide(String montantSource) {

        assertThrows(
                NumberFormatException.class,
                () -> new BigDecimal(montantSource));
    }
}