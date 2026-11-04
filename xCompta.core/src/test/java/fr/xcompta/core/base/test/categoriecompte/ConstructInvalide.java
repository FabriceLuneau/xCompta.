package fr.xcompta.core.base.test.categoriecompte;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.xcompta.core.base.CategorieCompte;

/** Tests de rejet des arguments invalides. */
class ConstructInvalide {

    @Test
    @DisplayName("Numéros de catégorie hors de 10 à 99")
    void numerosInvalides() {
        for (byte numero : new byte[] {-1, 0, 9, 100}) {
            assertThrows(IllegalArgumentException.class,
                    () -> new CategorieCompte(numero, "Libellé"));
        }
    }

    @Test
    @DisplayName("Libellé null")
    void libelleNull() {
        assertThrows(NullPointerException.class,
                () -> new CategorieCompte((byte) 60, null));
    }

    @Test
    @DisplayName("Libellé vide ou blanc")
    void libelleVideOuBlanc() {
        assertThrows(IllegalArgumentException.class,
                () -> new CategorieCompte((byte) 60, ""));
        assertThrows(IllegalArgumentException.class,
                () -> new CategorieCompte((byte) 60, "   "));
    }
}
