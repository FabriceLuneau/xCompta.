package fr.xcompta.core.base.test.integration.ecriturevalidee;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import fr.xcompta.core.base.*;
import fr.xcompta.core.base.exception.*;

/** Vérifie la création directe d'une écriture validée non persistée. */
class Construct4arg {
    private static final LocalDate DATE = LocalDate.of(2026, 11, 26);
    private static final Journal JOURNAL = new Journal("OD", "Opérations diverses");

    private static List<MouvementValidee> mouvements() throws XComptaCoreException {
        return List.of(
                new MouvementValidee(0, new BigDecimal("100.00"), new Compte("512", "Banque")),
                new MouvementValidee(0, new BigDecimal("-100.00"), new Compte("401", "Fournisseurs")));
    }

    @Test
    @DisplayName("Une nouvelle écriture validée possède trois identifiants à zéro")
    void identifiantsNonAttribues() throws XComptaCoreException {
        var ecriture = new EcritureValidee("Achat", DATE, JOURNAL, mouvements());
        assertEquals(0, ecriture.getId());
        assertEquals(0, ecriture.getNumeroDePreEnregistrement());
        assertEquals(0, ecriture.getNumeroEnregistrement());
        assertTrue(ecriture.estValide());
        assertDoesNotThrow(ecriture::testPreValidation);
        assertEquals("Achat", ecriture.getLibelle());
        assertEquals(DATE, ecriture.getDate());
        assertEquals(JOURNAL, ecriture.getJournal());
        assertEquals(2, ecriture.size());
        assertEquals(0, ecriture.getSolde().signum());
        assertEquals(0, new BigDecimal("100.00").compareTo(ecriture.getTotalDebit()));
        assertEquals(0, new BigDecimal("-100.00").compareTo(ecriture.getTotalCredit()));
    }

    @Test
    @DisplayName("Les mouvements sont copiés et rattachés sans modifier les sources")
    void copieDesMouvements() throws XComptaCoreException {
        var sources = new ArrayList<>(List.of(
                new MouvementValidee(11, new BigDecimal("100.00"), new Compte("512", "Banque")),
                new MouvementValidee(12, new BigDecimal("-100.00"), new Compte("401", "Fournisseurs"))));
        var ecriture = new EcritureValidee("Achat", DATE, JOURNAL, sources);
        for (int i = 0; i < sources.size(); i++) {
            var source = sources.get(i);
            var copie = ecriture.getMouvements().get(i);
            assertNotSame(source, copie);
            assertNull(source.getEcriture());
            assertSame(ecriture, copie.getEcriture());
            assertEquals(0, copie.getId());
            assertEquals(11 + i, source.getId());
            assertEquals(source.getCompte(), copie.getCompte());
            assertEquals(0, source.getMontant().compareTo(copie.getMontant()));
        }
        sources.clear();
        assertEquals(2, ecriture.size());
    }
}
