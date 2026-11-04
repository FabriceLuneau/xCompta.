package fr.xcompta.core.base.test.integration.ecrituredebrouillard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import fr.xcompta.core.base.Compte;
import fr.xcompta.core.base.EcritureDeBrouillard;
import fr.xcompta.core.base.Journal;
import fr.xcompta.core.base.MouvementDeBrouillard;
import fr.xcompta.core.base.test.integration.ecrituredebrouillard.JeuDonneesEcritureDeBrouillard.DonneesEcriture;
import fr.xcompta.core.base.test.integration.ecrituredebrouillard.JeuDonneesEcritureDeBrouillard.DonneesMouvement;

/**
 * Teste les constructions nominales d'une {@link EcritureDeBrouillard}
 * réalisées avec le constructeur initialisant l'entête.
 *
 * <p>L'entête est fourni au constructeur et la collection de mouvements
 * doit être créée vide. Des mouvements valides sont ensuite ajoutés afin
 * d'obtenir une écriture complète, équilibrée et valide.</p>
 *
 * @author Fabrice Luneau
 */
class Construct4arg {

    private static final String SOURCE =
            "fr.xcompta.core.base.test.integration.ecrituredebrouillard."
            + "JeuDonneesEcritureDeBrouillard#fournirEcritures";

    private static Journal creerJournal(final DonneesEcriture donnees) {
        return new Journal(donnees.codeJournal(), donnees.libelleJournal());
    }

    private static MouvementDeBrouillard creerMouvement(
            final DonneesMouvement donnees) {

        return new MouvementDeBrouillard(
                new Compte(donnees.numeroCompte(), donnees.libelleCompte()),
                new BigDecimal(donnees.montant()));
    }

    private static void verifierEcriture(
            final EcritureDeBrouillard ecriture,
            final DonneesEcriture donnees) {

        assertNotNull(ecriture);
        assertEquals(donnees.id(), ecriture.getId());
        assertEquals(donnees.date(), ecriture.getDate());
        assertEquals(donnees.libelle(), ecriture.getLibelle());
        assertNotNull(ecriture.getJournal());
        assertEquals(donnees.codeJournal(), ecriture.getJournal().code());
        assertEquals(donnees.libelleJournal(), ecriture.getJournal().libelle());
        assertEquals(donnees.mouvements().size(), ecriture.size());

        List<MouvementDeBrouillard> mouvements = ecriture.getMouvements();

        for (int index = 0; index < donnees.mouvements().size(); index++) {
            DonneesMouvement attendu = donnees.mouvements().get(index);
            MouvementDeBrouillard obtenu = mouvements.get(index);

            assertEquals(attendu.numeroCompte(), obtenu.getCompte().getNumero());
            assertEquals(attendu.libelleCompte(), obtenu.getCompte().getLibelle());
            assertEquals(
                    0,
                    new BigDecimal(attendu.montant())
                            .compareTo(obtenu.getMontant()));
            assertSame(ecriture, obtenu.getEcriture());
        }

        assertEquals(0, BigDecimal.ZERO.compareTo(ecriture.getSolde()));
        assertTrue(ecriture.estValide());
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource(SOURCE)
    @DisplayName("Construction valide avec le constructeur d'entête")
    void constructeurAvecEntete(final DonneesEcriture donnees) throws Exception {

        Journal journal = creerJournal(donnees);

        EcritureDeBrouillard ecriture = new EcritureDeBrouillard(donnees.id(), donnees.libelle(), donnees.date(), journal);

        // Reprise des anciens tests unitaires de l'entête.
        assertNotNull(ecriture);
        assertEquals(donnees.id(), ecriture.getId());
        assertEquals(donnees.date(), ecriture.getDate());
        assertEquals(donnees.libelle(), ecriture.getLibelle());
        assertSame(journal, ecriture.getJournal());
        assertNotNull(ecriture.getMouvements());
        assertTrue(ecriture.getMouvements().isEmpty());

        for (DonneesMouvement mouvement : donnees.mouvements()) {
            ecriture.ajouterMouvementALaFin(creerMouvement(mouvement));
        }

        verifierEcriture(ecriture, donnees);
    }
}
