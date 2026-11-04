package fr.xcompta.core.base.test.exercice;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.xcompta.core.base.Exercice;
import fr.xcompta.core.base.exception.XComptaCoreIllegalArgumentException;
import fr.xcompta.core.base.exception.XComptaCoreNullPointerException;

/** Vérifie la construction et les accesseurs d'un exercice valide. */
class Construct2Arg {

    private static final LocalDate DEBUT = LocalDate.of(2026, 1, 1);
    private static final LocalDate FIN = LocalDate.of(2026, 12, 31);

    @Test
    @DisplayName("Le constructeur à deux dates crée l'exercice zéro, ouvert")
    void constructeurPremierExercice() throws XComptaCoreIllegalArgumentException, XComptaCoreNullPointerException {
        Exercice exercice = new Exercice(DEBUT, FIN);
        assertAll(
                () -> assertEquals(0, exercice.getId()),
                () -> assertEquals(DEBUT, exercice.getDateDebut()),
                () -> assertEquals(FIN, exercice.getDateFin()),
                () -> assertNull(exercice.getDateCloture()),
                () -> assertTrue(exercice.isOuvert()),
                () -> assertTrue(exercice.estOuvert()),
                () -> assertFalse(exercice.isCloture()));
    }

    @Test
    @DisplayName("Le constructeur à identifiant explicite conserve l'identifiant")
    void constructeurAvecIdentifiant() throws XComptaCoreIllegalArgumentException, XComptaCoreNullPointerException {
        Exercice exercice = new Exercice(2, DEBUT, FIN);
        assertEquals(2, exercice.getId());
    }

    @Test
    @DisplayName("Un exercice d'un seul jour est autorisé")
    void debutEgalFin() throws XComptaCoreIllegalArgumentException, XComptaCoreNullPointerException {
        Exercice exercice = new Exercice(1, DEBUT, DEBUT);
        assertEquals(DEBUT, exercice.getDateFin());
        assertTrue(exercice.appartient(DEBUT));
    }
}
