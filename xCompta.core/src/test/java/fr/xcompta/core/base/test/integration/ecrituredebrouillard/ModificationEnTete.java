package fr.xcompta.core.base.test.integration.ecrituredebrouillard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.xcompta.core.base.Compte;
import fr.xcompta.core.base.EcritureDeBrouillard;
import fr.xcompta.core.base.Journal;
import fr.xcompta.core.base.MouvementDeBrouillard;
import fr.xcompta.core.base.exception.XComptaCoreException;
import fr.xcompta.core.base.exception.XComptaCoreIllegalArgumentException;
import fr.xcompta.core.base.exception.XComptaCoreNullPointerException;

/**
 * Tests d'intégration de la modification de l'en-tête d'une écriture de
 * brouillard.
 *
 * <p>
 * Ces tests vérifient que la date, le journal et le libellé peuvent être
 * modifiés pendant la saisie et que la validité de l'écriture évolue en
 * conséquence.
 * </p>
 *
 * <p>
 * Une valeur {@code null} affectée à la date, au journal ou au libellé est
 * refusée par le setter correspondant. Dans ce cas, la valeur précédente est
 * conservée et l'écriture reste valide.
 * </p>
 *
 * <p>
 * Un libellé vide ou composé uniquement d'espaces est autorisé pendant la
 * saisie, mais rend l'écriture invalide tant qu'un libellé correct n'a pas été
 * renseigné.
 * </p>
 *
 * @author Fabrice Luneau
 */
class ModificationEnTete {

	private EcritureDeBrouillard ecriture;

	@BeforeEach
	void initialiser() throws XComptaCoreException {

		ecriture = new EcritureDeBrouillard();

		ecriture.setDate(LocalDate.of(2026, Month.AUGUST, 19));

		ecriture.setJournal(new Journal("AC", "Achats"));

		ecriture.setLibelle("Achat fournisseur");

		ecriture.ajouterMouvementALaFin(mouvement("607", "Achats", "100.00"));

		ecriture.ajouterMouvementALaFin(mouvement("401", "Fournisseurs", "-100.00"));

		assertTrue(ecriture.estValide());
	}

	@Test
	@DisplayName("La date d'une écriture de brouillard peut être modifiée")
	void modifierDate() throws XComptaCoreException {

		LocalDate nouvelleDate = LocalDate.of(2026, Month.SEPTEMBER, 1);

		ecriture.setDate(nouvelleDate);

		assertEquals(nouvelleDate, ecriture.getDate());

		assertTrue(ecriture.estValide());
	}

	@Test
	@DisplayName("Le journal d'une écriture de brouillard peut être modifié")
	void modifierJournal() throws XComptaCoreException {

		Journal nouveauJournal = new Journal("OD", "Opérations diverses");

		ecriture.setJournal(nouveauJournal);

		assertSame(nouveauJournal, ecriture.getJournal());

		assertTrue(ecriture.estValide());
	}

	@Test
	@DisplayName("Le libellé d'une écriture de brouillard peut être modifié")
	void modifierLibelle() throws XComptaCoreException {

		ecriture.setLibelle("Nouveau libellé");

		assertEquals("Nouveau libellé", ecriture.getLibelle());

		assertTrue(ecriture.estValide());
	}

	@Test
	@DisplayName("Une date null est refusée et la date précédente est conservée")
	void dateNull() throws XComptaCoreException {

		LocalDate ancienneDate = ecriture.getDate();

		assertThrows(XComptaCoreNullPointerException.class, () -> ecriture.setDate(null));

		assertEquals(ancienneDate, ecriture.getDate());

		assertTrue(ecriture.estValide());
	}

	@Test
	@DisplayName("Un journal null est refusé et le journal précédent est conservé")
	void journalNull() throws XComptaCoreException {

		Journal ancienJournal = ecriture.getJournal();

		assertThrows(XComptaCoreNullPointerException.class, () -> ecriture.setJournal(null));

		assertSame(ancienJournal, ecriture.getJournal());

		assertTrue(ecriture.estValide());
	}

	@Test
	@DisplayName("Un libellé null est refusé et le libellé précédent est conservé")
	void libelleNull() throws XComptaCoreException {

		String ancienLibelle = ecriture.getLibelle();

		assertThrows(XComptaCoreNullPointerException.class, () -> ecriture.setLibelle(null));

		assertEquals(ancienLibelle, ecriture.getLibelle());

		assertTrue(ecriture.estValide());
	}

	@Test
	@DisplayName("Un libellé vide rend l'écriture invalide")
	void libelleVide() throws XComptaCoreException {

		assertThrows(XComptaCoreIllegalArgumentException.class,
				() -> ecriture.setLibelle(""));

		//La modification a été rejetée l'Ecriture reste  inchangée et toujours valide
		assertTrue(ecriture.estValide());
	}

	@Test
	@DisplayName("Un libellé composé d'espaces rend l'écriture invalide")
	void libelleBlanc() throws XComptaCoreException {

		assertThrows(XComptaCoreIllegalArgumentException.class,
		() -> ecriture.setLibelle("   "));

		//La modification a été rejetée l'Ecriture reste  inchangée et toujours valide
		assertTrue(ecriture.estValide());
	}

	@Test
	@DisplayName("La correction d'un libellé vide rétablit la validité")
	void correctionEntete() throws XComptaCoreException {

		assertThrows(XComptaCoreIllegalArgumentException.class,
		() -> ecriture.setLibelle(""));

		//La modification a été rejetée l'Ecriture reste  inchangée et toujours valide
		assertTrue(ecriture.estValide());

		ecriture.setLibelle("Achat fournisseur corrigé");

		assertEquals("Achat fournisseur corrigé", ecriture.getLibelle());

		assertTrue(ecriture.estValide());
	}

	private MouvementDeBrouillard mouvement(String numeroCompte, String libelleCompte, String montant)
			throws XComptaCoreException {

		return new MouvementDeBrouillard(new BigDecimal(montant), new Compte(numeroCompte, libelleCompte), null);
	}
}
