package fr.xcompta.core.base.test.exercice;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.xcompta.core.base.Exercice;
import fr.xcompta.core.base.exception.XComptaCoreIllegalArgumentException;
import fr.xcompta.core.base.exception.XComptaCoreNullPointerException;

/** Vérifie le rejet des arguments invalides à la construction. */
class ConstructInvalide {

	private static final LocalDate DEBUT = LocalDate.of(2026, 1, 1);
	private static final LocalDate FIN = LocalDate.of(2026, 12, 31);

	@Test
	@DisplayName("Une date de début nulle est refusée")
	void debutNull() {
		assertThrows(XComptaCoreNullPointerException.class, () -> new Exercice(null, FIN));
	}

	@Test
	@DisplayName("Une date de fin nulle est refusée")
	void finNull() {
		assertThrows(XComptaCoreNullPointerException.class, () -> new Exercice(DEBUT, null));
	}

	@Test
	@DisplayName("Une fin antérieure au début est refusée")
	void finAvantDebut() {
		assertThrows(XComptaCoreIllegalArgumentException.class, () -> new Exercice(FIN, DEBUT));
	}

	@Test
	@DisplayName("Un identifiant négatif est refusé")
	void identifiantNegatif() {
		assertThrows(XComptaCoreIllegalArgumentException.class, () -> new Exercice(-1, DEBUT, FIN));
	}
}
