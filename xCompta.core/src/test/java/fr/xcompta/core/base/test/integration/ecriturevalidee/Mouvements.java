package fr.xcompta.core.base.test.integration.ecriturevalidee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.xcompta.core.base.Compte;
import fr.xcompta.core.base.EcritureValidee;
import fr.xcompta.core.base.Journal;
import fr.xcompta.core.base.MouvementValidee;
import fr.xcompta.core.base.exception.XComptaCoreException;

/**
 * Tests d'intégration de la collection de mouvements
 * d'une écriture validée.
 *
 * <p>
 * Ces tests vérifient notamment l'ordre des mouvements,
 * le comportement de l'itérateur, le caractère non modifiable
 * de la collection exposée et la copie profonde des mouvements de la liste
 * fournie au constructeur.
 * </p>
 *
 * @author Fabrice Luneau
 */
class Mouvements {

    private EcritureValidee ecriture;

    private MouvementValidee mouvement1;
    private MouvementValidee mouvement2;
    private MouvementValidee mouvement3;

    @BeforeEach
    void initialiser()
            throws XComptaCoreException {

        mouvement1 =
                new MouvementValidee(
                        1001,
                        new BigDecimal("1000.00"),
                        new Compte(
                                "607",
                                "Achats"));

        mouvement2 =
                new MouvementValidee(
                        1002,
                        new BigDecimal("200.00"),
                        new Compte(
                                "44566",
                                "TVA déductible"));

        mouvement3 =
                new MouvementValidee(
                        1003,
                        new BigDecimal("-1200.00"),
                        new Compte(
                                "401",
                                "Fournisseurs"));

        ecriture =
                new EcritureValidee(3545, "Achat fournisseur", LocalDate.of(
                                2026,
                                Month.AUGUST,
                                19), new Journal(
                                "AC",
                                "Achats"), List.of(
                                mouvement1,
                                mouvement2,
                                mouvement3));
    }

    @Test
    @DisplayName(
            "L'itérateur parcourt les mouvements dans leur ordre")
    void iterateurParcourtMouvements() {

        Iterator<MouvementValidee> iterateur =
                ecriture.iterator();

        assertSame(
                ecriture.getMouvements().get(0),
                iterateur.next());

        assertSame(
                ecriture.getMouvements().get(1),
                iterateur.next());

        assertSame(
                ecriture.getMouvements().get(2),
                iterateur.next());
    }

    @Test
    @DisplayName(
            "L'itérateur ne permet pas de supprimer un mouvement")
    void iterateurInterditSuppression() {

        Iterator<MouvementValidee> iterateur =
                ecriture.iterator();

        iterateur.next();

        assertThrows(
                UnsupportedOperationException.class,
                iterateur::remove);
    }

    @Test
    @DisplayName(
            "getMouvements conserve l'ordre des mouvements")
    void getMouvementsConserveOrdre() {

        assertNotSame(
                mouvement1,
                ecriture.getMouvements().get(0));

        assertNotSame(
                mouvement2,
                ecriture.getMouvements().get(1));

        assertNotSame(
                mouvement3,
                ecriture.getMouvements().get(2));
    }

    @Test
    @DisplayName(
            "La collection retournée interdit la suppression")
    void getMouvementsInterditSuppression() {

        assertThrows(
                UnsupportedOperationException.class,
                () -> ecriture.getMouvements().remove(0));
    }

    @Test
    @DisplayName(
            "La collection retournée interdit l'ajout")
    void getMouvementsInterditAjout()
            throws XComptaCoreException {

        MouvementValidee mouvement =
                new MouvementValidee(
                        1004,
                        new BigDecimal("50.00"),
                        new Compte(
                                "606",
                                "Achats non stockés"));

        assertThrows(
                UnsupportedOperationException.class,
                () -> ecriture.getMouvements().add(
                        mouvement));
    }

    @Test
    @DisplayName(
            "La liste fournie au constructeur est copiée")
    void listeConstructeurCopiee()
            throws XComptaCoreException {

        MouvementValidee debit =
                new MouvementValidee(
                        2001,
                        new BigDecimal("100.00"),
                        new Compte(
                                "607",
                                "Achats"));

        MouvementValidee credit =
                new MouvementValidee(
                        2002,
                        new BigDecimal("-100.00"),
                        new Compte(
                                "401",
                                "Fournisseurs"));

        List<MouvementValidee> liste =
                new java.util.ArrayList<>(
                        List.of(
                                debit,
                                credit));

        EcritureValidee nouvelleEcriture =
                new EcritureValidee(4000, "Test copie défensive", LocalDate.of(
                                2026,
                                Month.AUGUST,
                                20), new Journal(
                                "AC",
                                "Achats"), liste);

        liste.clear();

        assertEquals(
                2,
                nouvelleEcriture.getMouvements().size());

        assertNotSame(
                debit,
                nouvelleEcriture.getMouvements().get(0));

        assertNotSame(
                credit,
                nouvelleEcriture.getMouvements().get(1));
    }
}