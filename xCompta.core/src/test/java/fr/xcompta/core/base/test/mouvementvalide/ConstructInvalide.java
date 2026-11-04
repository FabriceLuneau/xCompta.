package fr.xcompta.core.base.test.mouvementvalide;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import fr.xcompta.core.base.Compte;
import fr.xcompta.core.base.MouvementValidee;
import fr.xcompta.core.base.exception.XComptaCoreNullPointerException;
import fr.xcompta.core.base.exception.mouvement.MouvementException;

/**
 * Teste les cas invalides de construction d'un objet
 * {@link MouvementValidee}.
 *
 * <p>
 * Un mouvement validé doit être cohérent dès sa construction.
 * Il doit posséder un identifiant technique strictement positif,
 * un compte non {@code null} et un montant non {@code null}
 * et différent de zéro.
 * </p>
 *
 * <p>
 * Un mouvement validé peut cependant être construit temporairement
 * sans écriture. Il est alors considéré comme détaché, en attente de
 * rattachement à une {@code EcritureValidee}.
 * </p>
 *
 * <p>
 * L'absence d'écriture n'est donc pas testée ici comme une erreur de
 * construction.
 * </p>
 *
 * @author Fabrice Luneau
 */
class ConstructInvalide {

    private static final Compte COMPTE =
            new Compte("607", "Achats de marchandises");

    private static final BigDecimal MONTANT =
            new BigDecimal("100");

    @ParameterizedTest
    @ValueSource(ints = { -1, -10, -100 })
    @DisplayName(
    		"L'identifiant d'un mouvement validé doit être positif ou nul.")
    void identifiantDoitEtreStrictementPositif(final int id) {

        assertThrows(
                MouvementException.class,
                () -> new MouvementValidee(
                        id,
                        MONTANT,
                        COMPTE));
    }

    @Test
    @DisplayName(
            "Vérifie qu'un mouvement validé ne peut pas avoir un compte null")
    void compteNePeutPasEtreNull() {

        assertThrows(
                XComptaCoreNullPointerException.class,
                () -> new MouvementValidee(
                        1,
                        MONTANT,
                        null));
    }

    @Test
    @DisplayName(
            "Vérifie qu'un mouvement validé ne peut pas avoir un montant null")
    void montantNePeutPasEtreNull() {

        assertThrows(
                MouvementException.class,
                () -> new MouvementValidee(
                        1,
                        null,
                        COMPTE));
    }

    @Test
    @DisplayName(
            "Vérifie qu'un mouvement validé ne peut pas avoir un montant égal à zéro")
    void montantNePeutPasEtreEgalAZero() {

        assertThrows(
                MouvementException.class,
                () -> new MouvementValidee(
                        1,
                        BigDecimal.ZERO,
                        COMPTE));
    }
}