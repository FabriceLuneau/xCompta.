package fr.xcompta.core.base.test.classe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.xcompta.core.base.Classe;

/**
 * Teste l'identité métier de {@link Classe}, ainsi que le contrat entre
 * {@link Classe#equals(Object)} et {@link Classe#hashCode()}.
 *
 * <p>
 * L'identité métier d'une classe comptable repose uniquement sur son numéro.
 * Deux instances possédant le même numéro sont donc considérées comme égales,
 * même si leurs libellés sont différents.
 * </p>
 *
 * @author Fabrice Luneau
 */
class EqualsHashCode {

    private final byte numeroDeClasse1 = (byte) 1;
    private final String libelleDeClasse1 = "Comptes de capitaux";

    private final byte numeroDeClasse2 = (byte) 2;
    private final String libelleDeClasse2 = "Comptes d'immobilisations";

    @Test
    @DisplayName("Vérifie l'égalité de deux classes ayant le même numéro")
    void equals() {

        Classe classe1 = new Classe(numeroDeClasse1, libelleDeClasse1);
        Classe autreClasse1 = new Classe(numeroDeClasse1, "Autre libellé");

        assertEquals(classe1, autreClasse1);
    }

    @Test
    @DisplayName("Vérifie l'inégalité de deux classes ayant des numéros différents")
    void notEquals() {

        Classe classe1 = new Classe(numeroDeClasse1, libelleDeClasse1);
        Classe classe2 = new Classe(numeroDeClasse2, libelleDeClasse2);

        assertNotEquals(classe1, classe2);
    }

    @Test
    @DisplayName("Vérifie le contrat equals et hashCode dans un HashSet")
    void testHashCode() {

        Classe classe1 = new Classe(numeroDeClasse1, libelleDeClasse1);
        Classe autreClasse1 = new Classe(numeroDeClasse1, "Autre libellé");
        Classe classe2 = new Classe(numeroDeClasse2, libelleDeClasse2);

        Set<Classe> collection = new HashSet<>();

        assertEquals(0, collection.size());

        collection.add(classe1);
        assertEquals(1, collection.size());

        // Même clé métier : le Set ne doit pas contenir de doublon.
        collection.add(autreClasse1);
        assertEquals(1, collection.size());

        // Clé métier différente : l'élément doit être ajouté.
        collection.add(classe2);
        assertEquals(2, collection.size());
    }
}
