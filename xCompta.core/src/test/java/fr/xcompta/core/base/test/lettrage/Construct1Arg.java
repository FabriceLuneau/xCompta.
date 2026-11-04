package fr.xcompta.core.base.test.lettrage;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import fr.xcompta.core.base.*;
import fr.xcompta.core.base.exception.XComptaCoreException;

/** Tests de construction et d'accès d'un lettrage complet. */
class Construct1Arg {
    static List<MouvementValidee> mouvements() throws XComptaCoreException {
        Compte compte = new Compte("401", "Fournisseurs");
        return List.of(new MouvementValidee(1, new BigDecimal("120.00"), compte),
                new MouvementValidee(2, new BigDecimal("-120.00"), compte));
    }

    @Test
    void construitEtRattache() throws XComptaCoreException {
        List<MouvementValidee> mouvements = mouvements();
        LocalDate date = LocalDate.of(2026, 10, 9);
        Lettrage lettrage = new Lettrage(12, date, mouvements);
        assertEquals(12, lettrage.getId());
        assertEquals(date, lettrage.getDate());
        assertEquals(2, lettrage.getMouvements().size());
        assertEquals(0, lettrage.getTotal().compareTo(BigDecimal.ZERO));
        assertTrue(lettrage.isEquilibre());
        assertFalse(lettrage.isDebiteur());
        assertFalse(lettrage.isCrediteur());
        for (MouvementValidee mouvement : mouvements) {
            assertSame(lettrage, mouvement.getLettrage());
        }
        assertDoesNotThrow(lettrage::testPreValidation);
        assertThrows(UnsupportedOperationException.class,
                () -> lettrage.getMouvements().clear());
    }

    @Test
    void identifiantAttribueUneFois() throws XComptaCoreException {
        Lettrage lettrage = new Lettrage(mouvements());
        assertEquals(0, lettrage.getId());
        assertThrows(IllegalArgumentException.class, () -> lettrage.setId(0));
        assertThrows(IllegalArgumentException.class, () -> lettrage.setId(-1));
        lettrage.setId(7);
        assertEquals(7, lettrage.getId());
        assertThrows(IllegalStateException.class, () -> lettrage.setId(8));
    }
}
