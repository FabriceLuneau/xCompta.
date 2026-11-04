package fr.xcompta.core.base.test.exercice;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.xcompta.core.base.Exercice;
import fr.xcompta.core.base.exception.XComptaCoreIllegalArgumentException;
import fr.xcompta.core.base.exception.XComptaCoreIllegalStateException;
import fr.xcompta.core.base.exception.XComptaCoreNullPointerException;

/** Vérifie la fermeture temporaire et la clôture définitive. */
class MutationEtat {

	private static final LocalDate DEBUT = LocalDate.of(2026, 1, 1);
	private static final LocalDate FIN = LocalDate.of(2026, 12, 31);
	private static final LocalDate CLOTURE = LocalDate.of(2027, 2, 15);

	@Test
	@DisplayName("La fermeture temporaire permet une réouverture")
	void fermetureTemporairePuisReouverture() throws XComptaCoreIllegalStateException,
			XComptaCoreIllegalArgumentException, XComptaCoreNullPointerException {
		Exercice exercice = new Exercice(DEBUT, FIN);
		exercice.setOuvert(false);
		assertFalse(exercice.isOuvert());
		assertFalse(exercice.estOuvert());
		assertFalse(exercice.isCloture());
		assertNull(exercice.getDateCloture());

		exercice.setOuvert(true);
		assertTrue(exercice.isOuvert());
		assertFalse(exercice.isCloture());
	}

	@Test
	@DisplayName("La clôture définitive ferme l'exercice et conserve sa date")
	void clotureDefinitive() throws XComptaCoreIllegalArgumentException, XComptaCoreNullPointerException,
			XComptaCoreIllegalStateException {
		Exercice exercice = new Exercice(DEBUT, FIN);
		exercice.cloturer(CLOTURE);
		assertAll(() -> assertTrue(exercice.isCloture()), () -> assertFalse(exercice.isOuvert()),
				() -> assertEquals(CLOTURE, exercice.getDateCloture()));
		assertThrows(XComptaCoreIllegalStateException.class, () -> exercice.setOuvert(true));
		assertFalse(exercice.isOuvert());
		assertEquals(CLOTURE, exercice.getDateCloture());
	}

	@Test
	@DisplayName("Un exercice déjà clôturé ne peut pas être clôturé une seconde fois")
	void doubleCloture() throws XComptaCoreIllegalArgumentException, XComptaCoreNullPointerException,
			XComptaCoreIllegalStateException {
		Exercice exercice = new Exercice(DEBUT, FIN);
		exercice.cloturer(CLOTURE);
		assertThrows(XComptaCoreIllegalStateException.class, () -> exercice.cloturer(CLOTURE.plusDays(1)));
		assertEquals(CLOTURE, exercice.getDateCloture());
	}

	@Test
	@DisplayName("Une date de clôture nulle est refusée sans changer l'état")
	void dateClotureNull() throws XComptaCoreIllegalArgumentException, XComptaCoreNullPointerException {
		Exercice exercice = new Exercice(DEBUT, FIN);
		assertThrows(XComptaCoreNullPointerException.class, () -> exercice.cloturer(null));
		assertTrue(exercice.isOuvert());
		assertFalse(exercice.isCloture());
	}

	@Test
	@DisplayName("Un exercice fermé temporairement peut être clôturé définitivement")
	void clotureApresFermetureTemporaire() throws XComptaCoreIllegalStateException, XComptaCoreIllegalArgumentException,
			XComptaCoreNullPointerException {
		Exercice exercice = new Exercice(DEBUT, FIN);
		exercice.setOuvert(false);
		exercice.cloturer(CLOTURE);
		assertTrue(exercice.isCloture());
		assertFalse(exercice.isOuvert());
	}
}
