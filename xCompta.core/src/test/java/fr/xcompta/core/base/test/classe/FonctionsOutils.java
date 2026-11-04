package fr.xcompta.core.base.test.classe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.xcompta.core.base.Classe;

/**
 * Tests des méthodes utilitaires du référentiel des classes comptables.
 *
 * @author Fabrice Luneau
 */
class FonctionsOutils {

    @Test
    @DisplayName("getClasses retourne les neuf classes dans l'ordre")
    void getClassesRetourneNeufClassesOrdonnees() {
        Classe[] classes = Classe.getClasses();

        assertNotNull(classes);
        assertEquals(9, classes.length);
        for (int numero = 1; numero <= 9; numero++) {
            assertNotNull(classes[numero - 1]);
            assertEquals(numero, classes[numero - 1].numero());
        }
    }

    @Test
    @DisplayName("getClasses retourne une copie du tableau interne")
    void getClassesRetourneUneCopie() {
        Classe[] premiereCopie = Classe.getClasses();
        Classe[] secondeCopie = Classe.getClasses();

        assertNotSame(premiereCopie, secondeCopie);
        Classe classeInitiale = secondeCopie[0];
        premiereCopie[0] = null;

        assertSame(classeInitiale, Classe.getClasses()[0]);
    }

    @Test
    @DisplayName("getClasse retrouve chacune des neuf classes")
    void getClasseRetrouveToutesLesClasses() {
        Classe[] classes = Classe.getClasses();

        for (byte numero = 1; numero <= 9; numero++) {
            Classe classe = Classe.getClasse(numero);
            assertNotNull(classe);
            assertSame(classes[numero - 1], classe);
            assertEquals(numero, classe.numero());
        }
    }

    @Test
    @DisplayName("getClasse retourne null pour les numéros invalides")
    void getClasseNumeroInvalide() {
        assertNull(Classe.getClasse((byte) -1));
        assertNull(Classe.getClasse((byte) 0));
        assertNull(Classe.getClasse((byte) 10));
        assertNull(Classe.getClasse(Byte.MAX_VALUE));
    }

    @Test
    @DisplayName("Une classe du référentiel est égale à une classe de même numéro")
    void getClasseRespecteEgaliteEtHashCode() {
        Classe reference = Classe.getClasse((byte) 6);
        Classe autre = new Classe((byte) 6, "Autre libellé");

        assertNotNull(reference);
        assertEquals(reference, autre);
        assertEquals(reference.hashCode(), autre.hashCode());
    }
}
