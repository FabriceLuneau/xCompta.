package fr.xcompta.core.base.test.compte;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import fr.xcompta.core.base.Compte;

/**
 * Teste l'identification des comptes de tiers par {@link Compte}.
 *
 * <p>
 * Les cas positifs sont volontairement limités aux comptes de tiers utilisés
 * par XCompta dans ces tests : fournisseurs ({@code 401} et {@code 404}) et
 * clients ({@code 411}), y compris un compte fournisseur individualisé.
 * </p>
 *
 * <p>
 * Des comptes appartenant aux autres classes sont également testés afin de
 * vérifier qu'ils ne sont pas identifiés comme comptes de tiers.
 * </p>
 *
 * @author Fabrice Luneau
 */
class CompteDeTiers {

    @ParameterizedTest(name = "compte {0} : compte de tiers = {1}")
    @CsvSource({
            "401, true",
            "404, true",
            "411, true",
            "401DUPONT, true",
            "101, false",
            "211, false",
            "370, false",
            "512, false",
            "607, false",
            "707, false"
    })
    @DisplayName("Vérifie l'identification des comptes de tiers")
    void estCompteDeTiers(
            final String numero,
            final boolean resultatAttendu) {

        Compte compte = new Compte(numero, "Compte de test");

        assertEquals(resultatAttendu, compte.estCompteDeTiers());
    }
}
