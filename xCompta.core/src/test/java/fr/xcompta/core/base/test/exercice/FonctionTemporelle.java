package fr.xcompta.core.base.test.exercice;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.xcompta.core.base.Exercice;
import fr.xcompta.core.base.exception.XComptaCoreIllegalArgumentException;
import fr.xcompta.core.base.exception.XComptaCoreIllegalStateException;
import fr.xcompta.core.base.exception.XComptaCoreNullPointerException;

/** Vérifie l'appartenance temporelle et l'autorisation d'enregistrement. */
class FonctionTemporelle {

    private static final LocalDate DEBUT = LocalDate.of(2026, 1, 1);
    private static final LocalDate FIN = LocalDate.of(2026, 12, 31);

    @Test
    @DisplayName("Les bornes de l'exercice sont incluses")
    void appartenanceBornesIncluses() throws XComptaCoreIllegalArgumentException, XComptaCoreNullPointerException {
        Exercice exercice = new Exercice(DEBUT, FIN);
        assertTrue(exercice.appartient(DEBUT));
        assertTrue(exercice.appartient(LocalDate.of(2026, 6, 15)));
        assertTrue(exercice.appartient(FIN));
    }

    @Test
    @DisplayName("Les dates hors période et null n'appartiennent pas à l'exercice")
    void appartenanceHorsPeriode() throws XComptaCoreIllegalArgumentException, XComptaCoreNullPointerException {
        Exercice exercice = new Exercice(DEBUT, FIN);
        assertFalse(exercice.appartient(DEBUT.minusDays(1)));
        assertFalse(exercice.appartient(FIN.plusDays(1)));
        assertFalse(exercice.appartient(null));
    }

    @Test
    @DisplayName("L'enregistrement exige une date dans un exercice ouvert")
    void autorisationExerciceOuvert() throws XComptaCoreIllegalArgumentException, XComptaCoreNullPointerException {
        Exercice exercice = new Exercice(DEBUT, FIN);
        assertTrue(exercice.autoriseEnregistrement(DEBUT));
        assertTrue(exercice.autoriseEnregistrement(FIN));
        assertFalse(exercice.autoriseEnregistrement(DEBUT.minusDays(1)));
        assertFalse(exercice.autoriseEnregistrement(FIN.plusDays(1)));
        assertFalse(exercice.autoriseEnregistrement(null));
    }

    @Test
    @DisplayName("Une fermeture temporaire interdit l'enregistrement mais pas l'appartenance")
    void autorisationFermetureTemporaire() throws XComptaCoreIllegalArgumentException, XComptaCoreNullPointerException, XComptaCoreIllegalStateException {
        Exercice exercice = new Exercice(DEBUT, FIN);
        exercice.setOuvert(false);
        assertTrue(exercice.appartient(DEBUT));
        assertFalse(exercice.autoriseEnregistrement(DEBUT));
        exercice.setOuvert(true);
        assertTrue(exercice.autoriseEnregistrement(DEBUT));
    }

    @Test
    @DisplayName("Une clôture définitive interdit tout nouvel enregistrement")
    void autorisationApresCloture() throws XComptaCoreIllegalArgumentException, XComptaCoreNullPointerException, XComptaCoreIllegalStateException {
        Exercice exercice = new Exercice(DEBUT, FIN);
        exercice.cloturer(LocalDate.of(2027, 1, 15));
        assertTrue(exercice.appartient(FIN));
        assertFalse(exercice.autoriseEnregistrement(FIN));
    }
}
