package fr.xcompta.core.base.test.compte;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import fr.xcompta.core.base.Compte;

/**
 * Teste les fonctions supplémentaires de la classe {@link Compte}.
 *
 * <p>
 * Vérifie notamment le formatage d'un numéro de compte en complétant
 * celui-ci avec des zéros à droite jusqu'à atteindre la longueur demandée.
 * Un numéro dont la longueur est déjà supérieure à la longueur demandée
 * ne doit pas être tronqué.
 * </p>
 *
 * @author Fabrice LUNEAU
 */
class FormatNumero2 {

    @ParameterizedTest
    @CsvSource({
    	//les numéros les plus courts doivent allonger à 6
            "404,     404000",
            "5120,    512000",
            "60700,   607000",
            "70700,   707000",
            //les numéros déjà à la taille doivent rester
            "123456,  123456",
            //les numéros plus long ne doivent pas bouger
            "1234560, 1234560",
            "1234567, 1234567",
            //les comptes de tiers avec nom intégrés doivent êtres inchangés
            "401AB, 401AB",
            "401DUPONTLAJOIE, 401DUPONTLAJOIE" 
    })
    @DisplayName(
            "Vérifie que le formatage complète avec des zéros "
            + "sans tronquer les numéros plus longs")
    void testFormatNumero(
            final String numeroAFormater,
            final String numeroAttendu) {

        final String numero =
                Compte.formatNumero(numeroAFormater, 6);

        assertEquals(numeroAttendu, numero);
    }
}