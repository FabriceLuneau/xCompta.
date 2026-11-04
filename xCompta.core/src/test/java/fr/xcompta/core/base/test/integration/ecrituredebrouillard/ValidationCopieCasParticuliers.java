package fr.xcompta.core.base.test.integration.ecrituredebrouillard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.xcompta.core.base.Compte;
import fr.xcompta.core.base.EcritureDeBrouillard;
import fr.xcompta.core.base.EcritureValidee;
import fr.xcompta.core.base.Journal;
import fr.xcompta.core.base.MouvementDeBrouillard;
import fr.xcompta.core.base.exception.XComptaCoreException;

/**
 * Tests d'intégration de la validation d'une écriture de brouillard.
 *
 * <p>
 * Ces tests vérifient qu'une écriture de brouillard valide peut être
 * transformée en écriture validée tout en conservant sa finalité
 * comptable.
 * </p>
 *
 * <p>
 * Ils vérifient également que les éventuels mouvements vides utilisés
 * pendant la saisie ne sont pas intégrés à l'écriture validée.
 * </p>
 *
 * @author Fabrice Luneau
 */
class ValidationCopieCasParticuliers {

    @Test
    @DisplayName(
            "Un brouillard valide peut être transformé en écriture validée")
    void validerBrouillard()
            throws XComptaCoreException {

        EcritureDeBrouillard brouillard =
                creerEcriture();

        EcritureValidee validee =
                brouillard.valider();

        assertTrue(validee.estValide());
    }

    @Test
    @DisplayName(
            "L'écriture validée conserve la finalité comptable du brouillard")
    void validationConserveEquivalence()
            throws XComptaCoreException {

        EcritureDeBrouillard brouillard =
                creerEcriture();

        EcritureValidee validee =
                brouillard.valider();

        assertTrue(
                brouillard.estEquivalente(validee));

        assertTrue(
                validee.estEquivalente(brouillard));
    }

    @Test
    @DisplayName(
            "La copie comptable repart avec un ID technique nul")
    void copieReinitialiseId()
            throws XComptaCoreException {

        EcritureDeBrouillard brouillard =
                creerEcriture();

        EcritureValidee validee =
                brouillard.valider();

        assertEquals(0, validee.getId());
        org.junit.jupiter.api.Assertions.assertTrue(brouillard.getId() > 0);
    }

    @Test
    @DisplayName("La recopie prépare des mouvements sans identifiants techniques")
    void copiePourValidationReinitialiseIdentifiants() throws XComptaCoreException {
        EcritureDeBrouillard brouillard = creerEcriture();
        EcritureValidee copie = brouillard.copierPourValidation();
        assertEquals(0, copie.getId());
        assertEquals(brouillard.getMouvements().size(), copie.getMouvements().size());
        for (int i = 0; i < copie.getMouvements().size(); i++) {
            assertEquals(0, copie.getMouvements().get(i).getId());
            assertEquals(brouillard.getMouvements().get(i).getMontant(),
                    copie.getMouvements().get(i).getMontant());
            assertEquals(brouillard.getMouvements().get(i).getCompte(),
                    copie.getMouvements().get(i).getCompte());
        }
        assertTrue(brouillard.getId() > 0);
    }

    @Test
    @DisplayName(
            "La validation conserve la date")
    void validationConserveDate()
            throws XComptaCoreException {

        EcritureDeBrouillard brouillard =
                creerEcriture();

        EcritureValidee validee =
                brouillard.valider();

        assertEquals(
                brouillard.getDate(),
                validee.getDate());
    }

    @Test
    @DisplayName(
            "La validation conserve le journal")
    void validationConserveJournal()
            throws XComptaCoreException {

        EcritureDeBrouillard brouillard =
                creerEcriture();

        EcritureValidee validee =
                brouillard.valider();

        assertSame(
                brouillard.getJournal(),
                validee.getJournal());
    }

    @Test
    @DisplayName(
            "La validation conserve le libellé")
    void validationConserveLibelle()
            throws XComptaCoreException {

        EcritureDeBrouillard brouillard =
                creerEcriture();

        EcritureValidee validee =
                brouillard.valider();

        assertEquals(
                brouillard.getLibelle(),
                validee.getLibelle());
    }

    @Test
    @DisplayName(
            "La validation conserve le nombre de mouvements comptables")
    void validationConserveNombreMouvements()
            throws XComptaCoreException {

        EcritureDeBrouillard brouillard =
                creerEcriture();

        EcritureValidee validee =
                brouillard.valider();

        assertEquals(
                brouillard.size(),
                validee.size());
    }

    @Test
    @DisplayName(
            "Les mouvements validés sont rattachés à l'écriture validée")
    void validationRattacheMouvements()
            throws XComptaCoreException {

        EcritureDeBrouillard brouillard =
                creerEcriture();

        EcritureValidee validee =
                brouillard.valider();

        for (int i = 0; i < validee.size(); i++) {
            assertSame(
                    validee,
                    validee.getMouvements().get(0)
                            .getEcriture());
        }
    }

    @Test
    @DisplayName(
            "Les mouvements vides de saisie ne sont pas intégrés à l'écriture validée")
    void validationIgnoreMouvementsVides()
            throws XComptaCoreException {

        EcritureDeBrouillard brouillard =
                creerEcriture();

        brouillard.ajouterMouvementVide();
        brouillard.ajouterMouvementVide();

        assertEquals(
                5,
                brouillard.size());

        EcritureValidee validee =
                brouillard.valider();

        assertEquals(
                3,
                validee.size());

        assertTrue(
                brouillard.estEquivalente(validee));
    }

    private EcritureDeBrouillard creerEcriture()
            throws XComptaCoreException {

        Journal journal =
                new Journal(
                        "AC",
                        "Achats");

        List<MouvementDeBrouillard> mouvements =
                List.of(
                        mouvement(
                                "607",
                                "Achats de marchandises",
                                "1000.00"),

                        mouvement(
                                "44566",
                                "TVA déductible",
                                "200.00"),

                        mouvement(
                                "401",
                                "Fournisseurs",
                                "-1200.00"));

        return new EcritureDeBrouillard(
                        100,
                        "Achat de marchandises",
                        LocalDate.of(2026, 9, 17),
                        journal,
                        mouvements
                );
    }

    private MouvementDeBrouillard mouvement(
            String numeroCompte,
            String libelleCompte,
            String montant) {

        return new MouvementDeBrouillard(
                new Compte(
                        numeroCompte,
                        libelleCompte),
                new BigDecimal(montant));
    }
}
