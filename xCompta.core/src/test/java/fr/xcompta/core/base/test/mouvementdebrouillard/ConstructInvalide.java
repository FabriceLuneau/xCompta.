package fr.xcompta.core.base.test.mouvementdebrouillard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import fr.xcompta.core.base.MouvementDeBrouillard;
import fr.xcompta.core.base.exception.mouvement.MouvementIdViolationContrainteException;

/**
 * Teste les violations de contraintes propres à un
 * {@link MouvementDeBrouillard}.
 *
 * @author Fabrice Luneau
 */
class ConstructInvalide {

    @Test
    @DisplayName("Vérifie qu'un identifiant ne peut être attribué qu'une seule fois")
    void identifiantNePeutEtreAttribueQuUneFois()
            throws MouvementIdViolationContrainteException {

        MouvementDeBrouillard mouvement = new MouvementDeBrouillard();

        mouvement.setId(42);
        assertEquals(42, mouvement.getId());

        assertThrows(
                MouvementIdViolationContrainteException.class,
                () -> mouvement.setId(43));
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, -1, -10, -100 })
    @DisplayName("Vérifie qu'un identifiant attribué doit être strictement positif")
    void identifiantDoitEtreStrictementPositif(final int id) {

        MouvementDeBrouillard mouvement = new MouvementDeBrouillard();

        assertThrows(
                MouvementIdViolationContrainteException.class,
                () -> mouvement.setId(id));
    }
}
