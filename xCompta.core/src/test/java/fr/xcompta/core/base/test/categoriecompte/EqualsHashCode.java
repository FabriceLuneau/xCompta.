package fr.xcompta.core.base.test.categoriecompte;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.xcompta.core.base.CategorieCompte;

/** Tests du contrat d'égalité fondé sur le numéro. */
class EqualsHashCode {

    @Test
    @DisplayName("Même numéro : égalité et hashCode identique")
    void memeNumero() {
        CategorieCompte a = new CategorieCompte((byte) 60, "Achats");
        CategorieCompte b = new CategorieCompte((byte) 60, "Autre libellé");
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertEquals(a, a);
    }

    @Test
    @DisplayName("Numéros différents : catégories différentes")
    void numerosDifferents() {
        CategorieCompte a = new CategorieCompte((byte) 60, "Achats");
        CategorieCompte b = new CategorieCompte((byte) 61, "Achats");
        assertNotEquals(a, b);
        assertNotEquals(a, null);
        assertNotEquals(a, "60");
    }

    @Test
    @DisplayName("HashSet déduplique selon le numéro")
    void hashSet() {
        Set<CategorieCompte> ensemble = new HashSet<>();
        ensemble.add(new CategorieCompte((byte) 60, "Achats"));
        ensemble.add(new CategorieCompte((byte) 60, "Autre libellé"));
        ensemble.add(new CategorieCompte((byte) 61, "Services extérieurs"));
        assertEquals(2, ensemble.size());
    }
}
