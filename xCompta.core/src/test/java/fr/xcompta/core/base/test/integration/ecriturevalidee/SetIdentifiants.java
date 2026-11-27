package fr.xcompta.core.base.test.integration.ecriturevalidee;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import fr.xcompta.core.base.Compte;
import fr.xcompta.core.base.EcritureValidee;
import fr.xcompta.core.base.Journal;
import fr.xcompta.core.base.MouvementValidee;
import fr.xcompta.core.base.exception.XComptaCoreException;

/** Vérifie l'attribution unique et complète des identifiants. */
class SetIdentifiants {
    private static final int ID = 42;
    private static final int PRE = 1042;
    private static final int NUM = 27;
    private static final LocalDate DATE = LocalDate.of(2026, 11, 27);
    private static final Journal JOURNAL = new Journal("OD", "Opérations diverses");

    private static List<MouvementValidee> mouvements() throws XComptaCoreException {
        return List.of(new MouvementValidee(0, new BigDecimal("100"), new Compte("512", "Banque")),
                new MouvementValidee(0, new BigDecimal("-100"), new Compte("401", "Fournisseurs")));
    }

    private static EcritureValidee creer(int id, int pre, int num) throws XComptaCoreException {
        return new EcritureValidee(id, pre, num, "Achat", DATE, JOURNAL, mouvements());
    }

    private static void verifier(EcritureValidee e, int id, int pre, int num) {
        assertEquals(id, e.getId());
        assertEquals(pre, e.getNumeroDePreEnregistrement());
        assertEquals(num, e.getNumeroEnregistrement());
    }

    @ParameterizedTest
    @CsvSource({"0,0,0", "0,1042,0", "42,1042,0", "42,1042,27", "42,12,27"})
    void constructionAutorisee(int id, int pre, int num) throws XComptaCoreException {
        var e = creer(id, pre, num);
        verifier(e, id, pre, num);
        assertTrue(e.estValide());
    }

    @ParameterizedTest
    @CsvSource({"-1,1042,27", "42,-1,27", "42,1042,-1", "42,0,0", "42,0,27", "0,0,27", "0,1042,27"})
    void constructionInterdite(int id, int pre, int num) throws XComptaCoreException {
        var sources = mouvements();
        assertThrows(IllegalArgumentException.class,
                () -> new EcritureValidee(id, pre, num, "Achat", DATE, JOURNAL, sources));
    }

    @Test
    void attributionNouvelleEcriture() throws XComptaCoreException {
        var e = new EcritureValidee("Achat", DATE, JOURNAL, mouvements());
        var premier = e.getMouvements().get(0);
        e.setIdentifiants(ID, PRE, NUM);
        verifier(e, ID, PRE, NUM);
        assertSame(premier, e.getMouvements().get(0));
        assertTrue(e.estValide());
    }

    @ParameterizedTest
    @CsvSource({"0,1042", "42,1042"})
    void attributionConserveNumeros(int id, int pre) throws XComptaCoreException {
        var e = creer(id, pre, 0);
        e.setIdentifiants(ID, PRE, NUM);
        verifier(e, ID, PRE, NUM);
    }

    @Test
    void surchargeNumeroDefinitif() throws XComptaCoreException {
        var e = creer(ID, PRE, 0);
        e.setIdentifiants(NUM);
        verifier(e, ID, PRE, NUM);
        assertThrows(IllegalStateException.class, () -> e.setIdentifiants(NUM));
        verifier(e, ID, PRE, NUM);
    }

    @ParameterizedTest
    @CsvSource({"0,0", "0,1042"})
    void surchargeSansIdRefusee(int id, int pre) throws XComptaCoreException {
        var e = creer(id, pre, 0);
        assertThrows(IllegalArgumentException.class, () -> e.setIdentifiants(NUM));
        verifier(e, id, pre, 0);
    }

    @ParameterizedTest
    @CsvSource({"0,1042,27", "-1,1042,27", "42,0,27", "42,-1,27", "42,1042,0", "42,1042,-1"})
    void argumentsInvalidesSansModification(int id, int pre, int num) throws XComptaCoreException {
        var e = creer(0, 0, 0);
        assertThrows(IllegalArgumentException.class, () -> e.setIdentifiants(id, pre, num));
        verifier(e, 0, 0, 0);
    }

    @Test
    void changementIdentifiantsExistantsRefuse() throws XComptaCoreException {
        var e = creer(ID, PRE, 0);
        assertThrows(IllegalArgumentException.class, () -> e.setIdentifiants(ID + 1, PRE, NUM));
        verifier(e, ID, PRE, 0);
        assertThrows(IllegalArgumentException.class, () -> e.setIdentifiants(ID, PRE + 1, NUM));
        verifier(e, ID, PRE, 0);
        assertThrows(IllegalArgumentException.class, () -> e.setIdentifiants(0));
        verifier(e, ID, PRE, 0);
    }

    @Test
    void reattributionRefusee() throws XComptaCoreException {
        var e = creer(ID, PRE, NUM);
        assertThrows(IllegalStateException.class, () -> e.setIdentifiants(ID, PRE, NUM));
        assertThrows(IllegalStateException.class, () -> e.setIdentifiants(ID, PRE, NUM + 1));
        verifier(e, ID, PRE, NUM);
    }
}
