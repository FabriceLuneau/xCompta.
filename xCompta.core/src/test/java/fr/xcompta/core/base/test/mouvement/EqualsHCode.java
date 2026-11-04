package fr.xcompta.core.base.test.mouvement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.xcompta.core.base.Compte;
import fr.xcompta.core.base.Mouvement;
import fr.xcompta.core.base.MouvementDeBrouillard;
import fr.xcompta.core.base.MouvementValidee;
import fr.xcompta.core.base.exception.XComptaCoreNullPointerException;
import fr.xcompta.core.base.exception.mouvement.MouvementException;

/**
 * Teste le contrat commun d'égalité et d'équivalence comptable défini par
 * {@link Mouvement}.
 *
 * <p>
 * {@link Mouvement#equals(Object)} compare l'état propre d'un mouvement :
 * classe concrète, identifiant, compte et montant. Le rattachement à une
 * écriture n'intervient pas dans cette égalité.
 * </p>
 *
 * <p>
 * {@link Mouvement#estEquivalent(Mouvement)} compare uniquement le contenu
 * comptable : compte et montant. Deux mouvements de types ou d'identifiants
 * différents peuvent donc être équivalents sans être égaux.
 * </p>
 *
 * @author Fabrice Luneau
 */
class EqualsHCode {

    private Compte compte1;
    private Compte compte2;
    private BigDecimal montant1;
    private BigDecimal montant2;

    private MouvementDeBrouillard brouillard;
    private MouvementValidee valideMemeContenu;
    private MouvementValidee valideAutreId;
    private MouvementDeBrouillard brouillardAutreMontant;
    private MouvementDeBrouillard brouillardAutreCompte;

    /**
     * Initialise les mouvements utilisés par les tests.
     *
     * @throws MouvementException si un mouvement validé ne peut pas être construit
     * @throws XComptaCoreNullPointerException 
     */
    @BeforeEach
    void setupDatas() throws MouvementException, XComptaCoreNullPointerException {
        compte1 = new Compte("512", "Banque");
        compte2 = new Compte("531", "Caisse principale");
        montant1 = new BigDecimal("123");
        montant2 = new BigDecimal("-42");

        brouillard = new MouvementDeBrouillard(compte1, montant1);
        valideMemeContenu = new MouvementValidee(0, montant1, compte1);
        valideAutreId = new MouvementValidee(42, montant1, compte1);
        brouillardAutreMontant = new MouvementDeBrouillard(compte1, montant2);
        brouillardAutreCompte = new MouvementDeBrouillard(compte2, montant1);
    }

    @Test
    @DisplayName("Vérifie la réflexivité de equals")
    void equalsEstReflexif() {
        assertEquals(brouillard, brouillard);
        assertEquals(valideMemeContenu, valideMemeContenu);
    }

    @Test
    @DisplayName("Vérifie que deux types concrets différents ne sont pas égaux")
    void equalsTientCompteDuTypeConcret() {
        assertNotEquals(brouillard, valideMemeContenu);
        assertNotEquals(valideMemeContenu, brouillard);
    }

    @Test
    @DisplayName("Vérifie que l'identifiant participe à equals")
    void equalsTientCompteDeIdentifiant() {
        assertNotEquals(valideMemeContenu, valideAutreId);
    }

    @Test
    @DisplayName("Vérifie l'équivalence comptable entre brouillard et validé")
    void estEquivalentIgnoreTypeEtIdentifiant() {
        assertTrue(brouillard.estEquivalent(valideMemeContenu));
        assertTrue(valideMemeContenu.estEquivalent(brouillard));
        assertTrue(brouillard.estEquivalent(valideAutreId));
        assertTrue(valideAutreId.estEquivalent(brouillard));
    }

    @Test
    @DisplayName("Vérifie que l'équivalence comptable compare le montant")
    void estEquivalentTientCompteDuMontant() {
        assertFalse(brouillard.estEquivalent(brouillardAutreMontant));
    }

    @Test
    @DisplayName("Vérifie que l'équivalence comptable compare le compte")
    void estEquivalentTientCompteDuCompte() {
        assertFalse(brouillard.estEquivalent(brouillardAutreCompte));
    }

    @Test
    @DisplayName("Vérifie que l'équivalence comptable ignore l'échelle BigDecimal")
    void estEquivalentIgnoreEchelleBigDecimal() throws MouvementException, XComptaCoreNullPointerException {
        Mouvement autre = new MouvementValidee(
                99,
                new BigDecimal("123.000"),
                compte1);

        assertTrue(brouillard.estEquivalent(autre));
    }

    @Test
    @DisplayName("Vérifie que estEquivalent(null) retourne false")
    void estEquivalentNull() {
        assertFalse(brouillard.estEquivalent(null));
    }

    @Test
    @DisplayName("Vérifie le contrat equals et hashCode avec des types mélangés")
    void hashSetRespecteEquals() {
        Set<Mouvement> collection = new HashSet<>();

        collection.add(brouillard);
        collection.add(new MouvementDeBrouillard(compte1, new BigDecimal("123.00")));
        collection.add(valideMemeContenu);
        collection.add(valideAutreId);

        assertEquals(3, collection.size());
    }
}
