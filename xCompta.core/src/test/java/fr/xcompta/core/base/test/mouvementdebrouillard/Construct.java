package fr.xcompta.core.base.test.mouvementdebrouillard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import fr.xcompta.core.base.Compte;
import fr.xcompta.core.base.MouvementDeBrouillard;

/**
 * Teste les cas nominaux de construction d'un objet
 * {@link MouvementDeBrouillard}.
 *
 * <p>
 * Un mouvement de brouillard peut être construit sans argument afin de
 * permettre une saisie progressive de ses informations.
 * </p>
 *
 * <p>
 * Les tests vérifient également la construction directe avec un compte
 * et un montant, aussi bien pour les mouvements débiteurs que créditeurs.
 * </p>
 *
 * @author Fabrice Luneau
 */
class Construct {

    /**
     * Fournit différents mouvements valides pour les tests paramétrés.
     *
     * @return les comptes et montants utilisés pour les tests
     */
    static Stream<Arguments> mouvementsValides() {
        return Stream.of(
                Arguments.of(
                        new Compte("607", "Achats de marchandises"),
                        new BigDecimal("100")),
                Arguments.of(
                        new Compte("44566", "TVA sur ABS"),
                        new BigDecimal("20")),
                Arguments.of(
                        new Compte("401001", "Fournisseur A"),
                        new BigDecimal("-120")));
    }

    @Test
    @DisplayName("Vérifie que le constructeur sans argument retourne un objet")
    void constructeurSansArguments() {

        MouvementDeBrouillard mouvement = new MouvementDeBrouillard();

        assertNotNull(mouvement);
        assertEquals(0, mouvement.getId());
		assertNull(mouvement.getCompte());
        assertEquals(BigDecimal.ZERO, mouvement.getMontant());
        assertNull(mouvement.getEcriture());
    }
    
    @ParameterizedTest
    @MethodSource("mouvementsValides")
    @DisplayName("Vérifie l'affectation du compte et du montant avec les setters")
    void constructeurSansArgumentsPuisSetters(
            Compte compte,
            BigDecimal montant) {

        MouvementDeBrouillard mouvement = new MouvementDeBrouillard();

        mouvement.setCompte(compte);
        mouvement.setMontant(montant);

        assertEquals(compte, mouvement.getCompte());
        assertEquals(montant, mouvement.getMontant());
        assertEquals(0, mouvement.getId());
    }
    
    @ParameterizedTest
    @MethodSource("mouvementsValides")
    @DisplayName("Vérifie l'affectation du compte et du montant par le constructeur")
    void constructeurAvecArguments(
            Compte compte,
            BigDecimal montant) {

        MouvementDeBrouillard mouvement =
                new MouvementDeBrouillard(compte, montant);

        assertNotNull(mouvement);
        assertEquals(compte, mouvement.getCompte());
        assertEquals(montant, mouvement.getMontant());
        assertEquals(0, mouvement.getId());
    }
}