package fr.xcompta.core.base.test.journal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.xcompta.core.base.Journal;

/**
 * Teste l'égalité de {@link Journal}, ainsi que le contrat entre
 * {@link Journal#equals(Object)} et {@link Journal#hashCode()}.
 *
 * <p>
 * Dans une même comptabilité, le code identifie un journal. Deux journaux
 * de même code sont égaux, indépendamment de leur libellé. La correspondance
 * entre journaux de comptabilités différentes relève d’un autre contexte.
 * </p>
 *
 * @author Fabrice Luneau
 */
class EqualsHashCode {

    private final String codeDeJournal1 = "AC";
    private final String libelleDeJournal1 = "Journal des achats";

    private final String codeDeJournal2 = "BQ";
    private final String libelleDeJournal2 = "Journal de banque";

    @Test
    @DisplayName("Vérifie l'égalité de deux journaux identiques")
    void equals() {

        Journal journal1 = new Journal(codeDeJournal1, libelleDeJournal1);
        Journal autreJournal1 = new Journal(codeDeJournal1, libelleDeJournal1);

        assertEquals(journal1, autreJournal1);
    }

    @Test
    @DisplayName("Vérifie l'inégalité de deux journaux ayant des codes différents")
    void notEqualsCodeDifferent() {

        Journal journal1 = new Journal(codeDeJournal1, libelleDeJournal1);
        Journal journal2 = new Journal(codeDeJournal2, libelleDeJournal2);

        assertNotEquals(journal1, journal2);
    }

    @Test
    @DisplayName("Vérifie l'égalité de deux journaux ayant le même code")
    void equalsLibelleDifferent() {

        Journal journal1 = new Journal(codeDeJournal1, libelleDeJournal1);
        Journal autreJournal1 = new Journal(codeDeJournal1, "Autre libellé");

        assertEquals(journal1, autreJournal1);
    }

    @Test
    @DisplayName("Vérifie le contrat equals et hashCode dans un HashSet")
    void testHashCode() {

        Journal journal1 = new Journal(codeDeJournal1, libelleDeJournal1);
        Journal journalIdentique = new Journal(codeDeJournal1, libelleDeJournal1);
        Journal journalLibelleDifferent = new Journal(codeDeJournal1, "Autre libellé");
        Journal journal2 = new Journal(codeDeJournal2, libelleDeJournal2);

        Set<Journal> collection = new HashSet<>();

        assertEquals(0, collection.size());

        collection.add(journal1);
        assertEquals(1, collection.size());

        // Même code et même libellé : le Set ne doit pas contenir de doublon.
        collection.add(journalIdentique);
        assertEquals(1, collection.size());

        // Même code mais libellé différent : même journal métier.
        collection.add(journalLibelleDifferent);
        assertEquals(1, collection.size());

        // Code différent : l'élément doit également être ajouté.
        collection.add(journal2);
        assertEquals(2, collection.size());
    }
}
