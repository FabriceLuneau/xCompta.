package fr.xcompta.core.base.test.mouvementvalide;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import fr.xcompta.core.base.Compte;
import fr.xcompta.core.base.MouvementValidee;
import fr.xcompta.core.base.exception.XComptaCoreNullPointerException;
import fr.xcompta.core.base.exception.mouvement.MouvementException;

/**
 * Teste les cas nominaux de construction d'un objet
 * {@link MouvementValidee}.
 *
 * <p>
 * Un mouvement validé peut être construit temporairement en mode détaché,
 * c'est-à-dire sans référence à une écriture validée.
 * </p>
 *
 * <p>
 * Il doit néanmoins posséder toutes ses informations comptables : un compte
 * et un montant non nul et différent de zéro.
 * </p>
 *
 * <p>
 * Deux états de persistance sont testés :
 * </p>
 *
 * <ul>
 * <li>un mouvement détaché dont l'identifiant vaut {@code 0}, en attente
 * de l'attribution d'un identifiant par la couche de persistance ;</li>
 * <li>un mouvement détaché dont l'identifiant est strictement positif,
 * lorsque cet identifiant technique est déjà connu.</li>
 * </ul>
 *
 * <p>
 * Dans les deux cas, la référence à l'écriture reste {@code null}. Le
 * rattachement sera effectué ultérieurement lors de la reconstruction
 * de l'écriture validée.
 * </p>
 *
 * @author Fabrice Luneau
 */
class Construct3Args {

    /**
     * Fournit différents mouvements valides possédant déjà un identifiant
     * technique.
     *
     * @return les identifiants, comptes et montants utilisés pour les tests
     */
    static Stream<Arguments> mouvementsValidesAvecId() {
        return Stream.of(
                Arguments.of(
                        1,
                        new Compte(
                                "607",
                                "Achats de marchandises"),
                        new BigDecimal("100")),
                Arguments.of(
                        2,
                        new Compte(
                                "44566",
                                "TVA sur ABS"),
                        new BigDecimal("20")),
                Arguments.of(
                        3,
                        new Compte(
                                "401001",
                                "Fournisseur A"),
                        new BigDecimal("-120")));
    }

    /**
     * Fournit différents mouvements valides en attente de l'attribution
     * de leur identifiant technique.
     *
     * @return les comptes et montants utilisés pour les tests
     */
    static Stream<Arguments> mouvementsValidesSansId() {
        return Stream.of(
                Arguments.of(
                        new Compte(
                                "607",
                                "Achats de marchandises"),
                        new BigDecimal("100")),
                Arguments.of(
                        new Compte(
                                "44566",
                                "TVA sur ABS"),
                        new BigDecimal("20")),
                Arguments.of(
                        new Compte(
                                "401001",
                                "Fournisseur A"),
                        new BigDecimal("-120")));
    }

    /**
     * Vérifie la construction d'un mouvement validé détaché qui n'a pas
     * encore reçu son identifiant de la couche de persistance.
     *
     * @param compte  compte comptable du mouvement
     * @param montant montant signé du mouvement
     *
     * @throws MouvementException si le mouvement ne respecte pas les règles
     *                            métier
     * @throws XComptaCoreNullPointerException 
     */
    @ParameterizedTest
    @MethodSource("mouvementsValidesSansId")
    @DisplayName(
            "Vérifie la construction d'un mouvement détaché avec un id à 0")
    void constructeurMouvementDetacheSansId(
            Compte compte,
            BigDecimal montant)
            throws MouvementException, XComptaCoreNullPointerException {

        MouvementValidee mouvement =
                new MouvementValidee(
                        0,
                        montant,
                        compte);

        assertNotNull(mouvement);
        assertEquals(compte, mouvement.getCompte());
        assertEquals(montant, mouvement.getMontant());
        assertEquals(0, mouvement.getId());
        assertNull(mouvement.getEcriture());
    }

    /**
     * Vérifie la construction d'un mouvement validé détaché dont
     * l'identifiant technique est déjà connu.
     *
     * @param id      identifiant technique du mouvement
     * @param compte  compte comptable du mouvement
     * @param montant montant signé du mouvement
     *
     * @throws MouvementException si le mouvement ne respecte pas les règles
     *                            métier
     * @throws XComptaCoreNullPointerException 
     */
    @ParameterizedTest
    @MethodSource("mouvementsValidesAvecId")
    @DisplayName(
            "Vérifie la construction d'un mouvement détaché avec un id")
    void constructeurMouvementDetacheAvecId(
            int id,
            Compte compte,
            BigDecimal montant)
            throws MouvementException, XComptaCoreNullPointerException {

        MouvementValidee mouvement =
                new MouvementValidee(
                        id,
                        montant,
                        compte);

        assertNotNull(mouvement);
        assertEquals(compte, mouvement.getCompte());
        assertEquals(montant, mouvement.getMontant());
        assertEquals(id, mouvement.getId());
        assertNull(mouvement.getEcriture());
    }
}