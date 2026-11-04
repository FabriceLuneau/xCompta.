package fr.xcompta.core.base.test.compte;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.xcompta.core.base.Compte;

/**
 * Teste la normalisation du libellé et la
 * représentation textuelle de {@link Compte}.
 *
 * <p>
 * Le numéro et le libellé sont immuables après la construction du compte.
 * La méthode statique {@link Compte#normaliseLibelle(String)} permet de
 * valider et normaliser un libellé sans modifier un compte existant.
 * </p>
 *
 * @author Fabrice Luneau
 */
class FormatLibelle {

    @Test
    @DisplayName("Vérifie la normalisation du libellé")
    void normaliseLibelle() {

        String valeur = Compte.normaliseLibelle("  Achats de marchandises  ");

        assertEquals("Achats de marchandises", valeur);
    }

    @Test
    @DisplayName("Vérifie que normaliseLibelle refuse un libellé null")
    void normaliseLibelleNull() {

        assertThrows(
                NullPointerException.class,
                () -> Compte.normaliseLibelle(null));
    }

    @Test
    @DisplayName("Vérifie que normaliseLibelle refuse un libellé vide")
    void normaliseLibelleVide() {

        assertThrows(
                IllegalArgumentException.class,
                () -> Compte.normaliseLibelle("   "));
    }

    @Test
    @DisplayName("Vérifie la représentation textuelle d'un compte numérique")
    void toStringCompteNumerique() {

        Compte compte = new Compte("607", "Achats de marchandises");

        assertEquals(
                "607000 - Achats de marchandises",
                compte.toString());
    }

    @Test
    @DisplayName("Vérifie la représentation textuelle d'un compte individualisé")
    void toStringCompteAlphanumerique() {

        Compte compte = new Compte("401DUPONT", "Fournisseur Dupont");

        assertEquals(
                "401DUPONT - Fournisseur Dupont",
                compte.toString());
    }
}
