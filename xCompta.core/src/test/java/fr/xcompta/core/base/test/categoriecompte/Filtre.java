package fr.xcompta.core.base.test.categoriecompte;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.xcompta.core.base.CategorieCompte;
import fr.xcompta.core.base.Classe;

/** Tests des méthodes de consultation du référentiel statique. */
class Filtre {

    @Test
    @DisplayName("Le référentiel contient des catégories valides")
    void toutesLesCategories() {
        CategorieCompte[] categories = CategorieCompte.getCategories();
        assertTrue(categories.length > 0);
        assertTrue(Arrays.stream(categories).allMatch(c -> c != null
                && c.numero() >= 10 && c.numero() <= 99));
        assertEquals(categories.length,
                Arrays.stream(categories).map(CategorieCompte::numero).distinct().count());
    }

    @Test
    @DisplayName("getCategories retourne une copie défensive")
    void copieDefensive() {
        CategorieCompte[] premier = CategorieCompte.getCategories();
        CategorieCompte[] second = CategorieCompte.getCategories();
        assertNotSame(premier, second);
        CategorieCompte original = second[0];
        premier[0] = null;
        assertEquals(original, CategorieCompte.getCategories()[0]);
    }

    @Test
    @DisplayName("Le filtrage par numéro couvre les neuf classes")
    void filtrageParNumero() {
        CategorieCompte[] toutes = CategorieCompte.getCategories();
        for (int numero = 1; numero <= 9; numero++) {
            final int classe = numero;
            CategorieCompte[] resultat = CategorieCompte.getCategories(classe);
            assertTrue(Arrays.stream(resultat)
                    .allMatch(c -> c.getNumeroClasse() == classe));
            assertEquals(Arrays.stream(toutes)
                    .filter(c -> c.getNumeroClasse() == classe).count(), resultat.length);
        }
    }

    @Test
    @DisplayName("Le filtrage par objet Classe correspond au filtrage par numéro")
    void filtrageParClasse() {
        for (byte numero = 1; numero <= 9; numero++) {
            Classe classe = Classe.getClasse(numero);
            assertNotNull(classe);
            assertArrayEquals(CategorieCompte.getCategories(numero),
                    CategorieCompte.getCategories(classe));
        }
    }

    @Test
    @DisplayName("Les résultats filtrés sont des tableaux indépendants")
    void copieFiltrage() {
        CategorieCompte[] premier = CategorieCompte.getCategories(6);
        CategorieCompte[] second = CategorieCompte.getCategories(6);
        assertNotSame(premier, second);
        assertTrue(premier.length > 0);
        CategorieCompte original = second[0];
        premier[0] = null;
        assertEquals(original, CategorieCompte.getCategories(6)[0]);
    }

    @Test
    @DisplayName("Numéros de classe invalides")
    void classesInvalides() {
        for (int numero : new int[] {-1, 0, 10, 100}) {
            assertThrows(IllegalArgumentException.class,
                    () -> CategorieCompte.getCategories(numero));
        }
    }

    @Test
    @DisplayName("Classe null")
    void classeNull() {
        assertThrows(NullPointerException.class,
                () -> CategorieCompte.getCategories((Classe) null));
    }
}
