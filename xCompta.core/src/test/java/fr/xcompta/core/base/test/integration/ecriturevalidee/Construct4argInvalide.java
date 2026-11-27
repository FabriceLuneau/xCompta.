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
import fr.xcompta.core.base.exception.ecriture.*;
import fr.xcompta.core.date.exception.DateInvalideException;

/** Vérifie les contraintes comptables avant toute attribution de numéros. */
class Construct4argInvalide {
    private static final LocalDate DATE = LocalDate.of(2026, 11, 26);
    private static final Journal JOURNAL = new Journal("OD", "Opérations diverses");

    private static List<MouvementValidee> mouvements() throws XComptaCoreException {
        return List.of(
                new MouvementValidee(0, new BigDecimal("100.00"), new Compte("512", "Banque")),
                new MouvementValidee(0, new BigDecimal("-100.00"), new Compte("401", "Fournisseurs")));
    }

    @Test
    void dateNulle() {
        assertThrows(DateInvalideException.class,
                () -> new EcritureValidee("Achat", null, JOURNAL, mouvements()));
    }

    @Test
    void journalNul() {
        assertThrows(XComptaCoreNullPointerException.class,
                () -> new EcritureValidee("Achat", DATE, null, mouvements()));
    }

    @Test
    void libelleNul() {
        assertThrows(XComptaCoreNullPointerException.class,
                () -> new EcritureValidee(null, DATE, JOURNAL, mouvements()));
    }

    @Test
    void libelleVide() {
        assertThrows(XComptaCoreNullPointerException.class,
                () -> new EcritureValidee("", DATE, JOURNAL, mouvements()));
    }

    @Test
    void libelleBlanc() {
        assertThrows(XComptaCoreNullPointerException.class,
                () -> new EcritureValidee("   ", DATE, JOURNAL, mouvements()));
    }

    @Test
    void listeNulle() {
        assertThrows(EcritureNbMouvementsInferieurADeuxException.class,
                () -> new EcritureValidee("Achat", DATE, JOURNAL, null));
    }

    @Test
    void listeVide() {
        assertThrows(EcritureNbMouvementsInferieurADeuxException.class,
                () -> new EcritureValidee("Achat", DATE, JOURNAL, List.of()));
    }

    @Test
    void unSeulMouvement() {
        assertThrows(EcritureNbMouvementsInferieurADeuxException.class,
                () -> new EcritureValidee("Achat", DATE, JOURNAL, List.of(mouvements().get(0))));
    }

    @Test
    void desequilibre() throws XComptaCoreException {
        var sources = List.of(mouvements().get(0),
                new MouvementValidee(0, new BigDecimal("-90.00"), new Compte("401", "Fournisseurs")));
        assertThrows(EcritureNonEquilibreeException.class,
                () -> new EcritureValidee("Achat", DATE, JOURNAL, sources));
    }

    @Test
    void compensation() throws XComptaCoreException {
        var compte = new Compte("512", "Banque");
        var sources = List.of(new MouvementValidee(0, new BigDecimal("100.00"), compte),
                new MouvementValidee(0, new BigDecimal("-100.00"), compte));
        assertThrows(EcritureCompenseException.class,
                () -> new EcritureValidee("Achat", DATE, JOURNAL, sources));
    }

    @Test
    void mouvementNul() throws XComptaCoreException {
        var sources = new ArrayList<>(mouvements());
        sources.set(1, null);
        assertThrows(XComptaCoreNullPointerException.class,
                () -> new EcritureValidee("Achat", DATE, JOURNAL, sources));
    }
}
