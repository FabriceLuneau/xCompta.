package fr.xcompta.core.base.test.integration.ecrituredebrouillard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.xcompta.core.base.Compte;
import fr.xcompta.core.base.EcritureDeBrouillard;
import fr.xcompta.core.base.MouvementDeBrouillard;
import fr.xcompta.core.base.exception.XComptaCoreException;

/**
 * Tests d'intégration de la modification de la collection de mouvements d'une
 * écriture de brouillard.
 *
 * <p>
 * Ces tests couvrent le remplacement de la collection, la suppression d'un
 * mouvement et le nettoyage des mouvements vides.
 * </p>
 *
 * @author Fabrice Luneau
 */
class ModificationMouvement {

	@Test
	@DisplayName("Un mouvement peut être supprimé par son index")
	void supprimerParIndex() throws XComptaCoreException {

		EcritureDeBrouillard ecriture = new EcritureDeBrouillard();

		MouvementDeBrouillard premier = mouvement("607", "Achats", "100.00");

		MouvementDeBrouillard second = mouvement("401", "Fournisseurs", "-100.00");

		ecriture.ajouterMouvementALaFin(premier);

		ecriture.ajouterMouvementALaFin(second);

		ecriture.supprimerMouvement(0);

		assertEquals(1, ecriture.getMouvements().size());

		assertSame(second, ecriture.getMouvements().get(0));
	}

	@Test
	@DisplayName("Un mouvement présent peut être supprimé par référence")
	void supprimerParReference() throws XComptaCoreException {

		EcritureDeBrouillard ecriture = new EcritureDeBrouillard();

		MouvementDeBrouillard premier = mouvement("607", "Achats", "100.00");

		MouvementDeBrouillard second = mouvement("401", "Fournisseurs", "-100.00");

		ecriture.ajouterMouvementALaFin(premier);

		ecriture.ajouterMouvementALaFin(second);

		assertTrue(ecriture.supprimerMouvement(premier));

		assertEquals(1, ecriture.getMouvements().size());

		assertSame(second, ecriture.getMouvements().get(0));
	}

	@Test
	@DisplayName("Supprimer un mouvement absent ne modifie pas l'écriture")
	void supprimerMouvementAbsent() throws XComptaCoreException {

		EcritureDeBrouillard ecriture = new EcritureDeBrouillard();

		MouvementDeBrouillard present = mouvement("607", "Achats", "100.00");

		MouvementDeBrouillard absent = mouvement("401", "Fournisseurs", "-100.00");

		ecriture.ajouterMouvementALaFin(present);

		assertFalse(ecriture.supprimerMouvement(absent));

		assertEquals(1, ecriture.getMouvements().size());

		assertSame(present, ecriture.getMouvements().get(0));
	}

	@Test
	@DisplayName("Les mouvements vides peuvent être supprimés")
	void supprimerMouvementsVides() throws XComptaCoreException {

		EcritureDeBrouillard ecriture = new EcritureDeBrouillard();

		MouvementDeBrouillard debit = mouvement("607", "Achats", "100.00");

		MouvementDeBrouillard vide1 = new MouvementDeBrouillard();

		MouvementDeBrouillard credit = mouvement("401", "Fournisseurs", "-100.00");

		MouvementDeBrouillard vide2 = new MouvementDeBrouillard();

		ecriture.ajouterMouvementALaFin(debit);

		ecriture.ajouterMouvementALaFin(vide1);

		ecriture.ajouterMouvementALaFin(credit);

		ecriture.ajouterMouvementALaFin(vide2);

		ecriture.supprimerMouvementsVides();

		assertEquals(2, ecriture.getMouvements().size());

		assertSame(debit, ecriture.getMouvements().get(0));

		assertSame(credit, ecriture.getMouvements().get(1));
	}

	private MouvementDeBrouillard mouvement(String numeroCompte, String libelleCompte, String montant)
			throws XComptaCoreException {

		return new MouvementDeBrouillard(new BigDecimal(montant), new Compte(numeroCompte, libelleCompte), null);
	}
}
