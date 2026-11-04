package fr.xcompta.core.base.test.integration.ecrituredebrouillard;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import fr.xcompta.core.base.*;
import fr.xcompta.core.base.exception.XComptaCoreException;
import fr.xcompta.core.base.test.integration.ecrituredebrouillard.JeuDonneesEcritureDeBrouillard.DonneesEcriture;
import fr.xcompta.core.base.test.integration.ecrituredebrouillard.JeuDonneesEcritureDeBrouillard.DonneesMouvement;

/** Contrôles communs aux trois scénarios de copie pour validation.
 * @author Fabrice Luneau
 */
final class VerificationCopieValidation {
    private VerificationCopieValidation() { }

    static Journal creerJournal(DonneesEcriture d) {
        return new Journal(d.codeJournal(), d.libelleJournal());
    }

    static MouvementDeBrouillard creerMouvement(DonneesMouvement d) {
        return new MouvementDeBrouillard(
                new Compte(d.numeroCompte(), d.libelleCompte()),
                new BigDecimal(d.montant()));
    }

    static List<MouvementDeBrouillard> creerMouvements(DonneesEcriture d) {
        List<MouvementDeBrouillard> result = new ArrayList<>();
        for (DonneesMouvement m : d.mouvements()) {
            result.add(creerMouvement(m));
        }
        return result;
    }

    static void verifierEcriture(EcritureDeBrouillard e, DonneesEcriture d) {
        assertNotNull(e);
        assertEquals(d.id(), e.getId());
        assertEquals(d.date(), e.getDate());
        assertEquals(d.libelle(), e.getLibelle());
        assertNotNull(e.getJournal());
        assertEquals(d.codeJournal(), e.getJournal().code());
        assertEquals(d.libelleJournal(), e.getJournal().libelle());
        assertEquals(d.mouvements().size(), e.size());
        List<MouvementDeBrouillard> mouvements = e.getMouvements();
        for (int i = 0; i < mouvements.size(); i++) {
            DonneesMouvement attendu = d.mouvements().get(i);
            MouvementDeBrouillard obtenu = mouvements.get(i);
            assertEquals(attendu.numeroCompte(), obtenu.getCompte().getNumero());
            assertEquals(attendu.libelleCompte(), obtenu.getCompte().getLibelle());
            assertEquals(0, new BigDecimal(attendu.montant()).compareTo(obtenu.getMontant()));
            assertSame(e, obtenu.getEcriture());
        }
        assertEquals(0, BigDecimal.ZERO.compareTo(e.getSolde()));
        assertTrue(e.estValide());
    }

    static void verifier(EcritureDeBrouillard brouillard, DonneesEcriture donnees)
            throws XComptaCoreException {
        verifierEcriture(brouillard, donnees);
        EcritureValidee validee = brouillard.copierPourValidation();
        assertNotSame(brouillard, validee);
        assertEquals(0, validee.getId());
        assertEquals(brouillard.getLibelle(), validee.getLibelle());
        assertEquals(brouillard.getDate(), validee.getDate());
        assertEquals(brouillard.getJournal(), validee.getJournal());
        assertTrue(brouillard.estEquivalente(validee));
        assertTrue(validee.estEquivalente(brouillard));
        assertEquals(brouillard.size(), validee.size());
        List<MouvementDeBrouillard> originaux = brouillard.getMouvements();
        List<MouvementValidee> copies = validee.getMouvements();
        for (int i = 0; i < copies.size(); i++) {
            MouvementDeBrouillard source = originaux.get(i);
            MouvementValidee copie = copies.get(i);
            assertNotSame(source, copie);
            assertTrue(source.estEquivalent(copie));
            assertEquals(0, copie.getId());
            assertEquals(source.getCompte(), copie.getCompte());
            assertEquals(0, source.getMontant().compareTo(copie.getMontant()));
            assertSame(brouillard, source.getEcriture());
            assertSame(validee, copie.getEcriture());
        }
        verifierEcriture(brouillard, donnees);
    }
}
