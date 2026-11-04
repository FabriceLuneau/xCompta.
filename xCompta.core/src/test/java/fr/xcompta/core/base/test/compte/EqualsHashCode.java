package fr.xcompta.core.base.test.compte;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.xcompta.core.base.Compte;

/**
 * Teste l'identité métier de {@link Compte}, ainsi que le contrat entre
 * {@link Compte#equals(Object)} et {@link Compte#hashCode()}.
 *
 * <p>
 * L'identité métier d'un compte comptable repose uniquement sur son numéro
 * canonique. Deux instances possédant le même numéro sont donc considérées
 * comme égales, même si leurs libellés sont différents.
 * </p>
 *
 * @author Fabrice Luneau
 */
class EqualsHashCode {

    private final String numeroDeCompte1 = "101";
    private final String libelleDeCompte1 = "Capital";

    private final String numeroDeCompte2 = "607";
    private final String libelleDeCompte2 = "Achats de marchandises";

    @Test
    @DisplayName("Vérifie l'égalité de deux comptes ayant le même numéro")
    void equals() {

        Compte compte1 = new Compte(numeroDeCompte1, libelleDeCompte1);
        Compte autreCompte1 = new Compte(numeroDeCompte1, "Autre libellé");

        assertEquals(compte1, autreCompte1);
    }

    @Test
    @DisplayName("Vérifie l'égalité de deux comptes ayant le même numéro canonique")
    void equalsNumeroCanonique() {

        Compte compte1 = new Compte("607", "Achats");
        Compte compte2 = new Compte("607000", "Autre libellé");

        assertEquals(compte1, compte2);
        assertEquals(compte1.hashCode(), compte2.hashCode());
    }

    @Test
    @DisplayName("Vérifie plusieurs équivalences de numéros numériques canoniques")
    void equalsPlusieursNumerosCanoniques() {

        assertEquals(
                new Compte("530", "Caisse"),
                new Compte("530000", "Autre libellé"));

        assertEquals(
                new Compte("401", "Fournisseurs"),
                new Compte("401000", "Autre libellé"));
    }

    @Test
    @DisplayName("Vérifie que des subdivisions de comptes restent distinctes")
    void notEqualsSubdivisions() {

        assertNotEquals(
                new Compte("401", "Fournisseurs"),
                new Compte("401001", "Fournisseur 1"));

        assertNotEquals(
                new Compte("401", "Fournisseurs"),
                new Compte("401DUPONT", "Fournisseur Dupont"));
    }

    @Test
    @DisplayName("Vérifie l'inégalité de deux comptes ayant des numéros différents")
    void notEquals() {

        Compte compte1 = new Compte(numeroDeCompte1, libelleDeCompte1);
        Compte compte2 = new Compte(numeroDeCompte2, libelleDeCompte2);

        assertNotEquals(compte1, compte2);
    }

    @Test
    @DisplayName("Vérifie les cas particuliers du contrat equals")
    void equalsCasParticuliers() {

        Compte compte = new Compte(numeroDeCompte1, libelleDeCompte1);

        assertEquals(compte, compte);
        assertNotEquals(compte, null);
        assertNotEquals(compte, numeroDeCompte1);
    }

    @Test
    @DisplayName("Vérifie le contrat equals et hashCode dans un HashSet")
    void testHashCode() {

        Compte compte1 = new Compte(numeroDeCompte1, libelleDeCompte1);
        Compte autreCompte1 = new Compte(numeroDeCompte1, "Autre libellé");
        Compte compte2 = new Compte(numeroDeCompte2, libelleDeCompte2);

        Set<Compte> collection = new HashSet<>();

        assertEquals(0, collection.size());

        collection.add(compte1);
        assertEquals(1, collection.size());

        // Même clé métier : le Set ne doit pas contenir de doublon.
        collection.add(autreCompte1);
        assertEquals(1, collection.size());

        // Clé métier différente : l'élément doit être ajouté.
        collection.add(compte2);
        assertEquals(2, collection.size());
    }
}
