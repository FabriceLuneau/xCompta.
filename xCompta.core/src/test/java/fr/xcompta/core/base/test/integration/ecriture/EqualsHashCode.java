package fr.xcompta.core.base.test.integration.ecriture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.xcompta.core.base.Compte;
import fr.xcompta.core.base.Ecriture;
import fr.xcompta.core.base.EcritureDeBrouillard;
import fr.xcompta.core.base.EcritureValidee;
import fr.xcompta.core.base.Journal;
import fr.xcompta.core.base.MouvementDeBrouillard;
import fr.xcompta.core.base.MouvementValidee;
import fr.xcompta.core.base.exception.XComptaCoreException;

/**
 * Tests d'intégration transversaux des écritures.
 *
 * <p>
 * Ces tests distinguent deux notions :
 * </p>
 *
 * <ul>
 * <li>l'égalité, définie par {@link Object#equals(Object)}, qui compare
 * l'égalité complète de deux écritures de même classe concrète : identifiant,
 * date, libellé, journal et mouvements (dans leur ordre) ;</li>
 * <li>l'équivalence comptable, définie par
 * {@link Ecriture#estEquivalente(Ecriture)}, qui compare uniquement le
 * contenu comptable des écritures.</li>
 * </ul>
 *
 * <p>
 * Deux écritures peuvent donc être comptablement équivalentes sans être
 * égales. C'est notamment le cas d'une écriture de brouillard et de
 * l'écriture validée correspondante.
 * </p>
 *
 * <p>
 * L'équivalence comptable dépend des comptes et des montants signés des
 * mouvements. Elle est indépendante de leur ordre ainsi que des informations
 * d'en-tête de l'écriture.
 * </p>
 *
 * @author Fabrice Luneau
 */
class EqualsHashCode {

    @Test
    @DisplayName("Une écriture de brouillard et sa validation sont équivalentes")
    void brouillardEtValidationSontEquivalents()
            throws XComptaCoreException {

        EcritureDeBrouillard brouillard = creerBrouillard(
                JeuDonneesEcriture.ID_ECRITURE_1,
                JeuDonneesEcriture.DATE,
                JeuDonneesEcriture.LIBELLE,
                JeuDonneesEcriture.MOUVEMENTS);

        EcritureValidee validee = brouillard.valider();

        assertTrue(brouillard.estEquivalente(validee));
        assertTrue(validee.estEquivalente(brouillard));
    }

    @Test
    @DisplayName("L'équivalence est indépendante de l'ordre des mouvements")
    void equivalenceIndependanteOrdreMouvements()
            throws XComptaCoreException {

        EcritureDeBrouillard brouillard = creerBrouillard(
                JeuDonneesEcriture.ID_ECRITURE_1,
                JeuDonneesEcriture.DATE,
                JeuDonneesEcriture.LIBELLE,
                JeuDonneesEcriture.MOUVEMENTS);

        EcritureValidee validee = creerValidee(
                JeuDonneesEcriture.ID_ECRITURE_2,
                JeuDonneesEcriture.DATE,
                JeuDonneesEcriture.LIBELLE,
                JeuDonneesEcriture.MOUVEMENTS_ORDRE_INVERSE);

        assertTrue(brouillard.estEquivalente(validee));
        assertTrue(validee.estEquivalente(brouillard));
    }

    @Test
    @DisplayName("L'équivalence ignore l'id, la date, le journal et le libellé")
    void equivalenceIgnoreEnteteTechnique()
            throws XComptaCoreException {

        EcritureDeBrouillard brouillard = creerBrouillard(
                JeuDonneesEcriture.ID_ECRITURE_1,
                JeuDonneesEcriture.DATE,
                JeuDonneesEcriture.LIBELLE,
                JeuDonneesEcriture.MOUVEMENTS);

        Journal autreJournal =
                new Journal("OD", "Opérations diverses");

        EcritureValidee validee = creerValidee(
                JeuDonneesEcriture.ID_ECRITURE_2,
                JeuDonneesEcriture.DATE.plusDays(10),
                "Autre libellé",
                autreJournal,
                JeuDonneesEcriture.MOUVEMENTS_ORDRE_INVERSE);

        assertTrue(brouillard.estEquivalente(validee));
        assertTrue(validee.estEquivalente(brouillard));
    }

    @Test
    @DisplayName("Un montant différent rend les écritures non équivalentes")
    void montantDifferent()
            throws XComptaCoreException {

        EcritureDeBrouillard brouillard = creerBrouillard(
                JeuDonneesEcriture.ID_ECRITURE_1,
                JeuDonneesEcriture.DATE,
                JeuDonneesEcriture.LIBELLE,
                JeuDonneesEcriture.MOUVEMENTS);

        List<JeuDonneesEcriture.MouvementDonnees> autresMouvements =
                List.of(
                        new JeuDonneesEcriture.MouvementDonnees(
                                "607",
                                "Achats de marchandises",
                                new BigDecimal("1001.00")),
                        JeuDonneesEcriture.TVA,
                        new JeuDonneesEcriture.MouvementDonnees(
                                "401",
                                "Fournisseurs",
                                new BigDecimal("-1201.00")));

        EcritureValidee validee = creerValidee(
                JeuDonneesEcriture.ID_ECRITURE_2,
                JeuDonneesEcriture.DATE,
                JeuDonneesEcriture.LIBELLE,
                autresMouvements);

        assertFalse(brouillard.estEquivalente(validee));
        assertFalse(validee.estEquivalente(brouillard));
    }

    @Test
    @DisplayName("Un compte différent rend les écritures non équivalentes")
    void compteDifferent()
            throws XComptaCoreException {

        EcritureDeBrouillard brouillard = creerBrouillard(
                JeuDonneesEcriture.ID_ECRITURE_1,
                JeuDonneesEcriture.DATE,
                JeuDonneesEcriture.LIBELLE,
                JeuDonneesEcriture.MOUVEMENTS);

        List<JeuDonneesEcriture.MouvementDonnees> autresMouvements =
                List.of(
                        new JeuDonneesEcriture.MouvementDonnees(
                                "606",
                                "Achats non stockés",
                                new BigDecimal("1000.00")),
                        JeuDonneesEcriture.TVA,
                        JeuDonneesEcriture.FOURNISSEUR);

        EcritureValidee validee = creerValidee(
                JeuDonneesEcriture.ID_ECRITURE_2,
                JeuDonneesEcriture.DATE,
                JeuDonneesEcriture.LIBELLE,
                autresMouvements);

        assertFalse(brouillard.estEquivalente(validee));
        assertFalse(validee.estEquivalente(brouillard));
    }

    @Test
    @DisplayName("Une écriture n'est pas équivalente à null")
    void comparaisonAvecNull()
            throws XComptaCoreException {

        EcritureDeBrouillard brouillard = creerBrouillard(
                JeuDonneesEcriture.ID_ECRITURE_1,
                JeuDonneesEcriture.DATE,
                JeuDonneesEcriture.LIBELLE,
                JeuDonneesEcriture.MOUVEMENTS);

        assertFalse(brouillard.estEquivalente(null));
    }

    @Test
    @DisplayName("equals est réflexif pour les deux types d'écriture")
    void equalsReflexif()
            throws XComptaCoreException {

        EcritureDeBrouillard brouillard = creerBrouillard(
                JeuDonneesEcriture.ID_ECRITURE_1,
                JeuDonneesEcriture.DATE,
                JeuDonneesEcriture.LIBELLE,
                JeuDonneesEcriture.MOUVEMENTS);

        EcritureValidee validee = brouillard.valider();

        assertEquals(brouillard, brouillard);
        assertEquals(validee, validee);
    }

    @Test
    @DisplayName(
            "Une écriture de brouillard et une écriture validée ne sont jamais égales")
    void equalsEntreTypes()
            throws XComptaCoreException {

        EcritureDeBrouillard brouillard = creerBrouillard(
                JeuDonneesEcriture.ID_ECRITURE_1,
                JeuDonneesEcriture.DATE,
                JeuDonneesEcriture.LIBELLE,
                JeuDonneesEcriture.MOUVEMENTS);

        EcritureValidee validee = creerValidee(
                JeuDonneesEcriture.ID_ECRITURE_1,
                JeuDonneesEcriture.DATE,
                JeuDonneesEcriture.LIBELLE,
                JeuDonneesEcriture.MOUVEMENTS);

        assertNotEquals(brouillard, validee);
        assertNotEquals(validee, brouillard);

        assertTrue(brouillard.estEquivalente(validee));
        assertTrue(validee.estEquivalente(brouillard));
    }

    @Test
    @DisplayName("Deux écritures de brouillard identiques sont égales")
    void equalsDeuxBrouillards()
            throws XComptaCoreException {

        EcritureDeBrouillard brouillard1 = creerBrouillard(
                JeuDonneesEcriture.ID_ECRITURE_1,
                JeuDonneesEcriture.DATE,
                JeuDonneesEcriture.LIBELLE,
                JeuDonneesEcriture.MOUVEMENTS);

        EcritureDeBrouillard brouillard2 = creerBrouillard(
                JeuDonneesEcriture.ID_ECRITURE_1,
                JeuDonneesEcriture.DATE,
                JeuDonneesEcriture.LIBELLE,
                JeuDonneesEcriture.MOUVEMENTS);

        assertEquals(brouillard1, brouillard2);
        assertEquals(brouillard2, brouillard1);
        assertEquals(
                brouillard1.hashCode(),
                brouillard2.hashCode());
    }

    @Test
    @DisplayName("Deux écritures validées identiques sont égales")
    void equalsDeuxEcrituresValidees()
            throws XComptaCoreException {

        EcritureValidee validee1 = creerValidee(
                JeuDonneesEcriture.ID_ECRITURE_1,
                JeuDonneesEcriture.DATE,
                JeuDonneesEcriture.LIBELLE,
                JeuDonneesEcriture.MOUVEMENTS);

        EcritureValidee validee2 = creerValidee(
                JeuDonneesEcriture.ID_ECRITURE_1,
                JeuDonneesEcriture.DATE,
                JeuDonneesEcriture.LIBELLE,
                JeuDonneesEcriture.MOUVEMENTS);

        assertEquals(validee1, validee2);
        assertEquals(validee2, validee1);
        assertEquals(
                validee1.hashCode(),
                validee2.hashCode());
    }

    @Test
    @DisplayName("Deux écritures égales possèdent le même hashCode")
    void hashCodeCoherentAvecEquals()
            throws XComptaCoreException {

        EcritureDeBrouillard brouillard1 = creerBrouillard(
                JeuDonneesEcriture.ID_ECRITURE_1,
                JeuDonneesEcriture.DATE,
                JeuDonneesEcriture.LIBELLE,
                JeuDonneesEcriture.MOUVEMENTS);

        EcritureDeBrouillard brouillard2 = creerBrouillard(
                JeuDonneesEcriture.ID_ECRITURE_1,
                JeuDonneesEcriture.DATE,
                JeuDonneesEcriture.LIBELLE,
                JeuDonneesEcriture.MOUVEMENTS);

        assertEquals(brouillard1, brouillard2);
        assertEquals(
                brouillard1.hashCode(),
                brouillard2.hashCode());
    }

    @Test
    @DisplayName("HashSet élimine deux écritures réellement égales")
    void contratEqualsHashCodeDansHashSet()
            throws XComptaCoreException {

        EcritureDeBrouillard brouillard1 = creerBrouillard(
                JeuDonneesEcriture.ID_ECRITURE_1,
                JeuDonneesEcriture.DATE,
                JeuDonneesEcriture.LIBELLE,
                JeuDonneesEcriture.MOUVEMENTS);

        EcritureDeBrouillard brouillard2 = creerBrouillard(
                JeuDonneesEcriture.ID_ECRITURE_1,
                JeuDonneesEcriture.DATE,
                JeuDonneesEcriture.LIBELLE,
                JeuDonneesEcriture.MOUVEMENTS);

        Set<Ecriture<?>> ecritures = new HashSet<>();
        ecritures.add(brouillard1);
        ecritures.add(brouillard2);

        assertEquals(1, ecritures.size());
    }

    @Test
    @DisplayName(
            "HashSet conserve séparément deux écritures équivalentes mais non égales")
    void hashSetDistingueEcrituresEquivalentes()
            throws XComptaCoreException {

        EcritureDeBrouillard brouillard = creerBrouillard(
                JeuDonneesEcriture.ID_ECRITURE_1,
                JeuDonneesEcriture.DATE,
                JeuDonneesEcriture.LIBELLE,
                JeuDonneesEcriture.MOUVEMENTS);

        EcritureValidee validee = creerValidee(
                JeuDonneesEcriture.ID_ECRITURE_1,
                JeuDonneesEcriture.DATE,
                JeuDonneesEcriture.LIBELLE,
                JeuDonneesEcriture.MOUVEMENTS);

        assertTrue(brouillard.estEquivalente(validee));
        assertNotEquals(brouillard, validee);

        Set<Ecriture<?>> ecritures = new HashSet<>();
        ecritures.add(brouillard);
        ecritures.add(validee);

        assertEquals(2, ecritures.size());
    }

    @Test
    @DisplayName("Un identifiant différent rend deux écritures non égales")
    void equalsDistingueIdentifiantDifferent()
            throws XComptaCoreException {

        EcritureDeBrouillard brouillard1 = creerBrouillard(
                JeuDonneesEcriture.ID_ECRITURE_1,
                JeuDonneesEcriture.DATE,
                JeuDonneesEcriture.LIBELLE,
                JeuDonneesEcriture.MOUVEMENTS);

        EcritureDeBrouillard brouillard2 = creerBrouillard(
                JeuDonneesEcriture.ID_ECRITURE_2,
                JeuDonneesEcriture.DATE,
                JeuDonneesEcriture.LIBELLE,
                JeuDonneesEcriture.MOUVEMENTS);

        assertNotEquals(brouillard1, brouillard2);
        assertTrue(brouillard1.estEquivalente(brouillard2));
    }

    @Test
    @DisplayName("À ID identique, une date différente rend les écritures inégales")
    void equalsDistingueDateDifferente()
            throws XComptaCoreException {

        EcritureDeBrouillard brouillard1 = creerBrouillard(
                JeuDonneesEcriture.ID_ECRITURE_1,
                JeuDonneesEcriture.DATE,
                JeuDonneesEcriture.LIBELLE,
                JeuDonneesEcriture.MOUVEMENTS);

        EcritureDeBrouillard brouillard2 = creerBrouillard(
                JeuDonneesEcriture.ID_ECRITURE_1,
                JeuDonneesEcriture.DATE.plusDays(1),
                JeuDonneesEcriture.LIBELLE,
                JeuDonneesEcriture.MOUVEMENTS);

        assertNotEquals(brouillard1, brouillard2);
        assertNotEquals(brouillard2, brouillard1);
        assertTrue(brouillard1.estEquivalente(brouillard2));
    }

    @Test
    @DisplayName("À ID identique, un libellé différent rend les écritures inégales")
    void equalsDistingueLibelleDifferent()
            throws XComptaCoreException {

        EcritureDeBrouillard brouillard1 = creerBrouillard(
                JeuDonneesEcriture.ID_ECRITURE_1,
                JeuDonneesEcriture.DATE,
                JeuDonneesEcriture.LIBELLE,
                JeuDonneesEcriture.MOUVEMENTS);

        EcritureDeBrouillard brouillard2 = creerBrouillard(
                JeuDonneesEcriture.ID_ECRITURE_1,
                JeuDonneesEcriture.DATE,
                "Autre libellé",
                JeuDonneesEcriture.MOUVEMENTS);

        assertNotEquals(brouillard1, brouillard2);
        assertNotEquals(brouillard2, brouillard1);
        assertTrue(brouillard1.estEquivalente(brouillard2));
    }

    @Test
    @DisplayName("À ID identique, un journal différent rend les écritures inégales")
    void equalsDistingueJournalDifferent()
            throws XComptaCoreException {

        EcritureDeBrouillard brouillard1 = creerBrouillard(
                JeuDonneesEcriture.ID_ECRITURE_1,
                JeuDonneesEcriture.DATE,
                JeuDonneesEcriture.LIBELLE,
                JeuDonneesEcriture.MOUVEMENTS);

        Journal autreJournal =
                new Journal("OD", "Opérations diverses");

        EcritureDeBrouillard brouillard2 = creerBrouillard(
                JeuDonneesEcriture.ID_ECRITURE_1,
                JeuDonneesEcriture.DATE,
                JeuDonneesEcriture.LIBELLE,
                autreJournal,
                JeuDonneesEcriture.MOUVEMENTS);

        assertNotEquals(brouillard1, brouillard2);
        assertNotEquals(brouillard2, brouillard1);
        assertTrue(brouillard1.estEquivalente(brouillard2));
    }

    @Test
    @DisplayName("À ID identique, des mouvements différents rendent les écritures inégales")
    void equalsDistingueContenuDifferent()
            throws XComptaCoreException {

        EcritureDeBrouillard brouillard1 = creerBrouillard(
                JeuDonneesEcriture.ID_ECRITURE_1,
                JeuDonneesEcriture.DATE,
                JeuDonneesEcriture.LIBELLE,
                JeuDonneesEcriture.MOUVEMENTS);

        List<JeuDonneesEcriture.MouvementDonnees> autresMouvements =
                List.of(
                        new JeuDonneesEcriture.MouvementDonnees(
                                "606",
                                "Achats non stockés",
                                new BigDecimal("1000.00")),
                        JeuDonneesEcriture.TVA,
                        JeuDonneesEcriture.FOURNISSEUR);

        EcritureDeBrouillard brouillard2 = creerBrouillard(
                JeuDonneesEcriture.ID_ECRITURE_1,
                JeuDonneesEcriture.DATE,
                JeuDonneesEcriture.LIBELLE,
                autresMouvements);

        assertNotEquals(brouillard1, brouillard2);
        assertNotEquals(brouillard2, brouillard1);
        assertFalse(brouillard1.estEquivalente(brouillard2));
    }

    private EcritureDeBrouillard creerBrouillard(
            int id,
            LocalDate date,
            String libelle,
            List<JeuDonneesEcriture.MouvementDonnees> donnees)
            throws XComptaCoreException {

        return creerBrouillard(
                id,
                date,
                libelle,
                new Journal(
                        JeuDonneesEcriture.CODE_JOURNAL,
                        JeuDonneesEcriture.LIBELLE_JOURNAL),
                donnees);
    }

    private EcritureDeBrouillard creerBrouillard(
            int id,
            LocalDate date,
            String libelle,
            Journal journal,
            List<JeuDonneesEcriture.MouvementDonnees> donnees)
            throws XComptaCoreException {

        List<MouvementDeBrouillard> mouvements =
                new ArrayList<>();

        for (JeuDonneesEcriture.MouvementDonnees donnee : donnees) {
            mouvements.add(
                    new MouvementDeBrouillard(
                            new Compte(
                                    donnee.numeroCompte(),
                                    donnee.libelleCompte()),
                            donnee.montant()));
        }

        return new EcritureDeBrouillard(
                        id,
                        libelle,
                        date,
                        journal,
                        mouvements
                );
    }

    private EcritureValidee creerValidee(
            int id,
            LocalDate date,
            String libelle,
            List<JeuDonneesEcriture.MouvementDonnees> donnees)
            throws XComptaCoreException {

        return creerValidee(
                id,
                date,
                libelle,
                new Journal(
                        JeuDonneesEcriture.CODE_JOURNAL,
                        JeuDonneesEcriture.LIBELLE_JOURNAL),
                donnees);
    }

    private EcritureValidee creerValidee(
            int id,
            LocalDate date,
            String libelle,
            Journal journal,
            List<JeuDonneesEcriture.MouvementDonnees> donnees)
            throws XComptaCoreException {

        List<MouvementValidee> mouvements =
                new ArrayList<>();

        int idMouvement = 1;

        for (JeuDonneesEcriture.MouvementDonnees donnee : donnees) {
            mouvements.add(
                    new MouvementValidee(
                            idMouvement++,
                            donnee.montant(),
                            new Compte(
                                    donnee.numeroCompte(),
                                    donnee.libelleCompte())));
        }

        return new EcritureValidee(id, libelle, date, journal, mouvements);
    }
}
