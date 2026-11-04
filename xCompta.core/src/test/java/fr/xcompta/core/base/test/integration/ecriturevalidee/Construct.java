package fr.xcompta.core.base.test.integration.ecriturevalidee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import fr.xcompta.core.base.Compte;
import fr.xcompta.core.base.EcritureValidee;
import fr.xcompta.core.base.Journal;
import fr.xcompta.core.base.MouvementValidee;
import fr.xcompta.core.base.exception.XComptaCoreException;
import fr.xcompta.core.base.exception.XComptaCoreNullPointerException;
import fr.xcompta.core.base.exception.mouvement.MouvementException;
import fr.xcompta.core.base.test.integration.ecriturevalidee.JeuDonneesEcritureValidee.DonneesEcriture;
import fr.xcompta.core.base.test.integration.ecriturevalidee.JeuDonneesEcritureValidee.DonneesMouvement;

/**
 * Tests d'intégration des constructions nominales d'une
 * {@link EcritureValidee}.
 *
 * <p>
 * Le jeu de données vérifie la reconstruction complète d'une écriture validée à
 * partir des informations provenant de la persistance. Le test contrôle
 * notamment les identifiants de l'écriture et de ses mouvements, les comptes,
 * les montants, l'ordre des mouvements et leur rattachement à l'écriture.
 * </p>
 *
 * @author Fabrice Luneau
 */
class Construct {
    /*
     * TODO tests nominaux encore à ajouter :
     * - vérifier la copie défensive de la collection passée au constructeur ;
     * - vérifier que getMouvements() retourne une collection non modifiable ;
     * - vérifier contrepasser() sur une écriture validée ;
     * - vérifier estEquivalente() entre une écriture validée et un brouillard
     *   représentant les mêmes comptes et montants dans un ordre différent ;
     * - vérifier qu'une double contrepassation est comptablement équivalente
     *   à l'écriture d'origine.
     */


	private static final String SOURCE = "fr.xcompta.core.base.test.integration.ecriturevalidee."
			+ "JeuDonneesEcritureValidee#fournirEcritures";

	@ParameterizedTest(name = "[{index}] {0}")
    @MethodSource(SOURCE)
    @DisplayName("Reconstruction complète d'une écriture validée")
    void constructeurAvecArguments(final DonneesEcriture donnees)
            throws XComptaCoreException 	 {

        Journal journal = creerJournal(donnees);

        List<MouvementValidee> mouvements = new ArrayList<>();

        for (DonneesMouvement donneesMouvement : donnees.mouvements()) {
            mouvements.add(creerMouvement(donneesMouvement));
        }
        
        EcritureValidee ecriture = new EcritureValidee(donnees.id(), donnees.libelle(), donnees.date(), journal, mouvements);

        verifierEcriture(ecriture, donnees);

        assertTrue(ecriture.estValide());
    }

	private static Journal creerJournal(final DonneesEcriture donnees) {

		return new Journal(donnees.codeJournal(), donnees.libelleJournal());
	}

	private static MouvementValidee creerMouvement(final DonneesMouvement donnees) throws MouvementException, XComptaCoreNullPointerException {

		return new MouvementValidee(donnees.id(), new BigDecimal(donnees.montant()),
				new Compte(donnees.numeroCompte(), donnees.libelleCompte()));
	}

	private static void verifierEcriture(final EcritureValidee ecriture, final DonneesEcriture donnees) {

		assertNotNull(ecriture);
		assertEquals(donnees.id(), ecriture.getId());
		assertEquals(donnees.date(), ecriture.getDate());
		assertEquals(donnees.libelle(), ecriture.getLibelle());

		assertNotNull(ecriture.getJournal());
		assertEquals(donnees.codeJournal(), ecriture.getJournal().code());
		assertEquals(donnees.libelleJournal(), ecriture.getJournal().libelle());

		assertEquals(donnees.mouvements().size(), ecriture.size());

		List<MouvementValidee> mouvements = ecriture.getMouvements();

		for (int index = 0; index < donnees.mouvements().size(); index++) {
			DonneesMouvement attendu = donnees.mouvements().get(index);
			MouvementValidee obtenu = mouvements.get(index);

			assertEquals(attendu.id(), obtenu.getId());
			assertEquals(attendu.numeroCompte(), obtenu.getCompte().getNumero());
			assertEquals(attendu.libelleCompte(), obtenu.getCompte().getLibelle());
			assertEquals(0, new BigDecimal(attendu.montant()).compareTo(obtenu.getMontant()));
			assertSame(ecriture, obtenu.getEcriture());
		}

		assertEquals(0, BigDecimal.ZERO.compareTo(ecriture.getSolde()));
	}
}
