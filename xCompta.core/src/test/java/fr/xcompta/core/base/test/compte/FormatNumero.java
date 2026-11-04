package fr.xcompta.core.base.test.compte;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.xcompta.core.base.Compte;

/**
 * Teste la normalisation syntaxique d'un numéro de compte.
 *
 * <p>
 * La normalisation conserve les zéros significatifs, supprime les espaces
 * périphériques et convertit les lettres en majuscules.
 * </p>
 *
 * @author Fabrice Luneau
 */
class FormatNumero {

    @Test
    @DisplayName("Conserve les zéros terminaux")
    void trimNumeroConserveZerosTerminaux() {
        assertEquals("607000", Compte.trimNumero("607000"));
    }

    @Test
    @DisplayName("Supprime les espaces périphériques")
    void trimNumeroSupprimeEspaces() {
        assertEquals("607", Compte.trimNumero("  607  "));
    }

    @Test
    @DisplayName("Convertit les lettres en majuscules")
    void trimNumeroConvertitEnMajuscules() {
        assertEquals("401DUPONT", Compte.trimNumero("401dupont"));
    }

    @Test
    @DisplayName("Conserve un numéro déjà normalisé")
    void trimNumeroRienAModifier() {
        assertEquals("607", Compte.trimNumero("607"));
    }
}
