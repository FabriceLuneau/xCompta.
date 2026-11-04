package fr.xcompta.core.base.test.categoriecompte;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.xcompta.core.base.CategorieCompte;

/** Tests de construction d'une catégorie comptable. */
class Construct2Arg {

    @Test
    @DisplayName("Construction et accesseurs")
    void construction() {
        CategorieCompte categorie = new CategorieCompte((byte) 60, "Achats");
        assertEquals((byte) 60, categorie.numero());
        assertEquals("Achats", categorie.libelle());
        assertEquals((byte) 6, categorie.getNumeroClasse());
    }

    @Test
    @DisplayName("Le libellé est nettoyé des espaces extérieurs")
    void libelleNettoye() {
        CategorieCompte categorie = new CategorieCompte((byte) 60, "  Achats  ");
        assertEquals("Achats", categorie.libelle());
    }
}
