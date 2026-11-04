package fr.xcompta.core.base.test.mouvementdebrouillard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.xcompta.core.base.Compte;
import fr.xcompta.core.base.MouvementDeBrouillard;
import fr.xcompta.core.base.exception.mouvement.MouvementException;

/**
 * Teste {@link MouvementDeBrouillard#equals(Object)}, son contrat avec
 * {@link MouvementDeBrouillard#hashCode()} et la distinction avec
 * l'équivalence comptable.
 *
 * <p>
 * Deux mouvements de brouillard sont égaux s'ils ont le même identifiant,
 * le même compte et le même montant. L'identifiant participe donc à
 * {@code equals()} et à {@code hashCode()}.
 * </p>
 *
 * @author Fabrice Luneau
 */
class EqualsHasCode {

    private Compte compte1;
    private Compte compte2;
    private BigDecimal montant1;
    private BigDecimal montant2;

    private MouvementDeBrouillard mouvement1;
    private MouvementDeBrouillard mouvement1b;
    private MouvementDeBrouillard mouvement2;
    private MouvementDeBrouillard mouvement3;

    @BeforeEach
    void setupDatas() {
        compte1 = new Compte("512", "Banque");
        compte2 = new Compte("531", "Caisse principale");
        montant1 = new BigDecimal("123");
        montant2 = new BigDecimal("-42");

        mouvement1 = new MouvementDeBrouillard(compte1, montant1);
        mouvement1b = new MouvementDeBrouillard(compte1, montant1);
        mouvement2 = new MouvementDeBrouillard(compte1, montant2);
        mouvement3 = new MouvementDeBrouillard(compte2, montant1);
    }

    @Test
    @DisplayName("Vérifie l'égalité de deux mouvements de brouillard de même état")
    void equalsMemeEtat() {
        assertEquals(mouvement1, mouvement1b);
        assertEquals(mouvement1.hashCode(), mouvement1b.hashCode());
    }

    @Test
    @DisplayName("Vérifie que l'identifiant participe à equals")
    void equalsTientCompteDeIdentifiant() throws MouvementException {
        MouvementDeBrouillard mouvementAvecId =
                new MouvementDeBrouillard(compte1, montant1);
        mouvementAvecId.setId(42);

        assertNotEquals(mouvement1, mouvementAvecId);
        assertTrue(mouvement1.estEquivalent(mouvementAvecId));
    }

    @Test
    @DisplayName("Vérifie l'inégalité lorsque les montants diffèrent")
    void notEqualsMontantsDifferents() {
        assertNotEquals(mouvement1, mouvement2);
    }

    @Test
    @DisplayName("Vérifie l'inégalité lorsque les comptes diffèrent")
    void notEqualsComptesDifferents() {
        assertNotEquals(mouvement1, mouvement3);
    }

    @Test
    @DisplayName("Vérifie que equals ignore l'échelle BigDecimal")
    void equalsIgnoreEchelleBigDecimal() {
        MouvementDeBrouillard autre = new MouvementDeBrouillard(
                compte1,
                new BigDecimal("123.00"));

        assertEquals(mouvement1, autre);
        assertEquals(mouvement1.hashCode(), autre.hashCode());
    }

    @Test
    @DisplayName("Vérifie le contrat equals et hashCode dans un HashSet")
    void hashSetRespecteEquals() throws MouvementException {
        Set<MouvementDeBrouillard> collection = new HashSet<>();
        MouvementDeBrouillard mouvementAvecId =
                new MouvementDeBrouillard(compte1, montant1);
        mouvementAvecId.setId(42);

        collection.add(mouvement1);
        collection.add(mouvement1b);
        collection.add(mouvementAvecId);
        collection.add(mouvement2);
        collection.add(mouvement3);

        assertEquals(4, collection.size());
    }
}
